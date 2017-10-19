/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.Mail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author PMDVCNTT
 */
public class FeedEntityDao {

    public static ArrayList<FeedEntity> getListFeedEntity(String limit) throws SQLException, Exception {
        ArrayList<FeedEntity> feedEntitys = new ArrayList<>();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + FeedEntity.TABLE_NAME
                    + " WHERE STATUS = ? LIMIT ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, limit);
            rs = pst.executeQuery();
            while (rs.next()) {
                FeedEntity feedEntity = unitFeedEntity(rs);
                feedEntitys.add(feedEntity);

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
        return feedEntitys;

    }

    public static FeedEntity getByFeed(String feedId) throws SQLException, Exception {
        FeedEntity f = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + FeedEntity.TABLE_NAME
                    + " WHERE "
                    + " ID_FEED = ?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, feedId);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                f = new FeedEntity();
                f.setId(id);
                f.setIdFeed(feedId);

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
        return f;

    }

    public static void insert(FeedEntity feedEntity) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + FeedEntity.TABLE_NAME
                    + "(id_feed,"
                    + "content_feed,"
                    + "create_date,"
                    + "id_fanpage,"
                    + "fanpage_name) "
                    + "VALUES (?,?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, feedEntity.getIdFeed());
            pst.setString(2, feedEntity.getContentFeed());
            pst.setString(3, feedEntity.getCreateDate());
            pst.setString(4, feedEntity.getIdFanpage());
            pst.setString(5, feedEntity.getFanpageName());
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

    private static FeedEntity unitFeedEntity(ResultSet rs) throws SQLException {
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setId(rs.getInt("id"));
        feedEntity.setIdFeed(rs.getString("id_feed"));
        feedEntity.setContentFeed(rs.getString("content_feed"));
        feedEntity.setCreateDate(rs.getString("create_date"));
        feedEntity.setAmountMail(rs.getInt("amount_mail"));
        feedEntity.setTitleSend(rs.getString("title_send"));
        feedEntity.setContentSend(rs.getString("content_send"));
        feedEntity.setLinkDocument(rs.getString("link_document"));
        feedEntity.setIdFanpage(rs.getString("id_fanpage"));
        feedEntity.setFanpageName(rs.getString("fanpage_name"));
        return feedEntity;
    }

}
