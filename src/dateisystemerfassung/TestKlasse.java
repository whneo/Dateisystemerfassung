package dateisystemerfassung;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Rainer 22.06.2016
 */
public class TestKlasse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Dateisystem dS = new Dateisystem();
        
        //Eingabetest
//        String usereingabe = "C:\\hjp7";
//        String[] pfadsegmente = usereingabe.split(Pattern.quote("\\"));
//        dS.walk(pfadsegmente[pfadsegmente.length - 1], usereingabe);
//        dS.delete();
//        dS.create();
//        dS.insert();
        
        //Ausgabetest
        String userabfrage = "tools";
        dS.getAllByName(userabfrage);
        System.out.println(dS.dSsGet.toString());
        
//        int v1 = 1;
//        int v2 = 2;
//        int v3 = 3;
//        
//        for (int i = 0; i <= 3; i++) {
//            String string = "v";
//            String erhöhung = string + i;
//            System.out.println(erhöhung.);
//        }

//for (Dateisystem x : Dateisystem.getAllByName(userabfrage)){
//            System.out.println(x);
//            }
      
//        for (int i = 0; i < Dateisystem.dSsWalk.size(); i++) {
//            System.out.println("Element " + (i + 1) + " ArrayIndex " + i + " " + Dateisystem.dSsWalk.get(i));
//        }
//    public void walk(String path) {
//
//        ArrayList<Dateisystem> dSs = new ArrayList<>();
//        File root = new File(path);
//        File[] list = root.listFiles();
//
//        if (list == null) {
//            return;
//        }
//
//        for (File f : list) {
//            Dateisystem dS = new Dateisystem();
//            String pfad = f.getAbsolutePath();
//            String[] pfadsegmente = pfad.split( Pattern.quote( "\\" ) );
//            
//            if (f.isDirectory()) {
//                walk(f.getAbsolutePath());
////                String pfad = f.getAbsolutePath();
////                String[] pfadsegmente = pfad.split( Pattern.quote( "\\" ) );
////                System.out.println(pfadsegmente[pfadsegmente.length-1]);
//                //System.out.println("Dir:" + f.getAbsoluteFile());
//            } else {
////                String pfad = f.getAbsolutePath();
////                String[] pfadsegmente = pfad.split( Pattern.quote( "\\" ) );
////                System.out.println(pfadsegmente[pfadsegmente.length-1]);                
//                //System.out.println("File:" + f.getAbsoluteFile());
//            }
//            System.out.println(pfadsegmente[pfadsegmente.length-1]);
//        }
//    }
    }
}

//String path = "www.tutego.com";
//String[] segs = path.split( Pattern.quote( "." ) );
//System.out.println( Arrays.toString(segs) );
