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
public class MailSend {
    String id;
    String email;
    String hostMail;
    //thoi gian thuc hien gui mail cuoi cung
    //dua vao thoi gian nay de tinh xem thoi gian hien tai gui mail co
    //cach thoi gian gui gan nhat 24h khong tranh spam mail
    String lastTime;
    //danh sach mail da chan mail gui
    String mailBlocked;
    //so luong mail nhan toi da ma host mail cho phep
    //gui qua so luong nay mail se bi coi la spam
    int maxMail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHostMail() {
        return hostMail;
    }

    public void setHostMail(String hostMail) {
        this.hostMail = hostMail;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getMailBlocked() {
        return mailBlocked;
    }

    public void setMailBlocked(String mailBlocked) {
        this.mailBlocked = mailBlocked;
    }

    public int getMaxMail() {
        return maxMail;
    }

    public void setMaxMail(int maxMail) {
        this.maxMail = maxMail;
    }
    
    
    
}
