package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.kff.follow.Follow;
import com.tzg.entitys.kff.user.KFFUser;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.project.ProjectResponse;

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
	
		/**
	 * 查找KOL排行榜 关注人数超过30人的用户榜单，按粉丝数排名，最多20；
	 * 
	 * @param userId
	 * @param query
	 * @return
	 */
	public PageResult<KFFUser> selectKOLProjectPage(Integer userId, PaginationQuery query);
}