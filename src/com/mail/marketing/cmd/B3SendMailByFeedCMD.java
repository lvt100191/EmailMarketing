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
 * 
 * */
 //nhap vao id cua bang tbl_feed co so mail chua gui >=100, có title, có content, kiem tra link download tai lieu phai hoat dong
//, lay danh sach email theo 1 bai viet 
 //su dung gmail trong bang tbl_mail_send roi gui mail
 //tìm ra bài viết có số lượng mail chưa gửi nhiều nhất để gửi:
 //select id_tbl_feed, count( *)  from tbl_feed_mail where status=1 group by  id_tbl_feed order by count( *) desc
 //kiem tra so email chua gui cua 1 bai viet:
 //select id_tbl_feed, count( *)  from tbl_feed_mail where status=1 and  id_tbl_feed=127  group by  id_tbl_feed order by count( *) desc
//truyen vao dia chi mail gưi o cmd
//truoc khi clean build sua
//idFeed, idTblFeed, sendName, title, content

public class B3SendMailByFeedCMD {

    //tham so dau vao
    //trang thai chua gui mail theo bai viet trường status trong bang tbl_feed_mail
    static String statusFeedMailSend = "1";
    //trang thai da gui mail theo bai viet trường status trong bang tbl_feed_mail
    static String statusFeedMailSent = "2";
    //so luong toi da mail lay ra de gui trong bang tbl_mail
    static String numMaxMailTo = "100";
    //truoc khi chay thi copy tham so dau vao file B3SendMailByFeedCMDTest de test truoc
    //id cua bang tbl_Feed
    static String idTblFeed = "285";
    //truong id_feed cua  bang tbl_feed 
    static String idFeed = "233632063512875_713964435479633";
    //mail gui 
    //static String mailSend = "coso7.mshoatoeic@gmail.com";
    //ten nguoi gui, lay gia tri fanpage_name trong bang tbl_feed
    static String sendName = "Elight Learning English";
    //tieu de mail, lay title_send trong bang tbl_feed
    static String title = "1000 CÂU GIAO TIẾP HỌC 1 LẦN DÙNG GIAO TIẾP CẢ ĐỜI";
    //noi dung mail, lay trong content_send ra sửa đổi cho hợp lý, paste vào trang https://wordtohtml.net/ để xem trước
    static String content = "<p style=\"text-align: justify;\">C&aacute;c bạn c&oacute; nhớ h&ocirc;m nay l&agrave; ng&agrave;y m&agrave; c&aacute;c bạn phải v&agrave;o <strong>CHECK EMAIL</strong> để nhận t&agrave;i liệu từ Elight Learning English kh&ocirc;ng nhỉ? Đừng qu&ecirc;n nh&eacute;, Ad đang tiến h&agrave;nh lọc Email v&agrave; bắt đầu gửi rồi đấy&nbsp;<img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f6c/1/16/2764.png\" alt=\"\" width=\"16\" height=\"16\" /></p>\n" +
"<p style=\"text-align: justify;\"><span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f51/1/16/1f449.png\" alt=\"\" width=\"16\" height=\"16\" /></span><strong><span style=\"background-color: #ffff00; color: #800000;\">&nbsp;1000 C&Acirc;U GIAO TIẾP \"HỌC 1 LẦN D&Ugrave;NG GIAO TIẾP CẢ ĐỜI\"</span></strong></p>\n" +
"<p style=\"text-align: justify;\">Click v&agrave;o&nbsp;<span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f51/1/16/1f449.png\" alt=\"\" width=\"16\" height=\"16\" /></span>&nbsp;<a href=\"http://bit.ly/2iizyRp\"><strong><span style=\"color: #0000ff;\">Đ&Acirc;Y</span> </strong></a>để tải t&agrave;i liệu, 1000 C&Acirc;U GIAO TIẾP chỉ d&agrave;nh cho <span style=\"color: #ff0000;\"><strong>1000 lượt tải</strong></span> th&ocirc;i nh&eacute;, vượt qu&aacute; 1000 l&agrave; ADMIN sẽ ẩn link nha!</p>\n" +
"<p style=\"text-align: justify;\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/fb0/1/16/1f3af.png\" alt=\"\" width=\"16\" height=\"16\" />&nbsp;<strong><span style=\"color: #ff0000;\">ĐỪNG QU&Ecirc;N</span></strong></p>\n" +
"<p style=\"text-align: justify;\"><strong>Bước 1</strong>: Đăng k&yacute; k&ecirc;nh YOUTUBE: <a href=\"http://bit.ly/2iizyRp\"><strong><span style=\"color: #ff0000;\">Tiếng Anh Cho Người Việt</span></strong></a></p>\n" +
"<p style=\"text-align: justify;\"><strong>Bước 2</strong>: Like,&nbsp; Share&nbsp; &nbsp; FANPAGE:&nbsp;<a href=\"http://bit.ly/2iMwgcW\"><span style=\"color: #3366ff;\"><strong>Tiếng Anh Cho Người Việt</strong></span></a></p>\n" +
"<p style=\"text-align: justify;\">Rất nhiều t&agrave;i liệu sẽ được ưu ti&ecirc;n gửi sớm đến bạn n&agrave;o <strong>thực hiện đủ 2 bước</strong> tr&ecirc;n nh&eacute;</p>\n" +
"<p style=\"text-align: justify;\">Admin xin ch&acirc;n th&agrave;nh cảm ơn c&aacute;c bạn đ&atilde; ủng hộ ch&uacute;ng t&ocirc;i trong suốt thời gian qua</p>\n" +
"<p style=\"text-align: justify;\">Ch&uacute;c c&aacute;c bạn ng&agrave;y mới vui vẻ</p>\n" +
"<p style=\"text-align: justify;\">Ch&acirc;n th&agrave;nh!</p>\n" +
"<p style=\"text-align: justify;\"><span style=\"color: #3366ff;\"><strong>Tiếng Anh Cho Người Việt</strong></span></p>";

