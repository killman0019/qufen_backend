package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.DateUtil;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.coinproperty.CoinPropertyMapper;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFCoinPropertyService")
@Transactional(rollbackFor = Exception.class)
public class CoinPropertyService {

	@Autowired
	private CoinPropertyMapper coinPropertyMapper;
	@Autowired
	private TokenrecordsService kffTokenrecordsService;

	// 用户发布悬赏减少token币
	public boolean countReduceForReward(CoinProperty coinProty, BigDecimal rewardMoney, Integer postId) {
		// 修改token币数据
		BigDecimal coinLock = StringUtil.toBeBigDecimal(coinProty.getCoinLock().toString());
		BigDecimal senCount = StringUtil.subBigDecimal(coinLock, rewardMoney);
		coinProty.setCoinLock(Double.valueOf(senCount.toString()));
		coinPropertyMapper.update(coinProty);
		// 添加token币的流水
		Date date = new Date();
		Tokenrecords tokenrecords = new Tokenrecords();
		tokenrecords.setUserId(coinProty.getUserId());
		String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
		String replaceAllDate = stringDate.replaceAll("-", "");
		String format = String.format("%010d", coinProty.getUserId());
		tokenrecords.setTradeCode("08" + replaceAllDate + format); // 交易流水号
		tokenrecords.setTradeType(2); // 交易类型 1-收入 2-支出
		tokenrecords.setFunctionDesc(sysGlobals.REWARD_DESC); // 交易类型
		tokenrecords.setFunctionType(sysGlobals.REWARD_TYPE); // 交易的类型
		tokenrecords.setAmount(rewardMoney); // 发放奖励数
		tokenrecords.setTradeDate(date); // 交易日期
		tokenrecords.setCreateTime(date);
		tokenrecords.setUpdateTime(date);
		tokenrecords.setStatus(1);
		tokenrecords.setMemo(sysGlobals.REWARD_MENO);
		tokenrecords.setRewardGrantType(1); // 发放类型 1-一次性发放 2-线性发放
		tokenrecords.setPostId(postId);
		kffTokenrecordsService.save(tokenrecords);
		return true;
	}

	// 悬赏终止，返还用户的token币
	public boolean countAddForReward(CoinProperty coinProty, BigDecimal rewardMoney, Integer postId) {
		// 修改token币数据
		BigDecimal coinLock = StringUtil.toBeBigDecimal(coinProty.getCoinLock().toString());
		BigDecimal senCount = StringUtil.addBigDecimal(coinLock, rewardMoney);
		coinProty.setCoinLock(Double.valueOf(senCount.toString()));
		coinPropertyMapper.update(coinProty);
		// 添加token币的流水
		Date date = new Date();
		Tokenrecords tokenrecords = new Tokenrecords();
		tokenrecords.setUserId(coinProty.getUserId());
		String stringDate = DateUtil.getDate(date, "yyyy-MM-dd");
		String replaceAllDate = stringDate.replaceAll("-", "");
		String format = String.format("%010d", coinProty.getUserId());
		tokenrecords.setTradeCode("08" + replaceAllDate + format); // 交易流水号
		tokenrecords.setTradeType(1); // 交易类型 1-收入 2-支出
		tokenrecords.setFunctionDesc(sysGlobals.END_REWARD_DESC); // 交易类型
		tokenrecords.setFunctionType(sysGlobals.END_REWARD_TYPE); // 交易的类型
		tokenrecords.setAmount(rewardMoney); // 发放奖励数
		tokenrecords.setTradeDate(date); // 交易日期
		tokenrecords.setCreateTime(date);
		tokenrecords.setUpdateTime(date);
		tokenrecords.setStatus(1);
		tokenrecords.setMemo(sysGlobals.END_REWARD_MENO);
		tokenrecords.setRewardGrantType(1); // 发放类型 1-一次性发放 2-线性发放
		tokenrecords.setPostId(postId);
		kffTokenrecordsService.save(tokenrecords);
		return true;
	}

	@Transactional(readOnly = true)
	public CoinProperty findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return coinPropertyMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		coinPropertyMapper.deleteById(id);
	}

	@Transactional
	public void save(CoinProperty coinProperty) throws RestServiceException {
		coinPropertyMapper.save(coinProperty);
	}

	public void updateByMap(Map<String, Object> map) throws RestServiceException {
		coinPropertyMapper.updateByMap(map);
	}

	public void update(CoinProperty coinPropertyId) throws RestServiceException {
		if (coinPropertyId.getUserId() == null) {
			throw new RestServiceException("id不能为空");
		}
		coinPropertyMapper.update(coinPropertyId);
	}

	@Transactional(readOnly = true)
	public List<CoinProperty> findListByAttr(Map<String, Object> map) {
		return coinPropertyMapper.findListByAttr(map);
	}

	@Transactional(readOnly = true)
	public PageResult<CoinProperty> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<CoinProperty> result = null;
		try {
			Integer count = coinPropertyMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<CoinProperty> list = coinPropertyMapper.findPage(query.getQueryData());
				result = new PageResult<CoinProperty>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public CoinProperty findByUserId(Integer userid) throws RestServiceException {
		// CoinProperty coinProperty = coinPropertyMapper.findByUserId(userid);
		return coinPropertyMapper.findByUserId(userid);
	}

	public List<CoinProperty> findAllCoinpropertyByWhere(Map<String, Object> map) {

		return coinPropertyMapper.findAllCoinpropertyByWhere(map);
	}

	public List<CoinProperty> findByUserIds(Integer userId) {

		return coinPropertyMapper.findByUserIds(userId);
	}

	public List<CoinProperty> findCoinPropertyByUserId(Integer userId) {
		return coinPropertyMapper.findCoinPropertyByUserId(userId);
	}

	public List<CoinProperty> findAllCoinpropertyCoinUnlock(Integer userId, Double coinUnlock) {

		return coinPropertyMapper.findAllCoinpropertyCoinUnlock(userId, coinUnlock);
	}

	/**
	 * 
	 * TODO
	 * @param userId 用户id
	 * @param tokenrecords 交易对象
	 * @param type 类型   1加  2  减
	 * @author zhangdd
	 * @data 2018年8月16日
	 *
	 */
	public void updateCoin(Tokenrecords tokenrecords, Integer type) {

		CoinProperty coinPropertyUser = coinPropertyMapper.findByUserId(tokenrecords.getUserId()); // 接受打赏的人
		if (null != coinPropertyUser) {

			Double coinLockSend = coinPropertyUser.getCoinLock();
			if (type == 1) { // 加法
				coinLockSend = new BigDecimal(Double.toString(coinLockSend)).add(tokenrecords.getAmount()).doubleValue();
			} else if (type == 2) {// 减法
				coinLockSend = new BigDecimal(Double.toString(coinLockSend)).subtract(tokenrecords.getAmount()).doubleValue();
			}
			coinPropertyUser.setCoinLock(coinLockSend);
			coinPropertyMapper.update(coinPropertyUser);
		}
	}
}
