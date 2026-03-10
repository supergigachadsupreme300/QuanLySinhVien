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
            // Fallback to Tahoma if Arial.ttf not found
            titleFont = FontFactory.getFont("Tahoma", BaseFont.IDENTITY_H, true, 18, com.itextpdf.text.Font.BOLD);
            defaultFont = FontFactory.getFont("Tahoma", BaseFont.IDENTITY_H, true, 12, com.itextpdf.text.Font.NORMAL);
        }

        doc.add(new Paragraph("BAO CAO CHI TIET HE THONG QUAN LY HOC SINH", titleFont));
        doc.add(new Paragraph("Loai bao cao: Chi tiet", defaultFont));
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

        // TONG QUAN
        doc.add(new Paragraph("TONG QUAN", defaultFont));
        doc.add(new Paragraph("Tong so giao vien " + teachers.size(), defaultFont));
        doc.add(new Paragraph("Tong so hoc sinh " + students.size(), defaultFont));
        doc.add(new Paragraph("Tong so lop " + classes.size(), defaultFont));
        doc.add(Chunk.NEWLINE);

        // CHI TIET THEO LOP
        doc.add(new Paragraph("CHI TIET THEO LOP", defaultFont));

        for (Lop lop : classes) {

            doc.add(new Paragraph("Lop " + lop.getTenLop() + " (" + lop.getMaLop() + ")", defaultFont));

            GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());

            doc.add(new Paragraph("GVCN " + (gv == null ? "<khong>" : gv.getHoTen()), defaultFont));

            List<HocSinh> hsInLop =
                    students.stream()
                            .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                            .collect(Collectors.toList());

            doc.add(new Paragraph("Sĩ số " + hsInLop.size(), defaultFont));

            for (HocSinh hs : hsInLop) {

                doc.add(new Paragraph("Học sinh " + hs.getHoTen() + " (" + hs.getMaHS() + ")", defaultFont));

                List<Diem> diems = diemDao.getByMaHS(hs.getMaHS());

                if (!diems.isEmpty()) {

                    PdfPTable table = new PdfPTable(2);

                    table.addCell(new PdfPCell(new Paragraph("Môn", defaultFont)));

                    table.addCell(new PdfPCell(new Paragraph("Điểm TB", defaultFont)));

                    for (Diem d : diems) {

                        DataObject.Mon m = new MonHocDAO().findByMaMon(d.getMaMon());

                        table.addCell(new PdfPCell(new Paragraph(m == null ? "<khong>" : m.getTenMon(), defaultFont)));

                        table.addCell(new PdfPCell(new Paragraph(String.valueOf(d.getDiemTBMonHocKy()), defaultFont)));

                    }

                    doc.add(table);

                }

                List<HanhKiem> hks = hkDao.getByMaHS(hs.getMaHS());

                for (HanhKiem hk : hks) {

                    doc.add(new Paragraph(
                            "Hanh kiem " +
                                    hk.getXepLoai() +
                                    " " +
                                    hk.getNhanXet(), defaultFont
                    ));
                }

                List<DataObject.PhuHuynhHocSinh> phhsList = phhsBLL.layTheoHS(hs.getMaHS());

                for (DataObject.PhuHuynhHocSinh phhs : phhsList) {

                    DataObject.Parent p = phDao.getById(phhs.getMaPH());

                    if (p != null) {

                        doc.add(new Paragraph("Phu huynh: " + p.getTenPhH() + " (" + p.getMaPhH() + "), Quan he: " + phhs.getQuanHe(), defaultFont));

                    }
                }
            }

            doc.add(Chunk.NEWLINE);
        }

        // DANH SACH GIAO VIEN VA BO MON
        doc.add(new Paragraph("DANH SACH GIAO VIEN VA BO MON", defaultFont));

        for (GiaoVien gv : teachers) {

            doc.add(new Paragraph("Giao vien " + gv.getHoTen() + " (" + gv.getMaGV() + ")", defaultFont));

            // filter list of assignments manually since DAL lacks convenience method
            List<PhanCong> allPcs = pcDao.getAll();
            List<PhanCong> pcs = allPcs.stream()
                    .filter(pc -> gv.getMaGV().equals(pc.getMaGV()))
                    .collect(Collectors.toList());

            for (PhanCong pc : pcs) {

                DataObject.Mon m = new MonHocDAO().findByMaMon(pc.getMaMon());

                doc.add(new Paragraph(
                        "Bo mon " +
                                (m == null ? "<khong>" : m.getTenMon()), defaultFont
                ));
            }

            doc.add(Chunk.NEWLINE);
        }

        // THEO NAM
        NamHocBLL namBLL = new NamHocBLL();

        List<DataObject.NamHoc> years = namBLL.getAllActive();

        for (DataObject.NamHoc nh : years) {

            doc.add(new Paragraph("Nam hoc: " + nh.getTenNH(), defaultFont));

        }

        doc.close();
    }
}