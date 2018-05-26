package com.tzg.service.dprojectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.dprojectType.DprojectType;
import com.tzg.entitys.dprojectType.DprojectTypeMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class DprojectTypeService extends BaseService {

	@Autowired
	private DprojectTypeMapper dprojectTypeMapper;	
	   
	@Transactional(readOnly=true)
    public DprojectType findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return dprojectTypeMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        dprojectTypeMapper.deleteById(id);
    }
	
	public void save(DprojectType dprojectType) throws Exception {	
		if(StringUtils.isBlank(dprojectType.getProjectTypeName())){
			throw new Exception("名称不能为空");
		}
		if(dprojectType.getProjectTypeName().length()>30){
			throw new Exception("名称长度不能超过30字符");
		}
		Map<String,String> map = new HashMap<>();
		map.put("projectTypeName", dprojectType.getProjectTypeName());
		map.put("startRecord",Integer.toString(1));
		map.put("endRecord",Integer.toString(10));
		List<DprojectType> exist = dprojectTypeMapper.findPage(map);
		if(CollectionUtils.isNotEmpty(exist)){
			throw new Exception("名称:"+dprojectType.getProjectTypeName()+"已存在，请勿重复添加");
		}
		dprojectTypeMapper.save(dprojectType);
	}
	
	public void update(DprojectType dprojectType) throws Exception {	
		if(dprojectType.getProjectTypeId() == null){
			throw new Exception("id不能为空");
		}
		dprojectTypeMapper.update(dprojectType);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<DprojectType> findPage(PaginationQuery query) throws Exception {
		PageResult<DprojectType> result = null;
		try {
			Integer count = dprojectTypeMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<DprojectType> list = dprojectTypeMapper.findPage(query.getQueryData());
				result = new PageResult<DprojectType>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
