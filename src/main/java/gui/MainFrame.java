package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.Timer;
import utils.Auth;
import utils.MsgBox;
import utils.XImage;

public class MainFrame extends javax.swing.JFrame {

    public MainFrame() {
        new LoginJDialog(this, true).setVisible(true);
        initComponents();
        init();
        startTimer();
    }
    
    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss dd/MM/yyyy");
    
    
    void init() {
        setLocationRelativeTo(null);
        setIconImage(XImage.getAppIcon());
        updateStatus();
        updateTimer();
    }
    
    
    private void updateTimer() {
        String currentTime = dateFormat.format(new Date());
        lblDongHo.setText(currentTime);
    }
    
    private void startTimer() {
        Timer timer = new Timer(1000, e -> {
            updateTimer();
        });
        timer.start();
    }
    
    private void hover(JPanel panel) {
        JPanel[] pn = {btnHoaDon,btnKhachHang,btnKhoHang,btnNhanVien,btnSanPham,btnThanhToan,btnThongKe,btnDangXuat,btnHuongDan, btnChat, btnSettings};
        for (JPanel jPanel : pn) {
            jPanel.setBackground(new Color(19,144,234));
        }
        panel.setBackground(new Color(0,102,204));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        whiteBackground = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btnHome = new javax.swing.JLabel();
        btnHoaDon = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnKhachHang = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btnKhoHang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnSanPham = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        btnNhanVien = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnThongKe = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        btnDangXuat = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        btnHuongDan = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        btnChat = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnSettings = new javax.swing.JPanel();
        d = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblTenNhanVien = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtRoleNV = new javax.swing.JLabel();
        lblDongHo = new javax.swing.JLabel();
        jDesktopPane = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("BeeC Gear ");
        setPreferredSize(new java.awt.Dimension(1920, 1080));

        whiteBackground.setBackground(new java.awt.Color(243, 243, 243));

        menu.setBackground(new java.awt.Color(19, 144, 234));
        menu.setForeground(new java.awt.Color(255, 255, 255));
        menu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnHome.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnHome.setForeground(new java.awt.Color(255, 255, 255));
        btnHome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8-workstation-40.png"))); // NOI18N
        btnHome.setText("BeeC");
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHomeMouseClicked(evt);
            }
        });

        btnHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnHoaDon.setForeground(new java.awt.Color(204, 204, 204));
        btnHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHoaDonMouseClicked(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Bill.png"))); // NOI18N
        jLabel2.setText("Hóa Đơn");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel2.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnHoaDonLayout = new javax.swing.GroupLayout(btnHoaDon);
        btnHoaDon.setLayout(btnHoaDonLayout);
        btnHoaDonLayout.setHorizontalGroup(
            btnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHoaDonLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnHoaDonLayout.setVerticalGroup(
            btnHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2)
        );

        btnThanhToan.setBackground(new java.awt.Color(19, 144, 234));
        btnThanhToan.setForeground(new java.awt.Color(204, 204, 204));
        btnThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThanhToanMouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(250, 250, 250));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Coins.png"))); // NOI18N
        jLabel3.setText("Thanh Toán");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel3.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnThanhToanLayout = new javax.swing.GroupLayout(btnThanhToan);
        btnThanhToan.setLayout(btnThanhToanLayout);
        btnThanhToanLayout.setHorizontalGroup(
            btnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThanhToanLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnThanhToanLayout.setVerticalGroup(
            btnThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3)
        );

        btnKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnKhachHang.setForeground(new java.awt.Color(204, 204, 204));
        btnKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKhachHangMouseClicked(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Budget.png"))); // NOI18N
        jLabel5.setText("Khách Hàng");
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel5.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnKhachHangLayout = new javax.swing.GroupLayout(btnKhachHang);
        btnKhachHang.setLayout(btnKhachHangLayout);
        btnKhachHangLayout.setHorizontalGroup(
            btnKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnKhachHangLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel5)
                .addContainerGap(47, Short.MAX_VALUE))
        );
        btnKhachHangLayout.setVerticalGroup(
            btnKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5)
        );

        jSeparator2.setBackground(new java.awt.Color(0, 102, 204));
        jSeparator2.setForeground(new java.awt.Color(0, 102, 204));

        btnKhoHang.setBackground(new java.awt.Color(19, 144, 234));
        btnKhoHang.setForeground(new java.awt.Color(204, 204, 204));
        btnKhoHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnKhoHangMouseClicked(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Trolley.png"))); // NOI18N
        jLabel6.setText("Kho Hàng");
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel6.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnKhoHangLayout = new javax.swing.GroupLayout(btnKhoHang);
        btnKhoHang.setLayout(btnKhoHangLayout);
        btnKhoHangLayout.setHorizontalGroup(
            btnKhoHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnKhoHangLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnKhoHangLayout.setVerticalGroup(
            btnKhoHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6)
        );

        btnSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnSanPham.setForeground(new java.awt.Color(204, 204, 204));
        btnSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSanPhamMouseClicked(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(204, 204, 204));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Product.png"))); // NOI18N
        jLabel8.setText("Sản Phẩm");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel8.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnSanPhamLayout = new javax.swing.GroupLayout(btnSanPham);
        btnSanPham.setLayout(btnSanPhamLayout);
        btnSanPhamLayout.setHorizontalGroup(
            btnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSanPhamLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSanPhamLayout.setVerticalGroup(
            btnSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8)
        );

        jSeparator3.setBackground(new java.awt.Color(0, 102, 204));
        jSeparator3.setForeground(new java.awt.Color(0, 102, 204));

        btnNhanVien.setBackground(new java.awt.Color(19, 144, 234));
        btnNhanVien.setForeground(new java.awt.Color(204, 204, 204));
        btnNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNhanVienMouseClicked(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(204, 204, 204));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Manager.png"))); // NOI18N
        jLabel9.setText("Nhân Viên");
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel9.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnNhanVienLayout = new javax.swing.GroupLayout(btnNhanVien);
        btnNhanVien.setLayout(btnNhanVienLayout);
        btnNhanVienLayout.setHorizontalGroup(
            btnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnNhanVienLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnNhanVienLayout.setVerticalGroup(
            btnNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9)
        );

        btnThongKe.setBackground(new java.awt.Color(19, 144, 234));
        btnThongKe.setForeground(new java.awt.Color(204, 204, 204));
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnThongKeMouseClicked(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Bar Chart.png"))); // NOI18N
        jLabel10.setText("Thống Kê");
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel10.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnThongKeLayout = new javax.swing.GroupLayout(btnThongKe);
        btnThongKe.setLayout(btnThongKeLayout);
        btnThongKeLayout.setHorizontalGroup(
            btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnThongKeLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnThongKeLayout.setVerticalGroup(
            btnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10)
        );

        btnDangXuat.setBackground(new java.awt.Color(19, 144, 234));
        btnDangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDangXuatMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(250, 250, 250));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Logout.png"))); // NOI18N
        jLabel11.setText(" Đăng Xuất");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel11.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnDangXuatLayout = new javax.swing.GroupLayout(btnDangXuat);
        btnDangXuat.setLayout(btnDangXuatLayout);
        btnDangXuatLayout.setHorizontalGroup(
            btnDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDangXuatLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnDangXuatLayout.setVerticalGroup(
            btnDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11)
        );

        btnHuongDan.setBackground(new java.awt.Color(19, 144, 234));
        btnHuongDan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHuongDanMouseClicked(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(250, 250, 250));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/About.png"))); // NOI18N
        jLabel12.setText("Hướng Dẫn");
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel12.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnHuongDanLayout = new javax.swing.GroupLayout(btnHuongDan);
        btnHuongDan.setLayout(btnHuongDanLayout);
        btnHuongDanLayout.setHorizontalGroup(
            btnHuongDanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHuongDanLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnHuongDanLayout.setVerticalGroup(
            btnHuongDanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12)
        );

        jSeparator4.setBackground(new java.awt.Color(0, 102, 204));
        jSeparator4.setForeground(new java.awt.Color(0, 102, 204));

        btnChat.setBackground(new java.awt.Color(19, 144, 234));
        btnChat.setForeground(new java.awt.Color(204, 204, 204));
        btnChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnChatMouseClicked(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(204, 204, 204));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Chat Room.png"))); // NOI18N
        jLabel13.setText(" Messenger");
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel13.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnChatLayout = new javax.swing.GroupLayout(btnChat);
        btnChat.setLayout(btnChatLayout);
        btnChatLayout.setHorizontalGroup(
            btnChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnChatLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnChatLayout.setVerticalGroup(
            btnChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13)
        );

        btnSettings.setBackground(new java.awt.Color(19, 144, 234));
        btnSettings.setForeground(new java.awt.Color(204, 204, 204));
        btnSettings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSettingsMouseClicked(evt);
            }
        });

        d.setBackground(new java.awt.Color(204, 204, 204));
        d.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        d.setForeground(new java.awt.Color(255, 255, 255));
        d.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        d.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Settings.png"))); // NOI18N
        d.setText(" Settings");
        d.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        d.setInheritsPopupMenu(false);

        javax.swing.GroupLayout btnSettingsLayout = new javax.swing.GroupLayout(btnSettings);
        btnSettings.setLayout(btnSettingsLayout);
        btnSettingsLayout.setHorizontalGroup(
            btnSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSettingsLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(d)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnSettingsLayout.setVerticalGroup(
            btnSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(d)
        );

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnKhoHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDangXuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHuongDan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addComponent(btnChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(btnHome)
                .addGap(57, 57, 57)
                .addComponent(btnHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnKhoHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHuongDan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDangXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblTenNhanVien.setBackground(new java.awt.Color(0, 0, 0));
        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTenNhanVien.setForeground(new java.awt.Color(0, 0, 0));
        lblTenNhanVien.setText("CHÀO MỪNG TRỞ LẠI, TÊN NHÂN VIÊN");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Chúc bạn một ngày làm việc vui vẻ");

        txtRoleNV.setBackground(new java.awt.Color(0, 0, 0));
        txtRoleNV.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtRoleNV.setForeground(new java.awt.Color(0, 0, 0));
        txtRoleNV.setText("Role");

        lblDongHo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDongHo.setForeground(new java.awt.Color(0, 0, 0));
        lblDongHo.setText("30/09/2023 07:44:11");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTenNhanVien)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1132, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtRoleNV)
                    .addComponent(lblDongHo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(156, 156, 156))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtRoleNV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDongHo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTenNhanVien)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)))
                .addGap(47, 47, 47))
        );

        jDesktopPane.setBackground(new java.awt.Color(243, 243, 243));

        javax.swing.GroupLayout jDesktopPaneLayout = new javax.swing.GroupLayout(jDesktopPane);
        jDesktopPane.setLayout(jDesktopPaneLayout);
        jDesktopPaneLayout.setHorizontalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jDesktopPaneLayout.setVerticalGroup(
            jDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 919, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout whiteBackgroundLayout = new javax.swing.GroupLayout(whiteBackground);
        whiteBackground.setLayout(whiteBackgroundLayout);
        whiteBackgroundLayout.setHorizontalGroup(
            whiteBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(whiteBackgroundLayout.createSequentialGroup()
                .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(whiteBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDesktopPane))
                .addGap(93, 93, 93))
        );
        whiteBackgroundLayout.setVerticalGroup(
            whiteBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(whiteBackgroundLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDesktopPane)
                .addContainerGap())
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(whiteBackground, javax.swing.GroupLayout.PREFERRED_SIZE, 1917, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(whiteBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void openHoaDon() {
        hover(btnHoaDon);
        jDesktopPane.removeAll();
        QuanLyHD qlhd = new QuanLyHD();
        jDesktopPane.add(qlhd).setVisible(true);
        qlhd.selectTab(0);
    }
    
    private void btnHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHoaDonMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager()|| Auth.isSaler())
                openHoaDon();
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
            
        
    }//GEN-LAST:event_btnHoaDonMouseClicked

    private void openThanhToan() {
        hover(btnThanhToan);
        jDesktopPane.removeAll();
        QuanLyHD qlhd = new QuanLyHD();
        jDesktopPane.add(qlhd).setVisible(true);
        qlhd.selectTab(1);
    }
    
    private void btnThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThanhToanMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager()|| Auth.isSaler())
                openThanhToan(); 
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
        
    }//GEN-LAST:event_btnThanhToanMouseClicked
    
    private void openKhachHang() {
        hover(btnKhachHang);
        jDesktopPane.removeAll();
        QuanLyHD qlhd = new QuanLyHD();
        jDesktopPane.add(qlhd).setVisible(true);
        qlhd.selectTab(2);
    }
    
    private void btnKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhachHangMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager()|| Auth.isSaler())
                openKhachHang();
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
        
    }//GEN-LAST:event_btnKhachHangMouseClicked

    private void openKhoHang() {
        hover(btnKhoHang);
        jDesktopPane.removeAll();
        QuanLyKhoHangvaSanPham qlkhsp = new QuanLyKhoHangvaSanPham();
        jDesktopPane.add(qlkhsp).setVisible(true);
        qlkhsp.selectTab(0);
    }
        
    private void btnKhoHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhoHangMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager()|| Auth.isNVNhap())
                openKhoHang();
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
        
    }//GEN-LAST:event_btnKhoHangMouseClicked
    
    private void openSanPham() {
        hover(btnSanPham);
        jDesktopPane.removeAll();
        QuanLyKhoHangvaSanPham qlkhsp = new QuanLyKhoHangvaSanPham();
        jDesktopPane.add(qlkhsp).setVisible(true);
        qlkhsp.selectTab(1);
    }
    
    private void btnSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSanPhamMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager()|| Auth.isNVNhap())
                openSanPham();
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
        
    }//GEN-LAST:event_btnSanPhamMouseClicked

    private void openNhanVien() {
        hover(btnNhanVien);
        jDesktopPane.removeAll();
        NhanVienvaThongKe nvtk = new NhanVienvaThongKe();
        jDesktopPane.add(nvtk).setVisible(true);
        nvtk.selectTab(2);
    }
    
    private void btnNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNhanVienMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager())
                openNhanVien();
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
        
    }//GEN-LAST:event_btnNhanVienMouseClicked

    private void openThongKe() {
        hover(btnThongKe);
        jDesktopPane.removeAll();
        NhanVienvaThongKe nvtk = new NhanVienvaThongKe();
        jDesktopPane.add(nvtk).setVisible(true);
        nvtk.selectTab(0);
    }
    
    private void btnThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseClicked
        if(Auth.isLogin()) {
            if(Auth.isManager())
                openThongKe();
            else
                MsgBox.alert(null, "<html><font color='red'>Bạn không có quyền sử dụng chức năng này!</font></html>");
        } else {
            MsgBox.alert(null, "<html><font color='red'>Phải đăng nhập mới thực hiện được chức năng này!</font></html>");
        }
        
    }//GEN-LAST:event_btnThongKeMouseClicked

    private void btnHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMouseClicked
        jDesktopPane.removeAll();
        jDesktopPane.repaint();
        hover(null);
    }//GEN-LAST:event_btnHomeMouseClicked

    private void updateStatus() {
        lblTenNhanVien.setText("CHÀO MỪNG TRỞ LẠI, "+Auth.user.getHovaTen().toUpperCase());
        txtRoleNV.setText(Auth.isRoleString());
    }
    
    private void dangXuat() {
        jDesktopPane.removeAll();
        jDesktopPane.repaint();
        hover(btnDangXuat);
        Auth.clear();
        new LoginJDialog(this,true).setVisible(true);
        updateStatus();
    }
    
    private void btnDangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangXuatMouseClicked
        dangXuat(); 
    }//GEN-LAST:event_btnDangXuatMouseClicked

    private void btnHuongDanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuongDanMouseClicked
        hover(btnHuongDan);
        try {
            Desktop.getDesktop().browse(new File("help/hdsd.url").toURI());
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnHuongDanMouseClicked

    private void btnChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChatMouseClicked
        hover(btnChat);
    }//GEN-LAST:event_btnChatMouseClicked

    private void btnSettingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSettingsMouseClicked
        hover(btnSettings);
    }//GEN-LAST:event_btnSettingsMouseClicked

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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnChat;
    private javax.swing.JPanel btnDangXuat;
    private javax.swing.JPanel btnHoaDon;
    private javax.swing.JLabel btnHome;
    private javax.swing.JPanel btnHuongDan;
    private javax.swing.JPanel btnKhachHang;
    private javax.swing.JPanel btnKhoHang;
    private javax.swing.JPanel btnNhanVien;
    private javax.swing.JPanel btnSanPham;
    private javax.swing.JPanel btnSettings;
    private javax.swing.JPanel btnThanhToan;
    private javax.swing.JPanel btnThongKe;
    private javax.swing.JLabel d;
    private javax.swing.JDesktopPane jDesktopPane;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel lblDongHo;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel txtRoleNV;
    private javax.swing.JPanel whiteBackground;
    // End of variables declaration//GEN-END:variables
}
