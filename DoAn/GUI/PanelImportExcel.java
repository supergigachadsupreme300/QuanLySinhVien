package GUI;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;

public class PanelImportExcel extends JPanel {

    public PanelImportExcel(){

        setLayout(new MigLayout("fill","[grow]","[grow]"));

        setBorder(BorderFactory.createTitledBorder("IMPORT EXCEL"));

        JTabbedPane tabs = new JTabbedPane();

        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));

        tabs.addTab("Danh sách học sinh", new PanelImportHocSinh());
        tabs.addTab("Bảng điểm", new PanelImportDiem());
        tabs.setBackground(Color.WHITE);
        tabs.setForeground(Color.BLACK);
        tabs.setBorder(BorderFactory.createEmptyBorder());

        UIManager.put("TabbedPane.selected", new Color(255,255,255));
        UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);

        add(tabs,"grow");

    }
}
