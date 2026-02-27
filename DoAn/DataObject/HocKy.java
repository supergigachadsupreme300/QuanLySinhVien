package DataObject;

import java.time.LocalDate;


public class HocKy {
    // ===== Thuộc tính =====
    private String maHK;
    private String tenHK;
    private String maNH;          // mã năm học
    private LocalDate ngayBatDau; // ngày bắt đầu học kỳ
    private LocalDate ngayKetThuc;// ngày kết thúc học kỳ
    private int trangThai;        // trạng thái (active/inactive)

    // ===== Constructor rỗng =====
    public HocKy() {
        maHK = "";
        tenHK = "";
        maNH = "";
        ngayBatDau = null;
        ngayKetThuc = null;
        trangThai = 0;
    }

    // ===== Constructor không có mã HK (dùng khi insert) =====
    public HocKy(String tenHK, String maNH, LocalDate ngayBatDau, LocalDate ngayKetThuc, int trangThai) {
        this.tenHK = tenHK;
        this.maNH = maNH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    // ===== Constructor đầy đủ =====
    public HocKy(String maHK, String tenHK, String maNH, LocalDate ngayBatDau, LocalDate ngayKetThuc, int trangThai) {
        this.maHK = maHK;
        this.tenHK = tenHK;
        this.maNH = maNH;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    // ===== Constructor copy =====
    public HocKy(HocKy hk) {
        this.maHK = hk.maHK;
        this.tenHK = hk.tenHK;
        this.maNH = hk.maNH;
        this.ngayBatDau = hk.ngayBatDau;
        this.ngayKetThuc = hk.ngayKetThuc;
        this.trangThai = hk.trangThai;
    }

    // ===== Getter / Setter =====
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

    // ===== Hiển thị JComboBox =====
    @Override
    public String toString() {
        return tenHK;
    }
}
