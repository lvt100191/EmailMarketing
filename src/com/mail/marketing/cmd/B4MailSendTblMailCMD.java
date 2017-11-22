/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.FeedEntity;
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
//    static String sendName = "Ms Hoa TOEIC - Sứ giả truyền cảm hứng!";
//    //tieu de mail
//    static String title = "TOEIC - Luyện nghe PART 1 có đáp án";
//    //noi dung mail
//    static String content = "<p style=\"text-align: justify;\"><strong>GIỚI THIỆU PART 1:</strong></p>\n" +
//"<p style=\"text-align: justify;\">Trong phần Part 1 bạn sẽ c&oacute; 10 bức tranh v&agrave; mỗi bức tranh c&oacute; 4 đ&aacute;p &aacute;n (A,B,C v&agrave; D). Nhiệm vụ của bạn l&agrave; nghe một lượt duy nhất 4 đ&aacute;p &aacute;n n&agrave;y v&agrave; khoanh v&agrave;o đ&aacute;p &aacute;n m&agrave; bạn cho l&agrave; hợp l&iacute; nhất, ph&ugrave; hợp khi mi&ecirc;u tả về bức tranh đ&oacute;. C&aacute;c bạn lưu &yacute; nh&eacute;, chỉ nghe 1 lần duy nhất th&ocirc;i n&ecirc;n c&aacute;c bạn phải tập trung cao độ ở phần thi n&agrave;y. V&igrave; nếu c&aacute;c bạn muốn đạt từ 650 trở l&ecirc;n th&igrave; phải trả lời đ&uacute;ng 8/10 c&acirc;u đấy nh&eacute;.</p>\n" +
//"<p style=\"text-align: justify;\">Tiếp theo l&agrave; về cấu tr&uacute;c đề thi. Th&ocirc;ng thường người ta chia l&agrave;m 2 phần:&nbsp;<a href=\"https://goo.gl/TCN7HF\"><strong>tranh tả người</strong>&nbsp;</a>v&agrave;&nbsp;<a href=\"https://goo.gl/TCN7HF\"><strong>tranh tả vật</strong></a>.</p>\n" +
//"<p style=\"text-align: justify;\">Trong phần tranh tả người th&igrave; bao gồm tranh tả 1 người v&agrave; tranh tả nhiều người (từ 7-8 tranh). Bạn cần quan s&aacute;t kỹ h&agrave;nh động của người trong ảnh, li&ecirc;n tưởng ngay tới một động từ chỉ h&agrave;nh động, sau đ&oacute; nghe kỹ 4 đ&aacute;p &aacute;n, chọn c&acirc;u trả lời gần với ph&aacute;n đo&aacute;n của bạn nhất.</p>\n" +
//"<p style=\"text-align: justify;\">C&ograve;n tranh tả vật v&agrave; phong cảnh th&igrave; từ 2-3 tranh th&ocirc;i. Đối với&nbsp;bức tranh kh&ocirc;ng c&oacute; sự xuất hiện của con người, th&igrave; bạn phải x&aacute;c định được vị tr&iacute; nơi chốn của c&aacute;c vật trong tranh bằng c&aacute;c giời từ th&iacute;ch hợp, l&agrave;m như thế sẽ tr&aacute;nh được những sai s&oacute;t đ&aacute;ng kể đấy.</p>\n" +
//"<p style=\"text-align: justify;\">PART 1 được đ&aacute;nh gi&aacute; l&agrave; phần thi dễ nhất trong b&agrave;i thi TOEIC n&ecirc;n c&aacute;c bạn cố gắng luyện tập thật tốt v&agrave; hạn chế mất điểm ở phần n&agrave;y nha.</p>\n" +
//"<p style=\"text-align: justify;\"><strong>THỰC H&Agrave;NH PART 1</strong></p>\n" +
//"<p style=\"text-align: justify;\">Click v&agrave;o <a href=\"https://goo.gl/TCN7HF\"><strong>Đ&Acirc;Y</strong> </a>để luyện tập nh&eacute;, mục ti&ecirc;u cho c&aacute;c bạn l&agrave; tối thiểu đạt 6/10 c&acirc;u ah ^^</p>\n" +
//"<p style=\"text-align: justify;\">Nếu c&aacute;c bạn thấy b&agrave;i viết n&agrave;y hữu &iacute;ch đừng qu&ecirc;n <strong>LIKE, SHARE, ĐĂNG K&Yacute;</strong> k&ecirc;nh youtube: <span style=\"color: #ff0000;\"><a style=\"color: #ff0000;\" href=\"https://goo.gl/TCN7HF\"><strong>Tiếng Anh Cho Người Việt</strong></a></span> để ủng hộ c&ocirc; nha!</p>\n" +
//"<p style=\"text-align: justify;\">Cảm ơn cả nh&agrave; nhiều! Ch&uacute;c c&aacute;c em cuối tuần vui vẻ</p>\n" +
//"<p style=\"text-align: justify;\">&nbsp;</p>\n" +
//"<p style=\"text-align: justify;\">&nbsp;</p>\n" +
//"<p style=\"text-align: justify;\">&nbsp;</p>";

    public static void main(String[] args) {
        String mailSend1 = args[0].trim();
        String checkTest = args[1].trim();
        String mailReceiv = "";
        if (checkTest.equals("1")) {//truyen vao mail test, 0: ko test
            mailReceiv = args[2].trim();
        }
        //test mail gửi, chú ý phải là mail trong bang tbl_mail_send
        //String mailSend1 = "tienganhchonguoiviet.30082017.1@gmail.com";
        //end test
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("--------- Gui mail theo bang tbl_mail------------");
        System.out.println("-------------------------------- mail gui: " + mailSend1);
        System.out.println("-------------------------------- mail gui: " + mailSend1);
        System.out.println("-------------------------------- mail gui: " + mailSend1);
        System.out.println("-------------------------------- mail gui: " + mailSend1);
        System.out.println("-------------------------------- mail gui: " + mailSend1);
        try {
            String numMailTo = "";
            int countMailSentSuccess = 0;
            if (checkTest.equals("1")) {
                numMailTo = "99";
            } else {
                numMailTo = "100";
            }

            ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, numMailTo);
            if (checkTest.equals("1")) {
                Mail mxx = new Mail();
                mxx.setEmail(mailReceiv);
                lst.add(mxx);
            }
            //test
//            lst = new ArrayList<>();
//            Mail mxx = new Mail();
//            mxx.setEmail("tunglv9x@gmail.com");
//            lst.add(mxx);
            //end test
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");
            System.out.println("--------- Gui mail den danh sach gom: " + lst.size() + " email!");

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HH-mm-ss");
            String dateCreate = sdf.format(d);
            MailSend mSend = MailSendDao.getMailSendByEmail(mailSend1);
            FeedEntity feedEntity = FeedEntityDao.getByFeed("tunglv4");
            if (feedEntity == null) {
                throw new Exception("------------chua cau hinh bai viet trong bang tbl_feed voi id_feed=tunglv4-----------");
            }
            for (Mail to : lst) {
                try {

                    //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                    if (checkMailBlock(to.getEmail().trim())) {
                        continue;
                    }

                    EmailAction.sendGmail(feedEntity.getFanpageName().trim(), mailSend1, mSend.getPassword(), to.getEmail().toLowerCase(), feedEntity.getTitleSend().trim(), feedEntity.getContentSend().trim());

                    countMailSentSuccess++;
                    System.out.println("----------------" + mailSend1 + "---------------------");
                    System.out.println("----------------" + mailSend1 + "---------------------");
                    System.out.println("----------------" + mailSend1 + "---------------------");
                    System.out.println("----------------" + mailSend1 + "---------------------");
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
