import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
    
public class Compteur {

    public static void lireFichiers(File repertoire) {
        for (File fichier : repertoire.listFiles()) {
            if (fichier.isFile()) {
                System.out.println("Fichier: " + fichier.getName());
                // Vérifier l'extension du fichier
                if (fichier.getName().endsWith(".java")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                        String ligne;
                        int compteurVariables = 0;
                        int compteurPublic = 0;
                        int compteurPrivate = 0;
                        int compteurProtected = 0;
                
                        while ((ligne = reader.readLine()) != null) {
                            // Compter les variables
                            if (ligne.matches(".*\\s+\\w+\\s+\\w+\\s*;.*")) {
                                compteurVariables++;
                            }
                            // Compter les éléments public
                            if (ligne.contains("public")) {
                                    compteurPublic++;
                                }
                            // Compter les éléments private
                            if (ligne.contains("private")) {
                                    compteurPrivate++;
                            }
                            // Compter les éléments protected
                            if (ligne.contains("protected")) {
                                compteurProtected++;
                            }
                        }
                
                        System.out.println("Nombre de variables : " + compteurVariables);
                        System.out.println("Nombre d'éléments public : " + compteurPublic);
                        System.out.println("Nombre d'éléments private : " + compteurPrivate);
                        System.out.println("Nombre d'éléments protected : " + compteurProtected);
                
                    } catch (IOException e) {
                        e.printStackTrace();
                        }
                }
            }
            else if (fichier.isDirectory()) {
                System.out.println("Répertoire: " + fichier.getName());
                lireFichiers(fichier); // Appel récursif pour lire les fichiers dans le sous-répertoire
            }
        }
    }
    public static void main(String[] args) {
        File fichier = new File("C://Users/milen/OneDrive/Desktop/ProjetMetriques/src/LectureTraitement.java");
        lireFichiers(fichier);
    }
}    

