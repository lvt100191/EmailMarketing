/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.entity.MailSend;
import com.mail.marketing.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.mail.internet.AddressException;

/**
 *
 * @author TUNGLV
 */
public class B6MKT {

    static String sttMailSend = "1";
    static String sttMailSent = "2";

    public static void main(String[] args) throws Exception {
        //mail phai ton tai trong bang tbl_mail_send
        String mailSend1 = args[0].trim();
        //test
//        String mailSend1 = "coso4.mshoatoeic@gmail.com";
        //end test
        //lay thong tin mail dung de gui
        MailSend mSend = MailSendDao.getMailSendByEmail(mailSend1);
        //lay danh sach mail nhan
        ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, "100");
        //test
//        ArrayList<Mail> lst = new ArrayList<>();
//        Mail mxx = new Mail();
//        mxx.setEmail("tunglv9x@gmail.com");
//        Mail mxx1 = new Mail();
//        mxx1.setEmail("test.tunglv9x@gmail.com");
//        lst.add(mxx);
//        lst.add(mxx1);
        //end test
        //lay danh sach bai viet theo truong note='FEED_MIX_LIST'
        ArrayList<FeedEntity> feedEntitys = FeedEntityDao.getLstFeedByNote("FEED_MIX_LIST");
        if (feedEntitys != null && feedEntitys.size() > 0) {
            int countMail = 0;
            //duyet tung mail
            for (Mail m : lst) {
                try {
                    //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                    if (checkMailBlock(m.getEmail().trim())) {
                        continue;
                    }
                    //lay bai viet theo countMail
                    int idxFeed = countMail % feedEntitys.size();
                    FeedEntity feedEntity = feedEntitys.get(idxFeed);
                    //gen title, title config trong db phai theo format 
                    //String title = "Tài liệu, Bộ tài liệu, Bí kíp luyện thi|Toeic|cực khủng, cực đỉnh, cực chất, cực HOT";
                    String title = feedEntity.getTitleSend();
                    String[] arr3 = title.split("\\|");
                    String[] p0 = arr3[0].split(",");
                    String[] p1 = arr3[1].split(",");
                    String[] p2 = arr3[2].split(",");
                    List<String> titles = new ArrayList<String>();
                    for (int i = 0; i < p0.length; i++) {
                        for (int j = 0; j < p1.length; j++) {
                            for (int k = 0; k < p2.length; k++) {
                                String strTitle = p0[i] + " " + p1[j] + " " + p2[k];
                                strTitle = strTitle.trim();
                                if (!titles.contains(strTitle)) {
                                    titles.add(strTitle);
                                }
                            }
                        }
                    }
                    //chon title
                    //lay so du
                    int idxTitle = countMail % titles.size();
                    System.out.println("email: " + m + " title: " + titles.get(idxTitle));
                    String titleSend = titles.get(idxTitle);
                    EmailAction.sendGmail(feedEntity.getFanpageName().trim(), mailSend1, mSend.getPassword(), m.getEmail().toLowerCase(), titleSend, feedEntity.getContentSend().trim());
                    //update status mail nhan
                    m.setStatus(Integer.parseInt(sttMailSent));
                    MailDao.updateMail(m);
                    countMail = countMail + 1;
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                    System.out.println("-------------------da gui den:  " + m.getEmail() + "  thanh cong! tong so mail da gui:  " + countMail);
                } catch (AddressException adEx) {
                    System.out.println("-----------------tunglv4 gui toi mail: " + m.getEmail() + " bi loi: " + adEx.getMessage());
                    //tim mail trong bang tbl_mail lay ra id cua ban ghi
                    Mail mx = MailDao.getByEmail(m.getEmail());
                    //xoa ban ghi trong bang tbl_feed_mail theo id
                    if (mx != null) {
                        //xoa ban ghi trong bang tbl_mail
                        MailDao.deleteMail(mx.getId());
                    }
                    //insert vao bang tbl_mail_blocked
                    MailBlock mb = initMailBlock(m.getEmail());
                    MailBlockDao.insert(mb);
                } catch (Exception e) {
                    System.out.println("-----------------tunglv4 gui toi mail: " + m.getEmail() + " bi loi: " + e.getMessage());
                    if (e.getMessage().contains("550 5.4.5")) {
                        MailSendDao.updateMailLastTime(mSend);
                        throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                    }
                } finally {
                }
                MailSendDao.updateMailLastTime(mSend);
            }
        }
        System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx----------------------------------------------");
        System.out.println("--------------CHUONG TRINH GUI MAIL THEO BANG TBL-MAIL KET THUC-----------------");
        System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx----------------------------------------------");

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
}
