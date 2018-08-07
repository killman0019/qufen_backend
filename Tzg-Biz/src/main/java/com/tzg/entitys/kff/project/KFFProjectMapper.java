package com.tzg.entitys.kff.project;

import java.util.List;
import java.util.Map;

import com.tzg.common.base.BaseMapper;
import com.tzg.entitys.kff.user.KFFUser;

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

	Integer findPageCountInList(Map<String, Object> queryData);

	List<KFFProject> findPageInList(Map<String, Object> queryData);

	List<KFFProject> findByMap(Map<String, Object> map);

	public List<KFFProject> findListByMap(Map<String, Object> map);

	List<ProjectResponse> findAllProjectAndTrade(Map<String, Object> queryData);

}
