package com.tzg.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.user.User;
import com.tzg.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static Logger log = Logger.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/user/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			User user = userService.findById(id);
			model.addAttribute("param", user);
			model.addAttribute(SUCCESS, true);
			return "/user/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/user/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("user") User user,Model model) {		
		try {
			userService.save(user);
			model.addAttribute(SUCCESS, true);
			return "redirect:/user";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", user);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/user/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("user") User user,Model model) throws Exception {		
		try {
			userService.update(user);
			model.addAttribute(SUCCESS, true);
			return "redirect:/user";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", user);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/user/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			userService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/user";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/user";
		}
	}
	
	@RequestMapping(value="/active")
	public String active(Integer id,Model model) throws Exception {
		try {
			userService.active(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/user";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/user";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String userName) throws Exception{	
		
		if(StringUtils.isNotBlank(userName)){
			query.addQueryData("userName", userName);
		}		
		PageResult<User> pageList = userService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/user/list";
	}
}


