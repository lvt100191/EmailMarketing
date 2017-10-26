/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_email;

import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
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
 * @author PMDVCNTT https://wordtohtml.net/
 */
//dung de quang ba bai viet tren fanpage
//nhap vao 1 dia chi mail gui, gui den cac mail thu thap trong bang tbl_mail
//chu y co khoang 9-10 mail chan thi se bi chan gui mail ban ra loi 550 5.4.5 Daily user sending quota exceeded
//thuong  gui duoc 4 mail lien tiep, gui den mail thu 5 bi loi 550 5.4.5 Daily user sending quota 
//sang gui 4 mail, chieu 4 mail toi 4 mail
public class SendUseOneMailSend {

    //trang thai mail lay ra de gui truong status trong bang tbl_mail
    private static String sttMailSend = "1";
    //update trang thai da gui mail
    private static String sttMailSent = "2";
    
    private static String mailSend1 = "coso9.mshoatoeic@gmail.com";
    //ten nguoi gui
      private static String sendName = "Ms Hoa Toeic - Sứ giả truyền cảm hứng";

    private static String title = "Nhanh tay đăng ký 2 khóa học miễn phí từ Ms Hoa Toeic - Hôm nay là ngày cuối chốt danh sách";
    private static String content ="<table style=\"height: 263px;\" width=\"722\">\n" +
"<tbody>\n" +
"<tr style=\"height: 120px;\">\n" +
"<td style=\"width: 712px; height: 120px; background-color: #d3ed74;\">\n" +
"<p style=\"text-align: center;\"><span style=\"color: #800000;\"><strong>KH&Oacute;A HỌC TOEIC MIỄN PH&Iacute;</strong></span></p>\n" +
"<p style=\"text-align: left;\"><strong><span style=\"color: #ff0000;\">Qu&agrave; khủng</span> </strong>m&agrave; c&ocirc; hứa tặng c&aacute;c em, gồm 2 kh&oacute;a học:</p>\n" +
"<p style=\"text-align: left;\">1. Kh&oacute;a Online MIỄN PH&Iacute; TOEIC 4 kỹ năng 10 buổi&nbsp;<br />2. Lớp bổ trợ TOEIC Speaking &amp; Writing miễn ph&iacute; chiều thứ 7 h&agrave;ng tuần cho học vi&ecirc;n của trung t&acirc;m</p>\n" +
"<p style=\"text-align: left;\">Đều được ra mắt cả rồi ^^. C&ograve;n bạn n&agrave;o bị lỡ m&agrave; chưa nhận được kh&ocirc;ng :3.&nbsp;</p>\n" +
"</td>\n" +
"</tr>\n" +
"<tr style=\"height: 60px;\">\n" +
"<td style=\"width: 712px; height: 60px; background-color: #e6d8d8;\">\n" +
"<p style=\"text-align: justify;\">C&aacute;c em nhanh tay điền v&agrave;o mẫu đăng k&yacute;, trung t&acirc;m sẽ li&ecirc;n hệ hướng dẫn c&aacute;c em tham gia kh&oacute;a học, lưu &yacute; điền đ&uacute;ng địa chỉ v&agrave; số điện thoại để được học tại cơ sở gần nhất, v&igrave; số lượng c&oacute; hạn n&ecirc;n bạn n&agrave;o chưa nhanh tay th&igrave; hẹn c&aacute;c em v&agrave;o dịp kh&aacute;c nha!&nbsp;C&ocirc; sẽ <strong>chốt danh s&aacute;ch</strong> v&agrave;o <span style=\"color: #ff0000;\"><strong>18</strong> </span>giờ ng&agrave;y h&ocirc;m nay nh&eacute;!(<strong>26/10/2017</strong>)</p>\n" +
"<p style=\"text-align: justify;\">C&aacute;c em click v&agrave;o <span style=\"color: #0000ff;\"><strong><a style=\"color: #0000ff;\" href=\"http://bit.ly/2yQsR35\">Đ&Acirc;Y</a></strong></span> để đăng k&yacute; kh&oacute;a học nh&eacute;!</p>\n" +
"<p style=\"text-align: justify;\">Bạn n&agrave;o thực hiện</p>\n" +
"<p style=\"text-align: justify;\">Đăng k&yacute; k&ecirc;nh youtube:&nbsp;<span style=\"color: #ff0000;\"><strong><a style=\"color: #ff0000;\" href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q&amp;source=gmail&amp;ust=1509063884202000&amp;usg=AFQjCNE3ThiiKML9xG0CvmBbdlJsD5p5zQ\">Tiếng Anh Cho Người Việt</a></strong></span></p>\n" +
"<p style=\"text-align: justify;\">Like fanpage:&nbsp;<strong><a href=\"https://www.facebook.com/englishforvn/\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.facebook.com/englishforvn/&amp;source=gmail&amp;ust=1509063884202000&amp;usg=AFQjCNF9cRtKl7eGUxCiIxnmMA3LWSWO4Q\">Tiếng Anh Cho Người Việt</a></strong></p>\n" +
"<p style=\"text-align: justify;\"><strong>Sẽ được ưu ti&ecirc;n li&ecirc;n hệ trước nha</strong></p>\n" +
"<p style=\"text-align: justify;\"><strong><span style=\"font-size: 16pt;\">Ms.Hoa!</span></strong></p>\n" +
"</td>\n" +
"</tr>\n" +
"</tbody>\n" +
"</table>";

