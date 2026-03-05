package GUI;
import javax.swing.*; //javac -cp "lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI.java
import java.awt.*; //java -cp ".;lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.sql.Connection;
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
    private Diem diemPanel;
    private HanhKiem hanhKiemPanel;
    private Connection connection;

    // Constructor mặc định (tạo đối tượng rỗng để test)
    public student_GUI() {
        this(new HocSinh(), null);
    }

    // Constructor nhận đối tượng HocSinh và Connection
    public student_GUI(HocSinh hs, Connection conn) {
        this.hocSinh = hs != null ? hs : new HocSinh();
        this.connection = conn;
        setLayout(new BorderLayout());

        // Panel chính gồm: info + điểm/hạnh kiểm
        JPanel mainPanel = new JPanel(new MigLayout("fill, insets 10", "[grow]", "[fill][]"));
        
        // Panel thông tin học sinh
        JPanel infoPanel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]10[]10[]"));
        infoPanel.setBackground(Color.CYAN);
        infoPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HỌC SINH"));

        // Ảnh (tải nếu tồn tại, tránh NullPointerException)
        java.net.URL imgUrl = getClass().getResource("OIP.jpg");
        if (imgUrl != null) {
            ImageIcon icon = new ImageIcon(imgUrl);
            Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(img));
            infoPanel.add(label, "span, center");
        } else {
            infoPanel.add(new JLabel("[No Image]"), "span, center");
        }

        // Các trường thông tin (tạo bằng helper để giảm trùng lặp)
        txtMaHS = createReadOnlyField(infoPanel, "Mã học sinh:");
        txtTen  = createReadOnlyField(infoPanel, "Tên:");
        txtLop  = createReadOnlyField(infoPanel, "Lớp:");
        txtNgaySinh = createReadOnlyField(infoPanel, "Ngày sinh:");
        txtGioiTinh = createReadOnlyField(infoPanel, "Giới tính:");
        txtDiaChi = createReadOnlyField(infoPanel, "Địa chỉ:");

        // Các nút chức năng chính
        JButton btnXemDiem = new JButton("Xem điểm");
        JButton btnHanhKiem = new JButton("Hạnh kiểm");
        JButton btnPhuHuynh = new JButton("Phụ huynh");
        infoPanel.add(btnXemDiem, "split 3, center");
        infoPanel.add(btnHanhKiem);
        infoPanel.add(btnPhuHuynh, "wrap");

        // action listeners to show/hide panels
        btnXemDiem.addActionListener(e -> {
            if (diemPanel != null) {
                diemPanel.loadDiem(hocSinh.getMaHS());
                diemPanel.setVisible(!diemPanel.isVisible());
            }
        });

        btnHanhKiem.addActionListener(e -> {
            if (hanhKiemPanel != null) {
                hanhKiemPanel.loadHanhKiem(hocSinh.getMaHS());
                hanhKiemPanel.setVisible(!hanhKiemPanel.isVisible());
            }
        });

        // parent button behavior: mở FormPhuHuynh lọc theo học sinh
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

        // Buttons: center-aligned Edit and Delete
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton btnXoa = new JButton("Xóa");
        JButton btnEdit = new JButton("Sửa");
        btnRow.add(btnEdit);
        btnRow.add(btnXoa);
        infoPanel.add(btnRow, "span, growx, align center, wrap");

        mainPanel.add(infoPanel, "grow, wrap");
        
        // Panel cho Điểm và Hạnh kiểm
        if (connection != null) {
            diemPanel = new Diem(connection);
            diemPanel.setVisible(false);
            mainPanel.add(diemPanel, "growx, wrap");
            
            hanhKiemPanel = new HanhKiem(connection);
            hanhKiemPanel.setVisible(false);
            mainPanel.add(hanhKiemPanel, "growx");
        }
        
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);

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

    // Phương thức cập nhật đối tượng HocSinh
    public void setHocSinh(HocSinh hs) {
        this.hocSinh = hs;
        updateDisplay();
    }

    // --------------------------------
    // Utility helpers used within this panel
    // --------------------------------

    /**
     * Create a labeled, non-editable text field and add it to the given panel.
     */
    private JTextField createReadOnlyField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField fld = new JTextField(20);
        fld.setEditable(false);
        panel.add(fld, "growx");
        return fld;
    }

    /**
     * Open a simple parent info frame with given title.
     */
    private void openParentWindow(String title) {
        JFrame f = new JFrame(title);
        f.setSize(400, 400);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.add(new parent_GUI());
        f.setVisible(true);
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
