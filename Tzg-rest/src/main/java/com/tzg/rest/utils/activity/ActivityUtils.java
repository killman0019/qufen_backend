package com.tzg.rest.utils.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.tzg.common.enums.MiningActivityStatus;
import com.tzg.common.utils.DateUtil;
import com.tzg.entitys.kff.activity.MiningActivity;

public class ActivityUtils {
	
	/** 
	* @Title: checkActivityStatus 
	* @Description: TODO <校验活动是否可领取奖励>
	* @author linj <方法创建作者>
	* @create 上午10:01:14
	* @param @param mnAct
	* @param @return <参数说明>
	* @return Map<String,Object> 
	* @throws 
	* @update 上午10:01:14
	* @updator <修改人 修改后更新修改时间，不同人修改再添加>
	* @updateContext <修改内容>
	*/
	public static Map<String,Object> checkActivityStatus(MiningActivity mnAct) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(null==mnAct) {
			map.put("code", 500);
			map.put("msg", "未找到活动！");
			return map;
		}
		//判断活动的分数，不够的话不能参数活动咯
		if(mnAct.getTokenSurplusNum()<1) {
			map.put("code", 500);
			map.put("msg", "活动数量不足！");
			return map;
		}
		String startTime = mnAct.getBeginDt();
		String endTime = mnAct.getEndDt();
		long newTime = new Date().getTime();
		//判断活动是否未开始，是就不能继续
		long beginTime = DateUtil.getDate(startTime,DateUtil.dateTimePattern).getTime();
		if(newTime<beginTime) {
			map.put("code", 500);
			map.put("msg", "活动未开始！");
			return map;
		}
		//判断活动是否已结束，是就不能继续
		long offTime = DateUtil.getDate(endTime,DateUtil.dateTimePattern).getTime();
		if(newTime>offTime) {
			map.put("code", 500);
			map.put("msg", "活动已结束！");
			return map;
		}
		//判断活动是否已终止，是就不能继续
		if(mnAct.getStatus()==MiningActivityStatus.OVER.getValue()) {
			map.put("code", 500);
			map.put("msg", "活动已终止！");
			return map;
		}
		//判断活动是否已挖完，是就不能继续
		if(mnAct.getStatus()==MiningActivityStatus.DIGOUT.getValue()) {
			map.put("code", 500);
			map.put("msg", "活动奖励已领取完！");
			return map;
		}
		map.put("code", 200);
		map.put("msg", "success");
		return map;
	}
}
