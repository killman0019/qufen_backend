package com.tzg.common.service.kff;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.userqfindex.Userqfindex;
import com.tzg.entitys.kff.userqfindex.UserqfindexMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFUserqfindexService")
@Transactional
public class UserqfindexService {

	@Autowired
	private UserqfindexMapper userqfindexMapper;

	@Transactional(readOnly = true)
	public Userqfindex findById(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return userqfindexMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		userqfindexMapper.deleteById(id);
	}

	public void save(Userqfindex userqfindex) throws RestServiceException {
		userqfindexMapper.save(userqfindex);
	}

	public void update(Userqfindex userqfindex) throws RestServiceException {
		if (userqfindex.getQfindexId() == null) {
			throw new RestServiceException("id不能为空");
		}
		userqfindexMapper.update(userqfindex);
	}

	@Transactional(readOnly = true)
	public PageResult<Userqfindex> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Userqfindex> result = null;
		try {
			Integer count = userqfindexMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Userqfindex> list = userqfindexMapper.findPage(query.getQueryData());
				result = new PageResult<Userqfindex>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
