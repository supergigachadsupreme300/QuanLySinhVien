/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author admin
 */
import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class Sidebar extends JPanel {

    private MainMenu mainFrame;

    public Sidebar(MainMenu frame){
        this.mainFrame = frame;
        initUI();
    }
    
    private void initUI(){
        setPreferredSize(new Dimension(220, 0));
        setBackground(new Color(45, 62, 80));
        setLayout(new MigLayout("fillx, insets 15", "[grow]", "[]20[]10[]10[]"));

        JLabel lblTitle = new JLabel("CÁC MỤC QUẢN LÝ");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblTitle, "wrap");
        
        add(createTabButton("Quản lý Lớp", e -> mainFrame.showForm(MainMenu.LOP)), "growx, wrap");
        add(createTabButton("Quản lí Thời khóa biểu", e -> mainFrame.showForm(MainMenu.TKB)), "growx, wrap");
        add(createTabButton("Quản lý Lớp Test", e -> mainFrame.showForm(MainMenu.TestLOP)), "growx, wrap");
        add(createTabButton("Quản lí Thời khóa biểu Test", e -> mainFrame.showForm(MainMenu.TestTKB)), "growx, wrap");
        add(createTabButton("Thoát", e -> System.exit(0)), "growx");
        
    }
    
    private JButton createTabButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(52, 73, 94));
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 40));
        btn.addActionListener(action);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(70, 130, 180));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(52, 73, 94));
            }
        });

        return btn;
    }
    
}
