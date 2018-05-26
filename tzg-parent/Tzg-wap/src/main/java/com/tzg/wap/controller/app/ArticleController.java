package com.tzg.wap.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.helpcategy.Helpcategy;
import com.tzg.entitys.helpdetail.Helpdetail;
import com.tzg.entitys.news.News;
import com.tzg.rmi.service.CmsRmiService;
import com.tzg.wap.controller.BaseController;

@Controller
@RequestMapping("/app/article")
public class ArticleController extends BaseController {
	@Autowired
	private CmsRmiService cmsRmiService;
    
	
	
	/**
	 * 关于我们
	 * @return
	 */
	@RequestMapping("/aboutUs")
	public String aboutUs(Model model){
		model.addAttribute("currentNav", "aboutUs");
		return "/app/article/about-aboutUs";
	}
	
	/**
	 * 安全保障
	 * @return
	 */
	@RequestMapping("/safety")
	public String safety(Model model){
		model.addAttribute("currentNav", "safety");
		return "/app/article/safety";
	}
	
	
	/**
	 * 联系我们
	 * @return
	 */
	@RequestMapping("/contactUs")
	public String contactUs(Model model){
		model.addAttribute("currentNav", "contactUs");
		return "/app/article/about-contactUs";
	}
	
	
	/**
	 * 帮助中心
	 * @return
	 */
	@RequestMapping("/helpCenter")
	public String helpCenter(Integer id,Model model){
		try {
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(1);
			query.setRowsPerPage(100);
			query.addQueryData("istate", "1");
			PageResult<Helpcategy> result = cmsRmiService.findHelpcategyPage(query);
			model.addAttribute("result", result);
			if(id != null){
				query.addQueryData("ihelpCategyId", id+"");
			}else{
				id = 1;
				query.addQueryData("ihelpCategyId", "1");
			}
			PageResult<Helpdetail> helpdetailResult = cmsRmiService.findHelpdetailPage(query);
			model.addAttribute("helpdetailResult", helpdetailResult);
			
			model.addAttribute("id", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/app/article/help-center";
	}
	
	@RequestMapping("/helpDetail")
	public String helpDetail(Integer id,Model model){
		try {
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(1);
			query.setRowsPerPage(100);
			query.addQueryData("istate", "1");
			PageResult<Helpcategy> result = cmsRmiService.findHelpcategyPage(query);
			model.addAttribute("result", result);
			if(id != null){
				query.addQueryData("ihelpCategyId", id+"");
			}else{
				id = 1;
				query.addQueryData("ihelpCategyId", "1");
			}
			PageResult<Helpdetail> helpdetailResult = cmsRmiService.findHelpdetailPage(query);
			model.addAttribute("helpdetailResult", helpdetailResult);
			
			model.addAttribute("id", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/app/article/help-detail";
	}
	
	@RequestMapping("/teamContext")
    public String teamContext() {
        return "/app/article/about-teamContext";
    }
	
	
	/**
	 * 新闻 列表
	 * @param query
	 * @param model
	 * @return
	 */
	@RequestMapping("/newsList")
	public String news(PaginationQuery query,Model model){
		try {
			query.addQueryData("istate", "2");
			query.addQueryData("itype", "1");
			PageResult<News> result = cmsRmiService.findNewsPage(query);
			model.addAttribute("result", result);
			model.addAttribute("currentNav", "news");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/app/article/newsList";
	}
	
	/**
	 * 新闻详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/newsDetail")
	public String newsDetail(Integer id,Model model){
		try {
			News news = cmsRmiService.findNewsById(id);
			model.addAttribute("news", news);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("currentNav", "news");
		return "/app/article/newsDetail";
	}
	
	
}
