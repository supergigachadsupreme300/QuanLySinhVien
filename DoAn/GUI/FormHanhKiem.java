package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import BusinessLogicLayer.HanhKiemBLL;
import BusinessLogicLayer.HocSinhBLL;
import DAO.DatabaseConnect;
import DataObject.HanhKiem;
import DataObject.HocSinh;
import java.sql.Connection;
import net.miginfocom.swing.MigLayout;

public class FormHanhKiem extends JPanel {

    private JTextField txtMaHS, txtTenHS, txtLop;
    private JComboBox<String> cboHocKy, cboNamHoc, cboXepLoai;
    private JTextArea txtNhanXet;
    private JTable table;
    private DefaultTableModel model;
    private HanhKiemBLL hanhKiemBLL;
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    private DatabaseConnect db;
    private Connection con;
    private String filterMaHS = null;

    public FormHanhKiem() {
        initDB();
        initUI();
    }

    private void initDB() {
        db = new DatabaseConnect();
        con = db.openConnection();
        hanhKiemBLL = new HanhKiemBLL();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 10", "[grow]", "[][grow][]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ HẠNH KIỂM", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0,102,204));
        add(lblTitle, "dock north, wrap");

        add(createMainPanel(), "grow, wrap");
        add(createButtonPanel(), "dock south");
        // load all active hạnh kiểm on open
        loadAllActiveHanhKiem();
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("fill, insets 0", "[350!][grow]", "[grow]"));

        panel.add(createInputPanel(), "growy");
        panel.add(createTablePanel(), "grow");

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 10", "[right][grow]", "[]15[]15[]15[]15[]15[]15[]"));

        panel.setBorder(BorderFactory.createTitledBorder("Thông tin hạnh kiểm"));

        panel.add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField();
        panel.add(txtMaHS, "growx, wrap");

        panel.add(new JLabel("Tên HS:"));
        txtTenHS = new JTextField();
        panel.add(txtTenHS, "growx, wrap");

        panel.add(new JLabel("Lớp:"));
        txtLop = new JTextField();
        panel.add(txtLop, "growx, wrap");

        panel.add(new JLabel("Học kỳ:"));
        cboHocKy = new JComboBox<>(new String[]{"HK1", "HK2"});
        panel.add(cboHocKy, "growx, wrap");

        panel.add(new JLabel("Năm học:"));
        cboNamHoc = new JComboBox<>(new String[]{"2023-2024", "2024-2025", "2025-2026"});
        panel.add(cboNamHoc, "growx, wrap");

        panel.add(new JLabel("Xếp loại:"));
        cboXepLoai = new JComboBox<>(new String[]{"Tốt", "Khá", "Trung bình", "Yếu"});
        panel.add(cboXepLoai, "growx, wrap");

        panel.add(new JLabel("Nhận xét:"));
        txtNhanXet = new JTextArea(3, 20);
        panel.add(new JScrollPane(txtNhanXet), "growx, wrap");

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {"Mã HS", "Tên HS", "Lớp", "HK", "Năm học", "Xếp loại", "Nhận xét"};
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
                    String hk = (model.getValueAt(row, 3) != null) ? model.getValueAt(row, 3).toString() : "";
                    String nam = (model.getValueAt(row, 4) != null) ? model.getValueAt(row, 4).toString() : "";
                    String xep = (model.getValueAt(row, 5) != null) ? model.getValueAt(row, 5).toString() : "";
                    String nhan = (model.getValueAt(row, 6) != null) ? model.getValueAt(row, 6).toString() : "";

