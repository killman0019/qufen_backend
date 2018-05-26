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
import com.tzg.entitys.article.Article;
import com.tzg.service.article.ArticleService;

@Controller
@RequestMapping("/post/article")
public class ArticleController extends BaseController {
	private static Logger log = Logger.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/article/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Article article = articleService.findById(id);
			model.addAttribute("param", article);
			model.addAttribute(SUCCESS, true);
			return "/article/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/article/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("article") Article article,Model model) {		
		try {
			articleService.save(article);
			model.addAttribute(SUCCESS, true);
			return "redirect:/article";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", article);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/article/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("article") Article article,Model model) throws Exception {		
		try {
			articleService.update(article);
			model.addAttribute(SUCCESS, true);
			return "redirect:/article";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", article);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/article/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			articleService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/article";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/article";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String projectCode,String createUserName,String postTitle,Integer status) throws Exception{		
		
		if(StringUtils.isNotBlank(projectCode)){
			query.addQueryData("projectCode", projectCode);
		}
		if(StringUtils.isNotBlank(createUserName)){
			query.addQueryData("createUserName", createUserName);		
		}
		if(StringUtils.isNotBlank(postTitle)){
			query.addQueryData("postTitle", postTitle);
		}
		if(status != null){
			query.addQueryData("status", status+"");
		}
		query.addQueryData("postType", "3");
		PageResult<Article> pageList = articleService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/article/list";
	}
	
}


