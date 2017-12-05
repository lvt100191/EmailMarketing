/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.mail.main;

import com.marketing.db.FeedMailDao;
import com.marketing.db.MailBlockDao;
import com.marketing.db.MailDao;
import com.marketing.entity.FeedMail;
import com.marketing.entity.Mail;
import com.marketing.entity.MailBlock;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author TUNGLV
 */
public class CheckMailAddress {

    //truoc khi RUN truyen vao danh sach mail can check trong file
    //EmailMarketing\\src\\kiem_tra_danh_sach_mail_nhan.txt
    public static void main(String[] args) throws IOException {
        String listMailAddressValid = "";
        String listMailAddressInValid = "";
        //doc file txt danh sach dia chi mail can kiem tra dia chi - trỏ đúng đường dẫn
        String filePath = "..\\EmailMarketing\\src\\kiem_tra_danh_sach_mail_nhan.txt";
        File file = new File(filePath);
        for (String line : FileUtils.readLines(file)) {
            if (line.contains("@gmail.com")) {
                Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(line);
                while (m.find()) {
                    try {
                        String mail = m.group();
                        char end = mail.charAt(mail.length() - 1);
                        if (end == '.') {
                            mail = mail.substring(0, mail.length() - 1);
                        }
                        //kiem tra dieu kien truoc khi insert vao db
                        //truong hop mail ko bi chan va mail chua co trong
                        //bang tbl_mail
                        if (mail != null) {
                            mail = mail.trim();
                            mail = mail.toLowerCase();
                        }
                        if (!checkMailBlock(mail) && checkAddressMail(mail)) {
                            if (!listMailAddressValid.contains(mail)) {
                                listMailAddressValid = listMailAddressValid + mail + "\n";
                            }
                        } else {
                            listMailAddressInValid = listMailAddressInValid + line + "\n";
                        }

                    } catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                        e.printStackTrace();

                    }
                }
            }

        }
        //dia chi mail hop le la dia chi chua bi chan trong bang tbl_mail_blocked
        //dung dinh dang gmail
        System.out.println("==============DIA CHI MAIL HOP LE===================: \n" + listMailAddressValid);
        System.out.println("==============DIA CHI MAIL KHONG HOP LE=============: \n" + listMailAddressInValid);
    }

    private static boolean checkAddressMail(String mail) {
        if (mail.contains("@gmail.con")) {
            return false;
        }
        if (mail.contains("@gmail.com.")) {
            return false;
        }
        if (mail.contains("@gamil.com")) {
            return false;
        }
        if (mail.contains("..")) {
            return false;
        }
        if (mail.startsWith("_")) {
            return false;
        }
        if (mail.startsWith(".")) {
            return false;
        }

        if (mail.startsWith("0")) {
            return false;
        }
        if (mail.startsWith("1")) {
            return false;
        }
        if (mail.startsWith("2")) {
            return false;
        }
        if (mail.startsWith("3")) {
            return false;
        }
        if (mail.startsWith("4")) {
            return false;
        }
        if (mail.startsWith("5")) {
            return false;
        }
        if (mail.startsWith("6")) {
            return false;
        }
        if (mail.startsWith("7")) {
            return false;
        }
        if (mail.startsWith("8")) {
            return false;
        }
        if (mail.startsWith("9")) {
            return false;
        }

        return true;
    }

    //check dia chi mail co bi chan hay chua
    //false: mail chua ton tai
    //true: mail da ton tai
    private static boolean checkMailBlock(String mail) throws Exception {
        //ko insert mail đã tồn tại trong danh sách mail chặn tbl_mail_block
        MailBlock mailBlock = MailBlockDao.getByEmail(mail);
        if (mailBlock != null) {
            return true;
        }
        return false;
    }
}
