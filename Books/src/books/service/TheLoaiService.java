/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBcontext;
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
            Connection conn = DBcontext.getConnection();
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
        Connection conn = DBcontext.getConnection();
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

        try (Connection conn = DBcontext.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTheLoai);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
