package com.tzg.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.entitys.leopard.system.SystemParamMapper;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcel {
	
	
	/***************************************************************************
	 * @param fileName
	 *            EXCEL文件名称
	 * @param listTitle
	 *            EXCEL文件第一行列标题集合
	 * @param listContent
	 *            EXCEL文件正文数据集合
	 * @return
	 */
	public final static boolean exportExcel(HttpServletResponse response, String fileName, String[] title,String[] head1,String[] head2, List<String[]> strings) {
		try {
			//服务端备份
			SystemParamMapper systemParamMapper = SpringContextHolder.getBean("systemParamMapper");
        	SystemParam systemParam = systemParamMapper.findByCode("upload_local_path");
        	String path = systemParam.getVcParamValue()==null?"/":systemParam.getVcParamValue();
			File file=new File(path+"excel/"+DateUtil.getDate(new Date())+"/"+fileName);
			if (file.exists()){
				file.delete();
			}
			File dir = new File(file.getParent());
			if (!dir.exists())
				dir.mkdirs();
			if (file.createNewFile()) {
				OutputStream fos = new FileOutputStream(file);
				createExcel(fos, title, head1, head2, strings);
			}
			//下载到本地
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
			response.setContentType("application/msexcel");// 定义输出类型
			return createExcel(os, title, head1, head2, strings);
			
		} catch (IOException e) {
			return false;
		}
		
	}
	
	public final static boolean createExcel(OutputStream os, String[] title,String[] head1,String[] head2, List<String[]> strings){
		try {
			
			/** **********创建工作簿************ */
			WritableWorkbook workbook = Workbook.createWorkbook(os);
			/** **********创建工作表************ */
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			/** **********设置纵横打印（默认为纵打）、打印纸***************** */
			jxl.SheetSettings sheetset = sheet.getSettings();
			sheetset.setProtected(false);

			/** ************设置单元格字体************** */
			WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
			WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD);

			/** ************以下设置三种单元格样式，灵活备用************ */
			// 用于标题居中
			WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
			wcf_center.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); // 线条
			wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_center.setWrap(false); // 文字是否换行

			// 用于正文居左
			WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
			wcf_left.setBorder(jxl.format.Border.NONE,jxl.format.BorderLineStyle.THIN); // 线条
			wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
			wcf_left.setAlignment(Alignment.CENTRE); // 文字水平对齐
			wcf_left.setWrap(false); // 文字是否换行

			/** ***************以下是EXCEL第一行列标题********************* */
			sheet.setRowView(0, 400, false); //行高
			for(int i = 0; i < head1.length; i++){
				sheet.addCell(new Label(i, 0, head1[i], wcf_center));
			}
			/** ***************以下是EXCEL第二行列内容********************* */
			sheet.setRowView(1, 400, false); //行高
			for(int i = 0; i < head2.length; i++){
				sheet.addCell(new Label(i, 1, head2[i], wcf_left));
			}
			/** ***************以下是EXCEL第三行列标题********************* */
			sheet.setRowView(2, 400, false); //行高
			for (int i = 0; i < title.length; i++) {
				sheet.addCell(new Label(i, 2, title[i], wcf_center));
				sheet.setColumnView(i, title[i].length()*3); //列宽
			}
			/** ***************以下是EXCEL正文数据********************* */
			int i = 3;
			for (String[] string : strings) {
				int j = 0;
				for(String s : string){
					sheet.addCell(new Label(j, i, s, wcf_left));
					sheet.setRowView(i, 400, false); //行高
					j++;
				}
				i++;
			}
			//sheet.addCell(new Label(0, i, DateUtil.getDate(new Date()), wcf_left));
			
			/** **********将以上缓存中的内容写到EXCEL文件中******** */
			workbook.write();
			/** *********关闭文件************* */
			workbook.close();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}