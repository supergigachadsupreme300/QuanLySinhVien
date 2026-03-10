package GUI;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import DataObject.Parent;

public class parent_GUI extends JPanel {
    private Parent parent;
    private JTextField txtMaPhH;
    private JTextField txtTenPhH;
    private JTextField txtSdt;
    private JTextField txtNgheNghiep;
    private JButton btnHocSinh;
    private JButton btnEdit;
    private JButton btnXoa;
    private JButton btnQuanLyHS;
    private BusinessLogicLayer.ParentBLL parentBLLRef;
    private boolean isEditing = false;

    public parent_GUI() {
        this(new Parent());
    }

    public parent_GUI(Parent p) {
        this.parent = p != null ? p : new Parent();
        setLayout(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]"));
        setBackground(Color.CYAN);
        setPreferredSize(new Dimension(360, 180)); 

        add(new JLabel("Mã phụ huynh:"));
        txtMaPhH = new JTextField(20);
        txtMaPhH.setEditable(false);
        add(txtMaPhH, "growx");

        add(new JLabel("Tên phụ huynh:"));
        txtTenPhH = new JTextField(20);
        txtTenPhH.setEditable(false);
        add(txtTenPhH, "growx");

        add(new JLabel("Số điện thoại:"));
        txtSdt = new JTextField(20);
        txtSdt.setEditable(false);
        add(txtSdt, "growx");

        add(new JLabel("Nghề nghiệp:"));
        txtNgheNghiep = new JTextField(20);
        txtNgheNghiep.setEditable(false);
        add(txtNgheNghiep, "growx");

        btnHocSinh = new JButton("Học sinh");
        btnEdit = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnQuanLyHS = new JButton("Quản lý HS");

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        btnRow.add(btnEdit);
        btnRow.add(btnXoa);
        btnRow.add(btnHocSinh);
        btnRow.add(btnQuanLyHS);
        add(btnRow, "span, center");

        btnHocSinh.addActionListener(e -> {
            if (parent == null || parent.getMaPhH() == null || parent.getMaPhH().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phụ huynh.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                ((MainMenu) w).openHocSinhForParent(parent.getMaPhH());
            } else {
                JFrame f = new JFrame("Học sinh của phụ huynh " + parent.getMaPhH());
                f.setSize(900, 600);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                FormHocSinh formHS = new FormHocSinh();
                formHS.loadStudentsByParent(parent.getMaPhH());
                f.add(formHS);
                f.setVisible(true);
            }
        });

        btnQuanLyHS.addActionListener(e -> {
            if (parent == null || parent.getMaPhH() == null || parent.getMaPhH().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phụ huynh.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            openQuanLyHocSinhDialog(parent.getMaPhH());
        });

        btnEdit.addActionListener(e -> {
            if (parent == null || parent.getMaPhH() == null || parent.getMaPhH().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phụ huynh.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (parentBLLRef == null) {
                JOptionPane.showMessageDialog(this, "Không có BLL.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!isEditing) {
                txtTenPhH.setEditable(true);
                txtSdt.setEditable(true);
                txtNgheNghiep.setEditable(true);
                btnEdit.setText("Lưu");
                isEditing = true;
            } else {
                parent.setTenPhH(txtTenPhH.getText());
                parent.setSdt(txtSdt.getText());
                parent.setNgheNghiep(txtNgheNghiep.getText());
                if (parentBLLRef.suaParent(parent)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    FormPhuHuynh owner = (FormPhuHuynh) SwingUtilities.getAncestorOfClass(FormPhuHuynh.class, this);
                    if (owner != null) owner.loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
                txtTenPhH.setEditable(false);
                txtSdt.setEditable(false);
                txtNgheNghiep.setEditable(false);
                btnEdit.setText("Sửa");
                isEditing = false;
            }
        });

        btnXoa.addActionListener(e -> {
            if (parent == null || parent.getMaPhH() == null || parent.getMaPhH().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chưa chọn phụ huynh để xóa.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (parentBLLRef == null) {
                JOptionPane.showMessageDialog(this, "Không có BLL.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa phụ huynh mã " + parent.getMaPhH() + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (parentBLLRef.xoaParent(parent.getMaPhH())) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    FormPhuHuynh owner = (FormPhuHuynh) SwingUtilities.getAncestorOfClass(FormPhuHuynh.class, this);
                    if (owner != null) owner.loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateDisplay();
    }

    public void updateDisplay() {
        if (parent != null) {
            txtMaPhH.setText(parent.getMaPhH());
            txtTenPhH.setText(parent.getTenPhH());
            txtSdt.setText(parent.getSdt());
            txtNgheNghiep.setText(parent.getNgheNghiep());
        }
    }

    public void setParent(Parent p) {
        this.parent = p;
        if (isEditing) {
            txtTenPhH.setEditable(false);
            txtSdt.setEditable(false);
            txtNgheNghiep.setEditable(false);
            btnEdit.setText("Sửa");
            isEditing = false;
        }
        updateDisplay();
    }

    public void setParentBLL(BusinessLogicLayer.ParentBLL bll) {
        this.parentBLLRef = bll;
    }

    public Parent getParentEntity() {
        return parent;
    }

    private void openQuanLyHocSinhDialog(String maPH) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        QuanLyHocSinhDialog dlg = new QuanLyHocSinhDialog(frame, maPH);
        dlg.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Parent Information");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new parent_GUI());
            frame.setVisible(true);
        });
    }
}
