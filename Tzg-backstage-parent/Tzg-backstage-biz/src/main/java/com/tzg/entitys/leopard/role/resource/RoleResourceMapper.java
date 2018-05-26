package com.tzg.entitys.leopard.role.resource;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cfour on 12/1/14.
 * 
 * **************************************
 * 
 * @date 2014-12-02
 * @description:添加增、删、改功能
 * @author:wxg
 * 
 * @date 2014-12-03
 * @description:添加相关查询功能
 * @author:wxg
 */
@Component
public interface RoleResourceMapper {
	/**
	 * 根据角色编号，查询该角色拥有的资源记录列表
	 * @param iRoleinfoID
	 * @return
	 * @throws Exception
	 */
	public List<Integer> queryResourceListByRoleID(Integer iRoleinfoID) throws Exception;
	
	/**
	 * 根据资源编号，查询该资源拥有的角色记录列表
	 * @param iRoleinfoID
	 * @return
	 * @throws Exception
	 */
	public List<Integer> queryRoleListByResourceID(Integer iResourceID) throws Exception;
	
	/**
	 * 根据角色编号，查询该角色没有的资源记录列表
	 * @return
	 * @throws Exception
	 */
	public List<RoleResource>  queryNotResourceListByRoleinfoID(Integer iRoleinfoID) throws Exception;
	
	/**
	 * 根据角色编号，删除对应的记录
	 * @throws Exception
	 */
	public void deleteByRoleID(Integer iRoleinfoID) throws Exception;
	
	
	/**
	 * 根据资源编号，删除对应的记录
	 */
	public void deleteByResourceID(Integer iResourceID) throws Exception;
	
	int insertList(List<RoleResource> roleResourceList);
	
}
