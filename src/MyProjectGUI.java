import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MyProjectGUI extends JFrame implements ActionListener {

    private JTextField folderPathTextField;
    private JButton selectFolderButton;
    private JButton analyzeButton;
    private JTextArea resultsTextArea;

    public MyProjectGUI() {

        // Set frame properties
        setTitle("My Project");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);// Set background color to black

        // Create and add components
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBorder(new LineBorder(Color.CYAN, 2));
        folderPathTextField = new JTextField(25);
        selectFolderButton = new JButton("Select Folder");
        analyzeButton = new JButton("Analyze");
        topPanel.add(folderPathTextField);
        topPanel.add(selectFolderButton);
        topPanel.add(analyzeButton);
        topPanel.setBackground(Color.BLACK); // Set background color to black
        add(topPanel, BorderLayout.NORTH);

        resultsTextArea = new JTextArea(20, 40);
        resultsTextArea.setBorder(new LineBorder(Color.CYAN, 2));
        resultsTextArea.setEditable(false);
        resultsTextArea.setBackground(Color.BLACK); // Set background color to black
        resultsTextArea.setForeground(Color.CYAN); // Set text color to cyan
        resultsTextArea.setFont(new Font("Monospaced", Font.ITALIC, 16));// Set font
        add(new JScrollPane(resultsTextArea), BorderLayout.CENTER);

        // Add action listeners
        selectFolderButton.addActionListener(this);
        analyzeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectFolderButton) {
            // Implement folder selection
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFolder = fileChooser.getSelectedFile();
                folderPathTextField.setText(selectedFolder.getAbsolutePath());
            }
        } else if (e.getSource() == analyzeButton) {
            // Implement the analysis
            String folderPath = folderPathTextField.getText();
            File folder = new File(folderPath);

            if (folder.exists() && folder.isDirectory()) {
                resultsTextArea.setText(""); // Clear previous results
                analyzeFolder(folder);
            } else {
                resultsTextArea.setText("Folder does not exist or is not a directory.");
            }
        }
    }

    private void analyzeFolder(File folder) {

        //Call methods from my existing classes to perform the analyze

        /*============================= Nbr of total lines in Folder =============================== */

        int totalLines = CodeMetrices.NbrLineFolder.CLFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of lines in folder: " + totalLines + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*===================== Nbr of total Code lines (Non-Blank) in Folder ====================== */

        int totalLines1 = CodeMetrices.NbrLineCodeFolder.CNBLFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of non-blank lines in folder: " + totalLines1 + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*============================= Nbr of Blank-Line in Folder ================================ */

        int totalBlankLines = CodeMetrices.NbrBlankLineFolder.CBLFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of blank lines in folder: " + totalBlankLines + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*=================================Nbr of Comment line====================================== */

        int totalCommentLines = CodeMetrices.NbrCommentLineFolder.CCLFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of comment lines in folder: " + totalCommentLines + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*================================Ratio of comment/Line==================================== */

        double ratio = CodeMetrices.RatioFolder.CCCodeRatio(folder.getAbsolutePath());
        String fRatio = String.format("%.2f", ratio * 100);
        String GRatio = String.format("%.2f", 100-(ratio * 100));
        resultsTextArea.append("Code Lines to code ratio in folder: " + GRatio + "%\n");
        resultsTextArea.append("Comment to code ratio in folder: " + fRatio + "%\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*=====================================Nbr of Methods====================================== */

        int totalFunctions = SoftwareSizeMetrices.NbrMethodsFolder.CMFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of methods in folder: " + totalFunctions + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*===================================== nbr of class ====================================== */

        int totalClasses = SoftwareSizeMetrices.NbrCalssFolder.CClassFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of classes in folder: " + totalClasses + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*================================== Nbr of child class =================================== */

        int totalChildClasses = SoftwareSizeMetrices.NbrChildClassFolder.CChildCFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of child classes in folder: " + totalChildClasses + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*===================================== Nbr of Interface ================================== */

        int totalInterfaces = SoftwareSizeMetrices.NbrInterfaceFolder.CIFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of interfaces in folder: " + totalInterfaces + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*===================================== Nbr of Abstract =================================== */

        int totalAbstractClasses = SoftwareSizeMetrices.NbrAbstractClassFolder.CACFolder(folder.getAbsolutePath());
        resultsTextArea.append("Total number of abstract classes in folder: " + totalAbstractClasses + "\n");

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*===================================== FolderSize ======================================== */

        if (folder.exists() && folder.isDirectory()) {
            long folderSizeBytes = PerformanceMetrics.FolderSize.calculateFolderSize(folder);
            double folderSizeMB = PerformanceMetrics.FolderSize.convertBytesToMegabytes(folderSizeBytes);

            resultsTextArea.append("Size of folder: " + folderSizeMB + " KB\n");
        } else {
            resultsTextArea.append("Folder does not exist or is not a directory.\n");
        }

        resultsTextArea.append("-----------------------------------------------------");
        resultsTextArea.append("\n");

        /*============================= Display All Classes with size and coplex ===================*/

        Complex.CADComplexity(folder.getAbsolutePath(), resultsTextArea);

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyProjectGUI gui = new MyProjectGUI();
            gui.setVisible(true);
            // gui.setColors();
        });
    }
}