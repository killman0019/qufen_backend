package com.tzg.wap.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopicsController extends BaseController {
	
	/**
	 * 专题资源
	 * @param address
	 * @return
	 */
	@RequestMapping("/topics/{address}")
	public String topics(@PathVariable String address,Model model, HttpServletRequest req){
		model.addAttribute("now", new Date().getTime());
		model.addAttribute("currentNav", "topics");
		String key = req.getParameter("key");
		model.addAttribute("tzg_recommend_link_key", key);
		
		return "/topics/"+address;
	}
}
