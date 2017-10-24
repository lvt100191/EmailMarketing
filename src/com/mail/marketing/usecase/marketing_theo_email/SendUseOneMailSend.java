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
    
    private static String mailSend1 = "hoa.ms.toeic@gmail.com";

    private static String title = "Cuốn sách luyện tập về giới từ không thể bỏ qua";
    private static String content = "<p>C&ocirc; gửi c&aacute;c em cuốn s&aacute;ch c&ocirc; sưu tập được luyện tập về giới từ nh&eacute;, c&aacute;c em nhanh tay click v&agrave;o video rồi lấy link download ở phần m&ocirc; tả video, nhớ l&agrave; nhanh tay nh&eacute; v&igrave; c&ocirc; sẽ kh&ocirc;ng chia sẻ link l&acirc;u đ&acirc;u. Bạn n&agrave;o chậm tay m&agrave; chưa download được t&agrave;i liệu th&igrave; ch&uacute;c c&aacute;c em may mắn lần sau nha!</p>\n" +
"<p><a href=\"https://www.youtube.com/watch?v=TprwZsCjmkc\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.youtube.com/watch?v%3DTprwZsCjmkc&amp;source=gmail&amp;ust=1508932165457000&amp;usg=AFQjCNGiRVojwzXateNmtZPcrsXJ0F2NSA\"><strong>Tiếng Anh Cho Người Việt - Giới từ (Preposition )</strong></a></p>\n" +
"<p>Nhớ đăng k&yacute; k&ecirc;nh youtube v&agrave; like fanpage:&nbsp;<strong><a href=\"https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.youtube.com/channel/UC3GSyCJ2C2AQBmvJa8J8x8Q&amp;source=gmail&amp;ust=1508932165457000&amp;usg=AFQjCNEDd5N_WE8eNPg1HFMxZJ5JrZN8Fw\">Tiếng Anh Cho Người Việt</a>&nbsp;</strong>để nhận được c&aacute;c t&agrave;i liệu tiếp theo.</p>\n" +
"<p>Cảm ơn c&aacute;c em đ&atilde; ủng hộ c&ocirc; trong thời gian qua</p>\n" +
"<p>Ms.Hoa!</p>";
    public static void main(String[] args) throws Exception {
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
//                    Mail m = new Mail();
//                    m.setEmail("tunglv9x@gmail.com");
//                    lst.add(m);
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
                                    EmailAction.sendGmail(mailSend.getEmail(), mailSend.getPassword(), to.getEmail().toLowerCase(), title, content);

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
