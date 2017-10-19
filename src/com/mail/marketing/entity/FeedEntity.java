/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.entity;

/**
 *
 * @author PMDVCNTT
 */
public class FeedEntity {
     public static final String TABLE_NAME = "TBL_FEED";
    int id;
    //id cua bai viet
    String idFeed;
    //noi dung bai viet
    String contentFeed;
    //ngay insert vao bang yyyyMMddHHmmss
    String createDate;
    //so luong email thu thap duoc tu bai viet
    int amountMail;
    //tieu de gui mail marketing
    String titleSend;
    //noi dung gui mail marketing
    String contentSend;
    //link tai lieu de gui
    String linkDocument;

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

    public String getContentFeed() {
        return contentFeed;
    }

    public void setContentFeed(String contentFeed) {
        this.contentFeed = contentFeed;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getAmountMail() {
        return amountMail;
    }

    public void setAmountMail(int amountMail) {
        this.amountMail = amountMail;
    }

    public String getTitleSend() {
        return titleSend;
    }

    public void setTitleSend(String titleSend) {
        this.titleSend = titleSend;
    }

    public String getContentSend() {
        return contentSend;
    }

    public void setContentSend(String contentSend) {
        this.contentSend = contentSend;
    }

    public String getLinkDocument() {
        return linkDocument;
    }

    public void setLinkDocument(String linkDocument) {
        this.linkDocument = linkDocument;
    }
    
    
    
    
    
}
