/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package books.controller;

import books.model.TheLoai;
import books.service.TheLoaiService;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TheLoaiController {

    private TheLoaiService service;

    public TheLoaiController() {
        service = new TheLoaiService();
    }

    public List<TheLoai> getAllTheLoai() {
        return service.getAllTheLoai();
    }

    public void addTheLoai(TheLoai theLoai) {

        service.addTheLoai(theLoai);
    }

    public void deleteTheLoai(int maTheLoai) {
        service.deleteTheLoai(maTheLoai);
    }
     public void updateTheLoai(TheLoai update){
        service.updateTheLoai(update);
    }
}
