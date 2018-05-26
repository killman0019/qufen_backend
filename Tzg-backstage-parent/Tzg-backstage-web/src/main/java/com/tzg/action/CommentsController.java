package com.tzg.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.comments.Comments;
import com.tzg.service.comments.CommentsService;

@Controller
@RequestMapping("/post/comments")
public class CommentsController extends BaseController {
	private static Logger log = Logger.getLogger(CommentsController.class);
	
	@Autowired
	private CommentsService commentsService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/comments/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Comments comments = commentsService.findById(id);
			model.addAttribute("param", comments);
			model.addAttribute(SUCCESS, true);
			return "/comments/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/comments/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("comments") Comments comments,Model model) {		
		try {
			commentsService.save(comments);
			model.addAttribute(SUCCESS, true);
			return "redirect:/comments";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", comments);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/comments/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("comments") Comments comments,Model model) throws Exception {		
		try {
			commentsService.update(comments);
			model.addAttribute(SUCCESS, true);
			return "redirect:/comments";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", comments);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/comments/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			Comments comments = new Comments();
			comments.setCommentsId(id);
			comments.setStatus(0);
			comments.setUpdateTime(new Date());
			commentsService.update(comments);
			model.addAttribute(SUCCESS, true);
			return "redirect:/comments";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/comments";
		}
	}
	@RequestMapping(value="/active")
	public String active(Integer id,Model model) throws Exception {
		try {
			Comments comments = new Comments();
			comments.setCommentsId(id);
			comments.setStatus(1);
			comments.setUpdateTime(new Date());
			commentsService.update(comments);
			model.addAttribute(SUCCESS, true);
			return "redirect:/comments";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/comments";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String projectCode,String createUserName,String commentContent,Integer status) throws Exception{		
		if(StringUtils.isNotBlank(projectCode)){
			query.addQueryData("projectCode", projectCode);
		}
		if(StringUtils.isNotBlank(createUserName)){
			query.addQueryData("createUserName", createUserName);		
		}
		if(StringUtils.isNotBlank(commentContent)){
			query.addQueryData("commentContent", commentContent);
		}
		if(status != null){
			query.addQueryData("status", status+"");
		}		
		PageResult<Comments> pageList = commentsService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/comments/list";
	}
	
}


