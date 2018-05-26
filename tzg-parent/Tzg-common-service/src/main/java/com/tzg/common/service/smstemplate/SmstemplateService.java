package com.tzg.common.service.smstemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzg.entitys.smstemplate.Smstemplate;
import com.tzg.entitys.smstemplate.SmstemplateMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
public class SmstemplateService {

	@Autowired
	private SmstemplateMapper smstemplateMapper;	
	   
    public Smstemplate findById(java.lang.Integer id) throws Exception {
    	if(id == null){
			throw new RestServiceException("id不能为空");
		}
        return smstemplateMapper.findById(id);
    }
	
    public Smstemplate findByType(java.lang.Integer itype) throws Exception {
    	if(itype == null){
			throw new RestServiceException("itype不能为空");
		}
        return smstemplateMapper.findByType(itype);
    }
	
}
