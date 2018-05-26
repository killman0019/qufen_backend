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
import com.tzg.entitys.focus.Focus;
import com.tzg.service.focus.FocusService;
import com.tzg.service.uploadfile.UploadfileService;

/**
 * 
 * @Description：焦点图
 * @author
 * @Date 2015-1-8
 * 
 */
@Controller
@RequestMapping("/focus")
public class FocusController extends BaseController {
	private static Logger log = Logger.getLogger(FocusController.class);

	@Autowired
	private FocusService focusService;

	@Autowired
	private UploadfileService uploadfileService;

	

	

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("focus") Focus focus, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			int itype = focus.getItype();
			if(itype!=1){
				focus.setIblankWindow(null);
			}else{
				if(focus.getIblankWindow()==null){
					focus.setIblankWindow(1);
				}
			}
			focusService.save(focus);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/focus";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("focus", focus);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/focus/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("focus") Focus focus, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			int itype = focus.getItype();
			if(itype!=1){
				focus.setIblankWindow(null);
			}else{
				if(focus.getIblankWindow()==null){
					focus.setIblankWindow(1);
				}
			}
			focusService.update(focus);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/focus";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("focus", focus);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/focus/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model) throws Exception {
		try {
			focusService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/focus";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/focus";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(
			PaginationQuery query,
			Model model,
			String vcName,
			String itype ,
			@RequestParam(value = "istate", required = false, defaultValue = "1") String istate)
			throws Exception {
		try {
			query.addQueryData("istate", istate);
			query.addQueryData("itype", itype);
			query.addQueryData("vcName", vcName);
			PageResult<Focus> pageList = focusService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/focus/list";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/focus/list";
		}
	}


	
}
