/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class HoaDonChiTiet {

    private int maHDCT;
    private HoaDon hoaDon;
    private SanPhamChiTiet sanPhamChiTiet;
    private String tenSP;
    private int soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;
    private String trangThai;
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime updateAt;
    private String updateBy;
    private boolean deleted;
    private String theLoai;
    private String tacGia;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int maHDCT, HoaDon hoaDon, SanPhamChiTiet sanPhamChiTiet, String tenSP, int soLuong, BigDecimal donGia, BigDecimal thanhTien, String trangThai, LocalDateTime createAt, String createBy, LocalDateTime updateAt, String updateBy, boolean deleted, String theLoai, String tacGia) {
        this.maHDCT = maHDCT;
        this.hoaDon = hoaDon;
        this.sanPhamChiTiet = sanPhamChiTiet;
        this.tenSP = tenSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
        this.createAt = createAt;
        this.createBy = createBy;
        this.updateAt = updateAt;
        this.updateBy = updateBy;
        this.deleted = deleted;
        this.theLoai = theLoai;
        this.tacGia = tacGia;
    }

    public int getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public SanPhamChiTiet getSanPhamChiTiet() {
        return sanPhamChiTiet;
    }

    public void setSanPhamChiTiet(SanPhamChiTiet sanPhamChiTiet) {
        this.sanPhamChiTiet = sanPhamChiTiet;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public BigDecimal getThanhTien() {
        BigDecimal soLuongDecimal = new BigDecimal(soLuong);
        return donGia.multiply(soLuongDecimal);
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public void setSanPhamChiTiet(int maSPCT) {
        if (this.sanPhamChiTiet == null) {
            this.sanPhamChiTiet = new SanPhamChiTiet();
        }
        this.sanPhamChiTiet.setMaSPCT(maSPCT);
    }
    public void setHoaDon(int maHD) {
        if (this.hoaDon == null) {
            this.hoaDon = new HoaDon();
        }
        this.hoaDon.setMaHD(maHD);
    }

    public void setTenSPCT(String tenSPCT) {
        if (this.sanPhamChiTiet == null) {
            this.sanPhamChiTiet = new SanPhamChiTiet();
        }
        this.sanPhamChiTiet.setTen(tenSPCT);
    }

}
