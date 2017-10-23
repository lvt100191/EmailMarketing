/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase.marketing_theo_bai_viet;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.entity.FeedEntity;
import java.util.ArrayList;

/**
 *
 * @author TUNGLV
 */
//moi bai dang se thong bao chi nhan email den het ngay
//lay ra email binh luan trong bai dang gui mail quang ba
public class SendMailByFeed {
    //tham so truyen vao
    private static String token = "EAACEdEose0cBAJTwu3tm2ZAagHFhpa8R8dH2vgCuhctItCrNjwp217MB3VfulVYweeqAiA6DfrQOvY1YMFTS4CRr1chQe5CEJxe1ZCnCtEdLC5VrnBrB2xw5IADXMa2F2wpcGeqhxNTPfBVjSKRHY5wx7afpyma4CCMJuAQlhUkC1nu6WCe5jmVyDkATYZD";
    //id cua bang tbl_feed
    private static String idTblFeed = "";
    
    public static void main(String[] args) throws Exception {
        //lay bai dang theo id
        //lay danh sach id email tu bang tbl_feed_mail
        //lay danh sach dia chi email tu bang tbl_mail theo id
        //gui email den dia chi email co status_feed_mail=1
        //update status_feed_mail =2, trang thai da gui mail

    }
}
