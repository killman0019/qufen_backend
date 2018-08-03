package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.transactionpair.TransactionPair;
import com.tzg.entitys.kff.transactionpair.TransactionPairMapper;
import com.tzg.entitys.kff.transactionpair.TransactionPairResponse;

@Service(value = "transactionPairService")
@Transactional
public class TransactionPairService {
	@Autowired
	private TransactionPairMapper transactionPairMapper;

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

}
