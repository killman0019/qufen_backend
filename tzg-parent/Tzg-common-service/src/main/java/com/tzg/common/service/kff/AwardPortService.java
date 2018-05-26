package com.tzg.common.service.kff;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;

/**
 * 发放接口
 * @author Wang
 *
 * @date 2018年5月10日
 */
@Service(value="KFFAwardPortService")
@Transactional
public class AwardPortService  {
	@Autowired 
	private TokenrecordsService kffTokenrecordsService;
	@Autowired 
	private TokenawardService kffTokenawardService;
	@Autowired
	private UserService kffUserService;
	@Autowired
	private CoinPropertyService kffCoinPropertyService;
	//创建用户对象
	private	KFFUser user = new KFFUser();
	//创建奖励表对象
	private	Tokenaward tokenaward = new Tokenaward();
	//创建用户流水表对象
	private Tokenrecords tokenrecords = new Tokenrecords();
	//创建资产统计表的对象
	private CoinProperty coinProperty = new CoinProperty();
	
	//创建三个变量
	private Double m0 = 0.0d;
	private Double m1 = 0.0d;
	private Double m2 = 0.0d;
	//格式化时间
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	//注册的奖励发放
	public void registerAward(Integer userId){
		
		
		user=kffUserService.findById(userId);
		if(userId != null && userId != 0){
			//判断注册用户在哪个区间
			if(userId>0 && userId <= 50000){
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if(formatCreateTime==formatNewDate) {
					method1(userId);
				}else{
					
					issue(userId);
				}
				
			}else
			if(userId>50000 && userId <= 100000){
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if(formatCreateTime==formatNewDate) {
					method2(userId);
				}else{
					
					issue(userId);
				}
			}else
			if(userId>100000 && userId <= 500000){
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if(formatCreateTime==formatNewDate) {
					method3(userId);
				}else{
					
					issue(userId);
				}
			}else
			if(userId>5000000 && userId < 1000000){
				Date createTime = user.getCreateTime();
				String formatCreateTime = format.format(createTime);
				String formatNewDate = format.format(new Date());
				if(formatCreateTime==formatNewDate) {
					method4(userId);
				}else{
					
					issue(userId);
				}
			}
		}
		
	}
	
	//邀请奖励的发放

