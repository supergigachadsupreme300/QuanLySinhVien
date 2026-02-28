package BusinessLogicLayer;

import DataAcessLayer.HocKyDAL;
import DataObject.HocKy;
import java.util.List;

public class HocKyBLL {
    // Tạo sẵn DAL bên trong BUS
    HocKyDAL hkDAL = new HocKyDAL();

    // ===== GET ALL =====
    public List<HocKy> getAll() {
        return hkDAL.getAll();
    }

    // ===== GET ALL ACTIVE =====
    public List<HocKy> getAllActive() {
        return hkDAL.getAllActive();
    }

    public List<HocKy> getAllActiveByProc() {
        return hkDAL.getAllActiveByProc();
    }

    
    // ===== THÊM =====
    public boolean themHocKy(HocKy hk) {
        if (hk == null) return false;
        if (hkDAL.findByMaHK(hk.getMaHK()) != null) {
            System.out.println("Mã học kỳ đã tồn tại!");
            return false;
        }
        return hkDAL.insert(hk);
    }

    // ===== SỬA =====
    public boolean suaHocKy(HocKy hk) {
        if (hk == null) return false;
        return hkDAL.update(hk);
    }

    // ===== XÓA =====
    public boolean xoaHocKy(String maHK) {
        return hkDAL.delete(maHK);
    }

    // ===== TÌM THEO MÃ =====
    public HocKy getByMaHK(String maHK) {
        return hkDAL.findByMaHK(maHK);
    }
}
