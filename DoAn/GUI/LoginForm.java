package GUI;

import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;

public class LoginForm extends JPanel {

    private MainMenu mainMenu;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginForm(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        initUI();
    }

    private void initUI() {

        setLayout(new GridBagLayout());
        setBackground(new Color(10, 20, 40));

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setOpaque(false);
        add(backgroundPanel);

        JPanel card = new JPanel(new MigLayout(
                "wrap 1, insets 40, gapy 18, align center",
                "[center]",
                ""
        ));

        card.setPreferredSize(new Dimension(360, 420));
        card.setBackground(new Color(255, 255, 255, 220));

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255,255,255,180),2),
                BorderFactory.createEmptyBorder(30,30,30,30)
        ));


        JLabel lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblIcon.setForeground(new Color(41,128,185));
        card.add(lblIcon, "align center");


        JLabel lblTitle = new JLabel("CHƯƠNG TRÌNH QUẢN LÝ HỌC SINH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(lblTitle, "align center");


        JPanel userPanel = new JPanel(new MigLayout(
                "insets 0, gapx 8, align center",
                "[][180!]",
                ""
        ));
        userPanel.setOpaque(false);

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,180,180)),
                BorderFactory.createEmptyBorder(8,8,8,8)
        ));

        userPanel.add(userIcon);
        userPanel.add(txtUsername, "w 180!, h 40!");

        card.add(userPanel);


        JPanel passPanel = new JPanel(new MigLayout(
                "insets 0, gapx 8, align center",
                "[][180!]",
                ""
        ));
        passPanel.setOpaque(false);

        JLabel passIcon = new JLabel("🔒");
        passIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));

        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,180,180)),
                BorderFactory.createEmptyBorder(8,8,8,8)
        ));

        passPanel.add(passIcon);
        passPanel.add(txtPassword, "w 180!, h 40!");

        card.add(passPanel);


        btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(41,128,185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        card.add(btnLogin, "w 240!, h 42!, align center");

        backgroundPanel.add(card);

        txtPassword.addActionListener(e -> login());
        btnLogin.addActionListener(e -> login());
    }

    private void login() {

        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập tên đăng nhập và mật khẩu!",
                    "Thiếu thông tin",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if ("admin".equals(username) && "admin".equals(password)) {

            JOptionPane.showMessageDialog(this,
                    "Đăng nhập thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);

            mainMenu.loginSuccess();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Sai tên đăng nhập hoặc mật khẩu!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}