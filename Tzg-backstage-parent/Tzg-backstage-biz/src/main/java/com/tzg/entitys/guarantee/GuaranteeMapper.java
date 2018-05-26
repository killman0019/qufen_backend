package com.tzg.entitys.guarantee;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface GuaranteeMapper extends BaseMapper<Guarantee, java.lang.Integer> {	
	
	public List<Guarantee> queryTbGuaranteeAll() throws Exception;
	
	public Guarantee findByName(String vcName);

}
