/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author admin
 */
import DataObject.GiaoVien;
import DataObject.HocSinh;
import DataObject.Lop;
import DataObject.NamHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;

public class TestFormLop extends JPanel {

    private MainMenu mainFrame;
    // ================= DATA =================
    private ArrayList<Lop> dsLop = new ArrayList<>();
    private ArrayList<NamHoc> dsNamHoc = new ArrayList<>();
    private ArrayList<GiaoVien> dsGV = new ArrayList<>();
    private ArrayList<HocSinh> dsHS = new ArrayList<>();

    // ====== TRẠNG THÁI ĐANG CHỌN (GIỐNG FormTKB)
    private Lop lopDangChon = null;

    // ================= TABLE =================
    private JTable tblLop, tblHS;
    private DefaultTableModel modelLop, modelHS;

    // ================= FORM ==================
    private JTextField txtMaLop, txtTenLop, txtSiSo;
    private JComboBox<NamHoc> cboNamHoc;
    private JComboBox<GiaoVien> cboGVCN;

    // ================= BUTTON ================
    private JButton btnThem, btnSua, btnXoa, btnClear, btnQuayLai;

    // ================= CONSTRUCTOR =================
    public TestFormLop(MainMenu frame) {
        /*setTitle("Quản lý lớp");
        setSize(1100, 650);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int c = JOptionPane.showConfirmDialog(
                        TestFormLop.this,
                        "Bạn có muốn thoát chương trình?",
                        "Thoát",
                        JOptionPane.YES_NO_OPTION
                );
                if (c == JOptionPane.YES_OPTION) dispose();
            }
        });*/
        this.mainFrame = frame;

        initData();     // DATA GIẢ (SAU NÀY THAY = BLL)
        initUI();      // VẼ GIAO DIỆN
        loadTableLop(); // ĐỔ DATA LÊN BẢNG
        updateButtonState();
    }

    // ================= DATA GIẢ ===============
    private void initData() {

        dsNamHoc.add(new NamHoc("NH01", "2023 - 2024"));
        dsNamHoc.add(new NamHoc("NH02", "2024 - 2025"));

        dsGV.add(new GiaoVien("GV01", "Nguyễn Văn A", "0123", "a@gmail.com"));
        dsGV.add(new GiaoVien("GV02", "Trần Thị B", "0456", "b@gmail.com"));

        dsLop.add(new Lop("10A1", "10A1", 40, "NH01", "GV01"));
        dsLop.add(new Lop("10A2", "10A2", 38, "NH01", "GV02"));

        dsHS.add(new HocSinh("HS01", "Trần Minh", LocalDate.of(2008,5,12), "Nam", "HN", "10A1"));
        dsHS.add(new HocSinh("HS02", "Nguyễn Lan", LocalDate.of(2008,8,22), "Nữ", "HN", "10A1"));
        dsHS.add(new HocSinh("HS03", "Lê Hoàng", LocalDate.of(2008,3,3), "Nam", "HN", "10A2"));
    }

    // ================= UI =====================
    private void initUI() {

        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[]15[grow]"));

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
        txtSiSo  = new JTextField();
        cboNamHoc = new JComboBox<>(dsNamHoc.toArray(new NamHoc[0]));
        cboGVCN   = new JComboBox<>(dsGV.toArray(new GiaoVien[0]));

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
        btnThem  = createButton("Thêm", new Color(34, 139, 34));
        btnSua   = createButton("Sửa", new Color(255, 140, 0));
        btnXoa   = createButton("Xóa", new Color(220, 20, 60));
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
            public boolean isCellEditable(int r, int c) { return false; }
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

        // ===== TABLE HS =====
        modelHS = new DefaultTableModel(
                new String[]{"Mã HS", "Họ tên", "Ngày sinh", "Giới tính", "Địa chỉ"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblHS = new JTable(modelHS);
        tblHS.setRowHeight(25);
        tblHS.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblHS.getTableHeader().setBackground(new Color(0, 102, 204));
        tblHS.getTableHeader().setForeground(Color.WHITE);
        styleTable(tblHS);

        JPanel pnlHS = new JPanel(new BorderLayout());
        pnlHS.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        pnlHS.add(new JScrollPane(tblHS));

        //Split panel (Chia 2 bảng bằng nhau)
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlLop, pnlHS);
        split.setDividerLocation(520);
        add(split, "grow");

        // ===== EVENTS =====
        tblLop.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblLop.getSelectedRow();
            if (row < 0) return;

            lopDangChon = dsLop.get(row);
            fillForm();
            loadHocSinhTheoLop();
            updateButtonState();
        });

