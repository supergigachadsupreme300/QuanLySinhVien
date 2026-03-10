package DAO;


import DataObject.HanhKiem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HanhKiemDAL {

    private Connection con;

    public HanhKiemDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public HanhKiemDAL(Connection con) {
        this.con = con;
    }


    public HanhKiem getById(String maHanhKiem) {
        String sql = "SELECT * FROM HANHKIEM WHERE maHanhKiem = ?";
        HanhKiem hk = null;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHanhKiem);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    hk = new HanhKiem();
                    hk.setMaHanhKiem(rs.getString("maHanhKiem"));
                    hk.setMaHS(rs.getString("maHS"));
                    hk.setMaHocKy(rs.getString("maHocKy"));
                    hk.setXepLoai(rs.getString("xepLoai"));
                    hk.setSoLanViPham(rs.getInt("soLanViPham"));
                    hk.setNhanXet(rs.getString("nhanXet"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hk;
    }


    public List<HanhKiem> getAll() {
        List<HanhKiem> list = new ArrayList<>();
        String sql = "SELECT * FROM HANHKIEM ORDER BY maHanhKiem";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HanhKiem hk = new HanhKiem();
                hk.setMaHanhKiem(rs.getString("maHanhKiem"));
                hk.setMaHS(rs.getString("maHS"));
                hk.setMaHocKy(rs.getString("maHocKy"));
                hk.setXepLoai(rs.getString("xepLoai"));
                hk.setSoLanViPham(rs.getInt("soLanViPham"));
                hk.setNhanXet(rs.getString("nhanXet"));

                list.add(hk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<HanhKiem> getByMaHS(String maHS) {
        List<HanhKiem> list = new ArrayList<>();
        String sql = "SELECT * FROM HANHKIEM WHERE maHS = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maHS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HanhKiem hk = new HanhKiem();
                    hk.setMaHanhKiem(rs.getString("maHanhKiem"));
                    hk.setMaHS(rs.getString("maHS"));
                    hk.setMaHocKy(rs.getString("maHocKy"));
                    hk.setXepLoai(rs.getString("xepLoai"));
                    hk.setSoLanViPham(rs.getInt("soLanViPham"));
                    hk.setNhanXet(rs.getString("nhanXet"));

                    list.add(hk);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public HanhKiem getHanhKiem(String maHS, String maHK){

        String sql = "SELECT * FROM HANHKIEM WHERE maHS=? AND maHocKy=?";

        try{

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maHS);
            ps.setString(2, maHK);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                HanhKiem hk = new HanhKiem();

                hk.setMaHanhKiem(rs.getString("maHanhKiem"));
                hk.setMaHS(rs.getString("maHS"));
                hk.setMaHocKy(rs.getString("maHocKy"));
                hk.setXepLoai(rs.getString("xepLoai"));
                hk.setNhanXet(rs.getString("nhanXet"));
                hk.setSoLanViPham(rs.getInt("soLanViPham"));

                return hk;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    public boolean add(HanhKiem hk) {
        String sql = "INSERT INTO HANHKIEM VALUES (?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hk.getMaHanhKiem());
            ps.setString(2, hk.getMaHS());
            ps.setString(3, hk.getMaHocKy());
            ps.setString(4, hk.getXepLoai());
            ps.setInt(5, hk.getSoLanViPham());
            ps.setString(6, hk.getNhanXet());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(HanhKiem hk) {
        String sql = "UPDATE HANHKIEM SET maHS=?, maHocKy=?, xepLoai=?, soLanViPham=?, nhanXet=? "
                   + "WHERE maHanhKiem=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hk.getMaHS());
            ps.setString(2, hk.getMaHocKy());
            ps.setString(3, hk.getXepLoai());
            ps.setInt(4, hk.getSoLanViPham());
            ps.setString(5, hk.getNhanXet());
            ps.setString(6, hk.getMaHanhKiem());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(String maHanhKiem) {
        String sql = "DELETE FROM HANHKIEM WHERE maHanhKiem = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maHanhKiem);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
