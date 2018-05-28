package com.tzg.rmi.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.article.ArticleDetailResponse;
import com.tzg.entitys.kff.article.ArticleRequest;
import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.comments.CommentsShareRequest;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModel.DevaluationModelRequest;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.discuss.Discuss;
import com.tzg.entitys.kff.discuss.DiscussDetailResponse;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.discuss.DiscussShare;
import com.tzg.entitys.kff.dprojectType.DprojectType;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
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
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserHomeResponse;
import com.tzg.entitys.kff.usercard.UserCard;
import com.tzg.entitys.kff.userwallet.KFFUserWallet;
import com.tzg.entitys.loginaccount.RegisterRequest;
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
	 * @param string
	 *            :字段类型 如 "mobile" userName
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
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<Tokenrecords> findPageMyTokenRecords(PaginationQuery query) throws RestServiceException;

	/**
	 * 
	 * 分页获取用户收藏记录
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<CollectPostResponse> findPageMyCollectRecords(PaginationQuery query) throws RestServiceException;

	/**
	 * 
	 * 分页获取用户关注列表
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<FollowResponse> findPageMyFollow(PaginationQuery query) throws RestServiceException;

	/**
	 * 捐赠
	 * 
	 * @param commendationRequest
	 * @throws RestServiceException
	 */
	public Map<String, Object> saveCommendation(CommendationRequest commendationRequest) throws RestServiceException;

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
	 * 
	 * @param articleRequest
	 * @throws RestServiceException
	 */
	public void saveArticle(ArticleRequest articleRequest) throws RestServiceException;

	/**
	 * 
	 * 保存讨论
	 * 
	 * @param discussRequest
	 * @throws RestServiceException
	 */
	public void saveDiscuss(DiscussRequest discussRequest) throws RestServiceException;

	/**
	 * 
	 * 获取所有标签列表
	 * 
	 * @return
	 * @throws RestServiceException
	 */
	public List<Dtags> findAllTags() throws RestServiceException;

	/**
	 * 
	 * 保存评测
	 * 
	 * @param evaluationRequest
	 * @throws RestServiceException
	 */
	public void saveEvaluation(EvaluationRequest evaluationRequest) throws RestServiceException;

	/**
	 * 保存评测模型
	 * 
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
	 * @param sortType
	 *            :1-按关注排序;2-按名称排序
	 * @param userId
	 * @param projectCode
	 * @return
	 * @throws RestServiceException
	 */
	public List<ProjectResponse> findProjectByCode(int sortType, Integer userId, String projectCode) throws RestServiceException;

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
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<EvaluationDetailResponse> findPageEvaluationList(PaginationQuery query) throws RestServiceException;

	/**
	 * 分页获取讨论列表
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<PostResponse> findPageDisscussList(PaginationQuery query) throws RestServiceException;

	/**
	 * 分页获取文章列表
	 * 
	 * @param query
	 * @return
	 * @throws RestServiceException
	 */
	public PageResult<PostResponse> findPageArticleList(PaginationQuery query) throws RestServiceException;

	/**
	 * 获取项目信息
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public ProjectResponse findProjectById(Integer userId, Integer projectId) throws RestServiceException;
	/**
	 * 获取奖励表信息
	 * @param userId
	 * @return
	 * @throws RestServiceException
	public List<Tokenaward> findTokenawardById(Integer userId) throws RestServiceException;
	 */
	/**
	 * 获取资产表信息
	 * @param userId
	 * @return
	 * @throws RestServiceException
	 */
	public List<CoinProperty> findCoinPropertyById(Integer userId,Double coinUnlock) throws RestServiceException;
	/**
	 * 获取资产表信息
	 * @param userId
	 * @param coinUnlockType 
	 * @return
	 * @throws RestServiceException
	 */
	public List<CoinProperty> findCoinPropertyById(Integer userId,Date coinUnlockTime, Integer coinUnlockType) throws RestServiceException;

	/**
	 * 获取项目下活跃用户列表 --数量2 按 创建帖子数量排序
	 * 
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

	public PageResult<PostResponse> findPageRecommendList(Integer loginUserId, PaginationQuery query) throws RestServiceException;

	public PageResult<PostResponse> findPageFollowList(Integer loginUserId, PaginationQuery query) throws RestServiceException;

	public List<ProjectResponse> findPageProjectRankList(Integer loginUserId, PaginationQuery query) throws RestServiceException;

	/**
	 * 根据帖子id获取文章详情
	 * 
	 * @param userId
	 * @param postId
	 * @return
	 * @throws RestServiceException
	 */
	public ArticleDetailResponse findArticleDetail(Integer userId, Integer postId) throws RestServiceException;

	/**
	 * 
	 * 根据帖子id获取评测详情
	 * 
	 * @param userId
	 * @param postId
	 * @return
	 * @throws RestServiceException
	 */
	public EvaluationDetailResponse findEvaluationDetail(Integer userId, Integer postId) throws RestServiceException;

	public List<Comments> findPageHotCommentsList(Integer userId, Integer postId, PaginationQuery query) throws RestServiceException;

	public PageResult<Comments> findPageNewestComments(Integer userId, Integer postId, PaginationQuery query) throws RestServiceException;

	public List<Comments> findAllChildCommentsList(Integer userId, Integer commentsId, PaginationQuery query) throws RestServiceException;

	public DiscussDetailResponse findDiscussDetail(Integer userId, Integer postId) throws RestServiceException;

	public List<Comments> findAllDiscussCommentsList(Integer userId, Integer postId) throws RestServiceException;

	public void registerAward(Integer userId);
	/**
	 * 
	 * 查询用户发布的最新的一条讨论
	 * 
	 * @param loginUserId
	 * @return
	 * @throws RestServiceException
	 */
	public PostResponse findMyLatestDiscuss(Integer loginUserId) throws RestServiceException;

	/**
	 * 
	 * 对 用户某个评论进行点赞
	 * 
	 * @param userId
	 * @param commentsId
	 * @throws RestServiceException
	 */
	public int saveCommentsPraise(Integer userId, Integer commentsId) throws RestServiceException;

	/**
	 * 
	 * 取消对某个评论的点赞
	 * 
	 * @param userId
	 * @param commentsId
	 * @throws RestServiceException
	 */
	public int cancelCommentsPraise(Integer userId, Integer commentsId) throws RestServiceException;

	/**
	 * 
	 * 热门讨论2条
	 * 
	 * @param projectId
	 * @return
	 * @throws RestServiceException
	 */
	public List<PostResponse> findHotDiscussList(Integer projectId) throws RestServiceException;

	public List<CoinProperty> findCoinPropertyByUserId(Integer userId);

	public List<Tokenaward> findAllTokenawardUser(Integer userId);
	/**
	 * 绑定钱包操作
	 * 
	 * @param userId
	 * @param wallet
	 * @return
	 */
	public KFFUserWallet save(Integer userId, String wallet);

	/**
	 * 向认证表中添加认证信息
	 * 
	 * @param authentication
	 */
	public void updataAuthentication(Authentication authentication);

	KFFUserWallet updateWallet(Integer userId, String wallet, String walletLater);

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
	
	public ArticleDetailResponse findArticleDetailForShare(Integer postId);
	
	public KFFProject findProjectIdByCodeAndChineseName(KFFProject kffProject);
	public KFFUser saveUserByphonePass(String phoneNumber, Integer invaUserId, String password);
	
	public List<Dtags> findAllTagsName();
	
	public Integer selectAuthenticationStatusByUserId(Integer userId);
	
	public Integer selectUserCardStatusByUserId(Integer userId);
	
	public Integer selectUserCardNum(String userCardNum);
	
	public void setUserCardAuthentication(Integer userId, String phone);
	
	public String uploadIeviw(String photoIviews);
	
	public void updataUserIdCard(UserCard userCard);
	
	public void selectUserIdStstus(String userRealName, String userCardNum, String photoIviews, Integer userId);
	
	public List<KFFProject> findProjectName();
	
	public void saveAuthenticationByUseId(Integer userId);
	
	public List<Authentication> selectAuthenticatiobByUserId(Integer userId);
	
	public void saveUserIdCard(UserCard userCard);
	
	public Integer selectStatusByUserID(Integer userId);
	
	public KFFUser updateUserInfo(KFFUser account);
	
	public List<DevaluationModelDetail> findSysEvaModelDetailList();
	
	public List<DevaluationModelDetail> findProjectEvaStat(Integer projectId);
	
	public PageResult<Comments> findPageDiscussCommentsList(Integer userId, PaginationQuery query);
}
