package com.tzg.action;

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
import com.tzg.entitys.smsmanage.Smsmanage;
import com.tzg.service.smsmanage.SmsmanageService;

@Controller
@RequestMapping("/smsmanage")
public class SmsmanageController extends BaseController {
	private static Logger log = Logger.getLogger(SmsmanageController.class);

	@Autowired
	private SmsmanageService smsmanageService;

	@RequestMapping(value = "/new")
	public String add(RedirectAttributes redirectAttributes) throws Exception {
		try {
			return "/smsmanage/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/smsmanage";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Smsmanage smsmanage = smsmanageService.findById(id);
			model.addAttribute("param", smsmanage);
			model.addAttribute(SUCCESS, true);
			return "/smsmanage/update";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "redirect:/smsmanage";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("smsmanage") Smsmanage smsmanage,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			smsmanageService.save(smsmanage);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/smsmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smsmanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smsmanage/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("smsmanage") Smsmanage smsmanage,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			smsmanageService.update(smsmanage);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/smsmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smsmanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smsmanage/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			smsmanageService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_DELETE_SUCESS);
			return "redirect:/smsmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/smsmanage";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, String vcTitle, String istate,
			String itype, Model model) throws Exception {
		try {
			query.addQueryData("itype", itype);
			query.addQueryData("istate", istate);
			query.addQueryData("vcTitle", vcTitle);
			PageResult<Smsmanage> pageList = smsmanageService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/smsmanage/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/smsmanage/list";
		}
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Smsmanage smsmanage = smsmanageService.findById(id);
			model.addAttribute("param", smsmanage);
			model.addAttribute(SUCCESS, true);
			return "/smsmanage/detail";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "redirect:/smsmanage";
		}
	}

	@RequestMapping(value = "/publish")
	public String publish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Smsmanage smsmanage = smsmanageService.findById(id);
			model.addAttribute("param", smsmanage);
			model.addAttribute(SUCCESS, true);
			return "/smsmanage/publish";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "redirect:/smsmanage";
		}
	}

	@RequestMapping(value = "/doPublish")
	public String doPublish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		Smsmanage smsmanage = null;
		try {
			smsmanage = smsmanageService.findById(id);
			smsmanageService.publish(id);
			//执行发布
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE, "发布成功！");
			return "redirect:/smsmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smsmanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smsmanage/publish";
		}
	}
	
	
}
