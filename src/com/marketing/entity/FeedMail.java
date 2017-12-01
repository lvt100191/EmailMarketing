/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.entity;

/**
 *
 * @author PMDVCNTT
 */
public class FeedMail {
     public static final String TABLE_NAME = "TBL_FEED_MAIL";
     public static final int STATUS_INSERT = 1;
     public static final int STATUS_SENT = 2;
    int id;
    //id cua bang tbl_feed
    int idTblFeed;
    //id cua bang tbl_mail
    int idTblMail;
    //ngay insert du lieu
    String createDate;
    //trang thai danh dau da gui mailtheo bai viet hay chua 1: chua gui, 2:da gui
    int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTblFeed() {
        return idTblFeed;
    }

    public void setIdTblFeed(int idTblFeed) {
        this.idTblFeed = idTblFeed;
    }

    public int getIdTblMail() {
        return idTblMail;
    }

    public void setIdTblMail(int idTblMail) {
        this.idTblMail = idTblMail;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

  
    
    
    
}
