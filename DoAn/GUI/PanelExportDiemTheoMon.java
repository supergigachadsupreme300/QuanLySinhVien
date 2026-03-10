package GUI;

import BusinessLogicLayer.ExcelBLL;
import BusinessLogicLayer.HocKyBLL;
import BusinessLogicLayer.LopBLL;
import BusinessLogicLayer.MonHocBLL;
import DataObject.Lop;
import DataObject.Mon;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.miginfocom.swing.MigLayout;
import java.io.File;

public class PanelExportDiemTheoMon extends JPanel {

    private JComboBox<String> cboHocKy;
    private JComboBox<String> cboLop;
    private JComboBox<String> cboMon;
    private LopBLL lopBLL = new LopBLL();
    private MonHocBLL monBLL = new MonHocBLL();
    private HocKyBLL hocKyBLL = new HocKyBLL();
    private JTextField txtPath;

    private JTable table;
    private DefaultTableModel model;

    private ExcelBLL excelBLL;

    public PanelExportDiemTheoMon(){

        excelBLL = new ExcelBLL();

        setLayout(new MigLayout("fill","[grow]","[][grow]"));

        JPanel top = new JPanel(new MigLayout("fillx","[][120][][120][120][grow][100][120][120]"));

        cboLop = new JComboBox<>();
        cboMon = new JComboBox<>();
        cboHocKy = new JComboBox<>();

        txtPath = new JTextField();

        JButton btnLoad = new JButton("Load");
        JButton btnChoose = new JButton("Chọn nơi lưu");
        JButton btnExport = new JButton("Export");

        top.add(new JLabel("Lớp"));
        top.add(cboLop);

        top.add(new JLabel("Môn"));
        top.add(cboMon);
        
        top.add(new JLabel("Học kỳ"));
        top.add(cboHocKy);

        top.add(txtPath,"growx");

        top.add(btnLoad);
        top.add(btnChoose);
        top.add(btnExport);

        add(top,"growx,wrap");

        model = new DefaultTableModel();

        model.setColumnIdentifiers(new String[]{
                "MaHS","HoTen","TX","GK","CK","TB"
        });

        table = new JTable(model);

        add(new JScrollPane(table),"grow");

        btnLoad.addActionListener(e -> loadData());
        btnChoose.addActionListener(e -> choosePath());
        btnExport.addActionListener(e -> exportExcel());
        
        loadLopCombo();
        loadMonCombo();
        loadHocKyCombo();

        loadData();
    }

    private void loadData(){

        model.setRowCount(0);

        String maLop = cboLop.getSelectedItem().toString();
        String maMon = cboMon.getSelectedItem().toString();
        String maHK = cboMon.getSelectedItem().toString();


        excelBLL.previewDiemTheoLopMon(maLop, maMon, maHK, model);
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
        String maMon = cboMon.getSelectedItem().toString();

        File file = new File(txtPath.getText());

        boolean result = excelBLL.exportDiemTheoLopMon(maLop, maMon, file);

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
    private void loadMonCombo(){

        cboMon.removeAllItems();

        java.util.List<Mon> list = monBLL.getAllActive();

        for(Mon m : list){
            cboMon.addItem(m.getMaMon());
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
}