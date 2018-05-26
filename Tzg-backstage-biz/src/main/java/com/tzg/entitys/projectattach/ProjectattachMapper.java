package com.tzg.entitys.projectattach;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectattachMapper extends
		BaseMapper<Projectattach, java.lang.Integer> {

	/**
	 * 根据项目编号，查询记录
	 * 
	 * @param iProjectID
	 * @return
	 * @throws Exception
	 */
	public List<Projectattach> queryInfoByProjectIDList(Map<String,Object> map)
			throws Exception;
	
	/**
	 * 根据字段组合查询
	 * @param map
	 * @return
	 */
	public List<Projectattach> find(Map<String,String> map);
	
	/**
	 * 根据主键和类型删除 iProjectID  / iType
	 * @param map  
	 */
	public void deleteByProjectIdAndType(Map<String,String> map);

	/**
	 * 根据主键删除
	 * 
	 * @param iProjectID
	 * @throws Exception
	 */
	public void deleteByProjectId(Integer iProjectID) throws Exception;

}
