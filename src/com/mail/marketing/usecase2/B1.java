/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase2;

import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailSend;
import com.mail.marketing.mail.EmailAction;
import com.mail.marketing.ui.SendEmail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PMDVCNTT
 */
public class B1 {

    private static String sttMailSend = "1";
    private static String sttMailSent = "2";
    private static String mailSend1 = "coso8.mshoatoeic@gmail.com";
    private static String title = "Có khi nào giới từ đứng một mình?";
    private static String content = "<p>Hệ thống mail bị lỗi n&ecirc;n c&ocirc; gửi lại&nbsp; cho những bạn n&agrave;o chưa nhận được</p>\n"
            + "<p>Bạn n&agrave;o đ&atilde; nhận rồi th&igrave; đừng tr&aacute;ch c&ocirc; spam mail nha!</p>\n"
            + "<p>Sorry c&aacute;c em!</p>\n"
            + "<p>C&aacute;c em c&oacute; thể xem ở link sau:</p>\n"
            + "<p><a href=\"http://bit.ly/2zgOhTA\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=http://bit.ly/2zgOhTA&amp;source=gmail&amp;ust=1508333958814000&amp;usg=AFQjCNFpbiL0oNjyzJqL5Ni4s3aGjD0N2w\">http://bit.ly/2zgOhTA</a>&nbsp;</p>\n"
            + "<p>Hoặc theo d&otilde;i tr&ecirc;n</p>\n"
            + "<p>Facebook:&nbsp;<a href=\"https://www.facebook.com/permalink.php?story_fbid=287001431800193&amp;id=275158636317806\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.facebook.com/permalink.php?story_fbid%3D287001431800193%26id%3D275158636317806&amp;source=gmail&amp;ust=1508333958814000&amp;usg=AFQjCNGiRLAeszjozi_1wxwRthUEQN-SKQ\">Tiếng Anh Cho Người Việt</a></p>\n"
            + "<p>YouTube :&nbsp;<a href=\"https://www.youtube.com/watch?v=TprwZsCjmkc\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=vi&amp;q=https://www.youtube.com/watch?v%3DTprwZsCjmkc&amp;source=gmail&amp;ust=1508333958814000&amp;usg=AFQjCNF4aSsecEeTL6HzzRQ9KWqpHhzDQw\">Tiếng Anh Cho Người Việt</a></p>\n"
            + "<p>C&aacute;c em nhớ like FB v&agrave; k&ecirc;nh YT nh&eacute;, sắp tới c&ocirc; sẽ gửi mail tới c&aacute;c em trọn bọn ngữ ph&aacute;p tiếng anh.</p>";

    public static void main(String[] args) throws Exception {
        ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
        for (MailSend mailSend : lstSend) {
            //chi lay mailSend1 truyen vao de gui lam mail
            if (mailSend.getEmail().trim().equals(mailSend1)) {
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
                                if (mailSend.getHostMail().equals(Mail.GMAIL_HOST)) {
                                    EmailAction.sendGmail(mailSend.getEmail(), mailSend.getPassword(), to.getEmail(), title, content);

                                }
                                System.out.println("---------------- tunglv4 gui mail " + mailSend.getEmail() + " tu host " + mailSend.getHostMail() + " toi: " + to.getEmail() + " thanh cong");
                                //update status mail nhan
                                to.setStatus(Integer.parseInt(sttMailSent));
                                MailDao.updateMail(to);

                            } catch (Exception e) {
                                System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + e.getMessage());
//                            if (e.getMessage().contains("554 5.2.0")) {
//                                continue;
//                            }
                                if (e.getMessage().contains("550 5.4.5")) {
                                    MailSendDao.updateMailLastTime(mailSend);
                                    throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                                }
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
        System.out.println("------------------------Chuong trinh ket thuc------------------------------");
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
        return false;
    }

}
