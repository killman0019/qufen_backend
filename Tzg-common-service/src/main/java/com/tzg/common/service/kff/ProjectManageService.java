package com.tzg.common.service.kff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzg.entitys.kff.projectManage.ProjectManage;
import com.tzg.entitys.kff.projectManage.ProjectManageMapper;

@Transactional
@Service(value = "ProjectManageService")
public class ProjectManageService {

	@Autowired
	private ProjectManageMapper projectManageMapper;

	@Transactional(readOnly = true)
	public List<ProjectManage> selectAllTab() {
		// TODO 查询数据库中的所有有效的tab键
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "1");
		map.put("isShow", "1");
		List<ProjectManage> projectManages = projectManageMapper.findByMap(map);
		return projectManages;
	}

	@Transactional(readOnly = true)
	public ProjectManage selectById(Integer tabId) {
		// TODO 根据tabid查询projectmanage
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tabId", tabId);
		map.put("status", "1");
		map.put("isShow", "1");
		List<ProjectManage> projectManages = projectManageMapper.findByMap(map);
		if (!CollectionUtils.isEmpty(projectManages)) {
			ProjectManage projectManage = projectManages.get(0);
			if (null != projectManage) {
				return projectManage;
			}
			return null;
		}
		return null;
	}

}
