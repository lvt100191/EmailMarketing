/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.db;

import com.marketing.config.Config;
import com.marketing.entity.MailSend;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author TUNGLV
 */
public class MailSendDao {

    static Logger logger = Logger.getLogger(MailSendDao.class.getName());
    //update thoi gian gui mail gan nhat
    public static void updateMailLastTime(MailSend mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "UPDATE  " + MailSend.TABLE_NAME
                    + " SET LAST_TIME = ? WHERE ID= ?; ";
            pst = c.prepareStatement(query);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String lstTime = sdf.format(d);
            pst.setString(1, lstTime);
            pst.setInt(2, mail.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if(pst !=null) pst.close();
            if(c !=null) c.close();
        }

    }
    //update mailblocked
    public static void updateMailBlocked(MailSend mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "UPDATE  " + MailSend.TABLE_NAME
                    + " SET MAIL_BLOCKED = ? WHERE EMAIL= ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, mail.getMailBlocked());
            pst.setString(2, mail.getEmail());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if(pst !=null) pst.close();
            if(c !=null) c.close();
        }

    }
    

    public static ArrayList<MailSend> getListMailSend() throws SQLException, Exception {
        ArrayList<MailSend> mails = new ArrayList<>();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + MailSend.TABLE_NAME + ";";
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
                String password = rs.getString("password_mail");
                String hostMail = rs.getString("host_mail");
                String lastTime = rs.getString("last_time");
                String mailBloked = rs.getString("mail_blocked");
                int maxMail = rs.getInt("max_mail");
                String msgError = rs.getString("msg_error");
                MailSend m = new MailSend();
                m.setId(id);
                m.setEmail(mail);
                m.setHostMail(hostMail);
                m.setLastTime(lastTime);
                m.setMailBlocked(mailBloked);
                m.setMaxMail(maxMail);
                m.setPassword(password);
                m.setMsgError(msgError);
                mails.add(m);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if(rs !=null) rs.close();
            if(pst !=null) pst.close();
            if(c !=null) c.close();
        }
        return mails;

    }
    
    
        public static MailSend  getMailSendByEmail(String email) throws SQLException, Exception {
        MailSend mail = new MailSend();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + MailSend.TABLE_NAME + " WHERE EMAIL=?;";
            pst = c.prepareStatement(query);
            pst.setString(1, email.trim());
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password_mail");
                String hostMail = rs.getString("host_mail");
                String lastTime = rs.getString("last_time");
                String mailBloked = rs.getString("mail_blocked");
                int maxMail = rs.getInt("max_mail");
                String msgError = rs.getString("msg_error");
                mail.setId(id);
                mail.setEmail(email);
                mail.setHostMail(hostMail);
                mail.setLastTime(lastTime);
                mail.setMailBlocked(mailBloked);
                mail.setMaxMail(maxMail);
                mail.setPassword(password);
                mail.setMsgError(msgError);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if(rs !=null) rs.close();
            if(pst !=null) pst.close();
            if(c !=null) c.close();
        }
        return mail;

    }

}
