/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.service;

import books.connect.DBconnect;
import books.model.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SanPhamService {

    private List<SanPham> sanPham;

    public SanPhamService() {
        sanPham = new ArrayList<>();
    }

    public List<SanPham> getAllSanPham() {
        try {
            List<SanPham> list = new ArrayList<>();
            Connection conn = DBconnect.getConnection();

            String sql = "select maSP, ten, soLuong from SanPham";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSP(rs.getInt("maSP"));
                sp.setTen(rs.getString("ten"));
                sp.setSoLuong(rs.getInt("soLuong"));
                list.add(sp);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSanPham(SanPham sanPham) {
        Connection conn = DBconnect.getConnection();
        String sql = "INSERT INTO SanPham (ten, soLuong) VALUES (?,?)";
        try {
            // Tạo PreparedStatement
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sanPham.getTen());
            ps.setInt(2, sanPham.getSoLuong());
            // Thực thi câu lệnh SQL để thêm tác giả vào cơ sở dữ liệu
            ps.executeUpdate();
            // Đóng kết nối và các đối tượng liên quan
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSanPham(SanPham sanPhamToUpdate) {
        Connection conn = null;
        try {
            conn = DBconnect.getConnection();
            String sql = "UPDATE SanPham SET ten = ?, soLuong = ? WHERE maSP = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sanPhamToUpdate.getTen());
            ps.setInt(2, sanPhamToUpdate.getSoLuong());
            ps.setInt(3, sanPhamToUpdate.getMaSP());
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SanPham getSanPhamByTen(String tenSanPham) {
        // Giả sử bạn có một danh sách sản phẩm có sẵn
        List<SanPham> danhSachSanPham = getAllSanPham(); // Gọi hàm để lấy danh sách sản phẩm

        // Duyệt qua danh sách để tìm sản phẩm với tên tương ứng
        for (SanPham sanPham : danhSachSanPham) {
            if (sanPham.getTen().equals(tenSanPham)) {
                return sanPham; // Trả về sản phẩm tìm thấy
            }
        }

        // Trả về null nếu không tìm thấy sản phẩm
        return null;
    }

    public List<SanPham> getSanPhamByTenSP(String tenSanPham) {
        List<SanPham> danhSachTimThay = new ArrayList<>();

        try {
            // Sử dụng try-with-resources để đảm bảo kết nối được đóng ngay sau khi sử dụng
            try (Connection conn = DBconnect.getConnection(); PreparedStatement ps = conn.prepareStatement("SELECT maSP, ten, soLuong FROM SanPham WHERE ten LIKE ?")) {
                ps.setString(1, "%" + tenSanPham + "%"); // Sử dụng LIKE để tìm tên gần đúng
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // Lấy thông tin từ cột của kết quả truy vấn
                        int maSP = rs.getInt("maSP");
                        String ten = rs.getString("ten");
                        int soLuong = rs.getInt("soLuong");

                        // Tạo đối tượng SanPham chỉ với các thuộc tính cần thiết
                        SanPham sanPham = new SanPham(maSP, ten, soLuong);

                        // Thêm sản phẩm vào danh sách
                        danhSachTimThay.add(sanPham);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý ngoại lệ một cách phù hợp (ví dụ: hiển thị thông báo lỗi)
        }

        return danhSachTimThay;
    }

}
