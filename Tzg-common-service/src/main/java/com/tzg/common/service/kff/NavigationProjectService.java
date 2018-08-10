package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.content.NavigationProject;
import com.tzg.entitys.kff.content.NavigationProjectMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class NavigationProjectService {

	@Autowired
	private NavigationProjectMapper navigationProjectMapper;

	@Transactional(readOnly = true)
	public NavigationProject findById(java.lang.Integer id) throws RestServiceException {
		return navigationProjectMapper.selectByPrimaryKey(id);
	}

	@Transactional(readOnly = true)
	public List<NavigationProject> findListByAttr(Map<String, Object> map) throws RestServiceException {
		return navigationProjectMapper.findListByAttr(map);
	}
	

	@Transactional(readOnly = true)
	public PageResult<NavigationProject> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<NavigationProject> result = null;
		try {
			Integer count = navigationProjectMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<NavigationProject> list = navigationProjectMapper.findPage(query.getQueryData());
				result = new PageResult<NavigationProject>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
