package com.tzg.service.evaluation;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.evaluation.Evaluation;
import com.tzg.entitys.evaluation.EvaluationMapper;
import com.tzg.entitys.post.Post;
import com.tzg.entitys.post.PostMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class EvaluationService extends BaseService {

	@Autowired
	private EvaluationMapper evaluationMapper;	
	@Autowired
	private PostMapper postMapper;
	   
	@Transactional(readOnly=true)
    public Evaluation findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	Evaluation result = new Evaluation();
    	result = evaluationMapper.findById(id);
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
        evaluationMapper.deleteById(id);
    }
	
	public void save(Evaluation evaluation) throws Exception {	    
		evaluationMapper.save(evaluation);
	}
	
	public void update(Evaluation evaluation) throws Exception {	
		if(evaluation.getEvaluationId() == null){
			throw new Exception("id不能为空");
		}
		evaluationMapper.update(evaluation);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Evaluation> findPage(PaginationQuery query) throws Exception {
		PageResult<Evaluation> result = null;
		try {
			Integer count = postMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Post> list = postMapper.findPage(query.getQueryData());
				List<Evaluation> resultList = new ArrayList<>();
				if(CollectionUtils.isNotEmpty(list)){
					for(Post post:list){
						Evaluation eva = evaluationMapper.findById(post.getPostId());
						if(eva != null){
							BeanUtils.copyProperties(eva, post);
							resultList.add(eva);
						}
					}
				}				
				result = new PageResult<Evaluation>(resultList,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void updateStatus(Evaluation eva)  throws Exception {
		if(eva.getEvaluationId() == null){
			throw new Exception("id不能为空");
		}
		if(eva.getPostId() == null){
			throw new Exception("postid不能为空");
		}
		Evaluation existEva = evaluationMapper.findById(eva.getEvaluationId());
		if(existEva == null){
			throw new Exception("非法evaId,评测记录不存在");
		}
		Post existPost = postMapper.findById(eva.getPostId());
		if(existPost == null){
			throw new Exception("非法postId,帖子不存在");
		}		
		
		if(existEva.getPostId() != existPost.getPostId()){
			throw new Exception("非法postId,传入postid和评测postId不符");
		}
		
		Post post = new Post();
		post.setPostId(eva.getPostId());
		post.setStatus(eva.getStatus());
		post.setUpdateTime(eva.getUpdateTime());
		evaluationMapper.update(eva);
		postMapper.update(post);		
	}
	

	
}
