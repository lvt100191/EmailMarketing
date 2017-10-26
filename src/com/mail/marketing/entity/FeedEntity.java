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
    public static final int STATUS_SEND = 1;
    public static final int STATUS_NO_SEND= 2;
    int id;
    //id cua bai viet
    String idFeed;
    //noi dung bai viet
    String contentFeed;
    //ngay insert vao bang yyyyMMddHHmmss
    String createDate;
    //thoi gian dang bai viet
    String createTimeFeed;
    //tieu de gui mail marketing
    String titleSend;
    //noi dung gui mail marketing
    String contentSend;
    //link tai lieu de gui
    String linkDocument;
    //id cua fanpage
    String idFanpage;
    //ten trang
    String fanpageName;
    //trang thai lay ra bai viet de gui email 1: lay ra de gui, 2: khong lay ra
    int status;

    public String getIdFanpage() {
        return idFanpage;
    }

    public void setIdFanpage(String idFanpage) {
        this.idFanpage = idFanpage;
    }

    public String getFanpageName() {
        return fanpageName;
    }

    public void setFanpageName(String fanpageName) {
        this.fanpageName = fanpageName;
    }

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

    public String getCreateTimeFeed() {
        return createTimeFeed;
    }

    public void setCreateTimeFeed(String createTimeFeed) {
        this.createTimeFeed = createTimeFeed;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
