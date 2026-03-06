package tools;

import DAO.*;
import DataObject.*;
import java.sql.Connection;
import java.time.LocalDate;

/**
 * Small utility to seed test data into the database using existing DAOs.
 * Run with: java -cp "bin;lib/*" tools.SeedDatabase
 */
public class SeedDatabase {
    public static void main(String[] args) {
        DatabaseConnect db = new DatabaseConnect();
        Connection con = db.openConnection();
        if (con == null) {
            System.err.println("Cannot open DB connection, aborting seed.");
            return;
        }

        try {
            // DAOs using the same connection
            NamHocDAL namDao = new NamHocDAL(con);
            MonHocDAO monDao = new MonHocDAO(con);
            GiaoVienDAO gvDao = new GiaoVienDAO(con);
            LopDAL lopDao = new LopDAL(con);
            HocSinhDAO hsDao = new HocSinhDAO(con);

            // 1) NamHoc
            NamHoc nh = new NamHoc("NH2024", "2024-2025", 1);
            boolean ok = namDao.insert(nh);
            System.out.println("Insert NamHoc NH2024: " + ok);

            // 2) Mon
            Mon m1 = new Mon("M001", "Toán", 1);
            Mon m2 = new Mon("M002", "Ngữ văn", 1);
            System.out.println("Insert Mon M001: " + monDao.insert(m1));
            System.out.println("Insert Mon M002: " + monDao.insert(m2));

            // 3) GiaoVien
            GiaoVien gv = new GiaoVien();
            gv.setMaGV("GV01");
            gv.setHoTen("Nguyễn Văn G");
            gv.setDienThoai("0123456789");
            gv.setEmail("gv01@example.com");
            System.out.println("Insert GiaoVien GV01: " + gvDao.them(gv));

            // 4) Lop
            Lop lop = new Lop("L01", "6A1", 0, "NH2024", "GV01", 1);
            System.out.println("Insert Lop L01: " + lopDao.insert(lop));

            // 5) HocSinh
            HocSinh hs1 = new HocSinh();
            hs1.setMaHS("HS001");
            hs1.setHoTen("Nguyễn Văn A");
            hs1.setNgaySinh(LocalDate.of(2010, 1, 15));
            hs1.setGioiTinh("Nam");
            hs1.setDiaChi("Hà Nội");
            hs1.setMaLop("L01");
            System.out.println("Insert HocSinh HS001: " + hsDao.add(hs1));

            HocSinh hs2 = new HocSinh();
            hs2.setMaHS("HS002");
            hs2.setHoTen("Trần Thị B");
            hs2.setNgaySinh(LocalDate.of(2010, 5, 20));
            hs2.setGioiTinh("Nữ");
            hs2.setDiaChi("Hà Nội");
            hs2.setMaLop("L01");
            System.out.println("Insert HocSinh HS002: " + hsDao.add(hs2));

            // Update lớp sĩ số for initial class
            lopDao.updateSiSo("L01");
            System.out.println("Updated siSo for L01");

            // Additional DAOs for related records
            ParentDAO parentDao = new ParentDAO(con);
            HanhKiemDAL hanhKiemDao = new HanhKiemDAL(con);
            DiemDAL diemDao = new DiemDAL(con);
            ChiTietMonDAO ctDao = new ChiTietMonDAO(con);
            HocKyDAL hocKyDao = new HocKyDAL(con);

            // 6) Thêm Học kỳ và Chi tiết môn (dùng cho điểm)
            HocKy hocKy1 = new HocKy("HK01", "Học kỳ 1", "NH2024", LocalDate.of(2024, 9, 1), LocalDate.of(2025, 1, 15), 1);
            System.out.println("Insert HocKy HK01: " + hocKyDao.insert(hocKy1));

            ChiTietMon ct = new ChiTietMon("CTM001", "M001", "Kiểm tra giữa kỳ", 1);
            System.out.println("Insert ChiTietMon CTM001: " + ctDao.them(ct));

            // 7) Tạo 2 lớp mới: L02 và L03, mỗi lớp 10 học sinh
            Lop lop2 = new Lop("L02", "6B1", 0, "NH2024", "GV01", 1);
            Lop lop3 = new Lop("L03", "6B2", 0, "NH2024", "GV01", 1);
            System.out.println("Insert Lop L02: " + lopDao.insert(lop2));
            System.out.println("Insert Lop L03: " + lopDao.insert(lop3));

            // Helper: create students and related records
            for (int i = 1; i <= 10; i++) {
                // Class L02 students HS201..HS210
                String maHS = String.format("HS%03d", 200 + i);
                HocSinh hs = new HocSinh();
                hs.setMaHS(maHS);
                hs.setHoTen("Học sinh L02 " + i);
                hs.setNgaySinh(LocalDate.of(2010, (i % 12) + 1, Math.min(28, (i * 2))));
                hs.setGioiTinh((i % 2 == 0) ? "Nữ" : "Nam");
                hs.setDiaChi("Hà Nội");
                hs.setMaLop("L02");
                System.out.println("Insert HocSinh " + maHS + ": " + hsDao.add(hs));

                // Parent
                String maPH = String.format("PH%03d", 200 + i);
                Parent p = new Parent();
                p.setMaPhH(maPH);
                p.setTenPhH("Phụ huynh " + maPH);
                p.setSdt("090000" + String.format("%03d", i));
                p.setNgheNghiep("Không rõ");
                System.out.println("Insert Parent " + maPH + ": " + parentDao.add(p));
                parentDao.addRelation(maHS, maPH, "Mẹ");

                // Hạnh kiểm
                HanhKiem hk = new HanhKiem();
                hk.setMaHanhKiem("HKM" + maHS);
                hk.setMaHS(maHS);
                hk.setMaHocKy("HK01");
                hk.setXepLoai("Tốt");
                hk.setSoLanViPham(0);
                hk.setNhanXet("Học lực tốt, ý thức tốt");
                System.out.println("Insert HanhKiem for " + maHS + ": " + hanhKiemDao.add(hk));

                // Điểm (cho môn M001, chi tiết CTM001)
                Diem d = new Diem();
                d.setMaDiem("D" + maHS);
                d.setMaHS(maHS);
                d.setMaChiTiet("CTM001");
                d.setMaHocKy("HK01");
                d.setDiemThuongXuyen(7.0 + (i % 3));
                d.setDiemGiuaKy(6.5 + (i % 4) * 0.5);
                d.setDiemCuoiKy(7.0 + (i % 5) * 0.5);
                d.setDiemTBMonHocKy((d.getDiemThuongXuyen() + d.getDiemGiuaKy() + d.getDiemCuoiKy()) / 3.0);
                System.out.println("Insert Diem for " + maHS + ": " + diemDao.add(d));
            }

            for (int i = 1; i <= 10; i++) {
                // Class L03 students HS211..HS220
                String maHS = String.format("HS%03d", 210 + i);
                HocSinh hs = new HocSinh();
                hs.setMaHS(maHS);
                hs.setHoTen("Học sinh L03 " + i);
                hs.setNgaySinh(LocalDate.of(2010, ((i+3) % 12) + 1, Math.min(28, (i * 3))));
                hs.setGioiTinh((i % 2 == 0) ? "Nữ" : "Nam");
                hs.setDiaChi("Hà Nội");
                hs.setMaLop("L03");
                System.out.println("Insert HocSinh " + maHS + ": " + hsDao.add(hs));

                // Parent
                String maPH = String.format("PH%03d", 210 + i);
                Parent p = new Parent();
                p.setMaPhH(maPH);
                p.setTenPhH("Phụ huynh " + maPH);
                p.setSdt("091000" + String.format("%03d", i));
                p.setNgheNghiep("Không rõ");
                System.out.println("Insert Parent " + maPH + ": " + parentDao.add(p));
                parentDao.addRelation(maHS, maPH, "Cha");

                // Hạnh kiểm
                HanhKiem hk = new HanhKiem();
                hk.setMaHanhKiem("HKM" + maHS);
                hk.setMaHS(maHS);
                hk.setMaHocKy("HK01");
                hk.setXepLoai("Khá");
                hk.setSoLanViPham(0);
                hk.setNhanXet("Ý thức khá, cần cố gắng");
                System.out.println("Insert HanhKiem for " + maHS + ": " + hanhKiemDao.add(hk));

                // Điểm
                Diem d = new Diem();
                d.setMaDiem("D" + maHS);
                d.setMaHS(maHS);
                d.setMaChiTiet("CTM001");
                d.setMaHocKy("HK01");
                d.setDiemThuongXuyen(6.0 + (i % 4));
                d.setDiemGiuaKy(6.0 + (i % 3) * 0.5);
                d.setDiemCuoiKy(6.5 + (i % 5) * 0.4);
                d.setDiemTBMonHocKy((d.getDiemThuongXuyen() + d.getDiemGiuaKy() + d.getDiemCuoiKy()) / 3.0);
                System.out.println("Insert Diem for " + maHS + ": " + diemDao.add(d));
            }

            // Update sĩ số cho lớp mới
            lopDao.updateSiSo("L02");
            lopDao.updateSiSo("L03");
            System.out.println("Updated siSo for L02 and L03");

            System.out.println("Seeding completed.");

        } catch (Exception ex) {
            System.err.println("Error while seeding: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
}
