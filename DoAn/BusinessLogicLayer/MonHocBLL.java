package BusinessLogicLayer;

import DAO.MonHocDAO;
import DataObject.Mon;
import java.util.List;

public class MonHocBLL {

    private final MonHocDAO dao;

    public MonHocBLL() {
        this.dao = new MonHocDAO();
    }

    public List<Mon> getAll() {
        return dao.getAll();
    }

    public boolean themMonHoc(Mon mh) {
        if (mh == null) {
            return false;
        }

        String error = validate(mh);
        if (error != null) {
            System.err.println("Validate lỗi: " + error);
            return false;
        }


        if (getByMa(mh.getMaMon()) != null) {
            System.err.println("Mã môn học đã tồn tại: " + mh.getMaMon());
            return false;
        }

        return dao.insert(mh);
    }


    public boolean suaMonHoc(Mon mh) {
        if (mh == null || mh.getMaMon() == null || mh.getMaMon().trim().isEmpty()) {
            return false;
        }

        String error = validate(mh);
        if (error != null) {
            System.err.println("Validate lỗi khi sửa: " + error);
            return false;
        }

        return dao.update(mh);
    }


    public boolean xoaMonHoc(String maMon) {
        if (maMon == null || maMon.trim().isEmpty()) {
            return false;
        }

     

        return dao.delete(maMon);
    }


    public Mon getByMa(String maMon) {
        if (maMon == null || maMon.trim().isEmpty()) {
            return null;
        }
        return dao.findByMaMon(maMon);
    }

    public List<Mon> getAllActive() {
        return dao.getAllActive();
    }

    public List<Mon> getAllActiveProc() {
        return dao.getAllActiveByProc();
    }



    private String validate(Mon mh) {
        if (mh.getMaMon() == null || mh.getMaMon().trim().isEmpty()) {
            return "Mã môn học không được để trống";
        }
        if (mh.getTenMon() == null || mh.getTenMon().trim().isEmpty()) {
            return "Tên môn học không được để trống";
        }
        if (mh.getTrangThai() < 0) {
            return "Trạng thái không hợp lệ";
        }

       
        return null; 
    }
}