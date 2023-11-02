/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class ThongKeDAO {
    private List<Object[]> getListOfArray(String sql, String[] cols, Object...args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {            
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = rs.getObject(cols[i]);
                }
                list.add(vals);
                
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Object[]> getBangDiem(Date ngaythangnam, String locTheo) {
        String sql = "{CALL getThongKeNhanVienTheoThoiGian(?,?)}";
        String[] cols = {"MaNhanVien","HovaTen","TongDonHang","TongSoLuong","DonSo"};
        return getListOfArray(sql, cols, ngaythangnam, locTheo);
    };
    
    public List<Object[]> getDonHangThanhToan(Date ngaythangnam, String trangthaithanhtoan, String loctheo) {
        String sql = "{CALL getDonHangDaThanhToan(?,?,?)}";
        String[] cols = {"TongDonHang","TongTien"};
        return getListOfArray(sql, cols, ngaythangnam, trangthaithanhtoan, loctheo);
    };
    
    public List<Object[]> getVonNhapHang(Date ngaythangnam, String locTheo) {
        String sql = "{CALL getVonNhapHang(?,?)}";
        String[] cols = {"SoLuongHangHoa","VonNhapHang"};
        return getListOfArray(sql, cols, ngaythangnam, locTheo);
    };
}
