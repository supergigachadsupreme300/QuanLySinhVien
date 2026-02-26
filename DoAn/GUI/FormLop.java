package GUI;

/**
 * FORM QUẢN LÝ LỚP
 * - 
 *
 */

import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.HocSinhBLL;
import DataObject.Lop;
import DataObject.HocSinh;
import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.table.TableRowSorter;
import java.util.List;

import net.miginfocom.swing.MigLayout;

public class FormLop extends JPanel {

    private MainMenu mainFrame;
    private LopBLL lopBLL = new LopBLL();
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    // sau này dùng

    /* ================= TABLE ================= */
    private JTable tblLop, tblHS;
    private DefaultTableModel modelLop, modelHS;

    /* ================= FORM ================= */
    private JTextField txtMaLop, txtTenLop, txtSiSo, txtNamHoc, txtGVCN;

    /* ================= BUTTON ================= */
    private JButton btnThem, btnSua, btnXoa, btnClear;

    /* ================= CONSTRUCTOR ================= */
    public FormLop(MainMenu frame) {
        this.mainFrame = frame;
        initUI();
/*        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int c = JOptionPane.showConfirmDialog(
                        MainMenu.this,
                        "Bạn có chắc muốn thoát chương trình?",
                        "Thoát",
                        JOptionPane.YES_NO_OPTION
                );
                if (c == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });*/
    }

