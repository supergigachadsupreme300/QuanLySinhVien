package GUI;
import javax.swing.*; //javac -cp "lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI.java
import java.awt.*; //java -cp ".;lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI
import java.time.format.DateTimeFormatter;
import net.miginfocom.swing.MigLayout;
import DataObject.HocSinh;
import GUI.Diem;
import QLHS.HanhKiem;

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
            java.awt.Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                ((MainMenu) w).showForm(MainMenu.DIEM);
            } else {
                JFrame f = new JFrame("Điểm");
                f.setSize(500, 400);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                f.add(new Diem());
                f.setVisible(true);
            }
        });

        btnHanhKiem.addActionListener(e -> {
            java.awt.Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                ((MainMenu) w).showForm(MainMenu.HANHKIEM);
            } else {
                JFrame f = new JFrame("Hạnh kiểm");
                f.setSize(400, 300);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                f.add(new HanhKiem());
                f.setVisible(true);
            }
        });

        // parent buttons behavior
        btnBo.addActionListener(e -> {
            java.awt.Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                ((MainMenu) w).showForm(MainMenu.PARENT);
            } else {
                JFrame f = new JFrame("Thông tin phụ huynh - Bố");
                f.setSize(400, 400);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                f.add(new parent_GUI());
                f.setVisible(true);
            }
        });
        btnMe.addActionListener(e -> {
            java.awt.Window w = SwingUtilities.getWindowAncestor(this);
            if (w instanceof MainMenu) {
                ((MainMenu) w).showForm(MainMenu.PARENT);
            } else {
                JFrame f = new JFrame("Thông tin phụ huynh - Mẹ");
                f.setSize(400, 400);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationRelativeTo(null);
                f.add(new parent_GUI());
                f.setVisible(true);
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
