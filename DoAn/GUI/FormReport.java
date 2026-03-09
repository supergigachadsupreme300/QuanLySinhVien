package GUI;

import DAO.*;
import DataObject.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
        setBorder(BorderFactory.createTitledBorder("Bao cao he thong"));

        JLabel lblTitle = new JLabel("QUAN LY BAO CAO", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlOptions = new JPanel(new MigLayout("insets 10", "[]15[grow]", "[]10[]"));
        pnlOptions.setBorder(BorderFactory.createTitledBorder("Tuy chon bao cao"));

        pnlOptions.add(new JLabel("Loai bao cao:"));
        cboReportType = new JComboBox<>(new String[]{"Tom tat", "Chi tiet", "Theo lop", "Theo nam"});
        pnlOptions.add(cboReportType, "growx, wrap");

        pnlOptions.add(new JLabel("Ten file:"));
        txtFileName = new JTextField("report.pdf");
        pnlOptions.add(txtFileName, "growx");

        add(pnlOptions, "growx, wrap");

        JPanel pnlButtons = new JPanel();
        btnExport = createStyledButton("Xuat PDF", new Color(34, 139, 34));
        pnlButtons.add(btnExport);

        add(pnlButtons, "growx, wrap");

        lblStatus = new JLabel("San sang xuat bao cao.", JLabel.CENTER);
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

        com.itextpdf.text.Font titleFont =
                new com.itextpdf.text.Font(
                        com.itextpdf.text.Font.FontFamily.HELVETICA,
                        18,
                        com.itextpdf.text.Font.BOLD
                );

        doc.add(new Paragraph("BAO CAO HE THONG QUAN LY HOC SINH", titleFont));
        doc.add(new Paragraph("Loai bao cao: " + reportType));
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

        List<GiaoVien> teachers = gvDao.getAll();
        List<HocSinh> students = hsDao.getAll();
        List<Lop> classes = lopDao.getAll();

        if ("Tom tat".equals(reportType)) {

            doc.add(new Paragraph("TONG QUAN"));
            doc.add(new Paragraph("Tong so giao vien " + teachers.size()));
            doc.add(new Paragraph("Tong so hoc sinh " + students.size()));
            doc.add(new Paragraph("Tong so lop " + classes.size()));

        } else if ("Chi tiet".equals(reportType)) {

            doc.add(new Paragraph("CHI TIET THEO LOP"));

            for (Lop lop : classes) {

                doc.add(new Paragraph("Lop " + lop.getTenLop() + " (" + lop.getMaLop() + ")"));

                GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());

                doc.add(new Paragraph("GVCN " + (gv == null ? "<khong>" : gv.getHoTen())));

                List<HocSinh> hsInLop =
                        students.stream()
                                .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                                .collect(Collectors.toList());

                doc.add(new Paragraph("Si so " + hsInLop.size()));
                doc.add(Chunk.NEWLINE);
            }

        } else if ("Theo lop".equals(reportType)) {

            for (Lop lop : classes) {

                doc.add(new Paragraph("Lop " + lop.getTenLop() + " (" + lop.getMaLop() + ")"));

                GiaoVien gv = gvDao.getByMa(lop.getMaGVCN());
                doc.add(new Paragraph("GVCN " + (gv == null ? "<khong>" : gv.getHoTen())));

                List<HocSinh> hsInLop =
                        students.stream()
                                .filter(h -> lop.getMaLop().equals(h.getMaLop()))
                                .collect(Collectors.toList());

                doc.add(new Paragraph("Si so " + hsInLop.size()));

                for (HocSinh hs : hsInLop) {

                    doc.add(new Paragraph("Hoc sinh " + hs.getHoTen() + " (" + hs.getMaHS() + ")"));

                    List<Diem> diems = diemDao.getByMaHS(hs.getMaHS());

                    for (Diem d : diems) {

                        DataObject.Mon m = new MonHocDAO().findByMaMon(d.getMaMon());

                        // use average score since Diem does not expose a generic getDiem()
                        double score = d.getDiemTBMonHocKy();

                        doc.add(new Paragraph(
                                "Diem mon " +
                                        (m == null ? "<khong>" : m.getTenMon()) +
                                        " " +
                                        score
                        ));
                    }

                    List<HanhKiem> hks = hkDao.getByMaHS(hs.getMaHS());

                    for (HanhKiem hk : hks) {

                        doc.add(new Paragraph(
                                "Hanh kiem " +
                                        hk.getXepLoai() +
                                        " " +
                                        hk.getNhanXet()
                        ));
                    }
                }

                doc.add(Chunk.NEWLINE);
            }

            doc.add(new Paragraph("DANH SACH GIAO VIEN VA BO MON"));

            for (GiaoVien gv : teachers) {

                doc.add(new Paragraph("Giao vien " + gv.getHoTen() + " (" + gv.getMaGV() + ")"));

                // filter list of assignments manually since DAL lacks convenience method
                List<PhanCong> allPcs = pcDao.getAll();
                List<PhanCong> pcs = allPcs.stream()
                        .filter(pc -> gv.getMaGV().equals(pc.getMaGV()))
                        .collect(Collectors.toList());

                for (PhanCong pc : pcs) {

                    DataObject.Mon m = new MonHocDAO().findByMaMon(pc.getMaMon());

                    doc.add(new Paragraph(
                            "Bo mon " +
                                    (m == null ? "<khong>" : m.getTenMon())
                    ));
                }

                doc.add(Chunk.NEWLINE);
            }

        } else if ("Theo nam".equals(reportType)) {

            NamHocBLL namBLL = new NamHocBLL();

            List<DataObject.NamHoc> years = namBLL.getAllActive();

            for (DataObject.NamHoc nh : years) {

                doc.add(new Paragraph("Nam hoc: " + nh.getTenNH()));

            }
        }

        doc.close();
    }
}