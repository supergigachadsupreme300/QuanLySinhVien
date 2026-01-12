/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
import java.time.LocalDate;

public class HocSinh {
    // ===== Thuộc tính =====
    private String maHS;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String diaChi;
    private String maLop;

    // ===== Constructor rỗng =====
    public HocSinh() {
        maHS = "";
        hoTen = "";
        ngaySinh = null;
        gioiTinh = "";
        diaChi = "";
        maLop = "";
    }

    // ===== Constructor đầy đủ =====
    public HocSinh(String maHS, String hoTen, LocalDate ngaySinh,
                   String gioiTinh, String diaChi, String maLop) {
        this.maHS = maHS;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.maLop = maLop;
    }

    // ===== Constructor copy =====
    public HocSinh(HocSinh hs) {
        if (hs != null) {
            this.maHS = hs.maHS;
            this.hoTen = hs.hoTen;
            this.ngaySinh = hs.ngaySinh;
            this.gioiTinh = hs.gioiTinh;
            this.diaChi = hs.diaChi;
            this.maLop = hs.maLop;
        }
    }

    // ===== Getter / Setter =====
    public String getMaHS() { return maHS; }
    public void setMaHS(String maHS) { this.maHS = maHS; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    // ===== Hỗ trợ hiển thị JTable =====
    @Override
    public String toString() {
        return maHS + " - " + hoTen;
    }
}