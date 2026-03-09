package DataObject;

public class Diem {
    private String maDiem;
    private String maHS;
    private String maMon;
    private String maHocKy;
    private double diemThuongXuyen;
    private double diemGiuaKy;
    private double diemCuoiKy;
    private double diemTBMonHocKy;

    public Diem() {
        maDiem = "";
        maHS = "";
        maMon = "";
        maHocKy = "";
        diemThuongXuyen = 0;
        diemGiuaKy = 0;
        diemCuoiKy = 0;
        diemTBMonHocKy = 0;
    }

    public Diem(String maDiem, String maHS, String maMon, String maHocKy,
                 double diemThuongXuyen, double diemGiuaKy, double diemCuoiKy, double diemTBMonHocKy) {
        this.maDiem = maDiem;
        this.maHS = maHS;
        this.maMon = maMon;
        this.maHocKy = maHocKy;
        this.diemThuongXuyen = diemThuongXuyen;
        this.diemGiuaKy = diemGiuaKy;
        this.diemCuoiKy = diemCuoiKy;
        this.diemTBMonHocKy = diemTBMonHocKy;
    }

    public String getMaDiem() { return maDiem; }
    public void setMaDiem(String maDiem) { this.maDiem = maDiem; }

    public String getMaHS() { return maHS; }
    public void setMaHS(String maHS) { this.maHS = maHS; }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public double getDiemThuongXuyen() { return diemThuongXuyen; }
    public void setDiemThuongXuyen(double diemThuongXuyen) { this.diemThuongXuyen = diemThuongXuyen; }

    public double getDiemGiuaKy() { return diemGiuaKy; }
    public void setDiemGiuaKy(double diemGiuaKy) { this.diemGiuaKy = diemGiuaKy; }

    public double getDiemCuoiKy() { return diemCuoiKy; }
    public void setDiemCuoiKy(double diemCuoiKy) { this.diemCuoiKy = diemCuoiKy; }

    public double getDiemTBMonHocKy() { return diemTBMonHocKy; }
    public void setDiemTBMonHocKy(double diemTBMonHocKy) { this.diemTBMonHocKy = diemTBMonHocKy; }
}