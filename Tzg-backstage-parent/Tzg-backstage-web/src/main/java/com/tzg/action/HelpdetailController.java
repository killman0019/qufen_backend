package com.tzg.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.TzgConstant;
import com.tzg.entitys.helpcategy.Helpcategy;
import com.tzg.entitys.helpdetail.Helpdetail;
import com.tzg.service.helpcategy.HelpcategyService;
import com.tzg.service.helpdetail.HelpdetailService;

/**
 * 
 * @Description：帮助中心明细
 * @author
 * @Date 2015-1-7
 * 
 */
@Controller
@RequestMapping("/helpdetail")
public class HelpdetailController extends BaseController {
	private static Logger log = Logger.getLogger(HelpdetailController.class);

	@Autowired
	private HelpdetailService helpdetailService;

	@Autowired
	private HelpcategyService helpcategyService;

	@RequestMapping(value = "/new")
	public String add(Model model) throws Exception {
		List<Helpcategy> helpcategyList = helpcategyService
				.queryHelpcategyList(1);
		model.addAttribute("helpcategyList", helpcategyList);
		return "/helpdetail/new";
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model) {
		try {
			Helpdetail helpdetail = helpdetailService.findById(id);
			model.addAttribute("helpdetail", helpdetail);
			model.addAttribute(SUCCESS, true);
			List<Helpcategy> helpcategyList = helpcategyService
					.queryHelpcategyAll();
			model.addAttribute("helpcategyList", helpcategyList);
			return "/helpdetail/detail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpdetail/detail";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			Helpdetail helpdetail = helpdetailService.findById(id);
			model.addAttribute("helpdetail", helpdetail);
			model.addAttribute(SUCCESS, true);
			List<Helpcategy> helpcategyList = helpcategyService
					.queryHelpcategyAll();
			model.addAttribute("helpcategyList", helpcategyList);
			return "/helpdetail/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpdetail/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("helpdetail") Helpdetail helpdetail,
			Model model, RedirectAttributes redirectAttributes) throws Exception {
		try {
			helpdetailService.save(helpdetail);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/helpdetail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("helpdetail", helpdetail);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			List<Helpcategy> helpcategyList = helpcategyService
					.queryHelpcategyList(1);
			model.addAttribute("helpcategyList", helpcategyList);
			return "/helpdetail/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("helpdetail") Helpdetail helpdetail,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			helpdetailService.update(helpdetail);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/helpdetail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("helpdetail", helpdetail);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			List<Helpcategy> helpcategyList = helpcategyService
					.queryHelpcategyList(1);
			model.addAttribute("helpcategyList", helpcategyList);
			return "/helpdetail/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model,RedirectAttributes redirectAttributes) throws Exception {
		try {
			helpdetailService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_DELETE_SUCESS);
			return "redirect:/helpdetail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/helpdetail";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model, String istate,String vcQuestion,
			String ihelpCategyId) throws Exception {
		try {
			if (StringUtils.isBlank(istate)) {
				istate = "1";
			}
			query.addQueryData("istate", istate);
			query.addQueryData("ihelpCategyId", ihelpCategyId);
			query.addQueryData("vcQuestion", vcQuestion);			
			PageResult<Helpdetail> pageList = helpdetailService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			List<Helpcategy> helpcategyList = helpcategyService
					.queryHelpcategyAll();
			model.addAttribute("helpcategyList", helpcategyList);
			return "/helpdetail/list";
		} catch (Exception e) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpdetail/list";
		}
	}

}
