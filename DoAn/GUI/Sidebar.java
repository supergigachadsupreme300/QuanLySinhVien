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
        add(createTabButton("Quản lý Phân công", e -> mainFrame.showForm(MainMenu.PHANCONG)), "growx, wrap");
        add(createTabButton("Quản lý Chi Tiết Tiết", e -> mainFrame.showForm(MainMenu.CHITIETTIET)), "growx, wrap");
        add(createTabButton("Quản lý Chi Tiết Môn", e -> mainFrame.showForm(MainMenu.CHITIETHMON)), "growx, wrap");
        add(createTabButton("Quản lý Điểm", e -> mainFrame.showForm(MainMenu.DIEM)), "growx, wrap");
        add(createTabButton("Quản lý Giáo viên", e -> mainFrame.showForm(MainMenu.GIAOVIEN)), "growx, wrap");
        add(createTabButton("Quản lý Hạnh kiểm", e -> mainFrame.showForm(MainMenu.HANHKIEM)), "growx, wrap");
        add(createTabButton("Quản lý Học kỳ", e -> mainFrame.showForm(MainMenu.HOCKY)), "growx, wrap");
        add(createTabButton("Quản lý Học sinh", e -> mainFrame.showForm(MainMenu.HOCSINH)), "growx, wrap");
        add(createTabButton("Quản lý Môn học", e -> mainFrame.showForm(MainMenu.MONHOC)), "growx, wrap");
        add(createTabButton("Quản lý Năm học", e -> mainFrame.showForm(MainMenu.NAMHOC)), "growx, wrap");
        add(createTabButton("Quản lý Phụ huynh", e -> mainFrame.showForm(MainMenu.PHUHUYNH)), "growx, wrap");
        add(createTabButton("Báo cáo", e -> mainFrame.showForm(MainMenu.REPORT)), "growx, wrap");
        add(createTabButton("Quản lý Vi phạm", e -> mainFrame.showForm(MainMenu.VIPHAM)), "growx, wrap");
        add(createTabButton("Quản lý Xếp loại", e -> mainFrame.showForm(MainMenu.XEPLOAI)), "growx, wrap");
    //    add(createTabButton("Quản lý Lớp Test", e -> mainFrame.showForm(MainMenu.TestLOP)), "growx, wrap");
    //    add(createTabButton("Quản lí Thời khóa biểu Test", e -> mainFrame.showForm(MainMenu.TestTKB)), "growx, wrap");
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
