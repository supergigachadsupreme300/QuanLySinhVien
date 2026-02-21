package DAO;

import DataObject.GiaoVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GiaoVienDAO {

    // Thông tin kết nối - nên đưa ra file config sau này
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=TenCuaDatabase;"
            + "encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "your_password_here"; // thay bằng mật khẩu thật

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public GiaoVien getByMa(String maGV) {
        String sql = "SELECT * FROM GIAOVIEN WHERE maGV = ? AND trangThai = 1";
        GiaoVien gv = null;
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    gv = new GiaoVien();
                    gv.setMaGV(rs.getString("maGV"));
                    gv.setHoTen(rs.getNString("hoTen"));
                    gv.setSdt(rs.getString("sdt"));
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
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                GiaoVien gv = new GiaoVien();
                gv.setMaGV(rs.getString("maGV"));
                gv.setHoTen(rs.getNString("hoTen"));
                gv.setSdt(rs.getString("sdt"));
                gv.setEmail(rs.getString("email"));
                list.add(gv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean them(GiaoVien gv) {
        String sql = "INSERT INTO GIAOVIEN (maGV, hoTen, sdt, email, trangThai) "
                + "VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, gv.getMaGV());
            ps.setNString(2, gv.getHoTen());
            ps.setString(3, gv.getSdt());
            ps.setString(4, gv.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(GiaoVien gv) {
        String sql = "UPDATE GIAOVIEN SET hoTen = ?, sdt = ?, email = ? "
                + "WHERE maGV = ? AND trangThai = 1";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, gv.getHoTen());
            ps.setString(2, gv.getSdt());
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
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maGV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}