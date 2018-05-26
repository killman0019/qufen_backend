package com.tzg.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.smstemplate.Smstemplate;
import com.tzg.service.smstemplate.SmstemplateService;

/**
 * 
 * @Description：短信模板Controller
 * @author
 * @Date 2015-1-23
 * 
 */
@Controller
@RequestMapping("/smstemplate")
public class SmstemplateController extends BaseController {
	private static Logger log = Logger.getLogger(SmstemplateController.class);

	@Autowired
	private SmstemplateService smstemplateService;

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			Smstemplate smstemplate = smstemplateService.findById(id);
			model.addAttribute("smstemplate", smstemplate);
			model.addAttribute(SUCCESS, true);
			return "/smstemplate/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smstemplate/update";
		}
	}

	@RequestMapping(value = "/update")
	public String update(
			@ModelAttribute("smstemplate") Smstemplate smstemplate, Model model,
			RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			smstemplateService.update(smstemplate);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/smstemplate";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("smstemplate", smstemplate);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smstemplate/update";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model,
			@RequestParam(value = "istate", required = false, defaultValue = "1") String istate,
			String itype) throws Exception {
		try {
			query.addQueryData("istate", istate);
			query.addQueryData("itype", itype);
			PageResult<Smstemplate> pageList = smstemplateService
					.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/smstemplate/list";
		} catch (Exception e) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smstemplate/list";
		}
	}
	
	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model) {
		try {
			Smstemplate smstemplate = smstemplateService.findById(id);
			model.addAttribute("smstemplate", smstemplate);
			model.addAttribute(SUCCESS, true);
			return "/smstemplate/detail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smstemplate/detail";
		}
	}

}
