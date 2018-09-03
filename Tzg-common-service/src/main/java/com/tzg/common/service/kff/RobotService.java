package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.systemParam.SystemParamService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostMapper;
import com.tzg.entitys.kff.robot.Robot;
import com.tzg.entitys.kff.robot.RobotMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;
import com.tzg.entitys.kff.userqfindex.Userqfindex;
import com.tzg.entitys.leopard.system.SystemParam;

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

		Integer count = robotMapper.findPageCount(map);
		Random random = new Random();
		Robot robot = null;
		KFFUser user = null;
		while (true) {
			int id = random.nextInt(count + 1);
			if (user == null) {
				robot = robotMapper.findById(id);

			}
			if (robot != null) {
				user = usermapper.findById(robot.getUserId());
				if (null != user) {
					break;
				}
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
		int j = 1;
		while (true) {
			i = i + 1;
			PageResult<Post> postPage = getPostList(j);
			j = i * 10 + 1;
			if (null != postPage && CollectionUtils.isEmpty(postPage.getRows())) {
				break;
			}
			if (null != postPage && CollectionUtils.isNotEmpty(postPage.getRows())) {
				ExecutorService newFixedThreadPoolrobotCommentdation = null;
				try {
					newFixedThreadPoolrobotCommentdation = Executors.newFixedThreadPool(10);
					for (Post post : postPage.getRows()) {
						final Post postf = post;
						newFixedThreadPoolrobotCommentdation.execute(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								switch (k) {
								case 4:
									robotFollow(postf);// 关注
									break;
								case 3:
									robotCommendation(postf);// 打赏
									break;
								case 2:
									robotComment(postf);// 评论
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (!newFixedThreadPoolrobotCommentdation.isShutdown()) {
						newFixedThreadPoolrobotCommentdation.shutdown();
					}

				}
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
		KFFUser robotUser = findOneRobot();
		String token = AccountTokenUtil.getAccountToken(robotUser.getUserId());
		SystemParam sysBegin = systemParamService.findByCode(sysGlobals.RBT_FOLLOW_NUM_BEGIN);
		Integer begin = Integer.valueOf(sysBegin.getVcParamValue());
		SystemParam sysEnd = systemParamService.findByCode(sysGlobals.RBT_FOLLOW_NUM_END);
		Integer end = Integer.valueOf(sysEnd.getVcParamValue());
		if (end > begin) {

		}
		// 产生对象

	}

	/**
	 * 
	* @Title: robotComment 
	* @Description: TODO <评论>
	* @author zhangdd <方法创建作者>
	* @create 下午6:22:30
	* @param @param postf <参数说明>
	* @return void 
	* @throws 
	* @update 下午6:22:30
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotComment(Post postf) {
		// TODO 评论

	}

	/**
	 * 
	* @Title: robotFollow 
	* @Description: TODO <关注>
	* @author zhangdd <方法创建作者>
	* @create 下午6:22:39
	* @param @param postf <参数说明>
	* @return void 
	* @throws 
	* @update 下午6:22:39
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	protected void robotFollow(Post postf) {
		// TODO 关注
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
