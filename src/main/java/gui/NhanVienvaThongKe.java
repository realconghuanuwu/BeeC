package gui;

import dao.NhanVienDAO;
import dao.ThongKeDAO;
import entity.NhanVien;
import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import utils.Auth;
import utils.DataConvertsLib;
import utils.DateConvert;
import utils.MsgBox;
import utils.ValidationForm;
import utils.XImage;

public class NhanVienvaThongKe extends javax.swing.JInternalFrame {
    public NhanVienvaThongKe() {
        initComponents();
        init();
    }

    public void selectTab(int index) {
        tabs.setSelectedIndex(index);
    }
    
    private void init() {
        ImageIcon icon = new ImageIcon("src\\images\\Manager16.png");
        setFrameIcon(icon);
        
        fillDataToTable();
        fillTableThongKeNhanVien();
        fillTableThongKeDH();
        
        
        updateStatus();
    }
    
    NhanVienDAO nvDAO = new NhanVienDAO();
    ThongKeDAO tkDAO = new ThongKeDAO();
    
    private void hover(JPanel pn, JPanel icon) {
        JPanel[] panelContainer = {pnlDoanhSo,pnlDonHangDaThanhToan,pnlDonHangDangThanhToan,pnlDonHangHuyThanhToan,pnlLoiNhuan,pnlVon};
        JPanel[] icons = {pnlVonTT,pnlDS,pnlLN,pnlDaTT,pnlDangTT,pnlHuyTT};
        for (JPanel jPanel : panelContainer) {
            jPanel.setBackground(new Color(0,102,204));
        }
        for (JPanel icon1 : icons) {
                icon.setBackground(new Color(19,144,234));
        }
        pn.setBackground(new Color(19,144,234));
        icon.setBackground(new Color(0,102,204));
    }
    
    // NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN NHAN VIEN 
    int row = -1;
    
