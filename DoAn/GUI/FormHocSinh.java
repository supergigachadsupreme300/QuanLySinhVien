package GUI;

import BusinessLogicLayer.HocSinhBLL;
import DataObject.HocSinh;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
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

    // panel hiển thị chi tiết học sinh (sử dụng student_GUI có sẵn)
    private JPanel pnlStudent;
    private JButton btnCloseStudent;
    private student_GUI studentPanel;

    // form chỉnh sửa/nhập liệu (có thể tái sử dụng cho thêm/sửa)
    private JTextField txtMaHS, txtHoTen, txtNgaySinh, txtGioiTinh, txtDiaChi, txtMaLop;
    private JButton btnThem, btnXoa, btnClear;

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Object filterMaHS;

    public FormHocSinh() {
        initUI();
    }

    /**
     * Load students that belong to a specific parent (maPH) and display them.
     */
    public void loadStudentsByParent(String maPH) {
        modelHocSinh.setRowCount(0);
        BusinessLogicLayer.ParentBLL pbll = new BusinessLogicLayer.ParentBLL();
        java.util.List<DataObject.HocSinh> list = pbll.getStudentsByParent(maPH);
        for (DataObject.HocSinh hs : list) {
            modelHocSinh.addRow(new Object[]{
                    hs.getMaHS(), hs.getHoTen(),
                    hs.getNgaySinh() == null ? "" : hs.getNgaySinh().format(fmt),
                    hs.getGioiTinh(), hs.getDiaChi(), hs.getMaLop()
            });
        }
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
        // vertical: make scroll area taller; horizontal: revert widths to previous values
        tblHocSinh.setPreferredScrollableViewportSize(new Dimension(700, 600));
        tblHocSinh.setFillsViewportHeight(true);
        tblHocSinh.setRowHeight(24);
        tblHocSinh.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblHocSinh.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblHocSinh.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblHocSinh.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblHocSinh.getColumnModel().getColumn(4).setPreferredWidth(200);
        tblHocSinh.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane sp = new JScrollPane(tblHocSinh);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // student detail panel (ẩn) - reuse existing student_GUI
        studentPanel = new student_GUI();
        pnlStudent = new JPanel(new MigLayout("fill", "[grow]", "[][grow]"));
        JPanel pnlHeader = new JPanel(new MigLayout("fill", "[grow][]", "[]"));
        JLabel lblDetailTitle = new JLabel("Thông tin chi tiết");
        lblDetailTitle.setBorder(BorderFactory.createEmptyBorder(4,8,4,4));
        btnCloseStudent = new JButton("X");
        btnCloseStudent.setForeground(Color.WHITE);
        btnCloseStudent.setBackground(new Color(200,50,50));
        btnCloseStudent.setFocusPainted(false);
        pnlHeader.add(lblDetailTitle, "growx");
        pnlHeader.add(btnCloseStudent);
        pnlStudent.add(pnlHeader, "growx, wrap");
        pnlStudent.add(studentPanel, "grow");
        studentPanel.setPreferredSize(new Dimension(380, 320));
        pnlStudent.setVisible(false);
        
        // layout both components side-by-side with split ratio
        JPanel split = new JPanel(new MigLayout("fill", "[65%][35%]", "[grow]"));
        split.add(sp, "grow");
        split.add(pnlStudent, "grow");
        add(split, "grow, wrap");

        btnCloseStudent.addActionListener(e -> {
            pnlStudent.setVisible(false);
            tblHocSinh.clearSelection();
        });

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
        btnClear = createButton("Làm mới", new Color(70, 130, 180));
        pnlBtn.add(btnThem); pnlBtn.add(btnClear);
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
        btnClear.addActionListener(e -> clearForm());
        tblHocSinh.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tblHocSinh.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblHocSinh.getSelectedRow();
                if (row >= 0) {
                    fillStudentPanel(row);
                    // pass current HocSinh to student panel and set listener
                    String ma = modelHocSinh.getValueAt(row, 0).toString();
                    DataObject.HocSinh hs = hocSinhBLL.getByMa(ma);
                    if (hs != null) {
                        studentPanel.setHocSinh(hs);
                        // provide BLL ref to the embedded panel so it can update/delete directly
                        studentPanel.setHocSinhBLL(hocSinhBLL);
                        pnlStudent.setVisible(true);
                    }
                }
            }
        });

        loadTable();
    }

    public void loadTable() {
        modelHocSinh.setRowCount(0);
        for (HocSinh hs : hocSinhBLL.getAllActive()) {
            modelHocSinh.addRow(new Object[]{
                    hs.getMaHS(), hs.getHoTen(),
                    hs.getNgaySinh() == null ? "" : hs.getNgaySinh().format(fmt),
                    hs.getGioiTinh(), hs.getDiaChi(), hs.getMaLop()
            });
        }
    }

    // Called by child panels after updates
    public void refreshTableAfterChange() {
        loadTable();
        pnlStudent.setVisible(false);
    }

    private void searchByName() {
        String key = txtSearchName.getText().trim().toLowerCase();
        if (key.isEmpty()) {
            loadTable();
            return;
        }
        modelHocSinh.setRowCount(0);
        for (HocSinh hs : hocSinhBLL.getAllActive()) {
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
        // dialog chứa các tiêu chí tìm kiếm nâng cao
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Tìm kiếm nâng cao", true);
        dlg.setLayout(new MigLayout("fill", "[][grow]", "[]10[]10[]10[]10[]10[]10[]15[]"));
        JTextField fMa = new JTextField();
        JTextField fHoTen = new JTextField();
        JTextField fMaLop = new JTextField();
        JComboBox<String> fGioiTinh = new JComboBox<>(new String[]{"", "Nam", "Nữ"});
        JTextField fDiaChi = new JTextField();
        JTextField fNgaySinhTu = new JTextField();
        JTextField fNgaySinhDen = new JTextField();
        JButton btnOk = new JButton("Tìm");
        JButton btnCancel = new JButton("Hủy");
        JButton btnReset = new JButton("Đặt lại");

        dlg.add(new JLabel("Mã HS:")); dlg.add(fMa, "growx, wrap");
        dlg.add(new JLabel("Họ tên chứa:")); dlg.add(fHoTen, "growx, wrap");
        dlg.add(new JLabel("Mã lớp:")); dlg.add(fMaLop, "growx, wrap");
        dlg.add(new JLabel("Giới tính:")); dlg.add(fGioiTinh, "growx, wrap");
        dlg.add(new JLabel("Địa chỉ chứa:")); dlg.add(fDiaChi, "growx, wrap");
        dlg.add(new JLabel("Ngày sinh từ (yyyy-MM-dd):")); dlg.add(fNgaySinhTu, "growx, wrap");
        dlg.add(new JLabel("Ngày sinh đến (yyyy-MM-dd):")); dlg.add(fNgaySinhDen, "growx, wrap");
        dlg.add(btnOk, "split 3"); dlg.add(btnReset); dlg.add(btnCancel, "wrap");

        btnOk.addActionListener(ev -> {
            // lọc theo các điều kiện nâng cao
            modelHocSinh.setRowCount(0);
            for (HocSinh hs : hocSinhBLL.getAllActive()) {
                if (!fMa.getText().trim().isEmpty() && !hs.getMaHS().equals(fMa.getText().trim())) continue;
                if (!fHoTen.getText().trim().isEmpty() && !hs.getHoTen().toLowerCase()
                        .contains(fHoTen.getText().trim().toLowerCase())) continue;
                if (!fMaLop.getText().trim().isEmpty() && !hs.getMaLop().equals(fMaLop.getText().trim())) continue;
                if (!fGioiTinh.getSelectedItem().toString().isEmpty() && !hs.getGioiTinh().equals(fGioiTinh.getSelectedItem().toString())) continue;
                if (!fDiaChi.getText().trim().isEmpty() && !hs.getDiaChi().toLowerCase()
                        .contains(fDiaChi.getText().trim().toLowerCase())) continue;
                // Kiểm tra ngày sinh
                if (hs.getNgaySinh() != null) {
                    if (!fNgaySinhTu.getText().trim().isEmpty()) {
                        try {
                            LocalDate tu = LocalDate.parse(fNgaySinhTu.getText().trim(), fmt);
                            if (hs.getNgaySinh().isBefore(tu)) continue;
                        } catch (Exception e) { /* ignore */ }
                    }
                    if (!fNgaySinhDen.getText().trim().isEmpty()) {
                        try {
                            LocalDate den = LocalDate.parse(fNgaySinhDen.getText().trim(), fmt);
                            if (hs.getNgaySinh().isAfter(den)) continue;
                        } catch (Exception e) { /* ignore */ }
                    }
                } else {
                    if (!fNgaySinhTu.getText().trim().isEmpty() || !fNgaySinhDen.getText().trim().isEmpty()) continue;
                }
                modelHocSinh.addRow(new Object[]{
                        hs.getMaHS(), hs.getHoTen(),
                        hs.getNgaySinh() == null ? "" : hs.getNgaySinh().format(fmt),
                        hs.getGioiTinh(), hs.getDiaChi(), hs.getMaLop()
                });
            }
            dlg.dispose();
        });
        btnReset.addActionListener(ev -> {
            fMa.setText("");
            fHoTen.setText("");
            fMaLop.setText("");
            fGioiTinh.setSelectedIndex(0);
            fDiaChi.setText("");
            fNgaySinhTu.setText("");
            fNgaySinhDen.setText("");
        });
        btnCancel.addActionListener(ev -> dlg.dispose());
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    private void fillStudentPanel(int row) {
        String ma = modelHocSinh.getValueAt(row, 0).toString();
        DataObject.HocSinh hs = hocSinhBLL.getByMa(ma);
        if (hs != null) studentPanel.setHocSinh(hs);
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
        loadTable();
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
            // Refresh FormLop if present to update sĩ số
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                MainMenu mm = (MainMenu) w;
                for (Component comp : mm.getContentPane().getComponents()) {
                    if (comp instanceof JPanel) {
                        for (Component c : ((JPanel) comp).getComponents()) {
                            if (c.getClass().getSimpleName().equals("FormLop")) {
                                try {
                                    java.lang.reflect.Method m = c.getClass().getMethod("refreshTableAfterChange");
                                    m.invoke(c);
                                } catch (Exception ex) {
                                    // ignore: best-effort refresh
                                }
                            }
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
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

    public void resetFilter() {
        this.filterMaHS = null;
        loadTable();
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
