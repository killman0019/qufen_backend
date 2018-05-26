package com.tzg.action;

import java.util.List;

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
import com.tzg.entitys.news.News;
import com.tzg.service.news.NewsService;
import com.tzg.service.uploadfile.UploadfileService;

/**
 * 
 * @Description：新闻动态
 * @author
 * @Date 2015-1-14
 * 
 */
@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {
	private static Logger log = Logger.getLogger(NewsController.class);

	@Autowired
	private NewsService newsService;

	@Autowired
	private UploadfileService uploadfileService;

	@RequestMapping(value = "/new")
	public String add(RedirectAttributes redirectAttributes, Model model)
			throws Exception {
		try {
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("ihomedisplay", "2");
			query.setRowsPerPage(6);
			List<News> newsList = newsService.findPage(query).getRows();
			model.addAttribute("newsList", newsList);
			return "/news/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/news";
		}
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			News news = newsService.findById(id);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, true);
			
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("itype", news.getItype()+"");
			query.addQueryData("ihomedisplay", "2");
			query.setRowsPerPage(6);
			List<News> newsList = newsService.findPage(query).getRows();
			model.addAttribute("newsList", newsList);
			return "/news/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/news/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("news") News news, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			newsService.save(news);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/news";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("ihomedisplay", "2");
			query.setRowsPerPage(6);
			List<News> newsList = newsService.findPage(query).getRows();
			model.addAttribute("newsList", newsList);
			return "/news/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("news") News news, Model model,
			RedirectAttributes redirectAttributes) throws Exception {
		try {
			newsService.update(news);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/news";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("ihomedisplay", "2");
			query.setRowsPerPage(6);
			List<News> newsList = newsService.findPage(query).getRows();
			model.addAttribute("newsList", newsList);
			return "/news/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model) throws Exception {
		try {
			newsService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/news";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/news";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model, String vcTitle,
			String istate, String itype, String ihomedisplay) throws Exception {
		try {
			query.addQueryData("itype", itype);
			query.addQueryData("istate", istate);
			query.addQueryData("vcTitle", vcTitle);
			query.addQueryData("ihomedisplay", ihomedisplay);
			PageResult<News> pageList = newsService.findPage(query);
			model.addAttribute("result", pageList);
			model.addAttribute("query", query.getQueryData());
			return "/news/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/news/list";
		}
	}

	@RequestMapping(value = "/detail")
	public String detail(Integer id, Model model) {
		try {
			News news = newsService.findById(id);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, true);
			return "/news/detail";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/news/detail";
		}
	}

	@RequestMapping(value = "/publish")
	public String publish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			News news = newsService.findById(id);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, true);
			return "/news/publish";
		} catch (Exception e) {
			log.error(e);
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, e.getMessage());
			return "redirect:/news";
		}
	}

	@RequestMapping(value = "/undopublish")
	public String undoPublish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		News news = null;
		try {
			news = newsService.findById(id);
			newsService.undopublish(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE, "已撤销发布！");
			return "redirect:/news";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/news";
		}
	}
	
	@RequestMapping(value = "/doPublish")
	public String doPublish(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		News news = null;
		try {
			news = newsService.findById(id);
			newsService.publish(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE, "发布成功！");
			return "redirect:/news";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("news", news);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/news/publish";
		}
	}

}
