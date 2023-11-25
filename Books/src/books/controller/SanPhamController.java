/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.controller;

import books.model.SanPham;
import books.service.SanPhamService;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SanPhamController {

    private SanPhamService service;

    public SanPhamController() {
        service = new SanPhamService();
    }

    public List<SanPham> getAllSanPham() {
        return service.getAllSanPham();
    }

    public void addSanPham(SanPham sanPham) {

        service.addSanPham(sanPham);
    }

    public void updateSanPham(SanPham sanPhamToUpdate) {
        service.updateSanPham(sanPhamToUpdate);
    }

    public SanPham getSanPhamByTen(String tenSanPham) {
        return service.getSanPhamByTen(tenSanPham);

    }
}
