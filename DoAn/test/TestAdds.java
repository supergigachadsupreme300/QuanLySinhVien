import BusinessLogicLayer.*;
import DataObject.*;

public class TestAdds {
    public static void main(String[] args) {
        System.out.println("Starting add tests...");
        // GiaoVien
        GiaoVien gv = new GiaoVien(); gv.setMaGV("GV_TEST"); gv.setHoTen("Test GV"); gv.setDienThoai("0123456789"); gv.setEmail("test@example.com");
        System.out.println("GiaoVien add: " + new GiaoVienBLL().themGiaoVien(gv));
        // Mon
        Mon m = new Mon(); m.setMaMon("M_TEST"); m.setTenMon("Test Mon"); m.setTrangThai(1);
        System.out.println("Mon add: " + new MonHocBLL().themMonHoc(m));
        // ChiTietMon
        ChiTietMon ct = new ChiTietMon(); ct.setMaChiTiet("CT_TEST"); ct.setMaMon("M_TEST"); ct.setTenChiTiet("CT Test"); ct.setHeSo(1);
        System.out.println("ChiTietMon add: " + new ChiTietMonBLL().themChiTietMon(ct));
        // NamHoc
        NamHoc nh = new NamHoc(); nh.setMaNH("NH_TEST"); nh.setTenNH("2025-2026"); nh.setTrangThai(1);
        System.out.println("NamHoc add: " + new NamHocBLL().themNamHoc(nh));
        // HocKy
        HocKy hk = new HocKy(); hk.setMaHK("HK_TEST"); hk.setTenHK("HK Test"); hk.setMaNH("NH_TEST"); hk.setNgayBatDau(java.time.LocalDate.now()); hk.setNgayKetThuc(java.time.LocalDate.now()); hk.setTrangThai(1);
        System.out.println("HocKy add: " + new HocKyBLL().themHocKy(hk));
        // Lop
        Lop lop = new Lop(); lop.setMaLop("LOP_TEST"); lop.setTenLop("Test Lop"); lop.setSiSo(30); lop.setMaNH("NH_TEST"); lop.setMaGVCN("GV_TEST"); lop.setTrangThai(1);
        System.out.println("Lop add: " + new LopBLL().themLop(lop));
        // HocSinh
        HocSinh hs = new HocSinh(); hs.setMaHS("HS_TEST"); hs.setHoTen("Test HS"); hs.setNgaySinh(java.time.LocalDate.now()); hs.setGioiTinh("Nam"); hs.setDiaChi("Dia chi"); hs.setMaLop("LOP_TEST");
        System.out.println("HocSinh add: " + new HocSinhBLL().themHocSinh(hs));
        System.out.println("Done tests.");
    }
}
