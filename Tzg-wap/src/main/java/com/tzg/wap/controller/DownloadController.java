package com.tzg.wap.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzg.common.utils.StringUtil;
import com.tzg.common.utils.TzgConstant;

@RequestMapping("/down")
@Controller
public class DownloadController extends BaseController {
	
	@RequestMapping("/app")
	public String app(HttpServletRequest request,Model model){
    	return "/down/landingPage";
	}
	
	@RequestMapping("/iosDetail")
	public String iosDetail(HttpServletRequest request,Model model){
		return "/down/iosDetail";
	}
	
	@RequestMapping("/download")
	public void getDownLoadPath(HttpServletRequest request,HttpServletResponse response){
		try {
			String userAgent = request.getHeader("user-agent");
			if(userAgent.toLowerCase().contains("iphone")||userAgent.toLowerCase().contains("ipad")){
				response.sendRedirect(TzgConstant.IOS_APP_STORE_DOWN_URL);
				return;
			}
			String andriodAppDownUrl = TzgConstant.ANDRIOD_APP_DOWN_URL;
			String from = request.getParameter("from");
			if(StringUtil.isNotBlank(from)){
				andriodAppDownUrl = andriodAppDownUrl.replace("tzg.apk", from+".apk");
			}
			response.sendRedirect(andriodAppDownUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
