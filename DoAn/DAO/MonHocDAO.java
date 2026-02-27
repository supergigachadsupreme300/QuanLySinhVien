package DAO;

import DataObject.MonHoc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {

    // Thông tin kết nối - nên đưa ra file config sau này
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=TenCuaDatabase;"
                                    + "encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "your_password_here"; // thay bằng mật khẩu thật

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public MonHoc getByMa(String maMon) {
        String sql = "SELECT * FROM MONHOC WHERE maMon = ? AND trangThai = 1";
        MonHoc mh = null;
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMon);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    mh = new MonHoc();
                    mh.setMaMon(rs.getString("maMon"));
                    mh.setTenMon(rs.getNString("tenMon"));
                    mh.setSoTinChi(rs.getInt("soTinChi"));
                    mh.setKhoa(rs.getNString("khoa"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mh;
    }

    public List<MonHoc> getAll() {
        List<MonHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM MONHOC WHERE trangThai = 1 ORDER BY maMon";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MonHoc mh = new MonHoc();
                mh.setMaMon(rs.getString("maMon"));
                mh.setTenMon(rs.getNString("tenMon"));
                mh.setSoTinChi(rs.getInt("soTinChi"));
                mh.setKhoa(rs.getNString("khoa"));
                list.add(mh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<MonHoc> getAllFull() {
        List<MonHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM MONHOC ORDER BY maMon";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                MonHoc mh = new MonHoc();
                mh.setMaMon(rs.getString("maMon"));
                mh.setTenMon(rs.getNString("tenMon"));
                mh.setSoTinChi(rs.getInt("soTinChi"));
                mh.setKhoa(rs.getNString("khoa"));
                list.add(mh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
    public List<MonHoc> getAllActiveByProc() {
        List<MonHoc> list = new ArrayList<>();
        String sql = "{call sp_getAllActiveMonHoc()}"; // procedure trong DB
        try (Connection conn = getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {
            while (rs.next()) {
                MonHoc mh = new MonHoc();
                mh.setMaMon(rs.getString("maMon"));
                mh.setTenMon(rs.getNString("tenMon"));
                mh.setSoTinChi(rs.getInt("soTinChi"));
                mh.setKhoa(rs.getNString("khoa"));
                list.add(mh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public boolean them(MonHoc mh) {
        String sql = "INSERT INTO MONHOC (maMon, tenMon, soTinChi, khoa, trangThai) "
                   + "VALUES (?, ?, ?, ?, 1)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mh.getMaMon());
            ps.setNString(2, mh.getTenMon());
            ps.setInt(3, mh.getSoTinChi());
            ps.setNString(4, mh.getKhoa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(MonHoc mh) {
        String sql = "UPDATE MONHOC SET tenMon = ?, soTinChi = ?, khoa = ? "
                   + "WHERE maMon = ? AND trangThai = 1";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setNString(1, mh.getTenMon());
            ps.setInt(2, mh.getSoTinChi());
            ps.setNString(3, mh.getKhoa());
            ps.setString(4, mh.getMaMon());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String maMon) {
        String sql = "UPDATE MONHOC SET trangThai = 0 WHERE maMon = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
