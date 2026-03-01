package BusinessLogicLayer;

import DAO.DiemDAL;
import DataObject.Diem;
import java.sql.Connection;
import java.util.List;

public class DiemBLL {
    private DiemDAL dao;

    public DiemBLL(Connection con) {
        dao = new DiemDAL(con);
    }

    public List<Diem> getByMaHS(String maHS) {
        return dao.getByMaHS(maHS);
    }

    public String them(Diem d) {
        return dao.add(d) ? "Thêm điểm thành công!" : "Thêm điểm thất bại!";
    }

    public String sua(Diem d) {
        return dao.update(d) ? "Cập nhật điểm thành công!" : "Cập nhật điểm thất bại!";
    }

    public String xoa(String maDiem) {
        return dao.delete(maDiem) ? "Xóa điểm thành công!" : "Xóa điểm thất bại!";
    }
}