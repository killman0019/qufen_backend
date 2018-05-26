package com.tzg.entitys.safeguardmeasures;

import java.util.List;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface SafeguardmeasuresMapper extends BaseMapper<Safeguardmeasures, java.lang.Integer> {	
	
	/**
	 * 根据状态获取所有的保障措施
	 * @param iState
	 * @return
	 * @throws Exception
	 */
	public List<Safeguardmeasures> queryByIstate(Integer iState) throws Exception;
}
