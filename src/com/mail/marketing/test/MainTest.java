/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.mail.marketing.facebook.action.CommentAction;
import com.mail.marketing.http.ResponseUtil;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author PMDVCNTT
 */
public class MainTest {

    //gen title de gui mail
    public static void main(String[] args) throws Exception {
        String title = "Tài liệu, Bộ tài liệu, Bí kíp luyện thi|Toeic|cực khủng, cực đỉnh, cực chất, cực HOT";
        String[] arr3 = title.split("\\|");
        String[] p0 = arr3[0].split(",");
        String[] p1 = arr3[1].split(",");
        String[] p2 = arr3[2].split(",");

        List<String> emails = new ArrayList<String>();
        emails.add("m1");
        emails.add("m2");
        emails.add("m3");
        emails.add("m4");
        List<String> titles = new ArrayList<String>();
        titles.add("t1");
        titles.add("t2");
        titles.add("t3");
        titles.add("t4");
        titles.add("t5");
        int count = 0;
        for (String m : emails) {
            //lay so du
            int a = count % titles.size();
            System.out.println("email: " + m + " title: " + titles.get(a));
            count = count + 1;
        }

        System.out.println("success");
    }
}
