package BusinessLogicLayer;

import DataAcessLayer.MonDAL;
import DataObject.Mon;
import java.util.List;
import java.sql.*;

public class MonBLL {
    // Tạo sẵn đối tượng DAL bên trong BUS
    MonDAL monDAL = new MonDAL();

    // ===== GET ALL =====
    public List<Mon> getAll() {
        return monDAL.getAll();
    }

    // ===== GET ALL ACTIVE =====
    public List<Mon> getAllActive() {
        return monDAL.getAllActive();
    }
    
    public List<Mon> getAllActiveProc(){
        return monDAL.getAllActiveByProc();
    }

    // ===== THÊM =====
    public boolean themMon(Mon mon) {
        if (mon == null) return false;

        // kiểm tra trùng mã
        if (monDAL.findByMaMon(mon.getMaMon()) != null) {
            System.out.println("Mã môn đã tồn tại!");
            return false;
        }
        return monDAL.insert(mon);
    }

    // ===== SỬA =====
    public boolean suaMon(Mon mon) {
        if (mon == null) return false;
        return monDAL.update(mon);
    }

    // ===== XÓA =====
    public boolean xoaMon(String maMon) {
        return monDAL.delete(maMon);
    }

    // ===== TÌM THEO MÃ =====
    public Mon getByMaMon(String maMon) {
        return monDAL.findByMaMon(maMon);
    }
}
