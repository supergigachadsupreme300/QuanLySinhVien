package GUI;

import BusinessLogicLayer.ChiTietTietBLL;
import BusinessLogicLayer.HocKyBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.ThoiKhoaBieuBLL;
import DataObject.ChiTietTiet;
import DataObject.HocKy;
import DataObject.Lop;
import DataObject.ThoiKhoaBieu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.miginfocom.swing.MigLayout;
import com.toedter.calendar.JDateChooser;

public class FormTKB extends JPanel {

    private MainMenu mainFrame;
    private ThoiKhoaBieuBLL tkbBLL;
    private ChiTietTietBLL ctBLL;   
    
    // ================= TABLE =================
    private JTable tblTKBList, tblTKBLuoi;
    private DefaultTableModel modelTKBList, modelTKBLuoi;

    // ================= FORM ==================
    private JTextField txtMaTKB;
    private JComboBox<Lop> cboLop;
    private JComboBox<HocKy> cboHocKy;
    private JDateChooser dateChooserBD, dateChooserKT;

    // ================= BUTTON ================
    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;
    private boolean dataChanged = false;
    private List<Change> bufferChanges = new ArrayList<>();
    
    // Map lưu thông tin giờ cho từng tiết
    private Map<Integer, String> tietGioMap;
    
    // ================= CONSTRUCTOR =================
    public FormTKB(MainMenu frame) {
        this.mainFrame = frame;
        this.tkbBLL = new ThoiKhoaBieuBLL();
        this.ctBLL = new ChiTietTietBLL();
        initTietGioMap();
        initUI();
        loadTableFromList(); // Load active để hiển thị
        loadComboLop();
        loadComboHocKy();
        setupAutoGenerateMaTKB();
        updateButtonState();
    }

