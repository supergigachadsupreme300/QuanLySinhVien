package BusinessLogicLayer;

import DataAcessLayer.ChiTietTietDAL;
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
    public String themChiTietTiet(ChiTietTiet ct) {
        if (ct == null) return "Dữ liệu chi tiết tiết không hợp lệ!";
        if (ctDAL.findByMaChiTiet(ct.getMaChiTiet()) != null) {
            return "Mã chi tiết tiết đã tồn tại!";
        }
        return ctDAL.insert(ct) ? "Thêm chi tiết tiết thành công!" 
                                : "Thêm chi tiết tiết thất bại!";
    }

    // ===== SỬA =====
    public String suaChiTietTiet(ChiTietTiet ct) {
        if (ct == null) return "Dữ liệu chi tiết tiết không hợp lệ!";
        return ctDAL.update(ct) ? "Sửa chi tiết tiết thành công!" 
                                : "Sửa chi tiết tiết thất bại!";
    }

    // ===== XÓA =====
    public String xoaChiTietTiet(String maChiTiet) {
        return ctDAL.delete(maChiTiet) ? "Xóa chi tiết tiết thành công!" 
                                       : "Xóa chi tiết tiết thất bại!";
    }

    // ===== TÌM THEO MÃ =====
    public ChiTietTiet findByMaChiTiet(String maChiTiet) {
        return ctDAL.findByMaChiTiet(maChiTiet);
    }
    
    public List<ChiTietTiet> getByMaTKB(String maTKB) {
        return ctDAL.getByMaTKBByProc(maTKB);
    }

}
