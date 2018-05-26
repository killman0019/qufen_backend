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
import com.tzg.entitys.mediareports.Mediareports;
import com.tzg.service.mediareports.MediareportsService;
import com.tzg.service.uploadfile.UploadfileService;

/**
 * 
 * @Description：媒体报道Controller
 * @author
 * @Date 2015-1-16
 * 
 */
@Controller
@RequestMapping("/mediareports")
public class MediareportsController extends BaseController {
	private static Logger log = Logger.getLogger(MediareportsController.class);

	@Autowired
	private MediareportsService mediareportsService;

	@Autowired
	private UploadfileService uploadfileService;

	@RequestMapping(value = "/new")
	public String add() throws Exception {
		return "/mediareports/new";
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			Mediareports mediareports = mediareportsService.findById(id);
			model.addAttribute("mediareports", mediareports);
			model.addAttribute(SUCCESS, true);
			return "/mediareports/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/mediareports/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(
			@ModelAttribute("mediareports") Mediareports mediareports,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			mediareportsService.save(mediareports);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/mediareports";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("mediareports", mediareports);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/mediareports/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(
			@ModelAttribute("mediareports") Mediareports mediareports,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			mediareportsService.update(mediareports);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/mediareports";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("mediareports", mediareports);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/mediareports/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model) throws Exception {
		try {
			mediareportsService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/mediareports";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/mediareports";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			PaginationQuery query,
			Model model,
			@RequestParam(value = "istate", required = false, defaultValue = "1") String istate)
			throws Exception {
		try {
			query.addQueryData("istate", istate);
			PageResult<Mediareports> pageList = mediareportsService
					.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/mediareports/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/mediareports/list";
		}
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model) {
		try {
			Mediareports mediareports = mediareportsService.findById(id);
			model.addAttribute("mediareports", mediareports);
			model.addAttribute(SUCCESS, true);
			return "/mediareports/detail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/mediareports/detail";
		}
	}

}
