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
    
    //id cua bang tbl_Feed
    static String idTblFeed = "122";
    //truong id_feed cua  bang tbl_feed 
    static String idFeed = "612637105494489_1495485107209680";
    //mail gui 
    //static String mailSend = "coso7.mshoatoeic@gmail.com";
    //ten nguoi gui, lay gia tri fanpage_name trong bang tbl_feed
    static String sendName = "Tiếng Anh giao tiếp Langmaster";
    //tieu de mail, lay title_send trong bang tbl_feed
    static String title = "Toàn bộ 1000 bài học này + Ví dụ minh họa rõ ràng nhất";
    //noi dung mail, lay trong content_send ra sửa đổi cho hợp lý, paste vào trang https://wordtohtml.net/ để xem trước
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

    public static void main(String[] args) throws Exception {
        //lay dia chi mail gui truyen vao tu cmd
        String mailSend = args[0].trim();
        //test
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
