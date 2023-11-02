/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.HoaDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class HoaDonDAO extends BeeCGearDAO<HoaDon, Integer>{

    @Override
    public void insert(HoaDon entity) {
        String INSERT_SQL = "INSERT INTO HoaDonThanhToan(MaNguoiTao, MaKhachHang, TrangThaiThanhToan, GhiChu) Values(?, ?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaNguoiTao(), entity.getMaKhachHang(), entity.getTrangThaiThanhToan(),entity.getGhiChu());
        
    }

    @Override
    public void update(HoaDon entity) {
        String UPDATE_SQL = "UPDATE HoaDonThanhToan set MaKhachHang = ?, TrangThaiThanhToan = ?, GhiChu = ?  WHERE MaHoaDon = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getMaKhachHang(),entity.getTrangThaiThanhToan(),entity.getGhiChu(),entity.getMaHoaDon());
    }

    @Override
    public void delete(Integer key) {
        String DELETE_SQL = "DELETE FROM HoaDonThanhToan WHERE MaHoaDon = ?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<HoaDon> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM HoaDonThanhToan";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectByID(Integer key) {
        String SELECT_BY_ID = "SELECT * FROM HoaDonThanhToan WHERE MaHoaDon=?";
        List<HoaDon> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    protected List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHoaDon(rs.getInt("MaHoaDon"));
                entity.setMaKhachHang(rs.getString("MaKhachHang"));
                entity.setMaNguoiTao(rs.getString("MaNguoiTao"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                entity.setTrangThaiThanhToan(rs.getString("TrangThaiThanhToan"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<HoaDon> selectByKeyword(String keyword) {
        String sql = "Select * from HoaDonThanhToan where MaKhachHang like ? or MaNguoiTao like ? or TrangThaiThanhToan like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
    }
    
}
