package GUI;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;

public class HocKy extends JPanel {

    public HocKy() {
        setLayout(new MigLayout( "wrap 2","[right][grow]","[]10[]10[]10[]10[]10[]"));

        setBorder(BorderFactory.createTitledBorder("QUẢN LÝ HỌC KỲ"));

        add(new JLabel("Mã học kỳ:"));
        add(new JTextField(), "grow");

        add(new JLabel("Tên học kỳ:"));
        add(new JTextField(), "grow");

        add(new JLabel("Mã năm học:"));
        add(new JTextField(), "grow");

        add(new JLabel("Ngày bắt đầu:"));
        add(new JTextField(), "grow");

        add(new JLabel("Ngày kết thúc:"));
        add(new JTextField(), "grow");

        add(new JButton("Thêm"), "span, split 3, center");
        add(new JButton("Sửa"));
        add(new JButton("Xóa"));
    }

    
