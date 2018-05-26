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
import com.tzg.entitys.notice.Notice;
import com.tzg.service.notice.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	private static Logger log = Logger.getLogger(NoticeController.class);

	@Autowired
	private NoticeService noticeService;

	@RequestMapping(value = "/new")
	public String add(RedirectAttributes redirectAttributes) throws Exception {
		try {
			return "/notice/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/notice";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			Notice notice = noticeService.findById(id);
			model.addAttribute("param", notice);
			model.addAttribute(SUCCESS, true);
			return "/notice/update";
		} catch (Exception ex) {
			log.error(ex);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			return "/notice/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("notice") Notice notice, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			noticeService.save(notice);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/notice";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", notice);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/notice/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("notice") Notice notice, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			noticeService.update(notice);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/notice";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", notice);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/notice/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			noticeService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_DELETE_SUCESS);
			return "redirect:/notice";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/notice";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "vcTitle", required = false) String vcTitle,
			@RequestParam(value = "itype", required = false) String itype,
			@RequestParam(value = "istate", required = false, defaultValue = "1") String istate,
			PaginationQuery query, Model model) throws Exception {
		try {
			query.addQueryData("vcTitle", vcTitle);
			query.addQueryData("itype", itype);
			query.addQueryData("istate", istate);
			PageResult<Notice> pageList = noticeService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/notice/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/notice/list";
		}
	}

}
