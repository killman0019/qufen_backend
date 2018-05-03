package com.tzg.common.service.kff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.constant.KFFRestConstants;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFUserService")
@Transactional
public class UserService   {
	private static final Log logger = LogFactory.getLog(UserService.class);

	@Autowired
	private KFFUserMapper userMapper;	
	   
	@Transactional(readOnly=true)
    public KFFUser findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return userMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        userMapper.deleteById(id);
    }
	
	public void save(KFFUser user) throws RestServiceException {	    
		userMapper.save(user);
	}
	
	public boolean update(KFFUser user) throws RestServiceException {	
		if(user.getUserId() == null){
			throw new RestServiceException("id不能为空");
		}
		userMapper.update(user);
		return true;
	}	
	
	@Transactional(readOnly=true)
	public PageResult<KFFUser> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFUser> result = null;
		try {
			Integer count = userMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFUser> list = userMapper.findPage(query.getQueryData());
				result = new PageResult<KFFUser>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public KFFUser registerRest(RegisterRequest registerRequest) throws RestServiceException{
		Date createTime = new Date();
		KFFUser user = new KFFUser();
		user.setMobile(registerRequest.getPhoneNumber());
		if(StringUtils.isNotBlank(registerRequest.getPassword())){
			user.setPassword(SHAUtil.encode(registerRequest.getPassword()));
		}
		user.setUserName(KFFRestConstants.USER_NAME_PREFIX+RandomUtil.produceStringAndNumber(8));
		user.setIcon(KFFRestConstants.DEFAULT_USER_ICON);
		user.setCreateTime(createTime);
		user.setUpdateTime(createTime);
		user.setUserType(1);
		user.setStatus(1);
		userMapper.save(user);
		return findUserByPhoneNumber(registerRequest.getPhoneNumber());
	}

	public KFFUser login(String loginName, String password) throws RestServiceException{
		KFFUser user = null;
		if(StringUtils.isBlank(loginName)){
			throw new RestServiceException(RestErrorCode.USER_ID_BLANK);
		}else if(StringUtils.isBlank(password)){
			throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
		}
		logger.info("UserService login pwd before:"+password);
		password = SHAUtil.encode(password);
		logger.info("UserService login pwd after:"+password);
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("password", password);		
		if(loginName.length() == 11 && loginName.matches(RegexUtil.PHONEREGEX)){
		//手机号登录
			query.addQueryData("mobile", loginName);
			logger.info("UserService login:mobile=" +loginName);
		}else{
		//用户名登录
			query.addQueryData("userName", loginName);
			logger.info("UserService login:userName=" +loginName);
		}
		
		int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
		query.addQueryData("startRecord", Integer.toString(startRecord));
		query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
		List<KFFUser> list = userMapper.findPage(query.getQueryData());
		if(CollectionUtils.isNotEmpty(list)){
			user = list.get(0);
		}
		
		return user;
	}

	/**
     * 验证 登陆账号，手机号，邮箱账号 是否存在
     * @param key
     * @param value
     * @return
     */
	public boolean verifyLoginaccount(String key, String value) {
		  	   
	        if (StringUtils.isBlank(key) || StringUtils.isBlank(value)) {
	            return false;
	        }
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put(key, value);
	        Integer count = userMapper.verifyLoginaccount(map);
	        if (count > 0) {
	            return true;
	        }
	        return false;

	}

	/**
	 * 根据手机号查询用户
	 * @param phoneNumber
	 * @return
	 */
	public KFFUser findUserByPhoneNumber(String phoneNumber)  throws RestServiceException{

		  if (StringUtils.isBlank(phoneNumber)) {
	          throw new RestServiceException(RestErrorCode.PHONE_NULL);
	        }
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("mobile", phoneNumber);
	        map.put("status", "1");
	        List<KFFUser> users = userMapper.findAllPage(map);
	        if(CollectionUtils.isNotEmpty(users)){
	        	return users.get(0);
	        }
		    return null;
	}

	public void updateUserKFFCoinNum(Integer userId, Integer amount) {
		

		  if (userId == null) {
	          throw new RestServiceException("用户ID不能为空");
	      }
		  if (amount == null) {
	          throw new RestServiceException("数量不能为空");
	      }
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("userId", userId);
	        map.put("kffCoinNum", amount);
	        userMapper.updateUserKFFCoinNum(map);
	       
	}

	public void increaseFansNum(Integer userId) {
		userMapper.increaseFansNum(userId);
		
	}

	public void decreaseFansNum(Integer userId) {
		
		userMapper.decreaseFansNum(userId);
	}

	
}
