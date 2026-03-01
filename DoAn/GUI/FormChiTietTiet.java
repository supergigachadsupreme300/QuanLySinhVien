package GUI;

import BusinessLogicLayer.ChiTietTietBLL;
import BusinessLogicLayer.MonHocBLL;
import BusinessLogicLayer.ThoiKhoaBieuBLL;
import DataObject.ChiTietTiet;
import DataObject.Mon;
import DataObject.ThoiKhoaBieu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public FormChiTietTiet(MainMenu frame) {
        this.mainFrame = frame;
        this.ctBLL = new ChiTietTietBLL();
        this.tkbBLL = new ThoiKhoaBieuBLL();
        this.monBLL = new MonHocBLL();
        initUI();
        loadComboTKB();
        loadComboMon();
        loadDefaultLuoi();
        setupAutoGenerateMaCT();
        updateButtonState();
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
        txtMaCT.setEditable(false); // Không cho sửa mã tự động
        
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

        modelLuoi = new DefaultTableModel(
            new String[]{"Tiết","Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6", "Thứ 7"}, 0
        ){
            public boolean isCellEditable(int r,int c){return false;}
        };
        
        tblLuoi = new JTable(modelLuoi);
        tblLuoi.setRowHeight(35);
        JScrollPane sp = new JScrollPane(tblLuoi);
        sp.setBorder(BorderFactory.createTitledBorder("Lưới thời khóa biểu"));
        add(sp,"grow");

        // EVENTS
        cboTKB.addActionListener(e -> {
            if (cboTKB.getSelectedItem() != null) {
                reloadGrid();
                generateAndSetMaCT();
            }
        });
        
        cboThu.addActionListener(e -> generateAndSetMaCT());
        cboTiet.addActionListener(e -> generateAndSetMaCT());

        tblLuoi.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormFromGridSelection();
            }
        });

        btnThem.addActionListener(e->themCT());
        btnSua.addActionListener(e->suaCT());
        btnXoa.addActionListener(e->xoaCT());
        btnClear.addActionListener(e->clearForm());
        btnLuu.addActionListener(e->luuCT());
        
        addFocusEffect(txtMaCT);
        addFocusEffect(txtPhongHoc);
        addFocusEffect(txtGioBD);
        addFocusEffect(txtGioKT);
    }

    /**
     * HÀM RIÊNG: Thiết lập tự động tạo mã chi tiết tiết
     */
    private void setupAutoGenerateMaCT() {
        SwingUtilities.invokeLater(() -> {
            if (cboTKB.getItemCount() > 0) {
                cboTKB.setSelectedIndex(0);
                generateAndSetMaCT();
            }
        });
    }

    /**
     * HÀM RIÊNG: Tạo và gán mã chi tiết tiết
     */
    private void generateAndSetMaCT() {
        ThoiKhoaBieu tkb = (ThoiKhoaBieu) cboTKB.getSelectedItem();
        String thu = (String) cboThu.getSelectedItem();
        Integer tiet = (Integer) cboTiet.getSelectedItem();
        
        if (tkb != null && thu != null && tiet != null) {
            String maCTAuto = generateMaCT(tkb.getMaTKB(), thu, tiet);
            txtMaCT.setText(maCTAuto);
        }
    }

    /**
     * HÀM RIÊNG CẢI TIẾN: Tạo mã chi tiết tiết
     * - Xét TẤT CẢ dữ liệu từ database (kể cả đã xóa mềm)
     * - KHÔNG tái sử dụng mã đã xóa
     */
    public String generateMaCT(String maTKB, String thu, int tiet) {
        Set<String> used = new HashSet<>();
        
        // 1. Lấy TẤT CẢ dữ liệu từ database (kể cả trangThai = 0)
        List<ChiTietTiet> dsDB = ctBLL.getAll(); // Cần thêm method getAll() ở BLL
        for (ChiTietTiet ct : dsDB) {
            if (ct.getMaChiTiet() != null) {
                used.add(ct.getMaChiTiet());
            }
        }
        
        // 2. Xét dữ liệu từ buffer
        for (Change change : bufferChanges) {
            ChiTietTiet ct = change.ct;
            if (ct.getMaChiTiet() != null) {
                if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                    used.add(ct.getMaChiTiet());
                } else if (change.action.equals("DELETE")) {
                    // KHÔNG xóa khỏi used - giữ nguyên vì soft delete không tái sử dụng mã
                    // used.remove(ct.getMaChiTiet()); // Bỏ dòng này
                }
            }
        }
        
        // 3. Tạo mã cơ bản từ TKB, thứ, tiết
        String thuCode = thu.replace(" ", "").replace("ứ", "");
        String baseCode = maTKB + "_" + thuCode + "_T" + tiet;
        String newCode = baseCode;
        int counter = 1;
        
        // 4. Nếu đã tồn tại, thêm số đuôi
        while (used.contains(newCode)) {
            newCode = baseCode + "_" + counter;
            counter++;
        }
        
        return newCode;
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

        // Validate giờ nhập
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

        // Kiểm tra mã CT không trùng trong buffer
        String maCTMoi = txtMaCT.getText().trim();
        String currentMaCT = null;
        
        // Kiểm tra trong buffer (các ADD)
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
        List<ChiTietTiet> dsDB = ctBLL.getAll(); // Cần thêm method getAll() ở BLL
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

    private void themCT(){
        if (!validateForm()) return;

        ChiTietTiet ct = getCTFromForm();
        bufferChanges.add(new Change(ct,"ADD"));
        dataChanged = true; 
        updateSaveButtonState();

        // cập nhật lưới tạm thời
        int colIndex = switch(ct.getThu()){
            case "Thứ 2" -> 1;
            case "Thứ 3" -> 2;
            case "Thứ 4" -> 3;
            case "Thứ 5" -> 4;
            case "Thứ 6" -> 5;
            case "Thứ 7" -> 6;
            default -> -1;
        };
        
        if(colIndex != -1){
            // Đảm bảo đủ số dòng
            while (modelLuoi.getRowCount() < ct.getTiet()) {
                String tietLabel = "Tiết " + (modelLuoi.getRowCount() + 1);
                modelLuoi.addRow(new Object[]{tietLabel, "", "", "", "", "", ""});
            }
            
            String cellValue = "<html><center>" + ct.getMaMon() + "<br>P." + ct.getPhongHoc() 
                             + "<br>(" + ct.getGioBatDau() + "-" + ct.getGioKetThuc() + ")</center></html>";
            modelLuoi.setValueAt(cellValue, ct.getTiet()-1, colIndex);
        }

        JOptionPane.showMessageDialog(this,
            "Đã thêm chi tiết tiết thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
            
        resetInputForm();
    }

    private void suaCT(){
        int row = tblLuoi.getSelectedRow();
        int col = tblLuoi.getSelectedColumn();
        if(row < 0 || col <= 0){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ô trong lưới để sửa!");
            return;
        }

        if (!validateForm()) return;

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn sửa chi tiết tiết này?",
            "Xác nhận sửa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        ChiTietTiet ct = getCTFromForm();
        
        // Xóa change cũ nếu có
        bufferChanges.removeIf(c -> c.ct.getMaChiTiet().equals(ct.getMaChiTiet()) && c.action.equals("UPDATE"));
        
        bufferChanges.add(new Change(ct,"UPDATE"));
        dataChanged = true; 
        updateSaveButtonState();

        // Cập nhật lưới tạm thời
        String cellValue = "<html><center>" + ct.getMaMon() + "<br>P." + ct.getPhongHoc() 
                         + "<br>(" + ct.getGioBatDau() + "-" + ct.getGioKetThuc() + ")</center></html>";
        modelLuoi.setValueAt(cellValue, ct.getTiet()-1, col);

        JOptionPane.showMessageDialog(this,
            "Đã sửa chi tiết tiết thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
            
        resetInputForm();
    }

    private void xoaCT(){
        int row = tblLuoi.getSelectedRow();
        int col = tblLuoi.getSelectedColumn();
        if(row < 0 || col <= 0){
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một ô trong lưới để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn xóa chi tiết tiết này?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm != JOptionPane.YES_OPTION) return;

        String maCT = txtMaCT.getText().trim();
        ChiTietTiet ct = new ChiTietTiet();
        ct.setMaChiTiet(maCT);
        
        // KHÔNG xóa change cũ - giữ nguyên vì soft delete không tái sử dụng mã
        // bufferChanges.removeIf(c -> c.ct.getMaChiTiet().equals(maCT));
        
        bufferChanges.add(new Change(ct,"DELETE"));
        dataChanged = true; 
        updateSaveButtonState();

        // Xóa tạm trên lưới
        modelLuoi.setValueAt("", row, col);

        JOptionPane.showMessageDialog(this,
            "Đã xóa chi tiết tiết thành công! Nhấn 'Lưu' để lưu vào CSDL.",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE);
            
        resetInputForm();
    }

    private void luuCT(){
        try {
            for(Change change: bufferChanges){
                switch(change.action){
                    case "ADD": 
                        ctBLL.themChiTietTiet(change.ct); 
                        break;
                    case "UPDATE": 
                        ctBLL.suaChiTietTiet(change.ct); 
                        break;
                    case "DELETE": 
                        ctBLL.xoaChiTietTiet(change.ct.getMaChiTiet()); // Soft delete
                        break;
                }
            }
            bufferChanges.clear();
            JOptionPane.showMessageDialog(this, "Đã lưu thay đổi thành công!");
            dataChanged = false; 
            updateSaveButtonState();
            reloadGrid(); // Load lại chỉ các active
            resetInputForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi lưu dữ liệu: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    // Helpers
    private ChiTietTiet getCTFromForm(){
        ChiTietTiet ct = new ChiTietTiet();
        ct.setMaChiTiet(txtMaCT.getText().trim());
        ct.setMaTKB(((ThoiKhoaBieu)cboTKB.getSelectedItem()).getMaTKB());
        ct.setMaMon(((Mon)cboMon.getSelectedItem()).getMaMon());
        ct.setThu((String)cboThu.getSelectedItem());
        ct.setTiet((Integer)cboTiet.getSelectedItem());
        ct.setPhongHoc(txtPhongHoc.getText().trim());
        ct.setGioBatDau(txtGioBD.getText().trim());
        ct.setGioKetThuc(txtGioKT.getText().trim());
        ct.setTrangThai(1);
        return ct;
    }

    private void resetInputForm(){
        txtMaCT.setText("");
        txtPhongHoc.setText("");
        txtGioBD.setText("");
        txtGioKT.setText("");
        if(cboTKB.getItemCount()>0 && cboTKB.getSelectedItem() == null) {
            cboTKB.setSelectedIndex(0);
        }
        if(cboMon.getItemCount()>0) cboMon.setSelectedIndex(0);
        cboThu.setSelectedIndex(0);
        cboTiet.setSelectedIndex(0);
        
        // Tạo mã mới
        generateAndSetMaCT();
        
        tblLuoi.clearSelection();
    }

    private void clearForm(){
        resetInputForm();
        bufferChanges.clear();
        dataChanged = false; 
        updateSaveButtonState();
        reloadGrid();
    }

    private void updateButtonState(){
        btnSua.setEnabled(true);
        btnXoa.setEnabled(true);
    }

    private void updateSaveButtonState(){
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
            String maTKB = ((ThoiKhoaBieu)cboTKB.getSelectedItem()).getMaTKB();
            List<ChiTietTiet> ds = ctBLL.getByMaTKB(maTKB);
            String thu = tblLuoi.getColumnName(col);

            for (ChiTietTiet ct : ds) {
                if (ct.getTiet() == row+1 && ct.getThu().equals(thu)) {
                    txtMaCT.setText(ct.getMaChiTiet());
                    txtMaCT.setEnabled(false);
                    
                    for (int i=0; i<cboMon.getItemCount(); i++) {
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
                    break;
                }
            }
        }
    }

    private void loadComboMon(){
        cboMon.removeAllItems();
        for(Mon m : monBLL.getAllActiveProc()){
            cboMon.addItem(m);
        }
    }

    private void loadComboTKB() {
        cboTKB.removeAllItems();
        for (ThoiKhoaBieu tkb : tkbBLL.getAllActive()) {
            cboTKB.addItem(tkb);
        }
    }

    private void loadLuoi(String maTKB){
        modelLuoi.setRowCount(0);
        
        // Tạo 10 dòng mặc định
        for (int tiet = 1; tiet <= 10; tiet++) {
            String tietLabel = "Tiết " + tiet;
            modelLuoi.addRow(new Object[]{tietLabel, "", "", "", "", "", ""});
        }

        List<ChiTietTiet> ds = ctBLL.getByMaTKB(maTKB);
        for(ChiTietTiet ct : ds){
            int colIndex = switch(ct.getThu()){
                case "Thứ 2" -> 1;
                case "Thứ 3" -> 2;
                case "Thứ 4" -> 3;
                case "Thứ 5" -> 4;
                case "Thứ 6" -> 5;
                case "Thứ 7" -> 6;
                default -> -1;
            };

            if (colIndex != -1 && ct.getTiet() <= 10) {
                String value = "<html><center>" + ct.getMaMon() + "<br>P." + ct.getPhongHoc() 
                             + "<br>(" + ct.getGioBatDau() + "-" + ct.getGioKetThuc() + ")</center></html>";
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

    // Utils
    private JButton createButton(String text, Color bg){
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100,35));
        return btn;
    }

    private void addFocusEffect(JComponent c){
        c.setOpaque(true);
        c.setBackground(Color.WHITE);
        c.addFocusListener(new FocusAdapter(){
            @Override
            public void focusGained(FocusEvent e){
                c.setBackground(new Color(230,240,255));
            }
            @Override
            public void focusLost(FocusEvent e){
                c.setBackground(Color.WHITE);
            }
        });
    }

    // Buffer Change class
    private static class Change {
        ChiTietTiet ct;
        String action; // "ADD", "UPDATE", "DELETE"
        Change(ChiTietTiet ct, String action){
            this.ct = ct;
            this.action = action;
        }
    }
}