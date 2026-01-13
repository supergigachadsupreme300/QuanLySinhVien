
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class TietHoc extends JPanel {

    public TietHoc() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]10[]10[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("QUẢN LÝ CHI TIẾT TIẾT HỌC"));

        add(new JLabel("Mã tiết học:"));
        add(new JTextField(), "grow");

        add(new JLabel("Mã lớp:"));
        add(new JTextField(), "grow");

        add(new JLabel("Mã môn:"));
        add(new JTextField(), "grow");

        add(new JLabel("Ngày học:"));
        add(new JTextField(), "grow");

        add(new JLabel("Giờ bắt đầu:"));
        add(new JTextField(), "grow");

        add(new JLabel("Giờ kết thúc:"));
        add(new JTextField(), "grow");

        add(new JLabel("Phòng học:"));
        add(new JTextField(), "grow");

        add(new JButton("Thêm"), "span, split 3, center");
        add(new JButton("Sửa"));
        add(new JButton("Xóa"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Quản lý Chi tiết Tiết học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new TietHoc());
            frame.setSize(550, 450);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
