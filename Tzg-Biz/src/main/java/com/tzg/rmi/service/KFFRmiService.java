package com.tzg.rmi.service;

import java.util.ArrayList;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.comments.CommentsShareRequest;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModel.DevaluationModelRequest;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.discuss.DiscussShare;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.evaluation.ProjectEvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.ProjectEvaluationDetailShareResponse;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.message.KFFMessage;
import com.tzg.entitys.kff.mobileversionupdate.Mobileversionupdate;
import com.tzg.entitys.kff.notice.KFFNotice;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.project.SubmitKFFProjectRequest;
import com.tzg.entitys.kff.suggest.SuggestRequest;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.entitys.kff.userInvation.UserInvation;
import com.tzg.entitys.kff.usercard.UserCard;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.entitys.photo.PhotoIview;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * kff 相关远程服务调用
 * 
 * @author Administrator
 *
 */
public interface KFFRmiService {

	/**
	 * app端注册用户
	 * 
	 * @param registerRequest
	 * @return
	 */
	public KFFUser registerRest(RegisterRequest registerRequest) throws RestServiceException;

	/**
	 * 校验用户账号
	 * 
	 * @param string:字段类型
	 *            如 "mobile" userName
	 * @param phoneNumber
	 * @return
	 * @throws RestServiceException
	 */
	public boolean verifyLoginaccount(String string, String phoneNumber) throws RestServiceException;

	/**
	 * 用户登录
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 * @throws RestServiceException
	 */
	public KFFUser login(String loginName, String password) throws RestServiceException;

	/**
	 * 按类型获取最新升级信息
	 * 
	 * @param platformType
	 * @return
	 * @throws RestServiceException
	 */
	public Mobileversionupdate selectLastVersionByType(Integer platformType) throws RestServiceException;

	/**
	 * 提交反馈
	 * 
	 * @param suggestRequest
	 * @throws RestServiceException
	 */
	public void submitSuggest(SuggestRequest suggestRequest) throws RestServiceException;

	/**
	 * 获取最新公告
	 * 
	 * @return
	 * @throws RestServiceException
	 */
	public KFFNotice selectLatestNotice() throws RestServiceException;

	/**
	 * 按电话号码查询用户
	 * 
	 * @param phoneNumber
	 * @return
	 * @throws RestServiceException
	 */
	public KFFUser findUserByPhoneNumber(String phoneNumber) throws RestServiceException;

	/**
	 * 按用户id查询用户
	 * 
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 */
	public KFFUser findUserById(Integer userId) throws RestServiceException;

	/**
	 * 获取用户首页基本信息
	 * 
	 * @param loginUserId
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 */
	public KFFUserHomeResponse findUserHomeByUserId(Integer loginUserId, Integer userId) throws RestServiceException;

	/**
	 * 按区域码获取区域列表
	 * 
	 * @param areacode
	 * @return
	 * @throws RestServiceException
	 */
	public List<Dareas> getAreaListByCode(String areacode) throws RestServiceException;

	/**
	 * 分页获取用户资产列表
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<Tokenrecords> findPageMyTokenRecords(PaginationQuery query) throws RestServiceException;

	/**
	 * 
	 * 分页获取用户收藏记录
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<CollectPostResponse> findPageMyCollectRecords(PaginationQuery query) throws RestServiceException;

	/**
	 * 
	 * 分页获取用户关注列表
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<FollowResponse> findPageMyFollow(PaginationQuery query) throws RestServiceException;

	/**
	 *捐赠
	 * @param commendationRequest
	 * @throws RestServiceException
	 */
	public Map<String,Object> saveCommendation(CommendationRequest commendationRequest) throws RestServiceException;

	/**
	 * 保存评论
	 * 
	 * @param comment
	 * @throws RestServiceException
	 */
	public void saveComment(CommentsRequest comment) throws RestServiceException;
    /**
     * 
     * 保存文章
     * @param articleRequest
     * @throws RestServiceException
     */
	public void saveArticle(ArticleRequest articleRequest) throws RestServiceException;

	/**
	 * 
	 * 保存讨论
	 * @param discussRequest
	 * @throws RestServiceException
	 */
	public void saveDiscuss(DiscussRequest discussRequest) throws RestServiceException;

