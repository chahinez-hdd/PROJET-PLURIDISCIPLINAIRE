import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
 
    
public class Button {
	
	
    static int compteurJInternalFrame = 0;
    static int compteurJComboBox = 0;
    static int compteurJCheckBox = 0;
    static int compteurJList = 0;
    static int compteurButton = 0;
    static int compteurJMenu = 0;
    static int compteurJRadioButton = 0;
    static int compteurJSlider = 0;
    static int compteurJSpinner = 0;
    static int compteurJTextField = 0;
    static int compteurJPasswordField = 0;
    static int compteurJColorChooser = 0;
    static int compteurJEditorPane = 0;
    static int compteurJTextPane = 0;
    static int compteurJFileChooser = 0;
    static int compteurJTable = 0;
    static int compteurJLayeredPane = 0;
    static int compteurJTextArea = 0;
    static int compteurJTree = 0;
    static int compteurJLabel = 0;
    static int compteurJProgressBar = 0;
    static int compteurJSeparator = 0;
    static int compteurJToolTip = 0;
    static int compteurJApplet = 0;
    static int compteurJDialog = 0;
    static int compteurJFrame = 0;
    static int compteurJPanel = 0;
    static int compteurJScrollPane = 0;
    static int compteurJSplitPane = 0;
    static int compteurJTabbedPane = 0;
    static int compteurJToolBar = 0;
	