    /* ================= UI ================= */
    private void initUI() {

        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));

        /* ===== TITLE ===== */
        JLabel lblTitle = new JLabel("QUẢN LÝ LỚP HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));

        add(lblTitle, "growx, wrap");

        /* ===== FORM ===== */
        JPanel pnlForm = new JPanel(new MigLayout(
                "insets 15",
                "[]15[grow]30[]15[grow]",
                "[]10[]10[]10[]"
        ));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));

        txtMaLop  = new JTextField();
        txtTenLop = new JTextField();
        txtSiSo   = new JTextField();
        txtNamHoc = new JTextField();
        txtGVCN   = new JTextField();

        pnlForm.add(new JLabel("Mã lớp:"));
        pnlForm.add(txtMaLop, "growx");
        pnlForm.add(new JLabel("Tên lớp:"));
        pnlForm.add(txtTenLop, "growx, wrap");

        pnlForm.add(new JLabel("Sĩ số:"));
        pnlForm.add(txtSiSo, "growx");
        pnlForm.add(new JLabel("Năm học:"));
        pnlForm.add(txtNamHoc, "growx, wrap");

        pnlForm.add(new JLabel("GVCN:"));
        pnlForm.add(txtGVCN, "growx, span 3");

        add(pnlForm, "growx, wrap");

        /* ===== BUTTON ===== */
        JPanel pnlBtn = new JPanel();

        btnThem  = createButton("Thêm", new Color(34, 139, 34));
        btnSua   = createButton("Sửa", new Color(255, 140, 0));
        btnXoa   = createButton("Xóa", new Color(220, 20, 60));
        btnClear = createButton("Làm mới", new Color(70, 130, 180));

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);

        add(pnlBtn, "growx, wrap");

        /* ===== TABLE MODEL ===== */
        modelLop = new DefaultTableModel(
                new String[]{"Mã lớp", "Tên lớp", "Sĩ số", "Năm học", "GVCN"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        modelHS = new DefaultTableModel(
                new String[]{"Mã HS", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        /* ===== TABLE LỚP ===== */
        tblLop = new JTable(modelLop);
        styleTable(tblLop);
        tblLop.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLop.setPreferredScrollableViewportSize(new Dimension(450, 220));
        tblLop.setFillsViewportHeight(true);
        /*TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelLop);
        tblLop.setRowSorter(sorter);*/
        //sắp xêp theo thứ tự cột năm học

        
        tblLop.getColumnModel().getColumn(0).setPreferredWidth(80);    // Mã lớp
        tblLop.getColumnModel().getColumn(1).setPreferredWidth(150);   // Tên lớp
        tblLop.getColumnModel().getColumn(2).setPreferredWidth(70);    // Sĩ số
        tblLop.getColumnModel().getColumn(3).setPreferredWidth(120);   // Năm học
        tblLop.getColumnModel().getColumn(4).setPreferredWidth(150);   // GVCN

        JScrollPane spLop = new JScrollPane(tblLop);
        spLop.setBorder(BorderFactory.createTitledBorder("Danh sách lớp"));
        spLop.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        /* ===== TABLE HỌC SINH ===== */
        tblHS = new JTable(modelHS);
        styleTable(tblHS);
        tblHS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblHS.setPreferredScrollableViewportSize(new Dimension(550, 220));
        tblHS.setFillsViewportHeight(true);
        


        
        tblHS.getColumnModel().getColumn(0).setPreferredWidth(80);    // Mã HS
        tblHS.getColumnModel().getColumn(1).setPreferredWidth(150);   // Họ tên
        tblHS.getColumnModel().getColumn(2).setPreferredWidth(100);   // Ngày sinh
        tblHS.getColumnModel().getColumn(3).setPreferredWidth(80);    // Giới tính
        tblHS.getColumnModel().getColumn(4).setPreferredWidth(200);   // Địa chỉ

        JScrollPane spHS = new JScrollPane(tblHS);
        spHS.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        spHS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        /* ===== SPLIT ===== */
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                spLop,
                spHS
        );
        split.setResizeWeight(0.45);      // ưu tiên bảng lớp
        split.setDividerSize(8);

        add(split, "grow");



        /* ===== EVENTS ===== */
        btnThem.addActionListener(e -> themLop());
        btnSua.addActionListener(e -> suaLop());
        btnXoa.addActionListener(e -> xoaLop());
        btnClear.addActionListener(e -> clearForm());

        tblLop.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblLop.getSelectedRow();
            if (row >= 0) {
                fillFormFromTable(row);
                updateButtonState();
            }
        });

        /* ===== FOCUS EFFECT ===== */
        addFocusEffect(txtMaLop);
        addFocusEffect(txtTenLop);
        addFocusEffect(txtSiSo);
        addFocusEffect(txtNamHoc);
        addFocusEffect(txtGVCN);

        updateButtonState();
    }

    //=============== VALIDATE ========================//
    private boolean validateForm() {
        if (txtMaLop.getText().trim().isEmpty()
                || txtTenLop.getText().trim().isEmpty()
                || txtSiSo.getText().trim().isEmpty()
                || txtNamHoc.getText().trim().isEmpty()
                || txtGVCN.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        try {
            Integer.parseInt(txtSiSo.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Sĩ số phải là số nguyên!",
                    "Lỗi dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
            txtSiSo.requestFocus();
            return false;
        }
        return true;
    }
    
    

    /*================= CRUD ==========================*/
    private void themLop() {
        if (!validateForm()) return;

        Lop lop = getLopFromForm();

        /*modelLop.addRow(new Object[]{
                lop.getMaLop(),
                lop.getTenLop(),
                lop.getSiSo(),
                lop.getMaNH(),
                lop.getMaGVCN()
        });*/
        if (!lopBLL.themLop(lop)){
        
        JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");
        return;
        }
        
        loadTableLop();
        clearForm();
        JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");
    }

    private void suaLop() {
        int row = tblLop.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần sửa!");
            return;
        }

        if (!validateForm()) return;

        int c = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn sửa lớp này?",
                "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        Lop lop = getLopFromForm();
        lopBLL.suaLop(lop);
        loadTableLop();
        
        /*modelLop.setValueAt(lop.getTenLop(), row, 1);
        modelLop.setValueAt(lop.getSiSo(), row, 2);
        modelLop.setValueAt(lop.getMaNH(), row, 3);
        modelLop.setValueAt(lop.getMaGVCN(), row, 4);*/

        JOptionPane.showMessageDialog(this, "Sửa lớp thành công!");
    }

    private void xoaLop() {
        int row = tblLop.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần xóa!");
            return;
        }
        String maLop = modelLop.getValueAt(row, 0).toString();
        
        int c = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa lớp này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;
        

        /*modelLop.removeRow(row);*/
        if (!lopBLL.xoaLop(maLop)) {
            JOptionPane.showMessageDialog(this, "Xóa lớp thất bại!");
            return;
        }
        loadTableLop();
        clearForm();
        JOptionPane.showMessageDialog(this, "Xóa lớp thành công!");
    }

    /* =================================================
       ================= UI FLOW =======================
       ================================================= */
    private Lop getLopFromForm() {
        Lop lop = new Lop();
        lop.setMaLop(txtMaLop.getText().trim());
        lop.setTenLop(txtTenLop.getText().trim());
        lop.setSiSo(Integer.parseInt(txtSiSo.getText().trim()));
        lop.setMaNH(txtNamHoc.getText().trim());
        lop.setMaGVCN(txtGVCN.getText().trim());
        return lop;
    }

    private void fillFormFromTable(int row) {
    String maLop = modelLop.getValueAt(row, 0).toString();

        txtMaLop.setText(maLop);
        txtTenLop.setText(modelLop.getValueAt(row, 1).toString());
        txtSiSo.setText(modelLop.getValueAt(row, 2).toString());
        txtNamHoc.setText(modelLop.getValueAt(row, 3).toString());
        txtGVCN.setText(modelLop.getValueAt(row, 4).toString());    

        txtMaLop.setEnabled(false);

        loadHocSinhByLop(maLop);
    }   

    private void loadHocSinhByLop(String maLop) {
    modelHS.setRowCount(0);

    List<HocSinh> ds = hocSinhBLL.getByMaLop(maLop);

    for (HocSinh hs : ds) {
        modelHS.addRow(new Object[]{
            hs.getMaHS(),
            hs.getHoTen(),
            hs.getNgaySinh(),
            hs.getGioiTinh(),
            hs.getDiaChi()
        });
    }
}


    private void clearForm() {
        txtMaLop.setText("");
        txtTenLop.setText("");
        txtSiSo.setText("");
        txtNamHoc.setText("");
        txtGVCN.setText("");
        txtMaLop.setEnabled(true);
        tblLop.clearSelection();
        updateButtonState();
        txtMaLop.requestFocus();
    }

    private void updateButtonState() {
        boolean selected = tblLop.getSelectedRow() >= 0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
    }

    //================= UI UTILS ======================//
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

    private void loadTableLop() {
        modelLop.setRowCount(0);
        
        for (Lop l : lopBLL.getAll()) {
            modelLop.addRow(new Object[]{
                l.getMaLop(),
                l.getTenLop(),
                l.getSiSo(),
                l.getMaNH(),
                l.getMaGVCN()
            });
        }
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
}

