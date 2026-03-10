package DAO;

import DataObject.Lop;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LopDAL {
    private Connection con;

    public LopDAL() {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }
    
    public LopDAL(Connection con) {
        this.con = con;
    }


    public List<Lop> getAll() {
        List<Lop> list = new ArrayList<>();
        String sql = "SELECT maLop, tenLop, siSo, maNam, maGVCN, trangThai FROM LOP";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lop lop = new Lop(
                    rs.getString("maLop"),
                    rs.getString("tenLop"),
                    rs.getInt("siSo"),
                    rs.getString("maNam"),
                    rs.getString("maGVCN"),
                    rs.getInt("trangThai")
                );
                list.add(lop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Lop> getAllActive() {
        List<Lop> list = new ArrayList<>();
        String sql = "SELECT maLop, tenLop, siSo, maNam, maGVCN, trangThai FROM LOP WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Lop lop = new Lop(
                    rs.getString("maLop"),
                    rs.getString("tenLop"),
                    rs.getInt("siSo"),
                    rs.getString("maNam"),
                    rs.getString("maGVCN"),
                    rs.getInt("trangThai")
                );
                list.add(lop);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Lop> getAllActiveByProc() {

        return getAllActive();
    }

    

    public boolean insert(Lop lop) {
        String sql = "INSERT INTO LOP(maLop, tenLop, siSo, maNam, maGVCN, trangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lop.getMaLop());
            ps.setString(2, lop.getTenLop());
            ps.setInt(3, lop.getSiSo());
            ps.setString(4, lop.getMaNH());
            ps.setString(5, lop.getMaGVCN());
            ps.setInt(6, lop.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean update(Lop lop) {
        String sql = "UPDATE LOP SET tenLop=?, siSo=?, maNam=?, maGVCN=?, trangThai=? WHERE maLop=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lop.getTenLop());
            ps.setInt(2, lop.getSiSo());
            ps.setString(3, lop.getMaNH());
            ps.setString(4, lop.getMaGVCN());
            ps.setInt(5, lop.getTrangThai());
            ps.setString(6, lop.getMaLop());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void updateSiSo(String maLop) {
        String sql = "UPDATE LOP SET siSo = (SELECT COUNT(*) FROM HOCSINH WHERE maLop=? AND trangThai=1) WHERE maLop=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ps.setString(2, maLop);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean delete(String maLop) {
        String sql = "UPDATE LOP SET trangThai = 0 WHERE maLop=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLop);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public Lop findByMaLop(String maLop) {
        String sql = "SELECT maLop, tenLop, siSo, maNam, maGVCN, trangThai FROM LOP WHERE maLop=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLop);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Lop(
                    rs.getString("maLop"),
                    rs.getString("tenLop"),
                    rs.getInt("siSo"),
                    rs.getString("maNam"),
                    rs.getString("maGVCN"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
