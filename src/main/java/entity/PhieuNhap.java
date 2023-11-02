/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.Date;

/**
 *
 * @author huanl
 */
public class PhieuNhap {
    private String MaPhieuNhap;
    private String maNhaCungCap;
    private Date NgayTao;

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getMaPhieuNhap() {
        return MaPhieuNhap;
    }

    public void setMaPhieuNhap(String MaPhieuNhap) {
        this.MaPhieuNhap = MaPhieuNhap;
    }

//    public NhaCungCap getNhaCungCap() {
//        return nhaCungCap;
//    }
//
//    public void setNhaCungCap(NhaCungCap nhaCungCap) {
//        this.nhaCungCap = nhaCungCap;
//    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    @Override
    public String toString() {
        String man = String.valueOf(MaPhieuNhap);
        return man;
    }
    
    
}
