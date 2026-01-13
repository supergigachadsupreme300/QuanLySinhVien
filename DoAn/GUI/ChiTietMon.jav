import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class ChiTietMon extends JPanel {

    public ChiTietMon() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("QUẢN LÝ CHI TIẾT MÔN HỌC"));

        add(new JLabel("Mã chi tiết:"));
        add(new JTextField(), "grow");

        add(new JLabel("Mã môn:"));
        add(new JTextField(), "grow");

        add(new JLabel("Tên chi tiết:"));
        add(new JTextField(), "grow");

        add(new JLabel("Hệ số:"));
        add(new JTextField(), "grow");

        add(new JButton("Thêm"), "span, split 3, center");
        add(new JButton("Sửa"));
        add(new JButton("Xóa"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Quản lý Chi tiết Môn học");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ChiTietMon());
            frame.setSize(550, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
