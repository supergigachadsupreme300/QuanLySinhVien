package GUI;

import BusinessLogicLayer.ExcelBLL;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;

public class PanelExportExcel extends JPanel {

    private JComboBox<String> cboLop;
    private JComboBox<String> cboMon;
    private JTextField txtPath;

    private JTable table;
    private DefaultTableModel model;

    private ExcelBLL excelBLL;

    public PanelExportExcel(){

        excelBLL = new ExcelBLL();

        setLayout(new MigLayout("fill","[grow]","[][grow]"));
        setBorder(BorderFactory.createTitledBorder("EXPORT EXCEL"));

        // PANEL TOP
        JPanel top = new JPanel(new MigLayout("fillx","[100][100][grow][100][120][100]"));

        cboLop = new JComboBox<>(new String[]{
                "6A1","6A2","6A3"
        });

        cboMon = new JComboBox<>(new String[]{
                "TOAN","VAN","ANH","LY","HOA"
        });

        txtPath = new JTextField();

        JButton btnLoad = new JButton("Load Data");
        JButton btnSave = new JButton("Chọn nơi lưu");
        JButton btnExport = new JButton("Export");

        top.add(new JLabel("Lớp"));
        top.add(cboLop);

        top.add(new JLabel("Môn"));
        top.add(cboMon);

        top.add(txtPath,"growx");

        top.add(btnLoad);
        top.add(btnSave);
        top.add(btnExport);

        add(top,"growx,wrap");

        // TABLE
        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "MaHS","HoTen","TX","GK","CK","TB"
        });

        table = new JTable(model);

        add(new JScrollPane(table),"grow");

        // LOAD DATA
        btnLoad.addActionListener(e -> loadData());

        // CHỌN NƠI LƯU
        btnSave.addActionListener(e -> choosePath());

        // EXPORT
        btnExport.addActionListener(e -> exportExcel());

    }

    private void loadData(){

        model.setRowCount(0);

        String maLop = cboLop.getSelectedItem().toString();
        String maMon = cboMon.getSelectedItem().toString();

        excelBLL.previewDiemTheoLopMon(maLop, maMon, model);

    }

    private void choosePath(){

        JFileChooser chooser = new JFileChooser();

        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){

            File file = chooser.getSelectedFile();

            txtPath.setText(file.getAbsolutePath()+".xlsx");

        }

    }

    private void exportExcel(){

        String maLop = cboLop.getSelectedItem().toString();
        String maMon = cboMon.getSelectedItem().toString();

        File file = new File(txtPath.getText());

        boolean result = excelBLL.exportDiemTheoLopMon(maLop, maMon, file);

        if(result){

            JOptionPane.showMessageDialog(this,"Export thành công!");

        }else{

            JOptionPane.showMessageDialog(this,"Export thất bại!");

        }

    }

}