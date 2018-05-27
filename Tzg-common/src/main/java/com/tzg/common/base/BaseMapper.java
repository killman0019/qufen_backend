package com.tzg.common.base;



import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * mapper基类，集成此类后增删改查的方法不用再写
 */

public interface BaseMapper <E,PK extends Serializable>{

	/**
	* 根据id获取对象
	* @param id 对象id
	* @return Object  返回对象  
	*/
	public E findById(PK id);
	
	/**
	* 根据id删除对象
	* @param id 对象id
	*     
	*/
	public void deleteById(PK id);
	
	/**
	* 保存对象信息
	* @param entity 对象实体类
	*     
	*/
	public Integer save(E entity);
	
	/**
	* 更新对象信息 
	* @param entity 实体类
	*     
	*/
	public void update(E entity);
	/**
	 * 更新对象信息 
	 * @param entity 实体类
	 *     
	 */
	public void updateAll(E entity);

	/**
	* 分页查询--总条数
	* @return Integer 总条数   
	*/
	public Integer findPageCount(Map<String,Object> map);
	
	/**
	* 分页查询--总条数
	* @return Integer 总条数   
	*/
	public Integer findAllPageCount(Map<String,Object> map);
	
	/**
	* 分页查询列表，使用此方法的dto需要继承pageList
	* @return List<E> 返回数据集合   
	*/
	public List<E> findPage(Map<String,Object> map);
	
	/**
	* 分页查询列表，使用此方法的dto需要继承pageList
	* @return List<E> 返回数据集合   
	*/
	public List<E> findAllPage(Map<String,Object> map);

}
