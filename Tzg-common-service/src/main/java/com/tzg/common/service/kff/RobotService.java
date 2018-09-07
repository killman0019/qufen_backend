package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.HttpUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.coinproperty.CoinPropertyMapper;
import com.tzg.entitys.kff.commendation.Commendation;
import com.tzg.entitys.kff.commendation.CommendationMapper;
import com.tzg.entitys.kff.commendation.CommendationRequest;
import com.tzg.entitys.kff.commentlibrary.CommentLibrary;
import com.tzg.entitys.kff.commentlibrary.CommentLibraryMapper;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsMapper;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.follow.FollowMapper;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostMapper;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.praise.PraiseMapper;
import com.tzg.entitys.kff.robot.Robot;
import com.tzg.entitys.kff.robot.RobotMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;
import com.tzg.entitys.kff.userqfindex.Userqfindex;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rmi.service.KFFRmiService;

@Service
@Transactional(rollbackFor = Exception.class)
public class RobotService {
	@Autowired
	private RobotMapper robotMapper;
	@Autowired
	private KFFUserMapper usermapper;

	@Autowired
	private SystemParamService systemParamService;

	@Autowired
	private PostMapper postMapper;
	@Autowired
	private RedisService redisService;

	@Autowired
	private FollowMapper followMapper;

	@Autowired
	private PraiseMapper praiseMapper;

	@Autowired
	private CommentLibraryMapper commentLibraryMapper;

	@Autowired
	private CommentsMapper commentsMapper;

	@Autowired
	private CoinPropertyMapper coinPropertyMapper;

	@Autowired
	private CommendationMapper commendationMapper;

	@Autowired
	private KFFRmiService KFFRmiService;

	@Value("#{paramConfig['DEV_ENVIRONMENT']}")
	private static String devEnvironment;

	private static final Log logger = LogFactory.getLog(RobotService.class);

