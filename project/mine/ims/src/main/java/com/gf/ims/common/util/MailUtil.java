package com.gf.ims.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.lang.StringUtils;
import org.springmodules.validation.bean.conf.loader.annotation.handler.Email;

public class MailUtil {
	private static Properties pp = null;
	/*
	 * static{ init(); }
	 */

	public static Properties init() {
		if (pp == null) {
			pp = new Properties();
			try {
				pp.load(MailUtil.class.getResourceAsStream("/com/gf/ims/resources/mail.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return pp;
		}
		return pp;
	}

	public static String getProperty(String key) {
		if (pp.containsKey(key)) {
			return String.valueOf(pp.get(key));
		}
		return null;
	}

	/**
	 * 
	 * @param isAuth
	 *            服务器是否需要身份认证
	 * @param subject
	 *            主题
	 * @param title
	 *            标题
	 * @param fromAddress
	 *            寄件人地址
	 * @param fromName
	 *            寄件人名称
	 * @param pass
	 *            寄件人密码
	 * @param toAddress
	 *            收件人地址 逗号隔开（发送）
	 * @param ccAddress
	 *            收件人 逗号隔开（抄送）
	 * @param content
	 *            邮件内容
	 * @param fileMap
	 *            附件 以文件路径，文件名称的形式
	 */
	public static boolean sendEmailWithAttachment(boolean isAuth, String subject, String title, String fromAddress,
			String fromName, String pass, String toAddress, String ccAddress, String content,
			Map<String, String> fileMap, Map<InputStream, String> inputStreamMap) throws Exception {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.exmail.qq.com");
		Session session = null;
		if (isAuth) {
			prop.put("mail.smtp.auth", "true");
			session = Session.getDefaultInstance(prop, new MyAuthenricator(fromAddress, pass));
		} else {
			prop.put("mail.smtp.auth", "false");
			session = Session.getDefaultInstance(prop, null);
		}
		session.setDebug(true);
		Message message = new MimeMessage(session);
		boolean flag = false;
		try {
			message.setSubject(subject);// 设置邮件主题
			message.setHeader("Header:", title); // 设置邮件标题
			message.setSentDate(new Date()); // 设置发送时间

			Address addressFrom = new InternetAddress(fromAddress, fromName); // 设置发信人地址
																				// 、发信人名称
			message.setFrom(addressFrom);
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(content, "text/html;charset=utf-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			MimeBodyPart mimeBodyPart;
			if (fileMap != null&&fileMap.size()>0) {
				 //多个附件  
	                for (String key : fileMap.keySet()) {  
	                	String fileSource="";  
	                	String fileName="";  
	                    fileSource = key;  
	                    fileName = fileMap.get(key);  
	                    mimeBodyPart = new MimeBodyPart();  
	                    DataSource source = new FileDataSource(fileSource);  
	                    mimeBodyPart.setDataHandler(new DataHandler(source));  
	                    mimeBodyPart.setFileName(MimeUtility.encodeText(fileName));  
	                    multipart.addBodyPart(mimeBodyPart);// Put parts in  
	                }  
			}
/*			if (inputStreamMap!=null&&inputStreamMap.size()>0) {
                for (InputStream key : inputStreamMap.keySet()) {  
                	InputStream fileSource=null;  
                	String fileName="";
                	String type="";
                    fileSource = key;  
                    String value = inputStreamMap.get(key);
                    String[] arr = value.split("\\|");
                    fileName=arr[0];
                    type=arr[1];
                    DataSource source =new ByteArrayDataSource(fileSource, type);
                    mimeBodyPart = new MimeBodyPart();  
                    mimeBodyPart.setDataHandler(new DataHandler(source));  
                    mimeBodyPart.setFileName(MimeUtility.encodeText(fileName));  
                    multipart.addBodyPart(mimeBodyPart);// Put parts in  
                }  
			}*/
			message.setContent(multipart);
			if (StringUtils.isNotBlank(toAddress)) {
				// 设置多个收件人地址
				InternetAddress[] internetAddressTo = new InternetAddress().parse(toAddress);
				message.setRecipients(Message.RecipientType.TO, internetAddressTo);
			}
			if (StringUtils.isNotBlank(ccAddress)) {
				// 设置多个抄送地址
				InternetAddress[] internetAddressCC = new InternetAddress().parse(ccAddress);
				message.setRecipients(Message.RecipientType.CC, internetAddressCC);
			}

			message.saveChanges();
			Transport.send(message);
			flag = true;
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	static class MyAuthenricator extends Authenticator {
		String user = null;
		String pass = "";

		public MyAuthenricator(String user, String pass) {
			this.user = user;
			this.pass = pass;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, pass);
		}

	}

	public static void main(String[] args) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("C:\\Users\\Administrator\\Desktop\\运营中心需求汇总表（高优先级）.xlsx", "文件名称.xlsx");
		sendEmailWithAttachment(true, "空腹的邮件", "哈哈哈标题", "xianggf@hori-gz.com", "空腹", "Gfxiang_tx10!",
				"1020009891@qq.com", "1020009891@qq.com", "哈哈哈", map, null);
	}

}
