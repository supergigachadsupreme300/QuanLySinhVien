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

public class Sidebar extends JScrollPane {

    private MainMenu mainFrame;
    private JPanel contentPanel;

    public Sidebar(MainMenu frame){
        this.mainFrame = frame;
        initUI();
    }
    
    private void initUI(){
        // Tạo panel chứa nội dung
        contentPanel = new JPanel();
        contentPanel.setBackground(new Color(45, 62, 80));
        contentPanel.setLayout(new MigLayout("fillx, insets 15", "[grow]", "[]20[]10[]10[]"));

        JLabel lblTitle = new JLabel("CÁC MỤC QUẢN LÝ");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(lblTitle, "wrap");
        
        contentPanel.add(createTabButton("Quản lý Lớp", e -> mainFrame.showForm(MainMenu.LOP)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lí Thời khóa biểu", e -> mainFrame.showForm(MainMenu.TKB)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Phân công", e -> mainFrame.showForm(MainMenu.PHANCONG)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Chi Tiết Tiết", e -> mainFrame.showForm(MainMenu.CHITIETTIET)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Chi Tiết Môn", e -> mainFrame.showForm(MainMenu.CHITIETHMON)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Điểm", e -> mainFrame.showForm(MainMenu.DIEM)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Giáo viên", e -> mainFrame.showForm(MainMenu.GIAOVIEN)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Hạnh kiểm", e -> mainFrame.showForm(MainMenu.HANHKIEM)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Học kỳ", e -> mainFrame.showForm(MainMenu.HOCKY)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Học sinh", e -> mainFrame.showForm(MainMenu.HOCSINH)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Môn học", e -> mainFrame.showForm(MainMenu.MONHOC)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Năm học", e -> mainFrame.showForm(MainMenu.NAMHOC)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Phụ huynh", e -> mainFrame.showForm(MainMenu.PHUHUYNH)), "growx, wrap");
        contentPanel.add(createTabButton("Báo cáo", e -> mainFrame.showForm(MainMenu.REPORT)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Vi phạm", e -> mainFrame.showForm(MainMenu.VIPHAM)), "growx, wrap");
        contentPanel.add(createTabButton("Quản lý Xếp loại", e -> mainFrame.showForm(MainMenu.XEPLOAI)), "growx, wrap");
        contentPanel.add(createTabButton("Import/Export Excel", e -> mainFrame.showForm(MainMenu.EXCEL)), "growx, wrap");
    //    contentPanel.add(createTabButton("Quản lý Lớp Test", e -> mainFrame.showForm(MainMenu.TestLOP)), "growx, wrap");
    //    contentPanel.add(createTabButton("Quản lí Thời khóa biểu Test", e -> mainFrame.showForm(MainMenu.TestTKB)), "growx, wrap");
        contentPanel.add(createTabButton("Thoát", e -> System.exit(0)), "growx");
        
        // Cấu hình JScrollPane
        setViewportView(contentPanel);
        setPreferredSize(new Dimension(220, 0));
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        getVerticalScrollBar().setUnitIncrement(10);
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

