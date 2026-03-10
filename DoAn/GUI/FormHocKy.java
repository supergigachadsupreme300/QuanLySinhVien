package GUI;

import BusinessLogicLayer.HocKyBLL;
import DataObject.HocKy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import net.miginfocom.swing.MigLayout;

public class FormHocKy extends JPanel {

    private HocKyBLL hkBLL = new HocKyBLL();

    private boolean dataChanged = false;
    private List<Change> bufferChanges = new ArrayList<>();

    private String originalMaHK = null;

    private JTextField txtMaHK, txtTenHK, txtMaNH, txtNgayBD, txtNgayKT;
    private JCheckBox chkTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnClear, btnLuu;
    private JTable tblHocKy;
    private DefaultTableModel modelHocKy;

    public FormHocKy() {
        initUI();
        loadTableHocKy();
        updateButtonState();
    }

    private void initUI() {

        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ HỌC KỲ", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlForm = new JPanel(new MigLayout("insets 15", "[]15[grow]", "[]10[]10[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Thông tin học kỳ"));

        txtMaHK = new JTextField();
        txtTenHK = new JTextField();
        txtMaNH = new JTextField();
        txtNgayBD = new JTextField();
        txtNgayKT = new JTextField();
        chkTrangThai = new JCheckBox("Hoạt động", true);

        pnlForm.add(new JLabel("Mã học kỳ:"));
        pnlForm.add(txtMaHK, "growx, wrap");

        pnlForm.add(new JLabel("Tên học kỳ:"));
        pnlForm.add(txtTenHK, "growx, wrap");

        pnlForm.add(new JLabel("Mã năm học:"));
        pnlForm.add(txtMaNH, "growx, wrap");

        pnlForm.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):"));
        pnlForm.add(txtNgayBD, "growx, wrap");

        pnlForm.add(new JLabel("Ngày kết thúc (yyyy-MM-dd):"));
        pnlForm.add(txtNgayKT, "growx, wrap");

        pnlForm.add(new JLabel("Trạng thái:"));
        pnlForm.add(chkTrangThai);

        add(pnlForm, "growx, wrap");

        JPanel pnlBtn = new JPanel();

        btnThem = createButton("Thêm", new Color(34,139,34));
        btnSua = createButton("Sửa", new Color(255,140,0));
        btnXoa = createButton("Xóa", new Color(220,20,60));
        btnClear = createButton("Làm mới", new Color(70,130,180));
        btnLuu = createButton("Lưu", new Color(150,150,150));
        btnLuu.setEnabled(false);

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
        pnlBtn.add(btnLuu);

        add(pnlBtn, "growx, wrap");

        modelHocKy = new DefaultTableModel(
                new String[]{"Mã HK","Tên HK","Mã NH","Ngày BD","Ngày KT","Trạng thái"},0){
            public boolean isCellEditable(int r,int c){return false;}
        };

        tblHocKy = new JTable(modelHocKy);
        styleTable(tblHocKy);

        add(new JScrollPane(tblHocKy),"grow");

        btnThem.addActionListener(e -> themHocKy());
        btnSua.addActionListener(e -> suaHocKy());
        btnXoa.addActionListener(e -> xoaHocKy());
        btnClear.addActionListener(e -> clearForm());
        btnLuu.addActionListener(e -> luuHocKy());

        tblHocKy.getSelectionModel().addListSelectionListener(e -> {
            if(e.getValueIsAdjusting()) return;
            int row = tblHocKy.getSelectedRow();
            if(row>=0){
                fillFormFromTable(row);
            }
            updateButtonState();
        });
    }

