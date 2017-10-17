/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.entity;

/**
 *
 * @author TUNGLV
 */
public class MailSendDocument {
    int id;
    //id cua bai dang
    String idFeed;
    //noi dung bai dang
    String content;
    //email thuoc bai dang
    String email;
    //trang thai da gui mail
    int status;
    //ngay thu thap vao email
    String createDate;
    //link de gui tai lieu
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLinkDocument() {
        return linkDocument;
    }

    public void setLinkDocument(String linkDocument) {
        this.linkDocument = linkDocument;
    }
    
    
    
}
