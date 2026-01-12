/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
public class ChiTietTiet {

    // ===== Thuộc tính =====
    private String maChiTiet;
    private String maTKB;
    private String maMon;
    private String thu;          // VD: "Thứ 2"
    private int tiet;            // Tiết số mấy
    private String phongHoc;
    private String gioBatDau;
    private String gioKetThuc;

    // ===== 1. Constructor rỗng =====
    public ChiTietTiet() {
        maChiTiet = "";
        maTKB = "";
        maMon = "";
        thu = "";
        tiet = 0;
        phongHoc = "";
        gioBatDau = "";
        gioKetThuc = "";
    }

    // ===== 2. Constructor đầy đủ =====
    public ChiTietTiet(String maChiTiet, String maTKB, String maMon,
                       String thu, int tiet,
                       String phongHoc, String gioBatDau, String gioKetThuc) {
        this.maChiTiet = maChiTiet;
        this.maTKB = maTKB;
        this.maMon = maMon;
        this.thu = thu;
        this.tiet = tiet;
        this.phongHoc = phongHoc;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    // ===== 3. Constructor copy =====
    public ChiTietTiet(ChiTietTiet ct) {
        this.maChiTiet = ct.maChiTiet;
        this.maTKB = ct.maTKB;
        this.maMon = ct.maMon;
        this.thu = ct.thu;
        this.tiet = ct.tiet;
        this.phongHoc = ct.phongHoc;
        this.gioBatDau = ct.gioBatDau;
        this.gioKetThuc = ct.gioKetThuc;
    }

    // ===== Getter / Setter =====
    public String getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(String maChiTiet) { this.maChiTiet = maChiTiet; }
    public String getMaTKB() { return maTKB; }
    public void setMaTKB(String maTKB) { this.maTKB = maTKB; }
    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }
    public String getThu() { return thu; }
    public void setThu(String thu) { this.thu = thu; }
    public int getTiet() { return tiet; }
    public void setTiet(int tiet) { this.tiet = tiet; }
    public String getPhongHoc() { return phongHoc; }
    public void setPhongHoc(String phongHoc) { this.phongHoc = phongHoc; }
    public String getGioBatDau() { return gioBatDau; }
    public void setGioBatDau(String gioBatDau) { this.gioBatDau = gioBatDau; }
    public String getGioKetThuc() { return gioKetThuc; }
    public void setGioKetThuc(String gioKetThuc) { this.gioKetThuc = gioKetThuc; }
}