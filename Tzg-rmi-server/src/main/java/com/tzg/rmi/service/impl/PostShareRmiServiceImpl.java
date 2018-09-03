package com.tzg.rmi.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.midi.SysexMessage;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hpsf.ReadingNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.CoinPropertyService;
import com.tzg.common.service.kff.PostShareService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.activity.PostShare;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.PostShareRmiService;

public class PostShareRmiServiceImpl implements PostShareRmiService {

	private static final Log logger = LogFactory.getLog(PostShareRmiServiceImpl.class);

	@Autowired
	private PostShareService postShareService;

	@Autowired
	private SystemParamService systemParamService;

	@Autowired
	private UserService userService;

	@Autowired
	private QfIndexService qfIndexService;

	@Autowired
	private TokenrecordsService tokenrecordsService;

	@Autowired
	private CoinPropertyService coinPropertyService;

	public PostShare findById(Integer id) {
		return postShareService.findById(id);
	}

	public List<PostShare> findListByAttr(Map<String, Object> map) {
		return postShareService.findListByAttr(map);
	}

	public Map<String, Object> save(PostShare postShare, Post post) throws RestServiceException {
		postShareService.save(postShare);
		// 分享,同一篇内容一天只能首次分享有奖励
		Map<String, Object> shareMap = new HashMap<String, Object>();
		shareMap.put("isShareAward", false);
		shareMap.put("amount", null);
		if (isShareToTokenAward(postShare)) {
			shareMap = sharePostTokenAward(postShare, post);

		}

		return shareMap;
	}

	private boolean isShareToTokenAward(PostShare postShare) {
		// TODO 判断今天是否此内容分享是否进行奖励发放
		Integer userId = postShare.getUserId();
		Integer articleId = postShare.getArticleId();
		// Date now = new Date();
		Map<String, Object> sharePostMap = new HashMap<String, Object>();
		sharePostMap.put("userId", userId);
		sharePostMap.put("articleId", articleId);
		List<PostShare> postShareList = postShareService.findByMap(sharePostMap);
		if (CollectionUtils.isNotEmpty(postShareList)) {
			if (postShareList.size() == 1) {// 首次分享
				return true;
			}
			if (postShareList.size() > 1) { // 判断是否是当天首次分享
				Date createdAt = postShareList.get(1).getCreatedAt();
				if (DateUtil.isToday(createdAt.getTime())) {
					return false;
				} else {
					return true;
				}
			}
		}
		return false;
	}

	private Map<String, Object> sharePostTokenAward(PostShare postShare, Post post) {
		// TODO 分享内容成功送币
		Date now = new Date();
		if (postShare.getId() == null) {
			throw new RestServiceException(RestErrorCode.SHARE_POST_FAIL);
		}
		Integer shareUserId = postShare.getUserId();
		Integer createPostUserId = post.getCreateUserId();
		QfIndex qfCreatePost = qfIndexService.findByUserId(createPostUserId);
		QfIndex qfShare = qfIndexService.findByUserId(shareUserId);
		BigDecimal amount = null;
		Boolean isShareAward = false;

		if (qfCreatePost != null && qfShare != null && qfCreatePost.getStatusHierarchyType() > 0 && qfShare.getStatusHierarchyType() > 0) {
			if (qfShare.getYxSharePost() != null && qfShare.getYxSharePost() > 0) {

				Integer yxSharePost = qfShare.getYxSharePost();// 有效分享次数
				if (yxSharePost != (int) (Math.floor(qfShare.getStatusHierarchyType() * 0.1))) {
					if (DateUtil.isToday(qfShare.getUpdateTime().getTime())) {// 判断点赞人的更新时间是不是今天
																				// 是今天不更新
																				// 不是今天更新

					} else {
						qfIndexService.updateSetYxComment(qfShare.getUserId());
					}
				}

				Tokenrecords tokenrecords = new Tokenrecords();
				tokenrecords.setUserId(postShare.getUserId());
				String format = String.format("%010d", postShare.getArticleId());
				tokenrecords.setTradeCode(format);
				tokenrecords.setTradeType(1);
				tokenrecords.setFunctionDesc("分享奖励");
				tokenrecords.setFunctionType(26);
				SystemParam amountDB = systemParamService.findByCode("SHARE_POST_AWARD_TOKEN");
				if (amountDB != null) {
					amount = new BigDecimal(amountDB.getVcParamValue());
					tokenrecords.setAmount(amount);
				}
				tokenrecords.setTradeDate(now);

				tokenrecords.setCreateTime(now);
				tokenrecords.setUpdateTime(now);
				tokenrecords.setStatus(1);

				tokenrecords.setRewardGrantType(1);
				tokenrecords.setPostId(post.getPostId());
				tokenrecordsService.save(tokenrecords);
				coinPropertyService.updateCoin(tokenrecords, 1);
				qfIndexService.updateYxSharePost(shareUserId);
				isShareAward = true;
			}

		}
		Map<String, Object> shareMap = new HashMap<String, Object>();
		shareMap.put("isShareAward", isShareAward);
		shareMap.put("amount", amount);
		return shareMap;
	}
}
