package com.tzg.rmi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.tzg.common.enums.LinkedType;
import com.tzg.common.service.kff.MessageService;
import com.tzg.common.service.kff.PostService;
import com.tzg.common.service.kff.ReportInforService;
import com.tzg.common.service.kff.ReportModelService;
import com.tzg.common.service.kff.ReportedContentService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.reportedcontent.ReportedContent;
import com.tzg.entitys.kff.reportinfor.ReportInfor;
import com.tzg.entitys.kff.reportmodel.ReportModel;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFReportRmiService;

/**
 * 
 * ClassName: KFFReportRmiServiceImpl  
 * Function: TODO  投诉相关接口的实现
 * date: 2018年8月13日 下午2:13:46  
 * 
 * @author zhangdd 
 * @version  
 * @since JDK 1.7 
 *
 */
public class KFFReportRmiServiceImpl implements KFFReportRmiService {

	private static final Log logger = LogFactory.getLog(KFFReportRmiServiceImpl.class);

	@Autowired
	private PostService kffPostService;
	@Autowired
	private UserService kffUserService;

	@Autowired
	private ReportModelService reportModelService;
	@Autowired
	private ReportedContentService reportedContentService;
	@Autowired
	private ReportInforService reportInforService;
	@Autowired
	private SystemParamService systemParamService;
	@Autowired
	private KFFRmiServiceImpl kffRmiServiceImpl;
	@Autowired
	private MessageService messageService;

	@Override
	public List<ReportModel> getReportModelList() throws RestServiceException {
		// TODO 获得投诉明细
		List<ReportModel> reportModelList = null;
		Map<String, Object> reportMap = new HashMap<String, Object>();
		reportMap.put("status", "1");
		reportMap.put("isShow", "1");
		reportModelList = reportModelService.findByMap(reportMap);
		return reportModelList;
	}

