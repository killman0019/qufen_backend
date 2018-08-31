package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.entitys.kff.interactionroot.InteractionRoot;
import com.tzg.entitys.kff.interactionroot.InteractionRootMapper;

@Service
@Transactional(rollbackFor = Exception.class)
public class InteractionRootService {

	@Autowired
	private InteractionRootMapper interactionRootMapper;

	/**
	 * 
	* @Title: findByMap 
	* @Description: TODO <map查询>
	* @author zhangdd <方法创建作者>
	* @create 下午1:56:23
	* @param @param map
	* @param @return <参数说明>
	* @return List<InteractionRoot> 
	* @throws 
	* @update 下午1:56:23
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public List<InteractionRoot> findByMap(Map<String, Object> map) {

		return null;
	}

	public List<Integer> findUserIdList() {
		// TODO Auto-generated method stub
		return null;
	}

}
