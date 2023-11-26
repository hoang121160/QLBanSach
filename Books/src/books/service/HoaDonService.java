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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tranvandang
 */
public class HoaDonService {
    public List<HoaDon> list = new ArrayList<>();

    public List<HoaDon> getAll() {
        String sql = """
                    SELECT 
                            HoaDon.maHD, 
                            HoaDon.maKH, 
                            HoaDon.maNV, 
                            HoaDon.tenNguoiNhan, 
                            HoaDon.diaChiNhan,
                            HoaDon.soDienThoai,
                            HoaDon.hinhThucThanhToan,
                            HoaDon.trangThai,
                            HoaDon.createAt,
                            HoaDon.updateAt,
                            HoaDonChiTiet.maSPCT, 
                            SanPhamChiTiet.ten AS tenSanPham, 
                            HoaDonChiTiet.soLuong, 
                            HoaDonChiTiet.donGia, 
                            HoaDonChiTiet.thanhTien,
                            NhanVien.ten AS tenNhanVien
                        FROM 
                            HoaDon
                        INNER JOIN 
                            HoaDonChiTiet ON HoaDon.maHD = HoaDonChiTiet.maHD
                        INNER JOIN 
                            SanPhamChiTiet ON HoaDonChiTiet.maSPCT = SanPhamChiTiet.maSPCT
                        INNER JOIN 
                            NhanVien ON HoaDon.maNV = NhanVien.maNV
                    """;
        try ( Connection con = DBconnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon();
                hd.setMaHD(rs.getInt("maHD"));
                hd.setMaKH(rs.getInt("maKH"));
                hd.setMaNV(rs.getInt("maNV"));
                hd.setTenNguoiNhan(rs.getString("tenNguoiNhan"));
                hd.setDiaChiNhan(rs.getString("diaChiNhan"));
                hd.setSoDienThoai(rs.getString("soDienThoai"));
                hd.setSoLuong(rs.getInt("soLuong"));
                hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hd.setTrangThai(rs.getString("trangThai"));
                hd.setCreateAt(rs.getTimestamp("createAt").toLocalDateTime());
                hd.setUpdateAt(rs.getTimestamp("updateAt").toLocalDateTime());             
                hd.setThanhTien(rs.getString("thanhTien"));
                hd.setNhanVien(rs.getString("tenNhanVien"));
               

                list.add(hd);

            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public HoaDon findNhanVienMaNV(int maHD) {
    String sql = "SELECT * FROM [dbo].[HoaDon] WHERE maHD = ?";
    
    try (Connection con = DBconnect.getConnection();  
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        // Thiết lập giá trị cho tham số
        ps.setInt(1, maHD);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            HoaDon hd = new HoaDon();
            hd.setMaHD(rs.getInt("maHD"));
            hd.setMaKH(rs.getInt("maKH"));
            hd.setMaNV(rs.getInt("maNV"));
            hd.setTenNguoiNhan(rs.getString("tenNguoiNhan"));
            hd.setDiaChiNhan(rs.getString("diaChiNhan"));
            hd.setSoDienThoai(rs.getString("soDienThoai"));
            hd.setSoLuong(rs.getInt("soLuong"));
            hd.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
            hd.setTrangThai(rs.getString("trangThai"));

            // Trả về đối tượng HoaDon đã được thiết lập giá trị
            return hd;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    
    // Nếu không tìm thấy dữ liệu, trả về null
    return null;
}

    
    

    public static void main(String[] args) {

    }
    
}
