/*
 * Business logic layer for MonHoc entities.
 */
package BusinessLogicLayer;

import DataObject.MonHoc;
import java.util.ArrayList;
import java.util.List;

public class MonHocBLL {
    private List<MonHoc> dsMon;

    public MonHocBLL() {
        dsMon = new ArrayList<>();
    }

    public List<MonHoc> getAll() {
        return dsMon;
    }

    public boolean themMonHoc(MonHoc mh) {
        if (mh == null) return false;
        dsMon.add(mh);
        return true;
    }

    public boolean suaMonHoc(MonHoc mh) {
        if (mh == null) return false;
        for (int i = 0; i < dsMon.size(); i++) {
            if (dsMon.get(i).getMaMon().equals(mh.getMaMon())) {
                dsMon.set(i, mh);
                return true;
            }
        }
        return false;
    }

    public boolean xoaMonHoc(String maMon) {
        return dsMon.removeIf(m -> m.getMaMon().equals(maMon));
    }

    public MonHoc getByMa(String maMon) {
        for (MonHoc m : dsMon) {
            if (m.getMaMon().equals(maMon)) return m;
        }
        return null;
    }
}