    private boolean validateForm(){
        if(txtMaHK.getText().trim().isEmpty()
                || txtTenHK.getText().trim().isEmpty()
                || txtMaNH.getText().trim().isEmpty()
                || txtNgayBD.getText().trim().isEmpty()
                || txtNgayKT.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,"Vui lòng nhập đầy đủ thông tin!");
            return false;
        }
        return true;
    }

    private void themHocKy(){

        if(!validateForm()) return;

        String ma = txtMaHK.getText().trim();


        for(Change c : bufferChanges){
            if(c.hk.getMaHK().equals(ma) && !c.action.equals("DELETE")){
                JOptionPane.showMessageDialog(this,"Mã học kỳ đã tồn tại!");
                return;
            }
        }


        if(hkBLL.getByMaHK(ma) != null){
            JOptionPane.showMessageDialog(this,"Mã học kỳ đã tồn tại!");
            return;
        }

        try{
            LocalDate bd = LocalDate.parse(txtNgayBD.getText().trim());
            LocalDate kt = LocalDate.parse(txtNgayKT.getText().trim());

            if(bd.isAfter(kt)){
                JOptionPane.showMessageDialog(this,"Ngày bắt đầu phải trước ngày kết thúc!");
                return;
            }

            HocKy hk = new HocKy(
                    ma,
                    txtTenHK.getText().trim(),
                    txtMaNH.getText().trim(),
                    bd,
                    kt,
                    chkTrangThai.isSelected()?1:0
            );

            modelHocKy.addRow(new Object[]{
                    hk.getMaHK(),
                    hk.getTenHK(),
                    hk.getMaNH(),
                    hk.getNgayBatDau(),
                    hk.getNgayKetThuc(),
                    hk.getTrangThai()==1?"Hoạt động":"Ngừng"
            });

            bufferChanges.add(new Change(hk,"ADD"));
            dataChanged=true;
            updateSaveButtonState();
            resetInputForm();

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Sai định dạng ngày yyyy-MM-dd");
        }
    }

    private void suaHocKy(){

        int row = tblHocKy.getSelectedRow();
        if(row<0){ JOptionPane.showMessageDialog(this,"Chọn học kỳ cần sửa!"); return;}

        if(!validateForm()) return;

        try{
            LocalDate bd = LocalDate.parse(txtNgayBD.getText().trim());
            LocalDate kt = LocalDate.parse(txtNgayKT.getText().trim());

            if(bd.isAfter(kt)){
                JOptionPane.showMessageDialog(this,"Ngày bắt đầu phải trước ngày kết thúc!");
                return;
            }

            HocKy hk = new HocKy(
                    originalMaHK,
                    txtTenHK.getText().trim(),
                    txtMaNH.getText().trim(),
                    bd,
                    kt,
                    chkTrangThai.isSelected()?1:0
            );

            modelHocKy.setValueAt(hk.getTenHK(),row,1);
            modelHocKy.setValueAt(hk.getMaNH(),row,2);
            modelHocKy.setValueAt(hk.getNgayBatDau(),row,3);
            modelHocKy.setValueAt(hk.getNgayKetThuc(),row,4);
            modelHocKy.setValueAt(hk.getTrangThai()==1?"Hoạt động":"Ngừng",row,5);

            handleUpdateBuffer(hk);

            dataChanged=true;
            updateSaveButtonState();
            resetInputForm();

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Sai định dạng ngày yyyy-MM-dd");
        }
    }

    private void xoaHocKy(){

        int row = tblHocKy.getSelectedRow();
        if(row<0){ JOptionPane.showMessageDialog(this,"Chọn học kỳ cần xóa!"); return;}

        String maHK = modelHocKy.getValueAt(row,0).toString();
        modelHocKy.removeRow(row);

        handleDeleteBuffer(maHK);

        dataChanged=true;
        updateSaveButtonState();
        resetInputForm();
    }

    private void luuHocKy(){

        for(Change c:bufferChanges){

            boolean result = false;

            switch(c.action){
                case "ADD": result = hkBLL.themHocKy(c.hk); break;
                case "UPDATE": result = hkBLL.suaHocKy(c.hk); break;
                case "DELETE": result = hkBLL.xoaHocKy(c.hk.getMaHK()); break;
            }

            if(!result){
                JOptionPane.showMessageDialog(this,"Lỗi khi lưu dữ liệu!");
                return;
            }
        }

        bufferChanges.clear();
        dataChanged=false;
        updateSaveButtonState();
        loadTableHocKy();

        JOptionPane.showMessageDialog(this,"Đã lưu thành công!");
    }

    private void handleUpdateBuffer(HocKy hk){
        for(Change c : bufferChanges){
            if(c.hk.getMaHK().equals(hk.getMaHK())){
                if(c.action.equals("ADD")){
                    c.hk = hk;
                    return;
                }
                c.hk = hk;
                c.action="UPDATE";
                return;
            }
        }
        bufferChanges.add(new Change(hk,"UPDATE"));
    }

    private void handleDeleteBuffer(String maHK){
        for(int i=0;i<bufferChanges.size();i++){
            Change c = bufferChanges.get(i);

            if(c.hk.getMaHK().equals(maHK)){
                if(c.action.equals("ADD")){
                    bufferChanges.remove(i);
                    return;
                }
                c.action="DELETE";
                return;
            }
        }

        HocKy hk = new HocKy();
        hk.setMaHK(maHK);
        bufferChanges.add(new Change(hk,"DELETE"));
    }

    private void loadTableHocKy(){
        modelHocKy.setRowCount(0);
        for(HocKy hk: hkBLL.getAllActive()){
            modelHocKy.addRow(new Object[]{
                    hk.getMaHK(),
                    hk.getTenHK(),
                    hk.getMaNH(),
                    hk.getNgayBatDau(),
                    hk.getNgayKetThuc(),
                    hk.getTrangThai()==1?"Hoạt động":"Ngừng"
            });
        }
    }

    private void fillFormFromTable(int row){
        originalMaHK = modelHocKy.getValueAt(row,0).toString();
        txtMaHK.setText(originalMaHK);
        txtTenHK.setText(modelHocKy.getValueAt(row,1).toString());
        txtMaNH.setText(modelHocKy.getValueAt(row,2).toString());
        txtNgayBD.setText(modelHocKy.getValueAt(row,3).toString());
        txtNgayKT.setText(modelHocKy.getValueAt(row,4).toString());
        chkTrangThai.setSelected(modelHocKy.getValueAt(row,5).toString().equals("Hoạt động"));
        txtMaHK.setEditable(false);
    }

    private void resetInputForm(){
        txtMaHK.setText("");
        txtTenHK.setText("");
        txtMaNH.setText("");
        txtNgayBD.setText("");
        txtNgayKT.setText("");
        chkTrangThai.setSelected(true);
        txtMaHK.setEditable(true);
        originalMaHK=null;
        tblHocKy.clearSelection();
        updateButtonState();
    }

    private void clearForm(){
        resetInputForm();
        bufferChanges.clear();
        dataChanged=false;
        updateSaveButtonState();
        loadTableHocKy();
    }

    private void updateButtonState(){
        boolean selected = tblHocKy.getSelectedRow()>=0;
        btnSua.setEnabled(selected);
        btnXoa.setEnabled(selected);
        btnThem.setEnabled(!selected);
    }

    private void updateSaveButtonState(){
        if(dataChanged){
            btnLuu.setEnabled(true);
            btnLuu.setBackground(new Color(34,139,34));
        }else{
            btnLuu.setEnabled(false);
            btnLuu.setBackground(new Color(150,150,150));
        }
    }

    private JButton createButton(String text, Color bg){
        JButton btn=new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial",Font.BOLD,12));
        btn.setPreferredSize(new Dimension(100,35));
        return btn;
    }

    private void styleTable(JTable tbl){
        tbl.setRowHeight(25);
        tbl.getTableHeader().setFont(new Font("Arial",Font.BOLD,12));
        tbl.getTableHeader().setBackground(new Color(0,102,204));
        tbl.getTableHeader().setForeground(Color.WHITE);
    }

    private static class Change{
        HocKy hk;
        String action;
        Change(HocKy hk,String action){
            this.hk=hk;
            this.action=action;
        }
    }
}