package com.tzg.entitys.kff.post;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface PostMapper extends BaseMapper<Post, java.lang.Integer> {

	List<Post> findProjectActiveUsers(Map<String, Object> map);	
	Post findByUUID(String uuid);
	void updateDonateNum(Integer postId);
	void increasePraiseNum(Integer postId);
	void decreasePraiseNum(Integer postId);
	void increaseCollectNum(Integer postId);
	void increasePageviewNum(Integer postId);
	void decreaseCollectNum(Integer postId);
	void updateUserInfo(Map<String, Object> postMap);
	Integer findMyPageFollowListCount(Map<String, Object> map);
	List<Post> findMyPageFollowList(Map<String, Object> map);
}
