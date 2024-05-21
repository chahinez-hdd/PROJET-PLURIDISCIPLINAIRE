import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class NbCalculator {
    int TotalAbstractMethods;
    ArrayList<String> nameAbstractMethods;
    double ratio,ratioprc;

    public static NbCalculator fetchNbAbstractClasses(String FilePath,String filename) throws IOException {
        NbCalculator nb = new NbCalculator();
       
        try {
            analyzeDataFlow(filename);
            DataFlowVisitor visitor = analyzeDataFlow(filename);
            nb.nameAbstractMethods=visitor.abstractMethodsName;
            nb.ratio=visitor.printReport();
            nb.ratioprc=nb.ratio*100;
            nb.TotalAbstractMethods=nb.nameAbstractMethods.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //DataFlowVisitor visitor = analyzeDataFlow(filename);
        // nb.nameAbstractMethods=visitor.abstractMethodsName;
        // nb.ratio=visitor.printReport();
        // nb.ratioprc=nb.ratio*100;
        // nb.TotalAbstractMethods=nb.nameAbstractMethods.size();
        return nb;

    }


    private static DataFlowVisitor analyzeDataFlow(String filePath) throws IOException {
        FileInputStream in = new FileInputStream(filePath);
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(in).getResult().orElseThrow(() -> new RuntimeException("Parsing failed"));

        DataFlowVisitor visitor = new DataFlowVisitor();
        visitor.visit(cu, null);

        in.close();

        visitor.printReport();
        return visitor;
    }

    private static class DataFlowVisitor extends VoidVisitorAdapter<Void> {
        private ArrayList<String> abstractMethodsName = new ArrayList<>();
        private int cpt=0;
        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            //boolean abstractClass = false;
            if(n.getBody().isPresent()) cpt++;
            
            if(n.getBody().isEmpty()){
                String callee = n.getNameAsString();
                abstractMethodsName.add(callee);
                cpt++;
            }
        }

        public double printReport() {
            
            double Ratio = (double)abstractMethodsName.size()/cpt;
            System.out.println("Total abstract methods: " + abstractMethodsName.size());
            abstractMethodsName.forEach((classes) -> System.out.println("class: " + classes ));
            System.out.println("le ratio des methodes abstraites sur les methodes totales est :" + Ratio);
            System.out.println("le ratio des methodes abstraites sur les methodes totales en % est :" + Ratio*100+"%");
            return Ratio;
        }
    }
    
}
