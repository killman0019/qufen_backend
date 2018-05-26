package com.tzg.service.discuss;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.article.Article;
import com.tzg.entitys.discuss.Discuss;
import com.tzg.entitys.discuss.DiscussMapper;
import com.tzg.entitys.evaluation.Evaluation;
import com.tzg.entitys.post.Post;
import com.tzg.entitys.post.PostMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class DiscussService extends BaseService {

	@Autowired
	private DiscussMapper discussMapper;	
	@Autowired
	private PostMapper postMapper;
	   
	@Transactional(readOnly=true)
    public Discuss findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return discussMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        discussMapper.deleteById(id);
    }
	
	public void save(Discuss discuss) throws Exception {
		if(StringUtils.isBlank(discuss.getDisscussContents())){
			throw new Exception("讨论不能为空");
		}
		if(discuss.getDisscussContents().length() > 300){
			throw new Exception("讨论不能超过300字符");
		}
		
		discussMapper.save(discuss);
	}
	
	public void update(Discuss discuss) throws Exception {	
		if(discuss.getDiscussId() == null){
			throw new Exception("id不能为空");
		}
		discussMapper.update(discuss);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Discuss> findPage(PaginationQuery query) throws Exception {
		PageResult<Discuss> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				
				List<Post> list = postMapper.findPage(query.getQueryData());
				List<Discuss> resultList = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(list)){
					for(Post post:list){
						Discuss discuss = discussMapper.findById(post.getPostId());
						if(discuss != null){
							BeanUtils.copyProperties(discuss, post);
							resultList.add(discuss);
						}
					}
				}		
				result = new PageResult<Discuss>(resultList,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	public void updateStatus(Discuss dis)  throws Exception {
		if(dis.getDiscussId() == null){
			throw new Exception("id不能为空");
		}
		if(dis.getPostId() == null){
			throw new Exception("postid不能为空");
		}
		Discuss existEva = discussMapper.findById(dis.getDiscussId());
		if(existEva == null){
			throw new Exception("非法evaId,评测记录不存在");
		}
		Post existPost = postMapper.findById(dis.getPostId());
		if(existPost == null){
			throw new Exception("非法postId,帖子不存在");
		}		
		
		if(existEva.getPostId() != existPost.getPostId()){
			throw new Exception("非法postId,传入postid和讨论postId不符");
		}
		
		Post post = new Post();
		post.setPostId(dis.getPostId());
		post.setStatus(dis.getStatus());
		post.setUpdateTime(dis.getUpdateTime());
		discussMapper.update(dis);
		postMapper.update(post);		
	}
}
