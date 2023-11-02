/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.NhaCungCap;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class NhaCungCapDAO extends BeeCGearDAO<NhaCungCap, String>{

    @Override
    public void insert(NhaCungCap entity) {
        String INSERT_SQL = "INSERT INTO NhaCungCap(MaNhaCungCap, TenNhaCungCap, DiaChi, SoDienThoai, MaSoThue, GhiChu) Values(?, ?, ?, ?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaNCC(), entity.getTenNCC(), entity.getDiaChi(), entity.getSoDienThoai(), entity.getMaSoThue(), entity.getGhiChu());
    }

    @Override
    public void update(NhaCungCap entity) {
        String UPDATE_SQL = "UPDATE NhaCungCap set TenNhaCungCap = ?, DiaChi = ?, SoDienThoai = ?, MaSoThue = ?, GhiChu = ? WHERE MaNhaCungCap = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getTenNCC(), entity.getDiaChi(), entity.getSoDienThoai(), entity.getMaSoThue(), entity.getGhiChu(), entity.getMaNCC());
    }

    @Override
    public void delete(String key) {
        String DELETE_SQL = "DELETE FROM NhaCungCap WHERE MaNhaCungCap=?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<NhaCungCap> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM NhaCungCap";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhaCungCap selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM NhaCungCap WHERE MaNhaCungCap=?";
        List<NhaCungCap> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                NhaCungCap entity = new NhaCungCap();
                entity.setMaNCC(rs.getString("MaNhaCungCap"));
                entity.setTenNCC(rs.getString("TenNhaCungCap"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setSoDienThoai(rs.getString("SoDienThoai"));
                entity.setMaSoThue(rs.getString("MaSoThue"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<NhaCungCap> selectByKeyword(String keyword) {
        String sql = "Select * from NhaCungCap where TenNhaCungCap like ? or MaNhaCungCap like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%");
    }
    
}
