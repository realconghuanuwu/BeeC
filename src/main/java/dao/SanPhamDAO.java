/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.SanPham;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class SanPhamDAO extends BeeCGearDAO<SanPham, Integer>{

    @Override
    public void insert(SanPham entity) {
        String INSERT_SQL = "INSERT INTO SanPham(MaPhieuNhap, TenSanPham, MaModel, MaThuongHieu, MaDanhMuc, GiaBan, MoTa, MaNguoiNhap, Anh, ThoiGianBaoHanh, SoLuong) "
                + "Values(?, ?, ?, ?, ?, ? ,? ,? ,?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaPhieuNhap(), entity.getTenSanPham(), entity.getMaModel(), entity.getMaThuongHieu(), entity.getMaDanhMuc(), entity.getGiaBan(), entity.getMoTa()
        , entity.getMaNguoiNhap(), entity.getAnh(), entity.getThoiGianBaoHanh(), entity.getSoLuong()
        );
    }

    @Override
    public void update(SanPham entity) {
        String UPDATE_SQL = "UPDATE SanPham set TenSanPham = ?, MaModel = ?, MaThuongHieu = ?, MaDanhMuc = ?, GiaBan = ?, MoTa = ?, Anh = ?, ThoiGianBaoHanh = ?, SoLuong = ? WHERE MaSanPham = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getTenSanPham(), entity.getMaModel(), entity.getMaThuongHieu(), entity.getMaDanhMuc(), entity.getGiaBan(), entity.getMoTa(),entity.getAnh() 
                , entity.getThoiGianBaoHanh(), entity.getSoLuong(), entity.getMaSanPham());
    }

    @Override
    public void delete(Integer key) {
        String DELETE_SQL = "DELETE FROM SanPham WHERE MaSanPham = ?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<SanPham> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM SanPham";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public SanPham selectByID(Integer key) {
        String SELECT_BY_ID = "SELECT * FROM SanPham WHERE MaSanPham=?";
        List<SanPham> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    public SanPham selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM SanPham WHERE TenSanPham = ?";
        List<SanPham> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<SanPham> selectBySql(String sql, Object... args) {
        List<SanPham> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                SanPham entity = new SanPham();
                entity.setMaSanPham(rs.getInt("MaSanPham"));
                entity.setMaPhieuNhap(rs.getString("MaPhieuNhap"));
                entity.setTenSanPham(rs.getString("TenSanPham"));
                entity.setMaModel(rs.getString("MaModel"));
                entity.setMaThuongHieu(rs.getString("MaThuongHieu"));
                entity.setMaDanhMuc(rs.getString("MaDanhMuc"));
                entity.setGiaBan(rs.getBigDecimal("GiaBan"));
                entity.setMoTa(rs.getString("MoTa"));
                entity.setMaNguoiNhap(rs.getString("MaNguoiNhap"));
                entity.setNgayNhap(rs.getDate("NgayNhap"));
                entity.setAnh(rs.getString("Anh"));
                entity.setThoiGianBaoHanh(rs.getInt("ThoiGianBaoHanh"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<SanPham> selectByKeyword(String keyword) {
        String sql = "Select * from SanPham where TenSanPham like ? or MaModel like ? or MaDanhMuc like ? or MaThuongHieu like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
    }
    
//    public void updateSoLuong(int soluong, String masp) {
//        String UPDATE_SQL = "UPDATE SanPham set SoLuong = ? WHERE MaSanPham = ?";
//        JdbcHelper.update(UPDATE_SQL, soluong, masp);
//    }
     
}
