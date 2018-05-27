package com.tzg.entitys.kff.authentication;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface AuthenticationMapper extends BaseMapper<Authentication, java.lang.Integer> {

	List<Authentication> selectAuthenticationByUserId(Integer userId);

	Integer updataAuthenByUserId(Authentication authentication);

	Integer saveAuthenByUserId(Authentication authentication);

}
