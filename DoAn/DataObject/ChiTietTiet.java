
package DataObject;


public class ChiTietTiet {


    private String maChiTiet;
    private String maTKB;
    private String maMon;
    private String thu;      
    private int tiet;         
    private String phongHoc;
    private String gioBatDau;
    private String gioKetThuc;
    private int trangThai;


    public ChiTietTiet() {
        maChiTiet = "";
        maTKB = "";
        maMon = "";
        thu = "";
        tiet = 0;
        phongHoc = "";
        gioBatDau = "";
        gioKetThuc = "";
        trangThai=0;
    }


    public ChiTietTiet(String maChiTiet, String maTKB, String maMon,
                       String thu, int tiet,
                       String phongHoc, String gioBatDau, String gioKetThuc, int trangThai) {
        this.maChiTiet = maChiTiet;
        this.maTKB = maTKB;
        this.maMon = maMon;
        this.thu = thu;
        this.tiet = tiet;
        this.phongHoc = phongHoc;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.trangThai = trangThai;
    }


    public ChiTietTiet(ChiTietTiet ct) {
        this.maChiTiet = ct.maChiTiet;
        this.maTKB = ct.maTKB;
        this.maMon = ct.maMon;
        this.thu = ct.thu;
        this.tiet = ct.tiet;
        this.phongHoc = ct.phongHoc;
        this.gioBatDau = ct.gioBatDau;
        this.gioKetThuc = ct.gioKetThuc;
        this.trangThai = ct.trangThai;
    }


    public String getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(String maChiTiet) { this.maChiTiet = maChiTiet; }
    public String getMaTKB() { return maTKB; }
    public void setMaTKB(String maTKB) { this.maTKB = maTKB; }
    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }
    public String getThu() { return thu; }
    public void setThu(String thu) { this.thu = thu; }
    public int getTiet() { return tiet; }
    public void setTiet(int tiet) { this.tiet = tiet; }
    public String getPhongHoc() { return phongHoc; }
    public void setPhongHoc(String phongHoc) { this.phongHoc = phongHoc; }
    public String getGioBatDau() { return gioBatDau; }
    public void setGioBatDau(String gioBatDau) { this.gioBatDau = gioBatDau; }
    public String getGioKetThuc() { return gioKetThuc; }
    public void setGioKetThuc(String gioKetThuc) { this.gioKetThuc = gioKetThuc; }
    public int getTrangThai() { return trangThai;}
    public void setTrangThai (int trangThai) { this.trangThai = trangThai; }
    
    @Override
    public String toString() {
        return maChiTiet + " - " + maMon + " (" + thu + " tiết " + tiet + ")";
    }

}
