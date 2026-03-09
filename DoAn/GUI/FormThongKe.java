package GUI;

import BusinessLogicLayer.DiemBLL;
import BusinessLogicLayer.HanhKiemBLL;
import BusinessLogicLayer.HocSinhBLL;
import DataObject.Diem;
import DataObject.HanhKiem;
import DataObject.HocSinh;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class FormThongKe extends JFrame {

    private DiemBLL diemBLL;
    private HanhKiemBLL hanhKiemBLL;
    private HocSinhBLL hocSinhBLL;

    public FormThongKe(Connection con) {

        diemBLL = new DiemBLL(con);
        hanhKiemBLL = new HanhKiemBLL();
        hocSinhBLL = new HocSinhBLL();

        setTitle("Dashboard Thống Kê");
        setSize(1000,700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel dashboard = new JPanel(new GridLayout(2,2));

        dashboard.add(new ChartPanel(createHocLucChart()));
        dashboard.add(new ChartPanel(createHanhKiemChart()));
        dashboard.add(new ChartPanel(createHocSinhTheoLopChart()));

        add(dashboard,BorderLayout.CENTER);
    }

    private JFreeChart createHocLucChart(){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int gioi=0,kha=0,tb=0,yeu=0;

        List<HocSinh> hsList = hocSinhBLL.getAll();

        for(HocSinh hs : hsList){

            List<Diem> list = diemBLL.getByMaHS(hs.getMaHS());

            if(list == null) continue;

            for(Diem d : list){

                double diem = d.getDiemTBMonHocKy();

                if(diem>=8) gioi++;
                else if(diem>=6.5) kha++;
                else if(diem>=5) tb++;
                else yeu++;
            }
        }

        dataset.addValue(gioi,"Học lực","Giỏi");
        dataset.addValue(kha,"Học lực","Khá");
        dataset.addValue(tb,"Học lực","Trung bình");
        dataset.addValue(yeu,"Học lực","Yếu");

        return ChartFactory.createBarChart(
                "Thống kê học lực",
                "Loại",
                "Số lượng",
                dataset
        );
    }

    private JFreeChart createHanhKiemChart(){

        DefaultPieDataset dataset = new DefaultPieDataset();

        int tot=0,kha=0,tb=0,yeu=0;

        List<HanhKiem> list = hanhKiemBLL.getAll();

        for(HanhKiem hk : list){

            String xl = hk.getXepLoai();

            if(xl.equalsIgnoreCase("Tốt")) tot++;
            else if(xl.equalsIgnoreCase("Khá")) kha++;
            else if(xl.equalsIgnoreCase("Trung bình")) tb++;
            else yeu++;
        }

        dataset.setValue("Tốt",tot);
        dataset.setValue("Khá",kha);
        dataset.setValue("Trung bình",tb);
        dataset.setValue("Yếu",yeu);

        return ChartFactory.createPieChart(
                "Thống kê hạnh kiểm",
                dataset,
                true,
                true,
                false
        );
    }

    private JFreeChart createHocSinhTheoLopChart(){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<HocSinh> list = hocSinhBLL.getAll();

        Map<String,Integer> map = new HashMap<>();

        for(HocSinh hs : list){

            String lop = hs.getMaLop();

            map.put(lop,map.getOrDefault(lop,0)+1);
        }

        for(String lop : map.keySet()){
            dataset.addValue(map.get(lop),"Sĩ số",lop);
        }

        return ChartFactory.createBarChart(
                "Thống kê học sinh theo lớp",
                "Lớp",
                "Số học sinh",
                dataset
        );
    }
}