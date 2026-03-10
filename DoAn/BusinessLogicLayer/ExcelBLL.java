package BusinessLogicLayer;

import DAO.ExcelDAL;
import java.io.File;
import javax.swing.table.DefaultTableModel;

public class ExcelBLL {

    private ExcelDAL excelDAL;

    public ExcelBLL() {
        excelDAL = new ExcelDAL();
    }


    public void previewHocSinh(String path, DefaultTableModel model) {
        excelDAL.previewHocSinh(path, model);
    }


    public boolean importHocSinh(File file) {
        return excelDAL.importHocSinh(file);
    }


    public void previewDiem(String path, DefaultTableModel model) {
        excelDAL.previewDiem(path, model);
    }


    public boolean importDiem(File file) {
        return excelDAL.importDiem(file);
    }


    public void previewDiemTheoLopMon(String maLop, String maMon, String maHK, DefaultTableModel model){
        excelDAL.previewDiemTheoLopMon(maLop, maMon, model);
    }


    public boolean exportDiemTheoLopMon(String maLop,String maMon, File file) {
        return excelDAL.exportDiemTheoLopMon(maLop, maMon, file);
    }

    public void previewBangDiemTatCaMon(String maLop, String maHK,  DefaultTableModel model){
        excelDAL.previewBangDiemTatCaMon(maLop, maHK, model);
    }


    public boolean exportBangDiemTatCaMon(String maLop, String maHK, File file){
        return excelDAL.exportBangDiemTatCaMon(maLop, maHK, file);
    }
}
