/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.KhachHang;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class KhachHangDAO extends BeeCGearDAO<KhachHang, String>{

    @Override
    public void insert(KhachHang entity) {
        String INSERT_SQL = "INSERT INTO KhachHang(MaKhachHang, HovaTen, DiaChi ,GhiChu) Values(?, ?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaKhachHang(), entity.getHovaTen(), entity.getDiaChi(), entity.getGhiChu());
    }

    @Override
    public void update(KhachHang entity) {
        String UPDATE_SQL = "UPDATE KhachHang set HovaTen = ?, DiaChi = ?, GhiChu = ? WHERE MaKhachHang = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getHovaTen(), entity.getDiaChi(), entity.getGhiChu(), entity.getMaKhachHang());
    }

    @Override
    public void delete(String key) {
        String DELETE_SQL = "DELETE FROM KhachHang WHERE MaKhachHang=?";
        JdbcHelper.update(DELETE_SQL, key);
    }

    @Override
    public List<KhachHang> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM KhachHang";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectByID(String key) {
        String SELECT_BY_ID = "SELECT * FROM KhachHang WHERE MaKhachHang=?";
        List<KhachHang> list= this.selectBySql(SELECT_BY_ID, key);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    protected List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKhachHang(rs.getString("MaKhachHang"));
                entity.setHovaTen(rs.getString("HovaTen"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
    public List<KhachHang> selectByKeyword(String keyword) {
        String sql = "Select * from KhachHang where MaKhachHang like ? or HovaTen like ?";
        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%");
    }
    
}
