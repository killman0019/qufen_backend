package com.tzg.rest.exception.rest.handle;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ObjectFieldSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tzg.rest.exception.rest.RestError;
import com.tzg.rest.exception.rest.RestErrorConverter;
import com.tzg.rest.exception.rest.RestErrorResolver;

/**
 * 
* @ClassName: RestServiceExceptionHandler
* @Description: rest异常处理，需要判断是否为rest异常，如果不是，则交给正常页面异常处理，否则抛出json格式错误
* 判断是否rest异常需要具体项目具体实现，默认统一为true
* @author chy@tzg.cn
* @date 2015-1-5 下午1:39:24
*
 */
public class RestServiceExceptionHandler extends
		DefaultHandlerExceptionResolver {
	
	protected static final Logger LOGGER = Logger.getLogger(RestServiceExceptionHandler.class);

	/**
	 * 通过xml文件配置，可能存在自定义的异常类型
	 */
	private RestErrorResolver errorResolver;
	
	@Autowired
	private RestErrorConverter<?> errorConverter; //错误信息转换器
	
	/**
	 * 确认是否rest服务方法，需要子类重写
	 * 如果不是则不做处理，直接交给父类处理
	 * @param request
	 * @return 是否为rest服务
	 */
	public boolean isRestServiceException(HttpServletRequest request){
		return true;
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (isRestServiceException(request)) {
			LOGGER.error("catch restful error:" + ex);
			RestError error = errorResolver.resolveError(request, handler, ex);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
			try {
				JsonGenerator jsonGenerator = mapper.getFactory()
						.createGenerator(response.getOutputStream(),
								JsonEncoding.UTF8);
				mapper.writeValue(jsonGenerator, errorConverter.convert(error));
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("=================================================:"+mapper);
			try {
				response.setContentType("application/json;charset=UTF-8");
				response.getWriter().write(JSON.toJSONString(mapper,
						SerializerFeature.PrettyFormat,
						SerializerFeature.WriteMapNullValue,
						SerializerFeature.WriteNullStringAsEmpty,
						SerializerFeature.WriteNullBooleanAsFalse,
						SerializerFeature.WriteNullNumberAsZero
						));
			} catch (IOException e1) {
				logger.error(e1);
			}
			return new ModelAndView();
		} else
			return super.doResolveException(request, response, handler, ex);
	}

	public RestErrorResolver getErrorResolver() {
		return errorResolver;
	}

	public void setErrorResolver(RestErrorResolver errorResolver) {
		this.errorResolver = errorResolver;
	}
	
	
	

}
