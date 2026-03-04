package BusinessLogicLayer;

import DAO.HocSinhDAO;
import DataObject.HocSinh;
import java.sql.Connection;
import java.util.List;

public class HocSinhBLL {
    // Tạo sẵn đối tượng DAL bên trong BUS
    HocSinhDAO hsDAL = new HocSinhDAO();

    public HocSinhBLL() {
        // sử dụng constructor mặc định của HocSinhDAO (tự mở connection)
        this.hsDAL = new HocSinhDAO();
    }

    public HocSinhBLL(Connection con) {
        this.hsDAL = new HocSinhDAO(con);
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
    public boolean themHocSinh(HocSinh hs) {
        if (hs == null) return false;
        if (hsDAL.findByMaHS(hs.getMaHS()) != null) {
            return false;
        }
        return hsDAL.add(hs);
    }

    // ===== SỬA =====
    public boolean suaHocSinh(HocSinh hs) {
        if (hs == null) return false;
        return hsDAL.update(hs);
    }

    // ===== XÓA =====
    public boolean xoaHocSinh(String maHS) {
        HocSinh hs = hsDAL.findByMaHS(maHS);
        boolean ok = hsDAL.delete(maHS);
        return ok && hs != null;
    }

    public HocSinh getByMa(String maHS) {
        return hsDAL.getById(maHS);
    }
}
