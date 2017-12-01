/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.db;

import com.marketing.config.Config;
import com.marketing.entity.FeedMail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author PMDVCNTT
 */
public class FeedMailDao {

    public static FeedMail getByIdTblFeedIdTblMail(int idTblFeed, int idTblMail) throws SQLException, Exception {
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
                    + "create_date,"
                    + "status) "
                    + "VALUES (?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setInt(1, fm.getIdTblFeed());
            pst.setInt(2, fm.getIdTblMail());
            pst.setString(3, fm.getCreateDate());
            pst.setInt(4, fm.getStatus());
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

    public static void deleteFeedMail(int idTblMail) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "DELETE FROM  " + FeedMail.TABLE_NAME
                    + " WHERE ID_TBL_MAIL= ?; ";
            pst = c.prepareStatement(query);
            pst.setInt(1, idTblMail);
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

    public static void updateStatus(FeedMail fm) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "UPDATE  " + FeedMail.TABLE_NAME
                    + " SET STATUS = ? WHERE ID= ?; ";
            pst = c.prepareStatement(query);
            pst.setInt(1, FeedMail.STATUS_SENT);
            pst.setInt(2, fm.getId());
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

    //dem so email chua gui theo bai viet
    public static String countMailToNoSendByFeed(String status, String idTblFeed) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);
            String query = "select count(*) from   " + FeedMail.TABLE_NAME + "  where status=? and  id_tbl_feed=?;";
            pst = c.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(status));
            pst.setInt(2, Integer.parseInt(idTblFeed));
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return String.valueOf(count);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
    }

    //dem so mail thu thập được theo bài viết
    public static String countMailToByFeed(String idTblFeed) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);
            String query = "select count(*) from   " + FeedMail.TABLE_NAME + "  where  id_tbl_feed=?;";
            pst = c.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(idTblFeed));
            rs = pst.executeQuery();
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return String.valueOf(count);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
    }

    public static ArrayList<FeedMail> getFmList(String status, String idTblFeed, String limit) throws Exception {
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<FeedMail> lst = new ArrayList<>();

        try {
            c = DBUtil.connectDB(Config.DB_NAME);
            String query = "select * from   " + FeedMail.TABLE_NAME + "  where  id_tbl_feed=? and status=? limit ?;";
            pst = c.prepareStatement(query);
            pst.setInt(1, Integer.parseInt(idTblFeed));
            pst.setInt(2, Integer.parseInt(status));
            pst.setInt(3, Integer.parseInt(limit));
            rs = pst.executeQuery();
            while (rs.next()) {
                FeedMail fm = new FeedMail();
                int id = rs.getInt("id");
                int idTblMail = rs.getInt("id_tbl_mail");
                fm.setId(id);
                fm.setIdTblFeed(Integer.parseInt(idTblFeed));
                fm.setIdTblMail(idTblMail);
                String date = rs.getString("create_date");
                if (date != null && !date.isEmpty()) {
                    fm.setCreateDate(rs.getString("create_date"));
                }
                lst.add(fm);
            }
            return lst;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
    }
}
