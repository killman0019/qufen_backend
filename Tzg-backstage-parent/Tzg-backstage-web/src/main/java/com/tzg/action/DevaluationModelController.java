package com.tzg.action;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

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

import com.tzg.action.BaseController;
import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.devaluationModel.DevaluationModel;
import com.tzg.service.devaluationModel.DevaluationModelService;


@Controller
@RequestMapping("/devaluationModel")
public class DevaluationModelController extends BaseController {
	private static Logger log = Logger.getLogger(DevaluationModelController.class);
	
	@Autowired
	private DevaluationModelService devaluationModelService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/devaluationModel/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			DevaluationModel devaluationModel = devaluationModelService.findById(id);
			model.addAttribute("param", devaluationModel);
			model.addAttribute(SUCCESS, true);
			return "/devaluationModel/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/devaluationModel/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("devaluationModel") DevaluationModel devaluationModel,Model model) {		
		try {
			devaluationModelService.save(devaluationModel);
			model.addAttribute(SUCCESS, true);
			return "redirect:/devaluationModel";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", devaluationModel);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/devaluationModel/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("devaluationModel") DevaluationModel devaluationModel,Model model) throws Exception {		
		try {
			devaluationModelService.update(devaluationModel);
			model.addAttribute(SUCCESS, true);
			return "redirect:/devaluationModel";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", devaluationModel);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/devaluationModel/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			devaluationModelService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/devaluationModel";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/devaluationModel";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model) throws Exception{		
		PageResult<DevaluationModel> pageList = devaluationModelService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/devaluationModel/list";
	}
	
}


