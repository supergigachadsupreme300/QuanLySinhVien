import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class MonHoc extends JPanel {

    public MonHoc() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("QUẢN LÝ MÔN HỌC"));

        add(new JLabel("Mã môn học:"));
        add(new JTextField(), "grow");

        add(new JLabel("Tên môn học:"));
        add(new JTextField(), "grow");

        add(new JLabel("Số tín chỉ:"));
        add(new JTextField(), "grow");

        add(new JLabel("Khoa :"));
        add(new JTextField(), "grow");

        add(new JButton("Thêm"), "span, split 3, center");
        add(new JButton("Sửa"));
        add(new JButton("Xóa"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Quản lý Môn học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new MonHoc());
            frame.setSize(550, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
