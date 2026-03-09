package GUI;

import BusinessLogicLayer.ExcelBLL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.io.File;

public class PanelImportDiem extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtFile;

    private ExcelBLL excelBLL;

    public PanelImportDiem(){

        excelBLL = new ExcelBLL();

        setLayout(new MigLayout("fill, insets 15","[grow]","[]10[grow]"));
        setBackground(Color.WHITE);

        // ===== TOP PANEL =====
        JPanel top = new JPanel(new MigLayout("fillx","[grow][110][110][110]"));
        top.setBorder(BorderFactory.createTitledBorder("Chọn file Excel"));

        txtFile = new JTextField();
        txtFile.setEditable(false);

        JButton btnChoose = new JButton("Chọn file");
        JButton btnPreview = new JButton("Preview");
        JButton btnImport = new JButton("Import");

        btnChoose.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPreview.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));

        top.add(txtFile,"growx");
        top.add(btnChoose);
        top.add(btnPreview);
        top.add(btnImport);

        add(top,"growx,wrap");

        // ===== TABLE =====
        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "Mã HS","Môn","Học Kỳ","TX","GK","CK"
        });

        table = new JTable(model);

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.PLAIN,13));
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Dữ liệu xem trước"));

        add(scroll,"grow");

        // ===== EVENT =====
        btnChoose.addActionListener(e->chooseFile());
        btnPreview.addActionListener(e->preview());
        btnImport.addActionListener(e->importExcel());
    }

    private void chooseFile(){

        JFileChooser chooser = new JFileChooser();

        if(chooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION){

            txtFile.setText(chooser.getSelectedFile().getAbsolutePath());

        }
    }

    private void preview(){

        model.setRowCount(0);

        excelBLL.previewDiem(txtFile.getText(),model);
    }

    private void importExcel(){

        boolean kq = excelBLL.importDiem(new File(txtFile.getText()));

        JOptionPane.showMessageDialog(this,
                kq ? "Import thành công" : "Import thất bại");
    }
}
