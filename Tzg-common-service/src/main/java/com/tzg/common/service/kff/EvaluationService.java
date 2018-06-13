package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFEvaluationService")
@Transactional
public class EvaluationService {

	@Autowired
	private EvaluationMapper evaluationMapper;

	@Transactional(readOnly = true)
	public Evaluation findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return evaluationMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		evaluationMapper.deleteById(id);
	}

	public void save(Evaluation evaluation) throws RestServiceException {
		evaluationMapper.save(evaluation);
	}

	public void update(Evaluation evaluation) throws RestServiceException {
		if (evaluation.getEvaluationId() == null) {
			throw new RestServiceException("id不能为空");
		}
		evaluationMapper.update(evaluation);
	}

	@Transactional(readOnly = true)
	public PageResult<Evaluation> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Evaluation> result = null;
		try {
			Integer count = evaluationMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Evaluation> list = evaluationMapper.findPage(query.getQueryData());
				result = new PageResult<Evaluation>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Transactional(readOnly = true)
	public Evaluation selectEvaluationByPostId(Integer postId) {
		if (null == postId) {
			throw new RestServiceException("参数不能为空");
		}

		return evaluationMapper.selectEvaluationByPostId(postId);
	}

	@Transactional(readOnly = true)
	public List<Evaluation> findEvaliationByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		return evaluationMapper.findEvaliationByProjectId(projectId);
	}
	@Transactional(readOnly = true)
	public List<Evaluation> selectEvaluationOrNotByUserId(Evaluation evaluation) {
		// TODO Auto-generated method stub
		return evaluationMapper.selectEvaluationOrNotByUserId(evaluation);
	}
	@Transactional(readOnly = true)
	public Evaluation findByPostId(Integer postId) throws RestServiceException {
		if (postId == null) {
			throw new RestServiceException("postId不能为空");
		}
		return evaluationMapper.findByPostId(postId);
	}
	@Transactional(readOnly = true)
	public List<Evaluation> findByProjectId(Integer projectId) throws RestServiceException {
		if (projectId == null) {
			throw new RestServiceException("project不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId + "");
		map.put("status", "1");
		map.put("modelType", "2");
		List<Evaluation> list = evaluationMapper.findByWhere(map);

		return list;
	}
	@Transactional(readOnly = true)
	public List<Evaluation> findAllTypeByProjectId(Integer projectId) throws RestServiceException {
		if (projectId == null) {
			throw new RestServiceException("project不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId + "");
		map.put("status", "1");
		List<Evaluation> list = evaluationMapper.findByWhere(map);

		return list;
	}
	@Transactional(readOnly = true)
	public List<Evaluation> findSimpleEvaByProjectId(Integer projectId) throws RestServiceException {
		if (projectId == null) {
			throw new RestServiceException("project不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId + "");
		map.put("status", "1");
		map.put("modelType", "1");
		List<Evaluation> list = evaluationMapper.findByWhere(map);

		return list;
	}
	@Transactional(readOnly = true)
	public List<Evaluation> selectEvaluationByParam(Integer projectId, Integer createUserId, Integer modelType) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		map.put("createUserId", createUserId);
		map.put("modelType", modelType);
		return evaluationMapper.findByWhere(map);
	}

}
