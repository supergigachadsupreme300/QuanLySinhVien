import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class parent_GUI extends JFrame {
    public parent_GUI() {
        setTitle("Parent Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel với MigLayout, nền Cyan giống student_GUI
        JPanel panel = new JPanel(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]"));
        panel.setBackground(Color.CYAN);
        add(panel);

        // Các trường thông tin
        panel.add(new JLabel("Tên phụ huynh:"));
        panel.add(new JTextField("Trần Văn Bố"), "growx");

        panel.add(new JLabel("Số điện thoại:"));
        panel.add(new JTextField("0987654321"), "growx");

        panel.add(new JLabel("Nghề nghiệp:"));
        panel.add(new JTextField("Kỹ sư"), "growx");

        panel.add(new JLabel("Quan hệ với học sinh:"));
        panel.add(new JTextField("Bố"), "growx");

        // Nút "Học sinh" ở dưới cùng, chiếm cả 2 cột và căn giữa
        panel.add(new JButton("Học sinh"), "span, center");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            parent_GUI frame = new parent_GUI();
            frame.setVisible(true);
        });
    }
}