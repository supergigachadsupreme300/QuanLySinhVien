package GUI;
import javax.swing.*;
import java.awt.*;
import net.miginfocom.swing.MigLayout;
import DataObject.Parent;

public class parent_GUI extends JPanel {
    private Parent parent;
    private JTextField txtMaPhH;
    private JTextField txtTenPhH;
    private JTextField txtSdt;
    private JTextField txtNgheNghiep;
    private JTextField txtQuanHe;

    // Constructor mặc định (tạo đối tượng rỗng để test)
    public parent_GUI() {
        this(new Parent());
    }

    // Constructor nhận đối tượng Parent
    public parent_GUI(Parent p) {
        this.parent = p != null ? p : new Parent();
        setLayout(new MigLayout("wrap 2", "[right][grow,fill]", "[]10[]"));
        setBackground(Color.CYAN);

        add(new JLabel("Mã phụ huynh:"));
        txtMaPhH = new JTextField(20);
        txtMaPhH.setEditable(false);
        add(txtMaPhH, "growx");

        add(new JLabel("Tên phụ huynh:"));
        txtTenPhH = new JTextField(20);
        txtTenPhH.setEditable(false);
        add(txtTenPhH, "growx");

        add(new JLabel("Số điện thoại:"));
        txtSdt = new JTextField(20);
        txtSdt.setEditable(false);
        add(txtSdt, "growx");

        add(new JLabel("Nghề nghiệp:"));
        txtNgheNghiep = new JTextField(20);
        txtNgheNghiep.setEditable(false);
        add(txtNgheNghiep, "growx");

        add(new JLabel("Quan hệ với học sinh:"));
        txtQuanHe = new JTextField(20);
        txtQuanHe.setEditable(false);
        add(txtQuanHe, "growx");

        add(new JButton("Học sinh"), "span, center");

        // Cập nhật dữ liệu từ đối tượng
        updateDisplay();
    }

    // Phương thức cập nhật hiển thị từ đối tượng Parent
    public void updateDisplay() {
        if (parent != null) {
            txtMaPhH.setText(parent.getMaPhH());
            txtTenPhH.setText(parent.getTenPhH());
            txtSdt.setText(parent.getSdt());
            txtNgheNghiep.setText(parent.getNgheNghiep());
            txtQuanHe.setText(parent.getQuanHe());
        }
    }

    // Phương thức cập nhật đối tượng Parent
    public void setParent(Parent p) {
        this.parent = p;
        updateDisplay();
    }

    // Phương thức lấy đối tượng Parent hiện tại
    public Parent getParent() {
        return parent;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Parent Information");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new parent_GUI());
            frame.setVisible(true);
        });
    }
}
