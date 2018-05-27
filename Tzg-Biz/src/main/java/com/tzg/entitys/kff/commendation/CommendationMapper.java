package com.tzg.entitys.kff.commendation;
import com.tzg.common.base.BaseMapper;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CommendationMapper extends BaseMapper<Commendation, java.lang.Integer> {

	BigDecimal findCommendationNum(Map<String, Object> totalMap);

	void updateUserInfo(Map<String, Object> commendationMap);	

}
