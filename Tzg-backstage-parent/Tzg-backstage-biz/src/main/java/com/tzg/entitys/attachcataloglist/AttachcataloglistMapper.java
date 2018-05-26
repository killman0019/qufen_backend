package com.tzg.entitys.attachcataloglist;

import java.util.List;

import com.tzg.entitys.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachcataloglistMapper extends BaseMapper<Attachcataloglist, java.lang.Integer> {	
	
	/**
	 * 
	 * @param iState
	 * @return
	 * @throws Exception
	 */
	public List<Attachcataloglist> queryAttachCatalogByIStateList(Integer iState) throws Exception;

}
