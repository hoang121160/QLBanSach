/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.TheLoai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TheLoaiService {
    private List<TheLoai> theLoai;

    public TheLoaiService() {
        theLoai = new ArrayList<>();
    }

    public List<TheLoai> getAllTheLoai() {
        try {
            List<TheLoai> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "SELECT MaTheLoai, ten FROM TheLoai";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TheLoai tl = new TheLoai();
                tl.setMaTheLoai(rs.getInt("MaTheLoai"));
                tl.setTen(rs.getString("ten"));
                list.add(tl);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public void addTheLoai(TheLoai theLoai) {
        Connection conn = DBconnect.getConnection();
        String sql = "INSERT INTO TheLoai (ten) VALUES (?)";
        try {
            // Tạo PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, theLoai.getTen());
            // Thực thi câu lệnh SQL để thêm tác giả vào cơ sở dữ liệu
            ps.executeUpdate();
            // Đóng kết nối và các đối tượng liên quan
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTheLoai(int maTheLoai) {
        String sql = "DELETE FROM TheLoai WHERE maTheLoai = ?";

        try (Connection conn = DBconnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTheLoai);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     public void updateTheLoai(TheLoai update) {
        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "UPDATE TheLoai SET ten = ? WHERE MaTheLoai = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, update.getTen());
            ps.setInt(2, update.getMaTheLoai());
            // Thực thi câu lệnh SQL để cập nhật dữ liệu trong cơ sở dữ liệu
            ps.executeUpdate();
            // Đóng các đối tượng liên quan
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
