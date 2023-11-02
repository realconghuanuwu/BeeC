package gui;

import dao.ChiTietPhieuNhapDAO;
import dao.DanhMucDAO;
import dao.NhaCungCapDAO;
import dao.PhieuNhapDAO;
import dao.SanPhamDAO;
import dao.ThuongHieuDAO;
import entity.ChiTietPhieuNhap;
import entity.DanhMuc;
import entity.NhaCungCap;
import entity.PhieuNhap;
import entity.SanPham;
import entity.ThuongHieu;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import utils.Auth;
import utils.DataConvertsLib;
import utils.DateConvert;
import utils.MsgBox;
import utils.ValidationForm;
import utils.XImage;

/**
 *
 * @author huanl
 */
public class QuanLyKhoHangvaSanPham extends javax.swing.JInternalFrame {

    public QuanLyKhoHangvaSanPham() {
        initComponents();
        init();
    }
    
    DanhMucDAO danhmucDAO = new DanhMucDAO();
    ThuongHieuDAO thuonghieuDAO = new ThuongHieuDAO();
    NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    PhieuNhapDAO pnDAO = new PhieuNhapDAO();
    ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
    SanPhamDAO spDAO = new SanPhamDAO();
    
    public void selectTab(int index) {
        tabs.setSelectedIndex(index);
    }

    private void init() {
        ImageIcon icon = new ImageIcon("src\\images\\Product16.png");
        setFrameIcon(icon);
        
        fillDataToTableDanhMuc();
        fillDataToTableThuongHieu();
        fillDataToTableNhaCungCap();
        fillDataToTablePhieuNhap();
        fillDataToTableSanPham();
        
        updateStatusDanhMuc();
        updateStatusThuongHieu();
        updateStatusNhaCungCap();
        updateStatusPhieuNhap();
        updateStatusSanPham();
        
        if(nccDAO.selectAll() != null) 
            fillNCCToComBoBox();
        if(thuonghieuDAO.selectAll() != null)  
            fillThuongHieuToComBoBox();
        if(danhmucDAO.selectAll() != null)
            fillDanhMucToComBoBox();
    }
    
    
    //DANH MỤC DANH MỤC DANH MỤC DANH MỤC DANH MỤC DANH MỤC DANH MỤC DANH MỤC DANH MỤC
    int rowdm = -1;
    
