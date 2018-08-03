package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;


public interface MiningActivityMapper extends BaseMapper<MiningActivity, java.lang.Integer>{
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(MiningActivity record);
//
//    int insertSelective(MiningActivity record);

    MiningActivity selectByPrimaryKey(Integer id);

    List<MiningActivity> findListByAttr(Map<String,Object> map);

    void updateByMap(Map<String,Object> map);
    
    Integer updateByOptimisticLock(MiningActivity miningActivity);
}