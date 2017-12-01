/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.db;

import coml.marketing.config.Config;
import com.marketing.entity.MailBlock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PMDVCNTT
 */
public class MailBlockDao {
    
        public static void insert(MailBlock mailBlock) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + MailBlock.TABLE_NAME
                    + "(email_block,"
                    + "create_date) "
                    + "VALUES (?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, mailBlock.getMailBlock());
            pst.setString(2, mailBlock.getCreateDate());
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
        
    public static MailBlock getByEmail(String email) throws SQLException, Exception {
        MailBlock m = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + MailBlock.TABLE_NAME
                    + " WHERE "
                    + " LOWER(EMAIL_BLOCK) = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email_block");
                m = new MailBlock();
                m.setId(id);
                m.setMailBlock(mail);

            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if(rs !=null) rs.close();
            if(pst !=null) pst.close();
            if(c !=null) c.close();
        }
        return m;

    }
    
}
