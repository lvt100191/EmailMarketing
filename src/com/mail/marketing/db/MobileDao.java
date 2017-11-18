/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.Mobile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PMDVCNTT
 */
public class MobileDao {
    
        public static void insert(Mobile mobile) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + Mobile.TABLE_NAME
                    + "(mobile,"
                    + "create_date) "
                    + "VALUES (?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, mobile.getMobile());
            pst.setString(2, mobile.getCreateDate());
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
        
    public static Mobile getMobile(String number) throws SQLException, Exception {
        Mobile m = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + Mobile.TABLE_NAME
                    + " WHERE "
                    + " LOWER(mobile) = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, number);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String num = rs.getString("mobile");
                String dateStr = rs.getString("create_date");
                m = new Mobile();
                m.setId(id);
                m.setMobile(num);
                m.setCreateDate(dateStr);

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
