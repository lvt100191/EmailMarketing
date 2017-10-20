/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.Mail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author TUNGLV
 */
public class MailDao {

    static Logger logger = Logger.getLogger(MailDao.class.getName());
    
            public static void deleteMail(int id) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "DELETE FROM  " + Mail.TABLE_NAME
                    + " WHERE ID= ?; ";
            pst = c.prepareStatement(query);
            pst.setInt(1, id);
            pst.execute();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (c != null) {
                c.close();
            }
        }

    }

    public static void insert(Mail mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + Mail.TABLE_NAME
                    + "(email,"
                    + "owner,"
                    + "birth_day,"
                    + "address,"
                    + "mobile,"
                    + "note,"
                    + "create_date,"
                    + "status,"
                    + "status_feed_mail) "
                    + "VALUES (?,?,?,?,?,?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, mail.getEmail());
            pst.setString(2, "");
            pst.setString(3, "");
            pst.setString(4, "");
            pst.setString(5, "");
            pst.setString(6, "");
            pst.setString(7, mail.getCreateDate());
            pst.setInt(8, mail.getStatus());
            pst.setInt(9, mail.getStatusFeedMail());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (c != null) {
                c.close();
            }
        }
    }
    //lay doi tuong Mail theo địa chỉ email
    public static Mail getByEmail(String email) throws SQLException, Exception {
        Mail m = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + Mail.TABLE_NAME
                    + " WHERE "
                    + " EMAIL = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
                m = new Mail();
                m.setId(id);
                m.setEmail(mail);

            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return m;

    }

    public static ArrayList<Mail> getListMail(String status, String limit) throws SQLException, Exception {
        ArrayList<Mail> mails = new ArrayList<>();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + Mail.TABLE_NAME
                    + " WHERE STATUS = ? LIMIT ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, status);
            pst.setString(2, limit);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
                Mail m = new Mail();
                m.setId(id);
                m.setEmail(mail);
                mails.add(m);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (c != null) {
                c.close();
            }
        }
        return mails;

    }

    //tam thoi chi update status
    public static void updateMail(Mail mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "UPDATE  " + Mail.TABLE_NAME
                    + " SET STATUS = ? WHERE ID= ?; ";
            pst = c.prepareStatement(query);
            pst.setInt(1, mail.getStatus());
            pst.setInt(2, mail.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (c != null) {
                c.close();
            }
        }

    }

    //lay so luong toan bo email trong bang tbl_mail
    public static int count() throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);
            String query = "select count(*) from   " + Mail.TABLE_NAME + ";";
            pst = c.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }

    }

    public static int countMailSent(String statusSent) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);
            String query = "select count(*) from   " + Mail.TABLE_NAME + " where status=?;";
            pst = c.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(statusSent));
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
    }
}
