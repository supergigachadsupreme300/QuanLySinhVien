import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class parent_GUI extends JPanel {
    public parent_GUI() {
        setLayout(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]"));
        setBackground(Color.CYAN);

        add(new JLabel("Tên phụ huynh:"));
        add(new JTextField("Trần Văn Bố"), "growx");

        add(new JLabel("Số điện thoại:"));
        add(new JTextField("0987654321"), "growx");

        add(new JLabel("Nghề nghiệp:"));
        add(new JTextField("Kỹ sư"), "growx");

        add(new JLabel("Quan hệ với học sinh:"));
        add(new JTextField("Bố"), "growx");

        add(new JButton("Học sinh"), "span, center");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Parent Information");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new parent_GUI());
        frame.setVisible(true);
    }
}
