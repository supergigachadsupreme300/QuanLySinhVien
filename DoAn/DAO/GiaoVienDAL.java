package DataAcessLayer;

import DataObject.GiaoVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienDAL {
    private Connection con;

    // Constructor mặc định: tự mở kết nối
    public GiaoVienDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    // Constructor đầy đủ: nếu muốn truyền connection từ ngoài
    public GiaoVienDAL(Connection con) {
        this.con = con;
    }

    // ===== GET ALL =====
    public List<GiaoVien> getAll() {
        List<GiaoVien> list = new ArrayList<>();
        String sql = "SELECT maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai FROM GIAOVIEN";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GiaoVien gv = new GiaoVien(
                    rs.getString("maGV"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("soDienThoai"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getInt("trangThai")
                );
                list.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ===== GET ALL ACTIVE =====
    public List<GiaoVien> getAllActive() {
        List<GiaoVien> list = new ArrayList<>();
        String sql = "SELECT maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai FROM GIAOVIEN WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                GiaoVien gv = new GiaoVien(
                    rs.getString("maGV"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("soDienThoai"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getInt("trangThai")
                );
                list.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET ALL ACTIVE (Procedure) =====
    public List<GiaoVien> getAllActiveByProc() {
        List<GiaoVien> list = new ArrayList<>();
        String sql = "{call sp_getAllActiveGiaoVien()}"; // procedure trong DB
        try (CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                GiaoVien gv = new GiaoVien(
                    rs.getString("maGV"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("soDienThoai"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getInt("trangThai")
                );
                list.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    

    // ===== INSERT =====
    public boolean insert(GiaoVien gv) {
        String sql = "INSERT INTO GIAOVIEN(maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gv.getMaGV());
            ps.setString(2, gv.getHoTen());
            ps.setDate(3, Date.valueOf(gv.getNgaySinh()));
            ps.setString(4, gv.getGioiTinh());
            ps.setString(5, gv.getDienThoai());
            ps.setString(6, gv.getEmail());
            ps.setString(7, gv.getDiaChi());
            ps.setInt(8, gv.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== UPDATE =====
    public boolean update(GiaoVien gv) {
        String sql = "UPDATE GIAOVIEN SET hoTen=?, ngaySinh=?, gioiTinh=?, soDienThoai=?, email=?, diaChi=?, trangThai=? WHERE maGV=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gv.getHoTen());
            ps.setDate(2, Date.valueOf(gv.getNgaySinh()));
            ps.setString(3, gv.getGioiTinh());
            ps.setString(4, gv.getDienThoai());
            ps.setString(5, gv.getEmail());
            ps.setString(6, gv.getDiaChi());
            ps.setInt(7, gv.getTrangThai());
            ps.setString(8, gv.getMaGV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== DELETE (soft) =====
    public boolean delete(String maGV) {
        String sql = "UPDATE GIAOVIEN SET trangThai = 0 WHERE maGV=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== FIND BY ID =====
    public GiaoVien findByMaGV(String maGV) {
        String sql = "SELECT maGV, hoTen, ngaySinh, gioiTinh, soDienThoai, email, diaChi, trangThai FROM GIAOVIEN WHERE maGV=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGV);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new GiaoVien(
                    rs.getString("maGV"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("soDienThoai"),
                    rs.getString("email"),
                    rs.getString("diaChi"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
