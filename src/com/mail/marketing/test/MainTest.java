/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailSend;
import com.mail.marketing.facebook.action.CommentAction;
import com.mail.marketing.http.ResponseUtil;
import com.mail.marketing.mail.EmailAction;
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
        String mailSend1 = "";
        //lay thong tin mail dung de gui
        MailSend mSend = MailSendDao.getMailSendByEmail(mailSend1);
        //lay danh sach mail nhan
        ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, "99");
        Mail mxx = new Mail();
        mxx.setEmail("tunglv9x@gmail.com");
        lst.add(mxx);
        //lay danh sach bai viet theo truong note='FEED_MIX_LIST'
        ArrayList<FeedEntity> feedEntitys = FeedEntityDao.getLstFeedByNote("FEED_MIX_LIST");
        if (feedEntitys != null && feedEntitys.size() > 0) {
            int countMail = 0;
            //duyet tung mail
            for (Mail m : lst) {
                //lay bai viet theo countMail
                int idxFeed = countMail % feedEntitys.size();
                FeedEntity feedEntity = feedEntitys.get(idxFeed);
                //gen title
                 //String title = "Tài liệu, Bộ tài liệu, Bí kíp luyện thi|Toeic|cực khủng, cực đỉnh, cực chất, cực HOT";
                 String title = feedEntity.getTitleSend();
        String[] arr3 = title.split("\\|");
        String[] p0 = arr3[0].split(",");
        String[] p1 = arr3[1].split(",");
        String[] p2 = arr3[2].split(",");
        List<String> titles = new ArrayList<String>();
        for(int i=0; i< p0.length; i++){
            for(int j=0; j<p1.length; j++){
                for(int k=0; k<p2.length; k++){
                    String strTitle = p0[i]+" "+ p1[j] +" "+ p2[k];
                    strTitle = strTitle.trim();
                    if(!titles.contains(strTitle)){
                        titles.add(strTitle);
                    }
                }
            }
        }
        //chon title
            //lay so du
            int idxTitle = countMail % titles.size();
            System.out.println("email: " + m + " title: " + titles.get(idxTitle));     
                String titleSend = titles.get(idxTitle);
                EmailAction.sendGmail(feedEntity.getFanpageName().trim(), mailSend1, mSend.getPassword(), m.getEmail().toLowerCase(), titleSend, feedEntity.getContentSend().trim());
                countMail = countMail + 1;
            }
        }

//        //lay danh sach mail nhan
//        List<String> emails = new ArrayList<String>();
//        emails.add("m1");
//        emails.add("m2");
//        emails.add("m3");
//        emails.add("m4");
//        //chon bai viet
//        //gen title
//        String title = "Tài liệu, Bộ tài liệu, Bí kíp luyện thi|Toeic|cực khủng, cực đỉnh, cực chất, cực HOT";
//        String[] arr3 = title.split("\\|");
//        String[] p0 = arr3[0].split(",");
//        String[] p1 = arr3[1].split(",");
//        String[] p2 = arr3[2].split(",");
//        List<String> titles = new ArrayList<String>();
//        for(int i=0; i< p0.length; i++){
//            for(int j=0; j<p1.length; j++){
//                for(int k=0; k<p2.length; k++){
//                    String strTitle = p0[i]+" "+ p1[j] +" "+ p2[k];
//                    strTitle = strTitle.trim();
//                    if(!titles.contains(strTitle)){
//                        titles.add(strTitle);
//                    }
//                }
//            }
//        }
//        for(String t: titles){
//            System.out.println(t+"\n");
//        }
//        //chon title
//        int count = 0;
//        for (String m : emails) {
//            //lay so du
//            int a = count % titles.size();
//            System.out.println("email: " + m + " title: " + titles.get(a));
//            count = count + 1;
//        }
        System.out.println("success");
    }
}
