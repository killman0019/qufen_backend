package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.base.BaseService;
import com.tzg.entitys.kff.activity.ExplainActivity;
import com.tzg.entitys.kff.activity.ExplainActivityMapper;

@Service
@Transactional
public class ExplainActivityService extends BaseService {

	@Autowired
	private ExplainActivityMapper explainActivityMapper;	
	
	public ExplainActivity findById(Integer id) {
		return explainActivityMapper.selectByPrimaryKey(id);
	}
	
	
	public List<ExplainActivity> findListByAttr(Map<String, Object> map) {
		return explainActivityMapper.findListByAttr(map);
	}
	
}
