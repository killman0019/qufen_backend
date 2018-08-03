package com.tzg.entitys.kff.commendation;

import com.tzg.common.base.BaseMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommendationMapper extends BaseMapper<Commendation, java.lang.Integer> {

	BigDecimal findCommendationNum(Map<String, Object> totalMap);

	void updateUserInfo(Map<String, Object> commendationMap);

	/**
	 * 根据postid 查询打赏表中 的所有对这个贴子的打赏
	 * @param postId 
	 * 
	 * @return
	 */
	List<Commendation> selectAllCommendationByPostId(Integer postId);

}
