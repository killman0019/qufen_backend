package com.tzg.core.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * @Description：文件上传工具类
 * @author wxg
 * @Date 2014-12-17
 * 
 */
public class FileOperateUtil {

	private static final String UPLOADDIR = "\\WEB-INF\\static\\contract\\";

	/**
	 * 文件上传
	 * 
	 * @param request
	 * @throws IOException
	 */
	public static String uploadFile(HttpServletRequest request)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		MultipartFile multipartFile = multipartRequest
				.getFile(fileNames.next());
		// String fileAlias = multipartFile.getName();
		String name = multipartFile.getOriginalFilename();
		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")
				+ FileOperateUtil.UPLOADDIR;
		String filePath = uploadDir + "/" + name;
		saveFile(filePath, multipartFile.getBytes());
		
		return "/static/contract/" + name;
	}

	/**
	 * 保存文件
	 * 
	 * @param filePath
	 * @param content
	 * @throws IOException
	 */
	private static void saveFile(String filePath, byte[] content)
			throws IOException {
		BufferedOutputStream bos = null;
		try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				// 文件路径不存在时，创建保存文件所需要的路径
				file.getParentFile().mkdirs();
			}
			// 创建文件（这是个空文件，用来写入上传过来的文件的内容）
			file.createNewFile();
			bos = new BufferedOutputStream(new FileOutputStream(file));
			bos.write(content);
		} catch (Exception e) {
			throw new FileNotFoundException("文件不存在。");
		} finally {
			if (null != bos) {
				bos.close();
			}
		}
	}
	
	public static String getUpLoadDir(){
		return UPLOADDIR;
	}
	

}
