package GUI;

/**
 * FORM QUẢN LÝ LỚP
 * - Tự động tạo mã lớp khi chọn khối
 * - Xử lý đúng soft delete (không tái sử dụng mã đã xóa mềm)
 *
 */

import BusinessLogicLayer.GiaoVienBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.HocSinhBLL;
import BusinessLogicLayer.NamHocBLL;
import DAO.DatabaseConnect;
import DataObject.GiaoVien;
import DataObject.Lop;
import DataObject.HocSinh;
import DataObject.NamHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.miginfocom.swing.MigLayout;

public class FormLop extends JPanel {

    private MainMenu mainFrame;
    private Connection con;
    private LopBLL lopBLL;
    private HocSinhBLL hocSinhBLL;
    private NamHocBLL namHocBLL;
    private GiaoVienBLL giaoVienBLL;

    /* ================= TABLE ================= */
    private JTable tblLop, tblHS;
    private DefaultTableModel modelLop, modelHS;

    /* ================= FORM ================= */
    private JTextField txtMaLop, txtTenLop, txtSiSo;
    private JComboBox<NamHoc> cboNamHoc; 
    private JComboBox<GiaoVien> cboGVCN;
    private JComboBox<Integer> cboKhoi;

    /* ================= BUTTON ================= */
    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;
    private boolean dataChanged = false;
    
    private List<Change> bufferChanges = new ArrayList<>();

    /* ================= CONSTRUCTOR ================= */
    public FormLop(MainMenu frame) {
        DatabaseConnect db = new DatabaseConnect();
        this.con = db.openConnection();
        this.lopBLL = new LopBLL(con); 
        this.hocSinhBLL = new HocSinhBLL(con);
        this.namHocBLL = new NamHocBLL();
        this.giaoVienBLL = new GiaoVienBLL();
        this.mainFrame = frame;
        initUI();
        loadTableLop(); 
        loadComboNamHoc(); 
        loadComboGiaoVien();
        setupAutoGenerateMaLop();
        txtSiSo.setText("0");
        txtSiSo.setEditable(false);
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
        txtMaLop.setEditable(false); // Không cho sửa mã tự động
        
        txtTenLop = new JTextField();
        txtSiSo   = new JTextField();
        cboNamHoc = new JComboBox<>();
        cboGVCN   = new JComboBox<>();
        cboKhoi = new JComboBox<>(new Integer[]{6, 7, 8, 9});

        pnlForm.add(new JLabel("Mã lớp:"));
        pnlForm.add(txtMaLop, "growx");
        pnlForm.add(new JLabel("Tên lớp:"));
        pnlForm.add(txtTenLop, "growx, wrap");
        pnlForm.add(new JLabel("Khối:")); 
        pnlForm.add(cboKhoi, "growx, wrap");

        pnlForm.add(new JLabel("Sĩ số:"));
        pnlForm.add(txtSiSo, "growx");
        pnlForm.add(new JLabel("Năm học:"));
        pnlForm.add(cboNamHoc, "growx, wrap");

        pnlForm.add(new JLabel("GVCN:"));
        pnlForm.add(cboGVCN, "growx, span 3");

        add(pnlForm, "growx, wrap");

        /* ===== BUTTON ===== */
        JPanel pnlBtn = new JPanel();

        btnThem  = createButton("Thêm", new Color(34, 139, 34));
        btnSua   = createButton("Sửa", new Color(255, 140, 0));
        btnXoa   = createButton("Xóa", new Color(220, 20, 60));
        btnClear = createButton("Làm mới", new Color(70, 130, 180));
        btnLuu = createButton("Lưu", new Color(150, 150, 150)); 
        btnLuu.setEnabled(false);
        
        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
        pnlBtn.add(btnLuu);
        
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
        split.setResizeWeight(0.45);
        split.setDividerSize(8);
        add(split, "grow");

        /* ===== EVENTS ===== */
        btnThem.addActionListener(e -> themLop());
        btnSua.addActionListener(e -> suaLop());
        btnXoa.addActionListener(e -> xoaLop());
        btnClear.addActionListener(e -> clearForm());
        btnLuu.addActionListener(e -> luuLop());

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
        addFocusEffect(cboNamHoc);
        addFocusEffect(cboGVCN);

        updateButtonState();
    }

