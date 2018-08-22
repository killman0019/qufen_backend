package com.tzg.entitys.kff.content;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

public interface NavigationMapper extends BaseMapper<Navigation, Integer>{

    Navigation selectByPrimaryKey(Integer id);

    List<Navigation> findAll();
    
    List<Navigation> findListByAttr(Map<String, Object> map);
    
}