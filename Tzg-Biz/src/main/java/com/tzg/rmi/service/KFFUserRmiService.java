package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.user.KFFUser;

/** 
* @ClassName: NewsFlashRmiService 
* @Description: TODO<快讯远程服务调用> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:17 
* @version v1.0.0 
*/
public interface KFFUserRmiService {

	public KFFUser findById(Integer id);
	
	public List<KFFUser> findListByAttr(Map<String, Object> map);

	public List<KFFUser> findListByMap(Map<String, Object> map);
}
