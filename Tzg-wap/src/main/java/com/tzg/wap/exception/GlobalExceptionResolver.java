package com.tzg.wap.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
/**
 * 全局异常处理  将所有的异常进行统一的处理  
 * @author Administrator
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mv = getDefaultModelAndView(request, ex);

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			ResponseBody responseBody = method.getAnnotation(ResponseBody.class);
			if (null != responseBody) {
				BaseResponseEntity baseResponseEntity = new BaseResponseEntity();
				baseResponseEntity.setCode(null);
				baseResponseEntity.setMsg(null);
				if (ex instanceof RestServiceException) {
					RestServiceException restServiceException = (RestServiceException) ex;
					baseResponseEntity.setCode(null);
					baseResponseEntity.setMsg(null);
				}
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json;charset=utf-8");
				PrintWriter pw = null;
				try {
					pw = response.getWriter();
					pw.write(JSON.toJSONString(baseResponseEntity));
					pw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (null != pw) {
						pw.close();
					}
				}
				return null;

			} else {
				if (ex instanceof RestServiceException) {

					RestServiceException restServiceException = (RestServiceException) ex;

					mv.addObject("errorMsg", restServiceException.getMessage());
					mv.addObject("errorCode", restServiceException.getErrorCode());
				}
				return mv;
			}
		} else {
			return mv;
		}

	}

	private ModelAndView getDefaultModelAndView(HttpServletRequest request, Exception ex) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("error");
		mv.addObject("ctx", request.getContextPath());
		// 设置错误页面的jsp
		mv.setView(null);
		mv.addObject("uri", request.getRequestURI());
		return mv;
	}

}
