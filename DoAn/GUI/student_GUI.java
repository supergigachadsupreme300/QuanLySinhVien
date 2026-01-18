package GUI;
import javax.swing.*; //javac -cp "lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI.java
import java.awt.*; //java -cp ".;lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI
import java.time.format.DateTimeFormatter;
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

    // Constructor mặc định (tạo đối tượng rỗng để test)
    public student_GUI() {
        this(new HocSinh());
    }

    // Constructor nhận đối tượng HocSinh
    public student_GUI(HocSinh hs) {
        this.hocSinh = hs != null ? hs : new HocSinh();
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]"));
        panel.setBackground(Color.CYAN);
        add(panel, BorderLayout.CENTER);

        // Ảnh
        ImageIcon icon = new ImageIcon(getClass().getResource("OIP.jpg"));
        Image img = icon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
        JLabel label = new JLabel(new ImageIcon(img));
        panel.add(label, "span, center");

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
        panel.add(txtDiaChi, "growx");

        // Các nút chức năng
        panel.add(new JButton("Xem điểm"), "split 2, center");
        panel.add(new JButton("Xem vi phạm"), "center");

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
