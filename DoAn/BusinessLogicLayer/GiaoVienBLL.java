/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLogicLayer;

/**
 *
 * @author admin
 */
import DataObject.GiaoVien;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienBLL {

    private List<GiaoVien> dsGV;

    public GiaoVienBLL() {
        dsGV = new ArrayList<>();
    }

    public List<GiaoVien> getAll() {
        return dsGV;
    }

    public boolean themGiaoVien(GiaoVien gv) {
        if (gv == null) return false;
        dsGV.add(gv);
        return true;
    }

    public boolean suaGiaoVien(GiaoVien gv) {
        if (gv == null) return false;
        for (int i = 0; i < dsGV.size(); i++) {
            if (dsGV.get(i).getMaGV().equals(gv.getMaGV())) {
                dsGV.set(i, gv);
                return true;
            }
        }
        return false;
    }

    public boolean xoaGiaoVien(String maGV) {
        return dsGV.removeIf(g -> g.getMaGV().equals(maGV));
    }
}
