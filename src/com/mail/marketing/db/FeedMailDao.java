/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.FeedMail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author PMDVCNTT
 */
public class FeedMailDao {
        public static FeedMail getByIdTblFeedIdTblMail(int idTblFeed, int idTblMail ) throws SQLException, Exception {
        FeedMail fm = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + FeedMail.TABLE_NAME
                    + " WHERE "
                    + " ID_TBL_FEED = ? and ID_TBL_MAIL=?; ";
            pst = c.prepareStatement(query);
            pst.setInt(1, idTblFeed);
            pst.setInt(2, idTblMail);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                fm = new FeedMail();
                fm.setId(id);
                fm.setIdTblFeed(idTblFeed);
                fm.setIdTblMail(idTblMail);
                fm.setCreateDate(rs.getString("create_date"));
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
        return fm;

    }

    public static void insert(FeedMail fm) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + FeedMail.TABLE_NAME
                    + "(id_tbl_feed,"
                    + "id_tbl_mail,"
                    + "create_date) "
                    + "VALUES (?,?,?);";
            pst = c.prepareStatement(query);
            pst.setInt(1, fm.getIdTblFeed());
            pst.setInt(2, fm.getIdTblMail());
            pst.setString(3, fm.getCreateDate());
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
}
