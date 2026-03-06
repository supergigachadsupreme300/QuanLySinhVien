import BusinessLogicLayer.*;
import DataObject.*;

public class TestAddsUnique {
    public static void main(String[] args) {
        String s = String.valueOf(System.currentTimeMillis()).substring(8);
        System.out.println("Starting unique add tests... suffix="+s);
        // GiaoVien
        GiaoVien gv = new GiaoVien(); gv.setMaGV("GV_"+s); gv.setHoTen("Test GV"); gv.setDienThoai("0123456789"); gv.setEmail("test@example.com");
        System.out.println("GiaoVien add: " + new GiaoVienBLL().themGiaoVien(gv));
        // Mon
        Mon m = new Mon(); m.setMaMon("M_"+s); m.setTenMon("Test Mon"); m.setTrangThai(1);
        System.out.println("Mon add: " + new MonHocBLL().themMonHoc(m));
        // ChiTietMon
        ChiTietMon ct = new ChiTietMon(); ct.setMaChiTiet("CT_"+s); ct.setMaMon("M_"+s); ct.setTenChiTiet("CT Test"); ct.setHeSo(1);
        System.out.println("ChiTietMon add: " + new ChiTietMonBLL().themChiTietMon(ct));
        // NamHoc
        NamHoc nh = new NamHoc(); nh.setMaNH("NH_"+s); nh.setTenNH("2025-2026"); nh.setTrangThai(1);
        System.out.println("NamHoc add: " + new NamHocBLL().themNamHoc(nh));
        // HocKy
        HocKy hk = new HocKy(); hk.setMaHK("HK_"+s); hk.setTenHK("HK Test"); hk.setMaNH("NH_"+s); hk.setNgayBatDau(java.time.LocalDate.now()); hk.setNgayKetThuc(java.time.LocalDate.now()); hk.setTrangThai(1);
        System.out.println("HocKy add: " + new HocKyBLL().themHocKy(hk));
        // Lop
        Lop lop = new Lop(); lop.setMaLop("LOP_"+s); lop.setTenLop("Test Lop"); lop.setSiSo(30); lop.setMaNH("NH_"+s); lop.setMaGVCN("GV_"+s); lop.setTrangThai(1);
        System.out.println("Lop add: " + new LopBLL().themLop(lop));
        // HocSinh
        HocSinh hs = new HocSinh(); hs.setMaHS("HS_"+s); hs.setHoTen("Test HS"); hs.setNgaySinh(java.time.LocalDate.now()); hs.setGioiTinh("Nam"); hs.setDiaChi("Dia chi"); hs.setMaLop("LOP_"+s);
        System.out.println("HocSinh add: " + new HocSinhBLL().themHocSinh(hs));
        System.out.println("Done unique tests.");
    }
}
