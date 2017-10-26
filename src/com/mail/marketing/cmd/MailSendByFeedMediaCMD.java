/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.cmd;

import com.mail.marketing.db.FeedEntityDao;
import com.mail.marketing.db.FeedMailDao;
import com.mail.marketing.db.MailBlockDao;
import com.mail.marketing.db.MailDao;
import com.mail.marketing.db.MailSendDao;
import com.mail.marketing.entity.FeedEntity;
import com.mail.marketing.entity.FeedMail;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.entity.MailBlock;
import com.mail.marketing.entity.MailSend;
import com.mail.marketing.mail.EmailAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author TUNGLV
 */
//su dung 1 mail gui, duyet qua tat ca bai viet trong tbl_feed co trang thai 1
//title_send va content_send ko null
//gui thong bao den cac mail thuoc bai viet chua thuc hien gui mail
//            String host ="mail.vnedu.vn";
//            String mailSend1 = "noreply@truelife.vn";
public class MailSendByFeedMediaCMD {
    //tham so truyen vao
    //so luong ban ghi lay ra tu bang tbl_feed
    private static String numFeed = "1000";
    //lay bai viet co trang thai la 1 de gui mail
    private static int statusFeed = 1;
    //trang thai chua gui mail theo bai viet
    static String statusFeedMailSend = "1";
    //trang thai da gui mail theo bai viet
    static String statusFeedMailSent = "2";
    //so luong toi da mail lay ra de gui trong bang tbl_mail
    static String numMaxMailTo = "1000";
    //server mail
    static String host = "mail.vnedu.vn";
    //mail gui 
    static String mailSend = "noreply@truelife.vn";

    public static void main(String[] args) throws Exception {
        System.out.println("tham so dau tien: "+args[0]);
        //lay danh sach bai viet trong bang tbl_feed
        //lay danh sach bai viet tu bang tbl_feed theo so luong truyen vao
        ArrayList<FeedEntity> lstFeed = FeedEntityDao.getListFeedEntity(numFeed, statusFeed);
        //duyet qua tung bai viet 
        for (FeedEntity f : lstFeed) {
            //lay ra danh sach dia chi email chua gui thu
            //select * from tbl_mail where id in (select  id_tbl_mail from tbl_feed_mail where id_tbl_feed=38 and status=1)
            ArrayList<Mail> lst = MailDao.getMailFromTblFeed(String.valueOf(f.getId()), statusFeedMailSend, numMaxMailTo);
                                        //test
//                    ArrayList<Mail> lst = new ArrayList<>();
//                    Mail mx = new Mail();
//                    mx.setEmail("tunglv9x@gmail.com");
//                    lst.add(mx);
//lay ra thong tin bai viet tu bang tbl_feed
            FeedEntity feedEntity = FeedEntityDao.getByFeed(f.getIdFeed());
            //lay tieu de, noi dung de gui mail
            String title = feedEntity.getTitleSend();
            String content = feedEntity.getContentSend();
            String sendName = feedEntity.getFanpageName();
            if (title == null || title.isEmpty()) {
                continue;
            }
            if (content == null || content.isEmpty()) {
                continue;
            }
            System.out.println("Gui mail cho bai viet: " + f.getId() + " so luong mail: " + lst.size());
            sendMail(String.valueOf(f.getId()), host, sendName, mailSend, title, content, lst, statusFeedMailSent);
        }
        System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx-----------------");
        System.out.println("--------------CHUONG TRINH KET THUC-----------------");
        System.out.println("--------------xxxxxxxxxxxxxxxxxxxxx-----------------");
    }

    private static void sendMail(String idTblFeed, String host, String sendName, String mailSend, String title, String content, ArrayList<Mail> lst, String statusFeedMailSent) throws Exception {
        //lay thong tin mail gui
        MailSend mSend = MailSendDao.getMailSendByEmail(mailSend);
        int countMailSentSuccess = 0;
        for (Mail to : lst) {
            try {
                if (mSend.getMailBlocked() != null && !mSend.getMailBlocked().isEmpty() && mSend.getMailBlocked().contains(to.getEmail().trim())) {
                    continue;
                }
                //neu mail da ton tai trong bang tbl_mail_blocked thi khong gui mail
                if (checkMailBlock(to.getEmail().trim())) {
                    continue;
                }

                sendMailMedia(host, sendName, mailSend, to.getEmail().toLowerCase(), title, content);

                System.out.println("---------------- CMD gui mail theo bai viet tu " + mSend.getEmail() + " toi: " + to.getEmail() + " thanh cong");
                System.out.println("----------------So luong mail da gui: " + countMailSentSuccess++);
                //update status=2 tbl_feed_mail da gui
                FeedMail fm = FeedMailDao.getByIdTblFeedIdTblMail(Integer.parseInt(idTblFeed), to.getId());
                fm.setStatus(Integer.parseInt(statusFeedMailSent));
                FeedMailDao.updateStatus(fm);

            } catch (AddressException adEx) {
                System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + adEx.getMessage());
                //tim mail trong bang tbl_mail lay ra id cua ban ghi
                Mail m = MailDao.getByEmail(to.getEmail());
                //xoa ban ghi trong bang tbl_feed_mail theo id
                if (m != null) {
                    FeedMailDao.deleteFeedMail(m.getId());
                    //xoa ban ghi trong bang tbl_mail
                    MailDao.deleteMail(m.getId());
                }
                //insert vao bang tbl_mail_blocked
                MailBlock mb = initMailBlock(to.getEmail());
                MailBlockDao.insert(mb);
            } catch (Exception e) {
                System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + e.getMessage());
//                            if (e.getMessage().contains("554 5.2.0")) {
//                                continue;
//                            }
                if (e.getMessage().contains("550 5.4.5")) {
                    MailSendDao.updateMailLastTime(mSend);
                    throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                }

            } finally {

            }
        }
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

    private static MailBlock initMailBlock(String mailLock) {
        MailBlock m = new MailBlock();
        m.setMailBlock(mailLock);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCreate = sdf.format(d);
        m.setCreateDate(dateCreate);
        return m;
    }

    public static void sendMailMedia(String host, String sendName, String sendMail, String mailReceiver, String title, String content) {
        try {
            final String username = "";
            final String password = "";

            Properties props = new Properties();
            //danh sach host
            //mail.truelife.vn
            //mail.vnedu.vn
            //mail.vos.vasc.com.vn

            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "25");

            Session session = null;
            if (username != null && username != "") {
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                session = Session.getInstance(props, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
            } else {
                session = Session.getInstance(props, null);
            }

            MimeMessage msg = new MimeMessage(session);
            msg.setHeader("Content-Type", "text/plain; charset=UTF-8");

            InternetAddress senderAddress = new InternetAddress(sendMail);
            senderAddress.setPersonal(sendName, "UTF-8");
            senderAddress.validate();
            msg.setFrom(senderAddress);

            InternetAddress receiverAddress = new InternetAddress(mailReceiver);
            receiverAddress.validate();
            msg.setRecipient(Message.RecipientType.TO, receiverAddress);
            msg.setSubject(title, "UTF-8");
            msg.setSentDate(new Date());
            MimeMultipart multipart = new MimeMultipart("related");

            // first part (the html)
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html; charset=UTF-8");

            // add it
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            Transport.send(msg);
        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (SendFailedException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
