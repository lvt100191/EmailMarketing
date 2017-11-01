/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.cmd.*;
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
    static String idTblFeed = "163";
    //truong id_feed cua  bang tbl_feed 
    static String idFeed = "233632063512875_712505208958889";
    //mail gui 
    //static String mailSend = "coso7.mshoatoeic@gmail.com";
    //ten nguoi gui, lay gia tri fanpage_name trong bang tbl_feed
    static String sendName = "Elight Learning English";
    //tieu de mail, lay title_send trong bang tbl_feed
    static String title = "Bộ quà tặng dành riêng có các bạn đang lạc lối trên con đường học tiếng Anh và không biết bắt đầu từ đâu";
    //noi dung mail, lay trong content_send ra sửa đổi cho hợp lý, paste vào trang https://wordtohtml.net/ để xem trước
    static String content = "<p style=\"text-align: center;\"><em><strong>C&ograve;n bạn n&agrave;o chưa nhận được t&agrave;i liệu của Elight Learning English kh&ocirc;ng?</strong></em></p>\n" +
"<p style=\"text-align: justify;\">👉 BỘ T&Agrave;I LIỆU \"GỐI ĐẦU GIƯỜNG\" CỦA C&Aacute;C CAO THỦ TIẾNG ANH TỪ CƠ BẢN ĐẾN N&Acirc;NG CAO</p>\n" +
"<p style=\"text-align: justify;\">👉 Bộ t&agrave;i liệu BAO GỒM:<br />1. PHƯƠNG PH&Aacute;P HỌC TIẾNG ANH CHO NGƯỜI MỚI BẮT ĐẦU<br />2. TẤT TẦN TẬT NHỮNG BỘ T&Agrave;I LIỆU \"Đ&Aacute;NG ĐỒNG TIỀN B&Aacute;T GẠO\" m&agrave; bạn c&oacute; thể bỏ c&ocirc;ng sức để học.</p>\n" +
"<p style=\"text-align: justify;\">Nếu chưa nhận được t&agrave;i liệu c&aacute;c bạn click v&ocirc; <span style=\"color: #ff0000; background-color: #ffffff;\"><strong><a style=\"background-color: #ffffff; color: #ff0000;\" href=\"http://bit.ly/2z6n8X1\">Đ&Acirc;Y</a></strong></span> để download t&agrave;i liệu nh&eacute;! C&oacute; rất nhiều t&agrave;i liệu để ch&uacute;ng ta học cả đời m&agrave; kh&ocirc;ng hết.</p>\n" +
"<p style=\"text-align: justify;\"><strong>Ch&uacute; &yacute;</strong>: <span style=\"background-color: #ffff00;\">Danh s&aacute;ch t&agrave;i liệu ở phần m&ocirc; tả của mỗi video</span></p>\n" +
"<p style=\"text-align: justify;\">Đăng k&yacute;, Like , Share k&ecirc;nh Youtube: <a href=\"http://bit.ly/2z6n8X1\"><strong><span style=\"color: #ff0000;\">Tiếng Anh Cho Người Việt</span></strong></a> , Fanpage: <strong><span style=\"color: #0000ff;\"><a style=\"color: #0000ff;\" href=\"http://bit.ly/2iMwgcW\">Tiếng Anh Cho Người Việt</a></span></strong> để theo d&otilde;i cập nhật c&aacute;c t&agrave;i liệu mới nhất từ k&ecirc;nh v&agrave; fanpage nha cả nh&agrave;!</p>\n" +
"<p style=\"text-align: justify;\">Admin cảm ơn c&aacute;c bạn đ&atilde; ủng hộ!</p>";

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
