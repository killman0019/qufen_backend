package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;


public interface PostShareMapper extends BaseMapper<PostShare, java.lang.Integer>{
	
    PostShare selectByPrimaryKey(Integer id);
    List<PostShare> findListByAttr(Map<String,Object> map);
}