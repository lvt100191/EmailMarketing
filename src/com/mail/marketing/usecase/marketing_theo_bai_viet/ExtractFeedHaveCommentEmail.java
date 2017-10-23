/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.db.FaceBookDao;
import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.entity.FaceBook;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.facebook.dto.Comment;
import com.mail.marketing.facebook.dto.Feed;
import com.mail.marketing.facebook.dto.Page;
import com.mail.marketing.facebook.usecase.FanPageAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author tunglv4
 */
//thu thap bai viet co binh luan email tu ngay truyen vao den hien tai cua cac fanpage lay tu bang tbl_facebook
public class ExtractFeedHaveCommentEmail {

    //tham so truyen vao
    private static String fromDateUI = "2017-10-16";
    private static String token = "EAACEdEose0cBAJTwu3tm2ZAagHFhpa8R8dH2vgCuhctItCrNjwp217MB3VfulVYweeqAiA6DfrQOvY1YMFTS4CRr1chQe5CEJxe1ZCnCtEdLC5VrnBrB2xw5IADXMa2F2wpcGeqhxNTPfBVjSKRHY5wx7afpyma4CCMJuAQlhUkC1nu6WCe5jmVyDkATYZD";

    public static void main(String[] args) throws Exception {
        ArrayList<FaceBook> lst = FaceBookDao.getListFaceBook(FaceBook.TYPE_FANPAGE);
        FanPageAction fanPageAction = new FanPageAction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse(fromDateUI);
        for (FaceBook fg : lst) {
            //fix chi lay ra trang tieng anh cho nguoi viet
            if(fg.getIdFacebook().equals("275158636317806")){
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
                    //neu binh luan co chua gmail
                    if (comment.contains("@gmail.com")) {
                        //kiem tra id_feed da ton tai trong bang tbl_feed hay chua
                        if (!checkFeedExisted(f)) {
                            //khoi tao doi tuong insert
                            FeedEntity feedEntity = initFeedEntity(f,page);
                            //insert thong tin bai viet vao bang tbl_feed
                            FeedEntityDao.insert(feedEntity);
                        }
                    }
                }

            }  
            }


        }
        System.out.println("                    -----*****-----**********************-------------------------------*****----");
        System.out.println("                    -----*****-----CHUONG TRINH ExtractFeedHaveCommentEmail KET THUC!---*****----");
        System.out.println("                    -----*****-----**********************-------------------------------*****----");
    }

    //true: da ton tai
    //false: chua ton tai
    private static boolean checkFeedExisted(Feed f) throws Exception {
        FeedEntity feedEntity = FeedEntityDao.getByFeed(f.getId());
        if (feedEntity != null) {
            return true;
        }
        return false;
    }

    private static FeedEntity initFeedEntity(Feed f, Page p) {
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setIdFeed(f.getId());
        feedEntity.setContentFeed(f.getMessage());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        feedEntity.setCreateDate(dateCreate);
        feedEntity.setIdFanpage(p.getId());
        feedEntity.setFanpageName(p.getName());
        return feedEntity;
    }
}
