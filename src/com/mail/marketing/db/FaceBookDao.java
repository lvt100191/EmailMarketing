/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.db;

import com.mail.marketing.config.Config;
import com.mail.marketing.entity.FaceBook;
import com.mail.marketing.entity.Mail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author TUNGLV
 */
public class FaceBookDao {
     static Logger logger = Logger.getLogger(FaceBookDao.class.getName());

    public static void insert(FaceBook face) throws SQLException, Exception {
        Connection c = null;
        PreparedStatement pst = null;

        try {
            c = DBUtil.connectDB(Config.DB_NAME);

            String query = "INSERT INTO " + FaceBook.TABLE_NAME
                    + "(id_facebook,"
                    + "name,"
                    + "type,"
                    + "note) "
                    + "VALUES (?,?,?,?);";
            pst = c.prepareStatement(query);
            pst.setString(1, face.getId());
            pst.setString(2, face.getName());
            pst.setString(3, face.getType());
            pst.setString(4, "");
            pst.executeUpdate();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            pst.close();
            c.close();
        }
    }
}
