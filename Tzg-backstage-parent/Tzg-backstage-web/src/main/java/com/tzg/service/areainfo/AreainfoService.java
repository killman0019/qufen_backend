package com.tzg.service.areainfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.areainfo.Areainfo;
import com.tzg.entitys.areainfo.AreainfoMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class AreainfoService extends BaseService {

	@Autowired
	private AreainfoMapper areainfoMapper;

	@Transactional(readOnly = true)
	public Areainfo findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return areainfoMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		areainfoMapper.deleteById(id);
	}

	public void save(Areainfo areainfo) throws Exception {
		if (StringUtils.isBlank(areainfo.getVcCode())) {
			throw new Exception("代码不能为空！");
		}
		if (StringUtils.isBlank(areainfo.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		areainfo.setIsort(0);
		areainfo.setIvalid(1);
		areainfoMapper.save(areainfo);
	}

	public void update(Areainfo areainfo) throws Exception {
		if (areainfo.getId() == null) {
			throw new Exception("id不能为空");
		}
		if (StringUtils.isBlank(areainfo.getVcCode())) {
			throw new Exception("代码不能为空！");
		}
		if (StringUtils.isBlank(areainfo.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		areainfoMapper.update(areainfo);
	}

	@Transactional(readOnly = true)
	public PageResult<Areainfo> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Areainfo> result = null;
		try {
			Integer count = areainfoMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)
						* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",
						Integer.toString(query.getRowsPerPage()));
				List<Areainfo> list = areainfoMapper.findPage(query
						.getQueryData());
				result = new PageResult<Areainfo>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询所有地区
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> querylAll() throws Exception {
		List<Areainfo> list = null;
		list = areainfoMapper.findAll();
		return list;
	}

	/**
	 * 查询所有顶级地区
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> queryFirstLevelAll() throws Exception {
		List<Areainfo> list = null;
		list = areainfoMapper.findFirstLevelAll();
		return list;
	}

	/**
	 * 查询所有父级地区
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> queryFatherLevelAll(String vcCode) throws Exception {
		List<Areainfo> list = new ArrayList<Areainfo>();
		boolean top = true;
		while (top) {
			Areainfo vcareainfo = areainfoMapper.findAreaByCode(vcCode);
			vcCode = vcareainfo.getVcCode();
			list.add(vcareainfo);
			if (vcareainfo.getVcParentCode().equals(0)) {
				top = false;
			}
		}
		Collections.reverse(list);
		return list;
	}

	/**
	 * 根据地区编码，查询本级地区的所有下级地区
	 * 
	 * @param vcCode
	 * @return
	 * @throws Exception
	 */
	public List<Areainfo> queryInfoByiFatherID(String vcCode) throws Exception {
		List<Areainfo> list = null;
		list = areainfoMapper.findAreaListByParentCode(vcCode);
		return list;
	}

	/**
	 * 
	 * @param vcCode
	 * @return
	 * @throws Exception
	 */
	public Areainfo queryParentInfo(String vcCode) throws Exception {
		Areainfo info = null;
		info = areainfoMapper.findAreaByCode(vcCode);
		return info;
	}

}