	/**
	 * 
	 * 获取所有标签列表 
	 * @return
	 * @throws RestServiceException
	 */
	public List<Dtags> findAllTags() throws RestServiceException;

	/**
	 * 
	 * 保存评测
	 * @param evaluationRequest
	 * @throws RestServiceException
	 */
	public void saveEvaluation(EvaluationRequest evaluationRequest) throws RestServiceException;

	/**
	 * 保存评测模型
	 * @param devaluationModelRequest
	 * @throws RestServiceException
	 */
	public void saveEvaluationModel(DevaluationModelRequest devaluationModelRequest) throws RestServiceException;

	/**
	 * 获取消息分页列表
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<KFFMessage> findPageMyMessages(PaginationQuery query) throws RestServiceException;

	/**
	 * 获取消息详情
	 * 
	 * @param userId
	 * @param messageId
	 * @return
	 * @throws RestServiceException
	 */
	public KFFMessage getMessageDetail(Integer userId, Integer messageId) throws RestServiceException;

	/**
	 * 软删除消息
	 * 
	 * @param userId
	 * @param messageId
	 * @throws RestServiceException
	 */
	public void deleteMessage(Integer userId, Integer messageId) throws RestServiceException;

	/**
	 * 提交项目
	 * 
	 * @param project
	 * @return
	 * @throws RestServiceException
	 */
	public KFFProject submitProject(SubmitKFFProjectRequest project) throws RestServiceException;

	/**
	 * 获取项目类型列表
	 * 
	 * @return
	 * @throws RestServiceException
	 */
	public List<DprojectType> findAllProjectTypes() throws RestServiceException;

	/**
	 * 搜索项目
	 * 
	 * @param sortType:1-按关注排序;2-按名称排序
	 * @param userId
	 * @param projectCode
	 * @return
	 * @throws RestServiceException
	 */
	public List<ProjectResponse> findProjectByCode(int sortType, Integer userId, String projectCode)
			throws RestServiceException;

	/**
	 * 更新用户信息
	 * 
	 * @param account
	 * @throws RestServiceException
	 */
	public boolean updateUser(KFFUser account) throws RestServiceException;

	/**
	 * 
	 * 分页获取评测列表
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<EvaluationDetailResponse> findPageEvaluationList(PaginationQuery query) throws RestServiceException;

	/**
	 *  分页获取讨论列表 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<PostResponse> findPageDisscussList(PaginationQuery query) throws RestServiceException;

	/**
	 *分页获取文章列表
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<PostResponse> findPageArticleList(PaginationQuery query) throws RestServiceException;

	/**
	 * 获取项目信息
	 * @param userId
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public ProjectResponse findProjectById(Integer userId, Integer projectId) throws RestServiceException;

	/**
	 * 获取项目下活跃用户列表 --数量2 按 创建帖子数量排序
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public List<KFFUser> findProjectActiveUsers(Integer projectId) throws RestServiceException;

	public int savePraise(Integer userId, Integer postId) throws RestServiceException;

	public int cancelPraise(Integer userId, Integer postId) throws RestServiceException;

	public void saveFollow(Integer userId, Integer followType, Integer followedId) throws RestServiceException;

	public void cancelFollow(Integer userId, Integer followType, Integer followedId) throws RestServiceException;

	public void saveCollect(Integer userId, Integer postId) throws RestServiceException;

	public void cancelCollect(Integer userId, Integer postId) throws RestServiceException;

	public PageResult<PostResponse> findPageRecommendList(Integer loginUserId, PaginationQuery query)
			throws RestServiceException;

	public PageResult<PostResponse> findPageFollowList(Integer loginUserId, PaginationQuery query)
			throws RestServiceException;

	public List<ProjectResponse> findPageProjectRankList(Integer loginUserId, PaginationQuery query)
			throws RestServiceException;

	/**
     *根据帖子id获取文章详情
	 * @param userId
	 * @param postId
	 * @return
	 * @throws RestServiceException
	 */
	public ArticleDetailResponse findArticleDetail(Integer userId, Integer postId)throws RestServiceException;

	/**
	 * 
	 * 根据帖子id获取评测详情
	 * @param userId
	 * @param postId
	 * @return
	 * @throws RestServiceException
	 */
	public EvaluationDetailResponse findEvaluationDetail(Integer userId, Integer postId) throws RestServiceException;

