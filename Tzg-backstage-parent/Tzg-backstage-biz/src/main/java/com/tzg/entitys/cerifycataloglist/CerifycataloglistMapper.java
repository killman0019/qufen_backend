package com.tzg.entitys.cerifycataloglist;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface CerifycataloglistMapper extends
		BaseMapper<Cerifycataloglist, java.lang.Integer> {

	/**
	 * 根据目录类型，查询目录信息
	 * 
	 * @param iTypeID
	 * @return
	 * @throws Exception
	 */
	public List<Cerifycataloglist> queryCatalogNameByTypeList(Integer iTypeID)
			throws Exception;

	/**
	 * 根据状态，查询目录信息
	 * @param iState
	 * @return
	 * @throws Exception
	 */
	public List<Cerifycataloglist> querytbCerifyCatalogByStateList(
			Integer iState) throws Exception;

}
