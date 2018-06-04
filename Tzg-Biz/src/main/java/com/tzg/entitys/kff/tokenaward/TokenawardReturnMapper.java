package com.tzg.entitys.kff.tokenaward;

import com.tzg.common.base.BaseMapper;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.tokenaward.TokenawardReturn;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface TokenawardReturnMapper extends BaseMapper<TokenawardReturn, java.lang.Integer> {

	Integer findPageCounts(Map<String, Object> queryData);

	List<TokenawardReturn> findPageToken(Map<String, Object> queryData);


}
