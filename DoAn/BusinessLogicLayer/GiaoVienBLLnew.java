package BusinessLogicLayer;

import DataAcessLayer.GiaoVienDAL;
import DataObject.GiaoVien;
import java.util.List;

public class GiaoVienBLL {
    // Tạo sẵn DAL bên trong BUS
    GiaoVienDAL gvDAL = new GiaoVienDAL();

    // ===== GET ALL =====
    public List<GiaoVien> getAll() {
        return gvDAL.getAll();
    }

    public List<GiaoVien> getAllActive() {
        return gvDAL.getAllActive();
    }

    public List<GiaoVien> getAllActiveProc(){
        return gvDAL.getAllActiveByProc();
    }
    
    // ===== THÊM =====
    public boolean themGiaoVien(GiaoVien gv) {
        if (gv == null) return false;
        if (gvDAL.findByMaGV(gv.getMaGV()) != null) {
            System.out.println("Mã giáo viên đã tồn tại!");
            return false;
        }
        return gvDAL.insert(gv);
    }

    // ===== SỬA =====
    public boolean suaGiaoVien(GiaoVien gv) {
        if (gv == null) return false;
        return gvDAL.update(gv);
    }

    // ===== XÓA =====
    public boolean xoaGiaoVien(String maGV) {
        return gvDAL.delete(maGV);
    }

    // ===== TÌM THEO MÃ =====
    public GiaoVien getByMaGV(String maGV) {
        return gvDAL.findByMaGV(maGV);
    }
}
