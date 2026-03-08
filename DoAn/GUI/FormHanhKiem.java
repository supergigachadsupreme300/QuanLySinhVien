package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import BusinessLogicLayer.HanhKiemBLL;
import BusinessLogicLayer.HocSinhBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.HocKyBLL;
import BusinessLogicLayer.NamHocBLL;
import DAO.DatabaseConnect;
import DataObject.HanhKiem;
import DataObject.HocSinh;
import DataObject.Lop;
import DataObject.HocKy;
import DataObject.NamHoc;
import java.sql.Connection;
import net.miginfocom.swing.MigLayout;

public class FormHanhKiem extends JPanel {

    private JTextField txtMaHS, txtTenHS;
    private JComboBox<String> cboLop;
    private JComboBox<String> cboHocKy, cboNamHoc, cboXepLoai;
    private JTextArea txtNhanXet;
    private JTable table;
    private DefaultTableModel model;
    private HanhKiemBLL hanhKiemBLL;
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    private LopBLL lopBLL = new LopBLL();
    private HocKyBLL hocKyBLL = new HocKyBLL();
    private NamHocBLL namHocBLL = new NamHocBLL();
    private DatabaseConnect db;
    private Connection con;
    private String filterMaHS = null;
    private boolean choPhepNhap = false;

    public FormHanhKiem() {
        initDB();
        initUI();
    }

    private void initDB() {
        db = new DatabaseConnect();
        con = db.openConnection();
        hanhKiemBLL = new HanhKiemBLL();
    }

    private void initUI() {

        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ HẠNH KIỂM", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0,102,204));

        add(lblTitle, "growx, wrap");

        add(createInputPanel(), "growx, wrap");
        add(createButtonPanel(), "growx, wrap");
        add(createTablePanel(), "grow");
        
        loadLopCombo();
        loadHocKyCombo();
        loadNamHocCombo();
        
        cboLop.addActionListener(e -> loadHocSinhTheoLop());
        cboHocKy.addActionListener(e -> loadHocSinhTheoLop());
        cboNamHoc.addActionListener(e -> loadHocSinhTheoLop());

        loadAllActiveHanhKiem();
    }

    private JPanel createInputPanel() {

        JPanel panel = new JPanel(new MigLayout(
            "insets 15",
            "[]15[grow]30[]15[grow]",
            "[]10[]"));

        panel.setBorder(BorderFactory.createTitledBorder("Thông tin hạnh kiểm"));

        panel.add(new JLabel("Lớp:"));
        cboLop = new JComboBox<>();
        panel.add(cboLop, "growx");

        panel.add(new JLabel("Học kỳ:"));
        cboHocKy = new JComboBox<>(new String[]{"HK1", "HK2"});
        panel.add(cboHocKy, "growx, wrap");

        panel.add(new JLabel("Năm học:"));
        cboNamHoc = new JComboBox<>(new String[]{
                "2023-2024","2024-2025","2025-2026"
        });
        panel.add(cboNamHoc, "growx");

        panel.add(new JLabel("Xếp loại:"));
        cboXepLoai = new JComboBox<>(new String[]{"Tốt", "Khá", "Trung bình", "Yếu"});
        cboXepLoai.setEnabled(false); 
        panel.add(cboXepLoai, "growx, wrap");

        panel.add(new JLabel("Nhận xét:"));
        txtNhanXet = new JTextArea(3,20);
        txtNhanXet.setEnabled(false); 
        panel.add(new JScrollPane(txtNhanXet), "span,growx");

        return panel;
    }

