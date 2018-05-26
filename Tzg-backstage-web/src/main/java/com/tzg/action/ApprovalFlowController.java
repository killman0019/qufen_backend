package com.tzg.action;

import java.util.List;

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
import com.tzg.entitys.approvalflow.ApprovalFlow;
import com.tzg.entitys.approvalrating.ApprovalRating;
import com.tzg.service.approvalflow.ApprovalFlowService;
import com.tzg.service.approvalrating.ApprovalRatingService;
/**
 * Created by cfour on 12/3/14.
 */
@Controller
@RequestMapping("/approvalflows")
public class ApprovalFlowController extends BaseController {

	private static Logger log = Logger.getLogger(ApprovalFlowController.class);

	@Autowired
	private ApprovalFlowService approvalFlowService;

	@Autowired
	private ApprovalRatingService approvalRatingService;

	/**
	 * 记录页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes redirectAttributes) {
		try {
			List<ApprovalRating> ratList = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("ratList", ratList);
			return "/approvalflows/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/approvalflows";
		}
	}

	/**
	 * 记录新增
	 * 
	 * @param approvalFlow
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(String vcApprovalType, String vcApprovalFlow,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		ApprovalFlow approvalFlow = new ApprovalFlow();
		approvalFlow.setVcApprovalType(Integer.parseInt(vcApprovalType));
		approvalFlow.setVcApprovalFlow(vcApprovalFlow);
		try {
			approvalFlow.setVcApprovalType(Integer.parseInt(vcApprovalType));
			approvalFlow.setVcApprovalFlow(vcApprovalFlow);
			approvalFlowService.save(approvalFlow);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/approvalflows";
		} catch (Exception ex) {
			List<ApprovalRating> ratList = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("ratList", ratList);
			model.addAttribute("approvalFlow", approvalFlow);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/approvalflows/new";
		}
	}

	/**
	 * 记录编辑页面跳转
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			ApprovalFlow approvalFlow = approvalFlowService.findById(Integer
					.parseInt(id));
			model.addAttribute("approvalFlow", approvalFlow);
			List<ApprovalRating> ratList = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("ratList", ratList);
			return "/approvalflows/update";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/approvalflows";
		}
	}

	/**
	 * 记录更新
	 * 
	 * @param id
	 * @param approvalFlow
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String id, @ModelAttribute ApprovalFlow approvalFlow,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			approvalFlow.setId(Integer.parseInt(id));
			approvalFlowService.update(approvalFlow);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/approvalflows";
		} catch (Exception ex) {
			List<ApprovalRating> ratList = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("ratList", ratList);
			model.addAttribute("approvalFlow", approvalFlow);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/approvalflows/update";
		}

	}

	/**
	 * 分页列表展示
	 * 
	 * @param pageIndex
	 * @param vcName
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
			Model model) throws Exception {
		try {
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			PageResult<ApprovalFlow> page = approvalFlowService.findPage(query);
			model.addAttribute("result", page);
			return "/approvalflows/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/approvalflows/list";
		}
	}

}
