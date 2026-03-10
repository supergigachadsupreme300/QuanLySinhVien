package GUI;

import BusinessLogicLayer.ExcelBLL;
import BusinessLogicLayer.HocKyBLL;
import BusinessLogicLayer.LopBLL;
import DataObject.Lop;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import java.io.File;

public class PanelExportTatCaMon extends JPanel {

    private JComboBox<String> cboLop;
    private JComboBox<String> cboHocKy;
    private LopBLL lopBLL = new LopBLL();
    private HocKyBLL hocKyBLL = new HocKyBLL();
    private JTextField txtPath;

    private JTable table;
    private DefaultTableModel model;

    private ExcelBLL excelBLL;

    public PanelExportTatCaMon(){

        excelBLL = new ExcelBLL();

        setLayout(new MigLayout("fill","[grow]","[][grow]"));

        JPanel top = new JPanel(new MigLayout("fillx","[][120][][120][grow][100][120][120]"));

        cboLop = new JComboBox<>();
        cboHocKy = new JComboBox<>();

        txtPath = new JTextField();

        JButton btnLoad = new JButton("Load");
        JButton btnChoose = new JButton("Chọn nơi lưu");
        JButton btnExport = new JButton("Export");

        top.add(new JLabel("Lớp"));
        top.add(cboLop);
        top.add(new JLabel("Học Kỳ"));
        top.add(cboHocKy);

        top.add(new JLabel(""));

        top.add(txtPath,"growx");

        top.add(btnLoad);
        top.add(btnChoose);
        top.add(btnExport);

        add(top,"growx,wrap");

        model = new DefaultTableModel();


        table = new JTable(model);

        add(new JScrollPane(table),"grow");
        loadLopCombo();
        loadHocKyCombo();
        
        btnLoad.addActionListener(e -> loadData());
        btnChoose.addActionListener(e -> choosePath());
        btnExport.addActionListener(e -> exportExcel());
        
        loadData();
    }

    private void loadData(){

        model.setRowCount(0);

        String maLop = cboLop.getSelectedItem().toString();
        String maHK = cboHocKy.getSelectedItem().toString();


        excelBLL.previewBangDiemTatCaMon(maLop, maHK, model);

    }

    private void choosePath(){

        JFileChooser chooser = new JFileChooser();

        if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){

            File file = chooser.getSelectedFile();

            txtPath.setText(file.getAbsolutePath()+".xlsx");

        }
    }

    private void exportExcel(){

        String maLop = cboLop.getSelectedItem().toString();
        String maHK = cboHocKy.getSelectedItem().toString();


        File file = new File(txtPath.getText());

        boolean result = excelBLL.exportBangDiemTatCaMon(maLop, maHK, file);

        JOptionPane.showMessageDialog(this,
                result ? "Export thành công" : "Export thất bại");

    }
    private void loadLopCombo(){

        cboLop.removeAllItems();

        java.util.List<Lop> list = lopBLL.getAllActive();

        for(Lop lop : list){
            cboLop.addItem(lop.getMaLop());
        }
    }
    private void loadHocKyCombo() {
        cboHocKy.removeAllItems();
        java.util.List<DataObject.HocKy> list = hocKyBLL.getAllActive();
        if (list == null) return;
        for (DataObject.HocKy hk : list) {
            cboHocKy.addItem(hk.getMaHK());
        }
    }
    private void loadHocSinhTheoLop(){

        model.setRowCount(0);

        String maLop = (String) cboLop.getSelectedItem();
        String maHK = (String) cboLop.getSelectedItem();
        if(maLop == null || maHK == null){
            return;
        }
        excelBLL.previewBangDiemTatCaMon(maLop, maHK, model);
    }    
}