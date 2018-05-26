package com.tzg.service.uploadfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tzg.common.page.PageResult;
import com.tzg.common.page.PaginationQuery;
import com.tzg.common.utils.RandomUtil;
import com.tzg.core.utils.DateUtil;
import com.tzg.core.utils.FtpUtil;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.uploadfile.Uploadfile;
import com.tzg.entitys.uploadfile.UploadfileMapper;
import com.tzg.service.SystemParam.SystemParamService;
import com.tzg.service.base.BaseService;

@Service
@Transactional
public class UploadfileService extends BaseService {

	private static Logger log = Logger.getLogger(UploadfileService.class);

	@Autowired
	private UploadfileMapper uploadfileMapper;

	@Autowired
	private SystemParamService systemParamService;

	private static final String UPLOAD_FILE_Path = "upload_file_path";

	private static final String URL_SERVER_IP_CODE = "upload_server_ip";

	private static final String URL_SERVER_PORT_CODE = "upload_server_port";

	private static final String URL_SERVER_NAME_CODE = "upload_server_username";

	private static final String URL_SERVER_PASSWORD_CODE = "upload_server_password";
	
	private static final String UPLOAD_FILE_TYPE = "upload_file_type";
	
	private static final String UPLOAD_LOCAL_PATH = "upload_local_path";

	@Transactional(readOnly = true)
	public Uploadfile findById(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		return uploadfileMapper.findById(id);
	}

	public void delete(java.lang.Integer id) throws Exception {
		if (id == null) {
			throw new Exception("id不能为空");
		}
		uploadfileMapper.deleteById(id);
	}