	/**
	 * 
	* @Title: putRedis 
	* @Description: TODO <零点定时Redis存放23小时>
	* @author zhangdd <方法创建作者>
	* @create 下午7:35:19
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午7:35:19
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void putRedis() {

		try {
			// 关注//每天取一次
			SystemParam sysBeginf = systemParamService.findByCode(sysGlobals.RBT_FOLLOW_NUM_BEGIN);
			Integer fBegin = Integer.valueOf(sysBeginf.getVcParamValue());
			SystemParam sysEndf = systemParamService.findByCode(sysGlobals.RBT_FOLLOW_NUM_END);
			Integer fEnd = Integer.valueOf(sysEndf.getVcParamValue());
			Integer followNum = RandomUtil.randomNumber(fBegin, fEnd);
			redisService.del("followNumRBT");
			redisService.put("followNumRBT", followNum + "", 60 * 60 * 24);

			// 点赞//每天选一次
			SystemParam sysBeginp = systemParamService.findByCode(sysGlobals.RBT_PRAISE_NUM_BEGIN);
			Integer pBegin = Integer.valueOf(sysBeginp.getVcParamValue());
			SystemParam sysEndp = systemParamService.findByCode(sysGlobals.RBT_PRAISE_NUM_END);
			Integer pEnd = Integer.valueOf(sysEndp.getVcParamValue());
			Integer PraiseNum = RandomUtil.randomNumber(pBegin, pEnd);
			redisService.del("PraiseNumRBT");
			redisService.put("PraiseNumRBT", PraiseNum + "", 60 * 60 * 24);

			/*// 打赏
			SystemParam sysBeginCd = systemParamService.findByCode(sysGlobals.RBT_PRAISE_NUM_BEGIN);
			Integer CdBegin = Integer.valueOf(sysBeginCd.getVcParamValue());
			SystemParam sysEndCd = systemParamService.findByCode(sysGlobals.RBT_PRAISE_NUM_END);
			Integer CdEnd = Integer.valueOf(sysEndCd.getVcParamValue());
			Integer commdationNum = RandomUtil.randomNumber(CdBegin, CdEnd);
			redisService.del("commendationNumRBT");
			redisService.put("commendationNumRBT", commdationNum + "", 60 * 60 * 24);
			*/
			// 评论//每天取一次
			SystemParam sysBeginC = systemParamService.findByCode(sysGlobals.RBT_PRAISE_NUM_BEGIN);
			Integer CBegin = Integer.valueOf(sysBeginC.getVcParamValue());
			SystemParam sysEndC = systemParamService.findByCode(sysGlobals.RBT_PRAISE_NUM_END);
			Integer CEnd = Integer.valueOf(sysEndC.getVcParamValue());
			Integer commentNum = RandomUtil.randomNumber(CBegin, CEnd);
			redisService.del("commentNumRBT");
			redisService.put("commentNumRBT", commentNum + "", 60 * 60 * 24);

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: findOneConnentLibrary 
	* @Description: TODO <根据类型获得评论库中的评论>
	* @author zhangdd <方法创建作者>
	* @create 上午10:41:33
	* @param @param type
	* @param @return <参数说明>
	* @return CommentLibrary 
	* @throws 
	* @update 上午10:41:33
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public CommentLibrary findOneConnentLibrary(Integer type) {
		// 查询评论库的总量

		Map<String, Object> clMap = new HashMap<String, Object>();
		clMap.put("status", 1);
		Integer clCount = commentLibraryMapper.findAllPageCount(clMap);
		Random random = new Random();
		CommentLibrary commentLibrary = null;
		if (clCount == 0) {
			return commentLibrary;
		}
		int i = 0;
		while (true) {
			i++;
			int id = random.nextInt(clCount + 1);
			if (commentLibrary == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("type", type);
				map.put("id", id);
				map.put("status", 1);
				List<CommentLibrary> commentLibraryList = commentLibraryMapper.findByMap(map);
				if (CollectionUtils.isNotEmpty(commentLibraryList)) {
					commentLibrary = commentLibraryList.get(0);
					if (null != commentLibrary) {
						if (commentLibrary.getType() == type && commentLibrary.getStatus() == 1) {
							break;
						}
					}
				}
				if (CollectionUtils.isEmpty(commentLibraryList)) {
					break;
				}
				if (i == 100) {
					break;
				}
			}
		}
		return commentLibrary;
	}

