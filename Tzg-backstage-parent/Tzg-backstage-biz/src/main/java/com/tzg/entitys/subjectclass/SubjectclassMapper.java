package com.tzg.entitys.subjectclass;

import java.util.Map;

import com.tzg.entitys.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface SubjectclassMapper extends BaseMapper<Subjectclass, java.lang.Integer> {	
	
	public Integer maxItypeid(Integer iclassType);
	
	public Subjectclass findSubjectClass(Map<String,String> map);

}
