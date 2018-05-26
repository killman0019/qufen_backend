package com.tzg.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.leopard.system.SystemParamMapper;

public class ExportExcelByPoi {

	// 标题字体
//	private static HSSFFont titleFont = null;
	private static XSSFFont titleFont = null; //2007格式

	// 标题样式
//	private static HSSFCellStyle titleStyle = null;
	private static XSSFCellStyle titleStyle = null;//2007格式

	// 行信息内容样式
//	private static HSSFCellStyle contentStyle = null;
	private static XSSFCellStyle contentStyle = null;//2007格式

	/** 样式初始化 */
	public static void setExcelStyle(XSSFWorkbook workBook) {
		// 设置列标题字体，样式
		titleFont = workBook.createFont();
		titleFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		// 标题列样式
		titleStyle = workBook.createCellStyle();
		titleStyle.setBorderTop(XSSFCellStyle.BORDER_THIN); // 设置边框
		titleStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		titleStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		titleStyle.setFont(titleFont);
		// 内容列样式
		contentStyle = workBook.createCellStyle();
		contentStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		contentStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
		contentStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		contentStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
	}
	
	/***************************************************************************
	 * @param fileName
	 *            EXCEL文件名称
	 * @param listTitle
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public final static boolean exportExcel(HttpServletResponse response,
			String fileName, String[] title, String[] head1, String[] head2,
			List<String[]> strings) {
		try {
			// 服务端备份
			SystemParamMapper systemParamMapper = SpringContextHolder.getBean("systemParamMapper");
			SystemParam systemParam = systemParamMapper.findByCode("upload_local_path");
			String path = systemParam.getVcParamValue() == null ? "/": systemParam.getVcParamValue();
			File file = new File(path + "excel/" + DateUtil.getDate(new Date())+ "/" + fileName);
			if (file.exists()) {
				file.delete();
			}
			File dir = new File(file.getParent());
			if (!dir.exists())
				dir.mkdirs();
			OutputStream fos=null;
			if (file.createNewFile()) {
				fos = new FileOutputStream(file);
//				createExcel(fos, title, head1, head2, strings, fileName);
			}
			// 下载到本地
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename="+ new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			return createExcel(os, title, head1, head2, strings, fileName,fos);
		} catch (IOException e) {
			return false;
		}

	}

	public final static boolean createExcel(OutputStream os, String[] title,
			String[] head1, String[] head2, List<String[]> strings,
			String filename,OutputStream fos) {
		try {
//			HSSFWorkbook wb = new HSSFWorkbook();// 创建新HSSFWorkbook对象
			XSSFWorkbook wb = new XSSFWorkbook();//2007格式

			setExcelStyle(wb);// 执行样式初始化

//			HSSFSheet sheet = wb.createSheet(filename);// 创建新的sheet对象
		    XSSFSheet sheet = wb.createSheet(filename);//2007格式
			
			/** ***************以下是EXCEL第一行列标题********************* */
//			HSSFRow h1 = sheet.createRow((short) 0);// 创建第一行
			XSSFRow h1 = sheet.createRow((short) 0);//2007格式

			h1.setHeight((short)300);//设置行高,设置太小可能被隐藏看不到
			h1.setHeightInPoints(20);// 20像素
			
			for (int k = 0; k < head1.length; k++) {
//				HSSFCell cell = h1.createCell((short) k); // 新建一个单元格
				XSSFCell cell = h1.createCell((short) k); //2007格式

//				cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 中文字符集转换
				cell.setCellStyle(titleStyle);// 设置标题样式
				// cell.setCellValue(new HSSFRichTextString(titleStrs[k])); //
				// 为单元格赋值
				// cell.setCellValue(wb.getCreationHelper().createRichTextString(""));
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(head1[k]);
				sheet.setColumnWidth((short) k, (short) 5000);// 设置列宽
			}
			
			/** ***************以下是EXCEL第二行列内容********************* */
//			HSSFRow h2 = sheet.createRow((short) 1);// 创建第一行
			XSSFRow h2 = sheet.createRow((short) 1);//2007格式

			h2.setHeight((short)300);//设置行高,设置太小可能被隐藏看不到
			h2.setHeightInPoints(20);// 20像素

			// 写标题
			for (int k = 0; k < head1.length; k++) {
//				HSSFCell cell = h2.createCell((short) k); // 新建一个单元格
				XSSFCell cell = h2.createCell((short) k); //2007格式

				// cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 中文字符集转换
				cell.setCellStyle(titleStyle);// 设置标题样式
				// cell.setCellValue(new HSSFRichTextString(titleStrs[k])); //
				// 为单元格赋值
				// cell.setCellValue(wb.getCreationHelper().createRichTextString(""));
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(head2[k]);
				sheet.setColumnWidth((short) k, (short) 5000);// 设置列宽
			}

			/** ***************以下是EXCEL第三行列标题********************* */
//			HSSFRow titleRow = sheet.createRow((short) 2);// 创建第一行
			XSSFRow titleRow = sheet.createRow((short) 2);//2007格式

			titleRow.setHeight((short)300);//设置行高,设置太小可能被隐藏看不到
			titleRow.setHeightInPoints(20);// 20像素
			int titleCount = title.length;// 列数
			// 写标题
			for (int k = 0; k < titleCount; k++) {
//				HSSFCell cell = titleRow.createCell((short) k); // 新建一个单元格
				XSSFCell cell = titleRow.createCell((short) k); //2007格式

				// cell.setEncoding(HSSFCell.ENCODING_UTF_16); // 中文字符集转换
				cell.setCellStyle(titleStyle);// 设置标题样式
				// cell.setCellValue(new HSSFRichTextString(titleStrs[k])); //
				// 为单元格赋值
				// cell.setCellValue(wb.getCreationHelper().createRichTextString(""));
				cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(title[k]);
				sheet.setColumnWidth((short) k, (short) 5000);// 设置列宽
			}

			/** ***************以下是EXCEL正文数据********************* */
			int contentCount = strings.size();// 总的记录数
			// 写内容
			for (int i = 0; i < contentCount; i++) {
				String[] contents = strings.get(i);
//				HSSFRow row = sheet.createRow(i + 3); // 新建一行
				XSSFRow row = sheet.createRow(i + 3); // //2007格式

				for (int j = 0; j < titleCount; j++) {
//					HSSFCell cell = row.createCell((short) j); // 新建一个单元格
					XSSFCell cell = row.createCell((short) j); // //2007格式

					cell.setCellStyle(contentStyle);// 设置内容样式
					if (contents[j] == null || contents[j].equals("null")) {
						contents[j] = "";
					} else {
						cell.setCellValue(new XSSFRichTextString(contents[j]));
					}
				}
			}
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			wb.write(os);
			os.flush();
			if(fos!=null){
				wb.write(fos);
				fos.flush();
			}
			/** *********关闭文件************* */
			os.close();
			if(fos!=null){
				fos.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
