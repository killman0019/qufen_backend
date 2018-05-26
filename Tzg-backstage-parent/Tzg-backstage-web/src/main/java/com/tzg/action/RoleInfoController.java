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
import com.tzg.entitys.approvalrating.ApprovalRating;
import com.tzg.entitys.leopard.resource.Resource;
import com.tzg.entitys.roleinfo.RoleInfo;
import com.tzg.service.approvalrating.ApprovalRatingService;
import com.tzg.service.resource.ResourceService;
import com.tzg.service.roleinfo.RoleInfoService;

/**
 * @Description:系统权限控制的角色表action
 * @author wxg
 * @Date: 2014-12-02
 * 
 */
@Controller
@RequestMapping("/roleinfos")
public class RoleInfoController extends BaseController {

	private static Logger log = Logger.getLogger(RoleInfoController.class);

	@Autowired
	private RoleInfoService roleInfoService;

	@Autowired
	private ResourceService resourceService;

	@Autowired
	private ApprovalRatingService approvalRatingService;

	/**
	 * 跳转到记录新增页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			List<Resource> resources = resourceService.getAllResource(null);
			List<ApprovalRating> approvalRatings = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("resources", resources);
			model.addAttribute("approvalRatings", approvalRatings);
			return "/roleinfos/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/roleinfos";
		}

	}

	/**
	 * 记录新增
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute RoleInfo roleInfo, Model model,RedirectAttributes redirectAttributes)
			throws Exception {
		try {
			int roleid = roleInfoService.save(roleInfo);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/roleinfos";
		} catch (Exception ex) {
			List<Resource> resources = resourceService.getAllResource(null);
			model.addAttribute("resources", resources);
			List<ApprovalRating> approvalRatings = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("approvalRatings", approvalRatings);
			model.addAttribute("roleInfo", roleInfo);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/roleinfos/new";
		}
	}

	/**
	 * 跳转到编辑界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(int id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			RoleInfo roleInfo = roleInfoService.findById(id);
			List<Resource> resources = resourceService.getAllResource(roleInfo
					.getId());

			List<ApprovalRating> approvalRatings = approvalRatingService
					.queryApprovalratingAll();
			model.addAttribute("resources", resources);
			model.addAttribute("roleInfo", roleInfo);
			model.addAttribute("approvalRatings", approvalRatings);
			return "/roleinfos/update";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/roleinfos";
		}
	}

	/**
	 * 记录更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(int id, @ModelAttribute RoleInfo roleInfo,
			Model model, RedirectAttributes redirectAttributes)
			throws Exception {
		roleInfo.setId(id);
		try {
			roleInfoService.update(roleInfo);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/roleinfos";
		} catch (Exception ex) {
			List<Resource> resources = resourceService.getAllResource(id);
			model.addAttribute("resources", resources);
			model.addAttribute("roleInfo", roleInfo);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/roleinfos/update";
		}
	}

	/**
	 * 记录删除
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			roleInfoService.delete(id);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_DELETE_SUCESS);
			return "redirect:/roleinfos";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/roleinfos";
		}
	}

	/**
	 * 分页列表展示
	 * 
	 * @param pageIndex
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = "vcRoleName", required = false, defaultValue = "") String vcRoleName,
			Model model) {
		try {
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("vcRoleName", vcRoleName);
			query.setPageIndex(pageIndex);
			PageResult<RoleInfo> roleInfoPage = roleInfoService
					.queryRoleInfoListByPage(query);
			model.addAttribute("result", roleInfoPage);
			model.addAttribute("vcRoleName", vcRoleName);
			return "/roleinfos/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/roleinfos/list";
		}
	}

}
