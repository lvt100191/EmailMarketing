/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author TUNGLV
 */
public class TestSendMail {
    public static void main(String[] args) {
        try {
			final String username = "";
			final String password = "";

			Properties props = new Properties();
                        //danh sach host
//                        mail.vos.vasc.com.vn
//mail.truelife.vn
//mail.vnedu.vn
//mail.vos.vasc.com.vn
//mail.truelife.vn
//mail.vnedu.vn
//mail.vnedu.vn
//mail.vos.vasc.com.vn
//mail.vos.vasc.com.vn
			props.put("mail.smtp.host", "mail.vnedu.vn");
			props.put("mail.smtp.port", "25");

			Session session = null;
			if (username != null && username != "") {
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
			} 
			else {
				session = Session.getInstance(props, null);
			}

			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("Content-Type", "text/plain; charset=UTF-8");

			InternetAddress senderAddress = new InternetAddress("noreply@truelife.vn");
			senderAddress.setPersonal("Alo KidEnglish", "UTF-8");
			senderAddress.validate();
			msg.setFrom(senderAddress);
//			if (mailInQueue.getReceiver().indexOf(",") > 0) {
//				String[] arrReceiver = mailInQueue.getReceiver().split(",");
//				InternetAddress[] receiverAddress = new InternetAddress[arrReceiver.length];
//				for (int i = 0; i < arrReceiver.length; i++) {
//					if (arrReceiver[i].trim().length() > 1) {
//						receiverAddress[i] = new InternetAddress(arrReceiver[i]);
//						receiverAddress[i].validate();
//					}
//				}
//				msg.setRecipients(Message.RecipientType.TO, receiverAddress);
//			} 
//			else {
				InternetAddress receiverAddress = new InternetAddress("tunglv9x@gmail.com");
				receiverAddress.validate();
				msg.setRecipient(Message.RecipientType.TO, receiverAddress);
//			}

			msg.setSubject("test mail", "UTF-8");
			msg.setSentDate(new Date());
			MimeMultipart multipart = new MimeMultipart("related");

			// first part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent("<p style=\"text-align: center;\">hello</p>", "text/html; charset=UTF-8");

			// add it
			multipart.addBodyPart(messageBodyPart);
			//msg.setText("<p style=\"text-align: center;\">hello</p>","utf-8","html");
                     msg.setContent(multipart);
                           Transport.send(msg); 
                        
			
                        System.out.println("gui mail thanh cong");
		} 
		catch (AddressException ex) {
//			ex.printStackTrace();
//			throw ex;
			System.out.println(ex.getMessage());
		} 
		catch (SendFailedException ex) {
//			ex.printStackTrace();
//			throw ex;
			System.out.println(ex.getMessage());
		}
		catch (Exception ex) {
//			ex.printStackTrace();
//			throw ex;
			System.out.println(ex.getMessage());
		}
    }
}
