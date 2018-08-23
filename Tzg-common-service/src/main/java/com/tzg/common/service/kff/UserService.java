package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.constants.KFFConstants;
import com.tzg.common.enums.AppType;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.RandomUtil;
import com.tzg.common.utils.RegexUtil;
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.ThreadPoolUtils;
import com.tzg.common.utils.sysGlobals;
import com.tzg.common.utils.Calculation.CalculationUtils;
import com.tzg.common.utils.getui.PushList;
import com.tzg.common.utils.getui.iOSPushList;
import com.tzg.entitys.kff.app.NewsPush;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.user.KFFUserMapper;
import com.tzg.entitys.kff.userwallet.KFFUserWallet;
import com.tzg.entitys.kff.userwallet.KFFUserWalletMapper;
import com.tzg.entitys.loginaccount.RegisterRequest;
import com.tzg.rest.constant.KFFRestConstants;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFUserService")
@Transactional
public class UserService {
	private static final Log logger = LogFactory.getLog(UserService.class);
	@Autowired
	private KFFUserMapper userMapper;
	@Autowired
	private QfIndexService qfIndexService;
	@Autowired
	private AwardPortService awardPortService;
	@Autowired
	private KFFUserWalletMapper kFFUserWalletMapper;
	@Autowired
	private UserCardService userCardService;

	@Transactional(readOnly = true)
	public KFFUser findById(java.lang.Integer id) throws RestServiceException {
		return userMapper.findById(id);
	}

	public List<KFFUser> findListByAttr(Map<String, Object> map) {
		return userMapper.findListByAttr(map);
	}

