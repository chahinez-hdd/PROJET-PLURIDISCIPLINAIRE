import java.io.File;
import java.io.IOException;

public class PerformanceMetrics {

    /*=================================== ExecutionTime =================================== */

    public static class ExecutionTime {

        public static double MPETime(String folderPath) throws IOException, InterruptedException {
            long startTime = System.nanoTime();

            File folder = new File(folderPath);
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".java")) {

                        Process compileProcess = Runtime.getRuntime().exec("javac " + file.getAbsolutePath());
                        compileProcess.waitFor();

                        String className = file.getName().substring(0, file.getName().lastIndexOf('.'));
                        Process executionProcess = Runtime.getRuntime().exec("java -cp " + folderPath + " " + className);
                        executionProcess.waitFor();
                    }
                }
            }

            long endTime = System.nanoTime();
            double ETS = (endTime - startTime) / 1_000_000_000.0; // Resault in seconds
            return ETS;
        }
    }

    /*=================================== FolderSize ====================================== */

    public static class FolderSize {

        public static long calculateFolderSize(File folder) {
            long size = 0;
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        size += file.length();
                    } else {
                        size += calculateFolderSize(file);
                    }
                }
            }

            return size;
        }

        public static double convertBytesToMegabytes(long bytes) {
            return bytes / 1024.0;
        }
    }



}