/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.controller;

import books.service.HoaDonChiTietService;
import books.model.HoaDonChiTiet;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonChiTietController {

    private HoaDonChiTietService hoaDonService;

    public HoaDonChiTietController() {
        hoaDonService = new HoaDonChiTietService();
    }

//    public List<HoaDonChiTiet> getAllHoaDonChiTiet() {
//        return hoaDonService.getAllHoaDonChiTiet();
//    }

//    public void addHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
//        hoaDonService.addHoaDonChiTiet(hoaDonChiTiet);
//    }
//
//    public void deleteHoaDonChiTiet(int maHDCT) {
//        hoaDonService.deleteHoaDonChiTiet(maHDCT);
//    }
//
//    public void updateHoaDonChiTiet(HoaDonChiTiet hoaDonChiTiet) {
//        hoaDonService.updateHoaDonChiTiet(hoaDonChiTiet);
//    }
}
