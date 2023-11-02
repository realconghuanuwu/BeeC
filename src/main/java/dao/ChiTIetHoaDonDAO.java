/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.ChiTietHoaDon;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.JdbcHelper;

/**
 *
 * @author huanl
 */
public class ChiTIetHoaDonDAO extends BeeCGearDAO<ChiTietHoaDon, Integer>{
    
    @Override
    public void insert(ChiTietHoaDon entity) {
        String INSERT_SQL = "INSERT INTO ChiTietHoaDon(MaHoaDon, MaSanPham, SoLuong) Values(?, ?, ?)";
        JdbcHelper.update(INSERT_SQL, entity.getMaHoaDon(), entity.getMaSanPham(), entity.getSoLuong());
        
    }

    @Override
    public void update(ChiTietHoaDon entity) {
        String UPDATE_SQL = "UPDATE ChiTietHoaDon set SoLuong = ? WHERE MaHoaDon = ? and MaSanPham = ?";
        JdbcHelper.update(UPDATE_SQL, entity.getSoLuong(),entity.getMaHoaDon(), entity.getMaSanPham());
    }

    public void delete(Integer mahd, Integer masp) {
        String DELETE_SQL = "DELETE FROM ChiTietHoaDon WHERE MaHoaDon = ? and MaSanPham = ?";
        JdbcHelper.update(DELETE_SQL, mahd, masp);
    }

    @Override
    public List<ChiTietHoaDon> selectAll() {
        String SELECT_ALL_SQL = "SELECT * FROM ChiTietHoaDon";
        return this.selectBySql(SELECT_ALL_SQL);
    }

    public ChiTietHoaDon selectByID(Integer mahd, Integer masp) {
        String SELECT_BY_ID = "SELECT * FROM ChiTietHoaDon WHERE MaHoaDon = ? and MaSanPham = ?";
        List<ChiTietHoaDon> list= this.selectBySql(SELECT_BY_ID, mahd, masp);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    
    @Override
    protected List<ChiTietHoaDon> selectBySql(String sql, Object... args) {
        List<ChiTietHoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JdbcHelper.query(sql, args);
            while (rs.next()) {
                ChiTietHoaDon entity = new ChiTietHoaDon();
                entity.setMaHoaDon(rs.getInt("MaHoaDon"));
                entity.setMaSanPham(rs.getInt("MaSanPham"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
//    public List<ChiTietHoaDon> selectByKeyword(String keyword) {
//        String sql = "Select * from ChiTietHoaDon where MaHoaDon = ? and Ma";
//        return this.selectBySql(sql, "%"+keyword+"%", "%"+keyword+"%", "%"+keyword+"%");
//    }

    @Override
    public void delete(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ChiTietHoaDon selectByID(Integer key) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
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
    
    public List<Object[]> getChiTietHoaDonByMaHoaDon(Integer MaHD) {
        String sql = "{CALL getChiTietHoaDonByMaHoaDon(?)}";
        String[] cols = {"MaSanPham","TenSanPham","MaModel","SoLuong","GiaBan"};
        return getListOfArray(sql, cols, MaHD);
    };

}
