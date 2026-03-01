package DAO;

import DataObject.PhanCong;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhanCongDAL {
    private Connection con;
    
    public PhanCongDAL(){
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }

    public PhanCongDAL(Connection con) {
        this.con = con;
    }

    // Lấy tất cả phân công
    public List<PhanCong> getAll() {
        List<PhanCong> list = new ArrayList<>();
        String sql = "SELECT maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai FROM PHANCONG";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhanCong pc = new PhanCong(
                    rs.getString("maPC"),
                    rs.getString("maGV"),
                    rs.getString("maMon"),
                    rs.getString("maLop"),
                    rs.getString("maNam"),
                    rs.getString("ghiChu"),
                    rs.getInt("trangThai")
                );
                list.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tất cả phân công đang hoạt động
    public List<PhanCong> getAllActive() {
        List<PhanCong> list = new ArrayList<>();
        String sql = "SELECT maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai FROM PHANCONG WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhanCong pc = new PhanCong(
                    rs.getString("maPC"),
                    rs.getString("maGV"),
                    rs.getString("maMon"),
                    rs.getString("maLop"),
                    rs.getString("maNam"),
                    rs.getString("ghiChu"),
                    rs.getInt("trangThai")
                );
                list.add(pc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm phân công
    public boolean insert(PhanCong pc) {
        String sql = "INSERT INTO PHANCONG(maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pc.getMaPC());
            ps.setString(2, pc.getMaGV());
            ps.setString(3, pc.getMaMon());
            ps.setString(4, pc.getMaLop());
            ps.setString(5, pc.getMaNam());
            ps.setString(6, pc.getGhiChu());
            ps.setInt(7, pc.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật phân công
    public boolean update(PhanCong pc) {
        String sql = "UPDATE PHANCONG SET maGV=?, maMon=?, maLop=?, maNam=?, ghiChu=?, trangThai=? WHERE maPC=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pc.getMaGV());
            ps.setString(2, pc.getMaMon());
            ps.setString(3, pc.getMaLop());
            ps.setString(4, pc.getMaNam());
            ps.setString(5, pc.getGhiChu());
            ps.setInt(6, pc.getTrangThai());
            ps.setString(7, pc.getMaPC());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Soft delete: đổi trạng thái = 0
    public boolean delete(String maPC) {
        String sql = "UPDATE PHANCONG SET trangThai = 0 WHERE maPC=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPC);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm phân công theo mã
    public PhanCong findByMaPC(String maPC) {
        String sql = "SELECT maPC, maGV, maMon, maLop, maNam, ghiChu, trangThai FROM PHANCONG WHERE maPC=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPC);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PhanCong(
                    rs.getString("maPC"),
                    rs.getString("maGV"),
                    rs.getString("maMon"),
                    rs.getString("maLop"),
                    rs.getString("maNam"),
                    rs.getString("ghiChu"),
                    rs.getInt("trangThai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
