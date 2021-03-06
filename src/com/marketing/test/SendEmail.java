/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marketing.test;

import com.marketing.mail.ui.DashBoard;
import com.marketing.db.MailDao;
import com.marketing.db.MailSendDao;
import com.marketing.entity.Mail;
import com.marketing.entity.MailSend;
import com.marketing.mail.action.EmailAction;
import java.awt.Desktop;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author PMDVCNTT
 */
public class SendEmail extends javax.swing.JFrame {

    private String myLink = "Soạn HTML";

    /**
     * Creates new form SendEmail
     */
    public SendEmail() throws IOException, Exception {
        initComponents();
        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        //projectPath C:\Users\PMDVCNTT\Documents\GitHub\EmailMarketing\
        String projectPath = helper.substring(0, helper.length() - 1);
        File folderImage = new File(projectPath + "\\src\\images\\");
        File[] listFile = folderImage.listFiles();
        if (listFile.length > 0) {
            ImageIcon ii = new ImageIcon(scaleImage(120, 120, ImageIO.read(new File(projectPath + "\\src\\images\\" + listFile[0].getName()))));//get the image from file chooser and scale it to match JLabel size
            lbImage.setIcon(ii);
        }

        setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTitle = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtAmountMailSent = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAmountMail = new javax.swing.JTextField();
        txtStatusSend = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConent = new javax.swing.JTextArea();
        btSendMail = new javax.swing.JButton();
        txtStatusSent = new javax.swing.JTextField();
        btCheckAmountMail = new javax.swing.JButton();
        btAmountSent = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btCheckMailSend = new javax.swing.JButton();
        txtNumOfMailSend = new javax.swing.JTextField();
        txtAddImage = new javax.swing.JButton();
        lbImage = new javax.swing.JLabel();
        btRemoveImage = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gửi Eail quảng bá");

        jLabel3.setText("Trạng thái mail chưa gửi");

        jLabel4.setText("Trạng thái mail đã gửi");

        txtStatusSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusSendActionPerformed(evt);
            }
        });

        jLabel6.setText("Title");

        jLabel7.setText("Content");

        txtConent.setColumns(20);
        txtConent.setRows(5);
        jScrollPane1.setViewportView(txtConent);

        btSendMail.setText("Send Mail");
        btSendMail.setToolTipText("he thong se lay danh sach email gui co so luong la amount of mail send trong  bang tbl_mail co trang thai mail send status de gui, gui thanh cong update status trong bang tbl_mail thanh mail sent status hai trang thai nay luan phien doi cho gia tri 1 va 2 cho nhau tuy chon");
        btSendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSendMailActionPerformed(evt);
            }
        });

        btCheckAmountMail.setText("Số mail thu thập được");
        btCheckAmountMail.setToolTipText("");
        btCheckAmountMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCheckAmountMailActionPerformed(evt);
            }
        });

        btAmountSent.setText("Số mail đã gửi marketing");
        btAmountSent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAmountSentActionPerformed(evt);
            }
        });

        jButton2.setText("Quay lại");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btCheckMailSend.setText("Số mail được phép gửi");
        btCheckMailSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCheckMailSendActionPerformed(evt);
            }
        });

        txtAddImage.setText("Thêm hình ảnh");
        txtAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddImageActionPerformed(evt);
            }
        });

        btRemoveImage.setText("Xóa ảnh đính kèm");
        btRemoveImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoveImageActionPerformed(evt);
            }
        });

        jLabel1.setText("<html><font color=\"#0000CF\"><u>"+myLink+"</u></font></html>");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtAddImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btRemoveImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(51, 51, 51)
                                .addComponent(lbImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(9, 9, 9))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btCheckAmountMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btAmountSent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btCheckMailSend, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btSendMail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStatusSent, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(txtAmountMail)
                            .addComponent(txtAmountMailSent)
                            .addComponent(txtStatusSend, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(txtNumOfMailSend)
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btCheckAmountMail)
                            .addComponent(txtAmountMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAmountSent)
                    .addComponent(txtAmountMailSent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btCheckMailSend)
                                    .addComponent(txtNumOfMailSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(txtStatusSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(txtStatusSent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton2)
                            .addComponent(btSendMail))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btRemoveImage)
                                .addGap(18, 18, 18)
                                .addComponent(txtAddImage)
                                .addGap(46, 46, 46))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lbImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(34, 34, 34))))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStatusSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusSendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusSendActionPerformed

    private void btSendMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSendMailActionPerformed
        try {
            //lay danh sach mail gui
            ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
            for (MailSend mailSend : lstSend) {
                try {
                    String title = txtTitle.getText().trim();
                    String content = txtConent.getText().trim();
                    String sttMailSend = txtStatusSend.getText().trim();
                    String sttMailSent = txtStatusSent.getText().trim();
                    //lay danh sach mail gui theo trang thai va so luong mail cho phep gui trong ngay
                    ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, String.valueOf(mailSend.getMaxMail()));
                    //test
//                    ArrayList<Mail> lst = new ArrayList<>();
//                    Mail m = new Mail();
//                    m.setEmail("tunglv9x@gmail.com");
//                    lst.add(m);
                    //kiem tra thoi gian hien tai co thoa man gui mail khong
                    //checkTime = true roi vao truong hop mailSend.getLastTime()=null va khac "" 
                    boolean checkTime = true;
                    if (mailSend.getLastTime() != null && !mailSend.getLastTime().isEmpty()) {
                        checkTime = checkRunTime(mailSend.getLastTime());
                    }

                    if (checkTime) {
                        for (Mail to : lst) {
                            try {
                                if (mailSend.getMailBlocked() != null && !mailSend.getMailBlocked().isEmpty() && mailSend.getMailBlocked().contains(to.getEmail().trim())) {
                                    continue;
                                }
                                if (mailSend.getHostMail().equals(Mail.GMAIL_HOST)) {
                                    EmailAction.sendGmail(null,mailSend.getEmail(), mailSend.getPassword(), to.getEmail(), title, content);

                                }
                                if (mailSend.getHostMail().equals(Mail.OUTLOOK_HOST)) {
                                    EmailAction.sendOutlookMail(mailSend.getEmail(), mailSend.getPassword(), to.getEmail(), title, content);
                                }
                                if (mailSend.getHostMail().equals(Mail.ZOHO_HOST)) {
                                    EmailAction.sendZohoMail(mailSend.getEmail(), mailSend.getPassword(), to.getEmail(), title, content);
                                }

                                System.out.println("---------------- tunglv4 gui mail " + mailSend.getEmail() + " tu host " + mailSend.getHostMail() + " toi: " + to.getEmail() + " thanh cong");
                                //update status mail nhan
                                to.setStatus(Integer.parseInt(sttMailSent));
                                MailDao.updateMail(to);

                            } catch (Exception e) {
                                System.out.println("-----------------tunglv4 gui toi mail: " + to.getEmail() + " bi loi: " + e.getMessage());
//                            if (e.getMessage().contains("554 5.2.0")) {
//                                continue;
//                            }
                                if (e.getMessage().contains("550 5.4.5")) {
                                    MailSendDao.updateMailLastTime(mailSend);
                                    throw new Exception("------------tunglv4 gui qua so luong mail cho phep trong ngay");
                                }
                            }
                        }
                        //update thoi gian mail gui
                        //kiem tra phai co mail gui thi moi update thoi gian, chua them dieu kien
                        //so mail da gui phai =so mail max config trong db
                        if (lst.size() > 0) {
                            MailSendDao.updateMailLastTime(mailSend);
                        }
                    }
                } catch (Exception e) {
                    System.out.println(mailSend.getEmail() + " : " + e.getMessage());
                    continue;
                }

            }
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSendMailActionPerformed

    private void btCheckAmountMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCheckAmountMailActionPerformed
        try {
            int amount = MailDao.count();
            txtAmountMail.setText(String.valueOf(amount));
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btCheckAmountMailActionPerformed

    private void btAmountSentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAmountSentActionPerformed
        try {
            String statusSent = txtStatusSent.getText().trim();
            if (!statusSent.isEmpty() && statusSent != null) {
                int amount = MailDao.countMailSent(statusSent);
                txtAmountMailSent.setText(String.valueOf(amount));
            }
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAmountSentActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new DashBoard();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btCheckMailSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCheckMailSendActionPerformed
        txtNumOfMailSend.setText("0");
        int count = 0;
        try {
            //lay ra danh sach mail gui
            ArrayList<MailSend> lstSend = MailSendDao.getListMailSend();
            for (MailSend mailSend : lstSend) {
                //neu gia tri mailSend.getLastTime() khac null va khac ""
                if (mailSend.getLastTime() != null && !mailSend.getLastTime().isEmpty()) {
                    boolean checkTime = checkRunTime(mailSend.getLastTime());
                    //thoi gian thoa man gui mail
                    if (checkTime) {
                        count++;
                    }
                } else {
                    count++;
                }

            }
            if (count > 0) {
                txtNumOfMailSend.setText(String.valueOf(count));
            }
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btCheckMailSendActionPerformed

    private void txtAddImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddImageActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        if (f != null) {
            String filename = f.getAbsolutePath();

            try {
                File currentDirFile = new File(".");
                String helper = currentDirFile.getAbsolutePath();
                //projectPath C:\Users\PMDVCNTT\Documents\GitHub\EmailMarketing\
                String projectPath = helper.substring(0, helper.length() - 1);
                FileUtils.cleanDirectory(new File(projectPath + "\\src\\images\\"));
                File dest = new File(projectPath + "\\src\\images\\" + f.getName());
                File source = new File(filename);
                FileUtils.copyFile(source, dest);
                ImageIcon ii = new ImageIcon(scaleImage(120, 120, ImageIO.read(new File(f.getAbsolutePath()))));//get the image from file chooser and scale it to match JLabel size
                lbImage.setIcon(ii);
                System.out.println("filename: " + filename);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_txtAddImageActionPerformed

    private void btRemoveImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoveImageActionPerformed
        try {
            File currentDirFile = new File(".");
            String helper = currentDirFile.getAbsolutePath();
            //projectPath C:\Users\PMDVCNTT\Documents\GitHub\EmailMarketing\
            String projectPath = helper.substring(0, helper.length() - 1);
            FileUtils.cleanDirectory(new File(projectPath + "\\src\\images\\"));
            lbImage.setIcon(null);
        } catch (IOException ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btRemoveImageActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        //mo link
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("https://wordtohtml.net/"));
            } catch (URISyntaxException | IOException ex) {
                Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jLabel1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SendEmail.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new SendEmail().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAmountSent;
    private javax.swing.JButton btCheckAmountMail;
    private javax.swing.JButton btCheckMailSend;
    private javax.swing.JButton btRemoveImage;
    private javax.swing.JButton btSendMail;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbImage;
    private javax.swing.JButton txtAddImage;
    private javax.swing.JTextField txtAmountMail;
    private javax.swing.JTextField txtAmountMailSent;
    private javax.swing.JTextArea txtConent;
    private javax.swing.JTextField txtNumOfMailSend;
    private javax.swing.JTextField txtStatusSend;
    private javax.swing.JTextField txtStatusSent;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables

    private boolean checkRunTime(String lastDate) {
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        //lay ra yyyyMMdd
        String endDate = lastDate.trim().substring(0, 8);
        //lay ra HHmmss
        String endTime = lastDate.trim().substring(8, 14);
        //lay thong tin thoi gian hien tai
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currDate = sdf.format(currentDate);
        //lay ra yyyyMMdd thoi gian hien tai
        String curDate = currDate.trim().substring(0, 8);
        //lay ra HHmmss thoi gian hien tai
        String curTime = currDate.trim().substring(8, 14);
        //dieu kien de chay la ngay hien tai phai khac ngay gui cuoi cung
        //thoi gian hien tai phai lon hon thoi gian gui cuoi cung
        //chua xu ly dc hqua chay 21h xong hnay qua 12h no la 00 nen ko chay

        if (!endDate.equals(curDate) && Integer.parseInt(curTime) > Integer.parseInt(endTime)) {
            return true;
        }
        if(Integer.parseInt(curDate) - Integer.parseInt(endDate) >= 2){
            return true;
        }
        return false;
    }

    public static BufferedImage scaleImage(int w, int h, BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, w, h, null);
        g2d.dispose();
        return bi;
    }

}
