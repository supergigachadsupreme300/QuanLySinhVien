package BusinessLogicLayer;

import DAO.NamHocDAL;
import DataObject.NamHoc;
import java.util.List;

public class NamHocBLL {

    NamHocDAL nhDAL = new NamHocDAL();

    public List<NamHoc> getAll() {
        return nhDAL.getAll();
    }

    public List<NamHoc> getAllActive() {
        return nhDAL.getAllActive();
    }

    public List<NamHoc> getAllActiveByProc() {
        return nhDAL.getAllActiveByProc();
    }

    

    public boolean themNamHoc(NamHoc nh) {
        if (nh == null) return false;


        if (nhDAL.findByMaNH(nh.getMaNH()) != null) {
            System.out.println("Mã năm học đã tồn tại!");
            return false;
        }
        return nhDAL.insert(nh);
    }


    public boolean suaNamHoc(NamHoc nh) {
        if (nh == null) return false;
        return nhDAL.update(nh);
    }


    public boolean xoaNamHoc(String maNH) {
        return nhDAL.delete(maNH);
    }


    public NamHoc getByMaNH(String maNH) {
        return nhDAL.findByMaNH(maNH);
    }
}
