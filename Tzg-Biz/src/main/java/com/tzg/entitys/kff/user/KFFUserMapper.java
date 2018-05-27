package com.tzg.entitys.kff.user;

import com.tzg.common.base.BaseMapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface KFFUserMapper extends BaseMapper<KFFUser, java.lang.Integer> {

	/**
	 * 根据邮箱，手机号，用户名，微信号 (wechatuserName mobile email)查询用户是否存在
	 */
	public Integer verifyLoginaccount(Map<String, Object> map);

	public void updateUserKFFCoinNum(Map<String, Object> map);

	public void increaseFansNum(Integer userId);

	public void decreaseFansNum(Integer userId);

	public String findPhoneByUserId(Integer userId);


}
