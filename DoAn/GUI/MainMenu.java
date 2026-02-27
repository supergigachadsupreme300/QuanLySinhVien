package GUI;

import javax.swing.*;
import java.awt.*;
import GUI.FormLop;
import GUI.Sidebar;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenu extends JFrame {

    public static final String LOP  = "LOP";
    public static final String TKB = "TKB";
    public static final String PHANCONG = "PHANCONG";
    public static final String CHITIETTIET = "CHITIETTIET";
   // public static final String TestTKB = "TestTKB";
   // public static final String TestLOP = "TestLOP";
    private CardLayout cardLayout;
    private JPanel mainPanel;
    

    public MainMenu() {
        setTitle("Hệ thống Quản lý Học sinh");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int c = JOptionPane.showConfirmDialog(
                        MainMenu.this,
                        "Bạn có chắc muốn thoát chương trình?",
                        "Xác nhận thoát",
                        JOptionPane.YES_NO_OPTION
                );

                if (c == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });


        setLayout(new BorderLayout());
        Sidebar sidebar = new Sidebar(this);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new FormLop(this), LOP);
        mainPanel.add(new FormTKB(this), TKB);
        mainPanel.add(new FormPhanCong(this), PHANCONG);
        mainPanel.add(new FormChiTietTiet(this), CHITIETTIET);
    //    mainPanel.add(new TestFormTKB(this), TestTKB);
    //    mainPanel.add(new TestFormLop(this), TestLOP);
        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        showForm(LOP);
    }

    
    public void showForm(String name){
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
