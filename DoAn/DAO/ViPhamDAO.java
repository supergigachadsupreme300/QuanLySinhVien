package DAO;

import DataObject.ViPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViPhamDAO {

    private Connection con;

    public ViPhamDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public ViPhamDAO(Connection con) {
        this.con = con;
    }

    // ===== GET BY ID =====
    public ViPham getById(String maViPham) {
        String sql = "SELECT * FROM VIPHAM WHERE maViPham = ?";
        ViPham vp = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maViPham);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    vp = new ViPham();
                    vp.setMaViPham(rs.getString("maViPham"));
                    vp.setMaHS(rs.getString("maHS"));
                    vp.setMaHocKy(rs.getString("maHocKy"));
                    vp.setNgayViPham(rs.getDate("ngayViPham").toLocalDate());
                    vp.setNoiDung(rs.getString("noiDung"));
                    vp.setMucDo(rs.getString("mucDo"));
                    vp.setTrangThai(rs.getBoolean("trangThai"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vp;
    }

    // ===== GET ALL =====
    public List<ViPham> getAll() {
        List<ViPham> list = new ArrayList<>();
        String sql = "SELECT * FROM VIPHAM ORDER BY maViPham";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ViPham vp = new ViPham();
                vp.setMaViPham(rs.getString("maViPham"));
                vp.setMaHS(rs.getString("maHS"));
                vp.setMaHocKy(rs.getString("maHocKy"));
                vp.setNgayViPham(rs.getDate("ngayViPham").toLocalDate());
                vp.setNoiDung(rs.getString("noiDung"));
                vp.setMucDo(rs.getString("mucDo"));
                vp.setTrangThai(rs.getBoolean("trangThai"));

                list.add(vp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET BY MA HOC SINH =====
    public List<ViPham> getByMaHS(String maHS) {
        List<ViPham> list = new ArrayList<>();
        String sql = "SELECT * FROM VIPHAM WHERE maHS = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ViPham vp = new ViPham();
                    vp.setMaViPham(rs.getString("maViPham"));
                    vp.setMaHS(rs.getString("maHS"));
                    vp.setMaHocKy(rs.getString("maHocKy"));
                    vp.setNgayViPham(rs.getDate("ngayViPham").toLocalDate());
                    vp.setNoiDung(rs.getString("noiDung"));
                    vp.setMucDo(rs.getString("mucDo"));
                    vp.setTrangThai(rs.getBoolean("trangThai"));

                    list.add(vp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== ADD =====
    public boolean add(ViPham vp) {
        String sql = "INSERT INTO VIPHAM VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vp.getMaViPham());
            ps.setString(2, vp.getMaHS());
            ps.setString(3, vp.getMaHocKy());
            ps.setDate(4, Date.valueOf(vp.getNgayViPham()));
            ps.setString(5, vp.getNoiDung());
            ps.setString(6, vp.getMucDo());
            ps.setBoolean(7, vp.isTrangThai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== UPDATE =====
    public boolean update(ViPham vp) {
        String sql = "UPDATE VIPHAM SET maHS=?, maHocKy=?, ngayViPham=?, noiDung=?, mucDo=?, trangThai=? "
                   + "WHERE maViPham=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, vp.getMaHS());
            ps.setString(2, vp.getMaHocKy());
            ps.setDate(3, Date.valueOf(vp.getNgayViPham()));
            ps.setString(4, vp.getNoiDung());
            ps.setString(5, vp.getMucDo());
            ps.setBoolean(6, vp.isTrangThai());
            ps.setString(7, vp.getMaViPham());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== DELETE =====
    public boolean delete(String maViPham) {
        String sql = "DELETE FROM VIPHAM WHERE maViPham = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maViPham);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}