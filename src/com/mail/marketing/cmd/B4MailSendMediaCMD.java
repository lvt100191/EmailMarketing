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
    static String host = "mail.truelife.vn";
    //mail gửi, chú ý phải là mail này thì server mail mới cho phép gửi
    static String mailSend1 = "noreply@truelife.vn";
    //ten nguoi gui, ten nay se hiển thị trên người gửi đến, 
    //nếu không cấu hình sẽ hiển thị địa chỉ mail gửi
    static String sendName = "Ms Hoa Toeic - Sứ giả truyền cảm hứng";
    //tieu de mail
    static String title = "2 Khóa học FREE hot nhất năm";
    //noi dung mail
    static String content = "<p style=\"text-align: center;\"><span style=\"color: #0000ff;\"><strong>ĐĂNG K&Yacute; KH&Oacute;A HỌC TOEIC MIỄN PH&Iacute; TẠI MS.HOA TOEIC</strong></span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #000000;\">Thay lời tri &acirc;n đến tất cả c&aacute;c bạn đ&atilde; ủng hộ ch&uacute;ng t&ocirc;i trong suốt thời gian qua,Ms.Hoa xin gửi tới tất cả mọi người 2 kh&oacute;a học đặc biệt <strong>MIỄN PH&Iacute;</strong> v&agrave; c&oacute; <strong>GIỚI HẠN</strong> số lượng.</span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #000000;\">Nhanh tay thực hiện đủ <strong>3 bước</strong> sau:</span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #000000;\"><strong>Bước 1</strong>: Đăng k&yacute; k&ecirc;nh Youtube: <a href=\"http://bit.ly/2iizyRp\"><strong><span style=\"color: #ff0000;\">Tiếng Anh Cho Người Việt</span></strong></a></span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #000000;\"><strong><span style=\"color: #ff0000;\">http://bit.ly/2iizyRp</span></strong></span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #000000;\"><strong>Bước 2</strong>: Like fanpage: <a href=\"http://bit.ly/2iMwgcW\"><strong><span style=\"color: #0000ff;\">Tiếng Anh Cho Người Việt</span></strong></a></span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #000000;\"><strong><span style=\"color: #0000ff;\">http://bit.ly/2iMwgcW</span></strong></span></p>\n" +
"<p style=\"text-align: justify;\"><strong>Bước 3</strong>: Click v&agrave;o <a href=\"https://goo.gl/forms/c2b8bGFMghLqzCdr2\"><strong><span style=\"color: #0000ff;\">Đ&Acirc;Y</span></strong></a> điền v&agrave;o form đăng k&yacute; chi tiết v&agrave; ch&iacute;nh x&aacute;c nhất nha!</p>\n" +
"<p style=\"text-align: justify;\">Ch&uacute;ng t&ocirc;i sẽ li&ecirc;n hệ sớm nhất với c&aacute;c bạn l&agrave;m <strong>đủ 3 bước tr&ecirc;n</strong> theo số điện thoại hoặc email để th&ocirc;ng b&aacute;o hướng dẫn tham gia kh&oacute;a học.&nbsp;</p>\n" +
"<p style=\"text-align: center;\">&nbsp; <span style=\"color: #ff0000;\"><strong>&nbsp;TH&Ocirc;NG TIN CHI TIẾT 2 KH&Oacute;A HỌC</strong></span>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</p>\n" +
"<h1 class=\"title\"><span style=\"font-size: 10pt; color: #ff0000;\">Kh&oacute;a học TOEIC A (MỤC TI&Ecirc;U 500-550+)</span></h1>\n" +
"<p style=\"text-align: justify;\"><span style=\"font-size: 10pt;\"><strong>ĐIỀU KIỆN ĐẦU V&Agrave;O:</strong>&nbsp;Kh&oacute;a học ph&ugrave; hợp cho những bạn&nbsp;đ&atilde; c&oacute; kiến thức cơ bản về Tiếng Anh nhưng chưa&nbsp;được hệ thống b&agrave;i bản theo Format chuẩn&nbsp;TOEIC.&nbsp;Điểm&nbsp;đầu v&agrave;o&nbsp;<strong>tối thiểu 350/990</strong>&nbsp;thi thử tại Ms Hoa TOEIC hoặc&nbsp;đ&atilde; c&oacute;&nbsp;điểm thi tại IIG Việt Nam kh&ocirc;ng qu&aacute; 4 th&aacute;ng</span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"font-size: 10pt;\"><strong>MỤC TI&Ecirc;U ĐẦU RA:&nbsp;</strong><strong>500-550+ điểm</strong>&nbsp;<strong>(Hoặc tăng &iacute;t nhất&nbsp;150-200 điểm&nbsp;so với điểm đầu v&agrave;o)</strong></span></p>\n" +
"<h1 class=\"title\"><span style=\"font-size: 10pt; color: #ff0000;\">Kh&oacute;a học TOEIC B (MỤC TI&Ecirc;U 650-750+)</span></h1>\n" +
"<p style=\"text-align: justify;\"><span style=\"font-size: 10pt;\"><strong>Kh&oacute;a học TOEIC B</strong>&nbsp;được thiết kế d&agrave;nh cho đối tượng học vi&ecirc;n&nbsp;đ&atilde; nắm kh&aacute; vững kiến thức cơ bản, khả năng nghe kh&aacute; tốt v&agrave; mong muốn chinh phục TOEIC&nbsp;ở mục ti&ecirc;u cao nhất.</span><br /><br /><span style=\"font-size: 10pt;\"><strong>ĐIỀU KIỆN ĐẦU V&Agrave;O:</strong>&nbsp;<strong>Tối thiểu 500/990&nbsp;điểm</strong>. Thi thử tại Ms Hoa TOEIC hoặc c&oacute;&nbsp;điểm ch&iacute;nh thức từ IIG Việt Nam kh&ocirc;ng qu&aacute; 4 th&aacute;ng.</span></p>\n" +
"<p style=\"text-align: justify;\"><span style=\"font-size: 10pt;\"><strong>MỤC TI&Ecirc;U ĐẦU RA:&nbsp;650-750+ điểm</strong>&nbsp;<strong>(Hoặc CAM KẾT</strong>&nbsp;<strong>tăng&nbsp;&iacute;t nhất&nbsp;150 - 200 điểm&nbsp;so với điểm đầu v&agrave;o).&nbsp;</strong></span></p>";

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
            //String filename = "xxx-mail-to-" + dateCreate;
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
