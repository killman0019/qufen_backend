package com.tzg.entitys.kff.tokenaward;

import com.tzg.common.base.BaseMapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface TokenawardMapper extends BaseMapper<Tokenaward, java.lang.Integer> {

	Integer save(Tokenaward tokenaward);

	void update(Tokenaward tokenaward);

	List<Tokenaward> findByUserId(Integer id);

	Double reawardSum(Integer userId, Integer functionType);

	List<Tokenaward> priaiseAwardSum(Integer userId);

	Double findUserSumRewardToken(Integer userId, Integer functionType);

	Double findUserSumInviteRewards(Integer userId, Integer functionType);

	<E> Double findUserSumInviteRewards(Map<E, E> map);

	List<Tokenaward> findAllTokenawardByWhere(Map<String, Object> map);

	void updateByGrantType(Integer a);
}
