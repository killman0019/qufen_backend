package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.content.Navigation;
import com.tzg.entitys.kff.content.NavigationMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class NavigationService {

	@Autowired
	private NavigationMapper navigationMapper;

	@Transactional(readOnly = true)
	public Navigation findById(java.lang.Integer id) throws RestServiceException {
		return navigationMapper.selectByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	public List<Navigation> findListByAttr(Map<String, Object> map) throws RestServiceException {
		return navigationMapper.findListByAttr(map);
	}
	

	@Transactional(readOnly = true)
	public PageResult<Navigation> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Navigation> result = null;
		try {
			Integer count = navigationMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Navigation> list = navigationMapper.findPage(query.getQueryData());
				result = new PageResult<Navigation>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
