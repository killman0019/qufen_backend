package com.tzg.rest.exception.rest.handle;

import javax.servlet.http.HttpServletRequest;


public class AppExceptionHandler extends RestServiceExceptionHandler{
	
	@Override
	public boolean isRestServiceException(HttpServletRequest request){
		 return true;
	}

}
