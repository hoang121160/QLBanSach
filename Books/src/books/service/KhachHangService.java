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
import java.sql.SQLException;
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
    public void addKhachHang(KhachHang kh) {
        Connection conn = DBconnect.getConnection();
        String sql = "INSERT INTO KhachHang (ten, gioiTinh, diaChi, soDienThoai, email) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, kh.getTen());
            ps.setString(2, kh.getGioiTinh());
            ps.setString(3, kh.getDiaChi());
            ps.setString(4, kh.getSoDienThoai());
            ps.setString(5, kh.getEmail());
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateKhachHang(KhachHang update) {
        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "UPDATE KhachHang SET ten = ?, gioiTinh = ?, diaChi = ?, soDienThoai = ?, email = ? WHERE maKH = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, update.getTen());
            ps.setString(2, update.getGioiTinh());
            ps.setString(3, update.getDiaChi());
            ps.setString(4, update.getSoDienThoai());
            ps.setString(5, update.getEmail());
            ps.setInt(6, update.getMaKH());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void deleteKhachHang(int maKH) {
        try {
            Connection conn = DBconnect.getConnection();
            String query = "DELETE FROM KhachHang WHERE maKH = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maKH);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public KhachHang findKhachHangByMaKH(int maKH) {
        KhachHang nhanVien = null;

        try {
            Connection conn = DBconnect.getConnection();

            String query = "SELECT * FROM KhachHang WHERE maKH = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhanVien = new KhachHang();
                nhanVien.setMaKH(rs.getInt("maKH"));
                nhanVien.setTen(rs.getString("ten"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setDiaChi(rs.getString("diaChi"));
                nhanVien.setSoDienThoai(rs.getString("soDienThoai"));
                nhanVien.setEmail(rs.getString("email"));
            }
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Handle SQL exceptions
            e.printStackTrace();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }

        return nhanVien;
    }
}
