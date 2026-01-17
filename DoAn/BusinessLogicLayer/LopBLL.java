/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLogicLayer;

/**
 *
 * @author admin
 */

/*import DataObject.Lop;
import DataAcessLayer.LopDAL;
import java.util.List;

public class LopBLL {

    private LopDAL lopDAL;

    public LopBLL() {
        lopDAL = new LopDAL();
    }

    // ===== GET ALL =====
    public List<Lop> getAll() {
        return lopDAL.findAll();
    }

    // ===== THÊM =====
    public boolean themLop(Lop lop) {
        if (lop == null) return false;

        // kiểm tra trùng mã
        if (lopDAL.findByMaLop(lop.getMaLop()) != null) {
                return false;
        }
        return lopDAL.insert(lop);
    }        

    // ===== SỬA =====
    public boolean suaLop(Lop lop) {
        if (lop == null) return false;
        return lopDAL.update(lop);
    }

    // ===== XÓA =====
    public boolean xoaLop(String maLop) {
        return lopDAL.delete(maLop);
    }
}*/

import DataObject.Lop;
import java.util.ArrayList;
import java.util.List;

public class LopBLL {

    private List<Lop> dsLop;

    public LopBLL() {
        dsLop = new ArrayList<>();
    }

    
    // Thêm lớp
    public boolean themLop(Lop lop) {
        if (lop == null) return false;
        dsLop.add(lop);
        return true;
    }

    // Sửa lớp
    public boolean suaLop(Lop lop) {
        for (int i = 0; i < dsLop.size(); i++) {
            if (dsLop.get(i).getMaLop().equals(lop.getMaLop())) {
                dsLop.set(i, lop);
                return true;
            }
        }
        return false;
    }

    // Xóa lớp
    public boolean xoaLop(String maLop) {
        return dsLop.removeIf(l -> l.getMaLop().equals(maLop));
    }

    // Lấy danh sách lớp
    public List<Lop> getAll() {
        return dsLop;
    }

    // Tìm lớp theo mã
    public Lop getByMaLop(String maLop) {
        for (Lop l : dsLop) {
            if (l.getMaLop().equals(maLop)) {
                return l;
            }
        }
        return null;
    }
}
