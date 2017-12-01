/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.test;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 *
 * @author TUNGLV
 */
public class SendMailUseJangoSmtp {
    public static void main(String[] args) {
        //Declare recipient's & sender's e-mail id.
        
        //mail nhan
      String destmailid = "tunglv9x@gmail.com";
      //mail gui
      String sendrmailid = "coso9.mshoatoeic@gmail.com";	  
     //Mention user name and password as per your configuration
      final String uname = "congiola1991";
      final String pwd = "Lvt@100191";
      //We are using relay.jangosmtp.net for sending emails
      //String smtphost = "relay.jangosmtp.net";
       String smtphost = "relay.jangosmtp.net";
      
     //Set properties and their values
      Properties propvls = new Properties();
      propvls.put("mail.smtp.auth", "true");
      propvls.put("mail.smtp.starttls.enable", "true");
      propvls.put("mail.smtp.host", smtphost);
      propvls.put("mail.smtp.port", "25");
      //Create a Session object & authenticate uid and pwd
      Session sessionobj = null;
      if(uname != null && uname != ""){
                 sessionobj = Session.getInstance(propvls,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication(uname, pwd);
	   }
         });
      }else{
          sessionobj = Session.getInstance(propvls, null);
      }


      try {
	   //Create MimeMessage object & set values
	   Message messageobj = new MimeMessage(sessionobj);
	   messageobj.setFrom(new InternetAddress(sendrmailid));
	   messageobj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(destmailid));
	   messageobj.setSubject("This is test Subject");
	   messageobj.setText("Checking sending emails by using JavaMail APIs");
	  //Now send the message
	   Transport.send(messageobj);
	   System.out.println("Your email sent successfully....");
      } catch (MessagingException exp) {
         throw new RuntimeException(exp);
      }
    }
}
