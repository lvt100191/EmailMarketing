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
//su dung mot mail gui de gui thong bao den tat ca mail trong bang tbl_mail
//            String host ="mail.vnedu.vn";
//            String mailSend1 = "noreply@truelife.vn";
public class MailSendMediaCMD {

    public static void main(String[] args) {
        try {

            // TODO add your handling code here:
            //trang thai mail lay ra de gui truong status trong bang tbl_mail
            String sttMailSend = "1";
            //update trang thai da gui mail
            String sttMailSent = "2";
            //server mail
            String host ="mail.vnedu.vn";
            String mailSend1 = "noreply@truelife.vn";

            String sendName = "Tiếng Anh giao tiếp Langmaster";
            String title = "TỔNG HỢP 9000 BÀI HỌC ĐỦ GIAO TIẾP CẢ ĐỜI TIẾNG ANH (Full bản PDF + Video giáo viên bản ngữ đọc chuẩn Tây)";
            String content = "<p style=\"text-align: justify;\"><span style=\"color: #0000ff;\"><strong>Tiếng Anh giao tiếp Langmaster</strong> </span>gửi đến c&aacute;c bạn&nbsp;</p>\n" +
"<p style=\"text-align: justify;\">TỔNG HỢP 9000 B&Agrave;I HỌC ĐỦ GIAO TIẾP CẢ ĐỜI TIẾNG ANH (Full bản PDF + Video gi&aacute;o vi&ecirc;n bản ngữ đọc chuẩn T&acirc;y)</p>\n" +
"<p style=\"text-align: justify;\">C&aacute;c bạn click v&agrave;o <a href=\"http://bit.ly/2xpTLiE\"><span style=\"color: #0000ff;\"><strong>Đ&Acirc;Y</strong></span> </a>để tải t&agrave;i liệu nh&eacute;. Nhanh tay download để kh&ocirc;ng bị bỏ lỡ cơ hội nhận trọn bộ t&agrave;i liệu qu&yacute; gi&aacute; n&agrave;y. Sau <strong>18h00</strong> ng&agrave;y <strong>30/10/2017</strong> l&agrave; admin kh&ocirc;ng đảm bảo đ&acirc;u nha :D</p>\n" +
"<p style=\"text-align: justify;\">Nhớ đăng k&yacute; k&ecirc;nh Youtube: <a href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\"><span style=\"color: #ff0000;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a></p>\n" +
"<p style=\"text-align: justify;\">Like fanpage:&nbsp;&nbsp;<a href=\"https://www.facebook.com/englishforvn/\"><span style=\"color: #0000ff;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a></p>\n" +
"<p style=\"text-align: justify;\">Để được ưu ti&ecirc;n gửi kh&oacute;a học miễn ph&iacute;, t&agrave;i liệu sớm nhất.</p>\n" +
"<p style=\"text-align: justify;\">Ch&uacute;ng t&ocirc;i ch&acirc;n th&agrave;nh cảm ơn!</p>";
            int countMailSentSuccess = 0;
            ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(1000));
            //test
//                    ArrayList<Mail> lst = new ArrayList<>();
//                    Mail mxx = new Mail();
//                    mxx.setEmail("tunglv9x@gmail.com");
//                    lst.add(mxx);
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
            System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx-----------------");
            System.out.println("--------------CHUONG TRINH KET THUC-----------------");
            System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx-----------------");
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