    /**
     * HÀM RIÊNG: Khởi tạo map giờ cho các tiết học
     */
    private void initTietGioMap() {
        tietGioMap = new HashMap<>();
        tietGioMap.put(1, "07:00-07:45");
        tietGioMap.put(2, "07:50-08:35");
        tietGioMap.put(3, "08:40-09:25");
        tietGioMap.put(4, "09:45-10:30");
        tietGioMap.put(5, "10:35-11:20");
        tietGioMap.put(6, "13:00-13:45");
        tietGioMap.put(7, "13:50-14:35");
        tietGioMap.put(8, "14:40-15:25");
        tietGioMap.put(9, "15:30-16:15");
        tietGioMap.put(10, "16:20-17:05");
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
        JPanel pnlInput = new JPanel(new MigLayout("", "[]10[grow]", "[]10[]10[]"));
        
        txtMaTKB = new JTextField();
        txtMaTKB.setEditable(false); // Không cho sửa mã tự động
        
        cboLop = new JComboBox<>();
        cboHocKy = new JComboBox<>();

        pnlInput.add(new JLabel("Mã TKB:"));
        pnlInput.add(txtMaTKB, "growx, wrap");
        pnlInput.add(new JLabel("Lớp:"));
        pnlInput.add(cboLop, "growx, wrap");
        pnlInput.add(new JLabel("Học kỳ:"));
        pnlInput.add(cboHocKy, "growx");

        // --- Panel Date (Bên phải) ---
        JPanel pnlDate = new JPanel(new MigLayout("", "[]10[grow]", "[]10[]"));
        pnlDate.setBorder(BorderFactory.createTitledBorder("Thời gian áp dụng"));

        dateChooserBD = new JDateChooser();
        dateChooserBD.setDateFormatString("dd/MM/yyyy");
        dateChooserBD.setPreferredSize(new Dimension(150, 25));
        
        dateChooserKT = new JDateChooser();
        dateChooserKT.setDateFormatString("dd/MM/yyyy");
        dateChooserKT.setPreferredSize(new Dimension(150, 25));

        pnlDate.add(new JLabel("Ngày bắt đầu:"));
        pnlDate.add(dateChooserBD, "growx, wrap");
        pnlDate.add(new JLabel("Ngày kết thúc:"));
        pnlDate.add(dateChooserKT, "growx, wrap");

        pnlTop.add(pnlInput, "grow");
        pnlTop.add(pnlDate, "grow");
        add(pnlTop, "growx, wrap");
     
        // ===== BUTTON =====
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

        // ===== TABLE DANH SÁCH TKB =====
        modelTKBList = new DefaultTableModel(
            new String[]{"Mã TKB", "Lớp", "Học kỳ", "Ngày bắt đầu", "Ngày kết thúc"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        tblTKBList = new JTable(modelTKBList);
        tblTKBList.setRowHeight(25);
        styleTable(tblTKBList);
        
        JScrollPane scrollList = new JScrollPane(tblTKBList);
        scrollList.setBorder(BorderFactory.createTitledBorder("Danh sách TKB"));
        add(scrollList, "grow, wrap");

        // ===== TABLE LƯỚI TKB =====
        modelTKBLuoi = new DefaultTableModel(
            new String[]{"Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };

        tblTKBLuoi = new JTable(modelTKBLuoi);
        tblTKBLuoi.setRowHeight(45);
        styleTable(tblTKBLuoi);
        
        // Set renderer cho cột "Tiết"
        tblTKBLuoi.getColumnModel().getColumn(0).setCellRenderer(
            new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    
                    c.setFont(c.getFont().deriveFont(Font.BOLD));
                    c.setBackground(new Color(230, 240, 255));
                    setHorizontalAlignment(JLabel.CENTER);
                    setVerticalAlignment(JLabel.CENTER);
                    
                    return c;
                }
            }
        );
        
        // Set renderer cho các cột môn học
        for (int i = 1; i <= 6; i++) {
            tblTKBLuoi.getColumnModel().getColumn(i).setCellRenderer(
                new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value,
                            boolean isSelected, boolean hasFocus, int row, int column) {
                        Component c = super.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                        
                        setHorizontalAlignment(JLabel.CENTER);
                        setVerticalAlignment(JLabel.CENTER);
                        
                        if (value != null && !value.toString().isEmpty()) {
                            c.setBackground(new Color(220, 255, 220));
                        } else {
                            c.setBackground(Color.WHITE);
                        }
                        
                        return c;
                    }
                }
            );
        }

        JScrollPane scrollLuoi = new JScrollPane(tblTKBLuoi);
        scrollLuoi.setBorder(BorderFactory.createTitledBorder("Lưới thời khóa biểu"));
        add(scrollLuoi, "grow, wrap");

        // ===== EVENTS =====
        tblTKBList.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblTKBList.getSelectedRow();
            if (row >= 0) {
                fillFormFromTable(row);
                loadLuoiTKB(modelTKBList.getValueAt(row, 0).toString());
                updateButtonState();
            }
        });

        cboLop.addActionListener(e -> generateAndSetMaTKB());
        cboHocKy.addActionListener(e -> generateAndSetMaTKB());

        btnThem.addActionListener(e -> themTKB());
        btnSua.addActionListener(e -> suaTKB());
        btnXoa.addActionListener(e -> xoaTKB());
        btnClear.addActionListener(e -> clearForm());
        btnLuu.addActionListener(e -> luuTKB());

        addFocusEffect(txtMaTKB);
        addFocusEffect(cboLop);
        addFocusEffect(cboHocKy);
        addFocusEffect(dateChooserBD.getDateEditor().getUiComponent());
        addFocusEffect(dateChooserKT.getDateEditor().getUiComponent());
    }

    /**
     * HÀM RIÊNG: Lấy giờ cho tiết học
     */
    private String getGioTiet(int tiet) {
        return tietGioMap.getOrDefault(tiet, "??:??-??:??");
    }

    /**
     * HÀM RIÊNG: Thiết lập tự động tạo mã TKB
     */
    private void setupAutoGenerateMaTKB() {
        SwingUtilities.invokeLater(() -> {
            if (cboLop.getItemCount() > 0 && cboHocKy.getItemCount() > 0) {
                cboLop.setSelectedIndex(0);
                cboHocKy.setSelectedIndex(0);
                generateAndSetMaTKB();
                dateChooserBD.setDate(new java.util.Date());
                dateChooserKT.setDate(new java.util.Date());
            }
        });
    }

    /**
     * HÀM RIÊNG: Tạo và gán mã TKB
     */
    private void generateAndSetMaTKB() {
        Lop lop = (Lop) cboLop.getSelectedItem();
        HocKy hk = (HocKy) cboHocKy.getSelectedItem();
        
        if (lop != null && hk != null) {
            String maTKBAuto = generateMaTKB(lop.getMaLop(), hk.getMaHK());
            txtMaTKB.setText(maTKBAuto);
        }
    }

    /**
     * HÀM RIÊNG CẢI TIẾN: Tạo mã TKB
     * - Lấy TẤT CẢ mã từ DB (kể cả đã xóa mềm)
     * - KHÔNG tái sử dụng mã đã xóa
     */
    public String generateMaTKB(String maLop, String maHK) {
        Set<String> used = new HashSet<>();
        
        // 1. Lấy TẤT CẢ từ database (kể cả trangThai = 0)
        List<ThoiKhoaBieu> dsDB = tkbBLL.getAll(); // Cần method getAll() ở BLL
        for (ThoiKhoaBieu tkb : dsDB) {
            if (tkb.getMaTKB() != null) {
                used.add(tkb.getMaTKB());
            }
        }
        
        // 2. Xét dữ liệu từ buffer
        for (Change change : bufferChanges) {
            ThoiKhoaBieu tkb = change.tkb;
            if (tkb.getMaTKB() != null) {
                if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                    used.add(tkb.getMaTKB());
                } else if (change.action.equals("DELETE")) {
                    // KHÔNG xóa khỏi used - giữ nguyên vì soft delete không tái sử dụng mã
                    // used.remove(tkb.getMaTKB()); // Bỏ dòng này
                }
            }
        }
        
        // 3. Tạo mã cơ bản và đảm bảo độ dài không vượt quá giới hạn DB
        String baseCode = ("TKB" + maLop + maHK).replaceAll("\\s+", "");
        int maxLen = 9; // conservative max to avoid DB truncation

        // If base too long, truncate to leave room for numeric suffix
        String base = baseCode;
        if (base.length() > maxLen) base = base.substring(0, maxLen);

        String newCode = base;
        int counter = 1;

        while (used.contains(newCode)) {
            String suffix = String.valueOf(counter);
            int keep = Math.max(0, maxLen - suffix.length());
            String left = base.length() <= keep ? base : base.substring(0, keep);
            newCode = left + suffix;
            counter++;
        }

        return newCode;
    }

    // ===== TẠO BUTTON =====
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
    
    // ================= CLASS CHANGE =================
    private static class Change {
        ThoiKhoaBieu tkb;
        String action; // "ADD", "UPDATE", "DELETE"
        
        Change(ThoiKhoaBieu tkb, String action) {
            this.tkb = tkb;
            this.action = action;
        }
    }
    
    // ================= VALIDATE =================
    private boolean validateForm() {
        if (txtMaTKB.getText().trim().isEmpty()
            || cboLop.getSelectedItem() == null
            || cboHocKy.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập đầy đủ thông tin!",
                "Thiếu dữ liệu",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        java.util.Date dateBD = dateChooserBD.getDate();
        java.util.Date dateKT = dateChooserKT.getDate();
        
        if (dateBD == null || dateKT == null) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn ngày bắt đầu và ngày kết thúc!",
                "Thiếu dữ liệu",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (dateBD.after(dateKT)) {
            JOptionPane.showMessageDialog(this,
                "Ngày bắt đầu phải trước ngày kết thúc!",
                "Lỗi dữ liệu",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Kiểm tra mã TKB không trùng trong buffer
        String maTKBMoi = txtMaTKB.getText().trim();
        String currentMaTKB = null;
        if (tblTKBList.getSelectedRow() >= 0) {
            currentMaTKB = modelTKBList.getValueAt(tblTKBList.getSelectedRow(), 0).toString();
        }
        
        // Kiểm tra trong buffer (các ADD)
        for (Change change : bufferChanges) {
            if (change.action.equals("ADD") && change.tkb.getMaTKB().equals(maTKBMoi)) {
                JOptionPane.showMessageDialog(this,
                    "Mã TKB đã tồn tại trong danh sách chờ lưu!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        // Kiểm tra trong database (kể cả đã xóa mềm)
        List<ThoiKhoaBieu> dsDB = tkbBLL.getAll();
        for (ThoiKhoaBieu tkb : dsDB) {
            if (tkb.getMaTKB().equals(maTKBMoi)) {
                // Nếu đang sửa và là chính nó thì bỏ qua
                if (currentMaTKB != null && tkb.getMaTKB().equals(currentMaTKB)) {
                    continue;
                }
                JOptionPane.showMessageDialog(this,
                    "Mã TKB đã tồn tại trong hệ thống!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    // ================= CRUD =================
    private void themTKB() {
        if (!validateForm()) return;

        java.util.Date dateBD = dateChooserBD.getDate(); 
        java.util.Date dateKT = dateChooserKT.getDate(); 
        LocalDate ngayBD = dateBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
        LocalDate ngayKT = dateKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        Lop lop = (Lop) cboLop.getSelectedItem();
        HocKy hk = (HocKy) cboHocKy.getSelectedItem();

        ThoiKhoaBieu tkb = new ThoiKhoaBieu(
            txtMaTKB.getText(),
            lop.getMaLop(),
            hk.getMaHK(),
            1,
            ngayBD,
            ngayKT
        );

        modelTKBList.addRow(new Object[]{ 
            tkb.getMaTKB(), 
            tkb.getMaLop(), 
            tkb.getMaHK(), 
            tkb.getNgayBatDau(), 
            tkb.getNgayKetThuc() 
        }); 
        
        bufferChanges.add(new Change(tkb, "ADD")); 
        resetInputForm(); 
        dataChanged = true; 
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã thêm TKB thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void suaTKB() {
        int row = tblTKBList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn TKB cần sửa!");
            return;
        }
        
        if (!validateForm()) return;
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn sửa TKB này?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        String maTKBCu = modelTKBList.getValueAt(row, 0).toString();
        
        java.util.Date dateBD = dateChooserBD.getDate(); 
        java.util.Date dateKT = dateChooserKT.getDate(); 
        LocalDate ngayBD = dateBD.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
        LocalDate ngayKT = dateKT.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        
        Lop lop = (Lop) cboLop.getSelectedItem();
        HocKy hk = (HocKy) cboHocKy.getSelectedItem();

        ThoiKhoaBieu tkb = new ThoiKhoaBieu(
            txtMaTKB.getText(),
            lop.getMaLop(),
            hk.getMaHK(),
            1,
            ngayBD,
            ngayKT
        );

        modelTKBList.setValueAt(tkb.getMaLop(), row, 1); 
        modelTKBList.setValueAt(tkb.getMaHK(), row, 2); 
        modelTKBList.setValueAt(tkb.getNgayBatDau(), row, 3); 
        modelTKBList.setValueAt(tkb.getNgayKetThuc(), row, 4); 
        
        // Xóa change cũ nếu có
        bufferChanges.removeIf(c -> c.tkb.getMaTKB().equals(maTKBCu) && c.action.equals("UPDATE"));
        
        bufferChanges.add(new Change(tkb, "UPDATE")); 
        
        resetInputForm(); 
        dataChanged = true; 
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã sửa TKB thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void xoaTKB() {
        int row = tblTKBList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn TKB cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn xóa TKB này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;
        
        String maTKB = modelTKBList.getValueAt(row, 0).toString(); 
        modelTKBList.removeRow(row); 
        
        ThoiKhoaBieu tkb = new ThoiKhoaBieu(); 
        tkb.setMaTKB(maTKB); 
        
        // KHÔNG xóa change cũ - giữ nguyên vì soft delete không tái sử dụng mã
        // bufferChanges.removeIf(c -> c.tkb.getMaTKB().equals(maTKB));
        
        bufferChanges.add(new Change(tkb, "DELETE")); 
        
        resetInputForm(); 
        dataChanged = true; 
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã xóa TKB thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    // ================= UI FLOW ================
    private void fillFormFromTable(int row) {
        txtMaTKB.setText(modelTKBList.getValueAt(row, 0).toString());
        
        String maLop = modelTKBList.getValueAt(row, 1).toString();
        for (int i = 0; i < cboLop.getItemCount(); i++) {
            if (cboLop.getItemAt(i).getMaLop().equals(maLop)) {
                cboLop.setSelectedIndex(i);
                break;
            }
        }
        
        String maHK = modelTKBList.getValueAt(row, 2).toString();
        for (int i = 0; i < cboHocKy.getItemCount(); i++) {
            if (cboHocKy.getItemAt(i).getMaHK().equals(maHK)) {
                cboHocKy.setSelectedIndex(i);
                break;
            }
        }
        
        Object ngayBDObj = modelTKBList.getValueAt(row, 3);
        Object ngayKTObj = modelTKBList.getValueAt(row, 4);
        
        if (ngayBDObj != null && ngayBDObj instanceof LocalDate) { 
            dateChooserBD.setDate(java.sql.Date.valueOf((LocalDate) ngayBDObj)); 
        } 
        if (ngayKTObj != null && ngayKTObj instanceof LocalDate) { 
            dateChooserKT.setDate(java.sql.Date.valueOf((LocalDate) ngayKTObj)); 
        }
        
        txtMaTKB.setEnabled(false);
        cboLop.setEnabled(false);
        cboHocKy.setEnabled(false);
    }
    
    private void resetInputForm() {
        txtMaTKB.setEnabled(true);
        cboLop.setEnabled(true);
        cboHocKy.setEnabled(true);

        generateAndSetMaTKB();
        
        dateChooserBD.setDate(new java.util.Date());
        dateChooserKT.setDate(new java.util.Date());

        tblTKBList.clearSelection();
        modelTKBLuoi.setRowCount(0);
        updateButtonState();
        txtMaTKB.requestFocus();
    }

    private void clearForm() {
        resetInputForm();
        bufferChanges.clear();
        dataChanged = false;
        updateSaveButtonState();
        loadTableFromList();
        // reload combo to reflect any class changes
        loadComboLop();
    }
    
    private void luuTKB() {
        try {
            // Execute buffered changes one by one; abort if any operation fails
            for (Change change : bufferChanges) {
                String res = null;
                switch (change.action) {
                    case "ADD":
                        res = tkbBLL.themThoiKhoaBieu(change.tkb);
                        break;
                    case "UPDATE":
                        res = tkbBLL.suaThoiKhoaBieu(change.tkb);
                        break;
                    case "DELETE":
                        res = tkbBLL.xoaThoiKhoaBieu(change.tkb.getMaTKB()); // Soft delete
                        break;
                }
                if (res == null || !res.toLowerCase().contains("thành công") && !res.toLowerCase().contains("thanh cong")) {
                    // Do not clear buffer; notify user and abort remaining saves
                    JOptionPane.showMessageDialog(this,
                        "Lưu thất bại: " + (res == null ? "Không rõ lỗi" : res),
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // All ops succeeded
            bufferChanges.clear();
            JOptionPane.showMessageDialog(this, "Đã lưu thay đổi thành công!");
            dataChanged = false;
            updateSaveButtonState();
            loadTableFromList(); // Load lại chỉ các active
            resetInputForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lưu dữ liệu: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateButtonState() {
        boolean dangChon = tblTKBList.getSelectedRow() >= 0;
        btnSua.setEnabled(dangChon);
        btnXoa.setEnabled(dangChon);
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

    private void loadTableFromList() {
        modelTKBList.setRowCount(0);
        for (ThoiKhoaBieu tkb : tkbBLL.getAllActive()) { // Chỉ lấy active để hiển thị
            modelTKBList.addRow(new Object[]{
                tkb.getMaTKB(),
                tkb.getMaLop(),
                tkb.getMaHK(),
                tkb.getNgayBatDau(),
                tkb.getNgayKetThuc()
            });
        }
    }
    
    private void loadComboLop() {
        cboLop.removeAllItems();
        for (Lop l : new LopBLL().getAllActive()) {
            cboLop.addItem(l);
        }
    }

    private void loadComboHocKy() {
        cboHocKy.removeAllItems();
        for (HocKy hk : new HocKyBLL().getAllActive()) {
            cboHocKy.addItem(hk);
        }
    }

    private void loadLuoiTKB(String maTKB) {
        modelTKBLuoi.setRowCount(0);

        List<ChiTietTiet> dsChiTiet = ctBLL.getByMaTKB(maTKB);
        
        for (int tiet = 1; tiet <= 10; tiet++) {
            String gioTiet = getGioTiet(tiet);
            String tietLabel = "<html><center>Tiết " + tiet + "<br>(" + gioTiet + ")</center></html>";
            modelTKBLuoi.addRow(new Object[]{tietLabel, "", "", "", "", "", ""});
        }

        for (ChiTietTiet ct : dsChiTiet) {
            int colIndex = switch (ct.getThu()) {
                case "Thứ 2" -> 1;
                case "Thứ 3" -> 2;
                case "Thứ 4" -> 3;
                case "Thứ 5" -> 4;
                case "Thứ 6" -> 5;
                case "Thứ 7" -> 6;
                default -> -1;
            };

            if (colIndex != -1 && ct.getTiet() <= 10) {
                String value = "<html><center>" + ct.getMaMon() + "<br>P." + ct.getPhongHoc() + "</center></html>";
                modelTKBLuoi.setValueAt(value, ct.getTiet() - 1, colIndex);
            }
        }
    }
}