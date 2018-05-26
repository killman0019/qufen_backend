package com.tzg.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.plexus.util.StringUtils;
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
import com.tzg.common.utils.SHAUtil;
import com.tzg.common.utils.TzgConstant;
import com.tzg.core.utils.HttpSessionUtil;
import com.tzg.entitys.leopard.console.ConsoleLoginAccount;
import com.tzg.entitys.roleinfo.RoleInfo;
import com.tzg.service.consoleLoginAccount.ConsoleLoginAccountService;
import com.tzg.service.roleinfo.RoleInfoService;

/**
 * 账号管理 以及登陆用户的密码修改
 */
@Controller
@RequestMapping("/consoleaccounts")
public class ConsoleAccountController extends BaseController {

	private static Logger log = Logger
			.getLogger(ConsoleAccountController.class);

	@Autowired
	private ConsoleLoginAccountService consoleLoginAccountService;

	@Autowired
	private RoleInfoService roleInfoService;

	/**
	 * 跳转到新增页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String add(Model model, RedirectAttributes redirectAttributes) {
		try {
			List<RoleInfo> roleInfoList = roleInfoService.findAllValidRoleInfo();
			model.addAttribute("roleInfoList", roleInfoList);
			return "/consoleaccounts/new";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_EXCEPTION);
			log.error(ex);
			return "redirect:/consoleaccounts";
		}
	}

	/**
	 * 指定ID记录，跳转到明细页面
	 * 
	 * @param id
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(String id, Model model,
			RedirectAttributes redirectAttributes) {
		try {
			ConsoleLoginAccount account = consoleLoginAccountService
					.findById(Integer.parseInt(id));
			List<RoleInfo> roleInfoList = roleInfoService.findAllValidRoleInfo();
			model.addAttribute("roleInfoList", roleInfoList);
			model.addAttribute("account", account);
			return "/consoleaccounts/update";
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute(SUCCESS, false);
			redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "redirect:/consoleaccounts";
		}
	}

	/**
	 * 执行更新操作
	 * 
	 * @param account
	 * @param oldLoginName
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute ConsoleLoginAccount account,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			consoleLoginAccountService.update(account);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_UPDATE_SUCESS);
			return "redirect:/consoleaccounts";
		} catch (Exception ex) {
			List<RoleInfo> roleInfoList = roleInfoService.findAllValidRoleInfo();
			model.addAttribute("roleInfoList", roleInfoList);
			model.addAttribute("account", account);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/consoleaccounts/update";
		}
	}

	/**
	 * 执行保存操作
	 * 
	 * @param account
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute ConsoleLoginAccount account,
			Model model, RedirectAttributes redirectAttributes) {
		try {
			consoleLoginAccountService.save(account);
			redirectAttributes.addFlashAttribute(SUCCESS, true);
			redirectAttributes.addFlashAttribute(MESSAGE,
					TzgConstant.MESSAGE_RECORD_SAVE_SUCESS);
			return "redirect:/consoleaccounts";
		} catch (Exception ex) {
			List<RoleInfo> roleInfoList = roleInfoService.findAllValidRoleInfo();
			model.addAttribute("roleInfoList", roleInfoList);
			model.addAttribute("account", account);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/consoleaccounts/new";
		}
	}

	/**
	 * 跳到更改密码页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changepassword(Model model) {
		ConsoleLoginAccount account = HttpSessionUtil.getLoginSession();
		model.addAttribute("account", account);
		return "/consoleaccounts/changepassword";
	}

	/**
	 * 执行更改密码操作
	 * @param account
	 * @param oldPassword
	 * @param newPassword
	 * @param confirmNewPassword
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	public String updatepassword(@ModelAttribute ConsoleLoginAccount account,
			String oldPassword, String newPassword, String confirmNewPassword,
			Model model) {
		try {
			if (StringUtils.isBlank(oldPassword)) {
				throw new Exception("当前密码不能为空");
			}
			if (StringUtils.isBlank(newPassword)) {
				throw new Exception("新密码不能为空");
			}
			if (StringUtils.isBlank(confirmNewPassword)) {
				throw new Exception("确认密码不能为空");
			}
			if (!newPassword.equals(confirmNewPassword)) {
				throw new Exception("两次输入密码不一致");
			}
			String pwd = SHAUtil.encode(oldPassword);
			if (!pwd.equals(HttpSessionUtil.getLoginSession()
					.getVcLoginPassword())) {
				throw new Exception("当前密码错误");
			}
			account.setVcLoginPassword(SHAUtil.encode(newPassword));
			account.setDtModify(new Date());
			consoleLoginAccountService.updatepassword(account);
			return "/consoleaccounts/sucess";
		} catch (Exception e) {
			model.addAttribute("newPassword", newPassword);
			model.addAttribute("confirmNewPassword", confirmNewPassword);
			model.addAttribute("account", account);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/consoleaccounts/changepassword";
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(int id, Model model) {
		consoleLoginAccountService.delete(id);
		return "redirect:/consoleaccounts";
	}

	/**
	 * 首页查询
	 * 
	 * @param pageIndex
	 * @param vcLoginName
	 * @param iValid
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = "vcLoginName", required = false, defaultValue = "") String vcLoginName,
			@RequestParam(value = "iValid", required = false, defaultValue = "1") String iValid,
			Model model) {
		try {
			PaginationQuery query = new PaginationQuery();
			query.addQueryData("vcLoginName", vcLoginName);
			query.addQueryData("iValid", iValid);
			query.setPageIndex(pageIndex);
			PageResult<ConsoleLoginAccount> accounts = consoleLoginAccountService
					.getAccountList(query);
			model.addAttribute("vcLoginName", vcLoginName);
			model.addAttribute("result", accounts);
			model.addAttribute("iValid", iValid);
			return "/consoleaccounts/list";
		} catch (Exception ex) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, ex.getMessage());
			log.error(ex);
			return "/consoleaccounts/list";
		}
	}
	
	/**
	 * 重置密码
	 * @throws Exception 
	 * @throws NumberFormatException 
	 * 
	 * */
	@RequestMapping(value="/resetPwd")
	public String resetPwd(String id, Model model) throws NumberFormatException, Exception{
		int flag = consoleLoginAccountService.resetPwd(id);
		return "redirect:/consoleaccounts";
	}
}
