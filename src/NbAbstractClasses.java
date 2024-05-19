import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class NbAbstractClasses {

    public static void main(String[] args) throws IOException {
        try {
            analyzeDataFlow("C:\\Users\\user\\Documents\\vscode\\JAVA\\testsMetrics\\SecurityMetric\\src\\testAbstractClass\\AbstractClass.java");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        private ArrayList<String> abstractClassesName = new ArrayList<>();
        private int cpt=0;
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            //boolean abstractClass = false;
            if(n.getBody().isPresent()) cpt++;
            
            if(n.getBody().isEmpty()){
                String callee = n.getNameAsString();
                abstractClassesName.add(callee);
                cpt++;
            }
        }

        public void printReport() {
            
            double Ratio = (double)abstractClassesName.size()/cpt;
            System.out.println("Total abstract methods: " + abstractClassesName.size());
            abstractClassesName.forEach((classes) -> System.out.println("class: " + classes ));
            System.out.println("le ratio des methodes abstraites sur les methodes totales est :" + Ratio);
            System.out.println("le ratio des methodes abstraites sur les methodes totales en % est :" + Ratio*100);
        }
    }
    
}
