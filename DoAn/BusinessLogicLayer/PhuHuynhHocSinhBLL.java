package BusinessLogicLayer;

import DAO.PhuHuynhHocSinhDAO;
import DataObject.PhuHuynhHocSinh;
import java.util.List;

public class PhuHuynhHocSinhBLL {
    private PhuHuynhHocSinhDAO dao;

    public PhuHuynhHocSinhBLL() {
        this.dao = new PhuHuynhHocSinhDAO();
    }

    public boolean themQuanHe(PhuHuynhHocSinh p) {
        if (p == null) return false;
        if (p.getMaHS() == null || p.getMaHS().trim().isEmpty() ||
            p.getMaPH() == null || p.getMaPH().trim().isEmpty()) {
            return false;
        }
        // Kiểm tra tồn tại? Có thể cho phép thêm nếu chưa có, hoặc cập nhật nếu đã có (soft delete)
        PhuHuynhHocSinh existing = dao.getById(p.getMaHS(), p.getMaPH());
        if (existing != null) {
            // Nếu đã tồn tại nhưng đang inactive, có thể reactivate
            if (existing.getTrangThai() == 0) {
                existing.setTrangThai(1);
                existing.setQuanHe(p.getQuanHe());
                return dao.update(existing);
            }
            return false; // Đã tồn tại active
        }
        p.setTrangThai(1);
        return dao.add(p);
    }

    public boolean suaQuanHe(PhuHuynhHocSinh p) {
        if (p == null) return false;
        return dao.update(p);
    }

    public boolean xoaQuanHe(String maHS, String maPH) {
        return dao.delete(maHS, maPH);
    }

    public List<PhuHuynhHocSinh> layTheoPH(String maPH) {
        return dao.getByMaPH(maPH);
    }

    public List<PhuHuynhHocSinh> layTheoHS(String maHS) {
        return dao.getByMaHS(maHS);
    }

    public PhuHuynhHocSinh layMotQuanHe(String maHS, String maPH) {
        return dao.getById(maHS, maPH);
    }
}