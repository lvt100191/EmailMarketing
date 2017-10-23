/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_email;

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

/**
 *
 * @author PMDVCNTT
 * https://wordtohtml.net/
 */
//dung de quang ba bai viet tren fanpage
//nhap vao 1 dia chi mail gui, gui den cac mail thu thap trong bang tbl_mail
//chu y co khoang 9-10 mail chan thi se bi chan gui mail ban ra loi 550 5.4.5 Daily user sending quota exceeded
//mot buoi sang gui duoc 4 mail tu 8h den 16h  gui dc tiep 2 email, thi ko gap loi quota
//theo doi test xem sau bao lau gui dc tiep

public class SendUseOneMailSend {
    //trang thai mail lay ra de gui truong status trong bang tbl_mail
    private static String sttMailSend = "1";
    //update trang thai da gui mail
    private static String sttMailSent = "2";
    private static String mailSend1 = "coso9.mshoatoeic@gmail.com";
    private static String title = "Giới từ là phần ngữ pháp không nên bỏ qua trong tiếng anh!";
    private static String content = "<p>Đừng nghĩ giới từ kh&ocirc;ng quan trọng, l&uacute;c n&agrave;o n&oacute; cũng xuất hiện trong phần ngữ ph&aacute;p của c&aacute;c b&agrave;i thi tiếng anh. C&ocirc; xin gửi đến c&aacute;c bạn tất tần tật t&agrave;i liệu về giới từ.T&agrave;i liệu sẽ được bổ sung ng&agrave;y một phong ph&uacute; đa dạng. C&aacute;c bạn&nbsp;<strong>Like</strong>&nbsp;v&agrave;&nbsp;<strong>đăng k&yacute;</strong>&nbsp;k&ecirc;nh Youtube&nbsp; để c&ugrave;ng nhau học tốt. Lượng like v&agrave; đăng k&yacute; k&ecirc;nh youtube Tiếng Anh Cho Người Việt c&agrave;ng nhiều th&igrave; t&agrave;i liệu sẽ được bổ sung nhiều hơn nha c&aacute;c em.</p>\n" +
"<p>Cảm ơn c&aacute;c bạn đ&atilde; ủng hộ c&ocirc; trong suốt thời gian qua!</p>\n" +
"<p>Link download t&agrave;i liệu: Tiếng Anh Cho Người Việt:&nbsp;<a class=\"m_3864711229718116219yt-simple-endpoint m_3864711229718116219style-scope m_3864711229718116219yt-formatted-string\" href=\"https://www.youtube.com/redirect?v=TprwZsCjmkc&amp;redir_token=zjtGDNfgWg5g-uT2wonRmNxVYqd8MTUwODgwMjc1OUAxNTA4NzE2MzU5&amp;event=video_description&amp;q=https%3A%2F%2Fgoo.gl%2FTpHT7n\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?hl=en&amp;q=https://www.youtube.com/redirect?v%3DTprwZsCjmkc%26redir_token%3DzjtGDNfgWg5g-uT2wonRmNxVYqd8MTUwODgwMjc1OUAxNTA4NzE2MzU5%26event%3Dvideo_description%26q%3Dhttps%253A%252F%252Fgoo.gl%252FTpHT7n&amp;source=gmail&amp;ust=1508836426296000&amp;usg=AFQjCNGL_CYtkbY-mXjYzddMgwVr8p-UBA\">https://goo.gl/TpHT7n</a></p>\n" +
"<p>Ms Hoa!</p>";

    public static void main(String[] args) throws Exception {
        ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
        int countMailSentSuccess =0;
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
                                //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                                if(checkMailBlock(to.getEmail().trim())){
                                    continue; 
                                }
                                
                                if (mailSend.getHostMail().equals(Mail.GMAIL_HOST)) {
                                    EmailAction.sendGmail(mailSend.getEmail(), mailSend.getPassword(), to.getEmail(), title, content);

                                }
                                System.out.println("---------------- tunglv4 gui mail " + mailSend.getEmail() + " tu host " + mailSend.getHostMail() + " toi: " + to.getEmail() + " thanh cong");
                                countMailSentSuccess ++;
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
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("------------------------CHƯƠNG TRÌNH GỬI MAIL KẾT THÚC------------------------------");
        System.out.println("------------------------------------------------------------------------------------");
        System.out.println("-------------------Đã gửi đến thành công: "+ countMailSentSuccess+" email!----------");
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
        if(Integer.parseInt(curDate) - Integer.parseInt(endDate) >= 2){
            return true;
        }
        return false;
    }

}
