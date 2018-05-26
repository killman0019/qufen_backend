package com.tzg.common.utils.rest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class Base64Util {

 /**
  * 将文件转成base64 字符串
  * @param path 文件路径
  * @return  * 
  * @throws Exception
  */

 public static String encodeBase64File(String path) throws Exception {
  File file = new File(path);
  FileInputStream inputFile = new FileInputStream(file);
  byte[] buffer = new byte[(int) file.length()];
  inputFile.read(buffer);
  inputFile.close();
  return new BASE64Encoder().encode(buffer);

 }
 /**
  * 将文件转成base64 字符串
  * @param path 文件路径
  * @return  *
  * @throws Exception
  */

 public static String encodeBase64FileYubo(String path) throws Exception {
  File file = new File(path);
  FileInputStream inputFile = new FileInputStream(file);
  byte[] buffer = new byte[(int) file.length()];
  inputFile.read(buffer);
  inputFile.close();
  return new Base64().encode(buffer);

 }
 /**
  * 将base64字符解码保存文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  */

 public static void decoderBase64File(String base64Code, String targetPath)
   throws Exception {
  byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
  FileOutputStream out = new FileOutputStream(targetPath);
  out.write(buffer);
  out.close();

 }



 /**
  * 将base64字符保存文本文件
  * @param base64Code
  * @param targetPath
  * @throws Exception
  */

 public static void toFile(String base64Code, String targetPath)
   throws Exception {

  byte[] buffer = base64Code.getBytes();
  FileOutputStream out = new FileOutputStream(targetPath);
  out.write(buffer);
  out.close();
 }

 /*
 public static String bitmapToBase64(Bitmap bitmap) {

  String result = null;
  ByteArrayOutputStream baos = null;
  try {
   if (bitmap != null) {
    baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

    baos.flush();
    //baos.close();

    byte[] bitmapBytes = baos.toByteArray();
    // result = new BASE64Encoder().encode(bitmapBytes);
    result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
   }
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   try {
    if (baos != null) {
     baos.flush();
     baos.close();
    }
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
  return result;
 }
*/

 /**
  * base64字符串转文件
  * @param base64
  * @return
  */
 public static File base64ToFileYubo(String base64,String targetPath) {
  File file = null;
  FileOutputStream out = null;
  try {
   // 解码，然后将字节转换为文件
   file = new File(targetPath);

   if (!file.exists())
    file.createNewFile();
   byte[] bytes = Base64.decode(base64);// 将字符串转换为byte数组
   ByteArrayInputStream in = new ByteArrayInputStream(bytes);
   byte[] buffer = new byte[1024];
   out = new FileOutputStream(file);
   int bytesum = 0;
   int byteread = 0;
   while ((byteread = in.read(buffer)) != -1) {
    bytesum += byteread;
    out.write(buffer, 0, byteread); // 文件写操作
   }
  } catch (IOException ioe) {
   ioe.printStackTrace();
  } finally {
   try {
    if (out!= null) {
     out.close();
    }
   } catch (IOException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  }
  return file;
 }

 public static void main(String[] args) {
  try {
   String base64Code = encodeBase64FileYubo("c:/a.jpg");
   System.out.println(base64Code);
   decoderBase64File(base64Code, "c:/b.jpg");
   base64ToFileYubo(base64Code,"c:/aa.jpg");
   toFile(base64Code, "c:/c.jpg");
  } catch (Exception e) {
   e.printStackTrace();

  }

 }

}
