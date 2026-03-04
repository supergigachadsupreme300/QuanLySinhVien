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
    private int trangThai;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;

    // Constructor rỗng
    public ThoiKhoaBieu() {
        maTKB = "";
        maLop = "";
        maHK = "";
        trangThai = 0;
        ngayBatDau = null;
        ngayKetThuc = null;
    }

    // Constructor đầy đủ
    public ThoiKhoaBieu(String maTKB, String maLop, String maHK, int trangThai, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maTKB = maTKB;
        this.maLop = maLop;
        this.maHK = maHK;
        this.trangThai = trangThai; 
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }
    
    public ThoiKhoaBieu(ThoiKhoaBieu tkb){
        this.maTKB = tkb.maTKB;
        this.maHK = tkb.maHK;
        this.maLop = tkb.maLop;
        this.trangThai = tkb.trangThai;
        this.ngayBatDau = tkb.ngayBatDau;
        this.ngayKetThuc = tkb.ngayKetThuc;
    }

    // Getter / Setter
    public String getMaTKB() { return maTKB; }
    public void setMaTKB(String maTKB) { this.maTKB = maTKB; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getMaHK() { return maHK; }
    public void setMaHK(String maHK) { this.maHK = maHK; }

    // compatibility: allow code to call getMaHocKy()/setMaHocKy()
    public String getMaHocKy() { return maHK; }
    public void setMaHocKy(String maHocKy) { this.maHK = maHocKy; }
    
    public int getTrangThai(){return trangThai;}
    public void setTrangThai(int trangThai){this.trangThai = trangThai;}

    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public void setNgayBatDau(LocalDate ngayBatDau) { this.ngayBatDau = ngayBatDau; }
    
    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public void setNgayKetThuc(LocalDate ngayKetThuc) { this.ngayKetThuc = ngayKetThuc; }
    
    @Override
    public String toString() {
        return maTKB + " - " + maLop;
    }

}
