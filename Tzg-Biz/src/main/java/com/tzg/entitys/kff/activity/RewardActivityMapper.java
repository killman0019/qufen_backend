package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

public interface RewardActivityMapper extends BaseMapper<RewardActivity, java.lang.Integer> {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(RewardActivity record);

    List<RewardActivity> findListByAttr(Map<String, Object> map);

//    int updateByPrimaryKeySelective(RewardActivity record);
//
//    int updateByPrimaryKey(RewardActivity record);
}