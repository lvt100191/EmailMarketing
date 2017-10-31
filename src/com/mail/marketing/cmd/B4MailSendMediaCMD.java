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
    static String sendName = "Tiếng Anh giao tiếp Langmaster";
    //tieu de mail
    static String title = "Toàn bộ 1000 bài học này + Ví dụ minh họa rõ ràng nhất";
    //noi dung mail
    static String content = "<p style=\"text-align: center;\"><strong>Post mỏi cả tay mới xong hết đống t&agrave;i liệu n&agrave;y</strong>&nbsp;<img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f4c/1/16/1f642.png\" alt=\"\" width=\"16\" height=\"16\" /></p>\n" +
"<p>~ N&oacute;ng nhất lu&ocirc;n nha<br /><span class=\"_47e3 _5mfr\" title=\"Biểu tượng cảm x&uacute;c smile\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f4c/1/16/1f642.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\" aria-hidden=\"true\">:)</span></span>&nbsp;Bạn n&agrave;o muốn nhận to&agrave;n bộ 1000 b&agrave;i học n&agrave;y + V&iacute; dụ minh họa r&otilde; r&agrave;ng nhất th&igrave;<br /><span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f51/1/16/1f449.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">👉</span></span>&nbsp;Share post n&agrave;y&nbsp;<br /><span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f51/1/16/1f449.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">👉</span></span>&nbsp;Comment email của c&aacute;c bạn nh&eacute;&nbsp;<span class=\"_47e3 _5mfr\" title=\"Biểu tượng cảm x&uacute;c heart\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f6c/1/16/2764.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\" aria-hidden=\"true\">&lt;3</span></span><br />B&acirc;y giờ ad gửi lu&ocirc;n nh&eacute;!&nbsp;<span class=\"_47e3 _5mfr\" title=\"Biểu tượng cảm x&uacute;c heart\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f6c/1/16/2764.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\" aria-hidden=\"true\">&lt;3</span></span>&nbsp;Nhanh nh&eacute;&nbsp;<span class=\"_47e3 _5mfr\" title=\"Biểu tượng cảm x&uacute;c smile\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f4c/1/16/1f642.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\" aria-hidden=\"true\">=)</span></span>))</p>\n" +
"<p><strong>Admin</strong> gửi c&aacute;c bạn link download t&agrave;i liệu bao gồm:</p>\n" +
"<p>- 200 C&Acirc;U HỎI PHỎNG VẤN BẰNG TIẾNG ANH</p>\n" +
"<p>-&nbsp;DOWNLOAD TRỌN BỘ 500 C&Acirc;U GIAO TIẾP TIẾNG ANH TH&Ocirc;NG DỤNG</p>\n" +
"<p>-&nbsp;100 ĐOẠN GIAO TIẾP TIẾNG ANH H&Agrave;NG NG&Agrave;Y</p>\n" +
"<p>- 200 B&Agrave;I H&Aacute;T TIẾNG ANH HAY NHẤT</p>\n" +
"<p>Click v&agrave;o <a href=\"http://bit.ly/2xpTLiE\"><span style=\"color: #ff0000;\"><strong>Đ&Acirc;Y</strong></span> </a>để tải t&agrave;i liệu, ch&uacute; &yacute; nhanh tay, admin kh&ocirc;ng share l&acirc;u đ&acirc;u :D, 1000 lượt download l&agrave; admin ẩn link nh&eacute;!</p>\n" +
"<p>C&aacute;c bạn đăng k&yacute; k&ecirc;nh youtube:&nbsp;<strong><span style=\"color: #ff0000;\"><a style=\"color: #ff0000;\" href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\">Tiếng Anh Cho Người Việt</a></span></strong></p>\n" +
"<p>Like fanpage:&nbsp;<span style=\"color: #0000ff;\"><strong><a style=\"color: #0000ff;\" href=\"https://www.facebook.com/englishforvn/\">Tiếng Anh Cho Người Việt</a></strong></span></p>\n" +
"<p>Để được ưu ti&ecirc;n gửi mail, nhận t&agrave;i liệu, th&ocirc;ng kh&oacute;a học miễn ph&iacute;</p>\n" +
"<p>Ch&uacute;ng t&ocirc;i xin ch&acirc;n th&agrave;nh cảm ơn!</p>\n" +
"<p>&nbsp;</p>";

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
