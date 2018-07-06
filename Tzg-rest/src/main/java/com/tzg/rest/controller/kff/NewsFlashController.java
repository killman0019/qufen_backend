package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.utils.PolicyUtil;
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
	* @Title: getNewsFlashPageList 
	* @Description: TODO <获取快讯分页列表>
	* @author linj <方法创建作者>
	* @create 上午9:46:43
	* @param @param request 前端对分页做了加密的参数
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午9:46:43
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getNewsFlashPageList")
	public BaseResponseEntity getNewsFlashPageList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			BaseRequest baseRequest = getParamMapFromRequestPolicy(request, BaseRequest.class);
			PaginationQuery query = new PaginationQuery();
	        query.addQueryData("state", sysGlobals.ENABLE);
	        query.setPageIndex(baseRequest.getPageIndex());
	        query.setRowsPerPage(baseRequest.getPageSize());
			PageResult<KFFNewsFlash> data = newsFlashRmiService.findNewsFlashPage(query);
            map.put("data", data);
			bre.setData(map);
		} catch (RestServiceException e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashController getNewsFlashPageList:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
	
	
	/** 
	* @Title: updateNewsFlashRiseAndFall 
	* @Description: TODO <快讯的看涨，看跌>
	* @author linj <方法创建作者>
	* @create 下午2:08:47
	* @param @param request
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:08:47
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/updateNewsFlashRiseAndFall")
	public BaseResponseEntity updateNewsFlashRiseAndFall(BaseRequest baseRequest,HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			String policy;
			KFFNewsFlash newsFlash;
			try{
				policy = PolicyUtil.decryptPolicy(baseRequest.getPolicy());
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.DECRYPT_POLICY_ERROR);
			}
			try{
				newsFlash = JSON.parseObject(policy, KFFNewsFlash.class);
			}catch(Exception e){
				e.printStackTrace();
				throw new RestServiceException(RestErrorCode.PARSE_JSON_ERROR);
				
			}
			if(null == newsFlash) {
				throw new RestServiceException(RestErrorCode.JSON_BLANK_ERROR);
			}
			Integer id = newsFlash.getId();
			if(null==id) {
				throw new RestServiceException(RestErrorCode.POST_ID_BLANK);
			}
			Integer rise = newsFlash.getRise();
			Integer fall = newsFlash.getFall();
			if(rise<1&&fall<1) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			
			if(rise>0&&fall>0) {
				throw new RestServiceException(RestErrorCode.NEWS_FLASH_RISE_FALL);
			}
			Map<String,Object> reMap = new HashMap<String,Object>();
			reMap.put("id", id);
			if(rise>0) {
				reMap.put("risec", rise);
			}else if(rise<1) {
				reMap.put("fallc", fall);
			}
		    newsFlashRmiService.updateByMap(reMap);
		    KFFNewsFlash kffNewsFlash = newsFlashRmiService.findById(id);
		    map.put("rise", kffNewsFlash.getRise());
		    map.put("fall", kffNewsFlash.getFall());
		} catch (RestServiceException e) {
			logger.error("NewsFlashController updateNewsFlashRiseAndFall：", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("NewsFlashController updateNewsFlashRiseAndFall：", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		bre.setData(map);
		return bre;
	}
	
}


