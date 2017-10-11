/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.test;

import com.sun.mail.imap.IMAPStore;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Quota;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author PMDVCNTT
 */
public class Test {
    public static void main(String[] args) throws NoSuchProviderException, MessagingException {
 Properties prop = System.getProperties();
String html="<html><font color='#0000CF'><u>Soạn HTML</u></font></html>";
    String host = "imap.outlook.com";
    String username = "coso1.mshoatoeic@outlook.com";
    String password = "123456a@";

    prop.setProperty("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    prop.setProperty("mail.imap.host", host);
    prop.setProperty("mail.imap.port", "993");
    prop.setProperty("mail.imap.starttls.enable", "true");
    prop.setProperty("mail.imap.socketFactory.fallback", "false");
    prop.setProperty("mail.debug", "true");

    Session ses = Session.getInstance(prop, null);
    Store store = ses.getStore("imap");
    store.connect(host, username, password);

    if (!IMAPStore.class.isInstance(store))
        throw new IllegalStateException("Is not IMAPStore");

    IMAPStore imapStore = (IMAPStore) store;
    Quota[] quotas = imapStore.getQuota("INBOX");

    for (Quota quota : quotas) {
        System.out.println(String.format("quotaRoot:'%s'", quota.quotaRoot));

        for (Quota.Resource resource : quota.resources) {
            System.out.println(String.format("name:'%s', limit:'%s', usage:'%s'",
                    resource.name, resource.limit, resource.usage));
        }
    }
    }
}
