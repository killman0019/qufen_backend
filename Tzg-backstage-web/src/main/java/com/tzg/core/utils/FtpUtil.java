package com.tzg.core.utils;

import java.io.ByteArrayInputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 * ftp
 * @author wuws
 *
 */
public class FtpUtil {

	private  FTPClient ftp;      
    /**  
     *   
     * @param path 上传到ftp服务器哪个路径下     
     * @param addr 地址  
     * @param port 端口号  
     * @param username 用户名  
     * @param password 密码  
     * @return  
     * @throws Exception  
     */    
    public  boolean connect(String path,String addr,int port,String username,String password) throws Exception {      
        boolean result = false;      
        ftp = new FTPClient();      
        int reply;      
        ftp.connect(addr,port);      
        ftp.login(username,password);      
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);      
        reply = ftp.getReplyCode();      
        if (!FTPReply.isPositiveCompletion(reply)) {      
            ftp.disconnect();      
            return result;      
        }      
        ftp.changeWorkingDirectory(path);      
        result = true;      
        return result;      
    }      
    /**  
     *   
     * @param file 上传的文件或文件夹  
     * @throws Exception  
     */    
    public void upload(String path,String fileName,byte[] content) throws Exception{           
        ftp.makeDirectory(path);                
        ftp.changeWorkingDirectory(path);      
        ByteArrayInputStream is = new ByteArrayInputStream(content); 
        ftp.setBufferSize(1024);
        ftp.storeFile(fileName, is);      
        is.close();     
    }
    
}
