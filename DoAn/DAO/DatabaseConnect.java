/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcessLayer;

/**
 *
 * @author admin
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    
    // ========== CẤU HÌNH KẾT NỐI ==========
    private static final String URL = 
        "jdbc:sqlserver://localhost:1433;" +
        "databaseName=QuanLyHocSinh;" +
        "trustServerCertificate=true;" +
        "encrypt=true";
    
    private static final String USER = "sa";
    private static final String PASS = "123456";
    
    // Connection instance (không static để tránh conflict đa luồng)
    private Connection con;
    
    
    /**
     * Constructor
     */    
    public DatabaseConnect() {
        this.con = null;
    }
    
    /**
     * MỞ KẾT NỐI
     * @return Connection object
     */
    public Connection openConnection() {
        try {
            // Load JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            
            // Tạo connection
            con = DriverManager.getConnection(URL, USER, PASS);
            
            System.out.println("✅ Kết nối database thành công!");
            return con;
            
        } catch (ClassNotFoundException ex) {
            System.err.println("❌ Không tìm thấy JDBC Driver!");
            ex.printStackTrace();
            return null;
            
        } catch (SQLException ex) {
            System.err.println("❌ Lỗi kết nối database!");
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * ĐÓNG KẾT NỐI
     */
    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("✅ Đã đóng kết nối database");
            }
        } catch (SQLException ex) {
            System.err.println("❌ Lỗi khi đóng kết nối!");
            ex.printStackTrace();
        }
    }
    
    /**
     * LẤY CONNECTION HIỆN TẠI
     * @return Connection object
     */
    public Connection getConnection() {
        return this.con;
    }
    
    /**
     * KIỂM TRA KẾT NỐI
     * @return true nếu kết nối OK
     */
    public boolean testConnection() {
        try {
            Connection testConn = openConnection();
            if (testConn != null && testConn.isValid(3)) {
                closeConnection();
                return true;
            }
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}