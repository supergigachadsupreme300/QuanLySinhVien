package GUI;

import BusinessLogicLayer.MonHocBLL;
import DataObject.Mon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;
import net.miginfocom.swing.MigLayout;


public class FormMonHoc extends JPanel {

    private final MonHocBLL monHocBLL = new MonHocBLL();


    private JTable tblMonHoc;
    private DefaultTableModel modelMonHoc;


    private JTextField txtMaMon, txtTenMon, txtSoTinChi, txtKhoa;


    private JButton btnThem, btnSua, btnXoa, btnClear;

    public FormMonHoc() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));


        JLabel lblTitle = new JLabel("QUẢN LÝ MÔN HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");


        JPanel pnlForm = new JPanel(new MigLayout(
                "insets 15",
                "[]15[grow]30[]15[grow]",
                "[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin môn học"));

        txtMaMon = new JTextField();
        txtTenMon = new JTextField();
        txtSoTinChi = new JTextField();
        txtKhoa = new JTextField();

        pnlForm.add(new JLabel("Mã môn học:"));
        pnlForm.add(txtMaMon, "growx");
        pnlForm.add(new JLabel("Tên môn học:"));
        pnlForm.add(txtTenMon, "growx, wrap");

        pnlForm.add(new JLabel("Số tín chỉ:"));
        pnlForm.add(txtSoTinChi, "growx");
        pnlForm.add(new JLabel("Khoa:"));
        pnlForm.add(txtKhoa, "growx");

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


        modelMonHoc = new DefaultTableModel(
                new String[] { "Mã môn", "Tên môn", "Số tín chỉ", "Khoa" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblMonHoc = new JTable(modelMonHoc);
        styleTable(tblMonHoc);

        tblMonHoc.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        tblMonHoc.setPreferredScrollableViewportSize(new Dimension(600, 300));
        tblMonHoc.setFillsViewportHeight(true);

        tblMonHoc.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblMonHoc.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblMonHoc.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblMonHoc.getColumnModel().getColumn(3).setPreferredWidth(200);

        JScrollPane spMonHoc = new JScrollPane(tblMonHoc);
        spMonHoc.setBorder(BorderFactory.createTitledBorder("Danh sách môn học"));
        spMonHoc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(spMonHoc, "grow");


        btnThem.addActionListener(e -> themMonHoc());
        btnSua.addActionListener(e -> suaMonHoc());
        btnXoa.addActionListener(e -> xoaMonHoc());
        btnClear.addActionListener(e -> clearForm());

        tblMonHoc.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = tblMonHoc.getSelectedRow();
                if (row >= 0) {
                    fillFormFromTable(row);
                    updateButtonState();
                }
            }
        });

        addFocusEffect(txtMaMon);
        addFocusEffect(txtTenMon);
        addFocusEffect(txtSoTinChi);
        addFocusEffect(txtKhoa);

        updateButtonState();
        loadTableMonHoc();
    }


    private boolean validateForm() {
        String maMon = txtMaMon.getText().trim();
        String tenMon = txtTenMon.getText().trim();
        String soTinChiStr = txtSoTinChi.getText().trim();
        String khoa = txtKhoa.getText().trim();

        if (maMon.isEmpty() || tenMon.isEmpty() || soTinChiStr.isEmpty() || khoa.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int soTinChi = Integer.parseInt(soTinChiStr);
            if (soTinChi <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Số tín chỉ phải là số nguyên dương (> 0)!",
                        "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                txtSoTinChi.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Số tín chỉ phải là số nguyên!",
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            txtSoTinChi.requestFocus();
            return false;
        }

        return true;
    }


    private void themMonHoc() {
        if (!validateForm())
            return;

        Mon mh = getEntityFromForm();

        if (monHocBLL.themMonHoc(mh)) {
            JOptionPane.showMessageDialog(this,
                    "Thêm môn học thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableMonHoc();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Thêm thất bại!\nCó thể mã môn học đã tồn tại hoặc lỗi dữ liệu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void suaMonHoc() {
        int row = tblMonHoc.getSelectedRow();
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

        Mon mh = getEntityFromForm();

        if (monHocBLL.suaMonHoc(mh)) {
            JOptionPane.showMessageDialog(this, "Sửa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableMonHoc();
        } else {
            JOptionPane.showMessageDialog(this, "Sửa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaMonHoc() {
        int row = tblMonHoc.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Chưa chọn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maMon = modelMonHoc.getValueAt(row, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa môn học này?\nHành động không thể hoàn tác.",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION)
            return;

        if (monHocBLL.xoaMonHoc(maMon)) {
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            loadTableMonHoc();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Xóa thất bại!\nCó thể môn học đang được sử dụng ở nơi khác.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }



    private Mon getEntityFromForm() {
        Mon mh = new Mon();
        mh.setMaMon(txtMaMon.getText().trim());
        mh.setTenMon(txtTenMon.getText().trim());

        try {
            mh.setSoTinChi(Integer.parseInt(txtSoTinChi.getText().trim()));
        } catch (NumberFormatException e) {
            mh.setSoTinChi(1); 
        }

        mh.setKhoa(txtKhoa.getText().trim());
        return mh;
    }

    private void fillFormFromTable(int row) {
        txtMaMon.setText(modelMonHoc.getValueAt(row, 0).toString());
        txtTenMon.setText(modelMonHoc.getValueAt(row, 1).toString());
        txtSoTinChi.setText(modelMonHoc.getValueAt(row, 2).toString());
        txtKhoa.setText(modelMonHoc.getValueAt(row, 3).toString());

        txtMaMon.setEnabled(false); 
    }

    private void clearForm() {
        txtMaMon.setText("");
        txtTenMon.setText("");
        txtSoTinChi.setText("");
        txtKhoa.setText("");
        txtMaMon.setEnabled(true);
        tblMonHoc.clearSelection();
        updateButtonState();
        txtMaMon.requestFocus();
    }

    private void updateButtonState() {
        boolean selected = tblMonHoc.getSelectedRow() >= 0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
        btnThem.setEnabled(!selected);
    }

    private void loadTableMonHoc() {
        modelMonHoc.setRowCount(0);
        List<Mon> list = monHocBLL.getAll();
        for (Mon mh : list) {
            modelMonHoc.addRow(new Object[] {
                    mh.getMaMon(),
                    mh.getTenMon(),
                    mh.getSoTinChi(),
                    mh.getKhoa()
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
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
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
            JFrame frame = new JFrame("Quản lý Môn học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new FormMonHoc());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
