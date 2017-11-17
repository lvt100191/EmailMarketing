/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.mail.marketing.facebook.action.CommentAction;
import com.mail.marketing.http.ResponseUtil;
import java.text.Normalizer;
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
public class chuyenhoasangthuong {

    public static void main(String[] args) throws Exception {
 String jsonStr = "{\n" +
"        \"name\": \"Giày Nam Javis Store\",\n" +
"        \"id\": \"320672431401280\",\n" +
"        \"created_time\": \"2017-11-03T03:28:02+0000\"\n" +
"      }";
        JSONParser parser = null;
        parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(jsonStr);
        //Set a = obj.entrySet();
        Set b = obj.keySet();
      
//        page.setId(obj.get("id").toString());
//        page.setName(obj.get("name").toString());
        System.out.println("success");
   }  
    }



