package DAO;

import DataObject.XepLoai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XepLoaiDAO {

    private Connection con;

    public XepLoaiDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public XepLoaiDAO(Connection con) {
        this.con = con;
    }


    public XepLoai getById(String maXepLoai) {
        String sql = "SELECT * FROM XEPLOAI WHERE maXepLoai = ?";
        XepLoai xl = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maXepLoai);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    xl = new XepLoai();
                    xl.setMaXepLoai(rs.getString("maXepLoai"));
                    xl.setMaHS(rs.getString("maHS"));
                    xl.setMaHocKy(rs.getString("maHocKy"));
                    xl.setXepLoaiHocLuc(rs.getNString("xepLoaiHocLuc"));
                    xl.setXepLoaiHanhKiem(rs.getNString("xepLoaiHanhKiem"));
                    xl.setDiemTBChung(rs.getDouble("diemTBChung"));
                    xl.setNhanXet(rs.getNString("nhanXet"));
                    xl.setDuocLenLop(rs.getBoolean("duocLenLop"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return xl;
    }


    public List<XepLoai> getAll() {
        List<XepLoai> list = new ArrayList<>();
        String sql = "SELECT * FROM XEPLOAI ORDER BY maXepLoai";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                XepLoai xl = new XepLoai();
                xl.setMaXepLoai(rs.getString("maXepLoai"));
                xl.setMaHS(rs.getString("maHS"));
                xl.setMaHocKy(rs.getString("maHocKy"));
                xl.setXepLoaiHocLuc(rs.getNString("xepLoaiHocLuc"));
                xl.setXepLoaiHanhKiem(rs.getNString("xepLoaiHanhKiem"));
                xl.setDiemTBChung(rs.getDouble("diemTBChung"));
                xl.setNhanXet(rs.getNString("nhanXet"));
                xl.setDuocLenLop(rs.getBoolean("duocLenLop"));

                list.add(xl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<XepLoai> getByMaHS(String maHS) {
        List<XepLoai> list = new ArrayList<>();
        String sql = "SELECT * FROM XEPLOAI WHERE maHS = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    XepLoai xl = new XepLoai();
                    xl.setMaXepLoai(rs.getString("maXepLoai"));
                    xl.setMaHS(rs.getString("maHS"));
                    xl.setMaHocKy(rs.getString("maHocKy"));
                    xl.setXepLoaiHocLuc(rs.getNString("xepLoaiHocLuc"));
                    xl.setXepLoaiHanhKiem(rs.getNString("xepLoaiHanhKiem"));
                    xl.setDiemTBChung(rs.getDouble("diemTBChung"));
                    xl.setNhanXet(rs.getNString("nhanXet"));
                    xl.setDuocLenLop(rs.getBoolean("duocLenLop"));

                    list.add(xl);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean add(XepLoai xl) {
        String sql = "INSERT INTO XEPLOAI VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, xl.getMaXepLoai());
            ps.setString(2, xl.getMaHS());
            ps.setString(3, xl.getMaHocKy());
            ps.setNString(4, xl.getXepLoaiHocLuc());
            ps.setNString(5, xl.getXepLoaiHanhKiem());
            ps.setDouble(6, xl.getDiemTBChung());
            ps.setNString(7, xl.getNhanXet());
            ps.setBoolean(8, xl.isDuocLenLop());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(XepLoai xl) {
        String sql = "UPDATE XEPLOAI SET maHS=?, maHocKy=?, xepLoaiHocLuc=?, "
                   + "xepLoaiHanhKiem=?, diemTBChung=?, nhanXet=?, duocLenLop=? "
                   + "WHERE maXepLoai=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, xl.getMaHS());
            ps.setString(2, xl.getMaHocKy());
            ps.setNString(3, xl.getXepLoaiHocLuc());
            ps.setNString(4, xl.getXepLoaiHanhKiem());
            ps.setDouble(5, xl.getDiemTBChung());
            ps.setNString(6, xl.getNhanXet());
            ps.setBoolean(7, xl.isDuocLenLop());
            ps.setString(8, xl.getMaXepLoai());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(String maXepLoai) {
        String sql = "DELETE FROM XEPLOAI WHERE maXepLoai = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maXepLoai);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
