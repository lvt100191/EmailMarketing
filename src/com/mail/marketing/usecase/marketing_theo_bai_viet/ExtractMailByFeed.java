/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.FeedMail;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.facebook.dto.Comment;
import com.mail.marketing.facebook.usecase.FanPageAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author TUNGLV
 */
//tu cac bai viet thu thap duoc thuc hien thu thap email tu cac binh luan
public class ExtractMailByFeed {

    //tham so truyen vao
    private static String token = "EAACEdEose0cBAKLqZCUJ3PML8D7ufx0ZAeRuS8AaGM1WKWZCeOHZC4bGiCdsAcx4IjXBEW1a6Y4yK3x0e2Mdj3qUX4v0onE25zLSlwR9bvRJcC6ZCzZApvIhiog53BSZBdfQDyuWljw3hDW1gfPwHqsclfOH1RiLPm9cYhXahRw8RKyeS8PV1f8WXQdVnmc4yfcN1tUzPTllAZDZD";
    //so luong ban ghi lay ra tu bang tbl_feed
    private static String numFeed = "1000";
    
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
                            //truong hop mail ko bi chan va mail chua co trong
                            //bang tbl_mail
                            if (!checkMailBlock(mail)) {
                                //kiem tra mail đã tồn tại trong bảng tbl_mail
                                Mail mailDB = MailDao.getByEmail(mail);
                                if (mailDB == null) {
                                    //khoi tao doi tuong mail
                                    Mail email = initMail(mail);
                                    //insert vao bang tbl_mail
                                    MailDao.insert(email);
                                    //kiem tra cap idTblFeed - idTblMail da ton hay chua
                                    Mail mailDto = MailDao.getByEmail(mail);
                                    if (!checkIdTblFeedIdTblMail(f.getId(), mailDto.getId())) {
                                        FeedMail fm = initFeedMail(f.getId(), mailDto.getId());
                                        //insert vao bang tbl_feed_mail
                                        FeedMailDao.insert(fm);
                                    }
                                } else {//truong hop mail da ton tai trong tbl_mail chua ton tai trong tbl_feed_mail
                                    if (!checkIdTblFeedIdTblMail(f.getId(), mailDB.getId())) {
                                        //insert vao bang tbl_feed_mail 
                                        FeedMail fm = initFeedMail(f.getId(), mailDB.getId());
                                        //insert vao bang tbl_feed_mail
                                        FeedMailDao.insert(fm);
                                    }
                                    
                                }
                                
                            }
                            
                        } catch (Exception e) {
                            
                        }
                    }
                }
                
            }
            
        }
        System.out.println("                    -----*****-----**********************---------------------*****----");
        System.out.println("                    -----*****-----CHUONG TRINH ExtractMailByFeed KET THUC!---*****----");
        System.out.println("                    -----*****-----**********************---------------------*****----");        
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

    //kiem tra cap IdTblFeed IdTblMail da ton tai hay chua
    //true: da ton tai
    //false: chua ton tai
    private static boolean checkIdTblFeedIdTblMail(int idTblFeed, int idTblMail) throws Exception {
        FeedMail fm = FeedMailDao.getByIdTblFeedIdTblMail(idTblFeed, idTblMail);
        if (fm != null) {
            return true;
        }
        return false;
    }
    
    private static FeedMail initFeedMail(int idTblFeed, int idTblMail) {
        FeedMail fm = new FeedMail();
        fm.setIdTblFeed(idTblFeed);
        fm.setIdTblMail(idTblMail);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        fm.setCreateDate(dateCreate);
        return fm;
    }
    
}
