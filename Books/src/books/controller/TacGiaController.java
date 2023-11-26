
package books.controller;

import books.model.TacGia;
import books.service.TacGiaService;
import java.util.List;

/**
 *
 * @author Admin
 */
public class TacGiaController {
     private TacGiaService service;

    public TacGiaController() {
        service = new TacGiaService();
    }

    public List<TacGia> getAllTacGia() {
        return service.getAllTacGia();
    }
    public void addTacGia(TacGia tacGia) {

        service.addTacGia(tacGia);
    }
    public void deleteTacGia(int maTacGia){
        service.deleteTacGia(maTacGia);
    }
    public void updateTacGia(TacGia update){
        service.updateTacGia(update);
    }
}
