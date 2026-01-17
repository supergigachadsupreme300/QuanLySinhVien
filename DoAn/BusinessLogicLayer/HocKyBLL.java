/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLogicLayer;

import DataObject.HocKy;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class HocKyBLL {
    private List<HocKy> dsHK = new ArrayList<>();

    public void them(HocKy hk) {
        dsHK.add(hk);
    }

    public List<HocKy> getByMaNH(String maNH) {
        List<HocKy> kq = new ArrayList<>();
        for (HocKy hk : dsHK) {
            if (hk.getMaNH().equals(maNH)) {
                kq.add(hk);
            }
        }
        return kq;
    }

    public HocKy getByMaHK(String maHK) {
        for (HocKy hk : dsHK) {
            if (hk.getMaHK().equals(maHK)) return hk;
        }
        return null;
    }
}
