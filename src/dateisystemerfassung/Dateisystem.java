package dateisystemerfassung;

import java.io.File;

/**
 * @author Rainer 22.06.2016
 */
public class Dateisystem {

    // Variablen
    private int id;
    private String name;
    private String typ;
    private String pfad;

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
        return "Dateisystem{" + "id=" + id + ", name=" + name + ", typ=" + typ + ", pfad=" + pfad + '}';
    }

    public void walk(String path) {

        File root = new File(path);
        File[] list = root.listFiles();

        if (list == null) {
            return;
        }

        for (File f : list) {
            if (f.isDirectory()) {
                walk(f.getAbsolutePath());
                System.out.println("Dir:" + f.getAbsoluteFile());
            } else {
                System.out.println("File:" + f.getAbsoluteFile());
            }
        }
    }
}
