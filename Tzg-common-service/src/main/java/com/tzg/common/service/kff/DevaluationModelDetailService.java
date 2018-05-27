package com.tzg.common.service.kff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetail;
import com.tzg.entitys.kff.devaluationModelDetail.DevaluationModelDetailMapper;
import com.tzg.rest.exception.rest.RestServiceException;


@Service(value="KFFDevaluationModelDetailService")
@Transactional
public class DevaluationModelDetailService {

	@Autowired
	private DevaluationModelDetailMapper devaluationModelDetailMapper;	
	   
	@Transactional(readOnly=true)
    public DevaluationModelDetail findById(java.lang.Integer id) throws RestServiceException {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return devaluationModelDetailMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
    	DevaluationModelDetail update = new DevaluationModelDetail();
    	update.setId(id);
    	update.setUpdateTime(new Date());
    	update.setStatus(0);
        devaluationModelDetailMapper.update(update);
    }
	
	public void save(DevaluationModelDetail devaluationModelDetail) throws RestServiceException {	
		
		if(devaluationModelDetail.getModelId() == null){
			throw new RestServiceException("模型ID不能为空");
		}
		if(StringUtils.isBlank(devaluationModelDetail.getDetailDesc())){
			throw new RestServiceException("维度描述不能为空");
		}
		if(devaluationModelDetail.getDetailDesc().length()>200){
			throw new RestServiceException("维度描述不能超过200字符");
		}
		if(StringUtils.isBlank(devaluationModelDetail.getDetailName())){
			throw new RestServiceException("维度名称不能为空");
		}
		if(devaluationModelDetail.getDetailName().length()>30){
			throw new RestServiceException("维度名称不能超过30字符");
		}
		if(devaluationModelDetail.getDetailWeight() == null){
			throw new RestServiceException("权重不能为空");
		}
		if(devaluationModelDetail.getDetailWeight() <=0 ||devaluationModelDetail.getDetailWeight()>=100){
			throw new RestServiceException("权重数只能为0-100之间数字");
		}
		
		//校验已存在维度之和是否超过100
		Map<String,String> map = new HashMap<String,String>();
		map.put("modelId", devaluationModelDetail.getModelId() +"");
		map.put("status", "1");
		List<DevaluationModelDetail> detailList = devaluationModelDetailMapper.findByWhere(map);
		if(CollectionUtils.isNotEmpty(detailList)){
			int total = 0;
			for(DevaluationModelDetail detail:detailList){
				total = total + (detail.getDetailWeight()==null?0:detail.getDetailWeight());
			}
			if(total + devaluationModelDetail.getDetailWeight() > 100){
				throw new RestServiceException("同一模型下的权重之和:"+(total+ devaluationModelDetail.getDetailWeight())+"已超过100");
			}
		}
		
		//校验同名维度是否存在
		Map<String,String> namemap = new HashMap<String,String>();
		namemap.put("detailName", devaluationModelDetail.getDetailName());
		namemap.put("status", "1");
		List<DevaluationModelDetail> sameNameList = devaluationModelDetailMapper.findByWhere(namemap);
		if(CollectionUtils.isNotEmpty(sameNameList)){				
			throw new RestServiceException("同一模型下,维度名称"+devaluationModelDetail.getDetailName()+"已存在");		
		}
		
		//保存
		Date now = new Date();
		devaluationModelDetail.setCreateTime(now);
		devaluationModelDetail.setStatus(1);
		devaluationModelDetail.setUpdateTime(now);
		devaluationModelDetailMapper.save(devaluationModelDetail);
	}
	
	public void update(DevaluationModelDetail devaluationModelDetail) throws RestServiceException {	
		if(devaluationModelDetail.getId() == null){
			throw new RestServiceException("id不能为空");
		}
		devaluationModelDetailMapper.update(devaluationModelDetail);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<DevaluationModelDetail> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<DevaluationModelDetail> result = null;
		try {
			Integer count = devaluationModelDetailMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<DevaluationModelDetail> list = devaluationModelDetailMapper.findPage(query.getQueryData());
				result = new PageResult<DevaluationModelDetail>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

	
}
