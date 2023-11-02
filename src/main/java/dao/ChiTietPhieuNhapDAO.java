/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ChiTietPhieuNhap;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class ChiTietPhieuNhapDAO extends BeeCGearDAO<ChiTietPhieuNhap, String>{

    @Override
    public void insert(ChiTietPhieuNhap entity) {
        String INSERT_SQL = "INSERT INTO ChiTietPhieuNhap(MaPhieuNhap, TenSanPham, DonViTinh, SoLuong, DonGia) Values(?, ?, ?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaPhieuNhap(), entity.getTenSanPham(), entity.getDVT(), entity.getSoLuong(), entity.getDonGia());
    }

    @Override
    public void update(ChiTietPhieuNhap entity) {
        String UPDATE_SQL = "UPDATE ChiTietPhieuNhap set TenSanPham = ?, DonViTinh = ?, SoLuong = ?, DonGia = ? WHERE MaPhieuNhap = ? and TenSanPham = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getTenSanPham(), entity.getDVT(), entity.getSoLuong(), entity.getDonGia(), entity.getMaPhieuNhap(), entity.getTenSanPham());
    }

    public void delete(String mapn, String tensp) {
        String DELETE_SQL = "DELETE FROM ChiTietPhieuNhap WHERE MaPhieuNhap=? and TenSanPham = ?";
        JdbcHelper.update(DELETE_SQL, mapn, tensp);
    }

    @Override
    public List<ChiTietPhieuNhap> selectAll() {
        String SELECT_ALL_SQL = "SELECT * from ChiTietPhieuNhap";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public ChiTietPhieuNhap selectByID(String mapn, String tensp) {
        String SELECT_BY_ID = "SELECT * FROM ChiTietPhieuNhap WHERE MaPhieuNhap=? and TenSanPham = ?";
        List<ChiTietPhieuNhap> list= this.selectBySql(SELECT_BY_ID, mapn, tensp);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ChiTietPhieuNhap> selectBySql(String sql, Object... args) {
        List<ChiTietPhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChiTietPhieuNhap entity = new ChiTietPhieuNhap();
                entity.setMaPhieuNhap(rs.getString("MaPhieuNhap"));
                entity.setTenSanPham(rs.getString("TenSanPham"));
                entity.setDVT(rs.getString("DonViTinh"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setDonGia(rs.getBigDecimal("DonGia"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<ChiTietPhieuNhap> selectByMaPhieuNhap(String MaPhieuNhap) {
        String sql = "SELECT * From ChiTietPhieuNhap where MaPhieuNhap like ?";
        return this.selectBySql(sql, "%"+MaPhieuNhap+"%");
    }

    @Override
    public void delete(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChiTietPhieuNhap selectByID(String key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
