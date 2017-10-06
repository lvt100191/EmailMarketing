/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.MarketingDaily;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author TUNGLV
 */
public class MarketingDailyDao {

    public static void insert(MarketingDaily marketingDaily) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + MarketingDaily.TABLE_NAME
                    + "(id_feed,"
                    + "post_date,"
                    + "from_source,"
                    + "name,"
                    + "amount_mail,"
                    + "mail_list,"
                    + "note,"
                    + "content) "
                    + "VALUES (?,?,?,?,?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, marketingDaily.getIdFeed());
            pst.setString(2, marketingDaily.getPostDate());
            pst.setString(3, marketingDaily.getFromSource());
            pst.setString(4, marketingDaily.getName());
            pst.setInt(5, marketingDaily.getAmountMail());
            pst.setString(6, marketingDaily.getMailList());
            pst.setString(7, marketingDaily.getNote());
            pst.setString(7, marketingDaily.getContent());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            pst.close();
            c.close();
        }
    }
}