	static void Check(String ligne) {

        // Compter le nombre de JButton 
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JButton")) {
            compteurButton++;
        }
        // Compter le nombre de JCheckBox
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JCheckBox")) {
                compteurJCheckBox++;
            }
        // Compter le nombre de JComboBox
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JComboBox")) {
                compteurJComboBox++;
        }
        // Compter le nombre de JList
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JList")) {
            compteurJList++;
        }
        // Compter le nombre de JMenu
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JMenu")) {
            compteurJMenu++;
        }
        // Compter le nombre de JRadioButton
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JRadioButton")) {
            compteurJRadioButton++;
        }
        // Compter le nombre de JSlider
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JSlider")) {
            compteurJSlider++;
        }
        // Compter le nombre de JSpinner
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JSpinner")) {
            compteurJSpinner++;
        }
        // Compter le nombre de JTextField
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JTextField")) {
            compteurJTextField++;
        }
        // Compter le nombre de JPasswordField
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JPasswordField")) {
            compteurJPasswordField++;
        }
        // Compter le nombre de JColorChooser
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JColorChooser")) {
            compteurJColorChooser++;
        }
        // Compter le nombre de JEditorPane
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JEditorPane")) {
            compteurJEditorPane++;
        }
        // Compter le nombre de JTextPane
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JTextPane")) {
            compteurJTextPane++;
        }
        // Compter le nombre de JFileChooser
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JFileChooser")) {
            compteurJFileChooser++;
        }
        // Compter le nombre de JTable
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JTable")) {
            compteurJTable++;
        }
        // Compter le nombre de JTextArea
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JTextArea")) {
            compteurJTextArea++;
        }
        // Compter le nombre de JTree
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JTree")) {
            compteurJTree++;
        }
        // Compter le nombre de JLabel
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JLabel")) {
            compteurJLabel++;
        }
        // Compter le nombre de JProgressBar
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JProgressBar")) {
            compteurJProgressBar++;
        }
        // Compter le nombre de JSeparator
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("JSeparator")) {
            compteurJSeparator++;
        }
        // Compter le nombre de JToolTip
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JToolTip")) {
            compteurJToolTip++;
        }
        // Compter le nombre de JApplet
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JApplet")) {
            compteurJApplet++;
        }
        // Compter le nombre de JDialog
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JDialog")) {
            compteurJDialog++;
        }
        // Compter le nombre de JFrame
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JFrame")) {
            compteurJFrame++;
        }
        // Compter le nombre de JPanel
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JPanel")) {
            compteurJPanel++;
        }
        // Compter le nombre de JScrollPane
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JScrollPane")) {
            compteurJScrollPane++;
        }
        // Compter le nombre de JSplitPane
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JSplitPane")) {
            compteurJSplitPane++;
        }
        // Compter le nombre de JTabbedPane
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JTabbedPane")) {
            compteurJTabbedPane++;
        }
        // Compter le nombre de JToolBar
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JToolBar")) {
            compteurJToolBar++;
        }
        // Compter le nombre de JInternalFrame
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JInternalFrame")) {
            compteurJInternalFrame++;
        }
        // Compter le nombre de JLayeredPane
        if (Comment.IsVariable(ligne) || Comment.IsNew(ligne) &&  ligne.contains("new JLayeredPane")) {
            compteurJLayeredPane++;
        }
        
    
	}

    public static void LireF(File repertoire) {
        for (File fichier : repertoire.listFiles()) {
            if (fichier.isFile()) {
                System.out.println("Fichier: " + fichier.getName());
                // Vérifier l'extension du fichier
                if (fichier.getName().endsWith(".java")) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
                        String ligne;
                      
                
                        while ((ligne = reader.readLine()) != null) {
                        	if(Comment.ContainsComment(ligne)) {
                        		ligne = Comment.RemoveComment(ligne);
                        		Check(ligne);
                        		
                        	}
                        	
                        	if(Comment.IsCommentOnlyCompleted(ligne)) {
                        		Comment.JumpComment(ligne, null, reader);
                        	}
                        	
                        	if(Comment.ContainsOpeningComment(ligne)) {
                        		ligne = Comment.CodeOpeningComment(ligne);
                        		Check(ligne);
                        	}
                        	
                        	if(Comment.ContainsClosingComment(ligne)) {
                        		ligne = Comment.CodeClosingComment(ligne);
                        		Check(ligne);
                        	}
                        	
                        	if(!Comment.ContainsComment(ligne) && !Comment.IsCommentOnlyCompleted(ligne) && !Comment.ContainsOpeningComment(ligne) && !Comment.ContainsClosingComment(ligne) ) {
                        		Check(ligne);
                        	}
                        	
                                                   }
                
                        System.out.println("Nombre de boutons : " + compteurButton + "\n");
                        System.out.println("Nombre de JInternalFrame : " + compteurJInternalFrame + "\n");
                        System.out.println("Nombre de JComboBox : " + compteurJComboBox + "\n");
                        System.out.println("Nombre de JCheckBox : " + compteurJCheckBox + "\n");
                        System.out.println("Nombre de JList : " + compteurJList + "\n");
                        System.out.println("Nombre de JMenu : " + compteurJMenu + "\n");
                        System.out.println("Nombre de JRadioButton : " + compteurJRadioButton + "\n");
                        System.out.println("Nombre de JSlider : " + compteurJSlider + "\n");
                        System.out.println("Nombre de JSpinner : " + compteurJSpinner + "\n");
                        System.out.println("Nombre de JTextField : " + compteurJTextField + "\n");
                        System.out.println("Nombre de JPasswordField : " + compteurJPasswordField + "\n");
                        System.out.println("Nombre de JColorChooser : " + compteurJColorChooser + "\n");
                        System.out.println("Nombre de JEditorPane : " + compteurJEditorPane + "\n");
                        System.out.println("Nombre de JTextPane : " + compteurJTextPane + "\n");
                        System.out.println("Nombre de JFileChooser : " + compteurJFileChooser + "\n");
                        System.out.println("Nombre de JTable : " + compteurJTable + "\n");
                        System.out.println("Nombre de JTextArea : " + compteurJTextArea + "\n");
                        System.out.println("Nombre de JTree : " + compteurJTree + "\n");
                        System.out.println("Nombre de JLabel : " + compteurJLabel + "\n");
                        System.out.println("Nombre de JProgressBar : " + compteurJProgressBar + "\n");
                        System.out.println("Nombre de JSeparator : " + compteurJSeparator + "\n");
                        System.out.println("Nombre de JToolTip : " + compteurJToolTip + "\n");
                        System.out.println("Nombre de JApplet : " + compteurJApplet + "\n");
                        System.out.println("Nombre de JDialog : " + compteurJDialog + "\n");
                        System.out.println("Nombre de JFrame : " + compteurJFrame + "\n");
                        System.out.println("Nombre de JPanel : " + compteurJPanel + "\n");
                        System.out.println("Nombre de JScrollPane : " + compteurJScrollPane + "\n");
                        System.out.println("Nombre de JSplitPane : " + compteurJSplitPane + "\n");
                        System.out.println("Nombre de JTabbedPane : " + compteurJTabbedPane + "\n");
                        System.out.println("Nombre de JToolBar : " + compteurJToolBar + "\n");
                        System.out.println("Nombre de JLayeredPane : " + compteurJLayeredPane + "\n");
                
                    } catch (IOException e) {
                        e.printStackTrace();
                        }
                }
            }
            else if (fichier.isDirectory()) {
                System.out.println("Répertoire: " + fichier.getName());
                LireF(fichier); // Appel récursif pour lire les fichiers dans le sous-répertoire
            }
        }
    }
    public static void main(String[] args) {
        File test = new File("C:\\Users\\Ryad\\Desktop\\testsMetrics\\Nombre-ligne- de-code\\Test");
        LireF(test);
    }
}    