package com.tzg.action;

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
import com.tzg.service.helpcategy.HelpcategyService;
/**
 * 
 * @Description：帮助中心类别
 * @author 
 * @Date 2015-1-7
 *
 */
@Controller
@RequestMapping("/helpcategy")
public class HelpcategyController extends BaseController {
	private static Logger log = Logger.getLogger(HelpcategyController.class);
	
	@Autowired
	private HelpcategyService helpcategyService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/helpcategy/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Helpcategy helpcategy = helpcategyService.findById(id);
			model.addAttribute("helpcategy", helpcategy);
			model.addAttribute(SUCCESS, true);
			return "/helpcategy/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpcategy/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("helpcategy") Helpcategy helpcategy,
			Model model,RedirectAttributes redirectAttributes) {		
		try {
			helpcategyService.save(helpcategy);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/helpcategy";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("helpcategy", helpcategy);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpcategy/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("helpcategy") Helpcategy helpcategy,
			Model model,RedirectAttributes redirectAttributes) throws Exception {		
		try {
			helpcategyService.update(helpcategy);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/helpcategy";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("helpcategy", helpcategy);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpcategy/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			helpcategyService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/helpcategy";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/helpcategy";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String istate,String vcName) throws Exception{	
		try {
			if (StringUtils.isBlank(istate)) {
				istate = "1";
			}
			query.addQueryData("istate", istate);
			query.addQueryData("vcName", vcName);
			PageResult<Helpcategy> pageList = helpcategyService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			model.addAttribute("istate", istate);
			model.addAttribute("vcName", vcName);
			return "/helpcategy/list";
		} catch (Exception e) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/helpcategy/list";
		}
	}
	
}


