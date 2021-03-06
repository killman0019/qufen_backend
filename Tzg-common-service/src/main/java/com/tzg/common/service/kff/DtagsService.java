package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.base.BaseService;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.dtags.DtagsMapper;
import com.tzg.entitys.kff.dtags.DtagsReponse;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value = "KFFDtagsService")
@Transactional(rollbackFor=Exception.class)
public class DtagsService  extends BaseService {

	@Autowired
	private DtagsMapper dtagsMapper;

	@Transactional(readOnly = true)
	public Dtags findById(java.lang.Integer id) throws RestServiceException {
		return dtagsMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws RestServiceException {
		if (id == null) {
			throw new RestServiceException("id不能为空");
		}
		dtagsMapper.deleteById(id);
	}

	public void save(Dtags dtags) throws RestServiceException {
		dtagsMapper.save(dtags);
	}

	public void update(Dtags dtags) throws RestServiceException {
		if (dtags.getTagId() == null) {
			throw new RestServiceException("id不能为空");
		}
		dtagsMapper.update(dtags);
	}
	
	@Transactional(readOnly = true)
	public List<Dtags> findListByAttr(Map<String, Object> map) {
		return dtagsMapper.findListByAttr(map);
	}


	@Transactional(readOnly = true)
	public PageResult<Dtags> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Dtags> result = null;
		try {
			Integer count = dtagsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Dtags> list = dtagsMapper.findPage(query.getQueryData());
				result = new PageResult<Dtags>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {	
		dtagsMapper.updateByMap(map);
	}	

	public List<Dtags> findAllTags() {

		return dtagsMapper.findAllTags();
	}

	public List<Dtags> findAllTagsName() {
		// TODO Auto-generated method stub
		return dtagsMapper.findAllTagsName();
	}

	public List<DtagsReponse> getEvaTagsAndTagType(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return dtagsMapper.getEvaTagsAndTagType(map);
	}

	public List<Dtags> findByMap(Map<String, Object> dtagsMap) {
		// TODO Auto-generated method stub
		return dtagsMapper.findByMap(dtagsMap);
	}

}
