package gui;

import dao.NhanVienDAO;
import entity.NhanVien;
import gui.ForgotPasswordJDialog;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import utils.Auth;
import utils.MsgBox;
import utils.XImage;

public class LoginJDialog extends javax.swing.JDialog {

    /**
     * Creates new form LoginJDialog
     */
    public LoginJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }
    
    void init() {
        setLocationRelativeTo(null);
        setTitle("BeeC Gear - Đăng Nhập");
        setIconImage(XImage.getAppIcon());
    }
    

    
    NhanVienDAO nvDAO = new NhanVienDAO();
    int tryLoginCount = 0;
    
    void DangNhap() {
        String manv = txtMaNhanVien.getText();
        String matKhau = new String(txtPassword.getPassword());
        NhanVien nv = nvDAO.selectByID(manv);
        
        if(nv==null) {
            MsgBox.alert(this, "Sai mã nhân viên hoặc mật khẩu!");
            tryLoginCount++;
        } else if (!matKhau.equals(nv.getMatKhau())) {
            MsgBox.alert(this, "Sai mã nhân viên hoặc mật khẩu!");
            tryLoginCount++;
        } else {
            Auth.user = nv;
            this.dispose();
        }
        
        if(tryLoginCount > 2) {
            if(MsgBox.confirm(this, "Quên mật khẩu? Bấm YES để lấy lại mật khẩu!")) {
                openQuenMatKhau();
            } else {
                tryLoginCount = 0;
            }
        }
                
    }
    
    private void openQuenMatKhau() {
        new ForgotPasswordJDialog(null, true).setVisible(true);
    }
    
    private boolean isValidationForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaNhanVien.getText().isEmpty() || txtMaNhanVien.getText().trim().length()==0) {
            strb.append("Mã nhân viên không được để trống!\n");
        }
        
        if(String.valueOf(txtPassword.getPassword()).isEmpty() || String.valueOf(txtPassword.getPassword()).trim().length()==0) {
            strb.append("Password không được để trống!\n");
        }
        
        if(strb.length()>0) {
            MsgBox.alert(this, "<html><font color='red'>" + strb + "</font></html>");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        lblForgotPassword = new javax.swing.JLabel();
        panelEmail = new javax.swing.JPanel();
        txtMaNhanVien = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        panelPassword = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnTogglePassword = new javax.swing.JButton();
        lblBanner = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setForeground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(19, 144, 234));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đăng Nhập");

        btnLogin.setBackground(new java.awt.Color(19, 144, 234));
        btnLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(19, 144, 234)));
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoginMouseEntered(evt);
            }
        });

        lblLogin.setBackground(new java.awt.Color(255, 255, 255));
        lblLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(255, 255, 255));
        lblLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogin.setText("Đăng Nhập");
        lblLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblLoginMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnLoginLayout = new javax.swing.GroupLayout(btnLogin);
        btnLogin.setLayout(btnLoginLayout);
        btnLoginLayout.setHorizontalGroup(
            btnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );
        btnLoginLayout.setVerticalGroup(
            btnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
        );

        lblForgotPassword.setBackground(new java.awt.Color(0, 0, 0));
        lblForgotPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblForgotPassword.setForeground(new java.awt.Color(0, 0, 0));
        lblForgotPassword.setText("Quên mật khẩu?");
        lblForgotPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForgotPasswordMouseClicked(evt);
            }
        });

        panelEmail.setBackground(new java.awt.Color(255, 255, 255));
        panelEmail.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(195, 195, 195), 2, true));

        txtMaNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNhanVien.setText("NV01");
        txtMaNhanVien.setBorder(null);
        txtMaNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaNhanVienMouseClicked(evt);
            }
        });
        txtMaNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaNhanVienActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-email-18.png"))); // NOI18N

        javax.swing.GroupLayout panelEmailLayout = new javax.swing.GroupLayout(panelEmail);
        panelEmail.setLayout(panelEmailLayout);
        panelEmailLayout.setHorizontalGroup(
            panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEmailLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtMaNhanVien))
        );
        panelEmailLayout.setVerticalGroup(
            panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEmailLayout.createSequentialGroup()
                .addGroup(panelEmailLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(txtMaNhanVien))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        panelPassword.setBackground(new java.awt.Color(255, 255, 255));
        panelPassword.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(195, 195, 195), 2, true));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-password-18.png"))); // NOI18N

        txtPassword.setBackground(new java.awt.Color(255, 255, 255));
        txtPassword.setText("1");
        txtPassword.setBorder(null);
        txtPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtPasswordMouseClicked(evt);
            }
        });

        btnTogglePassword.setBackground(new java.awt.Color(255, 255, 255));
        btnTogglePassword.setBorder(null);
        btnTogglePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogglePasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPasswordLayout = new javax.swing.GroupLayout(panelPassword);
        panelPassword.setLayout(panelPasswordLayout);
        panelPasswordLayout.setHorizontalGroup(
            panelPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelPasswordLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTogglePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelPasswordLayout.setVerticalGroup(
            panelPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPasswordLayout.createSequentialGroup()
                .addGroup(panelPasswordLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(txtPassword)
                    .addComponent(btnTogglePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        lblBanner.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/loginimage.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("V1.0");

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(lblForgotPassword)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(panelEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(panelPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(22, 22, 22))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBanner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(panelEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(panelPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblForgotPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2))
            .addComponent(lblBanner, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTogglePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogglePasswordActionPerformed
        //hiển thị text trong jpasswordfield khi bấm nút
        if (count % 2 == 0) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('•');
        }
        count++;
    }//GEN-LAST:event_btnTogglePasswordActionPerformed

    private void txtPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtPasswordMouseClicked
        Border border = BorderFactory.createLineBorder(new Color(19,144,234), 2);
        panelPassword.setBorder(border);
        border = BorderFactory.createLineBorder(new Color(187,187,187), 2);
        panelEmail.setBorder(border);
    }//GEN-LAST:event_txtPasswordMouseClicked

    private void txtMaNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaNhanVienActionPerformed

    private void txtMaNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaNhanVienMouseClicked
        Border border = BorderFactory.createLineBorder(new Color(19,144,234), 2);
        panelEmail.setBorder(border);
        border = BorderFactory.createLineBorder(new Color(187,187,187), 2);
        panelPassword.setBorder(border);
    }//GEN-LAST:event_txtMaNhanVienMouseClicked

    private void lblForgotPasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForgotPasswordMouseClicked
        openQuenMatKhau();
    }//GEN-LAST:event_lblForgotPasswordMouseClicked

    private void btnLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseEntered

    }//GEN-LAST:event_btnLoginMouseEntered

    private void btnLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoginMouseClicked
        lblLoginMouseClicked(evt);
    }//GEN-LAST:event_btnLoginMouseClicked

    private void lblLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseExited
        //hover button Login
        btnLogin.setBackground(new Color(19,144,234));
        Border border = BorderFactory.createLineBorder(new Color(19,144,234), 1);
        btnLogin.setBorder(border);
        lblLogin.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblLoginMouseExited

    private void lblLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseEntered
        //hover button Login
        btnLogin.setBackground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(new Color(19,144,234), 1);
        btnLogin.setBorder(border);
        lblLogin.setForeground(new Color(19,144,234));
    }//GEN-LAST:event_lblLoginMouseEntered

    private void lblLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLoginMouseClicked
        Border border = BorderFactory.createLineBorder(new Color(187,187,187), 2);
        panelEmail.setBorder(border);
        panelPassword.setBorder(border);

        if(isValidationForm()) {
            DangNhap();
        }

        //check validation
        //        if(isValidationForm()) {
            //            //thao tác đăng nhập
            //            switch (accDAO.checkLogin(txtEmail.getText(), String.valueOf(txtPassword.getPassword()), role)) {
                //                case 1:
                //                    System.out.println("Nhan Vien");
                //                    break;
                //                case 2:
                //                    System.out.println("Admin");
                //                    break;
                //                case 0:
                //                    JOptionPane.showMessageDialog(this, "Username hoặc mật khẩu không đúng!");
                //                default:
                //                    System.out.println("error");
                //            }
            //
            //        }
    }//GEN-LAST:event_lblLoginMouseClicked
    boolean role = false;    
    int count = 0;
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
                if ("Window".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LoginJDialog dialog = new LoginJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JPanel btnLogin;
    private javax.swing.JButton btnTogglePassword;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblBanner;
    private javax.swing.JLabel lblForgotPassword;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JPanel panelEmail;
    private javax.swing.JPanel panelPassword;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
