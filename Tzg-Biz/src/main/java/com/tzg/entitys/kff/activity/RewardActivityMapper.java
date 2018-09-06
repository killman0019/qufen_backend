package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;
import com.tzg.entitys.kff.post.PostDiscussVo;

public interface RewardActivityMapper extends BaseMapper<RewardActivity, java.lang.Integer> {
	
    List<RewardActivity> findListByAttr(Map<String, Object> map);
    
    List<PostDiscussVo> findSetTopPost(Map<String, Object> map);
    Integer findSetTopPostCount(Map<String, Object> map);
    

}