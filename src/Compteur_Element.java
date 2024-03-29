import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
    
public class Compteur_Element {

    public static void lireFichiers(File repertoire) {
        if (repertoire.exists() && repertoire.isDirectory()) {
            for (File fichier : repertoire.listFiles()) {
                if (fichier.isFile() && fichier.getName().endsWith(".java")) {
                    System.out.println("Fichier: " + fichier.getName());
                    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                        String ligne;
                        int compteurPublic = 0;
                        int compteurPrivate = 0;
                        int compteurProtected = 0;
                        int totalMembres = 0;
        
                        while ((ligne = reader.readLine()) != null) {
                            // Compter les éléments public
                            if (ligne.contains("public")) {
                                compteurPublic++;
                                totalMembres++;
                            }
                            // Compter les éléments private
                            if (ligne.contains("private")) {
                                compteurPrivate++;
                                totalMembres++;
                            }
                            // Compter les éléments protected
                            if (ligne.contains("protected")) {
                                compteurProtected++;
                                totalMembres++;
                            }                            
                        }
        
                        System.out.println("Nombre d'éléments public : " + compteurPublic);
                        System.out.println("Nombre d'éléments private : " + compteurPrivate);
                        System.out.println("Nombre d'éléments protected : " + compteurProtected);
                        System.out.println("Total des membres : " + totalMembres);
                        double tauxEncapsulation = (double) (compteurPrivate + compteurProtected)*100 / totalMembres;
                        System.out.println("Taux d'encapsulation : " + tauxEncapsulation+"%");
        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            
                } else if (fichier.isDirectory()) {
                    System.out.println("Répertoire: " + fichier.getName());
                    lireFichiers(fichier); // Appel récursif pour lire les fichiers dans le sous-répertoire
                }
            }

        } else {
            System.out.println("Le chemin spécifié n'est pas un répertoire valide.");
        }
    }
        
    public static void main(String[] args) {
        File repertoire = new File("C://Users/milen/OneDrive/Desktop/ProjetMetriques/src");
        lireFichiers(repertoire);
    }
}
    
