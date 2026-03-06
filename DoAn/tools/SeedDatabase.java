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

            // Update lớp sĩ số
            lopDao.updateSiSo("L01");
            System.out.println("Updated siSo for L01");

            System.out.println("Seeding completed.");

        } catch (Exception ex) {
            System.err.println("Error while seeding: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            db.closeConnection();
        }
    }
}
