import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import TESTS.test2;

public class CohesionMetric {

    public static double calculateCohesion(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        Method[] methods = clazz.getDeclaredMethods();
        
        // Ignore static fields and methods
        Set<Field> instanceFields = new HashSet<>();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                instanceFields.add(field);
            }
        }

        Set<Set<Field>> methodFieldSets = new HashSet<>();
        
        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                Set<Field> accessedFields = new HashSet<>();
                
                // Check which fields are used in this method
                for (Field field : instanceFields) {
                    if (isFieldUsedInMethod(clazz, field, method)) {
                        accessedFields.add(field);
                    }
                }
                
                if (!accessedFields.isEmpty()) {
                    methodFieldSets.add(accessedFields);
                }
            }
        }
        
        // Calculate cohesion metric
        int pairsWithSharedFields = 0;
        int totalPairs = 0;
        
        Set<Method> methodSet = new HashSet<>();
        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers())) {
                methodSet.add(method);
            }
        }

        for (Method method1 : methodSet) {
            for (Method method2 : methodSet) {
                if (!method1.equals(method2)) {
                    totalPairs++;
                    if (shareFields(clazz, method1, method2, instanceFields)) {
                        pairsWithSharedFields++;
                    }
                }
            }
        }
        
        return totalPairs == 0 ? 1 : (double) pairsWithSharedFields / totalPairs;
    }

    private static boolean isFieldUsedInMethod(Class<?> clazz, Field field, Method method) {
        // This is a placeholder for actual bytecode analysis
        // For simplicity, we assume a method uses a field if the field name is found in the method's code
        // In a real implementation, you would use a library like ASM or BCEL to analyze the bytecode
        String methodName = method.toString();
        String fieldName = field.getName();
        return methodName.contains(fieldName);
    }

    private static boolean shareFields(Class<?> clazz, Method method1, Method method2, Set<Field> fields) {
        Set<Field> method1Fields = new HashSet<>();
        Set<Field> method2Fields = new HashSet<>();
        
        for (Field field : fields) {
            if (isFieldUsedInMethod(clazz, field, method1)) {
                method1Fields.add(field);
            }
            if (isFieldUsedInMethod(clazz, field, method2)) {
                method2Fields.add(field);
            }
        }
        
        method1Fields.retainAll(method2Fields);
        return !method1Fields.isEmpty();
    }

    public static void main(String[] args) {
        // Example usage
        double cohesion = calculateCohesion(test2.class);
        System.out.println("Cohesion: " + cohesion);
    }
}
