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
import com.tzg.entitys.post.Post;
import com.tzg.service.post.PostService;
@Controller
@RequestMapping("/post")
public class PostController extends BaseController {
	private static Logger log = Logger.getLogger(PostController.class);
	
	@Autowired
	private PostService postService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/post/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Post post = postService.findById(id);
			model.addAttribute("param", post);
			model.addAttribute(SUCCESS, true);
			return "/post/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/post/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("post") Post post,Model model) {		
		try {
			postService.save(post);
			System.err.println(post);
			model.addAttribute(SUCCESS, true);
			return "redirect:/post";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", post);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/post/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("post") Post post,Model model) throws Exception {		
		try {
			postService.update(post);
			model.addAttribute(SUCCESS, true);
			return "redirect:/post";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", post);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/post/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			postService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/post";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/post";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model) throws Exception{		
		PageResult<Post> pageList = postService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/post/list";
	}
	
}


