package GUI;

import javax.swing.*;
import javax.swing.border.BorderFactory;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;
import net.miginfocom.swing.MigLayout;
import BusinessLogicLayer.DiemBLL;
import BusinessLogicLayer.HocSinhBLL;

public class Diem extends JPanel {
    
    // connection and context fields (previously provided by StudentDetailPanel)
    private Connection connection;
    private HocSinhBLL hocSinhBLL;      // may be useful later
    protected String currentMaHS;

    private JTextField txtMaHS, txtDiemMieng, txtDiem15Phut, txtDiemGiuaKy, txtDiemCuoiKy;
    private JButton btnSua;
    private DiemBLL diemBLL;

    public Diem(Connection connection) {
        // initialize context
        this.connection = connection;
        if (connection != null) {
            this.hocSinhBLL = new HocSinhBLL(connection);
        }
        initDiemBLL(connection);
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]"));
        setBorder(BorderFactory.createTitledBorder("ĐIỂM HỌC SINH"));
        
        // Mã học sinh
        add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField();
        txtMaHS.setEditable(false);
        add(txtMaHS, "grow");

        // Điểm miệng
        add(new JLabel("Điểm miệng:"));
        txtDiemMieng = new JTextField();
        add(txtDiemMieng, "grow");

        // Điểm 15 phút
        add(new JLabel("Điểm 15 phút:"));
        txtDiem15Phut = new JTextField();
        add(txtDiem15Phut, "grow");

        // Điểm giữa kỳ
        add(new JLabel("Điểm giữa kỳ:"));
        txtDiemGiuaKy = new JTextField();
        add(txtDiemGiuaKy, "grow");

        // Điểm cuối kỳ
        add(new JLabel("Điểm cuối kỳ:"));
        txtDiemCuoiKy = new JTextField();
        add(txtDiemCuoiKy, "grow");

        // Nút sửa
        btnSua = new JButton("Sửa");
        btnSua.addActionListener(e -> suaDiem());
        add(btnSua, "span, center");
    }
    
    // BLL initialization moved to constructor of superclass; only need DiemBLL here
    private void initDiemBLL(Connection connection) {
        try {
            diemBLL = new DiemBLL(connection);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khởi tạo DiemBLL: " + e.getMessage());
        }
    }
    
    public void loadData(String maHS) {
        // store current student id
        this.currentMaHS = maHS;
        try {
            txtMaHS.setText(maHS);
            
            List<DataObject.Diem> diems = diemBLL.getByMaHS(maHS);
            if (diems != null && !diems.isEmpty()) {
                DataObject.Diem d = diems.get(0);
                txtDiemMieng.setText(String.valueOf(d.getDiemThuongXuyen()));
                txtDiem15Phut.setText("");
                txtDiemGiuaKy.setText(String.valueOf(d.getDiemGiuaKy()));
                txtDiemCuoiKy.setText(String.valueOf(d.getDiemCuoiKy()));
            } else {
                clearFields();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }
    
    private void suaDiem() {
        if (currentMaHS == null || currentMaHS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn học sinh!");
            return;
        }
        
        try {
            List<DataObject.Diem> diems = diemBLL.getByMaHS(currentMaHS);
            if (diems == null || diems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy điểm của học sinh này!");
                return;
            }
            
            DataObject.Diem d = diems.get(0);
            if (!txtDiemMieng.getText().trim().isEmpty()) {
                d.setDiemThuongXuyen(Double.parseDouble(txtDiemMieng.getText()));
            }
            if (!txtDiemGiuaKy.getText().trim().isEmpty()) {
                d.setDiemGiuaKy(Double.parseDouble(txtDiemGiuaKy.getText()));
            }
            if (!txtDiemCuoiKy.getText().trim().isEmpty()) {
                d.setDiemCuoiKy(Double.parseDouble(txtDiemCuoiKy.getText()));
            }
            
            String result = diemBLL.sua(d);
            JOptionPane.showMessageDialog(this, result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số!");
        }
    }
    
    protected void clearFields() {
        txtDiemMieng.setText("");
        txtDiem15Phut.setText("");
        txtDiemGiuaKy.setText("");
        txtDiemCuoiKy.setText("");
    }

    // helper copied from previous base class
    protected void setCurrentMaHS(String maHS) {
        this.currentMaHS = maHS;
    }
}

