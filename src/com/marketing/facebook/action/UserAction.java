/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.facebook.action;

import com.marketing.config.Config;
import com.marketing.facebook.dto.User;
import com.marketing.http.ResponseUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author TUNGLV
 */
public class UserAction {

    //lay user theo token
    public static User getUserByToken(String token) throws Exception {
        User user = new User();
        try {

            String rsJsonUser = null;
            String urlMe = "https://graph.facebook.com/me?access_token=" + token;
            rsJsonUser = ResponseUtil.sendGet(urlMe);

            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(rsJsonUser);
            user.setId(obj.get("id").toString());
            user.setName(obj.get("name").toString());
        } catch (Exception e) {
            throw new Exception("getUserByToken(String token) error: " + e.getMessage());
        }
        return user;
    }

    /**
     * desc: Lay thong tin user da like
     *
     * @param token: ma truy cap nguoi dung
     * @param id: id cua nguoi dung can lay thong tin so luong trang ng dung do
     * da like
     * @return
     * @throws Exception
     */
    public void getPageUserLikes(String token, String id) throws Exception {
        try {
            String rsJsonPageUserLikes = null;
            String urlPageUserLikes = "https://graph.facebook.com/" + id + "/likes?access_token=" + token;
            rsJsonPageUserLikes = ResponseUtil.sendGet(urlPageUserLikes);
            System.out.println("thuc hien thanh cong");
        } catch (Exception e) {
            throw new Exception("getPageUserLikes error: " + e.getMessage());
        }
    }

    //post bai len trang ca nhan của chính mình
    //token: ma truy nhap cua user developer
    //userID: id cua user
    //msg: noi dung bai post len trang
    public static String postFeed(String token, String userID, String msg) throws Exception {
        try {
            String urlPostFeed = "https://graph.facebook.com/" + userID + "/feed?message=" + msg + "&access_token=" + token;
            ResponseUtil responseUtil = new ResponseUtil();
            String rs = responseUtil.sendPost(urlPostFeed);
            return rs;
        } catch (Exception e) {
            throw new Exception("getPageUserLikes error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String token="EAACEdEose0cBAMWrgvlWWxNJhd1CpuECHZBJvUK2Yhtx3ZAAZAwzRsb3deS5aNq7rZAeBoFslGgdL48rDKqZAGl4IY4hZCrO44r5hyCaUJxUjK8KFZAZA1Q95MFBiHHTpRSRS1hLZAKoosLeVwqtBaZC6rv4sutZA5rSSJ03A7JRGOLC4swULo8VItKTdzlrGGEvh8ZD";
  User u = getUserByToken(token);
        String rs= postFeed(token,"100007282699326","tienganhchobe");
  
        System.out.println("=========================ket qua thuc hien"+rs);

    }

}
