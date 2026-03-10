package GUI;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.Connection;
import BusinessLogicLayer.HocSinhBLL;
import GUI.FormHocSinh;
import net.miginfocom.swing.MigLayout;
import DataObject.HocSinh;

public class student_GUI extends JPanel {
    private HocSinh hocSinh;
    private JTextField txtTen;
    private JTextField txtMaHS;
    private JTextField txtLop;
    private JTextField txtNgaySinh;
    private JTextField txtGioiTinh;
    private JTextField txtDiaChi;
    private Connection connection;

    
    public student_GUI() {
        this(new HocSinh(), null);
    }

    // Constructor nhận đối tượng HocSinh và Connection
    public student_GUI(HocSinh hs, Connection conn) {
        this.hocSinh = hs != null ? hs : new HocSinh();
        this.connection = conn;
        setLayout(new MigLayout("fill"));

        // Panel chính gồm: info + (không còn điểm/hạnh kiểm nhúng)
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10", "[grow]", "[fill]"));
        
        // Panel thông tin học sinh
        JPanel infoPanel = new JPanel(new MigLayout("wrap 2", "[90!][grow]", "[]10[]10[]10[]"));
        infoPanel.setBackground(Color.CYAN);
        infoPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HỌC SINH"));

        // Các trường thông tin
        infoPanel.add(new JLabel("Mã học sinh:"));
        txtMaHS = new JTextField(20);
        txtMaHS.setEditable(false);
        infoPanel.add(txtMaHS, "cell 1 1,growx, wrap");

        infoPanel.add(new JLabel("Tên:"));
        txtTen = new JTextField(20);
        txtTen.setEditable(false);
        infoPanel.add(txtTen, "cell 1 2,growx, wrap");

        infoPanel.add(new JLabel("Lớp:"));
        txtLop = new JTextField(20);
        txtLop.setEditable(false);
        infoPanel.add(txtLop, "cell 1 3,growx, wrap");

        infoPanel.add(new JLabel("Ngày sinh:"));
        txtNgaySinh = new JTextField(20);
        txtNgaySinh.setEditable(false);
        infoPanel.add(txtNgaySinh, "cell 1 4,growx, wrap");

        infoPanel.add(new JLabel("Giới tính:"));
        txtGioiTinh = new JTextField(20);
        txtGioiTinh.setEditable(false);
        infoPanel.add(txtGioiTinh, "cell 1 5,growx, wrap");

        infoPanel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(20);
        txtDiaChi.setEditable(false);
        infoPanel.add(txtDiaChi, "cell 1 6, growx, wrap");

        // Các nút chức năng chính
        JButton btnPhuHuynh = new JButton("Phụ huynh");
        JButton btnEdit = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        infoPanel.add(btnPhuHuynh, "split 3, center");
        infoPanel.add(btnEdit);
        infoPanel.add(btnXoa, "wrap");

        // Nút Phụ huynh
        btnPhuHuynh.addActionListener(e -> {
            if (hocSinh == null || hocSinh.getMaHS() == null || hocSinh.getMaHS().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có học sinh để xem phụ huynh.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                ((MainMenu) w).openPhuHuynhForStudent(hocSinh.getMaHS());
            } else {
                // fallback: open in new frame
                JFrame f = new JFrame("Phụ huynh của " + hocSinh.getMaHS());
                f.setSize(900, 600);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                FormPhuHuynh fp = new FormPhuHuynh(hocSinh.getMaHS());
                f.add(fp);
                f.setVisible(true);
            }
        });

        mainPanel.add(infoPanel, "grow");

        add(new JScrollPane(mainPanel), "grow");

        // action listeners using HocSinhBLL reference
        btnXoa.addActionListener(ev -> {
            if (hocSinh == null || hocSinh.getMaHS() == null || hocSinh.getMaHS().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có học sinh để xóa.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (hocSinhBLLRef == null) {
                JOptionPane.showMessageDialog(this, "Chưa kết nối xử lý dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int c = JOptionPane.showConfirmDialog(this, "Xóa học sinh mã " + hocSinh.getMaHS() + "?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) {
                if (hocSinhBLLRef.xoaHocSinh(hocSinh.getMaHS())) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    FormHocSinh owner = (FormHocSinh) SwingUtilities.getAncestorOfClass(FormHocSinh.class, this);
                    if (owner != null) owner.refreshTableAfterChange();
                    Window w = SwingUtilities.getWindowAncestor(this);
                    if (w instanceof MainMenu) {
                        ((MainMenu) w).refreshLop();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEdit.addActionListener(ev -> {
            boolean editable = !txtTen.isEditable();
            if (editable) {
                txtTen.setEditable(true); 
                txtLop.setEditable(true); 
                txtNgaySinh.setEditable(true); 
                txtGioiTinh.setEditable(true); 
                txtDiaChi.setEditable(true);
                btnEdit.setText("Lưu");
            } else {
                txtTen.setEditable(false); 
                txtLop.setEditable(false); 
                txtNgaySinh.setEditable(false); 
                txtGioiTinh.setEditable(false); 
                txtDiaChi.setEditable(false);
                btnEdit.setText("Sửa");

                if (hocSinhBLLRef == null) {
                    JOptionPane.showMessageDialog(this, "Chưa kết nối xử lý dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                HocSinh newHs = new HocSinh();
                newHs.setMaHS(txtMaHS.getText());
                newHs.setHoTen(txtTen.getText());
                newHs.setGioiTinh(txtGioiTinh.getText());
                newHs.setDiaChi(txtDiaChi.getText());
                newHs.setMaLop(txtLop.getText());

                if (!txtNgaySinh.getText().trim().isEmpty()) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate d = LocalDate.parse(txtNgaySinh.getText().trim(), formatter);
                        newHs.setNgaySinh(d);
                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ (dd/MM/yyyy)", "Lỗi", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (hocSinhBLLRef.suaHocSinh(newHs)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công.", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                    FormHocSinh owner = (FormHocSinh) SwingUtilities.getAncestorOfClass(FormHocSinh.class, this);
                    if (owner != null) owner.refreshTableAfterChange();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Cập nhật dữ liệu từ đối tượng
        updateDisplay();
    }

    // Phương thức cập nhật hiển thị từ đối tượng HocSinh
    public void updateDisplay() {
        if (hocSinh != null) {
            txtMaHS.setText(hocSinh.getMaHS());
            txtTen.setText(hocSinh.getHoTen());
            txtLop.setText(hocSinh.getMaLop());
            txtGioiTinh.setText(hocSinh.getGioiTinh());
            txtDiaChi.setText(hocSinh.getDiaChi());

            // Định dạng ngày sinh
            if (hocSinh.getNgaySinh() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtNgaySinh.setText(hocSinh.getNgaySinh().format(formatter));
            } else {
                txtNgaySinh.setText("");
            }
        }
    }

    // Phương thức cập nhật đối tượng HocSinh
    public void setHocSinh(HocSinh hs) {
        this.hocSinh = hs;
        updateDisplay();
    }

    // Phương thức lấy đối tượng HocSinh hiện tại
    public HocSinh getHocSinh() {
        return hocSinh;
    }

    // BLL reference to operate on data directly
    private HocSinhBLL hocSinhBLLRef;

    public void setHocSinhBLL(HocSinhBLL bll) {
        this.hocSinhBLLRef = bll;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Information");
            frame.setSize(500, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new student_GUI());
            frame.setVisible(true);
        });
    }

}