    public static void main(String[] args) throws Exception {
        ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
        int countMailSentSuccess = 0;
        for (MailSend mailSend : lstSend) {
            //chi lay mailSend1 truyen vao de gui lam mail
            if (mailSend.getEmail().trim().equals(mailSend1.trim())) {
                try {
                    //lay danh sach mail gui theo trang thai va so luong mail cho phep gui trong ngay
                    //ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(mailSend.getMaxMail()));
                    //test
                    ArrayList<Mail> lst = new ArrayList<>();
                    Mail mx = new Mail();
                    mx.setEmail("tunglv9x@gmail.com");
                    lst.add(mx);
                    //kiem tra thoi gian hien tai co thoa man gui mail khong
                    //checkTime = true roi vao truong hop mailSend.getLastTime()=null va khac "" 
                    boolean checkTime = true;
                    if (mailSend.getLastTime() != null && !mailSend.getLastTime().isEmpty()) {
                        checkTime = checkRunTime(mailSend.getLastTime());
                    }

                    if (checkTime) {
                        for (Mail to : lst) {
                            try {
                                if (mailSend.getMailBlocked() != null && !mailSend.getMailBlocked().isEmpty() && mailSend.getMailBlocked().contains(to.getEmail().trim())) {
                                    continue;
                                }
                                //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                                if (checkMailBlock(to.getEmail().trim())) {
                                    continue;
                                }

                                if (mailSend.getHostMail().equals(Mail.GMAIL_HOST)) {
                                    EmailAction.sendGmail(sendName, mailSend.getEmail(), mailSend.getPassword(), to.getEmail().toLowerCase(), title, content);

                                }
                                System.out.println("---------------- tunglv4 gui mail " + mailSend.getEmail() + " tu host " + mailSend.getHostMail() + " toi: " + to.getEmail() + " thanh cong");
                                countMailSentSuccess++;
                                //update status mail nhan
                                to.setStatus(Integer.parseInt(sttMailSent));
                                MailDao.updateMail(to);

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
                                    MailSendDao.updateMailLastTime(mailSend);
                                    throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                                }

                            } finally {

                            }
                        }
                        //update thoi gian mail gui
                        //kiem tra phai co mail gui thi moi update thoi gian, chua them dieu kien
                        //so mail da gui phai =so mail max config trong db
                        if (lst.size() > 0) {
                            MailSendDao.updateMailLastTime(mailSend);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(mailSend.getEmail() + " : " + e.getMessage());
                    continue;
                }
            }
        }
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("------------------------CHƯƠNG TRÌNH GỬI MAIL KẾT THÚC------------------------------");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("-------------------Đã gửi đến thành công: " + countMailSentSuccess + " email!----------");
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

    private static boolean checkRunTime(String lastDate) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //lay ra yyyyMMdd
        String endDate = lastDate.trim().substring(0, 8);
        //lay ra HHmmss
        String endTime = lastDate.trim().substring(8, 14);
        //lay thong tin thoi gian hien tai
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currDate = sdf.format(currentDate);
        //lay ra yyyyMMdd thoi gian hien tai
        String curDate = currDate.trim().substring(0, 8);
        //lay ra HHmmss thoi gian hien tai
        String curTime = currDate.trim().substring(8, 14);
        //dieu kien de chay la ngay hien tai phai khac ngay gui cuoi cung
        //thoi gian hien tai phai lon hon thoi gian gui cuoi cung
        //chua xu ly dc hqua chay 21h xong hnay qua 12h no la 00 nen ko chay
        if (!endDate.equals(curDate) && Integer.parseInt(curTime) > Integer.parseInt(endTime)) {
            return true;
        }
        if (Integer.parseInt(curDate) - Integer.parseInt(endDate) >= 2) {
            return true;
        }
        return false;
    }

}
