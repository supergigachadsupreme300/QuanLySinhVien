/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataObject;

/**
 *
 * @author admin
 */
public class Lop {
    private String maLop;
    private String tenLop;
    private int siSo;
    private String maNH;
    private String maGVCN;

    public Lop(){
        maLop = "";
        tenLop = "";
        siSo = 0;
        maNH = "";
        maGVCN = "";
    }
    
    public Lop(String maLop, String tenLop, int siSo, 
            String maNH, String maGVCN){
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.siSo = siSo;
        this.maNH = maNH;
        this.maGVCN = maGVCN;
    }
    
    public Lop(Lop lop){
        this.maLop = lop.maLop;
        this.tenLop = lop.tenLop;
        this.siSo = lop.siSo;
        this.maNH = lop.maNH;
        this.maGVCN = lop.maGVCN;
    }
    public String getMaLop(){return maLop;}
    public void setMaLop(String maLop){this.maLop = maLop;}
    
    public String getTenLop(){return tenLop;}
    public void setTenLop(String tenLop){this.tenLop = tenLop;}
    
    public String getMaNH(){return maNH;}
    public void setMaNH(String maNH){this.maNH = maNH;}
    
    public int getSiSo(){return siSo;}
    public void setSiSo(int siSo){this.siSo = siSo;}
    
    public String getMaGVCN(){return maGVCN;}
    public void setMaGVCN(String maGVCN){this.maGVCN = maGVCN;}
    
    @Override
    public String toString() {
        return maLop + " - " + tenLop + " - Sĩ số: " + siSo;
    }
}
