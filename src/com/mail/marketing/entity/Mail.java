/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.entity;

import java.util.Date;

/**
 *
 * @author TUNGLV luu thong tin tai khoan mail
 */
public class Mail {

    public static final String TABLE_NAME = "TBL_MAIL";
    public static final String GMAIL_HOST= "GMAIL";
    public static final String OUTLOOK_HOST= "OUTLOOK";
    public static final String ZOHO_HOST= "ZOHO";
    public static final int STATUS_INSERT = 1;
    //id
    int id;
    //dia chi email
    String email;
    //cua ai
    String owner;
    //gioi tinh male, female
    String gender;
    //ngay sinh
    Date birthDay;
    //dia chi
    String address;
    //so dien thoai
    String mobile;
    //nhung truong sau nay se them vao ngoai thiet ke co san
    //cac truong ngan cach nhau bang ky tu |
    String note;
    //ngay insert vao csdl
    String createDate;
    //trang thai email da gui hoac chua gui mail
    //chu y trang thai nay quy dinh nhu sau:
    //khi insert set gia tri status=1 la chua gui
    //gui mail thanh cong set status=2
    //khi so ban ghi status=2 chiem hau het so luong ban ghi toan bang
    //gui mail den ban ghi status=2
    //gui mail thanh cong update status=1
    //lap nhu vay de gui mail theo lo tranh tinh trang chay chuong trinh lau
    int status;



    
    

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

  

}
