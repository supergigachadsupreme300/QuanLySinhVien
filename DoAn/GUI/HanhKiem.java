package GUI;

import javax.swing.*;
import javax.swing.BorderFactory;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import BusinessLogicLayer.HanhKiemBLL;
import BusinessLogicLayer.HocSinhBLL;

public class HanhKiem extends JPanel {
    
    private Connection connection;
    private HocSinhBLL hocSinhBLL;
    protected String currentMaHS;
    protected String filterMaHS;

    private JTextField txtMaHS;
    private JComboBox<String> cbXepLoai;
    private JTextArea txtNhanXet;
    private JButton btnSua;
    private HanhKiemBLL hanhKiemBLL;

    public HanhKiem(Connection connection) {
        this.connection = connection;
        if (connection != null) {
            this.hocSinhBLL = new HocSinhBLL(connection);
        }
        initHanhKiemBLL(connection);
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]10[]"));
        setBorder(BorderFactory.createTitledBorder("HẠNH KIỂM HỌC SINH"));

        // Mã học sinh
        add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField();
        txtMaHS.setEditable(false);
        add(txtMaHS, "grow");

        // Xếp loại
        add(new JLabel("Xếp loại:"));
        cbXepLoai = new JComboBox<>(
                new String[]{"Tốt", "Khá", "Trung bình", "Yếu"}
        );
        add(cbXepLoai, "grow");

        // Nhận xét
        add(new JLabel("Nhận xét:"));
        txtNhanXet = new JTextArea(3, 20);
        add(new JScrollPane(txtNhanXet), "grow");

        // Nút sửa
        btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> suaHanhKiem());
        add(btnSua, "span, center");
    }
    
    private void initHanhKiemBLL(Connection connection) {
        try {
            hanhKiemBLL = new HanhKiemBLL();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo HanhKiemBLL: " + e.getMessage());
        }
    }
    
    public void loadData(String maHS) {
        this.currentMaHS = maHS;
        try {
            txtMaHS.setText(maHS);
            
            List<DataObject.HanhKiem> hkList = hanhKiemBLL.getByMaHS(maHS);
            if (hkList != null && !hkList.isEmpty()) {
                DataObject.HanhKiem hk = hkList.get(0);
                cbXepLoai.setSelectedItem(hk.getXepLoai());
                txtNhanXet.setText(hk.getNhanXet());
            } else {
                clearFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    // alias for older callers
    public void loadHanhKiem(String maHS) {
        loadData(maHS);
    }
    
    private void suaHanhKiem() {
        if (currentMaHS == null || currentMaHS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học sinh!");
            return;
        }
        
        try {
            List<DataObject.HanhKiem> hkList = hanhKiemBLL.getByMaHS(currentMaHS);
            if (hkList == null || hkList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin hạnh kiểm!");
                return;
            }
            
            DataObject.HanhKiem hk = hkList.get(0);
            hk.setXepLoai((String) cbXepLoai.getSelectedItem());
            hk.setNhanXet(txtNhanXet.getText());
            
            boolean result = hanhKiemBLL.update(hk);
            if (result) {
                JOptionPane.showMessageDialog(this, "Cập nhật hạnh kiểm thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hạnh kiểm thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
    
    protected void clearFields() {
        cbXepLoai.setSelectedIndex(0);
        txtNhanXet.setText("");
    }

    protected void setCurrentMaHS(String maHS) {
        this.currentMaHS = maHS;
    }

    public void setFilterMaHS(String maHS) { this.filterMaHS = maHS; }

    public void loadByMaHS() {
        String ma = (filterMaHS != null && !filterMaHS.isEmpty()) ? filterMaHS : this.currentMaHS;
        if (ma == null || ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có mã HS để tải hạnh kiểm.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        loadData(ma);
    }
}

