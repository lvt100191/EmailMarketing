/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.FeedMail;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.entity.MailSend;
import com.mail.marketing.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.internet.AddressException;

/**
 *
 * @author TUNGLV
 */
//nhap vao id cua bang tbl_feed, lay danh sach email theo 1 bai viet roi gui mail
public class SendMailByFeed {

    //tham so dau vao
    //truong id_feed cua  bang tbl_feed đảm bảo  title_send !=null, content_send !=null
    static String idFeed = "612637105494489_1494612213963636";
    //id cua bang tbl_Feed
    static String idTblFeed="127";
    //mail gui 
    static String mailSend = "coso5.mshoatoeic@gmail.com";

    //trang thai chua gui mail theo bai viet
    static String statusFeedMailSend = "1";
    //trang thai da gui mail theo bai viet
    static String statusFeedMailSent = "2";
    //so luong toi da mail lay ra de gui trong bang tbl_mail
    static String numMaxMailTo = "100";

    public static void main(String[] args) throws Exception {
        //lay email tu bai viet chua gui mail theo bai viet status_feed_mail=1, moi lan gui lay max 100 mail de gui
        //select * from tbl_mail where id in (select  id_tbl_mail from tbl_feed_mail where id_tbl_feed=38) and status_feed_mail=1 limit 100
        ArrayList<Mail> lst = MailDao.getMailFromTblFeed(idTblFeed, statusFeedMailSend, numMaxMailTo);
                            //test
//                    ArrayList<Mail> lst = new ArrayList<>();
//                    Mail mx = new Mail();
//                    mx.setEmail("tunglv9x@gmail.com");
//                    lst.add(mx);
//lay ra thong tin bai viet tu bang tbl_feed
        FeedEntity feedEntity = FeedEntityDao.getByFeed(idFeed);
        //lay tieu de, noi dung, link tai lieu o bang tbl_feed cua feed de gui mail
        //String title = feedEntity.getTitleSend();
        //String content = feedEntity.getContentSend();
        String title = "TỔNG HỢP 9000 BÀI HỌC ĐỦ GIAO TIẾP CẢ ĐỜI TIẾNG ANH\n" +
                       "(Full bản PDF + Video giáo viên bản ngữ đọc chuẩn Tây)";
        String content = "<p style=\"text-align: justify;\"><span style=\"color: #0000ff;\"><strong>Tiếng Anh giao tiếp Langmaster</strong> </span>gửi đến c&aacute;c bạn&nbsp;</p>\n" +
"<p style=\"text-align: justify;\">TỔNG HỢP 9000 B&Agrave;I HỌC ĐỦ GIAO TIẾP CẢ ĐỜI TIẾNG ANH (Full bản PDF + Video gi&aacute;o vi&ecirc;n bản ngữ đọc chuẩn T&acirc;y)</p>\n" +
"<p style=\"text-align: justify;\">C&aacute;c bạn click v&agrave;o <a href=\"http://bit.ly/2xpTLiE\"><span style=\"color: #0000ff;\"><strong>Đ&Acirc;Y</strong></span> </a>để tải t&agrave;i liệu nh&eacute;. Nhanh tay download để kh&ocirc;ng bị bỏ lỡ cơ hội nhận trọn bộ t&agrave;i liệu qu&yacute; gi&aacute; n&agrave;y.</p>\n" +
"<p style=\"text-align: justify;\">Nhớ đăng k&yacute; k&ecirc;nh Youtube: <a href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\"><span style=\"color: #ff0000;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a></p>\n" +
"<p style=\"text-align: justify;\">Like fanpage:&nbsp;&nbsp;<a href=\"https://www.facebook.com/englishforvn/\"><span style=\"color: #0000ff;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a></p>\n" +
"<p style=\"text-align: justify;\">Để được ưu ti&ecirc;n gửi kh&oacute;a học miễn ph&iacute;, t&agrave;i liệu sớm nhất.</p>\n" +
"<p style=\"text-align: justify;\">Ch&uacute;ng t&ocirc;i ch&acirc;n th&agrave;nh cảm ơn!</p>";
        //ten nguoi gui
        String sendName = "Tiếng Anh giao tiếp Langmaster";
        sendMail(feedEntity.getId(),sendName, mailSend, title, content, lst, statusFeedMailSent);

    }

    private static void sendMail(int idTblFeed,String sendName, String mailSend, String title, String content, ArrayList<Mail> lst, String statusFeedMailSent) throws Exception {
        //lay thong tin mail gui
        MailSend mSend = MailSendDao.getMailSendByEmail(mailSend);
        int countMailSentSuccess = 0;
        for (Mail to : lst) {
            try {
                if (mSend.getMailBlocked() != null && !mSend.getMailBlocked().isEmpty() && mSend.getMailBlocked().contains(to.getEmail().trim())) {
                    continue;
                }
                //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                if (checkMailBlock(to.getEmail().trim())) {
                    continue;
                }

                if (mSend.getHostMail().equals(Mail.GMAIL_HOST)) {
                    EmailAction.sendGmail(sendName, mSend.getEmail(), mSend.getPassword(), to.getEmail().toLowerCase(), title, content);

                }
                System.out.println("---------------- tunglv4 gui mail " + mSend.getEmail() + " tu host " + mSend.getHostMail() + " toi: " + to.getEmail() + " thanh cong");
                countMailSentSuccess++;
                //update status=2 tbl_feed_mail da gui
                FeedMail fm = FeedMailDao.getByIdTblFeedIdTblMail(idTblFeed, to.getId());
                fm.setStatus(Integer.parseInt(statusFeedMailSent));
                FeedMailDao.updateStatus(fm);

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
//                            if (e.getMessage().contains("554 5.2.0")) {
//                                continue;
//                            }
                if (e.getMessage().contains("550 5.4.5")) {
                    MailSendDao.updateMailLastTime(mSend);
                    throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                }

            } finally {

            }
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
