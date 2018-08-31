package com.tzg.rmi.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.tzg.common.redis.RedisService;
import com.tzg.common.service.kff.InteractionRootService;
import com.tzg.common.service.kff.RobotService;
import com.tzg.common.utils.DozerMapperUtils;
import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.entitys.kff.interactionroot.InteractionRoot;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFRobotRmiService;

/**
 * 
* @ClassName: KFFRobotRmiServiceImpl 
* @Description: TODO<机器人相关实现类> 
* @author zhangdd<作者>
* @date 2018年8月31日 下午2:04:24 
* @version v1.0.0
 */
public class KFFRobotRmiServiceImpl implements KFFRobotRmiService {
	@Autowired
	private RedisService redisService;
	@Autowired
	private InteractionRootService interactionRootService;

	@Autowired
	private RobotService robotService;

	@Override
	public CommentsRequest createComment(CommentsRequest comment, Integer loginUserId) throws RestServiceException {
		// TODO Auto-generated method stub

		Map<String, Object> robotMap = new HashMap<String, Object>();
		robotMap.put("status", "1");
		robotMap.put("userId", loginUserId);
		List<InteractionRoot> rootList = interactionRootService.findByMap(robotMap);
		if (CollectionUtils.isEmpty(rootList)) {
			return comment;
		} else {
			// 产生一个随机的机器人代替作者
			KFFUser robotUser = robotService.findOneRobot();
			CommentsRequest robotComment = new CommentsRequest();
			DozerMapperUtils.map(comment, robotComment);
			if (null != robotUser) {
				robotComment.setCommentUserIcon(robotUser.getIcon());
				robotComment.setCommentUserId(robotUser.getUserId());
				robotComment.setCommentUserName(robotUser.getUserName());

			}
			return robotComment;

		}

	}

}
