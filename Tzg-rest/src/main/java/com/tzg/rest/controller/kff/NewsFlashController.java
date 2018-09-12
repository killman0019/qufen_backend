package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.base.BaseRequest;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.sysGlobals;
import com.tzg.entitys.kff.app.NewsFlash;
import com.tzg.entitys.kff.newFlash.KFFNewsFlash;
import com.tzg.rest.controller.BaseController;
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
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			Integer id = (Integer) policyJson.get("id");
			if(null==id) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			Integer rise = (Integer) policyJson.get("rise");
			Integer fall = (Integer) policyJson.get("fall");
			if(rise<1&&fall<1) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS);
			}
			
			if(rise>0&&fall>0) {
				throw new RestServiceException(RestErrorCode.NEWS_FLASH_RISE_FALL);
			}
			String type = "";
			Object str = policyJson.get("type");
			if(StringUtil.isEmptyObject(str)) {
				type = "0";
			}else {
				type = str.toString();
			}
			
			Map<String,Object> reMap = new HashMap<String,Object>();
			reMap.put("id", id);
			if(rise>0) {
				reMap.put("risec", rise);
			}else if(rise<1) {
				reMap.put("fallc", fall);
			}
			reMap.put("type", type);
		    newsFlashRmiService.updateByMap(reMap);
			if(type.equals("0")) {
			    KFFNewsFlash kffNewsFlash = newsFlashRmiService.findById(id);
			    map.put("rise", kffNewsFlash.getRise());
			    map.put("fall", kffNewsFlash.getFall());
			}else if(type.equals("1")) {
			    NewsFlash newsFlash = newsFlashRmiService.findAppNewsFlashById(id);
			    map.put("rise", newsFlash.getRise());
			    map.put("fall", newsFlash.getFall());
			}
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
	
	/** 
	* @Title: getAppNewsFlashPageList 
	* @Description: TODO <获取币种详情页的快讯列表接口>
	* @author linj <方法创建作者>
	* @create 上午10:04:01
	* @param @param request
	* @param @param projectCode 币种的Code
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 上午10:04:01
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/getAppNewsFlashPageList")
	public BaseResponseEntity getAppNewsFlashPageList(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
        HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			
			PaginationQuery query = new PaginationQuery();
			Integer curPage = (Integer)policyJson.get("pageIndex");
			Integer pageSize = (Integer)policyJson.get("pageSize");
			curPage=curPage==null||curPage<1?1:curPage;
			pageSize=pageSize==null||pageSize<1?15:pageSize;
	        query.setPageIndex(curPage);
	        query.setRowsPerPage(pageSize);
	        String projectCode = (String) policyJson.get("projectCode");
	        if(StringUtil.isBlank(projectCode)) {
				throw new RestServiceException(RestErrorCode.MISSING_ARGS); 
			}
	        query.addQueryData("projectCode", projectCode);
	        //先获取币种新闻
	        query.addQueryData("typec", 0);//先获取币种的新闻
			PageResult<NewsFlash> data = new  PageResult<NewsFlash>(); 
			data = newsFlashRmiService.findAppNewsFlashPage(query);
			//当没有币种新闻的时候在获取币种的快讯
			query.addQueryData("typec", 1);//先获取币种的快讯
			if(null!=data) {
				List<NewsFlash> rows = data.getRows();
				if(rows.isEmpty()) {
					data = newsFlashRmiService.findAppNewsFlashPage(query);
				}
			}else {
				data = newsFlashRmiService.findAppNewsFlashPage(query);
			}
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
	
}


