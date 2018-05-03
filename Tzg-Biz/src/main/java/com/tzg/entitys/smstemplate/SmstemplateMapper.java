package com.tzg.entitys.smstemplate;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface SmstemplateMapper extends BaseMapper<Smstemplate, java.lang.Integer> {	

	/**
	 * 根据状态返回可用的记录 
	 * @param itype
	 * @return
	 */
	public Smstemplate findByType(Integer itype);
}
