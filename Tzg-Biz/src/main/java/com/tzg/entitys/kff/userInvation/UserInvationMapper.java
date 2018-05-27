package com.tzg.entitys.kff.userInvation;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface UserInvationMapper extends BaseMapper<UserInvation, java.lang.Integer> {

	void insert(UserInvation userInvation);

	UserInvation selectUserInvation(Integer userId);

}
