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

    public void themGiaoVien(GiaoVien gv) {
        dsGV.add(gv);
    }
}
