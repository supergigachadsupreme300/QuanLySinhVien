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
    private int trangThai;
    // ===== Constructor rỗng =====
    public HocSinh() {
        maHS = "";
        hoTen = "";
        ngaySinh = null;
        gioiTinh = "";
        diaChi = "";
        maLop = "";
        trangThai =0;
    }

    // ===== Constructor đầy đủ =====
    public HocSinh(String maHS, String hoTen, LocalDate ngaySinh,
                   String gioiTinh, String diaChi, String maLop,
                   int trangThai) {
        this.maHS = maHS;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.maLop = maLop;
       this.trangThai = trangThai;
    }
    // ===== Constructor copy =====
    public HocSinh(HocSinh hs) {
            this.maHS = hs.maHS;
            this.hoTen = hs.hoTen;
            this.ngaySinh = hs.ngaySinh;
            this.gioiTinh = hs.gioiTinh;
            this.diaChi = hs.diaChi;
            this.maLop = hs.maLop;
            this.trangThai = hs.trangThai;
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
    
    public int getTrangThai() { return trangThai;}
    public void setTrangThai(int trangThai ){ this.trangThai = trangThai;}

    // ===== Hỗ trợ hiển thị JTable =====
    @Override
    public String toString() {
        return maHS + " - " + hoTen;
    }
}
    