	private void method1(Integer userId) {
		//判断如果用户没有被邀请 并且是普通用户
		if(user.getReferLevel()==0 && (user.getUserType()==1 || user.getUserType()==4)){
			m0=50000d;
			//没有邀请人
			//将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0); // 奖励余额初始化 是注册奖励
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			//将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			//从数据表中获取所有该用户的奖励数据
			issue(userId);
		}else if(user.getReferLevel()==0 && (user.getUserType()==2||user.getUserType()==3)){ // 有邀请人并且是项目方
			m0 = 50000d*150/100;
			
			//将用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			
			tokenaward.setAwardBalance(m0);  // 奖励余额初始化 是注册奖励
			kffTokenawardService.save(tokenaward);
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			
			//将用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			//从数据表中获取所有该用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//根据用户id去获取用户资产信息
			CoinProperty coinProperty = kffCoinPropertyService.findByUserId(userId);
			
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				CoinProperty coinProperty2 = new CoinProperty();
				KFFUser user2 = kffUserService.findById(userId);
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
					Double x = rewards/100f;
					//如果是注册奖励
					
					if(awardFunctionType == 16){ 
						tokenrecords.setUserId(userId);
						tokenrecords.setFunctionDesc("注册奖励");
						tokenrecords.setFunctionType(awardFunctionType);
						tokenrecords.setAmount(x);
						tokenrecords.setUpdateTime(new Date());
						kffTokenrecordsService.update(tokenrecords);
						tokenaward.setUserId(userId);
						tokenaward.setTokenAwardFunctionType(awardFunctionType);
						tokenaward.setCounter(counter+1);
						Double awardBalance = award.getAwardBalance();
						tokenaward.setAwardBalance(awardBalance-x);
						
						kffTokenrecordsService.update(tokenrecords);
						kffTokenawardService.update(tokenaward);
						
						KFFUser kffUser = kffUserService.findById(userId);
						Double kffCoinNum = kffUser.getKffCoinNum();
						kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
						
						
						Double coinLock = coinProperty.getCoinLock();
						Double coinDistributed = coinProperty.getCoinDistributed();
						coinProperty2.setCoinLock(coinLock + x);
						coinProperty2.setCoinDistributed(coinDistributed - x);
						kffCoinPropertyService.update(coinProperty2);
						
					}else if(awardFunctionType == 18){//如果是邀请好友的奖励
						
						tokenrecords.setUserId(user2.getUserId());
						tokenrecords.setFunctionDesc("邀请奖励");
						tokenrecords.setFunctionType(awardFunctionType);
						tokenrecords.setAmount(x); // 发放奖励数
						tokenrecords.setUpdateTime(new Date());
						kffTokenrecordsService.update(tokenrecords);
						tokenaward.setUserId(user2.getUserId());
						tokenaward.setTokenAwardFunctionType(awardFunctionType);
						tokenaward.setCounter(counter+1);
						
						Double awardBalance = award.getAwardBalance();
						tokenaward.setAwardBalance(awardBalance-x);
						
						kffTokenawardService.update(tokenaward);
						
						KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
						Double kffCoinNum = kffUser.getKffCoinNum();
						kffUserService.updateUserKFFCoinNum(user2.getUserId(), kffCoinNum+=x);
						
						Double coinLock = coinProperty.getCoinLock();
						Double coinDistributed = coinProperty.getCoinDistributed();
						coinProperty2.setCoinLock(coinLock + x);
						coinProperty2.setCoinDistributed(coinDistributed - x);
						kffCoinPropertyService.update(coinProperty2);
						
						}
					}
				}
			}
			// 有邀请人,并且注册的是普通用户
		}else if(user.getReferLevel()==1 && (user.getUserType()==1 || user.getUserType()==4)){
			//获取邀请人的id
			Integer referUserId = user.getReferUserId();
			//获取邀请人的对象
			KFFUser user2 = kffUserService.findByUserId(referUserId);
			
			//判断邀请人是普通用户，并且没有上一级邀请
			if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==0){
				m0=50000d;
				m1=2500d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				
				tokenaward.setAwardBalance(m0);
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//List<Tokenaward> findByUserId2 = kffTokenawardService.findByUserId(user2.getUserId());
				//获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				if(sum <= 50000){         
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(16);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					
					
					tokenaward2.setAwardBalance(m1);
					kffTokenawardService.save(tokenaward2);
					
					coinProperty.setUserId(user2.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
					coinProperty.setCoinDistributed(m1);
					
					kffCoinPropertyService.save(coinProperty);
					
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					
					CoinProperty coinProperty2 = new CoinProperty();
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenrecordsService.update(tokenrecords);
								kffTokenawardService.update(tokenaward);
								
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
								
							}else if(awardFunctionType == 18){// 如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							}
						}
					}
				}
				//判断邀请人是项目方用户，并且没有上一级邀请
			}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==0){
				m0=50000d*150/100;
				m1=2500d*150/100;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				if(sum <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(16);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					
					tokenaward2.setAwardBalance(m1);
					
					kffTokenawardService.save(tokenaward2);
					
					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty); 
					
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					CoinProperty coinProperty2 = new CoinProperty();
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								kffTokenrecordsService.update(tokenrecords);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findById(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(user2.getUserId(), kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}
						}
					}
				}
				//判断邀请人是普通用户，并且有上一级邀请
			}else if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==1){
				//获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				//判断注册用户的上上级是普通用户
				if((user3.getUserType()==1||user3.getUserType()==4)){
					m0=50000d;
					m1=2500d;
					m2=500d;
					//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					kffTokenawardService.save(tokenaward);
					
					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);
					
					//将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);
					
					//从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					//获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
					//获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
					//kffTokenawardService.findByUserId(user2.getUserId());
					if(sum <= 50000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						tokenaward2.setAwardBalance(m1);
						
						kffTokenawardService.save(tokenaward2);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
						coinProperty.setUserId(user2.getUserId());
						coinProperty.setCoinLock(kffUserService.findById(user2.getUserId()).getKffCoinNum());
						CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
						coinProperty.setCoinDistributed(m1+coinProperty.getCoinDistributed());
						kffCoinPropertyService.save(coinProperty);
						
					}
					if(sum2 <= 50000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						kffTokenawardService.save(tokenawarde3);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
						coinProperty.setUserId(user3.getUserId());
						coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
						CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
						coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
						kffCoinPropertyService.save(coinProperty);
						
					}
					for (Tokenaward award : list) {
						//获取每个奖励已经奖励的次数
						Integer counter = award.getCounter();
						//获取发放的类型
						Integer distributionType = award.getDistributionType();
						//获取每个发放的金额
						Double rewards = award.getInviteRewards();
						//获取奖励的类型
						Integer awardFunctionType = award.getTokenAwardFunctionType();
						//如果奖励没有发放完毕并且是线性发放
						if(counter<100 && distributionType == 1 ){
							if(award.getGrantType()==2) { 
								Double x = rewards/100;
								//如果是注册奖励
								if(awardFunctionType == 16){
									tokenrecords.setUserId(userId);
									tokenrecords.setFunctionDesc("注册奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x);
									tokenrecords.setUpdateTime(new Date());
									kffTokenrecordsService.update(tokenrecords);
									tokenaward.setUserId(userId);
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									Double awardBalance = award.getAwardBalance();
									kffTokenawardService.update(award);
									tokenaward.setAwardBalance(awardBalance-x);
									
									KFFUser kffUser = kffUserService.findByUserId(userId);
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
									Double coinLock = coinProperty.getCoinLock();
									CoinProperty coinProperty2 = new CoinProperty();
									Double coinDistributed = coinProperty.getCoinDistributed();
									coinProperty2.setCoinLock(coinLock + x);
									coinProperty2.setCoinDistributed(coinDistributed - x);
									kffCoinPropertyService.update(coinProperty2);
									
								}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
									tokenrecords.setUserId(user2.getUserId());
									tokenrecords.setFunctionDesc("邀请奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x); // 发放奖励数
									tokenrecords.setUpdateTime(new Date());
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									
									Double awardBalance = award.getAwardBalance();
									tokenaward.setAwardBalance(awardBalance-x);
									
									kffTokenawardService.update(award);
									
									
									kffTokenrecordsService.update(tokenrecords);
									
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									kffTokenawardService.update(tokenaward);
									
									KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
								}
							}
						}
					}
					//如果注册用户的上上级用户是项目方
				}
				
				else if((user3.getUserType()==2||user3.getUserType()==3)){
					m0=50000d;
					m1=2500d;
					m2=500d*150/100;
					//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					kffTokenawardService.save(tokenaward);
					
					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);
					
					//将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);
					
					//从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					//获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
					//获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
					
					if(sum <= 50000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						kffTokenawardService.save(tokenaward2);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
					}
					if(sum2 <= 50000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						kffTokenawardService.save(tokenawarde3);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
						coinProperty.setUserId(user3.getUserId());
						coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
						CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
						coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
						kffCoinPropertyService.save(coinProperty);
						
					}
					for (Tokenaward award : list) {
						//获取每个奖励已经奖励的次数
						Integer counter = award.getCounter();
						//获取发放的类型
						Integer distributionType = award.getDistributionType();
						//获取每个发放的金额
						Double rewards = award.getInviteRewards();
						//获取奖励的类型
						Integer awardFunctionType = award.getTokenAwardFunctionType();
						//如果奖励没有发放完毕并且是线性发放
						if(counter<100 && distributionType == 1 ){
							if(award.getGrantType()==2) { 
								Double x = rewards/100f;
								//如果是注册奖励
								if(awardFunctionType == 16){
									tokenrecords.setUserId(userId);
									tokenrecords.setFunctionDesc("注册奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x);
									tokenrecords.setUpdateTime(new Date());
									kffTokenrecordsService.update(tokenrecords);
									tokenaward.setUserId(userId);
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									Double awardBalance = award.getAwardBalance();
									kffTokenawardService.update(award);
									tokenaward.setAwardBalance(awardBalance-x);
									
									KFFUser kffUser = kffUserService.findByUserId(userId);
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
									Double coinLock = coinProperty.getCoinLock();
									CoinProperty coinProperty2 = new CoinProperty();
									Double coinDistributed = coinProperty.getCoinDistributed();
									coinProperty2.setCoinLock(coinLock + x);
									coinProperty2.setCoinDistributed(coinDistributed - x);
									kffCoinPropertyService.update(coinProperty2);
									
								}else if(awardFunctionType == 18){//如果是邀请好友的奖励
									
									tokenrecords.setUserId(user2.getUserId());
									tokenrecords.setFunctionDesc("邀请奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x); // 发放奖励数
									tokenrecords.setUpdateTime(new Date());
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									
									Double awardBalance = award.getAwardBalance();
									tokenaward.setAwardBalance(awardBalance-x);
									
									kffTokenawardService.update(award);
									
									
									kffTokenrecordsService.update(tokenrecords);
									
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									kffTokenawardService.update(tokenaward);
									
									KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
									
								}
							}
						}
					}
				}
				//如果上级邀请人是是项目方，并且有上级邀请人
			}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==1){

				//获取上上级的用户表
				KFFUser user3 = kffUserService.findById(user2.getReferUserId());
				//判断注册用户的上上级是普通用户
				if((user3.getUserType()==1||user3.getUserType()==4)){
					m0=50000d*150/100;
					m1=2500d*150/100;
					m2=500d;
					//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					kffTokenawardService.save(tokenaward);
					
					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);
					
					//将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);
					
					//从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					//获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
					//获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
					if(sum <= 50000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						kffTokenawardService.save(tokenawarde3);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
						coinProperty.setUserId(user3.getUserId());
						coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
						CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
						coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
						kffCoinPropertyService.save(coinProperty);
						
						
					}
					if(sum2 <= 50000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						kffTokenawardService.save(tokenawarde3);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
						coinProperty.setUserId(user3.getUserId());
						coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
						CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
						coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
						kffCoinPropertyService.save(coinProperty);
						
					}
					for (Tokenaward award : list) {
						//获取每个奖励已经奖励的次数
						Integer counter = award.getCounter();
						//获取发放的类型
						Integer distributionType = award.getDistributionType();
						//获取每个发放的金额
						Double rewards = award.getInviteRewards();
						//获取奖励的类型
						Integer awardFunctionType = award.getTokenAwardFunctionType();
						//如果奖励没有发放完毕并且是线性发放
						if(counter<100 && distributionType == 1 ){
							if(award.getGrantType()==2) { 
								Double x = rewards/100;
								//如果是注册奖励
								if(awardFunctionType == 16){
									tokenrecords.setUserId(userId);
									tokenrecords.setFunctionDesc("注册奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x);
									tokenrecords.setUpdateTime(new Date());
									kffTokenrecordsService.update(tokenrecords);
									tokenaward.setUserId(userId);
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									Double awardBalance = award.getAwardBalance();
									kffTokenawardService.update(award);
									tokenaward.setAwardBalance(awardBalance-x);
									
									KFFUser kffUser = kffUserService.findByUserId(userId);
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
									Double coinLock = coinProperty.getCoinLock();
									CoinProperty coinProperty2 = new CoinProperty();
									Double coinDistributed = coinProperty.getCoinDistributed();
									coinProperty2.setCoinLock(coinLock + x);
									coinProperty2.setCoinDistributed(coinDistributed - x);
									kffCoinPropertyService.update(coinProperty2);
								}else if(awardFunctionType == 18){//如果是邀请好友的奖励
									
									tokenrecords.setUserId(user2.getUserId());
									tokenrecords.setFunctionDesc("邀请奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x); // 发放奖励数
									tokenrecords.setUpdateTime(new Date());
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									
									Double awardBalance = award.getAwardBalance();
									tokenaward.setAwardBalance(awardBalance-x);
									
									kffTokenawardService.update(award);
									
									
									kffTokenrecordsService.update(tokenrecords);
									
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									kffTokenawardService.update(tokenaward);
									
									KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
								}
							}
						}
					}
					//如果注册用户的上上级用户是项目方
				}else if((user3.getUserType()==2||user3.getUserType()==3)){	//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					m0=50000d;
					m1=2500d*150/100;
					m2=500d*150/100;
					tokenaward.setUserId(userId);
					tokenaward.setTokenAwardFunctionType(16);
					tokenaward.setTokenAwardFunctionDesc("注册奖励");
					tokenaward.setInviteRewards(m0);
					tokenaward.setCreateTime(new Date());
					tokenaward.setDistributionType(1);
					tokenaward.setAwardBalance(m0);
					tokenaward.setUserName(user.getUserName());
					tokenaward.setMobile(user.getMobile());
					kffTokenawardService.save(tokenaward);
					
					coinProperty.setUserId(userId);
					coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
					coinProperty.setCoinDistributed(m0);
					kffCoinPropertyService.save(coinProperty);
					
					//将注册用户的信息同时存入流水表单中
					tokenrecords.setUserId(userId);
					tokenrecords.setFunctionDesc("注册奖励");
					tokenrecords.setFunctionType(16);
					tokenrecords.setTradeType(1);
					tokenrecords.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords);
					
					//从数据表中获取所有注册用户的奖励数据
					List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
					//获取上级邀请人邀请的钱数的总额
					Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
					//获取上上级邀请人邀请的钱数的总额
					Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
					
					if(sum <= 5000000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenaward2 = new Tokenaward();
						tokenaward2.setUserId(user2.getUserId());
						tokenaward2.setTokenAwardFunctionType(18);
						tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
						tokenaward2.setInviteRewards(m1);
						tokenaward2.setCreateTime(new Date());
						tokenaward2.setDistributionType(1);
						kffTokenawardService.save(tokenaward2);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user2.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
					}
					if(sum2 <= 5000000){
						//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
						Tokenaward tokenawarde3 = new Tokenaward();
						tokenawarde3.setUserId(user3.getUserId());
						tokenawarde3.setTokenAwardFunctionType(18);
						tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
						tokenawarde3.setInviteRewards(m2);
						tokenawarde3.setCreateTime(new Date());
						tokenawarde3.setDistributionType(1);
						tokenawarde3.setAwardBalance(m2);
						kffTokenawardService.save(tokenawarde3);
						//将注册用户的信息同时存入流水表单中
						Tokenrecords tokenrecords2 = new Tokenrecords();
						tokenrecords2.setUserId(user3.getUserId());
						tokenrecords2.setFunctionDesc("邀请奖励");
						tokenrecords2.setFunctionType(16);
						tokenrecords2.setTradeType(1);
						tokenrecords2.setCreateTime(new Date());
						kffTokenrecordsService.save(tokenrecords2);
						
						coinProperty.setUserId(user3.getUserId());
						coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
						CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
						coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
						kffCoinPropertyService.save(coinProperty);
						
					}
					for (Tokenaward award : list) {
						//获取每个奖励已经奖励的次数
						Integer counter = award.getCounter();
						//获取发放的类型
						Integer distributionType = award.getDistributionType();
						//获取每个发放的金额
						Double rewards = award.getInviteRewards();
						//获取奖励的类型
						Integer awardFunctionType = award.getTokenAwardFunctionType();
						//如果奖励没有发放完毕并且是线性发放
						if(counter<100 && distributionType == 1 ){
							if(award.getGrantType()==2) { 
								Double x = rewards/100f;
								//如果是注册奖励
								if(awardFunctionType == 16){
									tokenrecords.setUserId(userId);
									tokenrecords.setFunctionDesc("注册奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x);
									tokenrecords.setUpdateTime(new Date());
									kffTokenrecordsService.update(tokenrecords);
									tokenaward.setUserId(userId);
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									Double awardBalance = award.getAwardBalance();
									kffTokenawardService.update(award);
									tokenaward.setAwardBalance(awardBalance-x);
									
									KFFUser kffUser = kffUserService.findByUserId(userId);
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
									Double coinLock = coinProperty.getCoinLock();
									CoinProperty coinProperty2 = new CoinProperty();
									Double coinDistributed = coinProperty.getCoinDistributed();
									coinProperty2.setCoinLock(coinLock + x);
									coinProperty2.setCoinDistributed(coinDistributed - x);
									kffCoinPropertyService.update(coinProperty2);
									
								}else if(awardFunctionType == 18){//如果是邀请好友的奖励
									
									tokenrecords.setUserId(user2.getUserId());
									tokenrecords.setFunctionDesc("邀请奖励");
									tokenrecords.setFunctionType(awardFunctionType);
									tokenrecords.setAmount(x); // 发放奖励数
									tokenrecords.setUpdateTime(new Date());
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									
									Double awardBalance = award.getAwardBalance();
									tokenaward.setAwardBalance(awardBalance-x);
									
									kffTokenawardService.update(award);
									
									
									kffTokenrecordsService.update(tokenrecords);
									
									
									tokenaward.setUserId(user2.getUserId());
									tokenaward.setTokenAwardFunctionType(awardFunctionType);
									tokenaward.setCounter(counter+1);
									kffTokenawardService.update(tokenaward);
									
									KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
									Double kffCoinNum = kffUser.getKffCoinNum();
									kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
									
									
								}
							}
						}
					}
				}
			}
		}else if(true){
			
		}
		
		
	}

	private void issue(Integer userId) {
		List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
		//根据用户id去获取用户资产信息
		CoinProperty coinProperty = kffCoinPropertyService.findByUserId(userId);
		for (Tokenaward award : list) {
			//获取每个奖励已经奖励的次数
			Integer counter = award.getCounter();
			//获取发放的类型
			Integer distributionType = award.getDistributionType();
			//获取每个发放的金额
			Double rewards = award.getInviteRewards();
			//获取奖励的类型
			Integer awardFunctionType = award.getTokenAwardFunctionType();
			// 如果奖励没有发放完毕并且是线性发放
			CoinProperty coinProperty2 = new CoinProperty();
			if(counter<100 && distributionType == 1 ){
				if(award.getGrantType()==2) { // 1-今天发放一次,2-今天未发放
					
					Double x = rewards/100;
					// 如果是注册奖励
					if(awardFunctionType == 16){
						tokenrecords.setUserId(userId);
						tokenrecords.setFunctionDesc("注册奖励");
						tokenrecords.setFunctionType(awardFunctionType);
						tokenrecords.setAmount(x);
						tokenrecords.setUpdateTime(new Date());
						tokenaward.setUserId(userId);
						tokenaward.setTokenAwardFunctionType(awardFunctionType);
						tokenaward.setCounter(counter+1);
						Double awardBalance = award.getAwardBalance();
						tokenaward.setAwardBalance(awardBalance-x);
						
						kffTokenrecordsService.update(tokenrecords);
						kffTokenawardService.update(tokenaward);
						
						KFFUser kffUser = kffUserService.findById(userId);
						Double kffCoinNum = kffUser.getKffCoinNum();
						kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
						
						Double coinLock = coinProperty.getCoinLock();
						Double coinDistributed = coinProperty.getCoinDistributed();
						coinProperty2.setCoinLock(coinLock + x);
						coinProperty2.setCoinDistributed(coinDistributed - x);
						kffCoinPropertyService.update(coinProperty2);
						
						
						
					}else if(awardFunctionType == 18){// 如果是邀请好友的奖励
						
						tokenrecords.setUserId(userId);
						tokenrecords.setFunctionDesc("邀请奖励");
						tokenrecords.setFunctionType(awardFunctionType);
						tokenrecords.setAmount(x); // 发放奖励数
						tokenrecords.setUpdateTime(new Date());
						kffTokenrecordsService.update(tokenrecords);
						tokenaward.setUserId(userId);
						tokenaward.setTokenAwardFunctionType(awardFunctionType);
						tokenaward.setCounter(counter+1);
						
						Double awardBalance = award.getAwardBalance();
						tokenaward.setAwardBalance(awardBalance-x);
						
						kffTokenawardService.update(award);
						KFFUser kffUser = kffUserService.findById(userId);
						Double kffCoinNum = kffUser.getKffCoinNum();
						kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
						
						Double coinLock = coinProperty.getCoinLock();
						Double coinDistributed = coinProperty.getCoinDistributed();
						coinProperty2.setCoinLock(coinLock + x);
						coinProperty2.setCoinDistributed(coinDistributed - x);
						kffCoinPropertyService.update(coinProperty2);
					}
				}
			}
		}
	}
	
	private void method2(Integer userId) {
		//获取邀请人的id
		Integer referUserId = user.getReferUserId();
		//获取邀请人的对象
		KFFUser user2 = kffUserService.findByUserId(referUserId);
		
		//判断邀请人是普通用户，并且没有上一级邀请
		if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==0){
			m0=30000d;
			m1=1500d;
			//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setAwardBalance(m0);
			kffTokenawardService.save(tokenaward);
			
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			//将注册用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			//从数据表中获取所有注册用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//List<Tokenaward> findByUserId2 = kffTokenawardService.findByUserId(user2.getUserId());
			//获取邀请人邀请的钱数的总额
			Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
			if(sum <= 30000){         
				//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				Tokenaward tokenaward2 = new Tokenaward();
				tokenaward2.setUserId(user2.getUserId());
				tokenaward2.setTokenAwardFunctionType(16);
				tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
				tokenaward2.setInviteRewards(m1);
				tokenaward2.setCreateTime(new Date());
				tokenaward2.setDistributionType(1);
				
				tokenaward2.setAwardBalance(m1);
				kffTokenawardService.save(tokenaward2);
				
				coinProperty.setUserId(user2.getUserId());
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m1);
				
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				Tokenrecords tokenrecords2 = new Tokenrecords();
				tokenrecords2.setUserId(user2.getUserId());
				tokenrecords2.setFunctionDesc("邀请奖励");
				tokenrecords2.setFunctionType(16);
				tokenrecords2.setTradeType(1);
				tokenrecords2.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords2);
				
			}
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				
				CoinProperty coinProperty2 = new CoinProperty();
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
						Double x = rewards/100;
						//如果是注册奖励
						if(awardFunctionType == 16){
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x);
							tokenrecords.setUpdateTime(new Date());
							kffTokenrecordsService.update(tokenrecords);
							tokenaward.setUserId(userId);
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenrecordsService.update(tokenrecords);
							kffTokenawardService.update(tokenaward);
							
							
							KFFUser kffUser = kffUserService.findByUserId(userId);
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
							
						}else if(awardFunctionType == 18){// 如果是邀请好友的奖励
							
							tokenrecords.setUserId(user2.getUserId());
							tokenrecords.setFunctionDesc("邀请奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x); // 发放奖励数
							tokenrecords.setUpdateTime(new Date());
							
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenawardService.update(award);
							
							
							kffTokenrecordsService.update(tokenrecords);
							
							
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
						}
					}
				}
			}
			//判断邀请人是项目方用户，并且没有上一级邀请
		}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==0){
			m0=30000d*150/100;
			m1=1500d*150/100;
			//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			kffTokenawardService.save(tokenaward);
			
			
			//将注册用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			//从数据表中获取所有注册用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//获取邀请人邀请的钱数的总额
			Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
			if(sum <= 5000000){
				//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				Tokenaward tokenaward2 = new Tokenaward();
				tokenaward2.setUserId(user2.getUserId());
				tokenaward2.setTokenAwardFunctionType(16);
				tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
				tokenaward2.setInviteRewards(m1);
				tokenaward2.setCreateTime(new Date());
				tokenaward2.setDistributionType(1);
				
				tokenaward2.setAwardBalance(m1);
				
				kffTokenawardService.save(tokenaward2);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty); 
				
				//将注册用户的信息同时存入流水表单中
				Tokenrecords tokenrecords2 = new Tokenrecords();
				tokenrecords2.setUserId(user2.getUserId());
				tokenrecords2.setFunctionDesc("邀请奖励");
				tokenrecords2.setFunctionType(16);
				tokenrecords2.setTradeType(1);
				tokenrecords2.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords2);
				
			}
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				CoinProperty coinProperty2 = new CoinProperty();
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
						Double x = rewards/100;
						//如果是注册奖励
						if(awardFunctionType == 16){
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x);
							tokenrecords.setUpdateTime(new Date());
							tokenaward.setUserId(userId);
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							kffTokenrecordsService.update(tokenrecords);
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findById(userId);
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
						}else if(awardFunctionType == 18){//如果是邀请好友的奖励
							
							tokenrecords.setUserId(user2.getUserId());
							tokenrecords.setFunctionDesc("邀请奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x); // 发放奖励数
							tokenrecords.setUpdateTime(new Date());
							kffTokenrecordsService.update(tokenrecords);
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(user2.getUserId(), kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
						}
					}
				}
			}
			//判断邀请人是普通用户，并且有上一级邀请
		}else if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==1){
			//获取上上级的用户表
			KFFUser user3 = kffUserService.findById(user2.getReferUserId());
			//判断注册用户的上上级是普通用户
			if((user3.getUserType()==1||user3.getUserType()==4)){
				m0=30000d;
				m1=1500d;
				m2=300d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				//kffTokenawardService.findByUserId(user2.getUserId());
				if(sum <= 30000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setAwardBalance(m1);
					
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user2.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user2.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m1+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				if(sum2 <= 30000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
							
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
							}
						}
					}
				}
				//如果注册用户的上上级用户是项目方
			}
			
			else if((user3.getUserType()==2||user3.getUserType()==3)){
				m0=30000d;
				m1=1500d;
				m2=300d*150/100;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				
				if(sum <= 30000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				if(sum2 <= 30000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100f;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								
							}
						}
					}
				}
			}
			//如果上级邀请人是是项目方，并且有上级邀请人
		}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==1){

			//获取上上级的用户表
			KFFUser user3 = kffUserService.findById(user2.getReferUserId());
			//判断注册用户的上上级是普通用户
			if((user3.getUserType()==1||user3.getUserType()==4)){
				m0=30000d*150/100;
				m1=1500d*150/100;
				m2=300d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				if(sum <= 30000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
					
				}
				if(sum2 <= 30000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
							}
						}
					}
				}
				//如果注册用户的上上级用户是项目方
			}else if((user3.getUserType()==2||user3.getUserType()==3)){	//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				m0=30000d;
				m1=1500d*150/100;
				m2=300d*150/100;
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				
				if(sum <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				if(sum2 <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100f;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								
							}
						}
					}
				}
			}
		}
	}
	
	
	private void method3(Integer userId) {
		//获取邀请人的id
		Integer referUserId = user.getReferUserId();
		//获取邀请人的对象
		KFFUser user2 = kffUserService.findByUserId(referUserId);
		
		//判断邀请人是普通用户，并且没有上一级邀请
		if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==0){
			m0=10000d;
			m1=500d;
			//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setAwardBalance(m0);
			kffTokenawardService.save(tokenaward);
			
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			//将注册用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			//从数据表中获取所有注册用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//List<Tokenaward> findByUserId2 = kffTokenawardService.findByUserId(user2.getUserId());
			//获取邀请人邀请的钱数的总额
			Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
			if(sum <= 10000){         
				//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				Tokenaward tokenaward2 = new Tokenaward();
				tokenaward2.setUserId(user2.getUserId());
				tokenaward2.setTokenAwardFunctionType(16);
				tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
				tokenaward2.setInviteRewards(m1);
				tokenaward2.setCreateTime(new Date());
				tokenaward2.setDistributionType(1);
				
				tokenaward2.setAwardBalance(m1);
				kffTokenawardService.save(tokenaward2);
				
				coinProperty.setUserId(user2.getUserId());
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m1);
				
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				Tokenrecords tokenrecords2 = new Tokenrecords();
				tokenrecords2.setUserId(user2.getUserId());
				tokenrecords2.setFunctionDesc("邀请奖励");
				tokenrecords2.setFunctionType(16);
				tokenrecords2.setTradeType(1);
				tokenrecords2.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords2);
				
			}
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				
				CoinProperty coinProperty2 = new CoinProperty();
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
						Double x = rewards/100;
						//如果是注册奖励
						if(awardFunctionType == 16){
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x);
							tokenrecords.setUpdateTime(new Date());
							kffTokenrecordsService.update(tokenrecords);
							tokenaward.setUserId(userId);
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenrecordsService.update(tokenrecords);
							kffTokenawardService.update(tokenaward);
							
							
							KFFUser kffUser = kffUserService.findByUserId(userId);
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
							
						}else if(awardFunctionType == 18){// 如果是邀请好友的奖励
							
							tokenrecords.setUserId(user2.getUserId());
							tokenrecords.setFunctionDesc("邀请奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x); // 发放奖励数
							tokenrecords.setUpdateTime(new Date());
							
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenawardService.update(award);
							
							
							kffTokenrecordsService.update(tokenrecords);
							
							
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
						}
					}
				}
			}
			//判断邀请人是项目方用户，并且没有上一级邀请
		}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==0){
			m0=10000d*150/100;
			m1=500d*150/100;
			//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			kffTokenawardService.save(tokenaward);
			
			
			//将注册用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			//从数据表中获取所有注册用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//获取邀请人邀请的钱数的总额
			Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
			if(sum <= 5000000){
				//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				Tokenaward tokenaward2 = new Tokenaward();
				tokenaward2.setUserId(user2.getUserId());
				tokenaward2.setTokenAwardFunctionType(16);
				tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
				tokenaward2.setInviteRewards(m1);
				tokenaward2.setCreateTime(new Date());
				tokenaward2.setDistributionType(1);
				
				tokenaward2.setAwardBalance(m1);
				
				kffTokenawardService.save(tokenaward2);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty); 
				
				//将注册用户的信息同时存入流水表单中
				Tokenrecords tokenrecords2 = new Tokenrecords();
				tokenrecords2.setUserId(user2.getUserId());
				tokenrecords2.setFunctionDesc("邀请奖励");
				tokenrecords2.setFunctionType(16);
				tokenrecords2.setTradeType(1);
				tokenrecords2.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords2);
				
			}
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				CoinProperty coinProperty2 = new CoinProperty();
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
						Double x = rewards/100;
						//如果是注册奖励
						if(awardFunctionType == 16){
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x);
							tokenrecords.setUpdateTime(new Date());
							tokenaward.setUserId(userId);
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							kffTokenrecordsService.update(tokenrecords);
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findById(userId);
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
						}else if(awardFunctionType == 18){//如果是邀请好友的奖励
							
							tokenrecords.setUserId(user2.getUserId());
							tokenrecords.setFunctionDesc("邀请奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x); // 发放奖励数
							tokenrecords.setUpdateTime(new Date());
							kffTokenrecordsService.update(tokenrecords);
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(user2.getUserId(), kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
						}
					}
				}
			}
			//判断邀请人是普通用户，并且有上一级邀请
		}else if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==1){
			//获取上上级的用户表
			KFFUser user3 = kffUserService.findById(user2.getReferUserId());
			//判断注册用户的上上级是普通用户
			if((user3.getUserType()==1||user3.getUserType()==4)){
				m0=10000d;
				m1=500d;
				m2=100d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				//kffTokenawardService.findByUserId(user2.getUserId());
				if(sum <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setAwardBalance(m1);
					
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user2.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user2.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m1+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				if(sum2 <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
							
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
							}
						}
					}
				}
				//如果注册用户的上上级用户是项目方
			}
			
			else if((user3.getUserType()==2||user3.getUserType()==3)){
				m0=10000d;
				m1=500d*150/100;
				m2=100d*150/100;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				
				if(sum <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				if(sum2 <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100f;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								
							}
						}
					}
				}
			}
			//如果上级邀请人是是项目方，并且有上级邀请人
		}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==1){

			//获取上上级的用户表
			KFFUser user3 = kffUserService.findById(user2.getReferUserId());
			//判断注册用户的上上级是普通用户
			if((user3.getUserType()==1||user3.getUserType()==4)){
				m0=10000d;
				m1=500d*150/100;
				m2=100d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				if(sum <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
					
				}
				if(sum2 <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
							}
						}
					}
				}
				//如果注册用户的上上级用户是项目方
			}else if((user3.getUserType()==2||user3.getUserType()==3)){	//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				m0=10000d;
				m1=500d*150/100;
				m2=100d*150/100;
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				
				if(sum <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				if(sum2 <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100f;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								
							}
						}
					}
				}
			}
		}
	}
	
	
	private void method4(Integer userId) {
		//获取邀请人的id
		Integer referUserId = user.getReferUserId();
		//获取邀请人的对象
		KFFUser user2 = kffUserService.findByUserId(referUserId);
		
		//判断邀请人是普通用户，并且没有上一级邀请
		if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==0){
			m0=0.0000d;
			m1=250d;
			//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setAwardBalance(m0);
			kffTokenawardService.save(tokenaward);
			
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			//将注册用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			//从数据表中获取所有注册用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//List<Tokenaward> findByUserId2 = kffTokenawardService.findByUserId(user2.getUserId());
			//获取邀请人邀请的钱数的总额
			Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
			if(sum <= 10000){         
				//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				Tokenaward tokenaward2 = new Tokenaward();
				tokenaward2.setUserId(user2.getUserId());
				tokenaward2.setTokenAwardFunctionType(16);
				tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
				tokenaward2.setInviteRewards(m1);
				tokenaward2.setCreateTime(new Date());
				tokenaward2.setDistributionType(1);
				
				tokenaward2.setAwardBalance(m1);
				kffTokenawardService.save(tokenaward2);
				
				coinProperty.setUserId(user2.getUserId());
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m1);
				
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				Tokenrecords tokenrecords2 = new Tokenrecords();
				tokenrecords2.setUserId(user2.getUserId());
				tokenrecords2.setFunctionDesc("邀请奖励");
				tokenrecords2.setFunctionType(16);
				tokenrecords2.setTradeType(1);
				tokenrecords2.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords2);
				
			}
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				
				CoinProperty coinProperty2 = new CoinProperty();
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
						Double x = rewards/100;
						//如果是注册奖励
						if(awardFunctionType == 16){
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x);
							tokenrecords.setUpdateTime(new Date());
							kffTokenrecordsService.update(tokenrecords);
							tokenaward.setUserId(userId);
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenrecordsService.update(tokenrecords);
							kffTokenawardService.update(tokenaward);
							
							
							KFFUser kffUser = kffUserService.findByUserId(userId);
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
							
						}else if(awardFunctionType == 18){// 如果是邀请好友的奖励
							
							tokenrecords.setUserId(user2.getUserId());
							tokenrecords.setFunctionDesc("邀请奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x); // 发放奖励数
							tokenrecords.setUpdateTime(new Date());
							
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenawardService.update(award);
							
							
							kffTokenrecordsService.update(tokenrecords);
							
							
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
						}
					}
				}
			}
			//判断邀请人是项目方用户，并且没有上一级邀请
		}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==0){
			m0=0.0000d;
			m1=250d*150/100;
			//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
			tokenaward.setUserId(userId);
			tokenaward.setTokenAwardFunctionType(16);
			tokenaward.setTokenAwardFunctionDesc("注册奖励");
			tokenaward.setInviteRewards(m0);
			tokenaward.setCreateTime(new Date());
			tokenaward.setDistributionType(1);
			tokenaward.setAwardBalance(m0);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			kffTokenawardService.save(tokenaward);
			
			
			//将注册用户的信息同时存入流水表单中
			tokenrecords.setUserId(userId);
			tokenrecords.setFunctionDesc("注册奖励");
			tokenrecords.setFunctionType(16);
			tokenrecords.setTradeType(1);
			tokenrecords.setCreateTime(new Date());
			kffTokenrecordsService.save(tokenrecords);
			
			
			coinProperty.setUserId(userId);
			coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
			coinProperty.setCoinDistributed(m0);
			kffCoinPropertyService.save(coinProperty);
			
			//从数据表中获取所有注册用户的奖励数据
			List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
			//获取邀请人邀请的钱数的总额
			Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
			if(sum <= 5000000){
				//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				Tokenaward tokenaward2 = new Tokenaward();
				tokenaward2.setUserId(user2.getUserId());
				tokenaward2.setTokenAwardFunctionType(16);
				tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
				tokenaward2.setInviteRewards(m1);
				tokenaward2.setCreateTime(new Date());
				tokenaward2.setDistributionType(1);
				
				tokenaward2.setAwardBalance(m1);
				
				kffTokenawardService.save(tokenaward2);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty); 
				
				//将注册用户的信息同时存入流水表单中
				Tokenrecords tokenrecords2 = new Tokenrecords();
				tokenrecords2.setUserId(user2.getUserId());
				tokenrecords2.setFunctionDesc("邀请奖励");
				tokenrecords2.setFunctionType(16);
				tokenrecords2.setTradeType(1);
				tokenrecords2.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords2);
				
			}
			for (Tokenaward award : list) {
				//获取每个奖励已经奖励的次数
				Integer counter = award.getCounter();
				//获取发放的类型
				Integer distributionType = award.getDistributionType();
				//获取每个发放的金额
				Double rewards = award.getInviteRewards();
				//获取奖励的类型
				Integer awardFunctionType = award.getTokenAwardFunctionType();
				//如果奖励没有发放完毕并且是线性发放
				CoinProperty coinProperty2 = new CoinProperty();
				if(counter<100 && distributionType == 1 ){
					if(award.getGrantType()==2) { 
						Double x = rewards/100;
						//如果是注册奖励
						if(awardFunctionType == 16){
							tokenrecords.setUserId(userId);
							tokenrecords.setFunctionDesc("注册奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x);
							tokenrecords.setUpdateTime(new Date());
							tokenaward.setUserId(userId);
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							kffTokenrecordsService.update(tokenrecords);
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findById(userId);
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
						}else if(awardFunctionType == 18){//如果是邀请好友的奖励
							
							tokenrecords.setUserId(user2.getUserId());
							tokenrecords.setFunctionDesc("邀请奖励");
							tokenrecords.setFunctionType(awardFunctionType);
							tokenrecords.setAmount(x); // 发放奖励数
							tokenrecords.setUpdateTime(new Date());
							kffTokenrecordsService.update(tokenrecords);
							tokenaward.setUserId(user2.getUserId());
							tokenaward.setTokenAwardFunctionType(awardFunctionType);
							tokenaward.setCounter(counter+1);
							
							Double awardBalance = award.getAwardBalance();
							tokenaward.setAwardBalance(awardBalance-x);
							
							kffTokenawardService.update(tokenaward);
							
							KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
							Double kffCoinNum = kffUser.getKffCoinNum();
							kffUserService.updateUserKFFCoinNum(user2.getUserId(), kffCoinNum+=x);
							
							Double coinLock = coinProperty.getCoinLock();
							Double coinDistributed = coinProperty.getCoinDistributed();
							coinProperty2.setCoinLock(coinLock + x);
							coinProperty2.setCoinDistributed(coinDistributed - x);
							kffCoinPropertyService.update(coinProperty2);
							
						}
					}
				}
			}
			//判断邀请人是普通用户，并且有上一级邀请
		}else if((user2.getUserType()==1||user2.getUserType()==4) && user2.getReferLevel() ==1){
			//获取上上级的用户表
			KFFUser user3 = kffUserService.findById(user2.getReferUserId());
			//判断注册用户的上上级是普通用户
			if((user3.getUserType()==1||user3.getUserType()==4)){
				m0=0.0000d;
				m1=250d;
				m2=50d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				//kffTokenawardService.findByUserId(user2.getUserId());
				if(sum <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					tokenaward2.setAwardBalance(m1);
					
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user2.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user2.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m1+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				if(sum2 <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
							
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
							}
						}
					}
				}
				//如果注册用户的上上级用户是项目方
			}
			
			else if((user3.getUserType()==2||user3.getUserType()==3)){
				m0=0.0000d;
				m1=250d;
				m2=50d*150/100;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				
				if(sum <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				if(sum2 <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100f;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								
							}
						}
					}
				}
			}
			//如果上级邀请人是是项目方，并且有上级邀请人
		}else if((user2.getUserType()==2||user2.getUserType()==3) && user2.getReferLevel() ==1){

			//获取上上级的用户表
			KFFUser user3 = kffUserService.findById(user2.getReferUserId());
			//判断注册用户的上上级是普通用户
			if((user3.getUserType()==1||user3.getUserType()==4)){
				m0=0.0000d;
				m1=250d*150/100;
				m2=50d;
				//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				if(sum <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
					
				}
				if(sum2 <= 10000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
							}
						}
					}
				}
				//如果注册用户的上上级用户是项目方
			}else if((user3.getUserType()==2||user3.getUserType()==3)){	//将注册用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
				m0=0.0000d;
				m1=250d*150/100;
				m2=50d*150/100;
				tokenaward.setUserId(userId);
				tokenaward.setTokenAwardFunctionType(16);
				tokenaward.setTokenAwardFunctionDesc("注册奖励");
				tokenaward.setInviteRewards(m0);
				tokenaward.setCreateTime(new Date());
				tokenaward.setDistributionType(1);
				tokenaward.setAwardBalance(m0);
				tokenaward.setUserName(user.getUserName());
				tokenaward.setMobile(user.getMobile());
				kffTokenawardService.save(tokenaward);
				
				coinProperty.setUserId(userId);
				coinProperty.setCoinLock(kffUserService.findById(userId).getKffCoinNum());
				coinProperty.setCoinDistributed(m0);
				kffCoinPropertyService.save(coinProperty);
				
				//将注册用户的信息同时存入流水表单中
				tokenrecords.setUserId(userId);
				tokenrecords.setFunctionDesc("注册奖励");
				tokenrecords.setFunctionType(16);
				tokenrecords.setTradeType(1);
				tokenrecords.setCreateTime(new Date());
				kffTokenrecordsService.save(tokenrecords);
				
				//从数据表中获取所有注册用户的奖励数据
				List<Tokenaward> list = kffTokenawardService.findByUserId(userId);
				//获取上级邀请人邀请的钱数的总额
				Double sum = kffTokenawardService.reawardSum(user2.getUserId(),18);
				//获取上上级邀请人邀请的钱数的总额
				Double sum2 = kffTokenawardService.reawardSum(user3.getUserId(),18);
				
				if(sum <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenaward2 = new Tokenaward();
					tokenaward2.setUserId(user2.getUserId());
					tokenaward2.setTokenAwardFunctionType(18);
					tokenaward2.setTokenAwardFunctionDesc("邀请奖励");
					tokenaward2.setInviteRewards(m1);
					tokenaward2.setCreateTime(new Date());
					tokenaward2.setDistributionType(1);
					kffTokenawardService.save(tokenaward2);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user2.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
				}
				if(sum2 <= 5000000){
					//将邀请用户id 奖励类型 奖励金额 当前时间 发放的方式存入奖励表中
					Tokenaward tokenawarde3 = new Tokenaward();
					tokenawarde3.setUserId(user3.getUserId());
					tokenawarde3.setTokenAwardFunctionType(18);
					tokenawarde3.setTokenAwardFunctionDesc("邀请奖励");
					tokenawarde3.setInviteRewards(m2);
					tokenawarde3.setCreateTime(new Date());
					tokenawarde3.setDistributionType(1);
					tokenawarde3.setAwardBalance(m2);
					kffTokenawardService.save(tokenawarde3);
					//将注册用户的信息同时存入流水表单中
					Tokenrecords tokenrecords2 = new Tokenrecords();
					tokenrecords2.setUserId(user3.getUserId());
					tokenrecords2.setFunctionDesc("邀请奖励");
					tokenrecords2.setFunctionType(16);
					tokenrecords2.setTradeType(1);
					tokenrecords2.setCreateTime(new Date());
					kffTokenrecordsService.save(tokenrecords2);
					
					coinProperty.setUserId(user3.getUserId());
					coinProperty.setCoinLock(kffUserService.findById(user3.getUserId()).getKffCoinNum());
					CoinProperty coinProperty = kffCoinPropertyService.findByUserId(user2.getUserId());
					coinProperty.setCoinDistributed(m2+coinProperty.getCoinDistributed());
					kffCoinPropertyService.save(coinProperty);
					
				}
				for (Tokenaward award : list) {
					//获取每个奖励已经奖励的次数
					Integer counter = award.getCounter();
					//获取发放的类型
					Integer distributionType = award.getDistributionType();
					//获取每个发放的金额
					Double rewards = award.getInviteRewards();
					//获取奖励的类型
					Integer awardFunctionType = award.getTokenAwardFunctionType();
					//如果奖励没有发放完毕并且是线性发放
					if(counter<100 && distributionType == 1 ){
						if(award.getGrantType()==2) { 
							Double x = rewards/100f;
							//如果是注册奖励
							if(awardFunctionType == 16){
								tokenrecords.setUserId(userId);
								tokenrecords.setFunctionDesc("注册奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x);
								tokenrecords.setUpdateTime(new Date());
								kffTokenrecordsService.update(tokenrecords);
								tokenaward.setUserId(userId);
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								Double awardBalance = award.getAwardBalance();
								kffTokenawardService.update(award);
								tokenaward.setAwardBalance(awardBalance-x);
								
								KFFUser kffUser = kffUserService.findByUserId(userId);
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								Double coinLock = coinProperty.getCoinLock();
								CoinProperty coinProperty2 = new CoinProperty();
								Double coinDistributed = coinProperty.getCoinDistributed();
								coinProperty2.setCoinLock(coinLock + x);
								coinProperty2.setCoinDistributed(coinDistributed - x);
								kffCoinPropertyService.update(coinProperty2);
								
							}else if(awardFunctionType == 18){//如果是邀请好友的奖励
								
								tokenrecords.setUserId(user2.getUserId());
								tokenrecords.setFunctionDesc("邀请奖励");
								tokenrecords.setFunctionType(awardFunctionType);
								tokenrecords.setAmount(x); // 发放奖励数
								tokenrecords.setUpdateTime(new Date());
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								
								Double awardBalance = award.getAwardBalance();
								tokenaward.setAwardBalance(awardBalance-x);
								
								kffTokenawardService.update(award);
								
								
								kffTokenrecordsService.update(tokenrecords);
								
								
								tokenaward.setUserId(user2.getUserId());
								tokenaward.setTokenAwardFunctionType(awardFunctionType);
								tokenaward.setCounter(counter+1);
								kffTokenawardService.update(tokenaward);
								
								KFFUser kffUser = kffUserService.findByUserId(user2.getUserId());
								Double kffCoinNum = kffUser.getKffCoinNum();
								kffUserService.updateUserKFFCoinNum(userId, kffCoinNum+=x);
								
								
							}
						}
					}
				}
			}
		}
	}
	
}







