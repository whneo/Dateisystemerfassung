package dateisystemerfassung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Rainer
 * 22.06.2016
 */

public class MySQLVerbindung {
    private static final String URL = 
            "jdbc:mysql://localhost:3306/dateisystemerfassungklon";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection con = null;

    public static Connection getConnection() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return con;
    }
    public static void closeConnection(){
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}