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
    
    public Mon(){
        maMon ="";
        tenMon ="";
        trangThai=0;
    }
    
    public Mon(String maMon, String tenMon, int trangThai){
        this.maMon = maMon;
        this.tenMon = tenMon;
        this.trangThai = trangThai;
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
    
    @Override
    public String toString() {
        return tenMon;
    }
}
