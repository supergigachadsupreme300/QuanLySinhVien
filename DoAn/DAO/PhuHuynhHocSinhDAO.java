package DAO;

import DataObject.PhuHuynhHocSinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhuHuynhHocSinhDAO {
    private Connection con;

    public PhuHuynhHocSinhDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public PhuHuynhHocSinhDAO(Connection con) {
        this.con = con;
    }


    public PhuHuynhHocSinh getById(String maHS, String maPH) {
        String sql = "SELECT * FROM HOCSINH_PHUHUYNH WHERE maHS = ? AND maPH = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            ps.setString(2, maPH);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PhuHuynhHocSinh p = new PhuHuynhHocSinh();
                    p.setMaHS(rs.getString("maHS"));
                    p.setMaPH(rs.getString("maPH"));
                    p.setQuanHe(rs.getNString("quanHe"));
                    p.setTrangThai(rs.getInt("trangThai"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<PhuHuynhHocSinh> getByMaPH(String maPH) {
        List<PhuHuynhHocSinh> list = new ArrayList<>();
        String sql = "SELECT * FROM HOCSINH_PHUHUYNH WHERE maPH = ? AND trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhuHuynhHocSinh p = new PhuHuynhHocSinh();
                    p.setMaHS(rs.getString("maHS"));
                    p.setMaPH(rs.getString("maPH"));
                    p.setQuanHe(rs.getNString("quanHe"));
                    p.setTrangThai(rs.getInt("trangThai"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<PhuHuynhHocSinh> getByMaHS(String maHS) {
        List<PhuHuynhHocSinh> list = new ArrayList<>();
        String sql = "SELECT * FROM HOCSINH_PHUHUYNH WHERE maHS = ? AND trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhuHuynhHocSinh p = new PhuHuynhHocSinh();
                    p.setMaHS(rs.getString("maHS"));
                    p.setMaPH(rs.getString("maPH"));
                    p.setQuanHe(rs.getNString("quanHe"));
                    p.setTrangThai(rs.getInt("trangThai"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean add(PhuHuynhHocSinh p) {
        String sql = "INSERT INTO HOCSINH_PHUHUYNH (maHS, maPH, quanHe, trangThai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getMaHS());
            ps.setString(2, p.getMaPH());
            ps.setNString(3, p.getQuanHe());
            ps.setInt(4, p.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(PhuHuynhHocSinh p) {
        String sql = "UPDATE HOCSINH_PHUHUYNH SET quanHe = ?, trangThai = ? WHERE maHS = ? AND maPH = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setNString(1, p.getQuanHe());
            ps.setInt(2, p.getTrangThai());
            ps.setString(3, p.getMaHS());
            ps.setString(4, p.getMaPH());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maHS, String maPH) {
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
}