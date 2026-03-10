
package DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    

    private static final String URL = 
        "jdbc:sqlserver://localhost:1433;" +
        "databaseName=QuanLyHocSinh;" +
        "trustServerCertificate=true;" +
        "encrypt=true;" +
        "useUnicode=true;" +
        "characterEncoding=UTF-8";
    
    private static final String USER = "sa";
    private static final String PASS = "123456";
    

    private Connection con;
    
    

    public DatabaseConnect() {
        this.con = null;
    }
    

    public Connection openConnection() {
        try {

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            

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
    

    public Connection getConnection() {
        return this.con;
    }
    

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