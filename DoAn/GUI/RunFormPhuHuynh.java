package GUI;

import javax.swing.*;

public class RunFormPhuHuynh {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Form Phu Huynh");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);
            frame.add(new FormPhuHuynh());
            frame.setVisible(true);
        });
    }
}
