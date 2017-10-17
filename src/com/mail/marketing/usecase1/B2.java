/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase1;

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
 * @author TUNGLV
 */
public class B2 {

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

    private static void sendMail(ArrayList<MailSend> lstSend, String title, String content, String sttMailSend, String sttMailSent) {
        for (MailSend mailSend : lstSend) {
            try {
                //lay danh sach mail gui theo trang thai va so luong mail cho phep gui trong ngay
                //gmail dang config gui 100 mail moi ngay
                ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(mailSend.getMaxMail()));

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

    public static void main(String[] args) {
        //tham so dau vao
        //nhập tiêu đề mail
        String title = "";
        //nhập nội dung mail vao trang https://wordtohtml.net/ de soan noi dung
        //noi dung copy bai o B1, va them link cua bai tren fanpage
        String content = "";
        //trạng  thái lấy ra mail gui trong bang TBL_MAIL
        String sttMailSend = "1";
        //trang thái đã gửi mail update trường status trong bảng TBL_MAIL
        String sttMailSent = "2";
        try {
            //lay danh sach mail de gui quang ba
            ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
            //gui mail den danh sach lay ra
            sendMail(lstSend, title, content, sttMailSend, sttMailSent);

        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
