package com.tzg.service.roleinfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.leopard.role.resource.RoleResource;
import com.tzg.entitys.leopard.role.resource.RoleResourceMapper;
import com.tzg.entitys.roleinfo.RoleInfo;
import com.tzg.entitys.roleinfo.RoleInfoMapper;
import com.tzg.service.base.BaseService;

/**
 * @Description:系统权限控制的角色Service
 * @author wxg
 * @Date: 2014-12-02
 * 
 */
@Service
@Transactional
public class RoleInfoService extends BaseService {

	@Autowired
	private RoleInfoMapper roleInfoMapper;

	@Autowired
	private RoleResourceMapper roleResourceMapper;

	public List<RoleInfo> findAllRole() {
		return roleInfoMapper.findAllRoleInfo();
	}

	/**
	 * 记录新增
	 * 
	 * @throws Exception
	 */
	public int save(RoleInfo roleInfo) throws Exception {
		if (StringUtils.isBlank(roleInfo.getVcRoleName())) {
			throw new Exception("角色名称不能为空！");
		}
		if(null != roleInfoMapper.findByRoleName(roleInfo.getVcRoleName())){
			throw new Exception("角色名称已经存在！");
		}
		roleInfo.setDtCreate(new Date());
		roleInfo.setiValid(1); 
		int id = roleInfoMapper.save(roleInfo);
		if (roleInfo.getResourcesId() != null
				&& (roleInfo.getResourcesId()).size() > 0) {
			return saveRoleResource(roleInfo.getId(), roleInfo.getResourcesId());
		} else {
			return id;
		}
	}

	public int saveRoleResource(int roleid, List<String> resourcesId)
			throws Exception {
		roleResourceMapper.deleteByRoleID(roleid);
		List<RoleResource> roleResourceList = new ArrayList<RoleResource>();
		for (int i = 0; i < resourcesId.size(); i++) {
			String iResourceID = resourcesId.get(i);
			RoleResource roleResource = new RoleResource();
			roleResource.setiRoleinfoID(roleid);
			roleResource.setiResourceID(Integer.parseInt(iResourceID));
			roleResourceList.add(roleResource);
		}
		return roleResourceMapper.insertList(roleResourceList);
	}

	/**
	 * 记录更新
	 * 
	 * @param roleInfo
	 * @throws Exception
	 */
	public void update(RoleInfo roleInfo) throws Exception {
		if (StringUtils.isBlank(roleInfo.getVcRoleName())) {
			throw new Exception("角色名称不能为空！");
		}
		RoleInfo role = roleInfoMapper.findByRoleName(roleInfo.getVcRoleName());
		if(null != role && role.getId() != roleInfo.getId() ){
			throw new Exception("角色名称已经存在！");
		}
		roleInfo.setDtModify(new Date());
		roleInfoMapper.update(roleInfo);
		if (roleInfo.getResourcesId() != null
				&& (roleInfo.getResourcesId()).size() > 0) {
			saveRoleResource(roleInfo.getId(), roleInfo.getResourcesId());
		} else {
			roleResourceMapper.deleteByRoleID(roleInfo.getId());
		}
	}

	/**
	 * 记录删除
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void delete(Integer id) throws Exception {
//		roleInfoMapper.delete(id);
		//数据库中的数据做假删除操作
		RoleInfo roleInfo = new RoleInfo();
		roleInfo.setId(id);
		roleInfo.setiValid(2); //1有效，2无效
		roleInfo.setDtModify(new Date());
		roleInfoMapper.update(roleInfo);
		
		// 根据角色编号，角色资源关系表删除相关记录
		roleResourceMapper.deleteByRoleID(id);
	}

	/**
	 * 根据主键编号，查询记录
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RoleInfo findById(Integer id) throws Exception {
		RoleInfo roleInfo = roleInfoMapper.findById(id);
		
		return roleInfo;
	}

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<RoleInfo> queryRoleInfoListByPage(PaginationQuery query) {
		PageResult<RoleInfo> result = null;
		try {
			Integer count = roleInfoMapper.queryRoleInfoCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<RoleInfo> list = roleInfoMapper.queryRoleInfoList(query.getQueryData());
				result = new PageResult<RoleInfo>(list,count,query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<RoleInfo> findAllValidRoleInfo(){
		return roleInfoMapper.findAllValidRoleInfo();
	}

}
