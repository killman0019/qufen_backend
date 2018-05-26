package com.tzg.action;

import org.apache.commons.lang.StringUtils;
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
import com.tzg.entitys.approvalrating.ApprovalRating;
import com.tzg.service.approvalrating.ApprovalRatingService;

/**
 * Created by cfour on 12/3/14.
 */
@Controller
@RequestMapping("/approvalratings")
public class ApprovalRatingController extends BaseController {

	private static Logger log = Logger
			.getLogger(ApprovalRatingController.class);

	@Autowired
	private ApprovalRatingService approvalRatingService;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(RedirectAttributes redirectAttributes) {
		try {
			return "/approvalratings/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/approvalratings";
		}
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			ApprovalRating approvalRating = approvalRatingService
					.findById(Integer.parseInt(id));
			model.addAttribute("approvalRating", approvalRating);
			return "/approvalratings/update";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/approvalratings";
		}
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(String id,
			@ModelAttribute ApprovalRating approvalRating, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			approvalRating.setId(Integer.parseInt(id));
			approvalRatingService.update(approvalRating);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/approvalratings";
		} catch (Exception ex) {
			model.addAttribute("approvalRating", approvalRating);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/approvalratings/update";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute ApprovalRating approvalRating,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			approvalRatingService.save(approvalRating);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/approvalratings";
		} catch (Exception ex) {
			model.addAttribute("approvalFlow", approvalRating);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/approvalratings/new";
		}
	}

	/**
	 * 分页列表查询展示
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
			String vcApprovalName, Model model) throws Exception {
		try {
			PaginationQuery query = new PaginationQuery();
			query.setPageIndex(pageIndex);
			if (StringUtils.isBlank(vcApprovalName)) {
				vcApprovalName = null;
			}
			query.addQueryData("vcApprovalName", vcApprovalName);
			PageResult<ApprovalRating> page = approvalRatingService
					.findPage(query);
			model.addAttribute("result", page);
			model.addAttribute("vcApprovalName", vcApprovalName);
			return "/approvalratings/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/approvalratings/list";
		}
	}

}
