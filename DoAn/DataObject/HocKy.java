/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

import java.time.LocalDate;

/**
 * Represents an academic term (Học kỳ) in the system.
 *
 * <p>The attributes mirror the HOCKY table defined in <code>dbdiagramQLHS.sql</code>.
 * </p>
 *
 * @author admin
 */
public class HocKy {
    private String maHK;
    private String tenHK;
    private String maNH; // mã năm học
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    // ===== Constructors =====

    /**
     * Empty constructor initializes fields to empty/default values.
     */
    public HocKy() {
        maHK = "";
        tenHK = "";
        maNH = "";
        ngayBatDau = null;
        ngayKetThuc = null;
    }

    /**
     * Constructor without primary key (for insertion scenarios).
     */
    public HocKy(String tenHK, String maNH, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.tenHK = tenHK;
        this.maNH = maNH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    /**
     * Full constructor.
     */
    public HocKy(String maHK, String tenHK, String maNH, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maHK = maHK;
        this.tenHK = tenHK;
        this.maNH = maNH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    // ===== Getters / Setters =====
    public String getMaHK() { return maHK; }
    public void setMaHK(String maHK) { this.maHK = maHK; }

    public String getTenHK() { return tenHK; }
    public void setTenHK(String tenHK) { this.tenHK = tenHK; }

    public String getMaNH() { return maNH; }
    public void setMaNH(String maNH) { this.maNH = maNH; }

    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }

    @Override
    public String toString() {
        // useful for JComboBox display, etc.
        return tenHK;
    }
}
