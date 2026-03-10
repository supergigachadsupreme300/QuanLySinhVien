package BusinessLogicLayer;

import DAO.ChiTietMonDAO;
import DataObject.ChiTietMon;
import java.util.List;


public class ChiTietMonBLL {

    private final ChiTietMonDAO dao;

    public ChiTietMonBLL() {
        this.dao = new ChiTietMonDAO();
    }

    public List<ChiTietMon> getAll() {
        return dao.getAll();
    }

    public boolean themChiTietMon(ChiTietMon ct) {
        if (ct == null) {
            return false;
        }

        String error = validate(ct);
        if (error != null) {
            System.err.println("Validate lỗi: " + error);
            return false;
        }

        if (getByMa(ct.getMaChiTiet()) != null) {
            System.err.println("Mã chi tiết đã tồn tại: " + ct.getMaChiTiet());
            return false;
        }

        return dao.them(ct);
    }

    public boolean suaChiTietMon(ChiTietMon ct) {
        if (ct == null || ct.getMaChiTiet() == null || ct.getMaChiTiet().trim().isEmpty()) {
            return false;
        }

        String error = validate(ct);
        if (error != null) {
            System.err.println("Validate lỗi khi sửa: " + error);
            return false;
        }

        return dao.sua(ct);
    }

    public boolean xoaChiTietMon(String maChiTiet) {
        if (maChiTiet == null || maChiTiet.trim().isEmpty()) {
            return false;
        }

        return dao.xoa(maChiTiet);
    }


    public ChiTietMon getByMa(String maChiTiet) {
        if (maChiTiet == null || maChiTiet.trim().isEmpty()) {
            return null;
        }
        return dao.getByMa(maChiTiet);
    }


    private String validate(ChiTietMon ct) {
        if (ct.getMaChiTiet() == null || ct.getMaChiTiet().trim().isEmpty()) {
            return "Mã chi tiết không được để trống";
        }
        if (ct.getMaMon() == null || ct.getMaMon().trim().isEmpty()) {
            return "Mã môn học không được để trống";
        }
        if (ct.getTenChiTiet() == null || ct.getTenChiTiet().trim().isEmpty()) {
            return "Tên chi tiết không được để trống";
        }
        if (ct.getHeSo() <= 0) {
            return "Hệ số phải là số nguyên dương (> 0)";
        }

        return null; 
    }
}