package com.tzg.service.pcextendimage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.pcextendimage.Pcextendimage;
import com.tzg.entitys.pcextendimage.PcextendimageMapper;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class PcextendimageService extends BaseService {

	@Autowired
	private PcextendimageMapper pcextendimageMapper;	
	   
	@Transactional(readOnly=true)
    public Pcextendimage findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        return pcextendimageMapper.findById(id);
    }
	
    public void delete(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new Exception("id不能为空");
		}
        pcextendimageMapper.deleteById(id);
    }
	
	public void save(Pcextendimage pcextendimage) throws Exception {	    
		pcextendimageMapper.save(pcextendimage);
	}
	
	public void update(Pcextendimage pcextendimage) throws Exception {	
		if(pcextendimage.getId() == null){
			throw new Exception("id不能为空");
		}
		pcextendimageMapper.update(pcextendimage);
	}	
	
	@Transactional(readOnly=true)
	public PageResult<Pcextendimage> findPage(PaginationQuery query) throws Exception {
		PageResult<Pcextendimage> result = null;
		try {
			Integer count = pcextendimageMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<Pcextendimage> list = pcextendimageMapper.findPage(query.getQueryData());
				result = new PageResult<Pcextendimage>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Integer getMaxSort(Integer iextendPlace){
		return pcextendimageMapper.getMaxSort(iextendPlace);
	}
	
	public Integer getVaildMaxSort(Integer iextendPlace){
		return pcextendimageMapper.getVaildMaxSort(iextendPlace);
	}
}
