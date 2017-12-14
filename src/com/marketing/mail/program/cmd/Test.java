/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.mail.program.cmd;


import com.marketing.facebook.action.FanPageAction;
import com.marketing.facebook.dto.Comment;
import java.util.ArrayList;

/**
 *
 * @author PMDVCNTT
 */
public class Test {
    public static void main(String[] args) throws Exception {
        String token="EAACEdEose0cBAAJyUrvJOdNyvvgN6nZAJXy4ZCuzLZBpvQ4LsyNuvnTHNRJoPLqpZBI3sQZAfNQNfuVJRwtWCw08CH3T8ddcSDZBYyq2mn8FheGSpOj6OYlnfFVFfcfE24qGjTP8sF5c4fmzqPSYdSum2nvrIM8dcHd4EwmKHwTRpmTgXrugXrtMY65UGGmYtcj3VxLlXdxgZDZD";
                FanPageAction fanPageAction = new FanPageAction();
       ArrayList<Comment> comments = fanPageAction.getComments(token,"1549564051799982");
        System.out.println("xong");
    }
}
