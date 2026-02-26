package GUI;

import BusinessLogicLayer.PhanCongBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.GiaoVienBLL;
import BusinessLogicLayer.MonBLL;
import BusinessLogicLayer.NamHocBLL;
import DataObject.PhanCong;
import DataObject.Lop;
import DataObject.GiaoVien;
import DataObject.Mon;
import DataObject.NamHoc;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.miginfocom.swing.MigLayout;

public class FormPhanCong extends JPanel {

    private MainMenu mainFrame;
    private PhanCongBLL pcBLL;
    private boolean dataChanged = false;
    private List<Change> bufferChanges = new ArrayList<>();

    // FORM
    private JTextField txtMaPC, txtGhiChu;
    private JComboBox<Lop> cboLop;
    private JComboBox<Mon> cboMon;
    private JComboBox<GiaoVien> cboGV;
    private JComboBox<NamHoc> cboNamHoc;

    // BUTTON
    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;

    // TABLE
    private JTable tblPhanCong;
    private DefaultTableModel modelPhanCong;

    public FormPhanCong(MainMenu frame) {
        this.mainFrame = frame;
        this.pcBLL = new PhanCongBLL();
        initUI();
        loadTablePhanCong();
        loadComboLop();
        loadComboMon();
        loadComboGV();
        loadComboNamHoc();
        setupAutoGenerateMaPC();
        updateButtonState();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ PHÂN CÔNG GIẢNG DẠY", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlForm = new JPanel(new MigLayout("insets 15", "[]15[grow]30[]15[grow]", "[]10[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin phân công"));

        txtMaPC = new JTextField();
        // Cho phép sửa mã PC nếu cần
        txtMaPC.setEditable(true);
        
        cboLop = new JComboBox<>();
        cboMon = new JComboBox<>();
        cboGV = new JComboBox<>();
        cboNamHoc = new JComboBox<>();
        txtGhiChu = new JTextField();

        pnlForm.add(new JLabel("Mã PC:"));
        pnlForm.add(txtMaPC, "growx");
        pnlForm.add(new JLabel("Lớp:"));
        pnlForm.add(cboLop, "growx, wrap");

        pnlForm.add(new JLabel("Môn học:"));
        pnlForm.add(cboMon, "growx");
        pnlForm.add(new JLabel("Giáo viên:"));
        pnlForm.add(cboGV, "growx, wrap");

        pnlForm.add(new JLabel("Năm học:"));
        pnlForm.add(cboNamHoc, "growx");
        pnlForm.add(new JLabel("Ghi chú:"));
        pnlForm.add(txtGhiChu, "growx");

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

        modelPhanCong = new DefaultTableModel(
            new String[]{"Mã PC", "Lớp", "Môn học", "Giáo viên", "Năm học", "Ghi chú"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblPhanCong = new JTable(modelPhanCong);
        styleTable(tblPhanCong);
        JScrollPane spPC = new JScrollPane(tblPhanCong);
        spPC.setBorder(BorderFactory.createTitledBorder("Danh sách phân công"));
        add(spPC, "grow");

        // EVENTS
        // Sự kiện khi thay đổi các combo box - tự động tạo gợi ý mã mới
        cboLop.addActionListener(e -> suggestMaPC());
        cboMon.addActionListener(e -> suggestMaPC());
        cboGV.addActionListener(e -> suggestMaPC());
        cboNamHoc.addActionListener(e -> suggestMaPC());

        btnThem.addActionListener(e -> themPhanCong());
        btnSua.addActionListener(e -> suaPhanCong());
        btnXoa.addActionListener(e -> xoaPhanCong());
        btnClear.addActionListener(e -> clearForm());
        btnLuu.addActionListener(e -> luuPhanCong());

        tblPhanCong.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = tblPhanCong.getSelectedRow();
            if (row >= 0) {
                fillFormFromTable(row);
                updateButtonState();
            }
        });

        addFocusEffect(txtMaPC);
        addFocusEffect(txtGhiChu);
    }

    /**
     * HÀM RIÊNG: Thiết lập tự động tạo mã phân công
     */
    private void setupAutoGenerateMaPC() {
        // Tạo mã tự động khi form được hiển thị
        SwingUtilities.invokeLater(() -> {
            if (cboLop.getItemCount() > 0 && cboMon.getItemCount() > 0 
                && cboGV.getItemCount() > 0 && cboNamHoc.getItemCount() > 0) {
                cboLop.setSelectedIndex(0);
                cboMon.setSelectedIndex(0);
                cboGV.setSelectedIndex(0);
                cboNamHoc.setSelectedIndex(0);
                suggestMaPC();
            }
        });
    }

    /**
     * HÀM RIÊNG: Gợi ý mã phân công (không tự động set, chỉ gợi ý)
     */
    private void suggestMaPC() {
        Lop lop = (Lop) cboLop.getSelectedItem();
        Mon mon = (Mon) cboMon.getSelectedItem();
        GiaoVien gv = (GiaoVien) cboGV.getSelectedItem();
        NamHoc nh = (NamHoc) cboNamHoc.getSelectedItem();
        
        if (lop != null && mon != null && gv != null && nh != null) {
            String maPCSuggest = suggestMaPC(lop.getMaLop(), mon.getMaMon(), gv.getMaGV(), nh.getMaNH());
            // Chỉ gợi ý nếu text field đang trống
            if (txtMaPC.getText().trim().isEmpty()) {
                txtMaPC.setText(maPCSuggest);
            }
        }
    }

    /**
     * HÀM RIÊNG CẢI TIẾN: Tạo gợi ý mã phân công
     * - Xét TẤT CẢ dữ liệu trong DB (kể cả đã xóa mềm)
     */
    public String suggestMaPC(String maLop, String maMon, String maGV, String maNH) {
        Set<String> used = new HashSet<>();
        
        // 1. Lấy TẤT CẢ dữ liệu từ database (kể cả đã xóa mềm)
        List<PhanCong> dsDB = pcBLL.getAll(); // Cần thêm method getAll() ở BLL
        for (PhanCong pc : dsDB) {
            if (pc.getMaPC() != null) {
                used.add(pc.getMaPC());
            }
        }
        
        // 2. Xét dữ liệu từ buffer
        for (Change change : bufferChanges) {
            PhanCong pc = change.pc;
            if (pc.getMaPC() != null) {
                if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                    used.add(pc.getMaPC());
                } else if (change.action.equals("DELETE")) {
                    // Nếu là DELETE, KHÔNG xóa khỏi used vì soft delete vẫn giữ mã
                    // used.remove(pc.getMaPC()); // Bỏ dòng này
                }
            }
        }
        
        // 3. Tìm số nhỏ nhất còn trống
        int counter = 1;
        while (true) {
            String newCode = String.format("PC%03d", counter);
            if (!used.contains(newCode)) {
                return newCode;
            }
            counter++;
        }
    }

