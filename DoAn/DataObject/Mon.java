/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
public class Mon {
    private String maMon;
    private String tenMon;
    private int trangThai;
    private int soTinChi;
    private String khoa;
    
    public Mon(){
        maMon ="";
        tenMon ="";
        trangThai=0;
    }
    
    public Mon(String maMon, String tenMon, int trangThai){
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.trangThai = trangThai;
        this.soTinChi = 0;
        this.khoa = "";
    }
    
    public Mon (Mon mon){
        this.maMon = mon.maMon;
        this.tenMon = mon.tenMon;
        this.trangThai = mon.trangThai;
    }
    
    public String getMaMon(){return maMon;}
    public void setMaMon(String maMon){this.maMon = maMon;}
    
    public String getTenMon(){return tenMon;}
    public void setTenMon(String tenMon){this.tenMon = tenMon;}
    
    public int getTrangThai(){return trangThai;}
    public void setTrangThai(int trangThai){ this.trangThai = trangThai;}

    public int getSoTinChi() { return soTinChi; }
    public void setSoTinChi(int soTinChi) { this.soTinChi = soTinChi; }

    public String getKhoa() { return khoa; }
    public void setKhoa(String khoa) { this.khoa = khoa; }
    
    @Override
    public String toString() {
        return tenMon;
    }
}
