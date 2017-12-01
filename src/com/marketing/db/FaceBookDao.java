/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.db;

import coml.marketing.config.Config;
import com.marketing.entity.FaceBook;
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
public class FaceBookDao {

    static Logger logger = Logger.getLogger(FaceBookDao.class.getName());

    public static FaceBook getFaceBook(FaceBook face) throws SQLException, Exception {
        FaceBook fb = null;
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + FaceBook.TABLE_NAME
                    + " WHERE "
                    + " id_facebook = ? and type=?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, face.getIdFacebook());
            pst.setString(2, face.getType());
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String idFacebook = rs.getString("id_facebook");
                fb = new FaceBook();
                fb.setId(id);
                fb.setIdFacebook(idFacebook);

            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
        return fb;

    }

    public static ArrayList<FaceBook> getListFaceBook(String type) throws SQLException, Exception {
        ArrayList<FaceBook> lst = new ArrayList<>();
        Connection c = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "SELECT * FROM  " + FaceBook.TABLE_NAME
                    + " WHERE "
                    + " type=?; ";
            pst = c.prepareStatement(query);
            pst.setString(1, type);
            rs = pst.executeQuery();
            while (rs.next()) {
                FaceBook fb = new FaceBook();
                int id = rs.getInt("id");
                String idFacebook = rs.getString("id_facebook");
                String name = rs.getString("name");
                fb = new FaceBook();
                fb.setId(id);
                fb.setIdFacebook(idFacebook);
                fb.setName(name);
                fb.setType(type);
                lst.add(fb);

            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            rs.close();
            pst.close();
            c.close();
        }
        return lst;

    }

    public static void insert(FaceBook face) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + FaceBook.TABLE_NAME
                    + "(id_facebook,"
                    + "name,"
                    + "type,"
                    + "note,"                    
                    + "member) "
                    + "VALUES (?,?,?,?,?);";//chu y phai du tham so va dau hoi ?
            pst = c.prepareStatement(query);
            pst.setString(1, face.getIdFacebook());
            pst.setString(2, face.getName());
            pst.setString(3, face.getType());
            pst.setString(4, "");
            pst.setString(5, face.getMember());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            pst.close();
            c.close();
        }
    }
}
