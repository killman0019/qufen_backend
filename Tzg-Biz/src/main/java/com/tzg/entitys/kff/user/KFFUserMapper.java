package com.tzg.entitys.kff.user;

import com.tzg.common.base.BaseMapper;

import java.util.List;
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

	// 根据用户id 去查询用户类型
	public KFFUser findUserById(Integer userId);

	public KFFUser findByMobileId(String loginName);

	public KFFUser findByUserName(String loginName);

	public Integer findReferCount(Integer userId);

	public List<KFFUser> selectInvationM1Num(Integer userId);

	public void increaseArticleNum(Integer userId);

	public void increaseEvaNum(Integer userId);

	public void increaseDiscussNum(Integer userId);

	public Integer saveUser(KFFUser user);

	public void increasePraiseNum(Integer userId);

	public void decreasePraiseNum(Integer userId);

	public Integer findPopByToken(Integer userId);

	public void updateUserKFFPop(Integer userId);

	public KFFUser findUserStatusByPhoneNumber(String mobile);

	public void updateUserKFFsetPopZero(Integer userId);

	public List<KFFUser> findPageWithCID(Map<String, Object> map);

	public List<KFFUser> findListByAttr(Map<String, Object> map);

	public List<KFFUser> findListByMap(Map<String, Object> map);

	public void increaseKffcoinNum(Map<String, Object> map);

	public void decreaseKffcoinNum(Map<String, Object> map);

	/**
	 * 
	* @Title: setPop 
	* @Description: TODO <将所有用户的弹出框重置成0>
	* @author zhangdd <方法创建作者>
	* @create 上午9:59:57
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 上午9:59:57
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void setPop();

}
