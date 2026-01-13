import javax.swing.*; //javac -cp "lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI.java
import java.awt.*; //java -cp ".;lib\miglayout-core-11.4.2.jar;lib\miglayout-swing-11.4.2.jar" student_GUI
import net.miginfocom.swing.MigLayout;

public class student_GUI extends JPanel {
    public student_GUI() {
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
        panel.add(new JLabel("Tên:"));
        panel.add(new JTextField("Nguyễn Văn A"), "growx");

        panel.add(new JLabel("Lớp:"));
        panel.add(new JButton("12A1"), "growx");

        panel.add(new JLabel("Ngày sinh:"));
        panel.add(new JTextField("01/01/2005"), "growx");

        panel.add(new JLabel("Giới tính:"));
        panel.add(new JTextField("Nam"), "growx");

        panel.add(new JLabel("Địa chỉ:"));
        panel.add(new JTextField("123 Đường ABC, Quận XYZ, TP.HCM"), "growx");

        panel.add(new JLabel("Số điện thoại:"));
        panel.add(new JTextField("0123456789"), "growx");

        panel.add(new JLabel("Email:"));
        panel.add(new JTextField("nguyenvana@example.com"), "growx");

        panel.add(new JLabel("Họ và tên mẹ:"));
        panel.add(new JButton("Trần Thị B"), "growx");

        panel.add(new JLabel("Họ và tên bố:"));
        panel.add(new JButton("Lê Văn C"), "growx");

        panel.add(new JLabel("Xếp loại:"));
        panel.add(new JTextField("Giỏi"), "growx");

        panel.add(new JLabel("Hạnh kiểm:"));
        panel.add(new JTextField("Tốt"), "growx");

        // Các nút chức năng
        panel.add(new JButton("Xem điểm"), "split 2, center");
        panel.add(new JButton("Xem vi phạm"), "center");
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
