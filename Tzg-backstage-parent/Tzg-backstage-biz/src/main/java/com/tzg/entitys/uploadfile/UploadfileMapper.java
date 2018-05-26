package com.tzg.entitys.uploadfile;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tzg.entitys.BaseMapper;

@Repository
public interface UploadfileMapper extends
		BaseMapper<Uploadfile, java.lang.Integer> {

	/**
	 * 
	 * @param vcUrl
	 * @return
	 * @throws Exception
	 */
	public Uploadfile queryUploadFileByVcUrl(String vcUrl) throws Exception;

	/**
	 * 分页查询--总条数
	 * 
	 * @return Integer 总条数
	 */
	public Integer findPageNotSyncCount(Map<String, String> map);

	/**
	 * 分页查询列表，使用此方法的dto需要继承pageList
	 * 
	 * @return List<E> 返回数据集合
	 */
	public List<Uploadfile> findNotSyncPage(Map<String, String> map);

}
