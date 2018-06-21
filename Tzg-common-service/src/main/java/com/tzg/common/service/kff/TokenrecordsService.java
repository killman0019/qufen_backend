package com.tzg.common.service.kff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tokenaward.Tokenaward;
import com.tzg.entitys.kff.tokenaward.TokenawardMapper;
import com.tzg.entitys.kff.tokenaward.TokenawardReturn;
import com.tzg.entitys.kff.tokenaward.TokenawardReturnMapper;
import com.tzg.entitys.kff.tokenaward.TokenawardReturn;
import com.tzg.entitys.kff.tokenrecords.Tokenrecords;
import com.tzg.entitys.kff.tokenrecords.TokenrecordsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFTokenrecordsService")
@Transactional
public class TokenrecordsService   {

	@Autowired
	private TokenrecordsMapper tokenrecordsMapper;
	@Autowired
	private TokenawardMapper tokenawardMapper;
	@Autowired
	private TokenawardReturnMapper tokenawardReturnMapper;

	@Transactional(readOnly = true)
	public Tokenrecords findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
        return tokenrecordsMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
		tokenrecordsMapper.deleteById(id);
	}

	public void save(Tokenrecords tokenrecords) throws RestServiceException {
		tokenrecordsMapper.save(tokenrecords);
	}

	public void update(Tokenrecords tokenrecords) throws RestServiceException {
		if (tokenrecords.getTokenRecordsId() == null) {
			throw new RestServiceException("id不能为空");
		}
		tokenrecordsMapper.update(tokenrecords);
	}

	@Transactional(readOnly = true)
	public PageResult<Tokenrecords> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Tokenrecords> result = null;
		try {
			Integer count = tokenrecordsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Tokenrecords> list = tokenrecordsMapper.findPage(query.getQueryData());
				result = new PageResult<Tokenrecords>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Tokenrecords findByUserIdAndFunctionType(Integer userId) {
		return tokenrecordsMapper.findByUserIdAndFunctionType(userId);
	}

	public PageResult<TokenawardReturn> findPageMyTokenawardReturn(PaginationQuery query) {
		PageResult<TokenawardReturn> result = null;
		try {

			Integer count = tokenawardReturnMapper.findPageCounts(query.getQueryData());
			System.err.println(count);
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<TokenawardReturn> list = tokenawardReturnMapper.findPageToken(query.getQueryData());
				
				result = new PageResult<TokenawardReturn>(list, count, query);
			}
		} catch (Exception e) {
			throw new RestServiceException("操作有误!");
		}
		return result;
	}

	public List<Tokenrecords> findAllTokenrecordsUserId(Integer userId) {
		// TODO Auto-generated method stub
		return tokenrecordsMapper.findAllTokenrecordsUserId(userId);
	}

	public Double findTodayToken(Integer loginUserId) {
		Double token = 0.00d;
		Map<String, Object> map = new HashMap<>();
		long current=System.currentTimeMillis();//当前时间毫秒数
        long zero=current/(1000*3600*24)*(1000*3600*24)-TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
        long twelve=zero+24*60*60*1000-1;//今天23点59分59秒的毫秒数
        Date createTimeBegin = new Date(zero);
        Date createTimeEnd = new Date(twelve);
		map.put("userId", loginUserId);
		map.put("createTimeBegin", createTimeBegin);
		map.put("createTimeEnd", createTimeEnd);
		token = tokenrecordsMapper.findTodayToken(map);
		if(null  == token)
			token = 0.00d;
		return token;
	}
	
}
