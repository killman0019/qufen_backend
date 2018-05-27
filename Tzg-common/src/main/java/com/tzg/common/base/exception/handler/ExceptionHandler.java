package com.tzg.common.base.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzg.common.base.exception.BusinessException;
import com.tzg.common.base.exception.ParameterException;
import com.tzg.common.utils.TzgConstant;

/**
 * 
 * Filename:    ExceptionHandler.java  
 * Description: 异常拦截
 * 异常处理：
 * <p>
 *  1、使用Spring MVC提供的简单异常处理器SimpleMappingExceptionResolver（仅能获取到异常信息，对需要获取除异常以外的数据的情况不适用）
 *  2、实现Spring的异常处理接口HandlerExceptionResolver 自定义自己的异常处理器（在异常处理时能获取导致出现异常的对象，有利于提供更详细的异常处理信息）
 *  3、使用@ExceptionHandler注解实现异常处理(在异常处理时不能获取除异常以外的数据)
 *      BaseController类，并在类中使用@ExceptionHandler注解声明异常处理
 *          //可以添加一个或多个异常的类型，如果为空的话则认为可以触发所有的异常类型错误
 *          @ExceptionHandler  
 *           public String exp(HttpServletRequest request, Exception ex) {  
 *             request.setAttribute("ex", ex);
 *           }    
 *      需要异常处理的Controller都继承BaseController类
 * </p>   
 * 未捕获异常的处理:web.xml中增加error-page
 * Copyright:   Copyright (c) 2015-2018 All Rights Reserved.
 * Company:     tzg.cn Inc.
 * @author:     heyiwu 
 * @version:    1.0  
 * Create at:   2016年1月22日 下午5:23:15  
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        Map<String, Object> model = new HashMap<String, Object>();
        if (!(handler instanceof HandlerMethod)) {
            logger.error("EXEC {},{}:", handler, ex.getLocalizedMessage(), ex);
            if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
                "XMLHttpRequest") > -1))) {
                return resolveException(ex, model);
            }
            return ajaxError(response, ex, model);
        }
        HandlerMethod hm = (HandlerMethod) handler;
        //异常日志记录
        logger.error("EXEC {}.{},{}:", hm.getBeanType().getName(), hm.getMethod().getName(), ex.getLocalizedMessage(), ex);
        ResponseBody body = hm.getMethodAnnotation(ResponseBody.class);
        if (body == null) {
            return resolveException(ex, model);
        }
        //ajax请求异常处理
        return ajaxError(response, ex, model);
    }
    /**
     * 异常处理(非ajax请求异常)
     * @author:  heyiwu 
     * @param ex
     * @param model
     * @return
     */
    private ModelAndView resolveException(Exception ex, Map<String, Object> model) {
        model.put("ex", ex);
        if (ex instanceof BusinessException) {
            return new ModelAndView("redirect:error/500", model);
        } else if (ex instanceof ParameterException) {
            return new ModelAndView("redirect:error/400", model);
        }
        return new ModelAndView("redirect:error/500", model);
    }
    
    /**
     * ajax异常处理
     * @author:  heyiwu 
     * @param response
     * @param ex
     * @param model
     * @return
     */
    private ModelAndView ajaxError(HttpServletResponse response, Exception ex, Map<String, Object> model) {
        //        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());  
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            PrintWriter writer = response.getWriter();
            model.put(TzgConstant.SUCCESS, false);
            boolean checked = (ex instanceof ParameterException) || (ex instanceof BusinessException);
            model.put(TzgConstant.MESSAGE, checked ? ex.getLocalizedMessage() : TzgConstant.DEFAULT_ERROR_MSG);
            writer.write(JSON.toJSONString(model));
            writer.flush();
        } catch (IOException e) {
        }
        return new ModelAndView();
    }
}
