/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
public class Parent {
    // ===== Thuộc tính =====
    private String maPhH;
    private String tenPhH;
    private String sdt;
    private String ngheNghiep;
    private String quanHe;

    // ===== Constructor rỗng =====
    public Parent() {
        maPhH = "";
        tenPhH = "";
        sdt = "";
        ngheNghiep = "";
        quanHe = "";
    }

    // ===== Constructor đầy đủ =====
    public Parent(String maPhH, String tenPhH, String sdt, String ngheNghiep, String quanHe) {
        this.maPhH = maPhH;
        this.tenPhH = tenPhH;
        this.sdt = sdt;
        this.ngheNghiep = ngheNghiep;
        this.quanHe = quanHe;
    }

    // ===== Constructor copy =====
    public Parent(Parent p) {
        if (p != null) {
            this.maPhH = p.maPhH;
            this.tenPhH = p.tenPhH;
            this.sdt = p.sdt;
            this.ngheNghiep = p.ngheNghiep;
            this.quanHe = p.quanHe;
        }
    }

    // ===== Getter / Setter =====
    public String getMaPhH() { return maPhH; }
    public void setMaPhH(String maPhH) { this.maPhH = maPhH; }

    public String getTenPhH() { return tenPhH; }
    public void setTenPhH(String tenPhH) { this.tenPhH = tenPhH; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getNgheNghiep() { return ngheNghiep; }
    public void setNgheNghiep(String ngheNghiep) { this.ngheNghiep = ngheNghiep; }

    public String getQuanHe() { return quanHe; }
    public void setQuanHe(String quanHe) { this.quanHe = quanHe; }

    // ===== Hỗ trợ hiển thị =====
    @Override
    public String toString() {
        return maPhH + " - " + tenPhH;
    }
}
