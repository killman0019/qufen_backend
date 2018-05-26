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
import com.tzg.entitys.dtags.Dtags;
import com.tzg.service.dtags.DtagsService;


@Controller
@RequestMapping("/dtags")
public class DtagsController extends BaseController {
	private static Logger log = Logger.getLogger(DtagsController.class);
	
	@Autowired
	private DtagsService dtagsService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/dtags/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Dtags dtags = dtagsService.findById(id);
			model.addAttribute("param", dtags);
			model.addAttribute(SUCCESS, true);
			return "/dtags/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/dtags/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("dtags") Dtags dtags,Model model) {		
		try {
						
			dtagsService.save(dtags);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dtags";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", dtags);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/dtags/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("dtags") Dtags dtags,Model model,Integer id) throws Exception {		
		try {
			dtags.setTagId(id);
			dtagsService.update(dtags);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dtags";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", dtags);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/dtags/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			dtagsService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dtags";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/dtags";
		}
	}
	
	@RequestMapping(value="/active")
	public String active(Integer id,Model model) throws Exception {
		try {
			dtagsService.active(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/dtags";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/dtags";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String tagName) throws Exception{
		if(StringUtils.isNotBlank(tagName)){
			query.addQueryData("tagName", tagName);
		}
		PageResult<Dtags> pageList = dtagsService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/dtags/list";
	}
	
}


