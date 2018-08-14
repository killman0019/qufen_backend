package com.tzg.common.service.kff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.praise.PraiseMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFPraiseService")
@Transactional
public class PraiseService {

	@Autowired
	private PraiseMapper praiseMapper;

	@Transactional(readOnly = true)
	public Praise findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return praiseMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		praiseMapper.deleteById(id);
	}

	public void save(Praise praise) throws RestServiceException {
		praiseMapper.save(praise);
	}

	public void update(Praise praise) throws RestServiceException {
		if (praise.getPraiseId() == null) {
			throw new RestServiceException("id不能为空");
		}
		praiseMapper.update(praise);
	}

	@Transactional(readOnly = true)
	public PageResult<Praise> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Praise> result = null;
		try {
			Integer count = praiseMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Praise> list = praiseMapper.findPage(query.getQueryData());
				result = new PageResult<Praise>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Praise findByPostId(Integer userId, Integer postId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", postId);
		map.put("praiseType", 1);
		map.put("praiseUserId", userId);
		return praiseMapper.findByPostId(map);
	}

	public Praise findByCommentsId(Integer userId, Integer commentsId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("postId", commentsId);
		map.put("praiseType", 2);
		map.put("praiseUserId", userId);
		return praiseMapper.findByPostId(map);
	}

	@Transactional(readOnly = true)
	public Praise findByPraiseId(Integer createUserId, Integer userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bepraiseUserId", createUserId);
		map.put("praiseType", 1);
		map.put("praiseUserId", userId);
		return praiseMapper.findByPraiseId(map);
	}

	@Transactional(readOnly = true)
	public List<Praise> findAllActivePraisesByPostId(Evaluation eva, Integer projectId) {
		// 系统专业评测 //用户自定义评测
		Map<String, Object> map = new HashMap<>();
		map.put("status", "1");
		map.put("postType", "1");
		map.put("postId", eva.getPostId());
		map.put("projectId", projectId + "");
		map.put("praiseType", "1");
		return praiseMapper.findAllActivePraisesByPostId(map);
	}

	/**
	 * 
	 * TODO   获得当前用户的所有点赞的postid 
	 * @param loginUserId
	 * @return
	 * @author zhangdd
	 * @data 2018年8月14日
	 *
	 */
	public List<Integer> findPraisedPostIdByUserId(Integer loginUserId) {
		// TODO 获得查询此用户所有点赞数list
		Map<String, Object> praiseMap = new HashMap<String, Object>();
		praiseMap.put("praiseUserId", loginUserId);
		praiseMap.put("status", "1");
		List<Praise> praiseList = findByMap(praiseMap);
		List<Integer> praisedPostIdList = new ArrayList<Integer>();
		if (CollectionUtils.isEmpty(praiseList)) {
			return praisedPostIdList;
		}
		for (Praise praise : praiseList) {
			if (null != praise) {
				praisedPostIdList.add(praise.getPostId());
			}
		}
		return praisedPostIdList;
	}

	public List<Praise> findByMap(Map<String, Object> praiseMap) {
		// TODO map 查询

		return praiseMapper.findByMap(praiseMap);
	}

}
