package GUI;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class MainMenu extends JFrame {

    public static final String LOGIN = "LOGIN";
    public static final String LOP  = "LOP";
    public static final String TKB = "TKB";
    public static final String PHANCONG = "PHANCONG";
    public static final String CHITIETTIET = "CHITIETTIET";
    public static final String CHITIETHMON = "CHITIETHMON";
    public static final String DIEM = "DIEM";
    public static final String GIAOVIEN = "GIAOVIEN";
    public static final String HANHKIEM = "HANHKIEM";
    public static final String HOCKY = "HOCKY";
    public static final String HOCSINH = "HOCSINH";
    public static final String MONHOC = "MONHOC";
    public static final String NAMHOC = "NAMHOC";
    public static final String PHUHUYNH = "PHUHUYNH";
    public static final String REPORT = "REPORT";
    public static final String VIPHAM = "VIPHAM";
    public static final String XEPLOAI = "XEPLOAI";
    public static final String EXCEL = "EXCEL";

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private Sidebar sidebar;

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

        sidebar = new Sidebar(this);
        sidebar.setVisible(false); // Ẩn sidebar lúc đầu

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Thêm các form quản lý
        mainPanel.add(new FormLop(this), LOP);
        mainPanel.add(new FormTKB(this), TKB);
        mainPanel.add(new FormPhanCong(this), PHANCONG);
        mainPanel.add(new FormChiTietTiet(this), CHITIETTIET);
        mainPanel.add(new FormChiTietMon(), CHITIETHMON);
        mainPanel.add(new FormDiem(), DIEM);
        mainPanel.add(new FormGiaoVien(), GIAOVIEN);
        mainPanel.add(new FormHanhKiem(), HANHKIEM);
        mainPanel.add(new FormHocKy(), HOCKY);
        mainPanel.add(new FormHocSinh(), HOCSINH);
        mainPanel.add(new FormMonHoc(), MONHOC);
        mainPanel.add(new FormNamHoc(this), NAMHOC);
        mainPanel.add(new FormPhuHuynh(), PHUHUYNH);
        mainPanel.add(new FormReport(), REPORT);
        mainPanel.add(new FormViPham(), VIPHAM);
        mainPanel.add(new FormXepLoai(), XEPLOAI);
        mainPanel.add(new FormExcel(), EXCEL);
        // Thêm form đăng nhập
        mainPanel.add(new LoginForm(this), LOGIN);

        add(sidebar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // Hiển thị form đăng nhập đầu tiên
        showForm(LOGIN);
    }

    public void showForm(String name) {
        // Reset filter trước khi show form tương ứng (giữ nguyên code cũ)
        if (name.equals(PHUHUYNH)) {
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof FormPhuHuynh) {
                    ((FormPhuHuynh) comp).resetFilter();
                    break;
                }
            }
        } else if (name.equals(HOCSINH)) {
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof FormHocSinh) {
                    ((FormHocSinh) comp).resetFilter();
                    break;
                }
            }
        }
        cardLayout.show(mainPanel, name);
    }

    // Được gọi từ LoginForm khi đăng nhập thành công
    public void loginSuccess() {
        sidebar.setVisible(true);
        showForm(LOP);
    }

    // Được gọi từ nút Đăng xuất trên Sidebar
    public void logout() {
        sidebar.setVisible(false);
        showForm(LOGIN);
    }

    // Các phương thức refresh giữ nguyên...
    public void refreshChiTietTietTKB(String maTKBToSelect) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof FormChiTietTiet) {
                ((FormChiTietTiet) comp).refreshTKBList(maTKBToSelect);
                return;
            }
        }
    }

    public void refreshTKBLuoi() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof FormTKB) {
                ((FormTKB) comp).refreshCurrentLuoi();
                return;
            }
        }
    }

    public void refreshLop() {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof FormLop) {
                ((FormLop) comp).refreshTableAfterChange();
                return;
            }
        }
    }

    public void openHocSinhForParent(String maPH) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof FormHocSinh) {
                FormHocSinh fh = (FormHocSinh) comp;
                fh.loadStudentsByParent(maPH);
                cardLayout.show(mainPanel, HOCSINH);
                return;
            }
        }
        cardLayout.show(mainPanel, HOCSINH);
    }

    public void openPhuHuynhForStudent(String maHS) {
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof FormPhuHuynh) {
                FormPhuHuynh fp = (FormPhuHuynh) comp;
                fp.setFilterMaHS(maHS);
                fp.loadTable();
                cardLayout.show(mainPanel, PHUHUYNH);
                return;
            }
        }
        cardLayout.show(mainPanel, PHUHUYNH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
