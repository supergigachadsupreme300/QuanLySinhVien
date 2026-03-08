package GUI;

import BusinessLogicLayer.ExcelBLL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import java.io.File;

public class PanelImportDiem extends JPanel {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtFile;

    private ExcelBLL excelBLL;

    public PanelImportDiem(){

        excelBLL = new ExcelBLL();

        setLayout(new MigLayout("fill","[grow]","[][grow]"));

        JPanel top = new JPanel(new MigLayout("fillx","[grow][120][120][120]"));

        txtFile = new JTextField();

        JButton btnChoose = new JButton("Chọn file");
        JButton btnPreview = new JButton("Preview");
        JButton btnImport = new JButton("Import");

        top.add(txtFile,"growx");
        top.add(btnChoose);
        top.add(btnPreview);
        top.add(btnImport);

        add(top,"growx,wrap");

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "MaHS","Mon","HocKy","TX","GK","CK"
        });

        table = new JTable(model);

        add(new JScrollPane(table),"grow");

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