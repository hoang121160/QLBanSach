/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class KhachHangService {

    public List<KhachHang> getAllKhachHang() {
        try {
            List<KhachHang> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "SELECT maKH, ten, gioiTinh, diaChi, soDienThoai, email FROM KhachHang";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang();
                kh.setMaKH(rs.getInt("maKH"));
                kh.setTen(rs.getString("ten"));
                kh.setGioiTinh(rs.getString("gioiTinh"));
                kh.setDiaChi(rs.getString("diaChi"));
                kh.setSoDienThoai(rs.getString("soDienThoai"));
                kh.setEmail(rs.getString("email"));
                list.add(kh);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
