package com.tzg.service.tokenaward;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.stat.TableStat.Name;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.HttpSessionUtil;
import com.tzg.entitys.leopard.console.ConsoleLoginAccount;
import com.tzg.entitys.tokenaward.Tokenaward;
import com.tzg.entitys.tokenaward.TokenawardMapper;
import com.tzg.entitys.tokenrecords.Tokenrecords;
import com.tzg.entitys.tokenrecords.TokenrecordsMapper;
import com.tzg.entitys.user.User;
import com.tzg.entitys.user.UserMapper;
import com.tzg.service.user.UserService;

@Service(value="KFFTokenawardService")
@Transactional
public class TokenawardService {

	@Autowired
	private TokenawardMapper tokenawardMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userservice;
	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;
	@Transactional(readOnly=true)
    public List<Tokenaward> findByUserId(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return tokenawardMapper.findByUserId(id);
    }
	@Transactional(readOnly=true)
	public List<Tokenaward> priaiseAwardSum(java.lang.Integer userId) throws Exception {
		if(userId == null){
			throw new Exception("id不能为空");
		}
		return tokenawardMapper.priaiseAwardSum(userId);
	}
	
	public Tokenaward findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return tokenawardMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	tokenawardMapper.deleteById(id);
    }
	
	public void save(Tokenaward tokenaward) throws Exception {	   

		try {
			    tokenaward.setAwardBalance(tokenaward.getRewardToken());
			 //   User user = userMapper.findByMobile(tokenaward.getMobile());
			    System.err.println(tokenaward.getMobile());
			    System.err.println("发放备注:     "+tokenaward.getRemark());
			    Tokenrecords tokenrecords = new Tokenrecords();
			    User user = userservice.findByMobile(tokenaward.getMobile());
			    if(tokenaward.getDistributionType()==1) {
			    	
			    	Double kffCoinNum = user.getKffCoinNum();
			    	Double nviteRewards = tokenaward.getInviteRewards();
			    	kffCoinNum =kffCoinNum + nviteRewards;
			    	user.setKffCoinNum(kffCoinNum);
			    	userMapper.update(user);
			    	
			    	tokenrecords.setUserId(user.getUserId());
			    	tokenrecords.setTradeCode(" ");
			    	tokenrecords.setTradeType(1);
			    	tokenrecords.setFunctionType(tokenaward.getTokenAwardFunctionType());
			    	tokenrecords.setAmount(tokenaward.getInviteRewards());
			    	tokenrecords.setTradeDate(new Date());
			    	tokenrecords.setBalance(tokenaward.getInviteRewards());
			    	tokenrecords.setCreateTime(new Date());
			    	tokenrecords.setStatus(1);
			    	tokenrecords.setMemo(tokenaward.getRemark());
			    	tokenrecords.setRewardGrantType(2);
			    	tokenrecordsMapper.save(tokenrecords);
			    }
			 //   System.out.println("======================================"+user.getUserId());
			    tokenaward.setUserId(user.getUserId());
			    tokenaward.setUserName(user.getUserName());
			    tokenaward.setAwardBalance(tokenaward.getInviteRewards());
			    tokenaward.setCreateTime(new Date());
			    tokenaward.setCounter(0);
			    tokenaward.setDistributionType(tokenaward.getDistributionType());
			    tokenaward.setUpdateTime(new Date());
			    
			    tokenrecords.setUserId(user.getUserId());
		    	tokenrecords.setTradeCode(" ");
		    	tokenrecords.setTradeType(1);
		    	tokenrecords.setFunctionType(tokenaward.getTokenAwardFunctionType());
		    	tokenrecords.setAmount(tokenaward.getInviteRewards());
		    	tokenrecords.setTradeDate(new Date());
		    	tokenrecords.setBalance(tokenaward.getInviteRewards());
		    	tokenrecords.setCreateTime(new Date());
		    	tokenrecords.setStatus(1);
		    	tokenrecords.setMemo(tokenaward.getRemark());
		    	tokenrecords.setRewardGrantType(1);
		    	tokenrecordsMapper.save(tokenrecords);
			  //  tokenaward.setTokenAwardFunctionDesc(tokenaward.getTokenAwardFunctionDesc());
			    ConsoleLoginAccount loginSession = HttpSessionUtil.getLoginSession();
			    String vcLoginName = loginSession.getVcLoginName();
			    tokenaward.setIssuer(vcLoginName);
			 //   tokenaward.setRemark("发放备注");
			    String string = new String(tokenaward.getRemark().getBytes("ISO-8859-1"), "UTF-8");
			    tokenaward.setRemark(string);
			    
			    
				tokenawardMapper.add(tokenaward);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		
		tokenawardMapper.save(tokenaward);
	}
	
	public void update(Tokenaward tokenaward) throws Exception {	
		if(tokenaward.getTokenRecordsId() == null){
			throw new Exception("id不能为空");
		}
		tokenawardMapper.update(tokenaward);
	}	
	public Double reawardSum(Integer userId,Integer functionType) throws Exception {	
		if(userId == null || functionType == null){
			throw new Exception("传入参数不能为空");
		}
		return tokenawardMapper.reawardSum(userId,functionType);
	}	
	public Double findUserSumInviteRewards(Integer userId,Integer functionType) throws Exception {	
		if(userId == null || functionType == null){
			throw new Exception("传入参数不能为空");
		}
		return tokenawardMapper.findUserSumInviteRewards(userId,functionType);
	}	
	public Double findUserSumRewardToken(Integer userId,Integer functionType) throws Exception {	
		if(userId == null || functionType == null){
			throw new Exception("传入参数不能为空");
		}
		return tokenawardMapper.findUserSumRewardToken(userId,functionType);
	}
	public  List<Tokenaward> findAllTokenawardByWhere(Map<String, Object> map) {
	
		return tokenawardMapper.findAllTokenawardByWhere (map);
	}
	public void updateByGrantType(Integer a) {

		tokenawardMapper.updateByGrantType(a);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Tokenaward> findPage(PaginationQuery query) throws Exception {
		PageResult<Tokenaward> result = null;
		try {
			Integer count = tokenawardMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Tokenaward> list = tokenawardMapper.findPage(query.getQueryData());
/*				for (Tokenaward tokenaward : list) {
					Integer userId = tokenaward.getUserId();
					User findById = userMapper.findById(userId);
					String userName = findById.getUserName();
					String mobile = findById.getMobile();
					tokenaward.setUserName(userName);
					tokenaward.setMobile(mobile);
					
				}
*/				result = new PageResult<Tokenaward>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public PageResult<Tokenaward> findPageSelect(PaginationQuery query, Map<String, String> hashMap) {
		PageResult<Tokenaward> result = null;
		try {
			Integer count = tokenawardMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				// 遍历map
				for(String key : hashMap.keySet()) {
					query.addQueryData(key, hashMap.get(key));
				}
				
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				
				List<Tokenaward> list = tokenawardMapper.findPageSelect(query.getQueryData());
				result = new PageResult<Tokenaward>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	public static void main(String[] args) {
		Map<String,String> hashMap = new HashMap<>();
		hashMap.put("userName", "11");
		hashMap.put("password", "passwod");
		hashMap.put("addd", "add");
		for(String key : hashMap.keySet()) {
			System.err.println("key: " + key);
			System.err.println("value: " + hashMap.get(key));
		}
	}
	public void add(Tokenaward tokenaward) {
		try {
			    tokenaward.setAwardBalance(tokenaward.getRewardToken());
			 //   User user = userMapper.findByMobile(tokenaward.getMobile());
			    User user = userservice.findByMobile(tokenaward.getMobile());
			    Double kffCoinNum = user.getKffCoinNum();
			    Double rewardToken = tokenaward.getRewardToken();
			    kffCoinNum += rewardToken;
			    user.setKffCoinNum(kffCoinNum);
			    userMapper.update(user);
			    
			    tokenaward.setUserName(user.getUserName());
			    tokenaward.setAwardBalance(tokenaward.getRewardToken());
			    tokenaward.setCreateTime(new Date());
			    tokenaward.setCounter(0);
			    tokenaward.setDistributionType(tokenaward.getDistributionType());
			    tokenaward.setUpdateTime(new Date());
			    
				tokenawardMapper.add(tokenaward);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
