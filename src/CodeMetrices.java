import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CodeMetrices {

    /*============================= Nbr of total lines in Folder =================================== */

    public class NbrLineFolder {

        public static int CLFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalLines = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int linesInFile = CLFile(file.getAbsolutePath());
                            totalLines += linesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalLines;
        }

        public static int CLFile(String filePath) throws IOException {
            int lines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                while (reader.readLine() != null) {
                    lines++;
                }
            }
            return lines;
        }
    }

    /*============================== Nbr of total Codelines in Folder ============================== */

    public class NbrLineCodeFolder {

        public static int CNBLFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalLines = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int linesInFile = CNBLFile(file.getAbsolutePath());
                            totalLines += linesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalLines;
        }

        public static int CNBLFile(String filePath) throws IOException {
            int lines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        lines++;
                    }
                }
            }
            return lines;
        }
    }

    /*=============================== Nbr of Blank-Line in Folder ================================== */

    public class NbrBlankLineFolder {

        public static int CBLFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalBlankLines = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int blankLinesInFile = CBLFile(file.getAbsolutePath());
                            totalBlankLines += blankLinesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalBlankLines;
        }

        public static int CBLFile(String filePath) throws IOException {
            int blankLines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        blankLines++;
                    }
                }
            }
            return blankLines;
        }
    }

    /*=================================Nbr of comment line========================================== */

    public class NbrCommentLineFolder {

        public static int CCLFolder(String folderPath) {
            File folder = new File(folderPath);
            File[] files = folder.listFiles();
            int totalCommentLines = 0;

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {
                        try {
                            int commentLinesInFile = CCLFile(file.getAbsolutePath());
                            totalCommentLines += commentLinesInFile;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                System.out.println("Folder is empty or does not exist.");
            }

            return totalCommentLines;
        }

        public static int CCLFile(String filePath) throws IOException {
            int commentLines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean inMultiLineComment = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    int indexOfSingleLineComment = line.indexOf("//");
                    int indexOfMultiLineComment = line.indexOf("/*");

                    if (indexOfSingleLineComment >= 0 && (indexOfMultiLineComment == -1 || indexOfSingleLineComment < indexOfMultiLineComment)) {
                        commentLines++;
                    } else if (indexOfMultiLineComment >= 0 && (indexOfSingleLineComment == -1 || indexOfMultiLineComment < indexOfSingleLineComment)) {
                        commentLines++;
                        if (line.contains("*/")) {
                            inMultiLineComment = false;
                        } else {
                            inMultiLineComment = true;
                        }
                    } else if (inMultiLineComment) {
                        commentLines++;
                        if (line.contains("*/")) {
                            inMultiLineComment = false;
                        }
                    }
                }
            }
            return commentLines;
        }

    }

    /*======================================Ratio of comment======================================== */

    public class RatioFolder {

        public static double CCCodeRatio(String folderPath) {
            int totalCommentLines = NbrCommentLineFolder.CCLFolder(folderPath);
            int totalNonBlankLines = NbrLineCodeFolder.CNBLFolder(folderPath);

            if (totalNonBlankLines == 0) {
                return 0.0;
            }

            return (double) totalCommentLines / totalNonBlankLines;
        }
    }

}