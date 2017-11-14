/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.entity.MailSend;
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
public class B4MailSendTblMailCMD {

    //tham so dau vao
    //trang thai mail lay ra de gui truong status trong bang tbl_mail
    static String sttMailSend = "1";
    //update trang thai da gui mail
    static String sttMailSent = "2";
    //mail gửi, chú ý phải là mail trong bang tbl_mail_send
    //static String mailSend1 = "tienganhchonguoiviet.30082017.1@gmail.com";
    //ten nguoi gui, ten nay se hiển thị trên người gửi đến, 
    //nếu không cấu hình sẽ hiển thị địa chỉ mail gửi
    static String sendName = "Ms Hoa Toeic - Sứ giả truyền cảm hứng";
    //tieu de mail
    static String title = "TRỌN BỘ TÀI LIỆU TỪ VỰNG TIẾNG ANH CHUYÊN NGÀNH";
    //noi dung mail
    static String content = "<p style=\"text-align: justify;\">C&aacute;c em ơi ủng hộ k&ecirc;nh youtube mới của c&ocirc; nh&eacute;! <a href=\"https://goo.gl/aWrRM9\"><span style=\"color: #ff0000;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a> l&agrave; k&ecirc;nh youtube tổng hợp tất tần tật t&agrave;i liệu từ a-z, từ z đến vv...</p>\n" +
"<p style=\"text-align: justify;\">Mỗi video sẽ giới thiệu tổng quan về một chủ đề, tại phần m&ocirc; tả sẽ c&oacute; link download tất cả t&agrave;i liệu m&agrave; c&ocirc; sưu tập được, t&agrave;i liệu sẽ ng&agrave;y một phong ph&uacute; v&agrave; đa dạng, đặc biệt hứa hẹn sẽ x&acirc;y dựng một kho t&agrave;i liệu m&agrave; c&aacute;c bạn học cả đời kh&ocirc;ng hết</p>\n" +
"<p style=\"text-align: justify;\">Đừng qu&ecirc;n đăng k&yacute; v&agrave; chia sẻ k&ecirc;nh Youtube:<a href=\"https://goo.gl/RiY2S6\"><span style=\"color: #ff0000;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a></p>\n" +
"<p style=\"text-align: justify;\">Like fanpage&nbsp;<a href=\"https://goo.gl/AvHdwi\"><span style=\"color: #3366ff;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a> để theo d&otilde;i những t&agrave;i liệu mới cập nhật của c&ocirc; nha!</p>\n" +
"<p style=\"text-align: justify;\">Tại k&ecirc;nh youtube hoặc fanpage c&ocirc; sẽ sưu tập kh&oacute;a học free cho c&aacute;c bạn n&agrave;o đăng k&yacute; k&ecirc;nh youtube thay lời tri &acirc;n, h&atilde;y nhanh tay v&agrave; đừng bỏ lỡ.</p>\n" +
"<p style=\"text-align: justify;\">Cảm ơn c&aacute;c em nhiều!</p>\n" +
"<p>&nbsp;</p>";

    public static void main(String[] args) {
        String mailSend1 = args[0].trim();
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("-------------------------------- mail gui: "+mailSend1);
        System.out.println("-------------------------------- mail gui: "+mailSend1);
        System.out.println("-------------------------------- mail gui: "+mailSend1);
        System.out.println("-------------------------------- mail gui: "+mailSend1);
        System.out.println("-------------------------------- mail gui: "+mailSend1);
        try {
            int countMailSentSuccess = 0;
            ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(100));
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            //test
//            ArrayList<Mail> lst = new ArrayList<>();
//            Mail mxx = new Mail();
//            mxx.setEmail("tunglv9x@gmail.com");
//            lst.add(mxx);
            //end test
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
            String dateCreate = sdf.format(d);
            MailSend mSend = MailSendDao.getMailSendByEmail(mailSend1);
            for (Mail to : lst) {
                try {

                    //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                    if (checkMailBlock(to.getEmail().trim())) {
                        continue;
                    }

                    EmailAction.sendGmail(sendName, mailSend1, mSend.getPassword(), to.getEmail(), title, content);

                    countMailSentSuccess++;
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
                    System.out.println("-------------------da gui den:  " + to.getEmail() + "  thanh cong! tong so mail da gui:  " + countMailSentSuccess);
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
                        //xoa ban ghi trong bang tbl_mail
                        MailDao.deleteMail(m.getId());
                    }
                    //insert vao bang tbl_mail_blocked
                    MailBlock mb = initMailBlock(to.getEmail());
                    MailBlockDao.insert(mb);
                } catch (Exception e) {
                    System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + e.getMessage());
                    if (e.getMessage().contains("550 5.4.5")) {
                        MailSendDao.updateMailLastTime(mSend);
                        throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                    }
                } finally {
                }
            }
            MailSendDao.updateMailLastTime(mSend);

        } catch (Exception ex) {
            Logger.getLogger(GUITestMailSendABC.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx----------------------------------------------");
            System.out.println("--------------CHUONG TRINH GUI MAIL THEO BANG TBL-MAIL KET THUC-----------------");
            System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx----------------------------------------------");
            //gui het 100 mail thi update  mail gui

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
