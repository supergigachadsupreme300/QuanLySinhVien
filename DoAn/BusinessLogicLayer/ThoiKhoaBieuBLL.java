/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLogicLayer;

import DataObject.ThoiKhoaBieu;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ThoiKhoaBieuBLL {

    private List<ThoiKhoaBieu> dsTKB;

    public ThoiKhoaBieuBLL() {
        dsTKB = new ArrayList<>();
    }

    public List<ThoiKhoaBieu> getAll() {
        return dsTKB;
    }

    public boolean them(ThoiKhoaBieu tkb) {
        if (tkb == null) return false;

        // check trùng mã
        for (ThoiKhoaBieu t : dsTKB) {
            if (t.getMaTKB().equals(tkb.getMaTKB())) {
                return false;
            }
        }
        dsTKB.add(tkb);
        return true;
    }

    public boolean xoa(String maTKB) {
        return dsTKB.removeIf(t -> t.getMaTKB().equals(maTKB));
    }

    public ThoiKhoaBieu getByMa(String maTKB) {
        for (ThoiKhoaBieu t : dsTKB) {
            if (t.getMaTKB().equals(maTKB)) return t;
        }
        return null;
    }
}
