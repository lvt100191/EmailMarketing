/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.config.Config;
import com.mail.marketing.db.FaceBookDao;
import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.entity.FaceBook;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.facebook.dto.Comment;
import com.mail.marketing.facebook.dto.Feed;
import com.mail.marketing.facebook.dto.Page;
import com.mail.marketing.facebook.usecase.FanPageAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author tunglv4
 */
//thu thap bai viet co binh luan email tu ngay truyen vao den hien tai cua cac fanpage lay tu bang tbl_facebook
//khong phai sua khi clean build
public class B1GetFeedHaveEmailCMD {

    //tham so truyen vao
    //private static String fromDateUI = "2017-10-23";
    //private static String token = "EAACEdEose0cBAAylZB1wY2jB8GdWWHsjrCMp6E4InDkMWHAUoIdQI4HNoGUcybKsZB0cJh3ZCBaDJHhYHurlQTdoOUezBUO2yxR1wdUVMNynPYbd3W3663pwZBB1RZCz4JMeBUzMytHDt2WCCrtw3GN5Rf48RFivIRGFLQSY0h6ZArfNFZBD9kALWqDchOGHV4ZD";
    public static void main(String[] args) throws Exception {
        //sau file jar la tham so truyen vao bat dau tu tham so args[0]
        // thu thap bai viet cua fanpage dang tu ngay truyen vao den ngay hien tai
        //String fromDateUI = args[0];
        //token cua user deverloper
        //String token = args[1];
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        System.out.println("------Thu thap bai viet--------: ");
        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        String fromDateUI = cfg.FROM_DATE;
        System.out.println("------tham so truyen vao--------: ");
        System.out.println("------fromDateUI: " + fromDateUI);
        System.out.println("------token: " + token);
        ArrayList<FaceBook> lst = FaceBookDao.getListFaceBook(FaceBook.TYPE_FANPAGE);
        FanPageAction fanPageAction = new FanPageAction();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse(fromDateUI);
        int count = 0;
        for (FaceBook fg : lst) {
            try {
                //lay thong tin trang
                Page page = fanPageAction.getPageInfoById(token, fg.getIdFacebook());
                System.out.println("------------------duyet qua trang: " + page.getName());
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
                                FeedEntity feedEntity = initFeedEntity(f, page);
                                //insert thong tin bai viet vao bang tbl_feed
                                FeedEntityDao.insert(feedEntity);
                                System.out.println("-----thu thap duoc bai viet: " + feedEntity.getIdFeed() + "tu fanpage" + feedEntity.getFanpageName());
                                System.out.println("-----tong so bai viet thu thap duoc: " + count++);
                            }
                            break;
                        }
                    }

                }
                System.out.println("-----tong so bai viet thu thap duoc: " + count);
            } catch (Exception ex) {

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

    private static FeedEntity initFeedEntity(Feed f, Page p) throws ParseException {
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setIdFeed(f.getId());
        feedEntity.setContentFeed(f.getMessage());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        feedEntity.setCreateDate(dateCreate);
        //2017-09-27T08:29:04+0000
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date crTime = sdf1.parse(f.getCreateTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String crt = sdf2.format(crTime);
        feedEntity.setCreateTimeFeed(crt);
        feedEntity.setIdFanpage(p.getId());
        feedEntity.setFanpageName(p.getName());
        feedEntity.setStatus(FeedEntity.STATUS_NO_SEND);
        return feedEntity;
    }
}
