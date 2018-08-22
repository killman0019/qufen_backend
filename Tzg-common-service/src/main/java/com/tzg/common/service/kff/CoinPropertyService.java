package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.coinproperty.CoinProperty;
import com.tzg.entitys.kff.coinproperty.CoinPropertyMapper;
import com.tzg.entitys.kff.qfindex.QfIndex;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFCoinPropertyService")
@Transactional
public class CoinPropertyService {

	@Autowired
	private CoinPropertyMapper coinPropertyMapper;

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

	public void update(CoinProperty coinPropertyId) throws RestServiceException {
		if (coinPropertyId.getUserId() == null) {
			throw new RestServiceException("id不能为空");
		}
		coinPropertyMapper.update(coinPropertyId);
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
		if (null == coinPropertyUser) {
			throw new RestServiceException("个人账单生成异常");
		}
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
