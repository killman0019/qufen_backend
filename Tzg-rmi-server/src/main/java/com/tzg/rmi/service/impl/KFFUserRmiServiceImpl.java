package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.FollowService;
import com.tzg.common.service.kff.UserService;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFUserRmiService;

public class KFFUserRmiServiceImpl implements KFFUserRmiService {

	private static final Log logger = LogFactory.getLog(KFFUserRmiServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService kffFollowService;
	
	public PageResult<KFFUser> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFUser> result = userService.findPage(query);
		return result;
	}

	public KFFUser findById(Integer id) {
		return userService.findById(id);
	}

	public List<KFFUser> findListByAttr(Map<String, Object> map) {
		return userService.findListByAttr(map);
	}

	public List<KFFUser> findListByMap(Map<String, Object> map) {
		return userService.findListByMap(map);
	}
	
	public boolean findUserByStatus(Integer userId) {
		KFFUser user = userService.findById(userId);
		if(null==user) {
			return false;
		}
		//状态：0-删除；1-有效
		if(user.getStatus()==0) {
			return false;
		}else if(user.getStatus()==1) {
			return true;
		}
		return false;
	}
	

	@Override
	public PageResult<KFFUser> selectKOLProjectPage(Integer userId, PaginationQuery query) {
		// TODO 获得kol
		List<Integer> userFollow = null;
		if (null != userId) {
			userFollow = (List<Integer>) kffFollowService.getUserFollow(userId, 3).get("followedProjectIds");
		}
		query.addQueryData("DYFansNum", "30");
		query.addQueryData("status", "1");
		// query.setPageIndex(20);
		query.addQueryData("sortField", "fans_num");
		query.addQueryData("sortSequence", "DESC");
		query.addQueryData("DYPostNumAll", 5 + "");
		PageResult<KFFUser> userPage = userService.findPage(query);
		if (null != userPage && !CollectionUtils.isEmpty(userPage.getRows())) {
			List<KFFUser> userList = userPage.getRows();
			for (KFFUser kffUser : userList) {
				if (null != kffUser) {
					if (!CollectionUtils.isEmpty(userFollow) && null != userId && userFollow.contains(kffUser.getUserId())) {
						kffUser.setFollowStatus(1);// 重置成已关注
					} else {
						kffUser.setFollowStatus(0);// 重置成未关注
					}
				}
			}
		}
		return userPage;
	}
}
