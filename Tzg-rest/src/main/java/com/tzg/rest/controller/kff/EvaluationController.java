package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.entitys.kff.devaluationModel.DevaluationModelRequest;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.entitys.kff.evaluation.EvaluationRequest;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value="KFFEvaluationController")
@RequestMapping("/kff/evaluation")
public class EvaluationController extends BaseController {
	private static Logger log = Logger.getLogger(EvaluationController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
	
	
	/**
	* @Title: saveEvaluation  TODO 增加表来存储专业评测并定义接口入参格式
	* @Description: 发表评测
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/saveEvaluation",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity saveEvaluation(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			EvaluationRequest evaluationRequest = getParamMapFromRequestPolicy(request, EvaluationRequest.class);
			String token = evaluationRequest.getToken();			
			Integer userId = getUserIdByToken(token);
			evaluationRequest.setCreateUserId(userId);
			kffRmiService.saveEvaluation(evaluationRequest);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("EvaluationController saveEvaluation:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("EvaluationController saveEvaluation:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	* @Title: saveEvaluationModel 
	* @Description: 发表评测模型
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/saveEvaluationModel",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity saveEvaluationModel(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			DevaluationModelRequest devaluationModelRequest = getParamMapFromRequestPolicy(request, DevaluationModelRequest.class);
			String token = devaluationModelRequest.getToken();			
			Integer userId = getUserIdByToken(token);
			devaluationModelRequest.setCreateUserId(userId);
			kffRmiService.saveEvaluationModel(devaluationModelRequest);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("EvaluationController saveEvaluationModel:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("EvaluationController saveEvaluationModel:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	* @Title: getSysEvaluationModel
	* @Description: 获取生效中的系统评测模型
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/getSysEvaluationModel",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity getSysEvaluationModel(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			
			
			DevaluationModelRequest devaluationModelRequest = getParamMapFromRequestPolicy(request, DevaluationModelRequest.class);
			String token = devaluationModelRequest.getToken();			
			Integer userId = getUserIdByToken(token);
			devaluationModelRequest.setCreateUserId(userId);
			List<DevaluationModelDetail> sysEvuationModel = kffRmiService.findSysEvaModelDetailList();
			map.put("modeDetailList", sysEvuationModel);
            bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("EvaluationController getSysEvaluationModel:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("EvaluationController getSysEvaluationModel:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
}


