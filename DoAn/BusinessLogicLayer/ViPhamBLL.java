package BusinessLogicLayer;

import DataAcessLayer.ViPhamDAO;
import DataObject.ViPham;
import java.util.List;

public class ViPhamBLL {

    private ViPhamDAO viPhamDAO;

    public ViPhamBLL() {
        viPhamDAO = new ViPhamDAO();
    }

    // ===== GET ALL =====
    public List<ViPham> getAll() {
        return viPhamDAO.getAll();
    }

    // ===== GET BY ID =====
    public ViPham getById(String maViPham) {
        if (maViPham == null || maViPham.trim().isEmpty()) {
            return null;
        }
        return viPhamDAO.getById(maViPham);
    }

    // ===== GET BY MA HOC SINH =====
    public List<ViPham> getByMaHS(String maHS) {
        if (maHS == null || maHS.trim().isEmpty()) {
            return null;
        }
        return viPhamDAO.getByMaHS(maHS);
    }

    // ===== ADD =====
    public boolean add(ViPham vp) {
        if (!validate(vp)) return false;

        if (viPhamDAO.getById(vp.getMaViPham()) != null) {
            return false; // Trùng mã
        }

        return viPhamDAO.add(vp);
    }

    // ===== UPDATE =====
    public boolean update(ViPham vp) {
        if (!validate(vp)) return false;

        return viPhamDAO.update(vp);
    }

    // ===== DELETE =====
    public boolean delete(String maViPham) {
        if (maViPham == null || maViPham.trim().isEmpty()) return false;

        return viPhamDAO.delete(maViPham);
    }

    // ===== VALIDATE DATA =====
    private boolean validate(ViPham vp) {
        if (vp == null) return false;

        if (vp.getMaViPham() == null || vp.getMaViPham().trim().isEmpty()) return false;
        if (vp.getMaHS() == null || vp.getMaHS().trim().isEmpty()) return false;
        if (vp.getMaHocKy() == null || vp.getMaHocKy().trim().isEmpty()) return false;
        if (vp.getNgayViPham() == null) return false;
        if (vp.getNoiDung() == null || vp.getNoiDung().trim().isEmpty()) return false;
        if (vp.getMucDo() == null || vp.getMucDo().trim().isEmpty()) return false;

        return true;
    }
}