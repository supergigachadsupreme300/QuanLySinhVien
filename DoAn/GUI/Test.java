package GUI;

import javax.swing.*;

public class Test {
    public static void main(String[] args) {
        // Tạo cửa sổ chính
        JFrame frame = new JFrame("Quản lý chi tiết môn học");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Thêm panel FromChiTietMon vào frame
        frame.add(new FromChiTietMon());

        // Thiết lập kích thước và hiển thị
        frame.pack();                 // tự động căn chỉnh theo nội dung
        frame.setLocationRelativeTo(null); // hiển thị ở giữa màn hình
        frame.setVisible(true);       // bật cửa sổ lên
    }
}