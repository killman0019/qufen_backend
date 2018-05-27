package com.tzg.wap.controller.h5;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.wap.utils.DateUtil;

@Controller
@RequestMapping("/kff/upload")
public class UploadController extends BaseController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private KFFRmiService kFFRmiService;

	@ResponseBody
	@RequestMapping(value = "/idcard", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadIdCard(@RequestParam(required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 保存图片到
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTime();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());

		upfile.transferTo(new File("D:\\opt\\file\\upload\\Idcard\\" + name + "." + ext));

		resMap.put("picPath", "Idcard\\" + name + "." + ext);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/avatars", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadAvatars(@RequestParam(required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 保存图片到
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTime();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());

		upfile.transferTo(new File("D:\\opt\\file\\upload\\avatars\\" + name + "." + ext));
		resMap.put("picPath", "avatars\\" + name + "." + ext);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/postPic", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadPostPic(@RequestParam(value = "upfile", required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		log.info("图片进入接口!++++++++++++++++++++++");
		// 保存图片到
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTime();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());

		upfile.transferTo(new File("D:\\opt\\file\\upload\\postPic\\" + name + "." + ext));

		resMap.put("picPath", "postPic\\" + name + "." + ext);
		log.info("图片存入成功!++++++++++++++++++++++");
		log.info(name + "." + ext);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/postPicsf", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadPostPicsf(@RequestParam(value = "upfile", required = false) MultipartFile upfile, HttpServletRequest request,
			HttpServletResponse response) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		response.setContentType("text/html;charset=gbk");
		log.info("postPicsf图片进入接口!++++++++++++++++++++++");
		// 保存图片到

		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTime();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());

		upfile.transferTo(new File("D:\\opt\\file\\upload\\postPic\\" + name + "." + ext));

		resMap.put("file_path", "http://192.168.10.151:8080//postPic//" + name + "." + ext);
		resMap.put("success", "true");
		bre.setData(resMap);
		String file_Name = "http://192.168.10.151:8080//postPic//" + name + "." + ext;
		log.info("postPicsf图片存入成功!++++++++++++++++++++++");
		log.info(name + "." + ext);
		bre.setMsg("{\"success\":\"" + true + "\",\"file_path\":\"" + file_Name + "\"}");
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/authentication", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadAuthentication(@RequestParam(required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();

		// 保存图片到
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTime();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());

		upfile.transferTo(new File("D:\\opt\\file\\upload\\authentication\\" + name + "." + ext));

		resMap.put("picPath", "authentication\\" + name + "." + ext);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/test", method = { RequestMethod.POST, RequestMethod.GET })
	public String test(String str) {
		return kFFRmiService.uploadIeviw(str);

	}
}