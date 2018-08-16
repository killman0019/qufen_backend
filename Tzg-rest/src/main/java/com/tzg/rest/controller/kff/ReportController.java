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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.entitys.kff.reportmodel.ReportModel;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFReportRmiService;

/**
 * 
 * ClassName: ReportController  
 * Function: TODO   投诉相关接口
 * date: 2018年8月13日 下午4:52:09  
 * 
 * @author zhangdd 
 * @version  
 * @since JDK 1.7 
 *
 */
@Controller(value = "ReportController")
@RequestMapping(value = "/kff/report")
@SuppressWarnings("unused")
public class ReportController extends BaseController {
	private static Logger log = Logger.getLogger(ReportController.class);

	@Autowired
	private KFFReportRmiService kffReportRmiService;

	/**
	 * 
	 * TODO 获得投诉选项明细(传入参数token)
	 * @param request
	 * @return
	 * @author zhangdd
	 * @data 2018年8月13日
	 *
	 */
	@RequestMapping(value = "/getReportModelList", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getReportModelList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {

			List<ReportModel> getReportModelList = kffReportRmiService.getReportModelList();
			map.put("getReportModelList", getReportModelList);
			bre.setData(map);
			//System.err.println(JSON.toJSONString(getReportModelList));
		} catch (RestServiceException e) {
			logger.error("ReportModelController getReportModelList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ReportModelController getReportModelList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	/**
	 * 
	 * TODO 进行举报
	 * @param request
	 * @return
	 * @author zhangdd
	 * @data 2018年8月13日
	 *
	 */
	@RequestMapping(value = "/saveReport", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity saveReport(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject params = getParamMapFromRequestPolicy(request);
			String token = (String) params.get("token");

			Integer type = (Integer) params.get("type");
			Integer contentId = (Integer) params.get("contentId");
			Integer reportModelId = (Integer) params.get("reportModelId");

			if (null == contentId) {
				throw new RestServiceException("投诉的内容为空");
			}
			if (null == reportModelId) {
				throw new RestServiceException("请选择投诉原因");
			}

			Integer userId = getUserIdByToken(token);

			if (null == userId) {
				throw new RestServiceException("请进行登录");
			}
			kffReportRmiService.saveReport(userId, reportModelId, contentId, type);

			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("ReportModelController getReportModelList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("ReportModelController getReportModelList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
