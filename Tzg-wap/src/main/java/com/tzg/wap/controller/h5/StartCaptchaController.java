package com.tzg.wap.controller.h5;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.GeetestConfig;
import com.tzg.common.utils.GeetestLib;
import com.tzg.common.utils.SyseUtil;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;

@Controller(value = "StartCaptchaController")
@RequestMapping(value = "/kff/startCaptcha")
public class StartCaptchaController extends BaseController {
	private static Logger logger = Logger.getLogger(StartCaptchaController.class);
	@Autowired
	private RedisService redisService;

	@RequestMapping(value = "/sendStartCaptcha", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity sendStartCaptcha(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {

			Map<String, Object> resMap = new HashMap<String, Object>();
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());
			String resStr = "{}";
			String userid = "test";
			String userIp = getIpAddr(request);
			// 自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("user_id", userid); // 网站用户id
			param.put("client_type", "web"); // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", userIp); // 传输用户请求验证时所携带的IP

			// 进行验证预处理
			int gtServerStatus = gtSdk.preProcess(param);
			// 将服务器状态设置到session中
			request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
			// 将userid设置到session中
			request.getSession().setAttribute("userid", userid);
			redisService.put(gtSdk.gtServerStatusSessionKey, gtServerStatus + "", 3600);
			redisService.put("userid", userid, 3600);
			resStr = gtSdk.getResponseStr();
			System.out.println(JSON.toJSON(resStr));

			resMap.put("resStr", resStr);
			bre.setData(resMap);
		} catch (RestServiceException e) {
			logger.error("StartCaptchaController sendStartCaptcha：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("StartCaptchaController sendStartCaptcha：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/checkStartCaptcha", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity checkStartCaptcha(HttpServletRequest request, HttpServletResponse response) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(), GeetestConfig.isnewfailback());

			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
			HashMap<String, Object> map = new HashMap<String, Object>();
			// 从session中获取gt-server状态
			// int gt_server_status_code = (Integer)
			// request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
			// 从session中获取userid
			// String userid = (String) request.getSession().getAttribute("userid");
			String gt_server_status_codest = redisService.get(gtSdk.gtServerStatusSessionKey);
			int gt_server_status_code = Integer.valueOf(gt_server_status_codest);
			String userid = redisService.get("userid");
			String userIp = getIpAddr(request);
			// 自定义参数,可选择添加
			HashMap<String, String> param = new HashMap<String, String>();
			param.put("user_id", userid); // 网站用户id
			param.put("client_type", "web"); // web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
			param.put("ip_address", userIp); // 传输用户请求验证时所携带的IP

			int gtResult = 0;
			if (gt_server_status_code == 1) {
				// gt-server正常，向gt-server进行二次验证
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
				System.out.println(gtResult);
			} else {
				// gt-server非正常情况下，进行failback模式验证
				System.out.println("failback:use your own server captcha validate");
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
				System.out.println(gtResult);
			}

			if (gtResult == 1) {
				// 验证成功
				// PrintWriter out = response.getWriter();
				JSONObject data = new JSONObject();
				try {
					data.put("status", "success");
					data.put("version", gtSdk.getVersionInfo());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				map.put("data", data);

			} else {
				// 验证失败
				JSONObject data = new JSONObject();
				try {
					data.put("status", "fail");
					data.put("version", gtSdk.getVersionInfo());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				map.put("data", data);

			}
			bre.setData(map);
			SyseUtil.systemErrOutJson(bre);
		} catch (RestServiceException e) {
			logger.error("StartCaptchaController checkStartCaptcha：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("StartCaptchaController checkStartCaptcha：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}

	private static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
