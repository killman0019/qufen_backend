package com.tzg.entitys.registemessage;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface RegistemessageMapper extends BaseMapper<Registemessage, java.lang.Integer> {


	public List<Registemessage> findLatest();

	public List<Registemessage> getRegisteList();	
	

}
