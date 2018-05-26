package com.tzg.rest.controller.kff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.AccountTokenUtil;
import com.tzg.common.utils.EnumConstant.SmsBuss;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.common.utils.rest.Base64Util;
import com.tzg.common.utils.rest.RestConstants;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.collect.CollectPostResponse;
import com.tzg.entitys.kff.dareas.Dareas;
import com.tzg.entitys.kff.follow.FollowResponse;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.DateUtil;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.rmi.service.SystemParamRmiService;

@Controller(value="KFFUserController")
@RequestMapping("/kff/user")
public class UserController extends BaseController {
	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
    @Autowired
    private SystemParamRmiService  systemParamRmiService;
	@Autowired
	private RedisService redisService;
	/**
	* 
	* @Title: register 
	* @Description: 注册  
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/register",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity register(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			RegisterRequest registerRequest = getParamMapFromRequestPolicy(request, RegisterRequest.class);
			validateForm(registerRequest); 
			
			KFFUser user = kffRmiService.registerRest(registerRequest);
			// 释放验证码
			this.redisService.del(new StringBuffer(RestConstants.key_rest).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
		
			// 全局公用的用户登录信息
			String key =  SHAUtil.encode(user.getUserId() + TzgConstant.LOGIN_SIGN_KEY);
			redisService.put(key, key, 60 * 60 * 1);  //存放一个小时			
			// 生成account token
            String token = AccountTokenUtil.getAccountToken(user.getUserId());
            // 根据用户id 获取用户类型跟推荐人
            Integer userid = AccountTokenUtil.decodeAccountToken(token);
            kffRmiService.registerAward(userid);
            map.put("token", token);
            
		} catch (RestServiceException e) {
			logger.error("RegisterController register：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController register：", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 注册协议地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/register/protocol",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity registerProtocol(HttpServletRequest request){
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();

		try {
			SystemParam systemParam = systemParamRmiService.findByCode("registration_protocol_url");
			if(null != systemParam){
				resMap.put("protocolUrl", systemParam.getVcParamValue());		
			}
			bre.setData(resMap);
		} catch (RestServiceException e) {
			logger.error("RegisterController method registerProtocol reason：{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController method registerProtocol reason：{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/**
	 * 
	* @Title: validPhone 
	* @Description: 验证手机是否存在  
	* @param @param phoneNumber
	* @param @param request
	* @param @param response
	* @param @return    
	* @return BaseResponseEntity
	* @see    
	* @throws
	 */
	@RequestMapping(value="/register/phoneAvailable",method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public BaseResponseEntity phoneAvailable(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			Map<String, Object> resMap = new HashMap<String, Object>();
			JSONObject map = getParamMapFromRequestPolicy(request);
			String phone = (String)map.get("phone");
			if(StringUtils.isBlank(phone)) {
				new RestServiceException(RestErrorCode.PHONE_NULL);
			} else {
				String phonefmt = RegexUtil.PHONEREGEX;;
				if(!phone.matches(phonefmt)){
					new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
				}
			}
			if(kffRmiService.verifyLoginaccount("mobile", phone)) {
				resMap.put("isRegister", 1);
			} else {
				resMap.put("isRegister", 0);
			}
			bre.setData(resMap);
		} catch (RestServiceException e) {
			logger.error("RegisterController phoneAvailable reason:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("RegisterController phoneAvailable, reason：{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
	/**
	 * 用户登录
	 * @param request
	 * @return
	 */
	 @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	    @ResponseBody
	    public BaseResponseEntity login(HttpServletRequest request) {
	        BaseResponseEntity bre = new BaseResponseEntity();
	        HashMap<String, Object> map = new HashMap<String, Object>();
	        try {
	            JSONObject params = getParamMapFromRequestPolicy(request);
	            String loginName = (String) params.get("loginName");
	            String password = (String) params.get("password");
	            
	            if (StringUtils.isBlank(loginName)) {
	                throw new RestServiceException(RestErrorCode.LOGIN_NAME_NULL);
	            }
	            if (StringUtils.isBlank(password)) {
	                throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
	            }

	            KFFUser loginaccount = null;
	            try {
	                loginaccount = kffRmiService.login(loginName, password);
	            } catch (RestServiceException e) {
	            	return this.resResult(e.getErrorCode(), e.getMessage());
	            }catch(Exception e){
	            	return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
	            }

	            if (null == loginaccount) {
	                throw new RestServiceException(RestErrorCode.LOGIN_NAME_OR_PASSWORD_INCORRECT);
	            }
	            
	            map.put("user", this.formatLoginaccount(loginaccount));	           
	         
	            //生成account token
	            String token = AccountTokenUtil.getAccountToken(loginaccount.getUserId());
	            map.put("token", token);
	            
  
	            bre.setData(map);
	        } catch (RestServiceException e) {
	            logger.warn("login warn:{}", e);
	            return this.resResult(e.getErrorCode(), e.getMessage());
	        } catch (Exception e) {
	            logger.error("login error:{}", e);
	            return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
	        }
	        return bre;
	    }	 
	
	  /**
		 * 忘记密码
		 * TODO
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/forgetPassword",method = {RequestMethod.POST,RequestMethod.GET})
		@ResponseBody
		public BaseResponseEntity forgetPassword(HttpServletRequest request, HttpServletResponse response) {
			BaseResponseEntity bre = new BaseResponseEntity();
	        try {
	        	JSONObject params = getParamMapFromRequestPolicy(request);
	        	String phoneNumber = (String)params.get("phone");
	        	String dynamicVerifyCode = (String)params.get("code");
				String password = (String)params.get("password");

				if(StringUtils.isBlank(phoneNumber)) {
					throw new RestServiceException(RestErrorCode.PHONE_NULL);
				} else {
					String phonefmt = RegexUtil.PHONEREGEX;
					if(!phoneNumber.matches(phonefmt)){
						throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
					}
				}
				
				if(StringUtils.isBlank(dynamicVerifyCode)) {
					throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_NULL);
				}
				
				KFFUser account = kffRmiService.findUserByPhoneNumber(phoneNumber);
				if(null == account) {
					throw new RestServiceException(RestErrorCode.MOBILE_NOT_EXIST);
				}
				if(StringUtils.isBlank(password)){
					throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
				}				
				String pwdFmt = "^.{8,20}$";
				if(!password.matches(pwdFmt)){
					throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
				}
			     //效验验证码
	            String code = this.redisService.get(new StringBuffer(RestConstants.key_rest)
	                .append(SmsBuss.忘记密码效验码.getBus()).append(phoneNumber).toString());
	            //释放验证码  漏洞排查 防止不停输入错误验证码来暴库
	            this.redisService.del(new StringBuffer(RestConstants.key_rest)
	                .append(SmsBuss.忘记密码效验码.getBus()).append(phoneNumber).toString());
	            if(StringUtils.isEmpty(code)) {
	                throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_INVALID);
	            }
	            if (!dynamicVerifyCode.equalsIgnoreCase((String)code)) {
	                throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
	            }
				
				//更新密码
				KFFUser accounts = new KFFUser();
				accounts.setUserId(account.getUserId());
				accounts.setPassword(SHAUtil.encode(password));
				accounts.setUpdateTime(new Date());
				kffRmiService.updateUser(accounts);
			
				
			} catch (RestServiceException e) {
				logger.error("UserController forgetPassword:{}", e);
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				logger.error("UserController forgetPassword:{}", e);
				return this.resResult(RestErrorCode.SYS_ERROR);
			}
			return bre;
		}
	 
	 
		/**
		 * 上传用户头像 
		 * @param file
		 * @return
		 */
		@RequestMapping(value = "/uploadUserIcon",method = {RequestMethod.POST,RequestMethod.GET},
				produces = "text/html; charset=utf-8",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		public BaseResponseEntity uploadUserIcon(
				HttpServletRequest request, HttpServletResponse response
				) {
			BaseResponseEntity bre = new BaseResponseEntity();
			Map<String, Object> resMap = new HashMap<String, Object>();
			try {
				JSONObject params = getParamMapFromRequestPolicy(request);
	        	String token = (String)params.get("token");
	        	String imgdata = (String)params.get("imgdata");
	        	if(StringUtils.isBlank(token)){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
	        	}
	        	if(StringUtils.isBlank(imgdata)){
	        		throw new RestServiceException(RestErrorCode.USER_ICON_DATA_EMPTY);
	        	}
	        	Integer userId = AccountTokenUtil.decodeAccountToken(token);
	        	if(userId == null){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
	        	}
	        	SystemParam systemParam = systemParamRmiService.findByCode("upload_local_path");
	        	
	        	String path = systemParam.getVcParamValue() + "avatars/" + DateUtil.getCurrentYearMonth()+"/";
	        	String file = path + userId+".jpg";
	        	Base64Util.decoderBase64File(imgdata,file);
	        	
	        	//更新用户头像url
	        	systemParam = systemParamRmiService.findByCode("upload_file_path");
	        	path = systemParam.getVcParamValue()+ "/avatars/" + DateUtil.getCurrentYearMonth()+"/";
	        	file = path + userId+".jpg";
	        	KFFUser account = new KFFUser();
	        	account.setUserId(userId);
	        	account.setUpdateTime(new Date());
	        	account.setIcon(file);
	        	kffRmiService.updateUser(account);
				
	        	
			} catch (RestServiceException e) {
				logger.error("error in uploadUserIcon method:{}", e);
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				logger.error("uncatched error in uploadUserIcon method:{}", e);
				e.printStackTrace();
				return this.resResult(RestErrorCode.SYS_ERROR);
			}
			return bre;
		}
	 
		/*
		 
		@RequestMapping(value = "/uploadUserIcon",method = {RequestMethod.POST,RequestMethod.GET},
				produces = "text/html; charset=utf-8",consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
		public BaseResponseEntity uploadUserIcon(
				 @RequestParam("uploadfile") MultipartFile file
				) {
			BaseResponseEntity bre = new BaseResponseEntity();
			Map<String, Object> resMap = new HashMap<String, Object>();
			try {
				if (file.isEmpty()) {
					throw new Exception("未选择需要上传的图标！");
				}
				
			} catch (RestServiceException e) {
				logger.error("error in uploadUserIcon method:{}", e);
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				logger.error("uncatched error in uploadUserIcon method:{}", e);
				e.printStackTrace();
				return this.resResult(RestErrorCode.SYS_ERROR);
			}
			return bre;
		}
		 */
		
		/**
		 *更新用户信息 修改用户昵称或者头像
		 * @param userName
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/updateUserInfo",method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody BaseResponseEntity updateUserInfo(HttpServletRequest request) {
			BaseResponseEntity bre = new BaseResponseEntity();
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				
				JSONObject jsonObject = getParamMapFromRequestPolicy(request);

				String token = (String)jsonObject.get("token");
				String userName = (String)jsonObject.get("userName");
				Integer sex = (Integer)jsonObject.get("sex");
				String userSignature = (String)jsonObject.get("userSignature");
				String areaName = (String)jsonObject.get("areaName");
				if(StringUtils.isBlank(token)){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
	        	}
				if(sex != null && sex != 1 && sex != 2){
					throw new RestServiceException(RestErrorCode.USER_SEX_WRONG);
				}
				
				Integer userId = null;
				try{
					userId = AccountTokenUtil.decodeAccountToken(token);
				}catch(Exception e){
					logger.error("updateUserInfo decodeAccountToken error:{}",e);
					return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
				}
				if(userId == null){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
	        	}

				KFFUser loginaccount = kffRmiService.findUserById(userId);
				
				if(loginaccount == null){
					return this.resResult(RestErrorCode.USER_NOT_EXIST);
				}
				
				KFFUser account = new KFFUser();
				account.setUserId(loginaccount.getUserId());
				if(StringUtils.isNotBlank(userName)){
					account.setUserName(userName);
				}
				if(StringUtils.isNotBlank(userSignature)){
					account.setUserSignature(userSignature);
				}
				if(StringUtils.isNotBlank(areaName)){
					account.setAreaName(areaName);
				}
				if(sex != null){
					account.setSex(sex);
				}
				account.setUpdateTime(new Date());
				kffRmiService.updateUser(account);
				
				bre.setData(map);
			} catch (RestServiceException e) {
				logger.error("UserController updateUserInfo error,reason：{}", e);
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				logger.error("UserController updateUserInfo error,reason:{}", e);
				return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
			}
			return bre;
		}
		
		/**
		 * 我的关注列表，包括 话题和用户，分2次请求。
		 * @param request
		 * @return
		 */
		 @RequestMapping(value = "/myFollowList", method = {RequestMethod.POST,RequestMethod.GET})
		    @ResponseBody
		    public BaseResponseEntity myFollowList(HttpServletRequest request) {
		        BaseResponseEntity bre = new BaseResponseEntity();
		        HashMap<String, Object> map = new HashMap<String, Object>();
		        try {
		            JSONObject params = getParamMapFromRequestPolicy(request);
		            String token = (String) params.get("token");
		            Integer pageIndex = (Integer) params.get("pageIndex") == null ? 1:(Integer) params.get("pageIndex");
		            Integer pageSize = (Integer) params.get("pageSize") == null ? 10:(Integer) params.get("pageSize");
		            //关注类型：2-关注话题；3-关注用户
		            Integer followType = (Integer) params.get("followType") == null ? 2:(Integer) params.get("followType");

		            
		            if (StringUtils.isBlank(token)) {
		            	throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		            }		         
		            Integer userId = null;
					try{
						userId = AccountTokenUtil.decodeAccountToken(token);
					}catch(Exception e){
						logger.error("myFollowList decodeAccountToken error:{}",e);
						return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
					}
					if(userId == null){
		        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		        	}

					KFFUser loginaccount = kffRmiService.findUserById(userId);
					
					if(loginaccount == null){
						return this.resResult(RestErrorCode.USER_NOT_EXIST);
					}
					
		            PaginationQuery query = new PaginationQuery();
		            query.addQueryData("followUserId", userId +"");
		            query.addQueryData("followType", followType +"");
		            
		            query.setPageIndex(pageIndex);
		            query.setRowsPerPage(pageSize);
		            PageResult<FollowResponse> result = kffRmiService.findPageMyFollow(query);
		            
		            map.put("myFollows", result);
	  
		            bre.setData(map);
		        } catch (RestServiceException e) {
		            logger.warn("myFollowList warn:{}", e);
		            return this.resResult(e.getErrorCode(), e.getMessage());
		        } catch (Exception e) {
		            logger.error("myFollowList error:{}", e);
		            return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		        }
		        return bre;
		    }	 
		
		
		/**
		 * 我的收藏列表，当前用户收藏的评测、讨论、文章，按照时间排序（倒序）
		 * @param request
		 * @return
		 */
		 @RequestMapping(value = "/myCollectList", method = {RequestMethod.POST,RequestMethod.GET})
		    @ResponseBody
		    public BaseResponseEntity myCollectList(HttpServletRequest request) {
		        BaseResponseEntity bre = new BaseResponseEntity();
		        HashMap<String, Object> map = new HashMap<String, Object>();
		        try {
		            JSONObject params = getParamMapFromRequestPolicy(request);
		            String token = (String) params.get("token");
		            Integer pageIndex = (Integer) params.get("pageIndex") == null ? 1:(Integer) params.get("pageIndex");
		            Integer pageSize = (Integer) params.get("pageSize") == null ? 10:(Integer) params.get("pageSize");
		            		           	         
		            Integer userId = getUserIdByToken(token);
					KFFUser loginaccount = kffRmiService.findUserById(userId);
					
					if(loginaccount == null){
						return this.resResult(RestErrorCode.USER_NOT_EXIST);
					}
					
		            PaginationQuery query = new PaginationQuery();
		            query.addQueryData("collectUserId", userId +"");
		            query.setPageIndex(pageIndex);
		            query.setRowsPerPage(pageSize);
		            PageResult<CollectPostResponse> result = kffRmiService.findPageMyCollectRecords(query);
		            
		            map.put("myTokenRecords", result);
	  
		            bre.setData(map);
		        } catch (RestServiceException e) {
		            logger.warn("myCollectList warn:{}", e);
		            return this.resResult(e.getErrorCode(), e.getMessage());
		        } catch (Exception e) {
		            logger.error("myCollectList error:{}", e);
		            return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		        }
		        return bre;
		    }	 
		
		
		/**
		 * 我的资产明细列表，按照时间排序（倒序） (收支明细)
		 * @param request
		 * @return
		 */
		 @RequestMapping(value = "/myTokenRecordsList", method = {RequestMethod.POST,RequestMethod.GET})
		    @ResponseBody
		    public BaseResponseEntity myTokenRecordsList(HttpServletRequest request) {
		        BaseResponseEntity bre = new BaseResponseEntity();
		        HashMap<String, Object> map = new HashMap<String, Object>();
		        try {
		            JSONObject params = getParamMapFromRequestPolicy(request);
		            String token = (String) params.get("token");
		            Integer pageIndex = (Integer) params.get("pageIndex") == null ? 1:(Integer) params.get("pageIndex");
		            Integer pageSize = (Integer) params.get("pageSize") == null ? 10:(Integer) params.get("pageSize");
		            
		            if (StringUtils.isBlank(token)) {
		            	throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		            }		         
		            Integer userId = null;
					try{
						userId = AccountTokenUtil.decodeAccountToken(token);
					}catch(Exception e){
						logger.error("updateUserInfo decodeAccountToken error:{}",e);
						return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
					}
					if(userId == null){
		        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		        	}

					KFFUser loginaccount = kffRmiService.findUserById(userId);
					
					if(loginaccount == null){
						return this.resResult(RestErrorCode.USER_NOT_EXIST);
					}
					
		            PaginationQuery query = new PaginationQuery();
		            query.addQueryData("userId", userId +"");
		            query.setPageIndex(pageIndex);
		            query.setRowsPerPage(pageSize);
		            PageResult<Tokenrecords> result = kffRmiService.findPageMyTokenRecords(query);
		            
		            map.put("myTokenRecords", result);
	  
		            bre.setData(map);
		        } catch (RestServiceException e) {
		            logger.warn("myTokenRecordsList warn:{}", e);
		            return this.resResult(e.getErrorCode(), e.getMessage());
		        } catch (Exception e) {
		            logger.error("myTokenRecordsList error:{}", e);
		            return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		        }
		        return bre;
		    }	 
		
		
		/**
		 *获取H5相关页面URL的接口
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/getH5URLs",method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody BaseResponseEntity getH5URLs(HttpServletRequest request) {
			BaseResponseEntity bre = new BaseResponseEntity();
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				
				JSONObject jsonObject = getParamMapFromRequestPolicy(request);

				String token = (String)jsonObject.get("token");
				if(StringUtils.isBlank(token)){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
	        	}				
				Integer userId = null;
				try{
					userId = AccountTokenUtil.decodeAccountToken(token);
				}catch(Exception e){
					logger.error("getAreaList decodeAccountToken error:{}",e);
					return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
				}
				if(userId == null){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
	        	}

	        	SystemParam systemParam = systemParamRmiService.findByCode("kff_invite_user_url");
				
	        	map.put("inviteUserUrl", systemParam.getVcParamValue());
	        	
	        	systemParam = systemParamRmiService.findByCode("kff_about_us_url");
	        	map.put("aboutUsUrl", systemParam.getVcParamValue());
				
				bre.setData(map);
			} catch (RestServiceException e) {
				logger.error("UserController getH5URLs error,reason：{}", e);
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				logger.error("UserController getH5URLs error,reason:{}", e);
				return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
			}
			return bre;
		}
		
		/**
		 *获取省市区域列表
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping(value="/getAreaList",method = {RequestMethod.POST,RequestMethod.GET})
		public @ResponseBody BaseResponseEntity getAreaList(HttpServletRequest request) {
			BaseResponseEntity bre = new BaseResponseEntity();
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				
				JSONObject jsonObject = getParamMapFromRequestPolicy(request);

				String token = (String)jsonObject.get("token");
				String areacode = (String)jsonObject.get("code");				
				if(StringUtils.isBlank(token)){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
	        	}				
				Integer userId = null;
				try{
					userId = AccountTokenUtil.decodeAccountToken(token);
				}catch(Exception e){
					logger.error("getAreaList decodeAccountToken error:{}",e);
					return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
				}
				if(userId == null){
	        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
	        	}

				List<Dareas> areas = kffRmiService.getAreaListByCode(areacode);				
				map.put("areas", areas);
				
				bre.setData(map);
			} catch (RestServiceException e) {
				logger.error("UserController getAreaList error,reason：{}", e);
				return this.resResult(e.getErrorCode(), e.getMessage());
			} catch (Exception e) {
				logger.error("UserController getAreaList error,reason:{}", e);
				return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
			}
			return bre;
		}
		
		/**
		 * 
		* @Title: validateForm 
		* @Description: 表单验证
		* @param @param registerRequest
		* @param @param bre    
		* @return void
		* @see    
		* @throws
		 */
		private void validateForm(RegisterRequest registerRequest) throws Exception {
			if(StringUtils.isBlank(registerRequest.getPhoneNumber())) {
				throw new RestServiceException(RestErrorCode.PHONE_NULL);
			} else {
				//String phonefmt = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
				//统一用手机号白名单验证正则表达式
				String phonefmt = RegexUtil.PHONEREGEX;
				if(!registerRequest.getPhoneNumber().matches(phonefmt)){
					throw new RestServiceException(RestErrorCode.PHONE_FORMAT_ERROR);
				}
			}
			if(kffRmiService.verifyLoginaccount("mobile", registerRequest.getPhoneNumber())) {
				throw new RestServiceException(RestErrorCode.PHONE_ALREADY_EXIST);
			}
			
			if(StringUtils.isBlank(registerRequest.getPassword())){
				throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
			} else {
				//String pwdFmt = "^((?=.*?\\d)(?=.*?[A-Za-z])|(?=.*?\\d)(?=.*?[!@#$%^&])|(?=.*?[A-Za-z])(?=.*?[!@#$%^&]))[\\dA-Za-z!@#$%^&]{8,20}$";   //由字母、数字组成，8-20位
				String pwdFmt = "^.{8,20}$";
				if(!registerRequest.getPassword().matches(pwdFmt)){
					throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);
				}
			}
			if(StringUtils.isBlank(registerRequest.getDynamicVerifyCode())) {
				//throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
			} else {
				String code = this.redisService.get(new StringBuffer(RestConstants.key_rest).append(SmsBuss.注册效验码.getBus()).append(registerRequest.getPhoneNumber()).toString());
				if (!registerRequest.getDynamicVerifyCode().equalsIgnoreCase(code)) {
					throw new RestServiceException(RestErrorCode.DYNAMIC_VERIFY_CODE_ERROR);
			    }
			}
		}
		
		private KFFUser formatLoginaccount(KFFUser loginaccount){
			loginaccount.setPassword("true");
			loginaccount.setPayPassword("true");
			return loginaccount;
		}
		
		
		
		/**
		 * 我的资产明细列表，按照时间排序
		 * @param request (传入一个userId)
		 * @return
		 */
		 @RequestMapping(value = "/myTokenRecords", method = {RequestMethod.POST,RequestMethod.GET})
		    @ResponseBody
		    public BaseResponseEntity myTokenRecords(HttpServletRequest request) {
		        BaseResponseEntity bre = new BaseResponseEntity();
		        HashMap<String, Object> map = new HashMap<String, Object>();
		        try {
		            JSONObject params = getParamMapFromRequestPolicy(request);
		            String token = (String) params.get("token");
		            
		            if (StringUtils.isBlank(token)) {
		            	throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
		            }
		            Integer userId = null;
					try{
						userId = AccountTokenUtil.decodeAccountToken(token);
					}catch(Exception e){
						logger.error("updateUserInfo decodeAccountToken error:{}",e);
						return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
					}
					if(userId == null){
		        		throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
		        	}

					KFFUser loginaccount = kffRmiService.findUserById(userId);
					
					if(loginaccount == null){
						return this.resResult(RestErrorCode.USER_NOT_EXIST);
					}
					 List<CoinProperty> result = kffRmiService.findCoinPropertyByUserId(userId);
					
						map.put("myTokenRecords", result);
	  
		            bre.setData(map);
		        } catch (RestServiceException e) {
		            logger.warn("myTokenRecordsList warn:{}", e);
		            return this.resResult(e.getErrorCode(), e.getMessage());
		        } catch (Exception e) {
		            logger.error("myTokenRecordsList error:{}", e);
		            return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		        }
		        return bre;
		    }
		 /**
		  * 我的解锁token的操作接口
		  * @param request
		  * @return (必须传入参数为, token跟要解锁的token数量 coinUnlock(参数名))
		  * 无需分页
		  */
		 @RequestMapping(value = "/myTokenDeBlocks", method = {RequestMethod.POST,RequestMethod.GET})
		 @ResponseBody
		 public BaseResponseEntity myTokenDeBlocks(HttpServletRequest request) {
			 BaseResponseEntity bre = new BaseResponseEntity();
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 try {
				 JSONObject params = getParamMapFromRequestPolicy(request);
				 String token = (String) params.get("token");
				 Double coinUnlock = (Double) params.get("coinUnlock");
				 if(coinUnlock == 0) {
					 throw new RestServiceException(RestErrorCode.MISSING_POLICY);
				 }
				 if (StringUtils.isBlank(token)) {
					 throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
				 }		         
				 Integer userId = null;
				 try{
					 userId = AccountTokenUtil.decodeAccountToken(token);
				 }catch(Exception e){
					 logger.error("updateUserInfo decodeAccountToken error:{}",e);
					 return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
				 }
				 if(userId == null){
					 throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
				 }
				 
				 KFFUser loginaccount = kffRmiService.findUserById(userId);
				 
				 if(loginaccount == null){
					 return this.resResult(RestErrorCode.USER_NOT_EXIST);
				 }
				 List<CoinProperty> result = kffRmiService.findCoinPropertyById(userId,coinUnlock);
				 	
				    map.put("myTokenRecords", result);
				 
				 bre.setData(map);
			 } catch (RestServiceException e) {
				 logger.warn("myTokenRecordsList warn:{}", e);
				 return this.resResult(e.getErrorCode(), e.getMessage());
			 } catch (Exception e) {
				 logger.error("myTokenRecordsList error:{}", e);
				 return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
			 }
			 return bre;
		 }

		 /**
		  * 我的取消解锁token的操作接口
		  * @param request  (必须传入参数为, token跟要取消解锁  token的解锁锁时间(coinUnLockTime),加锁状态coinUnlockType)
		  * @return
		  * 无需分页
		  */
		 @RequestMapping(value = "/myTokenUncoilBlocks", method = {RequestMethod.POST,RequestMethod.GET})
		 @ResponseBody
		 public BaseResponseEntity myTokenUncoilBlocks(HttpServletRequest request) {
			 BaseResponseEntity bre = new BaseResponseEntity();
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 try {
				 JSONObject params = getParamMapFromRequestPolicy(request);
				 String token = (String) params.get("token");
				 Date coinUnlock = (Date) params.get("coinUnLockTime");
				  Integer coinUnlockType = (Integer) params.get("coinUnlockType");
				 if(coinUnlock == null) {
					 throw new RestServiceException(RestErrorCode.MISSING_POLICY);
				 }
				 if (StringUtils.isBlank(token)) {
					 throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
				 }		         
				 if (coinUnlockType == 1) {
					 throw new RestServiceException(RestErrorCode.MISSING_POLICY);
				 }		         
				 Integer userId = null;
				 try{
					 userId = AccountTokenUtil.decodeAccountToken(token);
				 }catch(Exception e){
					 logger.error("updateUserInfo decodeAccountToken error:{}",e);
					 return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
				 }
				 if(userId == null){
					 throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
				 }
				 
				 KFFUser loginaccount = kffRmiService.findUserById(userId);
				 
				 if(loginaccount == null){
					 return this.resResult(RestErrorCode.USER_NOT_EXIST);
				 }
				 List<CoinProperty> result = kffRmiService.findCoinPropertyById(userId,coinUnlock,coinUnlockType);
				 	
				    map.put("myTokenUncoilBlocks", result);
				 
				 bre.setData(map);
			 } catch (RestServiceException e) {
				 logger.warn("myTokenRecordsList warn:{}", e);
				 return this.resResult(e.getErrorCode(), e.getMessage());
			 } catch (Exception e) {
				 logger.error("myTokenRecordsList error:{}", e);
				 return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
			 }
			 return bre;
		 }

		 /**
		  * 发放中的接口
		  * @param request
		  * @return
		  * 
		  */
		 @RequestMapping(value = "/TokenInDistributed", method = {RequestMethod.POST,RequestMethod.GET})
		 @ResponseBody
		 public BaseResponseEntity TokenInDistributed(HttpServletRequest request) {
			 BaseResponseEntity bre = new BaseResponseEntity();
			 HashMap<String, Object> map = new HashMap<String, Object>();
			 try {
				 JSONObject params = getParamMapFromRequestPolicy(request);
				 String token = (String) params.get("token");
				 if (StringUtils.isBlank(token)) {
					 throw new RestServiceException(RestErrorCode.USER_NOT_LOGIN);
				 }		         
				 Integer userId = null;
				 try{
					 userId = AccountTokenUtil.decodeAccountToken(token);
				 }catch(Exception e){
					 logger.error("updateUserInfo decodeAccountToken error:{}",e);
					 return this.resResult(RestErrorCode.PARSE_TOKEN_ERROR,e.getMessage());
				 }
				 if(userId == null){
					 throw new RestServiceException(RestErrorCode.USER_NOT_EXIST);
				 }
				 
				 KFFUser loginaccount = kffRmiService.findUserById(userId);
				 
				 if(loginaccount == null){
					 return this.resResult(RestErrorCode.USER_NOT_EXIST);
				 }
				 List<Tokenaward> result = kffRmiService.findAllTokenawardUser(userId);
				 	
				    map.put("inDistribution", result);
				 
				 bre.setData(map);
			 } catch (RestServiceException e) {
				 logger.warn("myTokenRecordsList warn:{}", e);
				 return this.resResult(e.getErrorCode(), e.getMessage());
			 } catch (Exception e) {
				 logger.error("myTokenRecordsList error:{}", e);
				 return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
			 }
			 return bre;
		 }
}


