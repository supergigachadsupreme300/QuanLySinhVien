
package DataObject;


public class NamHoc {

    private String maNH;
    private String tenNH;
    private int trangThai;


    public NamHoc() {
        maNH = "";
        tenNH = "";
        trangThai=0;
    }


    public NamHoc(String maNH, String tenNH, int trangThai) {
        this.maNH = maNH;
        this.tenNH = tenNH;
        this.trangThai = trangThai;
    }


    public NamHoc(NamHoc nh) {
        this.maNH = nh.maNH;
        this.tenNH = nh.tenNH;
        this.trangThai = nh.trangThai;
    }


    public String getMaNH() {return maNH;}
    public void setMaNH(String maNH) {this.maNH = maNH;}
    public String getTenNH() {return tenNH;}
    public void setTenNH(String tenNH) {this.tenNH = tenNH;}
    public int getTrangThai(){return trangThai;}
    public void setTrangThai(int trangThai) {this.trangThai = trangThai;}
    

    @Override
    public String toString() {
        return tenNH;
    }
}
