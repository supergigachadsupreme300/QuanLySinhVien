package QLHS;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class HanhKiem extends JPanel {

    public HanhKiem() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("HẠNH KIỂM"));

        add(new JLabel("Mã học sinh:"));
        add(new JTextField(), "grow");

        add(new JLabel("Họ tên:"));
        add(new JTextField(), "grow");

        add(new JLabel("Hạnh kiểm:"));
        JComboBox<String> cbHanhKiem = new JComboBox<>(
                new String[]{"Tốt", "Khá", "Trung bình", "Yếu"}
        );
        add(cbHanhKiem, "grow");

        add(new JButton("Lưu"), "span, center");
    }
}

