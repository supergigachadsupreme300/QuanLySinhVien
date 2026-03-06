package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import DAO.DatabaseConnect;
import BusinessLogicLayer.DiemBLL;
import BusinessLogicLayer.HocSinhBLL;
import DataObject.Diem;
import DataObject.HocSinh;
import net.miginfocom.swing.MigLayout;

public class FormDiem extends JPanel {

    private JTextField txtMaHS, txtTenHS, txtLop, txtMon, txtDiemTX, txtDiemGK, txtDiemCK;
    private JTable table;
    private DefaultTableModel model;
    private DatabaseConnect db;
    private Connection con;
    private DiemBLL diemBLL;
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    private String filterMaHS = null;

    public FormDiem() {
        initDB();
        initUI();
    }

    private void initDB() {
        db = new DatabaseConnect();
        con = db.openConnection();
        diemBLL = new DiemBLL(con);
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 10", "[grow]", "[][grow][]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ ĐIỂM HỌC SINH", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0,102,204));
        add(lblTitle, "dock north, wrap");

        add(createMainPanel(), "grow, wrap");
        add(createButtonPanel(), "dock south");
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("fill, insets 0", "[300!][grow]", "[grow]"));

        panel.add(createInputPanel(), "grow");
        panel.add(createTablePanel(), "grow");

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 10", "[right][grow]", "[]15[]15[]15[]15[]"));

        panel.setBorder(BorderFactory.createTitledBorder("Thông tin điểm"));

        panel.add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField();
        panel.add(txtMaHS, "growx, wrap");

        panel.add(new JLabel("Tên HS:"));
        txtTenHS = new JTextField();
        panel.add(txtTenHS, "growx, wrap");

        panel.add(new JLabel("Lớp:"));
        txtLop = new JTextField();
        panel.add(txtLop, "growx, wrap");

        panel.add(new JLabel("Môn học (maChiTiet):"));
        txtMon = new JTextField();
        panel.add(txtMon, "growx, wrap");

        panel.add(new JLabel("Điểm thường xuyên:"));
        txtDiemTX = new JTextField();
        panel.add(txtDiemTX, "growx, wrap");

        panel.add(new JLabel("Điểm giữa kỳ:"));
        txtDiemGK = new JTextField();
        panel.add(txtDiemGK, "growx, wrap");

        panel.add(new JLabel("Điểm cuối kỳ:"));
        txtDiemCK = new JTextField();
        panel.add(txtDiemCK, "growx, wrap");

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {"Mã HS", "Tên HS", "Lớp", "Môn", "TX", "GK", "CK", "TB"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new MigLayout("center", "[]15[]15[]15[]15[]", "[]"));

        JButton btnLoad = createButton("Tải");
        JButton btnThem = createButton("Thêm");
        JButton btnSua = createButton("Sửa");
        JButton btnXoa = createButton("Xóa");
        JButton btnLuu = createButton("Lưu");
        JButton btnClear = createButton("Làm mới");

        panel.add(btnLoad);
        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLuu);
        panel.add(btnClear);

        // Events
        btnLoad.addActionListener(e -> loadByMaHS());
        btnThem.addActionListener(e -> themDiem());

        return panel;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }

    public void loadByMaHS() {
        String maHS = (filterMaHS != null && !filterMaHS.isEmpty()) ? filterMaHS : txtMaHS.getText().trim();
        if (maHS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã HS để tải điểm.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setRowCount(0);
        HocSinh hs = hocSinhBLL.getByMa(maHS);
        String ten = hs != null ? hs.getHoTen() : "";
        String lop = hs != null ? hs.getMaLop() : "";
        for (Diem d : diemBLL.getByMaHS(maHS)) {
            model.addRow(new Object[]{d.getMaHS(), ten, lop, d.getMaChiTiet(), d.getDiemThuongXuyen(), d.getDiemGiuaKy(), d.getDiemCuoiKy(), d.getDiemTBMonHocKy()});
        }
    }

    private void themDiem() {
        String maHS = txtMaHS.getText().trim();
        String maMon = txtMon.getText().trim();
        String tx = txtDiemTX.getText().trim();
        String gk = txtDiemGK.getText().trim();
        String ck = txtDiemCK.getText().trim();
        if (maHS.isEmpty() || maMon.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập Mã HS và Môn.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        double valTX = parseOrZero(tx);
        double valGK = parseOrZero(gk);
        double valCK = parseOrZero(ck);
        double tb = (valTX + valGK + valCK) / ( (valTX>0?1:0) + (valGK>0?1:0) + (valCK>0?1:0) );
        if (Double.isNaN(tb)) tb = 0;

        Diem d = new Diem();
        d.setMaDiem("D_" + maHS + "_" + maMon);
        d.setMaHS(maHS);
        d.setMaChiTiet(maMon);
        d.setMaHocKy("HK1");
        d.setDiemThuongXuyen(valTX);
        d.setDiemGiuaKy(valGK);
        d.setDiemCuoiKy(valCK);
        d.setDiemTBMonHocKy(tb);

        String res = diemBLL.them(d);
        JOptionPane.showMessageDialog(this, res);
        loadByMaHS();
    }

    private double parseOrZero(String s) {
        if (s == null || s.trim().isEmpty()) return 0;
        try { return Double.parseDouble(s.trim()); } catch (NumberFormatException ex) { return 0; }
    }

    public void setFilterMaHS(String maHS) { this.filterMaHS = maHS; }
}