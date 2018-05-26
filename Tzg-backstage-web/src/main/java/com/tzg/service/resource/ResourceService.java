package com.tzg.service.resource;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.leopard.resource.Resource;
import com.tzg.entitys.leopard.resource.ResourceMapper;
import com.tzg.entitys.leopard.role.resource.RoleResourceMapper;
import com.tzg.entitys.roleinfo.RoleInfoMapper;
import com.tzg.service.base.BaseService;

/**
 * @Description:系统权限控制的资源表Service
 * @author wxg
 * @Date: 2014-12-02
 * 
 */
@Service
@Transactional
public class ResourceService extends BaseService {

	@Autowired
	private ResourceMapper resourceMapper;

	@Autowired
	private RoleResourceMapper roleResourceMapper;
	
	@Autowired
	private RoleInfoMapper roleInfoMapper;

	/**
	 * 记录新增
	 * 
	 * @param resource
	 * @throws Exception
	 */
	public void save(Resource resource) throws Exception {
		if (null == resource.getiParentId()) {
			resource.setiParentId(0);
		}
		if(StringUtils.isBlank(resource.getVcName())){
			throw new Exception("资源名称不能为空");
		}
		if(StringUtils.isBlank(resource.getVcUrl())){
			throw new Exception("资源URL不能为空");
		}
		resource.setiValid(1);
		resource.setDtCreate(new Date());
		resourceMapper.save(resource);
	}

	/**
	 * 记录更新
	 * 
	 * @param resource
	 * @throws Exception
	 */
	public void update(Resource resource) throws Exception {
		resource.setDtModify(new Date());
		resourceMapper.update(resource);
	}

	/**
	 * 记录删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id) throws Exception {
		List<Integer> list = roleResourceMapper.queryRoleListByResourceID(id);
		if(list!=null && list.size()>0){
			throw new Exception("该资源已经分配给角色，无法删除");
		}
		// 数据库记录做假删除处理
		Resource resource = new Resource();
		resource.setId(id);
		resource.setiValid(2); // 1有效，2无效
		resource.setDtModify(new Date());
		resourceMapper.update(resource);
		// 根据资源编号，角色资源关系表删除相关记录
		roleResourceMapper.deleteByResourceID(id);
	}

	/**
	 * 根据主键编号，记录查询
	 * 
	 * @param id
	 * @return
	 */
	public Resource findById(Integer id) throws Exception {
		Resource resource = null;
		resource = resourceMapper.findById(id);
		return resource;
	}

	/**
	 * 获取所有资源，树形展示
	 * @return
	 * @throws Exception 
	 */
	public List<Resource> getAllResource(Integer roleID) throws Exception {
		List<Integer> belongResourcesList = null ;
		if(roleID !=null){
			belongResourcesList = roleResourceMapper.queryResourceListByRoleID(roleID);
		}
		List<Resource> firstList = resourceMapper.queryFirstLevelResourceAll();
		for(Resource first : firstList){
			this.getChildrenList(first, belongResourcesList);
		}
		return firstList;
	}
	
	private void getChildrenList(Resource resource,List<Integer> belongList){
		resource.setChecked(this.getChecked(resource.getId(), belongList));
		Map<String, String> map =new HashMap<String, String>();
		map.put("parentID", resource.getId()+"");
		List<Resource> list = resourceMapper.getSecondResourceAll(map);
		if(list!=null && list.size()>0){
			resource.setChildren(list);
			for(Resource branch : list){
				getChildrenList(branch, belongList);
			}
		}
	}
	
	private boolean getChecked(Integer resourceID,List<Integer> list)
	{
		if(null==list){
			return false;
		}
		if(0!=list.size()){
			return	list.contains ( resourceID );
		}else{
			return false;
		}
	}
	
	public PageResult<Resource> queryResourceListByPage(PaginationQuery query) {
		PageResult<Resource> result = null;
		try {
			Integer count = resourceMapper.queryResourcesCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Resource> list = resourceMapper.queryResourcesList(query.getQueryData());
				result = new PageResult<Resource>(list,count,query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询父级资源记录
	 * 
	 * @return
	 */
	public List<Resource> queryParentVcNameAll() {
		List<Resource> list = null;
		list = resourceMapper.queryParentVcNameAll();
		return list;
	}

	/**
	 * 查询所有的顶级记录
	 * 
	 * @return
	 */
	public List<Resource> queryFirstLevelResourceAll() {
		List<Resource> list = null;
		list = resourceMapper.queryFirstLevelResourceAll();
		return list;
	}
	
	public List<Resource> queryResourcesAllList(Map<String, String> map) {
		List<Resource> list = null;
		try {
				list = resourceMapper.queryResourcesAllList(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
