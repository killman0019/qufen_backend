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
import com.tzg.entitys.messagemanage.Messagemanage;
import com.tzg.service.messagemanage.MessagemanageService;

@Controller
@RequestMapping("/messagemanage")
public class MessagemanageController extends BaseController {
	private static Logger log = Logger.getLogger(MessagemanageController.class);

	@Autowired
	private MessagemanageService messagemanageService;

	@RequestMapping(value = "/new")
	public String add(RedirectAttributes redirectAttributes) throws Exception {
		try {
			return "/messagemanage/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/messagemanage";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Messagemanage messagemanage = messagemanageService.findById(id);
			model.addAttribute("param", messagemanage);
			model.addAttribute(SUCCESS, true);
			return "/messagemanage/update";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "redirect:/messagemanage";
		}
	}

	@RequestMapping(value = "/save")
	public String save(
			@ModelAttribute("messagemanage") Messagemanage messagemanage,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			messagemanageService.save(messagemanage);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/messagemanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", messagemanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/messagemanage/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(
			@ModelAttribute("messagemanage") Messagemanage messagemanage,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			messagemanageService.update(messagemanage);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/messagemanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", messagemanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/messagemanage/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			messagemanageService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_DELETE_SUCESS);
			return "redirect:/messagemanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/messagemanage";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, String vcTitle, String istate,String imode,
			String itype, Model model) throws Exception {
		try {
			query.addQueryData("itype", itype);
			query.addQueryData("istate", istate);
			query.addQueryData("imode", imode);
			query.addQueryData("vcTitle", vcTitle);
			PageResult<Messagemanage> pageList = messagemanageService
					.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/messagemanage/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/messagemanage/list";
		}
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Messagemanage messagemanage = messagemanageService.findById(id);
			model.addAttribute("param", messagemanage);
			model.addAttribute(SUCCESS, true);
			return "/messagemanage/detail";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "redirect:/messagemanage";
		}
	}

	@RequestMapping(value = "/publish")
	public String publish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Messagemanage messagemanage = messagemanageService.findById(id);
			model.addAttribute("param", messagemanage);
			model.addAttribute(SUCCESS, true);
			return "/messagemanage/publish";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "redirect:/messagemanage";
		}
	}

	@RequestMapping(value = "/doPublish")
	public String doPublish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		Messagemanage messagemanage = null;
		try {
			messagemanage = messagemanageService.findById(id);
			messagemanageService.publish(id);
			//执行发布
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE, "发布成功！");
			return "redirect:/messagemanage";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", messagemanage);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/messagemanage/publish";
		}
	}

}
