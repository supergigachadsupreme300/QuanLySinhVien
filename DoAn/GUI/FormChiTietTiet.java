package GUI;

import BusinessLogicLayer.ChiTietTietBLL;
import BusinessLogicLayer.MonHocBLL;
import BusinessLogicLayer.ThoiKhoaBieuBLL;
import DataObject.ChiTietTiet;
import DataObject.Mon;
import DataObject.ThoiKhoaBieu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.miginfocom.swing.MigLayout;

public class FormChiTietTiet extends JPanel {

    private MainMenu mainFrame;
    private ChiTietTietBLL ctBLL;
    private ThoiKhoaBieuBLL tkbBLL;
    private MonHocBLL monBLL;

    private boolean dataChanged = false;
    private List<Change> bufferChanges = new ArrayList<>();

    // FORM
    private JTextField txtMaCT, txtPhongHoc, txtGioBD, txtGioKT;
    private JComboBox<ThoiKhoaBieu> cboTKB;
    private JComboBox<Mon> cboMon;
    private JComboBox<String> cboThu;
    private JComboBox<Integer> cboTiet;

    // BUTTON
    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;

    // TABLE GRID
    private JTable tblLuoi;
    private DefaultTableModel modelLuoi;

    // Map lưu giờ cho từng tiết
    private Map<Integer, String> tietGioMap;

    public FormChiTietTiet(MainMenu frame) {
        this.mainFrame = frame;
        this.ctBLL = new ChiTietTietBLL();
        this.tkbBLL = new ThoiKhoaBieuBLL();
        this.monBLL = new MonHocBLL();
        initTietGioMap();
        initUI();
        loadComboTKB();
        loadComboMon();
        loadDefaultLuoi();
        setupAutoGenerateMaCT();
        resetFormState(); // Đặt trạng thái ban đầu
    }

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

    private String getGioTiet(int tiet) {
        return tietGioMap.getOrDefault(tiet, "??:??-??:??");
    }

