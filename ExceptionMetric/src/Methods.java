import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Methods {
	//fonction tedi fichier w te9rah w t3amer arraylist ta3 ExceptionInfo
	//fonction tedi arraylist ta3 mo9bil w tbedel les flag 
	static ArrayList<ExceptionInfo> fetchException (File fichier){
		ArrayList<ExceptionInfo> list = new ArrayList<>();
		boolean b=false;
		
		try (BufferedReader reader = new BufferedReader(new FileReader(fichier))){
			
			String line;
			while ((line = reader.readLine())!= null) {
				line =line.trim();
				line=RemoveQoute(line);
				if (!line.isEmpty() && !IsCommentOnlyCompleted(line)) {
					if (ContainsComment(line)) {
						line=RemoveComment(line);
					}
					else {
						ArrayList<String> Code=new ArrayList<>();
						if(FinishedComment(line)) {
	            			if(!ContainsOpeningComment(line)) {
	            				Code.add(CodeOpeningComment(line));
	            			}
	            			if(!ContainsClosingComment(line)) {
	            				Code.add(CodeClosingComment(line));
	            			}
	            		}
						else if (NotFinishedComment(line)) {
	            			JumpComment(line,Code,reader);
	            		}
	            		if(!Code.isEmpty()) {
	            			for(String code : Code) {
	            				
	            			}
	            		}
					}
					
				}
				
			}
		} catch (IOException e) {
	
			e.printStackTrace();
		}
				
		return list;
	}
	static Boolean IsThrows(String Line) {
		String line = Line;
		line = line.replaceAll(" ","");
		if(line.contains(")")) {
		return line.substring(line.lastIndexOf(")")).contains("throws ");
		}
		else {
			return false;
		}
	}
	static Boolean IsCatch(String Line) {
		   String line = Line;
		   line = line.replaceAll(" ","");
			line = line.trim();
			if(line.startsWith("}")) {
				line = line.replaceAll("}","");
			}
			return line.startsWith("catch");
	   }
	
	static Boolean IsThrow(String Line) {
		return Line.startsWith("throw ");
	}
	
	static String ThrowsException(String Line) {
		String line = Line.trim();
		int BI = line.indexOf("throws")+6;
		line = line.substring(BI);
		line = line.trim();
		line = line.replaceAll(" ", "");
		int BS = 0;
		if(line.contains("{")) {
		 BS = line.indexOf("{");
		 return line.substring(0,BS).replaceAll(" ", "");
		}
		else {
			return line.replaceAll(" ", "");
		}
		
	}
	
	static String ThrowException(String Line) {
		String line = Line;
		int BI = line.indexOf("new")+3;
		line = line.substring(BI);
		line = line.trim();
		line = line.replaceAll(" ", "");
		int BS = line.indexOf("(");
		return line.substring(0,BS);
	}
	
	static String CatchException(String Line) {
		String line = Line;
		int BI = line.indexOf("(")+1;
		line = line.substring(BI);
		line = line.trim();
		int BS = line.indexOf(" ");
		return line.substring(0,BS);
	}
	static boolean ContainsComment(String Line) {	
		
		 return Line.contains("//") ; 
		
	 }
	 static boolean ContainsOpeningComment(String Line) {
			return Line.startsWith("/*");
		}
	 static boolean ContainsClosingComment(String Line) {
			String line = Line.replaceAll(" ", "");
			return line.endsWith("*/");
		}
	 static String CodeOpeningComment(String Line) {
			return Line.substring(0,Line.indexOf("/*"));
		}
	 static String CodeClosingComment(String Line) {
			return Line.substring(Line.indexOf("*/")+2);
		}
	 
	 
	 static boolean FinishedComment(String Line) {
		 Line = Line.trim();
		return Line.contains("/*") && Line.contains("*/") ;	 
	 }
	static boolean NotFinishedComment(String Line) {
		Line = Line.trim();
		return Line.contains("/*") && !Line.contains("*/") ;
	}
	
	static boolean IsCommentOnlyCompleted(String Line) {
		Line = Line.trim();
	    String singleLineCommentPattern = "//.*"; 
	    String multiLineCommentPatternCompleted = "/\\*((?!(\\*/))[^\\n]|\\n)*(\\*/)";
	return Line.matches(multiLineCommentPatternCompleted)||Line.matches(singleLineCommentPattern);
	}
	 static void JumpComment (String Line,ArrayList<String> List,BufferedReader reader) {
			if(!ContainsOpeningComment(Line)) {
				List.add(CodeOpeningComment(Line));
			}
			System.out.println(Line);
			try {
				while ((Line = reader.readLine()) != null) { 
				Line = Line.trim();
				Line=RemoveQoute(Line);
				System.out.println(Line);
				if(Line.contains("*/")) {
					break;
				}
				}
			}
			catch(IOException e) {
				
			}
			if(!ContainsClosingComment(Line)) {
				List.add(CodeClosingComment(Line));
			}
		}
	public static boolean isComment(String line, boolean insideMultiLineComment) {
        // Removing leading and trailing white spaces for better detection
        line = line.trim();
        
        // If we are already inside a multi-line comment
        if (insideMultiLineComment) {
            // Check if the multi-line comment ends on this line
            if (line.endsWith("*/")) {
                // We've reached the end of the multi-line comment
                return true;
            } else {
                // The multi-line comment continues on the next line
                return true; // Return true because we're still in a comment
            }
        }
        
        // Checking for single-line comment
        if (line.startsWith("//")) {
            return true;
        }
        
        // Checking for multi-line comment
        if (line.startsWith("/*")) {
            // Check if the multi-line comment ends on the same line
            if (line.endsWith("*/")) {
                return true;
            } else {
                // Multi-line comment starts but doesn't end on the same line,
                // so we set the flag to indicate we're inside a multi-line comment
                return true;
            }
        }

        return false;
    }
	 
	 static boolean IsQoute(String Line) {
			Line = Line.trim();
			return Line.contains("\"");
		}
	 
	 static String RemoveQoute(String line) {
		   	String qoute;
		   	if(IsQoute(line)) {
		   	while(line.contains("\"")) {
		             
		   		int BI = line.indexOf("\"");
		   		 int BS = line.indexOf("\"",BI+1);
		   		  if (BS == -1) {
		   			  line = line.replace("\"", "");// Check if the closing quote was found
		   	            break; // Exit the loop if not found to avoid StringIndexOutOfBoundsException
		   	        }
		   		  qoute = line.substring(BI,BS+1);
		   		 line = line.replaceAll(Pattern.quote(qoute), "");
		   		 
		   		
		   		
		   							}
		   				}
		   	return line;
		   	
		   }
	 static String RemoveComment(String line) {
		 
	     int index = line.indexOf("//");
	     line = line.substring(0,index);
		 
			return line;
	 } 
	 

}
