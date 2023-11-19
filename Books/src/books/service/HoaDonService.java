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
                       
                      FROM [dbo].[HoaDon]
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
                hd.setCreateBy(rs.getString("createBy"));
                hd.setUpdateBy(rs.getString("updateBy"));
               

                list.add(hd);

            }
            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    

    public static void main(String[] args) {

    }
    
}
