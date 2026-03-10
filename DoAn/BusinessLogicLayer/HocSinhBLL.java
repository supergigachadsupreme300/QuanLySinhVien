package BusinessLogicLayer;

import DAO.HocSinhDAO;
import DataObject.HocSinh;
import java.sql.Connection;
import java.util.List;

public class HocSinhBLL {

    HocSinhDAO hsDAL = new HocSinhDAO();

    public HocSinhBLL() {

        this.hsDAL = new HocSinhDAO();
    }

    public HocSinhBLL(Connection con) {
        this.hsDAL = new HocSinhDAO(con);
    }
    
    public List<HocSinh> getAll() {
        return hsDAL.getAll();
    }
    
    public List<HocSinh> getAllActive() {
        return hsDAL.getAllActive();
    }
    

    public List<HocSinh> getByMaLop(String maLop) {
        return hsDAL.getByMaLop(maLop);
    }


    public boolean themHocSinh(HocSinh hs) {
        if (hs == null) return false;
        if (hsDAL.getById(hs.getMaHS()) != null) {
            return false;
        }
        boolean ok = hsDAL.add(hs);
        if (ok) {

            try {
                DAO.LopDAL lopDAL = new DAO.LopDAL();
                lopDAL.updateSiSo(hs.getMaLop());
            } catch (Exception ex) {

                ex.printStackTrace();
            }
        }
        return ok;
    }


    public boolean suaHocSinh(HocSinh hs) {
        if (hs == null) return false;
        return hsDAL.update(hs);
    }


    public boolean xoaHocSinh(String maHS) {
        HocSinh hs = hsDAL.getById(maHS);
        boolean ok = hsDAL.delete(maHS);
        return ok && hs != null;
    }

    public HocSinh getByMa(String maHS) {
        return hsDAL.getById(maHS);
    }
}
