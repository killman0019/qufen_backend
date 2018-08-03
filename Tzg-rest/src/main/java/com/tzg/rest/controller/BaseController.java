package com.tzg.rest.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.Assert;
import com.tzg.common.utils.IdCardUtil;
import com.tzg.common.utils.IoUtils;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.loginaccount.Loginaccount;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.HttpRequestDeviceUtils;
import com.tzg.rest.utils.PolicyUtil;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.SystemParamRmiService;

public abstract class BaseController {
	
	protected static Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private SystemParamRmiService systemParamRmiService;
	@Autowired
	private RedisService redisService;
	
	private String basePath;
    
    @ModelAttribute()
    public void init(ModelMap model) {
		model.addAttribute("basePath",getBasePath());
		model.addAttribute("staticPath", getBasePath() + "/assets");
    }
    
	public String getBasePath() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		this.basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		return basePath;
	}
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	
	/**
	 * 向客户端输出指定格式的文档
	 * @param response 		响应
	 * @param text   		要发的内容
	 * @param contentType   发送的格式
	 */
	public void doPrint(HttpServletResponse response, String text,
			String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (IOException e) {
			System.out.println("向客户端输出时发生异常 : " + e.getMessage());
		}
	}

	/**
	 * 直接输出二进制
	 */
	public void responseBin(HttpServletResponse response, byte[] buf, int off, int len) {
		try {
			response.setContentType("application/octet-stream");
			response.getOutputStream().write(buf, off, len);
		} catch (IOException e) {
			System.out.println("向客户端输出时发生异常 : " + e.getMessage());
		}
	}
	
	/**
	 * 直接输出json
	 */
	public void responseJson(HttpServletResponse response, String json) {
		doPrint(response, json,
				"application/json;charset=UTF-8");
	}
	/**
	 * 直接输出纯XML
	 */
	public void responseXml(HttpServletResponse response, Element res) {
		String text = res.asXML();
		doPrint(response, text,
				"text/xml;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	public void responseHtml(HttpServletResponse response, String text) {
		doPrint(response, text,
				"text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯字符串.
	 */
	public void responseText(HttpServletResponse response, String text) {
		doPrint(response, text,
				"application/plain;charset=UTF-8");
	}
	
	public void constructBaseResponseEntity(HttpServletRequest request,HttpServletResponse response,BaseResponseEntity entity){
		entity.setCode(0);
		entity.setStatus(200);
		entity.setMsg("Success");
		entity.setReason("");
		entity.setFromuri(request.getRequestURI());
		response.setStatus(200);
	}

	public void throwRestException(BaseResponseEntity bre,String msg,RestErrorCode re)  {
		bre.setCode(re.getValue());
		bre.setMsg(msg);
		bre.setReason(re.getErrorReason());
		throw new RestServiceException(msg,re.getValue());
	}
	
	 public static String convertStream2Json(InputStream inputStream) {
	        String jsonStr = "";
	        // ByteArrayOutputStream相当于内存输出流
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        // 将输入流转移到内存输出流中
	        try{
	            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1){
	                out.write(buffer, 0, len);
	            }
	            // 将内存流转换为字符串
	            jsonStr = new String(out.toByteArray(),"utf-8");
	        }catch (IOException e){
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return jsonStr;
	    }
	 
	 	protected String getJsonString( InputStream inputStream) throws Exception {   
	        //对应的字符编码转换  
	        Reader reader = new InputStreamReader(inputStream, "UTF-8");  
	        BufferedReader bufferedReader = new BufferedReader(reader);  
	        String str = null;  
	        StringBuffer sb = new StringBuffer();  
	        while ((str = bufferedReader.readLine()) != null) {  
	            sb.append(str);  
	        }  
	        reader.close();  
	        return sb.toString();  
	    }  
	 
	 	public JSONObject getParamMapFromRequest(HttpServletRequest request) throws Exception {
	 		System.out.println("--------------- request url----------------"+request.getRequestURI());
			InputStream is = request.getInputStream();
			String paramString = IoUtils.convertStream2String(is);
			JSONObject map = JSON.parseObject(paramString);
			if(null == map) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			return map;
		}
	 	
	 	public <T> T getParamMapFromRequest(HttpServletRequest request,Class<T> clazz) throws IOException {
	 		//System.out.println("--------------- 追踪线上问题2----------------"+request.getRequestURI());
			InputStream is = request.getInputStream();
			if (is!=null) {
				T map = JSON.parseObject(IoUtils.convertStream2String(is), clazz);
				if(null == map) {
					throw new RestServiceException(RestErrorCode.MISSING_ARGS);
				}
				return map;
			}
			return null;
		}

		public JSONObject getParamMapFromRequestPolicy(HttpServletRequest request) throws Exception {
	 		System.out.println("--------------- request url----------------"+request.getRequestURI());
	 		String policy = request.getParameter("policy");
			if(StringUtils.isBlank(policy)){
				throw new RestServiceException(RestErrorCode.MISSING_POLICY);
			}
			
			try{
				
				policy = PolicyUtil.decryptPolicy(policy);

			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.DECRYPT_POLICY_ERROR);
			}
			JSONObject map;
			try{
				map = JSON.parseObject(policy);
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.PARSE_JSON_ERROR);
				
			}
			if(null == map) {
				throw new RestServiceException(RestErrorCode.JSON_BLANK_ERROR);
			}
	 		return map;
		}
	 	
		public <T> T getParamMapFromRequestPolicy(HttpServletRequest request,Class<T> clazz) throws IOException {
	 		//System.out.println("--------------- 追踪线上问题2----------------"+request.getRequestURI());
			
			String policy = request.getParameter("policy");
			if(StringUtils.isBlank(policy)){
				throw new RestServiceException(RestErrorCode.MISSING_POLICY);
			}
			
			try{
				
				policy = PolicyUtil.decryptPolicy(policy);

			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.DECRYPT_POLICY_ERROR);
			}
			T resultmap;
			try{
				resultmap = JSON.parseObject(policy, clazz);
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.PARSE_JSON_ERROR);
				
			}
			if(null == resultmap) {
				throw new RestServiceException(RestErrorCode.JSON_BLANK_ERROR);
			}

			return resultmap;
			
		}
		
		/** 
		* @Title: getParamJsonFromRequestPolicy 
		* @Description: TODO <解析policy参数>
		* @author linj <方法创建作者>
		* @create 上午9:28:27
		* @param @param request
		* @param @return
		* @param @throws IOException <参数说明>
		* @return JSONObject 
		* @throws 
		* @update 上午9:28:27
		* @updator <修改人 修改后更新修改时间，不同人修改再添加>
		* @updateContext <修改内容>
		*/
		public JSONObject getParamJsonFromRequestPolicy(HttpServletRequest request) throws IOException {
			JSONObject parsePolicy = new JSONObject();
			String policy = request.getParameter("policy");
			if(StringUtils.isBlank(policy)){
				throw new RestServiceException(RestErrorCode.MISSING_POLICY);
			}
			try{
				policy = PolicyUtil.decryptPolicy(policy);
				if(StringUtil.isBlank(policy)) {
					throw new RestServiceException(RestErrorCode.MISSING_POLICY);
				}
				parsePolicy = JSONObject.parseObject(policy);
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.DECRYPT_POLICY_ERROR);
			}
			return parsePolicy;
		}
		
	 	
	
		public String getTerminalDeviceType(HttpServletRequest request) {
			String deviceType = HttpRequestDeviceUtils.getDeviceType(request);
			String tType = "android";
			if(null != deviceType && deviceType.equals("Android")) {
				tType = "android";
			}
			if(null != deviceType && (deviceType.equals("iPhone") || deviceType.equals("iPad"))) {
				tType = "ios";
			}
			return tType;
		}
		
		public Integer getTerminalType(HttpServletRequest request) {
			String deviceType = HttpRequestDeviceUtils.getAppDeviceType(request);
			//默认ios
			Integer tType = 3;
			if(deviceType!= null && deviceType.equalsIgnoreCase("Android")){
				tType = 4;
			}
			if(deviceType!= null && (deviceType.equalsIgnoreCase("iPhone")|| deviceType.equalsIgnoreCase("iPad"))){
				tType = 3;
			}
			return tType;
		}

		public BaseResponseEntity resResult(RestServiceException e) {
		    return this.resResult(e.getErrorCode(), e.getMessage());
		}
		
		public BaseResponseEntity resResult(RestErrorCode error) {
		    return this.resResult(error.getValue(), error.getErrorReason());
		}
		
		public BaseResponseEntity resResult(RestErrorCode error,String reason) {
		    return this.resResult(error.getValue(), error.getErrorReason(),reason);
		}
		
		public BaseResponseEntity resResult(String errorMsg) {
		    return this.resResult(-1, errorMsg);
		}
		
		public BaseResponseEntity resResult(Integer errorCode, String errorMsg) {
			BaseResponseEntity ent = new BaseResponseEntity();
			ent.setCode(errorCode);
			ent.setMsg(errorMsg);
			return ent;
		}	
		
		public BaseResponseEntity resResult(Integer errorCode, String errorMsg,String reason) {
			BaseResponseEntity ent = new BaseResponseEntity();
			ent.setCode(errorCode);
			ent.setMsg(errorMsg);
			ent.setReason(reason);
			return ent;
		}	
		
	
	
	
    /**
     * 根据传入token 校验 并返回用户ID
     * @param token
     * @return
     */
	public Integer getUserIdByToken(String token){
		
		if (StringUtils.isBlank(token)) {
	        	throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
	    }		         
	    Integer userId = null;
		try{
				userId = AccountTokenUtil.decodeAccountToken(token);
		}catch(Exception e){
				logger.error("submitSuggest decodeAccountToken error:{}",e);
				throw new RestServiceException(RestErrorCode.PARSE_TOKEN_ERROR);
		}
		if(userId == null){
	    		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
	    }
		
		return userId;
			
		}

}
