/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.facebook.action;

import com.mail.marketing.http.ResponseUtil;

/**
 *
 * @author TUNGLV
 */
public class CommentAction {

    //binh luan bai viet
    //id: la id cua trang _ id bai viet 275158636317806_298340487332954, id trang
    //lay o phan gioi thieu trang, id bai viet click vao thoi gian dang tren bai viet
    //token: la mã truy cập trang, chu y day la ma truy cap trang ko phai ma truy cap nguoi dung
    //vao chuc nang lay ma truy cap nguoi dung chon method GET
    //paste duong dan sau: 275158636317806?fields=access_token de lay ma truy cap trang
    //trong do 275158636317806 la id cua trang
    public static String postCommentById(String id, String token) throws Exception {
        String urlPost = "https://graph.facebook.com/v2.11/" + id + "/comments?access_token="+token+"&message=" + "This+is+a+test+part1+toeic";
        ResponseUtil responseUtil = new ResponseUtil();
        String rs = responseUtil.sendPost(urlPost);
        //id cua comment
        return rs;
    }

    public static void main(String[] args) {

    }
}
