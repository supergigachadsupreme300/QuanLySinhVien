package BusinessLogicLayer;

import DAO.HocKyDAL;
import DataObject.HocKy;
import java.sql.Connection;
import java.util.List;

public class HocKyBLL {

    HocKyDAL hkDAL = new HocKyDAL();
    
    public HocKyBLL() {
        this.hkDAL = new HocKyDAL();
    }
    
    public HocKyBLL(Connection con) {
        this.hkDAL = new HocKyDAL(con);
    }


    public List<HocKy> getAll() {
        return hkDAL.getAll();
    }


    public List<HocKy> getAllActive() {
        return hkDAL.getAllActive();
    }

    public List<HocKy> getAllActiveByProc() {
        return hkDAL.getAllActiveByProc();
    }

    

    public boolean themHocKy(HocKy hk) {
        if (hk == null) return false;
        if (hkDAL.findByMaHK(hk.getMaHK()) != null) {
            System.out.println("Mã học kỳ đã tồn tại!");
            return false;
        }
        return hkDAL.insert(hk);
    }


    public boolean suaHocKy(HocKy hk) {
        if (hk == null) return false;
        return hkDAL.update(hk);
    }


    public boolean xoaHocKy(String maHK) {
        return hkDAL.delete(maHK);
    }


    public HocKy getByMaHK(String maHK) {
        return hkDAL.findByMaHK(maHK);
    }
}
