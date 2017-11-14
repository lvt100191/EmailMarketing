/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 *
 * @author PMDVCNTT
 */
public class chuyenhoasangthuong {

    public static void main(String[] args) {
        String str = "Đại học Kinh tế Quốc dân";
        System.out.println(removeAccent(str));
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String str = pattern.matcher(temp).replaceAll("");
        if (str.contains("Đ")) {
            str = str.replace("Đ", "D");
        }
        if (str.contains("đ")) {
            str = str.replace("đ", "d");
        }
        return str;
    }
}