    /**
     * HÀM RIÊNG: Thiết lập tự động tạo mã lớp khi chọn khối
     */
    private void setupAutoGenerateMaLop() {
        cboKhoi.addActionListener(e -> {
            generateAndSetMaLop();
        });
        
        SwingUtilities.invokeLater(() -> {
            if (cboKhoi.getItemCount() > 0) {
                cboKhoi.setSelectedIndex(0);
                generateAndSetMaLop();
            }
        });
    }

    /**
     * HÀM RIÊNG: Tạo và gán mã lớp dựa trên khối đã chọn
     */
    private void generateAndSetMaLop() {
        Integer khoi = (Integer) cboKhoi.getSelectedItem();
        if (khoi != null) {
            String maLopAuto = generateMaLop(khoi);
            txtMaLop.setText(maLopAuto);
            txtMaLop.setEnabled(false);
        }
    }

    /**
     * HÀM RIÊNG CẢI TIẾN: Tạo mã lớp dựa trên khối
     * - Xét TẤT CẢ dữ liệu từ database (kể cả đã xóa mềm)
     * - KHÔNG tái sử dụng mã đã xóa
     * @param khoi khối lớp (6,7,8,9)
     * @return mã lớp dạng "6A1", "7A2", ...
     */
    public String generateMaLop(int khoi) {
        Set<Integer> used = new HashSet<>();
        
        // 1. Lấy TẤT CẢ dữ liệu từ database (kể cả trangThai = 0)
        List<Lop> dsDB = lopBLL.getAll(); // Cần thêm method getAll() ở BLL
        for (Lop l : dsDB) {
            if (l.getMaLop() != null && l.getMaLop().matches(khoi + "A\\d+")) {
                try {
                    int so = Integer.parseInt(l.getMaLop().substring(2));
                    used.add(so);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    // Bỏ qua nếu định dạng không đúng
                }
            }
        }
        
        // 2. Xét dữ liệu từ buffer
        for (Change change : bufferChanges) {
            Lop l = change.lop;
            if (l.getMaLop() != null && l.getMaLop().matches(khoi + "A\\d+")) {
                try {
                    int so = Integer.parseInt(l.getMaLop().substring(2));
                    
                    if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                        used.add(so);
                    } else if (change.action.equals("DELETE")) {
                        // KHÔNG xóa khỏi used - giữ nguyên vì soft delete không tái sử dụng mã
                        // used.remove(so); // Bỏ dòng này
                    }
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                    // Bỏ qua nếu định dạng không đúng
                }
            }
        }
        
        // 3. Tìm số nhỏ nhất còn trống từ 1-15 (có thể mở rộng)
        for (int i = 1; i <= 15; i++) {
            if (!used.contains(i)) {
                return khoi + "A" + i;
            }
        }
        
