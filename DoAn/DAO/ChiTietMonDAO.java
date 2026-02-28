package DAO;

import DataObject.ChiTietMon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietMonDAO {

    // Thông tin kết nối - nên đưa ra file config sau này
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=TenCuaDatabase;"
            + "encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "123456"; // thay bằng mật khẩu thật

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public ChiTietMon getByMa(String maChiTiet) {
        String sql = "SELECT * FROM CHITIETMON WHERE maChiTiet = ? AND trangThai = 1";
        ChiTietMon ct = null;
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maChiTiet);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ct = new ChiTietMon();
                    ct.setMaChiTiet(rs.getString("maChiTiet"));
                    ct.setMaMon(rs.getString("maMon"));
                    ct.setTenChiTiet(rs.getNString("tenChiTiet"));
                    ct.setHeSo(rs.getInt("heSo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ct;
    }

    public List<ChiTietMon> getAll() {
        List<ChiTietMon> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETMON WHERE trangThai = 1 ORDER BY maChiTiet";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ChiTietMon ct = new ChiTietMon();
                ct.setMaChiTiet(rs.getString("maChiTiet"));
                ct.setMaMon(rs.getString("maMon"));
                ct.setTenChiTiet(rs.getNString("tenChiTiet"));
                ct.setHeSo(rs.getInt("heSo"));
                list.add(ct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietMon> getByMon(String maMon) {
        List<ChiTietMon> list = new ArrayList<>();
        String sql = "SELECT * FROM CHITIETMON WHERE maMon = ? AND trangThai = 1 ORDER BY maChiTiet";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMon);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietMon ct = new ChiTietMon();
                    ct.setMaChiTiet(rs.getString("maChiTiet"));
                    ct.setMaMon(rs.getString("maMon"));
                    ct.setTenChiTiet(rs.getNString("tenChiTiet"));
                    ct.setHeSo(rs.getInt("heSo"));
                    list.add(ct);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean them(ChiTietMon ct) {
        String sql = "INSERT INTO CHITIETMON (maChiTiet, maMon, tenChiTiet, heSo, trangThai) "
                + "VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getMaChiTiet());
            ps.setString(2, ct.getMaMon());
            ps.setNString(3, ct.getTenChiTiet());
            ps.setInt(4, ct.getHeSo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(ChiTietMon ct) {
        String sql = "UPDATE CHITIETMON SET maMon = ?, tenChiTiet = ?, heSo = ? "
                + "WHERE maChiTiet = ? AND trangThai = 1";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getMaMon());
            ps.setNString(2, ct.getTenChiTiet());
            ps.setInt(3, ct.getHeSo());
            ps.setString(4, ct.getMaChiTiet());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String maChiTiet) {
        String sql = "UPDATE CHITIETMON SET trangThai = 0 WHERE maChiTiet = ?";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maChiTiet);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}