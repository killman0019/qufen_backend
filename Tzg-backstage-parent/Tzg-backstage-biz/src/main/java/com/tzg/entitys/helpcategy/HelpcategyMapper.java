package com.tzg.entitys.helpcategy;

import java.util.List;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface HelpcategyMapper extends BaseMapper<Helpcategy, java.lang.Integer> {	

	/**
	 * 根据istate值，查询记录
	 * @param istate
	 * @return
	 * @throws Exception
	 */
	public List<Helpcategy> queryHelpcategyList(Integer istate)throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Helpcategy> queryHelpcategyAll()throws Exception;
	
}
