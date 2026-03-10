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


            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("STT");
            header.createCell(1).setCellValue("MaHS");
            header.createCell(2).setCellValue("HoTen");
            header.createCell(3).setCellValue("DiemThuongXuyen");
            header.createCell(4).setCellValue("DiemGiuaKy");
            header.createCell(5).setCellValue("DiemCuoiKy");
            header.createCell(6).setCellValue("DiemTB");

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

                int stt = 1;
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(stt++);
                row.createCell(1).setCellValue(rs.getString("MaHS"));
                row.createCell(2).setCellValue(rs.getString("HoTen"));
                row.createCell(3).setCellValue(rs.getDouble("DiemThuongXuyen"));
                row.createCell(4).setCellValue(rs.getDouble("DiemGiuaKy"));
                row.createCell(5).setCellValue(rs.getDouble("DiemCuoiKy"));
                row.createCell(6).setCellValue(rs.getDouble("DiemTBMonHocKy"));

            }

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


    public void previewBangDiemTatCaMon(String maLop, String maHK, DefaultTableModel model){
        try{
            MonHocDAO monDAO = new MonHocDAO();
            DiemDAL diemDAL = new DiemDAL();
            List<String> listMon = monDAO.getAllMaMon();

            model.setColumnCount(0);
            model.setRowCount(0);

            model.addColumn("MaHS");
            model.addColumn("HoTen");

            for(String mon : listMon){
                model.addColumn(mon);
            }

            String sql = """
            SELECT MaHS, HoTen
            FROM HocSinh
            WHERE MaLop = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maLop);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                String maHS = rs.getString("MaHS");

                Object[] row = new Object[listMon.size() + 2];

                row[0] = maHS;
                row[1] = rs.getString("HoTen");

                for(int i = 0; i < listMon.size(); i++){

                    String maMon = listMon.get(i);

                    Diem d = diemDAL.getDiem(maHS, maMon, maHK);

                    if(d != null){
                        row[i + 2] = d.getDiemTBMonHocKy();
                    }else{
                        row[i + 2] = "";
                    }
                }
                model.addRow(row);

            }
            rs.close();
            ps.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public boolean exportBangDiemTatCaMon(String maLop, String maHK, File file){
        try{
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("BangDiem");

            MonHocDAO monDAO = new MonHocDAO();
            DiemDAL diemDAL = new DiemDAL();
            List<String> listMon = monDAO.getAllMaMon();

            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("MaHS");
            header.createCell(1).setCellValue("HoTen");

            for(int i=0;i<listMon.size();i++){
                header.createCell(i+2).setCellValue(listMon.get(i));
            }

            String sql = """
            SELECT MaHS, HoTen
            FROM HocSinh
            WHERE MaLop = ?
            """;

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, maLop);

            ResultSet rs = ps.executeQuery();

            int rowIndex = 1;

            while(rs.next()){
                String maHS = rs.getString("MaHS");

                Row row = sheet.createRow(rowIndex++);

                row.createCell(0).setCellValue(maHS);
                row.createCell(1).setCellValue(rs.getString("HoTen"));

                for(int i=0;i<listMon.size();i++){

                    String maMon = listMon.get(i);

                    Diem d = diemDAL.getDiem(maHS, maMon, maHK);

                    if(d == null){
                        row.createCell(i+2).setCellValue("");
                    }else{
                        row.createCell(i+2).setCellValue(d.getDiemTBMonHocKy());
                    }
                }
            }
            for(int i=0;i<listMon.size()+2;i++){
                sheet.autoSizeColumn(i);
            }
            FileOutputStream fos = new FileOutputStream(file);
            wb.write(fos);
            fos.close();
            wb.close();
            rs.close();
            ps.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
