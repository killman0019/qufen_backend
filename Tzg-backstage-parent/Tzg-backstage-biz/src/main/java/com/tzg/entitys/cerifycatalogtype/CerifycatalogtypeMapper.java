package com.tzg.entitys.cerifycatalogtype;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface CerifycatalogtypeMapper extends
		BaseMapper<Cerifycatalogtype, java.lang.Integer> {

	/**
	 * 根据条件，查询所有记录
	 * 
	 * @param iState
	 * @return
	 */
	public List<Cerifycatalogtype> queryCerifycatalogtypeAll(Integer iState);
}
