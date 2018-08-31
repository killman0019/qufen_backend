package com.tzg.rmi.service;

import com.tzg.entitys.kff.comments.CommentsRequest;
import com.tzg.rest.exception.rest.RestServiceException;

/**
 * 
* @ClassName: KFFRootRmiService 
* @Description: TODO<机器人相关接口> 
* @author zhangdd<作者>
* @date 2018年8月31日 下午1:59:53 
* @version v1.0.0
 */
public interface KFFRobotRmiService {

	/**
	 * 
	* @Title: createComment 
	* @Description: TODO <用一句话描述这个方法的作用>
	* @author zhangdd <方法创建作者>
	 * @param userId 
	* @create 下午2:32:25
	* @param @param comment
	* @param @return <参数说明>
	* @return CommentsRequest 
	* @throws 
	* @update 下午2:32:25
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	 */
	public CommentsRequest createComment(CommentsRequest comment, Integer userId) throws RestServiceException;
}
