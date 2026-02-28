package DataObject;

import java.time.LocalDate;

public class ViPham {

    // ===== Thuộc tính =====
    private String maViPham;
    private String maHS;
    private String maHocKy;
    private LocalDate ngayViPham;
    private String noiDung;
    private String mucDo;
    private boolean trangThai;

    // ===== Constructor rỗng =====
    public ViPham() {
        maViPham = "";
        maHS = "";
        maHocKy = "";
        ngayViPham = null;
        noiDung = "";
        mucDo = "";
        trangThai = true;
    }

    // ===== Constructor đầy đủ =====
    public ViPham(String maViPham, String maHS, String maHocKy,
                  LocalDate ngayViPham, String noiDung,
                  String mucDo, boolean trangThai) {
        this.maViPham = maViPham;
        this.maHS = maHS;
        this.maHocKy = maHocKy;
        this.ngayViPham = ngayViPham;
        this.noiDung = noiDung;
        this.mucDo = mucDo;
        this.trangThai = trangThai;
    }

    // ===== Constructor copy =====
    public ViPham(ViPham vp) {
        this.maViPham = vp.maViPham;
        this.maHS = vp.maHS;
        this.maHocKy = vp.maHocKy;
        this.ngayViPham = vp.ngayViPham;
        this.noiDung = vp.noiDung;
        this.mucDo = vp.mucDo;
        this.trangThai = vp.trangThai;
    }

    // ===== Getter / Setter =====
    public String getMaViPham() { return maViPham; }
    public void setMaViPham(String maViPham) { this.maViPham = maViPham; }

    public String getMaHS() { return maHS; }
    public void setMaHS(String maHS) { this.maHS = maHS; }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public LocalDate getNgayViPham() { return ngayViPham; }
    public void setNgayViPham(LocalDate ngayViPham) { this.ngayViPham = ngayViPham; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public String getMucDo() { return mucDo; }
    public void setMucDo(String mucDo) { this.mucDo = mucDo; }

    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    // ===== Hỗ trợ hiển thị JTable =====
    @Override
    public String toString() {
        return maHS + " - " + mucDo + " - " + ngayViPham;
    }
}