package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.systemParam.SystemParamService;
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
	private PostMapper mapper;

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
	* @Title: robotPraiseTask 
	* @Description: TODO <机器人进行点赞>
	* @author zhangdd <方法创建作者>
	* @create 下午4:44:50
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午4:44:50
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotPraiseTask() {

	}

	/**
	 * 
	* @Title: robotCommentTask 
	* @Description: TODO <机器人评论>
	* @author zhangdd <方法创建作者>
	* @create 下午4:45:45
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 下午4:45:45
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void robotCommentTask() {

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
	public void robotCommendationTask() {

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
	private PageResult<Post> getPostList(PaginationQuery query) {
		PageResult<Post> result = null;
		SystemParam param = systemParamService.findByCode(sysGlobals.ROBOT_GET_POST_CREATE_TIME);
		int days = Integer.valueOf(param.getVcParamValue());
		if (days > 0) {
			String postCreateBegin = DateUtil.getSpecifiedDayBeforeOrAfter(days);

			try {
				Integer count = mapper.findPageCount(query.getQueryData());
				if (null != count && count.intValue() > 0) {
					int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
					query.addQueryData("startRecord", Integer.toString(startRecord));
					query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
					List<Post> list = mapper.findPage(query.getQueryData());
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
