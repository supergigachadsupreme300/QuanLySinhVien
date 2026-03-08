package BusinessLogicLayer;

import DAO.ExcelDAL;
import javax.swing.table.DefaultTableModel;
import java.io.File;

public class ExcelBLL {

    private ExcelDAL excelDAL;

    public ExcelBLL() {
        excelDAL = new ExcelDAL();
    }

    // ===== PREVIEW HỌC SINH =====
    public void previewHocSinh(String path, DefaultTableModel model) {

        excelDAL.previewHocSinh(path, model);

    }

    // ===== IMPORT HỌC SINH =====
    public boolean importHocSinh(File file) {

        return excelDAL.importHocSinh(file);

    }

    // ===== PREVIEW ĐIỂM =====
    public void previewDiem(String path, DefaultTableModel model) {

        excelDAL.previewDiem(path, model);

    }

    // ===== IMPORT ĐIỂM =====
    public boolean importDiem(File file) {

        return excelDAL.importDiem(file);

    }

    public void previewDiemTheoLopMon(String maLop, String maMon, DefaultTableModel model){
        excelDAL.previewDiemTheoLopMon(maLop, maMon, model);
    }

    public boolean exportDiemTheoLopMon(String maLop,String maMon, File file) {

        return excelDAL.exportDiemTheoLopMon(maLop, maMon, file);

    }
}