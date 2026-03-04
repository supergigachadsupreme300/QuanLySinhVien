package GUI;

import DAO.*;
import DataObject.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormReport extends JPanel {
    private JButton btnExport;

    public FormReport() {
        btnExport = new JButton("Xuất PDF");
        this.add(btnExport);
        btnExport.addActionListener(e -> onExport());
    }

    private void onExport() {
        try {
            String dest = "report.pdf";
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(dest));
            doc.open();

            // --- gather data --------------------------------------------------
            GiaoVienDAO gvDao = new GiaoVienDAO();
            HocSinhDAO hsDao = new HocSinhDAO();
            LopDAL lopDao = new LopDAL();
            PhanCongDAL pcDao = new PhanCongDAL();
            ThoiKhoaBieuDAL tkbDao = new ThoiKhoaBieuDAL();
            ParentDAO phDao = new ParentDAO();
            DiemDAL diemDao = new DiemDAL();
            HanhKiemDAL hkDao = new HanhKiemDAL();
            ViPhamDAO vpDao = new ViPhamDAO();
            XepLoaiDAO xlDao = new XepLoaiDAO();
            // … add more as needed

            List<GiaoVien> teachers = gvDao.getAll();
            List<HocSinh> students = hsDao.getAll();
            List<Lop> classes = lopDao.getAll();
            // you can call getParentsByHocSinh() if you implemented it

            // --- write summary counts ----------------------------------------
            doc.add(new Paragraph("Tổng số giáo viên: " + teachers.size()));
            doc.add(new Paragraph("Tổng số học sinh: " + students.size()));
            doc.add(new Paragraph("Tổng số lớp: " + classes.size()));
            doc.add(Chunk.NEWLINE);

            // --- per‑class detail --------------------------------------------
            for (Lop lop : classes) {
                doc.add(new Paragraph("Lớp " + lop.getTenLop() + " (" + lop.getMaLop() + ")"));
                // giáo viên chủ nhiệm
                GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());
                doc.add(new Paragraph("  GVCN: " + (gv==null?"<không>":gv.getHoTen())));
                // số học sinh
                List<HocSinh> hsInLop = students.stream()
                        .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                        .collect(Collectors.toList());
                doc.add(new Paragraph("  Sĩ số: " + hsInLop.size()));
                // thời khóa biểu
                List<ThoiKhoaBieu> tkbs = tkbDao.getByLop(lop.getMaLop());
                for (ThoiKhoaBieu t : tkbs) {
                    doc.add(new Paragraph("    TKB HK " + t.getMaHocKy() +
                            " từ " + t.getNgayBatDau() + " đến " + t.getNgayKetThuc()));
                    // chi tiết tiết
                        List<ChiTietTiet> tiet = new ChiTietTietDAL().getByTKB(t.getMaTKB());
                        for (ChiTietTiet ct : tiet) {
                            DataObject.Mon m = new MonHocDAO().findByMaMon(ct.getMaMon());
                            doc.add(new Paragraph("      " + ct.getThu() + " tiết " + ct.getTiet() +
                                " môn " + (m==null?"<không>":m.getTenMon()) + " phòng " + ct.getPhongHoc()));
                    }
                }
                doc.add(Chunk.NEWLINE);
            }

            // --- per‑student detail (parents, điểm, …) ------------------------
            for (HocSinh hs : students) {
                doc.add(new Paragraph("Học sinh: " + hs.getHoTen() + " ("+hs.getMaHS()+")"));
                doc.add(new Paragraph("  Lớp: " + hs.getMaLop()));
                // bố mẹ
                List<Parent> parents = phDao.getParentsByHocSinh(hs.getMaHS());
                for (Parent p : parents) {
                    doc.add(new Paragraph("  Phụ huynh: " + p.getTenPhH() + " – " + p.getQuanHe()));
                }
                // bảng điểm
                List<DataObject.Diem> diem = diemDao.getByMaHS(hs.getMaHS());
                for (DataObject.Diem d : diem) {
                    doc.add(new Paragraph("  Điểm " + d.getMaChiTiet() + " HK " + d.getMaHocKy() +
                            ": TX=" + d.getDiemThuongXuyen() + ", GK=" + d.getDiemGiuaKy() +
                            ", CK=" + d.getDiemCuoiKy()));
                }
                // hạnh kiểm, vi phạm, xếp loại …
                // … similar calls to hkDao, vpDao, xlDao
                doc.add(Chunk.NEWLINE);
            }

            doc.close();
            JOptionPane.showMessageDialog(this, "PDF đã tạo tại " + dest);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tạo PDF: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Báo cáo tổng hợp");
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.getContentPane().add(new FormReport());
            f.pack();
            f.setSize(400,200);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}