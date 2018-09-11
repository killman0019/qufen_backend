package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.qfindex.QfindexResponse;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFQfIndexService")
@Transactional(rollbackFor = Exception.class)
public class QfIndexService {

	@Autowired
	private com.tzg.entitys.kff.qfindex.QfIndexMapper QfIndexMapper;
	@Autowired
	private SystemParamService systemParamService;

	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;

	@Transactional(readOnly = true)
	public QfIndex findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return QfIndexMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		QfIndexMapper.deleteById(id);
	}

	public void save(QfIndex qfIndex) throws RestServiceException {
		QfIndexMapper.save(qfIndex);
	}

	public void update(QfIndex qfIndex) throws RestServiceException {
		if (qfIndex.getQfIndexId() == null) {
			throw new RestServiceException("id不能为空");
		}
		QfIndexMapper.update(qfIndex);
	}

	@Transactional(readOnly = true)
	public PageResult<QfIndex> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<QfIndex> result = null;
		try {
			Integer count = QfIndexMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<QfIndex> list = QfIndexMapper.findPage(query.getQueryData());
				result = new PageResult<QfIndex>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public QfIndex findByUserId(Integer userid) throws RestServiceException {
		if (userid == null) {
			throw new RestServiceException("id不能为空");
		}
		return QfIndexMapper.findByUserId(userid);
	}

	public void updateYxPraise(Integer userid) throws RestServiceException {
		if (userid == null) {
			throw new RestServiceException("用户id不能为空");
		}

		QfIndexMapper.updateYxPraise(userid);
	}

	public void updateYxPraiseValid() {
		QfIndexMapper.updateYxAll();
	}

	public List<QfIndex> findByUserIds(Map<String, Object> qfUsersMap) {

		return QfIndexMapper.findByUserIds(qfUsersMap);
	}

	public void updateYxSharePost(Integer shareUserId) {
		// TODO 分享奖励发放成功, 有效分享次数减少
		QfIndexMapper.updateyxSharePost(shareUserId);
	}

	public void updateYXcomment(Integer userId) {
		// TODO 分享成功,有效评论次数减少
		QfIndexMapper.updateYXcomment(userId);
	}

	public void updateSetYxPraise(Integer userId) {
		// TODO 仅仅更新有效赞
		QfIndexMapper.updateSetYxPraise(userId);
	}

	public void updateSetYxComment(Integer userId) {
		// TODO 仅仅更新有效评论
		QfIndexMapper.updateSetYxComment(userId);

	}

	public void updateSetSharePost(Integer userId) {
		// TODO 仅仅更新有效评论
		QfIndexMapper.updateSetSharePost(userId);

	}

	public void updateSetAll(Integer userId) {
		QfIndexMapper.updateSetAll(userId);
	}

	/**
	 * 
	* @Title: getMember 
	* @Description: TODO <获得用户的会员中心>
	* @author zhangdd <方法创建作者>
	* @create 下午5:30:00
	* @param @param userId
	* @param @return <参数说明>
	* @return QfindexResponse 
	* @throws 
	* @update 下午5:30:00
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public QfindexResponse getMember(Integer userId) {

		QfindexResponse qfindexResponse = new QfindexResponse();
		if (userId != null) {

			QfIndex qfindex = QfIndexMapper.findByUserId(userId);
			if (qfindex != null) {
				// 今日赚币
				Map<String, Object> tokenMap = new HashMap<String, Object>();
				long current = System.currentTimeMillis();// 当前时间毫秒数
				long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
				long twelve = zero + 24 * 60 * 60 * 1000 - 1;// 今天23点59分59秒的毫秒数
				Date createTimeBegin = new Date(zero);
				Date createTimeEnd = new Date(twelve);
				tokenMap.put("userId", userId);
				tokenMap.put("tradeType", 1);
				tokenMap.put("functionTypeAll", "true");
				tokenMap.put("creatTimeBegin", createTimeBegin);
				tokenMap.put("creatTimeEnd", createTimeEnd);
				tokenMap.put("status", 1);
				List<Tokenrecords> tokenRecordsList = tokenrecordsMapper.findByMap(tokenMap);// TODO
																								// 待完成添加时间区间
				Double amountSum = 0.0;// 所有挣的币
				Double loginSum = 0.0;// 所有登陆奖励
				Double commentSum = 0.0;
				if (CollectionUtils.isNotEmpty(tokenRecordsList)) {
					for (Tokenrecords tokenrecords : tokenRecordsList) {
						amountSum = amountSum + (tokenrecords.getAmount().doubleValue());
						if (tokenrecords.getFunctionType() == 16 || tokenrecords.getFunctionType() == 18) {
							loginSum = loginSum + (tokenrecords.getAmount().doubleValue());
						}
						if (tokenrecords.getFunctionType() == 24) {
							commentSum = commentSum + (tokenrecords.getAmount().doubleValue());
						}
					}

				}
				qfindexResponse.setTodayAward(amountSum);// 今天所有赚的币
				qfindexResponse.setLoginAward(BigDecimal.valueOf(loginSum));// 今日的登陆奖励
				qfindexResponse.setStatusHierarchyType(qfindex.getStatusHierarchyType());// 区分指数

				SystemParam sys = systemParamService.findByCode(sysGlobals.INVA_EACH_AWARD);
				SystemParam sharePostSys = systemParamService.findByCode(sysGlobals.SHARE_POST_AWARD_TOKEN);
				SystemParam praiseAwardSys = systemParamService.findByCode(sysGlobals.YX_PRAISE_TOKEN_TO_PRAYSER);
				SystemParam commentAwardSys = systemParamService.findByCode(sysGlobals.COMMENT_AWARD_TOKEN);

				qfindexResponse.setInvaEachAward(new BigDecimal(sys.getVcParamValue()));// 每邀请一个用户获得奖励

				qfindexResponse.setSharePostAward(Double.valueOf(sharePostSys.getVcParamValue()));// 每次分享获得奖励
				qfindexResponse.setPraiseAward(Double.valueOf(praiseAwardSys.getVcParamValue()));// 进行点赞给点赞人的奖励
				qfindexResponse.setCommentAward(Double.valueOf(commentAwardSys.getVcParamValue()));// 给予评论人的奖励

				// 获得评论收益 从交易流失表中
				// 获得区分指数取整数值

				int i = (int) Math.floor(qfindex.getStatusHierarchyType() * 0.01);// 向下取整
				Integer yxComments = qfindex.getYxComments();
				Integer yxSharePost = qfindex.getYxSharePost();
				Integer yxPraise = qfindex.getYxpraise();
				Integer yxCommentRece = i - yxComments;
				Integer yxSharePostRece = i - yxSharePost;
				Integer yxPraiseRece = i = yxPraise;
				qfindexResponse.setCommentDegr(yxCommentRece);
				qfindexResponse.setPraiseDegr(yxPraiseRece);
				qfindexResponse.setSharePostDegr(yxSharePostRece);
				if (yxCommentRece == i) {
					qfindexResponse.setCommentReceStatus(1);// 已领取
				}
				if (yxPraiseRece == i) {
					qfindexResponse.setPraiseReceStatus(1);
				}
				if (yxSharePostRece == i) {
					qfindexResponse.setSharePostReceStatus(1);
				}

				qfindexResponse.setCommentAward(commentSum);// 评论奖励

				Double praiseSum = yxPraiseRece * Double.valueOf(praiseAwardSys.getVcParamValue());
				qfindexResponse.setPraiseAward(praiseSum);// 点赞奖励
				Double sharePostSum = yxSharePostRece * Double.valueOf(sharePostSys.getVcParamValue());
				qfindexResponse.setSharePostAward(sharePostSum);// 分享奖励

			}
		}
		return qfindexResponse;
	}

	/**
	 * 
	* @Title: increasePushEvaCount 
	* @Description: TODO <增加区分指数表中的有效评测次数>
	* @author zhangdd <方法创建作者>
	* @create 上午11:40:39
	* @param @param userId <参数说明>
	* @return void 
	* @throws 
	* @update 上午11:40:39
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void increasePushEvaCount(Integer userId) {

		QfIndexMapper.increasePushEvaCount(userId);
	}
}
