/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.db.FaceBookDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.FaceBook;
import com.mail.marketing.entity.Mail;
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
    private static String fromDateUI ="2017-10-19";
    private static String token="";
    
    public static void main(String[] args) throws Exception {
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
                                            Mail email = new Mail();
                                            email.setEmail(mail);
                                            //kiem tra dieu kien truoc khi insert vao db
                                            if (checkMailBeforeInsert(mail)) {
                                                MailDao.insert(email);
                                                System.out.println("thu thap duoc email: "+mail+" va insert vao bang tbl_mail");
                                            }

                                        } catch (Exception e) {

                                        }
                                    }
                                }

                            }

                        }
                    }
    }

    private static boolean checkMailBeforeInsert(String mail) {
        boolean check = false;
        //mail phải không tồn tại trong danh sách mail chặn tbl_mail_block
        //mail chưa được insert vào bảng tbl_mail
        return check;
    }
    
}
