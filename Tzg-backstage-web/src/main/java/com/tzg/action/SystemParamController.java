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
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.service.SystemParam.SystemParamService;

/**
 * Created by cfour on 12/3/14.
 */
@Controller
@RequestMapping("/systemparams")
public class SystemParamController extends BaseController {

	private static Logger log = Logger.getLogger(SystemParamController.class);

	@Autowired
	private SystemParamService systemParamService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(RedirectAttributes redirectAttributes) {
		try {
			return "/systemparams/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/systemparams";
		}
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			SystemParam systemParam = systemParamService.findById(id);
			model.addAttribute("systemParam", systemParam);
			return "/systemparams/update";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/systemparams";
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Integer id, @ModelAttribute SystemParam systemParam,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			systemParam.setId(id);
			systemParamService.update(systemParam);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/systemparams";
		} catch (Exception ex) {
			model.addAttribute("systemParam", systemParam);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/systemparams/update";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model,RedirectAttributes redirectAttributes) {
		try {
			systemParamService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_DELETE_SUCESS);
			return "redirect:/systemparams";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/systemparams";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute SystemParam systemParam, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			systemParamService.save(systemParam);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/systemparams";
		} catch (Exception ex) {
			model.addAttribute("systemParam", systemParam);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/systemparams/new";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
			@RequestParam(required = false, defaultValue = "") String vcParamName,
			@RequestParam(required = false, defaultValue = "") String vcParamCode,
			Model model) {
		try {
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("vcParamName", vcParamName);
			query.addQueryData("vcParamCode", vcParamCode);
			query.setPageIndex(pageIndex);
			PageResult<SystemParam> params = systemParamService
					.getParamsList(query);
			model.addAttribute("result", params);
			model.addAttribute("vcParamName", vcParamName);
			model.addAttribute("vcParamCode", vcParamCode);
			return "/systemparams/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/systemparams/list";
		}
	}
}
