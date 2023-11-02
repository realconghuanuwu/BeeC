/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package gui;

import dao.ChiTIetHoaDonDAO;
import dao.HoaDonDAO;
import dao.KhachHangDAO;
import dao.SanPhamDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.SanPham;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import utils.Auth;
import utils.DataConvertsLib;
import utils.DateConvert;
import utils.EverythingAboutPrint;
import utils.MsgBox;
import utils.ValidationForm;
import utils.XImage;

/**
 *
 * @author huanl
 */
public class QuanLyHD extends javax.swing.JInternalFrame {

    /**
     * Creates new form QuanLyHD
     */
    public QuanLyHD() {
        initComponents();
        init();
        
    }
    
    SanPhamDAO spDAO = new SanPhamDAO();
    KhachHangDAO khDAO = new KhachHangDAO();
    HoaDonDAO hdDAO = new HoaDonDAO();
    ChiTIetHoaDonDAO cthdDAO = new ChiTIetHoaDonDAO();
    
    
    private void init() {
        ImageIcon icon = new ImageIcon("src\\images\\Bill16.png");
        setFrameIcon(icon);
        
        fillComBoBoxTrangThai();
        fillDataToTableSanPham();
        fillDataToTableKhachHang();
        fillDataToTableHoaDon();
        
        updateStatusKhachHang();
        updateStatusSanPham();
        updateStatusHoaDon();

    }

    public void selectTab(int index) {
        tabs.setSelectedIndex(index);
    }
    
