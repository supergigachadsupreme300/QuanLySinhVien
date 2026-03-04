package GUI;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MainMenu extends JFrame {

    public static final String LOP  = "LOP";
    public static final String TKB = "TKB";
    public static final String PHANCONG = "PHANCONG";
    public static final String CHITIETTIET = "CHITIETTIET";
    public static final String CHITIETHMON = "CHITIETHMON";
    public static final String DIEM = "DIEM";
    public static final String GIAOVIER = "GIAOVIER";
    public static final String HANHKIEM = "HANHKIEM";
    public static final String HOCKY = "HOCKY";
    public static final String HOCSINH = "HOCSINH";
    public static final String MONHOC = "MONHOC";
    public static final String NAMHOC = "NAMHOC";
    public static final String PHUHUYNH = "PHUHUYNH";
    public static final String REPORT = "REPORT";
    public static final String VIPHAM = "VIPHAM";
    public static final String XEPLOAI = "XEPLOAI";
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
        mainPanel.add(new FormChiTietMon(), CHITIETHMON);
        mainPanel.add(new FormDiem(), DIEM);
        mainPanel.add(new FormGiaoVien(), GIAOVIER);
        mainPanel.add(new FormHanhKiem(), HANHKIEM);
        mainPanel.add(new FormHocKy(), HOCKY);
        mainPanel.add(new FormHocSinh(), HOCSINH);
        mainPanel.add(new FormMonHoc(), MONHOC);
        mainPanel.add(new FormNamHoc(this), NAMHOC);
        mainPanel.add(new FormPhuHuynh(), PHUHUYNH);
        mainPanel.add(new FormReport(), REPORT);
        mainPanel.add(new FormViPham(), VIPHAM);
        mainPanel.add(new FormXepLoai(), XEPLOAI);
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
