package BusinessLogicLayer;

import DAO.ChiTietMonDAO;
import DataObject.ChiTietMon;
import java.util.List;

/**
 * Lớp xử lý logic nghiệp vụ (Business Logic Layer) cho Chi Tiết Môn học
 */
public class ChiTietMonBLL {

    private final ChiTietMonDAO dao;

    public ChiTietMonBLL() {
        this.dao = new ChiTietMonDAO();
    }

    /**
     * Lấy toàn bộ danh sách chi tiết môn học
     * 
     * @return danh sách hoặc rỗng nếu không có dữ liệu
     */
    public List<ChiTietMon> getAll() {
        return dao.getAll();
    }

    /**
     * Thêm mới một chi tiết môn học
     * 
     * @param ct đối tượng cần thêm
     * @return true nếu thêm thành công, false nếu thất bại hoặc vi phạm ràng buộc
     */
    public boolean themChiTietMon(ChiTietMon ct) {
        if (ct == null) {
            return false;
        }

        // Validate dữ liệu trước khi thêm
        String error = validate(ct);
        if (error != null) {
            // Trong phiên bản nâng cao có thể throw exception hoặc trả về message
            System.err.println("Validate lỗi: " + error);
            return false;
        }

        // Kiểm tra trùng mã chi tiết (ràng buộc nghiệp vụ phổ biến)
        if (getByMa(ct.getMaChiTiet()) != null) {
            System.err.println("Mã chi tiết đã tồn tại: " + ct.getMaChiTiet());
            return false;
        }

        return dao.them(ct);
    }

    /**
     * Sửa thông tin chi tiết môn học
     * 
     * @param ct đối tượng đã cập nhật (phải có mã chi tiết)
     * @return true nếu sửa thành công
     */
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

    /**
     * Xóa chi tiết môn học theo mã
     * 
     * @param maChiTiet mã cần xóa
     * @return true nếu xóa thành công
     */
    public boolean xoaChiTietMon(String maChiTiet) {
        if (maChiTiet == null || maChiTiet.trim().isEmpty()) {
            return false;
        }

        return dao.xoa(maChiTiet);
    }

    /**
     * Lấy chi tiết môn theo mã (dùng để kiểm tra tồn tại hoặc hiển thị chi tiết)
     * 
     * @param maChiTiet mã cần tìm
     * @return đối tượng hoặc null nếu không tìm thấy
     */
    public ChiTietMon getByMa(String maChiTiet) {
        if (maChiTiet == null || maChiTiet.trim().isEmpty()) {
            return null;
        }
        return dao.getByMa(maChiTiet);
    }

    // ────────────────────────────────────────────────
    // HÀM VALIDATE NGHIỆP VỤ
    // ────────────────────────────────────────────────

    /**
     * Kiểm tra dữ liệu hợp lệ
     * 
     * @param ct đối tượng cần kiểm tra
     * @return null nếu hợp lệ, hoặc chuỗi lỗi nếu không hợp lệ
     */
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

        return null; // hợp lệ
    }
}