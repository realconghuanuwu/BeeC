/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhanVien;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class NhanVienDAO extends BeeCGearDAO<NhanVien, String>{

    @Override
    public void insert(NhanVien entity) {
        String INSERT_SQL = "INSERT INTO NhanVien(MaNhanVien, HovaTen, Email, MatKhau, VaiTro, GioiTinh, DiaChi, SoDienThoai, LichLamViec, Anh, Luong) Values"
                + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaNhanVien(), entity.getHovaTen(), entity.getEmail(), entity.getMatKhau(), entity.getVaiTro(), entity.isGioiTinh()
        ,entity.getDiaChi(), entity.getSoDienThoai(),entity.getLichLamViec(),entity.getAnh(), entity.getLuong());
    }

    @Override
    public void update(NhanVien entity) {
        String UPDATE_SQL = "UPDATE NhanVien set HovaTen = ?, Email = ?, MatKhau = ?, VaiTro = ?, GioiTinh = ?, DiaChi = ?, SoDienThoai = ?, LichLamViec= ?, Anh = ?, Luong = ?"
                + " where MaNhanVien = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getHovaTen(), entity.getEmail(), entity.getMatKhau(), entity.getVaiTro(), entity.isGioiTinh()
        ,entity.getDiaChi(), entity.getSoDienThoai(),entity.getLichLamViec(),entity.getAnh(), entity.getLuong(), entity.getMaNhanVien());
    }

    @Override
    public void delete(String key) {
        String DELETE_SQL = "DELETE FROM NhanVien WHERE MaNhanVien=?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<NhanVien> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM NhanVien";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM NhanVien WHERE MaNhanVien=?";
        List<NhanVien> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNhanVien(rs.getString("MaNhanVien"));
                entity.setHovaTen(rs.getString("HovaTen"));
                entity.setEmail(rs.getString("Email"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setVaiTro(rs.getInt("VaiTro"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setSoDienThoai(rs.getString("SoDienThoai"));
                entity.setLichLamViec(rs.getString("LichLamViec"));
                entity.setAnh(rs.getString("Anh"));
                entity.setLuong(rs.getBigDecimal("Luong"));
                entity.setNgayVaoCuaHang(rs.getDate("NgayVaoCuaHang"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<NhanVien> selectByKeyword(String keyword) {
        String sql = "Select * from NhanVien where HovaTen like ? or MaNhanVien like ? or Email like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
    }
    
}
