/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TUNGLV
 */
public class Config {
    private static Properties prop = null;
    static{
        prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("..\\EmailMarketing\\src\\config.properties");

		// load a properties file
		prop.load(input);

	} catch (IOException ex) {
		ex.printStackTrace();
	} finally {
		if (input != null) {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

    }
    //lay gia tri config trong file config.properties o day
    public static final String DB_NAME = prop.getProperty("db_name");
    public static final String USER_ACCESS_TOKEN = prop.getProperty("USER_ACCESS_TOKEN");
    public static final String NUMBER_MAIL = prop.getProperty("number_mail");
    public static final String STATUS_MAIL_SEND = prop.getProperty("status_mail_send");
    public static final String STATUS_MAIL_UPDATE = prop.getProperty("status_mail_update");
}