    /**
     * HÀM RIÊNG: Kiểm tra phân công đã tồn tại chưa (kể cả đã xóa mềm)
     */
    private boolean isPhanCongExist(String maLop, String maMon, String maGV, String maNH, String currentMaPC) {
        // 1. Kiểm tra trong database (kể cả đã xóa mềm)
        List<PhanCong> dsDB = pcBLL.getAll(); // Cần thêm method getAll() ở BLL
        for (PhanCong pc : dsDB) {
            if (pc.getMaLop().equals(maLop) 
                && pc.getMaMon().equals(maMon) 
                && pc.getMaGV().equals(maGV) 
                && pc.getMaNam().equals(maNH)) {
                // Nếu đang sửa và là chính nó thì bỏ qua
                if (currentMaPC != null && pc.getMaPC().equals(currentMaPC)) {
                    continue;
                }
                return true;
            }
        }
        
        // 2. Kiểm tra trong buffer
        for (Change change : bufferChanges) {
            PhanCong pc = change.pc;
            if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                if (pc.getMaLop().equals(maLop) 
                    && pc.getMaMon().equals(maMon) 
                    && pc.getMaGV().equals(maGV) 
                    && pc.getMaNam().equals(maNH)) {
                    // Nếu đang sửa và là chính nó thì bỏ qua
                    if (currentMaPC != null && pc.getMaPC().equals(currentMaPC)) {
                        continue;
                    }
                    return true;
                }
            }
        }
        
