package BusinessLogicLayer;

import DAO.ChiTietTietDAL;
import DataObject.ChiTietTiet;
import java.util.List;

public class ChiTietTietBLL {
    ChiTietTietDAL ctDAL = new ChiTietTietDAL();


    public List<ChiTietTiet> getAll() {
        return ctDAL.getAll();
    }


    public List<ChiTietTiet> getAllActive() {
        return ctDAL.getAllActive();
    }

    public List<ChiTietTiet> getAllActiveProc() {
        return ctDAL.getAllActiveProc();
    }
    

    public List<ChiTietTiet> getByMaTKBByProc(String maTKB) {
        return ctDAL.getByMaTKBByProc(maTKB);
    }
    


    public Boolean themChiTietTiet(ChiTietTiet ct) {
        if (ct == null) return false;
        if (ctDAL.findByMaChiTiet(ct.getMaChiTiet()) != null) {
            return false;
        }
        return ctDAL.insert(ct);
    }


    public Boolean suaChiTietTiet(ChiTietTiet ct) {
        if (ct == null) return false;
        return ctDAL.update(ct);
    }


    public Boolean xoaChiTietTiet(String maChiTiet) {
        return ctDAL.delete(maChiTiet);
    }


    public ChiTietTiet findByMaChiTiet(String maChiTiet) {
        return ctDAL.findByMaChiTiet(maChiTiet);
    }
    
    public List<ChiTietTiet> getByMaTKB(String maTKB) {
        return ctDAL.getByMaTKBByProc(maTKB);
    }

}
