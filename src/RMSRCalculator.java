
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import TESTS.test2;



public class RMSRCalculator {
    public static void main(String[] args) throws FileNotFoundException  {

        Class<?> clazz = test2.class; //ou vous allez tout simplement remplacer MyClass par le nom de la classe dont vous avez cree le fichier 
        //ou une classe que vous possedez deja dans votre projet
        
        int totalMethods = countTotalMethods(clazz);
        int overloadedMethods = countOverloadedMethods(clazz);
        int overrideMethods = countOverrideMethods(clazz);
        
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
                //System.out.println(method.getName()+"\n");
            }
            i++;
        }
        return overloadedCount;
    }

    public static boolean isOverloaded(Method method, Class<?> clazz, int i) {
        // int totalMethods = countTotalMethods(clazz);
        Method[] methods = clazz.getDeclaredMethods();
        Boolean overload=false;
        int j=0;
        for (Method method2 : methods){
            if(method2.getName()==method.getName()&& (j!=i) && compareMethodParameters(method, method2)==false && compareMethodType(method, method2)==true)//parametres differents
                overload=true;
            j++;
        }
        return overload;
    }

    public static int countOverrideMethods (Class<?> clazz) throws FileNotFoundException{
        Method[] methodsClass = clazz.getDeclaredMethods();
        int overridedCount=0;

        for (Method method : methodsClass) {
            if (isOverride(method, clazz)) {
                overridedCount++;
                //System.out.println(method.getName()+"\n");
            }
        }
        return overridedCount;
        
    }
    
    public static Boolean isOverride(Method method,Class<?> clazz) {
    // Obtenez la classe de la classe dérivée (la classe actuelle)
    //TRAITEMENT DE L'OVERRIDE DANS L'HERITAGE
        Class<?> classesSup=clazz;
        classesSup= classesSup.getSuperclass();
        while (classesSup != null) {
            Method[] methodsSuperClass = classesSup.getDeclaredMethods();
            for (Method method2 : methodsSuperClass) {
                if(method.getName()==method2.getName() && compareMethodParameters(method, method2)==true && compareMethodType(method, method2)==true)
                   return true;
                
            }
            classesSup= classesSup.getSuperclass();
        }


    //TRAITEMENT DE  L'OVERRIDE DANS LES INTERFACES
        Class<?>[]interfacesTab = clazz.getInterfaces();
            for(Class<?> Classinterface : interfacesTab){
                for(Method method2 : Classinterface.getDeclaredMethods()){
                    if(method.getName()==method2.getName() && compareMethodParameters(method, method2)==true && compareMethodType(method, method2)==true)
                       return true;
                }
            }
        return false;
    }
    
    public static boolean compareMethodParameters(Method method1, Method method2) {
        // Comparez les types de paramètres
        Class<?>[] paramTypes1 = method1.getParameterTypes();
        Class<?>[] paramTypes2 = method2.getParameterTypes();
        if (!Arrays.equals(paramTypes1, paramTypes2)) {
            return false;
        }
        //Si les types de paramètres 
        return true;
    }
    public static boolean compareMethodType(Method method1, Method method2) {
        // Comparez le type de retour
        Class<?> returnType1 = method1.getReturnType();
        Class<?> returnType2 = method2.getReturnType();
        if (!returnType1.equals(returnType2)) {
            return false;
        }
        // Si les types de retour sont identiques
        return true;
    }

}