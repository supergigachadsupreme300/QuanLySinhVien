package DAO;

import DataObject.GiaoVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienDAO {
    private Connection con;

    public GiaoVienDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public GiaoVienDAO(Connection con) {
        this.con = con;
    }

    public GiaoVien getByMa(String maGV) {
        String sql = "SELECT * FROM GIAOVIEN WHERE maGV = ? AND trangThai = 1";
        GiaoVien gv = null;
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gv = new GiaoVien();
                    gv.setMaGV(rs.getString("maGV"));
                    gv.setHoTen(rs.getNString("hoTen"));
                    gv.setDienThoai(rs.getString("soDienThoai"));
                    gv.setEmail(rs.getString("email"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gv;
    }

    public List<GiaoVien> getAll() {
        List<GiaoVien> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN WHERE trangThai = 1 ORDER BY maGV";
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GiaoVien gv = new GiaoVien();
                gv.setMaGV(rs.getString("maGV"));
                gv.setHoTen(rs.getNString("hoTen"));
                gv.setDienThoai(rs.getString("soDienThoai"));
                gv.setEmail(rs.getString("email"));
                list.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<GiaoVien> getAllFull() {
        List<GiaoVien> list = new ArrayList<>();
        String sql = "SELECT * FROM GIAOVIEN ORDER BY maGV";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GiaoVien gv = new GiaoVien();
                gv.setMaGV(rs.getString("maGV"));
                gv.setHoTen(rs.getNString("hoTen"));
                gv.setDienThoai(rs.getString("soDienThoai"));
                gv.setEmail(rs.getString("email"));
                gv.setDiaChi(rs.getString("diaChi"));
                gv.setTrangThai(rs.getInt("trangThai"));
                list.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<GiaoVien> getAllActiveByProc() {
        // Stored procedure not present in DB; delegate to SELECT-based method
        return getAll();
    }


    
    public boolean them(GiaoVien gv) {
        String sql = "INSERT INTO GIAOVIEN (maGV, hoTen, soDienThoai, email, trangThai) "
            + "VALUES (?, ?, ?, ?, 1)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gv.getMaGV());
            ps.setNString(2, gv.getHoTen());
            ps.setString(3, gv.getDienThoai());
            ps.setString(4, gv.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(GiaoVien gv) {
        String sql = "UPDATE GIAOVIEN SET hoTen = ?, soDienThoai = ?, email = ? "
            + "WHERE maGV = ? AND trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, gv.getHoTen());
            ps.setString(2, gv.getDienThoai());
            ps.setString(3, gv.getEmail());
            ps.setString(4, gv.getMaGV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String maGV) {
        String sql = "UPDATE GIAOVIEN SET trangThai = 0 WHERE maGV = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
