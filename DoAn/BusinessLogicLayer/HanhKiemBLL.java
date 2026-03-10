package BusinessLogicLayer;

import DAO.HanhKiemDAL;
import DataObject.HanhKiem;
import java.util.List;

public class HanhKiemBLL {

    private HanhKiemDAL hanhKiemDAL;

    public HanhKiemBLL() {
        hanhKiemDAL = new HanhKiemDAL();
    }


    public HanhKiem getById(String maHanhKiem) {
        if (maHanhKiem == null || maHanhKiem.trim().isEmpty()) return null;
        return hanhKiemDAL.getById(maHanhKiem);
    }


    public List<HanhKiem> getAll() {
        return hanhKiemDAL.getAll();
    }


    public List<HanhKiem> getByMaHS(String maHS) {
        if (maHS == null || maHS.trim().isEmpty()) return null;
        return hanhKiemDAL.getByMaHS(maHS);
    }
    
    public HanhKiem getHanhKiem(String maHS, String maHK){
        return hanhKiemDAL.getHanhKiem(maHS, maHK);
    }


    public boolean add(HanhKiem hk) {
        if (!kiemTraHopLe(hk)) return false;


        DAO.HocSinhDAO hsDao = new DAO.HocSinhDAO();
        if (hsDao.getById(hk.getMaHS()) == null) {
            System.err.println("Học sinh không tồn tại: " + hk.getMaHS());
            return false;
        }

        DAO.HocKyDAL hkDao = new DAO.HocKyDAL();
        if (hkDao.findByMaHK(hk.getMaHocKy()) == null) {
            System.err.println("Học kỳ không tồn tại: " + hk.getMaHocKy());
            return false;
        }


        if (hanhKiemDAL.getById(hk.getMaHanhKiem()) != null) {
            return false;
        }

        return hanhKiemDAL.add(hk);
    }


    public boolean update(HanhKiem hk) {
        if (!kiemTraHopLe(hk)) return false;

        return hanhKiemDAL.update(hk);
    }


    public boolean delete(String maHanhKiem) {
        if (maHanhKiem == null || maHanhKiem.trim().isEmpty()) return false;

        return hanhKiemDAL.delete(maHanhKiem);
    }


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
    
    public boolean addOrUpdate(HanhKiem h) {

        HanhKiem exist = hanhKiemDAL.getById(h.getMaHanhKiem());

        if (exist == null) {
            return hanhKiemDAL.add(h);
        } else {
            return hanhKiemDAL.update(h);
        }
    }
}
