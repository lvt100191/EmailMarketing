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
 * luu thong tin tai khoan face
 */
public class FaceBook {
    //id
    String id;
    //id dinh danh tren face: id ca nhan, id trang, id group
    String idFace;
    //loai tai khoan 1: ca nhan, 2: trang, 3: group
    String type;
    //ten ca nhan, ten trang, ten group
    String name;
    //gioi tinh neu la ca nhan
    String gender;
    //ngay sinh neu la ca nhan
    String birthDay;
    //dia chi ca nhan, dia chi trang (vi du dia chi shop)
    String address;
    //so dien thoai
    String mobile;
    //nhung truong sau nay se them vao ngoai thiet ke co san
    //cac truong ngan cach nhau bang ky tu |
    String note;
    Date createDate;
    
}
