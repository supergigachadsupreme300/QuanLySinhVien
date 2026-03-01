package BusinessLogicLayer;

import DAO.MonHocDAO;
import DataObject.Mon;
import java.util.List;

/**
 * Lớp xử lý logic nghiệp vụ cho Môn Học
 */
public class MonHocBLL {

    private final MonHocDAO dao;

    public MonHocBLL() {
        this.dao = new MonHocDAO();
    }

    /**
     * Lấy toàn bộ danh sách môn học
     */
    public List<Mon> getAll() {
        return dao.getAll();
    }

    /**
     * Thêm mới một môn học
     * @param mh đối tượng Mon cần thêm
     * @return true nếu thành công, false nếu thất bại hoặc vi phạm ràng buộc
     */
    public boolean themMonHoc(Mon mh) {
        if (mh == null) {
            return false;
        }

        // Validate dữ liệu
        String error = validate(mh);
        if (error != null) {
            System.err.println("Validate lỗi: " + error);
            return false;
        }

        // Kiểm tra trùng mã môn
        if (getByMa(mh.getMaMon()) != null) {
            System.err.println("Mã môn học đã tồn tại: " + mh.getMaMon());
            return false;
        }

        return dao.them(mh);
    }

    /**
     * Sửa thông tin môn học
     * @param mh đối tượng đã cập nhật (phải có mã môn)
     * @return true nếu sửa thành công
     */
    public boolean suaMonHoc(Mon mh) {
        if (mh == null || mh.getMaMon() == null || mh.getMaMon().trim().isEmpty()) {
            return false;
        }

        String error = validate(mh);
        if (error != null) {
            System.err.println("Validate lỗi khi sửa: " + error);
            return false;
        }

        return dao.sua(mh);
    }

    /**
     * Xóa môn học theo mã (soft-delete)
     * @param maMon mã môn cần xóa
     * @return true nếu xóa thành công
     */
    public boolean xoaMonHoc(String maMon) {
        if (maMon == null || maMon.trim().isEmpty()) {
            return false;
        }

     

        return dao.xoa(maMon);
    }

    /**
     * Lấy môn học theo mã
     * @param maMon mã môn cần tìm
     * @return đối tượng hoặc null nếu không tìm thấy
     */
    public Mon getByMa(String maMon) {
        if (maMon == null || maMon.trim().isEmpty()) {
            return null;
        }
        return dao.getByMa(maMon);
    }

    // ────────────────────────────────────────────────
    //               HÀM VALIDATE NGHIỆP VỤ
    // ────────────────────────────────────────────────

    private String validate(Mon mh) {
        if (mh.getMaMon() == null || mh.getMaMon().trim().isEmpty()) {
            return "Mã môn học không được để trống";
        }
        if (mh.getTenMon() == null || mh.getTenMon().trim().isEmpty()) {
            return "Tên môn học không được để trống";
        }
        if (mh.getSoTinChi() <= 0) {
            return "Số tín chỉ phải là số nguyên dương (> 0)";
        }
        if (mh.getKhoa() == null || mh.getKhoa().trim().isEmpty()) {
            return "Khoa không được để trống";
        }

       
        return null; // hợp lệ
    }
}