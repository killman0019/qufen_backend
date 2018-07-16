package com.tzg.rmi.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.common.base.Objects;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
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
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.zookeeper.ZKClient;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostFile;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.projectevastat.Projectevastat;
import com.tzg.entitys.kff.projectevastat.ProjectevastatByGrade;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.user.KFFUser;
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
	private PraiseService praiseService;
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
		// List<Evaluation> evas = kffEvaluationService.findSimpleEvaByProjectId(projectId);
		List<Evaluation> evas = kffEvaluationService.selectAllEvaExceptOnlyEvaByProjectId(projectId);
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
			// resultMap.put("totalScore", 0);
		} else {
			// resultMap.put("totalScore", totalScore.divide(new BigDecimal(totalRaterNum), 1,
			// RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN));
		}
		resultMap.put("totalRaterNum", totalRaterNum);
		return resultMap;
	}

	@Override
	public PageResult<Comments> findPagecommentCommentsList(Integer userId, PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		System.err.println("1comments" + JSON.toJSONString(comments));
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);// 2
			logger.info("评论数量" + comments.getRows().size());
			// 添加数组
			List<Comments> list = new ArrayList<Comments>();
			for (Comments comment : comments.getRows()) {
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {

					for (Comments commentsone : childComments.getRows()) {
						System.err.println("孩子" + JSON.toJSONString(commentsone));
						if (null != commentsone.getParentCommentsId()) {
							list.add(commentsone);

						}
						while (CollectionUtils.isNotEmpty(commentsone.getChildCommentsList())) {

							list.addAll(commentsone.getChildCommentsList());

						}
						comment.setChildCommentsList(list);
						comment.setChildCommentsNum(childComments.getRowCount());
					}

				}
				// 登录用户判断点赞状态
				if (user != null) {
					Praise praise = kffPraiseService.findByCommentsId(userId, comment.getCommentsId());
					if (praise != null) {
						comment.setPraiseStatus(praise.getStatus());
					}
				} else {
					comment.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
				}
			}

		}
		System.err.println("comments" + JSON.toJSONString(comments));
		return comments;

	}

	@Override
	public PageResult<PostResponse> findMyPageFollowList(Integer userId, PaginationQuery query) {
		PageResult<PostResponse> result = new PageResult<>();
		if (userId == null || userId == 0) {
			throw new RestServiceException("用户未登录,请重新登录");
		}
		query.addQueryData("userId", userId + "");
		// 1 关注的用户 点赞帖子 2关注的用户 发表帖子 3关注的用户 关注项目 4关注的项目下发表的帖子
		PageResult<Post> posts = kffPostService.findMyPageFollowList(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			List<PostResponse> rows = new ArrayList<>();
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			KFFUser user = null;

			for (Post post : posts.getRows()) {
				PostResponse pr = new PostResponse();
				Post realPost = null;
				// 评测去除简单评测
				if (Objects.equal(1, post.getPostType())) {
					user = kffUserService.findById(post.getCreateUserId());
					pr.setActionDesc(user == null ? "匿名用户" : user.getUserName() + "点赞了帖子");
					realPost = kffPostService.findById(post.getPostId());
					if (realPost != null && Objects.equal(1, realPost.getPostType())) {
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						if (eva != null && Objects.equal(1, eva.getModelType())) {
							continue;
						} else {
							BeanUtils.copyProperties(realPost, pr);
						}
						pr.setTotalScore(eva.getTotalScore());
					} else if (realPost != null) {
						BeanUtils.copyProperties(realPost, pr);
					}

					// 讨论返回标签
					if (realPost != null && Objects.equal(2, realPost.getPostType())) {
						Discuss dis = kffDiscussService.findByPostId(realPost.getPostId());
						if (dis != null) {
							pr.setTagInfos(dis.getTagInfos());
						}
					}
					Post postPro = kffPostService.findById(post.getPostId());
					Follow existFollow = kffFollowService.findByUserIdAndFollowTypeShow(userId, KFFConstants.FOLLOW_TYPE_PROJECT, postPro.getProjectId());
					// 关注状态
					if (null != existFollow) {
						pr.setFollowStatus(1);
					} else {
						pr.setFollowStatus(0);
					}
				} else if (Objects.equal(2, post.getPostType())) {
					user = kffUserService.findById(post.getCreateUserId());
					pr.setActionDesc(user == null ? "匿名用户" : user.getUserName() + "发表了帖子");
					realPost = kffPostService.findById(post.getPostId());
					if (realPost != null && Objects.equal(1, realPost.getPostType())) {
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						if (eva != null && Objects.equal(1, eva.getModelType())) {
							continue;
						} else {
							BeanUtils.copyProperties(realPost, pr);
						}
						pr.setTotalScore(eva.getTotalScore());
					} else if (realPost != null) {
						BeanUtils.copyProperties(realPost, pr);
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						// pr.setTotalScore(eva.getTotalScore());
					}
					// 讨论返回标签
					if (realPost != null && Objects.equal(2, realPost.getPostType())) {
						Discuss dis = kffDiscussService.findByPostId(realPost.getPostId());
						if (dis != null) {
							pr.setTagInfos(dis.getTagInfos());
						}
					}
					Post postPro = kffPostService.findById(post.getPostId());
					Follow existFollow = kffFollowService.findByUserIdAndFollowTypeShow(userId, KFFConstants.FOLLOW_TYPE_PROJECT, postPro.getProjectId());
					// 关注状态
					if (null != existFollow) {
						pr.setFollowStatus(1);
					} else {
						pr.setFollowStatus(0);
					}
				} else if (Objects.equal(3, post.getPostType())) {
					user = kffUserService.findById(post.getCreateUserId());
					pr.setActionDesc(user == null ? "匿名用户" : user.getUserName() + "关注了项目");
					KFFProject project = kffProjectService.findById(post.getPostId());
					if (project != null) {
						BeanUtils.copyProperties(project, pr);
					}
					pr.setCreateUserId(user == null ? null : user.getUserId());
					pr.setCreateUserIcon(user == null ? "/upload/avatars/default.png" : user.getIcon());
					pr.setCreateUserName(user == null ? "匿名用户" : user.getUserName());
					pr.setCreateUserSignature(user == null ? "" : user.getUserSignature());
					// 项目 关注状态
					Follow follow = kffFollowService.findByUserIdAndFollowTypeShow(userId, 1, post.getPostId());
					if (follow != null && Objects.equal(1, follow.getStatus())) {
						pr.setFollowStatus(1);
					} else {
						pr.setFollowStatus(0);
					}
					pr.setTotalScore(project.getTotalScore());
				} else if (Objects.equal(4, post.getPostType())) {
					pr.setActionDesc("关注项目下发表了新帖");
					realPost = kffPostService.findById(post.getPostId());
					if (realPost != null && Objects.equal(1, realPost.getPostType())) {
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						if (eva != null && Objects.equal(1, eva.getModelType())) {
							continue;
						} else {
							BeanUtils.copyProperties(realPost, pr);
						}
						pr.setTotalScore(eva.getTotalScore());
					} else if (realPost != null) {
						BeanUtils.copyProperties(realPost, pr);
					}
					// 讨论返回标签
					if (realPost != null && Objects.equal(2, realPost.getPostType())) {
						Discuss dis = kffDiscussService.findByPostId(realPost.getPostId());
						if (dis != null) {
							pr.setTagInfos(dis.getTagInfos());
						}
					}
					Post postPro = kffPostService.findById(post.getPostId());
					Follow existFollow = kffFollowService.findByUserIdAndFollowTypeShow(userId, KFFConstants.FOLLOW_TYPE_PROJECT, postPro.getProjectId());
					// 关注状态
					if (null != existFollow) {
						pr.setFollowStatus(1);
					} else {
						pr.setFollowStatus(0);
					}
				} else {
					continue;
				}
				pr.setActionType(post.getPostType());
				pr.setCommentsNum(post.getCommentsNum());
				rows.add(pr);
			}
			result.setRows(rows);
		}

		return result;
	}

	@Override
	public Map<String, Object> findProjectEvaStat(Integer projectId) throws RestServiceException {
		Map<String, Object> resultMap = new HashMap<>();
		int totalProEvaRaterNum = 0;
		if (projectId == null || projectId == 0) {
			throw new RestServiceException("项目id不能为空" + projectId);
		}

		List<DevaluationModelDetail> result = new ArrayList<>();

		DevaluationModel activeProModel = findSysActiveProModel();
		List<DevaluationModelDetail> details = findSysEvaModelDetailList(activeProModel);

		List<DevaluationModelDetail> detailList = new ArrayList<DevaluationModelDetail>();

		if (details != null && CollectionUtils.isNotEmpty(details)) {
			Map<String, BigDecimal> detailModelTotalScore = new HashMap<String, BigDecimal>();
			Map<String, Integer> detailModelTotalRater = new HashMap<String, Integer>();
			List<Evaluation> proEvas = kffEvaluationService.findProEvaByProjectId(projectId);
			if (CollectionUtils.isNotEmpty(proEvas)) {
				for (Evaluation eva : proEvas) {
					try {
						eva.getTotalScore();
						List<DevaluationModel> models = JSON.parseArray(eva.getProfessionalEvaDetail(), DevaluationModel.class);
						if (CollectionUtils.isEmpty(models)) {
							continue;
						}
						BigDecimal score = BigDecimal.ZERO;
						Integer raterNum = 0;
						for (DevaluationModel model : models) {
							if (StringUtils.isBlank(model.getModelName())) {
								continue;// 跳过此次循环直接下次循环
							}
							score = detailModelTotalScore.get(model.getModelName()) == null ? BigDecimal.ZERO : detailModelTotalScore.get(model.getModelName());
							score = score.add(model.getScore() == null ? BigDecimal.ZERO : model.getScore());
							detailModelTotalScore.put(model.getModelName(), score);
							raterNum = detailModelTotalRater.get(model.getModelName()) == null ? 0 : detailModelTotalRater.get(model.getModelName());
							raterNum++;
							detailModelTotalRater.put(model.getModelName(), raterNum);

						}
						totalProEvaRaterNum++;
					} catch (Exception e) {
						logger.error("专业评测统计解析评测内容出错,evaId:" + eva.getEvaluationId() + " details:" + eva.getProfessionalEvaDetail());
					}

				}

			}

			for (DevaluationModelDetail detail : details) {

				BigDecimal totalScore = BigDecimal.ZERO;
				detail.setRaterNum(detailModelTotalRater.get(detail.getDetailName()) == null ? 0 : detailModelTotalRater.get(detail.getDetailName()));
				if (Objects.equal(0, detail.getRaterNum())) {
					detail.setTotalScore(BigDecimal.ZERO);
				} else {
					totalScore = detailModelTotalScore.get(detail.getDetailName()) == null ? BigDecimal.ZERO : detailModelTotalScore
							.get(detail.getDetailName());
					detail.setTotalScore(totalScore.divide(new BigDecimal(detail.getRaterNum()), 1, RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN));
				}

			}
		}
		// end

		// PaginationQuery query = new PaginationQuery();
		// query.addQueryData("modelId", "3");
		// query.addQueryData("sortField", "comments_num");
		// query.addQueryData("status", "1");
		// query.setPageIndex(1);
		// query.setRowsPerPage(100);
		// PageResult<Projectevastat> proEvaStat = kffProjectevastatService.findPage(query);
		// Map<String, Object> evaMap = new HashMap<String, Object>();
		// if (proEvaStat != null && CollectionUtils.isNotEmpty(proEvaStat.getRows())) {
		// for (Projectevastat eva : proEvaStat.getRows()) {
		//
		// }
		// }
		// for (DevaluationModelDetail detail : details) {
		// float x = 8.1f;
		// if (detail.getId() == 10)
		// x = 9.1f;
		// if (detail.getId() == 13)
		// x = 6.1f;
		// BigDecimal totalScore = BigDecimal.valueOf(x).setScale(1, BigDecimal.ROUND_HALF_DOWN);
		// detail.setTotalScore(totalScore);
		// detail.setRaterNum(Integer.valueOf(RandomUtil.produceNumber(3)));
		//
		// result.add(detail);
		// }
		// }

		/*for(DevaluationModelDetail detail:details)
		{
			System.out.println("====================" +detail.getTotalScore());
			if(null != detail.getTotalScore()&&detail.getTotalScore().compareTo(BigDecimal.ZERO) !=0)
			{
				detailList.add(detail);
			}
		}*/
		resultMap.put("projectEvaStat", details);// project主页纬度
		resultMap.put("totalProEvaRaterNum", totalProEvaRaterNum);// 完整加自定义
		return resultMap;
	}

	private DevaluationModel findSysActiveProModel() {
		DevaluationModel result = null;
		List<DevaluationModel> modelPage = kffDevaluationModelService.selectEvaluationModelByModelType(KFFConstants.EVA_MODEL_TYPE_FULL_PRO);
		if (modelPage == null || CollectionUtils.isEmpty(modelPage)) {
			// throw new RestServiceException("当前不存在生效中的系统模型");
			return result;
		}
		result = modelPage.get(0);
		return result;
	}

	private List<DevaluationModelDetail> findSysEvaModelDetailList(DevaluationModel activeProModel) throws RestServiceException {
		List<DevaluationModelDetail> result = null;
		if (activeProModel == null) {
			return result;
		}
		PaginationQuery detailQuery = new PaginationQuery();
		detailQuery.setPageIndex(1);
		detailQuery.setRowsPerPage(10);
		detailQuery.addQueryData("modelId", activeProModel.getModelId() + "");
		detailQuery.addQueryData("status", "1");
		PageResult<DevaluationModelDetail> modelDetailList = kffDevaluationModelDetailService.findPage(detailQuery);

		if (modelDetailList != null) {
			result = modelDetailList.getRows();
		}
		return result;
	}

	@Override
	public List<PostResponse> findHotEvaList(Integer projectId) throws RestServiceException {
		// https://www.tapd.cn/21950911/bugtrace/bugs/view?bug_id=1121950911001000461
		// 规则：点赞量超过10 & 排名前2的内容
		if (projectId == null) {
			throw new RestServiceException("项目id不能为空");
		}
		// Date day7 = DateUtil.getSpecifiedDateBeforeOrAfter(7).getTime();
		List<PostResponse> respones = new ArrayList<>();
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("projectId", projectId + "");
		// query.addQueryData("sortField", "comments_num");
		query.addQueryData("sortField", "praise_num");
		query.addQueryData("status", "1");
		query.addQueryData("postType", "1");
		// query.addQueryData("createTimeBegin", DateUtil.getDate(day7, "yyyy-MM-dd"));
		query.setPageIndex(1);
		query.setRowsPerPage(2);
		PageResult<Post> posts = kffPostService.findPage(query);
		if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
			for (Post post : posts.getRows()) {
				if (post.getPraiseNum() < 10) {
					continue;
				}
				PostResponse response = new PostResponse();
				BeanUtils.copyProperties(post, response);
				if (StringUtils.isNotBlank(post.getPostSmallImages())) {
					try {
						List<PostFile> pfl = JSONArray.parseArray(post.getPostSmallImages(), PostFile.class);
						response.setPostSmallImagesList(pfl);
					} catch (Exception e) {
						logger.error("讨论列表解析帖子缩略图json出错:{}", e);
					}
				}
				Evaluation eva = kffEvaluationService.findByPostId(post.getPostId());
				if (eva != null) {
					if (Objects.equal(1, eva.getModelType())) {
						continue;
					}
					response.setEvaluationTags(eva.getEvaluationTags());
					response.setEvaTotalScore(eva.getTotalScore());
					response.setEvauationContent(eva.getEvauationContent());
					response.setProfessionalEvaDetail(eva.getProfessionalEvaDetail());
				}
				respones.add(response);
			}
		}
		return respones;
	}

	@Override
	public PageResult<EvaluationDetailResponse> findPageSimpleEvaluationList(PaginationQuery query, Integer userId) {

		PageResult<EvaluationDetailResponse> evaList = kffEvaluationService.findPageSimpleEvaluation(query, userId);
		return evaList;
	}

	/**
	 * 查询项目中的总分数
	 */
	@Override
	public Map<String, Object> selectProjectAllTotalScore(Integer projectId) throws RestServiceException {
		Map<String, Object> resultMap = new HashMap<>();
		int totalProEvaRaterNum = 0;
		if (projectId == null || projectId == 0) {
			throw new RestServiceException("项目id不能为空" + projectId);
		}

		List<DevaluationModelDetail> result = new ArrayList<>();

		DevaluationModel activeProModel = findSysActiveProModel();
		List<DevaluationModelDetail> details = findSysEvaModelDetailList(activeProModel);

		List<DevaluationModelDetail> detailList = new ArrayList<DevaluationModelDetail>();

		if (details != null && CollectionUtils.isNotEmpty(details)) {
			Map<String, BigDecimal> detailModelTotalScore = new HashMap<String, BigDecimal>();
			Map<String, Integer> detailModelTotalRater = new HashMap<String, Integer>();
			List<Evaluation> proEvas = kffEvaluationService.findProEvaByProjectId(projectId);// 获得单项评测纬度
			if (CollectionUtils.isNotEmpty(proEvas)) {
				for (Evaluation eva : proEvas) {
					try {
						eva.getTotalScore();
						List<DevaluationModel> models = JSON.parseArray(eva.getProfessionalEvaDetail(), DevaluationModel.class);

						if (CollectionUtils.isEmpty(models)) {
							continue;
						}
						BigDecimal score = BigDecimal.ZERO;
						Integer raterNum = 0;
						for (DevaluationModel model : models) {
							if (StringUtils.isBlank(model.getModelName())) {
								continue;
							}
							score = detailModelTotalScore.get(model.getModelName()) == null ? BigDecimal.ZERO : detailModelTotalScore.get(model.getModelName());
							score = score.add(model.getScore() == null ? BigDecimal.ZERO : model.getScore());
							detailModelTotalScore.put(model.getModelName(), score);
							raterNum = detailModelTotalRater.get(model.getModelName()) == null ? 0 : detailModelTotalRater.get(model.getModelName());
							raterNum++;
							detailModelTotalRater.put(model.getModelName(), raterNum);

						}
						totalProEvaRaterNum++;

					} catch (Exception e) {
						logger.error("专业评测统计解析评测内容出错,evaId:" + eva.getEvaluationId() + " details:" + eva.getProfessionalEvaDetail());
					}

				}

			}

			for (DevaluationModelDetail detail : details) {

				BigDecimal totalScore = BigDecimal.ZERO;
				detail.setRaterNum(detailModelTotalRater.get(detail.getDetailName()) == null ? 0 : detailModelTotalRater.get(detail.getDetailName()));
				if (Objects.equal(0, detail.getRaterNum())) {
					detail.setTotalScore(BigDecimal.ZERO);
				} else {
					totalScore = detailModelTotalScore.get(detail.getDetailName()) == null ? BigDecimal.ZERO : detailModelTotalScore
							.get(detail.getDetailName());
					detail.setTotalScore(totalScore.divide(new BigDecimal(detail.getRaterNum()), 1, RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN));
				}

			}
		}
		resultMap.put("projectEvaStat", details);// project主页纬度
		resultMap.put("totalProEvaRaterNum", totalProEvaRaterNum);// 完整加自定义
		return resultMap;
	}

	@Override
	public KFFProject selectProjectByProjectId(Integer projectId) {
		KFFProject project = null;
		if (null != projectId) {
			project = kffProjectService.findById(projectId);
		}
		return project;
	}

	@Override
	public Map<String, Object> selectProjectEvaStatSelf(Integer projectId) throws RestServiceException {
		Map<String, Object> resultMap = new HashMap<>();
		int totalProEvaRaterNum = 0;
		if (projectId == null || projectId == 0) {
			throw new RestServiceException("项目id不能为空" + projectId);
		}

		List<DevaluationModelDetail> result = new ArrayList<>();

		DevaluationModel activeProModel = findSysActiveProModel();
		List<DevaluationModelDetail> details = findSysEvaModelDetailList(activeProModel);

		List<DevaluationModelDetail> detailList = new ArrayList<DevaluationModelDetail>();

		if (details != null && CollectionUtils.isNotEmpty(details)) {
			Map<String, BigDecimal> detailModelTotalScore = new HashMap<String, BigDecimal>();
			Map<String, Integer> detailModelTotalRater = new HashMap<String, Integer>();
			List<Evaluation> proEvas = kffEvaluationService.selectProOnlyEvaByProjectId(projectId);// 项目中的单项评测的内容
			QfIndex qfIndex = null;
			Integer ownerQfScore = 100;
			BigDecimal totalDividor = BigDecimal.ZERO;
			Long totalDividend = 0L;
			Integer raterNum = 0;
			if (CollectionUtils.isNotEmpty(proEvas)) {
				for (Evaluation eva : proEvas) {
					BigDecimal oneDividor = BigDecimal.ZERO;
					Long oneDividend = 0L;
					if (null != eva && eva.getProfessionalEvaDetail() != null) {
						BigDecimal oneScore = eva.getTotalScore() == null ? BigDecimal.ZERO : eva.getTotalScore();// 取分数
						// eva.getProfessionalEvaDetail()
						List<DevaluationModel> models = JSON.parseArray(eva.getProfessionalEvaDetail(), DevaluationModel.class);
						if (CollectionUtils.isNotEmpty(models) && models.size() > 0) {
							if (models.size() == 1) {
								qfIndex = qfIndexService.findByUserId(eva.getCreateUserId());
								if (qfIndex != null) {
									ownerQfScore = qfIndex.getStatusHierarchyType() == null ? 100 : qfIndex.getStatusHierarchyType();// 取区分指数
								}
								// 单项评测
								if (Objects.equal(3, eva.getModelType())) {

									List<Praise> praises = praiseService.findAllActivePraisesByPostId(eva, projectId);// 获取单项评测点赞对象
									if (CollectionUtils.isEmpty(praises)) {// 没有赞
										oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
										oneDividend = ownerQfScore * 100L;
									} else {// 有赞
										// 判断赞的个数
										List<QfIndex> priaseUserQfIndexs = new ArrayList<QfIndex>();
										if (praises.size() != 1) {
											List<Integer> praiseUserIds = new ArrayList<>();
											for (Praise p : praises) {
												praiseUserIds.add(p.getPraiseUserId());
											}
											Map<String, Object> qfUsersMap = new HashMap<>();
											String userIds = StringUtils.join(praiseUserIds.toArray(), ",");
											qfUsersMap.put("userIds", userIds);
											priaseUserQfIndexs = qfIndexService.findByUserIds(qfUsersMap); // qfIndexMapper.findByUserIds(qfUsersMap);
										} else {

											QfIndex priaseqfIndexUser = qfIndexService.findByUserId(praises.get(0).getPraiseUserId());
											if (null != priaseqfIndexUser) {
												priaseUserQfIndexs.add(priaseqfIndexUser);
											}
										}

										if (!CollectionUtils.isEmpty(priaseUserQfIndexs)) {
											Integer totalQf = 0;
											for (QfIndex qf : priaseUserQfIndexs) {
												totalQf = totalQf + (qf.getStatusHierarchyType() == null ? 100 : qf.getStatusHierarchyType());
											}
											if (totalQf == 0)
												totalQf = 100;
											oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(totalQf));
											oneDividend = ownerQfScore * (Long.valueOf(totalQf));
										} else {
											oneDividor = oneScore.multiply(new BigDecimal(ownerQfScore)).multiply(new BigDecimal(100));
											oneDividend = ownerQfScore * 100L;
										}
									}
									raterNum = detailModelTotalRater.get(models.get(0).getModelName()) == null ? 0 : detailModelTotalRater.get(models.get(0)
											.getModelName());
									raterNum++;
									detailModelTotalRater.put(models.get(0).getModelName(), raterNum);
								}
								totalDividor = totalDividor.add(oneDividor);
								totalDividend = totalDividend + oneDividend;
							}
						}
						BigDecimal totalScore = BigDecimal.ZERO;
						// logger.info("分子" + totalDividor);
						// logger.info("分母" + totalDividend);
						if (totalDividend != 0) {
							totalScore = totalDividor.divide(new BigDecimal(totalDividend), 1, RoundingMode.HALF_DOWN);
							// logger.info("totalScore" + totalScore);
							detailModelTotalScore.put(models.get(0).getModelName(), totalScore);
							// logger.info("key" + models.get(0).getModelName());
						}
					}
				}
			}

			for (DevaluationModelDetail detail : details) {

				BigDecimal totalScore = BigDecimal.ZERO;
				detail.setRaterNum(detailModelTotalRater.get(detail.getDetailName()) == null ? 0 : detailModelTotalRater.get(detail.getDetailName()));
				// logger.info("key2: " + detail.getDetailName());
				if (Objects.equal(0, detail.getRaterNum())) {
					detail.setTotalScore(BigDecimal.ZERO);
				} else {
					/*totalScore = detailModelTotalScore.get(detail.getDetailName()) == null ? BigDecimal.ZERO : detailModelTotalScore
							.get(detail.getDetailName());
					detail.setTotalScore(totalScore.divide(new BigDecimal(detail.getRaterNum()), 1, RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN));
					*/
					// 单项评测添加区分指数计算
					totalScore = detailModelTotalScore.get(detail.getDetailName()) == null ? BigDecimal.ZERO : detailModelTotalScore
							.get(detail.getDetailName());
					// logger.info("totalScore2: " + totalScore);
					detail.setTotalScore(totalScore);
				}

			}
		}

		resultMap.put("projectEvaStat", details);// project主页纬度
		for (DevaluationModelDetail devaluationModelDetail : details) {
			// logger.info("devaluationModelDetail.getDetailName()" +
			// devaluationModelDetail.getDetailName());
			// logger.info("devaluationModelDetail.getDetailWeight()" +
			// devaluationModelDetail.getDetailWeight());
			// logger.info("devaluationModelDetail.getTotalScore()" +
			// devaluationModelDetail.getTotalScore());

		}
		resultMap.put("totalProEvaRaterNum", totalProEvaRaterNum);// 完整加自定义
		// logger.info("totalProEvaRaterNum" + totalProEvaRaterNum);
		return resultMap;
	}
}
