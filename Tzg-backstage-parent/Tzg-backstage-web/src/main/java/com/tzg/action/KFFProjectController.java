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
import com.tzg.entitys.kffproject.Project;
import com.tzg.service.kffproject.KFFProjectService;

@Controller
@RequestMapping("/project")
public class KFFProjectController extends BaseController {
	private static Logger log = Logger.getLogger(KFFProjectController.class);
	
	@Autowired
	private KFFProjectService projectService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/project/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Project project = projectService.findById(id);
			model.addAttribute("param", project);
			model.addAttribute(SUCCESS, true);
			return "/project/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/project/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("project") Project project,Model model) {		
		try {
			projectService.save(project);
			model.addAttribute(SUCCESS, true);
			return "redirect:/project";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", project);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/project/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("project") Project project,Model model) throws Exception {		
		try {
			projectService.update(project);
			model.addAttribute(SUCCESS, true);
			return "redirect:/project";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", project);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/project/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			Project project = new Project();
			project.setProjectId(id);
			project.setUpdateTime(new Date());
			project.setStatus(0);
			projectService.update(project);
			model.addAttribute(SUCCESS, true);
			return "redirect:/project";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/project";
		}
	}
	@RequestMapping(value="/approve")
	public String approve(Integer id,Model model) throws Exception {
		try {
			Project project = new Project();
			project.setProjectId(id);
			project.setUpdateTime(new Date());
			project.setStatus(1);
			project.setState(2);
			projectService.update(project);
			model.addAttribute(SUCCESS, true);
			return "redirect:/project";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/project";
		}
	}
	@RequestMapping(value="/reject")
	public String reject(Integer id,Model model) throws Exception {
		try {
			Project project = new Project();
			project.setProjectId(id);
			project.setUpdateTime(new Date());
			project.setStatus(1);
			project.setState(3);
			projectService.update(project);
			model.addAttribute(SUCCESS, true);
			return "redirect:/project";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/project";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String projectCode,String projectEnglishName,String projectChineseName,Integer listed,String issueDateBegin,String issueDateEnd) throws Exception{		
		query.addQueryData("status", "1");
		if(StringUtils.isNotBlank(projectCode)){
			query.addQueryData("projectCode", projectCode);
		}
		if(StringUtils.isNotBlank(projectEnglishName)){
			query.addQueryData("projectEnglishName", projectEnglishName);
		}
		if(StringUtils.isNotBlank(projectChineseName)){
			query.addQueryData("projectChineseName", projectChineseName);
		}
		if(listed != null){
			query.addQueryData("listed", listed + "");
		}
		if(StringUtils.isNotBlank(issueDateBegin)){
			query.addQueryData("issueDateBegin", issueDateBegin);
		}
		if(StringUtils.isNotBlank(issueDateEnd)){
			query.addQueryData("issueDateEnd", issueDateEnd);
		}
		
		PageResult<Project> pageList = projectService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/project/list";
	}
	
}


