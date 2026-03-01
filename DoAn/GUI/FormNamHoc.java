package GUI;

import BusinessLogicLayer.NamHocBLL;
import DataObject.NamHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import net.miginfocom.swing.MigLayout;

public class FormNamHoc extends JPanel {

    private MainMenu mainFrame;
    private NamHocBLL nhBLL;

    private boolean dataChanged = false;
    private List<Change> bufferChanges = new ArrayList<>();

    private String originalMaNH = null;

    private JTextField txtMaNH, txtTenNH;
    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;
    private JTable tblNamHoc;
    private DefaultTableModel modelNamHoc;

    public FormNamHoc(MainMenu frame) {
        this.mainFrame = frame;
        this.nhBLL = new NamHocBLL();
        initUI();
        loadTableNamHoc();
        updateButtonState();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ NĂM HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlForm = new JPanel(new MigLayout("insets 15", "[]15[grow]", "[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin năm học"));

        txtMaNH = new JTextField();
        txtTenNH = new JTextField();

        pnlForm.add(new JLabel("Mã năm học:"));
        pnlForm.add(txtMaNH, "growx, wrap");

        pnlForm.add(new JLabel("Tên năm học:"));
        pnlForm.add(txtTenNH, "growx");

        add(pnlForm, "growx, wrap");

        JPanel pnlBtn = new JPanel();

        btnThem = createButton("Thêm", new Color(34, 139, 34));
        btnSua = createButton("Sửa", new Color(255, 140, 0));
        btnXoa = createButton("Xóa", new Color(220, 20, 60));
        btnClear = createButton("Làm mới", new Color(70, 130, 180));
        btnLuu = createButton("Lưu", new Color(150, 150, 150));
        btnLuu.setEnabled(false);

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
        pnlBtn.add(btnLuu);

        add(pnlBtn, "growx, wrap");

        modelNamHoc = new DefaultTableModel(
                new String[]{"Mã năm học", "Tên năm học", "Trạng thái"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblNamHoc = new JTable(modelNamHoc);
        styleTable(tblNamHoc);

        JScrollPane sp = new JScrollPane(tblNamHoc);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách năm học"));
        add(sp, "grow");

        btnThem.addActionListener(e -> themNamHoc());
        btnSua.addActionListener(e -> suaNamHoc());
        btnXoa.addActionListener(e -> xoaNamHoc());
        btnClear.addActionListener(e -> clearForm());
        btnLuu.addActionListener(e -> luuNamHoc());

        tblNamHoc.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblNamHoc.getSelectedRow();
            if (row >= 0) {
                fillFormFromTable(row);
                updateButtonState();
            }
        });

        addFocusEffect(txtMaNH);
        addFocusEffect(txtTenNH);
    }

    private boolean validateForm() {
        if (txtMaNH.getText().trim().isEmpty()
                || txtTenNH.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isDuplicateMa(String ma) {
        for (int i = 0; i < modelNamHoc.getRowCount(); i++) {
            if (modelNamHoc.getValueAt(i, 0).equals(ma)) {
                return true;
            }
        }
        return false;
    }

    private void themNamHoc() {
        if (!validateForm()) return;

        String ma = txtMaNH.getText().trim();

        if (isDuplicateMa(ma)) {
            JOptionPane.showMessageDialog(this, "Mã năm học đã tồn tại!");
            return;
        }

        NamHoc nh = new NamHoc(ma, txtTenNH.getText().trim(), 1);

        modelNamHoc.addRow(new Object[]{
                nh.getMaNH(),
                nh.getTenNH(),
                "Hoạt động"
        });

        removePendingChange(ma);
        bufferChanges.add(new Change(nh, "ADD"));
        dataChanged = true;
        updateSaveButtonState();
        resetInputForm();

        JOptionPane.showMessageDialog(this,
                "Đã thêm năm học! Nhấn 'Lưu' để lưu vào CSDL.");
    }

    private void suaNamHoc() {
        int row = tblNamHoc.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm học cần sửa!");
            return;
        }

        if (!validateForm()) return;

        NamHoc nh = new NamHoc(originalMaNH,
                txtTenNH.getText().trim(), 1);

        modelNamHoc.setValueAt(nh.getTenNH(), row, 1);

        handleUpdateBuffer(nh);
        dataChanged = true;
        updateSaveButtonState();
        resetInputForm();

        JOptionPane.showMessageDialog(this,
                "Đã sửa năm học! Nhấn 'Lưu' để lưu vào CSDL.");
    }

    private void xoaNamHoc() {
        int row = tblNamHoc.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn năm học cần xóa!");
            return;
        }

        String maNH = modelNamHoc.getValueAt(row, 0).toString();
        modelNamHoc.removeRow(row);

        handleDeleteBuffer(maNH);

        dataChanged = true;
        updateSaveButtonState();
        resetInputForm();

        JOptionPane.showMessageDialog(this,
                "Đã xóa năm học! Nhấn 'Lưu' để lưu vào CSDL.");
    }

