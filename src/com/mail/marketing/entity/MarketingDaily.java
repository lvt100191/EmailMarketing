/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.entity;

import java.util.Date;

/**
 *
 * @author TUNGLV
 */
public class MarketingDaily {

    public static final String TABLE_NAME = "TBL_MARKETING_DAILY";
    int id;
    //id cua bai dang
    String idFeed;
    //thoi gian dang
    Date postDate;
    //id_Facebook cua group hay fanpage
    String fromSource;
    //ten group. fanpage
    String name;
    //so mail trong bai dang
    int amountMail;
    //danh sach mail
    String mailList;
    String note;
    //noi dung bai dang
    String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdFeed() {
        return idFeed;
    }

    public void setIdFeed(String idFeed) {
        this.idFeed = idFeed;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getFromSource() {
        return fromSource;
    }

    public void setFromSource(String fromSource) {
        this.fromSource = fromSource;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountMail() {
        return amountMail;
    }

    public void setAmountMail(int amountMail) {
        this.amountMail = amountMail;
    }

    public String getMailList() {
        return mailList;
    }

    public void setMailList(String mailList) {
        this.mailList = mailList;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
