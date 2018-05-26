package com.tzg.rest.controller.kff;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzg.rest.controller.BaseController;
import com.tzg.rmi.service.KFFRmiService;

@Controller(value="KFFPostController")
@RequestMapping("/kff/post")
public class PostController extends BaseController {
	private static Logger log = Logger.getLogger(PostController.class);
	
	@Autowired
	private KFFRmiService kffRmiService;
}


