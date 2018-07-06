package com.tzg.entitys.kff.newFlash;

import java.util.Map;

import com.tzg.common.base.BaseMapper;

public interface KFFNewsFlashMapper extends BaseMapper<KFFNewsFlash, Integer> {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(SystemNewsFlash record);
//
//    int insertSelective(SystemNewsFlash record);

    KFFNewsFlash selectByPrimaryKey(Integer id);

    void updateByMap(Map<String,Object> map);
//
//    int updateByPrimaryKey(SystemNewsFlash record);
}