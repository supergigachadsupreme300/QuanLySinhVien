package BusinessLogicLayer;

import DAO.LopDAL;
import DataObject.Lop;
import java.sql.Connection;
import java.util.List;

public class LopBLL {
    LopDAL lopDAL;

    public LopBLL() {
        this.lopDAL = new LopDAL(); 
    }

    public LopBLL(Connection con) {
        this.lopDAL = new LopDAL(con);
    }

    // ===== GET ALL =====
    public List<Lop> getAll() {
        return lopDAL.getAll();
    }

    // ===== GET ALL ACTIVE =====
    public List<Lop> getAllActive() {
        return lopDAL.getAllActive();
    }

    public List<Lop> getAllActiveProc(){
        return lopDAL.getAllActiveByProc();
    }

    // ===== THÊM =====
    public String themLop(Lop lop) {
        if (lop == null) return "Dữ liệu lớp không hợp lệ!";
        
        // kiểm tra trùng mã
        if (lopDAL.findByMaLop(lop.getMaLop()) != null) {
            return "Mã lớp đã tồn tại!";
        }
        
        boolean result = lopDAL.insert(lop);
        return result ? "Thêm lớp thành công!" : "Thêm lớp thất bại!";
    }

    // ===== SỬA =====
    public String suaLop(Lop lop) {
        if (lop == null) return "Dữ liệu lớp không hợp lệ!";
        
        boolean result = lopDAL.update(lop);
        return result ? "Cập nhật lớp thành công!" : "Cập nhật lớp thất bại!";
    }

    // ===== XÓA =====
    public String xoaLop(String maLop) {
        boolean result = lopDAL.delete(maLop);
        return result ? "Xóa lớp thành công!" : "Xóa lớp thất bại!";
    }

    // ===== TÌM THEO MÃ =====
    public Lop getByMaLop(String maLop) {
        return lopDAL.findByMaLop(maLop);
    }
}
