package com.tzg.service.article;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.article.Article;
import com.tzg.entitys.article.ArticleMapper;
import com.tzg.entitys.evaluation.Evaluation;
import com.tzg.entitys.post.Post;
import com.tzg.entitys.post.PostMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class ArticleService extends BaseService {

	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private PostMapper postMapper;
	   
	@Transactional(readOnly=true)
    public Article findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	Article result = new Article();
    	result = articleMapper.findById(id);
    	if(result != null){
    	  Post post = postMapper.findById(result.getPostId());
    	  if(post != null){
    		  BeanUtils.copyProperties(result, post);
    	  }
    		  
    	}
        return result;
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        articleMapper.deleteById(id);
    }
	
	public void save(Article article) throws Exception {	    
		articleMapper.save(article);
	}
	
	public void update(Article article) throws Exception {	
		if(article.getArticleId() == null){
			throw new Exception("id不能为空");
		}
		articleMapper.update(article);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Article> findPage(PaginationQuery query) throws Exception {
		PageResult<Article> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPage(query.getQueryData());
				List<Article> resultList = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(list)){
					for(Post post:list){
						Article article = articleMapper.findById(post.getPostId());
						if(article != null){
							BeanUtils.copyProperties(article, post);
							resultList.add(article);
						}
					}
				}		
				result = new PageResult<Article>(resultList,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public void updateStatus(Article article)  throws Exception {
		if(article.getArticleId() == null){
			throw new Exception("id不能为空");
		}
		if(article.getPostId() == null){
			throw new Exception("postid不能为空");
		}
		Article existEva = articleMapper.findById(article.getArticleId());
		if(existEva == null){
			throw new Exception("非法articleId,文章记录不存在");
		}
		Post existPost = postMapper.findById(article.getPostId());
		if(existPost == null){
			throw new Exception("非法postId,帖子不存在");
		}		
		
		if(existEva.getPostId() != existPost.getPostId()){
			throw new Exception("非法postId,传入postid和文章postId不符");
		}
		
		Post post = new Post();
		post.setPostId(article.getPostId());
		post.setStatus(article.getStatus());
		post.setUpdateTime(article.getUpdateTime());
		articleMapper.update(article);
		postMapper.update(post);		
	}
}
