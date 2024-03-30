
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;



public class RMSRCalculator {
    public static void main(String[] args) throws FileNotFoundException  {
        String FilePath ="CHEMIN VERS LE FIHCIER QUI CONTIENT LA CLASSE ";
        File file = new File(FilePath);


        Class<?> clazz = MyClass.class; //ou vous allez tout simplement remplacer MyClass par le nom de la classe dont vous avez cree le fichier 
        //ou une classe que vous possedez deja dans votre projet
        
        int totalMethods = countTotalMethods(clazz);
        int overloadedMethods = countOverloadedMethods(clazz);
        int overrideMethods = countOverrideMethods(file);
        
        double RatioMethodsSur = (double) overloadedMethods / totalMethods;
        double RatioMethodsRedef = (double) overrideMethods / totalMethods;
        double rsmr = (double) (overrideMethods+overrideMethods) / totalMethods;

        System.out.println("Nombre total des methodes: " + totalMethods);
        System.out.println("Nombre des methodes redefinies: " + overloadedMethods);
        System.out.println("Nombre des methodes surchergees: " + overrideMethods);
        
        System.out.println("\nRatio de Méthodes Surchargees: " + RatioMethodsSur);
        System.out.println("le taux des Méthodes Surchargees(%): " + RatioMethodsSur*100);
        
        System.out.println("\nRatio de Méthodes Redefinies: " + RatioMethodsRedef);
        System.out.println("le taux des Méthodes Redefinies(%): " + RatioMethodsRedef*100);
       
        System.out.println("\n\nRatio de Méthodes Redefinies & surchargees: " + rsmr);
        System.out.println("le taux des Méthodes Redefinies(%)& surchargees: " + rsmr*100);
        
        //il reste le calcule des methodes surchargees(override), il me faut une idee
    }

    public static int countTotalMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        return methods.length;
    }

    public static int countOverloadedMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        int overloadedCount = 0;
        int i=0;
        for (Method method : methods) {

            if (isOverloaded(method, clazz,i)) {
                overloadedCount++;
            }
            i++;
        }
        return overloadedCount;
    }

    public static boolean isOverloaded(Method method, Class<?> clazz, int i) {
        // int totalMethods = countTotalMethods(clazz);
        Method[] methods = clazz.getDeclaredMethods();
        Boolean overloaed=false;
        int j=0;
        for (Method method2 : methods){
            if(method2.getName()==method.getName()&& (j!=i) )//parametres differents
                overloaed=true;
            j++;
        }
        return overloaed;
    }

    public static int countOverrideMethods (File file) throws FileNotFoundException{
        Scanner sc = new Scanner(file);
        int cpt=0;
        while(sc.hasNextLine()){
            if(sc.nextLine().contains("@Override")) cpt++;
        }
        return cpt;
    }



  
}

