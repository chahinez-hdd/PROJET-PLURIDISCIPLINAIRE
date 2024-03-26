import java.io.File;

public class LireProjet {
    public static void lireFichiers(File repertoire) {
        for (File fichier : repertoire.listFiles()) {
            
            if (fichier.isFile()) {
                System.out.println("Fichier: " + fichier.getName());
            } else if (fichier.isDirectory()) {
                System.out.println("Répertoire: " + fichier.getName());
                lireFichiers(fichier); // Appel récursif pour lire les fichiers dans le sous-répertoire
            }
        }
    }

    public static void main(String[] args) {
        File repertoire = new File("C://Users/milen/OneDrive/Desktop/divertissement");
        lireFichiers(repertoire);
    }
}


