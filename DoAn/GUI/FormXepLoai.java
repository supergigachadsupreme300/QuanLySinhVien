package GUI;

import BusinessLogicLayer.DiemBLL;
import BusinessLogicLayer.HanhKiemBLL;
import BusinessLogicLayer.HocKyBLL;
import BusinessLogicLayer.HocSinhBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.NamHocBLL;
import DataObject.HanhKiem;
import DataObject.Lop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class FormXepLoai extends JPanel {

    private JTextField txtMaHS, txtTenHS, txtDiemTB;
    private JComboBox<String> cboLop, cboHocKy, cboNamHoc, cboHocLuc, cboHanhKiem;
    private JTextArea txtNhanXet;
    private LopBLL lopBLL = new LopBLL();
    private HocKyBLL hocKyBLL = new HocKyBLL();
    private NamHocBLL namHocBLL = new NamHocBLL();
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();
    private DiemBLL diemBLL = new DiemBLL();
    private HanhKiemBLL hanhKiemBLL = new HanhKiemBLL();
    private boolean choPhepNhap = false;

    private JTable table;
    private DefaultTableModel model;

    public FormXepLoai() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 10", "[grow]", "[][grow][]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ XẾP LOẠI", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0,102,204));
        add(lblTitle, "grow, wrap");

        add(createMainPanel(), "grow, wrap");
        add(createButtonPanel(), "dock south");
        
        loadLopCombo();
        loadHocKyCombo();
        loadNamHocCombo();

        cboLop.addActionListener(e -> loadHocSinhTheoLop());
        cboHocKy.addActionListener(e -> loadHocSinhTheoLop());
        cboNamHoc.addActionListener(e -> loadHocSinhTheoLop());
        
        table.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                capNhatTuBang();
            }
        });
        setEditable(false);

    }
    private void setEditable(boolean editable){

        cboHanhKiem.setEnabled(editable);
        txtNhanXet.setEditable(editable);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("fill, insets 0", "[380!][grow]", "[grow]"));

        panel.add(createInputPanel(), "growy");
        panel.add(createTablePanel(), "grow");

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 10", "[right][grow]", 
                "[]15[]15[]15[]15[]15[]15[]15[]15[]"));

        panel.setBorder(BorderFactory.createTitledBorder("Thông tin xếp loại"));

        panel.add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField();
        panel.add(txtMaHS, "growx, wrap");

        panel.add(new JLabel("Tên HS:"));
        txtTenHS = new JTextField();
        panel.add(txtTenHS, "growx, wrap");

        panel.add(new JLabel("Lớp:"));
        cboLop = new JComboBox<>();
        panel.add(cboLop, "growx, wrap");

        panel.add(new JLabel("Học kỳ:"));
        cboHocKy = new JComboBox<>();
        panel.add(cboHocKy, "growx, wrap");

        panel.add(new JLabel("Năm học:"));
        cboNamHoc = new JComboBox<>();
        panel.add(cboNamHoc, "growx, wrap");

        panel.add(new JLabel("Điểm TB:"));
        txtDiemTB = new JTextField();
        panel.add(txtDiemTB, "growx, wrap");

        panel.add(new JLabel("Học lực:"));
        cboHocLuc = new JComboBox<>(new String[]{"Giỏi", "Khá", "Trung bình", "Yếu"});
        panel.add(cboHocLuc, "growx, wrap");

        panel.add(new JLabel("Hạnh kiểm:"));
        cboHanhKiem = new JComboBox<>(new String[]{"Tốt", "Khá", "Trung bình", "Yếu"});
        panel.add(cboHanhKiem, "growx, wrap");

        panel.add(new JLabel("Nhận xét:"));
        txtNhanXet = new JTextArea(3, 20);
        panel.add(new JScrollPane(txtNhanXet), "growx, wrap");

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {
            "Mã HS", "Tên HS", "Lớp", "Học Kỳ", "Năm học", 
            "Điểm TB", "Học lực", "Hạnh kiểm", "Nhận xét"
        };

        model = new DefaultTableModel(cols,0){

            @Override
            public boolean isCellEditable(int row,int column){

                if(!choPhepNhap) return false;

                return column == 8; 
                // Hạnh kiểm + Nhận xét
            }
        };
        table = new JTable(model);

        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new MigLayout("center", "[]15[]15[]15[]15[]", "[]"));

        JButton btnCapNhat = createButton("Cập nhật");
        JButton btnLuu = createButton("Lưu");
        JButton btnLamMoi = createButton("Làm mới");

        panel.add(btnCapNhat);
        panel.add(btnLuu);
        panel.add(btnLamMoi);
        
        btnCapNhat.addActionListener(e -> {

            choPhepNhap = true;

            JOptionPane.showMessageDialog(this,
                "Bạn có thể sửa Nhận xét trực tiếp trên bảng");
        });
        btnLuu.addActionListener(e -> {

            if(!choPhepNhap){
                JOptionPane.showMessageDialog(this,"Hãy bấm Cập nhật trước");
                return;
            }

            luuTatCaXepLoai();

            choPhepNhap = false;

            table.repaint();
        });
        btnLamMoi.addActionListener(e -> lamMoiForm());

        return panel;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }
    
    private void loadLopCombo(){

        cboLop.removeAllItems();

        java.util.List<Lop> list = lopBLL.getAllActive();

        for(Lop lop : list){
            cboLop.addItem(lop.getMaLop());
        }
    }
    
    private void loadHocKyCombo() {
        cboHocKy.removeAllItems();
        java.util.List<DataObject.HocKy> list = hocKyBLL.getAllActive();
        if (list == null) return;
        for (DataObject.HocKy hk : list) {
            cboHocKy.addItem(hk.getMaHK());
        }
    }
    private void loadNamHocCombo() {
        cboNamHoc.removeAllItems();
        java.util.List<DataObject.NamHoc> list = namHocBLL.getAllActive();
        if (list == null) return;
        for (DataObject.NamHoc nh : list) {
            cboNamHoc.addItem(nh.getMaNH());
        }
    }
    
    private void loadHocSinhTheoLop(){

        model.setRowCount(0);

        String maLop = (String) cboLop.getSelectedItem();
        String maHK = (String) cboHocKy.getSelectedItem();
        String maNH = (String) cboNamHoc.getSelectedItem();

        if(maLop == null || maHK == null || maNH == null){
            return;
        }

        java.util.List<DataObject.HocSinh> list = hocSinhBLL.getByMaLop(maLop);

        for(DataObject.HocSinh hs : list){

            double tb = diemBLL.getDiemTBHocKy(hs.getMaHS(), maHK);

            String hocLuc = xepHocLuc(tb);
            DataObject.HanhKiem hk = hanhKiemBLL.getHanhKiem(hs.getMaHS(), maHK);
            String hanhKiem = "";
            if(hk != null){
                hanhKiem = hk.getXepLoai();
            }

            model.addRow(new Object[]{
                hs.getMaHS(),
                hs.getHoTen(),
                maLop,
                maHK,
                maNH,
                String.format("%.2f", tb),
                hocLuc,
                hanhKiem,
                ""
            });
        }
    }
    private String xepHocLuc(double tb){

        if(tb >= 8) return "Giỏi";
        if(tb >= 6.5) return "Khá";
        if(tb >= 5) return "Trung bình";
        return "Yếu";
    }
    
    private void capNhatTuBang(){
        int row = table.getSelectedRow();
        if(row == -1){
            return;
        }
        txtMaHS.setText(model.getValueAt(row,0).toString());
        txtTenHS.setText(model.getValueAt(row,1).toString());
        cboLop.setSelectedItem(model.getValueAt(row,2).toString());
        cboHocKy.setSelectedItem(model.getValueAt(row,3).toString());
        cboNamHoc.setSelectedItem(model.getValueAt(row,4).toString());
        txtDiemTB.setText(model.getValueAt(row,5).toString());
        cboHocLuc.setSelectedItem(model.getValueAt(row,6).toString());
        cboHanhKiem.setSelectedItem(model.getValueAt(row,7).toString());
        txtNhanXet.setText(model.getValueAt(row,8).toString());
    }
    private void luuTatCaXepLoai(){

        for(int i=0;i<model.getRowCount();i++){

            try{

                String maHS = model.getValueAt(i,0).toString();
                String maHK = model.getValueAt(i,3).toString();

                Object oHK = model.getValueAt(i,7);
                Object oNX = model.getValueAt(i,8);

                String hanhKiem = oHK == null ? "" : oHK.toString();
                String nhanXet = oNX == null ? "" : oNX.toString();

                HanhKiem hk = hanhKiemBLL.getHanhKiem(maHS,maHK);

                if(hk == null){

                    hk = new HanhKiem();

                    hk.setMaHanhKiem("HK"+maHS+maHK);
                    hk.setMaHS(maHS);
                    hk.setMaHocKy(maHK);
                    hk.setXepLoai(hanhKiem);
                    hk.setNhanXet(nhanXet);
                    hk.setSoLanViPham(0);

                    hanhKiemBLL.add(hk);

                }else{

                    hk.setXepLoai(hanhKiem);
                    hk.setNhanXet(nhanXet);

                    hanhKiemBLL.update(hk);
                }

            }catch(Exception ex){

                System.out.println("Lỗi dòng "+i);
                ex.printStackTrace();
            }
        }

        JOptionPane.showMessageDialog(this,"Lưu xếp loại thành công");

        loadHocSinhTheoLop();
    }
    private void lamMoiForm(){

        txtMaHS.setText("");
        txtTenHS.setText("");
        txtDiemTB.setText("");
        txtNhanXet.setText("");

        cboHocLuc.setSelectedIndex(0);
        cboHanhKiem.setSelectedIndex(0);
    }
}
