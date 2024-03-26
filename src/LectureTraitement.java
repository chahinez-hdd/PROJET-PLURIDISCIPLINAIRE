import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LectureTraitement {
    public static void lireFichiers(File repertoire) {
        for (File fichier : repertoire.listFiles()) {
            if (fichier.isFile()) {
                System.out.println("Fichier: " + fichier.getName());
                // Vérifier l'extension du fichier
                if (fichier.getName().endsWith(".txt")) {
                    lireContenuFichier(fichier);
                }
            } else if (fichier.isDirectory()) {
                System.out.println("Répertoire: " + fichier.getName());
                lireFichiers(fichier); // Appel récursif pour lire les fichiers dans le sous-répertoire
            }
        }
    }

    public static void lireContenuFichier(File fichier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                // Traiter chaque ligne du fichier
                System.out.println(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File repertoire = new File("C://Users/milen/OneDrive/Desktop/divertissement");
        lireFichiers(repertoire);
    }
}


