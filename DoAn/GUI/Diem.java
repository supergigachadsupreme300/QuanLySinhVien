package QLHS;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class Diem extends JPanel {

    public Diem() {
        setLayout(new MigLayout("wrap 2", "[right][grow]", "[]10[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("QUẢN LÝ ĐIỂM"));

        add(new JLabel("Mã học sinh:"));
        add(new JTextField(), "grow");

        add(new JLabel("Họ tên:"));
        add(new JTextField(), "grow");

        add(new JLabel("Điểm miệng:"));
        add(new JTextField(), "grow");

        add(new JLabel("Điểm 15 phút:"));
        add(new JTextField(), "grow");

        add(new JLabel("Điểm giữa kỳ:"));
        add(new JTextField(), "grow");

        add(new JLabel("Điểm cuối kỳ:"));
        add(new JTextField(), "grow");

        add(new JButton("Thêm"), "span, split 3, center");
        add(new JButton("Sửa"));
        add(new JButton("Xóa"));
    }
}

