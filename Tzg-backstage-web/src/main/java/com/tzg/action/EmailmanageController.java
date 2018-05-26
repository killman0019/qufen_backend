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
import com.tzg.entitys.emailmanage.Emailmanage;
import com.tzg.service.emailmanage.EmailmanageService;

/**
 * 
 * @Description：邮件Controller
 * @author
 * @Date 2015-1-13
 * 
 */
@Controller
@RequestMapping("/emailmanage")
public class EmailmanageController extends BaseController {
	private static Logger log = Logger.getLogger(EmailmanageController.class);

	@Autowired
	private EmailmanageService emailmanageService;

	@RequestMapping(value = "/new")
	public String add(RedirectAttributes redirectAttributes) throws Exception {
		try {
			return "/emailmanage/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/emailmanage";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			Emailmanage emailmanage = emailmanageService.findById(id);
			model.addAttribute("emailmanage", emailmanage);
			model.addAttribute(SUCCESS, true);
			return "/emailmanage/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/emailmanage/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("emailmanage") Emailmanage emailmanage,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			emailmanageService.save(emailmanage);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/emailmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("emailmanage", emailmanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/emailmanage/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(
			@ModelAttribute("emailmanage") Emailmanage emailmanage,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			emailmanageService.update(emailmanage);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/emailmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("emailmanage", emailmanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/emailmanage/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model) throws Exception {
		try {
			emailmanageService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/emailmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/emailmanage";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model, String vcTitle,
			String istate, String itype) throws Exception {
		try {
			query.addQueryData("itype", itype);
			query.addQueryData("istate", istate);
			query.addQueryData("vcTitle", vcTitle);
			PageResult<Emailmanage> pageList = emailmanageService
					.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/emailmanage/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/emailmanage/list";
		}
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Emailmanage emailmanage = emailmanageService.findById(id);
			model.addAttribute("emailmanage", emailmanage);
			model.addAttribute(SUCCESS, true);
			return "/emailmanage/detail";
		} catch (Exception e) {
			log.error(e);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
			return "redirect:/emailmanage";
		}
	}

	@RequestMapping(value = "/publish")
	public String publish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Emailmanage emailmanage = emailmanageService.findById(id);
			model.addAttribute("emailmanage", emailmanage);
			model.addAttribute(SUCCESS, true);
			return "/emailmanage/publish";
		} catch (Exception e) {
			log.error(e);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
			return "redirect:/emailmanage";
		}
	}

	@RequestMapping(value = "/doPublish")
	public String doPublish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		Emailmanage emailmanage = null;
		try {
			emailmanage = emailmanageService.findById(id);
			emailmanageService.publish(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE, "发布成功！");
			return "redirect:/emailmanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("emailmanage", emailmanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/emailmanage/publish";
		}
	}

}
