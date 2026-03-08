package BusinessLogicLayer;

import DAO.ChiTietMonDAO;
import DAO.DiemDAL;
import DAO.HocKyDAL;
import DAO.HocSinhDAO;
import DataObject.Diem;
import java.sql.Connection;
import java.util.List;

public class DiemBLL {
    private DiemDAL dao;
    private Connection con;

    public DiemBLL(Connection con) {
        this.con = con;
        dao = new DiemDAL(con);
    }

    public List<Diem> getByMaHS(String maHS) {
        return dao.getByMaHS(maHS);
    }

    public String them(Diem d) {
        if (d == null) return "Dữ liệu điểm không hợp lệ.";

        // Validate related entities to avoid FK errors
        HocSinhDAO hsDao = new HocSinhDAO(con);
        if (hsDao.getById(d.getMaHS()) == null) {
            return "Không tìm thấy Học sinh với mã: " + d.getMaHS();
        }

        ChiTietMonDAO ctDao = new ChiTietMonDAO(con);
        if (ctDao.getByMa(d.getMaChiTiet()) == null) {
            return "Không tìm thấy Chi tiết môn với mã: " + d.getMaChiTiet();
        }

        HocKyDAL hkDao = new HocKyDAL(con);
        if (hkDao.findByMaHK(d.getMaHocKy()) == null) {
            return "Không tìm thấy Học kỳ với mã: " + d.getMaHocKy();
        }

        // Basic length checks to avoid truncation errors (adjust if schema allows longer)
        if (d.getMaDiem() != null && d.getMaDiem().length() > 50) {
            return "Mã điểm quá dài (>50 ký tự). Vui lòng kiểm tra quy tắc sinh mã.";
        }

        boolean ok = dao.add(d);
        return ok ? "Thêm điểm thành công!" : "Thêm điểm thất bại! (Kiểm tra ràng buộc dữ liệu)";
    }

    public String sua(Diem d) {
        if (d == null) return "Dữ liệu điểm không hợp lệ.";

        HocSinhDAO hsDao = new HocSinhDAO(con);
        if (hsDao.getById(d.getMaHS()) == null) {
            return "Không tìm thấy Học sinh với mã: " + d.getMaHS();
        }

        ChiTietMonDAO ctDao = new ChiTietMonDAO(con);
        if (ctDao.getByMa(d.getMaChiTiet()) == null) {
            return "Không tìm thấy Chi tiết môn với mã: " + d.getMaChiTiet();
        }

        HocKyDAL hkDao = new HocKyDAL(con);
        if (hkDao.findByMaHK(d.getMaHocKy()) == null) {
            return "Không tìm thấy Học kỳ với mã: " + d.getMaHocKy();
        }

        boolean ok = dao.update(d);
        return ok ? "Cập nhật điểm thành công!" : "Cập nhật điểm thất bại! (Kiểm tra ràng buộc dữ liệu)";
    }

    public String xoa(String maDiem) {
        boolean ok = dao.delete(maDiem);
        return ok ? "Xóa điểm thành công!" : "Xóa điểm thất bại!";
    }
}