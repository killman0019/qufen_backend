package com.tzg.action;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.entitys.evaluation.Evaluation;
import com.tzg.service.evaluation.EvaluationService;

@Controller
@RequestMapping("/post/evaluation")
public class EvaluationController extends BaseController {
	private static Logger log = Logger.getLogger(EvaluationController.class);
	
	@Autowired
	private EvaluationService evaluationService;
	
	@RequestMapping(value="/new")
	public String add() throws Exception {		
		return "/evaluation/new";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Evaluation evaluation = evaluationService.findById(id);
			model.addAttribute("param", evaluation);
			model.addAttribute(SUCCESS, true);
			return "/evaluation/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/evaluation/update";
		}
	}
	
	@RequestMapping(value="/save")
	public String save(@ModelAttribute("evaluation") Evaluation evaluation,Model model) {		
		try {
			evaluationService.save(evaluation);
			model.addAttribute(SUCCESS, true);
			return "redirect:/evaluation";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", evaluation);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/evaluation/new";
		}
	}
	
	@RequestMapping(value="/update")
	public String update(@ModelAttribute("evaluation") Evaluation evaluation,Model model) throws Exception {		
		try {
			evaluationService.update(evaluation);
			model.addAttribute(SUCCESS, true);
			return "redirect:/evaluation";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", evaluation);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/evaluation/update";
		}
	}
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model,Integer postId) throws Exception {
		try {
			Evaluation eva = new Evaluation();
			eva.setEvaluationId(id);
			eva.setPostId(postId);
			eva.setStatus(0);
			eva.setUpdateTime(new Date());
			evaluationService.updateStatus(eva);
			model.addAttribute(SUCCESS, true);
			return "redirect:/evaluation";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/evaluation";
		}
	}
	
	@RequestMapping(value="/active")
	public String active(Integer id,Model model,Integer postId) throws Exception {
		try {
			Evaluation eva = new Evaluation();
			eva.setEvaluationId(id);
			eva.setPostId(postId);
			eva.setStatus(1);
			eva.setUpdateTime(new Date());
			evaluationService.updateStatus(eva);
			model.addAttribute(SUCCESS, true);
			return "redirect:/evaluation";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/evaluation";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String projectCode,String createUserName,Integer modelType,Integer status) throws Exception{	
		
		if(StringUtils.isNotBlank(projectCode)){
			query.addQueryData("projectCode", projectCode);
		}
		if(StringUtils.isNotBlank(createUserName)){
			query.addQueryData("createUserName", createUserName);		
		}
		if(modelType != null){
			query.addQueryData("modelType", modelType+"");
		}
		if(status != null){
			query.addQueryData("status", status+"");
		}
		query.addQueryData("postType", "1");
		PageResult<Evaluation> pageList = evaluationService.findPage(query);
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		return "/evaluation/list";
	}
	
}


