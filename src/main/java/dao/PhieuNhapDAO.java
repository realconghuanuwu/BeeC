/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.PhieuNhap;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class PhieuNhapDAO extends BeeCGearDAO<PhieuNhap, String>{

    @Override
    public void insert(PhieuNhap entity) {
        String INSERT_SQL = "INSERT INTO PhieuNhapHang(MaPhieuNhap, MaNhaCungCap, NgayTao) Values(?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaPhieuNhap(), entity.getMaNhaCungCap(), entity.getNgayTao());
    }

    @Override
    public void update(PhieuNhap entity) {
        String UPDATE_SQL = "UPDATE PhieuNhapHang set MaNhaCungCap = ?, NgayTao = ? WHERE MaPhieuNhap = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getMaNhaCungCap(), entity.getNgayTao(), entity.getMaPhieuNhap());
    }

    @Override
    public void delete(String key) {
        String DELETE_SQL = "DELETE FROM PhieuNhapHang WHERE MaPhieuNhap=?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<PhieuNhap> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM PhieuNhapHang";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuNhap selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM PhieuNhapHang WHERE MaPhieuNhap=?";
        List<PhieuNhap> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<PhieuNhap> selectBySql(String sql, Object... args) {
        List<PhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                PhieuNhap entity = new PhieuNhap(); 
                entity.setMaPhieuNhap(rs.getString("MaPhieuNhap"));
                entity.setMaNhaCungCap(rs.getString("MaNhaCungCap"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<PhieuNhap> selectByKeyword(String keyword) {
        String sql = "SELECT * From PhieuNhapHang where MaPhieuNhap like ? or MaNhaCungCap like ?";
        
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%");
    }
    
    
}
