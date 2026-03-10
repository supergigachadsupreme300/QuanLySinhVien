package BusinessLogicLayer;

import DAO.XepLoaiDAO;
import DataObject.XepLoai;
import java.util.List;

public class XepLoaiBLL {

    private XepLoaiDAO xepLoaiDAO;

    public XepLoaiBLL() {
        xepLoaiDAO = new XepLoaiDAO();
    }

    // ===== GET BY ID =====
    public XepLoai getById(String maXepLoai) {
        if (maXepLoai == null || maXepLoai.trim().isEmpty()) return null;
        return xepLoaiDAO.getById(maXepLoai);
    }

    // ===== GET ALL =====
    public List<XepLoai> getAll() {
        return xepLoaiDAO.getAll();
    }

    // ===== GET BY MA HỌC SINH =====
    public List<XepLoai> getByMaHS(String maHS) {
        if (maHS == null || maHS.trim().isEmpty()) return null;
        return xepLoaiDAO.getByMaHS(maHS);
    }

    // ===== ADD =====
    public boolean add(XepLoai xl) {
        if (!kiemTraHopLe(xl)) return false;

        // Không cho trùng mã xếp loại
        if (xepLoaiDAO.getById(xl.getMaXepLoai()) != null) {
            return false;
        }

        return xepLoaiDAO.add(xl);
    }

    // ===== UPDATE =====
    public boolean update(XepLoai xl) {
        if (!kiemTraHopLe(xl)) return false;

        return xepLoaiDAO.update(xl);
    }

    // ===== DELETE =====
    public boolean delete(String maXepLoai) {
        if (maXepLoai == null || maXepLoai.trim().isEmpty()) return false;

        return xepLoaiDAO.delete(maXepLoai);
    }

    // ===== VALIDATE NGHIỆP VỤ =====
    private boolean kiemTraHopLe(XepLoai xl) {

        if (xl == null) return false;

        if (xl.getMaXepLoai() == null || xl.getMaXepLoai().trim().isEmpty())
            return false;

        if (xl.getMaHS() == null || xl.getMaHS().trim().isEmpty())
            return false;

        if (xl.getMaHocKy() == null || xl.getMaHocKy().trim().isEmpty())
            return false;

        if (xl.getXepLoaiHocLuc() == null || xl.getXepLoaiHocLuc().trim().isEmpty())
            return false;

        if (xl.getXepLoaiHanhKiem() == null || xl.getXepLoaiHanhKiem().trim().isEmpty())
            return false;

        if (xl.getDiemTBChung() < 0 || xl.getDiemTBChung() > 10)
            return false;

        // nhanXet có thể null → không bắt buộc

        return true;   
    }
    
    public String xepHocLuc(double tb){

        if(tb >= 8)
            return "Giỏi";
        else if(tb >= 6.5)
            return "Khá";
        else if(tb >= 5)
            return "Trung bình";
        else
            return "Yếu";
    }

}
