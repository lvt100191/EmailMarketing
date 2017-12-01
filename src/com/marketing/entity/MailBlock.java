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
public class MailBlock {
     public static final String TABLE_NAME = "TBL_MAIL_BLOCKED";
     
     int id;
     //email chặn
     String mailBlock;
     //ngay tao
     String createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getMailBlock() {
        return mailBlock;
    }

    public void setMailBlock(String mailBlock) {
        this.mailBlock = mailBlock;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
     
    
}
