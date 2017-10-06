/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.facebook.usecase;

import com.mail.marketing.config.Config;
import com.mail.marketing.facebook.dto.User;
import com.mail.marketing.http.ResponseUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author TUNGLV
 */
public class UserAction {

    //lay user theo token
    public User getUserByToken(String token) throws Exception {
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

    //post bai len trang ca nhan
    //token: ma truy nhap cua user developer
    //userID: id cua user
    //msg: noi dung bai post len trang
    public void postFeed(String token, String userID, String msg) throws Exception {
        try {
            String urlPostFeed = "https://graph.facebook.com/v2.10/" + userID + "/feed?message=" + msg + "&access_token=" + token;
            ResponseUtil responseUtil = new ResponseUtil();
            responseUtil.sendPost(urlPostFeed);
        } catch (Exception e) {
            throw new Exception("getPageUserLikes error: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        UserAction userAction = new UserAction();
        //lay thong tin user
        User u = userAction.getUserByToken(token);
  
        System.out.println("=========================thuc hien thanh cong");

    }

}