        btnThem.addActionListener(e -> themLop());
        btnSua.addActionListener(e -> suaLop());
        btnXoa.addActionListener(e -> xoaLop());
        btnClear.addActionListener(e -> clearForm());
//        btnQuayLai.addActionListener(e -> quaylai());
        
        // ===== EFFECT (GIỐNG FormTKB) =====
        /*addFocus(txtMaLop);
        addFocus(txtTenLop);
        addFocus(txtSiSo);
        addFocus(cboNamHoc);
        addFocus(cboGVCN);

        addHover(btnThem);
        addHover(btnSua);
        addHover(btnXoa);
        addHover(btnClear);*/
        
        // ===== FOCUS EFFECT (GIỐNG FormLop) =====
        addFocusEffect(txtMaLop);
        addFocusEffect(txtTenLop);
        addFocusEffect(txtSiSo);
        addFocusEffect(cboNamHoc);
        addFocusEffect(cboGVCN);        
    }

    
    // ================= BUTTON STYLE (COPY TỪ FormLop) =================
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color original = bgColor;
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(original.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(original);
            }
        });
        return btn;
    }
    
    // ================= FOCUS EFFECT =================
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
    
    // ================= TABLE STYLE =================
    private void styleTable(JTable tbl) {
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0, 102, 204));
        tbl.getTableHeader().setForeground(Color.WHITE);
    }
    
    // ================= CRUD ===================
    private void themLop() {
        Lop l = new Lop(
                txtMaLop.getText(),
                txtTenLop.getText(),
                Integer.parseInt(txtSiSo.getText()),
                cboNamHoc.getSelectedItem().toString(),
                cboGVCN.getSelectedItem().toString()
        );
        dsLop.add(l);
        loadTableLop();
        clearForm();
        JOptionPane.showMessageDialog(this, "Thêm lớp thành công!");
    }

    private void suaLop() {
        if (lopDangChon == null) return;

        int c = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn sửa lớp này?",
                "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        lopDangChon.setTenLop(txtTenLop.getText());
        lopDangChon.setSiSo(Integer.parseInt(txtSiSo.getText()));

        loadTableLop();
        JOptionPane.showMessageDialog(this, "Sửa lớp thành công!");
    }

    private void xoaLop() {
        if (lopDangChon == null) return;

        int c = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa lớp này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        dsLop.remove(lopDangChon);
        loadTableLop();
        clearForm();
        JOptionPane.showMessageDialog(this, "Xóa lớp thành công!");
    }

    // ================= LOAD ===================
    private void loadTableLop() {
        modelLop.setRowCount(0);
        for (Lop l : dsLop) {
            modelLop.addRow(new Object[]{
                    l.getMaLop(),
                    l.getTenLop(),
                    l.getSiSo(),
                    l.getMaNH(),
                    l.getMaGVCN()
            });
        }
    }

    private void loadHocSinhTheoLop() {
        modelHS.setRowCount(0);
        if (lopDangChon == null) return;

        for (HocSinh hs : dsHS) {
            if (hs.getMaLop().equals(lopDangChon.getMaLop())) {
                modelHS.addRow(new Object[]{
                        hs.getMaHS(),
                        hs.getHoTen(),
                        hs.getNgaySinh(),
                        hs.getGioiTinh(),
                        hs.getDiaChi()
                });
            }
        }
    }
    
    // ===== QUAY LẠI MAIN MENU =====
    /*private void quaylai() {
        new MainMenu().setVisible(true);
        this.dispose();
    }*/

    // ================= UI FLOW ================
    private void fillForm() {
        txtMaLop.setText(lopDangChon.getMaLop());
        txtTenLop.setText(lopDangChon.getTenLop());
        txtSiSo.setText(String.valueOf(lopDangChon.getSiSo()));
        txtMaLop.setEnabled(false);
    }

    private void clearForm() {
        txtMaLop.setText("");
        txtTenLop.setText("");
        txtSiSo.setText("");

        txtMaLop.setEnabled(true);
        tblLop.clearSelection();
        modelHS.setRowCount(0);

        lopDangChon = null;
        updateButtonState();
        txtMaLop.requestFocus();
    }

    private void updateButtonState() {
        boolean dangChon = lopDangChon != null;
        btnSua.setEnabled(dangChon);
        btnXoa.setEnabled(dangChon);
    }

    /*private void addFocus(JComponent c) {
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
    
    private void addHover(JButton btn) {
        Color normal = btn.getBackground();
        btn.setFocusPainted(false);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(180, 200, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normal);
            }
        });
    }*/

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new TestFormLop().setVisible(true));
    }
}
