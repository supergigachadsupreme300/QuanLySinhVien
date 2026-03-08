package DAO;

import DataObject.HocSinh;
import DataObject.Diem;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.sql.*;

public class ExcelDAL {

    private HocSinhDAO hocSinhDAO;
    private DiemDAL diemDAL;
    private Connection conn;

    public ExcelDAL() {

        hocSinhDAO = new HocSinhDAO();
        diemDAL = new DiemDAL();
        DatabaseConnect db = new DatabaseConnect();
        this.conn = db.openConnection();
        

    }

    // ================= PREVIEW HỌC SINH =================
    public void previewHocSinh(String path, DefaultTableModel model) {

        try {

            FileInputStream fis = new FileInputStream(path);
            Workbook wb = new XSSFWorkbook(fis);

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                String maHS = row.getCell(0).getStringCellValue();
                String hoTen = row.getCell(1).getStringCellValue();
                String maLop = row.getCell(2).getStringCellValue();

                Date date = row.getCell(3).getDateCellValue();
                LocalDate ngaySinh = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                String gioiTinh = row.getCell(4).getStringCellValue();
                String diaChi = row.getCell(5).getStringCellValue();

                model.addRow(new Object[]{
                        maHS, hoTen, maLop,ngaySinh, gioiTinh, diaChi,
                });
            }

            wb.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // ================= IMPORT HỌC SINH =================
    public boolean importHocSinh(File file) {

        try {

            FileInputStream fis = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(fis);

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                HocSinh hs = new HocSinh();

                hs.setMaHS(row.getCell(0).getStringCellValue());
                hs.setHoTen(row.getCell(1).getStringCellValue());

                hs.setMaLop(row.getCell(2).getStringCellValue());

                Date date = row.getCell(3).getDateCellValue();
                LocalDate ngaySinh = date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                hs.setNgaySinh(ngaySinh);

                hs.setGioiTinh(row.getCell(4).getStringCellValue());
                hs.setDiaChi(row.getCell(5).getStringCellValue());

                hs.setTrangThai(1);

                hocSinhDAO.add(hs);
            }

            wb.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }

    // ================= PREVIEW ĐIỂM =================
    public void previewDiem(String path, DefaultTableModel model) {

        try {

            FileInputStream fis = new FileInputStream(path);
            Workbook wb = new XSSFWorkbook(fis);

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                String maHS = row.getCell(0).getStringCellValue();
                String maMon = row.getCell(1).getStringCellValue();
                String hocKy = row.getCell(2).getStringCellValue();

                double tx = row.getCell(3).getNumericCellValue();
                double gk = row.getCell(4).getNumericCellValue();
                double ck = row.getCell(5).getNumericCellValue();

                model.addRow(new Object[]{
                        maHS, maMon, hocKy, tx, gk, ck
                });
            }

            wb.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    // ================= IMPORT ĐIỂM =================
    public boolean importDiem(File file) {

        try {

            FileInputStream fis = new FileInputStream(file);
            Workbook wb = new XSSFWorkbook(fis);

            Sheet sheet = wb.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                Diem d = new Diem();

                String maHS = row.getCell(0).getStringCellValue();
                String maMon = row.getCell(1).getStringCellValue();
                String hocKy = row.getCell(2).getStringCellValue();

                double tx = row.getCell(3).getNumericCellValue();
                double gk = row.getCell(4).getNumericCellValue();
                double ck = row.getCell(5).getNumericCellValue();

                d.setMaHS(maHS);
                d.setMaMon(maMon);
                d.setMaHocKy(hocKy);

                d.setDiemThuongXuyen(tx);
                d.setDiemGiuaKy(gk);
                d.setDiemCuoiKy(ck);

                d.setDiemTBMonHocKy((tx + gk * 2 + ck * 3) / 6);

                diemDAL.add(d);
            }

            wb.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
    // ================= EXPORT EXCEL =================
    public void previewExport(String maLop, DefaultTableModel model) {

        try {

            String sql = """
            SELECT hs.MaHS, hs.HoTen, hs.MaLop,
                   d.MaMon, d.DiemThuongXuyen, d.DiemGiuaKy, d.DiemCuoiKy
            FROM HocSinh hs
            JOIN Diem d ON hs.MaHS = d.MaHS
            WHERE hs.MaLop = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maLop);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getString("MaHS"),
                        rs.getString("HoTen"),
                        rs.getString("MaLop"),
                        rs.getString("MaMon"),
                        rs.getDouble("DiemThuongXuyen"),
                        rs.getDouble("DiemGiuaKy"),
                        rs.getDouble("DiemCuoiKy")
                });

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    
    public void previewDiemTheoLopMon(String maLop,String maMon,DefaultTableModel model){

        try{

            String sql = """
            SELECT hs.MaHS,hs.HoTen,
                   d.DiemThuongXuyen,
                   d.DiemGiuaKy,
                   d.DiemCuoiKy,
                   d.DiemTBMonHocKy
            FROM HocSinh hs
            JOIN Diem d ON hs.MaHS = d.MaHS
            WHERE hs.MaLop = ? AND d.MaMon = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maLop);
            ps.setString(2, maMon);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                model.addRow(new Object[]{
                        rs.getString("MaHS"),
                        rs.getString("HoTen"),
                        rs.getDouble("DiemThuongXuyen"),
                        rs.getDouble("DiemGiuaKy"),
                        rs.getDouble("DiemCuoiKy"),
                        rs.getDouble("DiemTBMonHocKy")
                });

            }

        }catch(Exception e){
            e.printStackTrace();
        }

    }
    
    public boolean exportDiemTheoLopMon(String maLop, String maMon, File file) {

        try {

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("BangDiem");

            // Header
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("MaHS");
            header.createCell(1).setCellValue("HoTen");
            header.createCell(2).setCellValue("DiemThuongXuyen");
            header.createCell(3).setCellValue("DiemGiuaKy");
            header.createCell(4).setCellValue("DiemCuoiKy");
            header.createCell(5).setCellValue("DiemTB");

            String sql = """
            SELECT hs.MaHS, hs.HoTen,
                   d.DiemThuongXuyen,
                   d.DiemGiuaKy,
                   d.DiemCuoiKy,
                   d.DiemTBMonHocKy
            FROM HocSinh hs
            JOIN Diem d ON hs.MaHS = d.MaHS
            WHERE hs.MaLop = ? AND d.MaMon = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, maLop);
            ps.setString(2, maMon);

            ResultSet rs = ps.executeQuery();

            int rowIndex = 1;

            while (rs.next()) {

                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(rs.getString("MaHS"));
                row.createCell(1).setCellValue(rs.getString("HoTen"));
                row.createCell(2).setCellValue(rs.getDouble("DiemThuongXuyen"));
                row.createCell(3).setCellValue(rs.getDouble("DiemGiuaKy"));
                row.createCell(4).setCellValue(rs.getDouble("DiemCuoiKy"));
                row.createCell(5).setCellValue(rs.getDouble("DiemTBMonHocKy"));

            }

            // Auto resize
            for (int i = 0; i <= 5; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fos = new FileOutputStream(file);

            wb.write(fos);

            fos.close();
            wb.close();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

        }

        return false;
    }
}