    public static void main(String[] args) throws Exception {
        //lay dia chi mail gui truyen vao tu cmd
        String mailSend = args[0].trim();
        //test
        //mail phai thuoc bang tbl_mail_send
        //String mailSend = "coso7.mshoatoeic@gmail.com";
        //end test
        System.out.println("--------dia chi mail gui la: "+ mailSend);
        System.out.println("--------dia chi mail gui la: "+ mailSend);
        System.out.println("--------dia chi mail gui la: "+ mailSend);
        //lay email tu bai viet chua gui mail theo bai viet status_feed_mail=1, moi lan gui lay max 100 mail de gui
        //select * from tbl_mail where id in (select  id_tbl_mail from tbl_feed_mail where id_tbl_feed=38) and status_feed_mail=1 limit 100
        ArrayList<Mail> lst = MailDao.getMailFromTblFeed(idTblFeed, statusFeedMailSend, numMaxMailTo);
        //test
//        ArrayList<Mail> lst = new ArrayList<>();
//        Mail mx = new Mail();
//        mx.setEmail("tunglv9x@gmail.com");
//        lst.add(mx);
        //lay ra thong tin bai viet tu bang tbl_feed
        FeedEntity feedEntity = FeedEntityDao.getByFeed(idFeed);
        //lay tieu de, noi dung, link tai lieu o bang tbl_feed cua feed de gui mail
        //String title = feedEntity.getTitleSend();
        //String content = feedEntity.getContentSend();
        System.out.println("--------- Gui mail den danh sach gom: "+lst.size()+ " email!");
        System.out.println("--------- Gui mail den danh sach gom: "+lst.size()+ " email!");
        System.out.println("--------- Gui mail den danh sach gom: "+lst.size()+ " email!");
        //test
        //sendMail(feedEntity.getId(), sendName, mailSend, title, content, lst, statusFeedMailSent);
        //end test
        if(lst.size()>=100){
            sendMail(feedEntity.getId(), sendName, mailSend, title, content, lst, statusFeedMailSent);
        }else{
            System.out.println("--------chua co du 100 email de gui, so email hien tai cua bai viet chua gui la: "+lst.size());
        }

    }

    private static void sendMail(int idTblFeed, String sendName, String mailSend, String title, String content, ArrayList<Mail> lst, String statusFeedMailSent) throws Exception {
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
                System.out.println("---------------- ------tunglv4 gui mail " + mSend.getEmail() + " tu host " + mSend.getHostMail() + " toi: " + to.getEmail() + " thanh cong");
                countMailSentSuccess++;
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
                System.out.println("-----------------------tong so mail da gui la: " + countMailSentSuccess);
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
        //gui het 100 mail thi update trang thai mail gui
        MailSendDao.updateMailLastTime(mSend);
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
