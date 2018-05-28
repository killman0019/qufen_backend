package com.tzg.entitys.kff.qfindex;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface QfIndexMapper extends BaseMapper<QfIndex, java.lang.Integer> {

	QfIndex findByUserId(Integer userid);
	void updateYxPraise(Integer qfIndexId , Integer userId);
	void updateYxPraiseValid();

}
