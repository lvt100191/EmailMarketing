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
public class FaceBook {
    public static final String TABLE_NAME = "TBL_FACEBOOK";
    public static final String TYPE_GROUP="GROUP";
    public static final String TYPE_FANPAGE="FANPAGE";
    
    String id;
    String idFacebook;
    String name;
    String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
