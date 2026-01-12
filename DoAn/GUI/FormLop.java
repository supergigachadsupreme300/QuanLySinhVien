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

public class FormLop extends JFrame {

    // ================= TABLE =================
    private JTable tblLop, tblHS;
    private DefaultTableModel modelLop, modelHS;

    // ================= FORM ==================
    private JTextField txtMaLop, txtTenLop, txtSiSo;
    private JComboBox<String> cboNamHoc, cboGVCN;

    // ================= BUTTON ================
    private JButton btnThem, btnSua, btnXoa, btnClear, btnQuayLai;

    // ================= CONSTRUCTOR =================
    public FormLop() {
        setTitle("Quản lý lớp");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int c = JOptionPane.showConfirmDialog(
                    FormLop.this,
                    "Bạn có muốn thoát chương trình?",
                    "Thoát",
                    JOptionPane.YES_NO_OPTION
                );
                if (c == JOptionPane.YES_OPTION) dispose();
            }
        });

        initUI();
    }

    // ================= UI =====================
    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[]15[grow]"));

        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("QUẢN LÝ LỚP HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        // ===== FORM =====
        JPanel pnlForm = new JPanel(new MigLayout(
            "insets 15",
            "[]15[grow]30[]15[grow]",
            "[]10[]10[]"
        ));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));

        txtMaLop = new JTextField();
        txtTenLop = new JTextField();
        txtSiSo = new JTextField();
        cboNamHoc = new JComboBox<>(new String[]{"2023-2024", "2024-2025", "2025-2026"});
        cboGVCN = new JComboBox<>(new String[]{"Nguyễn Văn A", "Trần Thị B", "Lê Văn C"});

        pnlForm.add(new JLabel("Mã lớp:"));
        pnlForm.add(txtMaLop, "growx");
        pnlForm.add(new JLabel("Tên lớp:"));
        pnlForm.add(txtTenLop, "growx, wrap");

        pnlForm.add(new JLabel("Sĩ số:"));
        pnlForm.add(txtSiSo, "growx");
        pnlForm.add(new JLabel("Năm học:"));
        pnlForm.add(cboNamHoc, "growx, wrap");

        pnlForm.add(new JLabel("GVCN:"));
        pnlForm.add(cboGVCN, "growx, span 3");

        add(pnlForm, "growx, wrap");

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

        // ===== TABLE LỚP =====
        modelLop = new DefaultTableModel(
            new String[]{"Mã lớp", "Tên lớp", "Sĩ số", "Năm học", "GVCN"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        tblLop = new JTable(modelLop);
        tblLop.setRowHeight(25);
        tblLop.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblLop.getTableHeader().setBackground(new Color(0, 102, 204));
        tblLop.getTableHeader().setForeground(Color.WHITE);
        styleTable(tblLop);
        
        JPanel pnlLop = new JPanel(new BorderLayout());
        pnlLop.setBorder(BorderFactory.createTitledBorder("Danh sách lớp"));
        pnlLop.add(new JScrollPane(tblLop));

        // ===== TABLE HỌC SINH =====
        modelHS = new DefaultTableModel(
            new String[]{"Mã HS", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        tblHS = new JTable(modelHS);
        tblHS.setRowHeight(25);
        tblHS.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblHS.getTableHeader().setBackground(new Color(0, 102, 204));
        tblHS.getTableHeader().setForeground(Color.WHITE);
        styleTable(tblHS);
        
        JPanel pnlHS = new JPanel(new BorderLayout());
        pnlHS.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh trong lớp"));
        pnlHS.add(new JScrollPane(tblHS));

        // ===== SPLIT PANE (Chia 2 bảng ngang nhau) =====
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLop, pnlHS);
        split.setDividerLocation(520);
        add(split, "grow");

        // ===== EVENTS =====
        tblLop.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblLop.getSelectedRow();
            if (row >= 0) {
                fillFormFromTable(row);
                updateButtonState();
            }
        });

        btnThem.addActionListener(e -> themLop());
        btnSua.addActionListener(e -> suaLop());
        btnXoa.addActionListener(e -> xoaLop());
        btnClear.addActionListener(e -> clearForm());
        btnQuayLai.addActionListener(e -> quaylai());

        // ===== THÊM EFFECT =====
        addFocusEffect(txtMaLop);
        addFocusEffect(txtTenLop);
        addFocusEffect(txtSiSo);
        addFocusEffect(cboNamHoc);
        addFocusEffect(cboGVCN);

        updateButtonState();
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
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(originalColor);
            }
        });

        return btn;
    }
    
    // ================= TABLE STYLE =================
    private void styleTable(JTable tbl) {
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0, 102, 204));
        tbl.getTableHeader().setForeground(Color.WHITE);
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

    // ================= CRUD (TẠM THỜI ĐỂ TRỐNG - SAU NÀY NỐI BLL) =================
    private void themLop() {
        // TODO: Gọi BLL để thêm vào database
        String maLop = txtMaLop.getText();
        String tenLop = txtTenLop.getText();
        String siSo = txtSiSo.getText();
        String namHoc = cboNamHoc.getSelectedItem().toString();
        String gvcn = cboGVCN.getSelectedItem().toString();

        // Tạm thời thêm vào table để test
        modelLop.addRow(new Object[]{maLop, tenLop, siSo, namHoc, gvcn});
        
        clearForm();
        JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");
    }

    private void suaLop() {
        int row = tblLop.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần sửa!");
            return;
        }

        int c = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn sửa lớp này?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        // TODO: Gọi BLL để cập nhật database
        
        // Tạm thời cập nhật table
        modelLop.setValueAt(txtTenLop.getText(), row, 1);
        modelLop.setValueAt(txtSiSo.getText(), row, 2);
        modelLop.setValueAt(cboNamHoc.getSelectedItem(), row, 3);
        modelLop.setValueAt(cboGVCN.getSelectedItem(), row, 4);

        JOptionPane.showMessageDialog(this, "Sửa lớp thành công!");
    }

    private void xoaLop() {
        int row = tblLop.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần xóa!");
            return;
        }

        int c = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn xóa lớp này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        // TODO: Gọi BLL để xóa khỏi database

        // Tạm thời xóa khỏi table
        modelLop.removeRow(row);
        clearForm();
        JOptionPane.showMessageDialog(this, "Xóa lớp thành công!");
    }

    // ===== QUAY LẠI MAIN MENU =====
    private void quaylai() {
        new MainMenu().setVisible(true);
        this.dispose();
    }
    
    // ================= UI FLOW ================
    private void fillFormFromTable(int row) {
        txtMaLop.setText(modelLop.getValueAt(row, 0).toString());
        txtTenLop.setText(modelLop.getValueAt(row, 1).toString());
        txtSiSo.setText(modelLop.getValueAt(row, 2).toString());
        cboNamHoc.setSelectedItem(modelLop.getValueAt(row, 3));
        cboGVCN.setSelectedItem(modelLop.getValueAt(row, 4));
        txtMaLop.setEnabled(false);

        // TODO: Load học sinh của lớp này vào bảng bên phải
        // modelHS.setRowCount(0);
        // List<HocSinh> dsHS = hocSinhBLL.getByMaLop(maLop);
        // for (HocSinh hs : dsHS) {
        //     modelHS.addRow(...);
        // }
    }

    private void clearForm() {
        txtMaLop.setText("");
        txtTenLop.setText("");
        txtSiSo.setText("");
        cboNamHoc.setSelectedIndex(0);
        cboGVCN.setSelectedIndex(0);
        txtMaLop.setEnabled(true);
        
        tblLop.clearSelection();
        modelHS.setRowCount(0);
        
        updateButtonState();
        txtMaLop.requestFocus();
    }

    private void updateButtonState() {
        boolean dangChon = tblLop.getSelectedRow() >= 0;
        btnSua.setEnabled(dangChon);
        btnXoa.setEnabled(dangChon);
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLop().setVisible(true));
    }
}