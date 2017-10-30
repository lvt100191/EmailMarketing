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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.mail.internet.AddressException;

/**
 *
 * @author PMDVCNTT https://wordtohtml.net/
 */
//su dung 1 mail gui lay ra tu bang tbl_mail_send nhap vao mailSend1, lay ra 100 
//mail trong bang tbl_mail co trang thai la 1 de gui mail
//run cmd: 
public class UseOneMailSendFromCMD {

    //trang thai mail lay ra de gui truong status trong bang tbl_mail
    private static String sttMailSend = "1";
    //update trang thai da gui mail
    private static String sttMailSent = "2";
    //mail gui
    //private static String mailSend1 = "coso4.mshoatoeic@gmail.com";
    //ten nguoi gui, ten nay se hiển thị trên người gửi đến, 
    //nếu không cấu hình sẽ hiển thị địa chỉ mail gửi
    private static String sendName = "Tiếng Anh Cho Người Việt";
    //tieu de mail
    private static String title = "130 CÂU HỎI + 500 CÂU TRẢ LỜI PHỎNG VẤN CHUẨN MỰC  SONG NGỮ ANH - VIỆT";
    //noi dung mail
    private static String content = "<p>Gửi tất cả c&aacute;c bạn link download t&agrave;i liệu nh&eacute;!</p>\n" +
"<p><span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/fdc/1/16/26a0.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">⚠️</span></span>&nbsp;DOWNLOAD&nbsp;<span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/fdc/1/16/26a0.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">⚠️</span></span>&nbsp;130 C&Acirc;U HỎI + 500 C&Acirc;U TRẢ LỜI PHỎNG VẤN CHUẨN MỰC&nbsp;<span class=\"_5mfr _47e3\"><img class=\"img\" role=\"presentation\" src=\"https://www.facebook.com/images/emoji.php/v9/f51/1/16/1f449.png\" alt=\"\" width=\"16\" height=\"16\" /><span class=\"_7oe\">👉</span></span>&nbsp;SONG NGỮ ANH - VIỆT</p>\n" +
"<p>Click v&agrave;o <span style=\"color: #ff0000;\"><strong><a style=\"color: #ff0000;\" href=\"http://bit.ly/2xpTLiE\">Đ&Acirc;Y</a></strong></span> để tải t&agrave;i liệu</p>\n" +
"<p>C&aacute;c bạn nhớ đăng k&yacute; k&ecirc;nh youtube: <span style=\"color: #ff0000;\"><strong><a style=\"color: #ff0000;\" href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\">Tiếng Anh Cho Người Việt</a></strong></span></p>\n" +
"<p>Like Fanpage:&nbsp;<strong><span style=\"color: #0000ff;\"><a style=\"color: #0000ff;\" href=\"https://www.facebook.com/englishforvn/\">Tiếng Anh Cho Người Việt</a></span></strong></p>\n" +
"<p>Để được ưu ti&ecirc;n gửi mail, nhận t&agrave;i liệu, c&aacute;c kh&oacute;a học miễn ph&iacute; d&agrave;nh cho mọi người. Cũng như ủng hộ ch&uacute;ng t&ocirc;i c&oacute; động lực sưu tập v&agrave; gửi&nbsp; đến tất cả mọi người.</p>\n" +
"<p>Admin xin ch&acirc;n trọng cảm ơn!</p>";

    public static void main(String[] args) throws Exception {
        //nhap email gui tu cmd
        String mailSend1 = args[0].trim();
        ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
        int countMailSentSuccess = 0;
        for (MailSend mailSend : lstSend) {
            //chi lay mailSend1 truyen vao de gui lam mail
            if (mailSend.getEmail().trim().equals(mailSend1.trim())) {
                try {
                    //lay danh sach mail gui theo trang thai va so luong mail cho phep gui trong ngay
                    ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(mailSend.getMaxMail()));
                    //test
//                    ArrayList<Mail> lst = new ArrayList<>();
//                    Mail mx = new Mail();
//                    mx.setEmail("tunglv9x@gmail.com");
//                    lst.add(mx);
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
                                System.out.println("----------------So luong mail da gui thanh cong:-------------- " + countMailSentSuccess + "----------------------------");
                                System.out.println("----------------So luong mail da gui thanh cong:-------------- " + countMailSentSuccess + "----------------------------");
                                System.out.println("----------------So luong mail da gui thanh cong:-------------- " + countMailSentSuccess + "----------------------------");
                                System.out.println("----------------So luong mail da gui thanh cong:-------------- " + countMailSentSuccess + "----------------------------");
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
        System.out.println("------------------------CHƯƠNG TRÌNH GỬI GMAIL KẾT THÚC------------------------------");
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
