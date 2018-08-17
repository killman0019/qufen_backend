package com.tzg.common.service.kff;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.common.base.BaseService;
import com.tzg.common.enums.MiningActivityStatus;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.kff.activity.MiningActivity;
import com.tzg.entitys.kff.activity.MiningActivityMapper;
import com.tzg.rest.exception.rest.RestServiceException;

@Service
@Transactional
public class MiningActivityService extends BaseService {

	@Autowired
	private MiningActivityMapper miningActivityMapper;	
	
	public void updateByMap(Map<String,Object> map) throws RestServiceException {	
		miningActivityMapper.updateByMap(map);
	}	
	
	public MiningActivity findById(Integer id) {
		return miningActivityMapper.selectByPrimaryKey(id);
	}
	
	public List<MiningActivity> findListByAttr(Map<String, Object> map) {
		return miningActivityMapper.findListByAttr(map);
	}
	
	@Transactional(readOnly=true)
	public PageResult<MiningActivity> findPage(PaginationQuery query) throws RestServiceException {
		PageResult<MiningActivity> result = null;
		try {
			Integer count = miningActivityMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord", Integer.toString(query.getRowsPerPage()));
				List<MiningActivity> list = miningActivityMapper.findPage(query.getQueryData());
				result = new PageResult<MiningActivity>(list,count,query);
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/** 
	* @Title: manualActivity 
	* @Description: TODO <手动触发更新活动的开始和结束时间>
	* @author linj <方法创建作者>
	* @create 上午9:28:59
	* @param  <参数说明>
	* @return void 
	* @throws 
	* @update 上午9:28:59
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	public void manualActivity() {
		Map<String, Object> seMap = new HashMap<String, Object>();
		//判断未开始的活动开始时间到了，将状态置为开始
		seMap.put("state", MiningActivityStatus.UNSTART.getValue());
		seMap.put("nowBeginDt", new Date());
		seMap.put("status", MiningActivityStatus.STARTING.getValue());
		miningActivityMapper.updateByMap(seMap);
		//更新挖矿活动数量为0的状态变为已挖完
		seMap.clear();
		seMap.put("state", MiningActivityStatus.STARTING.getValue());
		seMap.put("status", MiningActivityStatus.DIGOUT.getValue());
		seMap.put("tokenSurplusNum", 0);
		miningActivityMapper.updateByMap(seMap);
		//判断进行中的活动结束时间到了，将状态置为结束
		seMap.clear();
		seMap.put("statec1", MiningActivityStatus.STARTING.getValue());
		seMap.put("statec2", MiningActivityStatus.DIGOUT.getValue());
		seMap.put("nowEndDt", new Date());
		seMap.put("status", MiningActivityStatus.END.getValue());
		miningActivityMapper.updateByMap(seMap);
	}

	//使用乐观锁去更新数据
	public Integer updateByOptimisticLock(MiningActivity mnAct) {
		return miningActivityMapper.updateByOptimisticLock(mnAct);
	}
	
	
	
}
