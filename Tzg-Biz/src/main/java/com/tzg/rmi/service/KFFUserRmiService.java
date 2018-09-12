package com.tzg.rmi.service;

import java.util.List;
import java.util.Map;

import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.qfindex.QfindexResponse;
import com.tzg.rest.exception.rest.RestServiceException;

/** 
* @ClassName: KFFUserRmiService 
* @Description: TODO<用户远程服务调用> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:17 
* @version v1.0.0 
*/
public interface KFFUserRmiService {

	public PageResult<KFFUser> findPage(PaginationQuery query);

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

	// 判断用户是否禁用
	public boolean findUserByStatus(Integer userId);

	/**
	 * 
	* @Title: getMember 
	* @Description: TODO <获得用户的会员中心>
	* @author zhangdd <方法创建作者>
	* @create 下午5:26:08
	* @param @param userId
	* @param @return
	* @param @throws RestServiceException <参数说明>
	* @return QfindexResponse 
	* @throws 
	* @update 下午5:26:08
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public QfindexResponse getMember(Integer userId) throws RestServiceException;

}
