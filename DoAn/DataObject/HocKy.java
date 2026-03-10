package DataObject;

import java.time.LocalDate;


public class HocKy {
    private String maHK;
    private String tenHK;
    private String maNH;        
    private LocalDate ngayBatDau; 
    private LocalDate ngayKetThuc;
    private int trangThai;        


    public HocKy() {
        maHK = "";
        tenHK = "";
        maNH = "";
        ngayBatDau = null;
        ngayKetThuc = null;
        trangThai = 0;
    }


    public HocKy(String tenHK, String maNH, LocalDate ngayBatDau, LocalDate ngayKetThuc, int trangThai) {
        this.tenHK = tenHK;
        this.maNH = maNH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }


    public HocKy(String maHK, String tenHK, String maNH, LocalDate ngayBatDau, LocalDate ngayKetThuc, int trangThai) {
        this.maHK = maHK;
        this.tenHK = tenHK;
        this.maNH = maNH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }


    public HocKy(HocKy hk) {
        this.maHK = hk.maHK;
        this.tenHK = hk.tenHK;
        this.maNH = hk.maNH;
        this.ngayBatDau = hk.ngayBatDau;
        this.ngayKetThuc = hk.ngayKetThuc;
        this.trangThai = hk.trangThai;
    }


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

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }


    @Override
    public String toString() {
        return tenHK;
    }
}
