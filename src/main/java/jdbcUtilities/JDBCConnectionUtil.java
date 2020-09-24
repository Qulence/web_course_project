package jdbcUtilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionUtil {

    // jdbc:DBMS://host:port/DBName
    private String url;
    private String DBMS;
    private String user;
    private String password;

    public JDBCConnectionUtil(String DBMS, String host, int port, String DBName, String user, String password) {
        this.url = "jdbc:" + DBMS + "://" + host + ":" + port + "/" + DBName;
        this.DBMS = DBMS;
        this.user = user;
        this.password = password;
        try {
            Class.forName("org." + DBMS + ".Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver error");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
