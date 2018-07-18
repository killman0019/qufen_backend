package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationDetailResponse;
import com.tzg.entitys.kff.evaluation.EvaluationMapper;
import com.tzg.entitys.kff.praise.Praise;
import com.tzg.entitys.kff.praise.PraiseMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFEvaluationService")
@Transactional
public class EvaluationService {

	@Autowired
	private EvaluationMapper evaluationMapper;

	@Autowired
	private PraiseMapper praiseMapper;

	@Autowired
	private KFFUserMapper kFFUserMapper;

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

	public List<Evaluation> selectEvaluationOrNotByUserId(Evaluation evaluation) {
		// TODO Auto-generated method stub
		return evaluationMapper.selectEvaluationOrNotByUserId(evaluation);
	}

	public Evaluation findByPostId(Integer postId) throws RestServiceException {
		if (postId == null) {
			throw new RestServiceException("postId不能为空");
		}
		return evaluationMapper.findByPostId(postId);
	}

	public List<Evaluation> findProEvaByProjectId(Integer projectId) throws RestServiceException {
		if (projectId == null) {
			throw new RestServiceException("project不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId + "");
		map.put("status", "1");
		map.put("modelType", "2");// 全面评测
		List<Evaluation> list = evaluationMapper.findByWhere(map);

		return list;
	}

	public List<Evaluation> selectProOnlyEvaByProjectId(Integer projectId) throws RestServiceException {
		if (projectId == null) {
			throw new RestServiceException("project不能为空");
		}
		Map<String, Object> map = new HashMap<>();
		map.put("projectId", projectId + "");
		map.put("status", "1");
		map.put("modelType", "3");// 单项评测
		List<Evaluation> list = evaluationMapper.findByWhere(map);

		return list;
	}

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

	public PageResult<EvaluationDetailResponse> findPageSimpleEvaluation(PaginationQuery query, Integer userId) {
		PageResult<EvaluationDetailResponse> result = null;
		try {
			Integer count = evaluationMapper.findPageSimpleEvaluationCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<EvaluationDetailResponse> list = evaluationMapper.findPageSimpleEvaluation(query.getQueryData());
				// 判断点赞状态
				// 用户判断点赞状态
				if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
					for (EvaluationDetailResponse response : list) {
						// 判断用户否为登陆状态 默认是2 未登录
						// 判断用户是否已经点赞
						Map<String, Object> map = new HashMap<String, Object>();
						/*map.put("bepraiseUserId", response.getCreateUserId());
						map.put("praiseUserId", userId);
						map.put("praiseType", "1");
						Praise praise = praiseMapper.findByPraiseId(map);*/
						/*	map.put("postId", response.getPostId());
							map.put("praiseType", 2);
							map.put("praiseUserId", userId);
							Praise praise = praiseMapper.findByPostId(map);
							System.err.println("prai" + JSON.toJSONString(praise));
							if (response.getCreateUserId().equals(userId)) {// 说明是自己写的帖子 已点赞
								// response.setPraiseStatus(KFFConstants.PRAISE_STATUS_NO);
							} else {
								if (praise != null) {
									// 说明已经点过赞
									response.setPraiseStatus(praise.getStatus());
								}
							}*/
						map.put("postId", response.getPostId());
						map.put("praiseType", 1);
						map.put("praiseUserId", userId);
						if (userId != null) {
							Praise praise = praiseMapper.findevaByPostId(map);
							if (praise != null) {
								response.setPraiseStatus(praise.getStatus());
							}
						} else {// 0-未点赞；1-已点赞，2-未登录用户不显示 数字
							response.setPraiseStatus(KFFConstants.PRAISE_STATUS_NOSHOW);
						}
						Map<String, Object> mappraise = new HashMap<String, Object>();
						mappraise.put("postId", response.getPostId());
						mappraise.put("status", "1");
						Integer praiseNum = praiseMapper.findPageCount(mappraise);
						response.setPraiseNum(praiseNum);
						KFFUser createUser = kFFUserMapper.findById(response.getCreateUserId());
						if (null != createUser) {
							response.setUserType(createUser.getUserType());
						}
					}

				}
				System.out.println("response" + JSON.toJSONString(list));
				result = new PageResult<EvaluationDetailResponse>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * @param projectId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Evaluation> selectAllEvaExceptOnlyEvaByProjectId(Integer projectId) {

		return evaluationMapper.getAllEvasExceptPartByProjectId(projectId);
	}

	@Transactional(readOnly = true)
	public List<Evaluation> selectEvaByMap(Map<String, Object> map) {
		return evaluationMapper.findByWhere(map);

	}

}
