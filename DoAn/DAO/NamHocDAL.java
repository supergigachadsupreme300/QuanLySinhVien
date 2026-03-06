package DAO;

import DataObject.NamHoc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NamHocDAL {
    private Connection con;

    public NamHocDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }    
    
    public NamHocDAL(Connection con) {
        this.con = con;
    }

    // Lấy tất cả năm học (chỉ lấy đang hoạt động)
    public List<NamHoc> getAll() {
        List<NamHoc> list = new ArrayList<>();
        String sql = "SELECT maNam, tenNam, trangThai FROM NAM WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NamHoc nh = new NamHoc(
                    rs.getString("maNam"),
                    rs.getString("tenNam"),
                    rs.getInt("trangThai")
                );
                list.add(nh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ===== GET ALL ACTIVE =====
    public List<NamHoc> getAllActive() {
        List<NamHoc> list = new ArrayList<>();
        String sql = "SELECT maNam, tenNam, trangThai FROM NAM WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                NamHoc nh = new NamHoc(
                    rs.getString("maNam"),
                    rs.getString("tenNam"),
                    rs.getInt("trangThai")
                );
                list.add(nh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NamHoc> getAllActiveByProc() {
        // Stored procedure may not exist; reuse SELECT-based method
        return getAllActive();
    }

    
    
    // Thêm năm học
    public boolean insert(NamHoc nh) {
        String sql = "INSERT INTO NAM(maNam, tenNam, trangThai) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nh.getMaNH());
            ps.setString(2, nh.getTenNH());
            ps.setInt(3, nh.getTrangThai()); // phải set trạng thái khi thêm mới
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật năm học
    public boolean update(NamHoc nh) {
        String sql = "UPDATE NAM SET tenNam=?, trangThai=? WHERE maNam=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nh.getTenNH());
            ps.setInt(2, nh.getTrangThai());
            ps.setString(3, nh.getMaNH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Soft delete: chỉ đổi trạng thái = 0
    public boolean delete(String maNH) {
        String sql = "UPDATE NAM SET trangThai = 0 WHERE maNam=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm năm học theo mã (chỉ lấy đang hoạt động)
    public NamHoc findByMaNH(String maNH) {
        String sql = "SELECT maNam, tenNam, trangThai FROM NAM WHERE maNam=? AND trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new NamHoc(
                    rs.getString("maNam"),
                    rs.getString("tenNam"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
