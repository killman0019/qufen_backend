package com.tzg.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RandomUtil;
import com.tzg.core.utils.HttpSessionUtil;
import com.tzg.entitys.tokenaward.Tokenaward;
import com.tzg.entitys.tokenrecords.Tokenrecords;
import com.tzg.service.consoleLoginAccount.ConsoleLoginAccountService;
import com.tzg.service.smssend.SmssendService;
import com.tzg.service.tokenaward.TokenawardService;
import com.tzg.service.tokenrecords.TokenrecordsService;
@Controller
@RequestMapping("/tokenaward")
public class FindAwardController extends BaseController {
	private static Logger log = Logger.getLogger(FindAwardController.class);
	
	@Autowired
	private TokenrecordsService tokenrecordsService;
	@Autowired
	private TokenawardService tokenawardService;
	@Autowired
	private  ConsoleLoginAccountService consoleLoginAccountService;
	@Autowired
	private  SmssendService smssendService;
	

	private String vcPhone;
	
	
	@RequestMapping(value="/add")
	public String add() throws Exception {		
		return "/tokenaward/add";
	}
	
	@RequestMapping(value="/find")	
	public String find(Integer id, Model model){
		try {	
			Tokenaward tokenaward = tokenawardService.findById(id);
			model.addAttribute("param", tokenaward);
			model.addAttribute(SUCCESS, true);
			return "/evaluation/update";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/tokenaward/update";
		}
	}
	
	@RequestMapping(value="/adds")  // 
	public String save(@ModelAttribute("tokenaward") Tokenaward tokenaward,Model model,String dynamicValidateCode) {		
		try {
		    Random random = new Random();  
		    String result=""; 
		    String vcPhone2 = HttpSessionUtil.getLoginSession().getVcPhone();
		    if(StringUtils.isBlank(dynamicValidateCode)){
	        	model.addAttribute(MESSAGE, "手机动态码不能为空");
	            return "/tokenaward/add";
	        }
		    System.out.println("1111");
		    if(!HttpSessionUtil.verifyDynamicValidateCode(vcPhone2, dynamicValidateCode)){
	    		model.addAttribute(MESSAGE, "手机动态码错误");
	            return "/tokenaward/add";
	    	}
		    System.out.println("2222");
		    System.out.println(tokenaward.getInviteRewards());
		    System.out.println(tokenaward.getMobile());
		    
			tokenawardService.save(tokenaward);
			model.addAttribute(SUCCESS, true);
			return "redirect:/tokenaward/grantFind";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", tokenaward);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/tokenaward/add";
		}
	}
	
	/**
	 * 后台帐号动态码
	 * @param loginname
	 * @param bus
	 * @return
	 */
	@RequestMapping("/toConsoleAccount")
	@ResponseBody
	public Map<String, Object> toConsoleAccount(@RequestParam(required=false)String bus){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			/*if(StringUtils.isBlank(loginname)){
				throw new Exception("用户名不能为空");
			}
			ConsoleLoginAccount consoleLoginAccount =  consoleLoginAccountService.getLoginAccount(loginname);
			if(consoleLoginAccount == null){
				throw new Exception("该用户不存在");
			}
			if(consoleLoginAccount.getiValid()!=1){
				throw new Exception("该用户已失效，暂时无法使用");
	     	}*/
			
			String dynamicValidateCode = RandomUtil.produceNumber(6)+"";
			System.out.println(dynamicValidateCode);
			vcPhone = HttpSessionUtil.getLoginSession().getVcPhone();
			HttpSessionUtil.setDynamicValidateCodeSession(vcPhone,dynamicValidateCode);
			this.sendMSG(vcPhone, dynamicValidateCode, bus);
			map.put(SUCCESS, true);
		} catch (Exception e) {
			map.put(SUCCESS, false);
			map.put(MESSAGE, e.getMessage());
		}
		
