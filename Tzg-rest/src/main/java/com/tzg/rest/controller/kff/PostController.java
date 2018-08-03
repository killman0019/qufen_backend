package com.tzg.rest.controller.kff;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFPostRmiService;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value = "KFFPostController")
@RequestMapping("/kff/post")
public class PostController extends BaseController {
	private static Logger log = Logger.getLogger(PostController.class);

	@Autowired
	private KFFRmiService kffRmiService;

	@Autowired
	private KFFPostRmiService kffPostRmiService;

	/**
	 * 币圈必读
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getBgMustRead", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public BaseResponseEntity getBgMustRead(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		HashMap<String, Object> map = new HashMap<String, Object>();

		try {
			JSONObject params = getParamMapFromRequestPolicy(request);

			Integer pageIndex = (Integer) params.get("pageIndex");
			Integer pageSize = (Integer) params.get("pageSize");

			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			PageResult<PostResponse> BgmustReadPage = kffPostRmiService.showBGmustRead(query);
			map.put("BgmustReadPage", BgmustReadPage);
			System.err.println("BgmustReadPage" + JSON.toJSONString(BgmustReadPage));
			bre.setData(map);
			System.err.println(JSON.toJSON(BgmustReadPage));
		} catch (RestServiceException e) {
			logger.error("PraiseController savePostPraise:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PraiseController savePostPraise:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
