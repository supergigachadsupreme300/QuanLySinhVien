package QLHS;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class XepLoai extends JPanel {

    public XepLoai() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("XẾP LOẠI"));

        add(new JLabel("Mã học sinh:"));
        add(new JTextField(), "grow");

        add(new JLabel("Họ tên:"));
        add(new JTextField(), "grow");

        add(new JLabel("Xếp loại:"));
        JTextField txtXepLoai = new JTextField();
        txtXepLoai.setEditable(false);
        add(txtXepLoai, "grow");

        add(new JButton("Xem xếp loại"), "span, center");
    }
}

