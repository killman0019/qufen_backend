package com.tzg.entitys.appwelcomeimage;

import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface AppwelcomeimageMapper extends BaseMapper<Appwelcomeimage, java.lang.Integer> {	
	public Appwelcomeimage findByMap(Map<String,String> where);
}