	/**
	 * 
	* @Title: findOneRobot 
	* @Description: TODO <随机查找一个机器人>
	* @author zhangdd <方法创建作者>
	* @create 下午2:44:37
	* @param @return <参数说明>
	* @return KFFUser 
	* @throws 
	* @update 下午2:44:37
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public KFFUser findOneRobot() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", 1);
		Integer count = robotMapper.findPageCount(map);
		int i = 0;
		Random random = new Random();
		Robot robot = null;
		KFFUser user = null;
		if (count == 0) {
			return user;
		}
		while (true) {
			i++;
			int id = random.nextInt(count + 1);
			if (user == null) {
				robot = robotMapper.findById(id);

			}
			if (robot != null) {
				user = usermapper.findById(robot.getUserId());
				if (null != user && user.getStatus() == 1) {
					break;
				}
			}

			if (i == 100) {
				break;
			}
		}

		return user;
	}

	/**
	 * 
	* @Title: robotCommendationTask 
	* @Description: TODO <机器人打赏>
	* @author zhangdd <方法创建作者>
	* @create 下午4:46:30
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午4:46:30
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotTask(final int k) {
		int i = 0;
		ExecutorService newFixedThreadPoolrobot = null;

		try {
			newFixedThreadPoolrobot = Executors.newFixedThreadPool(10);
			while (true) {
				i = i + 1;
				PageResult<Post> postPage = getPostList(i);// j 1 i 0 i 1 j 11

				if (null != postPage && CollectionUtils.isEmpty(postPage.getRows())) {
					break;
				}
				if (null != postPage && CollectionUtils.isNotEmpty(postPage.getRows())) {

					for (Post post : postPage.getRows()) {
						Integer r = RandomUtil.randomNumber(1, 3);
						if (r == 1) {
							continue;// 1/3的可能性不走接口
						}

						final Post postf = post;
						newFixedThreadPoolrobot.execute(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								switch (k) {
								case 5:
								//	robotComment(postf);// 一级评论
									break;
								case 4:
									robotFollow(postf);// 关注
									break;
								case 3:
									robotCommendation(postf);// 打赏
									break;
								case 2:
									//robotSecondComment(postf);// 评论
									break;
								case 1:
									robotPraise(postf);// 点赞
									break;

								default:
									break;
								}

							}
						});
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!newFixedThreadPoolrobot.isShutdown()) {
				newFixedThreadPoolrobot.shutdown();
			}

		}

	}

	/**
	 * 
	* @Title: robotPraise 
	* @Description: TODO <点赞>
	* @author zhangdd <方法创建作者>
	* @create 下午6:22:18
	* @param @param postf <参数说明>
	* @return void 
	* @throws 
	* @update 下午6:22:18
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotPraise(Post postf) {

		// TODO 点赞
		try {
			logger.warn("robotPraise begin :" + "postId" + postf.getPostId() + "title" + postf.getPostTitle());
			if (null != postf) {
				KFFUser robotUser = findOneRobot();
				String token = AccountTokenUtil.getAccountToken(robotUser.getUserId());
				String praiseNum = redisService.get("PraiseNumRBT");
				if (StringUtils.isEmpty(praiseNum)) {
					putRedis();
					praiseNum = redisService.get("followNumRBT");
				}
				// Integer createUserId = postf.getCreateUserId();
				Map<String, Object> praiseMap = new HashMap<String, Object>();
				praiseMap.put("praiseType", 1 + "");
				praiseMap.put("postId", 1 + postf.getPostId() + "");

				// praiseMap.put("bepraiseUserId", createUserId + "");
				praiseMap.put("status", "1");
				List<Praise> praiseList = praiseMapper.findByMap(praiseMap);
				if (praiseList.size() <= Integer.valueOf(praiseNum)) {
					SystemParam sysUrl = systemParamService.findByCode(sysGlobals.WEB_URL);// https://m.qufen.top/wap
					String url = sysUrl.getVcParamValue().trim();
					Integer postId = postf.getPostId();
					String regiUrlLocal = null;
					String para = "token=" + token + "&postId=" + postId;

					if (StringUtils.isNotBlank(devEnvironment) && devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {
						regiUrlLocal = url + "/kff/praise/savePostPraise?";// 线上url
					} else {
						regiUrlLocal = "http://192.168.10.153:803/kff/praise/savePostPraise?";// 本地url
					}
					String str = regiUrlLocal + para;
					String doGet = HttpUtil.doGet(str);
					if (doGet != null) {
						JSONObject parseObject = JSON.parseObject(doGet);

					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: robotComment 
	* @Description: TODO <进行一级评论>
	* @author zhangdd <方法创建作者>
	* @create 下午3:22:57
	* @param @param postf <参数说明>
	* @return void 
	* @throws 
	* @update 下午3:22:57
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotComment(Post postf) {
		try {
			logger.warn("robotComment begin :" + "postId" + postf.getPostId() + "title" + postf.getPostTitle());
			if (null != postf) {
				// TODO 评论
				Map<String, Object> commMap = new HashMap<String, Object>();
				commMap.put("postId", postf.getPostId());
				commMap.put("isNullParentCommentsId", "true");
				commMap.put("status", 1);
				String commentNum = redisService.get("commentNumRBT");
				if (StringUtils.isEmpty(commentNum)) {
					putRedis();
					commentNum = redisService.get("commentNumRBT");
				}
				List<Comments> comm = commentsMapper.findByMap(commMap);

				if (comm.size() <= Integer.valueOf(commentNum)) {

					CommentLibrary commentLib = findOneConnentLibrary(1);
					if (null != commentLib) {
						// 进行调用接口
						KFFUser robotUser = findOneRobot();
						if (robotUser == null) {
							return;
						}
						String token = AccountTokenUtil.getAccountToken(robotUser.getUserId());
						Integer postId = postf.getPostId();
						String regiUrlLocal = null;
						CommentsRequest commentsRequest = new CommentsRequest();
						commentsRequest.setCommentContent(commentLib.getContent());
						commentsRequest.setPostId(postId);
						SystemParam sysUrl = systemParamService.findByCode(sysGlobals.WEB_URL);// https://m.qufen.top/wap
						String url = sysUrl.getVcParamValue().trim();
						String para = "token=" + token + "&commentContent=" + commentLib.getContent() + "&postId=" + postId;
						if (StringUtils.isNotBlank(devEnvironment) && devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {
							regiUrlLocal = url + "/kff/comments/saveComment?";// 线上url
						} else {
							regiUrlLocal = "http://192.168.10.153:803/kff/comments/saveComment?";// 本地url
						}
						String str = regiUrlLocal + para;
						String doGet = HttpUtil.doGet(str);
						if (doGet != null) {
							JSONObject parseObject = JSON.parseObject(doGet);

						}

					}
				}

			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: robotSecondComment 
	* @Description: TODO <二级评论>
	* @author zhangdd <方法创建作者>
	* @create 下午6:22:30
	* @param @param postf <参数说明>
	* @return void 
	* @throws 
	* @update 下午6:22:30
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotSecondComment(Post postf) {
		try {
			logger.warn("robotSecondComment begin :" + "postId" + postf.getPostId() + "title" + postf.getPostTitle());
			if (null != postf) {
				// TODO 评论
				// 查找所有的一级评论
				Map<String, Object> commMap = new HashMap<String, Object>();
				commMap.put("postId", postf.getPostId());
				commMap.put("status", 1);
				commMap.put("contextlenth", 60);
				commMap.put("isNullParentCommentsId", "true");
				List<Comments> comm = commentsMapper.findByMap(commMap);
				String commentNum = redisService.get("commentNumRBT");
				if (StringUtils.isEmpty(commentNum)) {
					putRedis();
					commentNum = redisService.get("commentNumRBT");
				}
				if (CollectionUtils.isNotEmpty(comm)) {
					for (Comments comments : comm) {
						if (null != comments) {
							Integer commentUserId = comments.getCommentUserId();
							Robot robot = robotMapper.findByUserId(commentUserId);
							Integer postId = postf.getPostId();
							if (null == robot) {// 表示这个是真人
								// 判断这个二级评论是否达到规定值
								Map<String, Object> pareMap = new HashMap<String, Object>();
								pareMap.put("parentCommentsId", comments.getCommentsId());
								pareMap.put("postId", postId);
								List<Comments> commP = commentsMapper.findByMap(pareMap);
								if (CollectionUtils.isNotEmpty(commP)) {
									continue;
								}
								// 对评论进行二级评论
								CommentLibrary commentLib = findOneConnentLibrary(2);
								if (null != commentLib) {
									// 进行调用接口
									KFFUser robotUser = findOneRobot();
									if (robotUser == null) {
										return;
									}
									String token = AccountTokenUtil.getAccountToken(robotUser.getUserId());
									String regiUrlLocal = null;
									SystemParam sysUrl = systemParamService.findByCode(sysGlobals.WEB_URL);// https://m.qufen.top/wap
									String url = sysUrl.getVcParamValue().trim();
									CommentsRequest commentsRequest = new CommentsRequest();
									commentsRequest.setCommentContent(commentLib.getContent());
									commentsRequest.setPostId(postId);
									commentsRequest.setParentCommentsId(comments.getCommentsId());
									String para = "token=" + token + "&commentContent=" + commentLib.getContent() + "&postId=" + postId + "&parentCommentsId="
											+ comments.getCommentsId() + "&becommentedId=" + comments.getCommentsId();
									if (StringUtils.isNotBlank(devEnvironment) && devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {
										regiUrlLocal = url + "/kff/comments/saveComment?";// 线上url
									} else {
										regiUrlLocal = "http://192.168.10.153:803/kff/comments/saveComment?";// 本地url
									}
									String str = regiUrlLocal + para;
									String doGet = HttpUtil.doGet(str);
									if (doGet != null) {
										JSONObject parseObject = JSON.parseObject(doGet);

									}
								}
							}
						}
					}
				}

			}
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: robotFollow 
	* @Description: TODO <关注>
	* @author zhangdd <方法创建作者>
	* @create 下午6:22:39
	* @param @param postf <参数说明>
	* @return void 
	 * @throws Exception 
	* @throws 
	* @update 下午6:22:39
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotFollow(Post postf) {
		// TODO 关注
		try {
			logger.warn("robotFollow begin :" + "postId" + postf.getPostId() + "title" + postf.getPostTitle());
			if (postf != null) {
				KFFUser robotUser = findOneRobot();
				if (robotUser == null) {
					return;
				}
				String token = AccountTokenUtil.getAccountToken(robotUser.getUserId());

				String followNum = redisService.get("followNumRBT");
				if (StringUtils.isEmpty(followNum)) {
					putRedis();
					followNum = redisService.get("followNumRBT");
				}
				Integer createUserId = postf.getCreateUserId();
				Map<String, String> followMap = new HashMap<String, String>();
				followMap.put("followType", 3 + "");
				followMap.put("followedUserId", createUserId + "");
				followMap.put("status", 1 + "");
				String createTimeStr = DateUtil.getDate(postf.getCreateTime());
				followMap.put("createTimeBegin", createTimeStr);
				List<Follow> followList = followMapper.findByMap(followMap);

				if (followList.size() < Integer.valueOf(followNum)) {
					// 调用接口,进行对创建人进行关注
					Integer followType = 3;
					Integer followedId = createUserId;
					// 判读这个机器人有没有关注过这个用户,关注过就跳过
					Map<String, String> followRobotMap = new HashMap<String, String>();
					followRobotMap.put("followType", 3 + "");
					followRobotMap.put("followedUserId", createUserId + "");
					followRobotMap.put("status", 1 + "");
					followRobotMap.put("followUserId", robotUser.getUserId() + "");
					List<Follow> followRobotList = followMapper.findByMap(followRobotMap);
					if (CollectionUtils.isNotEmpty(followRobotList)) {// 说明已经关注过这个用户
						return;
					}
					String para = "followType=" + followType + "&followedId=" + followedId + "&token=" + token;
					String regiUrlLocal = null;
					SystemParam sysUrl = systemParamService.findByCode(sysGlobals.WEB_URL);// https://m.qufen.top/wap
					String url = sysUrl.getVcParamValue().trim();
					if (StringUtils.isNotBlank(devEnvironment) && devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {
						regiUrlLocal = url + "/kff/follow/saveFollow?";// 线上url
					} else {
						regiUrlLocal = "http://192.168.10.153:803/kff/follow/saveFollow?";// 本地url
					}
					String str = regiUrlLocal + para;
					String doGet = HttpUtil.doGet(str);
					if (doGet != null) {
						JSONObject parseObject = JSON.parseObject(doGet);

					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: robotCommendation 
	* @Description: TODO <打赏>
	* @author zhangdd <方法创建作者>
	* @create 下午6:22:54
	* @param @param postf <参数说明>
	* @return void 
	* @throws 
	* @update 下午6:22:54
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotCommendation(Post postf) {
		// TODO 打赏

		try {
			logger.warn("robotCommendation begin :" + "postId" + postf.getPostId() + "title" + postf.getPostTitle());
			if (postf != null) {
				KFFUser robotUser = findOneRobot();
				if (robotUser != null) {// 用户的账号余额为0时
					Integer RBTUserId = robotUser.getUserId();
					CoinProperty coinProperty = coinPropertyMapper.findByUserId(RBTUserId);
					SystemParam sysBegin = systemParamService.findByCode(sysGlobals.ROBOT_COMMENDATION_BEGIN_COUNT);
					Integer beg = Integer.valueOf(sysBegin.getVcParamValue());
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("receiveUserId", postf.getCreateUserId());
					map.put("postId", postf.getPostId());
					map.put("status", 1);
					List<Commendation> commendationList = commendationMapper.findByMap(map);
					if (commendationList.size() >= beg) {
						return;
					}
					String token = AccountTokenUtil.getAccountToken(robotUser.getUserId());
					KFFUser user = usermapper.findById(robotUser.getUserId());
					SystemParam sysBeginCd = systemParamService.findByCode(sysGlobals.RBT_COMMENDATION_NUM_BEGIN);
					Integer CdBegin = Integer.valueOf(sysBeginCd.getVcParamValue());
					SystemParam sysEndCd = systemParamService.findByCode(sysGlobals.RBT_COMMENDATION_NUM_END);
					Integer CdEnd = Integer.valueOf(sysEndCd.getVcParamValue());
					Integer cmNum = RandomUtil.randomNumber(CdBegin / 5, CdEnd / 5) * 5;
					if (coinProperty.getCoinLock() < cmNum) {
						return;
					}
					CommendationRequest commendationRequest = new CommendationRequest();
					commendationRequest.setAmount(new BigDecimal(cmNum));
					commendationRequest.setPostId(postf.getPostId());
					commendationRequest.setPostType(postf.getPostType());
					commendationRequest.setProjectId(postf.getProjectId());
					commendationRequest.setReceiveUserId(postf.getCreateUserId());
					commendationRequest.setSendUserIcon(user.getIcon());
					commendationRequest.setSendUserId(user.getUserId());

					String para = "amount=" + new BigDecimal(cmNum) + "&token=" + token + "&postId=" + postf.getPostId() + "&postType=" + postf.getPostType()
							+ "&projectId=" + postf.getProjectId() + "&receiveUserId=" + postf.getCreateUserId() + "&sendUserIcon=" + user.getIcon()
							+ "&sendUserId=" + user.getUserId();

					String regiUrlLocal = null;
					SystemParam sysUrl = systemParamService.findByCode(sysGlobals.WEB_URL);// https://m.qufen.top/wap
					String url = sysUrl.getVcParamValue().trim();
					if (StringUtils.isNotBlank(devEnvironment) && devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {
						regiUrlLocal = url + "/kff/token/commendation?";// 线上url
					} else {
						regiUrlLocal = "http://192.168.10.153:803/kff/token/commendation?";// 本地url
					}
					String str = regiUrlLocal + para;
					String doGet = HttpUtil.doGet(str);

					if (doGet != null) {
						JSONObject parseObject = JSON.parseObject(doGet);

					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	* @Title: getPostList 
	* @Description: TODO <获得近几天的内容>
	* @author zhangdd <方法创建作者>
	* @create 下午4:48:07
	* @param @return <参数说明>
	* @return PageResult<Post> 
	* @throws 
	* @update 下午4:48:07
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	private PageResult<Post> getPostList(int i) {
		PageResult<Post> result = null;
		SystemParam param = systemParamService.findByCode(sysGlobals.ROBOT_GET_POST_CREATE_TIME);
		int days = Integer.valueOf(param.getVcParamValue());
		if (days > 0) {
			String postCreateBegin = DateUtil.getSpecifiedDayBeforeOrAfter(days);
			PaginationQuery query = new PaginationQuery();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("createTimeBegin", postCreateBegin);
			map.put("sql_keyword_orderBy", "createTime");
			map.put("sql_keyword_sort", "DESC");
			query.setQueryData(map);
			query.setPageIndex(i);
			query.setRowsPerPage(10);
			try {
				Integer count = postMapper.findPageCount(query.getQueryData());
				if (null != count && count.intValue() > 0) {
					int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
					query.addQueryData("startRecord", Integer.toString(startRecord));
					query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
					List<Post> list = postMapper.findPage(query.getQueryData());
					result = new PageResult<Post>(list, count, query);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;

		}
		return null;
	}
}
