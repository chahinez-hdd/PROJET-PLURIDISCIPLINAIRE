package application.BackEnd;
import javax.tools.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class ClasspathResolver {
    public static void main(String[] args) {
        String javaProjectPath = "C:\\Users\\DELL\\eclipse-workspace\\depression\\src";
        createscriptfile(javaProjectPath);
        String output = executeScript(javaProjectPath);
        if (output != null) {
            System.out.println(output);
        } else {
            System.out.println("Error executing script.");
        }
    }

    public static void createscriptfile(String ProjectPath) {
        String FilePath = ProjectPath + "/SystemClassPathProperties.java";
        String ScriptContent = "public class SystemClassPathProperties{\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(System.getProperty(\"java.class.path\"));\n" +
                "    }\n" +
                "}\n";
        try {
            File file = new File(FilePath);
            FileWriter writer = new FileWriter(file);
            writer.write(ScriptContent);
            writer.close();
            System.out.println("File created successfully.");

        } catch (IOException e) {
            System.out.println("Can't create file.");
        }
    }

    public static String executeScript(String ProjectPath) {
        String FileJavaPath = ProjectPath + "\\SystemClassPathProperties.java";
        String FileClassPath = ProjectPath.replace("src", "bin");
        try {
            // Compile the Java file
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(FileJavaPath));
            compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();
            fileManager.close();

            // Load and execute the compiled class
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(FileClassPath).toURI().toURL()});
            Class<?> cls = Class.forName("SystemClassPathProperties", true, classLoader);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            System.setOut(ps);
            cls.getMethod("main", String[].class).invoke(null, (Object) null);
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
            return baos.toString();
        } catch (Exception e) {
            System.out.println("Error executing script: " + e.getMessage());
            return null;
        }
    }
}

