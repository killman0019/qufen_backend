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

import com.tzg.action.BaseController;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.dprojectType.DprojectType;
import com.tzg.service.dprojectType.DprojectTypeService;

@Controller
@RequestMapping("/dprojectType")
public class DprojectTypeController extends BaseController {
	private static Logger log = Logger.getLogger(DprojectTypeController.class);
	
	@Autowired
	private DprojectTypeService dprojectTypeService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/dprojectType/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			DprojectType dprojectType = dprojectTypeService.findById(id);
			model.addAttribute("param", dprojectType);
			model.addAttribute(SUCCESS, true);
			return "/dprojectType/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/dprojectType/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("dprojectType") DprojectType dprojectType,Model model) {		
		try {
			Date now = new Date();
			dprojectType.setCreateTime(now);
			dprojectType.setCreateUserId(0);
			dprojectType.setStatus(1);
			dprojectType.setUpdateTime(now);
			dprojectTypeService.save(dprojectType);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dprojectType";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", dprojectType);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/dprojectType/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("dprojectType") DprojectType dprojectType,Model model) throws Exception {		
		try {
			dprojectTypeService.update(dprojectType);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dprojectType";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", dprojectType);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/dprojectType/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			DprojectType dprojectType = new DprojectType();
			dprojectType.setProjectTypeId(id);
			dprojectType.setStatus(0);
			dprojectType.setUpdateTime(new Date());
			dprojectTypeService.update(dprojectType);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dprojectType";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/dprojectType";
		}
	}
	
	@RequestMapping(value="/active")
	public String active(Integer id,Model model) throws Exception {
		try {
			DprojectType dprojectType = new DprojectType();
			dprojectType.setProjectTypeId(id);
			dprojectType.setStatus(1);
			dprojectType.setUpdateTime(new Date());
			dprojectTypeService.update(dprojectType);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dprojectType";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/dprojectType";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String projectTypeName) throws Exception{
		if(StringUtils.isNotBlank(projectTypeName)){
			query.addQueryData("projectTypeName", projectTypeName);
		}
		PageResult<DprojectType> pageList = dprojectTypeService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/dprojectType/list";
	}
	
}


