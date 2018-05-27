package com.tzg.common.service.areainfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.common.base.BaseService;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.areainfo.Areainfo;
import com.tzg.entitys.areainfo.AreainfoMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class AreainfoService extends BaseService<Areainfo, Integer> {

	@Autowired
	private AreainfoMapper areainfoMapper;

	@Autowired
	public void setDao(AreainfoMapper areainfoMapper) {
		super.setMapper(areainfoMapper);
	}

	public AreainfoMapper getDao() {
		return (AreainfoMapper) super.getMapper();
	}

	public Areainfo findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		return this.getDao().findById(id);
	}

	public PageResult<Areainfo> findPage(PaginationQuery query) throws Exception {
		PageResult<Areainfo> result = null;
		Integer count = this.getDao().findPageCount(query.getQueryData());
		if (null != count && count.intValue() > 0) {
			int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
			query.addQueryData("startRecord", startRecord);
			query.addQueryData("endRecord", query.getRowsPerPage());
			List<Areainfo> list = this.getDao().findPage(query.getQueryData());
			result = new PageResult<Areainfo>(list, count, query);
		}
		return result;
	}

	/**
	 * 查询所有顶级地区
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> queryFirstLevelAll() throws Exception {
		List<Areainfo> list = null;
		list = this.getDao().findFirstLevelAll();
		return list;
	}

	/**
	 * 根据地区编号 获取下级地区
	 * 
	 * @param vcCode
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> queryInfoByiFatherID(String vcCode) throws Exception {
		List<Areainfo> list = null;
		list = this.getDao().findAreaListByParentCode(vcCode);
		return list;
	}

	/**
	 * 根据地区查询记录
	 * 
	 * @param vcCode
	 * @return
	 */
	public Areainfo queryInfoByCode(String vcCode) throws Exception {
		return this.getDao().findAreaByCode(vcCode);
	}

}
