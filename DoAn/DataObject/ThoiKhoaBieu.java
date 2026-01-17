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

/**
 *
 * @author admin
 */
public class ThoiKhoaBieu {
private String maTKB;
    private String maLop;
    private String maHK;
    //private LocalDate ngayBatDau;
    //private LocalDate ngayKetThuc;

    // Constructor rỗng
    public ThoiKhoaBieu() {
        maTKB = "";
        maLop = "";
        maHK = "";
    //    ngayBatDau = null;
    //    ngayKetThuc = null;   
    }

    // Constructor không có mã
    public ThoiKhoaBieu(String maLop, String maHK
                        /*LocalDate ngayBatDau, LocalDate ngayKetThuc*/) {
        this.maLop = maLop;
        this.maHK = maHK;
    //    this.ngayBatDau = ngayBatDau;
    //    this.ngayKetThuc = ngayKetThuc;
    }

    // Constructor đầy đủ
    public ThoiKhoaBieu(String maTKB, String maLop, String maHK
                        /*LocalDate ngayBatDau, LocalDate ngayKetThuc*/) {
        this.maTKB = maTKB;
        this.maLop = maLop;
        this.maHK = maHK;
    //    this.ngayBatDau = ngayBatDau;
    //    this.ngayKetThuc = ngayKetThuc;
    }

    // Getter / Setter
    public String getMaTKB() { return maTKB; }
    public void setMaTKB(String maTKB) { this.maTKB = maTKB; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getMaHK() { return maHK; }
    public void setMaHK(String maHK) { this.maHK = maHK; }

    //public LocalDate getNgayBatDau() { return ngayBatDau; }
    //public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }

    //public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    //public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    
    @Override
    public String toString() {
        return maTKB + " - " + maLop;
    }

}
