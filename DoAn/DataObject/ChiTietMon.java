/*
 * Data transfer object for ChiTietMon (detailed subject information).
 */
package DataObject;

/**
 * Represents a detail row in CHITIETMON table.
 */
public class ChiTietMon {
    private String maChiTiet;
    private String maMon;
    private String tenChiTiet;
    private int heSo;

    public ChiTietMon() {
        maChiTiet = "";
        maMon = "";
        tenChiTiet = "";
        heSo = 0;
    }

    public ChiTietMon(String maChiTiet, String maMon, String tenChiTiet, int heSo) {
        this.maChiTiet = maChiTiet;
        this.maMon = maMon;
        this.tenChiTiet = tenChiTiet;
        this.heSo = heSo;
    }

    public String getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(String maChiTiet) { this.maChiTiet = maChiTiet; }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getTenChiTiet() { return tenChiTiet; }
    public void setTenChiTiet(String tenChiTiet) { this.tenChiTiet = tenChiTiet; }

    public int getHeSo() { return heSo; }
    public void setHeSo(int heSo) { this.heSo = heSo; }

    @Override
    public String toString() {
        return tenChiTiet;
    }
}
