package com.tzg.wap.controller.h5;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tzg.common.redis.RedisService;
import com.tzg.common.utils.FileUtils;
import com.tzg.common.utils.RandomUtil;
import com.tzg.entitys.kff.user.KFFUser;
import com.tzg.rest.exception.rest.RestServiceException;
import com.tzg.rest.vo.BaseResponseEntity;
import com.tzg.rmi.service.KFFRmiService;
import com.tzg.wap.utils.DateUtil;
import com.tzg.wap.utils.QiniuUtil;

@Controller
@RequestMapping("/kff/upload")
public class UploadController extends BaseController {
	private static Logger log = Logger.getLogger(UserController.class);
	@Autowired
	private KFFRmiService kFFRmiService;

	@Value("#{paramConfig['registerUrl']}")
	private String contentself;

	@Value("#{paramConfig['picUrl']}")
	private String picUrl;

	@Value("#{paramConfig['ipPicUrl']}")
	private String ipPicUrl;

	@ResponseBody
	@RequestMapping(value = "/idcard", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadIdCard(@RequestParam(required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String picUrlIdCard = picUrl + "/upload/Idcard/";
		if (null == upfile) {
			throw new RestServiceException("上传图片不能为空!");
		}
		// 大于10M
		if (upfile.getSize() >= 5 * 1024 * 1024) {
			throw new RestServiceException("图片大于5M");
		}
		// 保存图片到
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTimeSS();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());
		if (!FileUtils.allowedExtensionSet().contains(ext)) {
			throw new RestServiceException("非法文件后缀" + ext);
		}

		//String fileName = picUrlIdCard + DateUtil.getCurrentYearMonth() + "/" + name + "." + ext;
		//FileUtils.createFileLocal(fileName, upfile.getBytes());

		String fName =  name + "." + ext;
		String picPath = QiniuUtil.uploadStream(upfile.getInputStream(), fName);
		
		resMap.put("picPath", picPath);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/avatars", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadAvatars(@RequestParam(required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String picUrlAvatars = picUrl + "/upload/avatars/";
		if (null == upfile) {
			throw new RestServiceException("上传图片不能为空!");
		}
		// 大于10M
		if (upfile.getSize() >= 5 * 1024 * 1024) {
			throw new RestServiceException("图片大于5M");
		}
		// 保存图片到
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTimeSS();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());
		if (!FileUtils.allowedExtensionSet().contains(ext)) {
			throw new RestServiceException("非法文件后缀" + ext);
		}

		//String fileName = picUrlAvatars + DateUtil.getCurrentYearMonth() + "/" + name + "." + ext;
		//FileUtils.createFileLocal(fileName, upfile.getBytes());

		String fName =  name + "." + ext;
		String picPath = QiniuUtil.uploadStream(upfile.getInputStream(), fName);
		
		resMap.put("picPath", picPath);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/postPic", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadPostPic(@RequestParam(value = "upfile", required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		String picUrlPostPic = picUrl + "/upload/postPic/";
		log.info("图片进入接口!++++++++++++++++++++++");
		// 保存图片到
		if (null == upfile) {
			throw new RestServiceException("上传图片不能为空!");
		}
		// 大于10M
		if (upfile.getSize() >= 5 * 1024 * 1024) {
			throw new RestServiceException("图片大于5M");
		}
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTimeSS();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());
		if (!FileUtils.allowedExtensionSet().contains(ext)) {
			throw new RestServiceException("非法文件后缀" + ext);
		}

		//String fileName = picUrlPostPic + DateUtil.getCurrentYearMonth() + "/" + name + "." + ext;
		//FileUtils.createFileLocal(fileName, upfile.getBytes());

		String fName =  name + "." + ext;
		String picPath = QiniuUtil.uploadStream(upfile.getInputStream(), fName);
		
		resMap.put("picPath",picPath );
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
		String picUrlPostPicsf = picUrl + "/upload/postPic/";
		if (null == upfile) {
			throw new RestServiceException("上传图片不能为空!");
		}
		// 大于10M
		if (upfile.getSize() >= 5 * 1024 * 1024) {
			throw new RestServiceException("图片大于5M");
		}
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTimeSS();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());
		if (!FileUtils.allowedExtensionSet().contains(ext)) {
			throw new RestServiceException("非法文件后缀" + ext);
		}

		//String fileName = picUrlPostPicsf + DateUtil.getCurrentYearMonth() + "/" + name + "." + ext;
		//FileUtils.createFileLocal(fileName, upfile.getBytes());
		
		String fName =  name + "." + ext;
		String picPath = QiniuUtil.uploadStream(upfile.getInputStream(), fName);
		
		resMap.put("file_path",picPath );
		resMap.put("success", "true");
		bre.setData(resMap);
		log.info("postPicsf图片存入成功!++++++++++++++++++++++");
		//bre.setMsg("{\"success\":\"" + true + "\",\"file_path\":\"" + file_Name + "\"}");
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/authentication", method = { RequestMethod.POST, RequestMethod.GET }, consumes = "multipart/form-data")
	public BaseResponseEntity uploadAuthentication(@RequestParam(required = false) MultipartFile upfile) throws Exception, IOException {
		BaseResponseEntity bre = new BaseResponseEntity();
		Map<String, Object> resMap = new HashMap<String, Object>();
		log.info("authentication图片进入接口!++++++++++++++++++++++");

		String picUrlAuthentication = picUrl + "/upload/authentication/";
		// 保存图片到
		// 保存图片到
		if (null == upfile) {
			throw new RestServiceException("上传图片不能为空!");
		}
		// 大于10M
		if (upfile.getSize() >= 5 * 1024 * 1024) {
			throw new RestServiceException("图片大于5M");
		}
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		name = DateUtil.getCurrentTimeSS();
		// jpg
		String ext = FilenameUtils.getExtension(upfile.getOriginalFilename());
		if (!FileUtils.allowedExtensionSet().contains(ext)) {
			throw new RestServiceException("非法文件后缀" + ext);
		}
		/*if (upfile.getSize() >= 3 * 1024 * 1024) {
			Thumbnails.of(upfile.getInputStream()).scale(1f).outputQuality(0.25f)
					.toFile(new File(picUrlAuthentication + DateUtil.getCurrentYearMonth() + "/" + name + "." + ext));
		} else {
			upfile.transferTo(new File(picUrlAuthentication + DateUtil.getCurrentYearMonth() + "\\" + name + "." + ext));
		}
		*/
		//String fileName = picUrlAuthentication + DateUtil.getCurrentYearMonth() + "/" + name + "." + ext;
		//FileUtils.createFileLocal(fileName, upfile.getBytes());
		
		String fName =  name + "." + ext;
		String picPath = QiniuUtil.uploadStream(upfile.getInputStream(), fName);
		
		log.info("authentication +++++++图片存入成功!++++++++++++++++++++++");
		log.info(name + "." + ext);
		resMap.put("picPath", picPath);
		bre.setData(resMap);
		return bre;
	}

	@ResponseBody
	@RequestMapping(value = "/test", method = { RequestMethod.POST, RequestMethod.GET })
	public String test(String str) {
		return kFFRmiService.uploadIeviw(str);

	}
}
