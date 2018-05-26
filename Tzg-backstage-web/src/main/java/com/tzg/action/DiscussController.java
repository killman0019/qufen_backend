package com.tzg.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.discuss.Discuss;
import com.tzg.entitys.evaluation.Evaluation;
import com.tzg.service.discuss.DiscussService;

@Controller
@RequestMapping("/post/discuss")
public class DiscussController extends BaseController {
	private static Logger log = Logger.getLogger(DiscussController.class);
	
	@Autowired
	private DiscussService discussService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/discuss/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Discuss discuss = discussService.findById(id);
			model.addAttribute("param", discuss);
			model.addAttribute(SUCCESS, true);
			return "/discuss/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/discuss/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("discuss") Discuss discuss,Model model) {		
		try {
			discussService.save(discuss);
			model.addAttribute(SUCCESS, true);
			return "redirect:/discuss";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", discuss);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/discuss/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("discuss") Discuss discuss,Model model) throws Exception {		
		try {
			discussService.update(discuss);
			model.addAttribute(SUCCESS, true);
			return "redirect:/discuss";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", discuss);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/discuss/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model,Integer postId) throws Exception {
		try {
			Discuss dis = new Discuss();
			dis.setDiscussId(id);
			dis.setPostId(postId);
			dis.setStatus(0);
			dis.setUpdateTime(new Date());
			discussService.updateStatus(dis);
			model.addAttribute(SUCCESS, true);
			return "redirect:/discuss";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/discuss";
		}
	}
	
	@RequestMapping(value="/active")
	public String active(Integer id,Model model,Integer postId) throws Exception {
		try {
			Discuss dis = new Discuss();
			dis.setDiscussId(id);
			dis.setPostId(postId);
			dis.setStatus(1);
			dis.setUpdateTime(new Date());
			discussService.updateStatus(dis);
			model.addAttribute(SUCCESS, true);
			return "redirect:/discuss";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/discuss";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String projectCode,String createUserName,String postTitle,Integer status) throws Exception{		
		
		if(StringUtils.isNotBlank(projectCode)){
			query.addQueryData("projectCode", projectCode);
		}
		if(StringUtils.isNotBlank(createUserName)){
			query.addQueryData("createUserName", createUserName);		
		}
		if(StringUtils.isNotBlank(postTitle)){
			query.addQueryData("postTitle", postTitle);
		}
		if(status != null){
			query.addQueryData("status", status+"");
		}
		query.addQueryData("postType", "2");
		PageResult<Discuss> pageList = discussService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/discuss/list";
	}
	
}


