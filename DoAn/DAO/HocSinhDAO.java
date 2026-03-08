package DAO;

import DataObject.HocSinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HocSinhDAO {
    private Connection con;

    public HocSinhDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public HocSinhDAO(Connection con) {
        this.con = con;
    }

    public boolean add(HocSinh hs) {
        String sql = "INSERT INTO HOCSINH (maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, trangThai) "
                   + "VALUES (?, ?, ?, ?, ?, ?, 1)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hs.getMaHS());
            ps.setNString(2, hs.getHoTen());
            ps.setDate(3, hs.getNgaySinh() != null ? Date.valueOf(hs.getNgaySinh()) : null);
            ps.setNString(4, hs.getGioiTinh());
            ps.setNString(5, hs.getDiaChi());
            ps.setString(6, hs.getMaLop());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HocSinh getById(String maHS) {
        String sql = "SELECT * FROM HOCSINH WHERE maHS = ? AND trangThai = 1";
        HocSinh hs = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hs = new HocSinh();
                    hs.setMaHS(rs.getString("maHS"));
                    hs.setHoTen(rs.getNString("hoTen"));  // dùng getNString cho NVARCHAR
                    Date ns = rs.getDate("ngaySinh");
                    hs.setNgaySinh(ns != null ? ns.toLocalDate() : null);
                    hs.setGioiTinh(rs.getNString("gioiTinh"));
                    hs.setDiaChi(rs.getNString("diaChi"));
                    hs.setMaLop(rs.getString("maLop"));
                    hs.setTrangThai(rs.getInt("trangThai")); // dùng cho object bên gui để xét các điều kiện nghiệp vụ them/xoa (formtkb,lop,chitiettiet,phancong)
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hs;
    }

    public List<HocSinh> getAll() {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT * FROM HOCSINH ORDER BY maHS";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HocSinh hs = new HocSinh();
                hs.setMaHS(rs.getString("maHS"));
                hs.setHoTen(rs.getNString("hoTen"));
                Date ns = rs.getDate("ngaySinh");
                hs.setNgaySinh(ns != null ? ns.toLocalDate() : null);
                hs.setGioiTinh(rs.getNString("gioiTinh"));
                hs.setDiaChi(rs.getNString("diaChi"));
                hs.setMaLop(rs.getString("maLop"));
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
        String sql = "SELECT * FROM HOCSINH WHERE trangThai = 1 ORDER BY maHS";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HocSinh hs = new HocSinh();
                hs.setMaHS(rs.getString("maHS"));
                hs.setHoTen(rs.getNString("hoTen"));
                Date ns = rs.getDate("ngaySinh");
                hs.setNgaySinh(ns != null ? ns.toLocalDate() : null);
                hs.setGioiTinh(rs.getNString("gioiTinh"));
                hs.setDiaChi(rs.getNString("diaChi"));
                hs.setMaLop(rs.getString("maLop"));
                list.add(hs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HocSinh> getByMaLop(String maLop) {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT * FROM HOCSINH WHERE maLop = ? AND trangThai = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maLop);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HocSinh hs = new HocSinh();
                    hs.setMaHS(rs.getString("maHS"));
                    hs.setHoTen(rs.getNString("hoTen"));
                    Date ns = rs.getDate("ngaySinh");
                    hs.setNgaySinh(ns != null ? ns.toLocalDate() : null);
                    hs.setGioiTinh(rs.getNString("gioiTinh"));
                    hs.setDiaChi(rs.getNString("diaChi"));
                    hs.setMaLop(rs.getString("maLop"));
                    list.add(hs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(HocSinh hs) {
        String sql = "UPDATE HOCSINH SET hoTen = ?, ngaySinh = ?, gioiTinh = ?, diaChi = ?, maLop = ? "
                   + "WHERE maHS = ? AND trangThai = 1";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setNString(1, hs.getHoTen());
            ps.setDate(2, hs.getNgaySinh() != null ? Date.valueOf(hs.getNgaySinh()) : null);
            ps.setNString(3, hs.getGioiTinh());
            ps.setNString(4, hs.getDiaChi());
            ps.setString(5, hs.getMaLop());
            ps.setString(6, hs.getMaHS());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maHS) {
        String sql = "UPDATE HOCSINH SET trangThai = 0 WHERE maHS = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHS);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
