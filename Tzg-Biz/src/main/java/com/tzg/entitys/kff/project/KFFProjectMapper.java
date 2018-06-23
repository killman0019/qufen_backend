package com.tzg.entitys.kff.project;
import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;

import org.springframework.stereotype.Repository;

@Repository
public interface KFFProjectMapper extends BaseMapper<KFFProject, java.lang.Integer> {

	List<KFFProject> findProjectByCode(Map<String, Object> map);

	void increaseFollowerNum(Integer projectId);

	void decreaseFollowerNum(Integer projectId);	

	List<KFFProject> findProjectName();

	KFFProject findProjectIdByCodeAndChineseName(KFFProject kffProject);

	void increaseRaterNum(Integer projectId);

	void updateTotalScore(KFFProject project);


}
