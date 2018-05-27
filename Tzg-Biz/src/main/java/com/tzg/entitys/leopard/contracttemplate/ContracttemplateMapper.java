package com.tzg.entitys.leopard.contracttemplate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

/**
 * 
 * @Description：合同模板Mapper
 * @author wxg
 * @Date 2014-12-17
 *
 */
@Repository
public interface ContracttemplateMapper extends BaseMapper<Contracttemplate, Integer>{
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Contracttemplate> queryTbContractTemplateAll() throws Exception;

}
