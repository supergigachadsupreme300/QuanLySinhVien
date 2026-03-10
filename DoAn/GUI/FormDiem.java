package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import DAO.DatabaseConnect;
import BusinessLogicLayer.DiemBLL;
import BusinessLogicLayer.HocSinhBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.MonHocBLL;
import BusinessLogicLayer.HocKyBLL;
import DataObject.Diem;
import DataObject.HocSinh;
import DataObject.Lop;
import DataObject.Mon;
import DataObject.HocKy;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import net.miginfocom.swing.MigLayout;
import javax.swing.event.ListSelectionListener;
import java.awt.Component;

public class FormDiem extends JPanel {

    private JTextField txtMaHS, txtTenHS, txtDiemTX, txtDiemGK, txtDiemCK;
    private JComboBox<String> cboHocKy;
    private JComboBox<String> cboLop;
    private JComboBox<String> cboMon;
    private JTable table;
    private DefaultTableModel model;
    private DatabaseConnect db;
    private Connection con;
    private DiemBLL diemBLL;
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    private LopBLL lopBLL = new LopBLL();
    private MonHocBLL monBLL = new MonHocBLL();
    private HocKyBLL hocKyBLL = new HocKyBLL();
    private String filterMaHS = null;
    private boolean choPhepNhap = false;

    public FormDiem() {
        initDB();
        initUI();
    }

