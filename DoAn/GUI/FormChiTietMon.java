package GUI;

import BusinessLogicLayer.ChiTietMonBLL;
import DataObject.ChiTietMon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class FormChiTietMon extends JPanel {

    private final ChiTietMonBLL chiTietMonBLL = new ChiTietMonBLL();


    private JTable tblChiTietMon;
    private DefaultTableModel modelChiTietMon;


    private JTextField txtMaChiTiet, txtMaMon, txtTenChiTiet, txtHeSo;


    private JButton btnThem, btnSua, btnXoa, btnClear;

    public FormChiTietMon() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));


        JLabel lblTitle = new JLabel("QUẢN LÝ CHI TIẾT MÔN HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");


        JPanel pnlForm = new JPanel(new MigLayout(
                "insets 15",
                "[]15[grow]30[]15[grow]",
                "[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết môn"));

        txtMaChiTiet = new JTextField();
        txtMaMon = new JTextField();
        txtTenChiTiet = new JTextField();
        txtHeSo = new JTextField();

        pnlForm.add(new JLabel("Mã chi tiết:"));
        pnlForm.add(txtMaChiTiet, "growx");
        pnlForm.add(new JLabel("Mã môn:"));
        pnlForm.add(txtMaMon, "growx, wrap");

        pnlForm.add(new JLabel("Tên chi tiết:"));
        pnlForm.add(txtTenChiTiet, "growx, wrap");

        pnlForm.add(new JLabel("Hệ số:"));
        pnlForm.add(txtHeSo, "growx");

        add(pnlForm, "growx, wrap");


        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34, 139, 34));
        btnSua = createButton("Sửa", new Color(0, 150, 136)); 
        btnXoa = createButton("Xóa", new Color(220, 20, 60)); 
        btnClear = createButton("Làm mới", new Color(70, 130, 180)); 

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
        add(pnlBtn, "growx, wrap");


        modelChiTietMon = new DefaultTableModel(
                new String[] { "Mã chi tiết", "Mã môn", "Tên chi tiết", "Hệ số" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblChiTietMon = new JTable(modelChiTietMon);
        styleTable(tblChiTietMon);

        tblChiTietMon.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tblChiTietMon.setPreferredScrollableViewportSize(new Dimension(600, 300));
        tblChiTietMon.setFillsViewportHeight(true);

        tblChiTietMon.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblChiTietMon.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblChiTietMon.getColumnModel().getColumn(2).setPreferredWidth(250);
        tblChiTietMon.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane spChiTiet = new JScrollPane(tblChiTietMon);
        spChiTiet.setBorder(BorderFactory.createTitledBorder("Danh sách chi tiết môn"));
        spChiTiet.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(spChiTiet, "grow");


        btnThem.addActionListener(e -> themChiTietMon());
        btnSua.addActionListener(e -> suaChiTietMon());
        btnXoa.addActionListener(e -> xoaChiTietMon());
        btnClear.addActionListener(e -> clearForm());

        tblChiTietMon.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblChiTietMon.getSelectedRow();
                if (row >= 0) {
                    fillFormFromTable(row);
                    updateButtonState();
                }
            }
        });


        addFocusEffect(txtMaChiTiet);
        addFocusEffect(txtMaMon);
        addFocusEffect(txtTenChiTiet);
        addFocusEffect(txtHeSo);

        updateButtonState();
        loadTableChiTietMon();
    }


    private boolean validateForm() {
        String maCT = txtMaChiTiet.getText().trim();
        String maMon = txtMaMon.getText().trim();
        String tenCT = txtTenChiTiet.getText().trim();
        String heSoStr = txtHeSo.getText().trim();

        if (maCT.isEmpty() || maMon.isEmpty() || tenCT.isEmpty() || heSoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int heSo = Integer.parseInt(heSoStr);
            if (heSo <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Hệ số phải là số nguyên dương (> 0)!",
                        "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                txtHeSo.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Hệ số phải là số nguyên!",
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            txtHeSo.requestFocus();
            return false;
        }
        return true;
    }

    private void themChiTietMon() {
        if (!validateForm())
            return;

        ChiTietMon ct = getEntityFromForm();

        if (chiTietMonBLL.themChiTietMon(ct)) {
            JOptionPane.showMessageDialog(this,
                    "Thêm chi tiết môn thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableChiTietMon();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Thêm thất bại!\nCó thể mã chi tiết đã tồn tại hoặc lỗi kết nối dữ liệu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaChiTietMon() {
        int row = tblChiTietMon.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!", "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateForm())
            return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn sửa thông tin này?",
                "Xác nhận sửa", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        ChiTietMon ct = getEntityFromForm();

        if (chiTietMonBLL.suaChiTietMon(ct)) {
            JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableChiTietMon();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaChiTietMon() {
        int row = tblChiTietMon.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maChiTiet = modelChiTietMon.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa chi tiết môn này?\nHành động không thể hoàn tác.",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        if (chiTietMonBLL.xoaChiTietMon(maChiTiet)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableChiTietMon();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Xóa thất bại!\nCó thể chi tiết môn đang được sử dụng ở nơi khác.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ChiTietMon getEntityFromForm() {
        ChiTietMon ct = new ChiTietMon();
        ct.setMaChiTiet(txtMaChiTiet.getText().trim());
        ct.setMaMon(txtMaMon.getText().trim());
        ct.setTenChiTiet(txtTenChiTiet.getText().trim());

        try {
            ct.setHeSo(Integer.parseInt(txtHeSo.getText().trim()));
        } catch (NumberFormatException e) {
            ct.setHeSo(1);
        }
        return ct;
    }

    private void fillFormFromTable(int row) {
        txtMaChiTiet.setText(modelChiTietMon.getValueAt(row, 0).toString());
        txtMaMon.setText(modelChiTietMon.getValueAt(row, 1).toString());
        txtTenChiTiet.setText(modelChiTietMon.getValueAt(row, 2).toString());
        txtHeSo.setText(modelChiTietMon.getValueAt(row, 3).toString());

        txtMaChiTiet.setEnabled(false);
    }

    private void clearForm() {
        txtMaChiTiet.setText("");
        txtMaMon.setText("");
        txtTenChiTiet.setText("");
        txtHeSo.setText("");
        txtMaChiTiet.setEnabled(true);
        tblChiTietMon.clearSelection();
        updateButtonState();
        txtMaChiTiet.requestFocus();
    }

    private void updateButtonState() {
        boolean selected = tblChiTietMon.getSelectedRow() >= 0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
    }

    private void loadTableChiTietMon() {
        modelChiTietMon.setRowCount(0);
        List<ChiTietMon> list = chiTietMonBLL.getAll();
        for (ChiTietMon ct : list) {
            modelChiTietMon.addRow(new Object[] {
                    ct.getMaChiTiet(),
                    ct.getMaMon(),
                    ct.getTenChiTiet(),
                    ct.getHeSo()
            });
        }
    }

    private JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setOpaque(true);


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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý Chi tiết Môn học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new FormChiTietMon());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
