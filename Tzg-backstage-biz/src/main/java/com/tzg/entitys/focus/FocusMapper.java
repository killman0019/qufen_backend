package com.tzg.entitys.focus;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface FocusMapper extends BaseMapper<Focus, java.lang.Integer> {	

	/**
	* 小米渠道活动分页查询--总条数
	* @return Integer 总条数   
	*/
	public Integer findPageCountForChannel(Map<String,String> map);
	
	/**
	* 小米渠道活动分页查询列表，使用此方法的dto需要继承pageList
	* @return List<E> 返回数据集合   
	*/
	public List<Focus> findPageForChannel(Map<String,String> map);
}
