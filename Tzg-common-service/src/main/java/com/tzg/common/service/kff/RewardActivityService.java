package com.tzg.common.service.kff;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.activity.RewardActivity;
import com.tzg.entitys.kff.activity.RewardActivityMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional(rollbackFor=Exception.class)
public class RewardActivityService {
	private static final Log logger = LogFactory.getLog(RewardActivityService.class);
	@Autowired
	private RewardActivityMapper rewardActivityMapper;

	@Transactional(readOnly = true)
	public RewardActivity findById(java.lang.Integer id) {
		return rewardActivityMapper.findById(id);
	}

	public List<RewardActivity> findListByAttr(Map<String, Object> map) {
		return rewardActivityMapper.findListByAttr(map);
	}

	public void saveRewardActivity(RewardActivity reAct) throws RestServiceException {
		rewardActivityMapper.save(reAct);
	}

	@Transactional(readOnly = true)
	public PageResult<RewardActivity> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<RewardActivity> result = null;
		try {
			Integer count = rewardActivityMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1) * query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<RewardActivity> list = rewardActivityMapper.findPage(query.getQueryData());
				result = new PageResult<RewardActivity>(list, count, query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
