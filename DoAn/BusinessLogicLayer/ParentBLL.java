package BusinessLogicLayer;

import DataObject.Parent;
import java.util.ArrayList;
import java.util.List;

public class ParentBLL {
    private final List<Parent> dsParent;

    public ParentBLL() {
        dsParent = new ArrayList<>();
    }

    public boolean themParent(Parent p) {
        if (p == null) return false;
        dsParent.add(p);
        return true;
    }

    public List<Parent> getAll() {
        return dsParent;
    }

    public boolean suaParent(Parent p) {
        if (p == null) return false;
        for (int i = 0; i < dsParent.size(); i++) {
            if (dsParent.get(i).getMaPhH().equals(p.getMaPhH())) {
                dsParent.set(i, p);
                return true;
            }
        }
        return false;
    }

    public boolean xoaParent(String ma) {
        return dsParent.removeIf(pp -> pp.getMaPhH().equals(ma));
    }

    public Parent getByMa(String ma) {
        for (Parent p : dsParent) if (p.getMaPhH().equals(ma)) return p;
        return null;
    }
}
