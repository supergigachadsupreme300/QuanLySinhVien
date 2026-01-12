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

public class MainMenu extends JFrame {
    
    public MainMenu() {
        setTitle("Hệ thống Quản lý Học sinh");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        initUI();
    }
    
    private void initUI() {
        setLayout(new MigLayout("fill, insets 30", "[center]", "[]30[]15[]15[]15[]15[]15[]"));
        
        // ===== TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ HỌC SINH", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "wrap, growx");
        
        // ===== CÁC NÚT MENU =====
        add(createMenuButton("Quản lý Học sinh", e -> openFormHocSinh()), "growx, wrap");
        add(createMenuButton("Quản lý Giáo viên", e -> openFormGiaoVien()), "growx, wrap");
        add(createMenuButton("Quản lý Lớp học", e -> openFormLop()), "growx, wrap");
        add(createMenuButton("Quản lý Thời khóa biểu", e -> openFormTKB()), "growx, wrap");
        add(createMenuButton("Quản lý Điểm", e -> openFormDiem()), "growx, wrap");
        add(createMenuButton("Đăng xuất", e -> dangXuat()), "growx, wrap");
    }
    
    private JButton createMenuButton(String text, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(350, 50));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addActionListener(action);
        
        // Hover effect
        Color original = btn.getBackground();
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(200, 220, 255));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(original);
            }
        });
        
        return btn;
    }
    
    // ===== CÁC PHƯƠNG THỨC CHUYỂN FORM =====
    
    private void openFormHocSinh() {
        // TODO: new FormHocSinh().setVisible(true);
        JOptionPane.showMessageDialog(this, "Form Học sinh chưa hoàn thành!");
    }
    
    private void openFormGiaoVien() {
        // TODO: new FormGiaoVien().setVisible(true);
        JOptionPane.showMessageDialog(this, "Form Giáo viên chưa hoàn thành!");
    }
    
    private void openFormLop() {
        new FormLop().setVisible(true);
        this.dispose(); // Đóng MainMenu
    }
    
    private void openFormTKB() {
        new FormTKB().setVisible(true);
        this.dispose(); // Đóng MainMenu
    }
    
    private void openFormDiem() {
        // TODO: new FormDiem().setVisible(true);
        JOptionPane.showMessageDialog(this, "Form Điểm chưa hoàn thành!");
    }
    
    private void dangXuat() {
        int c = JOptionPane.showConfirmDialog(
            this, 
            "Bạn có chắc muốn đăng xuất?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        );
        if (c == JOptionPane.YES_OPTION) {
            // TODO: Quay về form đăng nhập
            // new FormDangNhap().setVisible(true);
            System.exit(0); // Tạm thời thoát hẳn
        }
    }
}
