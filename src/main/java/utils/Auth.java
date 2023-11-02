/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import entity.NhanVien;

/**
 *
 * @author huanl
 */
public class Auth {
    public static NhanVien user = null; //duy tri user dang nhap vao he thong
    public static void clear() {
        Auth user = null;
    }
    
    public static boolean isLogin() {
        return Auth.user != null;
    }
    
    public static String isRoleString() {
        if(isLogin() && isManager())
            return "Quản Lý";
        else if(isLogin() && isSaler())
            return "Nhân Viên Bán Hàng";
        else if(isLogin() && isNVNhap())
            return "Nhân Viên Nhập Hàng";
        return "Chưa Login";
    }
    
    public static int isRole() {
        return user.getVaiTro();
    }
    // 0 - Quan ly, 1 - saler, 2 - input man
    
    public static boolean isSaler() {
        return Auth.isLogin()&&isRole()==1;
    }
    
    public static boolean isNVNhap() {
        return Auth.isLogin()&&isRole()==2;
    }
    
    public static boolean isManager() {
        return Auth.isLogin()&&isRole()==0;
    }
    
    
}
