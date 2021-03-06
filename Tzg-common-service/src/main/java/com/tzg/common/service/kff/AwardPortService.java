package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.jta.UserTransactionAdapter;

import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.entitys.kff.usercard.UserCard;

/**
 * 发放接口
 * 
 * @author Wang
 *
 * @date 2018年5月10日
 */
@Service(value = "AwardPortService")
public class AwardPortService {
	private static final Log logger = LogFactory.getLog(AwardPortService.class);
	@Autowired
	private TokenrecordsService kffTokenrecordsService;
	@Autowired
	private TokenawardService kffTokenawardService;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private CoinPropertyService kffCoinPropertyService;
	@Autowired
	private UserCardService userCardService;
	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;
	// 创建用户对象
	// private KFFUser user = new KFFUser();
	// 创建奖励表对象
	private Tokenaward tokenaward = new Tokenaward();
	// 创建用户流水表对象
	private Tokenrecords tokenrecords = new Tokenrecords();
	// 创建资产统计表的对象
	private CoinProperty coinProperty = new CoinProperty();

	// 创建三个变量
	private Double m0 = 0.0d;
	private Double m1 = 0.0d;
	private Double m2 = 0.0d;
	// 格式化时间
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	// 注册的奖励发放
	/******************* 区间判断begin **************************/
	@Transactional
	public void registerAward(Integer userId) {
		try {
			System.err.println("registerAward类的" + userId);
			KFFUser user = kffUserService.findByUserId(userId);
			if (null != user) {
				System.err.println("测试我是注册人id :" + user.getUserId());
				System.err.println("userIdRegisterAward : " + userId);
			}

			if (userId != null && userId != 0) {
				// 判断注册用户在哪个区间
				if (userId > 0 && userId <= 50000) {// 用户的id在0到50000 区间

					List<Tokenaward> findByUserId = kffTokenawardService.findByUserId(userId);

					if (findByUserId.size() == 0) {
						System.err.println("我是发放奖励");
						method1(userId);

					} else {

						issue(userId);
						System.err.println("我是账单生成");
					}

				}
				if (userId > 50000 && userId <= 100000) {// 用户在50000 到100000之间
					List<Tokenaward> findByUserId = kffTokenawardService.findByUserId(userId);

					if (findByUserId.size() == 0) {
						System.err.println("我是发放奖励");
						method2(userId);

					} else {

						issue(userId);
						System.err.println("我是账单生成");
					}
				}
				if (userId > 100000 && userId <= 500000) { // 用户在100000到500000之间
					List<Tokenaward> findByUserId = kffTokenawardService.findByUserId(userId);

					if (findByUserId.size() == 0) {
						System.err.println("我是发放奖励");
						method3(userId);

					} else {

						issue(userId);
						System.err.println("我是账单生成");
					}
				}
				if (userId > 500000 && userId < 1000000) {// 用户在500000和1000000之间
					List<Tokenaward> findByUserId = kffTokenawardService.findByUserId(userId);

					if (findByUserId.size() == 0) {
						System.err.println("我是发放奖励");
						method4(userId);

					} else {

						issue(userId);
						System.err.println("我是账单生成");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("login error:{}", e);
		}

	}

	/******************* 区间判断end **************************/
	/**************** 0<x<=50000 第一阶级begin *****************************/
	// 邀请奖励的发放
	@Transactional
	public void method1(Integer userId) {
		KFFUser user = kffUserService.findByUserId(userId);
		// 判断如果用户没有被邀请 并且是普通用户
		if (user.getReferLevel() == 0 && (user.getUserType() == 1 || user.getUserType() == 4)) {// 没有邀请人并且是普通用户或者机构用户
			m0 = 10000d;
			// 没有邀请人
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);*/

			// 从数据表中获取所有该用户的奖励数据
			// issue(userId);
		} else if (user.getReferLevel() == 0 && (user.getUserType() == 2 || user.getUserType() == 3)) { // 没有邀请人并且是项目方
			m0 = 10000d * 150 / 100;
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);

			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);*/

			// 有邀请人,并且注册的是普通用户
		} else if (user.getReferLevel() == 1 && (user.getUserType() == 1 || user.getUserType() == 4)) {// 有邀请人且是普通用户或者机构用户
			// 获取邀请人的id
			Integer referUserId = user.getReferUserId();
			// 获取邀请人的对象
			KFFUser user2 = kffUserService.findById(referUserId);

			// 判断邀请人是普通用户，并且没有上一级邀请
			if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 0) {// 邀请人的是普通用户或者机构用户且邀请人没上上级邀请人
				m0 = 10000d;
				m1 = 1500d;
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setPriaiseAward(0d);
				tokenaward.setAwardBalance(m0);
				kffTokenawardService.save(tokenaward);

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);*/

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// List<Tokenaward> findByUserId2 =
				// kffTokenawardService.findByUserId(user2.getUserId());
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());// 上级邀请人获得邀请奖励总和
				if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {// 总和小于50000
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}
			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 0) {// 邀请人是项目方或者评测媒体且邀请人没有上级邀请人
				Integer findReferCount = kffUserService.findReferCount(user2.getUserId());// 邀请人一共邀请了多少用户
				if (findReferCount <= 1000) {
					m0 = 10000d * 150 / 100;
					m1 = 1500d * 150 / 100;
				} else {
					m0 = 10000d;
					m1 = 1500d;
				}
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());*/
				// kffTokenrecordsService.save(tokenrecords);

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {// 这是评测媒体或者机构号
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}
			} else if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 1) {// 有上级邀请人,且上级邀请人是普通用户或者机构用户
				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());// 查找上级邀请人的上级邀请人对象
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {// 如果二级邀请人是普通用户或者机构用户
					m0 = 10000d;
					m1 = 1500d;
					m2 = 500d;
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*	// 将注册用户的信息同时存入流水表单中
						tokenrecords.setUserId(userId);
						tokenrecords.setFunctionDesc("注册奖励");
						tokenrecords.setFunctionType(16);
						tokenrecords.setTradeType(1);
						tokenrecords.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					// kffTokenawardService.findByUserId(user2.getUserId());
					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					if (sum2 < 120000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

				}

				else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) {// 如果二级邀请人是项目方或者评测机构
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 500d;
					} else {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 500d;
					}

					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					if (sum2 < 120000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					/*
					 * for (Tokenaward award : list) { //获取每个奖励已经奖励的次数 Integer
					 * counter = award.getCounter(); //获取发放的类型 Integer
					 * distributionType = award.getDistributionType();
					 * //获取每个发放的金额 Double rewards = award.getInviteRewards();
					 * //获取奖励的类型 Integer awardFunctionType =
					 * award.getTokenAwardFunctionType(); //如果奖励没有发放完毕并且是线性发放
					 * if(counter<100 && distributionType == 1 ){
					 * if(award.getGrantType()==2) { Double x = rewards/100f;
					 * //如果是注册奖励 if(awardFunctionType == 16){
					 * tokenrecords.setUserId(userId);
					 * tokenrecords.setFunctionDesc("注册奖励");
					 * tokenrecords.setFunctionType(awardFunctionType);
					 * tokenrecords.setAmount(x); tokenrecords.setUpdateTime(new
					 * Date()); kffTokenrecordsService.update(tokenrecords);
					 * tokenaward.setUserId(userId);
					 * tokenaward.setTokenAwardFunctionType(awardFunctionType);
					 * tokenaward.setCounter(counter+1); Double awardBalance =
					 * award.getAwardBalance();
					 * kffTokenawardService.update(award);
					 * tokenaward.setAwardBalance(awardBalance-x);
					 * 
					 * KFFUser kffUser = kffUserService.findByUserId(userId);
					 * Double kffCoinNum = kffUser.getKffCoinNum();
					 * kffUserService.updateUserKFFCoinNum(userId,
					 * kffCoinNum+=x);
					 * 
					 * Double coinLock = coinProperty.getCoinLock();
					 * CoinProperty coinProperty2 = new CoinProperty(); Double
					 * coinDistributed = coinProperty.getCoinDistributed();
					 * coinProperty2.setCoinLock(coinLock + x);
					 * coinProperty2.setCoinDistributed(coinDistributed - x);
					 * kffCoinPropertyService.update(coinProperty2);
					 * 
					 * }else if(awardFunctionType == 18){//如果是邀请好友的奖励
					 * 
					 * tokenrecords.setUserId(user2.getUserId());
					 * tokenrecords.setFunctionDesc("邀请奖励");
					 * tokenrecords.setFunctionType(awardFunctionType);
					 * tokenrecords.setAmount(x); // 发放奖励数
					 * tokenrecords.setUpdateTime(new Date());
					 * 
					 * tokenaward.setUserId(user2.getUserId());
					 * tokenaward.setTokenAwardFunctionType(awardFunctionType);
					 * tokenaward.setCounter(counter+1);
					 * 
					 * Double awardBalance = award.getAwardBalance();
					 * tokenaward.setAwardBalance(awardBalance-x);
					 * 
					 * kffTokenawardService.update(award);
					 * 
					 * 
					 * kffTokenrecordsService.update(tokenrecords);
					 * 
					 * 
					 * tokenaward.setUserId(user2.getUserId());
					 * tokenaward.setTokenAwardFunctionType(awardFunctionType);
					 * tokenaward.setCounter(counter+1);
					 * kffTokenawardService.update(tokenaward);
					 * 
					 * KFFUser kffUser =
					 * kffUserService.findByUserId(user2.getUserId()); Double
					 * kffCoinNum = kffUser.getKffCoinNum();
					 * kffUserService.updateUserKFFCoinNum(userId,
					 * kffCoinNum+=x);
					 * 
					 * 
					 * } } } }
					 */
				}
				// 如果上级邀请人是是项目方，并且有上级邀请人
			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 1) {// 有上级用户且上级用户是项目方或者评测机构

				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {
					Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d * 150 / 100;
						m1 = 1500d * 150 / 100;
						m2 = 500d;
					} else {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 500d;
					}
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					if (sum2 < 120000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
				} else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) { // 将注册用户id
																						// 奖励类型
																						// 奖励金额
																						// 当前时间
																						// 发放的方式存入奖励表中
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d * 150 / 100;
						m1 = 1500d * 150 / 100;
						m2 = 500d;
					} else {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 500d;
					}

					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					if (sum2 < 5000000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
				}
			}
		} else if (true) {

		}

	}

	/**************** 0<x<=50000 第一阶级end *****************************/

	@Transactional
	public void issue(Integer userId) {
		try {
			// 判断注册用户是否实名认证
			// UserCard findBycreateUserId = userCardService.findByUserid(userId);
			UserCard findByUserid = userCardService.findByUserid(userId);

			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			// 根据用户id去获取用户资产信息

			KFFUser finduser = kffUserService.findById(userId);

			for (Tokenaward award : list) {
				// 获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				// 获取发放的类型
				Integer distributionType = award.getDistributionType();// 1代表线性发放 2代表一次性发放',
				// 获取每个发放的金额
				Double rewards = award.getInviteRewards();
				// 获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				// 如果奖励没有发放完毕并且是线性发放
				// CoinProperty coinProperty2 = new CoinProperty();
				// CoinProperty coinProperty2 = kffCoinPropertyService.findByUserId(userId);
				if (counter != null && counter < 100 && distributionType == 1 && findByUserid != null) {
					if (award.getGrantType() == 2) { // 1-今天发放一次,2-今天未发放

						Double x = rewards / 100;
						// 如果是注册奖励
						if (awardFunctionType == 16) {
							// Tokenrecords tokenrecords1 =
							// tokenrecordsMapper.findByUserIdAndFunctionType(userId);
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励"); // 交易类型
							tokenrecords.setFunctionType(awardFunctionType); // 交易的类型
							tokenrecords.setAmount(new BigDecimal(x)); // 发放奖励数
							tokenrecords.setRewardGrantType(2); // 发放类型 1-一次性发放 2-线性发放
							Date date = new Date();
							String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
							String replaceAllDate = stringDate.replaceAll("-", "");
							String format = String.format("%010d", userId);
							tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
							tokenrecords.setTradeType(1); // 交易类型 1-收入 2-支出
							tokenrecords.setTradeDate(date); // 交易日期
							tokenrecords.setCreateTime(date); // 创建的日期
							tokenrecords.setStatus(1);
							tokenrecords.setMemo("注册奖励");
							kffTokenrecordsService.save(tokenrecords);

							award.setUserId(userId);
							award.setTokenAwardFunctionType(awardFunctionType);
							award.setCounter(counter + 1);
							Double awardBalance = award.getAwardBalance();
							award.setAwardBalance(awardBalance - x);
							award.setGrantType(1);

							BigDecimal tokenNum = finduser.getKffCoinNum();
							tokenNum = tokenNum.add(new BigDecimal(x));
							finduser.setKffCoinNum(tokenNum);
							finduser.setUpdateTime(new Date());
							kffUserService.update(finduser);

							System.err.println(award.getTokenAwardFunctionDesc());

							kffTokenawardService.update(award);

							/*KFFUser kffUser = kffUserService.findById(userId);
							BigDecimal kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum.add(new BigDecimal(x)));*/
							CoinProperty coinProperty1 = kffCoinPropertyService.findByUserId(userId);
							if (coinProperty1 != null) {
								Double coinLock = coinProperty1.getCoinLock();
								Double coinDistributed = coinProperty1.getCoinDistributed();
								coinProperty1.setCoinLock(coinLock + x);
								coinProperty1.setCoinDistributed(coinDistributed - x);
								coinProperty1.setUserId(userId);
								kffCoinPropertyService.update(coinProperty1);
							}

						} else if (awardFunctionType == 18) {// 如果是邀请好友的奖励

							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("邀请奖励"); // 交易类型
							tokenrecords.setFunctionType(awardFunctionType); // 交易的类型
							tokenrecords.setAmount(new BigDecimal(x)); // 发放奖励数
							tokenrecords.setRewardGrantType(2); // 发放类型 1-一次性发放 2-线性发放
							Date date = new Date();
							String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
							String replaceAllDate = stringDate.replaceAll("-", "");
							String format = String.format("%010d", userId);
							tokenrecords.setTradeCode("01" + replaceAllDate + format); // 交易流水号
							tokenrecords.setTradeType(1); // 交易类型 1-收入 2-支出
							tokenrecords.setTradeDate(date); // 交易日期
							tokenrecords.setCreateTime(date); // 创建的日期
							tokenrecords.setStatus(1);
							tokenrecords.setMemo("邀请好友的登录奖励");
							kffTokenrecordsService.save(tokenrecords);

							award.setUserId(userId);
							award.setTokenAwardFunctionType(awardFunctionType);
							award.setCounter(counter + 1);
							award.setUserName(finduser.getUserName());
							award.setMobile(finduser.getMobile());
							Double awardBalance = award.getAwardBalance();
							award.setAwardBalance(awardBalance - x);
							award.setGrantType(1);
							BigDecimal tokenNum = finduser.getKffCoinNum();
							tokenNum = tokenNum.add(new BigDecimal(x));
							finduser.setUpdateTime(new Date());
							kffUserService.update(finduser);

							kffTokenawardService.update(award);
							/*KFFUser kffUser = kffUserService.findById(userId);
							BigDecimal kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum.add(new BigDecimal(x)));*/
							CoinProperty coinProperty2 = kffCoinPropertyService.findByUserId(userId);
							if (coinProperty2 != null) {

								Double coinLock = coinProperty2.getCoinLock();
								Double coinDistributed = coinProperty2.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
							}
						}
					}
				}
			}

		} catch (NullPointerException ne) {
			ne.printStackTrace();
			logger.error("参数为空", ne);
		}
	}

	/********************* 50000<x<=100000 第二阶段begin ****************************/
	@Transactional
	public void method2(Integer userId) {
		KFFUser user = kffUserService.findByUserId(userId);
		// 判断如果用户没有被邀请 并且是普通用户
		if (user.getReferLevel() == 0 && (user.getUserType() == 1 || user.getUserType() == 4)) {
			m0 = 10000d;
			// 没有邀请人
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			*/
			// 从数据表中获取所有该用户的奖励数据
			// issue(userId);
		} else if (user.getReferLevel() == 0 && (user.getUserType() == 2 || user.getUserType() == 3)) { // 没有邀请人并且是项目方
			m0 = 10000d * 150 / 100;
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);*/

		} else if (user.getReferLevel() == 1 && (user.getUserType() == 1 || user.getUserType() == 4)) {
			// 获取邀请人的id
			Integer referUserId = user.getReferUserId();
			// 获取邀请人的对象
			KFFUser user2 = kffUserService.findById(referUserId);

			// 判断邀请人是普通用户，并且没有上一级邀请
			if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 0) {
				m0 = 10000d;
				m1 = 1500d;
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setAwardBalance(m0);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);*/

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// List<Tokenaward> findByUserId2 =
				// kffTokenawardService.findByUserId(user2.getUserId());
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
					m1 = 1500d;
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
					m1 = 1500d;
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 0) {
				Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
				if (findReferCount <= 1000) {
					m0 = 10000d * 150 / 100;
					m1 = 1500d * 150 / 100;
				} else {
					m0 = 10000d;
					m1 = 1500d;
				}
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());*/

				// kffTokenrecordsService.save(tokenrecords);

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {// 项目方或者评测媒体
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount <= 1000) {
						m1 = 1500d * 150 / 100;
					} else {
						m1 = 1500d;
					}

					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					tokenaward2.setPriaiseAward(0d);

					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {// 项目方或者评测媒体
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 1500d * 150 / 100;
					} else {
						m1 = 1500d;
					}

					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					tokenaward2.setPriaiseAward(0d);

					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}
			} else if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 1) {
				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {
					m0 = 10000d;
					m1 = 1500d;
					m2 = 300d;
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					// kffTokenawardService.findByUserId(user2.getUserId());
					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {// 上级用户
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {// 上上级用户
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
				}

				else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) {
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 300d;
					} else {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 300d;
					}

					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 120000 && user3.getUserId() <= 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
				}
				// 如果上级邀请人是是项目方，并且有上级邀请人
			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 1) {

				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {
					Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d * 150 / 100;
						m1 = 1500d * 150 / 100;
						m2 = 300d;
					} else {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 300d;
					}
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					if (sum2 < 100000 && user3.getUserId() > 50000 && user3.getUserId() <= 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum2 < 120000 && user3.getUserId() > 0 && user3.getUserId() <= 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

				} else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) { // 将注册用户id
																						// 奖励类型
																						// 奖励金额
																						// 当前时间
																						// 发放的方式存入奖励表中
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d * 150 / 100;
						m1 = 1500d * 150 / 100;
						m2 = 300d;
					} else {
						m0 = 10000d;
						m1 = 1500d;
						m2 = 300d;
					}

					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum2 < 5000000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

				}
			}
		} else if (true) {

		}

	}

	/********************* 50000<x<=100000 第二阶段end ****************************/

	/*********************** 100000<x<=500000 第三阶段 begin ********************************/
	@Transactional
	public void method3(Integer userId) {
		KFFUser user = kffUserService.findByUserId(userId);
		// 判断如果用户没有被邀请 并且是普通用户
		if (user.getReferLevel() == 0 && (user.getUserType() == 1 || user.getUserType() == 4)) {
			m0 = 10000d;
			// 没有邀请人
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);

			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);*/

			// 从数据表中获取所有该用户的奖励数据
			// issue(userId);
		} else if (user.getReferLevel() == 0 && (user.getUserType() == 2 || user.getUserType() == 3)) { // 有邀请人并且是项目方
			m0 = 10000d * 150 / 100;
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			*/
		} else if (user.getReferLevel() == 1 && (user.getUserType() == 1 || user.getUserType() == 4)) {
			// 获取邀请人的id
			Integer referUserId = user.getReferUserId();
			// 获取邀请人的对象
			KFFUser user2 = kffUserService.findById(referUserId);

			// 判断邀请人是普通用户，并且没有上一级邀请
			if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 0) {// 普通用户或者机构用户
																										// 没有上次邀请用户
				m0 = 10000d;
				m1 = 500d;
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setAwardBalance(m0);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				*/
				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// List<Tokenaward> findByUserId2 =
				// kffTokenawardService.findByUserId(user2.getUserId());
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 100000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m1 = 500d;
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m1 = 1500d;
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m1 = 1500d;
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 0) {// 没有上级
																												// 项目方或者评测机构
				Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
				if (findReferCount <= 1000) {
					m0 = 10000d * 150 / 100;
					m1 = 500d * 150 / 100;
				} else {
					m0 = 10000d;
					m1 = 500d;
				}
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);*/

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 5000000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 500d * 150 / 100;
					} else {
						m1 = 500d;
					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 1500d * 150 / 100;
					} else {
						m1 = 1500d;

					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 1500d * 150 / 100;
					} else {
						m1 = 1500d;
					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

			} else if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 1) {// 上级是普通用户或者机构用户有上级邀请人
				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {// 上上级是机构用户或者项目方
					m0 = 10000d;
					m1 = 500d;
					m2 = 100d;
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					// kffTokenawardService.findByUserId(user2.getUserId());
					if (sum < 100000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					if (sum2 < 100000 && user3.getUserId() <= 500000 & user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum2 < 100000 && user3.getUserId() <= 100000 & user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum2 < 120000 && user3.getUserId() <= 50000 & user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

				}

				else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) {
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d;
						m1 = 500d;
						m2 = 100d;
					} else {
						m0 = 10000d;
						m1 = 500d;
						m2 = 100d;
					}

					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 100000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

				}
				// 如果上级邀请人是是项目方，并且有上级邀请人
			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 1) {

				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {
					Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d * 150 / 100;
						m1 = 500d * 150 / 100;
						m2 = 100d;
					} else {
						m0 = 10000d;
						m1 = 500d;
						m2 = 100d;
					}
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					if (sum < 5000000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {//
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 500d * 150 / 100;
						} else {
							m1 = 500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {//
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {//
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					// 上上级用户是普通用户或者是媒体用户
					if (sum2 < 100000 && user2.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user2.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 120000 && user2.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

				} else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) { // 将注册用户id
																						// 奖励类型
																						// 奖励金额
																						// 当前时间
																						// 发放的方式存入奖励表中
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 10000d * 150 / 100;
						m1 = 500d * 150 / 100;
						m2 = 100d;
					} else {
						m0 = 10000d;
						m1 = 500d;
						m2 = 100d;
					}

					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 5000000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 500d * 150 / 100;
						} else {
							m1 = 500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
					/*if (sum2 < 5000000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 50d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}*/

					if (sum2 < 5000000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

				}
			}
		} else if (true) {

		}

	}

	/************************* 100000<x<=500000 第三阶段end *******************************/

	/************************* 500000<x<=1000000 第四阶段begin *******************************/

	@Transactional
	public void method4(Integer userId) {
		KFFUser user = kffUserService.findByUserId(userId);
		// 判断如果用户没有被邀请 并且是普通用户
		if (user.getReferLevel() == 0 && (user.getUserType() == 1 || user.getUserType() == 4)) {
			m0 = 0.00d;
			// 没有邀请人
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);*/

			// 从数据表中获取所有该用户的奖励数据
			// issue(userId);
		} else if (user.getReferLevel() == 0 && (user.getUserType() == 2 || user.getUserType() == 3)) {
			m0 = 0.00d;
			// 将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setCounter(0);
			tokenaward.setGrantType(2);
			tokenaward.setPriaiseAward(0d);
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			/*// 将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);*/

			// 从数据表中获取所有该用户的奖励数据
			// issue(userId);

		} else if (user.getReferLevel() == 1 && (user.getUserType() == 1 || user.getUserType() == 4)) {
			// 获取邀请人的id
			Integer referUserId = user.getReferUserId();
			// 获取邀请人的对象
			KFFUser user2 = kffUserService.findById(referUserId);

			// 判断邀请人是普通用户，并且没有上一级邀请
			if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 0) {
				m0 = 0.00d;
				m1 = 250d;
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setAwardBalance(m0);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);*/

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// List<Tokenaward> findByUserId2 =
				// kffTokenawardService.findByUserId(user2.getUserId());
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 100000 && user2.getUserId() <= 1000000 && user2.getUserId() > 500000) {
					m1 = 250d;
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					// 将注册用户的信息同时存入流水表单中
					/*Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 100000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m1 = 500d;
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					// 将注册用户的信息同时存入流水表单中
					/*Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m2 = 1500d;
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					// 将注册用户的信息同时存入流水表单中
					/*Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m1 = 1500d;
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					// 将注册用户的信息同时存入流水表单中
					/*Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 0) {
				Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
				if (findReferCount <= 1000) {
					m0 = 0.00d;
					m1 = 250d * 150 / 100;
				} else {
					m0 = 0.00d;
					m1 = 250d;
				}
				// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				tokenaward.setCounter(0);
				tokenaward.setGrantType(2);
				tokenaward.setPriaiseAward(0d);
				kffTokenawardService.save(tokenaward);

				/*// 将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);*/

				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);

				// 从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				// 获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
				if (sum < 5000000 && user2.getUserId() <= 1000000 && user2.getUserId() > 500000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 250d * 150 / 100;
					} else {
						m1 = 250d;
					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 5000000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 500d * 150 / 100;
					} else {
						m1 = 500d;
					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 1500d * 150 / 100;
					} else {
						m1 = 1500d;
					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

				if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
					// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					if (findReferCount < 1000) {
						m1 = 1500d * 150 / 100;
					} else {
						m1 = 1500d;
					}
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setCounter(0);
					tokenaward2.setGrantType(2);
					tokenaward2.setAwardBalance(m1);
					tokenaward2.setPriaiseAward(0d);
					tokenaward2.setUserName(user2.getUserName());
					tokenaward2.setMobile(user2.getMobile());
					kffTokenawardService.save(tokenaward2);

					CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

					Double coinDistributed = coinPropertyRefer.getCoinDistributed();
					if (coinDistributed == null) {
						coinDistributed = 0d;
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
					}
					coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

					kffCoinPropertyService.update(coinPropertyRefer);

					/*// 将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(18);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);*/

				}

			} else if ((user2.getUserType() == 1 || user2.getUserType() == 4) && user2.getReferLevel() == 1) {
				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {
					m0 = 0.00d;
					m1 = 250d;
					m2 = 50d;
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					// kffTokenawardService.findByUserId(user2.getUserId());
					if (sum < 100000 && user2.getUserId() <= 1000000 && user2.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 250d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m1 = 1500d;
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user3.getUserId() <= 1000000 && user3.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 50d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						m2 = 100d;
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 120000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

				}

				else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) {
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 0.00d;
						m1 = 250d * 150 / 100;
						m2 = 50d;
					} else {
						m0 = 0.00d;
						m1 = 250d;
						m2 = 50d;
					}

					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 100000 && user2.getUserId() <= 1000000 && user2.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 250d * 150 / 100;
						} else {
							m1 = 250d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 500d * 150 / 100;
						} else {
							m1 = 500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 100000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 120000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);
						*/
						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 1000000 && user3.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 50d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user3.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user3.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user3.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user3.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

				}
				// 如果上级邀请人是是项目方，并且有上级邀请人
			} else if ((user2.getUserType() == 2 || user2.getUserType() == 3) && user2.getReferLevel() == 1) {

				// 获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				// 判断注册用户的上上级是普通用户
				if ((user3.getUserType() == 1 || user3.getUserType() == 4)) {
					Integer findReferCount = kffUserService.findReferCount(user2.getUserId());
					if (findReferCount <= 1000) {
						m0 = 0.00d;
						m1 = 250d * 150 / 100;
						m2 = 50d;
					} else {
						m0 = 0.00d;
						m1 = 250d;
						m2 = 50d;
					}
					// 将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());
					if (sum < 5000000 && user2.getUserId() <= 1000000 && user2.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 250d * 150 / 100;
						} else {
							m1 = 250d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum < 5000000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 500d * 150 / 100;
						} else {
							m1 = 500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user2.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m1);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m1);
						tokenawarde3.setCounter(0);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);
					}

					if (sum2 < 100000 && user3.getUserId() <= 1000000 && user3.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 50d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 100000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 120000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
				} else if ((user3.getUserType() == 2 || user3.getUserType() == 3)) { // 将注册用户id
																						// 奖励类型
																						// 奖励金额
																						// 当前时间
																						// 发放的方式存入奖励表中
					Integer findReferCount = kffUserService.findReferCount(user3.getUserId());
					if (findReferCount <= 1000) {
						m0 = 0.00d;
						m1 = 250d * 150 / 100;
						m2 = 50d;
					} else {
						m0 = 0.00d;
						m1 = 250d;
						m2 = 50d;
					}

					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					tokenaward.setCounter(0);
					tokenaward.setGrantType(2);
					tokenaward.setPriaiseAward(0d);
					kffTokenawardService.save(tokenaward);

					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum().doubleValue());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);

					/*// 将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);*/

					// 从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					// 获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum1(user2.getUserId());
					// 获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum1(user3.getUserId());

					if (sum < 5000000 && user2.getUserId() <= 1000000 && user2.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 250d * 150 / 100;
						} else {
							m1 = 250d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 500000 && user2.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 500d * 150 / 100;
						} else {
							m1 = 500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 100000 && user2.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum < 5000000 && user2.getUserId() <= 50000 && user2.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						if (findReferCount < 1000) {
							m1 = 1500d * 150 / 100;
						} else {
							m1 = 1500d;
						}
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setCounter(0);
						tokenaward2.setGrantType(2);
						tokenaward2.setPriaiseAward(0d);
						tokenaward2.setAwardBalance(m1);
						tokenaward2.setUserName(user2.getUserName());
						tokenaward2.setMobile(user2.getMobile());
						kffTokenawardService.save(tokenaward2);
						/*	// 将注册用户的信息同时存入流水表单中
							Tokenrecords tokenrecords2 = new Tokenrecords();
							tokenrecords2.setUserId(user2.getUserId());
							tokenrecords2.setFunctionDesc("邀请奖励");
							tokenrecords2.setFunctionType(18);
							tokenrecords2.setTradeType(1);
							tokenrecords2.setCreateTime(new Date());
							kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user2.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m1 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 1000000 && user3.getUserId() > 500000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 50d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 500000 && user3.getUserId() > 100000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 100d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 100000 && user3.getUserId() > 50000) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 300d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}

					if (sum2 < 5000000 && user3.getUserId() <= 50000 && user3.getUserId() > 0) {
						// 将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						m2 = 500d;
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						tokenawarde3.setCounter(0);
						tokenawarde3.setGrantType(2);
						tokenawarde3.setPriaiseAward(0d);
						tokenawarde3.setUserName(user3.getUserName());
						tokenawarde3.setMobile(user3.getMobile());
						kffTokenawardService.save(tokenawarde3);
						/*// 将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(18);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);*/

						CoinProperty coinPropertyRefer = kffCoinPropertyService.findByUserId(user3.getUserId());

						Double coinDistributed = coinPropertyRefer.getCoinDistributed();
						if (coinDistributed == null) {
							coinDistributed = 0d;
							coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);
						}
						coinPropertyRefer.setCoinDistributed(m2 + coinDistributed);

						kffCoinPropertyService.update(coinPropertyRefer);

					}
				}
			}
		} else if (true) {

		}

	}
	/************************* 500000<x<=1000000 第四阶段end *******************************/
}
