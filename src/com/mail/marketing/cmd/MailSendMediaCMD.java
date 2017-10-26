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

            String sendName = "Ms Hoa Toeic - Sứ giả truyền cảm hứng";
            String title = "Nhanh tay đăng ký 2 khóa học miễn phí từ Ms Hoa Toeic - hôm nay (26/10/2017) là ngày cuối chốt danh sách rồi!";
            String content = "<table style=\"height: 263px;\" width=\"722\">\n"
                    + "<tbody>\n"
                    + "<tr style=\"height: 120px;\">\n"
                    + "<td style=\"width: 712px; height: 120px; background-color: #d3ed74;\">\n"
                    + "<p style=\"text-align: center;\"><span style=\"color: #800000;\"><strong>KH&Oacute;A HỌC TOEIC MIỄN PH&Iacute;</strong></span></p>\n"
                    + "<p style=\"text-align: left;\"><strong><span style=\"color: #ff0000;\">Qu&agrave; khủng</span> </strong>m&agrave; c&ocirc; hứa tặng c&aacute;c em, gồm 2 kh&oacute;a học:</p>\n"
                    + "<p style=\"text-align: left;\">1. Kh&oacute;a Online MIỄN PH&Iacute; TOEIC 4 kỹ năng 10 buổi&nbsp;<br />2. Lớp bổ trợ TOEIC Speaking &amp; Writing miễn ph&iacute; chiều thứ 7 h&agrave;ng tuần cho học vi&ecirc;n của trung t&acirc;m</p>\n"
                    + "<p style=\"text-align: left;\">Đều được ra mắt cả rồi ^^. C&ograve;n bạn n&agrave;o bị lỡ m&agrave; chưa nhận được kh&ocirc;ng :3.&nbsp;</p>\n"
                    + "</td>\n"
                    + "</tr>\n"
                    + "<tr style=\"height: 60px;\">\n"
                    + "<td style=\"width: 712px; height: 60px; background-color: #e6d8d8;\">\n"
                    + "<p style=\"text-align: justify;\">C&aacute;c em nhanh tay điền v&agrave;o mẫu đăng k&yacute;, trung t&acirc;m sẽ li&ecirc;n hệ hướng dẫn c&aacute;c em tham gia kh&oacute;a học, lưu &yacute; điền đ&uacute;ng địa chỉ v&agrave; số điện thoại để được học tại cơ sở gần nhất, v&igrave; số lượng c&oacute; hạn n&ecirc;n bạn n&agrave;o chưa nhanh tay th&igrave; hẹn c&aacute;c em v&agrave;o dịp kh&aacute;c nha!&nbsp;C&ocirc; sẽ <strong>chốt danh s&aacute;ch</strong> v&agrave;o <span style=\"color: #ff0000;\"><strong>18</strong> </span>giờ ng&agrave;y h&ocirc;m nay nh&eacute;!(<strong>26/10/2017</strong>)</p>\n"
                    + "<p style=\"text-align: justify;\">C&aacute;c em click v&agrave;o <span style=\"color: #0000ff;\"><strong><a style=\"color: #0000ff;\" href=\"http://bit.ly/2yQsR35\">Đ&Acirc;Y</a></strong></span> để đăng k&yacute; kh&oacute;a học nh&eacute;!</p>\n"
                    + "<p style=\"text-align: justify;\">Bạn n&agrave;o thực hiện</p>\n"
                    + "<p style=\"text-align: justify;\">Đăng k&yacute; k&ecirc;nh youtube:&nbsp;<span style=\"color: #ff0000;\"><strong><a style=\"color: #ff0000;\" href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q&amp;source=gmail&amp;ust=1509063884202000&amp;usg=AFQjCNE3ThiiKML9xG0CvmBbdlJsD5p5zQ\">Tiếng Anh Cho Người Việt</a></strong></span></p>\n"
                    + "<p style=\"text-align: justify;\">Like fanpage:&nbsp;<strong><a href=\"https://www.facebook.com/englishforvn/\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.facebook.com/englishforvn/&amp;source=gmail&amp;ust=1509063884202000&amp;usg=AFQjCNF9cRtKl7eGUxCiIxnmMA3LWSWO4Q\">Tiếng Anh Cho Người Việt</a></strong></p>\n"
                    + "<p style=\"text-align: justify;\"><strong>Sẽ được ưu ti&ecirc;n li&ecirc;n hệ trước nha</strong></p>\n"
                    + "<p style=\"text-align: justify;\"><strong><span style=\"font-size: 16pt;\">Ms.Hoa!</span></strong></p>\n"
                    + "</td>\n"
                    + "</tr>\n"
                    + "</tbody>\n"
                    + "</table>";
            int countMailSentSuccess = 0;
            ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(1));
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
                    System.out.println("-------------------Đã gửi đến:-- " + to.getEmail() + "-- thành công! Số lượng email đã gửi:  " + countMailSentSuccess);

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
            System.out.println("--------------CHƯƠNG TRÌNH KẾT THÚC-----------------");
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