        // 4. Nếu đã đủ 15 số, tạo số tiếp theo
        return khoi + "A" + (used.size() + 1);
    }

    //=============== VALIDATE ========================//
    private boolean validateForm() {
        if (txtMaLop.getText().trim().isEmpty()
                || txtTenLop.getText().trim().isEmpty()
                || cboNamHoc.getSelectedItem() == null
                || cboGVCN.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đầy đủ thông tin!",
                    "Thiếu dữ liệu",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }

        // Kiểm tra mã lớp không trùng trong buffer
        String maLopMoi = txtMaLop.getText().trim();
        String currentMaLop = null;
        if (tblLop.getSelectedRow() >= 0) {
            currentMaLop = modelLop.getValueAt(tblLop.getSelectedRow(), 0).toString();
        }
        
        // Kiểm tra trong buffer (các ADD)
        for (Change change : bufferChanges) {
            if (change.action.equals("ADD") && change.lop.getMaLop().equals(maLopMoi)) {
                JOptionPane.showMessageDialog(
                        this,
                        "Mã lớp đã tồn tại trong danh sách chờ lưu!",
                        "Lỗi trùng mã",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        }
        
        // Kiểm tra trong database (kể cả đã xóa mềm)
        List<Lop> dsDB = lopBLL.getAll(); // Cần thêm method getAll() ở BLL
        for (Lop l : dsDB) {
            if (l.getMaLop().equals(maLopMoi)) {
                // Nếu đang sửa và là chính nó thì bỏ qua
                if (currentMaLop != null && l.getMaLop().equals(currentMaLop)) {
                    continue;
                }
                JOptionPane.showMessageDialog(
                        this,
                        "Mã lớp đã tồn tại trong hệ thống!",
                        "Lỗi trùng mã",
                        JOptionPane.ERROR_MESSAGE
                );
                return false;
            }
        }
        
        return true;
    }

    /* =================================================
       ================= CRUD ==========================
       ================================================= */
    private void themLop() {
        if (!validateForm()) return;
        
        Lop lop = getLopFromForm();
        modelLop.addRow(new Object[]{ 
            lop.getMaLop(), 
            lop.getTenLop(), 
            lop.getSiSo(), 
            lop.getMaNH(), 
            lop.getMaGVCN() 
        }); 
        bufferChanges.add(new Change(lop, "ADD"));
        resetInputForm();
        dataChanged = true;
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã thêm lớp thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void suaLop() {
        int row = tblLop.getSelectedRow();
        if (row < 0) { 
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần sửa!"); 
            return; 
        }
        
        if (!validateForm()) return;
        
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn sửa lớp này?",
                "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;
        
        String maLopCu = modelLop.getValueAt(row, 0).toString();
        Lop lop = getLopFromForm();
        
        // Cập nhật trên table
        modelLop.setValueAt(lop.getTenLop(), row, 1); 
        modelLop.setValueAt(lop.getSiSo(), row, 2); 
        modelLop.setValueAt(lop.getMaNH(), row, 3); 
        modelLop.setValueAt(lop.getMaGVCN(), row, 4); 
        
        // Xóa change cũ nếu có (đối với lớp này)
        bufferChanges.removeIf(c -> c.lop.getMaLop().equals(maLopCu) && c.action.equals("UPDATE"));
        
        bufferChanges.add(new Change(lop, "UPDATE"));
        dataChanged = true;
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã sửa lớp thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void xoaLop() {
        int row = tblLop.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lớp cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa lớp này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            String maLop = modelLop.getValueAt(row, 0).toString();
            modelLop.removeRow(row);
            
            Lop lop = new Lop();
            lop.setMaLop(maLop);
            
            // KHÔNG xóa các change cũ - giữ nguyên vì soft delete không tái sử dụng mã
            // bufferChanges.removeIf(c -> c.lop.getMaLop().equals(maLop));
            
            bufferChanges.add(new Change(lop, "DELETE"));
            resetInputForm();
            dataChanged = true;
            updateSaveButtonState();
            
            JOptionPane.showMessageDialog(this, 
                "Đã xóa lớp thành công! Nhấn 'Lưu' để lưu vào CSDL.",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /* =================================================
       ================= UI FLOW =======================
       ================================================= */

    private void fillFormFromTable(int row) {
        String maLop = modelLop.getValueAt(row, 0).toString();
        txtMaLop.setText(maLop);
        txtMaLop.setEnabled(false);
        
        txtTenLop.setText(modelLop.getValueAt(row, 1).toString());
        txtSiSo.setText(modelLop.getValueAt(row, 2).toString());
        
        // Xác định khối từ mã lớp
        if (maLop != null && maLop.length() > 0) {
            try {
                int khoi = Integer.parseInt(maLop.substring(0, 1));
                cboKhoi.setSelectedItem(khoi);
            } catch (NumberFormatException e) {
                // Bỏ qua nếu không lấy được khối
            }
        }
        
        String maNH = modelLop.getValueAt(row, 3).toString();
        for (int i = 0; i < cboNamHoc.getItemCount(); i++) {
            if (cboNamHoc.getItemAt(i).getMaNH().equals(maNH)) { 
                cboNamHoc.setSelectedIndex(i); 
                break;
            }
        }
        
        String maGV = modelLop.getValueAt(row, 4).toString(); 
        for (int i = 0; i < cboGVCN.getItemCount(); i++) { 
            if (cboGVCN.getItemAt(i).getMaGV().equals(maGV)) { 
                cboGVCN.setSelectedIndex(i); 
                break; 
            } 
        }
        
        txtSiSo.setEditable(false);
        loadHocSinhByLop(maLop);
        cboKhoi.setEnabled(false);
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
    private void resetInputForm() {
        txtMaLop.setText(generateMaLop((Integer) cboKhoi.getSelectedItem()));
        txtTenLop.setText("");
        txtSiSo.setText("0");
        cboNamHoc.setSelectedIndex(-1);
        cboGVCN.setSelectedIndex(-1);
        cboKhoi.setEnabled(true);
        tblLop.clearSelection();
        modelHS.setRowCount(0);
        updateButtonState();
        txtTenLop.requestFocus();
    }

    private void clearForm() {
        resetInputForm();
        bufferChanges.clear();
        dataChanged = false;
        updateSaveButtonState();
        loadTableLop();
    }

    private Lop getLopFromForm() {
        Lop lop = new Lop();
        lop.setMaLop(txtMaLop.getText().trim());
        lop.setTenLop(txtTenLop.getText().trim());
        try {
            lop.setSiSo(Integer.parseInt(txtSiSo.getText().trim()));
        } catch (NumberFormatException e) {
            lop.setSiSo(0);
        }
        NamHoc nh = (NamHoc) cboNamHoc.getSelectedItem();
        lop.setMaNH(nh != null ? nh.getMaNH() : null);
        GiaoVien gv = (GiaoVien) cboGVCN.getSelectedItem();
        lop.setMaGVCN(gv != null ? gv.getMaGV() : null);
        // Khi tạo/ghi lớp từ form, mặc định đặt trạng thái là active (1)
        lop.setTrangThai(1);
        return lop;
    }

    private void loadComboNamHoc() {
        cboNamHoc.removeAllItems();
        for (NamHoc nh : namHocBLL.getAllActive()) {
            cboNamHoc.addItem(nh);
        }
    }

    private void loadComboGiaoVien() {
        cboGVCN.removeAllItems();
        for (GiaoVien gv : giaoVienBLL.getAll()) {
            cboGVCN.addItem(gv);
        }
    }

    private void updateButtonState() {
        boolean selected = tblLop.getSelectedRow() >= 0;
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

    private static class Change {
        Lop lop;
        String action; // "ADD", "UPDATE", "DELETE"
        Change(Lop lop, String action) {
            this.lop = lop;
            this.action = action;
        }
    }
    
    private void luuLop() {
        try {
            for (Change change : bufferChanges) { 
                switch (change.action) { 
                    case "ADD": 
                        lopBLL.themLop(change.lop); 
                        break; 
                    case "UPDATE": 
                        lopBLL.suaLop(change.lop); 
                        break; 
                    case "DELETE": 
                        lopBLL.xoaLop(change.lop.getMaLop()); // Soft delete
                        break; 
                } 
            } 
            bufferChanges.clear();
            JOptionPane.showMessageDialog(this, "Đã lưu thay đổi thành công!");
            dataChanged = false;
            updateSaveButtonState();
            loadTableLop(); // Load lại chỉ các active
            resetInputForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Lỗi khi lưu dữ liệu: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void loadTableLop() {
        modelLop.setRowCount(0);
        for (Lop l : lopBLL.getAllActive()) { // Chỉ lấy active để hiển thị
            // Lấy tên GVCN từ combo để hiển thị
            String tenGVCN = getTenGVFromMa(l.getMaGVCN());
            
            modelLop.addRow(new Object[]{
                l.getMaLop(),
                l.getTenLop(),
                l.getSiSo(),
                l.getMaNH(),
                tenGVCN
            });
        }
    }
    
    private String getTenGVFromMa(String maGV) {
        for (int i = 0; i < cboGVCN.getItemCount(); i++) {
            GiaoVien gv = cboGVCN.getItemAt(i);
            if (gv.getMaGV().equals(maGV)) {
                return gv.getHoTen();
            }
        }
        return maGV;
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