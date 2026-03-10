package GUI;



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


    private JTable tblLop, tblHS;
    private DefaultTableModel modelLop, modelHS;


    private JTextField txtMaLop, txtTenLop, txtSiSo;
    private JComboBox<NamHoc> cboNamHoc; 
    private JComboBox<GiaoVien> cboGVCN;
    private JComboBox<Integer> cboKhoi;


    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;
    private boolean dataChanged = false;
    
    private List<Change> bufferChanges = new ArrayList<>();


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


    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));


        JLabel lblTitle = new JLabel("QUẢN LÝ LỚP HỌC", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");


        JPanel pnlForm = new JPanel(new MigLayout(
                "insets 15",
                "[]15[grow]30[]15[grow]",
                "[]10[]10[]10[]"
        ));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin lớp"));

        txtMaLop  = new JTextField();
        txtMaLop.setEditable(false);
        
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


        tblLop = new JTable(modelLop);
        styleTable(tblLop);
        tblLop.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblLop.setPreferredScrollableViewportSize(new Dimension(450, 220));
        tblLop.setFillsViewportHeight(true);
        
        tblLop.getColumnModel().getColumn(0).setPreferredWidth(80);    
        tblLop.getColumnModel().getColumn(1).setPreferredWidth(150);  
        tblLop.getColumnModel().getColumn(2).setPreferredWidth(70);    
        tblLop.getColumnModel().getColumn(3).setPreferredWidth(120);  
        tblLop.getColumnModel().getColumn(4).setPreferredWidth(150);  

        JScrollPane spLop = new JScrollPane(tblLop);
        spLop.setBorder(BorderFactory.createTitledBorder("Danh sách lớp"));
        spLop.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        tblHS = new JTable(modelHS);
        styleTable(tblHS);
        tblHS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblHS.setPreferredScrollableViewportSize(new Dimension(550, 220));
        tblHS.setFillsViewportHeight(true);
        
        tblHS.getColumnModel().getColumn(0).setPreferredWidth(80);   
        tblHS.getColumnModel().getColumn(1).setPreferredWidth(150);  
        tblHS.getColumnModel().getColumn(2).setPreferredWidth(100);  
        tblHS.getColumnModel().getColumn(3).setPreferredWidth(80);    
        tblHS.getColumnModel().getColumn(4).setPreferredWidth(200);  

        JScrollPane spHS = new JScrollPane(tblHS);
        spHS.setBorder(BorderFactory.createTitledBorder("Danh sách học sinh"));
        spHS.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                spLop,
                spHS
        );
        split.setResizeWeight(0.45);
        split.setDividerSize(8);
        add(split, "grow");


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


        addFocusEffect(txtMaLop);
        addFocusEffect(txtTenLop);
        addFocusEffect(txtSiSo);
        addFocusEffect(cboNamHoc);
        addFocusEffect(cboGVCN);

        updateButtonState();
    }


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

    private void generateAndSetMaLop() {
        Integer khoi = (Integer) cboKhoi.getSelectedItem();
        if (khoi != null) {
            String maLopAuto = generateMaLop(khoi);
            txtMaLop.setText(maLopAuto);
            txtMaLop.setEnabled(false);
        }
    }


    public String generateMaLop(int khoi) {
        Set<Integer> used = new HashSet<>();
        

        List<Lop> dsDB = lopBLL.getAll(); 
        for (Lop l : dsDB) {
            if (l.getMaLop() != null && l.getMaLop().matches(khoi + "A\\d+")) {
                try {
                    int so = Integer.parseInt(l.getMaLop().substring(2));
                    used.add(so);
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

                }
            }
        }
        

        for (Change change : bufferChanges) {
            Lop l = change.lop;
            if (l.getMaLop() != null && l.getMaLop().matches(khoi + "A\\d+")) {
                try {
                    int so = Integer.parseInt(l.getMaLop().substring(2));
                    
                    if (change.action.equals("ADD") || change.action.equals("UPDATE")) {
                        used.add(so);
                    }
                } catch (NumberFormatException | StringIndexOutOfBoundsException e) {

                }
            }
        }
        
        for (int i = 1; i <= 15; i++) {
            if (!used.contains(i)) {
                return khoi + "A" + i;
            }
        }
        

        return khoi + "A" + (used.size() + 1);
    }


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


        String maLopMoi = txtMaLop.getText().trim();
        String currentMaLop = null;
        if (tblLop.getSelectedRow() >= 0) {
            currentMaLop = modelLop.getValueAt(tblLop.getSelectedRow(), 0).toString();
        }
        

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
        

        List<Lop> dsDB = lopBLL.getAll(); 
        for (Lop l : dsDB) {
            if (l.getMaLop().equals(maLopMoi)) {

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


        modelLop.setValueAt(lop.getTenLop(), row, 1); 
        modelLop.setValueAt(lop.getSiSo(), row, 2); 
        modelLop.setValueAt(lop.getMaNH(), row, 3); 

        String tenGVCN = getTenGVFromMa(lop.getMaGVCN());
        modelLop.setValueAt(tenGVCN, row, 4); 


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



    private void fillFormFromTable(int row) {
        String maLop = modelLop.getValueAt(row, 0).toString();
        txtMaLop.setText(maLop);
        txtMaLop.setEnabled(false);
        
        txtTenLop.setText(modelLop.getValueAt(row, 1).toString());
        txtSiSo.setText(modelLop.getValueAt(row, 2).toString());
        

        if (maLop != null && maLop.length() > 0) {
            try {
                int khoi = Integer.parseInt(maLop.substring(0, 1));
                cboKhoi.setSelectedItem(khoi);
            } catch (NumberFormatException e) {

            }
        }


        txtMaLop.setText(maLop);
        
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

        lop.setTrangThai(1);
        return lop;
    }

    private void loadComboNamHoc() {
        cboNamHoc.removeAllItems();
        for (NamHoc nh : namHocBLL.getAllActive()) {
            cboNamHoc.addItem(nh);
        }
        cboNamHoc.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NamHoc) {
                    setText(((NamHoc) value).getTenNH());
                }
                return this;
            }
        });
    }

    private void loadComboGiaoVien() {
        cboGVCN.removeAllItems();
        for (GiaoVien gv : giaoVienBLL.getAll()) {
            cboGVCN.addItem(gv);
        }

        cboGVCN.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof GiaoVien) {
                    setText(((GiaoVien) value).getHoTen());
                }
                return this;
            }
        });
    }

    private void updateButtonState() {
        boolean selected = tblLop.getSelectedRow() >= 0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
        btnThem.setEnabled(!selected);
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
        String action; 
        Change(Lop lop, String action) {
            this.lop = lop;
            this.action = action;
        }
    }
    
    private void luuLop() {
        try {
            for (Change change : bufferChanges) {
                String result = "";
                switch (change.action) {
                    case "ADD":
                        result = lopBLL.themLop(change.lop);
                        break;
                    case "UPDATE":
                        result = lopBLL.suaLop(change.lop);
                        break;
                    case "DELETE":
                        result = lopBLL.xoaLop(change.lop.getMaLop());
                        break;
                }
                if (!result.toLowerCase().contains("thành công")) {
                    JOptionPane.showMessageDialog(this, "Lỗi: " + result, "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            bufferChanges.clear();
            JOptionPane.showMessageDialog(this, "Đã lưu thay đổi thành công!");
            dataChanged = false;
            updateSaveButtonState();
            loadTableLop();
            resetInputForm();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTableLop() {
        modelLop.setRowCount(0);
        for (Lop l : lopBLL.getAllActive()) { 
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
    

    public void refreshTableAfterChange() {
        loadTableLop();
    }
    
    private String getTenGVFromMa(String maGV) {

        for (int i = 0; i < cboGVCN.getItemCount(); i++) {
            GiaoVien gv = cboGVCN.getItemAt(i);
            if (gv.getMaGV().equals(maGV)) {
                return gv.getHoTen();
            }
        }

        GiaoVien gv = giaoVienBLL.getByMaFull(maGV);
        return gv != null ? gv.getHoTen() : maGV;
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
