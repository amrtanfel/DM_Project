package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 * Ouvrir un fichier dans l'éditeur par défaut
 */
public class AfficheFichier {
 
    /**
     * Lancer l'executable et ouvrir le fichier
     * @param filename
     */
    public static void lanch(String filename) {
        File file = new File(filename);
        if (!file.exists() && file.length() < 0) {
            System.out.println("Specified file does not exist!");
            System.exit(0);
        }
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(AfficheFichier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    /**
     * Exemple pour une image au format gif et un texte
     * @param args
     */
    
}