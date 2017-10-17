/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase;

import com.mail.marketing.facebook.dto.Feed;
import com.mail.marketing.facebook.dto.Page;
import com.mail.marketing.facebook.usecase.FanPageAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author TUNGLV
 * 
 */
public class Get1000EmailFromFeed {
    private static String token = "EAACEdEose0cBANBZAfcOfZA8vE5Wz7ZCZAqzI7F4z6b2uE3bwhHcX6CV4Ib568QDF2ni3xjrZA3lDVrNXIqt1M0GGsbKRqjZClzvJLBfA8Tat62tdy4eCa5NxHedL7wW68zZAXwSyZBGk2yBUHJE9Taq7Y95ZA9m9W1pRzwwcFv4R3GgiVoiO0r8uIZBPqZCY2VLj4ZD";
    public static void main(String[] args) throws Exception {
        //lay thong tin trang theo id:275158636317806
        FanPageAction fanPageAction = new FanPageAction();
        String id= "275158636317806";
        Page page = fanPageAction.getPageInfoById(token, id);
        //lay bai dang cua trang tu ngay truyen vao
        //lay danh sach bai da dang tu ngay fromDate truyen vao den hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2017-10-16");
        ArrayList<Feed> lstFeed = fanPageAction.getFeed(token, page.getId(), fromDate);
        //tu danh sach bai dang debug de lay ra id cua bai dang can thu thap email
        String feedId="275158636317806_287001431800193";
    }
    
}
/**
 *
 * @Desc
 * Post 1 bai voi noi dung chia se tai lieu
 * Yeu cau:
 * like bai 
 * like fanpage 
 * dangky youtube
 * comment email
 * Duyet qua cac binh luan du 1000 mail
 * Gui tai lieu cho 1000 mail do
 */
