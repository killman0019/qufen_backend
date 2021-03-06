package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.HttpUtil;
import com.tzg.common.utils.getParamFromUrlUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.exchange.Exchange;
import com.tzg.entitys.kff.exchange.ExchangeMapper;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.transactionpair.TransactionPair;
import com.tzg.entitys.kff.transactionpair.TransactionPairMapper;
import com.tzg.entitys.kff.transactionpair.TransactionPairResponse;

@Service(value = "transactionPairService")
@Transactional
public class TransactionPairService {
	@Autowired
	private TransactionPairMapper transactionPairMapper;

	@Autowired
	private ExchangeMapper exchangeMapper;

	@Value("#{paramConfig['DEV_ENVIRONMENT']}")
	private String devEnvironment;

	// private static final ExecutorService newFixedThreadPool =
	// Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4);

	public List<TransactionPair> findByMap(Map<String, String> map) {
		// TODO 根据map进行查询
		return null;
	}

	public PageResult<TransactionPair> findPage(PaginationQuery query) {
		// TODO 根据条件进行分页查询
		PageResult<TransactionPair> transactionPairPage = null;
		Integer count = transactionPairMapper.findPageCount(query.getQueryData());
		if (null != count && count.intValue() > 0) {
			int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
			query.addQueryData("startRecord", Integer.toString(startRecord));
			query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
			List<TransactionPair> transactionPairList = transactionPairMapper.findPage(query.getQueryData());
			transactionPairPage = new PageResult<TransactionPair>(transactionPairList, count, query);
		}
		return transactionPairPage;
	}

	/**
	 * 
	 * TODO 根据交易所获得相关的交易对的数据插入数据库（定时任务目前定10分钟）
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	public void getdatafromUrlByexchangeTask() {
		// 查询所有的交易所 的信息
		// if (StringUtils.isNotBlank(devEnvironment) &&
		// devEnvironment.equals(sysGlobals.DEV_ENVIRONMENT)) {

		ExecutorService newFixedThreadPoolExchange = null;
		try {
			newFixedThreadPoolExchange = Executors.newFixedThreadPool(10);
			// int i = 0;
			// while (true) {
			// i = i + 1;
			List<Exchange> exchangeList = selectAllExchange();

			if (CollectionUtils.isNotEmpty(exchangeList)) {

				try {

					for (Exchange exchange : exchangeList) {

						if (null != exchange) {
							final Exchange exchangeF = exchange;
							newFixedThreadPoolExchange.execute(new Runnable() {

								@Override
								public void run() {

									// TODO 获得数据
									try {
										getdataforUrlByExchangeExecutor(exchangeF);

										Thread.sleep(1000 * 5);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});
							// getdataforUrlByExchangeExecutor(exchangeF);
						}

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.err.println(e.getMessage());

				}
			} /*else if (CollectionUtils.isEmpty(exchangeList)) {
				if (!newFixedThreadPoolExchange.isShutdown()) {
					newFixedThreadPoolExchange.shutdown();
					return;
				}
				System.err.println("结束");
				return;
				}*/
			// }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());

		} finally {
			newFixedThreadPoolExchange.shutdown();
		}
		System.err.println("close+++++++++++++++++++++");
	}

	// }

	/**
	 * 
	 * TODO 根据交易所获得相关的数据
	 * @param exchangeList
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	private void getdataforUrlByExchangeExecutor(Exchange exchange) throws Exception {
		// TODO
		String strUrl = "https://data.block.cc/api/v1/tickers?market=" + exchange.getExchangeName() + "&size=10000";
		List<TransactionPair> transactionPairList = new ArrayList<TransactionPair>();
		Date now = new Date();
		String dataFromUrl = HttpUtil.doGet(strUrl);
		// System.err.println(dataFromUrl);
		if (StringUtils.isNotEmpty(dataFromUrl)) {
			JSONObject jsonObject = new JSONObject(dataFromUrl);
			int code = (int) jsonObject.get("code");
			if (code != 0) {
				System.err.println("url+++++++++++++++++++++++++++++error");
				return;
			}
			if (code == 0) {
				Object data = jsonObject.get("data");
				// System.err.println("data" + data);
				JSONObject jsonData = new JSONObject(data.toString());
				// System.err.println(jsonData);
				JSONArray jsonArray = jsonData.getJSONArray("list");
				// System.err.println(jsonArray);
				for (Object object : jsonArray) {
					JSONObject jsonBase = new JSONObject(object.toString());
					Double usdRate = jsonBase.getDouble("usd_rate");

					String symbolPair = jsonBase.getString("symbol_pair");
					if (StringUtils.isNotEmpty(symbolPair)) {
						String[] split = symbolPair.split("_");
						String mainCode = split[0];
						String coinpair = split[1];

						String exchangeDB = jsonBase.getString("market");
						Double high = jsonBase.getDouble("high");
						Double last = jsonBase.getDouble("last");
						Double low = jsonBase.getDouble("low");
						Double baseVolume = jsonBase.getDouble("base_volume");
						Double changeDaily = jsonBase.getDouble("change_daily");

						Double vol = jsonBase.getDouble("vol");
						TransactionPair transactionPair = new TransactionPair();
						transactionPair.setBaseVolume(baseVolume);
						transactionPair.setChangeDaily(changeDaily);
						transactionPair.setHigh(high);
						transactionPair.setLast(last);
						transactionPair.setLow(low);
						transactionPair.setUsdRate(usdRate);
						transactionPair.setVol(vol);
						transactionPair.setMainCode(mainCode);
						transactionPair.setCoinpair(coinpair);
						transactionPair.setExchangeName(exchangeDB);
						transactionPair.setUpdateTime(now);
						transactionPair.setVaild(1);
						// transactionPairMapper.updateByMainCode(transactionPair);
						transactionPairList.add(transactionPair);
						if (transactionPairList.size() >= 50) {
							if (CollectionUtils.isNotEmpty(transactionPairList)) {
								transactionPairMapper.updateBatch(transactionPairList);
								transactionPairList.clear();
							}
						}
					}

				}
				// 进行数据库的更新
				if (CollectionUtils.isNotEmpty(transactionPairList)) {
					transactionPairMapper.updateBatch(transactionPairList);
				}
			}
		}

	}

	public List<Exchange> selectAllExchange() {
		Map<String, String> exchangeMap = new HashMap<String, String>();
		List<Exchange> exchangeList = exchangeMapper.findByMap(exchangeMap);
		return exchangeList;
	}

	public List<Exchange> selectAllExchange(int i) {
		List<Exchange> exchangeList = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "1");
			PaginationQuery query = new PaginationQuery();
			query.setRowsPerPage(10);
			query.setPageIndex(i);
			query.setQueryData(map);
			exchangeList = selectAllExchangeQuery(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exchangeList;
	}

	private List<Exchange> selectAllExchangeQuery(PaginationQuery query) {
		// TODO Auto-generated method stub
		PageResult<Exchange> result = null;
		try {
			Integer count = exchangeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Exchange> list = exchangeMapper.findPage(query.getQueryData());
				result = new PageResult<Exchange>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.getRows();

	}
}
