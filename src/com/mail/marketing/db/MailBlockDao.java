/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.MailBlock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PMDVCNTT
 */
public class MailBlockDao {
    public static MailBlock getByEmail(String email) throws SQLException, Exception {
        MailBlock m = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + MailBlock.TABLE_NAME
                    + " WHERE "
                    + " EMAIL_BLOCK = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, email);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String mail = rs.getString("email");
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
