package GUI;

import BusinessLogicLayer.ParentBLL;
import DataObject.Parent;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class FormPhuHuynh extends JPanel {

    private final ParentBLL parentBLL = new ParentBLL();

    private JTable tblParent;
    private DefaultTableModel modelParent;

    // search
    private JTextField txtSearchName;
    private JButton btnTim, btnNangCao;

    // detail panel wrapper + close (reuse parent_GUI)
    private JPanel pnlParent;
    private JButton btnCloseParent;
    private parent_GUI parentPanel;

    // input form
    private JTextField txtMa, txtTen, txtSdt, txtNghe, txtQuanHe, txtMaHS;
    private JButton btnThem, btnClear;

    public FormPhuHuynh() {
        initUI();
    }

    private String filterMaHS = null;

    /**
     * Tạo form và chỉ hiển thị phụ huynh liên quan tới mã học sinh truyền vào
     */
    public FormPhuHuynh(String maHS) {
        this.filterMaHS = maHS;
        initUI();
    }

    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[grow]15[]15[]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ PHỤ HUYNH", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        JPanel pnlSearch = new JPanel(new MigLayout("insets 0", "[][grow]10[][]", "[]"));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Tìm kiếm"));
        txtSearchName = new JTextField(); btnTim = new JButton("Tìm"); btnNangCao = new JButton("Nâng cao");
        pnlSearch.add(new JLabel("Tên:")); pnlSearch.add(txtSearchName, "growx"); pnlSearch.add(btnTim); pnlSearch.add(btnNangCao);
        add(pnlSearch, "growx, wrap");

        modelParent = new DefaultTableModel(new String[]{"Mã PH", "Họ tên", "SDT", "Nghề nghiệp", "Quan hệ"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblParent = new JTable(modelParent);
        styleTable(tblParent);
        tblParent.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        // make taller vertically; keep horizontal widths as before
        tblParent.setPreferredScrollableViewportSize(new Dimension(700, 600));
        tblParent.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblParent.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblParent.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblParent.getColumnModel().getColumn(3).setPreferredWidth(180);
        tblParent.getColumnModel().getColumn(4).setPreferredWidth(120);
        tblParent.setRowHeight(24);
        JScrollPane sp = new JScrollPane(tblParent);
        sp.setBorder(BorderFactory.createTitledBorder("Danh sách phụ huynh"));
        // postpone adding to layout until pnlParent is built


        // detail content - reuse parent_GUI
        parentPanel = new parent_GUI();
        parentPanel.setPreferredSize(new Dimension(360, 260));
        pnlParent = new JPanel(new BorderLayout());
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.add(new JLabel("Thông tin phụ huynh"), BorderLayout.WEST);
        btnCloseParent = new JButton("X"); btnCloseParent.setBackground(new Color(200,50,50)); btnCloseParent.setForeground(Color.WHITE); btnCloseParent.setFocusPainted(false);
        hdr.add(btnCloseParent, BorderLayout.EAST);
        pnlParent.add(hdr, BorderLayout.NORTH);
        pnlParent.add(parentPanel, BorderLayout.CENTER);
        pnlParent.setVisible(false);
        // now that both components exist, build split container
        JPanel split = new JPanel(new MigLayout("fill", "[65%][35%]", "[grow]"));
        split.add(sp, "grow");
        split.add(pnlParent, "grow");
        add(split, "grow, wrap");

        // input form
        JPanel pnlForm = new JPanel(new MigLayout("insets 15", "[]15[grow]30[]15[grow]", "[]10[]10[]10[]"));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Nhập / sửa thông tin"));
        txtMa = new JTextField(); txtTen = new JTextField(); txtSdt = new JTextField(); txtNghe = new JTextField(); txtQuanHe = new JTextField(); txtMaHS = new JTextField();
        pnlForm.add(new JLabel("Mã PH:")); pnlForm.add(txtMa, "growx");
        pnlForm.add(new JLabel("Họ tên:")); pnlForm.add(txtTen, "growx, wrap");
        pnlForm.add(new JLabel("SDT:")); pnlForm.add(txtSdt, "growx");
        pnlForm.add(new JLabel("Nghề nghiệp:")); pnlForm.add(txtNghe, "growx, wrap");
        pnlForm.add(new JLabel("Quan hệ:")); pnlForm.add(txtQuanHe, "growx, wrap");
        pnlForm.add(new JLabel("Mã học sinh (liên kết):")); pnlForm.add(txtMaHS, "growx, wrap");
        add(pnlForm, "growx, wrap");

        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34,139,34)); btnClear = createButton("Làm mới", new Color(70,130,180));
        pnlBtn.add(btnThem); pnlBtn.add(btnClear);
        add(pnlBtn, "growx, wrap");

        // events
        btnTim.addActionListener(e -> searchByName());
        btnNangCao.addActionListener(e -> showAdvancedSearch());
        btnThem.addActionListener(e -> them()); btnClear.addActionListener(e -> clearForm());

        tblParent.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int r = tblParent.getSelectedRow();
                if (r >= 0) {
                    fillDetail(r);
                    // provide BLL reference to panel for edit/delete
                    parentPanel.setParentBLL(parentBLL);
                    pnlParent.setVisible(true);
                }
            }
        });

        btnCloseParent.addActionListener(e -> { pnlParent.setVisible(false); tblParent.clearSelection(); });

        addFocusEffect(txtSearchName); addFocusEffect(txtMa); addFocusEffect(txtTen); addFocusEffect(txtSdt); addFocusEffect(txtNghe); addFocusEffect(txtQuanHe);

        loadTable();
    }

    public void loadTable() {
        modelParent.setRowCount(0);
        if (filterMaHS != null && !filterMaHS.isEmpty()) {
            for (Parent p : parentBLL.getParentsByHocSinh(filterMaHS)) {
                modelParent.addRow(new Object[]{p.getMaPhH(), p.getTenPhH(), p.getSdt(), p.getNgheNghiep(), p.getQuanHe()});
            }
            return;
        }
        for (Parent p : parentBLL.getAll()) {
            modelParent.addRow(new Object[]{p.getMaPhH(), p.getTenPhH(), p.getSdt(), p.getNgheNghiep(), p.getQuanHe()});
        }
    }

    public void setFilterMaHS(String maHS) {
        this.filterMaHS = maHS;
    }

    private void searchByName() {
        String k = txtSearchName.getText().trim().toLowerCase();
        if (k.isEmpty()) { loadTable(); return; }
        modelParent.setRowCount(0);
        for (Parent p : parentBLL.getAll()) if (p.getTenPhH().toLowerCase().contains(k)) modelParent.addRow(new Object[]{p.getMaPhH(), p.getTenPhH(), p.getSdt(), p.getNgheNghiep(), p.getQuanHe()});
    }

    private void showAdvancedSearch() {
        JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Tìm kiếm nâng cao - Phụ huynh", true);
        dlg.setLayout(new MigLayout("fill", "[][grow]", "[]10[]10[]10[]10[]15[]"));
        JTextField fMa = new JTextField();
        JTextField fHoTen = new JTextField();
        JTextField fSdt = new JTextField();
        JTextField fNghe = new JTextField();
        JTextField fQuanHe = new JTextField();
        JButton btnOk = new JButton("Tìm");
        JButton btnCancel = new JButton("Hủy");

        dlg.add(new JLabel("Mã PH:")); dlg.add(fMa, "growx, wrap");
        dlg.add(new JLabel("Họ tên chứa:")); dlg.add(fHoTen, "growx, wrap");
        dlg.add(new JLabel("SDT chứa:")); dlg.add(fSdt, "growx, wrap");
        dlg.add(new JLabel("Nghề nghiệp chứa:")); dlg.add(fNghe, "growx, wrap");
        dlg.add(new JLabel("Quan hệ chứa:")); dlg.add(fQuanHe, "growx, wrap");
        dlg.add(btnOk, "split 2"); dlg.add(btnCancel, "wrap");

        btnOk.addActionListener(ev -> {
            modelParent.setRowCount(0);
            for (Parent p : parentBLL.getAll()) {
                if (!fMa.getText().trim().isEmpty() && !p.getMaPhH().equals(fMa.getText().trim())) continue;
                if (!fHoTen.getText().trim().isEmpty() && !p.getTenPhH().toLowerCase().contains(fHoTen.getText().trim().toLowerCase())) continue;
                if (!fSdt.getText().trim().isEmpty() && !p.getSdt().toLowerCase().contains(fSdt.getText().trim().toLowerCase())) continue;
                if (!fNghe.getText().trim().isEmpty() && !p.getNgheNghiep().toLowerCase().contains(fNghe.getText().trim().toLowerCase())) continue;
                if (!fQuanHe.getText().trim().isEmpty() && !p.getQuanHe().toLowerCase().contains(fQuanHe.getText().trim().toLowerCase())) continue;
                modelParent.addRow(new Object[]{p.getMaPhH(), p.getTenPhH(), p.getSdt(), p.getNgheNghiep(), p.getQuanHe()});
            }
            dlg.dispose();
        });
        btnCancel.addActionListener(ev -> dlg.dispose());
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    private void fillDetail(int r) {
        String ma = modelParent.getValueAt(r,0).toString();
        DataObject.Parent p = parentBLL.getByMa(ma);
        if (p != null) parentPanel.setParent(p);
    }

    private void clearForm() { txtMa.setText(""); txtTen.setText(""); txtSdt.setText(""); txtNghe.setText(""); txtQuanHe.setText(""); tblParent.clearSelection(); pnlParent.setVisible(false); filterMaHS = ""; loadTable(); }

    private Parent getEntityFromForm() {
        Parent p = new Parent();
        p.setMaPhH(txtMa.getText().trim()); p.setTenPhH(txtTen.getText().trim()); p.setSdt(txtSdt.getText().trim()); p.setNgheNghiep(txtNghe.getText().trim()); p.setQuanHe(txtQuanHe.getText().trim());
        return p;
    }

    private void them() {
        Parent p = getEntityFromForm();
        if (parentBLL.themParent(p)) {
            // nếu có mã HS nhập vào, thêm quan hệ
            String maHS = txtMaHS.getText().trim();
            if (!maHS.isEmpty()) {
                parentBLL.addRelation(maHS, p.getMaPhH(), p.getQuanHe());
            }
            JOptionPane.showMessageDialog(this, "Thêm thành công"); loadTable(); clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /* helpers */
    private JButton createButton(String text, Color color) { JButton btn = new JButton(text); btn.setBackground(color); btn.setForeground(Color.WHITE); btn.setFocusPainted(false); return btn; }
    private void styleTable(JTable t) { t.getTableHeader().setReorderingAllowed(false); t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); }
    private void addFocusEffect(JTextField f) { f.addFocusListener(new FocusAdapter(){ public void focusGained(FocusEvent e){ f.setBackground(new Color(255,255,204)); } public void focusLost(FocusEvent e){ f.setBackground(Color.WHITE); } }); }

}
