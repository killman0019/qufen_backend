package com.tzg.wap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.utils.Assert;
import com.tzg.entitys.areainfo.Areainfo;
import com.tzg.rmi.service.AreainfoRmiService;

@Controller
@RequestMapping("/area")
public class AreainfoController {
	@Autowired
	private AreainfoRmiService areainfoRmiService;
	
	/**
	 * 得到省份
	 * @return
	 */
	@RequestMapping("/queryFirstLevelAll")
	@ResponseBody
	public List<Areainfo> queryFirstLevelAll(){
		try {
			return areainfoRmiService.findFirstLevelList();
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据地区编号 获取下级地区
	 * @param vcCode
	 * @return
	 */
	@RequestMapping("/queryInfoByiFatherID")
	@ResponseBody
	public List<Areainfo> queryInfoByiFatherID(String vcCode){
		try {
			Assert.isNull(vcCode, "请选择地区");
			return areainfoRmiService.findByParentCode(vcCode);
		} catch (Exception e) {
			return null;
		}
	}
}
