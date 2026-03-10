package BusinessLogicLayer;

import DAO.PhanCongDAL;
import DataObject.PhanCong;
import java.util.List;

public class PhanCongBLL {

    PhanCongDAL pcDAL = new PhanCongDAL();


    public List<PhanCong> getAll() {
        return pcDAL.getAll();
    }


    public List<PhanCong> getAllActive() {
        return pcDAL.getAllActive();
    }


    public boolean themPhanCong(PhanCong pc) {
        if (pc == null) return false;


        if (pcDAL.findByMaPC(pc.getMaPC()) != null) {
            System.out.println("Mã phân công đã tồn tại!");
            return false;
        }
        return pcDAL.insert(pc);
    }


    public boolean suaPhanCong(PhanCong pc) {
        if (pc == null) return false;
        return pcDAL.update(pc);
    }


    public boolean xoaPhanCong(String maPC) {
        return pcDAL.delete(maPC);
    }


    public PhanCong getByMaPC(String maPC) {
        return pcDAL.findByMaPC(maPC);
    }
}
