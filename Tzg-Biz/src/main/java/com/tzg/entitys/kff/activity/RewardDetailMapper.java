package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;


public interface RewardDetailMapper extends BaseMapper<RewardDetail, java.lang.Integer>{

    RewardDetail selectByPrimaryKey(Integer id);

    List<RewardDetail> findListByAttr(Map<String,Object> map);

}