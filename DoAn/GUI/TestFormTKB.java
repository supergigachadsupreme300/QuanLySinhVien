/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author admin
 */
import DataObject.ThoiKhoaBieu;
import DataObject.ChiTietTiet;
import DataObject.Lop;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
//import java.time.LocalDate;
import java.util.ArrayList;
import net.miginfocom.swing.MigLayout;

public class TestFormTKB extends JPanel {

    private MainMenu mainFrame;
    // ================= DATA GIẢ =================
    private ArrayList<ThoiKhoaBieu> dsTKB = new ArrayList<>();    // ~ DAO
    private ArrayList<ChiTietTiet> dsChiTiet = new ArrayList<>(); // ~ DAO
    private ArrayList<Lop> dsLop = new ArrayList<>();            // ~ DAO
    private ThoiKhoaBieu tkbDangChon = null;                     // ~ BUS/Logic đang chọn

    // ================= UI =================
    private JTextField txtMaTKB; //, txtNgayBD, txtNgayKT;
    private JComboBox<Lop> cboLop;
    private JComboBox<ThoiKhoaBieu> cboTKB;

    private JTable tblTKBList, tblTKBLuoi;
    private DefaultTableModel modelTKBList, modelTKBLuoi;

    private JButton btnThem, btnSua, btnXoa, btnClear; //,btnQuayLai;

    // ================= CONSTRUCTOR =================
    public TestFormTKB(MainMenu frame) {
        /*setTitle("Quản lý thời khóa biểu");
        setSize(1100, 720);
        setLocationRelativeTo(null);

        // Hỏi khi đóng form
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int c = JOptionPane.showConfirmDialog(
                        TestFormTKB.this,
                        "Bạn có muốn thoát chương trình?",
                        "Thoát",
                        JOptionPane.YES_NO_OPTION
                );
                if (c == JOptionPane.YES_OPTION) System.exit(0);
            }
        });*/
        this.mainFrame = frame;

        initData();  // Load dữ liệu mẫu
        initUI();    // Build giao diện
        loadComboLop(); //load dữ liệu lớp
        loadTableTKB();  // Load dữ liệu lên table
        loadComboTKB();  // Load combo box
        initGrid();      // Load lưới TKB trống
        updateButtonState();

    }

