package com.tzg.rmi.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tzg.common.service.kff.EvaluationService;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.rmi.service.EvaluationRmiService;

public class EvaluationRmiServiceImpl implements EvaluationRmiService {

	private static final Log logger = LogFactory.getLog(EvaluationRmiServiceImpl.class);

	@Autowired
	private EvaluationService evaluationService;

	
	public Evaluation findById(Integer id) {
		return  evaluationService.findById(id);
	}
	
	public List<Evaluation> findListByAttr(Map<String, Object> map) {
		return evaluationService.selectEvaByMap(map);
	}
}
