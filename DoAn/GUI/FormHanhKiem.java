package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class FormHanhKiem extends JPanel {

    private JTextField txtMaHS, txtTenHS, txtLop;
    private JComboBox<String> cboHocKy, cboNamHoc, cboXepLoai;
    private JTextArea txtNhanXet;
    private JTable table;
    private DefaultTableModel model;

    public FormHanhKiem() {
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 10", "[grow]", "[][grow][]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ HẠNH KIỂM", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(new Color(0,102,204));
        add(lblTitle, "dock north, wrap");

        add(createMainPanel(), "grow, wrap");
        add(createButtonPanel(), "dock south");
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new MigLayout("fill, insets 0", "[350!][grow]", "[grow]"));

        panel.add(createInputPanel(), "growy");
        panel.add(createTablePanel(), "grow");

        return panel;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 10", "[right][grow]", "[]15[]15[]15[]15[]15[]15[]"));

        panel.setBorder(BorderFactory.createTitledBorder("Thông tin hạnh kiểm"));

        panel.add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField();
        panel.add(txtMaHS, "growx, wrap");

        panel.add(new JLabel("Tên HS:"));
        txtTenHS = new JTextField();
        panel.add(txtTenHS, "growx, wrap");

        panel.add(new JLabel("Lớp:"));
        txtLop = new JTextField();
        panel.add(txtLop, "growx, wrap");

        panel.add(new JLabel("Học kỳ:"));
        cboHocKy = new JComboBox<>(new String[]{"HK1", "HK2"});
        panel.add(cboHocKy, "growx, wrap");

        panel.add(new JLabel("Năm học:"));
        cboNamHoc = new JComboBox<>(new String[]{"2023-2024", "2024-2025", "2025-2026"});
        panel.add(cboNamHoc, "growx, wrap");

        panel.add(new JLabel("Xếp loại:"));
        cboXepLoai = new JComboBox<>(new String[]{"Tốt", "Khá", "Trung bình", "Yếu"});
        panel.add(cboXepLoai, "growx, wrap");

        panel.add(new JLabel("Nhận xét:"));
        txtNhanXet = new JTextArea(3, 20);
        panel.add(new JScrollPane(txtNhanXet), "growx, wrap");

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] cols = {"Mã HS", "Tên HS", "Lớp", "HK", "Năm học", "Xếp loại", "Nhận xét"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);

        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        return new JScrollPane(table);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new MigLayout("center", "[]15[]15[]15[]15[]", "[]"));

        panel.add(createButton("Thêm"));
        panel.add(createButton("Sửa"));
        panel.add(createButton("Xóa"));
        panel.add(createButton("Lưu"));
        panel.add(createButton("Làm mới"));

        return panel;
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setPreferredSize(new Dimension(110, 35));
        return btn;
    }
}