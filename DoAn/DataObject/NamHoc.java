/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
public class NamHoc {
    // ===== Thuộc tính =====
    private String maNH;
    private String tenNH;
    private int trangThai;

    // ===== 1. Constructor rỗng =====
    public NamHoc() {
        maNH = "";
        tenNH = "";
        trangThai=0;
    }

    // ===== 2. Constructor truyền tham số =====
    public NamHoc(String maNH, String tenNH, int trangThai) {
        this.maNH = maNH;
        this.tenNH = tenNH;
        this.trangThai = trangThai;
    }

    // ===== 3. Constructor copy =====
    public NamHoc(NamHoc nh) {
        this.maNH = nh.maNH;
        this.tenNH = nh.tenNH;
        this.trangThai = nh.trangThai;
    }

    // ===== Getter / Setter =====
    public String getMaNH() {return maNH;}
    public void setMaNH(String maNH) {this.maNH = maNH;}
    public String getTenNH() {return tenNH;}
    public void setTenNH(String tenNH) {this.tenNH = tenNH;}
    public int getTrangThai(){return trangThai;}
    public void setTrangThai(int trangThai) {this.trangThai = trangThai;}
    
    // ===== Hỗ trợ hiển thị (JComboBox, JTable) =====
    @Override
    public String toString() {
        return tenNH;
    }
}