	public void save(Uploadfile uploadfile) throws Exception {
		if (uploadfile.getItype() == null) {
			throw new Exception("类型不能为空！");
		}
		if (StringUtils.isBlank(uploadfile.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		SystemParam systemParam = systemParamService
				.findByCode(UPLOAD_FILE_TYPE);
		if (systemParam == null || systemParam.getVcParamValue() == null) {
			throw new Exception("系统参数" + UPLOAD_FILE_TYPE + "未设置，请先设置！");
		}
		if(systemParam.getVcParamValue().trim().equalsIgnoreCase("ftp"))
			ftpUpload(uploadfile.getVcUrl(), uploadfile.getBlobSource());
		else
			createFileLocal(uploadfile.getVcUrl(), uploadfile.getBlobSource());
		// 更新状态
		systemParam = systemParamService.findByCode(UPLOAD_FILE_Path);
		uploadfile.setVcUrl(systemParam.getVcParamValue()+ uploadfile.getVcUrl());
		uploadfile.setIstate(2);
		uploadfile.setDtCreate(new Date());
		uploadfileMapper.save(uploadfile);
	}

	public void update(Uploadfile uploadfile) throws Exception {
		if (uploadfile.getItype() == null) {
			throw new Exception("类型不能为空！");
		}
		if (StringUtils.isBlank(uploadfile.getVcName())) {
			throw new Exception("名称不能为空！");
		}
		SystemParam systemParam = systemParamService
				.findByCode(UPLOAD_FILE_TYPE);
		if (systemParam == null || systemParam.getVcParamValue() == null) {
			throw new Exception("系统参数" + UPLOAD_FILE_TYPE + "未设置，请先设置！");
		}
		if (uploadfile.getVcUrl() != null
				&& uploadfile.getVcUrl().trim().length() > 0) {
			if(systemParam.getVcParamValue().trim().equalsIgnoreCase("ftp"))
				ftpUpload(uploadfile.getVcUrl(), uploadfile.getBlobSource());
			else
				createFileLocal(uploadfile.getVcUrl(), uploadfile.getBlobSource());
			// 更新状态
			//uploadfile.setVcUrl(systemParam.getVcParamValue() + uploadfile.getVcUrl());
		}
		uploadfileMapper.update(uploadfile);
	}

	@Transactional(readOnly = true)
	public PageResult<Uploadfile> findPage(PaginationQuery query)
			throws Exception {
		PageResult<Uploadfile> result = null;
		try {
			Integer count = uploadfileMapper.findPageCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",Integer.toString(query.getRowsPerPage()));
				List<Uploadfile> list = uploadfileMapper.findPage(query.getQueryData());
				result = new PageResult<Uploadfile>(list, count, query);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	/**
	 * 
	 * @param vcUrl
	 * @return
	 * @throws Exception
	 */
	public Uploadfile queryUploadFileByVcUrl(String vcUrl) throws Exception {
		Uploadfile uploadfile = null;
		uploadfile = uploadfileMapper.queryUploadFileByVcUrl(vcUrl);
		return uploadfile;
	}

	/**
	 * 生成本地文件
	 *
	 * @param request
	 * @param vcUrl
	 * @param blobSource
	 * @throws Exception
	 */
	private File createFileLocal(String vcUrl, byte[] blobSource)
			throws Exception {
		SystemParam systemParam = systemParamService.findByCode(UPLOAD_LOCAL_PATH);
		if (systemParam == null) {
			throw new Exception("系统参数" + UPLOAD_LOCAL_PATH + "未设置，请先设置！");
		}
		String path = systemParam.getVcParamValue();
		File file = new File(path + vcUrl);
		if (file.exists())
			return file;
		File dir = new File(file.getParent());
		if (!dir.exists())
			dir.mkdirs();
		if (file.createNewFile()) {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(blobSource);
			fos.close();
		}
		return file;
	}

	/**
	 * ftp文件上传
	 * 
	 * @param path
	 * @throws Exception
	 */
	private void ftpUpload(String fileName, byte[] content) throws Exception {
		// 上传资料服务器地址
		SystemParam serIPParam = systemParamService.findByCode(URL_SERVER_IP_CODE);
		// 上传资料服务器端口
		SystemParam serPortParam = systemParamService.findByCode(URL_SERVER_PORT_CODE);
		// 上传资料服务器用户名
		SystemParam serNameParam = systemParamService.findByCode(URL_SERVER_NAME_CODE);
		// 上传资料服务器密码
		SystemParam serPwdParam = systemParamService.findByCode(URL_SERVER_PASSWORD_CODE);
		String ip = null;
		String username = null;
		String password = null;
		int port;
		if (serIPParam == null) {
			throw new Exception("系统参数" + URL_SERVER_IP_CODE + "未设置，请先设置！");
		}
		if (serPortParam == null) {
			throw new Exception("系统参数" + URL_SERVER_PORT_CODE + "未设置，请先设置！");
		}
		if (serNameParam == null) {
			throw new Exception("系统参数" + URL_SERVER_NAME_CODE + "未设置，请先设置！");
		}
		if (serPwdParam == null) {
			throw new Exception("系统参数" + URL_SERVER_PASSWORD_CODE + "未设置，请先设置！");
		}
		ip = serIPParam.getVcParamValue();
		port = Integer.parseInt(serPortParam.getVcParamValue());
		username = serNameParam.getVcParamValue();
		password = serPwdParam.getVcParamValue();

		File file = new File(fileName);
		FtpUtil ftpClient = new FtpUtil();
		ftpClient.connect("/", ip, port, username, password);
		ftpClient.upload(file.getParent(), file.getName(), content);
	}

	/**
	 * 上传所有未同步文件
	 * 
	 * @throws Exception
	 */
	public void syncUpload() throws Exception {
		// 所有未同步记录
		PaginationQuery query = new PaginationQuery();
		query.addQueryData("istate", "1");
		PageResult<Uploadfile> pageList = findNotSyncPage(query);
		if (pageList != null) {
			for (int i = 0; i < pageList.getPageSize(); i++) {
				List<Uploadfile> list = pageList.getRows();
				if (list != null && list.size() > 0) {
					for (Uploadfile info : list) {
						try {
							String vcUrl = info.getVcUrl();
							Integer index = vcUrl.indexOf("/", 1);
							vcUrl = vcUrl.substring(index, vcUrl.length());
							ftpUpload(vcUrl, info.getBlobSource());
							info.setIstate(2);
							uploadfileMapper.update(info);
						} catch (Exception e) {
							log.error("文件路径为[" + info.getVcUrl() + "]的文件同步上传异常",e);
						}
					}
				}
				query.setPageIndex(query.getPageIndex() + 1);
				pageList = findNotSyncPage(query);
				if (pageList != null) {
					continue;
				} else {
					break;
				}
			}
		} else {
			throw new Exception("所有记录都已同步");
		}
	}

	@Transactional(readOnly = true)
	public PageResult<Uploadfile> findNotSyncPage(PaginationQuery query)
			throws Exception {
		PageResult<Uploadfile> result = null;
		try {
			Integer count = uploadfileMapper.findPageNotSyncCount(query.getQueryData());
			if (null != count && count.intValue() > 0) {
				int startRecord = (query.getPageIndex() - 1)* query.getRowsPerPage();
				query.addQueryData("startRecord", Integer.toString(startRecord));
				query.addQueryData("endRecord",Integer.toString(query.getRowsPerPage()));
				List<Uploadfile> list = uploadfileMapper.findNotSyncPage(query.getQueryData());
				result = new PageResult<Uploadfile>(list, count, query);
			}
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}
	
	
	
	//excelfile处理
	@SuppressWarnings("rawtypes")
	public List<Map> readExcel(String fileName, MultipartFile file)
			throws Exception {
		//save(file);// 保存到上传资料里
		List<Map> valueList = new ArrayList<Map>();
		String ExtensionName = getExtensionName(fileName);
		String filePath =createUploadFileName(file.getOriginalFilename());
		File tmpfile = new File(filePath);
		
		// 拷贝文件到服务器缓存目录（在项目下）
		copy(file, filePath);//spring mvc用的方法 
		if (ExtensionName.equalsIgnoreCase("xls")) {
			valueList = readExcel2003(filePath);
		} else if (ExtensionName.equalsIgnoreCase("xlsx")) {
			valueList = readExcel2007(filePath);
		}
		// 删除缓存文件
		tmpfile.delete();
		return valueList;
	}

	/**
	 * 读取97-2003格式
	 * 
	 * @param filePath
	 *            文件路径
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2003(String filePath) throws IOException {
		// 返回结果集
		List<Map> valueList = new ArrayList<Map>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			@SuppressWarnings("resource")
			HSSFWorkbook wookbook = new HSSFWorkbook(fis); // 创建对Excel工作簿文件的引用
			HSSFSheet sheet = wookbook.getSheetAt(0); // 在Excel文档中，第一张工作表的缺省索引是0
			int rows = sheet.getPhysicalNumberOfRows(); // 获取到Excel文件中的所有行数­
			Map<Integer, String> keys = new HashMap<Integer, String>();
			int cells = 0;
			// 遍历行­（第1行 表头） 准备Map里的key
			HSSFRow firstRow = sheet.getRow(2);
			if (firstRow != null) {
				// 获取到Excel文件中的所有的列
				cells = firstRow.getPhysicalNumberOfCells();
				// 遍历列
				for (int j = 0; j < cells; j++) {
					// 获取到列的值­
					try {
						HSSFCell cell = firstRow.getCell(j);
						String cellValue = getCellValue(cell);
						keys.put(j, cellValue);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			// 遍历行­（从第二行开始）
			for (int i = 3; i < rows; i++) {
				// 读取左上端单元格(从第二行开始)
				HSSFRow row = sheet.getRow(i);
				// 行不为空
				if (row != null) {
					// 准备当前行 所储存值的map
					Map<String, Object> val = new HashMap<String, Object>();
					boolean isValidRow = false;
					// 遍历列
					for (int j = 0; j < cells; j++) {
						// 获取到列的值­
						try {
							HSSFCell cell = row.getCell(j);
							String cellValue = getCellValue(cell);
							val.put(keys.get(j), cellValue);
							if (!isValidRow && cellValue != null && cellValue.trim().length() > 0) {
								isValidRow = true;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					// 第I行所有的列数据读取完毕，放入valuelist
					if (isValidRow) {
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		return valueList;
	}

	/**
	 * 读取2007-2013格式
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws java.io.IOException
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> readExcel2007(String filePath) {
		List<Map> valueList = new ArrayList<Map>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			@SuppressWarnings("resource")
			XSSFWorkbook xwb = new XSSFWorkbook(fis); // 构造 XSSFWorkbook
			XSSFSheet sheet = xwb.getSheetAt(0); // 读取第一章表格内容
			// 定义 row、cell
			XSSFRow row;
			// 循环输出表格中的第一行内容 表头
			Map<Integer, String> keys = new HashMap<Integer, String>();
			row = sheet.getRow(2);
			if (row != null) {
				for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
					// 通过 row.getCell(j).toString() 获取单元格内容，
					if (row.getCell(j) != null) {
						if (!row.getCell(j).toString().isEmpty()) {
							keys.put(j, row.getCell(j).toString());
						}
					} else {
						keys.put(j, "K-R1C" + j + "E");
					}
				}
			}
			// 循环输出表格中的从第二行开始内容
			for (int i = sheet.getFirstRowNum() + 3; i <= sheet.getPhysicalNumberOfRows(); i++) {
				row = sheet.getRow(i);
				
				if (row != null) {
					boolean isValidRow = false;
					Map<String, Object> val = new HashMap<String, Object>();
					for (int j = row.getFirstCellNum(); j <= row.getPhysicalNumberOfCells(); j++) {
						XSSFCell cell = row.getCell(j);
						if (cell != null) {
							String cellValue=null;
							if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
								if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
									cellValue = new DataFormatter().formatRawCellContents(cell.getNumericCellValue(),0, "yyyy-MM-dd HH:mm:ss");
								} else {
									DecimalFormat df = new DecimalFormat("0");  
									cellValue = df.format(cell.getNumericCellValue()); 
								}
							} else {
								DecimalFormat df = new DecimalFormat("0");  
								cellValue = df.format(cell.getNumericCellValue());  
							}
							if (cellValue != null&& cellValue.trim().length() <= 0) {
								cellValue = null;
							}
							val.put(keys.get(j), cellValue);
							if (!isValidRow && cellValue != null&& cellValue.trim().length() > 0) {
								isValidRow = true;
							}
						}
					}

					// 第I行所有的列数据读取完毕，放入valuelist
					if (isValidRow) {
						valueList.add(val);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return valueList;
	}
	
	public static void main(String[] args) throws IOException {
		StringBuffer sb =new StringBuffer();
		List<Map> valueList = new ArrayList<Map>();
		
		valueList = readExcel2003("C:\\111.xls");
		int a = 0;
		for (Map m : valueList) {
			for (Object o : m.keySet()) {
				if (o.equals("领用人账户")) {
					
					sb.append(m.get(o).toString());
					sb.append(",");
					a+=1;
					if(a%200==0){
						sb.append("\n");
					}
					
					}
				}
		}
		System.out.println(sb.toString());
		
	}

	/**
	 * 文件操作 获取文件扩展名
	 * 
	 * @Author: sunny
	 * @param filename
	 *            文件名称包含扩展名
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	  /** 
     * 上传copy文件方法(for MultipartFile) 
     * @param savePath 在linux上要保存完整路径 
     * @param newname 新的文件名称， 采用系统时间做文件名防止中文报错的问题 
     * @throws Exception 
     */  
    public static void copy(MultipartFile file,String savePath) throws Exception {  
        try {  
            File targetFile = new File(savePath);  
            if (!targetFile.exists()) {  
                //判断文件夹是否存在，不存在就创建  
                targetFile.mkdirs();  
            }  
  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
	private String createUploadFileName(String oriFileName) {
		String suffix = oriFileName.substring(oriFileName.lastIndexOf("."));
		return "/" + DateUtil.getCurrentYearMonth() + "/"
				+ DateUtil.getCurrentTime() + RandomUtil.produceNumber(6)
				+ suffix;
	}
	
	private static String getCellValue(HSSFCell cell) {
		DecimalFormat df = new DecimalFormat("#");
		String cellValue = null;
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cellValue = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
				break;
			}
			cellValue = df.format(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_STRING:
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			cellValue = String.valueOf(cell.getCellFormula());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = null;
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			cellValue = String.valueOf(cell.getErrorCellValue());
			break;
		}
		if (cellValue != null && cellValue.trim().length() <= 0) {
			cellValue = null;
		}
		return cellValue;
	}

	public Uploadfile save(MultipartFile file) throws Exception {
		try {
			Uploadfile uploadfile = new Uploadfile();
			uploadfile.setItype(15);//定向红包
			uploadfile.setIstate(1);//未同步
			uploadfile.setVcName("定向红包");
			uploadfile.setVcOrnName(file.getOriginalFilename());
			String url = createUploadFileName(file.getOriginalFilename());
			uploadfile.setVcUrl(url);
			uploadfile.setBlobSource(file.getBytes());
			save(uploadfile);
			return uploadfile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
