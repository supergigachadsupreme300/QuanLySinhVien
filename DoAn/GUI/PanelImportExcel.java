package GUI;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;

public class PanelImportExcel extends JPanel {

    private JButton btnImportHS;
    private JButton btnImportDiem;

    private JPanel panelContent;
    private CardLayout card;

    public PanelImportExcel(){

        setLayout(new MigLayout("fill","[grow]","[][grow]"));

        setBorder(BorderFactory.createTitledBorder("IMPORT EXCEL"));

        // MENU
        JPanel menu = new JPanel(new MigLayout("insets 0"));

        btnImportHS = new JButton("Import Danh sách học sinh");
        btnImportDiem = new JButton("Import Bảng điểm");

        menu.add(btnImportHS,"gapright 10");
        menu.add(btnImportDiem);

        add(menu,"center,wrap");

        // CONTENT
        card = new CardLayout();
        panelContent = new JPanel(card);

        panelContent.add(new PanelImportHocSinh(),"HS");
        panelContent.add(new PanelImportDiem(),"DIEM");

        add(panelContent,"grow");

        // mặc định
        card.show(panelContent,"HS");

        // EVENT
        btnImportHS.addActionListener(e -> card.show(panelContent,"HS"));
        btnImportDiem.addActionListener(e -> card.show(panelContent,"DIEM"));
    }
}