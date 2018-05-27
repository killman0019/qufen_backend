package com.tzg.common.service.kff;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.authentication.Authentication;
import com.tzg.entitys.kff.authentication.AuthenticationMapper;

@Service(value = "AuthenticationService")
@Transactional
public class AuthenticationService {
	private static final Log logger = LogFactory.getLog(UserCardService.class);
	@Autowired
	private AuthenticationMapper authenticationMapper;

	@Transactional(readOnly = true)
	public List<Authentication> selectAuthenticationByUserId(Integer userId) {
		return authenticationMapper.selectAuthenticationByUserId(userId);
	}

	public Integer updataAuthenByUserId(Authentication authentication) {
		return authenticationMapper.updataAuthenByUserId(authentication);
	}

	public Integer saveAuthenByUserId(Authentication authentication) {
		return authenticationMapper.saveAuthenByUserId(authentication);
	}

}
