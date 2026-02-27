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

import DataObject.HocSinh;
import java.util.ArrayList;
import java.util.List;

public class HocSinhBLL {

    private List<HocSinh> dsHocSinh;

    public HocSinhBLL() {
        dsHocSinh = new ArrayList<>();
    }

    // Thêm học sinh
    public boolean themHocSinh(HocSinh hs) {
        if (hs == null) return false;
        dsHocSinh.add(hs);
        return true;
    }

    // Lấy HS theo mã lớp
    public List<HocSinh> getByMaLop(String maLop) {
        List<HocSinh> result = new ArrayList<>();
        for (HocSinh hs : dsHocSinh) {
            if (hs.getMaLop().equals(maLop)) {
                result.add(hs);
            }
        }
        return result;
    }

    // Lấy toàn bộ
    public List<HocSinh> getAll() {
        return dsHocSinh;
    }

    // Cập nhật học sinh (dựa vào maHS)
    public boolean suaHocSinh(HocSinh hs) {
        if (hs == null) return false;
        for (int i = 0; i < dsHocSinh.size(); i++) {
            if (dsHocSinh.get(i).getMaHS().equals(hs.getMaHS())) {
                dsHocSinh.set(i, hs);
                return true;
            }
        }
        return false;
    }

    // Xóa học sinh theo mã
    public boolean xoaHocSinh(String maHS) {
        return dsHocSinh.removeIf(h -> h.getMaHS().equals(maHS));
    }

    // Lấy 1 học sinh theo mã
    public HocSinh getByMa(String maHS) {
        for (HocSinh h : dsHocSinh) {
            if (h.getMaHS().equals(maHS)) return h;
        }
        return null;
    }
}
