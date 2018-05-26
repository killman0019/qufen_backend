package com.tzg.service.comments;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;






import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.comments.Comments;
import com.tzg.entitys.comments.CommentsMapper;
import com.tzg.entitys.kffproject.Project;
import com.tzg.entitys.kffproject.ProjectMapper;
import com.tzg.entitys.post.Post;
import com.tzg.entitys.post.PostMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class CommentsService extends BaseService {

	@Autowired
	private CommentsMapper commentsMapper;	
	@Autowired
	private ProjectMapper projectMapper;	
	@Autowired
	private PostMapper postMapper;	
	
	@Transactional(readOnly=true)
    public Comments findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	Comments comments = commentsMapper.findById(id);
    	if(comments != null){
    		Post post = postMapper.findById(comments.getPostId());
    		if(post != null){
    			comments.setPostTitle(post.getPostTitle());
    			Project project = projectMapper.findById(post.getProjectId());
    			if(project != null){
    				comments.setProjectChineseName(project.getProjectChineseName());
    				comments.setProjectCode(project.getProjectCode());
    				comments.setProjectEnglishName(project.getProjectEnglishName());
    			}
    		}
    	}
        return comments;
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        commentsMapper.deleteById(id);
    }
	
	public void save(Comments comments) throws Exception {	    
		commentsMapper.save(comments);
	}
	
	public void update(Comments comments) throws Exception {	
		if(comments.getCommentsId() == null){
			throw new Exception("id不能为空");
		}
		commentsMapper.update(comments);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Comments> findPage(PaginationQuery query) throws Exception {
		PageResult<Comments> result = null;
		try {
			Integer count = commentsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Comments> list = commentsMapper.findPage(query.getQueryData());
				if(CollectionUtils.isNotEmpty(list)){
					for(Comments comments:list){
						Post post = postMapper.findById(comments.getPostId());
			    		if(post != null){
			    			comments.setPostTitle(post.getPostTitle());
			    			Project project = projectMapper.findById(post.getProjectId());
			    			if(project != null){
			    				comments.setProjectChineseName(project.getProjectChineseName());
			    				comments.setProjectCode(project.getProjectCode());
			    				comments.setProjectEnglishName(project.getProjectEnglishName());
			    			}
			    		}
					}
				}
				result = new PageResult<Comments>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
