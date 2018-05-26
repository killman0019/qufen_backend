package com.tzg.entitys.projectsuggestions;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsuggestionsMapper extends BaseMapper<Projectsuggestions, java.lang.Integer> {
	
	/**
	 * 根据项目编号，查询记录
	 * @param iProjectID
	 * @return
	 * @throws Exception
	 */
	public Projectsuggestions queryInfoByProjectID(Integer iProjectID) throws Exception;

}
