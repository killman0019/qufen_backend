package com.tzg.wap.controller.orther;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzg.rmi.service.CmsRmiService;

@Controller
public class IndexController extends BaseController {

	@Autowired
	private CmsRmiService cmsRmiService;

	
	
	
	/**
	 * 关于我们
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/aboutUs")
	public String aboutUs(){
		return "/static/aboutUs";
	}
	

	
	  /**
     * 系统错误 页面跳转
     * @param code
     * @return
     */
    @RequestMapping("/error/{code}")
    public String error(@PathVariable String code) {
        return "/error/" + code;
    }
}
