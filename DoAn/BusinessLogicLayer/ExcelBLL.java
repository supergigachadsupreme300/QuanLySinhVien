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

    // ===== PREVIEW ĐIỂM THEO LỚP + MÔN =====
    public void previewDiemTheoLopMon(String maLop, String maMon, String maHK, DefaultTableModel model){
        excelDAL.previewDiemTheoLopMon(maLop, maMon, model);
    }

    // ===== EXPORT ĐIỂM THEO LỚP + MÔN =====
    public boolean exportDiemTheoLopMon(String maLop,String maMon, File file) {
        return excelDAL.exportDiemTheoLopMon(maLop, maMon, file);
    }

    // ===== PREVIEW BẢNG ĐIỂM TẤT CẢ MÔN =====
    public void previewBangDiemTatCaMon(String maLop, String maHK,  DefaultTableModel model){
        excelDAL.previewBangDiemTatCaMon(maLop, maHK, model);
    }

    // ===== EXPORT BẢNG ĐIỂM TẤT CẢ MÔN =====
    public boolean exportBangDiemTatCaMon(String maLop, String maHK, File file){
        return excelDAL.exportBangDiemTatCaMon(maLop, maHK, file);
    }
}
