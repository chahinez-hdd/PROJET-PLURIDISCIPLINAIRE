import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import testMVC.*;

public class MVCinfos{
     public static void main(String[] args) throws IOException {
        List<Class<?>> classes = Arrays.asList(Model.class, View.class, Controller.class);
        Map<String, Set<String>> couplings = new HashMap<>();

        for (Class<?> cls : classes) {
            String layer = getLayer(cls);
            Set<String> dependencies = analyzeDependencies(cls);
            couplings.putIfAbsent(layer, new HashSet<>());
            couplings.get(layer).addAll(dependencies);
        }

        printCouplings(couplings);

        DisplayMethods(Model.class);
        DisplayMethods(View.class);
        DisplayMethods(Controller.class);

        try {
            analyzeDataFlow("C:\\Users\\user\\Documents\\vscode\\JAVA\\testsMetrics\\SecurityMetric\\src\\testMVC\\Controller.java");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        
    }

    private static String getLayer(Class<?> cls) {
        if (Model.class.isAssignableFrom(cls)) {
            return "MODEL";
        } else if (View.class.isAssignableFrom(cls)) {
            return "VIEW";
        } else if (Controller.class.isAssignableFrom(cls)) {
            return "CONTROLLER";
        }
        return "UNKNOWN";
    }

    private static Set<String> analyzeDependencies(Class<?> cls) {
        Set<String> dependencies = new HashSet<>();
        Field[] fields = cls.getDeclaredFields();

        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            String dependencyLayer = getLayer(fieldType);
            if (!dependencyLayer.equals("UNKNOWN")) {
                dependencies.add(dependencyLayer);
            }
        }

        return dependencies;
    }

    private static void printCouplings(Map<String, Set<String>> couplings) {
        for (Map.Entry<String, Set<String>> entry : couplings.entrySet()) {
            System.out.println(entry.getKey() + " depends on: " + entry.getValue());
        }
    }




    private static void analyzeDataFlow(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(filePath);
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(in).getResult().orElseThrow(() -> new RuntimeException("Parsing failed"));

        DataFlowVisitor visitor = new DataFlowVisitor();
        visitor.visit(cu, null);

        in.close();

        visitor.printReport();
    }

    private static class DataFlowVisitor extends VoidVisitorAdapter<Void> {
        private Map<String, Integer> dataFlowCount = new HashMap<>();

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);

            n.getBody().ifPresent(body -> body.findAll(MethodCallExpr.class).forEach(call -> {
                String callee = call.getNameAsString();
                dataFlowCount.put(callee, dataFlowCount.getOrDefault(callee, 0) + 1);
            }));
        }

        public void printReport() {
            System.out.println("Data Flow Complexity Report of the class Controller:");
            dataFlowCount.forEach((method, count) -> System.out.println("Method " + method + " is called " + count + " times."));
            System.out.println("Total complexity: " + dataFlowCount.values().stream().mapToInt(Integer::intValue).sum());
        }
    }

    private static void DisplayMethods(Class<?> cl){
        Method[] methods = cl.getDeclaredMethods();
        System.out.println("mothods of the class: "+ cl.getName() +" Are: ");
        for(Method method : methods){
            System.out.println("  "+method.getName());
        }
    }
}