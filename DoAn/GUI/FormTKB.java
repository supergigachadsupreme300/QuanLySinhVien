/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author admin
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import net.miginfocom.swing.MigLayout;


public class FormTKB extends JFrame {

    // ================= TABLE =================
    private JTable tblTKBList, tblTKBLuoi;
    private DefaultTableModel modelTKBList, modelTKBLuoi;

    // ================= FORM ==================
    private JTextField txtMaTKB, txtNgayBD, txtNgayKT;
    private JComboBox<String> cboLop, cboTKB;

    // ================= BUTTON ================
    private JButton btnThem, btnSua, btnXoa, btnClear,btnQuayLai;

    // ================= CONSTRUCTOR =================
    public FormTKB() {
        setTitle("Quản lý thời khóa biểu");
        setSize(1100, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int c = JOptionPane.showConfirmDialog(
                    FormTKB.this,
                    "Bạn có muốn thoát chương trình?",
                    "Thoát",
                    JOptionPane.YES_NO_OPTION
                );
                if (c == JOptionPane.YES_OPTION) dispose();
            }
        });

        initUI();
        initGrid(); // Khởi tạo lưới TKB trống
        updateButtonState();
    }

    // ================= UI =====================
    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[]15[grow]15[grow]"));

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("QUẢN LÝ THỜI KHÓA BIỂU", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        // ===== PANEL THÔNG TIN =====
        JPanel pnlTop = new JPanel(new MigLayout("fill", "[grow][grow]", "[]"));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin TKB"));

        // --- Panel Input (Bên trái) ---
        JPanel pnlInput = new JPanel(new MigLayout("", "[]10[grow]", "[]10[]10[]10[]"));
        
        txtMaTKB = new JTextField();
        txtNgayBD = new JTextField();
        txtNgayKT = new JTextField();
        cboLop = new JComboBox<>(new String[]{"10A1", "10A2", "10A3", "11A1", "11A2"});

        pnlInput.add(new JLabel("Mã TKB:"));
        pnlInput.add(txtMaTKB, "growx, wrap");
        pnlInput.add(new JLabel("Lớp:"));
        pnlInput.add(cboLop, "growx, wrap");
        pnlInput.add(new JLabel("Ngày bắt đầu:"));
        pnlInput.add(txtNgayBD, "growx, wrap");
        pnlInput.add(new JLabel("Ngày kết thúc:"));
        pnlInput.add(txtNgayKT, "growx");

        // --- Panel Select (Bên phải) ---
        JPanel pnlSelect = new JPanel(new MigLayout("", "[]10[grow]", "[]"));
        pnlSelect.setBorder(BorderFactory.createTitledBorder("Chọn TKB"));
        
        cboTKB = new JComboBox<>(new String[]{"TKB01 - 10A1", "TKB02 - 10A2"});
        pnlSelect.add(new JLabel("TKB đã có:"));
        pnlSelect.add(cboTKB, "growx");

        pnlTop.add(pnlInput, "grow");
        pnlTop.add(pnlSelect, "grow");
        add(pnlTop, "growx, wrap");

        // ===== BUTTON =====
        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34, 139, 34));
        btnSua = createButton("Sửa", new Color(255, 140, 0));
        btnXoa = createButton("Xóa", new Color(220, 20, 60));
        btnClear = createButton("Làm mới", new Color(70, 130, 180));
        btnQuayLai = createButton("Quay lại", new Color(100, 100, 100));
        
        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
        pnlBtn.add(btnQuayLai); 
        add(pnlBtn, "growx, wrap");

        // ===== TABLE DANH SÁCH TKB =====
        modelTKBList = new DefaultTableModel(
            new String[]{"Mã TKB", "Lớp", "Ngày bắt đầu", "Ngày kết thúc"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        tblTKBList = new JTable(modelTKBList);
        tblTKBList.setRowHeight(25);
        tblTKBList.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblTKBList.getTableHeader().setBackground(new Color(0, 102, 204));
        tblTKBList.getTableHeader().setForeground(Color.WHITE);
        styleTable(tblTKBList);
        
        JScrollPane scrollList = new JScrollPane(tblTKBList);
        scrollList.setBorder(BorderFactory.createTitledBorder("Danh sách TKB"));
        add(scrollList, "grow, wrap");

        // ===== TABLE LƯỚI TKB (THỜI KHÓA BIỂU) =====
        modelTKBLuoi = new DefaultTableModel(
            new String[]{"Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        tblTKBLuoi = new JTable(modelTKBLuoi);
        tblTKBLuoi.setRowHeight(30);
        tblTKBLuoi.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblTKBLuoi.getTableHeader().setBackground(new Color(0, 102, 204));
        tblTKBLuoi.getTableHeader().setForeground(Color.WHITE);
        //add(new JScrollPane(tblTKBLuoi), "grow");
        styleTable(tblTKBLuoi);
        
        // Set màu cho cột "Tiết"
        tblTKBLuoi.getColumnModel().getColumn(0).setCellRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    if (column == 0) {
                        c.setBackground(new Color(230, 240, 255));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            }
        );

        JScrollPane scrollLuoi = new JScrollPane(tblTKBLuoi);
        scrollLuoi.setBorder(BorderFactory.createTitledBorder("Lưới thời khóa biểu"));
        add(scrollLuoi, "grow, wrap");

        // ===== EVENTS =====
        tblTKBList.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblTKBList.getSelectedRow();
            if (row >= 0) {
                fillFormFromTable(row);
                updateButtonState();
            }
        });

        cboTKB.addActionListener(e -> {
            // TODO: Load TKB tương ứng vào lưới
            JOptionPane.showMessageDialog(this, "Đã chọn: " + cboTKB.getSelectedItem());
        });

        btnThem.addActionListener(e -> themTKB());
        btnSua.addActionListener(e -> suaTKB());
        btnXoa.addActionListener(e -> xoaTKB());
        btnClear.addActionListener(e -> clearForm());
        btnQuayLai.addActionListener(e -> quaylai());

        // ===== THÊM EFFECT =====
        addFocusEffect(txtMaTKB);
        addFocusEffect(txtNgayBD);
        addFocusEffect(txtNgayKT);
        addFocusEffect(cboLop);
        addFocusEffect(cboTKB);
    }

    // ===== TẠO BUTTON VỚI MÀU + HOVER =====
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color originalColor = bgColor;
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(originalColor.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(originalColor);
            }
        });

        return btn;
    }

    // ===== FOCUS EFFECT =====
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

     // ================= TABLE STYLE =================
    private void styleTable(JTable tbl) {
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0, 102, 204));
        tbl.getTableHeader().setForeground(Color.WHITE);
    }
    
    // ================= CRUD ( SAU NÀY NỐI BLL) =================
    private void themTKB() {
        // TODO: Gọi BLL để thêm vào database
        String maTKB = txtMaTKB.getText();
        String lop = cboLop.getSelectedItem().toString();
        String ngayBD = txtNgayBD.getText();
        String ngayKT = txtNgayKT.getText();

        if (maTKB.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã TKB!");
            return;
        }

        // Tạm thời thêm vào table để test
        modelTKBList.addRow(new Object[]{maTKB, lop, ngayBD, ngayKT});
        
        clearForm();
        JOptionPane.showMessageDialog(this, "Thêm TKB thành công!");
    }

    private void suaTKB() {
        int row = tblTKBList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn TKB cần sửa!");
            return;
        }

        int c = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn sửa TKB này?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        // TODO: Gọi BLL để cập nhật database
        
        // Tạm thời cập nhật table
        modelTKBList.setValueAt(cboLop.getSelectedItem(), row, 1);
        modelTKBList.setValueAt(txtNgayBD.getText(), row, 2);
        modelTKBList.setValueAt(txtNgayKT.getText(), row, 3);

        JOptionPane.showMessageDialog(this, "Sửa TKB thành công!");
    }

    private void xoaTKB() {
        int row = tblTKBList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn TKB cần xóa!");
            return;
        }

        int c = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn xóa TKB này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        // TODO: Gọi BLL để xóa khỏi database

        // Tạm thời xóa khỏi table
        modelTKBList.removeRow(row);
        clearForm();
        JOptionPane.showMessageDialog(this, "Xóa TKB thành công!");
    }
    
    // ===== QUAY LẠI MAIN MENU =====
    private void quaylai() {
        new MainMenu().setVisible(true);
        this.dispose();
    }

    // ================= UI FLOW ================
    private void fillFormFromTable(int row) {
        txtMaTKB.setText(modelTKBList.getValueAt(row, 0).toString());
        cboLop.setSelectedItem(modelTKBList.getValueAt(row, 1));
        txtNgayBD.setText(modelTKBList.getValueAt(row, 2).toString());
        txtNgayKT.setText(modelTKBList.getValueAt(row, 3).toString());
        txtMaTKB.setEnabled(false);

        // TODO: Load lưới TKB tương ứng
        loadSampleGrid(); // Tạm thời load mẫu
    }

    private void clearForm() {
        txtMaTKB.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        txtMaTKB.setEnabled(true);
        
        cboLop.setSelectedIndex(0);
        cboTKB.setSelectedIndex(0);
        tblTKBList.clearSelection();
        
        initGrid(); // Reset lưới về trống
        updateButtonState();
        txtMaTKB.requestFocus();
    }

    private void updateButtonState() {
        boolean dangChon = tblTKBList.getSelectedRow() >= 0;
        btnSua.setEnabled(dangChon);
        btnXoa.setEnabled(dangChon);
    }

    // ================= LƯỚI TKB =================
    private void initGrid() {
        modelTKBLuoi.setRowCount(0);
        for (int i = 1; i <= 10; i++) {
            modelTKBLuoi.addRow(new Object[]{"Tiết " + i, "", "", "", "", ""});
        }
    }

    private void loadSampleGrid() {
        initGrid();
        // Load mẫu để test UI
        modelTKBLuoi.setValueAt("Toán", 0, 1); // Tiết 1, Thứ 2
        modelTKBLuoi.setValueAt("Toán", 1, 1); // Tiết 2, Thứ 2
        modelTKBLuoi.setValueAt("Văn", 0, 2);  // Tiết 1, Thứ 3
        modelTKBLuoi.setValueAt("Lý", 1, 2);   // Tiết 2, Thứ 3
        modelTKBLuoi.setValueAt("Hóa", 0, 3);  // Tiết 1, Thứ 4
        modelTKBLuoi.setValueAt("Sinh", 1, 3); // Tiết 2, Thứ 4
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormTKB().setVisible(true));
    }
}