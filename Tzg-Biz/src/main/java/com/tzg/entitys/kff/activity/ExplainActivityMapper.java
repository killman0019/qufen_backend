package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

public interface ExplainActivityMapper extends BaseMapper<ExplainActivity, java.lang.Integer> {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(ExplainActivity record);
//
//    int insertSelective(ExplainActivity record);

    ExplainActivity selectByPrimaryKey(Integer id);

    List<ExplainActivity> findListByAttr(Map<String,Object> map);
    List<ExplainActivity> selectAll();
    
//    int updateByPrimaryKey(ExplainActivity record);
}