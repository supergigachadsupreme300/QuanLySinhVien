package GUI;

import BusinessLogicLayer.PhuHuynhHocSinhBLL;
import BusinessLogicLayer.HocSinhBLL; // để kiểm tra tồn tại HS
import DataObject.PhuHuynhHocSinh;
import DataObject.HocSinh;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class QuanLyHocSinhDialog extends JDialog {
    private String maPH;
    private PhuHuynhHocSinhBLL relationBLL;
    private HocSinhBLL hocSinhBLL;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaHS, txtQuanHe;

    public QuanLyHocSinhDialog(JFrame parent, String maPH) {
        super(parent, "Quản lý học sinh của phụ huynh", true);
        this.maPH = maPH;
        this.relationBLL = new PhuHuynhHocSinhBLL();
        this.hocSinhBLL = new HocSinhBLL();
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new MigLayout("fill", "[grow]", "[]10[grow]10[]"));
        setSize(600, 400);
        setLocationRelativeTo(getParent());

        // Panel nhập liệu
        JPanel pnlInput = new JPanel(new FlowLayout());
        pnlInput.add(new JLabel("Mã HS:"));
        txtMaHS = new JTextField(10);
        pnlInput.add(txtMaHS);
        pnlInput.add(new JLabel("Quan hệ:"));
        txtQuanHe = new JTextField(10);
        pnlInput.add(txtQuanHe);
        JButton btnThem = new JButton("Thêm");
        pnlInput.add(btnThem);
        add(pnlInput, "growx, wrap");

        // Bảng hiển thị danh sách học sinh (có cột quan hệ)
        model = new DefaultTableModel(new String[]{"Mã HS", "Họ tên", "Lớp", "Quan hệ"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        add(new JScrollPane(table), "grow, wrap");

        // Panel nút xóa
        JPanel pnlSouth = new JPanel();
        JButton btnXoa = new JButton("Xóa học sinh được chọn");
        pnlSouth.add(btnXoa);
        add(pnlSouth, "growx");

        // Sự kiện
        btnThem.addActionListener(this::themQuanHe);
        btnXoa.addActionListener(this::xoaQuanHe);
    }

    private void loadData() {
        model.setRowCount(0);
        List<PhuHuynhHocSinh> list = relationBLL.layTheoPH(maPH);
        for (PhuHuynhHocSinh relation : list) {
            // Lấy thông tin học sinh từ bảng HOCSINH
            HocSinh hs = hocSinhBLL.getByMa(relation.getMaHS());
            if (hs != null) {
                model.addRow(new Object[]{
                    hs.getMaHS(),
                    hs.getHoTen(),
                    hs.getMaLop(),
                    relation.getQuanHe()
                });
            } else {
                // Trường hợp dữ liệu lỗi, vẫn hiển thị mã HS
                model.addRow(new Object[]{
                    relation.getMaHS(),
                    "<Không tìm thấy>",
                    "",
                    relation.getQuanHe()
                });
            }
        }
    }

    private void themQuanHe(ActionEvent e) {
        String maHS = txtMaHS.getText().trim();
        String quanHe = txtQuanHe.getText().trim();
        if (maHS.isEmpty() || quanHe.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã HS và quan hệ.");
            return;
        }
        // Kiểm tra học sinh tồn tại
        HocSinh hs = hocSinhBLL.getByMa(maHS);
        if (hs == null) {
            JOptionPane.showMessageDialog(this, "Mã học sinh không tồn tại.");
            return;
        }
        PhuHuynhHocSinh relation = new PhuHuynhHocSinh(maHS, maPH, quanHe, 1);
        if (relationBLL.themQuanHe(relation)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công.");
            loadData();
            txtMaHS.setText("");
            txtQuanHe.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại. Có thể quan hệ đã tồn tại.");
        }
    }

    private void xoaQuanHe(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn một học sinh để xóa.");
            return;
        }
        String maHS = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa quan hệ với học sinh " + maHS + "?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (relationBLL.xoaQuanHe(maHS, maPH)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại.");
            }
        }
    }
}