package com.tzg.entitys.kff.follow;

import com.tzg.common.base.BaseMapper;
import com.tzg.entitys.kff.post.PostResponse;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FollowMapper extends BaseMapper<Follow, java.lang.Integer> {

	Follow findByWhere(Map<String, Object> map);

	void updateUserInfo(Map<String, Object> followMap);

	List<Follow> findFollowedProjects(Map<String, Object> map);

	List<Follow> findByMap(Map<String, String> map);

	Integer findPageCountFans(Map<String, Object> queryData);

	List<FollowResponse> findPageFans(Map<String, Object> queryData);

	List<Integer> findFollowerByMap(Map<String, Object> map);

	Integer findLinkedPageCount(Map<String, Object> queryData);
	List<PostResponse> findLinkedPage(Map<String, Object> queryData);
}
