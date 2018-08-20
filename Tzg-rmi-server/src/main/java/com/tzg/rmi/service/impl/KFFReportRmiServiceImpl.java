package com.tzg.rmi.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import com.tzg.common.redis.RedisService;
import com.tzg.common.service.kff.ArticleService;
import com.tzg.common.service.kff.AuthenticationService;
import com.tzg.common.service.kff.AwardPortService;
import com.tzg.common.service.kff.CoinPropertyService;
import com.tzg.common.service.kff.CollectService;
import com.tzg.common.service.kff.CommendationService;
import com.tzg.common.service.kff.CommentsService;
import com.tzg.common.service.kff.DareasService;
import com.tzg.common.service.kff.DevaluationModelDetailService;
import com.tzg.common.service.kff.DevaluationModelService;
import com.tzg.common.service.kff.DiscussService;
import com.tzg.common.service.kff.DprojectTypeService;
import com.tzg.common.service.kff.DtagsService;
import com.tzg.common.service.kff.EvaluationService;
import com.tzg.common.service.kff.FollowService;
import com.tzg.common.service.kff.MessageService;
import com.tzg.common.service.kff.MobileversionupdateService;
import com.tzg.common.service.kff.NoticeService;
import com.tzg.common.service.kff.PostService;
import com.tzg.common.service.kff.PraiseService;
import com.tzg.common.service.kff.ProjectService;
import com.tzg.common.service.kff.ProjectTradeService;
import com.tzg.common.service.kff.ProjectevastatService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.ReportInforService;
import com.tzg.common.service.kff.ReportModelService;
import com.tzg.common.service.kff.ReportedContentService;
import com.tzg.common.service.kff.SuggestService;
import com.tzg.common.service.kff.TokenawardService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.UserCardService;
import com.tzg.common.service.kff.UserInvationService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.kff.UserWalletService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.reportedcontent.ReportedContent;
import com.tzg.entitys.kff.reportinfor.ReportInfor;
import com.tzg.entitys.kff.reportmodel.ReportModel;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFReportRmiService;
import com.tzg.rmi.service.SmsSendRmiService;

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

		ReportInfor reportInfor = new ReportInfor();
		reportInfor.setCreateTime(now);
		reportInfor.setUpdateTime(now);
		reportInfor.setStatus(1);
		reportInfor.setReportModelId(reportModelId);
		reportInfor.setReportUserName(reportUser.getUserName());
		reportInfor.setReportUserId(reportUser.getUserId());

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
				reportedContent.setReportedContentUrl(null);
				reportedContent.setReportedCreaterMobile(contentCreateUser.getMobile());
				reportedContent.setReportedDegree(1);
				reportedContent.setStatus(1);
				reportedContent.setUpdateTime(now);
				reportedContent.setVaild(1);// 1投诉待审核
				reportedContent.setReportedContentCreateName(post.getCreateUserName());
				reportedContent.setReportedContentStatus(post.getStatus());
				if (post.getPostType() == 1) {// 1评测 2 讨论 3 文章
					SystemParam articlePara = systemParamService.findByCode("EVA_URL");
					reportedContent.setReportedContentUrl(articlePara.getVcParamValue() + "?id=" + post.getPostId());
				}
				if (post.getPostType() == 2) {// 1评测 2 讨论 3 文章
					SystemParam articlePara = systemParamService.findByCode("DIS_URL");
					reportedContent.setReportedContentUrl(articlePara.getVcParamValue() + "?id=" + post.getPostId());
				}
				if (post.getPostType() == 3) {// 1评测 2 讨论 3 文章
					SystemParam articlePara = systemParamService.findByCode("ARTICLE_URL");
					reportedContent.setReportedContentUrl(articlePara.getVcParamValue() + "?id=" + post.getPostId());
				}

				reportedContentService.save(reportedContent);
				reportInfor.setReportedContentKeyId(reportedContent.getReportedContentKeyId());
			} else {
				ReportedContent reportedContent = reportedContentList.get(0);
				reportedContentService.increaseReportedDegree(reportedContent.getReportedContentKeyId());
				SystemParam systemParam = systemParamService.findByCode("REPORT_DEGREE");
				if (null != systemParam) {
					String vcParamValue = systemParam.getVcParamValue();
					if (reportedContent.getReportedDegree() == Integer.valueOf(vcParamValue)) {// 当举报次数到达5时,先把post进行隐藏
						Post postDB = new Post();
						postDB.setPostId(post.getPostId());
						postDB.setStatus(0);
						kffPostService.update(postDB);
					}
				}
				reportInfor.setReportedContentKeyId(reportedContent.getReportedContentKeyId());
			}
			reportInforService.save(reportInfor);

		}

	}
}
