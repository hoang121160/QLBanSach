/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.HoaDon;
import books.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

/**
 *
 * @author tranvandang
 */
public class HoaDonService {

    public List<HoaDon> list = new ArrayList<>();

    public List<HoaDon> getUnpaidHoaDon() {
        String sql = "SELECT hd.maHD, kh.ten AS tenKH, nv.ten AS tenNV, hd.tenNguoiNhan, hd.diaChiNhan, hd.soDienThoai, hd.hinhThucThanhToan, hd.trangThai, hd.createAt "
                + "FROM HoaDon hd "
                + "INNER JOIN KhachHang kh ON hd.maKH = kh.maKH "
                + "INNER JOIN NhanVien nv ON hd.maNV = nv.maNV "
                + "WHERE hd.trangThai = 'Chưa thanh toán'";

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            List<HoaDon> unpaidHoaDons = new ArrayList<>();

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("maHD"));
                hd.setKhachHang(rs.getString("tenKH"));
                hd.setNhanVien(rs.getString("tenNV"));
                hd.setTenNguoiNhan(rs.getString("tenNguoiNhan"));
                hd.setDiaChiNhan(rs.getString("diaChiNhan"));
                hd.setSoDienThoai(rs.getString("soDienThoai"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getString("trangThai"));
                hd.setCreateAt(rs.getTimestamp("createAt").toLocalDateTime());

                unpaidHoaDons.add(hd);
            }

            return unpaidHoaDons;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addHoaDon(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (maKH, maNV, tenNguoiNhan, diaChiNhan, soDienThoai, hinhThucThanhToan, trangThai, createAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hoaDon.getKhachHang().getMaKH());
            ps.setInt(2, hoaDon.getNhanVien().getMaNV());
            ps.setString(3, hoaDon.getTenNguoiNhan());
            ps.setString(4, hoaDon.getDiaChiNhan());
            ps.setString(5, hoaDon.getSoDienThoai());
            ps.setString(6, hoaDon.getHinhThucThanhToan());
            ps.setString(7, hoaDon.getTrangThai());
            ps.setTimestamp(8, Timestamp.valueOf(hoaDon.getCreateAt()));

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HoaDon getHoaDonById(int maHD) {
        String sql = "SELECT * FROM HoaDon WHERE maHD = ?";

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHD(rs.getInt("maHD"));
                // Set các thông tin khác của hóa đơn từ ResultSet
                return hoaDon;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateHoaDonTrangThai(int maHD, String trangThaiMoi, String hinhThucThanhToanMoi) {
        String sql = "UPDATE HoaDon SET trangThai = ?, hinhThucThanhToan = ?, updateAt = ? WHERE maHD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trangThaiMoi);
            ps.setString(2, hinhThucThanhToanMoi);
            Timestamp updateAt = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(3, updateAt);
            ps.setInt(4, maHD);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HoaDon> getAllHoaDonInfo() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.maHD, hd.createAt, hd.updateAt, hd.maNV, hd.tenNguoiNhan, kh.diaChi, kh.soDienThoai, hd.hinhThucThanhToan, hd.trangThai "
                + "FROM HoaDon hd "
                + "JOIN KhachHang kh ON hd.maKH = kh.maKH";

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("maHD"));

                // Kiểm tra giá trị null trước khi chuyển đổi
                Timestamp createAtTimestamp = rs.getTimestamp("createAt");
                if (createAtTimestamp != null) {
                    hd.setCreateAt(createAtTimestamp.toLocalDateTime());
                } else {
                    // Xử lý trường hợp giá trị là null
                    hd.setCreateAt(null); // hoặc thực hiện xử lý phù hợp
                }

                Timestamp updateAtTimestamp = rs.getTimestamp("updateAt");
                if (updateAtTimestamp != null) {
                    hd.setUpdateAt(updateAtTimestamp.toLocalDateTime());
                } else {
                    // Xử lý trường hợp giá trị là null
                    hd.setUpdateAt(null); // hoặc thực hiện xử lý phù hợp
                }

                // Set the NhanVien object
                NhanVien nhanVien = new NhanVien();
                nhanVien.setMaNV(rs.getInt("maNV"));
                // Set other NhanVien fields as needed
                hd.setNhanVien(nhanVien);
                hd.setTenNguoiNhan(rs.getString("tenNguoiNhan"));
                hd.setDiaChiNhan(rs.getString("diaChi"));
                hd.setSoDienThoai(rs.getString("soDienThoai"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getString("trangThai"));
                list.add(hd);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void deleteHoaDon(int maHD) {
        try (Connection con = DBconnect.getConnection()) {
            // 1. Xóa chi tiết hóa đơn
            String deleteChiTietQuery = "DELETE FROM HoaDonChiTiet WHERE maHD = ?";
            try (PreparedStatement deleteChiTietPs = con.prepareStatement(deleteChiTietQuery)) {
                deleteChiTietPs.setInt(1, maHD);
                deleteChiTietPs.executeUpdate();
            }

            // 2. Xóa hóa đơn
            String deleteHoaDonQuery = "DELETE FROM HoaDon WHERE maHD = ?";
            try (PreparedStatement deleteHoaDonPs = con.prepareStatement(deleteHoaDonQuery)) {
                deleteHoaDonPs.setInt(1, maHD);
                deleteHoaDonPs.executeUpdate();
            }

            System.out.println("Đã xóa hóa đơn và chi tiết hóa đơn liên quan.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
