/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.DanhMuc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class DanhMucDAO extends BeeCGearDAO<DanhMuc, String> {
    
    @Override
    public void insert(DanhMuc entity) {
        String INSERT_SQL = "INSERT INTO DanhMucSanPham(MaDanhMuc, TenDanhMuc, GhiChu) Values(?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaDanhMuc(),entity.getTenDanhMuc(),entity.getGhiChu());
    }

    @Override
    public void update(DanhMuc entity) {
        String UPDATE_SQL = "UPDATE DanhMucSanPham set TenDanhMuc = ?, GhiChu = ? WHERE MaDanhMuc = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getTenDanhMuc(), entity.getGhiChu(), entity.getMaDanhMuc());
    }

    @Override
    public void delete(String key) {
        String DELETE_SQL = "DELETE FROM DanhMucSanPham WHERE MaDanhMuc=?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<DanhMuc> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM DanhMucSanPham";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public DanhMuc selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM DanhMucSanPham WHERE MaDanhMuc=?";
        List<DanhMuc> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<DanhMuc> selectBySql(String sql, Object... args) {
        List<DanhMuc> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                DanhMuc entity = new DanhMuc();
                entity.setMaDanhMuc(rs.getString("MaDanhMuc"));
                entity.setTenDanhMuc(rs.getString("TenDanhMuc"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<DanhMuc> selectByKeyword(String keyword) {
        String sql = "Select * from DanhMucSanPham where TenDanhMuc like ? or MaDanhMuc like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%");
    }
}
