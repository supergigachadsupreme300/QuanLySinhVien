package GUI;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FormExcel extends JPanel {

    private JButton tabImport;
    private JButton tabExport;

    private JPanel panelContent;
    private CardLayout card;

    private Color activeColor = new Color(255,255,255);
    private Color inactiveColor = new Color(235,235,235);
    private Color hoverColor = new Color(245,245,245);

    public FormExcel() {

        setLayout(new MigLayout("fill, insets 20","[grow]","[][grow]"));
        setBackground(new Color(245,246,250));

        JLabel lblTitle = new JLabel("QUẢN LÝ IMPORT / EXPORT EXCEL", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0,102,204));
        add(lblTitle, "grow, wrap");

        // ===== TAB BAR =====
        JPanel tabBar = new JPanel(new MigLayout("insets 0","[]0[]",""));
        tabBar.setOpaque(false);

        tabImport = createTab("Import Excel");
        tabExport = createTab("Export Excel");

        tabBar.add(tabImport);
        tabBar.add(tabExport);

        add(tabBar,"center,wrap");

        // ===== CONTENT =====
        card = new CardLayout();
        panelContent = new JPanel(card);
        panelContent.setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220,220,220)),
                BorderFactory.createEmptyBorder(10,10,10,10)
            )
        );     
        panelContent.add(new PanelImportExcel(),"IMPORT");
        panelContent.add(new PanelExportExcel(),"EXPORT");

        add(panelContent,"grow");
        setBackground(new Color(245,246,250));

        showImport();

        tabImport.addActionListener(e -> showImport());
        tabExport.addActionListener(e -> showExport());
    }

    private JButton createTab(String text){

        JButton btn = new JButton(text);

        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI",Font.BOLD,14));

        btn.setBackground(inactiveColor);
        btn.setBorder(BorderFactory.createEmptyBorder(10,25,10,25));

        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        btn.addMouseListener(new MouseAdapter(){

            public void mouseEntered(MouseEvent e){

                if(btn.getBackground() != activeColor){
                    btn.setBackground(hoverColor);
                }

            }

            public void mouseExited(MouseEvent e){

                if(btn.getBackground() != activeColor){
                    btn.setBackground(inactiveColor);
                }

            }

        });

        return btn;
    }

    private void showImport(){

        card.show(panelContent,"IMPORT");

        tabImport.setBackground(activeColor);
        tabExport.setBackground(inactiveColor);
    }

    private void showExport(){

        card.show(panelContent,"EXPORT");

        tabExport.setBackground(activeColor);
        tabImport.setBackground(inactiveColor);
    }

}
