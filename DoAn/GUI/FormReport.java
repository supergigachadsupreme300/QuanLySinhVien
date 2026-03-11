package GUI;

import DAO.*;
import DataObject.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.miginfocom.swing.MigLayout;

import BusinessLogicLayer.NamHocBLL;
import BusinessLogicLayer.PhuHuynhHocSinhBLL;

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
        setBorder(BorderFactory.createTitledBorder("Bao cao he thong"));

        JLabel lblTitle = new JLabel("QUẢN LÝ BÁO CÁO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlOptions = new JPanel(new MigLayout("insets 10", "[]15[grow]", "[]10[]"));
        pnlOptions.setBorder(BorderFactory.createTitledBorder("Tùy chọn báo cáo"));

        pnlOptions.add(new JLabel("Loại báo cáo:"));
        cboReportType = new JComboBox<>(new String[]{"Chi tiết", "Tổng quan"});
        pnlOptions.add(cboReportType, "growx, wrap");

        pnlOptions.add(new JLabel("Tên file:"));
        txtFileName = new JTextField("report.pdf");
        pnlOptions.add(txtFileName, "growx");

        add(pnlOptions, "growx, wrap");

        JPanel pnlButtons = new JPanel();
        btnExport = createStyledButton("Xuất PDF", new Color(34, 139, 34));
        pnlButtons.add(btnExport);

        add(pnlButtons, "growx, wrap");

        lblStatus = new JLabel("ẵn sàng xuất báo cáo.", JLabel.CENTER);
        lblStatus.setForeground(Color.BLUE);
        add(lblStatus, "growx");

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
            JOptionPane.showMessageDialog(this, "Vui long nhap ten file!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!fileNameTemp.endsWith(".pdf")) fileNameTemp += ".pdf";
        final String fileName = fileNameTemp;

        final String reportType = (String) cboReportType.getSelectedItem();

        lblStatus.setText("Dang xuat bao cao...");
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
                    lblStatus.setText("Xuat bao cao thanh cong: " + fileName);
                    lblStatus.setForeground(Color.GREEN);
                    JOptionPane.showMessageDialog(FormReport.this, "Bao cao da duoc xuat thanh cong!", "Thanh cong", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    lblStatus.setText("Loi xuat bao cao: " + ex.getMessage());
                    lblStatus.setForeground(Color.RED);
                    JOptionPane.showMessageDialog(FormReport.this, "Loi xuat bao cao: " + ex.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void exportReport(String dest, String reportType) throws Exception {

        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(dest));
        doc.open();

        com.itextpdf.text.Font titleFont;
        com.itextpdf.text.Font defaultFont;
        try {
            BaseFont bf = BaseFont.createFont("lib/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            titleFont = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
            defaultFont = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.NORMAL);
        } catch (Exception e) {
            titleFont = FontFactory.getFont("Tahoma", BaseFont.IDENTITY_H, true, 18, com.itextpdf.text.Font.BOLD);
            defaultFont = FontFactory.getFont("Tahoma", BaseFont.IDENTITY_H, true, 12, com.itextpdf.text.Font.NORMAL);
        }

        doc.add(new Paragraph("BÁO CÁO CHI TIẾT HỆ THỐNG QUẢN LÝ HỌC SINH", titleFont));
        doc.add(Chunk.NEWLINE);

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

        PhuHuynhHocSinhBLL phhsBLL = new PhuHuynhHocSinhBLL();

        List<GiaoVien> teachers = gvDao.getAll();
        List<HocSinh> students = hsDao.getAll();
        List<Lop> classes = lopDao.getAll();

        doc.add(new Paragraph("TỔNG QUAN", defaultFont));
        doc.add(new Paragraph("Tổng số giáo viên: " + teachers.size(), defaultFont));
        doc.add(new Paragraph("Tổng số học sinh: " + students.size(), defaultFont));
        doc.add(new Paragraph("Tổng số lớp: " + classes.size(), defaultFont));
        doc.add(Chunk.NEWLINE);


        doc.add(new Paragraph("CHI TIẾT THEO LỚP", defaultFont));

        for (Lop lop : classes) {

            doc.add(new Paragraph("Lớp " + lop.getTenLop() + " (" + lop.getMaLop() + ")", defaultFont));

            GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());

            doc.add(new Paragraph("GVCN " + (gv == null ? "<khong>" : gv.getHoTen()), defaultFont));

            List<HocSinh> hsInLop =
                    students.stream()
                            .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                            .collect(Collectors.toList());

            doc.add(new Paragraph("Sĩ số " + hsInLop.size(), defaultFont));

            java.util.Set<String> subjectNames = hsInLop.stream()
                    .flatMap(hs -> diemDao.getByMaHS(hs.getMaHS()).stream())
                    .map(d -> {
                        DataObject.Mon m = new MonHocDAO().findByMaMon(d.getMaMon());
                        return m == null ? "<khong>" : m.getTenMon();
                    })
                    .collect(Collectors.toCollection(java.util.LinkedHashSet::new));

            int cols = 2 + subjectNames.size() + 2;
            PdfPTable classTable = new PdfPTable(cols);
            classTable.setWidthPercentage(100);

            classTable.addCell(new PdfPCell(new Paragraph("Mã HS", defaultFont)));
            classTable.addCell(new PdfPCell(new Paragraph("Học sinh", defaultFont)));
            for (String subj : subjectNames) {
                classTable.addCell(new PdfPCell(new Paragraph(subj, defaultFont)));
            }
            classTable.addCell(new PdfPCell(new Paragraph("Hạnh kiểm", defaultFont)));
            classTable.addCell(new PdfPCell(new Paragraph("Phụ huynh", defaultFont)));

            for (HocSinh hs : hsInLop) {
                classTable.addCell(new PdfPCell(new Paragraph(hs.getMaHS(), defaultFont)));
                classTable.addCell(new PdfPCell(new Paragraph(hs.getHoTen(), defaultFont)));

                Map<String, String> scoreMap = diemDao.getByMaHS(hs.getMaHS()).stream()
                        .collect(Collectors.toMap(
                                d -> {
                                    DataObject.Mon m = new MonHocDAO().findByMaMon(d.getMaMon());
                                    return m == null ? "<khong>" : m.getTenMon();
                                },
                                d -> String.valueOf(d.getDiemTBMonHocKy()),
                                (a, b) -> a
                        ));

                for (String subj : subjectNames) {
                    String val = scoreMap.getOrDefault(subj, "");
                    classTable.addCell(new PdfPCell(new Paragraph(val, defaultFont)));
                }

                List<HanhKiem> hks = hkDao.getByMaHS(hs.getMaHS());
                String hkSummary = hks.stream()
                        .map(hk -> hk.getXepLoai() + " " + hk.getNhanXet())
                        .collect(Collectors.joining("; "));
                classTable.addCell(new PdfPCell(new Paragraph(hkSummary, defaultFont)));

                List<DataObject.PhuHuynhHocSinh> phhsList = phhsBLL.layTheoHS(hs.getMaHS());
                String phSummary = phhsList.stream()
                        .map(phhs -> {
                            DataObject.Parent p = phDao.getById(phhs.getMaPH());
                            return p == null ? "" : p.getTenPhH() + "(" + phhs.getQuanHe() + ")";
                        })
                        .collect(Collectors.joining("; "));
                classTable.addCell(new PdfPCell(new Paragraph(phSummary, defaultFont)));
            }

            doc.add(classTable);
            doc.add(Chunk.NEWLINE);
        }


        doc.add(new Paragraph("DANH SÁCH GIÁO VIÊN VÀ BỘ MÔN", defaultFont));

        for (GiaoVien gv : teachers) {

            doc.add(new Paragraph("Giáo viên " + gv.getHoTen() + " (" + gv.getMaGV() + ")", defaultFont));


            List<PhanCong> allPcs = pcDao.getAll();
            List<PhanCong> pcs = allPcs.stream()
                    .filter(pc -> gv.getMaGV().equals(pc.getMaGV()))
                    .collect(Collectors.toList());

            for (PhanCong pc : pcs) {

                DataObject.Mon m = new MonHocDAO().findByMaMon(pc.getMaMon());

                doc.add(new Paragraph(
                        "Bộ môn " +
                                (m == null ? "<khong>" : m.getTenMon()), defaultFont
                ));
            }

            doc.add(Chunk.NEWLINE);
        }


        NamHocBLL namBLL = new NamHocBLL();

        List<DataObject.NamHoc> years = namBLL.getAllActive();

        for (DataObject.NamHoc nh : years) {

            doc.add(new Paragraph("Năm học: " + nh.getTenNH(), defaultFont));

        }

        doc.close();
    }
}