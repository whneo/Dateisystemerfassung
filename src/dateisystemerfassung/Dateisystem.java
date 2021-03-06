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
    private ArrayList<Dateisystem> dSsWalk = new ArrayList<>();
    private ArrayList<Dateisystem> dSsGet = new ArrayList<>();
    private ArrayList<String> absoluterPfad = new ArrayList<>();

    // SQL-Variablen
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rst;

    // Konstruktoren
    public Dateisystem() {
    }

    public Dateisystem(String name, String typ, String pfad) {
        this.name = name;
        this.typ = typ;
        this.pfad = pfad;
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

    public ArrayList<Dateisystem> getdSsWalk() {
        return dSsWalk;
    }

    public ArrayList<Dateisystem> getdSsGet() {
        return dSsGet;
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
    // ToString-Methode
    @Override
    public String toString() {
        return "id = " + id + "\n" + "name = " + name + "\n" + "typ = "
                + typ + "\n" + "pfad = " + pfad + "\n" + "\n";
    }

    // Speichern in der Datenbank
    public void insert() {
        try {
            Connection con = MySQLVerbindung.getConnection();
            Dateisystem check = new Dateisystem();
            for (int i = 0; i < dSsWalk.size(); i++) {
                if (i == 0) {
                    String sqlFirstInsert = "INSERT INTO dateisystem VALUES "
                            + "(null, ?, ?, null)";
                    pst = con.prepareStatement(sqlFirstInsert,
                            PreparedStatement.RETURN_GENERATED_KEYS);
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
                    String sqlInsert = "INSERT INTO dateisystem VALUES "
                            + "(null, ?, ?, ?)";
                    pst = con.prepareStatement(sqlInsert,
                            PreparedStatement.RETURN_GENERATED_KEYS);
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

    // Ausgabe aus der Datenbank anhand der Usersuchanfrage
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
                ArrayList pfade = new ArrayList<>(dS.getPfadById(
                        rst.getInt("Pfad")));
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

    /* Fremdschlüssel-ID aus der Datenbank in String umwandeln um den Pfad 
       ausgeben zu können */
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

    // Methode zum auslesen des Dateisystems
    public void fileSystemReader(String rootVerzeichnis, String path) {
        File root = new File(path);
        File[] list = root.listFiles();
        if (list == null) {
            return;
        } else if (dSsWalk.isEmpty()) {
            Dateisystem dS = new Dateisystem(rootVerzeichnis,
                    "Verzeichnis", null);
            dSsWalk.add(dS);
            for (File list1 : list) {
                String[] pfadsegmente = list1.getAbsolutePath().split(
                        Pattern.quote("\\"));
                if (list1.isDirectory()) {
                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1],
                            "Verzeichnis", rootVerzeichnis);
                    dSsWalk.add(dS);
                    fileSystemReader(pfadsegmente[pfadsegmente.length - 1],
                            list1.getAbsolutePath());
                } else {
                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1],
                            "Datei", rootVerzeichnis);
                    dSsWalk.add(dS);
                }
            }
        } else {
            for (File list1 : list) {
                String[] pfadsegmente = list1.getAbsolutePath().split(
                        Pattern.quote("\\"));
                if (list1.isDirectory()) {
                    Dateisystem dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Verzeichnis",
                            rootVerzeichnis);
                    dSsWalk.add(dS);
                    fileSystemReader(pfadsegmente[pfadsegmente.length - 1],
                            list1.getAbsolutePath());
                } else {
                    Dateisystem dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Datei", rootVerzeichnis);
                    dSsWalk.add(dS);
                }
            }
        }
    }
    
//    /* Übergabeparameter (Ausgehendes Verzeichnis, absoluter Pfad zum 
//       ausgehenden Verzeichnis) */
//    public void fileSystemReader(String rootVerzeichnis, String path) {
//        /* Das Objekt root beinhaltet den absoluten Pfad des zu durchsuchenden 
//        Verzeichnis */
//        File root = new File(path);
//        /* Das Array list beinhaltet, dank der Methode listFiles, alle Dateien 
//        und Verzeichnisse die im zu durchsuchenden Verzeichnis zu finden waren*/
//        File[] list = root.listFiles();
//        /* Diese if-Anweisung testet, ob das Array list gefüllt ist oder nicht.
//        Wenn nicht wird die Methode fileSystemReader hier beendet, ansonsten
//        läuft sie weiter zur else if-Anweisung */
//        if (list == null) {
//            return;
//        } /* Diese else if-Anweisung überprüft, ob die ArrayList dSsWalk leer ist.
//        Wenn ja, dann wird der Code in der else if-Anweisung fortgeführt,
//        ansonsten läuft die Methode weiter zur else-Anweisung */ else if (dSsWalk.isEmpty()) {
//            /* Ein neues Objekt vom Typ Dateisystem wird mittels eines 
//            Konstruktors erstellt. Es beinhaltet den Namen des zu durchsuchenden
//            Verzeichnis, die Typbezeichnung Verzeichnis und als übergeordneten 
//            Pfad wird null(engl.) als Platzhalter für nichts angegeben. */
//            Dateisystem dS = new Dateisystem(rootVerzeichnis,
//                    "Verzeichnis", null);
//            // Das Objekt wird in die ArrayList dSsWalk gespeichert.
//            dSsWalk.add(dS);
//            // Schleife, die jedes Element aus dem Array list durchläuft
//            for (File list1 : list) {
//                /* Für das aktuelle Element wird der absolute Pfad an dem 
//                Trennzeichen "\" zerlegt und jeder Teil wird ein Element des
//                Arrays pfadsegmente */
//                String[] pfadsegmente = list1.getAbsolutePath().split(
//                        Pattern.quote("\\"));
//                /* In der if-Anweisung wird geprüft, ob es sich bei dem 
//                aktuellen Element um ein Verzeichnis handelt. Wenn ja, wird der
//                nachfolgende Code ausgeführt, ansonsten läuft die Methode weiter
//                zur else-Anweisung */
//                if (list1.isDirectory()) {
//                    /* Es wird ein neues Objekt erstellt und mit den Parametern
//                    Name, Typ, Pfad befüllt */
//                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1],
//                            "Verzeichnis", rootVerzeichnis);
//                    // Das Objekt wird in die ArrayList dSsWalk gepackt
//                    dSsWalk.add(dS);
//                    /* Hier ruft sich die eigentliche Methode erneut selbst auf,
//                    da in dem Verzeichnis ja weitere Dateien oder Verzeichnisse
//                    drin sein können, die erfasst werden müssen */
//                    fileSystemReader(pfadsegmente[pfadsegmente.length - 1],
//                            list1.getAbsolutePath());
//                } /* Ist das aktuelle Element eine Datei wird folgender Code 
//                ausgeführt */ else {
//                    /* Es wird ein neues Objekt erstellt und mit den Parametern
//                    Name, Typ, Pfad befüllt */
//                    dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1],
//                            "Datei", rootVerzeichnis);
//                    // Das Objekt wird in die ArrayList dSsWalk gepackt
//                    dSsWalk.add(dS);
//                }
//            }
//        } /* Die else-Anweisung wird ausgeführt,wenn weder das Array list noch die
//        ArrayList dSsWalk leer sind */ else {
//            // Schleife, die jedes Element aus dem Array list durchläuft
//            for (File list1 : list) {
//                /* Für das aktuelle Element wird der absolute Pfad an dem 
//                Trennzeichen "\" zerlegt und jeder Teil wird ein Element des
//                Arrays pfadsegmente */
//                String[] pfadsegmente = list1.getAbsolutePath().split(
//                        Pattern.quote("\\"));
//                /* In der if-Anweisung wird geprüft, ob es sich bei dem 
//                aktuellen Element um ein Verzeichnis handelt. Wenn ja, wird der
//                nachfolgende Code ausgeführt, ansonsten läuft die Methode weiter
//                zur else-Anweisung */
//                if (list1.isDirectory()) {
//                    /* Es wird ein neues Objekt erstellt und mit den Parametern
//                    Name, Typ, Pfad befüllt */
//                    Dateisystem dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Verzeichnis",
//                            rootVerzeichnis);
//                    // Das Objekt wird in die ArrayList dSsWalk gepackt
//                    dSsWalk.add(dS);
//                    /* Hier ruft sich die eigentliche Methode erneut selbst auf,
//                    da in dem Verzeichnis ja weitere Dateien oder Verzeichnisse
//                    drin sein können, die erfasst werden müssen */
//                    fileSystemReader(pfadsegmente[pfadsegmente.length - 1],
//                            list1.getAbsolutePath());
//                } /* Ist das aktuelle Element eine Datei wird folgender Code 
//                ausgeführt */ else {
//                    /* Es wird ein neues Objekt erstellt und mit den Parametern
//                    Name, Typ, Pfad befüllt */
//                    Dateisystem dS = new Dateisystem(pfadsegmente[pfadsegmente.length - 1], "Datei", rootVerzeichnis);
//                    // Das Objekt wird in die ArrayList dSsWalk gepackt
//                    dSsWalk.add(dS);
//                }
//            }
//        }
//    }
}
