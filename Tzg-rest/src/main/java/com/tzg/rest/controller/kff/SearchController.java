package com.tzg.rest.controller.kff;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.StringUtil;
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.project.KFFProject;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.controller.BaseController;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.DtagsRmiService;
import com.tzg.rmi.service.KFFPostRmiService;
import com.tzg.rmi.service.KFFProjectRmiService;
import com.tzg.rmi.service.KFFUserRmiService;

@Controller
@RequestMapping("/kff/search")
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
	public BaseResponseEntity indexSearch(HttpServletRequest request) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			JSONObject policyJson = getParamJsonFromRequestPolicy(request);
			Integer type = (Integer)policyJson.get("type");//状态：1-项目，2-内容，3-用户，4-话题
			Integer curPage = (Integer)policyJson.get("pageIndex");
			Integer pageSize = (Integer)policyJson.get("pageSize");
			String title = (String)policyJson.get("title");
			String token = (String)policyJson.get("token");
			Integer userId = null;
			if (StringUtils.isNotBlank(token)) {
				userId = getUserIdByToken(token);
			}
			if(null==type || null==curPage||null==pageSize||
					StringUtil.isBlank(title)) {
				bre.setNoRequstData();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(curPage);
	        query.setRowsPerPage(pageSize);
	        if(StringUtil.isNotBlank(title.trim())) {
	        	query.addQueryData("title", title.trim());
	        }
	        query.addQueryData("status", 1);
	        if(type==1) {
	        	Integer typec = 1;// 取关注项目
				query.addQueryData("sortField", "follower_num");
				query.addQueryData("sortSequence", "desc");
				query.addQueryData("state", 2);
				PageResult<KFFProject> data = projectRmiService.findPageWithFollower(query,typec,userId);
				if(null!=data) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==2) {
				Integer typec = 2;// 取关注人
				query.addQueryData("sql_keyword_orderBy", "createTime");
				query.addQueryData("sql_keyword_sort", "desc");
				query.addQueryData("postTypec", 4);
				PageResult<Post> data = postRmiService.findPageWithFollower(query,typec,userId);
				if(null!=data) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==3) {
				Integer typec = 2;// 取关注人
				query.addQueryData("sortField", "fans_num");
				query.addQueryData("sortSequence", "desc");
				PageResult<KFFUser> data = userRmiService.findPageWithFollower(query,typec,userId);
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


