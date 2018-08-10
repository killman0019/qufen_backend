package com.tzg.entitys.kff.content;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;


public interface NavigationProjectMapper extends BaseMapper<NavigationProject, Integer>{
    NavigationProject selectByPrimaryKey(Integer id);
    
    List<NavigationProject> findListByAttr(Map<String, Object> map);
    
}