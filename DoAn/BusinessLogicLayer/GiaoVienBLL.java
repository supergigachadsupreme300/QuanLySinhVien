package BusinessLogicLayer;

import DAO.GiaoVienDAO;
import DataObject.GiaoVien;
import java.util.List;


public class GiaoVienBLL {

    private final GiaoVienDAO dao;

    public GiaoVienBLL() {
        this.dao = new GiaoVienDAO();
    }

    public List<GiaoVien> getAll() {
        return dao.getAll();
    }

    public boolean themGiaoVien(GiaoVien gv) {
        if (gv == null) {
            return false;
        }

        String error = validate(gv);
        if (error != null) {
            System.err.println("Validate lỗi: " + error);
            return false;
        }

        if (getByMa(gv.getMaGV()) != null) {
            System.err.println("Mã giáo viên đã tồn tại: " + gv.getMaGV());
            return false;
        }

        return dao.them(gv);
    }

    public boolean suaGiaoVien(GiaoVien gv) {
        if (gv == null || gv.getMaGV() == null || gv.getMaGV().trim().isEmpty()) {
            return false;
        }

        String error = validate(gv);
        if (error != null) {
            System.err.println("Validate lỗi khi sửa: " + error);
            return false;
        }

        return dao.sua(gv);
    }

    public boolean xoaGiaoVien(String maGV) {
        if (maGV == null || maGV.trim().isEmpty()) {
            return false;
        }

        return dao.xoa(maGV);
    }

    public GiaoVien getByMa(String maGV) {
        if (maGV == null || maGV.trim().isEmpty()) {
            return null;
        }
        return dao.getByMa(maGV);
    }

    public java.util.List<GiaoVien> getAllActiveProc() {
        return dao.getAllActiveByProc();
    }

    private String validate(GiaoVien gv) {
        if (gv.getMaGV() == null || gv.getMaGV().trim().isEmpty()) {
            return "Mã giáo viên không được để trống";
        }
        if (gv.getHoTen() == null || gv.getHoTen().trim().isEmpty()) {
            return "Họ tên không được để trống";
        }
        if (gv.getDienThoai() == null || gv.getDienThoai().trim().isEmpty()) {
            return "Số điện thoại không được để trống";
        }
        if (gv.getEmail() == null || gv.getEmail().trim().isEmpty()) {
            return "Email không được để trống";
        }

        // Validate định dạng email cơ bản
        if (!gv.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return "Email không đúng định dạng";
        }

        // Validate số điện thoại (10-11 số)
        if (!gv.getDienThoai().matches("\\d{10,11}")) {
            return "Số điện thoại phải là 10 hoặc 11 chữ số";
        }

        return null; // hợp lệ
    }
}