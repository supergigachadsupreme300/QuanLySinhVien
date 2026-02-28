package BusinessLogicLayer;

import DataAcessLayer.HocSinhDAL;
import DataObject.HocSinh;
import java.sql.Connection;
import java.util.List;

public class HocSinhBLL {
    // Tạo sẵn đối tượng DAL bên trong BUS
    HocSinhDAL hsDAL = new HocSinhDAL();

    public HocSinhBLL(Connection con) {
        this.hsDAL = new HocSinhDAL(con);
    }
    
    public List<HocSinh> getAll() {
        return hsDAL.getAll();
    }
    
    public List<HocSinh> getAllActive() {
        return hsDAL.getAllActive();
    }
    
    // ===== GET BY MA LOP =====
    public List<HocSinh> getByMaLop(String maLop) {
        return hsDAL.getByMaLop(maLop);
    }

    // ===== THÊM =====
    public String themHocSinh(HocSinh hs) {
        if (hs == null) return "Dữ liệu học sinh không hợp lệ!";
        if (hsDAL.findByMaHS(hs.getMaHS()) != null) {
            return "Mã học sinh đã tồn tại!";
        }
        boolean ok = hsDAL.insert(hs);
        return ok ? "Thêm học sinh thành công!" : "Thêm học sinh thất bại!";
    }

    // ===== SỬA =====
    public String suaHocSinh(HocSinh hs) {
        if (hs == null) return "Dữ liệu học sinh không hợp lệ!";
        boolean ok = hsDAL.update(hs);
        return ok ? "Cập nhật học sinh thành công!" : "Cập nhật học sinh thất bại!";
    }

    // ===== XÓA =====
    public String xoaHocSinh(String maHS) {
        HocSinh hs = hsDAL.findByMaHS(maHS);
        boolean ok = hsDAL.delete(maHS);
        if (ok && hs != null) {
            return "Xóa học sinh thành công!";
        } else {
            return "Xóa học sinh thất bại!";
        }
    }
}
