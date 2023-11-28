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
import java.util.Date;
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
    public HoaDon getOne(int maHD) {
        String query = """
                        SELECT [maHD]
                              ,[maKH]
                              ,[maNV]
                              ,[tenNguoiNhan]
                              ,[diaChiNhan]
                              ,[soDienThoai]
                              ,[soLuong]
                              ,[hinhThucThanhToan]
                              ,[trangThai]
                              ,[createAt]
                              ,[createBy]
                              ,[updateAt]
                              ,[updateBy]
                              ,[deleted]
                          FROM [dbo].[HoaDon]
                        		WHERE maHD = ?
                       """;
        try ( Connection con = DBconnect.getConnection();  PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHD(rs.getInt("maHD"));
                hoaDon.setMaKH(rs.getInt("maKH"));
                hoaDon.setMaNV(rs.getInt("maNV"));
                hoaDon.setTenNguoiNhan(rs.getString("tenNguoiNhan"));
                hoaDon.setDiaChiNhan(rs.getString("diaChiNhan"));
                hoaDon.setSoDienThoai(rs.getString("soDienThoai"));
                hoaDon.setSoLuong(rs.getInt("soLuong"));
                hoaDon.setHinhThucThanhToan(rs.getString("hinhThucThanhToan"));
                hoaDon.setTrangThai(rs.getString("trangThai"));
                
                return hoaDon;
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return null;
    }
    public boolean addHoaDon(HoaDon hoaDon) {
        int check = 0;
        String query = """
                       INSERT INTO [dbo].[HoaDon]
                                  ([maKH]
                                  ,[maNV]
                                  ,[tenNguoiNhan]
                                  ,[diaChiNhan]
                                  ,[soDienThoai]
                                  ,[soLuong]
                                  ,[hinhThucThanhToan]
                                  ,[trangThai]
                                                           
                            VALUES(?,?,?,?,?,?,?,?)
                       """;
        try (Connection con = DBconnect.getConnection();
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, hoaDon.getMaHD());
            ps.setObject(2, hoaDon.getMaKH());
            ps.setObject(3, hoaDon.getMaNV());
            ps.setObject(4, hoaDon.getTenNguoiNhan());
            ps.setObject(5, hoaDon.getSoDienThoai());
            ps.setObject(6, hoaDon.getSoLuong());
            ps.setObject(7, hoaDon.getHinhThucThanhToan());
            ps.setObject(8, hoaDon.getTrangThai());
            check = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }
    

    public static void main(String[] args) {

    }

}