    private void insert() {
        NhanVien nv = getForm();
        try {
            nvDAO.insert(nv);
            fillDataToTable();
            MsgBox.alert(null, "Thêm mới nhân viên thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm mới nhân viên thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void update() {
        NhanVien nv = getForm();
        try {
            nvDAO.update(nv);
            fillDataToTable();
            MsgBox.alert(null, "Cập nhật nhân viên thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Cập nhật nhân viên thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void delete() {
        String ma = txtMaNhanVien.getText();
        try {
            nvDAO.delete(ma);
            fillDataToTable();
            clearForm();
            MsgBox.alert(null, "Xóa nhân viên thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Xóa nhân viên thất bại!");
            e.printStackTrace();
        }
    }
    
    private void clearForm() {
        this.row = -1;
        NhanVien nv = new NhanVien();
        nv.setNgayVaoCuaHang(DateConvert.addDays(new Date(), 0)); //du lieu tuong trung ko co tac dung gi den db
        nv.setLuong(new BigDecimal(0));
        this.setForm(nv);
        this.updateStatus();

    }
    
    private void timKiem() {
        this.fillDataToTable();
        this.clearForm();
        this.row = -1;
        updateStatus();
    }
        
        
    private void setForm(NhanVien nv) {
        System.out.println(""+nv.getMaNhanVien());
        txtMaNhanVien.setText(nv.getMaNhanVien());
        txtHovaTen.setText(nv.getHovaTen());
        txtEmail.setText(nv.getEmail());
        txtMatKhau.setText(nv.getMatKhau());
        if(nv.getVaiTro() == 0)
            rdoTruongPhong.setSelected(true);
        else if(nv.getVaiTro() == 1)
            rdoNhanVienSale.setSelected(true);
        if(nv.getVaiTro() == 2)
            rdoNhanVienNhap.setSelected(true);
        rdoNam.setSelected(nv.isGioiTinh());
        txtDiaChi.setText(nv.getDiaChi());
        txtSDT.setText(nv.getSoDienThoai());
        txtLichLamViec.setText(nv.getLichLamViec());
        if(nv.getAnh()!= null) {
            lblAnhNhanVien.setToolTipText(nv.getAnh());
            lblAnhNhanVien.setIcon(XImage.read(nv.getAnh()));
        } else {
            lblAnhNhanVien.setToolTipText(null);
            lblAnhNhanVien.setIcon(null);
        }
        if(nv.getLuong() != null) {
            txtLuong.setText(String.valueOf(nv.getLuong()));
        }
        
        if(nv.getNgayVaoCuaHang() != null) 
            txtNgayGiaNhap.setText(DateConvert.toString(nv.getNgayVaoCuaHang(), "dd/MM/yyyy"));        
    }
    
    private NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(txtMaNhanVien.getText());
        nv.setHovaTen(txtHovaTen.getText());
        nv.setEmail(txtEmail.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        if(rdoTruongPhong.isSelected())
            nv.setVaiTro(0);
        else if (rdoNhanVienSale.isSelected())
            nv.setVaiTro(1);
        else 
            nv.setVaiTro(2);
        nv.setGioiTinh(rdoNam.isSelected());
        nv.setDiaChi(txtDiaChi.getText());
        nv.setSoDienThoai(txtSDT.getText());
        nv.setLichLamViec(txtLichLamViec.getText());
        nv.setAnh(lblAnhNhanVien.getToolTipText());
        nv.setLuong(new BigDecimal(txtLuong.getText()));
        return nv;
    }
    
    private void edit() {
        String ma = (String) tblNhanVien.getValueAt(row, 0);
        NhanVien nv = nvDAO.selectByID(ma);
        setForm(nv);
        tblNhanVien.setRowSelectionInterval(row, row);
        this.updateStatus();
    }
    
    JFileChooser fileChooser = new JFileChooser("C:\\Users\\huanl\\Desktop");
    
    void chonAnh() {
        if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblAnhNhanVien.setIcon(icon);
            lblAnhNhanVien.setToolTipText(file.getName());
        }
    }
    
    private void first() {
        this.row = 0;
        this.edit();
    }
    
    private void prev() {
        if(this.row > 0) {
            this.row--;
            this.edit();
        }
    }
    
    private void next() {
        if(this.row < tblNhanVien.getRowCount()-1) {
            this.row++;
            this.edit();
        }
    }
    
    private void last() {
        this.row = tblNhanVien.getRowCount()-1;
        this.edit();
    }
    
    void updateStatus() {
        boolean edit = (this.row >=0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNhanVien.getRowCount()-1);
        
        txtMaNhanVien.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
        
        btnFirst.setEnabled(edit && !first);
        btnPrev.setEnabled(edit && !first);
        btnNext.setEnabled(edit && !last);
        btnLast.setEnabled(edit && !last);
        
    }
    
    private void fillDataToTable() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTimKiem.getText();
            List<NhanVien> list = nvDAO.selectByKeyword(keyword);
            for (NhanVien nv : list) {
                String vaiTro = "";
                if(nv.getVaiTro() == 0) 
                    vaiTro = "Quản Lý";
                else if(nv.getVaiTro() == 1) 
                    vaiTro = "Nhân Viên Sale";
                else
                    vaiTro = "Nhân Viên Nhập";
                String ngayVao = "";
                if (nv.getNgayVaoCuaHang() != null)
                    ngayVao = DateConvert.toString(nv.getNgayVaoCuaHang(), "dd/MM/yyyy");
                
                Object[] row = {nv.getMaNhanVien(),nv.getHovaTen(), nv.getMatKhau(), vaiTro, ngayVao, 
                    nv.getLichLamViec(),DataConvertsLib.formatCurrency(nv.getLuong().doubleValue()), nv.getDiaChi()};
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    void fillTableThongKeNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblThongKeNhanVien.getModel();
        model.setRowCount(0);
        int index = cboThongKeNhanVien.getSelectedIndex();
        String locTheo = "";
        if(index == 0) 
            locTheo = "ngay";
        else if (index == 1)
            locTheo = "thang";
        else 
            locTheo = "nam";
        
        List<Object[]> list = tkDAO.getBangDiem(DateConvert.addDays(new Date(), 0), locTheo);
        for (Object[] row : list) {
            model.addRow(new Object[] {row[0],row[1],row[2],row[3],DataConvertsLib.formatCurrency(Double.parseDouble(row[4].toString()))});
        }
    } 

    void fillTableThongKeDH() {
        int index = cboThongKeDonHang.getSelectedIndex();      
        String locTheo = "";
        JLabel[] lblTu = {this.lblTu,lblTu1,lblTu2,lblTu3,lblTu4,lblTu5};
        JLabel[] lblPlus = {this.lbl,lbl1,lbl2,lbl3,lbl4,lbl5};
        JLabel[] lblDen = {this.lblDen,lblDen1,lblDen2,lblDen3,lblDen4,lblDen5};
        if(index == 0) {
           locTheo = "ngay";  
            for (int i = 0; i < lblPlus.length; i++) {
                lblTu[i].setText("Hôm nay");
                lblPlus[i].setText("");
                lblDen[i].setText("");  
            }
        } 
        else if (index == 1) {
            locTheo = "thang";
            LocalDate firstDayOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            LocalDate lastDayOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int i = 0; i < lblPlus.length; i++) {
                lblTu[i].setText(""+firstDayOfMonth.format(formatter));
                lblPlus[i].setText("-");
                lblDen[i].setText(""+lastDayOfMonth.format(formatter));  
            }
        }
            
        else{
            locTheo = "nam";
            LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
            LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (int i = 0; i < lblPlus.length; i++) {
                lblTu[i].setText(""+firstDay.format(formatter));
                lblPlus[i].setText("-");
                lblDen[i].setText(""+lastDay.format(formatter));  
            }
        }
        //Đã thanh toán

        List<Object[]> soDonHangDaTT = tkDAO.getDonHangThanhToan(DateConvert.addDays(new Date(), 0), "Đã Thanh Toán", locTheo);
        for (Object[] row : soDonHangDaTT) {
            if(row[0]!= null) {
                lblSoDHDaThanhToan.setText(row[0].toString()+" Sản Phẩm");
                lblSanPhamDoanhSo.setText(row[0].toString()+" Sản Phẩm");
            }
            if(row[1] != null) {
                lblTongTienDaThanhToan.setText("VNĐ "+DataConvertsLib.formatCurrency(Double.parseDouble(row[1].toString())));
                lblTongDoanhSo.setText("VNĐ "+DataConvertsLib.formatCurrency(Double.parseDouble(row[1].toString())));   
            } 
            break;
        }
        //Đang thanh toán
        List<Object[]> soDonHangDangTT = tkDAO.getDonHangThanhToan(DateConvert.addDays(new Date(), 0), "Đang Thanh Toán", locTheo);
        for (Object[] row : soDonHangDangTT) {
            if(row[0]!= null)
                lblSoDHDangThanhToan.setText(row[0].toString()+" Sản Phẩm");
            if(row[1] != null)
                lblTongTienDangThanhToan.setText("VNĐ "+DataConvertsLib.formatCurrency(Double.parseDouble(row[1].toString())));
            break;
        }
        //Chưa thanh toán
        List<Object[]> soDonHangChuaTT = tkDAO.getDonHangThanhToan(DateConvert.addDays(new Date(), 0), "Chưa Thanh Toán", locTheo);
        for (Object[] row : soDonHangChuaTT) {
            if(row[0]!= null)
                lblSoDHChuaThanhToan.setText(row[0].toString()+" Sản Phẩm");
            if(row[1] != null)
                lblTongTienChuaThanhToan.setText("VNĐ "+DataConvertsLib.formatCurrency(Double.parseDouble(row[1].toString())));
            break;
        }
        //Vốn
        List<Object[]> soVON = tkDAO.getVonNhapHang(DateConvert.addDays(new Date(), 0),locTheo);  
        for (Object[] row : soVON) {
            if(row[0]!= null) {
                lblSoVonSanPham.setText(row[0].toString()+" Sản Phẩm");  
            } 
            if(row[1] != null) {
                lblTongVon.setText("VNĐ "+DataConvertsLib.formatCurrency(Double.parseDouble(row[1].toString())));
            }
            lblSanPhamLoiNhuan.setText(lblSoDHDaThanhToan.getText());
            
            
//            lblTongLoiNhuan.setText("VNĐ "+ DataConvertsLib.formatCurrency());
            break;
        }
        String von = lblTongVon.getText().replaceAll("[^0-9]", "");
        String ds = lblTongDoanhSo.getText().replaceAll("[^0-9]", "");
        double vonD = Double.parseDouble(von);
        double dsD = Double.parseDouble(ds);
//        System.out.println("" + vonD);
//        System.out.println("" + dsD);
        lblTongLoiNhuan.setText("VNĐ "+ DataConvertsLib.formatCurrency(dsD-vonD));
    }
    
    private void clearFormThongKeDonHang() {
        lblSoDHDaThanhToan.setText("0 Sản Phẩm");
        lblSoDHDangThanhToan.setText("0 Sản Phẩm");
        lblSoDHChuaThanhToan.setText("0 Sản Phẩm"); 
        lblSoVonSanPham.setText("0 Sản Phẩm");
        lblSanPhamDoanhSo.setText("0 Sản Phẩm");
        lblSanPhamLoiNhuan.setText("0 Sản Phẩm");
        lblTongTienDaThanhToan.setText("VNĐ 0");
        lblTongTienDangThanhToan.setText("VNĐ 0");
        lblTongTienChuaThanhToan.setText("VNĐ 0");
        lblTongVon.setText("VNĐ 0");
        lblTongDoanhSo.setText("VNĐ 0");
        lblTongLoiNhuan.setText("VNĐ 0");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        cboThongKeDonHang = new javax.swing.JComboBox<>();
        pnlDonHangDaThanhToan = new javax.swing.JPanel();
        pnlDaTT = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblSoDHDaThanhToan = new javax.swing.JLabel();
        lblTongTienDaThanhToan = new javax.swing.JLabel();
        lblTu = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        lblDen = new javax.swing.JLabel();
        pnlDonHangDangThanhToan = new javax.swing.JPanel();
        pnlDangTT = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblSoDHDangThanhToan = new javax.swing.JLabel();
        lblTongTienDangThanhToan = new javax.swing.JLabel();
        lblTu1 = new javax.swing.JLabel();
        lbl1 = new javax.swing.JLabel();
        lblDen1 = new javax.swing.JLabel();
        pnlDonHangHuyThanhToan = new javax.swing.JPanel();
        pnlHuyTT = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        lblSoDHChuaThanhToan = new javax.swing.JLabel();
        lblTongTienChuaThanhToan = new javax.swing.JLabel();
        lblTu2 = new javax.swing.JLabel();
        lbl2 = new javax.swing.JLabel();
        lblDen2 = new javax.swing.JLabel();
        pnlVon = new javax.swing.JPanel();
        pnlVonTT = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblSoVonSanPham = new javax.swing.JLabel();
        lblTongVon = new javax.swing.JLabel();
        lblTu3 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        lblDen3 = new javax.swing.JLabel();
        pnlDoanhSo = new javax.swing.JPanel();
        pnlDS = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        lblSanPhamDoanhSo = new javax.swing.JLabel();
        lblTongDoanhSo = new javax.swing.JLabel();
        lblTu4 = new javax.swing.JLabel();
        lbl4 = new javax.swing.JLabel();
        lblDen4 = new javax.swing.JLabel();
        pnlLoiNhuan = new javax.swing.JPanel();
        pnlLN = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        lblSanPhamLoiNhuan = new javax.swing.JLabel();
        lblTongLoiNhuan = new javax.swing.JLabel();
        lblTu5 = new javax.swing.JLabel();
        lbl5 = new javax.swing.JLabel();
        lblDen5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        cboThongKeNhanVien = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblThongKeNhanVien = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        rdoTruongPhong = new javax.swing.JRadioButton();
        rdoNhanVienNhap = new javax.swing.JRadioButton();
        rdoNhanVienSale = new javax.swing.JRadioButton();
        jPanel25 = new javax.swing.JPanel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnTimKiem = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        lblAnhNhanVien = new javax.swing.JLabel();
        txtMaNhanVien = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        txtHovaTen = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtLichLamViec = new javax.swing.JTextField();
        txtLuong = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        txtNgayGiaNhap = new javax.swing.JTextField();
        txtTimKiem = new javax.swing.JTextField();
        txtMatKhau = new javax.swing.JPasswordField();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Nhân Viên & Thống Kê");
        setPreferredSize(new java.awt.Dimension(1800, 900));

        tabs.setBackground(new java.awt.Color(0, 102, 204));
        tabs.setForeground(new java.awt.Color(255, 255, 255));
        tabs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lọc Theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel26.setForeground(new java.awt.Color(0, 0, 0));

        cboThongKeDonHang.setBackground(new java.awt.Color(255, 255, 255));
        cboThongKeDonHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThongKeDonHang.setForeground(new java.awt.Color(0, 0, 0));
        cboThongKeDonHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Tháng này", "Năm này" }));
        cboThongKeDonHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cboThongKeDonHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThongKeDonHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboThongKeDonHang, 0, 498, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(cboThongKeDonHang)
                .addContainerGap())
        );

        pnlDonHangDaThanhToan.setBackground(new java.awt.Color(19, 144, 234));
        pnlDonHangDaThanhToan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDonHangDaThanhToan.setPreferredSize(new java.awt.Dimension(338, 210));
        pnlDonHangDaThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDonHangDaThanhToanMouseClicked(evt);
            }
        });

        pnlDaTT.setBackground(new java.awt.Color(0, 102, 204));

        lblIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Shopping Bag.png"))); // NOI18N

        javax.swing.GroupLayout pnlDaTTLayout = new javax.swing.GroupLayout(pnlDaTT);
        pnlDaTT.setLayout(pnlDaTTLayout);
        pnlDaTTLayout.setHorizontalGroup(
            pnlDaTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        pnlDaTTLayout.setVerticalGroup(
            pnlDaTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Đơn Hàng Đã Thanh Toán");

        lblSoDHDaThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoDHDaThanhToan.setForeground(new java.awt.Color(224, 224, 224));
        lblSoDHDaThanhToan.setText("0 Sản Phẩm");

        lblTongTienDaThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTongTienDaThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTienDaThanhToan.setText("VNĐ 0");

        lblTu.setBackground(new java.awt.Color(255, 255, 255));
        lblTu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTu.setForeground(new java.awt.Color(255, 255, 255));
        lblTu.setText("25/09/2023");

        lbl.setBackground(new java.awt.Color(255, 255, 255));
        lbl.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl.setForeground(new java.awt.Color(255, 255, 255));
        lbl.setText("-");

        lblDen.setBackground(new java.awt.Color(255, 255, 255));
        lblDen.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDen.setForeground(new java.awt.Color(255, 255, 255));
        lblDen.setText("02/10/2023");

        javax.swing.GroupLayout pnlDonHangDaThanhToanLayout = new javax.swing.GroupLayout(pnlDonHangDaThanhToan);
        pnlDonHangDaThanhToan.setLayout(pnlDonHangDaThanhToanLayout);
        pnlDonHangDaThanhToanLayout.setHorizontalGroup(
            pnlDonHangDaThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDonHangDaThanhToanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlDonHangDaThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongTienDaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDonHangDaThanhToanLayout.createSequentialGroup()
                        .addComponent(pnlDaTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDonHangDaThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(lblSoDHDaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDonHangDaThanhToanLayout.createSequentialGroup()
                        .addComponent(lblTu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDen)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDonHangDaThanhToanLayout.setVerticalGroup(
            pnlDonHangDaThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDonHangDaThanhToanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlDonHangDaThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDaTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDonHangDaThanhToanLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoDHDaThanhToan)))
                .addGap(18, 18, 18)
                .addComponent(lblTongTienDaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlDonHangDaThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTu)
                    .addComponent(lbl)
                    .addComponent(lblDen))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pnlDonHangDangThanhToan.setBackground(new java.awt.Color(0, 102, 204));
        pnlDonHangDangThanhToan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDonHangDangThanhToan.setPreferredSize(new java.awt.Dimension(338, 210));
        pnlDonHangDangThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDonHangDangThanhToanMouseClicked(evt);
            }
        });

        pnlDangTT.setBackground(new java.awt.Color(19, 144, 234));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Purchase Order.png"))); // NOI18N

        javax.swing.GroupLayout pnlDangTTLayout = new javax.swing.GroupLayout(pnlDangTT);
        pnlDangTT.setLayout(pnlDangTTLayout);
        pnlDangTTLayout.setHorizontalGroup(
            pnlDangTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        pnlDangTTLayout.setVerticalGroup(
            pnlDangTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Đơn Hàng Đang Thanh Toán");

        lblSoDHDangThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoDHDangThanhToan.setForeground(new java.awt.Color(224, 224, 224));
        lblSoDHDangThanhToan.setText("0 Sản Phẩm");

        lblTongTienDangThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTongTienDangThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTienDangThanhToan.setText("VNĐ 0");

        lblTu1.setBackground(new java.awt.Color(255, 255, 255));
        lblTu1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTu1.setForeground(new java.awt.Color(255, 255, 255));
        lblTu1.setText("25/09/2023");

        lbl1.setBackground(new java.awt.Color(255, 255, 255));
        lbl1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl1.setForeground(new java.awt.Color(255, 255, 255));
        lbl1.setText("-");

        lblDen1.setBackground(new java.awt.Color(255, 255, 255));
        lblDen1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDen1.setForeground(new java.awt.Color(255, 255, 255));
        lblDen1.setText("02/10/2023");

        javax.swing.GroupLayout pnlDonHangDangThanhToanLayout = new javax.swing.GroupLayout(pnlDonHangDangThanhToan);
        pnlDonHangDangThanhToan.setLayout(pnlDonHangDangThanhToanLayout);
        pnlDonHangDangThanhToanLayout.setHorizontalGroup(
            pnlDonHangDangThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDonHangDangThanhToanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlDonHangDangThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongTienDangThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDonHangDangThanhToanLayout.createSequentialGroup()
                        .addComponent(pnlDangTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDonHangDangThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(lblSoDHDangThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDonHangDangThanhToanLayout.createSequentialGroup()
                        .addComponent(lblTu1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDen1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDonHangDangThanhToanLayout.setVerticalGroup(
            pnlDonHangDangThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDonHangDangThanhToanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlDonHangDangThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDangTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDonHangDangThanhToanLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoDHDangThanhToan)))
                .addGap(18, 18, 18)
                .addComponent(lblTongTienDangThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlDonHangDangThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTu1)
                    .addComponent(lbl1)
                    .addComponent(lblDen1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlDonHangHuyThanhToan.setBackground(new java.awt.Color(0, 102, 204));
        pnlDonHangHuyThanhToan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDonHangHuyThanhToan.setPreferredSize(new java.awt.Dimension(338, 210));
        pnlDonHangHuyThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDonHangHuyThanhToanMouseClicked(evt);
            }
        });

        pnlHuyTT.setBackground(new java.awt.Color(19, 144, 234));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cancel Order.png"))); // NOI18N

        javax.swing.GroupLayout pnlHuyTTLayout = new javax.swing.GroupLayout(pnlHuyTT);
        pnlHuyTT.setLayout(pnlHuyTTLayout);
        pnlHuyTTLayout.setHorizontalGroup(
            pnlHuyTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        pnlHuyTTLayout.setVerticalGroup(
            pnlHuyTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Đơn Hàng Chưa Thanh Toán");

        lblSoDHChuaThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoDHChuaThanhToan.setForeground(new java.awt.Color(224, 224, 224));
        lblSoDHChuaThanhToan.setText("0 Sản Phẩm");

        lblTongTienChuaThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTongTienChuaThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTienChuaThanhToan.setText("VNĐ 0");

        lblTu2.setBackground(new java.awt.Color(255, 255, 255));
        lblTu2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTu2.setForeground(new java.awt.Color(255, 255, 255));
        lblTu2.setText("25/09/2023");

        lbl2.setBackground(new java.awt.Color(255, 255, 255));
        lbl2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl2.setForeground(new java.awt.Color(255, 255, 255));
        lbl2.setText("-");

        lblDen2.setBackground(new java.awt.Color(255, 255, 255));
        lblDen2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDen2.setForeground(new java.awt.Color(255, 255, 255));
        lblDen2.setText("02/10/2023");

        javax.swing.GroupLayout pnlDonHangHuyThanhToanLayout = new javax.swing.GroupLayout(pnlDonHangHuyThanhToan);
        pnlDonHangHuyThanhToan.setLayout(pnlDonHangHuyThanhToanLayout);
        pnlDonHangHuyThanhToanLayout.setHorizontalGroup(
            pnlDonHangHuyThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDonHangHuyThanhToanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlDonHangHuyThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongTienChuaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDonHangHuyThanhToanLayout.createSequentialGroup()
                        .addComponent(pnlHuyTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDonHangHuyThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(lblSoDHChuaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDonHangHuyThanhToanLayout.createSequentialGroup()
                        .addComponent(lblTu2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDen2)))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        pnlDonHangHuyThanhToanLayout.setVerticalGroup(
            pnlDonHangHuyThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDonHangHuyThanhToanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlDonHangHuyThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHuyTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDonHangHuyThanhToanLayout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoDHChuaThanhToan)))
                .addGap(18, 18, 18)
                .addComponent(lblTongTienChuaThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlDonHangHuyThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTu2)
                    .addComponent(lbl2)
                    .addComponent(lblDen2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlVon.setBackground(new java.awt.Color(0, 102, 204));
        pnlVon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlVon.setPreferredSize(new java.awt.Dimension(338, 210));
        pnlVon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlVonMouseClicked(evt);
            }
        });

        pnlVonTT.setBackground(new java.awt.Color(19, 144, 234));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Sales Performance.png"))); // NOI18N

        javax.swing.GroupLayout pnlVonTTLayout = new javax.swing.GroupLayout(pnlVonTT);
        pnlVonTT.setLayout(pnlVonTTLayout);
        pnlVonTTLayout.setHorizontalGroup(
            pnlVonTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        pnlVonTTLayout.setVerticalGroup(
            pnlVonTTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Vốn");

        lblSoVonSanPham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSoVonSanPham.setForeground(new java.awt.Color(224, 224, 224));
        lblSoVonSanPham.setText("0 Sản Phẩm");

        lblTongVon.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTongVon.setForeground(new java.awt.Color(255, 255, 255));
        lblTongVon.setText("VNĐ 0");

        lblTu3.setBackground(new java.awt.Color(255, 255, 255));
        lblTu3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTu3.setForeground(new java.awt.Color(255, 255, 255));
        lblTu3.setText("25/09/2023");

        lbl3.setBackground(new java.awt.Color(255, 255, 255));
        lbl3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl3.setForeground(new java.awt.Color(255, 255, 255));
        lbl3.setText("-");

        lblDen3.setBackground(new java.awt.Color(255, 255, 255));
        lblDen3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDen3.setForeground(new java.awt.Color(255, 255, 255));
        lblDen3.setText("02/10/2023");

        javax.swing.GroupLayout pnlVonLayout = new javax.swing.GroupLayout(pnlVon);
        pnlVon.setLayout(pnlVonLayout);
        pnlVonLayout.setHorizontalGroup(
            pnlVonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlVonLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlVonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongVon, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlVonLayout.createSequentialGroup()
                        .addComponent(pnlVonTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlVonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(lblSoVonSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlVonLayout.createSequentialGroup()
                        .addComponent(lblTu3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDen3)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        pnlVonLayout.setVerticalGroup(
            pnlVonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlVonLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlVonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlVonTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlVonLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSoVonSanPham)))
                .addGap(18, 18, 18)
                .addComponent(lblTongVon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlVonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTu3)
                    .addComponent(lbl3)
                    .addComponent(lblDen3))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pnlDoanhSo.setBackground(new java.awt.Color(0, 102, 204));
        pnlDoanhSo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlDoanhSo.setPreferredSize(new java.awt.Dimension(338, 210));
        pnlDoanhSo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlDoanhSoMouseClicked(evt);
            }
        });

        pnlDS.setBackground(new java.awt.Color(19, 144, 234));

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Sales Performance Balance.png"))); // NOI18N

        javax.swing.GroupLayout pnlDSLayout = new javax.swing.GroupLayout(pnlDS);
        pnlDS.setLayout(pnlDSLayout);
        pnlDSLayout.setHorizontalGroup(
            pnlDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        pnlDSLayout.setVerticalGroup(
            pnlDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Doanh Số");

        lblSanPhamDoanhSo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSanPhamDoanhSo.setForeground(new java.awt.Color(224, 224, 224));
        lblSanPhamDoanhSo.setText("0 Sản Phẩm");

        lblTongDoanhSo.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTongDoanhSo.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDoanhSo.setText("VNĐ 0");

        lblTu4.setBackground(new java.awt.Color(255, 255, 255));
        lblTu4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTu4.setForeground(new java.awt.Color(255, 255, 255));
        lblTu4.setText("25/09/2023");

        lbl4.setBackground(new java.awt.Color(255, 255, 255));
        lbl4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl4.setForeground(new java.awt.Color(255, 255, 255));
        lbl4.setText("-");

        lblDen4.setBackground(new java.awt.Color(255, 255, 255));
        lblDen4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDen4.setForeground(new java.awt.Color(255, 255, 255));
        lblDen4.setText("02/10/2023");

        javax.swing.GroupLayout pnlDoanhSoLayout = new javax.swing.GroupLayout(pnlDoanhSo);
        pnlDoanhSo.setLayout(pnlDoanhSoLayout);
        pnlDoanhSoLayout.setHorizontalGroup(
            pnlDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDoanhSoLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDoanhSoLayout.createSequentialGroup()
                        .addComponent(pnlDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(lblSanPhamDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlDoanhSoLayout.createSequentialGroup()
                        .addComponent(lblTu4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDen4)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        pnlDoanhSoLayout.setVerticalGroup(
            pnlDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDoanhSoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDoanhSoLayout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSanPhamDoanhSo)))
                .addGap(18, 18, 18)
                .addComponent(lblTongDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlDoanhSoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTu4)
                    .addComponent(lbl4)
                    .addComponent(lblDen4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlLoiNhuan.setBackground(new java.awt.Color(0, 102, 204));
        pnlLoiNhuan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pnlLoiNhuan.setPreferredSize(new java.awt.Dimension(338, 210));
        pnlLoiNhuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLoiNhuanMouseClicked(evt);
            }
        });

        pnlLN.setBackground(new java.awt.Color(19, 144, 234));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cash.png"))); // NOI18N

        javax.swing.GroupLayout pnlLNLayout = new javax.swing.GroupLayout(pnlLN);
        pnlLN.setLayout(pnlLNLayout);
        pnlLNLayout.setHorizontalGroup(
            pnlLNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );
        pnlLNLayout.setVerticalGroup(
            pnlLNLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
        );

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Lợi Nhuận");

        lblSanPhamLoiNhuan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSanPhamLoiNhuan.setForeground(new java.awt.Color(224, 224, 224));
        lblSanPhamLoiNhuan.setText("0 Sản Phẩm");

        lblTongLoiNhuan.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lblTongLoiNhuan.setForeground(new java.awt.Color(255, 255, 255));
        lblTongLoiNhuan.setText("VNĐ 0");

        lblTu5.setBackground(new java.awt.Color(255, 255, 255));
        lblTu5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTu5.setForeground(new java.awt.Color(255, 255, 255));
        lblTu5.setText("25/09/2023");

        lbl5.setBackground(new java.awt.Color(255, 255, 255));
        lbl5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbl5.setForeground(new java.awt.Color(255, 255, 255));
        lbl5.setText("-");

        lblDen5.setBackground(new java.awt.Color(255, 255, 255));
        lblDen5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDen5.setForeground(new java.awt.Color(255, 255, 255));
        lblDen5.setText("02/10/2023");

        javax.swing.GroupLayout pnlLoiNhuanLayout = new javax.swing.GroupLayout(pnlLoiNhuan);
        pnlLoiNhuan.setLayout(pnlLoiNhuanLayout);
        pnlLoiNhuanLayout.setHorizontalGroup(
            pnlLoiNhuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoiNhuanLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlLoiNhuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTongLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlLoiNhuanLayout.createSequentialGroup()
                        .addComponent(pnlLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlLoiNhuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel45)
                            .addComponent(lblSanPhamLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlLoiNhuanLayout.createSequentialGroup()
                        .addComponent(lblTu5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDen5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlLoiNhuanLayout.setVerticalGroup(
            pnlLoiNhuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoiNhuanLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlLoiNhuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlLoiNhuanLayout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSanPhamLoiNhuan)))
                .addGap(18, 18, 18)
                .addComponent(lblTongLoiNhuan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlLoiNhuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTu5)
                    .addComponent(lbl5)
                    .addComponent(lblDen5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pnlVon, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(pnlDonHangDaThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlDoanhSo, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(pnlDonHangDangThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlDonHangHuyThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                            .addComponent(pnlLoiNhuan, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDonHangDaThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(pnlDonHangDangThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(pnlDonHangHuyThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlVon, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(pnlLoiNhuan, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                    .addComponent(pnlDoanhSo, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 556, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 254, Short.MAX_VALUE))
        );

        tabs.addTab("Thống Kê Đơn Hàng", jPanel6);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setToolTipText("");

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thống Kê Theo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        cboThongKeNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        cboThongKeNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboThongKeNhanVien.setForeground(new java.awt.Color(0, 0, 0));
        cboThongKeNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "Tháng này", "Năm này" }));
        cboThongKeNhanVien.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cboThongKeNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboThongKeNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboThongKeNhanVien, 0, 498, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                .addComponent(cboThongKeNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblThongKeNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân Viên", "Họ và Tên", "Các Sản Phẩm Đã Bán", "Số Lượng Bán Ra", "Đơn Số"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblThongKeNhanVien);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1659, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1800, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 869, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        tabs.addTab("Thống Kê Nhân Viên", jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân Viên", "Tên Nhân Viên", "Mật Khẩu", "Vai Trò", "Ngày Vào", "Lịch Làm Việc", "Lương", "Địa Chỉ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblNhanVien);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Vai Trò", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel24.setForeground(new java.awt.Color(0, 0, 0));

        rdoTruongPhong.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoTruongPhong);
        rdoTruongPhong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoTruongPhong.setForeground(new java.awt.Color(0, 0, 0));
        rdoTruongPhong.setText("Trưởng Phòng");

        rdoNhanVienNhap.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNhanVienNhap);
        rdoNhanVienNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNhanVienNhap.setForeground(new java.awt.Color(0, 0, 0));
        rdoNhanVienNhap.setText("Nhân Viên Nhập");

        rdoNhanVienSale.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNhanVienSale);
        rdoNhanVienSale.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNhanVienSale.setForeground(new java.awt.Color(0, 0, 0));
        rdoNhanVienSale.setSelected(true);
        rdoNhanVienSale.setText("Nhân Viên Bán Hàng");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel24Layout.createSequentialGroup()
                .addContainerGap(84, Short.MAX_VALUE)
                .addComponent(rdoNhanVienSale)
                .addGap(18, 18, 18)
                .addComponent(rdoNhanVienNhap)
                .addGap(18, 18, 18)
                .addComponent(rdoTruongPhong)
                .addGap(69, 69, 69))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rdoTruongPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(rdoNhanVienNhap)
                .addComponent(rdoNhanVienSale))
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Giới Tính", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel25.setForeground(new java.awt.Color(0, 0, 0));

        rdoNam.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNam.setForeground(new java.awt.Color(0, 0, 0));
        rdoNam.setText("Nam");

        rdoNu.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNu.setForeground(new java.awt.Color(0, 0, 0));
        rdoNu.setSelected(true);
        rdoNu.setText("Nữ");

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(rdoNam)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoNu)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(rdoNam)
                .addComponent(rdoNu))
        );

        btnTimKiem.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(19, 144, 234));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(19, 144, 234));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(19, 144, 234));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(19, 144, 234));
        btnLast.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLast.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(19, 144, 234));
        btnNext.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNext.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(19, 144, 234));
        btnPrev.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrev.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(19, 144, 234));
        btnFirst.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirst.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(19, 144, 234));
        btnNew.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Erase.png"))); // NOI18N
        btnNew.setText("Mới");
        btnNew.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        lblAnhNhanVien.setBackground(new java.awt.Color(0, 0, 0));
        lblAnhNhanVien.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAnhNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhNhanVien.setText("Bấm vào đây để tải ảnh lên!");
        lblAnhNhanVien.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnhNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhNhanVienMouseClicked(evt);
            }
        });

        txtMaNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNhanVien.setForeground(new java.awt.Color(0, 0, 0));
        txtMaNhanVien.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Nhân Viên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtSDT.setBackground(new java.awt.Color(255, 255, 255));
        txtSDT.setForeground(new java.awt.Color(0, 0, 0));
        txtSDT.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "SĐT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtHovaTen.setBackground(new java.awt.Color(255, 255, 255));
        txtHovaTen.setForeground(new java.awt.Color(0, 0, 0));
        txtHovaTen.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Họ và Tên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setForeground(new java.awt.Color(0, 0, 0));
        txtEmail.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Email", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtLichLamViec.setBackground(new java.awt.Color(255, 255, 255));
        txtLichLamViec.setForeground(new java.awt.Color(0, 0, 0));
        txtLichLamViec.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lịch Làm Việc", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtLichLamViec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLichLamViecActionPerformed(evt);
            }
        });

        txtLuong.setBackground(new java.awt.Color(255, 255, 255));
        txtLuong.setForeground(new java.awt.Color(0, 0, 0));
        txtLuong.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Lương/Tháng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtDiaChi.setBackground(new java.awt.Color(255, 255, 255));
        txtDiaChi.setForeground(new java.awt.Color(0, 0, 0));
        txtDiaChi.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Địa Chỉ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        txtDiaChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiaChiActionPerformed(evt);
            }
        });

        txtNgayGiaNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayGiaNhap.setForeground(new java.awt.Color(0, 0, 0));
        txtNgayGiaNhap.setText("11/01/2022");
        txtNgayGiaNhap.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ngày Gia Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtTimKiem.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiem.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtMatKhau.setBackground(new java.awt.Color(255, 255, 255));
        txtMatKhau.setForeground(new java.awt.Color(0, 0, 0));
        txtMatKhau.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mật Khẩu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblAnhNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtHovaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtNgayGiaNhap))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(txtLichLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtLuong))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtSDT))
                                    .addComponent(txtDiaChi))))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(918, 918, 918)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblAnhNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHovaTen, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLichLamViec, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnLast, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        tabs.addTab("Nhân Viên", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void pnlDonHangDaThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDonHangDaThanhToanMouseClicked
        hover(pnlDonHangDaThanhToan, pnlDaTT);
    }//GEN-LAST:event_pnlDonHangDaThanhToanMouseClicked

    private void pnlDonHangDangThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDonHangDangThanhToanMouseClicked
        hover(pnlDonHangDangThanhToan, pnlDangTT);
    }//GEN-LAST:event_pnlDonHangDangThanhToanMouseClicked

    private void pnlDonHangHuyThanhToanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDonHangHuyThanhToanMouseClicked
        hover(pnlDonHangHuyThanhToan, pnlHuyTT);
    }//GEN-LAST:event_pnlDonHangHuyThanhToanMouseClicked

    private void pnlVonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlVonMouseClicked
        hover(pnlVon, pnlVonTT);
    }//GEN-LAST:event_pnlVonMouseClicked

    private void pnlDoanhSoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlDoanhSoMouseClicked
        hover(pnlDoanhSo, pnlDS);
    }//GEN-LAST:event_pnlDoanhSoMouseClicked

    private void pnlLoiNhuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLoiNhuanMouseClicked
        hover(pnlLoiNhuan, pnlLN);
    }//GEN-LAST:event_pnlLoiNhuanMouseClicked

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if(evt.getClickCount() == 1) {
            this.row = tblNhanVien.getSelectedRow();
            this.edit();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private boolean isValidForm() {
        StringBuilder strb = new StringBuilder();
        if (txtMaNhanVien.getText().isEmpty()) {
            strb.append("Mã nhân viên không được để trống!<br>");
        }
        
        if (txtHovaTen.getText().isEmpty()) {
            strb.append("Họ và tên không được để trống!<br>");
        } else if(!ValidationForm.isValidAlphaWhitespace(txtHovaTen.getText())) {
            strb.append("Họ và tên không được chứa ký tự đặc biệt!<br>");
        }
        
        if(new String(txtMatKhau.getPassword()).isEmpty()) {
            strb.append("Mật khẩu không được để trống!<br>");
        }
        
        if(txtLichLamViec.getText().isEmpty()) {
            strb.append("Lịch làm việc không được để trống!<br>");
        }
        
        if(txtEmail.getText().isEmpty()) {
             strb.append("Email không được để trống!<br>");
        } else if(!ValidationForm.isValidEmail(txtEmail.getText())) {
            strb.append("Email không đúng định dạng!<br>");
        }
        
        if(txtLuong.getText().isEmpty()) {
             strb.append("Lương không được để trống!<br>");
        } else if(!ValidationForm.isValidNumber(txtLuong.getText())) {
            strb.append("Lương không đúng định dạng!<br>");
        }
        
        if(txtSDT.getText().isEmpty()) {
             strb.append("Số điện thoại không được để trống!<br>");
        } else if(!ValidationForm.isValidPhoneNumber(txtSDT.getText())) {
            strb.append("Số điện thoại không đúng định dạng!<br>");
        }
        
        if(txtDiaChi.getText().isEmpty()) {
             strb.append("Địa chỉ không được để trống!<br>");
        }

        if (strb.length() > 0) {
            MsgBox.alert(null, "<html><font color='red'>" + strb + "</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if(isValidForm()) {
            NhanVien nv = nvDAO.selectByID(txtMaNhanVien.getText());
            if(nv != null) {
                MsgBox.alert(null, "<html><font color='red'>Mã nhân viên đã tồn tại!</font></html>");
                return;
            } 
            insert();
            init();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if(MsgBox.confirm(null, "Xóa nhân viên này?\nĐiều này sẽ dẫn đến xóa các dữ liệu liên quan!")) {
            if(Auth.user.getMaNhanVien().equals(txtMaNhanVien.getText())) {
                MsgBox.alert(null, "<html><font color='red'>Không thể xóa chính bản thân!</font></html>");
                return;
            }
            delete();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        timKiem();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void lblAnhNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhNhanVienMouseClicked
       chonAnh();
    }//GEN-LAST:event_lblAnhNhanVienMouseClicked

    private void cboThongKeNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThongKeNhanVienActionPerformed
        fillTableThongKeNhanVien();
    }//GEN-LAST:event_cboThongKeNhanVienActionPerformed

    private void cboThongKeDonHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboThongKeDonHangActionPerformed
        clearFormThongKeDonHang();
        fillTableThongKeDH();
    }//GEN-LAST:event_cboThongKeDonHangActionPerformed

    private void txtLichLamViecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLichLamViecActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLichLamViecActionPerformed

    private void txtDiaChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiaChiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDiaChiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboThongKeDonHang;
    private javax.swing.JComboBox<String> cboThongKeNhanVien;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lbl1;
    private javax.swing.JLabel lbl2;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl4;
    private javax.swing.JLabel lbl5;
    private javax.swing.JLabel lblAnhNhanVien;
    private javax.swing.JLabel lblDen;
    private javax.swing.JLabel lblDen1;
    private javax.swing.JLabel lblDen2;
    private javax.swing.JLabel lblDen3;
    private javax.swing.JLabel lblDen4;
    private javax.swing.JLabel lblDen5;
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblSanPhamDoanhSo;
    private javax.swing.JLabel lblSanPhamLoiNhuan;
    private javax.swing.JLabel lblSoDHChuaThanhToan;
    private javax.swing.JLabel lblSoDHDaThanhToan;
    private javax.swing.JLabel lblSoDHDangThanhToan;
    private javax.swing.JLabel lblSoVonSanPham;
    private javax.swing.JLabel lblTongDoanhSo;
    private javax.swing.JLabel lblTongLoiNhuan;
    private javax.swing.JLabel lblTongTienChuaThanhToan;
    private javax.swing.JLabel lblTongTienDaThanhToan;
    private javax.swing.JLabel lblTongTienDangThanhToan;
    private javax.swing.JLabel lblTongVon;
    private javax.swing.JLabel lblTu;
    private javax.swing.JLabel lblTu1;
    private javax.swing.JLabel lblTu2;
    private javax.swing.JLabel lblTu3;
    private javax.swing.JLabel lblTu4;
    private javax.swing.JLabel lblTu5;
    private javax.swing.JPanel pnlDS;
    private javax.swing.JPanel pnlDaTT;
    private javax.swing.JPanel pnlDangTT;
    private javax.swing.JPanel pnlDoanhSo;
    private javax.swing.JPanel pnlDonHangDaThanhToan;
    private javax.swing.JPanel pnlDonHangDangThanhToan;
    private javax.swing.JPanel pnlDonHangHuyThanhToan;
    private javax.swing.JPanel pnlHuyTT;
    private javax.swing.JPanel pnlLN;
    private javax.swing.JPanel pnlLoiNhuan;
    private javax.swing.JPanel pnlVon;
    private javax.swing.JPanel pnlVonTT;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNhanVienNhap;
    private javax.swing.JRadioButton rdoNhanVienSale;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoTruongPhong;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTable tblThongKeNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHovaTen;
    private javax.swing.JTextField txtLichLamViec;
    private javax.swing.JTextField txtLuong;
    private javax.swing.JTextField txtMaNhanVien;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNgayGiaNhap;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
