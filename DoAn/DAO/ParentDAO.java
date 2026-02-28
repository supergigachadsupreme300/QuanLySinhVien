package DAO;

import DataObject.Parent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParentDAO {

    // Thông tin kết nối - có thể đưa ra file config sau này
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=TenCuaDatabase;"
                                    + "encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "123456";  // thay bằng mật khẩu thật

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Lấy phụ huynh theo mã
    public Parent getById(String maPH) {
        String sql = "SELECT * FROM PHUHUYNH WHERE maPH = ? AND trangThai = 1";
        Parent p = null;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Parent();
                    p.setMaPhH(rs.getString("maPH"));
                    p.setTenPhH(rs.getString("hoTen"));
                    p.setSdt(rs.getString("soDienThoai"));
                    p.setNgheNghiep(rs.getString("ngheNghiep"));
                    // quanHe nằm ở bảng HOCSINH_PHUHUYNH → xử lý riêng nếu cần
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    // Lấy tất cả phụ huynh đang hoạt động
    public List<Parent> getAll() {
        List<Parent> list = new ArrayList<>();
        String sql = "SELECT * FROM PHUHUYNH WHERE trangThai = 1";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Parent p = new Parent();
                p.setMaPhH(rs.getString("maPH"));
                p.setTenPhH(rs.getString("hoTen"));
                p.setSdt(rs.getString("soDienThoai"));
                p.setNgheNghiep(rs.getString("ngheNghiep"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới
    public boolean add(Parent p) {
        String sql = "INSERT INTO PHUHUYNH (maPH, hoTen, soDienThoai, ngheNghiep, trangThai) "
                   + "VALUES (?, ?, ?, ?, 1)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getMaPhH());
            ps.setString(2, p.getTenPhH());
            ps.setString(3, p.getSdt());
            ps.setString(4, p.getNgheNghiep());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật
    public boolean update(Parent p) {
        String sql = "UPDATE PHUHUYNH SET hoTen = ?, soDienThoai = ?, ngheNghiep = ? "
                   + "WHERE maPH = ? AND trangThai = 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTenPhH());
            ps.setString(2, p.getSdt());
            ps.setString(3, p.getNgheNghiep());
            ps.setString(4, p.getMaPhH());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm
    public boolean delete(String maPH) {
        String sql = "UPDATE PHUHUYNH SET trangThai = 0 WHERE maPH = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm theo tên (tìm gần đúng)
    public List<Parent> searchByName(String keyword) {
        List<Parent> list = new ArrayList<>();
        String sql = "SELECT * FROM PHUHUYNH WHERE hoTen LIKE ? AND trangThai = 1";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Parent p = new Parent();
                    p.setMaPhH(rs.getString("maPH"));
                    p.setTenPhH(rs.getString("hoTen"));
                    p.setSdt(rs.getString("soDienThoai"));
                    p.setNgheNghiep(rs.getString("ngheNghiep"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}