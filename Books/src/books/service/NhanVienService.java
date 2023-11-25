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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

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
                nv.setSoDienThoai(rs.getString("soDienThoai"));
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

    public void addNhanVien(NhanVien nv) {
        Connection conn = DBconnect.getConnection();
        String sql = "INSERT INTO NhanVien (ten,ngaySinh, gioiTinh, diaChi, soDienThoai, email, chucVu) VALUES (?,?,?,?,?,?,?)";
        try {
            // Tạo PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nv.getTen());
            ps.setDate(2, new java.sql.Date(nv.getNgaySinh().getTime()));
            ps.setString(3, nv.getGioiTinh());
            ps.setString(4, nv.getDiaChi());
            ps.setString(5, nv.getSoDienThoai());
            ps.setString(6, nv.getEmail());
            ps.setString(7, nv.getChucVu());

            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNhanVien(NhanVien update) {
        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "UPDATE NhanVien SET ten = ?, ngaySinh = ?, gioiTinh = ?, diaChi = ?, soDienThoai = ?, email = ?, chucVu = ? WHERE maNV = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, update.getTen());
            ps.setDate(2, new java.sql.Date(update.getNgaySinh().getTime()));
            ps.setString(3, update.getGioiTinh());
            ps.setString(4, update.getDiaChi());
            ps.setString(5, update.getSoDienThoai());
            ps.setString(6, update.getEmail());
            ps.setString(7, update.getChucVu());
            ps.setInt(8, update.getMaNV());
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

    public void deleteNhanVien(int maNV) {
        try {
            Connection conn = DBconnect.getConnection();
            String query = "DELETE FROM NhanVien WHERE maNV = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNV);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NhanVien findNhanVienByMaNV(int maNV) {
        NhanVien nhanVien = null;

        try {
            Connection conn = DBconnect.getConnection();

            String query = "SELECT * FROM NhanVien WHERE maNV = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nhanVien = new NhanVien();
                nhanVien.setMaNV(rs.getInt("maNV"));
                nhanVien.setTen(rs.getString("ten"));
                nhanVien.setNgaySinh(rs.getDate("ngaySinh"));
                nhanVien.setGioiTinh(rs.getString("gioiTinh"));
                nhanVien.setDiaChi(rs.getString("diaChi"));
                nhanVien.setSoDienThoai(rs.getString("soDienThoai"));
                nhanVien.setEmail(rs.getString("email"));
                nhanVien.setChucVu(rs.getString("chucVu"));
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
