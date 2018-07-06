package com.tzg.rmi.service;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.entitys.kff.newsFlashImg.KFFNewsFlashImg;
import com.tzg.rest.exception.rest.RestServiceException;

/** 
* @ClassName: NewsFlashImgRmiService 
* @Description: TODO<快讯轮播图远程调用服务> 
* @author linj<作者>
* @date 2018年7月5日 下午1:32:42 
* @version v1.0.0 
*/
public interface NewsFlashImgRmiService {

	/** 
	* @Title: findNewsFlashImgPage 
	* @Description: TODO <获取快讯轮播图列表>
	* @author linj <方法创建作者>
	* @create 上午9:27:20
	* @param @param query
	* @param @return
	* @param @throws RestServiceException <参数说明>
	* @return PageResult<KFFMessage> 
	* @throws 
	* @update 上午9:27:20
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	public PageResult<KFFNewsFlashImg> findNewsFlashImgPage(PaginationQuery query) throws RestServiceException;

}
