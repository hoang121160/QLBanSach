/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.TacGia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class TacGiaService {

    private List<TacGia> tacGia;

    public TacGiaService() {
        tacGia = new ArrayList<>();
    }

    public List<TacGia> getAllTacGia() {
        try {
            List<TacGia> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "SELECT maTacGia, ten, tieuSu FROM TacGia";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TacGia tg = new TacGia();
                tg.setMaTacGia(rs.getInt("maTacGia"));
                tg.setTen(rs.getString("ten"));
                tg.setTieuSu(rs.getString("tieuSu"));
                list.add(tg);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addTacGia(TacGia tacGia) {
        Connection conn = DBconnect.getConnection();
        String sql = "INSERT INTO TacGia (ten, tieuSu) VALUES (?, ?)";
        try {
            // Tạo PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tacGia.getTen());
            ps.setString(2, tacGia.getTieuSu());
            // Thực thi câu lệnh SQL để thêm tác giả vào cơ sở dữ liệu
            ps.executeUpdate();
            // Đóng kết nối và các đối tượng liên quan
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTacGia(int maTacGia) {
        String sql = "DELETE FROM tacGia WHERE maTacGia = ?";
        try (Connection conn = DBconnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, maTacGia);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     public void updateTacGia(TacGia update) {
        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "UPDATE TacGia SET ten = ?, tieuSu = ? WHERE MaTacGia = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, update.getTen());
            ps.setString(2, update.getTieuSu());
            ps.setInt(3, update.getMaTacGia());

            // Thực thi câu lệnh SQL để cập nhật dữ liệu trong cơ sở dữ liệu
            ps.executeUpdate();

            // Đóng các đối tượng liên quan
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
