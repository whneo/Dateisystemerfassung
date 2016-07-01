package dateisystemerfassung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Rainer 22.06.2016
 */
public class MySQLVerbindung {

    private static Statement st;
    private static Connection con = null;

    private static final String URL
            = "jdbc:mysql://localhost:3306/dateisystemerfassung";
    private static final String USER = "root";
    private static final String PASSWORD = "";

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

    public static void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

//    public static void createDB() {
//        try {
//            MySQLVerbindung.getConnection();
//            String sql = "CREATE DATABASE dateisystemerfassung";
//            st = con.createStatement();
//            st.executeUpdate(sql);
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (st != null) {
//                    st.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//                ex.printStackTrace();
//            }
//        }
//    }

    public static void createTable() {
        try {
            try {
            MySQLVerbindung.getConnection();
            String sql = "use dateisystemerfassung";
            st = con.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
            MySQLVerbindung.getConnection();
            String sql = "CREATE TABLE dateisystem (ID int(11) NOT NULL "
                    + "AUTO_INCREMENT, Name varchar(255) DEFAULT NULL, Typ "
                    + "enum('Datei','Verzeichnis') DEFAULT NULL, Pfad int(11) "
                    + "DEFAULT NULL, PRIMARY KEY (`ID`), FOREIGN KEY (`Pfad`) "
                    + "REFERENCES `dateisystem` (`ID`))";
            st = con.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
//    public static void dropDB() {
//        try {
//            MySQLVerbindung.getConnection();
//            String sql = "DROP DATABASE dateisystemerfassung";
//            st = con.createStatement();
//            st.executeUpdate(sql);
//        } catch (SQLException ex) {
//            System.out.println(ex.getMessage());
//            ex.printStackTrace();
//        } finally {
//            try {
//                if (st != null) {
//                    st.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//                ex.printStackTrace();
//            }
//        }
//    }
    
    public static void dropTable() {
        try {
            MySQLVerbindung.getConnection();
            String sql = "DROP TABLE dateisystem";
            st = con.createStatement();
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
