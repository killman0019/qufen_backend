package com.tzg.common.service.kff;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.userInvation.UserInvation;
import com.tzg.entitys.kff.userInvation.UserInvationMapper;

@Service(value = "UserInvationService")
@Transactional
public class UserInvationService {

	@Autowired
	private UserInvationMapper userInvationMapper;

	public void saveUserInvation(Integer userId, String userIdTo2code) {
		UserInvation userInvation = new UserInvation();
		userInvation.setUser2code(userIdTo2code);
		userInvation.setStatus(1);
		userInvation.setCreatetime(new Date());
		userInvation.setUserId(userId);
		userInvation.setUser2codepic(null);
		userInvation.setUserposterpic(null);
		userInvationMapper.insert(userInvation);
	}

	@Transactional(readOnly = true)
	public UserInvation selectUserInvation(Integer userId) {
		// TODO Auto-generated method stub
		return userInvationMapper.selectUserInvation(userId);
	}

	@Transactional(readOnly = true)
	public UserInvation selectUseInvation(Integer userId) {

		return userInvationMapper.selectUserInvation(userId);
	}

	public void updataUserInvation(Integer userId, String posterUrl, String code2Url) {
		UserInvation userInvation = new UserInvation();
		userInvation.setUser2code(null);
		userInvation.setStatus(1);
		userInvation.setCreatetime(null);
		userInvation.setUserId(userId);
		userInvation.setUser2codepic(code2Url);
		userInvation.setUserposterpic(posterUrl);
		userInvationMapper.updataUserInvation(userInvation);

	}

}
