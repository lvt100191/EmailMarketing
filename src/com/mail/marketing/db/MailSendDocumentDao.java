/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.MailSendDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author TUNGLV
 */
public class MailSendDocumentDao {

    public static void insert(MailSendDocument mail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + MailSendDocument.TABLE_NAME
                    + "(id_feed,"
                    + "email,"
                    + "status,"
                    + "create_date,"
                    + "link_document,"
                    + "content_feed) "
                    + "VALUES (?,?,?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, mail.getIdFeed());
            pst.setString(2, mail.getEmail());
            pst.setInt(3, mail.getStatus());
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String dateCreate = sdf.format(d);
            pst.setString(4, dateCreate);
            pst.setString(5, mail.getLinkDocument());
            pst.setString(6, mail.getContent());
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

    public static MailSendDocument getByEmail(String email) throws SQLException, Exception {
        MailSendDocument m = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + MailSendDocument.TABLE_NAME
                    + " WHERE "
                    + " EMAIL = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
                m = new MailSendDocument();
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
}
