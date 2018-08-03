package com.tzg.wap.controller.h5;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.entitys.kff.app.NewsFlash;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.NewsFlashRmiService;

/** 
* @ClassName: NewsFlashController 
* @Description: TODO<快讯controller> 
* @author linj<作者>
* @date 2018年7月5日 下午1:23:02 
* @version v1.0.0 
*/
@Controller
@RequestMapping("/kff/newsFlash")
public class NewsFlashController extends BaseController {
	private static Logger logger = Logger.getLogger(NewsFlashController.class);
	
	@Autowired
	private NewsFlashRmiService newsFlashRmiService;
	
	/** 
	* @Title: getAppNewsFlash 
	* @Description: TODO <获取币种详情页的快讯>
	* @author linj <方法创建作者>
	* @create 上午10:04:01
	* @param @param request
	* @param @param id 币种快讯的id
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午10:04:01
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getAppNewsFlash")
	public BaseResponseEntity getAppNewsFlash(HttpServletRequest request,Integer id) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(null==id) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
			NewsFlash nwFh = newsFlashRmiService.findAppNewsFlashById(id);
			if(null==nwFh) {
				throw new RestServiceException(RestErrorCode.NO_DATA_MSG); 
			}
			bre.setData(nwFh);
		} catch (RestServiceException e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
}


