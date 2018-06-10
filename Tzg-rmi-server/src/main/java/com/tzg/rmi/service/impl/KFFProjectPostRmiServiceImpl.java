package com.tzg.rmi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

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
import com.tzg.common.service.kff.ProjectevastatService;
import com.tzg.common.service.kff.QfIndexService;
import com.tzg.common.service.kff.SuggestService;
import com.tzg.common.service.kff.TokenawardService;
import com.tzg.common.service.kff.TokenrecordsService;
import com.tzg.common.service.kff.UserCardService;
import com.tzg.common.service.kff.UserInvationService;
import com.tzg.common.service.kff.UserService;
import com.tzg.common.service.kff.UserWalletService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.projectevastat.ProjectevastatByGrade;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFProjectPostRmiService;

public class KFFProjectPostRmiServiceImpl implements KFFProjectPostRmiService {

	private static final Log logger = LogFactory.getLog(KFFProjectPostRmiServiceImpl.class);

	@Autowired
	private ArticleService kffArticleService;
	@Autowired
	private CollectService kffCollectService;
	@Autowired
	private CommentsService kffCommentsService;
	@Autowired
	private DiscussService kffDiscussService;
	@Autowired
	private EvaluationService kffEvaluationService;
	@Autowired
	private DevaluationModelService kffDevaluationModelService;
	@Autowired
	private DevaluationModelDetailService kffDevaluationModelDetailService;
	@Autowired
	private FollowService kffFollowService;
	@Autowired
	private PostService kffPostService;
	@Autowired
	private MessageService kffMessageService;
	@Autowired
	private MobileversionupdateService kffMobileversionupdateService;
	@Autowired
	private PraiseService kffPraiseService;
	@Autowired
	private ProjectService kffProjectService;
	@Autowired
	private TokenrecordsService kffTokenrecordsService;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private NoticeService kffNoticeService;
	@Autowired
	private SuggestService kffSuggestService;
	@Autowired
	private DareasService kffDareasService;
	@Autowired
	private DtagsService kffDtagsService;
	@Autowired
	private DprojectTypeService kffDprojectTypeService;
	@Autowired
	private CommendationService kffCommendationService;
	@Autowired
	private ProjectevastatService kffProjectevastatService;
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private UserInvationService userInvationService;
	@Autowired
	private QfIndexService qfIndexService;
	@Autowired
	private CoinPropertyService coinPropertyService;
	@Autowired
	private TokenawardService tokenawardService;
	@Autowired
	private UserWalletService userWalletService;
	@Autowired
	private TokenawardService kffTokenawardService;
	@Autowired
	private AwardPortService awardPortService;

	@Autowired
	private ZKClient zkClient;
	@Autowired
	private SystemParamService systemParamService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public Map<String, Object> findProjectEvaStatScore(Integer projectId) throws RestServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (projectId == null || projectId == 0) {
			throw new RestServiceException("错误项目id" + projectId);
		}
		List<ProjectevastatByGrade> result = new ArrayList<>();
		List<ProjectevastatByGrade> finalList = new ArrayList<>();

		for (int i = 0; i < 5; i++) {
			ProjectevastatByGrade grade = new ProjectevastatByGrade();
			grade.setGrade(i + 1);
			if (i == 0) {
				grade.setGradeLable("1-2分");
			} else if (i == 1) {
				grade.setGradeLable("3-4分");
			} else if (i == 2) {
				grade.setGradeLable("5-6分");
			} else if (i == 3) {
				grade.setGradeLable("7-8分");
			} else if (i == 4) {
				grade.setGradeLable("9-10分");
			}
			result.add(i, grade);
		}
		List<Evaluation> evas = kffEvaluationService.findAllTypeByProjectId(projectId);
		BigDecimal eachTotalScore = BigDecimal.ZERO;
		BigDecimal totalScore = BigDecimal.ZERO;
		int totalRaterNum = 0;
		if (CollectionUtils.isNotEmpty(evas)) {
			int gradeOneRaterNum = 0;
			int gradeTwoRaterNum = 0;
			int gradeThreeRaterNum = 0;
			int gradeFourRaterNum = 0;
			int gradeFiveRaterNum = 0;
			for (Evaluation eva : evas) {
				eachTotalScore = eva.getTotalScore() == null ? BigDecimal.ZERO : eva.getTotalScore();
				totalScore = totalScore.add(eachTotalScore);
				if (eachTotalScore.compareTo(new BigDecimal(2)) <= 0) {
					gradeOneRaterNum++;
				} else if (eachTotalScore.compareTo(new BigDecimal(4)) <= 0) {
					gradeTwoRaterNum++;
				} else if (eachTotalScore.compareTo(new BigDecimal(6)) <= 0) {
					gradeThreeRaterNum++;
				} else if (eachTotalScore.compareTo(new BigDecimal(8)) <= 0) {
					gradeFourRaterNum++;
				} else if (eachTotalScore.compareTo(new BigDecimal(10)) <= 0) {
					gradeFiveRaterNum++;
				}
				totalRaterNum++;
			}

			for (int i = 0; i < 5; i++) {
				ProjectevastatByGrade grade = result.get(i);
				if (i == 0) {
					grade.setRaterNum(gradeOneRaterNum);
					grade.setPercent((Integer) ((gradeOneRaterNum * 100) / totalRaterNum));
				} else if (i == 1) {
					grade.setRaterNum(gradeTwoRaterNum);
					grade.setPercent((Integer) ((gradeTwoRaterNum * 100) / totalRaterNum));
				} else if (i == 2) {
					grade.setRaterNum(gradeThreeRaterNum);
					grade.setPercent((Integer) ((gradeThreeRaterNum * 100) / totalRaterNum));
				} else if (i == 3) {
					grade.setRaterNum(gradeFourRaterNum);
					grade.setPercent((Integer) ((gradeFourRaterNum * 100) / totalRaterNum));
				} else if (i == 4) {
					grade.setRaterNum(gradeFiveRaterNum);
					grade.setPercent((Integer) ((gradeFiveRaterNum * 100) / totalRaterNum));
				}
				finalList.add(grade);
			}

		}

		resultMap.put("evaGradeStat", finalList);
		if (totalRaterNum == 0) {
			resultMap.put("totalScore", 0);
		} else {
			resultMap.put("totalScore", totalScore.divide(new BigDecimal(totalRaterNum)).setScale(1, RoundingMode.HALF_DOWN));
		}
		resultMap.put("totalRaterNum", totalRaterNum);
		return resultMap;
	}

}
