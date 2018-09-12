package com.tzg.wap.controller.h5;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.HtmlUtils;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.DtagsRmiService;
import com.tzg.rmi.service.KFFPostRmiService;
import com.tzg.rmi.service.KFFProjectRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller
@RequestMapping("/H5/search")
public class SearchController extends BaseController {
	private static Logger logger = Logger.getLogger(SearchController.class);
	
	@Autowired
	private DtagsRmiService dTagsRmiService;
	@Autowired
	private KFFPostRmiService postRmiService;
	@Autowired
	private KFFUserRmiService userRmiService;
	@Autowired
	private KFFProjectRmiService projectRmiService;
	
	/** 
	* @Title: indexSearch 
	* @Description: TODO <搜索接口>
	* @author linj <方法创建作者>
	* @create 下午2:22:41
	* @param @param request
	* @param @param type //状态：1-项目，2-内容，3-用户，4-话题
	* @param @param title 搜索标题
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:22:41
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value="/indexSearch",method = {RequestMethod.POST,RequestMethod.GET})
	public BaseResponseEntity indexSearch(HttpServletRequest request,Integer type,Integer pageIndex,
			Integer pageSize,String title) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(pageIndex==null&&StringUtil.isBlank(title)&&pageSize==null&&type==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				title = (String) requestContent.get("title");
				pageSize = (Integer) requestContent.get("pageSize");
				type = (Integer) requestContent.get("type");
			}
			if(null==type || null==pageIndex||null==pageSize||
					StringUtil.isBlank(title)) {
				bre.setNoRequstData();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
	        query.setRowsPerPage(pageSize);
	        query.addQueryData("title", title.trim());
	        query.addQueryData("status", 1);
	        if(type==1) {
				query.addQueryData("sortField", "follower_num");
				query.addQueryData("sortSequence", "desc");
				PageResult<KFFProject> data = projectRmiService.findPage(query);
				if(null!=data) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==2) {
				query.addQueryData("sql_keyword_orderBy", "praise_num");
				query.addQueryData("sql_keyword_sort", "desc");
				PageResult<Post> data = postRmiService.findPage(query);
				if(null!=data) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==3) {
				query.addQueryData("sortField", "fans_num");
				query.addQueryData("sortSequence", "desc");
				PageResult<KFFUser> data = userRmiService.findPage(query);
				if(null!=data) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==4) {
				query.addQueryData("sortField", "createTime");
				query.addQueryData("sortSequence", "desc");
				PageResult<Dtags> data = dTagsRmiService.findPage(query);
				if(null!=data) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			bre.setNoDataMsg();
		} catch (RestServiceException e) {
			logger.error("FollowController saveFollow:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("FollowController saveFollow:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR,e.getMessage());
		}
		return bre;
	}
}


