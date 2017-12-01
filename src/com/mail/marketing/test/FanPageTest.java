/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.mail.marketing.config.Config;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.Mail;
import com.marketing.facebook.dto.Comment;
import com.marketing.facebook.dto.Feed;
import com.marketing.facebook.dto.Page;
import com.marketing.facebook.dto.User;
import com.marketing.facebook.action.FanPageAction;
import com.mail.marketing.http.ResponseUtil;
import com.mail.marketing.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author TUNGANH
 */
public class FanPageTest {

    public static void main(String[] args) throws Exception {
        //lay thong tin trang
        Config cfg = new Config();
        String token = cfg.USER_ACCESS_TOKEN;
        FanPageAction fanPageAction = new FanPageAction();
        Page page = fanPageAction.getPageInfoById(token, "127766340600543");
        //lay danh sach bai da dang tu ngay fromDate truyen vao den hien tai
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse("2017-10-16");
        ArrayList<Feed> lstFeed = fanPageAction.getFeed(token, page.getId(), fromDate);
        //duyet qua tung bai dang lay danh sach comment và email
        String mail = null;
        for (Feed f : lstFeed) {
            ArrayList<Comment> comments = getComments(token, f.getId());
            for (Comment c : comments) {
                //neu co email thi gui mail
                String comment = c.getContentComment();
                if (comment.contains("@")) {
                    Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(comment);
                    while (m.find()) {
                        try {
                            mail = m.group();
                            char end = mail.charAt(mail.length() - 1);
                            if (end == '.') {
                                mail = mail.substring(0, mail.length() - 1);
                            }
                            Mail email = new Mail();
                            email.setEmail(mail);
                            //select  count (*), email  from TBL_MAIL   group by email having count(*)>1 ;
                            if (!EmailAction.checkMailExisted(mail)) {
                                MailDao.insert(email);
                            }

                        } catch (Exception e) {

                        }
                    }
                }

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

}
