/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.HoaDon;
import books.model.HoaDonChiTiet;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonChiTietService {

    private List<HoaDonChiTiet> hoaDonChiTiet;

    public HoaDonChiTietService() {
        hoaDonChiTiet = new ArrayList<>();
    }

//    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
//        try {
//            List<HoaDonChiTiet> list = new ArrayList<>();
//            Connection conn = DBconnect.getConnection();
//            String sql = "select maSPCT, soLuong, donGia from HoaDonChiTiet";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                HoaDonChiTiet hdct = new HoaDonChiTiet();
//                hdct.setSanPhamChiTiet(rs.getInt("maSPCT"));
//                hdct.set;
//                
//                
//                list.add(hdct);
//            }
//            return (ArrayList<HoaDonChiTiet>) list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
    public void addHoaDonChiTiet(HoaDonChiTiet hdct) {
        String sql = "INSERT INTO HoaDonChiTiet (maHD, maSPCT, soLuong, donGia, trangThai) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, hdct.getHoaDon().getMaHD());
            ps.setInt(2, hdct.getSanPhamChiTiet().getMaSPCT());
            ps.setInt(3, hdct.getSoLuong());
            ps.setBigDecimal(4, hdct.getDonGia());
            ps.setString(5, hdct.getTrangThai());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
//        try {
//            List<HoaDonChiTiet> list = new ArrayList<>();
//            Connection conn = DBconnect.getConnection();
//            String sql = """                                               
//                         SELECT 
//                             HoaDonChiTiet.maHDCT,
//                             HoaDonChiTiet.maHD,
//                             HoaDonChiTiet.maSPCT,
//                             HoaDonChiTiet.soLuong,
//                             HoaDonChiTiet.donGia,
//                             HoaDonChiTiet.thanhTien,
//                             HoaDonChiTiet.trangThai,
//                             HoaDonChiTiet.createAt,
//                             HoaDonChiTiet.createBy,
//                             HoaDonChiTiet.updateAt,
//                             HoaDonChiTiet.updateBy,
//                             HoaDonChiTiet.deleted,
//                             TacGia.maTacGia,
//                             TacGia.ten AS tenTacGia,
//                             TheLoai.maTheLoai,
//                             TheLoai.ten AS tenTheLoai,
//                             SanPham.maSP,
//                             SanPham.ten AS tenSP
//                         FROM 
//                             HoaDonChiTiet
//                         INNER JOIN 
//                             SanPhamChiTiet ON HoaDonChiTiet.maSPCT = SanPhamChiTiet.maSPCT
//                         INNER JOIN 
//                             TacGia ON SanPhamChiTiet.MaTacGia = TacGia.maTacGia
//                         INNER JOIN 
//                             TheLoai ON SanPhamChiTiet.MaTheLoai = TheLoai.MaTheLoai
//                         INNER JOIN 
//                             SanPham ON SanPhamChiTiet.MaSP = SanPham.maSP;
//                         """;
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                HoaDonChiTiet hdct = new HoaDonChiTiet();
//                hdct.setMaSPCT(rs.getInt("maSPCT"));
//                hdct.setTenSp(rs.getString("tenSP"));
//                hdct.setSoLuong(rs.getInt("soLuong"));
//                hdct.setDonGia(rs.getBigDecimal("donGia"));
//                hdct.setThanhTien(rs.getBigDecimal("thanhTien"));
//                hdct.setTrangThai(rs.getString("trangThai"));
//                hdct.setTenSp(rs.getString("tenSP"));
//                hdct.setTacGia(rs.getString("tenTacGia"));
//                 hdct.setTheLoai(rs.getString("tenTheLoai"));
//                list.add(hdct);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public String getTenSPByMaSPCT(int maSPCT) {
//        List<HoaDonChiTiet> danhSachHoaDonChiTiet = new ArrayList<>(); // Lấy danh sách sản phẩm chi tiết từ cơ sở dữ liệu hoặc nguồn dữ liệu khác
//
//        for (HoaDonChiTiet hoaDonChiTiet : danhSachHoaDonChiTiet) {
//            if (hoaDonChiTiet.getMaSPCT() == maSPCT) {
//                return hoaDonChiTiet.getTenSp();
//            }
//        }
//
//        return null;
//    }
    public List<HoaDonChiTiet> getHoaDonChiTietByMaHD(int maHD) {
        List<HoaDonChiTiet> hoaDonChiTietList = new ArrayList<>();

        try {
            Connection conn = DBconnect.getConnection();

            String query = "SELECT hdct.maHDCT, hdct.maSPCT, spct.ten AS tenSPCT, hdct.soLuong, hdct.donGia, hdct.trangThai "
                    + "FROM hoaDonChiTiet hdct "
                    + "INNER JOIN sanPhamChiTiet spct ON hdct.maSPCT = spct.maSPCT "
                    + "WHERE hdct.maHD = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maHD);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setMaHDCT(rs.getInt("maHDCT"));
                hoaDonChiTiet.setSanPhamChiTiet(rs.getInt("maSPCT"));
                hoaDonChiTiet.setTenSPCT(rs.getString("tenSPCT"));
                hoaDonChiTiet.setSoLuong(rs.getInt("soLuong"));
                hoaDonChiTiet.setDonGia(rs.getBigDecimal("donGia"));
                hoaDonChiTietList.add(hoaDonChiTiet);
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
        }

        return hoaDonChiTietList;
    }

}
