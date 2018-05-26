package com.tzg.entitys.projectsafeguard;

import java.util.List;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsafeguardMapper extends
		BaseMapper<Projectsafeguard, java.lang.Integer> {

	/**
	 * 根据项目编号，查询记录
	 * 
	 * @param iProjectID
	 * @return
	 * @throws Exception
	 */
	public List<Projectsafeguard> queryByProjectID(Integer iProjectID)
			throws Exception;

	/**
	 * 根据项目编号删除记录
	 * @param iProjectID
	 * @throws Exception
	 */
	public void deleteByProjectID(Integer iProjectID) throws Exception;
}