        return false;
    }

    // CRUD
    private boolean validateForm() {
        if (txtMaPC.getText().trim().isEmpty()
            || cboLop.getSelectedItem() == null
            || cboMon.getSelectedItem() == null
            || cboGV.getSelectedItem() == null
            || cboNamHoc.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập đầy đủ thông tin phân công!",
                "Thiếu dữ liệu",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Kiểm tra mã PC không trùng trong buffer
        String maPCMoi = txtMaPC.getText().trim();
        String currentMaPC = null;
        if (tblPhanCong.getSelectedRow() >= 0) {
            currentMaPC = modelPhanCong.getValueAt(tblPhanCong.getSelectedRow(), 0).toString();
        }
        
        // Kiểm tra trong buffer (các ADD)
        for (Change change : bufferChanges) {
            if (change.action.equals("ADD") && change.pc.getMaPC().equals(maPCMoi)) {
                JOptionPane.showMessageDialog(this,
                    "Mã phân công đã tồn tại trong danh sách chờ lưu!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        // Kiểm tra trong database (kể cả đã xóa mềm)
        List<PhanCong> dsDB = pcBLL.getAll();
        for (PhanCong pc : dsDB) {
            if (pc.getMaPC().equals(maPCMoi)) {
                // Nếu đang sửa và là chính nó thì bỏ qua
                if (currentMaPC != null && pc.getMaPC().equals(currentMaPC)) {
                    continue;
                }
                JOptionPane.showMessageDialog(this,
                    "Mã phân công đã tồn tại trong hệ thống!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Kiểm tra ràng buộc: 1 GV không thể dạy cùng môn, cùng lớp, cùng năm học 2 lần
        Lop lop = (Lop) cboLop.getSelectedItem();
        Mon mon = (Mon) cboMon.getSelectedItem();
        GiaoVien gv = (GiaoVien) cboGV.getSelectedItem();
        NamHoc nh = (NamHoc) cboNamHoc.getSelectedItem();
        
        if (isPhanCongExist(lop.getMaLop(), mon.getMaMon(), gv.getMaGV(), nh.getMaNH(), currentMaPC)) {
            JOptionPane.showMessageDialog(this,
                "Phân công này đã tồn tại!\n"
                + "Giáo viên " + gv.getHoTen() + " đã được phân công dạy môn " + mon.getTenMon() 
                + " ở lớp " + lop.getTenLop() + " trong năm học " + nh.getTenNH() + ".",
                "Lỗi trùng phân công",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void themPhanCong() {
        if (!validateForm()) return;        
        PhanCong pc = getPhanCongFromForm();
        modelPhanCong.addRow(new Object[]{
            pc.getMaPC(), pc.getMaLop(), pc.getMaMon(), pc.getMaGV(), pc.getMaNam(), pc.getGhiChu()
        });
        bufferChanges.add(new Change(pc, "ADD"));
        resetInputForm();
        dataChanged = true;
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã thêm phân công thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void suaPhanCong() {
        int row = tblPhanCong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phân công cần sửa!");
            return;
        }
        if (!validateForm()) return;
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn sửa phân công này?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;
        
        String maPCCu = modelPhanCong.getValueAt(row, 0).toString();
        PhanCong pc = getPhanCongFromForm();
        
        modelPhanCong.setValueAt(pc.getMaLop(), row, 1);
        modelPhanCong.setValueAt(pc.getMaMon(), row, 2);
        modelPhanCong.setValueAt(pc.getMaGV(), row, 3);
        modelPhanCong.setValueAt(pc.getMaNam(), row, 4);
        modelPhanCong.setValueAt(pc.getGhiChu(), row, 5);
        
        // Xóa change cũ nếu có
        bufferChanges.removeIf(c -> c.pc.getMaPC().equals(maPCCu) && c.action.equals("UPDATE"));
        
        bufferChanges.add(new Change(pc, "UPDATE"));
        resetInputForm();
        dataChanged = true;
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã sửa phân công thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void xoaPhanCong() {
        int row = tblPhanCong.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phân công cần xóa!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn xóa phân công này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;
        
        String maPC = modelPhanCong.getValueAt(row, 0).toString();
        modelPhanCong.removeRow(row);
        
        PhanCong pc = new PhanCong();
        pc.setMaPC(maPC);
        
        // KHÔNG xóa các change cũ, vì soft delete cần giữ mã
        // bufferChanges.removeIf(c -> c.pc.getMaPC().equals(maPC));
        
        bufferChanges.add(new Change(pc, "DELETE"));
        resetInputForm();
        dataChanged = true;
        updateSaveButtonState();
        
        JOptionPane.showMessageDialog(this, 
            "Đã xóa phân công thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void luuPhanCong() {
        try {
            for (Change change : bufferChanges) {
                switch (change.action) {
                    case "ADD": 
                        pcBLL.themPhanCong(change.pc); 
                        break;
                    case "UPDATE": 
                        pcBLL.suaPhanCong(change.pc); 
                        break;
                    case "DELETE": 
                        pcBLL.xoaPhanCong(change.pc.getMaPC()); // Soft delete
                        break;
                }
            }
            bufferChanges.clear();
            JOptionPane.showMessageDialog(this, "Đã lưu thay đổi thành công!");
            dataChanged = false;
            updateSaveButtonState();
            loadTablePhanCong(); // Load lại chỉ các active
            resetInputForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lưu dữ liệu: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // UI Flow
    private void fillFormFromTable(int row) {
        txtMaPC.setText(modelPhanCong.getValueAt(row, 0).toString());
        txtMaPC.setEnabled(true);
        txtGhiChu.setText(modelPhanCong.getValueAt(row, 5).toString());

        String maLop = modelPhanCong.getValueAt(row, 1).toString();
        for (int i = 0; i < cboLop.getItemCount(); i++) {
            if (cboLop.getItemAt(i).getMaLop().equals(maLop)) {
                cboLop.setSelectedIndex(i); break;
            }
        }

        String maMon = modelPhanCong.getValueAt(row, 2).toString();
        for (int i = 0; i < cboMon.getItemCount(); i++) {
            if (cboMon.getItemAt(i).getMaMon().equals(maMon)) {
                cboMon.setSelectedIndex(i); break;
            }
        }

        String maGV = modelPhanCong.getValueAt(row, 3).toString();
        for (int i = 0; i < cboGV.getItemCount(); i++) {
            if (cboGV.getItemAt(i).getMaGV().equals(maGV)) {
                cboGV.setSelectedIndex(i); break;
            }
        }

        String maNH = modelPhanCong.getValueAt(row, 4).toString();
        for (int i = 0; i < cboNamHoc.getItemCount(); i++) {
            if (cboNamHoc.getItemAt(i).getMaNH().equals(maNH)) {
                cboNamHoc.setSelectedIndex(i); break;
            }
        }
        
        cboLop.setEnabled(true);
        cboMon.setEnabled(true);
        cboGV.setEnabled(true);
        cboNamHoc.setEnabled(true);
    }

    private PhanCong getPhanCongFromForm() {
        PhanCong pc = new PhanCong();
        pc.setMaPC(txtMaPC.getText().trim());
        pc.setMaLop(((Lop)cboLop.getSelectedItem()).getMaLop());
        pc.setMaMon(((Mon)cboMon.getSelectedItem()).getMaMon());
        pc.setMaGV(((GiaoVien)cboGV.getSelectedItem()).getMaGV());
        pc.setMaNam(((NamHoc)cboNamHoc.getSelectedItem()).getMaNH());
        pc.setGhiChu(txtGhiChu.getText().trim());
        pc.setTrangThai(1);
        return pc;
    }

    private void resetInputForm() {
        txtMaPC.setEnabled(true);
        txtMaPC.setText("");
        txtGhiChu.setText("");

        cboLop.setEnabled(true);
        cboMon.setEnabled(true);
        cboGV.setEnabled(true);
        cboNamHoc.setEnabled(true);

        if (cboLop.getItemCount() > 0) cboLop.setSelectedIndex(0);
        if (cboMon.getItemCount() > 0) cboMon.setSelectedIndex(0);
        if (cboGV.getItemCount() > 0) cboGV.setSelectedIndex(0);
        if (cboNamHoc.getItemCount() > 0) cboNamHoc.setSelectedIndex(0);
        
        suggestMaPC();

        tblPhanCong.clearSelection();
        updateButtonState();
        txtMaPC.requestFocus();
    }

    private void clearForm() {
        resetInputForm();
        bufferChanges.clear();
        dataChanged = false;
        updateSaveButtonState();
        loadTablePhanCong();
    }

    private void updateButtonState() {
        boolean selected = tblPhanCong.getSelectedRow() >= 0;
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

    // Load dữ liệu - CHỈ các active
    private void loadTablePhanCong() {
        modelPhanCong.setRowCount(0);
        for (PhanCong pc : pcBLL.getAllActive()) {
            String tenLop = getTenLopFromMa(pc.getMaLop());
            String tenMon = getTenMonFromMa(pc.getMaMon());
            String tenGV = getTenGVFromMa(pc.getMaGV());
            String tenNH = getTenNHFromMa(pc.getMaNam());
            
            modelPhanCong.addRow(new Object[]{
                pc.getMaPC(),
                tenLop,
                tenMon,
                tenGV,
                tenNH,
                pc.getGhiChu()
            });
        }
    }
    
    // Các hàm phụ lấy tên hiển thị
    private String getTenLopFromMa(String maLop) {
        for (int i = 0; i < cboLop.getItemCount(); i++) {
            Lop l = cboLop.getItemAt(i);
            if (l.getMaLop().equals(maLop)) {
                return l.getTenLop();
            }
        }
        return maLop;
    }
    
    private String getTenMonFromMa(String maMon) {
        for (int i = 0; i < cboMon.getItemCount(); i++) {
            Mon m = cboMon.getItemAt(i);
            if (m.getMaMon().equals(maMon)) {
                return m.getTenMon();
            }
        }
        return maMon;
    }
    
    private String getTenGVFromMa(String maGV) {
        for (int i = 0; i < cboGV.getItemCount(); i++) {
            GiaoVien gv = cboGV.getItemAt(i);
            if (gv.getMaGV().equals(maGV)) {
                return gv.getHoTen();
            }
        }
        return maGV;
    }
    
    private String getTenNHFromMa(String maNH) {
        for (int i = 0; i < cboNamHoc.getItemCount(); i++) {
            NamHoc nh = cboNamHoc.getItemAt(i);
            if (nh.getMaNH().equals(maNH)) {
                return nh.getTenNH();
            }
        }
        return maNH;
    }

    private void loadComboLop() {
        cboLop.removeAllItems();
        for (Lop l : new LopBLL().getAllActive()) {
            cboLop.addItem(l);
        }
    }

    private void loadComboMon() {
        cboMon.removeAllItems();
        for (Mon m : new MonBLL().getAllActive()) {
            cboMon.addItem(m);
        }
    }

    private void loadComboGV() {
        cboGV.removeAllItems();
        for (GiaoVien gv : new GiaoVienBLL().getAllActiveProc()) {
            cboGV.addItem(gv);
        }
    }

    private void loadComboNamHoc() {
        cboNamHoc.removeAllItems();
        for (NamHoc nh : new NamHocBLL().getAllActiveByProc()) {
            cboNamHoc.addItem(nh);
        }
    }

    // Utils
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

    // Buffer Change class
    private static class Change {
        PhanCong pc;
        String action;
        Change(PhanCong pc, String action) {
            this.pc = pc;
            this.action = action;
        }
    }
}