package dateisystemerfassung;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author Rainer 22.06.2016
 */
public class Dateisystem {

    // Variablen
    private int id;
    private String name;
    private String typ;
    private String pfad;
    ArrayList<Dateisystem> dSsWalk = new ArrayList<>();
    ArrayList<Dateisystem> dSsGet = new ArrayList<>();
    ArrayList<String> absoluterPfad = new ArrayList<>();

    // SQL-Variablen
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rst;

    // Konstruktoren
    public Dateisystem(int id, String name, String typ, String pfad) {
        this.id = id;
        this.name = name;
        this.typ = typ;
        this.pfad = pfad;
    }

    public Dateisystem(String name, String typ, String pfad) {
        this.name = name;
        this.typ = typ;
        this.pfad = pfad;
    }

    public Dateisystem(int id) {
        this.id = id;
    }

    public Dateisystem(String name) {
        this.name = name;
    }

    public Dateisystem() {
    }

    // Getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTyp() {
        return typ;
    }

    public String getPfad() {
        return pfad;
    }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }

    // Methoden
    // toString-Methode
    @Override
    public String toString() {
        return "id = " + id + "\n" + "name = " + name + "\n" + "typ = "
                + typ + "\n" + "pfad = " + pfad + "\n" + "\n";
    }

    // insert-Methode
    public void insert() {
        try {
            // VERBINDUNG AUFBBAUEN:
            Connection con = MySQLVerbindung.getConnection();
            Dateisystem check = new Dateisystem(0);
            for (int i = 0; i < dSsWalk.size(); i++) {
                if (i == 0) {
                    String sqlFirstInsert = "INSERT INTO dateisystem VALUES (null, ?, ?, null)";
                    pst = con.prepareStatement(sqlFirstInsert, PreparedStatement.RETURN_GENERATED_KEYS);
                    pst.setString(1, dSsWalk.get(i).getName());
                    pst.setString(2, dSsWalk.get(i).getTyp());
                    pst.executeUpdate();
                    rst = pst.getGeneratedKeys();
                    while (rst.next()) {
                        check.setId(rst.getInt(1));
                    }
                    continue;
                } else {
                    String sqlCheck = "SELECT * FROM dateisystem WHERE Name=?";
                    pst = con.prepareStatement(sqlCheck);
                    pst.setString(1, dSsWalk.get(i).getPfad());
                    rst = pst.executeQuery();
                    while (rst.next()) {
                        check.setId(rst.getInt("ID"));
                    }
                    String sqlInsert = "INSERT INTO dateisystem VALUES (null, ?, ?, ?)";
                    pst = con.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS);
                    pst.setString(1, dSsWalk.get(i).getName());
                    pst.setString(2, dSsWalk.get(i).getTyp());
                    pst.setInt(3, check.getId());
                    pst.executeUpdate();
                    rst = pst.getGeneratedKeys();
                    while (rst.next()) {
                        dSsWalk.get(i).setId(rst.getInt(1));
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // getAll-Methode
    public ArrayList<Dateisystem> getAllByName(String userabfrage) {
        try {
            Connection con = MySQLVerbindung.getConnection();
            String sql = "SELECT * FROM dateisystem WHERE Name LIKE ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, "%" + userabfrage + "%");
            rst = pst.executeQuery();
            while (rst.next()) {
                Dateisystem dS = new Dateisystem();
                dS.setId(rst.getInt("ID"));
                dS.setName(rst.getString("Name"));
                dS.setTyp(rst.getString("Typ"));
                ArrayList pfade = new ArrayList<>(dS.getPfadById(rst.getInt("Pfad")));
                String x = "";
                for (int i = pfade.size() - 1; i >= 0; i--) {
                    x = x.concat(pfade.get(i) + "\\");
                }
                dS.setPfad(x);
                dSsGet.add(dS);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return dSsGet;
    }

    public ArrayList<String> getPfadById(int pfad) {
        String pfadsegment = null;
        try {
            Connection con = MySQLVerbindung.getConnection();
            String sql = "SELECT * FROM dateisystem WHERE ID=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, pfad);
            rst = pst.executeQuery();
            while (rst.next()) {
                if (rst.getInt("ID") != 1) {
                    pfadsegment = rst.getString("Name");
                    absoluterPfad.add(pfadsegment);
                    getPfadById(rst.getInt("Pfad"));
                    break;
                } else {
                    pfadsegment = rst.getString("Name");
                    absoluterPfad.add(pfadsegment);
                    break;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
                if (rst != null) {
                    rst.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return absoluterPfad;
    }

    // delete-Methode
    public void delete() {
        try {
            Connection con = MySQLVerbindung.getConnection();
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

    // create-Methode
    public void create() {
        try {
            Connection con = MySQLVerbindung.getConnection();
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

    // Methode zum auslesen des Dateisystems
    public void walk(String rootVerzeichnis, String path) {
        File root = new File(path);
        File[] list = root.listFiles();
        if (list == null) {
            return;
        } else if (dSsWalk.isEmpty()) {
            Dateisystem dS = new Dateisystem(rootVerzeichnis, "Verzeichnis", null);
            dSsWalk.add(dS);
            for (File list1 : list) {
                String[] pfadsegmente = list1.getAbsolutePath().split(Pattern.quote("\\"));
                if (list1.isDirectory()) {
                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Verzeichnis", rootVerzeichnis);
                    dSsWalk.add(dS);
                    walk(pfadsegmente[pfadsegmente.length - 1], list1.getAbsolutePath());
                } else {
                    typ = "Datei";
                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Datei", rootVerzeichnis);
                    dSsWalk.add(dS);
                }
            }
        } else {
            for (File list1 : list) {
                String[] pfadsegmente = list1.getAbsolutePath().split(Pattern.quote("\\"));
                Dateisystem dS;
                if (list1.isDirectory()) {
                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Verzeichnis", rootVerzeichnis);
                    dSsWalk.add(dS);
                    walk(pfadsegmente[pfadsegmente.length - 1], list1.getAbsolutePath());
                } else {
                    typ = "Datei";
                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Datei", rootVerzeichnis);
                    dSsWalk.add(dS);
                }
            }
        }
    }
}
