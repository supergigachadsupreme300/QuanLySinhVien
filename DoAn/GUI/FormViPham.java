package GUI;

import BusinessLogicLayer.ViPhamBLL;
import DataObject.ViPham;
import com.toedter.calendar.JDateChooser;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import BusinessLogicLayer.HocSinhBLL;

public class FormViPham extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMaVP, txtMaHS, txtMaHK, txtNoiDung, txtMucDo;
    private JDateChooser dateNgayVP;
    private JCheckBox chkTrangThai;

    private ViPhamBLL bll = new ViPhamBLL();
    private HocSinhBLL hocSinhBLL = new HocSinhBLL();

    private JTextField txtSearchTenHS;
    private JButton btnTim;

    public FormViPham() {
        initUI();
        loadTable();
    }

    private void initUI() {
        setLayout(new MigLayout("fillx", "[grow]", "[]10[]10[grow]"));

        JPanel pnlSearch = new JPanel(new MigLayout("insets 0", "[][grow]10[]", "[]"));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txtSearchTenHS = new JTextField();
        btnTim = new JButton("Tìm");
        pnlSearch.add(new JLabel("Tên HS:"));
        pnlSearch.add(txtSearchTenHS, "growx");
        pnlSearch.add(btnTim);
        add(pnlSearch, "growx, wrap");

        JLabel lblTitle = new JLabel("QUẢN LÝ VI PHẠM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle, "growx, wrap");

        JPanel panelMain = new JPanel(new MigLayout("fillx", "[45%][55%]", "[grow]"));
        add(panelMain, "grow");

        JPanel form = new JPanel(new MigLayout("fillx", "[100]", "[]10[]10[]10[]10[]10[]10[]10[]"));

        txtMaVP = new JTextField();
        txtMaHS = new JTextField();
        txtMaHK = new JTextField();
        txtNoiDung = new JTextField();
        txtMucDo = new JTextField();
        dateNgayVP = new JDateChooser();
        chkTrangThai = new JCheckBox("Đã xử lý");

        form.add(new JLabel("Mã vi phạm"));
        form.add(txtMaVP, "growx, wrap");

        form.add(new JLabel("Mã học sinh"));
        form.add(txtMaHS, "growx, wrap");

        form.add(new JLabel("Mã học kỳ"));
        form.add(txtMaHK, "growx, wrap");

        form.add(new JLabel("Ngày vi phạm"));
        form.add(dateNgayVP, "growx, wrap");

        form.add(new JLabel("Nội dung"));
        form.add(txtNoiDung, "growx, wrap");

        form.add(new JLabel("Mức độ"));
        form.add(txtMucDo, "growx, wrap");

        form.add(chkTrangThai, "span, wrap");

        JPanel pnlBtn = new JPanel(new MigLayout("fillx", "[grow][grow][grow][grow]"));

        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xóa");
        JButton btnClear = new JButton("Clear");

        pnlBtn.add(btnAdd, "growx");
        pnlBtn.add(btnUpdate, "growx");
        pnlBtn.add(btnDelete, "growx");
        pnlBtn.add(btnClear, "growx");

        form.add(pnlBtn, "span, growx");

        panelMain.add(form, "grow");

        model = new DefaultTableModel(new String[]{
                "Mã VP", "Mã HS", "Tên HS", "Mã HK", "Ngày VP", "Nội dung", "Mức độ", "Trạng thái"
        }, 0);

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        panelMain.add(scroll, "grow");

        btnAdd.addActionListener(e -> add());
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnClear.addActionListener(e -> clear());
        btnTim.addActionListener(e -> searchByTenHS());

        table.getSelectionModel().addListSelectionListener(e -> fillForm());
    }

    private void searchByTenHS() {
        String key = txtSearchTenHS.getText().trim().toLowerCase();
        if (key.isEmpty()) {
            loadTable();
            return;
        }
        model.setRowCount(0);
        List<ViPham> list = bll.getAll();
        for (ViPham vp : list) {
            String tenHS = hocSinhBLL.getByMa(vp.getMaHS()) != null ? hocSinhBLL.getByMa(vp.getMaHS()).getHoTen() : "";
            if (tenHS.toLowerCase().contains(key)) {
                model.addRow(new Object[]{
                        vp.getMaViPham(),
                        vp.getMaHS(),
                        tenHS,
                        vp.getMaHocKy(),
                        vp.getNgayViPham(),
                        vp.getNoiDung(),
                        vp.getMucDo(),
                        vp.isTrangThai() ? "Đã xử lý" : "Chưa xử lý"
                });
            }
        }
    }

    private void loadTable() {
        model.setRowCount(0);
        List<ViPham> list = bll.getAll();

        for (ViPham vp : list) {
            String tenHS = hocSinhBLL.getByMa(vp.getMaHS()) != null ? hocSinhBLL.getByMa(vp.getMaHS()).getHoTen() : "Không tìm thấy";
            model.addRow(new Object[]{
                    vp.getMaViPham(),
                    vp.getMaHS(),
                    tenHS,
                    vp.getMaHocKy(),
                    vp.getNgayViPham(),
                    vp.getNoiDung(),
                    vp.getMucDo(),
                    vp.isTrangThai() ? "Đã xử lý" : "Chưa xử lý"
            });
        }
    }

    private ViPham getForm() {
        ViPham vp = new ViPham();
        vp.setMaViPham(txtMaVP.getText().trim());
        vp.setMaHS(txtMaHS.getText().trim());
        vp.setMaHocKy(txtMaHK.getText().trim());
        vp.setNgayViPham(dateNgayVP.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        vp.setNoiDung(txtNoiDung.getText().trim());
        vp.setMucDo(txtMucDo.getText().trim());
        vp.setTrangThai(chkTrangThai.isSelected());
        return vp;
    }

    private void add() {
        if (bll.add(getForm())) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void update() {
        if (bll.update(getForm())) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    private void delete() {
        if (bll.delete(txtMaVP.getText())) {
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại");
        }
    }

    private void clear() {
        txtMaVP.setText("");
        txtMaHS.setText("");
        txtMaHK.setText("");
        txtNoiDung.setText("");
        txtMucDo.setText("");
        chkTrangThai.setSelected(false);
        dateNgayVP.setDate(null);
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        txtMaVP.setText(model.getValueAt(row, 0).toString());
        txtMaHS.setText(model.getValueAt(row, 1).toString());
        txtMaHK.setText(model.getValueAt(row, 2).toString());
        dateNgayVP.setDate(java.sql.Date.valueOf(model.getValueAt(row, 3).toString()));
        txtNoiDung.setText(model.getValueAt(row, 4).toString());
        txtMucDo.setText(model.getValueAt(row, 5).toString());
        chkTrangThai.setSelected(model.getValueAt(row, 6).toString().equals("Đã xử lý"));
    }
}