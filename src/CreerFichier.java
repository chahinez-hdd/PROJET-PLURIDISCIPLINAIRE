

//*************************************************************************************************************************************************
//ON UTILISE CETTE CLASSE DANS LE CAS OU LE FICHIER NE SE TROUVE PAS DANS LE PACKAGE
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CreerFichier {

   public static void main(String[] args) throws IOException {
        String FilePath="CHEMIN VERS LE FICHIER QUI CONTIENT LA CLASSE QUE NOUS VOULONS RECREER";
        File file = new File(FilePath);
        
        File fileOut = new File(file.getName());
        fileOut.createNewFile();
        CreerClasse(fileOut, file);
    }

     public static void CreerClasse(File fichierDest, File fichierSrc) {
        try (Scanner sc = new Scanner(fichierSrc); PrintWriter pw = new PrintWriter(fichierDest)) {

            while (sc.hasNextLine()) {
                String ligne = sc.nextLine();
                // Traiter chaque ligne du fichier
                pw.println(ligne); 
                pw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }   
}
