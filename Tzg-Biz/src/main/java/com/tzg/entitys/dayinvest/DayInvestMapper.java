package com.tzg.entitys.dayinvest;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface DayInvestMapper extends BaseMapper<DayInvest, java.lang.Integer> {
	int save();
	int delete();
	List<DayInvest> list();
}
