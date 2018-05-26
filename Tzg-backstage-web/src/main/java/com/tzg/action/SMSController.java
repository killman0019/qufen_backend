package com.tzg.action;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.smsrecord.Smsrecord;
import com.tzg.service.SystemParam.SystemParamService;
import com.tzg.service.smsrecord.SmsrecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by cfour on 1/7/15.
 */
@Controller
@RequestMapping("/SMSSearch")
public class SMSController extends BaseController {
	@Autowired
	private SmsrecordService smsrecordService;

	private static final String SYS_ENVIRONMENT = "sys_environment";

	@Autowired
	private SystemParamService systemParamService;

	@RequestMapping(method = RequestMethod.GET)
	public String search(
			@RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
			@RequestParam(value = "vcPhone", required = false) String phone,
			@RequestParam(value = "itype", required = false) Integer type,
			Model model) {
		// 如果不是测试环境，退出不查询
		try {
			SystemParam systemParam = systemParamService
					.findByCode(SYS_ENVIRONMENT);
			if (systemParam == null
					|| !systemParam.getVcParamValue().trim()
							.equalsIgnoreCase("test"))
				throw new Exception("无权限操作！");

			if (phone == null || "".equals(phone)) {
				return "/sms/SMSSearch";
			} else {
				PaginationQuery query = new PaginationQuery();
				query.addQueryData("vcPhone", phone);
				if (type != null && !"".equals(type)) {
					query.addQueryData("itype", Integer.toString(type));
				}
				query.setPageIndex(pageIndex);
				PageResult<Smsrecord> result = smsrecordService.findPage(query);
				model.addAttribute("vcPhone", phone);
				model.addAttribute("itype", type);
				model.addAttribute("result", result);
				return "/sms/SMSSearch";
			}

		} catch (Exception e) {
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/sms/SMSSearch";
		}
	}
}
