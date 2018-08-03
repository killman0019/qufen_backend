package com.tzg.rest.controller.kff;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.base.BaseRequest;
import com.tzg.common.enums.NewsFlashWay;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.newsFlashImg.KFFNewsFlashImg;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.MiningActivityRmiService;
import com.tzg.rmi.service.NewsFlashImgRmiService;

/** 
* @ClassName: NewsFlashImgController 
* @Description: TODO<快讯轮播图controller> 
* @author linj<作者>
* @date 2018年7月5日 下午1:25:39 
* @version v1.0.0 
*/
@Controller
@RequestMapping("/kff/newsFlashImg")
public class NewsFlashImgController extends BaseController {
	private static Logger logger = Logger.getLogger(NewsFlashImgController.class);
	
	@Autowired
	private NewsFlashImgRmiService newsFlashImgRmiService;
	@Autowired
	private MiningActivityRmiService miningActivityRmiService;
	
	/** 
	* @Title: getNewsFlashPageList 
	* @Description: TODO <获取快讯轮播图>
	* @author linj <方法创建作者>
	* @create 下午1:25:24
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午1:25:24
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getNewsFlashImgList")
	public BaseResponseEntity getNewsFlashPageList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("state", sysGlobals.ENABLE);
	        query.addQueryData("newsFlashWay", NewsFlashWay.NEWSFLASHLIST.getValue());
	        query.setPageIndex(baseRequest.getPageIndex());
	        query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<KFFNewsFlashImg> data = newsFlashImgRmiService.findNewsFlashImgPage(query);
            map.put("data", data);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("NewsFlashImgController getNewsFlashImgList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashImgController getNewsFlashImgList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	/** 
	* @Title: getNewsFlashImgListForFound 
	* @Description: TODO <获取发现的轮播图列表>
	* @author linj <方法创建作者>
	* @create 下午12:00:12
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午12:00:12
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getNewsFlashImgListForFound")
	public BaseResponseEntity getNewsFlashImgListForFound(HttpServletRequest request) {
		
		miningActivityRmiService.manualActivity();
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("state", sysGlobals.ENABLE);
	        query.addQueryData("newsFlashWay", NewsFlashWay.FOUNDLIST.getValue());
	        query.setPageIndex(baseRequest.getPageIndex());
	        query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<KFFNewsFlashImg> data = newsFlashImgRmiService.findNewsFlashImgPage(query);
            map.put("data", data);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("NewsFlashImgController getNewsFlashImgList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashImgController getNewsFlashImgList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
}


