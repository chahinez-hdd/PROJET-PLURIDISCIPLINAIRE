import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JTextArea;

public class Complex {

    public static void CADComplexity(String folderPath, JTextArea resultsTextArea) {
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        int complexity = calculateCyclomaticComplexity(file);
                        long fileSizeKB = file.length() / 1024; // Convert bytes to KB
                        resultsTextArea.append("File: " + file.getName() + ", Size: " + fileSizeKB + " KB\n");
                        resultsTextArea.append("Cyclomatic Complexity: " + complexity + "\n");
                        printComplexityInterpretation(complexity, resultsTextArea);
                    }
                }
            }
        }
    }

    private static int calculateCyclomaticComplexity(File file) {
        int complexity = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("if") || line.startsWith("else") ||
                        line.startsWith("for") || line.startsWith("while") ||
                        line.startsWith("case") || line.startsWith("catch") ||
                        line.contains("&&") || line.contains("||")) {
                    complexity++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return complexity;
    }

    private static void printComplexityInterpretation(int complexity, JTextArea resultsTextArea) {
        if (complexity <= 10) {
            resultsTextArea.append("Interpretation: Low complexity.\n\n");
        } else if (complexity <= 20) {
            resultsTextArea.append("Interpretation: Moderate complexity.\n\n");
        } else if (complexity <= 50) {
            resultsTextArea.append("Interpretation: High complexity.\n\n");
        } else {
            resultsTextArea.append("Interpretation: Very high complexity.\n\n");
        }
    }
}