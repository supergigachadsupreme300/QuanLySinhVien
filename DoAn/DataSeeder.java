import DAO.*;
import DataObject.*;
import java.time.LocalDate;
import java.util.Random;

public class DataSeeder {
    public static void main(String[] args) {
        seedData();
    }

    public static void seedData() {
        // Seed NamHoc
        NamHocDAL nhDao = new NamHocDAL();
        nhDao.insert(new NamHoc("NH2023", "2023-2024", 1));
        nhDao.insert(new NamHoc("NH2024", "2024-2025", 1));
        nhDao.insert(new NamHoc("NH2025", "2025-2026", 1));

        // Seed HocKy
        HocKyDAL hkDao = new HocKyDAL();
        hkDao.insert(new HocKy("HK1_2023", "Học kỳ 1 2023-2024", "NH2023", LocalDate.of(2023, 9, 1), LocalDate.of(2023, 12, 31), 1));
        hkDao.insert(new HocKy("HK2_2023", "Học kỳ 2 2023-2024", "NH2023", LocalDate.of(2024, 1, 1), LocalDate.of(2024, 5, 31), 1));
        hkDao.insert(new HocKy("HK1_2024", "Học kỳ 1 2024-2025", "NH2024", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31), 1));
        hkDao.insert(new HocKy("HK2_2024", "Học kỳ 2 2024-2025", "NH2024", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 5, 31), 1));

        // Seed MonHoc
        MonHocDAO mhDao = new MonHocDAO();
        mhDao.insert(new Mon("MH01", "Toán", 1));
        mhDao.insert(new Mon("MH02", "Ngữ Văn", 1));
        mhDao.insert(new Mon("MH03", "Tiếng Anh", 1));
        mhDao.insert(new Mon("MH04", "Vật Lý", 1));
        mhDao.insert(new Mon("MH05", "Hóa Học", 1));
        mhDao.insert(new Mon("MH06", "Sinh Học", 1));
        mhDao.insert(new Mon("MH07", "Lịch Sử", 1));
        mhDao.insert(new Mon("MH08", "Địa Lý", 1));
        mhDao.insert(new Mon("MH09", "GDCD", 1));
        mhDao.insert(new Mon("MH10", "Thể Dục", 1));

        // Seed GiaoVien
        GiaoVienDAO gvDao = new GiaoVienDAO();
        gvDao.them(new GiaoVien("GV01", "Nguyễn Văn A", LocalDate.of(1980, 1, 1), "Nam", "0123456789", "a@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV02", "Trần Thị B", LocalDate.of(1981, 2, 2), "Nữ", "0987654321", "b@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV03", "Lê Văn C", LocalDate.of(1982, 3, 3), "Nam", "0111111111", "c@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV04", "Phạm Thị D", LocalDate.of(1983, 4, 4), "Nữ", "0222222222", "d@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV05", "Hoàng Văn E", LocalDate.of(1984, 5, 5), "Nam", "0333333333", "e@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV06", "Đỗ Thị F", LocalDate.of(1985, 6, 6), "Nữ", "0444444444", "f@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV07", "Bùi Văn G", LocalDate.of(1986, 7, 7), "Nam", "0555555555", "g@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV08", "Vũ Thị H", LocalDate.of(1987, 8, 8), "Nữ", "0666666666", "h@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV09", "Đinh Văn I", LocalDate.of(1988, 9, 9), "Nam", "0777777777", "i@gmail.com", "Hà Nội", 1));
        gvDao.them(new GiaoVien("GV10", "Ngô Thị K", LocalDate.of(1989, 10, 10), "Nữ", "0888888888", "k@gmail.com", "Hà Nội", 1));

        // Seed Lop
        LopDAL lopDao = new LopDAL();
        lopDao.insert(new Lop("L10A1", "10A1", 30, "NH2024", "GV01", 1));
        lopDao.insert(new Lop("L10A2", "10A2", 28, "NH2024", "GV02", 1));
        lopDao.insert(new Lop("L11A1", "11A1", 32, "NH2024", "GV03", 1));
        lopDao.insert(new Lop("L11A2", "11A2", 29, "NH2024", "GV04", 1));
        lopDao.insert(new Lop("L12A1", "12A1", 31, "NH2024", "GV05", 1));

        // Seed HocSinh
        HocSinhDAO hsDao = new HocSinhDAO();
        String[] hoTenHS = {"Nguyễn Văn An", "Trần Thị Bình", "Lê Văn Cao", "Phạm Thị Dung", "Hoàng Văn Em", "Đỗ Thị Phuong", "Bùi Văn Giang", "Vũ Thị Hoa", "Đinh Văn Huy", "Ngô Thị Lan", "Mai Văn Minh", "Lý Thị Nga", "Trịnh Văn Oanh", "Phan Thị Quy", "Tạ Văn Son", "Đặng Thị Thu", "Đoàn Văn Tuan", "Vương Thị Uyên", "Châu Văn Vinh", "Lương Thị Xuan"};
        String[] maLopList = {"L10A1", "L10A2", "L11A1", "L11A2", "L12A1"};
        Random rand = new Random();
        for (int i = 1; i <= 100; i++) {
            String maHS = "HS" + String.format("%03d", i);
            String hoTen = hoTenHS[rand.nextInt(hoTenHS.length)];
            LocalDate ngaySinh = LocalDate.of(2005 + rand.nextInt(5), 1 + rand.nextInt(12), 1 + rand.nextInt(28));
            String gioiTinh = rand.nextBoolean() ? "Nam" : "Nữ";
            String diaChi = "Hà Nội";
            String maLop = maLopList[rand.nextInt(maLopList.length)];
            HocSinh hs = new HocSinh(maHS, hoTen, ngaySinh, gioiTinh, diaChi, maLop, 1);
            hsDao.add(hs);
        }

        // Seed PhanCong
        PhanCongDAL pcDao = new PhanCongDAL();
        String[] maGVList = {"GV01", "GV02", "GV03", "GV04", "GV05", "GV06", "GV07", "GV08", "GV09", "GV10"};
        String[] maMonList = {"MH01", "MH02", "MH03", "MH04", "MH05", "MH06", "MH07", "MH08", "MH09", "MH10"};
        int pcCount = 1;
        for (String maGV : maGVList) {
            for (int i = 0; i < 3; i++) {
                String maMon = maMonList[rand.nextInt(maMonList.length)];
                String maPC = "PC" + String.format("%03d", pcCount++);
                String maLop = maLopList[rand.nextInt(maLopList.length)];
                pcDao.insert(new PhanCong(maPC, maGV, maMon, maLop, "NH2024", "", 1));
            }
        }

        // Seed ThoiKhoaBieu
        ThoiKhoaBieuDAL tkbDao = new ThoiKhoaBieuDAL();
        tkbDao.insert(new ThoiKhoaBieu("TKB001", "L10A1", "HK1_2024", 1, LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31)));
        tkbDao.insert(new ThoiKhoaBieu("TKB002", "L10A2", "HK1_2024", 1, LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31)));
        tkbDao.insert(new ThoiKhoaBieu("TKB003", "L11A1", "HK1_2024", 1, LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31)));
        tkbDao.insert(new ThoiKhoaBieu("TKB004", "L11A2", "HK1_2024", 1, LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31)));
        tkbDao.insert(new ThoiKhoaBieu("TKB005", "L12A1", "HK1_2024", 1, LocalDate.of(2024, 9, 1), LocalDate.of(2024, 12, 31)));

        // Seed ChiTietTiet
        ChiTietTietDAL ctDao = new ChiTietTietDAL();
        String[] thuList = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
        int ctCount = 1;
        for (String tkb : new String[]{"TKB001", "TKB002", "TKB003", "TKB004", "TKB005"}) {
            for (int i = 1; i <= 10; i++) {
                String thu = thuList[rand.nextInt(thuList.length)];
                int tiet = 1 + rand.nextInt(5);
                String maMon = maMonList[rand.nextInt(maMonList.length)];
                String phongHoc = "P" + (101 + rand.nextInt(10));
                String maChiTiet = "CT" + String.format("%03d", ctCount++);
                ctDao.insert(new ChiTietTiet(maChiTiet, tkb, maMon, thu, tiet, phongHoc, "07:00", "07:45", 1));
            }
        }

        // Seed Diem
        DiemDAL diemDao = new DiemDAL();
        int diemCount = 1;
        for (int i = 1; i <= 100; i++) {
            String maHS = "HS" + String.format("%03d", i);
            for (String maMon : maMonList) {
                double tx = 5 + rand.nextDouble() * 5;
                double gk = 5 + rand.nextDouble() * 5;
                double ck = 5 + rand.nextDouble() * 5;
                double tb = (tx + gk * 2 + ck * 3) / 6;
                String maDiem = "D" + String.format("%04d", diemCount++);
                diemDao.add(new Diem(maDiem, maHS, maMon, "HK1_2024", tx, gk, ck, tb));
            }
        }

        // Seed HanhKiem
        HanhKiemDAL hkDao2 = new HanhKiemDAL();
        String[] xepLoaiHK = {"Tốt", "Khá", "Trung bình", "Yếu"};
        int hkCount = 1;
        for (int i = 1; i <= 100; i++) {
            String maHS = "HS" + String.format("%03d", i);
            String xepLoai = xepLoaiHK[rand.nextInt(xepLoaiHK.length)];
            String nhanXet = "Học sinh " + xepLoai.toLowerCase();
            int soLanViPham = rand.nextInt(5);
            String maHanhKiem = "HK" + String.format("%03d", hkCount++);
            hkDao2.add(new HanhKiem(maHanhKiem, maHS, "HK1_2024", xepLoai, soLanViPham, nhanXet));
        }

        // Seed ViPham
        ViPhamDAO vpDao = new ViPhamDAO();
        String[] noiDungVP = {"Đi muộn", "Không làm bài tập", "Quấy rối bạn học", "Vi phạm nội quy"};
        String[] mucDoVP = {"Nhẹ", "Trung bình", "Nặng"};
        int vpCount = 1;
        for (int i = 1; i <= 50; i++) {
            String maHS = "HS" + String.format("%03d", rand.nextInt(100) + 1);
            LocalDate ngayVP = LocalDate.of(2024, 9 + rand.nextInt(4), 1 + rand.nextInt(28));
            String noiDung = noiDungVP[rand.nextInt(noiDungVP.length)];
            String mucDo = mucDoVP[rand.nextInt(mucDoVP.length)];
            String maViPham = "VP" + String.format("%03d", vpCount++);
            vpDao.add(new ViPham(maViPham, maHS, "HK1_2024", ngayVP, noiDung, mucDo, true));
        }

        // Seed XepLoai
        XepLoaiDAO xlDao = new XepLoaiDAO();
        String[] xepLoaiHocLuc = {"Giỏi", "Khá", "Trung bình", "Yếu"};
        int xlCount = 1;
        for (int i = 1; i <= 100; i++) {
            String maHS = "HS" + String.format("%03d", i);
            String hl = xepLoaiHocLuc[rand.nextInt(xepLoaiHocLuc.length)];
            String hk = xepLoaiHK[rand.nextInt(xepLoaiHK.length)];
            double dtb = 5 + rand.nextDouble() * 5;
            String nhanXet = "Học lực " + hl + ", hạnh kiểm " + hk;
            boolean duocLenLop = dtb >= 5;
            String maXepLoai = "XL" + String.format("%03d", xlCount++);
            xlDao.add(new XepLoai(maXepLoai, maHS, "HK1_2024", hl, hk, dtb, nhanXet, duocLenLop));
        }

        // Seed PhuHuynhHocSinh
        // PhuHuynhHocSinhDAO phDao = new PhuHuynhHocSinhDAO();
        // for (int i = 1; i <= 100; i++) {
        //     String maHS = "HS" + String.format("%03d", i);
        //     String maPH = "PH" + String.format("%03d", i);
        //     String hoTenPH = "Phụ huynh của " + maHS;
        //     String dienThoai = "09" + String.format("%08d", rand.nextInt(100000000));
        //     String email = "ph" + i + "@gmail.com";
        //     String quanHe = "Cha/Mẹ";
        //     phDao.add(new PhuHuynhHocSinh(maHS, maPH, quanHe, 1));
        // }

        System.out.println("Dữ liệu mẫu đã được thêm thành công!");
    }
}