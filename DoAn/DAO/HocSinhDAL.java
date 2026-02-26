package DataAcessLayer;

import DataObject.HocSinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HocSinhDAL {
    private Connection con;

    public HocSinhDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }
    
    public HocSinhDAL(Connection con) {
        this.con = con;
    }

    // Lấy tất cả học sinh
    public List<HocSinh> getAll() {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, trangThai FROM HOCSINH";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HocSinh hs = new HocSinh(
                    rs.getString("maHS"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("diaChi"),
                    rs.getString("maLop"),
                    rs.getInt("trangThai")
                );
                list.add(hs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET ALL ACTIVE =====
    public List<HocSinh> getAllActive() {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, trangThai FROM HOCSINH WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                HocSinh hs = new HocSinh(
                    rs.getString("maHS"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("diaChi"),
                    rs.getString("maLop"),
                    rs.getInt("trangThai")
                );
                list.add(hs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    // Lấy học sinh theo mã lớp
    public List<HocSinh> getByMaLop(String maLop) {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, trangThai FROM HOCSINH WHERE maLop=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HocSinh hs = new HocSinh(
                    rs.getString("maHS"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("diaChi"),
                    rs.getString("maLop"),
                    rs.getInt("trangThai")
                );
                list.add(hs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm học sinh
    public boolean insert(HocSinh hs) {
        String sql = "INSERT INTO HOCSINH(maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hs.getMaHS());
            ps.setString(2, hs.getHoTen());
            ps.setDate(3, Date.valueOf(hs.getNgaySinh()));
            ps.setString(4, hs.getGioiTinh());
            ps.setString(5, hs.getDiaChi());
            ps.setString(6, hs.getMaLop());
            ps.setInt(7, hs.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật học sinh
    public boolean update(HocSinh hs) {
        String sql = "UPDATE HOCSINH SET hoTen=?, ngaySinh=?, gioiTinh=?, diaChi=?, maLop=?, trangThai=? WHERE maHS=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hs.getHoTen());
            ps.setDate(2, Date.valueOf(hs.getNgaySinh()));
            ps.setString(3, hs.getGioiTinh());
            ps.setString(4, hs.getDiaChi());
            ps.setString(5, hs.getMaLop());
            ps.setInt(6, hs.getTrangThai());
            ps.setString(7, hs.getMaHS());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Soft delete
    public boolean delete(String maHS) {
        String sql = "UPDATE HOCSINH SET trangThai = 0 WHERE maHS=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm theo mã HS
    public HocSinh findByMaHS(String maHS) {
        String sql = "SELECT maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, trangThai FROM HOCSINH WHERE maHS=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HocSinh(
                    rs.getString("maHS"),
                    rs.getString("hoTen"),
                    rs.getDate("ngaySinh").toLocalDate(),
                    rs.getString("gioiTinh"),
                    rs.getString("diaChi"),
                    rs.getString("maLop"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