    private void fillComBoBoxTrangThai() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTrangThaiThanhToan.getModel();
        model.addElement("Chưa Thanh Toán");
        model.addElement("Đang Thanh Toán");
        model.addElement("Đã Thanh Toán");
        cboTrangThaiThanhToan.setModel(model);
    }
    
    // KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG KHÁCH HÀNG
    int rowkh = -1;
    
    private void insertKhachHang() {
        KhachHang kh = getFormKhachHang();
        try {
            khDAO.insert(kh);
            fillDataToTableKhachHang();
            MsgBox.alert(this, "Thêm mới khách hàng thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "<html><font color='red'>Thêm mới khách hàng thất bại!</font></html>");
            e.printStackTrace();
        }
        
    }
    
    private void updateKhachHang() {
        KhachHang kh = getFormKhachHang();
        try {
            khDAO.update(kh);
            fillDataToTableKhachHang();
            MsgBox.alert(this, "Cập nhật khách hàng thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "<html><font color='red'>Cập nhật khách hàng thất bại!</font></html>");
            e.printStackTrace();
        }
        
    }
    
    private void deleteKhachHang() {
        String ma = txtMaKhachHang.getText();
        try {
            khDAO.delete(ma);
            fillDataToTableKhachHang();
            clearFormKhachHang();
            MsgBox.alert(this, "Xóa khách hàng thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "<html><font color='red'>Xóa thất bại!<br>Cần xóa các dữ liệu liên quan đến khách hàng này trước!</font></html>");
            e.printStackTrace();
        }
    }
    
    private void clearFormKhachHang() {
        KhachHang kh = new KhachHang();
        this.setFormKhachHang(kh);
        this.rowkh = -1;
        this.updateStatusKhachHang();

    }
    
    private void timKiemKhachHang() {
        this.fillDataToTableKhachHang();
        this.clearFormKhachHang();
        this.rowkh = -1;
        updateStatusKhachHang();
    }
        
        
    private void setFormKhachHang(KhachHang kh) {
        txtMaKhachHang.setText(kh.getMaKhachHang());
        txtHovaTenKhachHang.setText(kh.getHovaTen());
        txtDiaChiKhachHang.setText(kh.getDiaChi());
        txtGhiChuKhachHang.setText(kh.getGhiChu());
    }
    
    private KhachHang getFormKhachHang() {
        KhachHang kh = new KhachHang();
        kh.setMaKhachHang(txtMaKhachHang.getText());
        kh.setHovaTen(txtHovaTenKhachHang.getText());
        kh.setDiaChi(txtDiaChiKhachHang.getText());
        kh.setGhiChu(txtGhiChuKhachHang.getText());
        return kh;
    }
    
    private void editKhachHang() {
        String ma = (String) tblKhachHang.getValueAt(rowkh, 0);
        KhachHang kh = khDAO.selectByID(ma);
        setFormKhachHang(kh);
        tblKhachHang.setRowSelectionInterval(rowkh, rowkh);
        this.updateStatusKhachHang();
    }
    
    private void firstKhachHang() {
        this.rowkh = 0;
        this.editKhachHang();
    }
    
    private void prevKhachHang() {
        if(this.rowkh > 0) {
            this.rowkh--;
            this.editKhachHang();
        }
    }
    
    private void nextKhachHang() {
        if(this.rowkh < tblKhachHang.getRowCount()-1) {
            this.rowkh++;
            this.editKhachHang();
        }
    }
    
    private void lastKhachHang() {
        this.rowkh = tblKhachHang.getRowCount()-1;
        this.editKhachHang();
    }
    
    void updateStatusKhachHang() {
        boolean edit = (this.rowkh >=0);
        boolean first = (this.rowkh == 0);
        boolean last = (this.rowkh == tblKhachHang.getRowCount()-1);
        
        txtMaKhachHang.setEditable(!edit);
        btnThemKhachHang.setEnabled(!edit);
        btnSuaKhachHang.setEnabled(edit);
        btnXoaKhachHang.setEnabled(edit);
        
        btnFirstKhachHang.setEnabled(edit && !first);
        btnPrevKhachHang.setEnabled(edit && !first);
        btnNextKhachHang.setEnabled(edit && !last);
        btnLastKhachHang.setEnabled(edit && !last);
        
    }
    
    private void fillDataToTableKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        
        try {
            String keyword = txtTimKiemKhachHang.getText();
            List<KhachHang> list = khDAO.selectByKeyword(keyword);
            for (KhachHang kh : list) {
                Object[] row = {kh.getMaKhachHang(), kh.getHovaTen(), kh.getDiaChi(), kh.getGhiChu()};
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    // THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN THANH TOÁN
    int rowsp = -1;
    
    private void editSanPham() {
        Integer ma = (Integer) tblSanPham.getValueAt(rowsp, 0);
        SanPham sp = spDAO.selectByID(ma);
        setFormSanPham(sp);
        tblSanPham.setRowSelectionInterval(rowsp, rowsp);
        updateStatusSanPham();
    }
    
    private void editSanPhamToChiTietHoaDon() {
        Integer ma = Integer.parseInt(lblMaSanPham.getText());
        SanPham sp = spDAO.selectByID(ma);
        sp.setSoLuong(Integer.parseInt(txtSoLuongSanPham.getText())); //set số lượng sp lên hóa đơn
//        newRowCTHD(sp);
        updateSoLuongSanPham(sp);
        
        
        
//        updateStatusSanPham();
    }
    
    private void newRowCTHD(SanPham sp) {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDonThanhToan.getModel();
        
        //kiểm tra nếu trùng sp trong list sp bảng chi tiết thì gộp số lượng      
        for (int i = 0; i < model.getRowCount(); i++) {
            int masp = Integer.parseInt(model.getValueAt(i, 0).toString());
            int soluong = Integer.parseInt(model.getValueAt(i, 3).toString());
            if(sp.getMaSanPham() == masp) {
                model.setValueAt(sp.getSoLuong()+soluong, i, 3); //update lại số lượng               
                tblChiTietHoaDonThanhToan.setModel(model);
                return;
            } 
        }
        
        //ko trùng thì thêm row
        
        Object[] row = {sp.getMaSanPham(),sp.getTenSanPham(),sp.getMaModel(),sp.getSoLuong(),
            DataConvertsLib.formatCurrency(sp.getGiaBan().doubleValue()), DataConvertsLib.formatCurrency(sp.getGiaBan().doubleValue()*sp.getSoLuong())};
        model.addRow(row);
        tblChiTietHoaDonThanhToan.setModel(model);
    }
    
    private void updateSoLuongSanPham(SanPham sp) {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        //update so lg san pham trong bang san pham      
        for (int i = 0; i < model.getRowCount(); i++) {
            int masp = Integer.parseInt(model.getValueAt(i, 0).toString());
            int soluong = Integer.parseInt(model.getValueAt(i, 3).toString());
            //kiểm tra trùng masp thì update lại số lượng
            if(sp.getMaSanPham() == masp) {
                model.setValueAt(soluong - sp.getSoLuong(), i, 3); //update lại số lượng
                tblSanPham.setModel(model);
                newRowCTHD(sp);
                return;
            }
       
        }
        
    }
    
    
    
    private void delRowCTHD(int row) {
        if(row != -1) {
//            ctpnDAO.delete(txtMaPhieuNhap.getText(), (String) tblChiTietPhieuNhap.getValueAt(row, 0));
            DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDonThanhToan.getModel();
            updateStatusSanPham();
            //xóa sản phẩm ra khỏi bảng và cập nhập lại số lượng
            int masp = Integer.parseInt(model.getValueAt(row, 0).toString());
            int soluong = Integer.parseInt(model.getValueAt(row, 3).toString());
            //cập nhập lại số lượng trong bảng sản phẩm
            SanPham sp = new SanPham();
            sp.setMaSanPham(masp);
            sp.setSoLuong(-soluong);
            updateSoLuongSanPham(sp);
            model.removeRow(row);
        }
    }
    
     private void setFormSanPham(SanPham sp) {
         txtSanPhamdangchon.setText("- "+sp.getTenSanPham());
         lblMaSanPham.setText(String.valueOf(sp.getMaSanPham()));
         txtSoLuongSanPham.setText("1");
     }
    
    private void timKiemSanPham() {
        this.fillDataToTableSanPham();
        this.rowsp = -1;
        updateStatusSanPham();
    }
    
    private void fillDataToTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        String keyword = txtTimKiemSanPham.getText();
        try {
            List<SanPham> list = spDAO.selectByKeyword(keyword);
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSanPham(), sp.getTenSanPham(), sp.getMaModel(), sp.getSoLuong(), sp.getMaDanhMuc(), sp.getMaThuongHieu(),
                    DataConvertsLib.formatCurrency(sp.getGiaBan().doubleValue())};
                model.addRow(row);
            } 
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    private void firstSanPham() {
        this.rowsp = 0;
        this.editSanPham();
    }
    
    private void prevSanPham() {
        if(this.rowsp  > 0) {
            this.rowsp --;
            this.editSanPham();
        }
    }
    
    private void nextSanPham() {
        if(this.rowsp  < tblSanPham.getRowCount()-1) {
            this.rowsp ++;
            this.editSanPham();
        }
    }
    
    private void lastSanPham() {
        this.rowsp  = tblSanPham.getRowCount()-1;
        this.editSanPham();
    }
    
    void updateStatusSanPham() {
        boolean edit = (this.rowsp >=0);
        boolean first = (this.rowsp == 0);
        boolean last = (this.rowsp == tblSanPham.getRowCount()-1);
        
        btnThemSanPhamVaoHoaDon.setEnabled(edit);
        
        btnFirstSanPham.setEnabled(edit && !first);
        btnPrevSanPham.setEnabled(edit && !first);
        btnNextSanPham.setEnabled(edit && !last);
        btnLastSanPham.setEnabled(edit && !last);
        
    }
    
    // HÓA ĐƠN HÓA ĐƠN HÓA ĐƠN HÓA ĐƠN HÓA ĐƠN HÓA ĐƠN HÓA ĐƠN HÓA ĐƠNHÓA ĐƠN HÓA ĐƠN  ĐƠNHÓA ĐƠN HÓA ĐƠN  ĐƠNHÓA ĐƠN HÓA ĐƠN  ĐƠNHÓA ĐƠN HÓA ĐƠN  ĐƠNHÓA ĐƠN HÓA ĐƠN
    int rowhd = -1;
    
    private HoaDon getFormHoaDon() {
        HoaDon hd = new HoaDon();
        if(!txtMaHD.getText().isEmpty()) {
           hd.setMaHoaDon(Integer.parseInt(txtMaHD.getText())); 
        }
        hd.setMaKhachHang(txtMaKH.getText());
        hd.setMaNguoiTao(Auth.user.getMaNhanVien());
        hd.setGhiChu(txtGhiChuHoaDon.getText());
        hd.setTrangThaiThanhToan((String) cboTrangThaiThanhToan.getSelectedItem());
        return hd;
    }
    
    private void setFormHoaDon(HoaDon hd) {
        txtMaHoaDon.setText(String.valueOf(hd.getMaHoaDon()));
        txtMaKhachHangHoaDon.setText(hd.getMaKhachHang());
        txtMaHD.setText(String.valueOf(hd.getMaHoaDon()));
        txtMaKH.setText(hd.getMaKhachHang());
        txtGhiChuHoaDon.setText(hd.getGhiChu());
        if(hd.getTrangThaiThanhToan() != null) {
            if (hd.getTrangThaiThanhToan().equals("Chưa Thanh Toán")) {
                cboTrangThaiThanhToan.setSelectedIndex(0);
            } else if (hd.getTrangThaiThanhToan().equals("Đang Thanh Toán")) {
                cboTrangThaiThanhToan.setSelectedIndex(1);
            } else {
                cboTrangThaiThanhToan.setSelectedIndex(2);
            }
        }
    }
    
    
    private void insertHoaDon() {
        HoaDon hd = getFormHoaDon();
        hd.setTrangThaiThanhToan("Chưa Thanh Toán");
        try {
            hdDAO.insert(hd);
            fillDataToTableHoaDon();
            MsgBox.alert(this, "Thêm mới hóa đơn thành công!");
            setFormHoaDon(hd);
//            updateStatusThanhToan(true);
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới hóa đơn thất bại!");
//            updateStatusThanhToan(false);
            e.printStackTrace();
        }  
    }
    
    boolean isToogleThanhToan = false;
    private void updateHoaDon() {
        HoaDon hd = getFormHoaDon();
        if(isToogleThanhToan) {
            hd.setTrangThaiThanhToan("Đã Thanh Toán");
        }
        try {
            hdDAO.update(hd);
            fillDataToTableHoaDon();
            setFormHoaDon(hd);
//            updateStatusThanhToan(true);
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật hóa đơn thất bại!");
//            updateStatusThanhToan(false);
            e.printStackTrace();
        }  
    }
    
    private void fillDataToTableHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
        model.setRowCount(0);
        String keyword = txtTimKiemHoaDon.getText();
        try {
            List<HoaDon> list = hdDAO.selectByKeyword(keyword);
            for (HoaDon hd : list) {
                Object[] row = {hd.getMaHoaDon(),hd.getMaKhachHang(), hd.getMaNguoiTao(), 
                    DateConvert.toString(hd.getNgayTao(), "dd/MM/yyyy"), hd.getTrangThaiThanhToan(), hd.getGhiChu()};
                model.addRow(row);
            } 
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    private void timKiemHoaDon() {
        this.fillDataToTableHoaDon();
//        this.clearFormKhachHang();
//        this.rowkh = -1;
//        updateStatusKhachHang();
    }
    
    private void editHoaDon() {
        Integer ma = (Integer) tblHoaDon.getValueAt(rowhd, 0);
        HoaDon hd = hdDAO.selectByID(ma);
        setFormHoaDon(hd);
        fillTableChiTietHoaDon(ma);
        tblHoaDon.setRowSelectionInterval(rowhd, rowhd);
        updateStatusHoaDon();
    }
    
    //đọc từng row biến thành object ctpn
    // insert từng row từ object trên
    private ChiTietHoaDon getCTHD(int row) {
        ChiTietHoaDon cthd = new ChiTietHoaDon();
        cthd.setMaHoaDon(Integer.parseInt(txtMaHoaDon.getText()));
        cthd.setMaSanPham(Integer.parseInt(tblChiTietHoaDonThanhToan.getValueAt(row, 0).toString()));
        cthd.setSoLuong(Integer.parseInt(tblChiTietHoaDonThanhToan.getValueAt(row, 3).toString()));
        return cthd;
    }
    
    private void insertupdateChiTietHoaDon() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDonThanhToan.getModel();
        try {
            for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                ChiTietHoaDon cthd = getCTHD(rowIndex);
                if(cthdDAO.selectByID(cthd.getMaHoaDon(),cthd.getMaSanPham())!= null) {
                    cthdDAO.update(cthd);
                } else {
                    cthdDAO.insert(cthd);  
                }
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm hàng hóa thất bại!");
            e.printStackTrace();
        }       
    }
    
    private SanPham getSanPham(int row) {
        int masp = Integer.parseInt(tblSanPham.getValueAt(row, 0).toString());
        SanPham sp = spDAO.selectByID(masp);
        sp.setSoLuong(Integer.parseInt(tblSanPham.getValueAt(row, 3).toString()));
        return sp;
    }
    
    private void updateTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        try {
            for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                SanPham sp = getSanPham(rowIndex);
                spDAO.update(sp);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "cật nhập số lượng sản phẩm thất bại!");
            e.printStackTrace();
        }       
    }
    
    
    
    private void deleteHoaDon() {
        int ma = Integer.parseInt(txtMaHD.getText());
        try {
            hdDAO.delete(ma);
            fillDataToTableHoaDon();
            clearFormHoaDon();
            MsgBox.alert(this, "Xóa hóa đơn thành công!");
        } catch (Exception e) {
            MsgBox.alert(this, "Xóa hóa đơn thất bại!");
            e.printStackTrace();
        }
    }
    
    private void clearFormHoaDon() {
        HoaDon hd = new HoaDon();
        cboTrangThaiThanhToan.setSelectedIndex(0);
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        model.setRowCount(0);
        txtGhiChuHoaDon.setText("");
        txtTongTienHoaDon.setText("");
        txtTongTien.setText("");
        this.setFormHoaDon(hd);
        this.rowhd = -1;
        this.updateStatusHoaDon();
    }           
    
    private void firstHoaDon() {
        this.rowhd = 0;
        this.editHoaDon();
    }
    
    private void prevHoaDon() {
        if(this.rowhd > 0) {
            this.rowhd--;
            this.editHoaDon();
        }
    }
    
    private void nextHoaDon() {
        if(this.rowhd < tblHoaDon.getRowCount()-1) {
            this.rowhd++;
            this.editHoaDon();
        }
    }
    
    private void lastHoaDon() {
        this.rowhd = tblHoaDon.getRowCount()-1;
        this.editHoaDon();
    }
    
    void updateStatusHoaDon() {
        boolean edit = (this.rowhd >=0);
        boolean first = (this.rowhd == 0);
        boolean last = (this.rowhd== tblHoaDon.getRowCount()-1);
        
        btnThemHoaDon.setEnabled(!edit);
        btnSuaHoaDon.setEnabled(edit);
        btnXoaHoaDon.setEnabled(edit);
        
        btnFirstHoaDon.setEnabled(edit && !first);
        btnPrevHoaDon.setEnabled(edit && !first);
        btnNextHoaDon.setEnabled(edit && !last);
        btnLastHoaDon.setEnabled(edit && !last);
        
        tblSanPham.setEnabled(edit);
        tblChiTietHoaDonThanhToan.setEnabled(edit);
        btnDelRow.setEnabled(edit); 
        txtGhiChuHoaDon.setEnabled(edit); 
        txtTongTienHoaDon.setEnabled(edit); 
        btnThanhToan.setEnabled(edit); 
        btnIn.setEnabled(edit);
        txtTimKiemSanPham.setEnabled(edit); 
        btnTimKiemSanPham.setEnabled(edit); 
        txtSanPhamdangchon.setEnabled(edit);
        btnTru.setEnabled(edit); 
        btnCong.setEnabled(edit); 
        txtSoLuongSanPham.setEnabled(edit);
        
    }
    
    private void fillTableChiTietHoaDon(int mahd) {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDon.getModel();
        DefaultTableModel model2 = (DefaultTableModel) tblChiTietHoaDonThanhToan.getModel();
        model.setRowCount(0);
        model2.setRowCount(0);
        List<Object[]> list = cthdDAO.getChiTietHoaDonByMaHoaDon(mahd);
        double sumTien = 0;
        for (Object[] row : list) {
            double soLuong = Double.valueOf(row[3].toString());
            double donGia = Double.valueOf(row[4].toString());
            double tongTien = donGia*soLuong;
            Object[] newRow = {row[1],row[3],DataConvertsLib.formatCurrency(donGia),DataConvertsLib.formatCurrency(tongTien)};
            model.addRow(newRow);
            Object[] newRow2 = {row[0],row[1],row[2],row[3],DataConvertsLib.formatCurrency(donGia),DataConvertsLib.formatCurrency(tongTien)};
            model2.addRow(newRow2);
            sumTien += tongTien;
        }
        txtTongTien.setText(DataConvertsLib.formatCurrency(sumTien)+ " VNĐ");
        txtTongTienHoaDon.setText(DataConvertsLib.formatCurrency(sumTien)+ " VNĐ");
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblChiTietHoaDon = new javax.swing.JTable();
        btnXoaHoaDon = new javax.swing.JButton();
        btnSuaHoaDon = new javax.swing.JButton();
        btnTimKiemHoaDon = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        cboTrangThaiThanhToan = new javax.swing.JComboBox<>();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtTongTien = new javax.swing.JLabel();
        btnNewHoaDon = new javax.swing.JButton();
        btnThemHoaDon = new javax.swing.JButton();
        txtMaHD = new javax.swing.JTextField();
        txtMaKH = new javax.swing.JTextField();
        txtTimKiemHoaDon = new javax.swing.JTextField();
        btnLastHoaDon = new javax.swing.JButton();
        btnNextHoaDon = new javax.swing.JButton();
        btnPrevHoaDon = new javax.swing.JButton();
        btnFirstHoaDon = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtMaHoaDon = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        txtMaKhachHangHoaDon = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietHoaDonThanhToan = new javax.swing.JTable();
        btnDelRow = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        btnFirstSanPham = new javax.swing.JButton();
        btnPrevSanPham = new javax.swing.JButton();
        btnNextSanPham = new javax.swing.JButton();
        btnLastSanPham = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        txtTimKiemSanPham = new javax.swing.JTextField();
        btnTimKiemSanPham = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        txtGhiChuHoaDon = new javax.swing.JTextField();
        jPanel43 = new javax.swing.JPanel();
        txtTongTienHoaDon = new javax.swing.JTextField();
        btnThanhToan = new javax.swing.JButton();
        btnIn = new javax.swing.JButton();
        jPanel44 = new javax.swing.JPanel();
        txtSanPhamdangchon = new javax.swing.JTextField();
        lblMaSanPham = new javax.swing.JLabel();
        btnThemSanPhamVaoHoaDon = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnTru = new javax.swing.JButton();
        btnCong = new javax.swing.JButton();
        txtSoLuongSanPham = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        txtMaKhachHang = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        txtHovaTenKhachHang = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        txtTimKiemKhachHang = new javax.swing.JTextField();
        btnTimKiemKhachHang = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        txtDiaChiKhachHang = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jPanel40 = new javax.swing.JPanel();
        txtGhiChuKhachHang = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btnFirstKhachHang = new javax.swing.JButton();
        btnPrevKhachHang = new javax.swing.JButton();
        btnNextKhachHang = new javax.swing.JButton();
        btnLastKhachHang = new javax.swing.JButton();
        btnThemKhachHang = new javax.swing.JButton();
        btnSuaKhachHang = new javax.swing.JButton();
        btnXoaKhachHang = new javax.swing.JButton();
        btnNewKhachHang = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Hóa Đơn & Thanh Toán");
        setPreferredSize(new java.awt.Dimension(1800, 900));

        jPanel2.setBackground(new java.awt.Color(243, 243, 243));
        jPanel2.setPreferredSize(new java.awt.Dimension(996, 729));

        tabs.setBackground(new java.awt.Color(0, 102, 204));
        tabs.setForeground(new java.awt.Color(255, 255, 255));
        tabs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setToolTipText("");

        tblChiTietHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblChiTietHoaDon);

        btnXoaHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaHoaDon.setText("Xóa");
        btnXoaHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaHoaDonActionPerformed(evt);
            }
        });

        btnSuaHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaHoaDon.setText("Sửa");
        btnSuaHoaDon.setToolTipText("");
        btnSuaHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaHoaDonActionPerformed(evt);
            }
        });

        btnTimKiemHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemHoaDonActionPerformed(evt);
            }
        });

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Trạng Thái", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        cboTrangThaiThanhToan.setBackground(new java.awt.Color(255, 255, 255));
        cboTrangThaiThanhToan.setForeground(new java.awt.Color(0, 0, 0));
        cboTrangThaiThanhToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboTrangThaiThanhToan, 0, 236, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addComponent(cboTrangThaiThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn", "Mã Khách", "Tạo Bởi", "Ngày Tạo", "Trạng Thái", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tblHoaDon);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 0), 2), "Tổng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 0, 0))); // NOI18N

        txtTongTien.setBackground(new java.awt.Color(0, 0, 0));
        txtTongTien.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 51, 0));
        txtTongTien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtTongTien, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnNewHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnNewHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnNewHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Copy.png"))); // NOI18N
        btnNewHoaDon.setText("Mới");
        btnNewHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewHoaDonActionPerformed(evt);
            }
        });

        btnThemHoaDon.setBackground(new java.awt.Color(255, 0, 51));
        btnThemHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnThemHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemHoaDon.setText("Tạo Hóa Đơn Mới");
        btnThemHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemHoaDonActionPerformed(evt);
            }
        });

        txtMaHD.setBackground(new java.awt.Color(255, 255, 255));
        txtMaHD.setForeground(new java.awt.Color(0, 0, 0));
        txtMaHD.setText("jTextField1");
        txtMaHD.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtMaKH.setBackground(new java.awt.Color(255, 255, 255));
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKH.setForeground(new java.awt.Color(0, 0, 0));
        txtMaKH.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(0, 0, 0))); // NOI18N

        txtTimKiemHoaDon.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemHoaDon.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiemHoaDon.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 431, Short.MAX_VALUE)
                        .addComponent(btnThemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(btnNewHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSuaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtTimKiemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7))
                .addGap(14, 14, 14)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(101, 101, 101))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNewHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSuaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTimKiemHoaDon)
                            .addComponent(btnTimKiemHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))))
                .addGap(27, 27, 27)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnLastHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnLastHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastHoaDonActionPerformed(evt);
            }
        });

        btnNextHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnNextHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextHoaDonActionPerformed(evt);
            }
        });

        btnPrevHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevHoaDonActionPerformed(evt);
            }
        });

        btnFirstHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(245, 245, 245)
                .addComponent(btnFirstHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPrevHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNextHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLastHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1631, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(770, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrevHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 155, Short.MAX_VALUE)))
        );

        tabs.addTab("Hóa Đơn", jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Hóa Đơn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaHoaDon.setForeground(new java.awt.Color(0, 0, 0));
        txtMaHoaDon.setBorder(null);
        txtMaHoaDon.setEnabled(false);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaKhachHangHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKhachHangHoaDon.setForeground(new java.awt.Color(0, 0, 0));
        txtMaKhachHangHoaDon.setBorder(null);
        txtMaKhachHangHoaDon.setEnabled(false);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaKhachHangHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaKhachHangHoaDon, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tblChiTietHoaDonThanhToan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Mã Model", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblChiTietHoaDonThanhToan.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblChiTietHoaDonThanhToan);
        if (tblChiTietHoaDonThanhToan.getColumnModel().getColumnCount() > 0) {
            tblChiTietHoaDonThanhToan.getColumnModel().getColumn(0).setMinWidth(100);
            tblChiTietHoaDonThanhToan.getColumnModel().getColumn(0).setMaxWidth(100);
            tblChiTietHoaDonThanhToan.getColumnModel().getColumn(3).setMinWidth(80);
            tblChiTietHoaDonThanhToan.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        btnDelRow.setBackground(new java.awt.Color(19, 144, 234));
        btnDelRow.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDelRow.setForeground(new java.awt.Color(255, 255, 255));
        btnDelRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnDelRow.setText("Xóa Sản Phẩm");
        btnDelRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnDelRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelRowActionPerformed(evt);
            }
        });

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Mã Model", "Số Lượng", "Danh Mục", "Thương Hiệu", "Đơn Giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(100);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(100);
            tblSanPham.getColumnModel().getColumn(3).setMinWidth(80);
            tblSanPham.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        btnFirstSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstSanPhamActionPerformed(evt);
            }
        });

        btnPrevSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevSanPhamActionPerformed(evt);
            }
        });

        btnNextSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnNextSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextSanPhamActionPerformed(evt);
            }
        });

        btnLastSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnLastSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastSanPhamActionPerformed(evt);
            }
        });

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTimKiemSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiemSanPham.setBorder(null);

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        btnTimKiemSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSanPhamActionPerformed(evt);
            }
        });

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ghi Chú", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtGhiChuHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChuHoaDon.setForeground(new java.awt.Color(0, 0, 0));
        txtGhiChuHoaDon.setBorder(null);
        txtGhiChuHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGhiChuHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGhiChuHoaDon)
                .addContainerGap())
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGhiChuHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "TỔNG TIỀN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTongTienHoaDon.setForeground(new java.awt.Color(0, 0, 0));
        txtTongTienHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel43Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTongTienHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel43Layout.setVerticalGroup(
            jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTongTienHoaDon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        btnThanhToan.setBackground(new java.awt.Color(255, 51, 51));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cash.png"))); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        btnIn.setBackground(new java.awt.Color(19, 144, 234));
        btnIn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIn.setForeground(new java.awt.Color(255, 255, 255));
        btnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Print.png"))); // NOI18N
        btnIn.setText("In");
        btnIn.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActionPerformed(evt);
            }
        });

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Đang chọn sản phẩm:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtSanPhamdangchon.setForeground(new java.awt.Color(255, 0, 0));
        txtSanPhamdangchon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lblMaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMaSanPham.setText("0");

        javax.swing.GroupLayout jPanel44Layout = new javax.swing.GroupLayout(jPanel44);
        jPanel44.setLayout(jPanel44Layout);
        jPanel44Layout.setHorizontalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 13, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSanPhamdangchon, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel44Layout.setVerticalGroup(
            jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel44Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(jPanel44Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSanPhamdangchon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSanPham)))
        );

        btnThemSanPhamVaoHoaDon.setBackground(new java.awt.Color(19, 144, 234));
        btnThemSanPhamVaoHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSanPhamVaoHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSanPhamVaoHoaDon.setText("Thêm vào hóa đơn");
        btnThemSanPhamVaoHoaDon.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemSanPhamVaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamVaoHoaDonActionPerformed(evt);
            }
        });

        jLabel4.setText("Số Lượng:");

        btnTru.setBackground(new java.awt.Color(255, 255, 255));
        btnTru.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        btnTru.setForeground(new java.awt.Color(0, 0, 0));
        btnTru.setText("-");
        btnTru.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruActionPerformed(evt);
            }
        });

        btnCong.setBackground(new java.awt.Color(255, 255, 255));
        btnCong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCong.setForeground(new java.awt.Color(0, 0, 0));
        btnCong.setText("+");
        btnCong.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCongActionPerformed(evt);
            }
        });

        txtSoLuongSanPham.setBackground(new java.awt.Color(255, 255, 255));
        txtSoLuongSanPham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtSoLuongSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtSoLuongSanPham.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuongSanPham.setText("0");
        txtSoLuongSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtSoLuongSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtSoLuongSanPhamMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(515, 515, 515)
                .addComponent(btnFirstSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPrevSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLastSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThemSanPhamVaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTru)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSoLuongSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCong))
                            .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(595, 595, 595))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnDelRow)
                            .addComponent(jPanel43, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnDelRow, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jPanel43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThanhToan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSoLuongSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(btnTru)
                                .addComponent(btnCong)))
                        .addGap(18, 18, 18)
                        .addComponent(btnThemSanPhamVaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrevSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(123, Short.MAX_VALUE))
        );

        tabs.addTab("Thanh Toán", jPanel3);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Khách Hàng (SĐT)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtMaKhachHang.setBorder(null);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaKhachHang)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Họ Và Tên", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtHovaTenKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHovaTenKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtHovaTenKhachHang.setBorder(null);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtHovaTenKhachHang)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtHovaTenKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTimKiemKhachHang.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiemKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(0, 19, Short.MAX_VALUE)
                .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        btnTimKiemKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemKhachHangActionPerformed(evt);
            }
        });

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Địa Chỉ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtDiaChiKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiaChiKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtDiaChiKhachHang.setBorder(null);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDiaChiKhachHang)
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDiaChiKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khách Hàng", "Họ và Tên", "Địa Chỉ", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhachHang);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ghi Chú", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtGhiChuKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChuKhachHang.setForeground(new java.awt.Color(0, 0, 0));
        txtGhiChuKhachHang.setBorder(null);

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGhiChuKhachHang, javax.swing.GroupLayout.DEFAULT_SIZE, 628, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGhiChuKhachHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0, 25, 0));

        btnFirstKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstKhachHangActionPerformed(evt);
            }
        });
        jPanel7.add(btnFirstKhachHang);

        btnPrevKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevKhachHangActionPerformed(evt);
            }
        });
        jPanel7.add(btnPrevKhachHang);

        btnNextKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnNextKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextKhachHangActionPerformed(evt);
            }
        });
        jPanel7.add(btnNextKhachHang);

        btnLastKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnLastKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastKhachHangActionPerformed(evt);
            }
        });
        jPanel7.add(btnLastKhachHang);

        btnThemKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnThemKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemKhachHang.setText("Thêm");
        btnThemKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKhachHangActionPerformed(evt);
            }
        });

        btnSuaKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaKhachHang.setText("Sửa");
        btnSuaKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaKhachHangActionPerformed(evt);
            }
        });

        btnXoaKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaKhachHang.setText("Xóa");
        btnXoaKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhachHangActionPerformed(evt);
            }
        });

        btnNewKhachHang.setBackground(new java.awt.Color(19, 144, 234));
        btnNewKhachHang.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewKhachHang.setForeground(new java.awt.Color(255, 255, 255));
        btnNewKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Copy.png"))); // NOI18N
        btnNewKhachHang.setText("Mới");
        btnNewKhachHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewKhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(737, 737, 737))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXoaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 810, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(505, 505, 505))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(505, Short.MAX_VALUE))
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(btnThemKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNewKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(191, 191, 191))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabs.addTab("Khách Hàng", jPanel6);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 1739, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1798, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 874, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDelRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelRowActionPerformed
        int row = tblChiTietHoaDonThanhToan.getSelectedRow();
        delRowCTHD(row);
        SumTien();
    }//GEN-LAST:event_btnDelRowActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        if(tblChiTietHoaDon.getRowCount() > 0) {
            MsgBox.alert(this, "<html><font color='red'>Hóa đơn này đã thanh toán<br>Vui lòng tạo hóa đơn mới!</font></html>");
            return;
        }
        
        if(MsgBox.confirm(this, "Xác nhận thanh toán?\nKiểm tra chi tiết hóa đơn trước khi thanh toán.")) {
            isToogleThanhToan = true;
            //update hóa đơn
            try {
                updateHoaDon();
                insertupdateChiTietHoaDon();
                updateTableSanPham();
                MsgBox.alert(this, "Thanh Toán Thành Công!");
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alert(this, "<html><font color='red'>Thanh Toán Thất bại!</font></html>");
            }
        }
    }//GEN-LAST:event_btnThanhToanActionPerformed
    
    
    private void SumTien() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietHoaDonThanhToan.getModel();
        double sum = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            Integer masp = Integer.parseInt(model.getValueAt(i, 0).toString());
            Integer soLuong = Integer.parseInt(model.getValueAt(i, 3).toString());
            SanPham sp = spDAO.selectByID(masp);
            Double giaBan = sp.getGiaBan().doubleValue();
            sum += soLuong*giaBan;
        }
        txtTongTienHoaDon.setText(""+DataConvertsLib.formatCurrency(sum)+" VNĐ");
    }
    
    private void btnThemSanPhamVaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamVaoHoaDonActionPerformed
        int soluong = Integer.parseInt(txtSoLuongSanPham.getText());
