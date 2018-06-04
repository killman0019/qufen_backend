package com.tzg.wap.controller.h5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.entitys.kff.devaluationModel.DevaluationModel;
import com.tzg.entitys.kff.devaluationModel.DevaluationModelRequest;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.evaluation.Evaluation;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFEvaluationController")
@RequestMapping("/kff/evaluation")
public class EvaluationController extends BaseController {
	private static Logger log = Logger.getLogger(EvaluationController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	/**
	 * 发表评测,传入evaluationRequest 对象
	 * 
	 * @param evaluationRequest
	 * @return
	 */
	@RequestMapping(value = "/saveEvaluation", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveEvaluation(HttpServletRequest request, HttpServletResponse response, EvaluationRequest evaluationRequest, String token,
			String projectName) {

		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			evaluationRequest.setProfessionalEvaDetail(StringEscapeUtils.unescapeHtml(evaluationRequest.getProfessionalEvaDetail()));
			evaluationRequest.setEvauationContent(StringEscapeUtils.unescapeHtml(evaluationRequest.getEvauationContent()));
			Integer userId = getUserIdByToken(token);
			evaluationRequest.setCreateUserId(userId);
			KFFProject project = kffRmiService.selectProjectByprojectName(projectName);
			evaluationRequest.setProjectId(project.getProjectId());
			// 判断用户是否已经对此项目进行了全面评测
			if (2 == evaluationRequest.getModelType()) {
				Evaluation evaluationDB = new Evaluation();
				evaluationDB.setModelType(evaluationRequest.getModelType());
				evaluationDB.setCreateUserId(evaluationRequest.getCreateUserId());
				evaluationDB.setProjectId(project.getProjectId());
				Evaluation evaluations = kffRmiService.selectEvaluationByUserId(evaluationDB);
				if (null != evaluations) {
					throw new RestServiceException("不能对此项目进行重复评测!");
				}
			}
			Map<String, Object> saveEvaluation = kffRmiService.saveEvaluation(evaluationRequest);
			bre.setData(saveEvaluation);
		} catch (RestServiceException e) {
			logger.error("EvaluationController saveEvaluation:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("EvaluationController saveEvaluation:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 发表评测 用户自建评测模型
	 * 
	 * @param devaluationModelRequest
	 * @return
	 */
	@RequestMapping(value = "/saveEvaluationModel", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveEvaluationModel(HttpServletRequest request, HttpServletResponse response, DevaluationModelRequest devaluationModelRequest,
			String token) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			Integer userId = getUserIdByToken(token);
			devaluationModelRequest.setCreateUserId(userId);
			kffRmiService.saveEvaluationModel(devaluationModelRequest);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("EvaluationController saveEvaluationModel:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("EvaluationController saveEvaluationModel:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 评测项目评分细节
	 * 
	 * @param devaluationModelRequest
	 * @return
	 */
	@RequestMapping(value = "/searchEvaluationModel", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity searchEvaluationModel(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		// 根据评测模型表查询有效启动的评测模板
		List<DevaluationModelDetail> sysEvuationModel = kffRmiService.findSysEvaModelDetailList();
		map.put("sysEvuationModel", sysEvuationModel);
		bre.setData(map);
		return bre;
	}
}
