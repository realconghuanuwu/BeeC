/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author huanl
 */
public class DanhMuc {
    private String maDanhMuc;
    private String tenDanhMuc;
    private String ghiChu;

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String MaDanhMuc) {
        this.maDanhMuc = MaDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String TenDanhMuc) {
        this.tenDanhMuc = TenDanhMuc;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.ghiChu = GhiChu;
    }
    
    @Override
    public String toString() {
        return maDanhMuc;
    }
    
    
}
