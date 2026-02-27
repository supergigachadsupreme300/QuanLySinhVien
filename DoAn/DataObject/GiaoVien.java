/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

import java.time.LocalDate;

public class GiaoVien {
    // ===== Thuộc tính =====
    private String maGV;
    private String hoTen;
    private LocalDate ngaySinh;
    private String gioiTinh;
    private String dienThoai;
    private String email;
    private String diaChi;
    private int trangThai;

    // ===== Constructor rỗng =====
    public GiaoVien() {
        maGV = "";
        hoTen = "";
        ngaySinh = null;
        gioiTinh = "";
        dienThoai = "";
        email = "";
        diaChi = "";
        trangThai = 0;
    }

    // ===== Constructor đầy đủ =====
    public GiaoVien(String maGV, String hoTen, LocalDate ngaySinh, String gioiTinh,
                    String dienThoai, String email, String diaChi, int trangThai) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.dienThoai = dienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    // ===== Constructor copy =====
    public GiaoVien(GiaoVien gv) {
        this.maGV = gv.maGV;
        this.hoTen = gv.hoTen;
        this.ngaySinh = gv.ngaySinh;
        this.gioiTinh = gv.gioiTinh;
        this.dienThoai = gv.dienThoai;
        this.email = gv.email;
        this.diaChi = gv.diaChi;
        this.trangThai = gv.trangThai;
    }

    // ===== Getter / Setter =====
    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDienThoai() { return dienThoai; }
    public void setDienThoai(String dienThoai) { this.dienThoai = dienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }

    // ===== Hiển thị JComboBox =====
    @Override
    public String toString() {
        return hoTen;
    }
}
