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
import com.tzg.entitys.kff.project.ProjectResponse;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rmi.service.KFFUserRmiService;

public class KFFUserRmiServiceImpl implements KFFUserRmiService {

	private static final Log logger = LogFactory.getLog(KFFUserRmiServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private FollowService kffFollowService;

	public KFFUser findById(Integer id) {
		return userService.findById(id);
	}

	public List<KFFUser> findListByAttr(Map<String, Object> map) {
		return userService.findListByAttr(map);
	}

	public List<KFFUser> findListByMap(Map<String, Object> map) {
		return userService.findListByMap(map);
	}

	@Override
	public PageResult<KFFUser> selectKOLProjectPage(Integer userId, PaginationQuery query) {
		// TODO KOL��(��ע�����30�˵��û��񵥣�����˿���������20��)
		List<Integer> userFollow = null;
		if (null != userId) {
			userFollow = kffFollowService.getUserFollow(userId, 3);
		}
		query.addQueryData("DYFansNum", "30");
		query.addQueryData("status", "1");
		// query.setPageIndex(20);
		query.addQueryData("sortField", "fans_num");
		query.addQueryData("sortSequence", "DESC");
		PageResult<KFFUser> userPage = userService.findPage(query);
		if (null != userPage && !CollectionUtils.isEmpty(userPage.getRows())) {
			List<KFFUser> userList = userPage.getRows();
			for (KFFUser kffUser : userList) {
				if (null != kffUser) {
					if (!CollectionUtils.isEmpty(userFollow) && null != userId && userFollow.contains(kffUser.getUserId())) {
						kffUser.setFollowStatus(1);// ���óɹ�ע
					} else {
						kffUser.setFollowStatus(0);// ���ó�δ��ע
					}
				}
			}
		}
		return userPage;
	}
}