    /**
     * Public helper to refresh/load TKB combo and grid from outside (used by MainMenu).
     */
    public void refreshTKBList() {
        try {
            loadComboTKB();
            loadDefaultLuoi();
        } catch (Exception ex) {
            // swallow to avoid crashing callers
            ex.printStackTrace();
        }
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ CHI TIẾT TIẾT HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlForm = new JPanel(new MigLayout("insets 15", "[]15[grow]30[]15[grow]", "[]10[]10[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin chi tiết tiết"));

        txtMaCT = new JTextField();
        txtMaCT.setEditable(false);

        cboTKB = new JComboBox<>();
        cboMon = new JComboBox<>();
        cboThu = new JComboBox<>(new String[]{"Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6", "Thứ 7"});
        cboTiet = new JComboBox<>();
        for(int i=1;i<=10;i++) cboTiet.addItem(i);
        txtPhongHoc = new JTextField();
        txtGioBD = new JTextField();
        txtGioKT = new JTextField();

        pnlForm.add(new JLabel("Mã CT:")); pnlForm.add(txtMaCT, "growx");
        pnlForm.add(new JLabel("TKB:")); pnlForm.add(cboTKB, "growx, wrap");
        pnlForm.add(new JLabel("Môn học:")); pnlForm.add(cboMon, "growx");
        pnlForm.add(new JLabel("Thứ:")); pnlForm.add(cboThu, "growx, wrap");
        pnlForm.add(new JLabel("Tiết:")); pnlForm.add(cboTiet, "growx");
        pnlForm.add(new JLabel("Phòng học:")); pnlForm.add(txtPhongHoc, "growx, wrap");
        pnlForm.add(new JLabel("Giờ bắt đầu:")); pnlForm.add(txtGioBD, "growx");
        pnlForm.add(new JLabel("Giờ kết thúc:")); pnlForm.add(txtGioKT, "growx");

        add(pnlForm, "growx, wrap");

        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34,139,34));
        btnSua = createButton("Sửa", new Color(255,140,0));
        btnXoa = createButton("Xóa", new Color(220,20,60));
        btnClear = createButton("Làm mới", new Color(70,130,180));
        btnLuu = createButton("Lưu", new Color(150,150,150));
        btnLuu.setEnabled(false);

        pnlBtn.add(btnThem); pnlBtn.add(btnSua); pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear); pnlBtn.add(btnLuu);
        add(pnlBtn, "growx, wrap");

        // ===== TABLE LƯỚI =====
        modelLuoi = new DefaultTableModel(
            new String[]{"Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        tblLuoi = new JTable(modelLuoi);
        tblLuoi.setRowHeight(45);
        styleTable(tblLuoi);

        tblLuoi.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(c.getFont().deriveFont(Font.BOLD));
                c.setBackground(new Color(230, 240, 255));
                setHorizontalAlignment(JLabel.CENTER);
                setVerticalAlignment(JLabel.CENTER);
                return c;
            }
        });

        for (int i = 1; i <= 6; i++) {
            tblLuoi.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(JLabel.CENTER);
                    setVerticalAlignment(JLabel.CENTER);
                    if (value != null && !value.toString().isEmpty()) {
                        c.setBackground(new Color(220, 255, 220));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            });
        }

        JScrollPane sp = new JScrollPane(tblLuoi);
        sp.setBorder(BorderFactory.createTitledBorder("Lưới thời khóa biểu"));
        add(sp, "grow");

        // EVENTS
        cboTKB.addActionListener(e -> {
            if (cboTKB.getSelectedItem() != null) {
                reloadGrid();
                generateAndSetMaCT();
                resetFormState();
            }
        });

        cboThu.addActionListener(e -> {
            if (!isCellSelected()) generateAndSetMaCT();
        });
        cboTiet.addActionListener(e -> {
            if (!isCellSelected()) generateAndSetMaCT();
        });

        tblLuoi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromGridSelection();
            }
        });

        btnThem.addActionListener(e -> themCT());
        btnSua.addActionListener(e -> suaCT());
        btnXoa.addActionListener(e -> xoaCT());
        btnClear.addActionListener(e -> clearForm());
        btnLuu.addActionListener(e -> luuCT());

        addFocusEffect(txtMaCT);
        addFocusEffect(txtPhongHoc);
        addFocusEffect(txtGioBD);
        addFocusEffect(txtGioKT);
    }

    private void styleTable(JTable tbl) {
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0, 102, 204));
        tbl.getTableHeader().setForeground(Color.WHITE);
    }

    private void setupAutoGenerateMaCT() {
        SwingUtilities.invokeLater(() -> {
            if (cboTKB.getItemCount() > 0) {
                cboTKB.setSelectedIndex(0);
                generateAndSetMaCT();
            }
        });
    }

    private void generateAndSetMaCT() {
        ThoiKhoaBieu tkb = (ThoiKhoaBieu) cboTKB.getSelectedItem();
        String thu = (String) cboThu.getSelectedItem();
        Integer tiet = (Integer) cboTiet.getSelectedItem();
        if (tkb != null && thu != null && tiet != null) {
            String maCTAuto = generateMaCT(tkb.getMaTKB(), thu, tiet);
            txtMaCT.setText(maCTAuto);
        }
    }

    public String generateMaCT(String maTKB, String thu, int tiet) {
        Set<String> used = new HashSet<>();
        List<ChiTietTiet> dsDB = ctBLL.getAll();
        for (ChiTietTiet ct : dsDB) {
            if (ct.getMaChiTiet() != null) {
                used.add(ct.getMaChiTiet());
            }
        }
        for (Change change : bufferChanges) {
            ChiTietTiet ct = change.ct;
            if (ct.getMaChiTiet() != null) {
                if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                    used.add(ct.getMaChiTiet());
                }
            }
        }
        String thuCode = thu.replace(" ", "").replace("ứ", "");
        String baseCode = maTKB + "_" + thuCode + "_T" + tiet;
        String newCode = baseCode;
        int counter = 1;
        while (used.contains(newCode)) {
            newCode = baseCode + "_" + counter;
            counter++;
        }
        return newCode;
    }

    private boolean isCellSelected() {
        int row = tblLuoi.getSelectedRow();
        int col = tblLuoi.getSelectedColumn();
        return row >= 0 && col > 0;
    }

    private void resetFormState() {
        // Khi không chọn ô hoặc chọn ô trống: chỉ bật Thêm, tắt Sửa/Xóa
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
        txtMaCT.setEnabled(true); // Cho phép thay đổi? Nhưng là auto gen nên không cần edit
    }

    private void enableEditButtons() {
        btnThem.setEnabled(false);
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);
        txtMaCT.setEnabled(false); // Không cho sửa mã khi đang ở chế độ sửa/xóa
    }

    // CRUD
    private boolean validateForm() {
        if (txtMaCT.getText().trim().isEmpty()
            || txtPhongHoc.getText().trim().isEmpty()
            || txtGioBD.getText().trim().isEmpty()
            || txtGioKT.getText().trim().isEmpty()
            || cboTKB.getSelectedItem() == null
            || cboMon.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this,
                "Vui lòng nhập đầy đủ thông tin!",
                "Thiếu dữ liệu",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            LocalTime.parse(txtGioBD.getText().trim(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime.parse(txtGioKT.getText().trim(), DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                "Giờ nhập không hợp lệ, định dạng phải HH:mm",
                "Lỗi dữ liệu",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String maCTMoi = txtMaCT.getText().trim();
        // Kiểm tra trùng trong buffer (ADD)
        for (Change change : bufferChanges) {
            if (change.action.equals("ADD") && change.ct.getMaChiTiet().equals(maCTMoi)) {
                JOptionPane.showMessageDialog(this,
                    "Mã chi tiết đã tồn tại trong danh sách chờ lưu!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        // Kiểm tra trong database (kể cả đã xóa mềm)
        List<ChiTietTiet> dsDB = ctBLL.getAll();
        for (ChiTietTiet ct : dsDB) {
            if (ct.getMaChiTiet().equals(maCTMoi)) {
                JOptionPane.showMessageDialog(this,
                    "Mã chi tiết đã tồn tại trong hệ thống!",
                    "Lỗi trùng mã",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private void themCT() {
        if (!validateForm()) return;

        ChiTietTiet ct = getCTFromForm();
        bufferChanges.add(new Change(ct, "ADD"));
        dataChanged = true;
        updateSaveButtonState();

        int colIndex = switch (ct.getThu()) {
            case "Thứ 2" -> 1;
            case "Thứ 3" -> 2;
            case "Thứ 4" -> 3;
            case "Thứ 5" -> 4;
            case "Thứ 6" -> 5;
            case "Thứ 7" -> 6;
            default -> -1;
        };

        if (colIndex != -1) {
            while (modelLuoi.getRowCount() < ct.getTiet()) {
                String gioTiet = getGioTiet(modelLuoi.getRowCount() + 1);
                String tietLabel = "<html><center>Tiết " + (modelLuoi.getRowCount() + 1) + "<br>(" + gioTiet + ")</center></html>";
                modelLuoi.addRow(new Object[]{tietLabel, "", "", "", "", "", ""});
            }
            String cellValue = "<html><center>" + ct.getMaMon() + "<br>P." + ct.getPhongHoc() + "</center></html>";
            modelLuoi.setValueAt(cellValue, ct.getTiet() - 1, colIndex);
        }

        JOptionPane.showMessageDialog(this,
            "Đã thêm chi tiết tiết thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);

        resetInputForm();
    }

    private void suaCT() {
        int row = tblLuoi.getSelectedRow();
        int col = tblLuoi.getSelectedColumn();
        if (row < 0 || col <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ô trong lưới để sửa!");
            return;
        }

        if (!validateForm()) return;

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn sửa chi tiết tiết này?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        ChiTietTiet ct = getCTFromForm();

        bufferChanges.removeIf(c -> c.ct.getMaChiTiet().equals(ct.getMaChiTiet()) && c.action.equals("UPDATE"));
        bufferChanges.add(new Change(ct, "UPDATE"));
        dataChanged = true;
        updateSaveButtonState();

        String cellValue = "<html><center>" + ct.getMaMon() + "<br>P." + ct.getPhongHoc() + "</center></html>";
        modelLuoi.setValueAt(cellValue, ct.getTiet() - 1, col);

        JOptionPane.showMessageDialog(this,
            "Đã sửa chi tiết tiết thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);

        resetInputForm();
    }

    private void xoaCT() {
        int row = tblLuoi.getSelectedRow();
        int col = tblLuoi.getSelectedColumn();
        if (row < 0 || col <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ô trong lưới để xóa!");
            return;
        }

        Object cellValue = modelLuoi.getValueAt(row, col);
        if (cellValue == null || cellValue.toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ô này không có dữ liệu để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa chi tiết tiết này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String maCT = txtMaCT.getText().trim();

        // 1. Nếu là bản ghi mới thêm chưa lưu → chỉ remove khỏi buffer
        boolean isNew = bufferChanges.removeIf(c ->
            c.ct.getMaChiTiet().equals(maCT) && c.action.equals("ADD")
        );

        // 2. Nếu không phải bản ghi mới → kiểm tra tồn tại trong DB
        if (!isNew) {
            ChiTietTiet ctDB = ctBLL.findByMaChiTiet(maCT); // dùng BLL gọi DAL
            if (ctDB != null) {
                ChiTietTiet ct = new ChiTietTiet();
                ct.setMaChiTiet(maCT);
                bufferChanges.add(new Change(ct, "DELETE"));
            } else {
                JOptionPane.showMessageDialog(this,
                    "Bản ghi đã không còn trong CSDL, chỉ xóa khỏi giao diện.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            }
        }

        // 3. Xóa khỏi lưới hiển thị
        modelLuoi.setValueAt("", row, col);

        dataChanged = true;
        updateSaveButtonState();

        JOptionPane.showMessageDialog(this,
            "Đã xóa chi tiết tiết thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);

        resetInputForm();
    }



    private void luuCT() {
        boolean allSuccess = true;
        for (Change change : bufferChanges) {
            boolean result = false;
            switch (change.action) {
                case "ADD":
                    result = ctBLL.themChiTietTiet(change.ct);
                    break;
                case "UPDATE":
                    result = ctBLL.suaChiTietTiet(change.ct);
                    break;
                case "DELETE":
                    result = ctBLL.xoaChiTietTiet(change.ct.getMaChiTiet());
                    break;
            }
            if (!result) {
                allSuccess = false;
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi " + change.action + " cho mã " + change.ct.getMaChiTiet(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                break; // hoặc continue để báo hết lỗi?
            }
        }
        if (allSuccess) {
            bufferChanges.clear();
            JOptionPane.showMessageDialog(this, "Đã lưu thay đổi thành công!");
            dataChanged = false;
            updateSaveButtonState();
            reloadGrid();
            resetInputForm();
        }
    }

    private ChiTietTiet getCTFromForm() {
        ChiTietTiet ct = new ChiTietTiet();
        ct.setMaChiTiet(txtMaCT.getText().trim());
        ct.setMaTKB(((ThoiKhoaBieu) cboTKB.getSelectedItem()).getMaTKB());
        ct.setMaMon(((Mon) cboMon.getSelectedItem()).getMaMon());
        ct.setThu((String) cboThu.getSelectedItem());
        ct.setTiet((Integer) cboTiet.getSelectedItem());
        ct.setPhongHoc(txtPhongHoc.getText().trim());
        ct.setGioBatDau(txtGioBD.getText().trim());
        ct.setGioKetThuc(txtGioKT.getText().trim());
        ct.setTrangThai(1);
        return ct;
    }

    private void resetInputForm() {
        txtMaCT.setText("");
        txtPhongHoc.setText("");
        txtGioBD.setText("");
        txtGioKT.setText("");
        if (cboTKB.getItemCount() > 0 && cboTKB.getSelectedItem() == null) {
            cboTKB.setSelectedIndex(0);
        }
        if (cboMon.getItemCount() > 0) cboMon.setSelectedIndex(0);
        cboThu.setSelectedIndex(0);
        cboTiet.setSelectedIndex(0);

        generateAndSetMaCT(); // Tạo mã mới cho form trống
        tblLuoi.clearSelection();
        resetFormState(); // Bật Thêm, tắt Sửa/Xóa
    }

    private void clearForm() {
        resetInputForm();
        bufferChanges.clear();
        dataChanged = false;
        updateSaveButtonState();
        reloadGrid();
    }

    private void updateButtonState() {
        // Không cần, vì đã có resetFormState và enableEditButtons
    }

    private void updateSaveButtonState() {
        if (dataChanged) {
            btnLuu.setEnabled(true);
            btnLuu.setBackground(new Color(34, 139, 34));
            btnLuu.setForeground(Color.WHITE);
        } else {
            btnLuu.setEnabled(false);
            btnLuu.setBackground(new Color(150, 150, 150));
            btnLuu.setForeground(Color.WHITE);
        }
    }

    private void fillFormFromGridSelection() {
        int row = tblLuoi.getSelectedRow();
        int col = tblLuoi.getSelectedColumn();

        if (row >= 0 && col > 0) {
            String maTKB = ((ThoiKhoaBieu) cboTKB.getSelectedItem()).getMaTKB();
            List<ChiTietTiet> ds = ctBLL.getByMaTKB(maTKB);
            String thu = tblLuoi.getColumnName(col);
            boolean found = false;

            for (ChiTietTiet ct : ds) {
                if (ct.getTiet() == row + 1 && ct.getThu().equals(thu)) {
                    txtMaCT.setText(ct.getMaChiTiet());
                    txtMaCT.setEnabled(false);

                    for (int i = 0; i < cboMon.getItemCount(); i++) {
                        Mon m = cboMon.getItemAt(i);
                        if (m.getMaMon().equals(ct.getMaMon())) {
                            cboMon.setSelectedIndex(i);
                            break;
                        }
                    }
                    cboThu.setSelectedItem(ct.getThu());
                    cboTiet.setSelectedItem(ct.getTiet());
                    txtPhongHoc.setText(ct.getPhongHoc());
                    txtGioBD.setText(ct.getGioBatDau());
                    txtGioKT.setText(ct.getGioKetThuc());
                    found = true;
                    break;
                }
            }

            if (found) {
                enableEditButtons(); // Bật Sửa/Xóa, tắt Thêm
            } else {
                // Ô trống
                clearFormFields();
                resetFormState(); // Bật Thêm, tắt Sửa/Xóa
            }
        } else {
            // Không chọn ô
            clearFormFields();
            resetFormState();
        }
    }

    private void clearFormFields() {
        txtMaCT.setText("");
        txtPhongHoc.setText("");
        txtGioBD.setText("");
        txtGioKT.setText("");
        generateAndSetMaCT(); // Tạo mã mới
        txtMaCT.setEnabled(true);
    }

    private void loadComboMon() {
        cboMon.removeAllItems();
        for (Mon m : monBLL.getAllActiveProc()) {
            cboMon.addItem(m);
        }
    }

    private void loadComboTKB() {
        cboTKB.removeAllItems();
        for (ThoiKhoaBieu tkb : tkbBLL.getAllActive()) {
            cboTKB.addItem(tkb);
        }
    }

    private void loadLuoi(String maTKB) {
        modelLuoi.setRowCount(0);

        for (int tiet = 1; tiet <= 10; tiet++) {
            String gioTiet = getGioTiet(tiet);
            String tietLabel = "<html><center>Tiết " + tiet + "<br>(" + gioTiet + ")</center></html>";
            modelLuoi.addRow(new Object[]{tietLabel, "", "", "", "", "", ""});
        }

        List<ChiTietTiet> ds = ctBLL.getByMaTKB(maTKB);
        for (ChiTietTiet ct : ds) {
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
                modelLuoi.setValueAt(value, ct.getTiet() - 1, colIndex);
            }
        }
    }

    private void loadDefaultLuoi() {
        List<ThoiKhoaBieu> dsTKB = tkbBLL.getAllActive();
        if (dsTKB != null && !dsTKB.isEmpty()) {
            ThoiKhoaBieu tkb = dsTKB.get(0);
            cboTKB.setSelectedItem(tkb);
            loadLuoi(tkb.getMaTKB());
        }
    }

    private void reloadGrid() {
        ThoiKhoaBieu tkb = (ThoiKhoaBieu) cboTKB.getSelectedItem();
        if (tkb != null) {
            loadLuoi(tkb.getMaTKB());
        }
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

    private static class Change {
        ChiTietTiet ct;
        String action;
        Change(ChiTietTiet ct, String action) {
            this.ct = ct;
            this.action = action;
        }
    }
}
