/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class NhanVienService {
    public List<NhanVien> getAllNhanVien() {
        try {
            List<NhanVien> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "SELECT maNV, ten, ngaySinh, gioiTinh, diaChi, soDienThoai, email, chucVu from NhanVien";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getInt("maNV"));
                nv.setTen(rs.getString("ten"));
                nv.setNgaySinh(rs.getDate("ngaySinh"));
                nv.setGioiTinh(rs.getString("gioiTinh"));
                nv.setDiaChi(rs.getString("diaChi"));
                nv.setDiaChi(rs.getString("soDienThoai"));
                nv.setEmail(rs.getString("email"));
                nv.setChucVu(rs.getString("chucVu"));
                list.add(nv);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