    private JScrollPane createTablePanel() {

        String[] cols = {
            "Mã HS",
            "Tên HS",
            "Lớp",
            "Học kỳ",
            "Năm học",
            "Xếp loại",
            "Nhận xét"
        };

        model = new DefaultTableModel(cols,0){

            @Override
            public boolean isCellEditable(int row,int col){

                if(!choPhepNhap) return false;

                return col == 5 || col == 6;
            }
        };

        table = new JTable(model);
        String[] xepLoai = {"Tốt", "Khá", "Trung bình", "Yếu"};

        JComboBox<String> comboXepLoai = new JComboBox<>(xepLoai);

        table.getColumnModel().getColumn(5)
                .setCellEditor(new DefaultCellEditor(comboXepLoai));

        table.setRowHeight(25);

        table.getTableHeader().setBackground(new Color(0,102,204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                Object x = model.getValueAt(row, 5);
                Object n = model.getValueAt(row, 6);
                cboXepLoai.setSelectedItem(x != null ? x.toString() : null);
                txtNhanXet.setText(n != null ? n.toString() : "");
            }
        });

        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {

        JPanel panel = new JPanel(new MigLayout("center", "[]20[]", "[]"));

        JButton btnCapNhat = createButton("Cập nhật");
        JButton btnLuu = createButton("Lưu");

        panel.add(btnCapNhat);
        panel.add(btnLuu);
        
        cboXepLoai.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && choPhepNhap) {
                model.setValueAt(cboXepLoai.getSelectedItem(), row, 5);
            }
        });

        txtNhanXet.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0 && choPhepNhap) {
                    model.setValueAt(txtNhanXet.getText(), row, 6);
                }
            }
        });

        btnCapNhat.addActionListener(e -> {
            choPhepNhap = true;
            table.setEnabled(true);     // mở bảng
            cboXepLoai.setEnabled(true); 
            txtNhanXet.setEnabled(true);
            JOptionPane.showMessageDialog(this,
                "Bạn có thể nhập hoặc sửa hạnh kiểm trực tiếp trên bảng");
        });
        btnLuu.addActionListener(e -> {
            if(!choPhepNhap){
                JOptionPane.showMessageDialog(this,"Hãy bấm Cập nhật trước");
                return;
            }
            luuTatCaHanhKiem();
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
            JOptionPane.showMessageDialog(this, "Nhập mã HS để tải hạnh kiểm.", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }
        model.setRowCount(0);
        HocSinh hs = hocSinhBLL.getByMa(maHS);
        String ten = hs != null ? hs.getHoTen() : "";
        String lop = hs != null ? hs.getMaLop() : "";
        java.util.List<HanhKiem> list = hanhKiemBLL.getByMaHS(maHS);
        if (list != null) {
            for (HanhKiem hk : list) {
                model.addRow(new Object[]{hk.getMaHS(), ten, lop, hk.getMaHocKy(), "", hk.getXepLoai(), hk.getNhanXet()});
            }
        }
    }
    
    private void loadNamHocCombo(){

        cboNamHoc.removeAllItems();

        java.util.List<NamHoc> list = namHocBLL.getAllActive();

        if(list == null) return;

        for(NamHoc nh : list){
            cboNamHoc.addItem(nh.getMaNH());
        }
    }
    
    
    private void loadHocSinhTheoLop(){

        model.setRowCount(0);

        String lop = cboLop.getSelectedItem().toString();
        String hk = cboHocKy.getSelectedItem().toString();
        String nam = cboNamHoc.getSelectedItem().toString();

        java.util.List<HocSinh> list = hocSinhBLL.getByMaLop(lop);

        for(HocSinh hs : list){

            HanhKiem hkcu = hanhKiemBLL.getHanhKiem(hs.getMaHS(),hk);

            if(hkcu != null){

                model.addRow(new Object[]{
                    hs.getMaHS(),
                    hs.getHoTen(),
                    hs.getMaLop(),
                    hk,
                    nam,
                    hkcu.getXepLoai(),
                    hkcu.getNhanXet()
                });

            }else{

                model.addRow(new Object[]{
                    hs.getMaHS(),
                    hs.getHoTen(),
                    hs.getMaLop(),
                    hk,
                    nam,
                    "",
                    ""
                });

            }
        }
    }
    private void loadLopCombo(){

        cboLop.removeAllItems();

        java.util.List<Lop> list = lopBLL.getAllActive();

        for(Lop lop : list){
            cboLop.addItem(lop.getMaLop());
        }
    }
    private void loadHocKyCombo(){

        cboHocKy.removeAllItems();

        java.util.List<HocKy> list = hocKyBLL.getAllActive();

        for(HocKy hk : list){
            cboHocKy.addItem(hk.getMaHK());
        }
    }

    public void setFilterMaHS(String maHS) { this.filterMaHS = maHS; }

    private void loadAllActiveHanhKiem() {
        model.setRowCount(0);
        java.util.List<HocSinh> students = hocSinhBLL.getAllActive();
        if (students == null) return;
        for (HocSinh hs : students) {
            java.util.List<HanhKiem> list = hanhKiemBLL.getByMaHS(hs.getMaHS());
            if (list == null) continue;
            for (HanhKiem hk : list) {
                model.addRow(new Object[]{hk.getMaHS(), hs.getHoTen(), hs.getMaLop(), hk.getMaHocKy(), "", hk.getXepLoai(), hk.getNhanXet()});
            }
        }
    }
    
    private void luuTatCaHanhKiem(){

        if(table.isEditing()){
            table.getCellEditor().stopCellEditing();
        }

        for(int i=0;i<model.getRowCount();i++){

            String maHS = model.getValueAt(i,0).toString();
            String hk = model.getValueAt(i,3).toString();

            String xep = "";
            String nhan = "";

            if(model.getValueAt(i,5) != null)
                xep = model.getValueAt(i,5).toString();

            if(model.getValueAt(i,6) != null)
                nhan = model.getValueAt(i,6).toString();

            HanhKiem h = hanhKiemBLL.getHanhKiem(maHS,hk);

            if(h == null){

                h = new HanhKiem();

                h.setMaHanhKiem("HK"+maHS+hk);
                h.setMaHS(maHS);
                h.setMaHocKy(hk);
                h.setXepLoai(xep);
                h.setNhanXet(nhan);
                h.setSoLanViPham(0);

                hanhKiemBLL.add(h);

            }else{

                h.setXepLoai(xep);
                h.setNhanXet(nhan);

                hanhKiemBLL.update(h);
            }
        }

        JOptionPane.showMessageDialog(this,"Lưu hạnh kiểm thành công");

        choPhepNhap = false;
        
        table.setEnabled(false);
        cboXepLoai.setEnabled(false);
        txtNhanXet.setEnabled(false);

        loadHocSinhTheoLop();
    }
    

}
