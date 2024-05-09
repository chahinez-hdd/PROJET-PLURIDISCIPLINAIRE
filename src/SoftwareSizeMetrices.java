import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SoftwareSizeMetrices {

    /*====================================== Nbr of Methods ======================================== */

    public class NbrMethodsFolder {

        public static int CMFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalMethods = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int methodsInFile = CMFile(file.getAbsolutePath());
                            totalMethods += methodsInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalMethods;
        }

        public static int CMFile(String filePath) throws IOException {
            int methodCount = 0;
            boolean insideMethod = false;
            boolean insideClass = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.startsWith("public class") || line.startsWith("class") || line.startsWith("public abstract class") || line.startsWith("public interface") || line.startsWith("public static void main")) {
                        continue;
                    }else {
                        if (line.startsWith("public") || line.startsWith("private") || line.startsWith("protected") || line.startsWith("static")) {
                            methodCount++;
                        }
                    }
                }
            }
            return methodCount;
        }
    }
    /*========================================= nbr of class ======================================= */

    public class NbrCalssFolder {

        public static int CClassFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalClasses = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int classesInFile = CClassesFile(file.getAbsolutePath());
                            totalClasses += classesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalClasses;
        }

        public static int CClassesFile(String filePath) throws IOException {
            int classes = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("public class") || line.startsWith("class")) {
                        classes++;
                    }
                }
            }
            return classes;
        }
    }

    /*====================================== Nbr of child class ==================================== */

    public class NbrChildClassFolder {

        public static int CChildCFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalChildClasses = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int childClassesInFile = CChildCFile(file.getAbsolutePath());
                            totalChildClasses += childClassesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalChildClasses;
        }

        public static int CChildCFile(String filePath) throws IOException {
            int childClasses = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.contains("extends")) {
                        childClasses++;
                    }
                }
            }
            return childClasses;
        }
    }

    /*===================================== Nbr of Interface ======================================= */

    public class NbrInterfaceFolder {

        public static int CIFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalInterfaces = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int interfacesInFile = CIFile(file.getAbsolutePath());
                            totalInterfaces += interfacesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalInterfaces;
        }

        public static int CIFile(String filePath) throws IOException {
            int interfaces = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("public interface") || line.startsWith("interface")) {
                        interfaces++;
                    }
                }
            }
            return interfaces;
        }
    }

    /*======================================== Nbr of Abstract ===================================== */

    public class NbrAbstractClassFolder {

        public static int CACFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalAbstractClasses = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int abstractClassesInFile = CACFile(file.getAbsolutePath());
                            totalAbstractClasses += abstractClassesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalAbstractClasses;
        }

        public static int CACFile(String filePath) throws IOException {
            int abstractClasses = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.startsWith("public abstract class") || line.startsWith("abstract class")) {
                        abstractClasses++;
                    }
                }
            }
            return abstractClasses;
        }
    }


}
