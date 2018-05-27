package com.tzg.wap.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.Numbers;
import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.wap.WapConstants;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.rmi.service.SystemParamRmiService;
import com.tzg.wap.utils.Statistic;

public abstract class BaseController {
    
    @Autowired
    private SystemParamRmiService systemParamRmiService;
    @Autowired
    private RedisService redisService;
    
    private static Numbers numbers = new Numbers();
    private static StringUtil stringUtil = new StringUtil();
    private static Statistic sc = new Statistic();
    
    @ModelAttribute()
    public void init(ModelMap model) {
//      model.addAttribute("staticPath", getBasePath() + "/assets");
//      model.addAttribute("staticPath2", getBasePath() + "/assets2");
//      model.addAttribute("basePath",getBasePath());
//      model.addAttribute("bankCardPaymentProtocol", getBankCardPaymentProtocol());
//      model.addAttribute("staticUploadUrl", systemParamRmiService.getstaticUploadUrl());
        //basePath、staticPath、staticPath2、bankCardPaymentProtocol、staticUploadUrl基于安全考虑不要设置到model（属性会暴漏到url上，提供参数注入攻击的机会）.
        getStaticPath();
        getStaticPath2();
        getBasePath();
        getBankCardPaymentProtocol();
        staticUploadUrl();
        getActPath();
    }
    
    @ModelAttribute("numbers")
    public static Numbers getNumbers() {
        return numbers;
    }

    @ModelAttribute("stringUtil")
    public static StringUtil getStringUtil() {
        return stringUtil;
    }
    
    @ModelAttribute("sc")
    public static Statistic getSc() {
        return sc;
    }

    public String getBasePath() {
        HttpServletRequest request = getRequest();
        ServletContext context = request.getServletContext();
        Object object = context.getAttribute("basePath");
        if (null != object) {
            return object.toString();
        }
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        if (request.getServerPort() == 443) {
            basePath = basePath.replace(":443", "");
        }
        context.setAttribute("basePath", basePath);
        return basePath;
    }
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
     
    public boolean verifyDynamicValidateCode(String phoneNumber,String code,String bus) throws Exception {
        String cacheCode = this.redisService.get(new StringBuffer(WapConstants.key_wap).append(bus).append(phoneNumber).toString());
        if(StringUtils.isEmpty(cacheCode)) {
            return false;   
        }
        if(code.equalsIgnoreCase((String)cacheCode)){
            return true;
        }
        return false;
    }
    public String getBankCardPaymentProtocol() {
        ServletContext context =  getRequest().getServletContext();
        Object object = context.getAttribute("bankCardPaymentProtocol");
        if (null != object) {
            return object.toString();
        }
        String bankCardPaymentProtocol = systemParamRmiService.getBankCardPaymentProtocol();
        context.setAttribute("bankCardPaymentProtocol", bankCardPaymentProtocol);
        return bankCardPaymentProtocol;
    }
    public String getStaticPath() {
        ServletContext context =  getRequest().getServletContext();
        Object object = context.getAttribute("staticPath");
        if (null != object) {
            return object.toString();
        }
        String staticPath = getBasePath() + "/assets";
        context.setAttribute("staticPath", staticPath);
        return staticPath;
    }
    public String getStaticPath2() {
        ServletContext context =  getRequest().getServletContext();
        Object object = context.getAttribute("staticPath2");
        if (null != object) {
            return object.toString();
        }
        String staticPath = getBasePath() + "/assets2";
        context.setAttribute("staticPath2", staticPath);
        return staticPath;
    }
    
    public String staticUploadUrl(){
        ServletContext context = getRequest().getServletContext();
        Object object = context.getAttribute("staticUploadUrl");
        if (null != object) {
            return object.toString();
        }
        String staticUploadUrl = systemParamRmiService.getstaticUploadUrl();
        context.setAttribute("staticUploadUrl", staticUploadUrl);
        return staticUploadUrl;
    }
    
    public String getActPath(){
        ServletContext context = getRequest().getServletContext();
        Object object = context.getAttribute("actPath");
        if (null != object) {
            return object.toString();
        }
        String actPath = "https://act.tzg.cn";
        try {
            SystemParam systemParam = systemParamRmiService.findByCode("act_address");
            if (null != systemParam && StringUtil.isNotBlank(systemParam.getVcParamValue())) {
                actPath = systemParam.getVcParamValue();
            }
        } catch (Exception e) {
        }
        context.setAttribute("actPath", actPath);
        return actPath;
    }
}