	@Override
	public void saveReport(Integer userId, Integer reportModelId, Integer contentId, Integer type) throws RestServiceException {
		// TODO 保存举报
		Date now = new Date();

		KFFUser reportUser = kffUserService.findById(userId);

		if (null == reportUser) {
			throw new RestServiceException("举报人不存在");
		}
		if (reportUser.getUsercardStatus() != 2) {
			throw new RestServiceException("尚未实名认证");
		}

		// 判断用户是否已经已经对此帖子进行投诉
		Map<String, Object> reportContentMap = new HashMap<String, Object>();
		reportContentMap.put("reportedContentId", contentId);
		reportContentMap.put("reportUserId", userId);

		reportContentMap.put("tcstatus", 1);
		reportContentMap.put("tfstatus", 1);
		Integer reportCount = reportedContentService.findIsReportToPContentFromUserId(reportContentMap);
		if (reportCount < 1) {// 说明此用户没有对此文章或者内容进行投诉
			ReportInfor reportInfor = new ReportInfor();
			reportInfor.setCreateTime(now);
			reportInfor.setUpdateTime(now);
			reportInfor.setStatus(1);
			reportInfor.setReportModelId(reportModelId);
			reportInfor.setReportUserName(reportUser.getUserName());
			reportInfor.setReportUserId(reportUser.getUserId());
			reportInfor.setReportUserMobile(reportUser.getMobile());

			if (type == 1) {// 进行的对post相关举报
				Post post = kffPostService.findById(contentId);
				if (null == post) {
					throw new RestServiceException("投诉的帖子不存在");
				}
				KFFUser contentCreateUser = kffUserService.findById(post.getCreateUserId());
				if (null == contentCreateUser) {
					throw new RestServiceException("帖子创始人不存在");
				}
				Map<String, Object> reportedContentMap = new HashMap<String, Object>();
				reportedContentMap.put("status", 1);
				reportedContentMap.put("vaild", 1);
				reportedContentMap.put("reportedContentId", contentId);
				reportedContentMap.put("contentTypeNotComments", "true");
				List<ReportedContent> reportedContentList = reportedContentService.findByMap(reportedContentMap);

				if (CollectionUtils.isEmpty(reportedContentList)) {
					ReportedContent reportedContent = new ReportedContent();
					reportedContent.setContentReportedTime(now);
					reportedContent.setContentTitle(post.getPostTitle());
					reportedContent.setContentType(post.getPostType());
					reportedContent.setReportedContentCreateId(post.getCreateUserId());
					reportedContent.setReportedContentCreateTime(post.getCreateTime());
					reportedContent.setReportedContentId(post.getPostId());

					reportedContent.setReportedCreaterMobile(contentCreateUser.getMobile());
					reportedContent.setReportedDegree(1);
					reportedContent.setStatus(1);
					reportedContent.setUpdateTime(now);
					reportedContent.setVaild(1);// 1投诉待审核
					reportedContent.setReportedContentCreateName(post.getCreateUserName());
					reportedContent.setReportedContentStatus(post.getStatus());
					if (post.getPostType() == 1) {// 1评测 2 讨论 3 文章
						SystemParam articlePara = systemParamService.findByCode(sysGlobals.EVA_URL);
						reportedContent.setReportedContentUrl(articlePara.getVcParamValue() + "?id=" + post.getPostId());
					}
					if (post.getPostType() == 2) {// 1评测 2 讨论 3 文章
						SystemParam articlePara = systemParamService.findByCode(sysGlobals.DIS_URL);
						reportedContent.setReportedContentUrl(articlePara.getVcParamValue() + "?id=" + post.getPostId());
					}
					if (post.getPostType() == 3) {// 1评测 2 讨论 3 文章
						SystemParam articlePara = systemParamService.findByCode(sysGlobals.ARTICLE_URL);
						reportedContent.setReportedContentUrl(articlePara.getVcParamValue() + "?id=" + post.getPostId());
					}

					reportedContentService.save(reportedContent);
					reportInfor.setReportedContentKeyId(reportedContent.getReportedContentKeyId());
				} else {
					ReportedContent reportedContent = reportedContentList.get(0);
					reportedContentService.increaseReportedDegree(reportedContent.getReportedContentKeyId());
					SystemParam systemParam = systemParamService.findByCode(sysGlobals.REPORT_DEGREE);
					if (null != systemParam) {
						String vcParamValue = systemParam.getVcParamValue();
						Integer valueOf = Integer.valueOf(vcParamValue) - 1;
						if (reportedContent.getReportedDegree() == valueOf) {// 当举报次数到达5时,先把post进行隐藏
							Post postDB = new Post();
							postDB.setPostId(post.getPostId());
							postDB.setStatus(0);
							kffPostService.update(postDB);
							ReportedContent reportedContentDB = new ReportedContent();
							reportedContentDB.setUpdateTime(now);
							reportedContentDB.setReportedContentKeyId(reportedContent.getReportedContentKeyId());
							reportedContentDB.setReportedContentStatus(0);
							reportedContentService.update(reportedContentDB);
							// 个推 通知被举报人 此文章被隐藏
							Post ppot = kffPostService.findById(post.getPostId());
							KFFUser kffUserc = kffUserService.findByUserId(ppot.getCreateUserId());
							if (null != kffUserc) {
								Integer linkedType = null;
								if (ppot.getPostType() == 1) {
									linkedType = LinkedType.CUSTOMEVALUATING.getValue();
								}
								if (ppot.getPostType() == 2) {
									linkedType = LinkedType.COUNTERFEIT.getValue();
								}
								if (ppot.getPostType() == 3) {
									linkedType = LinkedType.ARTICLE.getValue();
								}
								kffRmiServiceImpl.appNewsPush(linkedType, ppot.getPostId(), sysGlobals.DISABLE_FOR_CONTENT_TITLE, kffUserc.getMobile(),
										sysGlobals.DISABLE_FOR_CONTENT);
							}
							// 给被举报人发送APP消息
							KFFMessage msg = new KFFMessage();
							msg.setType(8);
							msg.setStatus(1);
							msg.setState(1);
							msg.setCreateTime(now);
							msg.setUpdateTime(now);
							msg.setUserId(ppot.getCreateUserId());
							msg.setTitle(sysGlobals.DISABLE_FOR_CONTENT_TITLE);
							msg.setContent(sysGlobals.DISABLE_FOR_CONTENT);
							msg.setSenderUserId(sysGlobals.QUFEN_ACCOUNT_ID);
							msg.setJumpInfo(sysGlobals.QUFEN_ACCOUNT_ID.toString());
							msg.setPostId(ppot.getPostId());
							msg.setPostType(ppot.getPostType());
							messageService.save(msg);
						}
					}
					reportInfor.setReportedContentKeyId(reportedContent.getReportedContentKeyId());
				}
				reportInforService.save(reportInfor);

			}

		}
	}
}
