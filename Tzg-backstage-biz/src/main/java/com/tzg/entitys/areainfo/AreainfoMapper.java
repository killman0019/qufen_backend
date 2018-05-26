package com.tzg.entitys.areainfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface AreainfoMapper extends BaseMapper<Areainfo, java.lang.Integer> {	
	/**
	 * 查询所有地区
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> findAll() throws Exception;
	
	/**
	 * 查询所有顶级地区
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> findFirstLevelAll() throws Exception;

    public Areainfo findAreaByCode(String vcCode) throws Exception;
    
    /**
     * 根据地区编码，查询本级地区的所有下级地区
     * @param vcCode
     * @return
     * @throws Exception
     */
    public List<Areainfo> findAreaListByParentCode(String vcCode) throws Exception;

}
