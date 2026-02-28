package DataObject;

public class XepLoai {

    // ===== Thuộc tính =====
    private String maXepLoai;
    private String maHS;
    private String maHocKy;
    private String xepLoaiHocLuc;
    private String xepLoaiHanhKiem;
    private double diemTBChung;
    private String nhanXet;
    private boolean duocLenLop;

    // ===== Constructor rỗng =====
    public XepLoai() {
        maXepLoai = "";
        maHS = "";
        maHocKy = "";
        xepLoaiHocLuc = "";
        xepLoaiHanhKiem = "";
        diemTBChung = 0;
        nhanXet = "";
        duocLenLop = true;
    }

    // ===== Constructor đầy đủ =====
    public XepLoai(String maXepLoai, String maHS, String maHocKy,
                   String xepLoaiHocLuc, String xepLoaiHanhKiem,
                   double diemTBChung, String nhanXet, boolean duocLenLop) {
        this.maXepLoai = maXepLoai;
        this.maHS = maHS;
        this.maHocKy = maHocKy;
        this.xepLoaiHocLuc = xepLoaiHocLuc;
        this.xepLoaiHanhKiem = xepLoaiHanhKiem;
        this.diemTBChung = diemTBChung;
        this.nhanXet = nhanXet;
        this.duocLenLop = duocLenLop;
    }

    // ===== Constructor copy =====
    public XepLoai(XepLoai xl) {
        this.maXepLoai = xl.maXepLoai;
        this.maHS = xl.maHS;
        this.maHocKy = xl.maHocKy;
        this.xepLoaiHocLuc = xl.xepLoaiHocLuc;
        this.xepLoaiHanhKiem = xl.xepLoaiHanhKiem;
        this.diemTBChung = xl.diemTBChung;
        this.nhanXet = xl.nhanXet;
        this.duocLenLop = xl.duocLenLop;
    }

    // ===== Getter / Setter =====
    public String getMaXepLoai() { return maXepLoai; }
    public void setMaXepLoai(String maXepLoai) { this.maXepLoai = maXepLoai; }

    public String getMaHS() { return maHS; }
    public void setMaHS(String maHS) { this.maHS = maHS; }

    public String getMaHocKy() { return maHocKy; }
    public void setMaHocKy(String maHocKy) { this.maHocKy = maHocKy; }

    public String getXepLoaiHocLuc() { return xepLoaiHocLuc; }
    public void setXepLoaiHocLuc(String xepLoaiHocLuc) {
        this.xepLoaiHocLuc = xepLoaiHocLuc;
    }

    public String getXepLoaiHanhKiem() { return xepLoaiHanhKiem; }
    public void setXepLoaiHanhKiem(String xepLoaiHanhKiem) {
        this.xepLoaiHanhKiem = xepLoaiHanhKiem;
    }

    public double getDiemTBChung() { return diemTBChung; }
    public void setDiemTBChung(double diemTBChung) {
        this.diemTBChung = diemTBChung;
    }

    public String getNhanXet() { return nhanXet; }
    public void setNhanXet(String nhanXet) { this.nhanXet = nhanXet; }

    public boolean isDuocLenLop() { return duocLenLop; }
    public void setDuocLenLop(boolean duocLenLop) {
        this.duocLenLop = duocLenLop;
    }

    // ===== Hỗ trợ hiển thị JTable =====
    @Override
    public String toString() {
        return maHS + " - " + xepLoaiHocLuc + " - " + xepLoaiHanhKiem;
    }
}