/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import books.model.SanPhamChiTiet;
import books.model.TacGia;
import books.model.TheLoai;
import books.model.SanPham;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class SanPhamChiTietService {

//    //    đẩy dữ liệu lên view sản phẩm
//    public ArrayList<SanPhamChiTiet> getAll() {
//    try {
//        List<SanPhamChiTiet> list = new ArrayList<>();
//        Connection conn = DBconnect.getConnection();
//        String sql = "SELECT\n" +
//                "    spct.maSPCT,\n" +
//                "    tg.ten AS tenTacGia,\n" +
//                "    tl.ten AS tenTheLoai,\n" +
//                "    spct.ten,\n" +
//                "    spct.gia,\n" +
//                "    spct.ngonNgu,\n" +
//                "    spct.soTrang,\n" +
//                "    spct.nhaXuatBan,\n" +
//                "    spct.namXuatBan,\n" +
//                "    spct.lanTaiBan\n" +
//                "FROM\n" +
//                "    SanPhamChiTiet spct\n" +
//                "    INNER JOIN TacGia tg ON spct.MaTacGia = tg.maTacGia\n" +
//                "    INNER JOIN TheLoai tl ON spct.MaTheLoai = tl.MaTheLoai";
//        PreparedStatement ps = conn.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while (rs.next()) {
//            SanPhamChiTiet spct = new SanPhamChiTiet();
//            spct.setMaSPCT(rs.getInt("maSPCT"));
//            spct.setTenTacGia(rs.getString("tenTacGia"));
//            spct.setTenTheLoai(rs.getString("tenTheLoai"));
//            spct.setTen(rs.getString("ten"));
//            spct.setGia(rs.getFloat("gia"));
//            spct.setNgonNgu(rs.getString("ngonNgu"));
//            spct.setSoTrang(rs.getInt("soTrang"));
//            spct.setNhaXuatBan(rs.getString("nhaXuatBan"));
//            spct.setNamXuatBan(rs.getInt("namXuatBan"));
//            spct.setLanTaiBan(rs.getInt("lanTaiBan"));
//            list.add(spct);
//        }
//        return (ArrayList<SanPhamChiTiet>) list;
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    return null;
//}
//
//    public void add(SanPhamChiTiet sp) {
//        try {
//            Connection conn = DBconnect.getConnection();
//            String sql = "insert into SanPhamChiTiet (MaTacGia,MaSanPham,MaTheLoai,ten,gia,ngonNgu,soTrang,nhaXuatBan,namXuatBan,lanTaiBan) values (?,?,?,?,?,?,?,?,?,?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            
//        } catch (Exception e) {
//        }
//    }
    private List<SanPhamChiTiet> sanPhamChiTietList;

    public SanPhamChiTietService() {
        sanPhamChiTietList = new ArrayList<>();
    }

    public List<SanPhamChiTiet> getAllSanPhamChiTiet() {
        try {
            List<SanPhamChiTiet> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "SELECT\n"
                    + "    sp.maSP as maSP,\n"
                    + "    spct.maSPCT,\n"
                    + "    tg.ten AS tenTacGia,\n"
                    + "    tl.ten AS tenTheLoai,\n"
                    + "    spct.ten,\n"
                    + "    spct.gia,\n"
                    + "    spct.ngonNgu,\n"
                    + "    spct.soTrang,\n"
                    + "    spct.nhaXuatBan,\n"
                    + "    spct.namXuatBan,\n"
                    + "    spct.lanTaiBan\n"
                    + "FROM\n"
                    + "    SanPhamChiTiet spct\n"
                    + "    INNER JOIN TacGia tg ON spct.MaTacGia = tg.maTacGia\n"
                    + "    INNER JOIN TheLoai tl ON spct.MaTheLoai = tl.MaTheLoai\n"
                    + "    INNER JOIN SanPham sp ON spct.maSP = sp.maSP";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setSanPham(rs.getInt("maSP"));
                spct.setMaSPCT(rs.getInt("maSPCT"));
                spct.setTacGia(rs.getString("tenTacGia"));
                spct.setTheLoai(rs.getString("tenTheLoai"));
                spct.setTen(rs.getString("ten"));
                spct.setGia(rs.getBigDecimal("gia"));
                spct.setNgonNgu(rs.getString("ngonNgu"));
                spct.setSoTrang(rs.getInt("soTrang"));
                spct.setNhaXuatBan(rs.getString("nhaXuatBan"));
                spct.setNamXuatBan(rs.getInt("namXuatBan"));
                spct.setLanTaiBan(rs.getInt("lanTaiBan"));
                list.add(spct);
            }
            return (ArrayList<SanPhamChiTiet>) list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SanPhamChiTiet> getSanPhamChiTietByMaSP(int maSP) {
        try {
            List<SanPhamChiTiet> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();
            String sql = "SELECT\n"
                    + "    spct.maSPCT,\n"
                    + "    tg.ten AS tenTacGia,\n"
                    + "    tl.ten AS tenTheLoai,\n"
                    + "    spct.ten,\n"
                    + "    spct.gia,\n"
                    + "    spct.ngonNgu,\n"
                    + "    spct.soTrang,\n"
                    + "    spct.nhaXuatBan,\n"
                    + "    spct.namXuatBan,\n"
                    + "    spct.lanTaiBan\n"
                    + "FROM\n"
                    + "    SanPhamChiTiet spct\n"
                    + "    INNER JOIN TacGia tg ON spct.MaTacGia = tg.maTacGia\n"
                    + "    INNER JOIN TheLoai tl ON spct.MaTheLoai = tl.MaTheLoai\n"
                    + "WHERE\n"
                    + "    spct.maSPCT = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maSP);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPhamChiTiet spct = new SanPhamChiTiet();
                spct.setMaSPCT(rs.getInt("maSPCT"));
                spct.setTacGia(rs.getString("tenTacGia"));
                spct.setTheLoai(rs.getString("tenTheLoai"));
                spct.setTen(rs.getString("ten"));
                spct.setGia(rs.getBigDecimal("gia"));
                spct.setNgonNgu(rs.getString("ngonNgu"));
                spct.setSoTrang(rs.getInt("soTrang"));
                spct.setNhaXuatBan(rs.getString("nhaXuatBan"));
                spct.setNamXuatBan(rs.getInt("namXuatBan"));
                spct.setLanTaiBan(rs.getInt("lanTaiBan"));
                list.add(spct);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSanPhamChiTiet(SanPhamChiTiet sp) {
        try {
            Connection conn = DBconnect.getConnection();
            String query = "INSERT INTO SanPhamChiTiet (maSP, MaTacGia, MaTheLoai, ten, gia, ngonNgu, soTrang, nhaXuatBan, namXuatBan, lanTaiBan)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set values for the parameters
            ps.setInt(1, sp.getSanPham().getMaSP()); // Assuming you have a getMaSP() method in SanPham class
            ps.setInt(2, sp.getTacGia().getMaTacGia()); // Assuming you have a getMaTacGia() method in TacGia class
            ps.setInt(3, sp.getTheLoai().getMaTheLoai()); // Assuming you have a getMaTheLoai() method in TheLoai class
            ps.setString(4, sp.getTen());
            ps.setBigDecimal(5, sp.getGia());
            ps.setString(6, sp.getNgonNgu());
            ps.setInt(7, sp.getSoTrang());
            ps.setString(8, sp.getNhaXuatBan());
            ps.setInt(9, sp.getNamXuatBan());
            ps.setInt(10, sp.getLanTaiBan());

            // Execute the update
            ps.executeUpdate();

            // Close the PreparedStatement
            ps.close();
            // Don't forget to close the connection
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error message)
        }
    }

    public void deleteSanPhamChiTiet(int maSPCT) {
        try {
            Connection conn = DBconnect.getConnection();
            String query = "DELETE FROM SanPhamChiTiet WHERE maSPCT = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maSPCT);
            ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSanPhamChiTiet(SanPhamChiTiet sanPhamChiTietToUpdate) {
        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "UPDATE SanPhamChiTiet SET ten = ?, gia = ?, ngonNgu = ?, soTrang = ?, nhaXuatBan = ?, namXuatBan = ?, lanTaiBan = ? WHERE maSPCT = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set values for the parameters
            ps.setString(1, sanPhamChiTietToUpdate.getTen());
            ps.setBigDecimal(2, sanPhamChiTietToUpdate.getGia());
            ps.setString(3, sanPhamChiTietToUpdate.getNgonNgu());
            ps.setInt(4, sanPhamChiTietToUpdate.getSoTrang());
            ps.setString(5, sanPhamChiTietToUpdate.getNhaXuatBan());
            ps.setInt(6, sanPhamChiTietToUpdate.getNamXuatBan());
            ps.setInt(7, sanPhamChiTietToUpdate.getLanTaiBan());
            ps.setInt(8, sanPhamChiTietToUpdate.getMaSPCT());

            // Execute the update
            ps.executeUpdate();

            // Close the PreparedStatement
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the connection in the finally block to ensure it's always closed
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
