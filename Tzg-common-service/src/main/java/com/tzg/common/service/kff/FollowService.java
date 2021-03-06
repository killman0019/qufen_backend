package com.tzg.common.service.kff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.cli.CommandLineUtils.StringStreamConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.follow.FollowMapper;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFFollowService")
@Transactional
public class FollowService {

	@Autowired
	private FollowMapper followMapper;

	@Transactional(readOnly = true)
	public Follow findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return followMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		followMapper.deleteById(id);
	}

	public void save(Follow follow) throws RestServiceException {
		followMapper.save(follow);
	}

	public void update(Follow follow) throws RestServiceException {
		if (follow.getFollowId() == null) {
			throw new RestServiceException("id不能为空");
		}
		followMapper.update(follow);
	}

	@Transactional(readOnly = true)
	public Integer findPageCount(Map<String, Object> map) {
		return followMapper.findPageCount(map);
	}

	@Transactional(readOnly = true)
	public PageResult<Follow> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Follow> result = null;
		try {
			Integer count = followMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Follow> list = followMapper.findPage(query.getQueryData());
				result = new PageResult<Follow>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public PageResult<PostResponse> findLinkedPage(PaginationQuery query) throws RestServiceException {
		PageResult<PostResponse> result = null;
		try {
			Integer count = followMapper.findLinkedPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<PostResponse> list = followMapper.findLinkedPage(query.getQueryData());
				result = new PageResult<PostResponse>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Follow findByUserIdAndFollowType(Integer userId, Integer followType, Integer followedId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("followUserId", userId + "");
		map.put("followType", followType + "");
		map.put("followedId", followedId + "");

		return followMapper.findByWhere(map);
	}

	public Follow findByUserIdAndFollowTypeShow(Integer userId, Integer followType, Integer followedId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("followUserId", userId + "");
		map.put("followType", followType + "");
		map.put("followedId", followedId + "");
		map.put("status", "1");
		return followMapper.findByWhere(map);
	}

	public void updateUserInfo(Map<String, Object> followMap) {

		followMapper.updateUserInfo(followMap);

	}

	public List<Follow> findFollowedProjects(Integer userId, List<Integer> projectIds) {
		if (userId == null) {
			return null;
		}
		if (CollectionUtils.isEmpty(projectIds)) {
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("followUserId", userId + "");
		map.put("followType", "1");
		map.put("followedIds", projectIds);
		map.put("status", "1");
		return followMapper.findFollowedProjects(map);
	}

	public List<Follow> findListByAttr(Map<String, Object> map) {
		return followMapper.findFollowedProjects(map);
	}

	/**
	* 根据类型用户查询用户的相关关注列表 (获得1-关注项目;2-关注帖子；3-关注用户 1关注 0 未关注)
	* @param userId
	* @param followType
	* @return
	*/
	public Map<String, Object> getUserFollow(Integer userId, Integer followType) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Integer> followedProjectIds = new ArrayList<Integer>();
		// 判断此项目是否被这个用户 关注
		Map<String, String> followMap = new HashMap<String, String>();
		followMap.put("followUserId", userId + "");
		followMap.put("followType", followType + ""); // 关注类型：1-关注项目;2-关注帖子；3-关注用户
		followMap.put("status", "1");

		List<Follow> followList = findByMap(followMap);
		if (followList != null && !CollectionUtils.isEmpty((followList))) {
			for (Follow follow : followList) {
				if (null != follow) {
					followedProjectIds.add(follow.getFollowedId());
				}
			}
		}
		resultMap.put("followedProjectIds", followedProjectIds);
		resultMap.put("followList", followList);
		return resultMap;
	}

	public List<Follow> findByMap(Map<String, String> map) {
		return followMapper.findByMap(map);
	}

	public PageResult<FollowResponse> findFansPage(PaginationQuery query) {
		// TODO 分页获得我的fans
		PageResult<FollowResponse> result = null;
		try {
			Integer count = followMapper.findPageCountFans(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<FollowResponse> list = followMapper.findPageFans(query.getQueryData());
				Integer loginUserId = (Integer) query.getQueryData().get("loginUserId");
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("followUserId", loginUserId);
				map.put("status", 1);
				map.put("followType", 3);
				List<Integer> userList = followMapper.findFollowerByMap(map);
				for (FollowResponse followResponse : list) {
					if (userList.contains(followResponse.getUserId())) {
						followResponse.setFollowStatus(1);// 关注状态:0-未关注；1-已关注，2-不显示
					} else {
						followResponse.setFollowStatus(0);
					}
					// followResponse.setFollowStatus(1);
				}
				result = new PageResult<FollowResponse>(list, count, query);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
