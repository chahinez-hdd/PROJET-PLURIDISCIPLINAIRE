import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

    public class App  {
    
    
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
        String FilePtah="C:\\Users\\user\\Documents\\vscode\\JAVA\\testsMetrics\\SecurityMetric\\src\\src2\\test2.java";
        File file = new File(FilePtah);
        
        
        String RepertoryPath ="C:\\Users\\user\\Documents\\vscode\\JAVA\\testsMetrics\\SecurityMetric\\src\\src2";
        File repertory = new File(RepertoryPath);
        nombreInstancesClasses(repertory,file);
        
    }
}
