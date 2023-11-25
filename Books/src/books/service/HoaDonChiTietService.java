/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;


import books.connect.DBconnect;
import books.model.HoaDonChiTiet;
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

    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
        try {
            List<HoaDonChiTiet> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = """                                               
                         SELECT 
                             HoaDonChiTiet.maHDCT,
                             HoaDonChiTiet.maHD,
                             HoaDonChiTiet.maSPCT,
                             HoaDonChiTiet.soLuong,
                             HoaDonChiTiet.donGia,
                             HoaDonChiTiet.thanhTien,
                             HoaDonChiTiet.trangThai,
                             HoaDonChiTiet.createAt,
                             HoaDonChiTiet.createBy,
                             HoaDonChiTiet.updateAt,
                             HoaDonChiTiet.updateBy,
                             HoaDonChiTiet.deleted,
                             TacGia.maTacGia,
                             TacGia.ten AS tenTacGia,
                             TheLoai.maTheLoai,
                             TheLoai.ten AS tenTheLoai,
                             SanPham.maSP,
                             SanPham.ten AS tenSP
                         FROM 
                             HoaDonChiTiet
                         INNER JOIN 
                             SanPhamChiTiet ON HoaDonChiTiet.maSPCT = SanPhamChiTiet.maSPCT
                         INNER JOIN 
                             TacGia ON SanPhamChiTiet.MaTacGia = TacGia.maTacGia
                         INNER JOIN 
                             TheLoai ON SanPhamChiTiet.MaTheLoai = TheLoai.MaTheLoai
                         INNER JOIN 
                             SanPham ON SanPhamChiTiet.MaSP = SanPham.maSP;
                         """;
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setMaSPCT(rs.getInt("maSPCT"));
                hdct.setTenSp(rs.getString("tenSP"));
                hdct.setSoLuong(rs.getInt("soLuong"));
                hdct.setDonGia(rs.getBigDecimal("donGia"));
                hdct.setThanhTien(rs.getBigDecimal("thanhTien"));
                hdct.setTrangThai(rs.getString("trangThai"));
                hdct.setTenSp(rs.getString("tenSP"));
                hdct.setTacGia(rs.getString("tenTacGia"));
                 hdct.setTheLoai(rs.getString("tenTheLoai"));
                list.add(hdct);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getTenSPByMaSPCT(int maSPCT) {
        List<HoaDonChiTiet> danhSachHoaDonChiTiet = new ArrayList<>(); // Lấy danh sách sản phẩm chi tiết từ cơ sở dữ liệu hoặc nguồn dữ liệu khác

        for (HoaDonChiTiet hoaDonChiTiet : danhSachHoaDonChiTiet) {
            if (hoaDonChiTiet.getMaSPCT() == maSPCT) {
                return hoaDonChiTiet.getTenSp();
            }
        }

        return null;
    }
}
