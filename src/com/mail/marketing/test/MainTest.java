/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.marketing.db.FeedEntityDao;
import com.marketing.db.MailSendDao;
import com.marketing.entity.FeedEntity;
import com.marketing.entity.Mail;
import com.marketing.entity.MailSend;
import com.marketing.facebook.action.CommentAction;
import com.marketing.http.ResponseUtil;
import com.marketing.mail.action.EmailAction;
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

    static String sttMailSend = "1";

    public static void main(String[] args) throws Exception {
        String mailSend1 = "e...yeuanh@gmail.com";
         if (mailSend1.contains("..")) {
            System.out.println("dinh roi");
        }
        System.out.println("success");
    }
}
