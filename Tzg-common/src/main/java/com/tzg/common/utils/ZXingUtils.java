package com.tzg.common.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 *  
* @ClassName: ZXingUtils
* @Description: ZXing工具类
* 目前仅支持二维码的生成和解析,生成二维码时支持添加logo头像
* @author chy@tzg.cn
* @date 2015-6-10 下午7:04:28
*
 */
public class ZXingUtils { 
    private ZXingUtils(){} 
       
    /**
     * 生成二维码
     * @param content   二维码内容
     * @param charset   编码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
     * @param outputStream 二维码图片输出流
     * @param width     生成的二维码图片宽度
     * @param height    生成的二维码图片高度
     * @param logoPath  logo头像存放路径(含文件名,若不加logo则传null即可)
     * @return 生成二维码结果(true or false)
     */ 
    public static boolean encodeQRCodeImage(String content, String charset, String format,ByteArrayOutputStream outputStream, int width, int height, String logoPath) {
    	ByteArrayOutputStream baos = null;
    	 if(null == logoPath){ 
    		 baos = outputStream;
    	 }else{
    		 baos = new ByteArrayOutputStream();
    	 }
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>(); 
        //hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); 
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); 
        BitMatrix bitMatrix = null; 
        try { 
            bitMatrix = new MultiFormatWriter().encode(new String(content.getBytes(charset==null?"UTF-8":charset), "ISO-8859-1"), BarcodeFormat.QR_CODE, width, height, hints); 
        } catch (Exception e) { 
            System.out.println("编码待生成二维码图片的文本时发生异常,堆栈轨迹如下"); 
            e.printStackTrace(); 
            return false; 
        } 
        MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF); 
        try { 
            MatrixToImageWriter.writeToStream(bitMatrix, format, baos, config);
        } catch (IOException e) { 
            System.out.println("生成二维码图片时遇到异常,堆栈轨迹如下"); 
            e.printStackTrace(); 
            return false; 
        } 
        if(null == logoPath){ 
            return true; 
        }else{
            try { 
                overlapImage( new ByteArrayInputStream(baos.toByteArray())  ,outputStream,format, logoPath); 
                return true; 
            } catch (IOException e) { 
                System.out.println("为二维码图片添加logo头像[" + logoPath + "]时遇到异常,堆栈轨迹如下"); 
                e.printStackTrace(); 
                return false; 
            } 
        } 
    } 
       
       
    /**
     * 解析二维码
     * @param input 二维码图片文件流(含文件名)
     * @param charset   解码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
     * @return 解析成功后返回二维码文本,否则返回空字符串
     */ 
    public static String decodeQRCodeImage(InputStream  input, String charset) { 
        BufferedImage image = null; 
        try { 
            image = ImageIO.read(input); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            return ""; 
        } 
        if(null == image){ 
            System.out.println("Could not decode QRCodeImage"); 
            return ""; 
        } 
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image))); 
        Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>(); 
        hints.put(DecodeHintType.CHARACTER_SET, charset==null ? "UTF-8" : charset); 
        Result result = null; 
        try { 
            result = new MultiFormatReader().decode(bitmap, hints); 
            return result.getText(); 
        } catch (NotFoundException e) { 
            System.out.println("二维码图片解析失败,堆栈轨迹如下"); 
            e.printStackTrace(); 
            return ""; 
        } 
    }
    
    public static String decodeQRCodeImage(String  filePath, String charset) { 
    	String r = "";
    	try {
			r =  decodeQRCodeImage(new FileInputStream(filePath),charset);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	return r;
    }
    
    /**
     * 为二维码图片增加logo头像
     * @see 其原理类似于图片加水印
     * @param input 二维码图片文件流
     * @param output 加logo后的二维码图片输出流
     * @param format 二维码图片格式
     * @param logoPath  logo头像存放路径(含文件名)
     */ 
    private static void overlapImage(InputStream  input,OutputStream output,String format, String logoPath) throws IOException { 
        BufferedImage image = ImageIO.read(input); 
        int logoWidth = image.getWidth()/5;  
        int logoHeight = image.getHeight()/5;
        int logoX = (image.getWidth()-logoWidth)/2; 
        int logoY = (image.getHeight()-logoHeight)/2;
        Graphics2D graphics = image.createGraphics(); 
        graphics.drawImage(ImageIO.read(new File(logoPath)), logoX, logoY, logoWidth, logoHeight, null); 
        graphics.dispose(); 
        ImageIO.write(image, format, output); 
    } 
    
    public static void main(String[] args) { 
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        encodeQRCodeImage("tzg618活动：chy@tzg.cn", null,"jpg", baos, 300, 300, "d:\favicon.jpg"); 
        FileUtils.getFileFromBytes(baos.toByteArray(),"d:/test.jpg");
        System.out.println(decodeQRCodeImage("D:/test.jpg", null)); 
    }
}