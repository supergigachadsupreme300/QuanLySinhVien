package GUI;

import javax.swing.*;

public class RunFormHocSinh {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Form Hoc Sinh");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 800);
            frame.setLocationRelativeTo(null);
            frame.add(new FormHocSinh());
            frame.setVisible(true);
        });
    }
}
