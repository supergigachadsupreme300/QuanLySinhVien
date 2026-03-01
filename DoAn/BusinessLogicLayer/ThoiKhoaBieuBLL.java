package BusinessLogicLayer;

import DAO.ThoiKhoaBieuDAL;
import DataObject.ThoiKhoaBieu;
import java.util.List;

public class ThoiKhoaBieuBLL {
    ThoiKhoaBieuDAL tkbDAL = new ThoiKhoaBieuDAL();

    public List<ThoiKhoaBieu> getAll() {
        return tkbDAL.getAll();
    }

    public List<ThoiKhoaBieu> getAllActive() {
        return tkbDAL.getAllActive();
    }

    public String themThoiKhoaBieu(ThoiKhoaBieu tkb) {
        if (tkb == null) return "Dữ liệu TKB không hợp lệ!";
        if (tkbDAL.findByMaTKB(tkb.getMaTKB()) != null) {
            return "Mã thời khóa biểu đã tồn tại!";
        }
        return tkbDAL.insert(tkb) ? "Thêm TKB thành công!" 
                                  : "Thêm TKB thất bại!";
    }

    public String suaThoiKhoaBieu(ThoiKhoaBieu tkb) {
        if (tkb == null) return "Dữ liệu TKB không hợp lệ!";
        return tkbDAL.update(tkb) ? "Sửa TKB thành công!" 
                                  : "Sửa TKB thất bại!";
    }

    public String xoaThoiKhoaBieu(String maTKB) {
        return tkbDAL.delete(maTKB) ? "Xóa TKB thành công!" 
                                    : "Xóa TKB thất bại!";
    }

    public ThoiKhoaBieu getByMaTKB(String maTKB) {
        return tkbDAL.findByMaTKB(maTKB);
    }
}
