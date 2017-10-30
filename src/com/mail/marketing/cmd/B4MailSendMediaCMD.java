/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.mail.EmailAction;
import com.mail.marketing.test.GUITestMailSendABC;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.BodyPart;
import javax.mail.Message;
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
//su dung mot mail gui de gui thong bao den tat ca mail trong bang tbl_mail co trang thai =1
//            String host ="mail.vnedu.vn";
//            String mailSend1 = "noreply@truelife.vn";
//truoc khi clean build sua
//sendName, title, content
public class B4MailSendMediaCMD {

    //tham so dau vao
    // TODO add your handling code here:
    //trang thai mail lay ra de gui truong status trong bang tbl_mail
    static String sttMailSend = "1";
    //update trang thai da gui mail
    static String sttMailSent = "2";
    //server mail
    static String host = "mail.vnedu.vn";
    //mail gửi, chú ý phải là mail này thì server mail mới cho phép gửi
    static String mailSend1 = "noreply@truelife.vn";
    //ten nguoi gui, ten nay se hiển thị trên người gửi đến, 
    //nếu không cấu hình sẽ hiển thị địa chỉ mail gửi
    static String sendName = "Tiếng Anh Cho Người Đi Làm";
    //tieu de mail
    static String title = "130 CÂU HỎI + 500 CÂU TRẢ LỜI PHỎNG VẤN CHUẨN MỰC  SONG NGỮ ANH - VIỆT";
    //noi dung mail
    static String content = "<p>Gửi tất cả c&aacute;c bạn link download t&agrave;i liệu nh&eacute;!</p>\n" +
"<p><span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/fdc/1/16/26a0.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">⚠️</span></span>&nbsp;DOWNLOAD&nbsp;<span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/fdc/1/16/26a0.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">⚠️</span></span>&nbsp;130 C&Acirc;U HỎI + 500 C&Acirc;U TRẢ LỜI PHỎNG VẤN CHUẨN MỰC&nbsp;<span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f51/1/16/1f449.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">👉</span></span>&nbsp;SONG NGỮ ANH - VIỆT</p>\n" +
"<p>Click v&agrave;o <span style=\"color: #ff0000;\"><strong><a style=\"color: #ff0000;\" href=\"http://bit.ly/2xpTLiE\">Đ&Acirc;Y</a></strong></span> để tải t&agrave;i liệu</p>\n" +
"<p>C&aacute;c bạn nhớ đăng k&yacute; k&ecirc;nh youtube: <span style=\"color: #ff0000;\"><strong><a style=\"color: #ff0000;\" href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\">Tiếng Anh Cho Người Việt</a></strong></span></p>\n" +
"<p>Like Fanpage:&nbsp;<strong><span style=\"color: #0000ff;\"><a style=\"color: #0000ff;\" href=\"https://www.facebook.com/englishforvn/\">Tiếng Anh Cho Người Việt</a></span></strong></p>\n" +
"<p>Để được ưu ti&ecirc;n gửi mail, nhận t&agrave;i liệu, c&aacute;c kh&oacute;a học miễn ph&iacute; d&agrave;nh cho mọi người. Cũng như ủng hộ ch&uacute;ng t&ocirc;i c&oacute; động lực sưu tập v&agrave; gửi&nbsp; đến tất cả mọi người.</p>\n" +
"<p>Admin xin ch&acirc;n trọng cảm ơn!</p>";

    public static void main(String[] args) {
        try {
            int countMailSentSuccess = 0;
            ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(1000));
            //test
//            ArrayList<Mail> lst = new ArrayList<>();
//            Mail mxx = new Mail();
//            mxx.setEmail("tunglv9x@gmail.com");
//            lst.add(mxx);
            //end test
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
            String dateCreate = sdf.format(d);
            String filename = "xxx-mail-to-" + dateCreate;
            for (Mail to : lst) {
                try {

                    //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                    if (checkMailBlock(to.getEmail().trim())) {
                        continue;
                    }

                    sendMail(host, sendName, mailSend1, to.getEmail().toLowerCase(), title, content);

                    countMailSentSuccess++;
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);

                    //update status mail nhan
                    to.setStatus(Integer.parseInt(sttMailSent));
                    MailDao.updateMail(to);
                    //lstMailSent = lstMailSent +  to.getEmail()+"\n";

                } catch (AddressException adEx) {
                    System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + adEx.getMessage());
                    //tim mail trong bang tbl_mail lay ra id cua ban ghi
                    Mail m = MailDao.getByEmail(to.getEmail());
                    //xoa ban ghi trong bang tbl_feed_mail theo id
                    if (m != null) {
                        FeedMailDao.deleteFeedMail(m.getId());
                        //xoa ban ghi trong bang tbl_mail
                        MailDao.deleteMail(m.getId());
                    }
                    //insert vao bang tbl_mail_blocked
                    MailBlock mb = initMailBlock(to.getEmail());
                    MailBlockDao.insert(mb);
                } catch (Exception e) {
                    System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + e.getMessage());

                } finally {

                }
            }

        } catch (Exception ex) {
            Logger.getLogger(GUITestMailSendABC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx----------------------------------------------");
            System.out.println("--------------CHUONG TRINH GUI MAIL noreply@truelife.vn KET THUC-----------------");
            System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx----------------------------------------------");
        }
    }

    //check dia chi mail co bi chan hay chua
    //false: mail chua ton tai
    //true: mail da ton tai
    private static boolean checkMailBlock(String mail) throws Exception {
        //ko insert mail đã tồn tại trong danh sách mail chặn tbl_mail_block
        MailBlock mailBlock = MailBlockDao.getByEmail(mail);
        if (mailBlock != null) {
            return true;
        }
        return false;
    }

    private static MailBlock initMailBlock(String mailLock) {
        MailBlock m = new MailBlock();
        m.setMailBlock(mailLock);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        m.setCreateDate(dateCreate);
        return m;
    }

    public static void sendMail(String host, String sendName, String sendMail, String mailReceiver, String title, String content) {
        try {
            final String username = "";
            final String password = "";

            Properties props = new Properties();
            //danh sach host
            //mail.truelife.vn
            //mail.vnedu.vn
            //mail.vos.vasc.com.vn

            props.put("mail.smtp.host", host);
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
            } else {
                session = Session.getInstance(props, null);
            }

            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", "text/plain; charset=UTF-8");

            InternetAddress senderAddress = new InternetAddress(sendMail);
            senderAddress.setPersonal(sendName, "UTF-8");
            senderAddress.validate();
            msg.setFrom(senderAddress);

            InternetAddress receiverAddress = new InternetAddress(mailReceiver);
            receiverAddress.validate();
            msg.setRecipient(Message.RecipientType.TO, receiverAddress);
            msg.setSubject(title, "UTF-8");
            msg.setSentDate(new Date());
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=UTF-8");

            // add it
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (SendFailedException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
