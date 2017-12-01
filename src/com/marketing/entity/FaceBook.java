/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.entity;

/**
 *
 * @author TUNGLV
 */
public class FaceBook {
    public static final String TABLE_NAME = "TBL_FACEBOOK";
    public static final String TYPE_GROUP="GROUP";
    public static final String TYPE_FANPAGE="FANPAGE";
    
    int id;
    //id cua trang hoac nhom tren face
    String idFacebook;
    String name;
    //loai fanpage hay group
    String type;
    //so thanh vien like fanpage 
    String member;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

 

    public String getIdFacebook() {
        return idFacebook;
    }

    public void setIdFacebook(String idFacebook) {
        this.idFacebook = idFacebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
