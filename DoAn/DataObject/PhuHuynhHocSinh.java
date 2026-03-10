package DataObject;

public class PhuHuynhHocSinh {
    private String maHS;
    private String maPH;
    private String quanHe;
    private int trangThai; 

    public PhuHuynhHocSinh() {
        maHS = "";
        maPH = "";
        quanHe = "";
        trangThai = 1;
    }

    public PhuHuynhHocSinh(String maHS, String maPH, String quanHe, int trangThai) {
        this.maHS = maHS;
        this.maPH = maPH;
        this.quanHe = quanHe;
        this.trangThai = trangThai;
    }


    public String getMaHS() { return maHS; }
    public void setMaHS(String maHS) { this.maHS = maHS; }

    public String getMaPH() { return maPH; }
    public void setMaPH(String maPH) { this.maPH = maPH; }

    public String getQuanHe() { return quanHe; }
    public void setQuanHe(String quanHe) { this.quanHe = quanHe; }

    public int getTrangThai() { return trangThai; }
    public void setTrangThai(int trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return maHS + " - " + maPH + " (" + quanHe + ")";
    }
}