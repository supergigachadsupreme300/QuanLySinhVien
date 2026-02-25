/*
 * Business logic layer for ChiTietMon entities.
 */
package BusinessLogicLayer;

import DataObject.ChiTietMon;
import java.util.ArrayList;
import java.util.List;

public class ChiTietMonBLL {
    private List<ChiTietMon> dsChiTiet;

    public ChiTietMonBLL() {
        dsChiTiet = new ArrayList<>();
    }

    public List<ChiTietMon> getAll() {
        return dsChiTiet;
    }

    public boolean themChiTietMon(ChiTietMon ct) {
        if (ct == null) return false;
        dsChiTiet.add(ct);
        return true;
    }

    public boolean suaChiTietMon(ChiTietMon ct) {
        if (ct == null) return false;
        for (int i = 0; i < dsChiTiet.size(); i++) {
            if (dsChiTiet.get(i).getMaChiTiet().equals(ct.getMaChiTiet())) {
                dsChiTiet.set(i, ct);
                return true;
            }
        }
        return false;
    }

    public boolean xoaChiTietMon(String maChiTiet) {
        return dsChiTiet.removeIf(c -> c.getMaChiTiet().equals(maChiTiet));
    }

    public ChiTietMon getByMa(String maChiTiet) {
        for (ChiTietMon c : dsChiTiet) {
            if (c.getMaChiTiet().equals(maChiTiet)) return c;
        }
        return null;
    }

    public List<ChiTietMon> getByMon(String maMon) {
        List<ChiTietMon> kq = new ArrayList<>();
        for (ChiTietMon c : dsChiTiet) {
            if (c.getMaMon().equals(maMon)) kq.add(c);
        }
        return kq;
    }
}
