/**
 * 
 */
package com.tzg.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang.RandomStringUtils;


/**
 * 验证码的产生类
 * @author liuronghuan
 *
 * 2014年6月26日
 */
public class ValidateUtil {
    private static final int  VALIDATENUM = 4;

    /**
     * 
     */
    public ValidateUtil() {
        // TODO Auto-generated constructor stub
    }
    
    public static String createValidateCode() {
        return RandomStringUtils.random(VALIDATENUM, true, true);
    }
    
    private static Color getRandColor(int fc, int bc) { // 给定范围获得随机颜色
        Random random = new Random();
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
    
    public static void createValidateImag(String validateCode, OutputStream out) throws IOException {
        int width = 90;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设定字体
        Font mFont = new Font("Times New Roman", Font.ITALIC, 24);// 设置字体
        g.setFont(mFont);

        g.setColor(getRandColor(160, 200));
        // 生成随机类
        Random random = new Random();
        for (int i = 0; i < 155; i++) {
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            int x3 = random.nextInt(12);
            int y3 = random.nextInt(12);
            g.drawLine(x2, y2, x2 + x3, y2 + y3);
        }
        // 将认证码显示到图象中
        g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));

        g.drawString(validateCode, 15, 24);

        // 图象生效
        g.dispose();
        // 输出图象到页面
        ImageIO.write((BufferedImage) image, "JPEG", out);

    }

}
