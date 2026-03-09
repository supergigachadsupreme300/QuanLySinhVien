package GUI;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;

public class PanelExportExcel extends JPanel {

    public PanelExportExcel(){

        setLayout(new MigLayout("fill","[grow]","[grow]"));

        setBorder(BorderFactory.createTitledBorder("EXPORT EXCEL"));

        JTabbedPane tabs = new JTabbedPane();

        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab("Bảng điểm theo môn", new PanelExportDiemTheoMon());
        tabs.addTab("Bảng điểm tất cả môn", new PanelExportTatCaMon());

        add(tabs,"grow");
    }
}

