/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
public class GiaoVien {
    // ===== Thuộc tính =====
    private String maGV;
    private String hoTen;
    private String dienThoai;
    private String email;

    // ===== Constructor rỗng =====
    public GiaoVien() {
        maGV = "";
        hoTen = "";
        dienThoai = "";
        email = "";
    }

    // ===== Constructor đầy đủ =====
    public GiaoVien(String maGV, String hoTen,
                    String dienThoai, String email) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.dienThoai = dienThoai;
        this.email = email;
    }

    // ===== Constructor copy =====
    public GiaoVien(GiaoVien gv) {
        this.maGV = gv.maGV;
        this.hoTen = gv.hoTen;
        this.dienThoai = gv.dienThoai;
        this.email = gv.email;
        
    }

    // ===== Getter / Setter =====
    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // ===== Hiển thị JComboBox =====
    @Override
    public String toString() {
        return hoTen;
    }
}