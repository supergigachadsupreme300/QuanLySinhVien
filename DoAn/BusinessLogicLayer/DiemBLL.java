package BusinessLogicLayer;

import DAO.DiemDAL;
import DAO.HocKyDAL;
import DAO.HocSinhDAO;
import DAO.MonHocDAO;
import DataObject.Diem;
import java.sql.Connection;
import java.util.List;

public class DiemBLL {
    private DiemDAL diemDAL = new DiemDAL();
    private Connection con;

    public DiemBLL() {
    }

    public DiemBLL(Connection con) {
        this.con = con;
        diemDAL = new DiemDAL(con);
    }

    public List<Diem> getByMaHS(String maHS) {
        return diemDAL.getByMaHS(maHS);
    }
    
    public Diem getDiem(String maHS, String maMon, String maHK){
        return diemDAL.getDiem(maHS, maMon, maHK);
    }

    public String them(Diem d) {
        if (d == null) return "Dữ liệu điểm không hợp lệ.";


        HocSinhDAO hsDao = new HocSinhDAO(con);
        if (hsDao.getById(d.getMaHS()) == null) {
            return "Không tìm thấy Học sinh với mã: " + d.getMaHS();
        }

        MonHocDAO monDao = new MonHocDAO(con);
        if (monDao.findByMaMon(d.getMaMon()) == null) {
            return "Không tìm thấy môn với mã: " + d.getMaMon();
        }
        HocKyDAL hkDao = new HocKyDAL(con);
        if (hkDao.findByMaHK(d.getMaHocKy()) == null) {
            return "Không tìm thấy Học kỳ với mã: " + d.getMaHocKy();
        }


        if (d.getMaDiem() != null && d.getMaDiem().length() > 50) {
            return "Mã điểm quá dài (>50 ký tự). Vui lòng kiểm tra quy tắc sinh mã.";
        }

        boolean ok = diemDAL.add(d);
        return ok ? "Thêm điểm thành công!" : "Thêm điểm thất bại! (Kiểm tra ràng buộc dữ liệu)";
    }

    public String sua(Diem d) {
        if (d == null) return "Dữ liệu điểm không hợp lệ.";


        HocSinhDAO hsDao = new HocSinhDAO(con);
        if (hsDao.getById(d.getMaHS()) == null) {
            return "Không tìm thấy Học sinh với mã: " + d.getMaHS();
        }

        MonHocDAO monDao = new MonHocDAO(con);
        if (monDao.findByMaMon(d.getMaMon()) == null) {
            return "Không tìm thấy môn với mã: " + d.getMaMon();
        }

        HocKyDAL hkDao = new HocKyDAL(con);
        if (hkDao.findByMaHK(d.getMaHocKy()) == null) {
            return "Không tìm thấy Học kỳ với mã: " + d.getMaHocKy();
        }

        boolean ok = diemDAL.update(d);
        return ok ? "Cập nhật điểm thành công!" : "Cập nhật điểm thất bại! (Kiểm tra ràng buộc dữ liệu)";
    }

    public String xoa(String maDiem) {
        boolean ok = diemDAL.delete(maDiem);
        return ok ? "Xóa điểm thành công!" : "Xóa điểm thất bại!";
    }
    public double getDiemTBHocKy(String maHS,String maHK){
        return diemDAL.getDiemTBHocKy(maHS, maHK);
    }
    
}
