package BusinessLogicLayer;

import DAO.ChiTietTietDAL;
import DataObject.ChiTietTiet;
import java.util.List;

public class ChiTietTietBLL {
    ChiTietTietDAL ctDAL = new ChiTietTietDAL();

    // ===== GET ALL =====
    public List<ChiTietTiet> getAll() {
        return ctDAL.getAll();
    }

    // ===== GET ALL ACTIVE =====
    public List<ChiTietTiet> getAllActive() {
        return ctDAL.getAllActive();
    }

    public List<ChiTietTiet> getAllActiveProc() {
        return ctDAL.getAllActiveProc();
    }
    
    // ===== GET BY MA TKB =====
    public List<ChiTietTiet> getByMaTKBByProc(String maTKB) {
        return ctDAL.getByMaTKBByProc(maTKB);
    }
    

    // ===== THÊM =====
    public Boolean themChiTietTiet(ChiTietTiet ct) {
        if (ct == null) return false;
        if (ctDAL.findByMaChiTiet(ct.getMaChiTiet()) != null) {
            return false;
        }
        return ctDAL.insert(ct);
    }

    // ===== SỬA =====
    public Boolean suaChiTietTiet(ChiTietTiet ct) {
        if (ct == null) return false;
        return ctDAL.update(ct);
    }

    // ===== XÓA =====
    public Boolean xoaChiTietTiet(String maChiTiet) {
        return ctDAL.delete(maChiTiet);
    }

    // ===== TÌM THEO MÃ =====
    public ChiTietTiet findByMaChiTiet(String maChiTiet) {
        return ctDAL.findByMaChiTiet(maChiTiet);
    }
    
    public List<ChiTietTiet> getByMaTKB(String maTKB) {
        return ctDAL.getByMaTKBByProc(maTKB);
    }

}
