package GUI;

import BusinessLogicLayer.HocSinhBLL;
import DataObject.HocSinh;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FormHocSinh extends JPanel {

    private final HocSinhBLL hocSinhBLL = new HocSinhBLL();

    /* ====== COMPONENTS ====== */
    private JTable tblHocSinh;
    private DefaultTableModel modelHocSinh;

    private JTextField txtSearchName;
    private JButton btnTim, btnNangCao;

    // panel hiển thị chi tiết học sinh (ẩn khi chưa chọn)
    private JPanel pnlStudent;
    private JTextField txtStuMaHS, txtStuHoTen, txtStuNgaySinh, txtStuGioiTinh, txtStuDiaChi, txtStuMaLop;

    // form chỉnh sửa/nhập liệu (có thể tái sử dụng cho thêm/sửa)
    private JTextField txtMaHS, txtHoTen, txtNgaySinh, txtGioiTinh, txtDiaChi, txtMaLop;
    private JButton btnThem, btnSua, btnXoa, btnClear;

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public FormHocSinh() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]15[]15[]"));

        // title
        JLabel lblTitle = new JLabel("QUẢN LÝ HỌC SINH", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        // search panel
        JPanel pnlSearch = new JPanel(new MigLayout("insets 0", "[][grow]10[][]", "[]"));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txtSearchName = new JTextField();
        btnTim = new JButton("Tìm");
        btnNangCao = new JButton("Nâng cao");
        pnlSearch.add(new JLabel("Tên:"));
        pnlSearch.add(txtSearchName, "growx");
        pnlSearch.add(btnTim);
        pnlSearch.add(btnNangCao);
        add(pnlSearch, "growx, wrap");

        // table model
        modelHocSinh = new DefaultTableModel(
                new String[]{"Mã HS", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ", "Mã lớp"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHocSinh = new JTable(modelHocSinh);
        styleTable(tblHocSinh);
        tblHocSinh.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblHocSinh.setPreferredScrollableViewportSize(new Dimension(700, 300));
        tblHocSinh.setFillsViewportHeight(true);
        tblHocSinh.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblHocSinh.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblHocSinh.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblHocSinh.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblHocSinh.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblHocSinh.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane sp = new JScrollPane(tblHocSinh);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp, "grow, wrap");

        // student detail panel (ẩn)
        pnlStudent = new JPanel(new MigLayout("insets 10", "[][grow]", "[][][][][][]"));
        pnlStudent.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết"));
        pnlStudent.setVisible(false);
        txtStuMaHS = new JTextField();
        txtStuHoTen = new JTextField();
        txtStuNgaySinh = new JTextField();
        txtStuGioiTinh = new JTextField();
        txtStuDiaChi = new JTextField();
        txtStuMaLop = new JTextField();
        txtStuMaHS.setEditable(false);
        pnlStudent.add(new JLabel("Mã HS:")); pnlStudent.add(txtStuMaHS, "growx, wrap");
        pnlStudent.add(new JLabel("Họ tên:")); pnlStudent.add(txtStuHoTen, "growx, wrap");
        pnlStudent.add(new JLabel("Ngày sinh:")); pnlStudent.add(txtStuNgaySinh, "growx, wrap");
        pnlStudent.add(new JLabel("Giới tính:")); pnlStudent.add(txtStuGioiTinh, "growx, wrap");
        pnlStudent.add(new JLabel("Địa chỉ:")); pnlStudent.add(txtStuDiaChi, "growx, wrap");
        pnlStudent.add(new JLabel("Mã lớp:")); pnlStudent.add(txtStuMaLop, "growx, wrap");
        add(pnlStudent, "growx, wrap");

        // input form for add/edit
        JPanel pnlForm = new JPanel(new MigLayout("insets 15", "[]15[grow]30[]15[grow]", "[]10[]10[]10[]10[]10"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Nhập / sửa thông tin"));
        txtMaHS = new JTextField();
        txtHoTen = new JTextField();
        txtNgaySinh = new JTextField();
        txtGioiTinh = new JTextField();
        txtDiaChi = new JTextField();
        txtMaLop = new JTextField();
        pnlForm.add(new JLabel("Mã HS:")); pnlForm.add(txtMaHS, "growx");
        pnlForm.add(new JLabel("Họ tên:")); pnlForm.add(txtHoTen, "growx, wrap");
        pnlForm.add(new JLabel("Ngày sinh (yyyy-MM-dd):")); pnlForm.add(txtNgaySinh, "growx");
        pnlForm.add(new JLabel("Giới tính:")); pnlForm.add(txtGioiTinh, "growx, wrap");
        pnlForm.add(new JLabel("Địa chỉ:")); pnlForm.add(txtDiaChi, "growx");
        pnlForm.add(new JLabel("Mã lớp:")); pnlForm.add(txtMaLop, "growx, wrap");
        add(pnlForm, "growx, wrap");

        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34, 139, 34));
        btnSua = createButton("Sửa", new Color(0, 150, 136));
        btnXoa = createButton("Xóa", new Color(220, 20, 60));
        btnClear = createButton("Làm mới", new Color(70, 130, 180));
        pnlBtn.add(btnThem); pnlBtn.add(btnSua); pnlBtn.add(btnXoa); pnlBtn.add(btnClear);
        add(pnlBtn, "growx, wrap");

        // focus effects
        addFocusEffect(txtSearchName);
        addFocusEffect(txtMaHS);
        addFocusEffect(txtHoTen);
        addFocusEffect(txtNgaySinh);
        addFocusEffect(txtGioiTinh);
        addFocusEffect(txtDiaChi);
        addFocusEffect(txtMaLop);

        // event handlers
        btnTim.addActionListener(e -> searchByName());
        btnNangCao.addActionListener(e -> showAdvancedSearch());
        btnThem.addActionListener(e -> themHocSinh());
        btnSua.addActionListener(e -> suaHocSinh());
        btnXoa.addActionListener(e -> xoaHocSinh());
        btnClear.addActionListener(e -> clearForm());

        tblHocSinh.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblHocSinh.getSelectedRow();
                if (row >= 0) {
                    fillStudentPanel(row);
                    pnlStudent.setVisible(true);
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        modelHocSinh.setRowCount(0);
        for (HocSinh hs : hocSinhBLL.getAll()) {
            modelHocSinh.addRow(new Object[]{
                    hs.getMaHS(), hs.getHoTen(),
                    hs.getNgaySinh() == null ? "" : hs.getNgaySinh().format(fmt),
                    hs.getGioiTinh(), hs.getDiaChi(), hs.getMaLop()
            });
        }
    }

    private void searchByName() {
        String key = txtSearchName.getText().trim().toLowerCase();
        if (key.isEmpty()) {
            loadTable();
            return;
        }
        modelHocSinh.setRowCount(0);
        for (HocSinh hs : hocSinhBLL.getAll()) {
            if (hs.getHoTen().toLowerCase().contains(key)) {
                modelHocSinh.addRow(new Object[]{
                        hs.getMaHS(), hs.getHoTen(),
                        hs.getNgaySinh() == null ? "" : hs.getNgaySinh().format(fmt),
                        hs.getGioiTinh(), hs.getDiaChi(), hs.getMaLop()
                });
            }
        }
    }

    private void showAdvancedSearch() {
        // dialog chứa các tiêu chí tìm kiếm
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Tìm kiếm nâng cao", true);
        dlg.setLayout(new MigLayout("fill", "[][grow]", "[]10[]10[]10[]10[]15[]"));
        JTextField fMa = new JTextField();
        JTextField fHoTen = new JTextField();
        JTextField fMaLop = new JTextField();
        JComboBox<String> fGioiTinh = new JComboBox<>(new String[]{"", "Nam", "Nữ"});
        JButton btnOk = new JButton("Tìm");
        JButton btnCancel = new JButton("Hủy");

        dlg.add(new JLabel("Mã HS:")); dlg.add(fMa, "growx, wrap");
        dlg.add(new JLabel("Họ tên chứa:")); dlg.add(fHoTen, "growx, wrap");
        dlg.add(new JLabel("Mã lớp:")); dlg.add(fMaLop, "growx, wrap");
        dlg.add(new JLabel("Giới tính:")); dlg.add(fGioiTinh, "growx, wrap");
        dlg.add(btnOk, "split 2"); dlg.add(btnCancel, "wrap");

        btnOk.addActionListener(ev -> {
            // lọc theo các điều kiện
            modelHocSinh.setRowCount(0);
            for (HocSinh hs : hocSinhBLL.getAll()) {
                if (!fMa.getText().trim().isEmpty() && !hs.getMaHS().equals(fMa.getText().trim())) continue;
                if (!fHoTen.getText().trim().isEmpty() && !hs.getHoTen().toLowerCase()
                        .contains(fHoTen.getText().trim().toLowerCase())) continue;
                if (!fMaLop.getText().trim().isEmpty() && !hs.getMaLop().equals(fMaLop.getText().trim())) continue;
                if (!fGioiTinh.getSelectedItem().toString().isEmpty() && !hs.getGioiTinh().equals(fGioiTinh.getSelectedItem().toString())) continue;
                modelHocSinh.addRow(new Object[]{
                        hs.getMaHS(), hs.getHoTen(),
                        hs.getNgaySinh() == null ? "" : hs.getNgaySinh().format(fmt),
                        hs.getGioiTinh(), hs.getDiaChi(), hs.getMaLop()
                });
            }
            dlg.dispose();
        });
        btnCancel.addActionListener(ev -> dlg.dispose());
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    private void fillStudentPanel(int row) {
        txtStuMaHS.setText(modelHocSinh.getValueAt(row, 0).toString());
        txtStuHoTen.setText(modelHocSinh.getValueAt(row, 1).toString());
        txtStuNgaySinh.setText(modelHocSinh.getValueAt(row, 2).toString());
        txtStuGioiTinh.setText(modelHocSinh.getValueAt(row, 3).toString());
        txtStuDiaChi.setText(modelHocSinh.getValueAt(row, 4).toString());
        txtStuMaLop.setText(modelHocSinh.getValueAt(row, 5).toString());
    }

    private void clearForm() {
        txtMaHS.setText("");
        txtHoTen.setText("");
        txtNgaySinh.setText("");
        txtGioiTinh.setText("");
        txtDiaChi.setText("");
        txtMaLop.setText("");
        tblHocSinh.clearSelection();
        pnlStudent.setVisible(false);
    }

    private boolean validateForm() {
        if (txtMaHS.getText().trim().isEmpty() ||
                txtHoTen.getText().trim().isEmpty() ||
                txtNgaySinh.getText().trim().isEmpty() ||
                txtGioiTinh.getText().trim().isEmpty() ||
                txtDiaChi.getText().trim().isEmpty() ||
                txtMaLop.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            LocalDate.parse(txtNgaySinh.getText().trim(), fmt);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ (yyyy-MM-dd)",
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            txtNgaySinh.requestFocus();
            return false;
        }
        return true;
    }

    private HocSinh getEntityFromForm() {
        HocSinh hs = new HocSinh();
        hs.setMaHS(txtMaHS.getText().trim());
        hs.setHoTen(txtHoTen.getText().trim());
        hs.setNgaySinh(LocalDate.parse(txtNgaySinh.getText().trim(), fmt));
        hs.setGioiTinh(txtGioiTinh.getText().trim());
        hs.setDiaChi(txtDiaChi.getText().trim());
        hs.setMaLop(txtMaLop.getText().trim());
        return hs;
    }

    private void themHocSinh() {
        if (!validateForm()) return;
        if (hocSinhBLL.themHocSinh(getEntityFromForm())) {
            JOptionPane.showMessageDialog(this, "Thêm học sinh thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaHocSinh() {
        int row = tblHocSinh.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học sinh cần sửa!", "Chưa chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validateForm()) return;
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa học sinh này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        if (hocSinhBLL.suaHocSinh(getEntityFromForm())) {
            JOptionPane.showMessageDialog(this, "Sửa thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaHocSinh() {
        int row = tblHocSinh.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học sinh muốn xóa!", "Chưa chọn", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String ma = modelHocSinh.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa học sinh mã " + ma + "?","Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        if (hocSinhBLL.xoaHocSinh(ma)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /* helper */
    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        return btn;
    }

    private void styleTable(JTable table) {
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void addFocusEffect(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBackground(new Color(255, 255, 204));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBackground(Color.WHITE);
            }
        });
    }
}
