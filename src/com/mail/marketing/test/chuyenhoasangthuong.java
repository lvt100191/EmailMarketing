/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.mail.marketing.facebook.action.CommentAction;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author PMDVCNTT
 */
public class chuyenhoasangthuong {

    public static void main(String[] args) throws Exception {
        //token cua page
        String token="EAACEdEose0cBANPHYsaItZAtjrEzG0agkRpLsJJZBnAxRQ0AeuqIWkGchB9eUbRZBtjRs4nYpn6LCjW6HzlvrnNjKe3xoFrLo38YZAvniFZBt4bt5X8M9NvOEdUzIjtQY2wAi92sgyELGJ7uFaVlWfUQzaZC4eDfAUXG07RiZALUsFtLt9tYt0rY870piDZB0ZCqC4w9vtt56lwZDZD";
        CommentAction.postCommentById("275158636317806_298340487332954",token);
        System.out.println("success!");
    }


}
