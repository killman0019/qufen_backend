package com.tzg.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.smswhitelist.Smswhitelist;
import com.tzg.service.smswhitelist.SmswhitelistService;

@Controller
@RequestMapping("/smswhitelist")
public class SmswhitelistController extends BaseController {
	private static Logger log = Logger.getLogger(SmswhitelistController.class);
	
	@Autowired
	private SmswhitelistService smswhitelistService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/smswhitelist/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Smswhitelist smswhitelist = smswhitelistService.findById(id);
			model.addAttribute("param", smswhitelist);
			model.addAttribute(SUCCESS, true);
			return "/smswhitelist/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smswhitelist/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("smswhitelist") Smswhitelist smswhitelist,Model model) {		
		try {
			smswhitelistService.save(smswhitelist);
			model.addAttribute(SUCCESS, true);
			return "redirect:/smswhitelist";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smswhitelist);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smswhitelist/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("smswhitelist") Smswhitelist smswhitelist,Model model) throws Exception {		
		try {
			smswhitelistService.update(smswhitelist);
			model.addAttribute(SUCCESS, true);
			return "redirect:/smswhitelist";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smswhitelist);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smswhitelist/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			smswhitelistService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/smswhitelist";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/smswhitelist";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String vcPhone) throws Exception{		
		
		query.addQueryData("vcPhone", vcPhone);
		PageResult<Smswhitelist> pageList = smswhitelistService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("query", query.getQueryData());
		model.addAttribute("pageIndex", query.getPageIndex());
		return "/smswhitelist/list";
	}
	
}


