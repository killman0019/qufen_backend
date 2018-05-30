package com.tzg.wap.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.tzg.entitys.kff.discuss.DiscussRequest;
import com.tzg.rest.vo.BaseResponseEntity;

/**
 * 路径存放思路 :将二维码生成放在指定的二维码路径下 ;将原始海报和二维码生成的图片存放在海报的路径下:在海报路径下添加文字在原来的基础上生成新的海报图片
 * 
 * @author Administrator
 *
 */
public class Create2Code {
	/**
	 * 二维码生成的存放路径
	 */
	private static String code2sysPath = "D:\\opt\\file\\upload\\2code\\";
	/**
	 * 邀请链接的url
	 */
	private static final String contentself = "http://192.168.10.196:5000/user/registerSmp?invaUIH=";
	/**
	 * 最终海报的存放路径
	 */
	private static final String posterSysPath = "D:\\opt\\file\\upload\\poster\\";
	/**
	 * 原始海报的存放路径
	 */
	private static String initPosterSysPath = "D:\\opt\\file\\upload\\poster\\init\\initpic.png";

	private static String text = "";

	public static void main(String[] args) {
		createPoster(null);
	}

	public static String createPoster(Integer userId) {

		String str = HexUtil.userIdTo2code(userId);

		// 调用create2CodeImg 方法生成二维码
		// 调用overlapImage 方法将两个图片合成一个
		// 调用createCharAtImg 方法 向图片上添加文字
		// 返回图片的相对于服务器的绝对地址
		String posterSysPathlast = posterSysPath + str + ".png";
		String contentselfid = contentself + str;
		String code2Path = createNameAndPath(str);// 二维码路径
		create2CodeImg(code2Path, contentselfid);// 在制定位置生成二维码
		overlapImage(initPosterSysPath, code2Path, posterSysPathlast);
		createCharAtImg(text, posterSysPathlast);
		return "upload\\poster\\" + str + "." + "png";
	}

	/**
	 * str :二维码字母
	 * 
	 * @param str
	 * @return
	 */
	public static void create2CodeImg(String str, String contentselfid) {
		try {
			// String content = "http://www.baidu.com";

			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			int margin = 1;// 设置白边框宽
			Map hints = new HashMap<EncodeHintType, Object>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			hints.put(EncodeHintType.MARGIN, margin);
			BitMatrix bitMatrix = multiFormatWriter.encode(contentselfid, BarcodeFormat.QR_CODE, 128, 128, hints);

			File file = new File(str);
			MatrixToImageWriter.writeToFile(bitMatrix, "png", file);
			System.out.println("========");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 小图片 大图片
	 * 
	 * @param bigPath
	 * @param smallPath
	 * @param outFile
	 */
	public static final void overlapImage(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			// int x = (big.getWidth() - small.getWidth()) / 2;
			// int y = (big.getHeight() - small.getHeight()) / 2;
			// int x = 128 * big.getWidth() / 345 + 4 - 22 + 10 - 3;
			// int y = (164 * big.getHeight() / 183) - 90 + 3 - 25 + 3 + 2;
			int x = 550;// 横
			int y = 1125;// 竖
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
			System.out.println("===================");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 将str 文字放在图片上 ¨ Font.PLAIN（普通）
	 * 
	 * 　　¨ Font.BOLD（加粗）
	 * 
	 * 　　¨ Font.ITALIC（斜体）
	 * 
	 * 　　¨ Font.BOLD+ Font.ITALIC（粗斜体）
	 * 
	 * @param str
	 */
	public static void createCharAtImg(String str, String lastPath) {
		try {
			// File file = new File("C:\\xcjz9.png");
			File file = new File(lastPath);
			BufferedImage image = ImageIO.read(file);
			Graphics2D g2 = image.createGraphics();
			// g2.setColor(Color.red);
			Font font = new Font("宋体", Font.BOLD, 28);// 添加字体的属性设置
			g2.setColor(Color.WHITE);
			g2.setFont(font);
			// g2.drawString(str, image.getWidth() - 260, image.getHeight() - 170);
			g2.drawString(str, 72, 1147);
			g2.dispose();
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建图片名称 和路径
	 * 
	 * @return
	 */
	public static String createNameAndPath(String code2) {
		String path = code2sysPath + code2 + ".png";
		return path;
	}

}
