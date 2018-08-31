package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.robot.Robot;
import com.tzg.entitys.kff.robot.RobotMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class RobotService {
	@Autowired
	private RobotMapper robotMapper;
	@Autowired
	private KFFUserMapper usermapper;


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
}
