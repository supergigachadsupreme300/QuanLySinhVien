package DAO;

import DataObject.HocSinh;
import DataObject.Parent;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParentDAO {
    private Connection con;

    public ParentDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public ParentDAO(Connection con) {
        this.con = con;
    }

    // Lấy phụ huynh theo mã
    public Parent getById(String maPH) {
        String sql = "SELECT * FROM PHUHUYNH WHERE maPH = ? AND trangThai = 1";
        Parent p = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {

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

        try (Statement stmt = con.createStatement();
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

        try (PreparedStatement ps = con.prepareStatement(sql)) {

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

        try (PreparedStatement ps = con.prepareStatement(sql)) {

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

        try (PreparedStatement ps = con.prepareStatement(sql)) {

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

        try (PreparedStatement ps = con.prepareStatement(sql)) {

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

    // Lấy phụ huynh theo mã học sinh (bảng quan hệ HOCSINH_PHUHUYNH)
    public List<Parent> getParentsByHocSinh(String maHS) {
        List<Parent> list = new ArrayList<>();
        String sql = "SELECT p.maPH, p.hoTen, p.soDienThoai, p.ngheNghiep, h.quanHe "
                   + "FROM PHUHUYNH p JOIN HOCSINH_PHUHUYNH h ON p.maPH = h.maPH "
                   + "WHERE h.maHS = ? AND p.trangThai = 1 AND h.trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Parent p = new Parent();
                    p.setMaPhH(rs.getString("maPH"));
                    p.setTenPhH(rs.getString("hoTen"));
                    p.setSdt(rs.getString("soDienThoai"));
                    p.setNgheNghiep(rs.getString("ngheNghiep"));
                    p.setQuanHe(rs.getString("quanHe"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm quan hệ HOCSINH_PHUHUYNH
    public boolean addRelation(String maHS, String maPH, String quanHe) {
        String sql = "INSERT INTO HOCSINH_PHUHUYNH (maHS, maPH, quanHe, trangThai) VALUES (?, ?, ?, 1)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            ps.setString(2, maPH);
            ps.setNString(3, quanHe);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa mềm quan hệ
    public boolean deleteRelation(String maHS, String maPH) {
        String sql = "UPDATE HOCSINH_PHUHUYNH SET trangThai = 0 WHERE maHS = ? AND maPH = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            ps.setString(2, maPH);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy danh sách học sinh của 1 phụ huynh
    public List<HocSinh> getStudentsByParent(String maPH) {
        List<HocSinh> list = new ArrayList<>();
        String sql = "SELECT hs.maHS, hs.hoTen, hs.ngaySinh, hs.gioiTinh, hs.diaChi, hs.maLop "
                   + "FROM HOCSINH hs JOIN HOCSINH_PHUHUYNH h ON hs.maHS = h.maHS "
                   + "WHERE h.maPH = ? AND hs.trangThai = 1 AND h.trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HocSinh hs = new HocSinh();
                    hs.setMaHS(rs.getString("maHS"));
                    hs.setHoTen(rs.getString("hoTen"));
                    java.sql.Date d = rs.getDate("ngaySinh");
                    if (d != null) hs.setNgaySinh(d.toLocalDate());
                    hs.setGioiTinh(rs.getString("gioiTinh"));
                    hs.setDiaChi(rs.getString("diaChi"));
                    hs.setMaLop(rs.getString("maLop"));
                    list.add(hs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}