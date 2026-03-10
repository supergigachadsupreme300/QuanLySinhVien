package DataObject;

public class PhanCong {
    private String maPC;
    private String maGV;
    private String maMon;
    private String maLop;
    private String maNam;
    private String ghiChu;
    private int trangThai;

    public PhanCong() {
        maPC ="";
        maGV ="";
        maMon="";
        maLop="";
        maNam="";
        ghiChu="";
        trangThai=0;
    }


    public PhanCong(String maPC, String maGV, String maMon, String maLop, String maNam, String ghiChu, int trangThai) {
        this.maPC = maPC;
        this.maGV = maGV;
        this.maMon = maMon;
        this.maLop = maLop;
        this.maNam = maNam;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public PhanCong(String maPC, String maGV, String maMon) {
        this.maPC = maPC;
        this.maGV = maGV;
        this.maMon = maMon;
    }


    public String getMaPC() { return maPC; }
    public void setMaPC(String maPC) { this.maPC = maPC; }

    public String getMaGV() { return maGV; }
    public void setMaGV(String maGV) { this.maGV = maGV; }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getMaLop() { return maLop; }
    public void setMaLop(String maLop) { this.maLop = maLop; }

    public String getMaNam() { return maNam; }
    public void setMaNam(String maNam) { this.maNam = maNam; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }


    @Override
    public String toString() {
        return "PhanCong{" +
                "maPC='" + maPC + '\'' +
                ", maGV='" + maGV + '\'' +
                ", maMon='" + maMon + '\'' +
                ", maLop='" + maLop + '\'' +
                ", maNam='" + maNam + '\'' +
                ", ghiChu='" + ghiChu + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}
