package com.tzg.entitys.projectcataloglist;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface ProjectcataloglistMapper extends
		BaseMapper<Projectcataloglist, java.lang.Integer> {

	/**
	 * 根据项目编号，查询记录
	 * 
	 * @param iProjectID
	 * @return
	 * @throws Exception
	 */
	public List<Projectcataloglist> queryInfoByProjectID(Integer iProjectID)
			throws Exception;
	
	/**
	 * 
	 * @param iProjectID
	 * @throws Exception
	 */
	public void deleteByIProjectID(Integer iProjectID) throws Exception;
	
	
	
	

}
