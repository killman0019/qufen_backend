package com.tzg.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tzg.common.utils.StringUtil;
import com.tzg.service.SystemParam.SystemParamService;

public abstract class BaseController {
	
	
	@Autowired
	private SystemParamService systemParamService;
	
    private static StringUtil stringUtil = new StringUtil();//工具类，方便在页面使用，如果有需要还可以添加更多的工具类

    final String MESSAGE ="msg";
    
    final String SUCCESS = "success";
    
    @SuppressWarnings("unused")
	private String www;
    
  
    @ModelAttribute()
    public void init(ModelMap model) throws Exception {
		model.addAttribute("www",getWww());
		model.addAttribute("staticUploadUrl", systemParamService.getStaticUploadUrl());
    }
    
    @ModelAttribute("stringUtil")
    public StringUtil getStringUtil() {
        return stringUtil;
    }
	public String getWww() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	public void setWww(String www) {
		this.www = www;
	}





    public  void setDynamicValidateCodeSession(String type,String code)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("dynamicValidateCode-"+type, code);
	}
	
	
	public  String getDynamicValidateCodeSession(String type)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        if(session == null) {
        	return null;	
        }
        String code = (String) session.getAttribute("dynamicValidateCode-"+type);
		return code;
	}
	
	public  boolean verifyDynamicValidateCode(String type,String code)
	{
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        if(session == null) {
        	return false;	
        }
        String dynamicValidateCode = (String) session.getAttribute("dynamicValidateCode-"+type);
		if(dynamicValidateCode!=null && dynamicValidateCode.equalsIgnoreCase(code)){
			return true;
		}
        return false;
	}
	
	public  void removeDynamicValidateCodeSession(String type) {
    	HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
        session.removeAttribute("dynamicValidateCode-"+type);
    }
	
}
