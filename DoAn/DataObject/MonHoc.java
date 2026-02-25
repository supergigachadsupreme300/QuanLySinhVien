/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFilePath to edit this template
 */
package DataObject;

/**
 * Data object representing a school subject (Môn học).
 * Fields correspond to the MONHOC table in the database.
 *
 * @author admin
 */
public class MonHoc {
    private String maMon;
    private String tenMon;
    private int soTinChi;
    private String khoa;

    public MonHoc() {
        maMon = "";
        tenMon = "";
        soTinChi = 0;
        khoa = "";
    }

    public MonHoc(String maMon, String tenMon, int soTinChi, String khoa) {
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.soTinChi = soTinChi;
        this.khoa = khoa;
    }

    public String getMaMon() { return maMon; }
    public void setMaMon(String maMon) { this.maMon = maMon; }

    public String getTenMon() { return tenMon; }
    public void setTenMon(String tenMon) { this.tenMon = tenMon; }

    public int getSoTinChi() { return soTinChi; }
    public void setSoTinChi(int soTinChi) { this.soTinChi = soTinChi; }

    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }

    @Override
    public String toString() {
        return tenMon;
    }
}