	public List<Comments> findPageHotCommentsList(Integer userId,Integer postId, PaginationQuery query) throws RestServiceException;

	public PageResult<Comments> findPageNewestComments(Integer userId,Integer postId, PaginationQuery query) throws RestServiceException;

	public List<Comments> findAllChildCommentsList(Integer userId, Integer commentsId, PaginationQuery query) throws RestServiceException;

	public DiscussDetailResponse findDiscussDetail(Integer userId, Integer postId) throws RestServiceException;

	public PageResult<Comments> findPageDiscussCommentsList(Integer userId, PaginationQuery query) throws RestServiceException;

	/**
	 * 
	 *查询用户发布的最新的一条讨论
	 * @param loginUserId
	 * @return
	 * @throws RestServiceException
	 */
	public PostResponse findMyLatestDiscuss(Integer loginUserId) throws RestServiceException;

	/**
	 * 
	 * 对 用户某个评论进行点赞
	 * @param userId
	 * @param commentsId
	 * @throws RestServiceException
	 */
	public int saveCommentsPraise(Integer userId, Integer commentsId) throws RestServiceException;

	/**
	 * 
	 * 取消对某个评论的点赞
	 * @param userId
	 * @param commentsId
	 * @throws RestServiceException
	 */
	public int cancelCommentsPraise(Integer userId, Integer commentsId) throws RestServiceException;

	/**
	 * 
	 * 热门讨论2条
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public List<PostResponse> findHotDiscussList(Integer projectId) throws RestServiceException;

	/**
	 * 
	 * 单个项目的专业评测统计信息
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public List<DevaluationModelDetail> findProjectEvaStat(Integer projectId)throws RestServiceException;

	/**
	 * 
	 * 获取生效中的系统评测模型的详情列表
	 * @return
	 * @throws RestServiceException
	 */
	public List<DevaluationModelDetail> findSysEvaModelDetailList()throws RestServiceException;

	/**
	 *更新用户信息  
	 * @param account
	 * @throws RestServiceException
	 */
	public KFFUser updateUserInfo(KFFUser account)throws RestServiceException;

/**
	 * 根据用户的ID 查询用户的身份审核状态 返回审核状态 (1:审核成功 2: 审核中 3 审核不通过 4 未进行身份审核)',
	 * 
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 */
	public Integer selectStatusByUserID(Integer userId) throws RestServiceException;

	/**
	 * 保存用户身份信息
	 * 
	 * @param userCard
	 * @throws RestServiceException
	 */
	public void saveUserIdCard(UserCard userCard) throws RestServiceException;

	/**
	 * 根据用户的ID 查询申请表,判断用户是否进行申请认证的相关操作
	 * 
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 */
	public List<Authentication> selectAuthenticatiobByUserId(Integer userId) throws RestServiceException;

	/**
	 * 根据用户ID插入审核表中
	 * 
	 * @param authentication
	 * @return
	 */
	public void saveAuthenticationByUseId(Integer userId);

	/**
	 * 从后台获取所有项目名称
	 * 
	 * @return
	 */
	public List<KFFProject> findProjectName();

	/**
	 * 根据用户传来的参数 用户 真实姓名 身份证号 ,图片的URL 进行判断
	 * 
	 * @param userRealName
	 * @param userCardNum
	 * @param photoIviews
	 * @return
	 */
	public void selectUserIdStstus(String userRealName, String userCardNum, String photoIviews, Integer userId);

	/**
	 * 将前台传入的参数转化成后台需要的数据
	 * 
	 * @param arrayLists
	 * @return
	 */
	public String uploadIeviw(String str);

	/**
	 * 将在身份审核表中添加相关用户资料
	 * 
	 * @param userId
	 */
	public void setUserCardAuthentication(Integer userId, String phone);

	/**
	 * 在注册时将user的相关信息传到usercard表中
	 * 
	 * @param userCard
	 */
	public void saveUserCardOnRegister(UserCard userCard);

	/**
	 * 跟新用户usercard表
	 * 
	 * @param userCard
	 */
	public void updataUserIdCard(UserCard userCard);

