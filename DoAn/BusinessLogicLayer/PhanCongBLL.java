package BusinessLogicLayer;

import DAO.PhanCongDAL;
import DataObject.PhanCong;
import java.util.List;

public class PhanCongBLL {
    // Tạo sẵn DAL bên trong BUS
    PhanCongDAL pcDAL = new PhanCongDAL();

    // ===== GET ALL =====
    public List<PhanCong> getAll() {
        return pcDAL.getAll();
    }

    // ===== GET ALL ACTIVE =====
    public List<PhanCong> getAllActive() {
        return pcDAL.getAllActive();
    }

    // ===== THÊM =====
    public boolean themPhanCong(PhanCong pc) {
        if (pc == null) return false;

        // kiểm tra trùng mã
        if (pcDAL.findByMaPC(pc.getMaPC()) != null) {
            System.out.println("Mã phân công đã tồn tại!");
            return false;
        }
        return pcDAL.insert(pc);
    }

    // ===== SỬA =====
    public boolean suaPhanCong(PhanCong pc) {
        if (pc == null) return false;
        return pcDAL.update(pc);
    }

    // ===== XÓA =====
    public boolean xoaPhanCong(String maPC) {
        return pcDAL.delete(maPC);
    }

    // ===== TÌM THEO MÃ =====
    public PhanCong getByMaPC(String maPC) {
        return pcDAL.findByMaPC(maPC);
    }
}
