package DataAcessLayer;

import DataObject.HocKy;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HocKyDAL {
    private Connection con;

    // Constructor mặc định: tự mở kết nối
    public HocKyDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    // Constructor đầy đủ: nếu muốn truyền connection từ ngoài
    public HocKyDAL(Connection con) {
        this.con = con;
    }

    // ===== GET ALL =====
    public List<HocKy> getAll() {
        List<HocKy> list = new ArrayList<>();
        String sql = "SELECT maHK, tenHK, maNam, trangThai FROM HOCKY";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HocKy hk = new HocKy(
                    rs.getString("maHK"),
                    rs.getString("tenHK"),
                    rs.getString("maNam"),
                    rs.getInt("trangThai")
                );
                list.add(hk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET ALL ACTIVE =====
    public List<HocKy> getAllActive() {
        List<HocKy> list = new ArrayList<>();
        String sql = "SELECT maHK, tenHK, maNam, trangThai FROM HOCKY WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HocKy hk = new HocKy(
                    rs.getString("maHK"),
                    rs.getString("tenHK"),
                    rs.getString("maNam"),
                    rs.getInt("trangThai")
                );
                list.add(hk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET ALL ACTIVE (Procedure) =====
    public List<HocKy> getAllActiveByProc() {
        List<HocKy> list = new ArrayList<>();
        String sql = "{call sp_getAllActiveHocKy()}"; // procedure trong DB
        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                HocKy hk = new HocKy(
                    rs.getString("maHK"),
                    rs.getString("tenHK"),
                    rs.getString("maNam"),
                    rs.getInt("trangThai")
                );
                list.add(hk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    // ===== INSERT =====
    public boolean insert(HocKy hk) {
        String sql = "INSERT INTO HOCKY(maHK, tenHK, maNam, trangThai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hk.getMaHK());
            ps.setString(2, hk.getTenHK());
            ps.setString(3, hk.getMaNH());
            ps.setInt(4, hk.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== UPDATE =====
    public boolean update(HocKy hk) {
        String sql = "UPDATE HOCKY SET tenHK=?, maNam=?, trangThai=? WHERE maHK=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hk.getTenHK());
            ps.setString(2, hk.getMaNH());
            ps.setInt(3, hk.getTrangThai());
            ps.setString(4, hk.getMaHK());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== DELETE (soft) =====
    public boolean delete(String maHK) {
        String sql = "UPDATE HOCKY SET trangThai = 0 WHERE maHK=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHK);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== FIND BY ID =====
    public HocKy findByMaHK(String maHK) {
        String sql = "SELECT maHK, tenHK, maNam, trangThai FROM HOCKY WHERE maHK=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHK);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HocKy(
                    rs.getString("maHK"),
                    rs.getString("tenHK"),
                    rs.getString("maNam"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