		return map;
	}
	
	
	private void sendMSG(String vcPhone2, String dynamicValidateCode, String bus) {
		try {
			String vcContent = dynamicValidateCode;
			Integer itype = null;
			if(bus.equals("consoleLogin")){
				itype = 28;
			}
			String smsContent;
			smsContent = smssendService.getSmsContent(itype, dynamicValidateCode);
			if(smsContent != null){
				vcContent = smsContent;
			}
			smssendService.save(vcPhone2,vcContent,2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value="/update")
	public String update(@ModelAttribute("tokenaward") Tokenrecords tokenaward,Model model) throws Exception {		
		try {
			tokenrecordsService.update(tokenaward);
			model.addAttribute(SUCCESS, true);
			return "redirect:/tokenaward";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute("param", tokenaward);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "/tokenaward/update";
		}
	}
	
	
	@RequestMapping(value="/delete")
	public String delete(Integer id,Model model) throws Exception {
		try {
			tokenrecordsService.delete(id);
			model.addAttribute(SUCCESS, true);
			return "redirect:/tokenaward";
		} catch (Exception e) {
			log.error(e);
			model.addAttribute(SUCCESS, false);
			model.addAttribute(MESSAGE, e.getMessage());
			return "redirect:/tokenaward";
		}
	}
		
	@RequestMapping(method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model) throws Exception{		
		
		PageResult<Tokenaward> pageList = tokenawardService.findPage(query);
		
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		
		return "/tokenaward/list";
		
	}
	@RequestMapping(value="/select",method = RequestMethod.GET)
	public String list(PaginationQuery query,Model model,String userName,String mobile,
			Integer tokenAwardFunctionType,Integer distributionType,String createTime) throws Exception{		
		Map<String,String> hashMap = new HashMap<>();
		if(StringUtils.isNotBlank(userName)){
			//query.addQueryData("userName", userName);
			hashMap.put("userName", userName);
		}
		if(StringUtils.isNotBlank(mobile)){
			//query.addQueryData("mobile", mobile);		
			hashMap.put("mobile", mobile);
		}
		if(tokenAwardFunctionType != null){
			//query.addQueryData("tokenAwardFunctionType", tokenAwardFunctionType+"");		
			hashMap.put("tokenAwardFunctionType", tokenAwardFunctionType+"");
		}
		if(distributionType != null){
		//	query.addQueryData("distributionType", distributionType+"");	
			hashMap.put("distributionType", distributionType+"");
		}
		if(createTime != null){
		//	query.addQueryData("createTime", createTime);	
			hashMap.put("createTime", createTime);
		}
		PageResult<Tokenaward> pageList = tokenawardService.findPageSelect(query,hashMap);
		
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		
		return "/tokenaward/list";
		
	}
	@RequestMapping(value="/grantFind",method = RequestMethod.GET)
	public String list2(PaginationQuery query,Model model,String userName,String mobile,
			Integer tokenAwardFunctionType,Integer distributionType,String createTime,String issuer,String remark,Double inviteRewards) throws Exception{		
		Map<String,String> hashMap = new HashMap<>();
		if(StringUtils.isNotBlank(userName)){
			//query.addQueryData("userName", userName);
			hashMap.put("userName", userName);
		}
		if(StringUtils.isNotBlank(mobile)){
			//query.addQueryData("mobile", mobile);		
			hashMap.put("mobile", mobile);
		}
		if(tokenAwardFunctionType != null){
			//query.addQueryData("tokenAwardFunctionType", tokenAwardFunctionType+"");		
			hashMap.put("tokenAwardFunctionType", tokenAwardFunctionType+"");
		}
		if(distributionType != null){
			//	query.addQueryData("distributionType", distributionType+"");	
			hashMap.put("distributionType", distributionType+"");
		}
		if(createTime != null){
			//	query.addQueryData("createTime", createTime);	
			hashMap.put("createTime", createTime+"");
		}
		if(remark != null){
			//	query.addQueryData("createTime", createTime);	
			hashMap.put("remark", remark);
		}
		if(issuer != null){
			//	query.addQueryData("createTime", createTime);	
			hashMap.put("issuer", issuer);
		}
		if(inviteRewards != null){
			//	query.addQueryData("createTime", createTime);	
			hashMap.put("inviteRewards", inviteRewards+"");
		}
		PageResult<Tokenaward> pageList = tokenawardService.findPageSelect(query,hashMap);
		
		model.addAttribute("result", pageList);
		model.addAttribute("query", query.getQueryData());
		
		return "/tokenaward/new";
		
	}
}


