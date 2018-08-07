package com.tzg.entitys.kff.transactionpair;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.tzg.common.base.BaseMapper;

@Repository
public interface TransactionPairMapper extends BaseMapper<TransactionPair, Integer> {
	/**
	 * 
	 * TODO 批量更新数据库
	 * @param transactionPairList
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	void updataList(List<TransactionPair> transactionPairList);

	/**
	 * 
	 * TODO
	 * @param transactionPair
	 * @author zhangdd
	 * @data 2018年8月6日
	 *
	 */
	void updateByMainCode(TransactionPair transactionPair);

	/**
	 * 
	 * TODO 批量进行数据更新
	 * @param transactionPairList
	 * @author zhangdd
	 * @data 2018年8月7日
	 *
	 */
	void updateBatch(List<TransactionPair> transactionPairList);

}