	/**
	 * 根据用户身份号查询身份证号码是否重复提交
	 * 
	 * @param userCardNum
	 * @return
	 */
	public Integer selectUserCardNum(String userCardNum);

	/**
	 * 根据用户ID查询用户的身份证审核状态
	 * 
	 * @param userId
	 * @return
	 */
	public Integer selectUserCardStatusByUserId(Integer userId);

	/**
	 * 根据用户的ID 查询认证的审核状态
	 * 
	 * @param userId
	 * @return
	 */
	public Integer selectAuthenticationStatusByUserId(Integer userId);

	/**
	 * 向认证表中添加认证信息
	 * 
	 * @param authentication
	 */
	public void updataAuthentication(Authentication authentication);

	/**
	 * 返回标签名称
	 * 
	 * @return
	 */
	public List<Dtags> findAllTagsName();

	/**
	 * 根据项目的code 和ChineseName查询项目的ID
	 * 
	 * @param kffProject
	 * @return
	 */
	public KFFProject findProjectIdByCodeAndChineseName(KFFProject kffProject);

	/**
	 * 验证码注册 根据邀请人用户的ID 进行注册
	 * 
	 * @param phoneNumber
	 * @param invaUserId
	 * @return
	 */
	public KFFUser saveUserByphoneNotPass(String phoneNumber, Integer invaUserId);

	/**
	 * 通过别人分享链接打开文章详情页
	 * 
	 * @param postId
	 * @return
	 */
	public ArticleDetailResponse findArticleDetailForShare(Integer acticleId);

	/**
	 * 通过别人分享链接打开评测详情页
	 * 
	 * @param postId
	 * @return
	 */
	public ProjectEvaluationDetailShareResponse findEvaluationDetailForShare(Integer postId);

	/**
	 * 通过别人 分享 链接 打开评测部分详情页
	 * 
	 * @param postId
	 * @return
	 */
	public ProjectEvaluationDetailShareResponse findEvaluationDetailPartForShare(Integer postId);

	/**
	 * 在tbuserInvation 表中插入对象
	 * 
	 * @param userId
	 * @param userIdTo2code
	 * @return
	 */
	public void saveUserInvation(Integer userId, String userIdTo2code);

	/**
	 * 根据postid 查询comment表查出相关内容
	 * 
	 * @param postid
	 * @return
	 */
	public CommentsShareRequest findCommentMessage(Integer postid);

	/**
	 * 从数据库查询authentication 的状态
	 * 
	 * @param userId
	 * @return
	 */
	public List<Authentication> selectAuthenticationByUserId(Integer userId);

	/**
	 * 根据projectID 查询Evaluation表
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Evaluation> findEvaliation(Integer projectId);

	/**
	 * 根据传入的URL list集合 转化成指定的json字符串存放在数据库中
	 * 
	 * @param photoIviewses
	 * @return
	 */
	public String uploadIeviwList(List<String> photoIviewses);

	/**
	 * 根据项目名称查询project
	 * 
	 * @param projectName
	 * @return
	 */
	public KFFProject selectProjectByprojectName(String projectName);

	/**
	 * 查询有效的评测模板
	 * 
	 * @return
	 */
	public List<DevaluationModel> findEvaliationModel();

	/**
	 * 判断用户是否对此项目进行了评测
	 * 
	 * @param evaluationDB
	 * @return
	 */
	public Evaluation selectEvaluationByUserId(Evaluation evaluationDB);

	/**
	 * 讨论的详情页
	 * 
	 * @param userId
	 * @return
	 */
	public DiscussShare findDiscussDetailWAP(Integer userId);

	/**
	 * 根据postID 查询讨论表
	 * 
	 * @param postId
	 * @return
	 */
	public Discuss findDisscussBypostId(Integer postId);

	/**
	 * 根据讨论获取最热评论
	 * 
	 * @param userId
	 * @param postId
	 * @param query
	 * @return
	 */
	public List<Comments> findPageHotCommentsListDis(Integer userId, Integer postId, PaginationQuery query);
	/**
	 * 根据讨论获取最新评论
	 * 
	 * @param userId
	 * @param postId
	 * @param query
	 * @return
	 */
	public List<Comments> findPageNewestCommentsDis(Integer userId, Integer postId, PaginationQuery query);

}
