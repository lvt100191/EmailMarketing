/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.mail.EmailAction;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class TestSendMailABC {

    //trang thai mail lay ra de gui truong status trong bang tbl_mail
    private static String sttMailSend = "1";
    //update trang thai da gui mail
    private static String sttMailSent = "2";

    private static String mailSend1 = "noreply@truelife.vn";
     //  private static String mailSend1 = "hoa.ms.toeic@gmail.com";

    private static String title = "Nhanh tay đăng ký 2 khóa học miễn phí từ Ms Hoa Toeic";
    private static String content = "<p><span style=\"color: #000000; background-color: #ffff00;\">Th&ocirc;ng b&aacute;o khuya:</span><br /><span style=\"color: #000000; background-color: #ffff00;\">- 2 \"qu&agrave; khủng\" m&agrave; c&ocirc; hứa tặng c&aacute;c em, gồm:</span></p>\n" +
"<p><span style=\"color: #000000; background-color: #ffff00;\">1. Kh&oacute;a Online MIỄN PH&Iacute; TOEIC 4 kỹ năng 10 buổi </span><br /><span style=\"color: #000000; background-color: #ffff00;\">2. Lớp Bổ trợ TOEIC Speaking &amp; Writing miễn ph&iacute; chiều thứ 7 h&agrave;ng tuần cho học vi&ecirc;n của trung t&acirc;m</span></p>\n" +
"<p><span style=\"color: #000000; background-color: #ffff00;\">Đều được ra mắt cả rồi ^^. C&ograve;n bạn n&agrave;o bị lỡ m&agrave; chưa nhận được kh&ocirc;ng :3. Comment m&oacute;n qu&agrave; mong muốn của em n&agrave;o :D</span></p>\n" +
"<p><br />C&ocirc; gửi c&aacute;c em link đăng k&yacute; kh&oacute;a học nh&eacute;! C&aacute;c em nhanh tay điền v&agrave;o mẫu đăng k&yacute;, trung t&acirc;m sẽ li&ecirc;n hệ hướng dẫn c&aacute;c em tham gia kh&oacute;a học, lưu &yacute; điền đ&uacute;ng địa chỉ v&agrave; số điện thoại để được học tại cơ sở gần nhất nh&eacute; c&aacute;c em, v&igrave; số lượng c&oacute; hạn n&ecirc;n bạn n&agrave;o chưa nhanh tay th&igrave; hẹn c&aacute;c em v&agrave;o dịp kh&aacute;c nha!</p>\n" +
"<p><a href=\"http://bit.ly/2yQsR35\">http://bit.ly/2yQsR35</a></p>\n" +
"<p>&nbsp;</p>";
    public static void main(String[] args) throws Exception {
        int countMailSentSuccess = 0;
        ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(1000));
                            //test
//                    ArrayList<Mail> lst = new ArrayList<>();
//                    Mail mxx = new Mail();
//                    mxx.setEmail("tunglv9x@gmail.com");
//                    lst.add(mxx);
        for (Mail to : lst) {
            try {

                //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                if (checkMailBlock(to.getEmail().trim())) {
                    continue;
                }

                sendMail(mailSend1, to.getEmail().toLowerCase(), title, content);

                System.out.println("---------------- tunglv4 gui mail " + mailSend1 + " toi: " + to.getEmail() + " thanh cong");
                countMailSentSuccess++;
                System.out.println("-------step------------Đã gửi đến thành công: " + countMailSentSuccess + " email!----------");
                //update status mail nhan
                to.setStatus(Integer.parseInt(sttMailSent));
                MailDao.updateMail(to);

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
        System.out.println("-------xxxxx------------Đã gửi đến thành công: " + countMailSentSuccess + " email!----------");
    }

    public static void sendMail(String sendMail, String mailReceiver, String title, String content) {
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
            } else {
                session = Session.getInstance(props, null);
            }

            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", "text/plain; charset=UTF-8");

            InternetAddress senderAddress = new InternetAddress(sendMail);
            senderAddress.setPersonal("Ms Hoa Toeic - Sứ giả truyền cảm hứng", "UTF-8");
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

}
