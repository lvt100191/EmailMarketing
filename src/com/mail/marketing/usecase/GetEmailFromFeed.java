/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.usecase;

import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDocumentDao;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailSendDocument;
import com.mail.marketing.facebook.dto.Comment;
import com.mail.marketing.facebook.dto.Feed;
import com.mail.marketing.facebook.dto.Page;
import com.mail.marketing.facebook.dto.User;
import com.mail.marketing.facebook.usecase.FanPageAction;
import com.mail.marketing.http.ResponseUtil;
import com.mail.marketing.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author TUNGLV
 *
 */
public class GetEmailFromFeed {

    private static String token = "EAACEdEose0cBANBZAfcOfZA8vE5Wz7ZCZAqzI7F4z6b2uE3bwhHcX6CV4Ib568QDF2ni3xjrZA3lDVrNXIqt1M0GGsbKRqjZClzvJLBfA8Tat62tdy4eCa5NxHedL7wW68zZAXwSyZBGk2yBUHJE9Taq7Y95ZA9m9W1pRzwwcFv4R3GgiVoiO0r8uIZBPqZCY2VLj4ZD";

    public static void main(String[] args) throws Exception {
        //lay thong tin trang tieng anh cho nguoi viet theo id:275158636317806
        FanPageAction fanPageAction = new FanPageAction();
        String id = "275158636317806";
        Page page = fanPageAction.getPageInfoById(token, id);
        //lay bai dang cua trang tu ngay truyen vao
        //lay danh sach bai da dang tu ngay fromDate truyen vao den hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2017-10-16");
        ArrayList<Feed> lstFeed = fanPageAction.getFeed(token, page.getId(), fromDate);
        //tu danh sach bai dang debug de lay ra id cua bai dang can thu thap email
        String feedId = "275158636317806_287001431800193";

        for (Feed feed : lstFeed) {
            //lay ra bai dang can thu thap mail theo id cua bai dang
            if (feed.getId().equals(feedId)) {
                //lay ra comment cua bai dang 
                ArrayList<Comment> comments = getComments(token, feed.getId());
                //duyet qua cac comment thu thap dia chi mail
                extractMailFromComment(comments, feedId);
            }
        }
    }

    public static ArrayList<Comment> getComments(String token, String feedId) throws Exception {
        ArrayList<Comment> listComment = new ArrayList<>();
        JSONParser parser = null;
        String urlGetComment = "https://graph.facebook.com/v2.10/" + feedId + "/comments?access_token=" + token;
        Object rsNext = null;
        do {
            if (rsNext == null) {
                String rsComment = ResponseUtil.sendGet(urlGetComment);

                parser = new JSONParser();
                JSONObject objComments = (JSONObject) parser.parse(rsComment);
                String rsPaging = objComments.get("paging").toString();
                parser = new JSONParser();
                JSONObject objPaging = (JSONObject) parser.parse(rsPaging);
                rsNext = objPaging.get("next");
                JSONArray dataComments = (JSONArray) objComments.get("data");

                if (dataComments != null && dataComments.size() > 0) {
                    for (int j = 0; j < dataComments.size(); j++) {
                        Comment c = new Comment();
                        //chu y dataComments get j khong duoc nham bien
                        JSONObject comment = (JSONObject) dataComments.get(j);
                        c.setId(comment.get("id").toString());
                        c.setIdFeed(feedId);
                        Object user = comment.get("from");
                        if (user != null) {
                            String userJson = comment.get("from").toString();
                            parser = new JSONParser();
                            JSONObject objUser = (JSONObject) parser.parse(userJson);
                            User u = new User();
                            u.setId(objUser.get("id").toString());
                            u.setName(objUser.get("name").toString());
                            c.setUser(u);
                        }
                        c.setTimeComment(comment.get("created_time").toString());
                        c.setContentComment(comment.get("message").toString());
                        //trong lst chua danh sach nguoi binh luan userName
                        listComment.add(c);

                    }
                }
            }
            if (rsNext != null) {
                String rsCommentNext = ResponseUtil.sendGet(rsNext.toString());
                parser = new JSONParser();
                JSONObject objComments = (JSONObject) parser.parse(rsCommentNext);
                String rsPaging = objComments.get("paging").toString();
                parser = new JSONParser();
                JSONObject objPaging = (JSONObject) parser.parse(rsPaging);
                rsNext = objPaging.get("next");
                JSONArray dataComments = (JSONArray) objComments.get("data");

                if (dataComments != null && dataComments.size() > 0) {
                    for (int j = 0; j < dataComments.size(); j++) {
                        Comment c = new Comment();
                        //chu y dataComments get j khong duoc nham bien
                        JSONObject comment = (JSONObject) dataComments.get(j);
                        c.setId(comment.get("id").toString());
                        c.setIdFeed(feedId);
                        Object user = comment.get("from");
                        if (user != null) {
                            String userJson = comment.get("from").toString();
                            parser = new JSONParser();
                            JSONObject objUser = (JSONObject) parser.parse(userJson);
                            User u = new User();
                            u.setId(objUser.get("id").toString());
                            u.setName(objUser.get("name").toString());
                            c.setUser(u);
                        }
                        c.setTimeComment(comment.get("created_time").toString());
                        c.setContentComment(comment.get("message").toString());
                        //trong lst chua danh sach nguoi binh luan userName
                        listComment.add(c);

                    }
                }
            }

        } while (rsNext != null);
        return listComment;
    }

