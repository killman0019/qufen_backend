package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.bgroupmustread.BGroupMustRead;
import com.tzg.entitys.kff.bgroupmustread.BGroupMustReadMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "BGroupMustReadService")
@Transactional
public class BGroupMustReadService {
	@Autowired
	private BGroupMustReadMapper bGroupMustReadMapper;

	public PageResult<BGroupMustRead> findPage(PaginationQuery query) throws RestServiceException {
		// TODO 在数据库中查询所有必读的对象
		PageResult<BGroupMustRead> bGroupNustReadPage = null;
		try {
			Integer count = bGroupMustReadMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<BGroupMustRead> BgroupMustreadList = bGroupMustReadMapper.findPage(query.getQueryData());
				bGroupNustReadPage = new PageResult<BGroupMustRead>(BgroupMustreadList, count, query);
			}
		} catch (Exception e) {
			// TODO: 抛出异常
			e.printStackTrace();
		}

		return bGroupNustReadPage;
	}

}
