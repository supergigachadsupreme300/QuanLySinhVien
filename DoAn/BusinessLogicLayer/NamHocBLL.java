package BusinessLogicLayer;

import DAO.NamHocDAL;
import DataObject.NamHoc;
import java.util.List;

public class NamHocBLL {
    // Tạo sẵn đối tượng DAL bên trong BUS
    NamHocDAL nhDAL = new NamHocDAL();

    // ===== GET ALL =====
    public List<NamHoc> getAll() {
        return nhDAL.getAll();
    }

    public List<NamHoc> getAllActive() {
        return nhDAL.getAllActive();
    }

    public List<NamHoc> getAllActiveByProc() {
        return nhDAL.getAllActiveByProc();
    }

    
    // ===== THÊM =====
    public boolean themNamHoc(NamHoc nh) {
        if (nh == null) return false;

        // kiểm tra trùng mã
        if (nhDAL.findByMaNH(nh.getMaNH()) != null) {
            System.out.println("Mã năm học đã tồn tại!");
            return false;
        }
        return nhDAL.insert(nh);
    }

    // ===== SỬA =====
    public boolean suaNamHoc(NamHoc nh) {
        if (nh == null) return false;
        return nhDAL.update(nh);
    }

    // ===== XÓA =====
    public boolean xoaNamHoc(String maNH) {
        return nhDAL.delete(maNH);
    }

    // ===== TÌM THEO MÃ =====
    public NamHoc getByMaNH(String maNH) {
        return nhDAL.findByMaNH(maNH);
    }
}