    //kiem tra so luong mail binh luan cua bai viet da dat 1000 chua 
    //fasle:chua dat true:da dat
    private static boolean countMailByFeedId(String idFeed) throws Exception {
        boolean check = false;
        int count = MailSendDocumentDao.countMailByFeedId(idFeed);
        if (count > 1000) {
            System.out.println("So luong mail da du 1000 khong the them moi email");
            return true;
        }
        return check;
    }

    //kiem tra tai khoan mail da ton tai chua
    //fasle:chua ton tai true:da ton tai
    private static boolean checkMailExisted(String mail) throws Exception {
        boolean check = false;
        MailSendDocument m = MailSendDocumentDao.getByEmail(mail);
        if (m != null) {
            System.out.println("tai khoan mail " + mail + " da ton tai");
            return true;
        }
        return check;
    }

    //comments: danh sach comment
    //idFeed: id cua bai dang
    private static void extractMailFromComment(ArrayList<Comment> comments, String idFeed) {
        String mail = null;
        for (Comment c : comments) {
            //neu comment co email thuc hien kiem tra
            //so luong email thu thap tu bai dang da du 1000 email chua
            //neu =1000 khong add vao, khi so luong email<1000
            //add vao bang tbl_mail_send_document
            String comment = c.getContentComment();
            if (comment.contains("@")) {
                //parse dinh danh mail
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(comment);
                while (m.find()) {
                    try {
                        mail = m.group();
                        char end = mail.charAt(mail.length() - 1);
                        if (end == '.') {
                            mail = mail.substring(0, mail.length() - 1);
                        }
                        MailSendDocument email = new MailSendDocument();
                        email.setEmail(mail);
                        //kiem tra email da ton tai trong bang tbl_mail_Send_document chua
                        //select  count (*), email  from tbl_mail_Send_document   group by email having count(*)>1 ;
                        //checkMailExisted
                        //kiem tra xem voi moi bai dang so luong email da du 1000 mail chua
                        // select count (*) from tbl_mail_Send_document where id_feed=id bai dang
                        //countMailByFeedId
                        //chi lay gmail
                        if (!checkMailExisted(mail) && !countMailByFeedId(idFeed) && mail.contains("@gmail.com")) {
                            MailSendDocumentDao.insert(email);
                        }
                    } catch (Exception e) {

                    }
                }
            }

        }
    }
}
/**
 *
 * @Desc Post 1 bai voi noi dung chia se tai lieu Yeu cau: like bai,like fanpage
 * dangky youtube, comment email(chu y phai comment gmail), .Duyet qua cac binh
 * luan lay du 1000 mail Gui tai lieu cho danh sach mail da thu thap duoc
 */
