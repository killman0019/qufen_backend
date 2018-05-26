package com.tzg.service.emailsender;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzg.core.utils.EMailAuthenticatorUtil;
import com.tzg.entitys.emailsender.EMailSender;
import com.tzg.entitys.leopard.system.SystemParam;
import com.tzg.service.SystemParam.SystemParamService;

/**
 * 
 * @Description：邮件发送Service
 * @author
 * @Date 2015-1-13
 * 
 */
@Service
@Transactional
public class EMailSenderService {

	@Autowired
	private SystemParamService systemParamService;

	private static final String SERVER_HOST_CODE = "mail_send_server_host";

	private static final String SERVER_PORT_CODE = "mail_send_server_port";

	private static final String SEND_LOGIN_ACCOUNT = "mail_send_user_login_account";

	private static final String SEND_LOGIN_PWD = "mail_send_user_login_pwd";

	/**
	 * 发送邮件
	 * 
	 * @param emailAddress
	 *            收件人地址
	 * @param vcTitle
	 *            邮件标题
	 * @param vcContent
	 *            邮件内容
	 * @throws Exception
	 */
	public void sendTextMail(String emailAddress, String vcTitle,
			String vcContent) throws Exception {
		SystemParam serverHostInfo = systemParamService
				.findByCode(SERVER_HOST_CODE);
		if (serverHostInfo == null) {
			throw new Exception("系统参数" + SERVER_HOST_CODE + "未设置，请先设置！");
		}
		SystemParam serverPortInfo = systemParamService
				.findByCode(SERVER_PORT_CODE);
		if (serverPortInfo == null) {
			throw new Exception("系统参数" + SERVER_PORT_CODE + "未设置，请先设置！");
		}
		SystemParam loginAccountInfo = systemParamService
				.findByCode(SEND_LOGIN_ACCOUNT);
		if (loginAccountInfo == null) {
			throw new Exception("系统参数" + SEND_LOGIN_ACCOUNT + "未设置，请先设置！");
		}
		SystemParam loginPwdInfo = systemParamService
				.findByCode(SEND_LOGIN_PWD);
		if (loginPwdInfo == null) {
			throw new Exception("系统参数" + SEND_LOGIN_PWD + "未设置，请先设置！");
		}
		EMailSender mailInfo = new EMailSender();
		mailInfo.setToAddress(emailAddress);
		mailInfo.setSubject(vcTitle);
		mailInfo.setContent(vcContent);
		mailInfo.setMailServerHost(serverHostInfo.getVcParamValue()); // 发件服务器地址
		mailInfo.setMailServerPort(serverPortInfo.getVcParamValue()); // 发件服务器端口
		mailInfo.setUserName(loginAccountInfo.getVcParamValue());
		mailInfo.setPassword(loginPwdInfo.getVcParamValue());// 您的邮箱密码
		mailInfo.setFromAddress(loginAccountInfo.getVcParamValue());
		mailInfo.setValidate(true);
		// 判断是否需要身份认证
		EMailAuthenticatorUtil authenticator = null;
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器
			authenticator = new EMailAuthenticatorUtil(mailInfo.getUserName(),
					mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session
				.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			// 创建邮件发送者地址
			Address from = new InternetAddress(mailInfo.getFromAddress());
			// 设置邮件消息的发送者
			mailMessage.setFrom(from);
			// 创建邮件的接收者地址，并设置到邮件消息中
			Address to = new InternetAddress(mailInfo.getToAddress());
			mailMessage.setRecipient(Message.RecipientType.TO, to);
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// 设置邮件消息的主要内容
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象    
			Multipart mainPart = new MimeMultipart();    
			// 创建一个包含HTML内容的MimeBodyPart    
			BodyPart html = new MimeBodyPart();     
			// 设置HTML内容     
			html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");     
			mainPart.addBodyPart(html);    
			// 将MiniMultipart对象设置为邮件内容    
			mailMessage.setContent(mainPart); 
			// 发送邮件
			Transport.send(mailMessage);
		} catch (Exception e) {
			throw e;
		}
	}

}
