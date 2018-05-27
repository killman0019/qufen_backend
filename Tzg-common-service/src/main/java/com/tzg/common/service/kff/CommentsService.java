package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.comments.Comments;
import com.tzg.entitys.kff.comments.CommentsMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service(value="KFFCommentsService")
@Transactional
public class CommentsService {

	@Autowired
	private CommentsMapper commentsMapper;	
	   
	@Transactional(readOnly=true)
    public Comments findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return commentsMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        commentsMapper.deleteById(id);
    }
	
	public void save(Comments comments) throws RestServiceException {	    
		commentsMapper.save(comments);
	}
	
	public void update(Comments comments) throws RestServiceException {	
		if(comments.getCommentsId() == null){
			throw new RestServiceException("id不能为空");
		}
		commentsMapper.update(comments);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Comments> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<Comments> result = null;
		try {
			Integer count = commentsMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Comments> list = commentsMapper.findPage(query.getQueryData());
				result = new PageResult<Comments>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	public List<Comments> findAllCommentsByWhere(Map<String, Object> map) {
		
		return commentsMapper.findAllCommentsByWhere(map);
	}

	public void increasePraiseNum(Integer commentsId) {
		
		commentsMapper.increasePraiseNum(commentsId);
		
	}
	
	public void decreasePraiseNum(Integer commentsId) {
		commentsMapper.decreasePraiseNum(commentsId);		
	}

	public void updateUserInfo(Map<String, Object> map) {
		commentsMapper.updateUserInfo(map);
		
	}
	
}
