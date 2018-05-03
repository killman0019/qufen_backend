package com.tzg.rmi.service;

import java.util.List;

import com.tzg.entitys.areainfo.Areainfo;

/**
 * 地区
 * @author hew
 *
 */
public interface AreainfoRmiService {
	
	/**
	 * 查询所有顶级地区
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> findFirstLevelList() throws Exception;
	
	/**
	 * 根据地区编号 获取下级地区
	 * @param parentCode
	 * @return
	 */
	public List<Areainfo> findByParentCode(String parentCode) throws Exception;
	
	/**
	 * 根据地区查询记录
	 * @param code
	 * @return
	 */
	public Areainfo findByCode(String code) throws Exception;
}
