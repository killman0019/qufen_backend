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
import com.tzg.entitys.smsrecord.Smsrecord;
import com.tzg.service.smsrecord.SmsrecordService;

@Controller
@RequestMapping("/smsrecord")
public class SmsrecordController extends BaseController {
	private static Logger log = Logger.getLogger(SmsrecordController.class);

	@Autowired
	private SmsrecordService smsrecordService;

	@RequestMapping(value = "/new")
	public String add() throws Exception {
		return "/smsrecord/new";
	}

	@RequestMapping(value = "/find")
	public String find(Integer id, Model model) {
		try {
			Smsrecord smsrecord = smsrecordService.findById(id);
			model.addAttribute("param", smsrecord);
			model.addAttribute(SUCCESS, true);
			return "/smsrecord/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smsrecord/update";
		}
	}

	@RequestMapping(value = "/save")
	public String save(@ModelAttribute("smsrecord") Smsrecord smsrecord,
			Model model) {
		try {
			smsrecordService.save(smsrecord);
			model.addAttribute(SUCCESS, true);
			return "redirect:/smsrecord";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smsrecord);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smsrecord/new";
		}
	}

	@RequestMapping(value = "/update")
	public String update(@ModelAttribute("smsrecord") Smsrecord smsrecord,
			Model model) throws Exception {
		try {
			smsrecordService.update(smsrecord);
			model.addAttribute(SUCCESS, true);
			return "redirect:/smsrecord";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", smsrecord);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/smsrecord/update";
		}
	}

	@RequestMapping(value = "/delete")
	public String delete(Integer id, Model model) throws Exception {
		try {
			smsrecordService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/smsrecord";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/smsrecord";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query, Model model, Smsrecord smsrecord,
			String dtSendBegin, String dtSendEnd) throws Exception {
		// 手机号码
		query.addQueryData("vcPhone", smsrecord.getVcPhone());
		// 发送状态
		if (null != smsrecord.getIstate())
			query.addQueryData("istate", smsrecord.getIstate().toString());
		//短信接口
		if (null != smsrecord.getSinterface())
			query.addQueryData("sinterface", smsrecord.getSinterface().toString());
		// 发送时间的范围
		query.addQueryData("dtSendBegin", dtSendBegin);
		query.addQueryData("dtSendEnd", dtSendEnd);
		PageResult<Smsrecord> pageList = smsrecordService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/smsrecord/list";
	}

}