//        int masp = Integer.parseInt(lblMaSanPham.getText());
        int soluongtrongbang = Integer.parseInt(tblSanPham.getValueAt(tblSanPham.getSelectedRow(), 3).toString());
        
        if(soluong <= 0 || soluongtrongbang <= 0 || soluong > soluongtrongbang) {
            MsgBox.alert(this, "<html><font color='red'>Số lượng không hợp lệ!</font></html>");
            return;
        }
        editSanPhamToChiTietHoaDon();
        SumTien();
        
    }//GEN-LAST:event_btnThemSanPhamVaoHoaDonActionPerformed

    private void btnPrevSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevSanPhamActionPerformed
        prevSanPham();
    }//GEN-LAST:event_btnPrevSanPhamActionPerformed

    private void btnTimKiemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSanPhamActionPerformed
        timKiemSanPham();
    }//GEN-LAST:event_btnTimKiemSanPhamActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowkh = tblKhachHang.getSelectedRow();
            this.editKhachHang();
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private boolean isValidKhachHangForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaKhachHang.getText().isEmpty()) {
            strb.append("Mã khách hàng không được để trống!<br>");
        }      
        
        if(txtHovaTenKhachHang.getText().isEmpty()) {
            strb.append("Họ và tên không được để trống!<br>");
        }
        
        if(txtDiaChiKhachHang.getText().isEmpty()) {
            strb.append("Địa chỉ không được để trống!<br>");
        }   
                      
        if(strb.length() > 0) {
            MsgBox.alert(this, "<html><font color='red'>"+strb+"</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKhachHangActionPerformed
        if(isValidKhachHangForm()) {
            if (khDAO.selectByID(txtMaKhachHang.getText()) != null) {
                MsgBox.alert(this, "<html><font color='red'>Mã khách hàng đã tồn tại!</font></html>");
                return;
            }
            insertKhachHang();
        }
    }//GEN-LAST:event_btnThemKhachHangActionPerformed

    private void btnSuaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaKhachHangActionPerformed
        updateKhachHang();
    }//GEN-LAST:event_btnSuaKhachHangActionPerformed

    private void btnXoaKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhachHangActionPerformed
        if (MsgBox.confirm(this, "Xóa khách hàng này?")) {
            deleteKhachHang();
            init();
        }
    }//GEN-LAST:event_btnXoaKhachHangActionPerformed

    private void btnFirstKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstKhachHangActionPerformed
        firstKhachHang();
    }//GEN-LAST:event_btnFirstKhachHangActionPerformed

    private void btnPrevKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevKhachHangActionPerformed
        prevKhachHang();
    }//GEN-LAST:event_btnPrevKhachHangActionPerformed

    private void btnNextKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextKhachHangActionPerformed
        nextKhachHang();
    }//GEN-LAST:event_btnNextKhachHangActionPerformed

    private void btnLastKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastKhachHangActionPerformed
        lastKhachHang();
    }//GEN-LAST:event_btnLastKhachHangActionPerformed

    private void btnNewKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewKhachHangActionPerformed
        clearFormKhachHang();
    }//GEN-LAST:event_btnNewKhachHangActionPerformed

    private void btnTimKiemKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemKhachHangActionPerformed
        timKiemKhachHang();
    }//GEN-LAST:event_btnTimKiemKhachHangActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowsp = tblSanPham.getSelectedRow();
            this.editSanPham();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnFirstSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstSanPhamActionPerformed
        firstSanPham();
    }//GEN-LAST:event_btnFirstSanPhamActionPerformed

    private void btnNextSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextSanPhamActionPerformed
        nextSanPham();
    }//GEN-LAST:event_btnNextSanPhamActionPerformed

    private void btnLastSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastSanPhamActionPerformed
        lastSanPham();
    }//GEN-LAST:event_btnLastSanPhamActionPerformed

    private void btnCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCongActionPerformed
        int solg = Integer.parseInt(txtSoLuongSanPham.getText());
        solg++;
        txtSoLuongSanPham.setText(""+solg);
    }//GEN-LAST:event_btnCongActionPerformed

    private void btnTruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruActionPerformed
        int solg = Integer.parseInt(txtSoLuongSanPham.getText());
        if(solg <= 0) {
            solg = 0;
        } else {
           solg--; 
        }
        txtSoLuongSanPham.setText(""+solg);
    }//GEN-LAST:event_btnTruActionPerformed

    private void btnTimKiemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemHoaDonActionPerformed
        timKiemHoaDon();
    }//GEN-LAST:event_btnTimKiemHoaDonActionPerformed

    private void txtSoLuongSanPhamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoLuongSanPhamMouseExited
        
    }//GEN-LAST:event_txtSoLuongSanPhamMouseExited

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowhd = tblHoaDon.getSelectedRow();
            editHoaDon();
        }
        
        if(evt.getClickCount() ==2) 
            selectTab(1);       
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnSuaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaHoaDonActionPerformed
        if(isValidHoaDonForm())
            updateHoaDon();
    }//GEN-LAST:event_btnSuaHoaDonActionPerformed

    private void btnXoaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaHoaDonActionPerformed
        if(isValidHoaDonForm()) {
            if (MsgBox.confirm(this, "Xóa hóa đơn này?\nĐiều này sẽ dẫn đến xóa các sản phẩm liên quan!")) {
                deleteHoaDon();
                init();
            }
        } 
    }//GEN-LAST:event_btnXoaHoaDonActionPerformed

    private boolean isValidHoaDonForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaKH.getText().isEmpty()) {
            strb.append("Mã khách hàng không được để trống!<br>");
        } else if(khDAO.selectByID(txtMaKH.getText())==null) {
            strb.append("Mã khách hàng không tồn tại!<br>Lưu ý: Phải thêm khách hàng tại mục Khách Hàng trước.");
        }  
           
        if(strb.length() > 0) {
            MsgBox.alert(this, "<html><font color='red'>"+strb+"</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemHoaDonActionPerformed
        if(isValidHoaDonForm()) {
           insertHoaDon(); 
        }
    }//GEN-LAST:event_btnThemHoaDonActionPerformed

    private void btnFirstHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstHoaDonActionPerformed
        firstHoaDon();
    }//GEN-LAST:event_btnFirstHoaDonActionPerformed

    private void btnNewHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewHoaDonActionPerformed
        clearFormHoaDon();
    }//GEN-LAST:event_btnNewHoaDonActionPerformed

    private void btnPrevHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevHoaDonActionPerformed
        prevHoaDon();
    }//GEN-LAST:event_btnPrevHoaDonActionPerformed

    private void btnNextHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextHoaDonActionPerformed
        nextHoaDon();
    }//GEN-LAST:event_btnNextHoaDonActionPerformed

    private void btnLastHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastHoaDonActionPerformed
        lastHoaDon();
    }//GEN-LAST:event_btnLastHoaDonActionPerformed

    private void txtGhiChuHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGhiChuHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGhiChuHoaDonActionPerformed

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed
        EverythingAboutPrint.print(tblChiTietHoaDonThanhToan,"Mã HĐ: "+ txtMaHoaDon.getText() +" - Mã KH: " + txtMaKhachHangHoaDon.getText(),"");
    }//GEN-LAST:event_btnInActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCong;
    private javax.swing.JButton btnDelRow;
    private javax.swing.JButton btnFirstHoaDon;
    private javax.swing.JButton btnFirstKhachHang;
    private javax.swing.JButton btnFirstSanPham;
    private javax.swing.JButton btnIn;
    private javax.swing.JButton btnLastHoaDon;
    private javax.swing.JButton btnLastKhachHang;
    private javax.swing.JButton btnLastSanPham;
    private javax.swing.JButton btnNewHoaDon;
    private javax.swing.JButton btnNewKhachHang;
    private javax.swing.JButton btnNextHoaDon;
    private javax.swing.JButton btnNextKhachHang;
    private javax.swing.JButton btnNextSanPham;
    private javax.swing.JButton btnPrevHoaDon;
    private javax.swing.JButton btnPrevKhachHang;
    private javax.swing.JButton btnPrevSanPham;
    private javax.swing.JButton btnSuaHoaDon;
    private javax.swing.JButton btnSuaKhachHang;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton btnThemHoaDon;
    private javax.swing.JButton btnThemKhachHang;
    private javax.swing.JButton btnThemSanPhamVaoHoaDon;
    private javax.swing.JButton btnTimKiemHoaDon;
    private javax.swing.JButton btnTimKiemKhachHang;
    private javax.swing.JButton btnTimKiemSanPham;
    private javax.swing.JButton btnTru;
    private javax.swing.JButton btnXoaHoaDon;
    private javax.swing.JButton btnXoaKhachHang;
    private javax.swing.JComboBox<String> cboTrangThaiThanhToan;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JLabel lblMaSanPham;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTietHoaDon;
    private javax.swing.JTable tblChiTietHoaDonThanhToan;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDiaChiKhachHang;
    private javax.swing.JTextField txtGhiChuHoaDon;
    private javax.swing.JTextField txtGhiChuKhachHang;
    private javax.swing.JTextField txtHovaTenKhachHang;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtMaKhachHang;
    private javax.swing.JTextField txtMaKhachHangHoaDon;
    private javax.swing.JTextField txtSanPhamdangchon;
    private javax.swing.JTextField txtSoLuongSanPham;
    private javax.swing.JTextField txtTimKiemHoaDon;
    private javax.swing.JTextField txtTimKiemKhachHang;
    private javax.swing.JTextField txtTimKiemSanPham;
    private javax.swing.JLabel txtTongTien;
    private javax.swing.JTextField txtTongTienHoaDon;
    // End of variables declaration//GEN-END:variables
}