	public List<KFFUser> findListByMap(Map<String, Object> map) {
		return userMapper.findListByMap(map);
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

	public KFFUser registerH(KFFUser user) throws RestServiceException {
		userMapper.saveUser(user);
		return userMapper.findUserById(user.getUserId());
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
		if (null != registerRequest.getPassword()) {
			String pwdFmt = RegexUtil.PASSWORD_LOGIN_REGISTER;
			if (!registerRequest.getPassword().matches(pwdFmt)) {
				throw new RestServiceException(RestErrorCode.PASSWORD_FORMAT_ERROR);

			}
		}
		user.setUserName(KFFRestConstants.USER_NAME_PREFIX + RandomUtil.produceStringAndNumber(8));
		Integer randomNumber = RandomUtil.randomNumber(1, 3);
		String userIconstr = null;

		if (randomNumber == 1) {
			userIconstr = KFFRestConstants.DEFAULT_USER_ICON1;
		} else if (randomNumber == 2) {
			userIconstr = KFFRestConstants.DEFAULT_USER_ICON2;
		} else if (randomNumber == 3) {
			userIconstr = KFFRestConstants.DEFAULT_USER_ICON3;
		}
		// 获取APP有无传送个推的ClientId过来
		String clientId = registerRequest.getClientId();
		if (StringUtils.isNotBlank(clientId)) {
			user.setClientId(clientId);
		}
		user.setIcon(userIconstr);
		user.setCreateTime(createTime);
		user.setUpdateTime(createTime);
		user.setUserType(1);
		user.setStatus(1);
		user.setSex(1);
		user.setUserDegree(1);
		user.setFansNum(0);
		user.setPraiseNum(0);
		user.setEvaluationNum(0);
		user.setDiscussNum(0);
		user.setArticleNum(0);
		user.setKffCoinNum(BigDecimal.ZERO);
		user.setStatus(1);
		user.setReferLevel(0);

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

	public KFFUser login(String loginName, String password, String clientId, Integer appType) throws RestServiceException {
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
		KFFUser loninUser = new KFFUser();
		if (loginName.length() == 11 && loginName.matches(RegexUtil.PHONEREGEX)) {
			// 手机号登录
			query.addQueryData("mobile", loginName);
			logger.info("UserService login:mobile=" + loginName);
			/**
			 * 根据用户的手机账号去查询用户的id
			 */
			loninUser = userMapper.findByMobileId(loginName);
			if (null == loninUser) {
				// 账号或者密码错误
				return null;
			}
			if (loninUser.getStatus() == 0) {
				throw new RestServiceException(sysGlobals.DISABLE_ACCOUNT_MSG);
			}
			Integer userId = loninUser.getUserId();
			System.err.println("登录用户的ID  :" + userId);
			// 根据用户id去tokenaward表中获取发放状态 grantType
			if (userId != null) {

				registerAward(userId);
			}

		} else {
			// 用户名登录
			query.addQueryData("userName", loginName);
			logger.info("UserService login:userName=" + loginName);
			/**
			 * 根据用户的用户名账号去查询用户的id
			 */
			loninUser = userMapper.findByUserName(loginName);
			if (null == loninUser) {
				// 账号或者密码错误
				return null;
			}
			if (loninUser.getStatus() == 0) {
				throw new RestServiceException("当前账户因违规已暂停使用，请联系客服");
			}
			Integer userId = loninUser.getUserId();
			// 根据用户id去tokenaward表中获取发放状态 grantType
			if (userId != null) {

				registerAward(userId);
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
			user.setLastLoginDateTime(new Date());
			if (null == user.getUsercardStatus()) {
				Integer userCardStatus = userCardService.selectUserCardStatusByUserId(user.getUserId());
				user.setUsercardStatus(userCardStatus);

			}
			// 将APP传送过来的个推clientid保存进去
			if (StringUtils.isNotBlank(clientId)) {
				// 判断clientid 在数据库中存在与否，存在即清空先
				Map<String, Object> seMap = new HashMap<String, Object>();
				seMap.put("clientId", clientId);
				List<KFFUser> uuser = userMapper.findListByAttr(seMap);
				if (uuser.size() > 0) {
					for (KFFUser kffUser : uuser) {
						kffUser.setClientId("");
						userMapper.update(kffUser);
					}
				}
				user.setClientId(clientId);
				if (null == appType) {
					user.setAppType(AppType.ANDROID.getValue());
				} else {
					user.setAppType(appType);
				}
			}
			userMapper.update(user);
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
			KFFUser kffUser = users.get(0);
			if (null == kffUser.getLastLoginDateTime()) {
				kffUser.setLastLoginDateTime(new Date());
				updateUserKFFsetPopZero(kffUser.getUserId());// 0
			}
			if (!DateUtil.isToday(kffUser.getLastLoginDateTime().getTime())) {// 最后一次登陆时间不是今天
				// 并把时间设置成今天
				updateUserKFFsetPopZero(kffUser.getUserId());// 0

			}
			kffUser.setLastLoginDateTime(new Date());
			if (kffUser.getUsercardStatus() == null) {
				Integer userCardStatus = userCardService.selectUserCardStatusByUserId(kffUser.getUserId());
				kffUser.setUsercardStatus(userCardStatus);

			}
			userMapper.update(kffUser);
			return kffUser;

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

	public KFFUser saveUserByphonePass(String phoneNumber, Integer invaUserId, String password) {

		Date createTime = new Date();
		KFFUser user = new KFFUser();
		user.setMobile(phoneNumber);
		if (StringUtils.isNotBlank(password)) {
			user.setPassword(SHAUtil.encode(password));
		}
		user.setUserName(KFFRestConstants.USER_NAME_PREFIX + RandomUtil.produceStringAndNumber(8));
		Integer randomNumber = RandomUtil.randomNumber(1, 3);
		String userIconstr = null;

		if (randomNumber == 1) {
			userIconstr = KFFRestConstants.DEFAULT_USER_ICON1;
		} else if (randomNumber == 2) {
			userIconstr = KFFRestConstants.DEFAULT_USER_ICON2;
		} else if (randomNumber == 3) {
			userIconstr = KFFRestConstants.DEFAULT_USER_ICON3;
		}
		user.setIcon(userIconstr);
		user.setCreateTime(createTime);
		user.setUpdateTime(createTime);
		user.setUserType(1);
		user.setStatus(1);
		user.setSex(1);
		user.setUserDegree(1);
		user.setFansNum(0);
		user.setPraiseNum(0);
		user.setEvaluationNum(0);
		user.setDiscussNum(0);
		user.setArticleNum(0);
		user.setKffCoinNum(BigDecimal.ZERO);
		user.setStatus(1);
		user.setReferLevel(0);
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
			} else if (userId > 500000 && userId < 1000000) {
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
		if (userId == null) {
			throw new RestServiceException("用户不能为空");
		}
		if (postType == KFFConstants.POST_TYPE_ARTICLE) {
			userMapper.increaseArticleNum(userId);
		} else if (postType == KFFConstants.POST_TYPE_EVALUATION) {
			userMapper.increaseEvaNum(userId);
		} else if (postType == KFFConstants.POST_TYPE_DISCUSS) {
			userMapper.increaseDiscussNum(userId);
		}

	}

	public void increasePraiseNum(Integer userId) {
		if (userId == null) {
			throw new RestServiceException("用户不能为空");
		}

		userMapper.increasePraiseNum(userId);

	}

	public void decreasePraiseNum(Integer userId) {
		if (userId == null) {
			throw new RestServiceException("用户不能为空");
		}

		userMapper.decreasePraiseNum(userId);

	}

	public Integer findPopByToken(Integer userId) throws RestServiceException {
		Integer pop = userMapper.findPopByToken(userId);
		if (null == pop) {
			pop = 0;
		}
		return pop;
	}

	public void updateUserKFFPop(Integer userId) {

		if (userId == null) {
			throw new RestServiceException("用户ID不能为空");
		}

		userMapper.updateUserKFFPop(userId);

	}

	public KFFUser findUserStatusByPhoneNumber(String phone) {

		return userMapper.findUserStatusByPhoneNumber(phone);
	}

	public void updateUserKFFsetPopZero(Integer loginUserId) {
		if (null == loginUserId) {
			throw new RestServiceException("用户ID不能为空");
		}
		userMapper.updateUserKFFsetPopZero(loginUserId);
	}

	// 给用户APP推送消息内容
	public boolean sendNewsToBatchUser(NewsPush newsPush, String msg) throws Exception {
		logger.info("----start pushNews---");
		String mobiles = newsPush.getPeopleRange();
		String title = newsPush.getTitle();
		String content = newsPush.getContent();
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		ExecutorService executor = ThreadPoolUtils.newFixedThreadPool(sysGlobals.START_THREAD_COUNT);
		// 1.推送消息给全部用户
		if (StringUtils.isBlank(mobiles)) {
			PaginationQuery query = new PaginationQuery();
			query.setRowsPerPage(sysGlobals.DEFAULT_MSG_COUNT);
			query.setPageIndex(1);
			PageResult<KFFUser> page = findPageWithCID(query);

			int i = 0;
			Integer ii = 1;
			while (page.isHasNext() || i == 0) {
				if (i != 0) {
					ii = ii + 1;
					query.setPageIndex(ii);
					page = findPageWithCID(query);
				}
				if (page == null || page.getRows() == null) {
					break;
				}
				try {
					List<KFFUser> usersList = page.getRows();
					if (usersList.isEmpty()) {
						return false;
					}
					Integer allCount = CalculationUtils.twoNumberDiv(usersList.size(), sysGlobals.SEND_GETUI_COUNT);
					for (int k = 0; k < allCount; k++) {
						Map<String, Object> map = new HashMap<String, Object>();
						int be = k * 100;
						int endNum = 0;
						if ((k + 1) == allCount) {
							endNum = usersList.size();
						} else {
							endNum = 100 + be;
						}
						List<String> list = new ArrayList<String>();
						for (int j = 0 + be; j < endNum; j++) {
							if (null != usersList.get(j)) {
								if (StringUtils.isNotBlank(usersList.get(j).getClientId())) {
									list.add(usersList.get(j).getClientId());
									// 判断给android还是ios推送消息
									Integer apptt = usersList.get(j).getAppType();
									if (null == apptt) {
										list.add(AppType.ANDROID.getValue().toString());
									} else {
										list.add(apptt.toString());
									}
								}
							}
						}
						map.put("arr", list);
						map.put("msg", msg);
						map.put("title", title);
						map.put("content", content);
						maps.add(map);
					}
					for (int e = 0; e < maps.size(); e++) {
						MyTask myTask = new MyTask(maps.get(e));
						executor.execute(myTask);
					}
					executor.shutdown();
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
		}
		// 2.推送消息给部分用户
		if (StringUtils.isNotBlank(mobiles)) {
			Map<String, Object> map = new HashMap<String, Object>();
			String[] mobilec = mobiles.split(",");
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < mobilec.length; j++) {
				KFFUser user = userMapper.findUserStatusByPhoneNumber(mobilec[j]);
				if (null != user) {
					if (StringUtils.isNotBlank(user.getClientId())) {
						list.add(user.getClientId());
						// 判断给android还是ios推送消息
						Integer apptt = user.getAppType();
						if (null == apptt) {
							list.add(AppType.ANDROID.getValue().toString());
						} else {
							list.add(apptt.toString());
						}
					}
				}
			}
			map.put("arr", list);
			map.put("msg", msg);
			map.put("title", title);
			map.put("content", content);
			maps.add(map);
			for (int e = 0; e < maps.size(); e++) {
				MyTask myTask = new MyTask(maps.get(e));
				executor.execute(myTask);
			}
			executor.shutdown();
		}
		return true;
	}

	public class MyTask implements Runnable {
		private Map<String, Object> arrs;

		public MyTask(Map<String, Object> arr) {
			this.arrs = arr;
		}

		@Override
		public void run() {
			logger.info("----正在执行task---" + arrs);
			List arrc = (ArrayList) arrs.get("arr");
			String msg = (String) arrs.get("msg");
			String title = (String) arrs.get("title");
			String content = (String) arrs.get("content");
			if (arrc.size() > 0) {
				for (int i = 0; i < arrc.size(); i++) {
					String str = (String) arrc.get(i);
					if (str.equals(AppType.ANDROID.getValue().toString())) {
						PushList.pushMsg(arrc, msg, title, content);
					}
					if (str.equals(AppType.IOS.getValue().toString())) {
						iOSPushList.iOSPushMsg(arrc, msg, title, content);
					}
				}
			}
			logger.info("个推执行的函数--->" + arrc.toString());
			logger.info("task 完毕--->" + arrs + "执行完毕");
		}
	}

	@Transactional(readOnly = true)
	public PageResult<KFFUser> findPageWithCID(PaginationQuery query) throws Exception {
		PageResult<KFFUser> result = null;
		try {
			Integer count = userMapper.findPageCount(query.getQueryData());

			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<KFFUser> list = userMapper.findPageWithCID(query.getQueryData());
				result = new PageResult<KFFUser>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 
	 * TODO  更新用户表中的kffcoinnum字段
	 * @param userId
	 * @param amount
	 * @param type
	 * @author zhangdd
	 * @data 2018年8月21日
	 *
	 */
	public void updateUserKFFCoinNumType(Integer userId, BigDecimal amount, Integer type) {
		// TODO 对用户表进行更新
		if (type > 0 && userId != null && amount != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("kffCoinNum", amount.doubleValue());
			if (type == 1) {// typr==1 加

				userMapper.increaseKffcoinNum(map);
			}
			if (type == 2) {
				userMapper.decreaseKffcoinNum(map);
			}
		}

	}

	/**
	 * 
	* @Title: setPop 
	* @Description: TODO <定时任务将所有的用户的pop状态重置成弹出状态>
	* @author zhangdd <方法创建作者>
	* @create 上午9:58:52
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 上午9:58:52
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public void setPop() {

		// '弹出框:0-弹出;1-不弹',

		userMapper.setPop();

	}
}
