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
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostFile;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.projectevastat.Projectevastat;
import com.tzg.entitys.kff.projectevastat.ProjectevastatByGrade;
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
		List<Evaluation> evas = kffEvaluationService.findSimpleEvaByProjectId(projectId);
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



	@Override
	public PageResult<Comments> findPagecommentCommentsList(Integer userId,
			PaginationQuery query) throws RestServiceException {
		KFFUser user = null;
		if (userId != null && userId != 0) {
			user = kffUserService.findById(userId);
		}
		PageResult<Comments> comments = kffCommentsService.findPage(query);
		if (comments != null && CollectionUtils.isNotEmpty(comments.getRows())) {
			PaginationQuery childQuery = new PaginationQuery();
			childQuery.setPageIndex(1);
			childQuery.setRowsPerPage(2);
			for (Comments comment : comments.getRows()) {
				childQuery.addQueryData("parentCommentsId", comment.getCommentsId() + "");
				PageResult<Comments> childComments = kffCommentsService.findPage(childQuery);
				if (childComments != null && CollectionUtils.isNotEmpty(childComments.getRows())) {
					comment.setChildCommentsList(childComments.getRows());
					comment.setChildCommentsNum(childComments.getRowCount());
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
		return comments;

	}



	@Override
	public PageResult<PostResponse> findMyPageFollowList(Integer userId, PaginationQuery query) {
		PageResult<PostResponse> result = new PageResult<>();
		if(userId == null || userId ==0){
			throw new RestServiceException("用户未登录,请重新登录");
		}
		query.addQueryData("userId", userId +"");
		//1 关注的用户 点赞帖子  2关注的用户 发表帖子  3关注的用户 关注项目 4关注的项目下发表的帖子
		PageResult<Post> posts = kffPostService.findMyPageFollowList(query);
		if(posts != null && CollectionUtils.isNotEmpty(posts.getRows())){
			List<PostResponse> rows = new ArrayList<>();
			result.setCurPageNum(posts.getCurPageNum());
			result.setPageSize(posts.getPageSize());
			result.setRowCount(posts.getRowCount());
			result.setRowsPerPage(posts.getRowsPerPage());
			KFFUser user = null;
			Post realPost = null;
			for(Post post:posts.getRows()){
				PostResponse pr = new PostResponse();	
				
				//评测去除简单评测
				if(Objects.equal(1, post.getPostType())){
					user = kffUserService.findById(post.getCreateUserId());
					pr.setActionDesc(user ==null?"匿名用户":user.getUserName() +"点赞了帖子");
					realPost = kffPostService.findById(post.getPostId());
					if(realPost != null && Objects.equal(1,realPost.getPostType())){
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						if(eva != null && Objects.equal(1,eva.getModelType())){
							continue;
						}else{
							BeanUtils.copyProperties(realPost, pr);
						}
					}
					
					//讨论返回标签
					if(realPost != null && Objects.equal(2,realPost.getPostType())){
						Discuss dis = kffDiscussService.findByPostId(realPost.getPostId());
						if(dis != null ){
							pr.setTagInfos(dis.getTagInfos());
						}
					}
					
					//关注状态
					pr.setFollowStatus(1);
				}else if(Objects.equal(2, post.getPostType())){
					user = kffUserService.findById(post.getCreateUserId());
					pr.setActionDesc(user ==null?"匿名用户":user.getUserName() +"发表了帖子");
					realPost = kffPostService.findById(post.getPostId());
					if(realPost != null && Objects.equal(1,realPost.getPostType())){
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						if(eva != null && Objects.equal(1,eva.getModelType())){
							continue;
						}else{
							BeanUtils.copyProperties(realPost, pr);
						}
					}
					//讨论返回标签
					if(realPost != null && Objects.equal(2,realPost.getPostType())){
						Discuss dis = kffDiscussService.findByPostId(realPost.getPostId());
						if(dis != null ){
							pr.setTagInfos(dis.getTagInfos());
						}
					}
					//关注状态
					pr.setFollowStatus(1);
				}else if(Objects.equal(3, post.getPostType())){
					user = kffUserService.findById(post.getCreateUserId());
					pr.setActionDesc(user ==null?"匿名用户":user.getUserName() +"关注了项目");
					KFFProject project = kffProjectService.findById(post.getPostId());
					if(project != null){
						BeanUtils.copyProperties(project, pr);
					}
					pr.setCreateUserId(user ==null?null:user.getUserId());
					pr.setCreateUserIcon(user ==null?"/upload/avatars/default.png":user.getIcon());
					pr.setCreateUserName(user ==null?"匿名用户":user.getUserName());
					pr.setCreateUserSignature(user ==null?"":user.getUserSignature());
					//项目 关注状态
					Follow follow = kffFollowService.findByUserIdAndFollowType(userId, 1, post.getPostId());
					if(follow != null && Objects.equal(1, follow.getStatus())){
						pr.setFollowStatus(1);
					}else{
						pr.setFollowStatus(0);
					}
				}else if(Objects.equal(4, post.getPostType())){
					pr.setActionDesc("关注项目下发表了新帖");
					realPost = kffPostService.findById(post.getPostId());
					if(realPost != null && Objects.equal(1,realPost.getPostType())){
						Evaluation eva = kffEvaluationService.findByPostId(realPost.getPostId());
						if(eva != null && Objects.equal(1,eva.getModelType())){
							continue;
						}else{
							BeanUtils.copyProperties(realPost, pr);
						}
					}
					//讨论返回标签
					if(realPost != null && Objects.equal(2,realPost.getPostType())){
						Discuss dis = kffDiscussService.findByPostId(realPost.getPostId());
						if(dis != null ){
							pr.setTagInfos(dis.getTagInfos());
						}
					}
					//关注状态
					pr.setFollowStatus(1);
				}else{
					continue;
				}
				pr.setActionType(post.getPostType());							
				rows.add(pr);
			}
			result.setRows(rows);
		}
		
		return result;
	}



	@Override
	public Map<String,Object> findProjectEvaStat(Integer projectId) throws RestServiceException {
		Map<String,Object> resultMap = new HashMap<>();
		int totalProEvaRaterNum = 0;
		if (projectId == null || projectId == 0) {
			throw new RestServiceException("项目id不能为空"+projectId);
		}

		List<DevaluationModelDetail> result = new ArrayList<>();

		DevaluationModel activeProModel = findSysActiveProModel();
		List<DevaluationModelDetail> details = findSysEvaModelDetailList(activeProModel);

		if (details != null && CollectionUtils.isNotEmpty(details)) {
			Map<String,BigDecimal> detailModelTotalScore = new HashMap<String,BigDecimal>();
			Map<String,Integer> detailModelTotalRater = new HashMap<String,Integer>();			
			List<Evaluation> proEvas = kffEvaluationService.findProEvaByProjectId(projectId);
			if(CollectionUtils.isNotEmpty(proEvas)){
				for(Evaluation eva:proEvas){
					try{
						eva.getTotalScore();
						List<DevaluationModel> models =JSON.parseArray(eva.getProfessionalEvaDetail(), DevaluationModel.class);
					    if(CollectionUtils.isEmpty(models)){
					    	continue;
					    }
					    BigDecimal score = BigDecimal.ZERO;
					    Integer raterNum = 0;
					    for(DevaluationModel model:models){
					    	if(StringUtils.isBlank(model.getModelName())){
					    		continue;
					    	}
					    	score = detailModelTotalScore.get(model.getModelName())==null?BigDecimal.ZERO:detailModelTotalScore.get(model.getModelName());
					    	score = score.add(model.getScore() == null? BigDecimal.ZERO:model.getScore());
					    	detailModelTotalScore.put(model.getModelName(),score);
					    	raterNum = detailModelTotalRater.get(model.getModelName()) == null?0:detailModelTotalRater.get(model.getModelName());
					    	raterNum++;
					    	detailModelTotalRater.put(model.getModelName(), raterNum);

					    }
					    totalProEvaRaterNum ++ ; 
					}catch(Exception e){
						logger.error("专业评测统计解析评测内容出错,evaId:"+eva.getEvaluationId()+" details:"+eva.getProfessionalEvaDetail());
					}
							
				}
				
			}
			
			for(DevaluationModelDetail detail:details){
				
			    BigDecimal totalScore = BigDecimal.ZERO;
				detail.setRaterNum(detailModelTotalRater.get(detail.getDetailName())==null?0:detailModelTotalRater.get(detail.getDetailName()));
				if(Objects.equal(0, detail.getRaterNum())){
					detail.setTotalScore(BigDecimal.ZERO);
				}else{
					totalScore = detailModelTotalScore.get(detail.getDetailName())==null?BigDecimal.ZERO:detailModelTotalScore.get(detail.getDetailName());
					detail.setTotalScore(totalScore.divide(new BigDecimal(detail.getRaterNum()),1,RoundingMode.HALF_DOWN).setScale(1, RoundingMode.HALF_DOWN));
				}
									
			}
		}	
			//end
			
//			PaginationQuery query = new PaginationQuery();
//			query.addQueryData("modelId", "3");
//			query.addQueryData("sortField", "comments_num");
//			query.addQueryData("status", "1");
//			query.setPageIndex(1);
//			query.setRowsPerPage(100);
//			PageResult<Projectevastat> proEvaStat = kffProjectevastatService.findPage(query);
//			Map<String, Object> evaMap = new HashMap<String, Object>();
//			if (proEvaStat != null && CollectionUtils.isNotEmpty(proEvaStat.getRows())) {
//				for (Projectevastat eva : proEvaStat.getRows()) {
//
//				}
//			}
//			for (DevaluationModelDetail detail : details) {
//				float x = 8.1f;
//				if (detail.getId() == 10)
//					x = 9.1f;
//				if (detail.getId() == 13)
//					x = 6.1f;
//				BigDecimal totalScore = BigDecimal.valueOf(x).setScale(1, BigDecimal.ROUND_HALF_DOWN);
//				detail.setTotalScore(totalScore);
//				detail.setRaterNum(Integer.valueOf(RandomUtil.produceNumber(3)));
//
//				result.add(detail);
//			}
//		  }
		 
		resultMap.put("projectEvaStat", details);
		resultMap.put("totalProEvaRaterNum", totalProEvaRaterNum);
		return resultMap;
	}


	private DevaluationModel findSysActiveProModel(){
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
		if(activeProModel == null){
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
		//https://www.tapd.cn/21950911/bugtrace/bugs/view?bug_id=1121950911001000461
				//规则：点赞量超过10 & 排名前2的内容
				if (projectId == null) {
					throw new RestServiceException("项目id不能为空");
				}
				//Date day7 = DateUtil.getSpecifiedDateBeforeOrAfter(7).getTime();
				List<PostResponse> respones = new ArrayList<>();
				PaginationQuery query = new PaginationQuery();
				query.addQueryData("projectId", projectId + "");
				//query.addQueryData("sortField", "comments_num");
				query.addQueryData("sortField", "praise_num");
				query.addQueryData("status", "1");
				query.addQueryData("postType", "1");
				//query.addQueryData("createTimeBegin", DateUtil.getDate(day7, "yyyy-MM-dd"));
				query.setPageIndex(1);
				query.setRowsPerPage(2);
				PageResult<Post> posts = kffPostService.findPage(query);
				if (posts != null && CollectionUtils.isNotEmpty(posts.getRows())) {
					for (Post post : posts.getRows()) {
						if(post.getPraiseNum()<10){
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
							if(Objects.equal(1, eva.getModelType())){
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


}
