package com.tzg.service.helpcategy;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.helpcategy.Helpcategy;
import com.tzg.entitys.helpcategy.HelpcategyMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class HelpcategyService extends BaseService {

	@Autowired
	private HelpcategyMapper helpcategyMapper;

	@Transactional(readOnly = true)
	public Helpcategy findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return helpcategyMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		helpcategyMapper.deleteById(id);
	}

	public void save(Helpcategy helpcategy) throws Exception {
		if (StringUtils.isBlank(helpcategy.getVcName())) {
			throw new Exception("类别不能为空！");
		}
		if (helpcategy.getiSort() == null) {
			throw new Exception("顺序不能为空！");
		}
		helpcategy.setIstate(1);
		helpcategy.setDtCreate(new Date());
		helpcategyMapper.save(helpcategy);
	}

	public void update(Helpcategy helpcategy) throws Exception {
		if (helpcategy.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(helpcategy.getVcName())) {
			throw new Exception("类别不能为空！");
		}
		if (helpcategy.getiSort() == null) {
			throw new Exception("顺序不能为空！");
		}
		helpcategyMapper.update(helpcategy);
	}

	@Transactional(readOnly = true)
	public PageResult<Helpcategy> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Helpcategy> result = null;
		try {
			Integer count = helpcategyMapper
					.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Helpcategy> list = helpcategyMapper.findPage(query
						.getQueryData());
				result = new PageResult<Helpcategy>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据istate值，查询记录
	 * 
	 * @param istate
	 * @return
	 * @throws Exception
	 */
	public List<Helpcategy> queryHelpcategyList(Integer istate)
			throws Exception {
		List<Helpcategy> list = null;
		list = helpcategyMapper.queryHelpcategyList(istate);
		return list;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Helpcategy> queryHelpcategyAll()throws Exception{
		List<Helpcategy> list = null;
		list = helpcategyMapper.queryHelpcategyAll();
		return list;
	}

}
