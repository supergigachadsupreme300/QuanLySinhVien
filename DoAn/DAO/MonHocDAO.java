package DAO;

import DataObject.Mon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {
    private Connection con;

    public MonHocDAO() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }
    
    public MonHocDAO(Connection con){
        this.con = con;
    }


    public List<Mon> getAll() {
        List<Mon> list = new ArrayList<>();
        String sql = "SELECT maMon, tenMon, trangThai FROM MON";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mon mon = new Mon(
                    rs.getString("maMon"),
                    rs.getString("tenMon"),
                    rs.getInt("trangThai")
                );
                list.add(mon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Mon> getAllActive() {
        List<Mon> list = new ArrayList<>();
        String sql = "SELECT maMon, tenMon, trangThai FROM MON WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Mon mon = new Mon(
                    rs.getString("maMon"),
                    rs.getString("tenMon"),
                    rs.getInt("trangThai")
                );
                list.add(mon);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Mon> getAllActiveByProc() {

        return getAllActive();
    }

    

    public boolean insert(Mon mon) {
        String sql = "INSERT INTO MON(maMon, tenMon, trangThai) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mon.getMaMon());
            ps.setString(2, mon.getTenMon());
            ps.setInt(3, mon.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Mon mon) {
        String sql = "UPDATE MON SET tenMon=?, trangThai=? WHERE maMon=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, mon.getTenMon());
            ps.setInt(2, mon.getTrangThai());
            ps.setString(3, mon.getMaMon());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean delete(String maMon) {
        String sql = "UPDATE MON SET trangThai = 0 WHERE maMon=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maMon);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Mon findByMaMon(String maMon) {
        String sql = "SELECT maMon, tenMon, trangThai FROM MON WHERE maMon=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maMon);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Mon(
                    rs.getString("maMon"),
                    rs.getString("tenMon"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    

    public List<String> getAllMaMon(){
        List<String> list = new ArrayList<>();
        try{
            String sql = "SELECT MaMon FROM MON";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getString("MaMon"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
