package DAO;

import DataObject.Diem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemDAL {

    private Connection con;

    public DiemDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public DiemDAL(Connection con) {
        this.con = con;
    }

    // ===== GET BY ID =====
    public Diem getById(String maDiem) {
        String sql = "SELECT * FROM DIEM WHERE maDiem = ?";
        Diem d = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDiem);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    d = new Diem();
                    d.setMaDiem(rs.getString("maDiem"));
                    d.setMaHS(rs.getString("maHS"));
                    d.setMaMon(rs.getString("maMon"));
                    d.setMaHocKy(rs.getString("maHocKy"));
                    d.setDiemThuongXuyen(rs.getDouble("diemThuongXuyen"));
                    d.setDiemGiuaKy(rs.getDouble("diemGiuaKy"));
                    d.setDiemCuoiKy(rs.getDouble("diemCuoiKy"));
                    d.setDiemTBMonHocKy(rs.getDouble("diemTBMonHocKy"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    public Diem getDiem(String maHS, String maMon, String maHK){

        String sql = "SELECT * FROM DIEM WHERE maHS=? AND maMon=? AND maHocKy=?";

        try{
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHS);
            ps.setString(2, maMon);
            ps.setString(3, maHK);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Diem d = new Diem();
                d.setMaDiem(rs.getString("maDiem"));
                d.setMaHS(rs.getString("maHS"));
                d.setMaMon(rs.getString("maMon"));
                d.setMaHocKy(rs.getString("maHocKy"));
                d.setDiemThuongXuyen(rs.getDouble("diemThuongXuyen"));
                d.setDiemGiuaKy(rs.getDouble("diemGiuaKy"));
                d.setDiemCuoiKy(rs.getDouble("diemCuoiKy"));
                d.setDiemTBMonHocKy(rs.getDouble("diemTBMonHocKy"));
                return d;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // ===== GET ALL =====
    public List<Diem> getAll() {
        List<Diem> list = new ArrayList<>();
        String sql = "SELECT * FROM DIEM ORDER BY maDiem";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Diem d = new Diem();
                d.setMaDiem(rs.getString("maDiem"));
                d.setMaHS(rs.getString("maHS"));
                d.setMaMon(rs.getString("maMon"));
                d.setMaHocKy(rs.getString("maHocKy"));
                d.setDiemThuongXuyen(rs.getDouble("diemThuongXuyen"));
                d.setDiemGiuaKy(rs.getDouble("diemGiuaKy"));
                d.setDiemCuoiKy(rs.getDouble("diemCuoiKy"));
                d.setDiemTBMonHocKy(rs.getDouble("diemTBMonHocKy"));

                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===== GET BY HOC SINH =====
    public List<Diem> getByMaHS(String maHS) {
        List<Diem> list = new ArrayList<>();
        String sql = "SELECT * FROM DIEM WHERE maHS = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Diem d = new Diem();
                    d.setMaDiem(rs.getString("maDiem"));
                    d.setMaHS(rs.getString("maHS"));
                    d.setMaMon(rs.getString("maMon"));
                    d.setMaHocKy(rs.getString("maHocKy"));
                    d.setDiemThuongXuyen(rs.getDouble("diemThuongXuyen"));
                    d.setDiemGiuaKy(rs.getDouble("diemGiuaKy"));
                    d.setDiemCuoiKy(rs.getDouble("diemCuoiKy"));
                    d.setDiemTBMonHocKy(rs.getDouble("diemTBMonHocKy"));

                    list.add(d);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // compatibility alias removed; use `getByMaHS` directly

    // ===== ADD =====
    public boolean add(Diem d) {
        String sql = "INSERT INTO DIEM VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getMaDiem());
            ps.setString(2, d.getMaHS());
            ps.setString(3, d.getMaMon());
            ps.setString(4, d.getMaHocKy());
            ps.setDouble(5, d.getDiemThuongXuyen());
            ps.setDouble(6, d.getDiemGiuaKy());
            ps.setDouble(7, d.getDiemCuoiKy());
            ps.setDouble(8, d.getDiemTBMonHocKy());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== UPDATE =====
    public boolean update(Diem d) {
        String sql = "UPDATE DIEM SET maHS=?, maMon=?, maHocKy=?, "
                   + "diemThuongXuyen=?, diemGiuaKy=?, diemCuoiKy=?, diemTBMonHocKy=? "
                   + "WHERE maDiem=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getMaHS());
            ps.setString(2, d.getMaMon());
            ps.setString(3, d.getMaHocKy());
            ps.setDouble(4, d.getDiemThuongXuyen());
            ps.setDouble(5, d.getDiemGiuaKy());
            ps.setDouble(6, d.getDiemCuoiKy());
            ps.setDouble(7, d.getDiemTBMonHocKy());
            ps.setString(8, d.getMaDiem());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ===== DELETE =====
    public boolean delete(String maDiem) {
        String sql = "DELETE FROM DIEM WHERE maDiem = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maDiem);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
