/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.db.FaceBookDao;
import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.FaceBook;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.facebook.dto.Comment;
import com.mail.marketing.facebook.dto.Feed;
import com.mail.marketing.facebook.dto.Page;
import com.mail.marketing.facebook.usecase.FanPageAction;
import com.mail.marketing.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TUNGLV
 */
public class ExtractMailByFeed {

    //tham so truyen vao
    private static String token = "";
    //so luong ban ghi lay ra tu bang tbl_feed
    private static String numFeed = "100";

    public static void main(String[] args) throws Exception {
        FanPageAction fanPageAction = new FanPageAction();
        //lay danh sach bai viet tu bang tbl_feed theo so luong truyen vao
        ArrayList<FeedEntity> lstFeed = FeedEntityDao.getListFeedEntity(numFeed);
        //lay danh sach binh luan theo bai dang
        String mail = null;
        for (FeedEntity f : lstFeed) {
            ArrayList<Comment> comments = fanPageAction.getComments(token, f.getIdFeed());
            for (Comment c : comments) {
                //lay noi dung binh luan
                String comment = c.getContentComment();
                //chi thu thap gmail
                if (comment.contains("@gmail.com")) {
                    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(comment);
                    while (m.find()) {
                        try {
                            mail = m.group();
                            char end = mail.charAt(mail.length() - 1);
                            if (end == '.') {
                                mail = mail.substring(0, mail.length() - 1);
                            }
                            //kiem tra dieu kien truoc khi insert vao db
                            if (checkMailBeforeInsert(mail)) {
                                //khoi tao doi tuong mail
                                Mail email = initMail(mail);
                                //insert vao bang tbl_mail
                                MailDao.insert(email);
                                //insert vao bang tbl_feed_mail

                            }

                        } catch (Exception e) {

                        }
                    }
                }

            }

        }
    }

    private static boolean checkMailBeforeInsert(String mail) throws Exception {
        //ko insert mail đã tồn tại trong danh sách mail chặn tbl_mail_block
        MailBlock mailBlock = MailBlockDao.getByEmail(mail);
        if (mailBlock != null) {
            return false;
        }
        //ko insert mail đã tồn tại trong bảng tbl_mail
        if (EmailAction.checkMailExisted(mail)) {
            return false;
        }
        return true;
    }

    private static Mail initMail(String mail) {
        Mail email = new Mail();
        email.setEmail(mail);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        email.setCreateDate(dateCreate);
        email.setStatus(Mail.STATUS_INSERT);
        email.setStatusFeedMail(Mail.STATUS_INSERT);
        return email;
    }

}
