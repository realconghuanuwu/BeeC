/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ThuongHieu;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class ThuongHieuDAO extends BeeCGearDAO<ThuongHieu, String>{

    @Override
    public void insert(ThuongHieu entity) {
        String INSERT_SQL = "INSERT INTO ThuongHieu(MaThuongHieu, TenThuongHieu, Anh, GhiChu) Values(?, ?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaThuongHieu(), entity.getTenThuongHieu(), entity.getAnh(), entity.getGhiChu());
    }

    @Override
    public void update(ThuongHieu entity) {
        String UPDATE_SQL = "UPDATE ThuongHieu set TenThuongHieu = ?, Anh = ?, GhiChu = ? WHERE MaThuongHieu = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getTenThuongHieu(), entity.getAnh(), entity.getGhiChu(), entity.getMaThuongHieu());
    }

    @Override
    public void delete(String key) {
        String DELETE_SQL = "DELETE FROM ThuongHieu WHERE MaThuongHieu=?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<ThuongHieu > selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM ThuongHieu";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ThuongHieu  selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM ThuongHieu WHERE MaThuongHieu=?";
        List<ThuongHieu > list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<ThuongHieu > selectBySql(String sql, Object... args) {
        List<ThuongHieu > list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ThuongHieu  entity = new ThuongHieu ();
                entity.setMaThuongHieu(rs.getString("MaThuongHieu"));
                entity.setTenThuongHieu(rs.getString("TenThuongHieu"));
                entity.setAnh(rs.getString("Anh"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<ThuongHieu > selectByKeyword(String keyword) {
        String sql = "Select * from ThuongHieu where TenThuongHieu like ? or MaThuongHieu like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%");
    }
    
}
