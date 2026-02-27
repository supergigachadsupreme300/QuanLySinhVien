package GUI;
import javax.swing.*; //javac -cp "lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI.java
import java.awt.*; //java -cp ".;lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import BusinessLogicLayer.HocSinhBLL;
import GUI.FormHocSinh;
import net.miginfocom.swing.MigLayout;
import DataObject.HocSinh;
import GUI.Diem;
import GUI.HanhKiem;

public class student_GUI extends JPanel {
    private HocSinh hocSinh;
    private JTextField txtTen;
    private JTextField txtMaHS;
    private JTextField txtLop;
    private JTextField txtNgaySinh;
    private JTextField txtGioiTinh;
    private JTextField txtDiaChi;

    // Constructor mặc định (tạo đối tượng rỗng để test)
    public student_GUI() {
        this(new HocSinh());
    }

    // Constructor nhận đối tượng HocSinh
    public student_GUI(HocSinh hs) {
        this.hocSinh = hs != null ? hs : new HocSinh();
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]10[]10[]"));
        panel.setBackground(Color.CYAN);
        panel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HỌC SINH"));
        add(panel, BorderLayout.CENTER);

        // Ảnh (tải nếu tồn tại, tránh NullPointerException)
        java.net.URL imgUrl = getClass().getResource("OIP.jpg");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(img));
            panel.add(label, "span, center");
        } else {
            // nếu không có ảnh, dùng khoảng trống hoặc nhãn thông báo
            panel.add(new JLabel("[No Image]"), "span, center");
        }

        // Các trường thông tin
        panel.add(new JLabel("Mã học sinh:"));
        txtMaHS = new JTextField(20);
        txtMaHS.setEditable(false);
        panel.add(txtMaHS, "growx");

        panel.add(new JLabel("Tên:"));
        txtTen = new JTextField(20);
        txtTen.setEditable(false);
        panel.add(txtTen, "growx");

        panel.add(new JLabel("Lớp:"));
        txtLop = new JTextField(20);
        txtLop.setEditable(false);
        panel.add(txtLop, "growx");

        panel.add(new JLabel("Ngày sinh:"));
        txtNgaySinh = new JTextField(20);
        txtNgaySinh.setEditable(false);
        panel.add(txtNgaySinh, "growx");

        panel.add(new JLabel("Giới tính:"));
        txtGioiTinh = new JTextField(20);
        txtGioiTinh.setEditable(false);
        panel.add(txtGioiTinh, "growx");

        panel.add(new JLabel("Địa chỉ:"));
        txtDiaChi = new JTextField(20);
        txtDiaChi.setEditable(false);
        panel.add(txtDiaChi, "growx, wrap");

        // bố mẹ buttons row (above others)
        JButton btnBo = new JButton("Bố");
        JButton btnMe = new JButton("Mẹ");
        panel.add(btnBo, "span, split 2, center");
        panel.add(btnMe, "wrap");

        // Các nút chức năng chính
        JButton btnXemDiem = new JButton("Xem điểm");
        JButton btnHanhKiem = new JButton("Hạnh kiểm");
        panel.add(btnXemDiem, "span, split 2, center");
        panel.add(btnHanhKiem, "wrap");

        // action listeners to open corresponding panels
        btnXemDiem.addActionListener(e -> {
            JFrame f = new JFrame("Điểm");
            f.setSize(500, 400);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.add(new Diem());
            f.setVisible(true);
        });

        btnHanhKiem.addActionListener(e -> {
            JFrame f = new JFrame("Hạnh kiểm");
            f.setSize(400, 300);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.add(new HanhKiem());
            f.setVisible(true);
        });

        // parent buttons behavior
        btnBo.addActionListener(e -> {
            JFrame f = new JFrame("Thông tin phụ huynh - Bố");
            f.setSize(400, 400);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.add(new parent_GUI());
            f.setVisible(true);
        });
        btnMe.addActionListener(e -> {
            JFrame f = new JFrame("Thông tin phụ huynh - Mẹ");
            f.setSize(400, 400);
            f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f.setLocationRelativeTo(null);
            f.add(new parent_GUI());
            f.setVisible(true);
        });

        // Buttons: center-aligned Edit and Delete
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnXoa = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        btnRow.add(btnEdit);
        btnRow.add(btnXoa);
        // ensure the button row takes full width and centers its contents
        panel.add(btnRow, "span, growx, align center, wrap");

        // set preferred size smaller when embedded
        setPreferredSize(new Dimension(360, 300));

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
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEdit.addActionListener(ev -> {
            boolean editable = !txtTen.isEditable();
            if (editable) {
                txtTen.setEditable(true); txtLop.setEditable(true); txtNgaySinh.setEditable(true); txtGioiTinh.setEditable(true); txtDiaChi.setEditable(true);
                btnEdit.setText("Lưu");
            } else {
                txtTen.setEditable(false); txtLop.setEditable(false); txtNgaySinh.setEditable(false); txtGioiTinh.setEditable(false); txtDiaChi.setEditable(false);
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

    // removed external listener - use BLL reference via setHocSinhBLL()

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
            frame.setSize(400, 550);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new student_GUI());
            frame.setVisible(true);
        });
    }
}
