/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.HoaDon;
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

    public void updateHoaDonTrangThai(int maHD, String trangThaiMoi) {
        String sql = "UPDATE HoaDon SET trangThai = ? WHERE maHD = ?";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, trangThaiMoi);
            ps.setInt(2, maHD);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public List<HoaDon> getAll() {
//        String sql = """
//                    SELECT 
//                            HoaDon.maHD, 
//                            HoaDon.maKH, 
//                            HoaDon.maNV, 
//                            HoaDon.tenNguoiNhan, 
//                            HoaDon.diaChiNhan,
//                            HoaDon.soDienThoai,
//                            HoaDon.hinhThucThanhToan,
//                            HoaDon.trangThai,
//                            HoaDon.createAt,
//                            HoaDon.updateAt,
//                            HoaDonChiTiet.maSPCT, 
//                            SanPhamChiTiet.ten AS tenSanPham, 
//                            HoaDonChiTiet.soLuong, 
//                            HoaDonChiTiet.donGia, 
//                            HoaDonChiTiet.thanhTien,
//                            NhanVien.ten AS tenNhanVien
//                        FROM 
//                            HoaDon
//                        INNER JOIN 
//                            HoaDonChiTiet ON HoaDon.maHD = HoaDonChiTiet.maHD
//                        INNER JOIN 
//                            SanPhamChiTiet ON HoaDonChiTiet.maSPCT = SanPhamChiTiet.maSPCT
//                        INNER JOIN 
//                            NhanVien ON HoaDon.maNV = NhanVien.maNV
//                    """;
//        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                HoaDon hd = new HoaDon();
//                hd.setMaHD(rs.getInt("maHD"));
//                hd.setMaKH(rs.getInt("maKH"));
//                hd.setMaNV(rs.getInt("maNV"));
//                hd.setTenNguoiNhan(rs.getString("tenNguoiNhan"));
//                hd.setDiaChiNhan(rs.getString("diaChiNhan"));
//                hd.setSoDienThoai(rs.getString("soDienThoai"));
//                hd.setSoLuong(rs.getInt("soLuong"));
//                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
//                hd.setTrangThai(rs.getString("trangThai"));
//                hd.setCreateAt(rs.getTimestamp("createAt").toLocalDateTime());
//                hd.setUpdateAt(rs.getTimestamp("updateAt").toLocalDateTime());
//                hd.setThanhTien(rs.getString("thanhTien"));
//                hd.setNhanVien(rs.getString("tenNhanVien"));
//
//                list.add(hd);
//
//            }
//            return list;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}