    private void luuNamHoc() {
        try {
            for (Change change : bufferChanges) {
                switch (change.action) {
                    case "ADD":
                        nhBLL.themNamHoc(change.nh);
                        break;
                    case "UPDATE":
                        nhBLL.suaNamHoc(change.nh);
                        break;
                    case "DELETE":
                        nhBLL.xoaNamHoc(change.nh.getMaNH());
                        break;
                }
            }

            bufferChanges.clear();
            dataChanged = false;
            updateSaveButtonState();
            loadTableNamHoc();
            resetInputForm();

            JOptionPane.showMessageDialog(this, "Đã lưu thành công!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTableNamHoc() {
        modelNamHoc.setRowCount(0);
        for (NamHoc nh : nhBLL.getAllActive()) {
            modelNamHoc.addRow(new Object[]{
                    nh.getMaNH(),
                    nh.getTenNH(),
                    "Hoạt động"
            });
        }
    }

    private void fillFormFromTable(int row) {
        originalMaNH = modelNamHoc.getValueAt(row, 0).toString();
        txtMaNH.setText(originalMaNH);
        txtTenNH.setText(modelNamHoc.getValueAt(row, 1).toString());
        txtMaNH.setEditable(false);
    }

    private void resetInputForm() {
        txtMaNH.setText("");
        txtTenNH.setText("");
        txtMaNH.setEditable(true);
        originalMaNH = null;
        tblNamHoc.clearSelection();
        updateButtonState();
    }

    private void clearForm() {
        resetInputForm();
        bufferChanges.clear();
        dataChanged = false;
        updateSaveButtonState();
        loadTableNamHoc();
    }

    private void updateButtonState() {
        boolean selected = tblNamHoc.getSelectedRow() >= 0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
    }

    private void updateSaveButtonState() {
        if (dataChanged) {
            btnLuu.setEnabled(true);
            btnLuu.setBackground(new Color(34, 139, 34));
        } else {
            btnLuu.setEnabled(false);
            btnLuu.setBackground(new Color(150, 150, 150));
        }
    }

    private void removePendingChange(String maNH) {
        bufferChanges.removeIf(c -> c.nh.getMaNH().equals(maNH));
    }

    private void handleUpdateBuffer(NamHoc nh) {
        for (Change c : bufferChanges) {
            if (c.nh.getMaNH().equals(nh.getMaNH())) {
                c.nh = nh;
                return;
            }
        }
        bufferChanges.add(new Change(nh, "UPDATE"));
    }

    private void handleDeleteBuffer(String maNH) {
        for (int i = 0; i < bufferChanges.size(); i++) {
            Change c = bufferChanges.get(i);

            if (c.nh.getMaNH().equals(maNH)) {

                if (c.action.equals("ADD")) {
                    bufferChanges.remove(i);
                    return;
                }

                c.action = "DELETE";
                return;
            }
        }

        NamHoc nh = new NamHoc();
        nh.setMaNH(maNH);
        bufferChanges.add(new Change(nh, "DELETE"));
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 35));
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
            public void focusGained(FocusEvent e) {
                c.setBackground(new Color(230, 240, 255));
            }

            public void focusLost(FocusEvent e) {
                c.setBackground(Color.WHITE);
            }
        });
    }

    private static class Change {
        NamHoc nh;
        String action;

        Change(NamHoc nh, String action) {
            this.nh = nh;
            this.action = action;
        }
    }
}