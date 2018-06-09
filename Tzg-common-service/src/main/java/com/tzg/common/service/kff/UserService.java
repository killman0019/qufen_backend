package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.deployment.resolver.AARBasedWSDLLocator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.constants.KFFConstants;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;
import com.tzg.entitys.kff.userqfindex.Userqfindex;
import com.tzg.entitys.kff.userqfindex.UserqfindexMapper;
import com.tzg.entitys.kff.userwallet.KFFUserWallet;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.constant.KFFRestConstants;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;

import cn.jpush.api.report.UsersResult.User;

@Service(value = "KFFUserService")
@Transactional
public class UserService {
	private static final Log logger = LogFactory.getLog(UserService.class);
	@Autowired
	private KFFUserMapper userMapper;
	@Autowired
	private QfIndexService qfIndexService;
	@Autowired
	private TokenawardService tokenawardService;
	@Autowired
	private AwardPortService awardPortService;
	@Autowired
	private KFFUserWalletMapper kFFUserWalletMapper;

	@Transactional(readOnly = true)
	public KFFUser findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return userMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		userMapper.deleteById(id);
	}

	public void save(KFFUser user) throws RestServiceException {
		userMapper.save(user);
	}

	public KFFUser registerH(KFFUser user) throws RestServiceException
	{
		Integer userId = userMapper.saveUser(user);
		return userMapper.findUserById(userId);
	}
	
	public boolean update(KFFUser user) throws RestServiceException {
		if (user.getUserId() == null) {
			throw new RestServiceException("id不能为空");
		}
		userMapper.update(user);
		return true;
	}

	@Transactional(readOnly = true)
	public PageResult<KFFUser> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<KFFUser> result = null;
		try {
			Integer count = userMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFUser> list = userMapper.findPage(query.getQueryData());
				result = new PageResult<KFFUser>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public KFFUser registerRest(RegisterRequest registerRequest) throws RestServiceException {
		Date createTime = new Date();
		KFFUser user = new KFFUser();
		user.setMobile(registerRequest.getPhoneNumber());
		if (StringUtils.isNotBlank(registerRequest.getPassword())) {
			user.setPassword(SHAUtil.encode(registerRequest.getPassword()));
		}
		user.setUserName(KFFRestConstants.USER_NAME_PREFIX + RandomUtil.produceStringAndNumber(8));
		user.setIcon(KFFRestConstants.DEFAULT_USER_ICON);
		user.setCreateTime(createTime);
		user.setUpdateTime(createTime);
		user.setUserType(1);
		user.setStatus(1);

		userMapper.save(user);
		QfIndex qfIndex = new QfIndex();
		qfIndex.setUserId(user.getUserId());
		qfIndex.setStatusHierarchyDesc("刁民");
		qfIndex.setStatusHierarchyType(0); // 区分指数 : 新建用户默认是0分
		qfIndex.setCreateTime(new Date());
		qfIndex.setUpdateTime(new Date());
		qfIndex.setYxpraise(0);
		qfIndexService.save(qfIndex);
		// awardPortService.registerAward(user.getUserId());
		KFFUserWallet kffUserWallet = new KFFUserWallet();
		kffUserWallet.setUserId(user.getUserId());
		kffUserWallet.setUserName(user.getUserName());
		kffUserWallet.setMobile(user.getMemo());
		kffUserWallet.setWalletType(0); // '钱包状态0-未绑定 1-已绑定'
		kFFUserWalletMapper.save(kffUserWallet);

		return findUserByPhoneNumber(registerRequest.getPhoneNumber());
	}

	public KFFUser login(String loginName, String password) throws RestServiceException {
		KFFUser user = null;
		if (StringUtils.isBlank(loginName)) {
			throw new RestServiceException(RestErrorCode.USER_ID_BLANK);
		} else if (StringUtils.isBlank(password)) {
			throw new RestServiceException(RestErrorCode.PASSWORD_NULL);
		}
		logger.info("UserService login pwd before:" + password);
		password = SHAUtil.encode(password);
		logger.info("UserService login pwd after:" + password);
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("password", password);
		if (loginName.length() == 11 && loginName.matches(RegexUtil.PHONEREGEX)) {
			// 手机号登录
			query.addQueryData("mobile", loginName);
			logger.info("UserService login:mobile=" + loginName);
			/**
			 * 根据用户的手机账号去查询用户的id
			 */
			KFFUser loninUser = userMapper.findByMobileId(loginName);
			if (null == loninUser) {
				// 账号或者密码错误
				return null;
			}
			Integer userId = loninUser.getUserId();
			System.err.println("登录用户的ID  :" + userId);
			// 根据用户id去tokenaward表中获取发放状态 grantType
			List<Tokenaward> findByUserId = tokenawardService.findByUserId(userId);
			for (Tokenaward tokenaward : findByUserId) {
				if(tokenaward != null) {
					if(tokenaward.getGrantType() != null) {
						
						if ( tokenaward.getGrantType() == 2) {
							
							// 调用发放接口
							// AwardPortService awardPortService = new AwardPortService();
							// Integer tokenawardUserid = tokenaward.getUserId();
							// awardPortService.registerAward(userId);
							registerAward(userId);
							tokenaward.setGrantType(1);
							tokenawardService.update(tokenaward);
						}
					}
				}
			
			}
			
		} else {
			// 用户名登录
			query.addQueryData("userName", loginName);
			logger.info("UserService login:userName=" + loginName);
			/**
			 * 根据用户的用户名账号去查询用户的id
			 */
			KFFUser loninUser = userMapper.findByUserName(loginName);
			if (null == loninUser) {
				// 账号或者密码错误
				return null;
			}
			Integer userId = loninUser.getUserId();
			// 根据用户id去tokenaward表中获取发放状态 grantType
			List<Tokenaward> findByUserId = tokenawardService.findByUserId(userId);
			for (Tokenaward tokenaward : findByUserId) {
				if(tokenaward != null) {
					if(tokenaward.getGrantType() != null) {
						
						if ( tokenaward.getGrantType() == 2) {
							
							// 调用发放接口
							// AwardPortService awardPortService = new AwardPortService();
							// Integer tokenawardUserid = tokenaward.getUserId();
							// awardPortService.registerAward(userId);
							registerAward(userId);
							tokenaward.setGrantType(1);
							tokenawardService.update(tokenaward);
						}
					}
				}
			
			}

		}

		int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
		query.addQueryData("startRecord", Integer.toString(startRecord));
		query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
		List<KFFUser> list = userMapper.findPage(query.getQueryData());
		if (CollectionUtils.isNotEmpty(list)) {
			user = list.get(0);
		}

		// 更新用户登录时间
		if (user != null) {
			KFFUser updateLastLoginTime = new KFFUser();
			updateLastLoginTime.setUserId(user.getUserId());
			updateLastLoginTime.setLastLoginDateTime(new Date());
			userMapper.update(updateLastLoginTime);
		}

		return user;
	}

	/**
	 * 验证 登陆账号，手机号，邮箱账号 是否存在
	 * 
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
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public KFFUser findUserByPhoneNumber(String phoneNumber) throws RestServiceException {

		if (StringUtils.isBlank(phoneNumber)) {
			throw new RestServiceException(RestErrorCode.PHONE_NULL);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobile", phoneNumber);
		map.put("status", "1");
		List<KFFUser> users = userMapper.findAllPage(map);
		if (CollectionUtils.isNotEmpty(users)) {
			awardPortService.issue(users.get(0).getUserId());
			return users.get(0);
			
			
		}
		return null;
	}

	public void updateUserKFFCoinNum(Integer userId, BigDecimal amount) {

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

	@Transactional(readOnly = true)
	public String findPhoneByUserId(Integer userId) {
		// TODO Auto-generated method stub
		return userMapper.findPhoneByUserId(userId);
	}

	public Integer findReferCount(Integer userId) {
		return userMapper.findReferCount(userId);

	}

	/*
	 * public Integer findReferUserIdByUserCount(Integer referUserId) {
	 * 
	 * return userMapper.findReferUserIdByUserCount(referUserId);
	 * 
	 * }
	 */


	public KFFUser saveUserByphonePass(String phoneNumber, Integer invaUserId, String password) {

		Date createTime = new Date();
		KFFUser user = new KFFUser();
		user.setMobile(phoneNumber);
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(SHAUtil.encode(password));
		}
		user.setUserName(KFFRestConstants.USER_NAME_PREFIX + RandomUtil.produceStringAndNumber(8));
		user.setIcon(KFFRestConstants.DEFAULT_USER_ICON);
		user.setCreateTime(createTime);
		user.setUpdateTime(createTime);
		user.setUserType(1);
		user.setStatus(1);
		// 不为null 判断邀请人是第几等级
		if (null != invaUserId) {
			// 根据invauserID 查询用户user表 进行查询
			KFFUser invaUser = userMapper.findById(invaUserId);
			//
			if (null == invaUser) {
				throw new RestServiceException("邀请人参数出错,请再次核实邀请人是否正确!");
			}

			user.setReferLevel(1);
			user.setReferUserId(invaUserId);

		}
		// 没有邀请人ID 说明此人没有推荐人
		if (null == invaUserId) {
			// 说明此人没有推荐人
			user.setReferLevel(0);
			user.setReferUserId(null);

		}
		
		return registerH(user);

	}

	public KFFUser findByUserId(Integer userId) {

		return userMapper.findUserById(userId);

	}

	public Integer selectInvationM1Num(Integer userId) {
		List<KFFUser> users = userMapper.selectInvationM1Num(userId);
		if (users == null) {
			return 0;
		}
		return users.size();

	}

	public Integer selectInvationM2Num(Integer userId) {
		// 所有的一级邀请人
		List<KFFUser> users = userMapper.selectInvationM1Num(userId);
		if (users.size() == 0) {
			return 0;
		}

		// 初始邀请M2总人数
		int userM2Sum = 0;
		for (KFFUser user : users) {
			Integer userM2 = selectInvationM1Num(user.getUserId());
			userM2Sum = userM2Sum + userM2.intValue();
		}
		return new Integer(userM2Sum);
	}

	private void registerAward(Integer userId) {
		System.err.println("registerAward类的" + userId);
		KFFUser user = findByUserId(userId);
		// 格式化时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.err.println("测试我是注册人id :" + user.getUserId());
		System.err.println("userIdRegisterAward : " + userId);
		if (userId != null && userId != 0) {
			// 判断注册用户在哪个区间
			if (userId > 0 && userId <= 50000) {
				Date createTime = user.getCreateTime();

				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if (formatCreateTime == formatNewDate) {

					System.err.println("我是发放奖励");
					awardPortService.method1(userId);

					// method1(userId);
					// issue(userId);
				} else {
					System.err.println("我是账单生成");
					awardPortService.issue(userId);
				}

			} else if (userId > 50000 && userId <= 100000) {
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if (formatCreateTime == formatNewDate) {
					awardPortService.method1(userId);
				} else {

					awardPortService.issue(userId);
				}
			} else if (userId > 100000 && userId <= 500000) {
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if (formatCreateTime == formatNewDate) {

					awardPortService.method3(userId);
				} else {

					awardPortService.issue(userId);
				}
			} else if (userId > 5000000 && userId < 1000000) {
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if (formatCreateTime == formatNewDate) {
					awardPortService.method4(userId);
				} else {

					awardPortService.issue(userId);
				}
			}
		}

	}
	
	public void increasePostNum(Integer userId, int postType) {
		if(userId == null){
			throw new RestServiceException("用户不能为空");
		}
		if(postType ==KFFConstants.POST_TYPE_ARTICLE){
			userMapper.increaseArticleNum(userId);
		}else if(postType == KFFConstants.POST_TYPE_EVALUATION){
			userMapper.increaseEvaNum(userId);
		}else if(postType==KFFConstants.POST_TYPE_DISCUSS){
			userMapper.increaseDiscussNum(userId);
		}
		
	}
}
