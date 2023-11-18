/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.controller;

import books.model.HoaDon;
import books.service.HoaDonService;
import java.util.List;

/**
 *
 * @author tranvandang
 */
public class HoaDonController {

    private HoaDonService service;

    public HoaDonController() {
        service = new HoaDonService();
    }
    public List<HoaDon> getAll() {
        return service.getAll();
    }

}
