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
    static String sendName = "Elight Learning English";
    //tieu de mail
    static String title = " BỘ TÀI LIỆU GỐI ĐẦU GIƯỜNG CỦA CÁC CAO THỦ TIẾNG ANH TỪ CƠ BẢN ĐẾN NÂNG CAO";
    //noi dung mail
    static String content = "<p style=\"text-align: center;\"><em><strong>C&ograve;n bạn n&agrave;o chưa nhận được t&agrave;i liệu của Elight Learning English kh&ocirc;ng?</strong></em></p>\n" +
"<p style=\"text-align: justify;\">👉 BỘ T&Agrave;I LIỆU \"GỐI ĐẦU GIƯỜNG\" CỦA C&Aacute;C CAO THỦ TIẾNG ANH TỪ CƠ BẢN ĐẾN N&Acirc;NG CAO</p>\n" +
"<p style=\"text-align: justify;\">👉 Bộ t&agrave;i liệu BAO GỒM:<br />1. PHƯƠNG PH&Aacute;P HỌC TIẾNG ANH CHO NGƯỜI MỚI BẮT ĐẦU<br />2. TẤT TẦN TẬT NHỮNG BỘ T&Agrave;I LIỆU \"Đ&Aacute;NG ĐỒNG TIỀN B&Aacute;T GẠO\" m&agrave; bạn c&oacute; thể bỏ c&ocirc;ng sức để học.</p>\n" +
"<p style=\"text-align: justify;\">Nếu chưa nhận được t&agrave;i liệu c&aacute;c bạn click v&ocirc; <a href=\"http://bit.ly/2hyJBlZ\"><span style=\"color: #ff0000; background-color: #ffffff;\"><strong>Đ&Acirc;Y</strong></span> </a>để download t&agrave;i liệu nh&eacute;! C&oacute; rất nhiều t&agrave;i liệu để ch&uacute;ng ta học cả đời m&agrave; kh&ocirc;ng hết.</p>\n" +
"<p style=\"text-align: justify;\"><strong>Ch&uacute; &yacute;</strong>: <span style=\"background-color: #ffff00;\">Danh s&aacute;ch t&agrave;i liệu ở phần m&ocirc; tả của mỗi video</span></p>\n" +
"<p style=\"text-align: justify;\">Đăng k&yacute;, Like , Share k&ecirc;nh Youtube: <a href=\"http://bit.ly/2z6n8X1\"><strong><span style=\"color: #ff0000;\">Tiếng Anh Cho Người Việt</span></strong></a> , Fanpage: <strong><span style=\"color: #0000ff;\"><a style=\"color: #0000ff;\" href=\"http://bit.ly/2iMwgcW\">Tiếng Anh Cho Người Việt</a></span></strong> để theo d&otilde;i cập nhật c&aacute;c t&agrave;i liệu mới nhất từ k&ecirc;nh v&agrave; fanpage nha cả nh&agrave;!</p>\n" +
"<p style=\"text-align: justify;\">Admin cảm ơn c&aacute;c bạn đ&atilde; ủng hộ!</p>";

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
