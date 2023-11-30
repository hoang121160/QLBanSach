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
                    + "    spct.ten, spct.soLuong,\n"
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
                spct.setSoLuong(rs.getInt("soLuong"));
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
                    + "    spct.MaSP = ?";
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
            String query = "INSERT INTO SanPhamChiTiet (maSP, MaTacGia, MaTheLoai, ten,soLuong, gia, ngonNgu, soTrang, nhaXuatBan, namXuatBan, lanTaiBan)\n"
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);

            // Set values for the parameters
            ps.setInt(1, sp.getSanPham().getMaSP());
            ps.setInt(2, sp.getTacGia().getMaTacGia());
            ps.setInt(3, sp.getTheLoai().getMaTheLoai());
            ps.setString(4, sp.getTen());
            ps.setInt(5, sp.getSoLuong());
            ps.setBigDecimal(6, sp.getGia());
            ps.setString(7, sp.getNgonNgu());
            ps.setInt(8, sp.getSoTrang());
            ps.setString(9, sp.getNhaXuatBan());
            ps.setInt(10, sp.getNamXuatBan());
            ps.setInt(11, sp.getLanTaiBan());

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
            String sql = "UPDATE SanPhamChiTiet SET ten = ?,soLuong = ?, gia = ?, ngonNgu = ?, soTrang = ?, nhaXuatBan = ?, namXuatBan = ?, lanTaiBan = ? WHERE maSPCT = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            // Set values for the parameters
            ps.setString(1, sanPhamChiTietToUpdate.getTen());
            ps.setInt(2, sanPhamChiTietToUpdate.getSoLuong());
            ps.setBigDecimal(3, sanPhamChiTietToUpdate.getGia());
            ps.setString(4, sanPhamChiTietToUpdate.getNgonNgu());
            ps.setInt(5, sanPhamChiTietToUpdate.getSoTrang());
            ps.setString(6, sanPhamChiTietToUpdate.getNhaXuatBan());
            ps.setInt(7, sanPhamChiTietToUpdate.getNamXuatBan());
            ps.setInt(8, sanPhamChiTietToUpdate.getLanTaiBan());
            ps.setInt(9, sanPhamChiTietToUpdate.getMaSPCT());

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

    public List<SanPhamChiTiet> findSanPhamChiTietByMaSP(int maSP) {
        List<SanPhamChiTiet> sanPhamChiTietList = new ArrayList<>();

        try {
            Connection conn = DBconnect.getConnection();

            String query = "SELECT spct.maSPCT, tg.ten AS tenTacGia, tl.ten AS tenTheLoai, spct.ten, spct.gia, "
                    + "spct.ngonNgu, spct.soTrang, spct.nhaXuatBan, spct.namXuatBan, spct.lanTaiBan "
                    + "FROM SanPhamChiTiet spct "
                    + "INNER JOIN TacGia tg ON spct.MaTacGia = tg.maTacGia "
                    + "INNER JOIN TheLoai tl ON spct.MaTheLoai = tl.MaTheLoai "
                    + "WHERE spct.maSPCT = ?";
            PreparedStatement ps = conn.prepareStatement(query);
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

                sanPhamChiTietList.add(spct);
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

        return sanPhamChiTietList;
    }

    public SanPhamChiTiet getSanPhamChiTietById(int maSPCT) {
        String sql = "SELECT * FROM SanPhamChiTiet WHERE maSPCT = ?";

        try (Connection con = DBconnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maSPCT);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
                sanPhamChiTiet.setMaSPCT(rs.getInt("maSPCT"));
                sanPhamChiTiet.setTen(rs.getString("ten"));
                sanPhamChiTiet.setGia(rs.getBigDecimal("gia"));
                // Set các thông tin khác của sản phẩm chi tiết từ ResultSet
                return sanPhamChiTiet;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void giamSoLuongSanPhamChiTiet(int maSPCT, int soLuongNhap) {
        try {
            Connection conn = DBconnect.getConnection();

            // Lấy số lượng hiện tại của SanPhamChiTiet từ cơ sở dữ liệu
            String query = "SELECT soLuong FROM SanPhamChiTiet WHERE maSPCT = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maSPCT);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int soLuongHienTai = rs.getInt("soLuong");
                // Kiểm tra xem có đủ số lượng để giảm không
                if (soLuongHienTai >= soLuongNhap) {
                    // Giảm số lượng hiện tại bằng số lượng nhập vào
                    int soLuongMoi = soLuongHienTai - soLuongNhap;

                    // Cập nhật số lượng mới vào cơ sở dữ liệu
                    String updateQuery = "UPDATE SanPhamChiTiet SET soLuong = ? WHERE maSPCT = ?";
                    PreparedStatement updatePs = conn.prepareStatement(updateQuery);
                    updatePs.setInt(1, soLuongMoi);
                    updatePs.setInt(2, maSPCT);
                    updatePs.executeUpdate();
                } else {
                    // Xử lý khi số lượng không đủ để giảm
                    System.out.println("Số lượng không đủ để giảm.");
                }
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            // Xử lý các ngoại lệ khác nếu cần
            e.printStackTrace();
        }
    }

}
