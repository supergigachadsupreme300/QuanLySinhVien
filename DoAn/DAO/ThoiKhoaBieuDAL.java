package DAO;

import DataObject.ThoiKhoaBieu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThoiKhoaBieuDAL {
    private Connection con;

    
    public ThoiKhoaBieuDAL(){
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
    }
    
    public ThoiKhoaBieuDAL(Connection con) {
        this.con = con;
    }

    // Lấy tất cả TKB
    public List<ThoiKhoaBieu> getAll() {
        List<ThoiKhoaBieu> list = new ArrayList<>();
        String sql = "SELECT maTKB, maLop, maHocKy, trangThai, ngayBatDau, ngayKetThuc FROM THOIKHOABIEU";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThoiKhoaBieu tkb = new ThoiKhoaBieu(
                    rs.getString("maTKB"),
                    rs.getString("maLop"),
                    rs.getString("maHocKy"),
                    rs.getInt("trangThai"),
                    rs.getDate("ngayBatDau").toLocalDate(),
                    rs.getDate("ngayKetThuc").toLocalDate()
                );
                list.add(tkb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy tất cả TKB đang hoạt động
    public List<ThoiKhoaBieu> getAllActive() {
        List<ThoiKhoaBieu> list = new ArrayList<>();
        String sql = "SELECT maTKB, maLop, maHocKy, trangThai, ngayBatDau, ngayKetThuc FROM THOIKHOABIEU WHERE trangThai = 1";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ThoiKhoaBieu tkb = new ThoiKhoaBieu(
                    rs.getString("maTKB"),
                    rs.getString("maLop"),
                    rs.getString("maHocKy"),
                    rs.getInt("trangThai"),
                    rs.getDate("ngayBatDau").toLocalDate(),
                    rs.getDate("ngayKetThuc").toLocalDate()
                );
                list.add(tkb);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm TKB
    public boolean insert(ThoiKhoaBieu tkb) {
        String sql = "INSERT INTO THOIKHOABIEU(maTKB, maLop, maHocKy, trangThai, ngayBatDau, ngayKetThuc) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tkb.getMaTKB());
            ps.setString(2, tkb.getMaLop());
            ps.setString(3, tkb.getMaHK());
            ps.setInt(4, tkb.getTrangThai());
            ps.setDate(5, Date.valueOf(tkb.getNgayBatDau()));
            ps.setDate(6, Date.valueOf(tkb.getNgayKetThuc()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật TKB
    public boolean update(ThoiKhoaBieu tkb) {
        String sql = "UPDATE THOIKHOABIEU SET maLop=?, maHocKy=?, trangThai=?, ngayBatDau=?, ngayKetThuc=? WHERE maTKB=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tkb.getMaLop());
            ps.setString(2, tkb.getMaHK());
            ps.setInt(3, tkb.getTrangThai());
            ps.setDate(4, tkb.getNgayBatDau() != null ? Date.valueOf(tkb.getNgayBatDau()) : null);
            ps.setDate(5, tkb.getNgayKetThuc() != null ? Date.valueOf(tkb.getNgayKetThuc()) : null);
            ps.setString(6, tkb.getMaTKB());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Soft delete: đổi trạng thái = 0
    public boolean delete(String maTKB) {
        String sql = "UPDATE THOIKHOABIEU SET trangThai = 0 WHERE maTKB=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maTKB);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm TKB theo mã
    public ThoiKhoaBieu findByMaTKB(String maTKB) {
        String sql = "SELECT maTKB, maLop, maHocKy, trangThai, ngayBatDau, ngayKetThuc FROM THOIKHOABIEU WHERE maTKB=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maTKB);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ThoiKhoaBieu(
                    rs.getString("maTKB"),
                    rs.getString("maLop"),
                    rs.getString("maHocKy"),
                    rs.getInt("trangThai"),
                    rs.getDate("ngayBatDau").toLocalDate(), 
                    rs.getDate("ngayKetThuc").toLocalDate()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách TKB theo mã lớp
    public List<ThoiKhoaBieu> getByLop(String maLop) {
        List<ThoiKhoaBieu> list = new ArrayList<>();
        String sql = "SELECT maTKB, maLop, maHocKy, trangThai, ngayBatDau, ngayKetThuc FROM THOIKHOABIEU WHERE maLop = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLop);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ThoiKhoaBieu tkb = new ThoiKhoaBieu(
                        rs.getString("maTKB"),
                        rs.getString("maLop"),
                        rs.getString("maHocKy"),
                        rs.getInt("trangThai"),
                        rs.getDate("ngayBatDau") != null ? rs.getDate("ngayBatDau").toLocalDate() : null,
                        rs.getDate("ngayKetThuc") != null ? rs.getDate("ngayKetThuc").toLocalDate() : null
                    );
                    list.add(tkb);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
