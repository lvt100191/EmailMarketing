/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

/**
 *
 * @author PMDVCNTT
 */
public class Test {
    public static void main(String[] args) {
        String  str="yyyyMMddHHmmss";
                                                //lay ra yyyyMMdd thoi gian hien tai
                    String curDate = str.trim().substring(0, 8);
                    //lay ra HHmmss thoi gian hien tai
                    String curTime = str.trim().substring(8, 14);
                    
                    System.out.println("curDate: "+ curDate);
                    System.out.println("curTime: "+ curTime);
    }
}
