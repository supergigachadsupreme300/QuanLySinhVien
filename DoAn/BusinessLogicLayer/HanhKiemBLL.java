package BusinessLogicLayer;

import DAO.HanhKiemDAL;
import DataObject.HanhKiem;
import java.util.List;

public class HanhKiemBLL {

    private HanhKiemDAL hanhKiemDAL;

    public HanhKiemBLL() {
        hanhKiemDAL = new HanhKiemDAL();
    }

    // ===== LẤY THEO MÃ =====
    public HanhKiem getById(String maHanhKiem) {
        if (maHanhKiem == null || maHanhKiem.trim().isEmpty()) return null;
        return hanhKiemDAL.getById(maHanhKiem);
    }

    // ===== LẤY TẤT CẢ =====
    public List<HanhKiem> getAll() {
        return hanhKiemDAL.getAll();
    }

    // ===== LẤY THEO MÃ HỌC SINH =====
    public List<HanhKiem> getByMaHS(String maHS) {
        if (maHS == null || maHS.trim().isEmpty()) return null;
        return hanhKiemDAL.getByMaHS(maHS);
    }

    // ===== THÊM =====
    public boolean add(HanhKiem hk) {
        if (!kiemTraHopLe(hk)) return false;

        // Không cho trùng mã
        if (hanhKiemDAL.getById(hk.getMaHanhKiem()) != null) {
            return false;
        }

        return hanhKiemDAL.add(hk);
    }

    // ===== SỬA =====
    public boolean update(HanhKiem hk) {
        if (!kiemTraHopLe(hk)) return false;

        return hanhKiemDAL.update(hk);
    }

    // ===== XÓA =====
    public boolean delete(String maHanhKiem) {
        if (maHanhKiem == null || maHanhKiem.trim().isEmpty()) return false;

        return hanhKiemDAL.delete(maHanhKiem);
    }

    // ===== KIỂM TRA DỮ LIỆU =====
    private boolean kiemTraHopLe(HanhKiem hk) {

        if (hk == null) return false;

        if (hk.getMaHanhKiem() == null || hk.getMaHanhKiem().trim().isEmpty())
            return false;

        if (hk.getMaHS() == null || hk.getMaHS().trim().isEmpty())
            return false;

        if (hk.getMaHocKy() == null || hk.getMaHocKy().trim().isEmpty())
            return false;

        if (hk.getXepLoai() == null || hk.getXepLoai().trim().isEmpty())
            return false;

        if (hk.getSoLanViPham() < 0)
            return false;

        return true;
    }
}