                    txtMaHS.setText(maHS);
                    txtTenHS.setText(ten);
                    txtLop.setText(lop);
                    if (!hk.isEmpty()) cboHocKy.setSelectedItem(hk);
                    if (!nam.isEmpty()) cboNamHoc.setSelectedItem(nam);
                    if (!xep.isEmpty()) cboXepLoai.setSelectedItem(xep);
                    txtNhanXet.setText(nhan);
                }
            }
        });

        return new JScrollPane(table);
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

        btnThem.addActionListener(e -> themHanhKiem());
        btnSua.addActionListener(e -> suaHanhKiem());
        btnXoa.addActionListener(e -> xoaHanhKiem());
        btnLuu.addActionListener(e -> luuHanhKiem());
        btnClear.addActionListener(e -> { clearForm(); loadAllActiveHanhKiem(); });

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
            JOptionPane.showMessageDialog(this, "Nhập mã HS để tải hạnh kiểm.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setRowCount(0);
        HocSinh hs = hocSinhBLL.getByMa(maHS);
        String ten = hs != null ? hs.getHoTen() : "";
        String lop = hs != null ? hs.getMaLop() : "";
        java.util.List<HanhKiem> list = hanhKiemBLL.getByMaHS(maHS);
        if (list != null) {
            for (HanhKiem hk : list) {
                model.addRow(new Object[]{hk.getMaHS(), ten, lop, hk.getMaHocKy(), "", hk.getXepLoai(), hk.getNhanXet()});
            }
        }
    }

    private void themHanhKiem() {
        String maHS = txtMaHS.getText().trim();
        String hk = cboHocKy.getSelectedItem().toString();
        String xep = cboXepLoai.getSelectedItem().toString();
        String nhan = txtNhanXet.getText().trim();
        if (maHS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã HS để thêm hạnh kiểm.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        HanhKiem h = new HanhKiem();
        h.setMaHanhKiem("HK_" + maHS + "_" + hk);
        h.setMaHS(maHS);
        h.setMaHocKy(hk);
        h.setXepLoai(xep);
        h.setSoLanViPham(0);
        h.setNhanXet(nhan);

        boolean ok = hanhKiemBLL.add(h);
        if (ok) JOptionPane.showMessageDialog(this, "Thêm hạnh kiểm thành công.");
        else JOptionPane.showMessageDialog(this, "Thêm hạnh kiểm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        loadAllActiveHanhKiem();
    }

    public void setFilterMaHS(String maHS) { this.filterMaHS = maHS; }

    private void loadAllActiveHanhKiem() {
        model.setRowCount(0);
        java.util.List<HocSinh> students = hocSinhBLL.getAllActive();
        if (students == null) return;
        for (HocSinh hs : students) {
            java.util.List<HanhKiem> list = hanhKiemBLL.getByMaHS(hs.getMaHS());
            if (list == null) continue;
            for (HanhKiem hk : list) {
                model.addRow(new Object[]{hk.getMaHS(), hs.getHoTen(), hs.getMaLop(), hk.getMaHocKy(), "", hk.getXepLoai(), hk.getNhanXet()});
            }
        }
    }

    private String buildMaHanhKiem(String maHS, String hk) {
        if (maHS == null || maHS.isEmpty() || hk == null || hk.isEmpty()) return "";
        return "HK_" + maHS + "_" + hk;
    }

    private void suaHanhKiem() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để sửa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String maHS = txtMaHS.getText().trim();
        String hk = cboHocKy.getSelectedItem().toString();
        String xep = cboXepLoai.getSelectedItem().toString();
        String nhan = txtNhanXet.getText().trim();
        HanhKiem h = new HanhKiem();
        h.setMaHanhKiem(buildMaHanhKiem(maHS, hk));
        h.setMaHS(maHS);
        h.setMaHocKy(hk);
        h.setXepLoai(xep);
        h.setNhanXet(nhan);
        h.setSoLanViPham(0);

        boolean ok = hanhKiemBLL.update(h);
        if (ok) JOptionPane.showMessageDialog(this, "Cập nhật hạnh kiểm thành công.");
        else JOptionPane.showMessageDialog(this, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        loadAllActiveHanhKiem();
    }

    private void xoaHanhKiem() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn một dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String maHS = txtMaHS.getText().trim();
        String hk = cboHocKy.getSelectedItem().toString();
        String maHK = buildMaHanhKiem(maHS, hk);
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        boolean ok = hanhKiemBLL.delete(maHK);
        if (ok) JOptionPane.showMessageDialog(this, "Xóa thành công.");
        else JOptionPane.showMessageDialog(this, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        loadAllActiveHanhKiem();
    }

    private void luuHanhKiem() {
        // Nếu có dòng chọn -> cập nhật, ngược lại thêm mới
        if (table.getSelectedRow() >= 0) suaHanhKiem(); else themHanhKiem();
    }

    private void clearForm() {
        txtMaHS.setText("");
        txtTenHS.setText("");
        txtLop.setText("");
        cboHocKy.setSelectedIndex(0);
        cboNamHoc.setSelectedIndex(0);
        cboXepLoai.setSelectedIndex(0);
        txtNhanXet.setText("");
        table.clearSelection();
    }
}