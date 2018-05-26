package com.tzg.entitys.leopard.resource;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.tzg.entitys.BaseMapper;

/**
 * Created by cfour on 12/1/14.
 * 
 * 
 * **************************************
 * 
 * @date 2014-12-02
 * @description:添加增、删、改功能
 * @author:wxg
 */
@Component
public interface ResourceMapper extends BaseMapper<Resource, Serializable> {

	
    List<Resource> getAllResource();

    List<String> findResourcesByAccount(int id);
    
    List<Resource> getFirstResourceByAccountId(int id);
    
    List<Resource> getSecondResourceByAccountId(Map<String, String> map);
    
    /**
     * 查询父级资源记录
     * @return
     */
    public List<Resource> queryParentVcNameAll();
    
    /**
     * 查询所有的顶级记录
     * @return
     */
    public List<Resource> queryFirstLevelResourceAll();

    List<Resource> getSecondResourceAll(Map<String, String> map);
    

	/*************** 分页查询 ******************/
	public int queryResourcesCount(Map<String, String> map);

	public List<Resource> queryResourcesList(Map<String, String> map);
	
	public List<Resource> queryResourcesAllList(Map<String, String> map);

}
