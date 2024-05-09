import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextArea;


public class DisplayContent {

    /*================================== Display All Classes ======================================= */

    public static class DisplayCalsses {

        public static void listClassFiles(String folderPath) {
            File folder = new File(folderPath);

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".class")) {
                            String className = file.getName().substring(0, file.getName().lastIndexOf('.'));
                            long fileSizeKB = file.length() / 1024; // Convert bytes to kilobytes
                            System.out.println("Class file: " + className + ", Size: " + fileSizeKB + " KB");
                        }
                    }
                } else {
                    System.out.println("Folder is empty or does not exist.");
                }
            } else {
                System.out.println("Invalid folder path.");
            }
        }

        public static void listClassFiles(String folderPath, JTextArea resultsTextArea) {
            File folder = new File(folderPath);

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".java")) {
                            // Check if the file contains a class (exclude interfaces)
                            if (containsClassDefinition(file)) {
                                String className = file.getName().substring(0, file.getName().lastIndexOf('.'));
                                long fileSizeKB = file.length() / 1024; // Convert bytes to kilobytes
                                resultsTextArea.append("Class: " + className + ", Size: " + fileSizeKB + " KB\n");
                            }
                        }
                    }
                } else {
                    resultsTextArea.append("Folder is empty or does not exist.\n");
                }
            } else {
                resultsTextArea.append("Invalid folder path.\n");
            }
        }

        // Helper method to check if a Java file contains a class (excluding interfaces)
        private static boolean containsClassDefinition(File file) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.contains("class ") && !line.contains("interface ")) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /*=========================== Nbr of @override/@overload is in folder ========================== */

    public static class OVRLFolder {

        public static int findAnnotations(String folderPath, String annotation) {
            File folder = new File(folderPath);
            int nbr = 0;

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".java")) {
                            nbr = nbr + findAnnotationsInFile(file, annotation);
                        }
                    }
                }
            }
            return nbr;
        }

        public static int findAnnotationsInFile(File file, String annotation) {
            int nbr = 0;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                int lineNumber = 0;
                while ((line = br.readLine()) != null) {
                    lineNumber++;
                    if (line.contains(annotation)) {
                        System.out.println("File: " + file.getName() + ", Line: " + lineNumber);
                        nbr++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return nbr;
        }
    }



}