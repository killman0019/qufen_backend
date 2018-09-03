package com.tzg.entitys.kff.tokenrecords;

import com.tzg.common.base.BaseMapper;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tokenaward.TokenawardReturn;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface TokenrecordsMapper extends BaseMapper<Tokenrecords, java.lang.Integer> {	

	Tokenrecords findByUserIdAndFunctionType(Integer userId);	

	PageResult<TokenawardReturn> findPageMyTokenawardReturn(PaginationQuery query);

	List<Tokenrecords> findAllTokenrecordsUserId(Integer userId);

	Double findTodayToken(Map<String, Object> map);

	List<Tokenrecords> findByMap(Map<String, Object> tokenMap);	

}
