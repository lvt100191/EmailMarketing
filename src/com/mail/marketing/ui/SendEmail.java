/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mail.marketing.ui;

import com.mail.marketing.db.MailDao;
import com.mail.marketing.entity.Mail;
import com.mail.marketing.mail.EmailAction;
import static com.mail.marketing.mail.EmailAction.sendEmail;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PMDVCNTT
 */
public class SendEmail extends javax.swing.JFrame {

    /**
     * Creates new form SendEmail
     */
    public SendEmail() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        txtPassUserSend = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtAmountMailSent = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAmountMail = new javax.swing.JTextField();
        txtStatusSend = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtUserSend = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtConent = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        btSendMail = new javax.swing.JButton();
        txtStatusSent = new javax.swing.JTextField();
        btSendMailDaily = new javax.swing.JButton();
        btCheckAmountMail = new javax.swing.JButton();
        btAmountSent = new javax.swing.JButton();
        lbLimit = new javax.swing.JLabel();
        txtLimitMailSend = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("From");

        jLabel2.setText("Password");

        jLabel3.setText("Trạng thái mail chưa gửi");

        jLabel4.setText("Trạng thái mail đã gửi");

        txtStatusSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusSendActionPerformed(evt);
            }
        });

        jLabel6.setText("Title");

        txtUserSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserSendActionPerformed(evt);
            }
        });

        jLabel7.setText("Content");

        txtConent.setColumns(20);
        txtConent.setRows(5);
        jScrollPane1.setViewportView(txtConent);

        jButton1.setText("Thêm hình ảnh");

        btSendMail.setText("Send Mail");
        btSendMail.setToolTipText("he thong se lay danh sach email gui co so luong la amount of mail send trong  bang tbl_mail co trang thai mail send status de gui, gui thanh cong update status trong bang tbl_mail thanh mail sent status hai trang thai nay luan phien doi cho gia tri 1 va 2 cho nhau tuy chon");
        btSendMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSendMailActionPerformed(evt);
            }
        });

        btSendMailDaily.setText("Send Mail Daily");

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

        lbLimit.setText("Số lượng mail gửi  tối đa");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                    .addComponent(txtPassUserSend)
                                    .addComponent(txtUserSend)))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)))
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btSendMail, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                                .addComponent(btSendMailDaily, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbLimit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btCheckAmountMail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btAmountSent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtStatusSent)
                                    .addComponent(txtAmountMail)
                                    .addComponent(txtAmountMailSent)
                                    .addComponent(txtStatusSend)
                                    .addComponent(txtLimitMailSend, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUserSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btCheckAmountMail)
                        .addComponent(txtAmountMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPassUserSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btAmountSent)
                        .addComponent(txtAmountMailSent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtStatusSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtStatusSent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbLimit)
                            .addComponent(txtLimitMailSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btSendMail)
                            .addComponent(btSendMailDaily)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtStatusSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusSendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusSendActionPerformed

    private void txtUserSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserSendActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserSendActionPerformed

    private void btSendMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSendMailActionPerformed
        try {
            String mailSend = txtUserSend.getText().trim();
            String password = txtPassUserSend.getText().trim();
            String title = txtTitle.getText().trim();
            String content = txtConent.getText().trim();
            String sttMailSend = txtStatusSend.getText().trim();
            String sttMailSent = txtStatusSend.getText().trim();
            String limitMailSend = txtLimitMailSend.getText().trim();
            //lay danh sach mail gui
            ArrayList<Mail> lst = EmailAction.getListMail(sttMailSend, limitMailSend);
            for (Mail to : lst) {
                try {
                    EmailAction.sendEmail(mailSend, password, to.getEmail(), title, content);
                    System.out.println("tunglv gui toi mail" + to.getEmail() + " thanh cong");
                    //update status
                    to.setStatus(Integer.parseInt(sttMailSent));
                    MailDao.updateMail(to);
                } catch (Exception e) {
                    System.out.println("tunglv gui toi mail: " + to.getEmail() + " bi loi" + e.getMessage());
                    if (e.getMessage() != null && e.getMessage() != "" && e.getMessage().contains("550 5.4.5 Daily user sending quota exceeded")) {
                        throw new Exception("Email da gui qua so luong cho phep trong ngay");
                    }
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btSendMailActionPerformed

    private void btCheckAmountMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCheckAmountMailActionPerformed
        try {
            int amount= MailDao.count();
            txtAmountMail.setText(String.valueOf(amount));
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btCheckAmountMailActionPerformed

    private void btAmountSentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAmountSentActionPerformed
        try {
            String statusSent = txtStatusSent.getText().trim();
            if(!statusSent.isEmpty() && statusSent!=null){
                int amount = MailDao.countMailSent(statusSent);
                txtAmountMailSent.setText(String.valueOf(amount));
            }
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btAmountSentActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SendEmail().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAmountSent;
    private javax.swing.JButton btCheckAmountMail;
    private javax.swing.JButton btSendMail;
    private javax.swing.JButton btSendMailDaily;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbLimit;
    private javax.swing.JTextField txtAmountMail;
    private javax.swing.JTextField txtAmountMailSent;
    private javax.swing.JTextArea txtConent;
    private javax.swing.JTextField txtLimitMailSend;
    private javax.swing.JPasswordField txtPassUserSend;
    private javax.swing.JTextField txtStatusSend;
    private javax.swing.JTextField txtStatusSent;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextField txtUserSend;
    // End of variables declaration//GEN-END:variables
}
