package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.enums.RewardDetailStatus;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.RewardDetail;
import com.tzg.entitys.kff.activity.RewardDetailMapper;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class RewardDetailService {

	@Autowired
	private RewardDetailMapper rewardDetailMapper;
	@Autowired
	private MiningActivityService miningActivityService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TokenrecordsService kffTokenrecordsService;
	@Autowired
	private TokenawardService tokenawardService;
	
	@Transactional(readOnly = true)
	public RewardDetail findById(Integer id) throws RestServiceException {
		return rewardDetailMapper.findById(id);
	}


	public void save(RewardDetail rewardDetail) throws RestServiceException {
		rewardDetailMapper.save(rewardDetail);
	}

	public void update(RewardDetail rewardDetail) throws RestServiceException {
		rewardDetailMapper.update(rewardDetail);
	}

	public List<RewardDetail> findListByAttr(Map<String, Object> map) {
		return rewardDetailMapper.findListByAttr(map);
	}
	
	@Transactional
	public MiningActivity add(MiningActivity mnAct,Integer userId) {
		KFFUser user = userService.findById(userId);
		if(null==user) {
			throw new RuntimeException("未找到该用户");
		}
		KFFProject project = projectService.findById(mnAct.getProjectId());
		if(null==project) {
			throw new RuntimeException("未找到该项目");
		}
		
		Date date = new Date();
		RewardDetail rewardDetail = new RewardDetail();
		rewardDetail.setCreatedAt(date);
		rewardDetail.setUpdatedAt(date);
		rewardDetail.setActivityId(mnAct.getId());
		rewardDetail.setUserId(userId);
		rewardDetail.setUserName(user.getUserName());
		rewardDetail.setMobile(user.getMobile());
		rewardDetail.setTitle(mnAct.getTitle());
		rewardDetail.setReward(mnAct.getTokenEveryCount());
		rewardDetail.setTokenName(mnAct.getTokenName());
		BigDecimal tokenNumc = new BigDecimal(mnAct.getTokenNum().toString());
		rewardDetail.setCash(StringUtil.divBigDecimal(mnAct.getTokenCash(), tokenNumc));
		rewardDetail.setProjectId(mnAct.getProjectId());
		rewardDetail.setProjectCode(mnAct.getProjectCode());
		rewardDetail.setProjectIcon(project.getProjectIcon());
		//假如奖励不是为FIND币，则直接往activity_reward_detail奖励表插入一条待发放的记录，
		//如果为FIND币，直接往activity_reward_detail奖励表插入一条已发放的记录，需要立即给用户增加相应的FIND
		
		if(!mnAct.getTokenName().equals(sysGlobals.QDKL_CION)) {
			rewardDetail.setStatus(RewardDetailStatus.UNSEND.getValue());
		}else {
			//奖励是公司的FIND币那么直接给用户发放就可以了
			rewardDetail.setStatus(RewardDetailStatus.SENDSUCCESS.getValue());
			//插入币的交易流水表？
			Tokenrecords tokenrecords = new Tokenrecords();
			tokenrecords.setUserId(userId);
			String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
			String replaceAllDate = stringDate.replaceAll("-", "");
			String format = String.format("%010d", userId);
			tokenrecords.setTradeCode("08" + replaceAllDate + format); // 交易流水号
			tokenrecords.setTradeType(1); // 交易类型 1-收入 2-支出
			tokenrecords.setFunctionDesc(sysGlobals.SET_FUNCTION_DESC); // 交易类型
			tokenrecords.setFunctionType(sysGlobals.SET_FUNCTION_TYPE); // 交易的类型
			tokenrecords.setAmount(mnAct.getTokenEveryCount()); // 发放奖励数
			tokenrecords.setTradeDate(date); // 交易日期
			tokenrecords.setBalance(user.getKffCoinNum());
			tokenrecords.setCreateTime(date);
			tokenrecords.setUpdateTime(date);
			tokenrecords.setStatus(1);
			tokenrecords.setMemo(sysGlobals.SET_MENO);
			tokenrecords.setRewardGrantType(1); // 发放类型 1-一次性发放 2-线性发放
			kffTokenrecordsService.save(tokenrecords);
			if(tokenrecords.getTokenRecordsId()==null) {
				throw new RuntimeException("发放异常，请重新领取！");
			}
			Tokenaward tokenaward = new Tokenaward();
			tokenaward.setUserId(userId);
			tokenaward.setTokenRecordsId(tokenrecords.getTokenRecordsId());
			tokenaward.setTokenAwardFunctionDesc(sysGlobals.SET_TOKENAWARD_FUNCTIONDESC);
			tokenaward.setTokenAwardFunctionType(sysGlobals.SET_TOKENAWARD_FUNCTIONTYPE);
			tokenaward.setContainerAward(Integer.valueOf(mnAct.getTokenEveryCount().toString()));
			tokenaward.setCreateTime(date);
			tokenaward.setUpdateTime(date);
			tokenaward.setRewardToken(Double.valueOf(mnAct.getTokenEveryCount().toString()));
			tokenaward.setAwardBalance(Double.valueOf(user.getKffCoinNum().toString()));
			tokenaward.setDistributionType(2);
			tokenaward.setGrantType(1);
			tokenaward.setUserName(user.getUserName());
			tokenaward.setMobile(user.getMobile());
			tokenaward.setIssuer(sysGlobals.SET_ISSUER);
			tokenaward.setRemark(sysGlobals.SET_REMARK);
			tokenawardService.save(tokenaward);
			//奖励是公司的FIND币那么直接给用户发放就可以了
			user.setKffCoinNum(StringUtil.addBigDecimal(user.getKffCoinNum(), mnAct.getTokenEveryCount()));
		}
		rewardDetailMapper.save(rewardDetail);
		//对活动表进行操作
		mnAct.setTokenUnclaimed(StringUtil.subBigDecimal(mnAct.getTokenUnclaimed(), mnAct.getTokenEveryCount()));
		mnAct.setTokenSurplusNum(mnAct.getTokenSurplusNum()-1);
		mnAct.setVersion(mnAct.getVersion()+1);
		Integer num = miningActivityService.updateByOptimisticLock(mnAct);
		if(num<1) {
			throw new RuntimeException("数据异常，请重新领取！");
		}
		return mnAct;
	}
	
	
	@Transactional(readOnly=true)
	public PageResult<RewardDetail> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<RewardDetail> result = null;
		try {
			Integer count = rewardDetailMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<RewardDetail> list = rewardDetailMapper.findPage(query.getQueryData());
				result = new PageResult<RewardDetail>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
