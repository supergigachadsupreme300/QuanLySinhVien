import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class GiaoVien extends JPanel {

    public GiaoVien() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]10[]10[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("QUẢN LÝ GIÁO VIÊN"));

        add(new JLabel("Mã giáo viên:"));
        add(new JTextField(), "grow");

        add(new JLabel("Họ tên:"));
        add(new JTextField(), "grow");


        add(new JLabel("Số điện thoại:"));
        add(new JTextField(), "grow");

        add(new JLabel("Email:"));
        add(new JTextField(), "grow");

        // Nút chức năng
        add(new JButton("Thêm"), "span, split 3, center");
        add(new JButton("Sửa"));
        add(new JButton("Xóa"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Quản lý Giáo viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new GiaoVien());
            frame.setSize(550, 480);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
