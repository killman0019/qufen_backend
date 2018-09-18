package com.tzg.rmi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.service.kff.BGroupMustReadService;
import com.tzg.common.service.kff.PostService;
import com.tzg.common.utils.DozerMapperUtils;
import com.tzg.entitys.kff.bgroupmustread.BGroupMustRead;
import com.tzg.entitys.kff.post.Post;
import com.tzg.entitys.kff.post.PostResponse;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rmi.service.KFFPostRmiService;

/**
 * 
 * ClassName: KFFPostRmiServiceImpl  
 * Function: TODO 关于post的相关服务实现类
 * date: 2018年8月4日 下午3:38:04  
 * 
 * @author zhangdd 
 * @version  
 * @since JDK 1.7 
 *
 */
public class KFFPostRmiServiceImpl implements KFFPostRmiService {

	@Autowired
	private PostService kffPostService;
	@Autowired
	private BGroupMustReadService bgroupMustReadService;
	
	public PageResult<Post> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Post> result = kffPostService.findPage(query);
		return result;
	}
	
	public PageResult<Post> findPageWithFollower(PaginationQuery query,Integer typec,Integer userId){
		return kffPostService.findPageWithFollower(query, typec, userId);
	}
	
	public PageResult<Post> findPageNewestList(PaginationQuery query,Integer typec,
			Integer userId){
		return kffPostService.findPageNewestList(query, typec, userId);
	}
	
	public PageResult<Post> findPageWithEvaluation(PaginationQuery query){
		return kffPostService.findPageWithEvaluation(query);
	}
	public PageResult<Post> findPageWithDiscuss(PaginationQuery query){
		return kffPostService.findPageWithDiscuss(query);
	}
	public PageResult<Post> findPageWithArticle(PaginationQuery query){
		return kffPostService.findPageWithArticle(query);
	}

	/**
	 * 暂时未使用(已在别处实现,此代码后期修改)
	 */
	@Override
	public Map<String, Object> countEveryPostIncome(Integer userId) throws RestServiceException {
		// 判断用户是否为空
		Map<String, Object> map = new HashMap<String, Object>();
		Double praiseIncome = null;
		Double donateIncome = null;
		Double postTotalIncome = null;

		if (userId == null) {
			throw new RestServiceException("用户没有登录,请重新登录!");
			// 查询 出当前帖子下的所有帖子 (是否在规定时间后进行查询)
		}
		List<Post> posts = kffPostService.selectAllPostByUserId(userId);
		if (!CollectionUtils.isEmpty(posts)) {// 判断当前用户名下的帖子是否存在
			for (Post post : posts) {
				if (null != post) {

				}
			}
		}
		map.put("praiseIncome", praiseIncome);
		map.put("donateIncome", donateIncome);
		map.put("postTotalIncome", postTotalIncome);
		return map;
	}

	/**
	 * 展示B圈必读相关信息
	 */
	@Override
	public PageResult<PostResponse> showBGmustRead(PaginationQuery query) throws RestServiceException {
		// TODO 展示B圈必读的信息 分页展示
		PageResult<PostResponse> postResponsePage = null;
		List<PostResponse> postResponseList = new ArrayList<PostResponse>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sql_keyword_orderBy", "create_time");
		map.put("sql_keyword_sort", "DESC");
		map.put("status", "1");
		map.put("isShow", "1");
		query.setQueryData(map);
		PageResult<BGroupMustRead> bgroupMustReadPage = bgroupMustReadService.findPage(query);
		if (null != bgroupMustReadPage && !CollectionUtils.isEmpty(bgroupMustReadPage.getRows())) {
			List<BGroupMustRead> bgroupMustreadList = bgroupMustReadPage.getRows();
			for (BGroupMustRead bGroupMustRead : bgroupMustreadList) {
				// 根据查询的对象查询相关帖子
				Integer postId = bGroupMustRead.getPostId();
				Post post = kffPostService.findById(postId);
				if (null != post) {
					PostResponse postResponse = new PostResponse();
					DozerMapperUtils.map(post, postResponse);
					postResponseList.add(postResponse);
				}

			}
			postResponsePage = new PageResult<PostResponse>(postResponseList, bgroupMustReadPage.getRowCount(), query);
		}
		return postResponsePage;
	}
}
