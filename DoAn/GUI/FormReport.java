package GUI;

import DAO.*;
import DataObject.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;import java.awt.*;import java.awt.event.*;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.miginfocom.swing.MigLayout;

import BusinessLogicLayer.NamHocBLL;

public class FormReport extends JPanel {
    private JButton btnExport;
    private JComboBox<String> cboReportType;
    private JLabel lblStatus;
    private JTextField txtFileName;

    public FormReport() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 20", "[grow]", "[]20[]20[]20[]"));
        setBorder(BorderFactory.createTitledBorder("Báo cáo hệ thống"));

        // Title
        JLabel lblTitle = new JLabel("QUẢN LÝ BÁO CÁO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        // Options panel
        JPanel pnlOptions = new JPanel(new MigLayout("insets 10", "[]15[grow]", "[]10[]"));
        pnlOptions.setBorder(BorderFactory.createTitledBorder("Tùy chọn báo cáo"));

        pnlOptions.add(new JLabel("Loại báo cáo:"));
        cboReportType = new JComboBox<>(new String[]{"Tóm tắt", "Chi tiết", "Theo lớp", "Theo năm"});
        pnlOptions.add(cboReportType, "growx, wrap");

        pnlOptions.add(new JLabel("Tên file:"));
        txtFileName = new JTextField("report.pdf");
        pnlOptions.add(txtFileName, "growx");

        add(pnlOptions, "growx, wrap");

        // Button panel
        JPanel pnlButtons = new JPanel();
        btnExport = createStyledButton("Xuất PDF", new Color(34, 139, 34));
        pnlButtons.add(btnExport);

        add(pnlButtons, "growx, wrap");

        // Status
        lblStatus = new JLabel("Sẵn sàng xuất báo cáo.", JLabel.CENTER);
        lblStatus.setForeground(Color.BLUE);
        add(lblStatus, "growx");

        // Events
        btnExport.addActionListener(e -> onExport());
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(120, 40));
        return btn;
    }

    private void onExport() {
        String fileNameTemp = txtFileName.getText().trim();
        if (fileNameTemp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên file!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!fileNameTemp.endsWith(".pdf")) fileNameTemp += ".pdf";
        final String fileName = fileNameTemp;

        final String reportType = (String) cboReportType.getSelectedItem();
        lblStatus.setText("Đang xuất báo cáo...");
        lblStatus.setForeground(Color.ORANGE);

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                exportReport(fileName, reportType);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    lblStatus.setText("Xuất báo cáo thành công: " + fileName);
                    lblStatus.setForeground(Color.GREEN);
                    JOptionPane.showMessageDialog(FormReport.this, "Báo cáo đã được xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi xuất báo cáo: " + ex.getMessage());
                    lblStatus.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(FormReport.this, "Lỗi xuất báo cáo: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void exportReport(String dest, String reportType) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(dest));
        doc.open();

        // Title
        com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD);
        doc.add(new Paragraph("BÁO CÁO HỆ THỐNG QUẢN LÝ HỌC SINH", titleFont));
        doc.add(new Paragraph("Loại báo cáo: " + reportType, new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12)));
        doc.add(Chunk.NEWLINE);

        // Gather data
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

        List<GiaoVien> teachers = gvDao.getAll();
        List<HocSinh> students = hsDao.getAll();
        List<Lop> classes = lopDao.getAll();

        if ("Tóm tắt".equals(reportType)) {
            // Summary
            doc.add(new Paragraph("TỔNG QUAN", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD)));
            doc.add(new Paragraph("Tổng số giáo viên: " + teachers.size()));
            doc.add(new Paragraph("Tổng số học sinh: " + students.size()));
            doc.add(new Paragraph("Tổng số lớp: " + classes.size()));
        } else if ("Chi tiết".equals(reportType)) {
            // Detailed
            doc.add(new Paragraph("CHI TIẾT THEO LỚP", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD)));
            for (Lop lop : classes) {
                doc.add(new Paragraph("Lớp " + lop.getTenLop() + " (" + lop.getMaLop() + ")", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD)));
                GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());
                doc.add(new Paragraph("  GVCN: " + (gv == null ? "<không>" : gv.getHoTen())));
                List<HocSinh> hsInLop = students.stream()
                        .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                        .collect(Collectors.toList());
                doc.add(new Paragraph("  Sĩ số: " + hsInLop.size()));
                doc.add(Chunk.NEWLINE);
            }
        } else if ("Theo lớp".equals(reportType)) {
            // Per class detailed
            for (Lop lop : classes) {
                doc.add(new Paragraph("Lớp " + lop.getTenLop() + " (" + lop.getMaLop() + ")", new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD)));
                GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());
                doc.add(new Paragraph("  GVCN: " + (gv == null ? "<không>" : gv.getHoTen())));
                List<HocSinh> hsInLop = students.stream()
                        .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                        .collect(Collectors.toList());
                doc.add(new Paragraph("  Sĩ số: " + hsInLop.size()));

                // TKB
                List<ThoiKhoaBieu> tkbs = tkbDao.getByLop(lop.getMaLop());
                for (ThoiKhoaBieu t : tkbs) {
                    doc.add(new Paragraph("    TKB HK " + t.getMaHocKy() + " từ " + t.getNgayBatDau() + " đến " + t.getNgayKetThuc()));
                    List<ChiTietTiet> tiet = new ChiTietTietDAL().getByTKB(t.getMaTKB());
                    for (ChiTietTiet ct : tiet) {
                        DataObject.Mon m = new MonHocDAO().findByMaMon(ct.getMaMon());
                        doc.add(new Paragraph("      " + ct.getThu() + " tiết " + ct.getTiet() + " môn " + (m == null ? "<không>" : m.getTenMon()) + " phòng " + ct.getPhongHoc()));
                    }
                }
                doc.add(Chunk.NEWLINE);
            }
        } else if ("Theo năm".equals(reportType)) {
            // hierarchical year -> semester -> class -> student -> classification/conduct/violations
            NamHocBLL namBLL = new NamHocBLL();
            List<DataObject.NamHoc> years = namBLL.getAllActive();
            for (DataObject.NamHoc nh : years) {
                doc.add(new Paragraph("Năm học: " + nh.getTenNH(), new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 14, com.itextpdf.text.Font.BOLD)));
                // semesters belonging to this year
                List<DataObject.HocKy> hks = new HocKyDAL().getAllActive().stream()
                        .filter(h -> nh.getMaNH().equals(h.getMaNH()))
                        .collect(Collectors.toList());
                for (DataObject.HocKy hk : hks) {
                    doc.add(new Paragraph("  Học kỳ: " + hk.getTenHK() + " (" + hk.getMaHK() + ")"));
                    // classes in year
                    List<Lop> lopsByYear = lopDao.getAllActive().stream()
                            .filter(l -> nh.getMaNH().equals(l.getMaNH()))
                            .collect(Collectors.toList());
                    for (Lop lop : lopsByYear) {
                        doc.add(new Paragraph("    Lớp " + lop.getTenLop() + " (" + lop.getMaLop() + ")"));
                        List<HocSinh> studentsInClass = hsDao.getByMaLop(lop.getMaLop());
                        for (HocSinh hs : studentsInClass) {
                            doc.add(new Paragraph("      HS: " + hs.getHoTen() + " (" + hs.getMaHS() + ")"));
                            // classification
                            List<XepLoai> xls = new XepLoaiDAO().getByMaHS(hs.getMaHS()).stream()
                                    .filter(x -> hk.getMaHK().equals(x.getMaHocKy()))
                                    .collect(Collectors.toList());
                            for (XepLoai xl : xls) {
                                doc.add(new Paragraph("        Xếp loại: HL=" + xl.getXepLoaiHocLuc() + ", HK=" + xl.getXepLoaiHanhKiem() + ", điểmTB=" + xl.getDiemTBChung()));
                            }
                            // conduct
                            List<HanhKiem> hksList = new HanhKiemDAL().getByMaHS(hs.getMaHS()).stream()
                                    .filter(h -> hk.getMaHK().equals(h.getMaHocKy()))
                                    .collect(Collectors.toList());
                            for (HanhKiem hkRec : hksList) {
                                doc.add(new Paragraph("        Hạnh kiểm: " + hkRec.getXepLoai() + " – " + hkRec.getNhanXet() + " (vi phạm=" + hkRec.getSoLanViPham() + ")"));
                                // violations under conduct
                                List<ViPham> vps = new ViPhamDAO().getByMaHS(hs.getMaHS()).stream()
                                        .filter(v -> hk.getMaHK().equals(v.getMaHocKy()))
                                        .collect(Collectors.toList());
                                for (ViPham vp : vps) {
                                    doc.add(new Paragraph("          Vi phạm: " + vp.getNgayViPham() + " – " + vp.getNoiDung() + " (" + vp.getMucDo() + ")"));
                                }
                            }
                        }
                    }
                }
                doc.add(Chunk.NEWLINE);
            }
        }

        doc.close();
    }
}