/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLogicLayer;

/**
 *
 * @author admin
 */
/*import DataObject.HocSinh;
import java.util.ArrayList;
import java.util.List;

public class HocSinhBLL {

    private List<HocSinh> dsHocSinh;

    public HocSinhBLL() {
        dsHocSinh = new ArrayList<>();
    }

    public List<HocSinh> getByMaLop(String maLop) {
        List<HocSinh> result = new ArrayList<>();
        for (HocSinh hs : dsHocSinh) {
            if (hs.getMaLop().equals(maLop)) {
                result.add(hs);
            }
        }
        return result;
    }

    public void themHocSinh(HocSinh hs) {
        dsHocSinh.add(hs);
    }
}*/

import DAO.HocSinhDAO;
import DataObject.HocSinh;
import java.util.List;

public class HocSinhBLL {

    private HocSinhDAO hocSinhDAO;

    public HocSinhBLL() {
        hocSinhDAO = new HocSinhDAO();
    }

    // Thêm học sinh
    public boolean themHocSinh(HocSinh hs) {
        if (hs == null) return false;
        return hocSinhDAO.add(hs);
    }

    // Lấy HS theo mã lớp
    public List<HocSinh> getByMaLop(String maLop) {
        if (maLop == null) return java.util.Collections.emptyList();
        return hocSinhDAO.getByClass(maLop);
    }

    // Lấy toàn bộ
    public List<HocSinh> getAll() {
        return hocSinhDAO.getAll();
    }

    // Lấy theo mã học sinh
    public HocSinh getById(String maHS) {
        return hocSinhDAO.getById(maHS);
    }
}
