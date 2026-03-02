package GUI;

import BusinessLogicLayer.GiaoVienBLL; // Giả định bạn sẽ tạo BLL tương tự
import DataObject.GiaoVien; // Giả định entity GiaoVien
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import net.miginfocom.swing.MigLayout;

/**
 * FORM QUẢN LÝ GIÁO VIÊN
 */
public class FormGiaoVien extends JPanel {

    private final GiaoVienBLL giaoVienBLL = new GiaoVienBLL(); // Tạo BLL tương tự ChiTietMonBLL

    /* ================= TABLE ================= */
    private JTable tblGiaoVien;
    private DefaultTableModel modelGiaoVien;

    /* ================= FORM ================= */
    private JTextField txtMaGV, txtHoTen, txtSDT, txtEmail;

    /* ================= BUTTON ================= */
    private JButton btnThem, btnSua, btnXoa, btnClear;

    public FormGiaoVien() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));

        // Tiêu đề
        JLabel lblTitle = new JLabel("QUẢN LÝ GIÁO VIÊN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        // Panel form nhập liệu
        JPanel pnlForm = new JPanel(new MigLayout(
                "insets 15",
                "[]15[grow]30[]15[grow]",
                "[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin giáo viên"));

        txtMaGV = new JTextField();
        txtHoTen = new JTextField();
        txtSDT = new JTextField();
        txtEmail = new JTextField();

        pnlForm.add(new JLabel("Mã giáo viên:"));
        pnlForm.add(txtMaGV, "growx");
        pnlForm.add(new JLabel("Họ tên:"));
        pnlForm.add(txtHoTen, "growx, wrap");

        pnlForm.add(new JLabel("Số điện thoại:"));
        pnlForm.add(txtSDT, "growx");
        pnlForm.add(new JLabel("Email:"));
        pnlForm.add(txtEmail, "growx");

        add(pnlForm, "growx, wrap");

        // Panel nút chức năng
        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34, 139, 34)); // xanh lá
        btnSua = createButton("Sửa", new Color(0, 150, 136)); // xanh ngọc
        btnXoa = createButton("Xóa", new Color(220, 20, 60)); // đỏ
        btnClear = createButton("Làm mới", new Color(70, 130, 180)); // xanh dương

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
        add(pnlBtn, "growx, wrap");

        // Table hiển thị danh sách
        modelGiaoVien = new DefaultTableModel(
                new String[] { "Mã GV", "Họ tên", "SĐT", "Email" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblGiaoVien = new JTable(modelGiaoVien);
        styleTable(tblGiaoVien);
        tblGiaoVien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblGiaoVien.setPreferredScrollableViewportSize(new Dimension(600, 300));
        tblGiaoVien.setFillsViewportHeight(true);

        tblGiaoVien.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblGiaoVien.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblGiaoVien.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblGiaoVien.getColumnModel().getColumn(3).setPreferredWidth(250);

        JScrollPane spGiaoVien = new JScrollPane(tblGiaoVien);
        spGiaoVien.setBorder(BorderFactory.createTitledBorder("Danh sách giáo viên"));
        spGiaoVien.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(spGiaoVien, "grow");

        // Sự kiện
        btnThem.addActionListener(e -> themGiaoVien());
        btnSua.addActionListener(e -> suaGiaoVien());
        btnXoa.addActionListener(e -> xoaGiaoVien());
        btnClear.addActionListener(e -> clearForm());

        tblGiaoVien.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblGiaoVien.getSelectedRow();
                if (row >= 0) {
                    fillFormFromTable(row);
                    updateButtonState();
                }
            }
        });

        // Hiệu ứng focus
        addFocusEffect(txtMaGV);
        addFocusEffect(txtHoTen);
        addFocusEffect(txtSDT);
        addFocusEffect(txtEmail);

        updateButtonState();
        loadTableGiaoVien();
    }

    // Validate form nhập liệu (có thể mở rộng thêm)
    private boolean validateForm() {
        String maGV = txtMaGV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String email = txtEmail.getText().trim();

        if (maGV.isEmpty() || hoTen.isEmpty() || sdt.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thiếu dữ liệu",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validate email cơ bản
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(this, "Email không hợp lệ!", "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return false;
        }

        // Validate số điện thoại (10-11 số)
        if (!sdt.matches("^\\d{10,11}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại phải là 10-11 chữ số!", "Lỗi định dạng",
                    JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }

        return true;
    }

    // ================= CRUD =================

    private void themGiaoVien() {
        if (!validateForm())
            return;

        GiaoVien gv = getEntityFromForm();

        if (giaoVienBLL.themGiaoVien(gv)) {
            JOptionPane.showMessageDialog(this, "Thêm giáo viên thành công!", "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);
            loadTableGiaoVien();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!\nCó thể mã giáo viên đã tồn tại.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaGiaoVien() {
        int row = tblGiaoVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!", "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateForm())
            return;

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn sửa thông tin này?", "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        GiaoVien gv = getEntityFromForm();

        if (giaoVienBLL.suaGiaoVien(gv)) {
            JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableGiaoVien();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaGiaoVien() {
        int row = tblGiaoVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maGV = modelGiaoVien.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa giáo viên này?\nHành động không thể hoàn tác.", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION)
            return;

        if (giaoVienBLL.xoaGiaoVien(maGV)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableGiaoVien();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại!\nCó thể giáo viên đang được sử dụng ở nơi khác.", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================= Helper =================

    private GiaoVien getEntityFromForm() {
        GiaoVien gv = new GiaoVien();
        gv.setMaGV(txtMaGV.getText().trim());
        gv.setHoTen(txtHoTen.getText().trim());
        gv.setSdt(txtSDT.getText().trim());
        gv.setEmail(txtEmail.getText().trim());
        return gv;
    }

    private void fillFormFromTable(int row) {
        txtMaGV.setText(modelGiaoVien.getValueAt(row, 0).toString());
        txtHoTen.setText(modelGiaoVien.getValueAt(row, 1).toString());
        txtSDT.setText(modelGiaoVien.getValueAt(row, 2).toString());
        txtEmail.setText(modelGiaoVien.getValueAt(row, 3).toString());

        txtMaGV.setEnabled(false); // Không cho sửa mã
    }

    private void clearForm() {
        txtMaGV.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtMaGV.setEnabled(true);
        tblGiaoVien.clearSelection();
        updateButtonState();
        txtMaGV.requestFocus();
    }

    private void updateButtonState() {
        boolean selected = tblGiaoVien.getSelectedRow() >= 0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
    }

    private void loadTableGiaoVien() {
        modelGiaoVien.setRowCount(0);
        List<GiaoVien> list = giaoVienBLL.getAll();
        for (GiaoVien gv : list) {
            modelGiaoVien.addRow(new Object[] {
                    gv.getMaGV(),
                    gv.getHoTen(),
                    gv.getSdt(),
                    gv.getEmail()
            });
        }
    }

    // ================= UI Utils =================

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setOpaque(true);

        // Hover effect
        Color hoverColor = bg.brighter();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }

    private void styleTable(JTable tbl) {
        tbl.setRowHeight(25);
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0, 102, 204));
        tbl.getTableHeader().setForeground(Color.WHITE);
    }

    private void addFocusEffect(JComponent c) {
        c.setOpaque(true);
        c.setBackground(Color.WHITE);
        c.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                c.setBackground(new Color(230, 240, 255));
            }

            @Override
            public void focusLost(FocusEvent e) {
                c.setBackground(Color.WHITE);
            }
        });
    }

    // Test nhanh
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý Giáo viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new FormGiaoVien());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
