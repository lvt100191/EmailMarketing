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
    static String sendName = "Ms Hoa TOEIC - Sứ giả truyền cảm hứng!";
    //tieu de mail
    static String title = "Luyện tập PART 1 bài thi TOEIC";
    //noi dung mail
    static String content = "<h2 style=\"text-align: justify;\"><span style=\"font-size: 14pt;\"><strong>Kinh nghiệm l&agrave;m b&agrave;i thi toeic part 1&nbsp;đạt điểm tối đa</strong></span></h2>\n" +
"<h4 style=\"text-align: justify;\"><strong>X&aacute;c định nội dung bức tranh</strong></h4>\n" +
"<p style=\"text-align: justify;\">Phần một sẽ kiểm tra khả năng nghe-hiểu của bạn. Phần n&agrave;y đ&ograve;i hỏi bạn phải x&aacute;c định c&aacute;c chi tiết về bức tranh v&agrave; lắng nghe c&aacute;c khẳng định rồi lựa chọn khẳng định đ&uacute;ng mi&ecirc;u tả bức tranh.</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>Kỹ năng cần thực hiện</strong></h4>\n" +
"<p style=\"text-align: justify;\">Để chọn được mi&ecirc;u tả đ&uacute;ng cho c&aacute;c bức tranh ở phần 1, bạn cần phải:</p>\n" +
"<ul style=\"text-align: justify;\">\n" +
"<li>Thực hi&ecirc;n liệt k&ecirc; nhanh tất cả c&aacute;c th&ocirc;ng tin ở trong bức tranh như con người, đồ vật, hoạt động, trạng th&aacute;i v&agrave; địa điểm.</li>\n" +
"<li>Nhận diện xem c&aacute;c khẳng định hoặc c&aacute;c c&acirc;u trả lời đ&oacute; sử dụng động từ/danh từ/ t&iacute;nh từ v&agrave; trạng từ n&agrave;o. Th&ocirc;ng thường THERE IS/ ARE hoặc c&aacute;c c&acirc;u m&ocirc; tả h&igrave;nh ảnh</li>\n" +
"<li>Ph&acirc;n biệt c&aacute;c mi&ecirc;u tả đ&uacute;ng v&agrave; sai về bức tranh dựa tr&ecirc;n nghĩa của từ v&agrave; &acirc;m từ</li>\n" +
"</ul>\n" +
"<h4 style=\"text-align: justify;\"><strong>Tập trung v&agrave;o ph&aacute;t &acirc;m&nbsp;tiếng anh</strong></h4>\n" +
"<p style=\"text-align: justify;\">Như đ&atilde; n&oacute;i ở tr&ecirc;n, c&aacute;c đ&aacute;p &aacute;n ở phần nghe tranh sẽ l&agrave; m&ocirc; tả đồ vật, bối cảnh hoặc h&agrave;nh động của con người trong đ&oacute;. V&igrave; thế n&ecirc;n việc nắm được ph&aacute;t &acirc;m của c&aacute;c đồ vật hay h&agrave;nh động của con người l&agrave; rất cần thiết.</p>\n" +
"<p style=\"text-align: justify;\">V&iacute; dụ như chiếc xe c&uacute;t k&iacute;t: wheelbarrow /ˈwiːlˌb&aelig;r.əʊ/ l&agrave; một từ m&agrave; kh&ocirc;ng nhiều c&aacute;c bạn biết đến, nhưng cũng c&oacute; xuất hiện trong phần nghe tranh Part 1. Vậy n&ecirc;n việc học c&aacute;c từ v&agrave; ph&aacute;t &acirc;m l&agrave; cực kỳ quan trọng quyết định ch&uacute;ng ta c&oacute; nghe được hay kh&ocirc;ng.</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>Cẩn thận với c&aacute;c &acirc;m dễ g&acirc;y nhầm lẫn</strong></h4>\n" +
"<p style=\"text-align: justify;\">Đ&acirc;y l&agrave; một c&aacute;ch để bẫy th&iacute; sinh, trong b&agrave;i sẽ sử dụng c&aacute;c từ c&oacute; &acirc;m tương tự nhưng nghĩa ho&agrave;n to&agrave;n kh&aacute;c nhau, v&iacute; dụ như first/fast, raise/ erase, dock/duck, filed/piled, plant/plan, track/crack/rack, rain/train/drain,v.v. Việc nắm vững ph&aacute;t &acirc;m của từ d&ugrave;ng để mi&ecirc;u tả bức tranh l&agrave; rất quan trọng.</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>X&aacute;c định sự xuất hiện của người hay của đồ vật?</strong></h4>\n" +
"<p style=\"text-align: justify;\">Trong&nbsp;<strong>part 1 toeic listening</strong>&nbsp;thường c&oacute; từ 6-7 tranh c&oacute; sự xuất hiện của người trong ảnh. Bạn cần quan s&aacute;t kỹ h&agrave;nh động của người trong ảnh, li&ecirc;n tưởng ngay tới một động từ chỉ h&agrave;nh động, sau đ&oacute; nghe kỹ 4 đ&aacute;p &aacute;n, chọn c&acirc;u trả lời gần với ph&aacute;n đo&aacute;n của bạn nhất.</p>\n" +
"<p style=\"text-align: justify;\">Việc nắm r&otilde; c&aacute;c giới từ chỉ vị tr&iacute; nơi chốn l&agrave; rất quan trọng, sẽ gi&uacute;p cho c&aacute;c bạn định h&igrave;nh được vị tr&iacute; c&aacute;c đồ vật hay con người trong bức tranh. V&iacute; dụ: in, on, at, behind, beside, beneath, below, above, under, v.v. C&aacute;c giới từ n&agrave;y tuy đơn giản nhưng nếu bạn kh&ocirc;ng nhớ n&oacute; th&igrave; rất c&oacute; thể bạn sẽ kh&ocirc;ng nghe được tranh v&agrave; mất điểm trong phần 1.</p>\n" +
"<p style=\"text-align: justify;\">Nếu như trong bức tranh kh&ocirc;ng c&oacute; sự xuất hiện của con người, th&igrave; việc đầu ti&ecirc;n bạn cần l&agrave;m khi nh&igrave;n v&agrave;o bức tranh đ&oacute; l&agrave; x&aacute;c định vị tr&iacute; nơi chốn của c&aacute;c vật sử dụng c&aacute;c giới từ th&iacute;ch hợp.</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>Mẹo loại trừ c&aacute;c đ&aacute;p &aacute;n sai</strong></h4>\n" +
"<p style=\"text-align: justify;\">Nếu như trong tranh m&ocirc; tả đồ vật, kh&ocirc;ng c&oacute; sự xuất hiện của con người m&agrave; c&oacute; những đ&aacute;p &aacute;n c&oacute; &ldquo;He, she, the man, the woman&rdquo; th&igrave; đều l&agrave; đ&aacute;p &aacute;n sai v&igrave; kh&ocirc;ng nhắm v&agrave;o bức tranh.</p>\n" +
"<p style=\"text-align: justify;\">C&aacute;c c&acirc;u đ&aacute;p &aacute;n d&ugrave;ng c&aacute;c từ như everyone, everybody th&igrave; thường l&agrave; đ&aacute;p &aacute;n sai</p>\n" +
"<p style=\"text-align: justify;\">C&aacute;c c&acirc;u m&ocirc; tả đồ vật, chỉ c&oacute; sự xuất hiện của đồ vật m&agrave; lại c&oacute; dạng bị động tiếp diễn th&igrave; khả năng sai cũng l&agrave; rất cao.</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>H&atilde;y tận dụng 1 ph&uacute;t 35 gi&acirc;y</strong></h4>\n" +
"<p style=\"text-align: justify;\">Đ&oacute; l&agrave; khoảng thời gian cho phần hướng dẫn của New TOEIC. Trong khi nghe phần hướng dẫn, m&ocirc; tả về phần thi nghe, trước khi tới c&acirc;u &ldquo;Now let us begin part 1 with question number 1&rdquo;, hầu hết c&aacute;c th&iacute; sinh đạt điểm cao đều tận dụng khoảng thời gian n&agrave;y để đọc c&acirc;u hỏi của Part 3,&nbsp;toeic part 4&nbsp;hay l&agrave;m Part 5.</p>\n" +
"<p style=\"text-align: justify;\">Nếu như bạn bỏ ph&iacute; khoảng thời gian n&agrave;y, th&igrave; khả năng bạn l&agrave;m tốt những phần sau v&agrave; được điểm cao sẽ giảm xuống đ&uacute;ng kh&ocirc;ng n&agrave;o?</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>Tận dụng thời gian 5 gi&acirc;y giữa c&aacute;c bức tranh</strong></h4>\n" +
"<p style=\"text-align: justify;\">Sau khi đọc 4 đ&aacute;p &aacute;n của một bức tranh, sẽ l&agrave; 5 gi&acirc;y nghĩ giữa qu&atilde;ng rồi tới tranh tiếp theo. Trong 5 gi&acirc;y n&agrave;y, bạn cần tận dụng quan s&aacute;t kỹ bức tranh kế tiếp, ph&acirc;n t&iacute;ch v&agrave; ph&aacute;n đo&aacute;n về bối cảnh tranh, c&aacute;c động từ chỉ h&agrave;nh động/ từ chỉ đồ vật trong tranh</p>\n" +
"<h4 style=\"text-align: justify;\"><strong>Đừng bận t&acirc;m tới những c&acirc;u vừa chọn</strong></h4>\n" +
"<p style=\"text-align: justify;\">Sau khi nghe xong 4 đ&aacute;p &aacute;n của 1 bức tranh, d&ugrave; c&oacute; chắc chắn hay kh&ocirc;ng về c&acirc;u trả lời, c&aacute;c bạn n&ecirc;n chọn dứt kho&aacute;t một c&acirc;u m&agrave; m&igrave;nh cho l&agrave; đ&uacute;ng nhất v&agrave; tập trung nghe c&aacute;c đ&aacute;p &aacute;n cho bức tranh tiếp theo.</p>\n" +
"<p style=\"text-align: justify;\">Tr&ecirc;n đ&acirc;y l&agrave; một số kinh nghiệm c&ocirc; chia sẻ với mọi người, để luyện tập c&aacute;c bạn click v&agrave;o <a href=\"https://goo.gl/oRXg7E\"><span style=\"color: #ff0000;\"><strong>Đ&Acirc;Y</strong> </span></a>thực h&agrave;nh v&agrave; download t&agrave;i liệu nh&eacute;.</p>\n" +
"<p style=\"text-align: justify;\">Cuối c&ugrave;ng đừng qu&ecirc;n <strong>LIKE, SHARE, ĐĂNG K&Yacute;</strong> fanpage v&agrave; k&ecirc;nh youtube: <span style=\"color: #ff0000;\"><strong>Tiếng Anh Cho Người Việt</strong></span> để theo d&otilde;i c&aacute;c b&agrave;i học tiếp theo nh&eacute; c&aacute;c em.</p>\n" +
"<p style=\"text-align: justify;\">&nbsp;</p>";

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

                    EmailAction.sendGmail(sendName, mailSend1, mSend.getPassword(), to.getEmail().toLowerCase(), title, content);

                    countMailSentSuccess++;
                    System.out.println("----------------"+mailSend1+"---------------------");
                    System.out.println("----------------"+mailSend1+"---------------------");
                    System.out.println("----------------"+mailSend1+"---------------------");
                    System.out.println("----------------"+mailSend1+"---------------------");
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
