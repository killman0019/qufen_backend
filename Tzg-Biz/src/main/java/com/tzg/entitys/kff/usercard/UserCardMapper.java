package com.tzg.entitys.kff.usercard;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;


@Repository
public interface UserCardMapper extends BaseMapper<UserCard, java.lang.Integer> {

	UserCard selectByCardId(Integer cardid);

	/**
	 * 根据用户的ID 查询此用户的身份审核状态  可能多中结果
	 * 
	 * @param userId
	 * @return
	 */
	List<UserCard> selectStatusByUserID(Integer userId);

	Integer selectUserCardNum(String userCardNum);

	void updateUserCard(UserCard userCard);

	List<UserCard> findByUserid(Integer userId);

}