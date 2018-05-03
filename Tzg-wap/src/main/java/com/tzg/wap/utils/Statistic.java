package com.tzg.wap.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Statistic {
	/**
	 * 百度统计
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getBaidu(HttpServletRequest request,HttpServletResponse response,String siteId){
		_HMT _hmt = new _HMT(siteId); //b274beec7e93e435fc242fc634bb5c73
		_hmt.setDomainName("m.tzg.cn");
		_hmt.setHttpServletObjects(request, response);
		String _hmtPixel = _hmt.trackPageview();
		return _hmtPixel;
	}
	
	/**
	 * cnzz统计
	 * @param request
	 * @param response
	 * @return
	 */
	public static String getCnzz(HttpServletRequest request,HttpServletResponse response,int siteId){
		_CS cs = new _CS(siteId); //1254699603
		cs.setHttpServlet(request,response);
		String imgurl = cs.trackPageView();
		return imgurl;
	}
}
