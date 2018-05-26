package com.tzg.service.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.post.Post;
import com.tzg.entitys.post.PostMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class PostService extends BaseService {

	@Autowired
	private PostMapper postMapper;	
	   
	@Transactional(readOnly=true)
    public Post findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return postMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        postMapper.deleteById(id);
    }
	
	public void save(Post post) throws Exception {	    
		postMapper.save(post);
	}
	
	public void update(Post post) throws Exception {	
		if(post.getPostId() == null){
			throw new Exception("id不能为空");
		}
		postMapper.update(post);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Post> findPage(PaginationQuery query) throws Exception {
		PageResult<Post> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPage(query.getQueryData());
				result = new PageResult<Post>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
