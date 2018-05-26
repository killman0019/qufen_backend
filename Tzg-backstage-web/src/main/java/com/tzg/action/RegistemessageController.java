package com.tzg.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.core.utils.DateUtil;
import com.tzg.entitys.registemessage.Registemessage;
import com.tzg.service.registemessage.RegistemessageService;

@Controller
@RequestMapping("/registemessage")
public class RegistemessageController extends BaseController {
	private static Logger log = Logger.getLogger(RegistemessageController.class);
	
	@Autowired
	private RegistemessageService registemessageService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/registemessage/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Registemessage registemessage = registemessageService.findById(id);
			model.addAttribute("param", registemessage);
			model.addAttribute(SUCCESS, true);
			return "/registemessage/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/registemessage/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("registemessage") Registemessage registemessage,Model model) {		
		try {
			registemessageService.saveOrUpdateVerify(registemessage);
			registemessageService.save(registemessage);
			model.addAttribute(SUCCESS, true);
			return "redirect:/registemessage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", registemessage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/registemessage/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("registemessage") Registemessage registemessage,Model model) throws Exception {		
		try {
			registemessageService.update(registemessage);
			model.addAttribute(SUCCESS, true);
			return "redirect:/registemessage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", registemessage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/registemessage/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			registemessageService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/registemessage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/registemessage";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,
			@RequestParam(required = false) String iState,
			Model model) throws Exception{	
		if (!StringUtils.isBlank(iState)) {
			query.addQueryData("istate", iState);
		}
		PageResult<Registemessage> pageList = registemessageService.findPage(query);
		if(pageList!=null&&pageList.getRows().size()>0){
		for (int i=0 ; i<pageList.getRows().size();i++){
			String dtStart=pageList.getRows().get(i).getDtStartTimeStr();
			String dtEnd=pageList.getRows().get(i).getDtEndTimeStr();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date date=new java.util.Date();
			String str=sdf.format(date);
			if(DateUtil.compareDateTime(dtEnd,str) >= 0&&DateUtil.compareDateTime(str,dtStart) >= 0&&pageList.getRows().get(i).getIstate()==1){
				pageList.getRows().get(i).setIstate(0);
			}
		}
		}
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/registemessage/list";
	}
	
}


