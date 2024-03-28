import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String FilePtah="chemin vers le fichier";
        File file = new File(FilePtah);
        Scanner sc = new Scanner(file);

        int cpt1=0,cpt2=0;
        String word;
        while(sc.hasNext()){
            word = sc.next();
            if(word=="private"||word=="protected")  cpt1++;
            if(word=="int"||word=="double"||word=="bool"||word=="private"||word=="protected"||word=="private"||word=="protected"){}
        }
    }
}
