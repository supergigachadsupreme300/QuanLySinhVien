package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import DAO.DatabaseConnect;
import BusinessLogicLayer.DiemBLL;
import BusinessLogicLayer.HocSinhBLL;
import BusinessLogicLayer.HocKyBLL;
import DataObject.Diem;
import DataObject.HocSinh;
import DataObject.HocKy;
import net.miginfocom.swing.MigLayout;

public class FormDiem extends JPanel {

    private JTextField txtMaHS, txtTenHS, txtLop, txtMon, txtDiemTX, txtDiemGK, txtDiemCK;
    private JComboBox<String> cboHocKy;
    private JTable table;
    private DefaultTableModel model;
    private DatabaseConnect db;
    private Connection con;
    private DiemBLL diemBLL;
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    private HocKyBLL hocKyBLL = new HocKyBLL();
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
        // Load hoc ky combo and all active students' diem on open
        loadHocKyCombo();
        loadAllActiveDiem();
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

        panel.add(new JLabel("Học kỳ:"));
        cboHocKy = new JComboBox<>();
        panel.add(cboHocKy, "growx, wrap");

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

        // Khi chọn 1 hàng trong bảng, hiển thị thông tin tương ứng lên form nhập
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    String maHS = (model.getValueAt(row, 0) != null) ? model.getValueAt(row, 0).toString() : "";
                    String ten = (model.getValueAt(row, 1) != null) ? model.getValueAt(row, 1).toString() : "";
                    String lop = (model.getValueAt(row, 2) != null) ? model.getValueAt(row, 2).toString() : "";
                    String mon = (model.getValueAt(row, 3) != null) ? model.getValueAt(row, 3).toString() : "";
                    String tx = (model.getValueAt(row, 4) != null) ? model.getValueAt(row, 4).toString() : "";
                    String gk = (model.getValueAt(row, 5) != null) ? model.getValueAt(row, 5).toString() : "";
                    String ck = (model.getValueAt(row, 6) != null) ? model.getValueAt(row, 6).toString() : "";

                    txtMaHS.setText(maHS);
                    txtTenHS.setText(ten);
                    txtLop.setText(lop);
                    txtMon.setText(mon);
                    txtDiemTX.setText(tx);
                    txtDiemGK.setText(gk);
                    txtDiemCK.setText(ck);
                }
            }
        });

        return new JScrollPane(table);
    }

    private void loadHocKyCombo() {
        cboHocKy.removeAllItems();
        java.util.List<HocKy> list = hocKyBLL.getAllActive();
        if (list == null) return;
        for (HocKy hk : list) {
            cboHocKy.addItem(hk.getMaHK());
        }
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new MigLayout("center", "[]15[]15[]15[]15[]", "[]"));

        JButton btnThem = createButton("Thêm");
        JButton btnSua = createButton("Sửa");
        JButton btnXoa = createButton("Xóa");
        JButton btnLuu = createButton("Lưu");
        JButton btnClear = createButton("Làm mới");

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnLuu);
        panel.add(btnClear);

        // Events
        btnThem.addActionListener(e -> themDiem());
        btnSua.addActionListener(e -> suaDiem());
        btnXoa.addActionListener(e -> xoaDiem());
        btnLuu.addActionListener(e -> luuDiem());
        btnClear.addActionListener(e -> { clearForm(); loadAllActiveDiem(); });

        return panel;
    }

    private void loadAllActiveDiem() {
        model.setRowCount(0);
        java.util.List<HocSinh> students = hocSinhBLL.getAllActive();
        if (students == null) return;
        for (HocSinh hs : students) {
            java.util.List<Diem> ds = diemBLL.getByMaHS(hs.getMaHS());
            if (ds == null) continue;
            for (Diem d : ds) {
                model.addRow(new Object[]{d.getMaHS(), hs.getHoTen(), hs.getMaLop(), d.getMaChiTiet(), d.getDiemThuongXuyen(), d.getDiemGiuaKy(), d.getDiemCuoiKy(), d.getDiemTBMonHocKy()});
            }
        }
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
        d.setMaDiem("D" + maHS);
        d.setMaHS(maHS);
        d.setMaChiTiet(maMon);
        // use selected hoc ky from combo
        String selectedHK = (cboHocKy.getSelectedItem() != null) ? cboHocKy.getSelectedItem().toString() : "";
        if (selectedHK.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn Học kỳ trước khi nhập điểm.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        d.setMaHocKy(selectedHK);
        d.setDiemThuongXuyen(valTX);
        d.setDiemGiuaKy(valGK);
        d.setDiemCuoiKy(valCK);
        d.setDiemTBMonHocKy(tb);

        String res = diemBLL.them(d);
        JOptionPane.showMessageDialog(this, res);
        loadAllActiveDiem();
    }

    private double parseOrZero(String s) {
        if (s == null || s.trim().isEmpty()) return 0;
        try { return Double.parseDouble(s.trim()); } catch (NumberFormatException ex) { return 0; }
    }

    public void setFilterMaHS(String maHS) { this.filterMaHS = maHS; }

    private Diem getSelectedDiemFromTable() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        String maHS = (model.getValueAt(row, 0) != null) ? model.getValueAt(row, 0).toString() : "";
        String maChiTiet = (model.getValueAt(row, 3) != null) ? model.getValueAt(row, 3).toString() : "";
        if (maHS.isEmpty() || maChiTiet.isEmpty()) return null;
        for (Diem d : diemBLL.getByMaHS(maHS)) {
            if (maChiTiet.equals(d.getMaChiTiet())) return d;
        }
        return null;
    }

    private void suaDiem() {
        Diem sel = getSelectedDiemFromTable();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để sửa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // Cập nhật từ form
        sel.setMaHS(txtMaHS.getText().trim());
        sel.setMaChiTiet(txtMon.getText().trim());
        sel.setDiemThuongXuyen(parseOrZero(txtDiemTX.getText().trim()));
        sel.setDiemGiuaKy(parseOrZero(txtDiemGK.getText().trim()));
        sel.setDiemCuoiKy(parseOrZero(txtDiemCK.getText().trim()));
        sel.setDiemTBMonHocKy((sel.getDiemThuongXuyen()+sel.getDiemGiuaKy()+sel.getDiemCuoiKy())/3.0);
        String res = diemBLL.sua(sel);
        JOptionPane.showMessageDialog(this, res);
        loadByMaHS();
    }

    private void xoaDiem() {
        Diem sel = getSelectedDiemFromTable();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        String res = diemBLL.xoa(sel.getMaDiem());
        JOptionPane.showMessageDialog(this, res);
        loadByMaHS();
    }

    private void luuDiem() {
        // Lưu hành động giống sửa nếu có dòng được chọn, nếu không thì thêm mới
        if (table.getSelectedRow() >= 0) {
            suaDiem();
        } else {
            themDiem();
        }
    }

    private void clearForm() {
        txtMaHS.setText("");
        txtTenHS.setText("");
        txtLop.setText("");
        txtMon.setText("");
        txtDiemTX.setText("");
        txtDiemGK.setText("");
        txtDiemCK.setText("");
        table.clearSelection();
    }
}