package com.tzg.entitys.kff.activity;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;
import com.tzg.entitys.kff.post.PostDiscussVo;
import com.tzg.entitys.kff.post.PostResponse;

public interface RewardActivityMapper extends BaseMapper<RewardActivity, java.lang.Integer> {
	
    List<RewardActivity> findListByAttr(Map<String, Object> map);
    
    List<PostDiscussVo> findSetTopPost(Map<String, Object> map);
    Integer findSetTopPostCount(Map<String, Object> map);
    
    Map<String, Object> findOneByAttr(Map<String, Object> map);
    
    List<PostResponse> findLinkedPage(Map<String, Object> map);
    Integer findLinkedPageCount(Map<String, Object> map);
    
    Integer findLinkedCount(Map<String, Object> map);
    
    List<RewardActivityVo> findRewardActivityListPage(Map<String, Object> map);
    Integer findRewardActivityListCount(Map<String, Object> map);
    
    void updateByMap(Map<String, Object> map);
}