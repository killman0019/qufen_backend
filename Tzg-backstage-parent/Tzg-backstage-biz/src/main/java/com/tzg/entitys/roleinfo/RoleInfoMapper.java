package com.tzg.entitys.roleinfo;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by cfour on 12/1/14.
 * 
 * **************************************
 * 
 * @date 2014-12-02
 * @description:添加增、删、改功能
 * @author:wxg
 */
@Component
public interface RoleInfoMapper {
    List<RoleInfo> findAllRoleInfo();
	/**
	 * 记录新增
	 * 
	 */
	public int save(RoleInfo roleInfo) throws Exception;

	/**
	 * 记录更新
	 * 
	 * @throws Exception
	 * 
	 */
	public void update(RoleInfo roleInfo) throws Exception;

	/**
	 * 记录删除
	 * 
	 * @param id
	 *            资源id
	 * @throws Exception
	 * 
	 */
	public void delete(Integer id) throws Exception;
	
	/**
	 * 根据主键编号，查询记录
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RoleInfo findById(Integer id) throws Exception;
	
	
	/***************   分页查询功能   ******************/
	public int queryRoleInfoCount(Map<String,String> map);

	public List<RoleInfo> queryRoleInfoList(Map<String,String> map);
	
	
	RoleInfo findByRoleName(String vcRoleName);
	
	public List<RoleInfo> findAllValidRoleInfo();

}
