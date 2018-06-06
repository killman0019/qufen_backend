package com.tzg.common.service.kff;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler.referenceInsertExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
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

	@Transactional(readOnly = true)
	public Integer selectCUserType(Integer createUserId) {

		List<Authentication> userType = authenticationMapper.selectAuthenticationByUserId(createUserId);
		if (CollectionUtils.isEmpty(userType)) {
			// 0为出查询的数据list为空,请求service抛出异常
			return 0;

		}
		Integer type = userType.get(0).getType();
		if (null == type) {
			// 说明用户没有进行认证,返回4
			return 4;
		}
		return type;
	}

}
