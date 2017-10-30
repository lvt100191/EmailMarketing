/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.db.FaceBookDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.FaceBook;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.facebook.dto.Comment;
import com.mail.marketing.facebook.dto.Feed;
import com.mail.marketing.facebook.dto.Page;
import com.mail.marketing.facebook.usecase.FanPageAction;
import com.mail.marketing.mail.EmailAction;
import com.mail.marketing.ui.ExtractMail;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TUNGLV
 */
//duyet qua cac fanpgae trong bang tbl_facebook
//lay ra danh sach bai viet theo ngay truyen vao
//thu thap email tư cac binh luan cua bai viet
//cmd run moi tham so cach nhau boi dau cach, thay gia tri ngay va token: 
//java -jar ExtractMailFromFanPageCMD.jar FANPAGE 2017-10-27 token20171027
//tam thoi khong chay nua
public class ExtractMailFromFanPageCMD {

    public static void main(String[] args) {
        //type: FANPAGE
        String type = args[0].trim();
        System.out.println("--------------type: " + type + "-----------------");
        System.out.println("--------------type: " + type + "-----------------");
        System.out.println("--------------type: " + type + "-----------------");
        //thu thap bai viet dang tu ngay truyen vao den ngay hien tai yyyy-MM-dd, 2017-10-27
        String fromDateUI = args[1].trim();
        System.out.println("--------------fromDateUI: " + fromDateUI + "------------------");
        System.out.println("--------------fromDateUI: " + fromDateUI + "------------------");
        System.out.println("--------------fromDateUI: " + fromDateUI + "------------------");
        // token
        String token = args[2];
        System.out.println("--------------token: " + token + "---------------");
        System.out.println("--------------token: " + token + "---------------");
        System.out.println("--------------token: " + token + "---------------");
        //xoa du lieu trong bang neu co
        if (type.equals(FaceBook.TYPE_FANPAGE)) {
            try {
                //lay danh sach FaceBook co type FANPAGE
                ArrayList<FaceBook> lst = FaceBookDao.getListFaceBook(FaceBook.TYPE_FANPAGE);
                FanPageAction fanPageAction = new FanPageAction();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fromDate = sdf.parse(fromDateUI);
                for (FaceBook fg : lst) {
                    //lay thong tin trang
                    Page page = fanPageAction.getPageInfoById(token, fg.getIdFacebook());
                    //lay danh sach bai da dang tu ngay fromDate truyen vao den hien tai
                    ArrayList<Feed> lstFeed = fanPageAction.getFeed(token, page.getId(), fromDate);
                    //lay danh sach binh luan theo bai dang
                    String mail = null;
                    for (Feed f : lstFeed) {
                        ArrayList<Comment> comments = fanPageAction.getComments(token, f.getId());
                        for (Comment c : comments) {
                            //neu co email thi gui mail
                            String comment = c.getContentComment();
                            if (comment.contains("@gmail.com")) {
                                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(comment);
                                while (m.find()) {
                                    try {
                                        mail = m.group();
                                        char end = mail.charAt(mail.length() - 1);
                                        if (end == '.') {
                                            mail = mail.substring(0, mail.length() - 1);
                                        }
                                        if (checkAddressMail(mail)) {
                                            if (mail.trim().contains("@gmail.com") && !EmailAction.checkMailExisted(mail)) {
                                                if (!checkMailBlock(mail)) {
                                                    Mail email = initMail(mail);
                                                    MailDao.insert(email);
                                                    System.out.println("thu thap duoc email: " + mail + " va insert vao bang tbl_mail");
                                                }

                                            }
                                        }

                                    } catch (Exception e) {

                                    }
                                }
                            }

                        }

                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(ExtractMail.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("thu thap dia chi mail tu fanpage thanh cong");
        }
        if (type.equals(FaceBook.TYPE_GROUP)) { //thu thap mail tu group

        }
    }

    private static boolean checkAddressMail(String mail) {
        if (mail.contains("@gmail.con")) {
            return false;
        }
        if (mail.contains("@gmail.com.")) {
            return false;
        }
        if (mail.contains("@gamil.com")) {
            return false;
        }
        if (mail.startsWith("_")) {
            return false;
        }
        return true;
    }

    //check dia chi mail co bi chan hay chua
    //true: da chan
    //false:chua chan
    private static boolean checkMailBlock(String mail) throws Exception {
        //ko insert mail đã tồn tại trong danh sách mail chặn tbl_mail_block
        MailBlock mailBlock = MailBlockDao.getByEmail(mail);
        if (mailBlock != null) {
            return true;
        }
        return false;
    }

    //khoi tao doi tuong mail
    private static Mail initMail(String mail) {
        Mail email = new Mail();
        email.setEmail(mail.toLowerCase());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        email.setCreateDate(dateCreate);
        email.setStatus(Mail.STATUS_INSERT);
        return email;
    }
}