    private void insertDanhMuc() {
        DanhMuc dm = getFormDanhMuc();
        try {
            danhmucDAO.insert(dm);
            fillDataToTableDanhMuc();
            MsgBox.alert(null, "Thêm mới danh mục thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm mới danh mục thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void updateDanhMuc() {
        DanhMuc dm = getFormDanhMuc();
        try {
            danhmucDAO.update(dm);
            fillDataToTableDanhMuc();
            MsgBox.alert(null, "Cập nhập danh mục thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Cập nhập danh mục thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void deleteDanhMuc() {
        String madm = txtMaDanhMuc.getText();
        try {
            danhmucDAO.delete(madm);
            fillDataToTableDanhMuc();
            clearFormDanhMuc();
            MsgBox.alert(null, "Xóa danh mục thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Xóa danh mục thất bại!");
            e.printStackTrace();
        }
    }
    
    private void clearFormDanhMuc() {
        DanhMuc dm = new DanhMuc();
        this.setFormDanhMuc(dm);
        this.rowdm = -1;
        this.updateStatusDanhMuc();

    }
    
    private void timKiemDanhMuc() {
        this.fillDataToTableDanhMuc();
        this.clearFormDanhMuc();
        this.rowdm = -1;
        updateStatusDanhMuc();
    }
        
        
    private void setFormDanhMuc(DanhMuc dm) {
        txtMaDanhMuc.setText(dm.getMaDanhMuc());
        txtTenDanhMuc.setText(dm.getTenDanhMuc());
        txtGhiChuDanhMuc.setText(dm.getGhiChu());
    }
    
    private DanhMuc getFormDanhMuc() {
        DanhMuc dm = new DanhMuc();
        dm.setMaDanhMuc(txtMaDanhMuc.getText());
        dm.setTenDanhMuc(txtTenDanhMuc.getText());
        dm.setGhiChu(txtGhiChuDanhMuc.getText());
        return dm;
    }
    
    private void editDanhMuc() {
        String madm = (String) tblDanhMuc.getValueAt(rowdm, 0);
        DanhMuc dm = danhmucDAO.selectByID(madm);
        setFormDanhMuc(dm);
        tblDanhMuc.setRowSelectionInterval(rowdm, rowdm);
        this.updateStatusDanhMuc();
    }
    
    private void firstDanhMuc() {
        this.rowdm = 0;
        this.editDanhMuc();
    }
    
    private void prevDanhMuc() {
        if(this.rowdm > 0) {
            this.rowdm--;
            this.editDanhMuc();
        }
    }
    
    private void nextDanhMuc() {
        if(this.rowdm < tblDanhMuc.getRowCount()-1) {
            this.rowdm++;
            this.editDanhMuc();
        }
    }
    
    private void lastDanhMuc() {
        this.rowdm = tblDanhMuc.getRowCount()-1;
        this.editDanhMuc();
    }
    
    void updateStatusDanhMuc() {
        boolean edit = (this.rowdm >=0);
        boolean first = (this.rowdm == 0);
        boolean last = (this.rowdm == tblDanhMuc.getRowCount()-1);
        
        txtMaDanhMuc.setEditable(!edit);
        btnThemDanhMuc.setEnabled(!edit);
        btnSuaDanhMuc.setEnabled(edit);
        btnXoaDanhMuc.setEnabled(edit);
        
        btnFirstDanhMuc.setEnabled(edit && !first);
        btnPrevDanhMuc.setEnabled(edit && !first);
        btnNextDanhMuc.setEnabled(edit && !last);
        btnLastDanhMuc.setEnabled(edit && !last);
        
    }
    
    private void fillDataToTableDanhMuc() {
        DefaultTableModel model = (DefaultTableModel) tblDanhMuc.getModel();
        model.setRowCount(0);
        
        try {
            String keyword = txtTimKiemDanhMuc.getText();
            List<DanhMuc> list = danhmucDAO.selectByKeyword(keyword);
            for (DanhMuc dm : list) {
                Object[] row = {dm.getMaDanhMuc(),dm.getTenDanhMuc()};
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    // THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU THƯƠNG HIỆU
    
    
    int rowth = -1;
    
    private void insertThuongHieu() {
        ThuongHieu th = getFormThuongHieu();
        try {
            thuonghieuDAO.insert(th);
            fillDataToTableThuongHieu();
            MsgBox.alert(null, "Thêm mới thương hiệu thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm mới thương hiệu thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void updateThuongHieu() {
        ThuongHieu th = getFormThuongHieu();
        try {
            thuonghieuDAO.update(th);
            fillDataToTableThuongHieu();
            MsgBox.alert(null, "Cập nhập thương hiệu thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Cập nhập thương hiệu thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void deleteThuongHieu() {
        String math = txtMaThuongHieu.getText();
        try {
            thuonghieuDAO.delete(math);
            fillDataToTableThuongHieu();
            clearFormThuongHieu();
            MsgBox.alert(null, "Xóa thương hiệu thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Xóa thương hiệu thất bại!");
            e.printStackTrace();
        }
    }
    
    private void clearFormThuongHieu() {
        ThuongHieu th = new ThuongHieu();
        this.setFormThuongHieu(th);
        this.rowth = -1;
        this.updateStatusThuongHieu();

    }
    
    private void timKiemThuongHieu() {
        this.fillDataToTableThuongHieu();
        this.clearFormThuongHieu();
        this.rowth = -1;
        updateStatusThuongHieu();
    }
        
        
    private void setFormThuongHieu(ThuongHieu th) {
        txtMaThuongHieu.setText(th.getMaThuongHieu());
        txtTenThuongHieu.setText(th.getTenThuongHieu());
        if(th.getAnh()!= null) {
            lblAnhThuongHieu.setToolTipText(th.getAnh());
            lblAnhThuongHieu.setIcon(XImage.read(th.getAnh()));
        } else {
            lblAnhThuongHieu.setToolTipText(null);
            lblAnhThuongHieu.setIcon(null);
        }
        txtGhiChuThuongHieu.setText(th.getGhiChu());
    }
    
    private ThuongHieu getFormThuongHieu() {
        ThuongHieu th = new ThuongHieu();
        th.setMaThuongHieu(txtMaThuongHieu.getText());
        th.setTenThuongHieu(txtTenThuongHieu.getText());
        th.setGhiChu(txtGhiChuThuongHieu.getText());
        th.setAnh(lblAnhThuongHieu.getToolTipText());
        return th;
    }
    
    private void editThuongHieu() {
        String math = (String) tblThuongHieu.getValueAt(rowth, 0);
        ThuongHieu th = thuonghieuDAO.selectByID(math);
        setFormThuongHieu(th);
        tblThuongHieu.setRowSelectionInterval(rowth, rowth);
        this.updateStatusThuongHieu();
    }
    
    JFileChooser fileChooserThuongHieu = new JFileChooser("C:\\Users\\huanl\\Desktop");
    
    void chonAnhThuongHieu() {
        if(fileChooserThuongHieu.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooserThuongHieu.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblAnhThuongHieu.setIcon(icon);
            lblAnhThuongHieu.setToolTipText(file.getName());
        }
    }
    
    private void firstThuongHieu() {
        this.rowth = 0;
        this.editThuongHieu();
    }
    
    private void prevThuongHieu() {
        if(this.rowth > 0) {
            this.rowth--;
            this.editThuongHieu();
        }
    }
    
    private void nextThuongHieu() {
        if(this.rowth < tblThuongHieu.getRowCount()-1) {
            this.rowth++;
            this.editThuongHieu();
        }
    }
    
    private void lastThuongHieu() {
        this.rowth = tblThuongHieu.getRowCount()-1;
        this.editThuongHieu();
    }
    
    void updateStatusThuongHieu() {
        boolean edit = (this.rowth >=0);
        boolean first = (this.rowth == 0);
        boolean last = (this.rowth == tblThuongHieu.getRowCount()-1);
        
        txtMaThuongHieu.setEditable(!edit);
        btnThemThuongHieu.setEnabled(!edit);
        btnSuaThuongHieu.setEnabled(edit);
        btnXoaThuongHieu.setEnabled(edit);
        
        btnFirstThuongHieu.setEnabled(edit && !first);
        btnPrevThuongHieu.setEnabled(edit && !first);
        btnNextThuongHieu.setEnabled(edit && !last);
        btnLastThuongHieu.setEnabled(edit && !last);
        
    }
    
    private void fillDataToTableThuongHieu() {
        DefaultTableModel model = (DefaultTableModel) tblThuongHieu.getModel();
        model.setRowCount(0);
        
        try {
            String keyword = txtTimKiemThuongHieu.getText();
            List<ThuongHieu> list = thuonghieuDAO.selectByKeyword(keyword);
            for (ThuongHieu th : list) {
                Object[] row = {th.getMaThuongHieu(),th.getTenThuongHieu()};
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    // NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP NHÀ CUNG CẤP

    int rowncc = -1;
    
    private void insertNhaCungCap() {
        NhaCungCap ncc = getFormNhaCungCap();
        try {
            nccDAO.insert(ncc);
            fillDataToTableNhaCungCap();
            MsgBox.alert(null, "Thêm mới nhà cung cấp thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm mới nhà cung cấp thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void updateNhaCungCap() {
        NhaCungCap ncc = getFormNhaCungCap();
        try {
            nccDAO.update(ncc);
            fillDataToTableNhaCungCap();
            MsgBox.alert(null, "Cập nhập nhà cung cấp thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Cập nhập nhà cung cấp thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void deleteNhaCungCap() {
        String ma = txtMaNCC.getText();
        try {
            nccDAO.delete(ma);
            fillDataToTableNhaCungCap();
            clearFormNhaCungCap();
            MsgBox.alert(null, "Xóa nhà cung cấp thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Xóa nhà cung cấp thất bại!");
            e.printStackTrace();
        }
    }
    
    private void clearFormNhaCungCap() {
        NhaCungCap ncc = new NhaCungCap();
        setFormNhaCungCap(ncc);
        this.rowncc = -1;
        this.updateStatusNhaCungCap();

    }
    
    private void timKiemNhaCungCap() {
        this.fillDataToTableNhaCungCap();
        this.clearFormNhaCungCap();
        this.rowncc = -1;
        updateStatusNhaCungCap();
    }
        
        
    private void setFormNhaCungCap(NhaCungCap ncc) {
        txtMaNCC.setText(ncc.getMaNCC());
        txtTenNCC.setText(ncc.getTenNCC());
        txtDiaChiNCC.setText(ncc.getDiaChi());
        txtSDTNCC.setText(ncc.getSoDienThoai());
        txtMaSoThueNCC.setText(ncc.getMaSoThue());
        txtGhiChuNCC.setText(ncc.getGhiChu());
                
    }
    
    private NhaCungCap getFormNhaCungCap() {
        NhaCungCap ncc = new NhaCungCap();
        ncc.setMaNCC(txtMaNCC.getText());
        ncc.setTenNCC(txtTenNCC.getText());
        ncc.setDiaChi(txtDiaChiNCC.getText());
        ncc.setSoDienThoai(txtSDTNCC.getText());
        ncc.setMaSoThue(txtMaSoThueNCC.getText());
        ncc.setGhiChu(txtGhiChuNCC.getText());
        return ncc;
    }
    
    private void editNhaCungCap() {
        String mancc = (String) tblNCC.getValueAt(rowncc, 0);
        NhaCungCap ncc = nccDAO.selectByID(mancc);
        setFormNhaCungCap(ncc);
        tblNCC.setRowSelectionInterval(rowncc, rowncc);
        this.updateStatusNhaCungCap();
    }
    
    private void firstNhaCungCap() {
        this.rowncc = 0;
        this.editNhaCungCap();
    }
    
    private void prevNhaCungCap() {
        if(this.rowncc > 0) {
            this.rowncc--;
            this.editNhaCungCap();
        }
    }
    
    private void nextNhaCungCap() {
        if(this.rowncc < tblNCC.getRowCount()-1) {
            this.rowncc++;
            this.editNhaCungCap();
        }
    }
    
    private void lastNhaCungCap() {
        this.rowncc = tblNCC.getRowCount()-1;
        this.editNhaCungCap();
    }
    
    void updateStatusNhaCungCap() {
        boolean edit = (this.rowncc >=0);
        boolean first = (this.rowncc == 0);
        boolean last = (this.rowncc == tblNCC.getRowCount()-1);
        
        txtMaNCC.setEditable(!edit);
        btnThemNCC.setEnabled(!edit);
        btnSuaNCC.setEnabled(edit);
        btnXoaNCC.setEnabled(edit);
        
        btnFirstNCC.setEnabled(edit && !first);
        btnPrevNCC.setEnabled(edit && !first);
        btnNextNCC.setEnabled(edit && !last);
        btnLastNCC.setEnabled(edit && !last);
        
    }
    
    private void fillDataToTableNhaCungCap() {
        DefaultTableModel model = (DefaultTableModel) tblNCC.getModel();
        model.setRowCount(0);
        
        try {
            String keyword = txtTimKiemNCC.getText();
            List<NhaCungCap> list = nccDAO.selectByKeyword(keyword);
            for (NhaCungCap ncc : list) {
                Object[] row = {ncc.getMaNCC(), ncc.getTenNCC(), ncc.getDiaChi(), ncc.getSoDienThoai(), ncc.getMaSoThue()};
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    // PHIẾU NHẬP PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP  PHIẾU NHẬP PHIẾU NHẬP 
    
    int rowpn = -1;
    
    private void insertPhieuNhap() {
        PhieuNhap pn = getPhieuNhap();
        try {
            pnDAO.insert(pn);
            fillDataToTablePhieuNhap();
            MsgBox.alert(null, "Thêm mới phiếu nhập thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm mới phiếu nhập thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void updatePhieuNhap() {
        PhieuNhap pn = getPhieuNhap();
        try {
            pnDAO.update(pn);
            fillDataToTablePhieuNhap();
            MsgBox.alert(null, "Cập nhật phiếu nhập thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Cập nhật phiếu nhập thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void deletePhieuNhap() {
        String ma = txtMaPhieuNhap.getText();
        try {
            pnDAO.delete(ma);
            fillDataToTablePhieuNhap();
            clearFormPhieuNhap();
            MsgBox.alert(null, "Xóa phiếu nhập thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Xóa phiếu nhập thất bại!");
            e.printStackTrace();
        }
    }
    
    private void fillDataToTablePhieuNhap() {
        DefaultTableModel model = (DefaultTableModel) tblPhieuNhap.getModel();
        model.setRowCount(0);
        
        try {
            String keyword = txtTimKiemPhieuNhap.getText();
            List<PhieuNhap> list = pnDAO.selectByKeyword(toMaNCC(keyword));
            for (PhieuNhap pn : list) {
                Object[] row = {pn.getMaPhieuNhap(),toTenNCC(pn.getMaNhaCungCap()),DateConvert.toString(pn.getNgayTao(), "dd/MM/yyyy")};
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
        
    }
    
    private void timKiemPhieuNhap() {
        this.fillDataToTablePhieuNhap();
        this.clearFormPhieuNhap();
        this.rowpn = -1;
        updateStatusPhieuNhap();
    }
    
    private void clearFormPhieuNhap() {
        PhieuNhap pn = new PhieuNhap();
        pn.setNgayTao(DateConvert.addDays(new Date(), 0));
        setFormPhieuNhap(pn);
        this.rowpn = -1;
        this.updateStatusPhieuNhap();
    }
    
    
    private void fillNCCToComBoBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbTenNCC.getModel();
        model.removeAllElements();
        List<NhaCungCap> list = nccDAO.selectAll();
        for (NhaCungCap ncc: list) {
            model.addElement(ncc);
        }
        cbbTenNCC.setSelectedIndex(0);
    }
    
    private PhieuNhap getPhieuNhap() {
        PhieuNhap pn = new PhieuNhap();
        pn.setMaPhieuNhap(txtMaPhieuNhap.getText());
        pn.setMaNhaCungCap(toMaNCC(String.valueOf(cbbTenNCC.getSelectedItem())));
        if(txtNgayTaoPhieuNhap.getText() != null)
            pn.setNgayTao(DateConvert.toDate(txtNgayTaoPhieuNhap.getText(), "dd/MM/yyyy"));
        else
            pn.setNgayTao(DateConvert.addDays(new Date(), 0));
        return pn;
    }
    
    private void setFormPhieuNhap(PhieuNhap pn) {
        if(pn.getMaPhieuNhap() != null) {
            txtMaPhieuNhap.setText(pn.getMaPhieuNhap());
            fillDataToTableChiTietPhieuNhap(txtMaPhieuNhap.getText());
        } else {
            txtMaPhieuNhap.setText("");
            fillDataToTableChiTietPhieuNhap("ngfkla41156dfaz#");
        }
        // đống này là để set combobox
        if(pn.getMaNhaCungCap() != null) {
            String tenncc = (String) tblPhieuNhap.getValueAt(rowpn, 1);
            List<NhaCungCap> ncc = nccDAO.selectAll();
            int index = 0;
            for (NhaCungCap item : ncc) {
                if (item.getTenNCC().equals(tenncc)) {
                    cbbTenNCC.setSelectedIndex(index);
                    break;
                }
                index++;
            }
        } else {
            cbbTenNCC.setSelectedIndex(0);
        }
        
        
        //
        if(pn.getNgayTao() != null) {
            txtNgayTaoPhieuNhap.setText(DateConvert.toString(pn.getNgayTao(), "dd/MM/yyyy"));
        } else {
            txtNgayTaoPhieuNhap.setText("");
        }
    }
    
    //covert từ tên ncc -> mã ncc
    
    private String toTenNCC(String maNCC) {
        List<NhaCungCap> list = nccDAO.selectAll();
        for (NhaCungCap nhaCungCap : list) {
            if(maNCC.equals(nhaCungCap.getMaNCC())) {
                return nhaCungCap.getTenNCC();
            }
        }
        return maNCC;
    }
    
    private String toMaNCC(String tenNCC) {
        List<NhaCungCap> list = nccDAO.selectAll();
        for (NhaCungCap nhaCungCap : list) {
            if(tenNCC.equals(nhaCungCap.getTenNCC())) {
                return nhaCungCap.getMaNCC();
            }
        }
        return tenNCC;
    }
    
    private void editPhieuNhap() {
        String ma = (String) tblPhieuNhap.getValueAt(rowpn, 0);
        PhieuNhap pn = pnDAO.selectByID(ma);
        setFormPhieuNhap(pn);
        tblPhieuNhap.setRowSelectionInterval(rowpn, rowpn);
        this.updateStatusPhieuNhap();
    }
    
    private void firstPhieuNhap() {
        this.rowpn = 0;
        this.editPhieuNhap();
    }
    
    private void prevPhieuNhap() {
        if(this.rowpn > 0) {
            this.rowpn--;
            this.editPhieuNhap();
        }
    }
    
    private void nextPhieuNhap() {
        if(this.rowpn < tblPhieuNhap.getRowCount()-1) {
            this.rowpn++;
            this.editPhieuNhap();
        }
    }
    
    private void lastPhieuNhap() {
        this.rowpn = tblPhieuNhap.getRowCount()-1;
        this.editPhieuNhap();
    }
    
    void updateStatusPhieuNhap() {
        boolean edit = (this.rowpn >=0);
        boolean first = (this.rowpn == 0);
        boolean last = (this.rowpn == tblPhieuNhap.getRowCount()-1);
        
        txtMaPhieuNhap.setEditable(!edit);
        btnThemPhieuNhap.setEnabled(!edit);
        btnSuaPhieuNhap.setEnabled(edit);
        btnXoaPhieuNhap.setEnabled(edit);
        btnNewRow.setEnabled(edit);
        btnDeleteRow.setEnabled(edit);
        btnEditRow.setEnabled(edit);
        
        btnFirstPhieuNhap.setEnabled(edit && !first);
        btnPrevPhieuNhap.setEnabled(edit && !first);
        btnNextPhieuNhap.setEnabled(edit && !last);
        btnLastPhieuNhap.setEnabled(edit && !last);
        
    }
    private void newRow() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietPhieuNhap.getModel();
        Object[] row = {"","","","",""};
        model.addRow(row);
        tblChiTietPhieuNhap.setModel(model);
    }
    
    private void delRow(int row) {
        if(row != -1) {
            ctpnDAO.delete(txtMaPhieuNhap.getText(), (String) tblChiTietPhieuNhap.getValueAt(row, 0));
            DefaultTableModel model = (DefaultTableModel) tblChiTietPhieuNhap.getModel();
            model.removeRow(row);
        }
    }
    
    //đọc từng row biến thành object ctpn
    // insert từng row từ object trên
    private ChiTietPhieuNhap getCTPN(int row) {
        ChiTietPhieuNhap ctpn = new ChiTietPhieuNhap();
        ctpn.setMaPhieuNhap(txtMaPhieuNhap.getText());
        ctpn.setTenSanPham((String) tblChiTietPhieuNhap.getValueAt(row, 0));
        ctpn.setDVT((String) tblChiTietPhieuNhap.getValueAt(row, 1));
        ctpn.setSoLuong(Integer.parseInt(tblChiTietPhieuNhap.getValueAt(row, 2).toString()));
        ctpn.setDonGia(new BigDecimal(tblChiTietPhieuNhap.getValueAt(row, 3).toString()));
        return ctpn;
    }
    
    private void dmm() {
        DefaultTableModel model = (DefaultTableModel) tblChiTietPhieuNhap.getModel();
        try {
            for (int rowIndex = 0; rowIndex < model.getRowCount(); rowIndex++) {
                ChiTietPhieuNhap ctpn = getCTPN(rowIndex);
                if(ctpnDAO.selectByID(ctpn.getMaPhieuNhap(), ctpn.getTenSanPham()) != null) {
                    ctpnDAO.update(ctpn);
                } else {
                    ctpnDAO.insert(ctpn);  
                }
            }
            MsgBox.alert(null, "cập nhật thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm hàng hóa thất bại!");
            e.printStackTrace();
        }       
    }

    private void fillDataToTableChiTietPhieuNhap(String maphieunhap) {
        DefaultTableModel model = (DefaultTableModel) tblChiTietPhieuNhap.getModel();
        model.setRowCount(0);
        
        try {
            List<ChiTietPhieuNhap> list = ctpnDAO.selectByMaPhieuNhap(maphieunhap);
            for (ChiTietPhieuNhap ctpn : list) {
                BigDecimal thanhTien = ctpn.getDonGia().multiply(BigDecimal.valueOf(ctpn.getSoLuong()));
                Object[] row = {ctpn.getTenSanPham(),ctpn.getDVT(),ctpn.getSoLuong(), 
                    DataConvertsLib.formatCurrency(ctpn.getDonGia().doubleValue()), DataConvertsLib.formatCurrency(thanhTien.doubleValue()) };
                model.addRow(row);
            } 
            
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    private void editChiTietPhieuNhap() {
        String tensp = (String)tblChiTietPhieuNhap.getValueAt(rowctpn, 0);
        System.out.println("tensp"+ tensp);
        String mapn = txtMaPhieuNhap.getText();
        ChiTietPhieuNhap ctpn = ctpnDAO.selectByID(mapn,tensp);
        setPhieuNhapToSanPham(ctpn);
        tblChiTietPhieuNhap.setRowSelectionInterval(rowpn, rowpn);
    }
    
    // SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM SẢN PHẨM
    
    int rowsp = -1;
    
    private void setPhieuNhapToSanPham(ChiTietPhieuNhap ctpn) {
        txtMaPhieuNhapSP.setText(ctpn.getMaPhieuNhap());
        txtTenSanPham.setText(ctpn.getTenSanPham());
        txtSoLuongSanPham.setText(String.valueOf(ctpn.getSoLuong()));
        txtGiaBanSanPham.setText(String.valueOf(ctpn.getDonGia()));
        txtMaModelSanPham.setText("");
        txtThoiGianBaoHanh.setText("");
        txtMoTaSanPham.setText("");
        tabs.setSelectedIndex(1);
        rowsp = -1;
        updateStatusSanPham();
    }

    private void insertSanPham() {
        SanPham sp = getFormSanPham();
        try {
            spDAO.insert(sp);
            fillDataToTableSanPham();
            MsgBox.alert(null, "Thêm mới sản phẩm thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Thêm mới sản phẩm thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void updateSanPham() {
        SanPham sp = getFormSanPham();
        sp.setMaSanPham(Integer.parseInt(txtMaSanPham.getText().toString()));
        try {
            spDAO.update(sp);
            fillDataToTableSanPham();
            MsgBox.alert(null, "Cập nhật sản phẩm thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Cập nhật sản phẩm thất bại!");
            e.printStackTrace();
        }
        
    }
    
    private void deleteSanPham() {
        Integer ma = Integer.parseInt(txtMaSanPham.getText());
        try {
            spDAO.delete(ma);
            fillDataToTableSanPham();
            clearFormSanPham();
            MsgBox.alert(null, "Xóa sản phẩm thành công!");
        } catch (Exception e) {
            MsgBox.alert(null, "Xóa sản phẩm thất bại!");
            e.printStackTrace();
        }
    }
    
    private void clearFormSanPham() {
        SanPham sp = new SanPham();
        sp.setMaSanPham(0);
        sp.setMaPhieuNhap("");
        sp.setTenSanPham("");
        sp.setMaModel("");
        sp.setMaThuongHieu("");
        sp.setMaDanhMuc("");
        sp.setGiaBan(new BigDecimal(0));
        sp.setMoTa("");
        sp.setAnh(null);
        this.setFormSanPham(sp);
        this.rowsp = -1;
        this.updateStatusSanPham();

    }
    
    private void timKiemSanPham() {
        this.fillDataToTableSanPham();
        this.clearFormSanPham();
        this.rowsp = -1;
        updateStatusSanPham();
    }
        
        
    private void setFormSanPham(SanPham sp) {
        txtMaPhieuNhapSP.setText(sp.getMaPhieuNhap());
        txtMaSanPham.setText(String.valueOf(sp.getMaSanPham()));
        txtTenSanPham.setText(sp.getTenSanPham());
        txtMaModelSanPham.setText(sp.getMaModel());
        
        // đống này là để set combobox thương hiệu
        if(sp.getMaThuongHieu() != null) {
            List<ThuongHieu> listTH = thuonghieuDAO.selectAll();
            int index  = 0;
            for (ThuongHieu thuongHieu : listTH) {
                if(thuongHieu.getMaThuongHieu().equals(sp.getMaThuongHieu())) {
                    cboThuongHieu.setSelectedIndex(index);
                    break;
                }
                index++;
            }
        } else {
            cboThuongHieu.setSelectedIndex(0);
            MsgBox.alert(null, "Không tìm thấy Thương Hiệu!");
        }
        
        // đống này là để set combobox danh mục
        if(sp.getMaDanhMuc() != null) {
            List<DanhMuc> listdm = danhmucDAO.selectAll();
            int index = 0;
            for (DanhMuc danhMuc : listdm) {
                if(danhMuc.getMaDanhMuc().equals(sp.getMaDanhMuc())) {
                    cboDanhMuc.setSelectedIndex(index);
                    break;
                }
                index++;
            }
        } else {
            cboDanhMuc.setSelectedIndex(0);
            MsgBox.alert(null, "Không tìm thấy Danh Mục!");
        }
          
        txtGiaBanSanPham.setText(String.valueOf(sp.getGiaBan()));
        txtMoTaSanPham.setText(sp.getMoTa());
        txtThoiGianBaoHanh.setText(String.valueOf(sp.getThoiGianBaoHanh()));
        txtSoLuongSanPham.setText(String.valueOf(sp.getSoLuong()));
        
        if(sp.getAnh()!= null) {
            lblAnhSanPham.setToolTipText(sp.getAnh());
            lblAnhSanPham.setIcon(XImage.read(sp.getAnh()));
        } else {
            lblAnhSanPham.setToolTipText(null);
            lblAnhSanPham.setIcon(null);
        }
        
    }
    
    private SanPham getFormSanPham() {
        SanPham sp = new SanPham();    
        sp.setMaPhieuNhap(txtMaPhieuNhapSP.getText());
        sp.setTenSanPham(txtTenSanPham.getText());
        sp.setMaModel(txtMaModelSanPham.getText());
        sp.setMaThuongHieu(String.valueOf(cboThuongHieu.getSelectedItem()));
        sp.setMaDanhMuc(String.valueOf(cboDanhMuc.getSelectedItem()));
        sp.setGiaBan(new BigDecimal(txtGiaBanSanPham.getText()));
        sp.setMoTa(txtMoTaSanPham.getText());
        sp.setMaNguoiNhap(Auth.user.getMaNhanVien());
        sp.setAnh(lblAnhSanPham.getToolTipText());
        sp.setSoLuong(Integer.parseInt(txtSoLuongSanPham.getText().toString()));
        sp.setThoiGianBaoHanh(Integer.parseInt(txtThoiGianBaoHanh.getText().toString()));
        return sp;
    }
    
    private void editSanPham() {
        Integer ma = (Integer) tblSanPham.getValueAt(rowsp, 0);
        SanPham sp = spDAO.selectByID(ma);
        setFormSanPham(sp);
        tblSanPham.setRowSelectionInterval(rowsp, rowsp);
        this.updateStatusSanPham();
    }
    
    JFileChooser fileChooserSanPham = new JFileChooser("C:\\Users\\huanl\\Desktop");
    
    void chonAnhSanPham() {
        if(fileChooserSanPham.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooserSanPham.getSelectedFile();
            XImage.save(file);
            ImageIcon icon = XImage.read(file.getName());
            lblAnhSanPham.setIcon(icon);
            lblAnhSanPham.setToolTipText(file.getName());
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
    
    private void updateStatusSanPham() {
        boolean edit = (this.rowsp >=0);
        boolean first = (this.rowsp == 0);
        boolean last = (this.rowsp == tblSanPham.getRowCount()-1);
        
//        txtMaSanPham.setEditable(!edit);
        btnThemSanPham.setEnabled(!edit);
        btnSuaSanPham.setEnabled(edit);
        btnXoaSanPham.setEnabled(edit);
        
        btnFirstSanPham.setEnabled(edit && !first);
        btnPrevSanPham.setEnabled(edit && !first);
        btnNextSanPham.setEnabled(edit && !last);
        btnLastSanPham.setEnabled(edit && !last);
        
    }
    
    private void fillDataToTableSanPham() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        String keyword = txtTimKiemSanPham.getText();
        try {
            List<SanPham> list = spDAO.selectByKeyword(keyword);
            for (SanPham sp : list) {
                Object[] row = {sp.getMaSanPham(), sp.getTenSanPham(), sp.getMaModel(), sp.getSoLuong(), 
                    DataConvertsLib.formatCurrency(sp.getGiaBan().doubleValue()), sp.getMaDanhMuc()};
                model.addRow(row);
            } 
        } catch (Exception e) {
            MsgBox.alert(null, "Lỗi truy vấn dữ liệu");
            e.printStackTrace();
        }
    }
    
    private void fillThuongHieuToComBoBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboThuongHieu.getModel();
        model.removeAllElements();
        List<ThuongHieu> list = thuonghieuDAO.selectAll();
        for (ThuongHieu thuongHieu : list) {
            model.addElement(thuongHieu);
        }
        cboThuongHieu.setSelectedIndex(0);
    }
    
    private void fillDanhMucToComBoBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboDanhMuc.getModel();
        model.removeAllElements();
        List<DanhMuc> list = danhmucDAO.selectAll();
        for (DanhMuc danhMuc : list) {
            model.addElement(danhMuc);
        }
        cboDanhMuc.setSelectedIndex(0);
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtMaPhieuNhap = new javax.swing.JTextField();
        jPanel20 = new javax.swing.JPanel();
        cbbTenNCC = new javax.swing.JComboBox<>();
        jPanel21 = new javax.swing.JPanel();
        txtNgayTaoPhieuNhap = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChiTietPhieuNhap = new javax.swing.JTable();
        btnThemPhieuNhap = new javax.swing.JButton();
        btnXoaPhieuNhap = new javax.swing.JButton();
        btnSuaPhieuNhap = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        btnFirstPhieuNhap = new javax.swing.JButton();
        btnPrevPhieuNhap = new javax.swing.JButton();
        btnNextPhieuNhap = new javax.swing.JButton();
        btnLastPhieuNhap = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        txtTimKiemPhieuNhap = new javax.swing.JTextField();
        btnTimKiemPhieuNhap = new javax.swing.JButton();
        btnDeleteRow = new javax.swing.JButton();
        btnNewRow = new javax.swing.JButton();
        btnEditRow = new javax.swing.JButton();
        btnNewPhieuNhap = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        txtMaSanPham = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txtTenSanPham = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        txtGiaBanSanPham = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        txtMaPhieuNhapSP = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        cboThuongHieu = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        cboDanhMuc = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        txtMaModelSanPham = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        txtSoLuongSanPham = new javax.swing.JTextField();
        btnTru = new javax.swing.JToggleButton();
        btnCong = new javax.swing.JToggleButton();
        jPanel16 = new javax.swing.JPanel();
        txtThoiGianBaoHanh = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMoTaSanPham = new javax.swing.JTextArea();
        btnThemSanPham = new javax.swing.JButton();
        btnXoaSanPham = new javax.swing.JButton();
        btnSuaSanPham = new javax.swing.JButton();
        btnFirstSanPham = new javax.swing.JButton();
        btnPrevSanPham = new javax.swing.JButton();
        btnNextSanPham = new javax.swing.JButton();
        btnLastSanPham = new javax.swing.JButton();
        btnTimKiemSanPham = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        txtTimKiemSanPham = new javax.swing.JTextField();
        lblAnhSanPham = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnThemNCC = new javax.swing.JButton();
        btnXoaNCC = new javax.swing.JButton();
        jPanel27 = new javax.swing.JPanel();
        txtMaNCC = new javax.swing.JTextField();
        btnSuaNCC = new javax.swing.JButton();
        btnFirstNCC = new javax.swing.JButton();
        jPanel28 = new javax.swing.JPanel();
        txtDiaChiNCC = new javax.swing.JTextField();
        btnPrevNCC = new javax.swing.JButton();
        btnNextNCC = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtGhiChuNCC = new javax.swing.JTextArea();
        btnLastNCC = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblNCC = new javax.swing.JTable();
        jPanel34 = new javax.swing.JPanel();
        txtTenNCC = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        txtSDTNCC = new javax.swing.JTextField();
        jPanel36 = new javax.swing.JPanel();
        txtMaSoThueNCC = new javax.swing.JTextField();
        jPanel40 = new javax.swing.JPanel();
        txtTimKiemNCC = new javax.swing.JTextField();
        btnTimKiemNCC = new javax.swing.JButton();
        btnNewNCC = new javax.swing.JButton();
        pnlThuongHieu = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        txtMaThuongHieu = new javax.swing.JTextField();
        jPanel24 = new javax.swing.JPanel();
        txtTenThuongHieu = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtGhiChuThuongHieu = new javax.swing.JTextArea();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblThuongHieu = new javax.swing.JTable();
        btnThemThuongHieu = new javax.swing.JButton();
        btnXoaThuongHieu = new javax.swing.JButton();
        btnSuaThuongHieu = new javax.swing.JButton();
        btnFirstThuongHieu = new javax.swing.JButton();
        btnPrevThuongHieu = new javax.swing.JButton();
        btnNextThuongHieu = new javax.swing.JButton();
        btnLastThuongHieu = new javax.swing.JButton();
        jPanel39 = new javax.swing.JPanel();
        txtTimKiemThuongHieu = new javax.swing.JTextField();
        btnTimKiemThuongHieu = new javax.swing.JButton();
        btnNewThuongHieu = new javax.swing.JButton();
        lblAnhThuongHieu = new javax.swing.JLabel();
        pnlDanhMuc = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        txtMaDanhMuc = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        txtTenDanhMuc = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        txtGhiChuDanhMuc = new javax.swing.JTextArea();
        jScrollPane10 = new javax.swing.JScrollPane();
        tblDanhMuc = new javax.swing.JTable();
        btnThemDanhMuc = new javax.swing.JButton();
        btnXoaDanhMuc = new javax.swing.JButton();
        btnSuaDanhMuc = new javax.swing.JButton();
        btnFirstDanhMuc = new javax.swing.JButton();
        btnPrevDanhMuc = new javax.swing.JButton();
        btnNextDanhMuc = new javax.swing.JButton();
        btnLastDanhMuc = new javax.swing.JButton();
        jPanel38 = new javax.swing.JPanel();
        txtTimKiemDanhMuc = new javax.swing.JTextField();
        btnTimKiemDanhMuc = new javax.swing.JButton();
        btnNewDanhMuc = new javax.swing.JButton();

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        setTitle("Kho Hàng & Sản Phẩm");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tabs.setBackground(new java.awt.Color(0, 102, 204));
        tabs.setForeground(new java.awt.Color(255, 255, 255));
        tabs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Phiếu Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaPhieuNhap.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaPhieuNhap.setForeground(new java.awt.Color(0, 0, 0));
        txtMaPhieuNhap.setBorder(null);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên Nhà Cung Cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        cbbTenNCC.setBackground(new java.awt.Color(255, 255, 255));
        cbbTenNCC.setForeground(new java.awt.Color(0, 0, 0));
        cbbTenNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cbbTenNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbbTenNCC, 0, 370, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cbbTenNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ngày Tạo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtNgayTaoPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayTaoPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNgayTaoPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNgayTaoPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        tblChiTietPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
        tblChiTietPhieuNhap.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblChiTietPhieuNhap.setForeground(new java.awt.Color(0, 0, 0));
        tblChiTietPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Sản Phẩm", "Đơn Vị Tính", "Số Lượng", "Đơn Giá", "Thành Tiền"
            }
        ));
        tblChiTietPhieuNhap.setFocusable(false);
        tblChiTietPhieuNhap.setGridColor(new java.awt.Color(246, 246, 246));
        tblChiTietPhieuNhap.setRowHeight(25);
        tblChiTietPhieuNhap.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblChiTietPhieuNhap.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblChiTietPhieuNhap.setShowGrid(false);
        tblChiTietPhieuNhap.setShowHorizontalLines(true);
        tblChiTietPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblChiTietPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblChiTietPhieuNhap);
        if (tblChiTietPhieuNhap.getColumnModel().getColumnCount() > 0) {
            tblChiTietPhieuNhap.getColumnModel().getColumn(1).setMinWidth(100);
            tblChiTietPhieuNhap.getColumnModel().getColumn(1).setMaxWidth(100);
            tblChiTietPhieuNhap.getColumnModel().getColumn(2).setMinWidth(60);
            tblChiTietPhieuNhap.getColumnModel().getColumn(2).setMaxWidth(60);
        }

        btnThemPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnThemPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnThemPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemPhieuNhap.setText("Phiếu Nhập");
        btnThemPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhieuNhapActionPerformed(evt);
            }
        });

        btnXoaPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaPhieuNhap.setText("Phiếu Nhập");
        btnXoaPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhieuNhapActionPerformed(evt);
            }
        });

        btnSuaPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaPhieuNhap.setText("Phiếu Nhập");
        btnSuaPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaPhieuNhapActionPerformed(evt);
            }
        });

        tblPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
        tblPhieuNhap.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblPhieuNhap.setForeground(new java.awt.Color(0, 0, 0));
        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Phiếu Nhập", "Tên Nhà Cung Cấp", "Ngày Tạo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPhieuNhap.setFocusable(false);
        tblPhieuNhap.setGridColor(new java.awt.Color(246, 246, 246));
        tblPhieuNhap.setRowHeight(25);
        tblPhieuNhap.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblPhieuNhap.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblPhieuNhap.setShowGrid(false);
        tblPhieuNhap.setShowHorizontalLines(true);
        tblPhieuNhap.setShowVerticalLines(false);
        tblPhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPhieuNhapMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblPhieuNhap);

        btnFirstPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstPhieuNhapActionPerformed(evt);
            }
        });

        btnPrevPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevPhieuNhapActionPerformed(evt);
            }
        });

        btnNextPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnNextPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextPhieuNhapActionPerformed(evt);
            }
        });

        btnLastPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnLastPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastPhieuNhapActionPerformed(evt);
            }
        });

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel41.setForeground(new java.awt.Color(0, 0, 0));

        txtTimKiemPhieuNhap.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemPhieuNhap.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiemPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addGap(0, 21, Short.MAX_VALUE)
                .addComponent(txtTimKiemPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnTimKiemPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemPhieuNhapActionPerformed(evt);
            }
        });

        btnDeleteRow.setBackground(new java.awt.Color(102, 102, 102));
        btnDeleteRow.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteRow.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Erase.png"))); // NOI18N
        btnDeleteRow.setText("Xóa Hàng Hóa");
        btnDeleteRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnDeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRowActionPerformed(evt);
            }
        });

        btnNewRow.setBackground(new java.awt.Color(102, 102, 102));
        btnNewRow.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewRow.setForeground(new java.awt.Color(255, 255, 255));
        btnNewRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnNewRow.setText("Thêm Dòng Mới");
        btnNewRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewRowActionPerformed(evt);
            }
        });

        btnEditRow.setBackground(new java.awt.Color(102, 102, 102));
        btnEditRow.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditRow.setForeground(new java.awt.Color(255, 255, 255));
        btnEditRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnEditRow.setText("Cập Nhật Hàng Hóa");
        btnEditRow.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnEditRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditRowActionPerformed(evt);
            }
        });

        btnNewPhieuNhap.setBackground(new java.awt.Color(19, 144, 234));
        btnNewPhieuNhap.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewPhieuNhap.setForeground(new java.awt.Color(255, 255, 255));
        btnNewPhieuNhap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Copy.png"))); // NOI18N
        btnNewPhieuNhap.setText("Mới");
        btnNewPhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewPhieuNhap.setPreferredSize(new java.awt.Dimension(85, 18));
        btnNewPhieuNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewPhieuNhapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(btnThemPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(btnXoaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(btnSuaPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)
                                .addComponent(btnNewPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 680, Short.MAX_VALUE)
                                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnTimKiemPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnNewRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDeleteRow, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnEditRow, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(594, 594, 594)
                        .addComponent(btnFirstPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLastPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(977, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnNewRow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(btnDeleteRow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(btnEditRow, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoaPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuaPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNewPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiemPhieuNhap, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jPanel41, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnThemPhieuNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrevPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastPhieuNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        tabs.addTab("Kho Hàng", jPanel3);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)), "Mã Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên Sản Phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtTenSanPham.setBorder(null);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTenSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Giá Bán", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtGiaBanSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaBanSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtGiaBanSanPham.setBorder(null);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtGiaBanSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtGiaBanSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 0, 0)), "Mã Phiếu Nhập", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaPhieuNhapSP.setBackground(new java.awt.Color(255, 255, 255));
        txtMaPhieuNhapSP.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtMaPhieuNhapSP.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaPhieuNhapSP, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaPhieuNhapSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Thương Hiệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        cboThuongHieu.setBackground(new java.awt.Color(255, 255, 255));
        cboThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        cboThuongHieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        cboThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboThuongHieu, 0, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(cboThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        cboDanhMuc.setBackground(new java.awt.Color(255, 255, 255));
        cboDanhMuc.setForeground(new java.awt.Color(0, 0, 0));
        cboDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDanhMucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboDanhMuc, 0, 228, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(cboDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Model", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaModelSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaModelSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtMaModelSanPham.setBorder(null);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaModelSanPham)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaModelSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Số Lượng", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtSoLuongSanPham.setBackground(new java.awt.Color(255, 255, 255));
        txtSoLuongSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoLuongSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtSoLuongSanPham.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSoLuongSanPham.setText("1");
        txtSoLuongSanPham.setBorder(null);

        btnTru.setBackground(new java.awt.Color(255, 255, 255));
        btnTru.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnTru.setForeground(new java.awt.Color(19, 144, 234));
        btnTru.setText("-");
        btnTru.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTru.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruActionPerformed(evt);
            }
        });

        btnCong.setBackground(new java.awt.Color(255, 255, 255));
        btnCong.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCong.setForeground(new java.awt.Color(19, 144, 234));
        btnCong.setText("+");
        btnCong.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnCong.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addComponent(btnTru, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSoLuongSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCong, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSoLuongSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCong)
                    .addComponent(btnTru))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "T.Gian Bảo Hành (Tháng)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtThoiGianBaoHanh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtThoiGianBaoHanh.setForeground(new java.awt.Color(0, 0, 0));
        txtThoiGianBaoHanh.setBorder(null);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtThoiGianBaoHanh, javax.swing.GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtThoiGianBaoHanh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        tblSanPham.setBackground(new java.awt.Color(255, 255, 255));
        tblSanPham.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblSanPham.setForeground(new java.awt.Color(0, 0, 0));
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Mã Model", "Số Lượng", "Giá Bán", "Danh Mục"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.setFocusable(false);
        tblSanPham.setGridColor(new java.awt.Color(246, 246, 246));
        tblSanPham.setRowHeight(25);
        tblSanPham.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblSanPham.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblSanPham.setShowGrid(false);
        tblSanPham.setShowHorizontalLines(true);
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);
        if (tblSanPham.getColumnModel().getColumnCount() > 0) {
            tblSanPham.getColumnModel().getColumn(0).setMinWidth(80);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(80);
            tblSanPham.getColumnModel().getColumn(1).setMinWidth(550);
            tblSanPham.getColumnModel().getColumn(1).setMaxWidth(550);
            tblSanPham.getColumnModel().getColumn(2).setMinWidth(100);
            tblSanPham.getColumnModel().getColumn(2).setMaxWidth(100);
            tblSanPham.getColumnModel().getColumn(3).setMinWidth(60);
            tblSanPham.getColumnModel().getColumn(3).setMaxWidth(60);
            tblSanPham.getColumnModel().getColumn(5).setMinWidth(70);
            tblSanPham.getColumnModel().getColumn(5).setMaxWidth(70);
        }

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Mô Tả", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMoTaSanPham.setColumns(20);
        txtMoTaSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtMoTaSanPham.setRows(5);
        txtMoTaSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane3.setViewportView(txtMoTaSanPham);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );

        btnThemSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnThemSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemSanPham.setText("Thêm");
        btnThemSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSanPhamActionPerformed(evt);
            }
        });

        btnXoaSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaSanPham.setText("Xóa");
        btnXoaSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaSanPhamActionPerformed(evt);
            }
        });

        btnSuaSanPham.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaSanPham.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaSanPham.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaSanPham.setText("Sửa");
        btnSuaSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaSanPhamActionPerformed(evt);
            }
        });

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

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTimKiemSanPham.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemSanPham.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiemSanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemSanPham, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        lblAnhSanPham.setBackground(new java.awt.Color(204, 204, 204));
        lblAnhSanPham.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAnhSanPham.setForeground(new java.awt.Color(0, 0, 0));
        lblAnhSanPham.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhSanPham.setText("Bấm vào đây để tải ảnh lên!");
        lblAnhSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnhSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhSanPhamMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblAnhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(18, 18, 18)
                                        .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(656, 656, 656)
                                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(btnTimKiemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(554, 554, 554)
                        .addComponent(btnFirstSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLastSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(431, 1200, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblAnhSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThemSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSuaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnTimKiemSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel37, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrevSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82))
        );

        tabs.addTab("Sản Phẩm", jPanel1);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnThemNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnThemNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemNCC.setText("Thêm");
        btnThemNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNCCActionPerformed(evt);
            }
        });

        btnXoaNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaNCC.setText("Xóa");
        btnXoaNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNCCActionPerformed(evt);
            }
        });

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Nhà Cung Cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaNCC.setForeground(new java.awt.Color(0, 0, 0));
        txtMaNCC.setBorder(null);

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        btnSuaNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaNCC.setText("Sửa");
        btnSuaNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaNCCActionPerformed(evt);
            }
        });

        btnFirstNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstNCCActionPerformed(evt);
            }
        });

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Địa Chỉ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtDiaChiNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiaChiNCC.setForeground(new java.awt.Color(0, 0, 0));
        txtDiaChiNCC.setBorder(null);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtDiaChiNCC)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtDiaChiNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        btnPrevNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevNCCActionPerformed(evt);
            }
        });

        btnNextNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnNextNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextNCCActionPerformed(evt);
            }
        });

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Ghi Chú", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtGhiChuNCC.setColumns(20);
        txtGhiChuNCC.setRows(5);
        txtGhiChuNCC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane7.setViewportView(txtGhiChuNCC);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
        );

        btnLastNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnLastNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastNCCActionPerformed(evt);
            }
        });

        tblNCC.setBackground(new java.awt.Color(255, 255, 255));
        tblNCC.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblNCC.setForeground(new java.awt.Color(0, 0, 0));
        tblNCC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhà Cung Cấp", "Tên  Nhà Cung Cấp", "Địa Chỉ", "Số Điện Thoại", "Mã Số Thuế"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNCC.setFocusable(false);
        tblNCC.setGridColor(new java.awt.Color(246, 246, 246));
        tblNCC.setRowHeight(25);
        tblNCC.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblNCC.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblNCC.setShowGrid(false);
        tblNCC.setShowHorizontalLines(true);
        tblNCC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNCCMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(tblNCC);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên Nhà Cung Cấp", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTenNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenNCC.setForeground(new java.awt.Color(0, 0, 0));
        txtTenNCC.setBorder(null);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTenNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "SĐT", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtSDTNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSDTNCC.setForeground(new java.awt.Color(0, 0, 0));
        txtSDTNCC.setBorder(null);

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtSDTNCC)
                .addContainerGap())
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSDTNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Số Thuế", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaSoThueNCC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaSoThueNCC.setForeground(new java.awt.Color(0, 0, 0));
        txtMaSoThueNCC.setBorder(null);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaSoThueNCC, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaSoThueNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel40.setForeground(new java.awt.Color(0, 0, 0));

        txtTimKiemNCC.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(txtTimKiemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemNCC, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        btnTimKiemNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemNCCActionPerformed(evt);
            }
        });

        btnNewNCC.setBackground(new java.awt.Color(19, 144, 234));
        btnNewNCC.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewNCC.setForeground(new java.awt.Color(255, 255, 255));
        btnNewNCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Copy.png"))); // NOI18N
        btnNewNCC.setText("Mới");
        btnNewNCC.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewNCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewNCCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 1620, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20)
                                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(20, 20, 20))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(18, 18, 18)))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jPanel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnSuaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnXoaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnNewNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel40, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnTimKiemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(685, 685, 685)
                        .addComponent(btnFirstNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLastNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(938, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnTimKiemNCC, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                        .addComponent(jPanel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThemNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSuaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoaNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrevNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLastNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62))
        );

        tabs.addTab("Nhà Cung Cấp", jPanel4);

        pnlThuongHieu.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Thương Hiệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        txtMaThuongHieu.setBorder(null);
        txtMaThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaThuongHieuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaThuongHieu, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên Thương Hiệu", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTenThuongHieu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        txtTenThuongHieu.setBorder(null);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTenThuongHieu)
                .addContainerGap())
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Ghi Chú", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtGhiChuThuongHieu.setColumns(20);
        txtGhiChuThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        txtGhiChuThuongHieu.setRows(5);
        txtGhiChuThuongHieu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane5.setViewportView(txtGhiChuThuongHieu);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );

        tblThuongHieu.setBackground(new java.awt.Color(255, 255, 255));
        tblThuongHieu.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        tblThuongHieu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Thương Hiệu", "Tên Thương Hiệu"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThuongHieu.setFocusable(false);
        tblThuongHieu.setGridColor(new java.awt.Color(246, 246, 246));
        tblThuongHieu.setRowHeight(25);
        tblThuongHieu.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblThuongHieu.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblThuongHieu.setShowGrid(true);
        tblThuongHieu.setShowVerticalLines(false);
        tblThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThuongHieuMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tblThuongHieu);

        btnThemThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnThemThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnThemThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemThuongHieu.setText("Thêm");
        btnThemThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemThuongHieuActionPerformed(evt);
            }
        });

        btnXoaThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaThuongHieu.setText("Xóa");
        btnXoaThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaThuongHieuActionPerformed(evt);
            }
        });

        btnSuaThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaThuongHieu.setText("Sửa");
        btnSuaThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaThuongHieuActionPerformed(evt);
            }
        });

        btnFirstThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnFirstThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstThuongHieuActionPerformed(evt);
            }
        });

        btnPrevThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnPrevThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevThuongHieuActionPerformed(evt);
            }
        });

        btnNextThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnNextThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnNextThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextThuongHieuActionPerformed(evt);
            }
        });

        btnLastThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnLastThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnLastThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastThuongHieuActionPerformed(evt);
            }
        });

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTimKiemThuongHieu.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiemThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel39Layout.createSequentialGroup()
                .addGap(0, 175, Short.MAX_VALUE)
                .addComponent(txtTimKiemThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        btnTimKiemThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemThuongHieuActionPerformed(evt);
            }
        });

        btnNewThuongHieu.setBackground(new java.awt.Color(19, 144, 234));
        btnNewThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewThuongHieu.setForeground(new java.awt.Color(255, 255, 255));
        btnNewThuongHieu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Copy.png"))); // NOI18N
        btnNewThuongHieu.setText("Mới");
        btnNewThuongHieu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewThuongHieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewThuongHieuActionPerformed(evt);
            }
        });

        lblAnhThuongHieu.setBackground(new java.awt.Color(204, 204, 204));
        lblAnhThuongHieu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblAnhThuongHieu.setForeground(new java.awt.Color(0, 0, 0));
        lblAnhThuongHieu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnhThuongHieu.setText("Bấm vào đây để tải ảnh lên!");
        lblAnhThuongHieu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        lblAnhThuongHieu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhThuongHieuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiemThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(lblAnhThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88)
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnThemThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnSuaThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnXoaThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnNewThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(btnFirstThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnPrevThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnNextThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnLastThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(533, 533, 533))
                    .addComponent(jScrollPane6))
                .addContainerGap(949, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblAnhThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFirstThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnPrevThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNextThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLastThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(btnThemThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSuaThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNewThuongHieu, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel39, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemThuongHieu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlThuongHieuLayout = new javax.swing.GroupLayout(pnlThuongHieu);
        pnlThuongHieu.setLayout(pnlThuongHieuLayout);
        pnlThuongHieuLayout.setHorizontalGroup(
            pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2578, Short.MAX_VALUE)
            .addGroup(pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlThuongHieuLayout.setVerticalGroup(
            pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
            .addGroup(pnlThuongHieuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlThuongHieuLayout.createSequentialGroup()
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 130, Short.MAX_VALUE)))
        );

        tabs.addTab("Thương Hiệu", pnlThuongHieu);

        pnlDanhMuc.setBackground(new java.awt.Color(255, 255, 255));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Mã Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtMaDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaDanhMuc.setForeground(new java.awt.Color(0, 0, 0));
        txtMaDanhMuc.setBorder(null);

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaDanhMuc)
                .addContainerGap())
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtMaDanhMuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Tên Danh Mục", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTenDanhMuc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDanhMuc.setForeground(new java.awt.Color(0, 0, 0));
        txtTenDanhMuc.setBorder(null);
        txtTenDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenDanhMucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTenDanhMuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "Ghi Chú", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtGhiChuDanhMuc.setColumns(20);
        txtGhiChuDanhMuc.setForeground(new java.awt.Color(0, 0, 0));
        txtGhiChuDanhMuc.setRows(5);
        txtGhiChuDanhMuc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane9.setViewportView(txtGhiChuDanhMuc);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 278, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
        );

        tblDanhMuc.setBackground(new java.awt.Color(255, 255, 255));
        tblDanhMuc.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblDanhMuc.setForeground(new java.awt.Color(0, 0, 0));
        tblDanhMuc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Danh Mục", "Tên Danh Mục"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDanhMuc.setFocusable(false);
        tblDanhMuc.setGridColor(new java.awt.Color(246, 246, 246));
        tblDanhMuc.setRowHeight(25);
        tblDanhMuc.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblDanhMuc.setSelectionForeground(new java.awt.Color(0, 0, 0));
        tblDanhMuc.setShowGrid(false);
        tblDanhMuc.setShowHorizontalLines(true);
        tblDanhMuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhMucMouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tblDanhMuc);

        btnThemDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnThemDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnThemDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        btnThemDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Add.png"))); // NOI18N
        btnThemDanhMuc.setText("Thêm");
        btnThemDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThemDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemDanhMucActionPerformed(evt);
            }
        });

        btnXoaDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnXoaDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXoaDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Delete.png"))); // NOI18N
        btnXoaDanhMuc.setText("Xóa");
        btnXoaDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnXoaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaDanhMucActionPerformed(evt);
            }
        });

        btnSuaDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnSuaDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSuaDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        btnSuaDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Edit.png"))); // NOI18N
        btnSuaDanhMuc.setText("Sửa");
        btnSuaDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnSuaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaDanhMucActionPerformed(evt);
            }
        });

        btnFirstDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnFirstDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFirstDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Skip to Start.png"))); // NOI18N
        btnFirstDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnFirstDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstDanhMucActionPerformed(evt);
            }
        });

        btnPrevDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnPrevDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnPrevDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Rewind.png"))); // NOI18N
        btnPrevDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnPrevDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevDanhMucActionPerformed(evt);
            }
        });

        btnNextDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnNextDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNextDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Fast Forward.png"))); // NOI18N
        btnNextDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNextDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextDanhMucActionPerformed(evt);
            }
        });

        btnLastDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnLastDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLastDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/End.png"))); // NOI18N
        btnLastDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnLastDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastDanhMucActionPerformed(evt);
            }
        });

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        txtTimKiemDanhMuc.setBackground(new java.awt.Color(255, 255, 255));
        txtTimKiemDanhMuc.setForeground(new java.awt.Color(0, 0, 0));
        txtTimKiemDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addGap(0, 20, Short.MAX_VALUE)
                .addComponent(txtTimKiemDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtTimKiemDanhMuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        btnTimKiemDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnTimKiemDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnTimKiemDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Search.png"))); // NOI18N
        btnTimKiemDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnTimKiemDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemDanhMucActionPerformed(evt);
            }
        });

        btnNewDanhMuc.setBackground(new java.awt.Color(19, 144, 234));
        btnNewDanhMuc.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnNewDanhMuc.setForeground(new java.awt.Color(255, 255, 255));
        btnNewDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/New Copy.png"))); // NOI18N
        btnNewDanhMuc.setText("Mới");
        btnNewDanhMuc.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnNewDanhMuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewDanhMucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                        .addComponent(btnFirstDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPrevDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNextDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLastDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnTimKiemDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel32, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThemDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSuaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNewDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1842, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFirstDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrevDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNextDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLastDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiemDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(btnThemDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnSuaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnXoaDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(btnNewDanhMuc, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 639, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlDanhMucLayout = new javax.swing.GroupLayout(pnlDanhMuc);
        pnlDanhMuc.setLayout(pnlDanhMucLayout);
        pnlDanhMucLayout.setHorizontalGroup(
            pnlDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2578, Short.MAX_VALUE)
            .addGroup(pnlDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDanhMucLayout.setVerticalGroup(
            pnlDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
            .addGroup(pnlDanhMucLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Danh Mục", pnlDanhMuc);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaThuongHieuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaThuongHieuActionPerformed

    private void txtTenDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenDanhMucActionPerformed

    private void btnDeleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRowActionPerformed
        int row = tblChiTietPhieuNhap.getSelectedRow();
        if(row < 0) {
            MsgBox.alert(null, "<html><font color='red'>Chưa chọn dòng cần xóa!</font></html>");
        } else {
            if (MsgBox.confirm(null, "Xóa dòng này?")) {
                delRow(row);
            }
        }
        
    }//GEN-LAST:event_btnDeleteRowActionPerformed

    private void btnNewRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewRowActionPerformed
        newRow();
    }//GEN-LAST:event_btnNewRowActionPerformed

    private void tblDanhMucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhMucMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowdm = tblDanhMuc.getSelectedRow();
            this.editDanhMuc();
        }
    }//GEN-LAST:event_tblDanhMucMouseClicked

    private boolean isValidDanhMucForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaDanhMuc.getText().isEmpty()) {
            strb.append("Mã danh mục không được để trống!<br>");
        }
        
        if(txtTenDanhMuc.getText().isEmpty()) {
            strb.append("Tên danh mục không được để trống!<br>");
        }
        
        if(strb.length() > 0) {
            MsgBox.alert(null, "<html><font color='red'>"+strb+"</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemDanhMucActionPerformed
        if(isValidDanhMucForm()) {
           if(danhmucDAO.selectByID(txtMaDanhMuc.getText()) != null) {
               MsgBox.alert(null, "<html><font color='red'>Mã danh mục đã tồn tại!</font></html>");
               return;
           }
           insertDanhMuc();
           init();
        }
        
    }//GEN-LAST:event_btnThemDanhMucActionPerformed

    private void btnSuaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaDanhMucActionPerformed
        if(isValidDanhMucForm()) {
            updateDanhMuc();
        }
    }//GEN-LAST:event_btnSuaDanhMucActionPerformed

    private void btnXoaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaDanhMucActionPerformed
        if(isValidDanhMucForm()) {
            if (MsgBox.confirm(null, "Xóa danh mục này?\nĐiều này sẽ dẫn đến xóa các sản phẩm liên quan!")) {
                deleteDanhMuc();
                init();
            }
        }
    }//GEN-LAST:event_btnXoaDanhMucActionPerformed

    private void btnFirstDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstDanhMucActionPerformed
        firstDanhMuc();
    }//GEN-LAST:event_btnFirstDanhMucActionPerformed

    private void btnPrevDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevDanhMucActionPerformed
        prevDanhMuc();
    }//GEN-LAST:event_btnPrevDanhMucActionPerformed

    private void btnNextDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDanhMucActionPerformed
        nextDanhMuc();
    }//GEN-LAST:event_btnNextDanhMucActionPerformed

    private void btnLastDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastDanhMucActionPerformed
        lastDanhMuc();
    }//GEN-LAST:event_btnLastDanhMucActionPerformed

    private void btnNewDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewDanhMucActionPerformed
        clearFormDanhMuc();
    }//GEN-LAST:event_btnNewDanhMucActionPerformed

    private void btnTimKiemDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemDanhMucActionPerformed
        timKiemDanhMuc();
    }//GEN-LAST:event_btnTimKiemDanhMucActionPerformed

    private void btnNewThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewThuongHieuActionPerformed
        clearFormThuongHieu();
    }//GEN-LAST:event_btnNewThuongHieuActionPerformed

    private boolean isValidThuongHieuForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaThuongHieu.getText().isEmpty()) {
            strb.append("Mã thương hiệu không được để trống!<br>");
        }
        
        if(txtTenThuongHieu.getText().isEmpty()) {
            strb.append("Tên thương hiệu không được để trống!<br>");
        }
        
        if(strb.length() > 0) {
            MsgBox.alert(null, "<html><font color='red'>"+strb+"</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemThuongHieuActionPerformed
        if(isValidThuongHieuForm()) {
            if (thuonghieuDAO.selectByID(txtMaThuongHieu.getText()) != null) {
                MsgBox.alert(null, "<html><font color='red'>Mã thương hiệu đã tồn tại!</font></html>");
                return;
            }
            insertThuongHieu();
            init();
        }
    }//GEN-LAST:event_btnThemThuongHieuActionPerformed

    private void btnSuaThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaThuongHieuActionPerformed
        if(isValidThuongHieuForm()) {
            updateThuongHieu();
        }
    }//GEN-LAST:event_btnSuaThuongHieuActionPerformed

    private void btnXoaThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaThuongHieuActionPerformed
        if(isValidThuongHieuForm()) {
            if (MsgBox.confirm(null, "Xóa thương hiệu này?\nĐiều này sẽ dẫn đến xóa các sản phẩm liên quan!")) {
                deleteThuongHieu();
                init();
            }
        }
    }//GEN-LAST:event_btnXoaThuongHieuActionPerformed

    private void btnFirstThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstThuongHieuActionPerformed
        firstThuongHieu();
    }//GEN-LAST:event_btnFirstThuongHieuActionPerformed

    private void btnPrevThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevThuongHieuActionPerformed
        prevThuongHieu();
    }//GEN-LAST:event_btnPrevThuongHieuActionPerformed

    private void btnNextThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextThuongHieuActionPerformed
        nextThuongHieu();
    }//GEN-LAST:event_btnNextThuongHieuActionPerformed

    private void btnLastThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastThuongHieuActionPerformed
        lastThuongHieu();
    }//GEN-LAST:event_btnLastThuongHieuActionPerformed

    private void btnTimKiemThuongHieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemThuongHieuActionPerformed
        timKiemThuongHieu();
    }//GEN-LAST:event_btnTimKiemThuongHieuActionPerformed

    private void tblThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThuongHieuMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowth = tblThuongHieu.getSelectedRow();
            this.editThuongHieu();
        }
    }//GEN-LAST:event_tblThuongHieuMouseClicked

    private void lblAnhThuongHieuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhThuongHieuMouseClicked
        chonAnhThuongHieu();
    }//GEN-LAST:event_lblAnhThuongHieuMouseClicked

    private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDanhMucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDanhMucActionPerformed
    
    private boolean isValidNCCForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaNCC.getText().isEmpty()) {
            strb.append("Mã nhà cung cấp không được để trống!<br>");
        }
        
        if(txtTenNCC.getText().isEmpty()) {
            strb.append("Tên nhà cung cấp không được để trống!<br>");
        }
        
        if(txtDiaChiNCC.getText().isEmpty()) {
            strb.append("Địa chỉ nhà cung cấp không được để trống!<br>");
        }
        
        if(txtSDTNCC.getText().isEmpty()) {
            strb.append("Số điện thoại nhà cung cấp không được để trống!<br>");
        }
        
        if(txtMaSoThueNCC.getText().isEmpty()) {
            strb.append("Mã số thuế nhà cung cấp không được để trống!<br>");
        }
        
        if(strb.length() > 0) {
            MsgBox.alert(null, "<html><font color='red'>"+strb+"</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNCCActionPerformed
        if(isValidNCCForm()) {
            if(nccDAO.selectByID(txtMaNCC.getText()) != null) {
                MsgBox.alert(null, "<html><font color='red'>Mã nhà cung cấp đã tồn tại!</font></html>");
                return;
            }
            insertNhaCungCap();
            init();
        }
    }//GEN-LAST:event_btnThemNCCActionPerformed

    private void btnSuaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaNCCActionPerformed
        if(isValidNCCForm()) {
            updateNhaCungCap();
        }
    }//GEN-LAST:event_btnSuaNCCActionPerformed

    private void btnXoaNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNCCActionPerformed
        if(isValidNCCForm()) {
            if (MsgBox.confirm(null, "Xóa nhà cung cấp này?\nĐiều này sẽ dấn đến xóa các sản phẩm liên quan!")) {
                deleteNhaCungCap();
                init();
            }
        }
    }//GEN-LAST:event_btnXoaNCCActionPerformed

    private void btnNewNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewNCCActionPerformed
        clearFormNhaCungCap();
    }//GEN-LAST:event_btnNewNCCActionPerformed

    private void btnTimKiemNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemNCCActionPerformed
        timKiemNhaCungCap();
    }//GEN-LAST:event_btnTimKiemNCCActionPerformed

    private void tblNCCMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNCCMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowncc = tblNCC.getSelectedRow();
            this.editNhaCungCap();
        }
    }//GEN-LAST:event_tblNCCMouseClicked

    private void btnFirstNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstNCCActionPerformed
        firstNhaCungCap();
    }//GEN-LAST:event_btnFirstNCCActionPerformed

    private void btnPrevNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevNCCActionPerformed
        prevNhaCungCap();
    }//GEN-LAST:event_btnPrevNCCActionPerformed

    private void btnNextNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextNCCActionPerformed
        nextNhaCungCap();
    }//GEN-LAST:event_btnNextNCCActionPerformed

    private void btnLastNCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastNCCActionPerformed
        lastNhaCungCap();
    }//GEN-LAST:event_btnLastNCCActionPerformed

    private void btnEditRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditRowActionPerformed
        if(tblChiTietPhieuNhap.getRowCount() <= 0) {
            MsgBox.alert(null, "Không có dữ liệu để cập nhật!");
            return;
        }
        dmm();
    }//GEN-LAST:event_btnEditRowActionPerformed

    private void btnTimKiemPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemPhieuNhapActionPerformed
        timKiemPhieuNhap();
    }//GEN-LAST:event_btnTimKiemPhieuNhapActionPerformed

    private void tblPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPhieuNhapMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowpn = tblPhieuNhap.getSelectedRow();
            this.editPhieuNhap();
        }
    }//GEN-LAST:event_tblPhieuNhapMouseClicked

    private boolean isValidPhieuNhapForm() {
        StringBuilder strb = new StringBuilder();
        if (txtMaPhieuNhap.getText().isEmpty()) {
            strb.append("Mã phiếu nhập không được để trống!<br>");
        }
        
        if (txtNgayTaoPhieuNhap.getText().isEmpty()) {
            strb.append("Ngày tạo phiếu nhập không được để trống!<br>");
        } else if(!ValidationForm.isValidDateFormat(txtNgayTaoPhieuNhap.getText())) {
            strb.append("Ngày tạo phiếu nhập không đúng định dạng dd/MM/yyyy!<br>");
        }

        if (strb.length() > 0) {
            MsgBox.alert(null, "<html><font color='red'>" + strb + "</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhieuNhapActionPerformed
        if(isValidPhieuNhapForm()) {
            if (pnDAO.selectByID(txtMaPhieuNhap.getText()) != null) {
                MsgBox.alert(null, "<html><font color='red'>Mã phiếu nhập đã tồn tại!</font></html>");
                return;
            }
            insertPhieuNhap();
            init();
        }
    }//GEN-LAST:event_btnThemPhieuNhapActionPerformed

    private void btnNewPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewPhieuNhapActionPerformed
        clearFormPhieuNhap();
    }//GEN-LAST:event_btnNewPhieuNhapActionPerformed

    private void btnXoaPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhieuNhapActionPerformed
        if(isValidPhieuNhapForm()) {
            if (MsgBox.confirm(null, "Xóa phiếu nhập này?\nĐiều này sẽ dẫn đến xóa các sản phẩm liên quan!")) {
                deletePhieuNhap();
                init();
            }
        }
    }//GEN-LAST:event_btnXoaPhieuNhapActionPerformed

    private void btnSuaPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaPhieuNhapActionPerformed
        if(isValidPhieuNhapForm())
            updatePhieuNhap();
    }//GEN-LAST:event_btnSuaPhieuNhapActionPerformed

    private void btnFirstPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstPhieuNhapActionPerformed
        firstPhieuNhap();
    }//GEN-LAST:event_btnFirstPhieuNhapActionPerformed

    private void btnPrevPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevPhieuNhapActionPerformed
        prevPhieuNhap();
    }//GEN-LAST:event_btnPrevPhieuNhapActionPerformed

    private void btnNextPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextPhieuNhapActionPerformed
        nextPhieuNhap();
    }//GEN-LAST:event_btnNextPhieuNhapActionPerformed

    private void btnLastPhieuNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastPhieuNhapActionPerformed
        lastPhieuNhap();
    }//GEN-LAST:event_btnLastPhieuNhapActionPerformed

    private void btnCongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCongActionPerformed
        int solg = Integer.parseInt(txtSoLuongSanPham.getText());
        solg++;
        txtSoLuongSanPham.setText(""+solg);
    }//GEN-LAST:event_btnCongActionPerformed
    int rowctpn = -1;
    private void tblChiTietPhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblChiTietPhieuNhapMouseClicked
        if(evt.getClickCount() == 2) {
            this.rowctpn = tblChiTietPhieuNhap.getSelectedRow();
            this.editChiTietPhieuNhap();
        }
    }//GEN-LAST:event_tblChiTietPhieuNhapMouseClicked

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if(evt.getClickCount() == 1) {
            this.rowsp = tblSanPham.getSelectedRow();
            this.editSanPham();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnFirstSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstSanPhamActionPerformed
        firstSanPham();
    }//GEN-LAST:event_btnFirstSanPhamActionPerformed

    private void btnPrevSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevSanPhamActionPerformed
        prevSanPham();
    }//GEN-LAST:event_btnPrevSanPhamActionPerformed

    private void btnNextSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextSanPhamActionPerformed
        nextSanPham();
    }//GEN-LAST:event_btnNextSanPhamActionPerformed

    private void btnLastSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastSanPhamActionPerformed
        lastSanPham();
    }//GEN-LAST:event_btnLastSanPhamActionPerformed

    private boolean isValidSanPhamForm() {
        StringBuilder strb = new StringBuilder();
        if(txtMaPhieuNhapSP.getText().isEmpty()) {
            strb.append("Mã phiếu nhập không được để trống!<br>");
        }      
        
        if(txtTenSanPham.getText().isEmpty()) {
            strb.append("Tên sản phẩm không được để trống!<br>");
        }
        
        if(txtMaModelSanPham.getText().isEmpty()) {
            strb.append("Mã Model không được để trống!<br>");
        }
        
        if(txtSoLuongSanPham.getText().isEmpty()) {
            strb.append("Số lượng không được để trống!<br>");
        }
        
        if(txtThoiGianBaoHanh.getText().isEmpty()) {
            strb.append("Thời gian bảo hành không được để trống!<br>");
        }
        
        if(txtGiaBanSanPham.getText().isEmpty()) {
            strb.append("Giá bán không được để trống!<br>");
        }
                      
        if(strb.length() > 0) {
            MsgBox.alert(null, "<html><font color='red'>"+strb+"</font></html>");
            return false;
        }
        return true;
    }
    
    private void btnThemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSanPhamActionPerformed
        if (isValidSanPhamForm()) {
            if (spDAO.selectByID(txtTenSanPham.getText()) != null) {
                if (MsgBox.confirm(null, "Sản phầm đã tồn tại\n Bạn có chắc muốn thêm vào?")) {
                    insertSanPham();
                    init();
                    return;
                }
                return;
            }
            insertSanPham();
            init();
        }
    }//GEN-LAST:event_btnThemSanPhamActionPerformed

    private void btnXoaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaSanPhamActionPerformed
        if(isValidSanPhamForm()) {
            if (MsgBox.confirm(null, "Xóa sản phẩm này?\nĐiều này sẽ dẫn đến xóa các sản phẩm liên quan!")) {
                deleteSanPham();
                init();
            }
        }
    }//GEN-LAST:event_btnXoaSanPhamActionPerformed

    private void btnSuaSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaSanPhamActionPerformed
        if(isValidSanPhamForm()) {
            updateSanPham();
        }
    }//GEN-LAST:event_btnSuaSanPhamActionPerformed

    private void btnTimKiemSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSanPhamActionPerformed
        timKiemSanPham();
    }//GEN-LAST:event_btnTimKiemSanPhamActionPerformed

    private void lblAnhSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhSanPhamMouseClicked
        chonAnhSanPham();
    }//GEN-LAST:event_lblAnhSanPhamMouseClicked

    private void btnTruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruActionPerformed
        int solg = Integer.parseInt(txtSoLuongSanPham.getText());
        if(solg == 0) {
            return;
        }
        solg--;
        txtSoLuongSanPham.setText(""+solg);
    }//GEN-LAST:event_btnTruActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnCong;
    private javax.swing.JButton btnDeleteRow;
    private javax.swing.JButton btnEditRow;
    private javax.swing.JButton btnFirstDanhMuc;
    private javax.swing.JButton btnFirstNCC;
    private javax.swing.JButton btnFirstPhieuNhap;
    private javax.swing.JButton btnFirstSanPham;
    private javax.swing.JButton btnFirstThuongHieu;
    private javax.swing.JButton btnLastDanhMuc;
    private javax.swing.JButton btnLastNCC;
    private javax.swing.JButton btnLastPhieuNhap;
    private javax.swing.JButton btnLastSanPham;
    private javax.swing.JButton btnLastThuongHieu;
    private javax.swing.JButton btnNewDanhMuc;
    private javax.swing.JButton btnNewNCC;
    private javax.swing.JButton btnNewPhieuNhap;
    private javax.swing.JButton btnNewRow;
    private javax.swing.JButton btnNewThuongHieu;
    private javax.swing.JButton btnNextDanhMuc;
    private javax.swing.JButton btnNextNCC;
    private javax.swing.JButton btnNextPhieuNhap;
    private javax.swing.JButton btnNextSanPham;
    private javax.swing.JButton btnNextThuongHieu;
    private javax.swing.JButton btnPrevDanhMuc;
    private javax.swing.JButton btnPrevNCC;
    private javax.swing.JButton btnPrevPhieuNhap;
    private javax.swing.JButton btnPrevSanPham;
    private javax.swing.JButton btnPrevThuongHieu;
    private javax.swing.JButton btnSuaDanhMuc;
    private javax.swing.JButton btnSuaNCC;
    private javax.swing.JButton btnSuaPhieuNhap;
    private javax.swing.JButton btnSuaSanPham;
    private javax.swing.JButton btnSuaThuongHieu;
    private javax.swing.JButton btnThemDanhMuc;
    private javax.swing.JButton btnThemNCC;
    private javax.swing.JButton btnThemPhieuNhap;
    private javax.swing.JButton btnThemSanPham;
    private javax.swing.JButton btnThemThuongHieu;
    private javax.swing.JButton btnTimKiemDanhMuc;
    private javax.swing.JButton btnTimKiemNCC;
    private javax.swing.JButton btnTimKiemPhieuNhap;
    private javax.swing.JButton btnTimKiemSanPham;
    private javax.swing.JButton btnTimKiemThuongHieu;
    private javax.swing.JToggleButton btnTru;
    private javax.swing.JButton btnXoaDanhMuc;
    private javax.swing.JButton btnXoaNCC;
    private javax.swing.JButton btnXoaPhieuNhap;
    private javax.swing.JButton btnXoaSanPham;
    private javax.swing.JButton btnXoaThuongHieu;
    private javax.swing.JComboBox<String> cbbTenNCC;
    private javax.swing.JComboBox<String> cboDanhMuc;
    private javax.swing.JComboBox<String> cboThuongHieu;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JLabel lblAnhSanPham;
    private javax.swing.JLabel lblAnhThuongHieu;
    private javax.swing.JPanel pnlDanhMuc;
    private javax.swing.JPanel pnlThuongHieu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblChiTietPhieuNhap;
    private javax.swing.JTable tblDanhMuc;
    private javax.swing.JTable tblNCC;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTable tblThuongHieu;
    private javax.swing.JTextField txtDiaChiNCC;
    private javax.swing.JTextArea txtGhiChuDanhMuc;
    private javax.swing.JTextArea txtGhiChuNCC;
    private javax.swing.JTextArea txtGhiChuThuongHieu;
    private javax.swing.JTextField txtGiaBanSanPham;
    private javax.swing.JTextField txtMaDanhMuc;
    private javax.swing.JTextField txtMaModelSanPham;
    private javax.swing.JTextField txtMaNCC;
    private javax.swing.JTextField txtMaPhieuNhap;
    private javax.swing.JLabel txtMaPhieuNhapSP;
    private javax.swing.JLabel txtMaSanPham;
    private javax.swing.JTextField txtMaSoThueNCC;
    private javax.swing.JTextField txtMaThuongHieu;
    private javax.swing.JTextArea txtMoTaSanPham;
    private javax.swing.JTextField txtNgayTaoPhieuNhap;
    private javax.swing.JTextField txtSDTNCC;
    private javax.swing.JTextField txtSoLuongSanPham;
    private javax.swing.JTextField txtTenDanhMuc;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTenSanPham;
    private javax.swing.JTextField txtTenThuongHieu;
    private javax.swing.JTextField txtThoiGianBaoHanh;
    private javax.swing.JTextField txtTimKiemDanhMuc;
    private javax.swing.JTextField txtTimKiemNCC;
    private javax.swing.JTextField txtTimKiemPhieuNhap;
    private javax.swing.JTextField txtTimKiemSanPham;
    private javax.swing.JTextField txtTimKiemThuongHieu;
    // End of variables declaration//GEN-END:variables
}