    private void initDB() {
        db = new DatabaseConnect();
        con = db.openConnection();
        diemBLL = new DiemBLL(con);
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ ĐIỂM HỌC SINH", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0,102,204));
        add(lblTitle, "grow, wrap");

        add(createInputPanel(), "growx, wrap");
        add(createButtonPanel(), "growx, wrap");
        add(createTablePanel(), "grow");

        loadLopCombo();
        loadMonCombo();
        loadHocKyCombo();

        // Chọn phần tử đầu tiên nếu có
        if (cboLop.getItemCount() > 0) cboLop.setSelectedIndex(0);
        if (cboMon.getItemCount() > 0) cboMon.setSelectedIndex(0);
        if (cboHocKy.getItemCount() > 0) cboHocKy.setSelectedIndex(0);

        // Sử dụng ItemListener thay ActionListener
        cboLop.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadHocSinhTheoLop();
            }
        });
        cboMon.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadHocSinhTheoLop();
            }
        });
        cboHocKy.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                loadHocSinhTheoLop();
            }
        });

        // Load học sinh lần đầu
        loadHocSinhTheoLop();
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new MigLayout(
                "insets 15",
            "[]15[grow]30[]15[grow]",
            "[]10[]10[]"));

        panel.setBorder(BorderFactory.createTitledBorder("Thông tin điểm"));

        panel.add(new JLabel("Lớp:"));
        cboLop = new JComboBox<>();
        panel.add(cboLop, "growx");

        panel.add(new JLabel("Môn học:"));
        cboMon = new JComboBox<>();
        panel.add(cboMon, "growx, wrap");

        panel.add(new JLabel("Học kỳ:"));
        cboHocKy = new JComboBox<>();
        panel.add(cboHocKy, "growx");

        panel.add(new JLabel(""));
        panel.add(new JLabel(""), "wrap");

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {"Mã HS", "Tên HS", "Lớp", "Môn", "TX", "GK", "CK", "TB"};
        model = new DefaultTableModel(cols,0){

            @Override
            public boolean isCellEditable(int row,int column){
                if(!choPhepNhap) return false;
                return column == 4 || column == 5 || column == 6;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column){
                if(column == 4 || column == 5 || column == 6){
                    if(aValue != null && !aValue.toString().trim().isEmpty()){
                        try{
                            double diem = Double.parseDouble(aValue.toString());
                            if(diem < 0 || diem > 10){
                                JOptionPane.showMessageDialog(null,"Điểm phải từ 0 đến 10");
                                return;
                            }
                        }catch(Exception ex){
                            JOptionPane.showMessageDialog(null,"Điểm phải là số");
                            return;
                        }
                    }
                }
                super.setValueAt(aValue,row,column);
                if(column == 4 || column == 5 || column == 6){
                    try{
                        double tx = 0, gk = 0, ck = 0;
                        Object oTX = getValueAt(row,4);
                        Object oGK = getValueAt(row,5);
                        Object oCK = getValueAt(row,6);
                        if(oTX != null && !oTX.toString().isEmpty())
                            tx = Double.parseDouble(oTX.toString());
                        if(oGK != null && !oGK.toString().isEmpty())
                            gk = Double.parseDouble(oGK.toString());
                        if(oCK != null && !oCK.toString().isEmpty())
                            ck = Double.parseDouble(oCK.toString());
                        double tb = (tx + gk*2 + ck*3)/6;
                        tb = Math.round(tb*100.0)/100.0;
                        super.setValueAt(tb,row,7);
                    }catch(Exception ignored){}
                }
            }
        };
        table = new JTable(model);
        table.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "selectNextRowCell");

        table.getActionMap().put("selectNextRowCell", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                row++;
                if (row < table.getRowCount()) {
                    table.changeSelection(row, col, false, false);
                    int finalRow = row;
                    int finalCol = col;
                    SwingUtilities.invokeLater(() -> {
                        table.editCellAt(finalRow, finalCol);
                        Component comp = table.getEditorComponent();
                        if (comp != null) {
                            comp.requestFocusInWindow();
                        }
                    });
                }
            }
        });
        table.setSurrendersFocusOnKeystroke(true);
        table.putClientProperty("terminateEditOnFocusLost", true);
        table.setSurrendersFocusOnKeystroke(true);
        table.setCellSelectionEnabled(true);
        DefaultCellEditor editor = (DefaultCellEditor) table.getDefaultEditor(Object.class);
        editor.setClickCountToStart(1);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(0,102,204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);
        
        ListSelectionListener autoEdit = e -> {
            if(e.getValueIsAdjusting()) return;
            int row = table.getSelectedRow();
            int col = table.getSelectedColumn();
            if(row >= 0 && col >= 0 && table.isCellEditable(row,col)){
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        table.editCellAt(row,col);
                        Component editor = table.getEditorComponent();
                        if(editor != null){
                            editor.requestFocusInWindow();
                        }
                    }
                });
            }
        };
        table.getSelectionModel().addListSelectionListener(autoEdit);
        table.getColumnModel().getSelectionModel().addListSelectionListener(autoEdit);

        return new JScrollPane(table);
    }

    private void loadLopCombo(){
        cboLop.removeAllItems();
        java.util.List<Lop> list = lopBLL.getAllActive();
        for(Lop lop : list){
            cboLop.addItem(lop.getMaLop());
        }
    }
    private void loadMonCombo(){
        cboMon.removeAllItems();
        java.util.List<Mon> list = monBLL.getAllActive();
        for(Mon m : list){
            cboMon.addItem(m.getMaMon());
        }
    }
    private void loadHocKyCombo() {
        cboHocKy.removeAllItems();
        java.util.List<HocKy> list = hocKyBLL.getAllActive();
        if (list == null) return;
        for (HocKy hk : list) {
            cboHocKy.addItem(hk.getMaHK());
        }
    }
    private void loadHocSinhTheoLop(){
        model.setRowCount(0);
        Object lop = cboLop.getSelectedItem();
        Object mon = cboMon.getSelectedItem();
        Object hk = cboHocKy.getSelectedItem();
        if(lop == null || mon == null || hk == null){
            return;
        }
        String maLop = lop.toString();
        String maMon = mon.toString();
        String maHK = hk.toString();
        java.util.List<HocSinh> list = hocSinhBLL.getByMaLop(maLop);
        for(HocSinh hs : list){
            Diem d = diemBLL.getDiem(hs.getMaHS(), maMon, maHK);
            if(d != null){
                model.addRow(new Object[]{
                    hs.getMaHS(),
                    hs.getHoTen(),
                    hs.getMaLop(),
                    maMon,
                    d.getDiemThuongXuyen(),
                    d.getDiemGiuaKy(),
                    d.getDiemCuoiKy(),
                    d.getDiemTBMonHocKy()
                });
            }else{
                model.addRow(new Object[]{
                    hs.getMaHS(),
                    hs.getHoTen(),
                    hs.getMaLop(),
                    maMon,
                    "",
                    "",
                    "",
                    ""
                });
            }
        }
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new MigLayout("center", "[]15[]15[]15[]15[]", "[]"));
        JButton btnCapNhat = createButton("Cập nhật");
        JButton btnLuu = createButton("Lưu");
        panel.add(btnCapNhat);
        panel.add(btnLuu);

        btnCapNhat.addActionListener(e -> {
            choPhepNhap = true;
            JOptionPane.showMessageDialog(this,
                    "Bạn có thể nhập hoặc sửa điểm trực tiếp trên bảng");
        });

        btnLuu.addActionListener(e -> {
            if(!choPhepNhap){
                JOptionPane.showMessageDialog(this,"Hãy bấm Cập nhật trước");
                return;
            }
            luuTatCaDiem();
            choPhepNhap = false;
            table.repaint(); 
        });
       
        return panel;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }

    public void loadByMaHS() {
        String maHS = (filterMaHS != null && !filterMaHS.isEmpty()) ? filterMaHS : txtMaHS.getText().trim();
        if (maHS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã HS để tải điểm.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setRowCount(0);
        HocSinh hs = hocSinhBLL.getByMa(maHS);
        String ten = hs != null ? hs.getHoTen() : "";
        String lop = hs != null ? hs.getMaLop() : "";
        for (Diem d : diemBLL.getByMaHS(maHS)) {
            model.addRow(new Object[]{d.getMaHS(), ten, lop, d.getMaMon(), d.getDiemThuongXuyen(), d.getDiemGiuaKy(), d.getDiemCuoiKy(), d.getDiemTBMonHocKy()});
        }
    }

    public void setFilterMaHS(String maHS) { this.filterMaHS = maHS; }

    private void luuTatCaDiem(){
        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        String maMon = cboMon.getSelectedItem().toString();
        String maHK = cboHocKy.getSelectedItem().toString();

        for(int i=0;i<model.getRowCount();i++){
            String maHS = model.getValueAt(i,0).toString();
            try{
                Object oTX = model.getValueAt(i,4);
                Object oGK = model.getValueAt(i,5);
                Object oCK = model.getValueAt(i,6);
                double tx = 0, gk = 0, ck = 0;
                if(oTX != null && !oTX.toString().trim().isEmpty()){
                    tx = Double.parseDouble(oTX.toString());
                }
                if(oGK != null && !oGK.toString().trim().isEmpty()){
                    gk = Double.parseDouble(oGK.toString());
                }
                if(oCK != null && !oCK.toString().trim().isEmpty()){
                    ck = Double.parseDouble(oCK.toString());
                }
                double tb = (tx + gk*2 + ck*3)/6;
                tb = Math.round(tb*100.0)/100.0;
                model.setValueAt(tb, i, 7);

                Diem d = diemBLL.getDiem(maHS,maMon,maHK);
                if(d == null){
                    d = new Diem();
                    d.setMaDiem("D"+maHS+maMon+maHK);
                    d.setMaHS(maHS);
                    d.setMaMon(maMon);
                    d.setMaHocKy(maHK);
                    d.setDiemThuongXuyen(tx);
                    d.setDiemGiuaKy(gk);
                    d.setDiemCuoiKy(ck);
                    d.setDiemTBMonHocKy(tb);
                    diemBLL.them(d);
                }else{
                    d.setDiemThuongXuyen(tx);
                    d.setDiemGiuaKy(gk);
                    d.setDiemCuoiKy(ck);
                    d.setDiemTBMonHocKy(tb);
                    diemBLL.sua(d);
                }
            }catch(Exception ex){
                System.out.println("Lỗi dòng " + i);
                ex.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this,"Lưu điểm thành công!");
        choPhepNhap = false;
        loadHocSinhTheoLop();
        table.repaint();
    }
}
