/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SanPhamService {
      private List<SanPham> sanPham;

    public SanPhamService() {
        sanPham = new ArrayList<>();
    }

    public List<SanPham> getAllSanPham() {
        try {
            List<SanPham> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "select maSP, ten from SanPham";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("maSP"));
                sp.setTen(rs.getString("ten"));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addSanPham(SanPham sanPham) {
        Connection conn = DBconnect.getConnection();
        String sql = "INSERT INTO SanPham (ten) VALUES (?)";
        try {
            // Tạo PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sanPham.getTen());
            // Thực thi câu lệnh SQL để thêm tác giả vào cơ sở dữ liệu
            ps.executeUpdate();
            // Đóng kết nối và các đối tượng liên quan
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
