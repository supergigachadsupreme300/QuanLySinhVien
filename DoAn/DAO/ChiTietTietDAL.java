package DAO;

import DataObject.ChiTietTiet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietTietDAL {
    private Connection con;

    public ChiTietTietDAL(){
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }
    
    public ChiTietTietDAL(Connection con) {
        this.con = con;
    }

    // Lấy tất cả chi tiết tiết
    public List<ChiTietTiet> getAll() {
        List<ChiTietTiet> list = new ArrayList<>();
        String sql = "SELECT maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai FROM CHITIETTIET";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChiTietTiet ct = new ChiTietTiet(
                    rs.getString("maChiTiet"),
                    rs.getString("maTKB"),
                    rs.getString("maMon"),
                    rs.getString("thu"),
                    rs.getInt("tiet"),
                    rs.getString("phongHoc"),
                    rs.getTime("gioBatDau").toLocalTime().toString(),
                    rs.getTime("gioKetThuc").toLocalTime().toString(),
                    rs.getInt("trangThai")
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tất cả chi tiết tiết đang hoạt động
    public List<ChiTietTiet> getAllActive() {
        List<ChiTietTiet> list = new ArrayList<>();
        String sql = "SELECT maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai FROM CHITIETTIET WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ChiTietTiet ct = new ChiTietTiet(
                    rs.getString("maChiTiet"),
                    rs.getString("maTKB"),
                    rs.getString("maMon"),
                    rs.getString("thu"),
                    rs.getInt("tiet"),
                    rs.getString("phongHoc"),
                    rs.getTime("gioBatDau").toLocalTime().toString(),
                    rs.getTime("gioKetThuc").toLocalTime().toString(),
                    rs.getInt("trangThai")
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm chi tiết tiết
    public boolean insert(ChiTietTiet ct) {
        String sql = "INSERT INTO CHITIETTIET(maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            // Ensure maChiTiet length fits DB (VARCHAR(20) in schema). Truncate if necessary to avoid SQL exception.
            String ma = ct.getMaChiTiet() != null ? ct.getMaChiTiet() : "";
            if (ma.length() > 20) {
                System.err.println("[Warning] maChiTiet too long, truncating to 20 chars: " + ma);
                ma = ma.substring(0, 20);
            }
            ps.setString(1, ma);
            ps.setString(2, ct.getMaTKB());
            ps.setString(3, ct.getMaMon());
            ps.setString(4, ct.getThu());
            ps.setInt(5, ct.getTiet());
            ps.setString(6, ct.getPhongHoc());
            ps.setString(7, ct.getGioBatDau());
            ps.setString(8, ct.getGioKetThuc());
            ps.setInt(9, ct.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ChiTietTietDAL.insert] SQLException: " + e.getMessage());
            return false;
        }
    }

    // Cập nhật chi tiết tiết
    public boolean update(ChiTietTiet ct) {
        String sql = "UPDATE CHITIETTIET SET maTKB=?, maMon=?, thu=?, tiet=?, phongHoc=?, gioBatDau=?, gioKetThuc=?, trangThai=? WHERE maChiTiet=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaTKB());
            ps.setString(2, ct.getMaMon());
            ps.setString(3, ct.getThu());
            ps.setInt(4, ct.getTiet());
            ps.setString(5, ct.getPhongHoc());
            ps.setString(6, ct.getGioBatDau());
            ps.setString(7, ct.getGioKetThuc());
            ps.setInt(8, ct.getTrangThai());
            ps.setString(9, ct.getMaChiTiet());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Soft delete: đổi trạng thái = 0
    public boolean delete(String maChiTiet) {
        String sql = "UPDATE CHITIETTIET SET trangThai = 0 WHERE maChiTiet=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maChiTiet);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm chi tiết tiết theo mã
    public ChiTietTiet findByMaChiTiet(String maChiTiet) {
        String sql = "SELECT maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai FROM CHITIETTIET WHERE maChiTiet=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maChiTiet);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ChiTietTiet(
                    rs.getString("maChiTiet"),
                    rs.getString("maTKB"),
                    rs.getString("maMon"),
                    rs.getString("thu"),
                    rs.getInt("tiet"),
                    rs.getString("phongHoc"),
                    rs.getTime("gioBatDau").toLocalTime().toString(),
                    rs.getTime("gioKetThuc").toLocalTime().toString(),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy tất cả chi tiết tiết đang hoạt động bằng proc
public List<ChiTietTiet> getAllActiveProc() {
    // Delegate to SELECT based implementation when stored procedure is not available
    return getAllActive();
}

    // Lấy chi tiết tiết theo mã TKB bằng proc
    public List<ChiTietTiet> getByMaTKBByProc(String maTKB) {
        // Use SELECT query implementation instead of missing stored procedure
        return getByTKB(maTKB);
    }

    // Lấy chi tiết tiết theo mã TKB (bằng query chuẩn)
    public List<ChiTietTiet> getByTKB(String maTKB) {
        List<ChiTietTiet> list = new ArrayList<>();
        String sql = "SELECT maChiTiet, maTKB, maMon, thu, tiet, phongHoc, gioBatDau, gioKetThuc, trangThai FROM CHITIETTIET WHERE maTKB = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maTKB);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietTiet ct = new ChiTietTiet(
                        rs.getString("maChiTiet"),
                        rs.getString("maTKB"),
                        rs.getString("maMon"),
                        rs.getString("thu"),
                        rs.getInt("tiet"),
                        rs.getString("phongHoc"),
                        rs.getTime("gioBatDau").toLocalTime().toString(),
                        rs.getTime("gioKetThuc").toLocalTime().toString(),
                        rs.getInt("trangThai")
                    );
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
