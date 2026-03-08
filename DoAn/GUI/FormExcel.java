package GUI;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.CardLayout;
import java.awt.Font;

public class FormExcel extends JPanel {

    private JButton btnImport;
    private JButton btnExport;

    private JPanel panelContent;

    private CardLayout card;

    public FormExcel() {

        setLayout(new MigLayout("fill","[grow]","[][grow]"));

        JLabel title = new JLabel("QUẢN LÝ IMPORT / EXPORT EXCEL");
        title.setFont(new Font("Segoe UI",Font.BOLD,20));

        add(title,"center,wrap");

        // ===== BUTTON MENU =====

        JPanel menu = new JPanel(new MigLayout("insets 0"));

        btnImport = new JButton("Import Excel");
        btnExport = new JButton("Export Excel");

        menu.add(btnImport,"gapright 10");
        menu.add(btnExport);

        add(menu,"center,wrap");

        // ===== CONTENT =====

        card = new CardLayout();
        panelContent = new JPanel(card);

        panelContent.add(new PanelImportExcel(),"IMPORT");
        panelContent.add(new PanelExportExcel(),"EXPORT");

        add(panelContent,"grow");

        // mặc định mở IMPORT
        card.show(panelContent,"IMPORT");

        // EVENT
        btnImport.addActionListener(e -> card.show(panelContent,"IMPORT"));
        btnExport.addActionListener(e -> card.show(panelContent,"EXPORT"));
    }
}