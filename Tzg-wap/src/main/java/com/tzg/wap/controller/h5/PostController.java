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
import com.tzg.entitys.kff.dtags.Dtags;
import com.tzg.entitys.kff.post.Post;
import com.tzg.rest.exception.rest.RestErrorCode;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.DtagsRmiService;
import com.tzg.rmi.service.KFFPostRmiService;

@Controller(value = "KFFPostController")
@RequestMapping("/H5/post")
public class PostController extends BaseController {
	private static Logger logger = Logger.getLogger(PostController.class);


	@Autowired
	private KFFPostRmiService kffPostRmiService;
	@Autowired
	private DtagsRmiService dTagsRmiService;

	/** 
	* @Title: getPostInfoWithTags 
	* @Description: TODO <获取话题下的评测，文章，爆料接口>
	* @author linj <方法创建作者>
	* @create 下午2:22:06
	* @param @param request
	* @param @param type 帖子类型：1-评测；2-讨论；3-文章
	* @param @param sort 展示类型：1-精选，2-最新
	* @param @param tagId 话题id
	* @param @param pageIndex
	* @param @param pageSize
	* @param @return <参数说明>
	* @return BaseResponseEntity 
	* @throws 
	* @update 下午2:22:06
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	@ResponseBody
	@RequestMapping(value = "/getPostInfoWithTags", method = { RequestMethod.POST, RequestMethod.GET })
	public BaseResponseEntity getPostInfoWithTags(HttpServletRequest request,Integer type,Integer sort,
			Integer tagId,Integer pageIndex,Integer pageSize) {
		BaseResponseEntity bre = new BaseResponseEntity();
		try {
			if(pageIndex==null&&pageSize==null&&type==null&&sort==null&&tagId==null) {
				JSONObject requestContent = HtmlUtils.getRequestContent(request);
				pageIndex = (Integer) requestContent.get("pageIndex");
				pageSize = (Integer) requestContent.get("pageSize");
				type = (Integer) requestContent.get("type");
				sort = (Integer) requestContent.get("sort");
				tagId = (Integer) requestContent.get("tagId");
			}
			if(null==type||null==sort||null==pageIndex||null==pageSize||
					tagId==null) {
				bre.setNoRequstData();
				return bre;
			}
			Dtags dTags = dTagsRmiService.findById(tagId);
			if(null==dTags) {
				bre.setNoDataMsg();
				return bre;
			}
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			query.setRowsPerPage(pageSize);
			query.addQueryData("sql_keyword_orderBy", "tpt.createTime");	
			query.addQueryData("sql_keyword_sort", "desc");	
			query.addQueryData("status", 1);
			if(sort==1) {
				query.addQueryData("praiseNum", 10);	
			}
			if(type==1) {
				query.addQueryData("tagName1", "\"tagName\":\""+dTags.getTagName()+"\"");
				query.addQueryData("postType", type);
				PageResult<Post> data = kffPostRmiService.findPageWithEvaluation(query);
				if(data!=null) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==2) {
				query.addQueryData("tagName2", "\"tagName\":\""+dTags.getTagName()+"\"");
				query.addQueryData("postType", type);
				PageResult<Post> data = kffPostRmiService.findPageWithDiscuss(query);
				if(data!=null) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			if(type==3) {
				query.addQueryData("tagName3", "\"tagName\":\""+dTags.getTagName()+"\"");
				query.addQueryData("postType", type);
				PageResult<Post> data = kffPostRmiService.findPageWithArticle(query);
				if(data!=null) {
					if(!data.getRows().isEmpty()) {
						bre.setData(data);
						return bre;
					}
				}
			}
			bre.setNoDataMsg();
		} catch (RestServiceException e) {
			logger.error("PostController getPostInfoWithTags:{}", e);
			return this.resResult(e.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("PostController getPostInfoWithTags:{}", e);
			return this.resResult(RestErrorCode.SYS_ERROR, e.getMessage());
		}
		return bre;
	}
}
