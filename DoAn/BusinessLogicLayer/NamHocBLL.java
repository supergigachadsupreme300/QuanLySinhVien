/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLogicLayer;

/**
 *
 * @author admin
 */
import DataObject.NamHoc;
import java.util.ArrayList;
import java.util.List;

public class NamHocBLL {

    private List<NamHoc> dsNamHoc;

    public NamHocBLL() {
        dsNamHoc = new ArrayList<>();
    }

    public List<NamHoc> getAll() {
        return dsNamHoc;
    }

    public void themNamHoc(NamHoc nh) {
        dsNamHoc.add(nh);
    }
}
