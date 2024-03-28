
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

    public class CollaborationDesClasses  {
    
    
        public static void nombreInstancesClasses (File repertory, File file) {
            
             for (File fichier : repertory.listFiles()) {
                try (Scanner scFile = new Scanner(file); Scanner scRepertory = new Scanner(fichier)) {
               
                    int cpt = 0;
                    if (fichier.isFile()) {
                        scFile.nextLine();
                        String nameClass = fichier.getName();
                        nameClass = nameClass.replace(".java", ""); 
                        while (scFile.hasNextLine()) {
                            if (scFile.nextLine().contains(nameClass)) cpt++;
                        }
                        System.out.println("La classe: " + nameClass + " a été instanciée: " + cpt + " fois dans votre fichier " + file.getName());
        
                    } else if (fichier.isDirectory()) {
                        System.out.println("Répertoire: " + fichier.getName());
                        nombreInstancesClasses(fichier, file); 
                    }
                
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }


    public static void main(String[] args) throws  FileNotFoundException {
        String FilePtah="CHEMIN VERS LE FICHIER QUE NOUS SOUHAITONS ETUDIER";
        File file = new File(FilePtah);
        
        
        String RepertoryPath ="CHEMIN VERS LE REP QUI CONTIENT LES CLASSES DONT NOUS SOUHAITONS SAVOIR LE NB D'INSTANCIATIONS";
        File repertory = new File(RepertoryPath);
        nombreInstancesClasses(repertory,file);
        
    }
}
