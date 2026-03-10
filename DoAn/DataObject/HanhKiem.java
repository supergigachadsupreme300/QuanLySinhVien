package DataObject;


public class HanhKiem {


    private String maHanhKiem;
    private String maHS;
    private String maHocKy;
    private String xepLoai;
    private int soLanViPham;
    private String nhanXet;


    public HanhKiem() {
        maHanhKiem = "";
        maHS = "";
        maHocKy = "";
        xepLoai = "";
        soLanViPham = 0;
        nhanXet = "";
    }


    public HanhKiem(String maHanhKiem, String maHS, String maHocKy,
                    String xepLoai, int soLanViPham, String nhanXet) {
        this.maHanhKiem = maHanhKiem;
        this.maHS = maHS;
        this.maHocKy = maHocKy;
        this.xepLoai = xepLoai;
        this.soLanViPham = soLanViPham;
        this.nhanXet = nhanXet;
    }


    public HanhKiem(HanhKiem hk) {
        this.maHanhKiem = hk.maHanhKiem;
        this.maHS = hk.maHS;
        this.maHocKy = hk.maHocKy;
        this.xepLoai = hk.xepLoai;
        this.soLanViPham = hk.soLanViPham;
        this.nhanXet = hk.nhanXet;
    }


    public String getMaHanhKiem() {
        return maHanhKiem;
    }

    public void setMaHanhKiem(String maHanhKiem) {
        this.maHanhKiem = maHanhKiem;
    }

    public String getMaHS() {
        return maHS;
    }

    public void setMaHS(String maHS) {
        this.maHS = maHS;
    }

    public String getMaHocKy() {
        return maHocKy;
    }

    public void setMaHocKy(String maHocKy) {
        this.maHocKy = maHocKy;
    }

    public String getXepLoai() {
        return xepLoai;
    }

    public void setXepLoai(String xepLoai) {
        this.xepLoai = xepLoai;
    }

    public int getSoLanViPham() {
        return soLanViPham;
    }

    public void setSoLanViPham(int soLanViPham) {
        this.soLanViPham = soLanViPham;
    }

    public String getNhanXet() {
        return nhanXet;
    }

    public void setNhanXet(String nhanXet) {
        this.nhanXet = nhanXet;
    }


    @Override
    public String toString() {
        return maHS + " - " + xepLoai;
    }
}