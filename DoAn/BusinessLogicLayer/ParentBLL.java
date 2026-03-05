package BusinessLogicLayer;

import DAO.ParentDAO;
import DataObject.Parent;
import java.util.List;

public class ParentBLL {
    private final ParentDAO dao;

    public ParentBLL() {
        this.dao = new ParentDAO();
    }

    public boolean themParent(Parent p) {
        if (p == null) return false;
        return dao.add(p);
    }

    public List<Parent> getAll() {
        return dao.getAll();
    }

    public boolean suaParent(Parent p) {
        if (p == null) return false;
        return dao.update(p);
    }

    public boolean xoaParent(String ma) {
        if (ma == null || ma.trim().isEmpty()) return false;
        return dao.delete(ma);
    }

    public Parent getByMa(String ma) {
        if (ma == null || ma.trim().isEmpty()) return null;
        return dao.getById(ma);
    }

    public List<Parent> getParentsByHocSinh(String maHS) {
        if (maHS == null || maHS.trim().isEmpty()) return java.util.Collections.emptyList();
        return dao.getParentsByHocSinh(maHS);
    }

    public boolean addRelation(String maHS, String maPH, String quanHe) {
        if (maHS == null || maHS.trim().isEmpty() || maPH == null || maPH.trim().isEmpty()) return false;
        return dao.addRelation(maHS, maPH, quanHe == null ? "" : quanHe);
    }

    public boolean deleteRelation(String maHS, String maPH) {
        if (maHS == null || maHS.trim().isEmpty() || maPH == null || maPH.trim().isEmpty()) return false;
        return dao.deleteRelation(maHS, maPH);
    }

    public List<DataObject.HocSinh> getStudentsByParent(String maPH) {
        if (maPH == null || maPH.trim().isEmpty()) return java.util.Collections.emptyList();
        return dao.getStudentsByParent(maPH);
    }
}
