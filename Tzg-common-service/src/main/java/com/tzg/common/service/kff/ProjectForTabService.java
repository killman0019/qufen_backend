package com.tzg.common.service.kff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tzg.common.base.BaseService;
import com.tzg.entitys.kff.projectForTab.ProjectForTab;
import com.tzg.entitys.kff.projectForTab.ProjectForTabMapper;

@Service
public class ProjectForTabService extends BaseService {
	@Autowired
	private ProjectForTabMapper projectForTabMapper;

	public List<String> selectProjectCodeByTabid(Integer tabId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> projectCodes = new ArrayList<String>();
		map.put("tabId", tabId);
		map.put("status", 1);
		List<ProjectForTab> projectForTabs = projectForTabMapper.findByMap(map);
		if (!CollectionUtils.isEmpty(projectForTabs)) {
			for (ProjectForTab projectForTab : projectForTabs) {
				if (null != projectForTab) {
					projectCodes.add(projectForTab.getProjectCode());
				}
			}
		}
		return projectCodes;
	}

}
