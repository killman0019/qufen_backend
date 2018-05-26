package com.tzg.action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.message.Message;
import com.tzg.service.message.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {
	private static Logger log = Logger.getLogger(MessageController.class);
	
	@Autowired
	private MessageService messageService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/message/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Message message = messageService.findById(id);
			model.addAttribute("param", message);
			model.addAttribute(SUCCESS, true);
			return "/message/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/message/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("message") Message message,Model model) {		
		try {
			messageService.save(message);
			model.addAttribute(SUCCESS, true);
			return "redirect:/message";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", message);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/message/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("message") Message message,Model model) throws Exception {		
		try {
			messageService.update(message);
			model.addAttribute(SUCCESS, true);
			return "redirect:/message";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", message);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/message/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			messageService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/message";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/message";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model) throws Exception{		
		PageResult<Message> pageList = messageService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/message/list";
	}
	
}


