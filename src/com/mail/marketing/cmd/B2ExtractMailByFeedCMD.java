/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.config.Config;
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
 *
 */
// tu cac bai viet thu thap duoc tu bang tbl_feed thuc hien thu thap email tu cac binh luan
// cua bai viet
//khong phai sua khi clean build
public class B2ExtractMailByFeedCMD {

    //tham so truyen vao
    //private static String token = "EAACEdEose0cBAAm24fJtdH17Y7zaTOGMeiZBWr5A7ZCFhddIKDY57KftLJPs4GgiGrni9oZAbA3DTZBG6yBHEqK9gmKvZARxd4Nt98Of07Ogdk7mEZC82d2DuMRHSjUv2Xt0P0yNZBLplOSZCigvmxS4QO44JgVepZCqkPPxIcFdy52U4CNZBFocZAulhu4oy2AZB1AZD";
    //so luong ban ghi lay ra tu bang tbl_feed
    //private static String numFeed = "1000";
    public static void main(String[] args) throws Exception {
        //sau file jar la tham so truyen vao bat dau tu tham so args[0]
        //so luong bai viet lay ra tu bang tbl_feed
        //String numFeed = args[0].trim();
        //token cua user facebook developer
        //String token = args[1].trim();
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");
        System.out.println("----Thu thap email tu bai viet-----------");

        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        String numFeed = cfg.NUMBER_FEED;
        System.out.println("----so bai viet lay ra: " + numFeed + "-----------");
        System.out.println("----so bai viet lay ra: " + numFeed + "-----------");
        System.out.println("----so bai viet lay ra: " + numFeed + "-----------");
        System.out.println("-------token truyen vao: " + token + "-----------");
        System.out.println("-------token truyen vao: " + token + "-----------");
        System.out.println("-------token truyen vao: " + token + "-----------");

        FanPageAction fanPageAction = new FanPageAction();
        //lay danh sach bai viet tu bang tbl_feed theo so luong truyen vao
        ArrayList<FeedEntity> lstFeed = FeedEntityDao.getListFeedEntity(numFeed);
        //lay danh sach binh luan theo bai dang
        String mail = null;
        int countFeed = 1;
        for (FeedEntity f : lstFeed) {
            System.out.println("--------------------------duyet qua bai viet thu: " + countFeed++);
            System.out.println("--------------------------id cua bai viet: " + f.getIdFeed() + " cua trang: " + f.getFanpageName());
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
                            if (mail != null) {
                                mail = mail.trim();
                                mail = mail.toLowerCase();
                            }
                            if (!checkMailBlock(mail) && checkAddressMail(mail)) {
                                //kiem tra mail đã tồn tại trong bảng tbl_mail
                                Mail mailDB = MailDao.getByEmail(mail);
                                if (mailDB == null) {//mail chua ton tai trong tbl_mail
                                    System.out.println("mail: " + mail + " chua ton tai trong bang tbl_mail, insert vao bang tbl_mail va tbl_feed_mail");
                                    //khoi tao doi tuong mail
                                    Mail email = initMail(mail);
                                    //lay ra id cua mail trong bang tbl_mail se insert
                                    int idTblMail = MailDao.getMaxId();
                                    //insert vao bang tbl_mail
                                    MailDao.insert(email);
                                    //kiem tra cap idTblFeed - idTblMail da ton hay chua
                                    //cho nay mailDto dang bi null tim hieu cach lay id sau khi insert
                                    // Mail mailDto = MailDao.getByEmail(mail);
                                    if (!checkIdTblFeedIdTblMail(f.getId(), idTblMail)) {
                                        FeedMail fm = initFeedMail(f.getId(), idTblMail);
                                        //insert vao bang tbl_feed_mail
                                        FeedMailDao.insert(fm);
                                    }
                                } else {//truong hop mail da ton tai trong tbl_mail chua ton tai trong tbl_feed_mail
                                    //System.out.println("mail: " + mailDB.getEmail() + " da ton tai trong bang tbl_mail, insert vao bang tbl_feed_mail");
                                    if (!checkIdTblFeedIdTblMail(f.getId(), mailDB.getId())) {
                                        //insert vao bang tbl_feed_mail 
                                        FeedMail fm = initFeedMail(f.getId(), mailDB.getId());
                                        //insert vao bang tbl_feed_mail
                                        FeedMailDao.insert(fm);
                                    }
                                }

                            }

                        } catch (Exception e) {
                            System.out.println("Exception: " + e.getMessage());
                            e.printStackTrace();

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
        if (mail.startsWith("1")) {
            return false;
        }
        if (mail.startsWith("0")) {
            return false;
        }
        if (mail.startsWith(".")) {
            return false;
        }
        return true;
    }

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
        fm.setStatus(FeedMail.STATUS_INSERT);
        return fm;
    }

}