    // ================= DATA GIẢ / DAO =================
    private void initData() {
        // ---- Dữ liệu lớp
        Lop lop1 = new Lop("10A1", "10A1", 40, "NH01", "GV01");
        Lop lop2 = new Lop("10A2", "10A2", 42, "NH01", "GV02");
        Lop lop3 = new Lop("10A3", "10A3", 45, "NH01", "GV03");
        
        dsLop.add(lop1);
        dsLop.add(lop2);
        dsLop.add(lop3);

        // ---- Dữ liệu TKB
        ThoiKhoaBieu tkb1 = new ThoiKhoaBieu(
                "TKB01", "10A1", "HK1"//,
//                LocalDate.of(2025, 9, 1),
//                LocalDate.of(2025, 12, 31)
        );
        ThoiKhoaBieu tkb2 = new ThoiKhoaBieu(
                "TKB02", "10A2", "HK1"//,
//                LocalDate.of(2025, 9, 1),
//                LocalDate.of(2025, 12, 31)
        );
        ThoiKhoaBieu tkb3 = new ThoiKhoaBieu(
                "TKB03", "10A3", "HK1"//,
//                LocalDate.of(2025, 9, 1),
//                LocalDate.of(2025, 12, 31)
        );
        dsTKB.add(tkb1);
        dsTKB.add(tkb2);
        dsTKB.add(tkb3);
        // ---- Dữ liệu chi tiết tiết học
        
        //tkb tuan của 10A1
        //Thứ 2 
        dsChiTiet.add(new ChiTietTiet("CT01", "TKB01", "TOAN", "Thứ 2", 1, "10A1", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT02", "TKB01", "TOAN", "Thứ 2", 2, "10A1", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT03", "TKB01", "TOAN", "Thứ 2", 3, "10A1", "09:00", "9:45"));
        dsChiTiet.add(new ChiTietTiet("CT04", "TKB01", "TOAN", "Thứ 2", 4, "10A1", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT05", "TKB01", "TOAN", "Thứ 2", 5, "10A1", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT06", "TKB01", "VAN", "Thứ 2", 6, "10A1", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT07", "TKB01", "VAN", "Thứ 2", 7, "10A1", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT08", "TKB01", "CN", "Thứ 2", 8, "10A1", "03:00", "03:45"));
        
        //Thứ 3
        dsChiTiet.add(new ChiTietTiet("CT09", "TKB01", "LY", "Thứ 3", 1, "10A1", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT010", "TKB01", "LY", "Thứ 3", 2, "10A1", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT011", "TKB01", "SINH", "Thứ 3", 3, "10A1", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT012", "TKB01", "TD", "Thứ 3", 4, "10A1", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT013", "TKB01", "TD", "Thứ 3", 5, "10A1", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT014", "TKB01", "TA", "Thứ 3", 6, "10A1", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT015", "TKB01", "TA", "Thứ 3", 7, "10A1", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT016", "TKB01", "TIN", "Thứ 3", 8, "10A1", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT017", "TKB01", "TIN", "Thứ 3", 9, "10A1", "03:50", "04:40"));
        
        //Thứ 4
        dsChiTiet.add(new ChiTietTiet("CT018", "TKB01", "SU", "Thứ 4", 1, "10A1", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT019", "TKB01", "SU", "Thứ 4", 2, "10A1", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT020", "TKB01", "GD", "Thứ 4", 3, "10A1", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT021", "TKB01", "GD", "Thứ 4", 4, "10A1", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT022", "TKB01", "", "Thứ 4", 5, "10A1", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT023", "TKB01", "TOAN", "Thứ 4", 6, "10A1", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT024", "TKB01", "TOAN", "Thứ 4", 7, "10A1", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT025", "TKB01", "VAN", "Thứ 4", 8, "10A1", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT026", "TKB01", "VAN", "Thứ 4", 9, "10A1", "03:50", "04:40"));
        
        //Thứ 5
        dsChiTiet.add(new ChiTietTiet("CT027", "TKB01", "SINH", "Thứ 5", 1, "10A1", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT028", "TKB01", "SINH", "Thứ 5", 2, "10A1", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT029", "TKB01", "HOA", "Thứ 5", 3, "10A1", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT030", "TKB01", "HOA", "Thứ 5", 4, "10A1", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT031", "TKB01", "TIN", "Thứ 5", 5, "10A1", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT032", "TKB01", "TOAN", "Thứ 5", 6, "10A1", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT033", "TKB01", "TOAN", "Thứ 5", 7, "10A1", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT034", "TKB01", "VAN", "Thứ 5", 8, "10A1", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT035", "TKB01", "VAN", "Thứ 5", 9, "10A1", "03:50", "04:40"));
        dsChiTiet.add(new ChiTietTiet("CT036", "TKB01", "VAN", "Thứ 5", 9, "10A1", "04:45", "05:20"));
        
        //Thứ 6
        dsChiTiet.add(new ChiTietTiet("CT037", "TKB01", "LY", "Thứ 6", 1, "10A1", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT038", "TKB01", "LY", "Thứ 6", 2, "10A1", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT039", "TKB01", "HOA", "Thứ 6", 3, "10A1", "09:00", "9:45"));
        dsChiTiet.add(new ChiTietTiet("CT040", "TKB01", "HOA", "Thứ 6", 4, "10A1", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT041", "TKB01", "CN", "Thứ 6", 5, "10A1", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT042", "TKB01", "", "Thứ 6", 6, "10A1", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT043", "TKB01", "GD", "Thứ 6", 7, "10A1", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT044", "TKB01", "GD", "Thứ 6", 8, "10A1", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT045", "TKB01", "", "Thứ 6", 9, "10A1", "03:50", "04:40"));
        dsChiTiet.add(new ChiTietTiet("CT046", "TKB01", "", "Thứ 6", 10, "10A1", "04:45", "05:20"));
        
        //tkb tuan của 10A2
        //Thứ 2 
        dsChiTiet.add(new ChiTietTiet("CT01", "TKB02", "TOAN", "Thứ 2", 1, "10A2", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT02", "TKB02", "TOAN", "Thứ 2", 2, "10A2", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT03", "TKB02", "TOAN", "Thứ 2", 3, "10A2", "09:00", "9:45"));
        dsChiTiet.add(new ChiTietTiet("CT04", "TKB02", "TOAN", "Thứ 2", 4, "10A2", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT05", "TKB02", "TOAN", "Thứ 2", 5, "10A2", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT06", "TKB02", "VAN", "Thứ 2", 6, "10A2", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT07", "TKB02", "VAN", "Thứ 2", 7, "10A2", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT08", "TKB02", "CN", "Thứ 2", 8, "10A2", "03:00", "03:45"));
        
        //Thứ 3
        dsChiTiet.add(new ChiTietTiet("CT09", "TKB02", "LY", "Thứ 3", 1, "10A2", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT010", "TKB02", "LY", "Thứ 3", 2, "10A2", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT011", "TKB02", "SINH", "Thứ 3", 3, "10A2", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT012", "TKB02", "TD", "Thứ 3", 4, "10A2", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT013", "TKB02", "TD", "Thứ 3", 5, "10A2", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT014", "TKB02", "TA", "Thứ 3", 6, "10A2", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT015", "TKB02", "TA", "Thứ 3", 7, "10A2", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT016", "TKB02", "TIN", "Thứ 3", 8, "10A2", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT017", "TKB02", "TIN", "Thứ 3", 9, "10A2", "03:50", "04:40"));
        
        //Thứ 4
        dsChiTiet.add(new ChiTietTiet("CT018", "TKB02", "SU", "Thứ 4", 1, "10A2", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT019", "TKB02", "SU", "Thứ 4", 2, "10A2", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT020", "TKB02", "GD", "Thứ 4", 3, "10A2", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT021", "TKB02", "GD", "Thứ 4", 4, "10A2", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT022", "TKB02", "", "Thứ 4", 5, "10A2", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT023", "TKB02", "TOAN", "Thứ 4", 6, "10A2", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT024", "TKB02", "TOAN", "Thứ 4", 7, "10A2", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT025", "TKB02", "VAN", "Thứ 4", 8, "10A2", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT026", "TKB02", "VAN", "Thứ 4", 9, "10A2", "03:50", "04:40"));
        
        //Thứ 5
        dsChiTiet.add(new ChiTietTiet("CT027", "TKB02", "SINH", "Thứ 5", 1, "10A2", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT028", "TKB02", "SINH", "Thứ 5", 2, "10A2", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT029", "TKB02", "HOA", "Thứ 5", 3, "10A2", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT030", "TKB02", "HOA", "Thứ 5", 4, "10A2", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT031", "TKB02", "TIN", "Thứ 5", 5, "10A2", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT032", "TKB02", "TOAN", "Thứ 5", 6, "10A2", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT033", "TKB02", "TOAN", "Thứ 5", 7, "10A2", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT034", "TKB02", "VAN", "Thứ 5", 8, "10A2", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT035", "TKB02", "VAN", "Thứ 5", 9, "10A2", "03:50", "04:40"));
        dsChiTiet.add(new ChiTietTiet("CT036", "TKB02", "VAN", "Thứ 5", 9, "10A2", "04:45", "05:20"));
        
        //Thứ 6
        dsChiTiet.add(new ChiTietTiet("CT037", "TKB02", "LY", "Thứ 6", 1, "10A2", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT038", "TKB02", "LY", "Thứ 6", 2, "10A2", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT039", "TKB02", "HOA", "Thứ 6", 3, "10A2", "09:00", "9:45"));
        dsChiTiet.add(new ChiTietTiet("CT040", "TKB02", "HOA", "Thứ 6", 4, "10A2", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT041", "TKB02", "CN", "Thứ 6", 5, "10A2", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT042", "TKB02", "", "Thứ 6", 6, "10A2", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT043", "TKB02", "GD", "Thứ 6", 7, "10A2", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT044", "TKB02", "GD", "Thứ 6", 8, "10A2", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT045", "TKB02", "", "Thứ 6", 9, "10A2", "03:50", "04:40"));
        dsChiTiet.add(new ChiTietTiet("CT046", "TKB02", "", "Thứ 6", 10, "10A2", "04:45", "05:20"));
        
        //tkb tuan của 10A3
        //Thứ 2 
        dsChiTiet.add(new ChiTietTiet("CT01", "TKB03", "TOAN", "Thứ 2", 1, "10A3", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT02", "TKB03", "TOAN", "Thứ 2", 2, "10A3", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT03", "TKB03", "TOAN", "Thứ 2", 3, "10A3", "09:00", "9:45"));
        dsChiTiet.add(new ChiTietTiet("CT04", "TKB03", "TOAN", "Thứ 2", 4, "10A3", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT05", "TKB03", "TOAN", "Thứ 2", 5, "10A3", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT06", "TKB03", "VAN", "Thứ 2", 6, "10A3", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT07", "TKB03", "VAN", "Thứ 2", 7, "10A3", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT08", "TKB03", "CN", "Thứ 2", 8, "10A3", "03:00", "03:45"));
        
        //Thứ 3
        dsChiTiet.add(new ChiTietTiet("CT09", "TKB03", "LY", "Thứ 3", 1, "10A3", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT010", "TKB03", "LY", "Thứ 3", 2, "10A3", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT011", "TKB03", "SINH", "Thứ 3", 3, "10A3", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT012", "TKB03", "TD", "Thứ 3", 4, "1031", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT013", "TKB03", "TD", "Thứ 3", 5, "10A3", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT014", "TKB03", "TA", "Thứ 3", 6, "10A3", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT015", "TKB03", "TA", "Thứ 3", 7, "10A3", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT016", "TKB03", "TIN", "Thứ 3", 8, "10A3", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT017", "TKB03", "TIN", "Thứ 3", 9, "10A3", "03:50", "04:40"));
        
        //Thứ 4
        dsChiTiet.add(new ChiTietTiet("CT018", "TKB03", "SU", "Thứ 4", 1, "10A3", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT019", "TKB03", "SU", "Thứ 4", 2, "10A3", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT020", "TKB03", "GD", "Thứ 4", 3, "10A3", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT021", "TKB03", "GD", "Thứ 4", 4, "10A3", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT022", "TKB03", "", "Thứ 4", 5, "10A3", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT023", "TKB03", "TOAN", "Thứ 4", 6, "10A3", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT024", "TKB03", "TOAN", "Thứ 4", 7, "10A3", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT025", "TKB03", "VAN", "Thứ 4", 8, "10A3", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT026", "TKB03", "VAN", "Thứ 4", 9, "10A3", "03:50", "04:40"));
        
        //Thứ 5
        dsChiTiet.add(new ChiTietTiet("CT027", "TKB03", "SINH", "Thứ 5", 1, "10A3", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT028", "TKB03", "SINH", "Thứ 5", 2, "10A3", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT029", "TKB03", "HOA", "Thứ 5", 3, "10A3", "09:00", "09:45"));
        dsChiTiet.add(new ChiTietTiet("CT030", "TKB03", "HOA", "Thứ 5", 4, "10A3", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT031", "TKB03", "TIN", "Thứ 5", 5, "10A3", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT032", "TKB03", "TOAN", "Thứ 5", 6, "10A3", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT033", "TKB03", "TOAN", "Thứ 5", 7, "10A3", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT034", "TKB03", "VAN", "Thứ 5", 8, "10A3", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT035", "TKB03", "VAN", "Thứ 5", 9, "10A3", "03:50", "04:40"));
        dsChiTiet.add(new ChiTietTiet("CT036", "TKB03", "VAN", "Thứ 5", 9, "10A3", "04:45", "05:20"));
        
        //Thứ 6
        dsChiTiet.add(new ChiTietTiet("CT037", "TKB03", "LY", "Thứ 6", 1, "10A3", "07:00", "07:45"));
        dsChiTiet.add(new ChiTietTiet("CT038", "TKB03", "LY", "Thứ 6", 2, "10A3", "07:50", "08:40"));
        dsChiTiet.add(new ChiTietTiet("CT039", "TKB03", "HOA", "Thứ 6", 3, "10A3", "09:00", "9:45"));
        dsChiTiet.add(new ChiTietTiet("CT040", "TKB03", "HOA", "Thứ 6", 4, "10A3", "09:50", "10:30"));
        dsChiTiet.add(new ChiTietTiet("CT041", "TKB03", "CN", "Thứ 6", 5, "10A3", "10:35", "11:20"));
        dsChiTiet.add(new ChiTietTiet("CT042", "TKB03", "", "Thứ 6", 6, "10A3", "01:00", "01:45"));
        dsChiTiet.add(new ChiTietTiet("CT043", "TKB03", "GD", "Thứ 6", 7, "10A3", "01:50", "02:40"));
        dsChiTiet.add(new ChiTietTiet("CT044", "TKB03", "GD", "Thứ 6", 8, "10A3", "03:00", "03:45"));
        dsChiTiet.add(new ChiTietTiet("CT045", "TKB03", "", "Thứ 6", 9, "10A3", "03:50", "04:40"));
        dsChiTiet.add(new ChiTietTiet("CT046", "TKB03", "", "Thứ 6", 10, "10A3", "04:45", "05:20"));
    }

    // ================= UI =================
    private void initUI() {
        setLayout(new MigLayout("fill, insets 15", "[grow]", "[]15[]15[]15[grow]15[grow]"));

        JLabel lblTitle = new JLabel("QUẢN LÝ THỜI KHÓA BIỂU", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, "growx, wrap");

        // ===== PANEL THÔNG TIN =====
        JPanel pnlTop = new JPanel(new MigLayout("fill", "[grow][grow]", "[]"));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin TKB"));

        // --- Panel Input (Bên trái) ---
        JPanel pnlInput = new JPanel(new MigLayout("", "[]10[grow]", "[]10[]10[]10[]"));
        txtMaTKB = new JTextField();
//        txtNgayBD = new JTextField();
//        txtNgayKT = new JTextField();
        cboLop = new JComboBox<>();

        pnlInput.add(new JLabel("Mã TKB:"));
        pnlInput.add(txtMaTKB, "growx, wrap");
        pnlInput.add(new JLabel("Lớp:"));
        pnlInput.add(cboLop, "growx, wrap");
//        pnlInput.add(new JLabel("Ngày BD:"));
//        pnlInput.add(txtNgayBD, "growx, wrap");
//        pnlInput.add(new JLabel("Ngày KT:"));
//        pnlInput.add(txtNgayKT, "growx");

        // --- Panel Select (Bên phải) ---
        JPanel pnlSelect = new JPanel(new MigLayout("", "[]10[grow]", "[]"));
        pnlSelect.setBorder(BorderFactory.createTitledBorder("Chọn TKB"));
        cboTKB = new JComboBox<>();
        pnlSelect.add(new JLabel("TKB đã có:"));
        pnlSelect.add(cboTKB, "growx");

        pnlTop.add(pnlInput, "grow");
        pnlTop.add(pnlSelect, "grow");
        add(pnlTop, "growx, wrap");

        // ===== BUTTON =====
        JPanel pnlBtn = new JPanel();
        btnThem = createButton("Thêm", new Color(34, 139, 34));
        btnSua = createButton("Sửa", new Color(255, 140, 0));
        btnXoa = createButton("Xóa", new Color(220, 20, 60));
        btnClear = createButton("Làm mới", new Color(70, 130, 180));
//        btnQuayLai = createButton("Quay lại", new Color(100, 100, 100));
        

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnClear);
//        pnlBtn.add(btnQuayLai); 
        add(pnlBtn, "growx, wrap");

        // ===== TABLE DANH SÁCH TKB =====
        modelTKBList = new DefaultTableModel(
                //new String[]{"Mã TKB", "Lớp", "Ngày BD", "Ngày KT"}, 0
                new String[]{"Mã TKB", "Lớp", "Học kỳ"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTKBList = new JTable(modelTKBList);
        tblTKBList.setRowHeight(25);
        tblTKBList.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblTKBList.getTableHeader().setBackground(new Color(0, 102, 204));
        tblTKBList.getTableHeader().setForeground(Color.WHITE);
        styleTable(tblTKBList);
        
        JScrollPane scrollList = new JScrollPane(tblTKBList);
        scrollList.setBorder(BorderFactory.createTitledBorder("Danh sách TKB"));
        add(scrollList, "grow, wrap");

        // ===== TABLE LƯỚI TKB (THỜI KHÓA BIỂU) =====
        modelTKBLuoi = new DefaultTableModel(
                new String[]{"Tiết", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6"}, 0
        ) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tblTKBLuoi = new JTable(modelTKBLuoi);
        tblTKBLuoi.setRowHeight(30);
        tblTKBLuoi.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblTKBLuoi.getTableHeader().setBackground(new Color(0, 102, 204));
        tblTKBLuoi.getTableHeader().setForeground(Color.WHITE);
        styleTable(tblTKBLuoi);
        
        JScrollPane scrollLuoi = new JScrollPane(tblTKBLuoi);
        scrollLuoi.setBorder(BorderFactory.createTitledBorder("Thời khóa biểu chi tiết"));
        add(scrollLuoi, "grow, wrap");
        
        // Set màu cho cột "Tiết"
        tblTKBLuoi.getColumnModel().getColumn(0).setCellRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                    if (column == 0) {
                        c.setBackground(new Color(230, 240, 255));
                        c.setFont(c.getFont().deriveFont(Font.BOLD));
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                    return c;
                }
            }
        );
        
        // ================= EVENTS =================
        btnThem.addActionListener(e -> themTKB());
        btnSua.addActionListener(e -> suaTKB());
        btnXoa.addActionListener(e -> xoaTKB());
        btnClear.addActionListener(e -> clearForm());
//        btnQuayLai.addActionListener(e -> quaylai());
        
        cboTKB.addActionListener(e -> {
            tkbDangChon = (ThoiKhoaBieu) cboTKB.getSelectedItem();
            fillForm();
        });

        tblTKBList.getSelectionModel().addListSelectionListener(e -> {
            int row = tblTKBList.getSelectedRow();
            if (row < 0) return;

            String ma = modelTKBList.getValueAt(row, 0).toString();
            for (ThoiKhoaBieu t : dsTKB) {
                if (t.getMaTKB().equals(ma)) {
                    tkbDangChon = t;
                    break;
                }
            }
            fillForm();
        });
        /*addFocus(txtMaTKB);
        addFocus(txtNgayBD);
        addFocus(txtNgayKT);
        addFocus(cboLop);
        addFocus(cboTKB);

        addHover(btnThem);
        addHover(btnSua);
        addHover(btnXoa);
        addHover(btnClear);*/
        
        addFocusEffect(txtMaTKB);
//        addFocusEffect(txtNgayBD);
//        addFocusEffect(txtNgayKT);
        addFocusEffect(cboLop);
        addFocusEffect(cboTKB);

    }

// ===== TẠO BUTTON VỚI MÀU + HOVER =====
    private JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(100, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Color originalColor = bgColor;
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(originalColor.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(originalColor);
            }
        });

        return btn;
    }

    // ===== FOCUS EFFECT =====
    private void addFocusEffect(JComponent c) {
        c.setOpaque(true);
        c.setBackground(Color.WHITE);
        c.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                c.setBackground(new Color(230, 240, 255));
            }
            @Override
            public void focusLost(FocusEvent e) {
                c.setBackground(Color.WHITE);
            }
        });
    }

    
   


    // ================= TABLE STYLE =================
    private void styleTable(JTable tbl) {
        tbl.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tbl.getTableHeader().setBackground(new Color(0, 102, 204));
        tbl.getTableHeader().setForeground(Color.WHITE);
    }
    
    // CRUD
    private void themTKB() {
        if (txtMaTKB.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã TKB");
            return;
        }

        for (ThoiKhoaBieu t : dsTKB) {
            if (t.getMaTKB().equalsIgnoreCase(txtMaTKB.getText())) {
                JOptionPane.showMessageDialog(this, "Mã TKB đã tồn tại");
                return;
            }
        }

        if (JOptionPane.showConfirmDialog(this, "Thêm TKB mới?", "Xác nhận",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;

        Lop lop = (Lop) cboLop.getSelectedItem();
        ThoiKhoaBieu t = new ThoiKhoaBieu(
                txtMaTKB.getText(),
                lop.getMaLop(),
                "HK1"//,
                //LocalDate.parse(txtNgayBD.getText()),
                //LocalDate.parse(txtNgayKT.getText())
        );

        dsTKB.add(t);
        loadTableTKB();
        loadComboTKB();
        clearForm();
    }

    private void suaTKB() {
        if (tkbDangChon == null) return;

        int c = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn sửa TKB này?",
                "Xác nhận sửa",
                JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

//        tkbDangChon.setNgayBatDau(LocalDate.parse(txtNgayBD.getText()));
//        tkbDangChon.setNgayKetThuc(LocalDate.parse(txtNgayKT.getText()));

        loadTableTKB();
        JOptionPane.showMessageDialog(this, "Sửa TKB thành công!");
    }


    private void xoaTKB() {
        if (tkbDangChon == null) return;

        int c = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa TKB này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );
        if (c != JOptionPane.YES_OPTION) return;

        dsTKB.remove(tkbDangChon);
        clearForm();
        loadTableTKB();
        loadComboTKB();

        JOptionPane.showMessageDialog(this, "Xóa TKB thành công!");
    }

    // ===== QUAY LẠI MAIN MENU =====
    /*private void quaylai() {
        //new MainMenu().setVisible(true);
        //this.dispose();
    }*/

    // ================= BUS / LOGIC =================
    private void fillForm() {
        if (tkbDangChon == null) return;

        txtMaTKB.setText(tkbDangChon.getMaTKB());
//        txtNgayBD.setText(tkbDangChon.getNgayBatDau().toString());
//        txtNgayKT.setText(tkbDangChon.getNgayKetThuc().toString());

        txtMaTKB.setEnabled(false); // CHUẨN FORMLOP
        loadGridTKB(tkbDangChon.getMaTKB());
        updateButtonState();
    }


    private void clearForm() {
        txtMaTKB.setText("");
//        txtNgayBD.setText("");
//        txtNgayKT.setText("");
        txtMaTKB.setEnabled(true);

        cboLop.setSelectedIndex(0);
        cboTKB.setSelectedIndex(-1);
        tblTKBList.clearSelection();

        tkbDangChon = null;
        initGrid();
        updateButtonState();
        txtMaTKB.requestFocus();
    }
 
    // ================= LOAD =================
    private void loadTableTKB() {
        modelTKBList.setRowCount(0);
        for (ThoiKhoaBieu t : dsTKB) {
            modelTKBList.addRow(new Object[]{
                    t.getMaTKB(), t.getMaLop(),
                    t.getMaHK()
//                    t.getNgayBatDau(), t.getNgayKetThuc()
            });
        }
    }

    private void loadComboTKB() {
        cboTKB.removeAllItems();
        for (ThoiKhoaBieu t : dsTKB) cboTKB.addItem(t);
    }

    private void loadComboLop() {
        cboLop.removeAllItems();
        for (Lop l : dsLop) {
            cboLop.addItem(l);
        }
    }
    
    private void fillFormFromTable(int row) {
        ThoiKhoaBieu tkb = (ThoiKhoaBieu) modelTKBList.getValueAt(row, 0);
        tkbDangChon = tkb;

        txtMaTKB.setText(tkb.getMaTKB());

        // set combo lớp
        for (int i = 0; i < cboLop.getItemCount(); i++) {
            if (cboLop.getItemAt(i).getMaLop().equals(tkb.getMaLop())) {
                cboLop.setSelectedIndex(i);
                break;
            }
        }

        loadGridByTKB(tkb);
        txtMaTKB.setEnabled(false);
    }

    private void loadGridByTKB(ThoiKhoaBieu tkb) {
        initGrid();

        for (ChiTietTiet ct : dsChiTiet) {
            if (!ct.getMaTKB().equals(tkb.getMaTKB())) continue;

            int row = ct.getTiet() - 1;
            int col = thuToColumn(ct.getThu());

            if (row >= 0 && col >= 1) {
                modelTKBLuoi.setValueAt(ct.getMaMon(), row, col);
            }
        }
    }

    private int thuToColumn(String thu) {
        switch (thu) {
            case "Thứ 2": return 1;
            case "Thứ 3": return 2;
            case "Thứ 4": return 3;
            case "Thứ 5": return 4;
            case "Thứ 6": return 5;
            default: return -1;
        }
    }


    
    private void initGrid() {
        modelTKBLuoi.setRowCount(0);
        for (int i = 1; i <= 10; i++) {
            modelTKBLuoi.addRow(new Object[]{"Tiết " + i, "", "", "", "", ""});
        }
    }

    private void loadGridTKB(String maTKB) {
        initGrid();
        for (ChiTietTiet ct : dsChiTiet) {
            if (!ct.getMaTKB().equals(maTKB)) continue;

            int row = ct.getTiet() - 1;
            int col = switch (ct.getThu()) {
                case "Thứ 2" -> 1;
                case "Thứ 3" -> 2;
                case "Thứ 4" -> 3;
                case "Thứ 5" -> 4;
                case "Thứ 6" -> 5;
                default -> -1;
            };

            if (row >= 0 && col >= 0) {
                modelTKBLuoi.setValueAt(ct.getMaMon(), row, col);
            }
        }
    }
    
    private void updateButtonState() {
        boolean dangChon = tkbDangChon != null;
        btnSua.setEnabled(dangChon);
        btnXoa.setEnabled(dangChon);
    }

    /*private void addFocus(JComponent c) {
        c.setOpaque(true);
        c.setBackground(Color.WHITE);
        c.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                c.setBackground(new Color(230, 240, 255));
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                c.setBackground(Color.WHITE);
            }
        });
    }

    private void addHover(JButton btn) {
        Color normal = btn.getBackground();
        btn.setFocusPainted(false);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(180, 200, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(normal);
            }
        });
    }*/


    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new TestFormTKB().setVisible(true));
    }
}